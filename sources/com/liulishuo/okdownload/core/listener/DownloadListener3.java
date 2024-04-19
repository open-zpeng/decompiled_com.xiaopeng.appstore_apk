package com.liulishuo.okdownload.core.listener;

import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.Util;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;
/* loaded from: classes2.dex */
public abstract class DownloadListener3 extends DownloadListener1 {
    protected abstract void canceled(DownloadTask downloadTask);

    protected abstract void completed(DownloadTask downloadTask);

    protected abstract void error(DownloadTask downloadTask, Exception exc);

    protected abstract void started(DownloadTask downloadTask);

    protected abstract void warn(DownloadTask downloadTask);

    @Override // com.liulishuo.okdownload.core.listener.assist.Listener1Assist.Listener1Callback
    public final void taskStart(DownloadTask downloadTask, Listener1Assist.Listener1Model listener1Model) {
        started(downloadTask);
    }

    /* renamed from: com.liulishuo.okdownload.core.listener.DownloadListener3$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$liulishuo$okdownload$core$cause$EndCause;

        static {
            int[] iArr = new int[EndCause.values().length];
            $SwitchMap$com$liulishuo$okdownload$core$cause$EndCause = iArr;
            try {
                iArr[EndCause.COMPLETED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$liulishuo$okdownload$core$cause$EndCause[EndCause.CANCELED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$liulishuo$okdownload$core$cause$EndCause[EndCause.ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$liulishuo$okdownload$core$cause$EndCause[EndCause.PRE_ALLOCATE_FAILED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$liulishuo$okdownload$core$cause$EndCause[EndCause.FILE_BUSY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$liulishuo$okdownload$core$cause$EndCause[EndCause.SAME_TASK_BUSY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    @Override // com.liulishuo.okdownload.core.listener.assist.Listener1Assist.Listener1Callback
    public void taskEnd(DownloadTask downloadTask, EndCause endCause, Exception exc, Listener1Assist.Listener1Model listener1Model) {
        switch (AnonymousClass1.$SwitchMap$com$liulishuo$okdownload$core$cause$EndCause[endCause.ordinal()]) {
            case 1:
                completed(downloadTask);
                return;
            case 2:
                canceled(downloadTask);
                return;
            case 3:
            case 4:
                error(downloadTask, exc);
                return;
            case 5:
            case 6:
                warn(downloadTask);
                return;
            default:
                Util.w("DownloadListener3", "Don't support " + endCause);
                return;
        }
    }
}
