package com.xiaopeng.appstore.bizcommon.logic.download;

import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.SpeedCalculator;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed;
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/* loaded from: classes2.dex */
public class OkDownloadListenerWrapper extends DownloadListener4WithSpeed {
    private static final String TAG = "OdListenerWrapper";
    private Map<String, ArrayList<XpDownloadListener>> mXpDownloadListenerMap = new ConcurrentHashMap();

    @Override // com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend.Listener4SpeedCallback
    public void blockEnd(DownloadTask task, int blockIndex, BlockInfo info, SpeedCalculator blockSpeed) {
    }

    @Override // com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend.Listener4SpeedCallback
    public void progressBlock(DownloadTask task, int blockIndex, long currentBlockOffset, SpeedCalculator blockSpeed) {
    }

    public void addXpDownloadListener(String url, XpDownloadListener xpDownloadListener) {
        if (xpDownloadListener == null) {
            return;
        }
        Logger.t(TAG).d("addXpDownloadListener url=" + url + " listener=" + xpDownloadListener.hashCode());
        ArrayList<XpDownloadListener> arrayList = this.mXpDownloadListenerMap.get(url);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.mXpDownloadListenerMap.put(url, arrayList);
        }
        if (arrayList.contains(xpDownloadListener)) {
            return;
        }
        arrayList.add(xpDownloadListener);
    }

    public void removeXpDownloadListener(XpDownloadListener xpDownloadListener) {
        int size = this.mXpDownloadListenerMap.size();
        Logger.t(TAG).d("removeXpDownloadListener size=" + size + " listener=" + xpDownloadListener.hashCode());
        ArrayList<String> arrayList = new ArrayList();
        for (int i = 0; i < size; i++) {
            for (Map.Entry<String, ArrayList<XpDownloadListener>> entry : this.mXpDownloadListenerMap.entrySet()) {
                ArrayList<XpDownloadListener> value = entry.getValue();
                if (value != null) {
                    value.remove(xpDownloadListener);
                    if (value.isEmpty()) {
                        arrayList.add(entry.getKey());
                    }
                }
            }
        }
        for (String str : arrayList) {
            this.mXpDownloadListenerMap.remove(str);
        }
    }

    public void removeXpDownloadListener(String url) {
        this.mXpDownloadListenerMap.remove(url);
        Logger.t(TAG).d("removeDownloadListener size=" + this.mXpDownloadListenerMap.size() + " url=" + url);
    }

    public XpDownloadListener[] getXpDownloadListeners(String url) {
        return getThreadSafeArray(this.mXpDownloadListenerMap.get(url));
    }

    @Override // com.liulishuo.okdownload.DownloadListener
    public void taskStart(final DownloadTask task) {
        Logger.t(TAG).d("taskStart: id=" + task.getId() + " url=" + task.getUrl());
    }

    @Override // com.liulishuo.okdownload.DownloadListener
    public void connectStart(DownloadTask task, int blockIndex, Map<String, List<String>> requestHeaderFields) {
        Logger.t(TAG).d("connectStart: id=" + task.getId() + " blockIndex=" + blockIndex);
    }

    @Override // com.liulishuo.okdownload.DownloadListener
    public void connectEnd(DownloadTask task, int blockIndex, int responseCode, Map<String, List<String>> responseHeaderFields) {
        Logger.t(TAG).d("connectEnd: id=" + task.getId() + " blockIndex=" + blockIndex + " responseCode=" + responseCode);
    }

    @Override // com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend.Listener4SpeedCallback
    public void infoReady(DownloadTask task, BreakpointInfo info, boolean fromBreakpoint, Listener4SpeedAssistExtend.Listener4SpeedModel model) {
        Logger.t(TAG).d("infoReady: id=" + task.getId() + " url=" + task.getUrl());
        XpDownloadListener[] threadSafeArray = getThreadSafeArray(task, this.mXpDownloadListenerMap);
        if (threadSafeArray == null) {
            return;
        }
        String url = task.getUrl();
        for (XpDownloadListener xpDownloadListener : threadSafeArray) {
            if (xpDownloadListener != null) {
                xpDownloadListener.onDownloadStart(task.getId(), url, task.getFilename(), info.getTotalLength());
            }
        }
    }

    @Override // com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend.Listener4SpeedCallback
    public void progress(DownloadTask task, long currentOffset, SpeedCalculator taskSpeed) {
        int i;
        Logger.t(TAG).v("progress: id=" + task.getId() + " currentOffset=" + currentOffset + " listener=" + hashCode(), new Object[0]);
        XpDownloadListener[] threadSafeArray = getThreadSafeArray(task, this.mXpDownloadListenerMap);
        if (threadSafeArray == null) {
            return;
        }
        String url = task.getUrl();
        int length = threadSafeArray.length;
        int i2 = 0;
        while (i2 < length) {
            XpDownloadListener xpDownloadListener = threadSafeArray[i2];
            if (xpDownloadListener == null || task.getInfo() == null) {
                i = i2;
            } else {
                i = i2;
                xpDownloadListener.onDownloadProgress(task.getId(), url, task.getInfo().getTotalLength(), currentOffset, taskSpeed.getBytesPerSecondAndFlush());
            }
            i2 = i + 1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x0109  */
    @Override // com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend.Listener4SpeedCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void taskEnd(com.liulishuo.okdownload.DownloadTask r6, com.liulishuo.okdownload.core.cause.EndCause r7, java.lang.Exception r8, com.liulishuo.okdownload.SpeedCalculator r9) {
        /*
            Method dump skipped, instructions count: 288
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.bizcommon.logic.download.OkDownloadListenerWrapper.taskEnd(com.liulishuo.okdownload.DownloadTask, com.liulishuo.okdownload.core.cause.EndCause, java.lang.Exception, com.liulishuo.okdownload.SpeedCalculator):void");
    }

    private static XpDownloadListener[] getThreadSafeArray(DownloadTask task, Map<String, ArrayList<XpDownloadListener>> realListenerMap) {
        return getThreadSafeArray(realListenerMap.get(task.getUrl()));
    }

    private static XpDownloadListener[] getThreadSafeArray(List<XpDownloadListener> listenerList) {
        if (listenerList == null || listenerList.isEmpty()) {
            return null;
        }
        XpDownloadListener[] xpDownloadListenerArr = new XpDownloadListener[listenerList.size()];
        listenerList.toArray(xpDownloadListenerArr);
        return xpDownloadListenerArr;
    }
}
