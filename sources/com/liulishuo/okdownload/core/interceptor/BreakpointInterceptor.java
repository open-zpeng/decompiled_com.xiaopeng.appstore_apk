package com.liulishuo.okdownload.core.interceptor;

import com.liulishuo.okdownload.OkDownload;
import com.liulishuo.okdownload.core.Util;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.connection.DownloadConnection;
import com.liulishuo.okdownload.core.download.DownloadChain;
import com.liulishuo.okdownload.core.exception.InterruptException;
import com.liulishuo.okdownload.core.exception.RetryException;
import com.liulishuo.okdownload.core.file.MultiPointOutputStream;
import com.liulishuo.okdownload.core.interceptor.Interceptor;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/* loaded from: classes2.dex */
public class BreakpointInterceptor implements Interceptor.Connect, Interceptor.Fetch {
    private static final Pattern CONTENT_RANGE_RIGHT_VALUE = Pattern.compile(".*\\d+ *- *(\\d+) */ *\\d+");
    private static final String TAG = "BreakpointInterceptor";

    @Override // com.liulishuo.okdownload.core.interceptor.Interceptor.Connect
    public DownloadConnection.Connected interceptConnect(DownloadChain downloadChain) throws IOException {
        DownloadConnection.Connected processConnect = downloadChain.processConnect();
        BreakpointInfo info = downloadChain.getInfo();
        if (downloadChain.getCache().isInterrupt()) {
            throw InterruptException.SIGNAL;
        }
        if (info.getBlockCount() == 1 && !info.isChunked()) {
            long exactContentLengthRangeFrom0 = getExactContentLengthRangeFrom0(processConnect);
            long totalLength = info.getTotalLength();
            if (exactContentLengthRangeFrom0 > 0 && exactContentLengthRangeFrom0 != totalLength) {
                Util.d(TAG, "SingleBlock special check: the response instance-length[" + exactContentLengthRangeFrom0 + "] isn't equal to the instance length from trial-connection[" + totalLength + "]");
                boolean z = info.getBlock(0).getRangeLeft() != 0;
                BlockInfo blockInfo = new BlockInfo(0L, exactContentLengthRangeFrom0);
                info.resetBlockInfos();
                info.addBlock(blockInfo);
                if (z) {
                    Util.w(TAG, "Discard breakpoint because of on this special case, we have to download from beginning");
                    throw new RetryException("Discard breakpoint because of on this special case, we have to download from beginning");
                }
                OkDownload.with().callbackDispatcher().dispatch().downloadFromBeginning(downloadChain.getTask(), info, ResumeFailedCause.CONTENT_LENGTH_CHANGED);
            }
        }
        try {
            if (downloadChain.getDownloadStore().update(info)) {
                return processConnect;
            }
            throw new IOException("Update store failed!");
        } catch (Exception e) {
            throw new IOException("Update store failed!", e);
        }
    }

    @Override // com.liulishuo.okdownload.core.interceptor.Interceptor.Fetch
    public long interceptFetch(DownloadChain downloadChain) throws IOException {
        long responseContentLength = downloadChain.getResponseContentLength();
        int blockIndex = downloadChain.getBlockIndex();
        boolean z = responseContentLength != -1;
        long j = 0;
        MultiPointOutputStream outputStream = downloadChain.getOutputStream();
        while (true) {
            try {
                long loopFetch = downloadChain.loopFetch();
                if (loopFetch == -1) {
                    break;
                }
                j += loopFetch;
            } finally {
                downloadChain.flushNoCallbackIncreaseBytes();
                if (!downloadChain.getCache().isUserCanceled()) {
                    outputStream.done(blockIndex);
                }
            }
        }
        if (z) {
            outputStream.inspectComplete(blockIndex);
            if (j != responseContentLength) {
                throw new IOException("Fetch-length isn't equal to the response content-length, " + j + "!= " + responseContentLength);
            }
        }
        return j;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    long getExactContentLengthRangeFrom0(com.liulishuo.okdownload.core.connection.DownloadConnection.Connected r7) {
        /*
            r6 = this;
            java.lang.String r0 = "Content-Range"
            java.lang.String r0 = r7.getResponseHeaderField(r0)
            boolean r1 = com.liulishuo.okdownload.core.Util.isEmpty(r0)
            r2 = 0
            if (r1 != 0) goto L1a
            long r0 = getRangeRightFromContentRange(r0)
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 <= 0) goto L1a
            r4 = 1
            long r0 = r0 + r4
            goto L1c
        L1a:
            r0 = -1
        L1c:
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 >= 0) goto L30
            java.lang.String r2 = "Content-Length"
            java.lang.String r7 = r7.getResponseHeaderField(r2)
            boolean r2 = com.liulishuo.okdownload.core.Util.isEmpty(r7)
            if (r2 != 0) goto L30
            long r0 = java.lang.Long.parseLong(r7)
        L30:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.liulishuo.okdownload.core.interceptor.BreakpointInterceptor.getExactContentLengthRangeFrom0(com.liulishuo.okdownload.core.connection.DownloadConnection$Connected):long");
    }

    static long getRangeRightFromContentRange(String str) {
        Matcher matcher = CONTENT_RANGE_RIGHT_VALUE.matcher(str);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        return -1L;
    }
}
