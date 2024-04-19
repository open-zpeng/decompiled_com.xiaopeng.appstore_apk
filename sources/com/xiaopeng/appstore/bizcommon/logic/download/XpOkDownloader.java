package com.xiaopeng.appstore.bizcommon.logic.download;

import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.liulishuo.okdownload.DownloadMonitor;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.OkDownload;
import com.liulishuo.okdownload.StatusUtil;
import com.liulishuo.okdownload.core.IdentifiedTask;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointStore;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.connection.DownloadOkHttp3Connection;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.entities.db.XpAppStoreDatabase;
import com.xiaopeng.appstore.bizcommon.entities.db.XpOkDownloadDao;
import com.xiaopeng.appstore.bizcommon.logic.download.XpOkDownloader;
import com.xiaopeng.appstore.bizcommon.utils.DatabaseUtils;
import com.xiaopeng.appstore.bizcommon.utils.DownloadUtils;
import com.xiaopeng.appstore.libcommon.utils.NumberUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class XpOkDownloader implements IXpDownloader {
    private static final int CALLBACK_MIN_INTERVAL = 300;
    private static final String TAG = "XpDownloader";
    private static volatile XpOkDownloader sInstance;
    private final Map<String, String> mDownloadTagUrlMap;
    private final Map<String, String> mDownloadTaskIdUrlMap;
    private final Map<String, String> mDownloadUrlTaskIdMap;
    private final Map<String, DownloadTask> mDownloadingTaskMap;
    private final Set<Integer> mTaskList;
    private final XpOkDownloadDao mXpOkDownloadDao;
    private final boolean mListenerOnUIThread = false;
    private final OkDownloadListenerWrapper mDownloadListenerWrapper = new OkDownloadListenerWrapper();
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private IXpDownloaderMonitor mXpDownloaderMonitor = new XpDownloaderDefaultMonitor();

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public boolean isReady() {
        return true;
    }

    public static XpOkDownloader get() {
        if (sInstance == null) {
            synchronized (XpOkDownloader.class) {
                if (sInstance == null) {
                    sInstance = new XpOkDownloader();
                }
            }
        }
        return sInstance;
    }

    private XpOkDownloader() {
        DownloadOkHttp3Connection.Factory factory = new DownloadOkHttp3Connection.Factory();
        factory.setBuilder(new OkHttpClient.Builder().readTimeout(45L, TimeUnit.SECONDS).connectTimeout(45L, TimeUnit.SECONDS).writeTimeout(45L, TimeUnit.SECONDS));
        OkDownload.setSingletonInstance(new OkDownload.Builder(Utils.getApp()).connectionFactory(factory).build());
        this.mTaskList = new HashSet();
        this.mXpOkDownloadDao = XpAppStoreDatabase.getInstance().getXpOkDownloadDao();
        OkDownload.with().setMonitor(new AnonymousClass1());
        this.mDownloadingTaskMap = new ArrayMap();
        this.mDownloadUrlTaskIdMap = new ArrayMap();
        this.mDownloadTaskIdUrlMap = new ArrayMap();
        this.mDownloadTagUrlMap = new HashMap();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.bizcommon.logic.download.XpOkDownloader$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass1 implements DownloadMonitor {
        @Override // com.liulishuo.okdownload.DownloadMonitor
        public void taskDownloadFromBeginning(DownloadTask task, BreakpointInfo info, ResumeFailedCause cause) {
        }

        @Override // com.liulishuo.okdownload.DownloadMonitor
        public void taskDownloadFromBreakpoint(DownloadTask task, BreakpointInfo info) {
        }

        AnonymousClass1() {
        }

        @Override // com.liulishuo.okdownload.DownloadMonitor
        public void taskStart(final DownloadTask task) {
            XpOkDownloader.this.mTaskList.add(Integer.valueOf(task.getId()));
            XpOkDownloader.this.mExecutor.execute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.download.-$$Lambda$XpOkDownloader$1$7Ot5FenoPSKfVeaKPrpa5pnORjc
                @Override // java.lang.Runnable
                public final void run() {
                    XpOkDownloader.AnonymousClass1.this.lambda$taskStart$1$XpOkDownloader$1(task);
                }
            });
            Logger.t(XpOkDownloader.TAG).d("taskStart: url=" + task.getUrl());
        }

        public /* synthetic */ void lambda$taskStart$1$XpOkDownloader$1(final DownloadTask downloadTask) {
            DatabaseUtils.tryExecute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.download.-$$Lambda$XpOkDownloader$1$h84vd0TGwzU79Xk74TNJILcFJhI
                @Override // java.lang.Runnable
                public final void run() {
                    XpOkDownloader.AnonymousClass1.this.lambda$taskStart$0$XpOkDownloader$1(downloadTask);
                }
            });
        }

        public /* synthetic */ void lambda$taskStart$0$XpOkDownloader$1(DownloadTask downloadTask) {
            XpOkDownloader.this.mXpOkDownloadDao.insert(downloadTask.getUrl(), (String) downloadTask.getTag(), downloadTask.getId());
        }

        @Override // com.liulishuo.okdownload.DownloadMonitor
        public void taskEnd(DownloadTask task, EndCause cause, Exception realCause) {
            String valueOf = String.valueOf(task.getId());
            final String str = (String) XpOkDownloader.this.mDownloadTaskIdUrlMap.get(valueOf);
            if (cause == EndCause.COMPLETED) {
                if (XpOkDownloader.this.mXpDownloaderMonitor != null) {
                    XpOkDownloader.this.mXpDownloaderMonitor.onDownloadComplete(valueOf, str, task.getFile());
                }
                Logger.t(XpOkDownloader.TAG).d("taskEnd: url=" + task.getUrl() + ". Completed.");
            } else if (cause == EndCause.CANCELED) {
                Logger.t(XpOkDownloader.TAG).d("taskEnd: url=" + task.getUrl() + ". Canceled.");
            } else {
                Logger.t(XpOkDownloader.TAG).d("taskEnd: url=" + task.getUrl() + ". " + cause.name() + ", msg=" + (realCause != null ? realCause.getMessage() : null));
            }
            XpOkDownloader.this.mTaskList.remove(Integer.valueOf(task.getId()));
            XpOkDownloader.this.mDownloadingTaskMap.remove(valueOf);
            if (!TextUtils.isEmpty(str)) {
                XpOkDownloader.this.mDownloadUrlTaskIdMap.remove(str);
            }
            XpOkDownloader.this.mDownloadTaskIdUrlMap.remove(valueOf);
            String str2 = (String) task.getTag();
            if (!TextUtils.isEmpty(str2)) {
                XpOkDownloader.this.mDownloadTagUrlMap.remove(str2);
            }
            XpOkDownloader.this.mExecutor.execute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.download.-$$Lambda$XpOkDownloader$1$OaiWCZZ8waiVLFJvasjq103zec8
                @Override // java.lang.Runnable
                public final void run() {
                    XpOkDownloader.AnonymousClass1.this.lambda$taskEnd$3$XpOkDownloader$1(str);
                }
            });
        }

        public /* synthetic */ void lambda$taskEnd$3$XpOkDownloader$1(final String str) {
            DatabaseUtils.tryExecute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.download.-$$Lambda$XpOkDownloader$1$8KWZm-pgVhsynErf0diEnK0UKDA
                @Override // java.lang.Runnable
                public final void run() {
                    XpOkDownloader.AnonymousClass1.this.lambda$taskEnd$2$XpOkDownloader$1(str);
                }
            });
        }

        public /* synthetic */ void lambda$taskEnd$2$XpOkDownloader$1(String str) {
            XpOkDownloader.this.mXpOkDownloadDao.delete(str);
        }
    }

    public void setMonitor(IXpDownloaderMonitor monitor) {
        this.mXpDownloaderMonitor = monitor;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void observeDownload(String url, XpDownloadListener xpDownloadListener) {
        this.mDownloadListenerWrapper.addXpDownloadListener(url, xpDownloadListener);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void removeObserver(XpDownloadListener xpDownloadListener) {
        removeDownloadObserver(xpDownloadListener);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void removeObserver(String url, XpDownloadListener xpDownloadListener) {
        throw new UnsupportedOperationException("not support");
    }

    public void removeDownloadObserver(XpDownloadListener xpDownloadListener) {
        this.mDownloadListenerWrapper.removeXpDownloadListener(xpDownloadListener);
        Logger.t(TAG).d("removeDownloadObserver listener=" + xpDownloadListener.toString());
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void release() {
        Map<String, DownloadTask> map = this.mDownloadingTaskMap;
        if (map != null) {
            for (Map.Entry<String, DownloadTask> entry : map.entrySet()) {
                this.mDownloadListenerWrapper.removeXpDownloadListener(entry.getValue().getUrl());
            }
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public int fetchDownloadStatus(long id) {
        DownloadTask downloadTask = this.mDownloadingTaskMap.get(String.valueOf(id));
        if (downloadTask != null) {
            StatusUtil.Status status = StatusUtil.getStatus(downloadTask);
            Logger.t(TAG).d("fetchDownloadStatus is %s.", status.name());
            return translateOkDownloadStatus(status);
        }
        return 0;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public int fetchDownloadStatus(String url) {
        return fetchDownloadStatus(NumberUtils.stringToLong(this.mDownloadUrlTaskIdMap.get(url)));
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public int getDownloadStatus(String url) {
        if (TextUtils.isEmpty(url)) {
            return 0;
        }
        StatusUtil.Status status = StatusUtil.getStatus(createFinder(url));
        Logger.t(TAG).d("getDownloadStatus is %s.", status.name());
        return translateOkDownloadStatus(status);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public int getDownloadStatus(long id) {
        DownloadTask downloadTask = this.mDownloadingTaskMap.get(String.valueOf(id));
        if (downloadTask != null) {
            StatusUtil.Status status = StatusUtil.getStatus(downloadTask);
            Logger.t(TAG).d("getDownloadStatus with id(%s) is %s.", Long.valueOf(id), status.name());
            return translateOkDownloadStatus(status);
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.bizcommon.logic.download.XpOkDownloader$2  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$liulishuo$okdownload$StatusUtil$Status;

        static {
            int[] iArr = new int[StatusUtil.Status.values().length];
            $SwitchMap$com$liulishuo$okdownload$StatusUtil$Status = iArr;
            try {
                iArr[StatusUtil.Status.PENDING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$liulishuo$okdownload$StatusUtil$Status[StatusUtil.Status.RUNNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$liulishuo$okdownload$StatusUtil$Status[StatusUtil.Status.COMPLETED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$liulishuo$okdownload$StatusUtil$Status[StatusUtil.Status.IDLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$liulishuo$okdownload$StatusUtil$Status[StatusUtil.Status.UNKNOWN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private int translateOkDownloadStatus(StatusUtil.Status okDownloadStatus) {
        int i = AnonymousClass2.$SwitchMap$com$liulishuo$okdownload$StatusUtil$Status[okDownloadStatus.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return i != 4 ? 0 : 3;
                }
                return 4;
            }
            return 1;
        }
        return 2;
    }

    public List<String> getDownloadingTasks() {
        if (this.mDownloadingTaskMap != null) {
            return new ArrayList(this.mDownloadingTaskMap.keySet());
        }
        return null;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public long enqueue(final String url, String title) {
        String str = this.mDownloadUrlTaskIdMap.get(url);
        DownloadTask downloadTask = !TextUtils.isEmpty(str) ? this.mDownloadingTaskMap.get(str) : null;
        if (downloadTask != null) {
            downloadTask.setTag(title);
            StatusUtil.Status status = StatusUtil.getStatus(downloadTask);
            if (status == StatusUtil.Status.IDLE || status == StatusUtil.Status.UNKNOWN) {
                downloadTask.enqueue(this.mDownloadListenerWrapper);
            } else {
                OkDownloadListenerWrapper okDownloadListenerWrapper = this.mDownloadListenerWrapper;
                if (okDownloadListenerWrapper != null) {
                    downloadTask.replaceListener(okDownloadListenerWrapper);
                }
            }
        } else {
            DownloadTask findSameTask = OkDownload.with().downloadDispatcher().findSameTask(createFinder(url));
            if (findSameTask != null) {
                findSameTask.cancel();
            }
            downloadTask = createTask(url);
            downloadTask.setTag(title);
            downloadTask.enqueue(this.mDownloadListenerWrapper);
        }
        String valueOf = String.valueOf(downloadTask.getId());
        this.mDownloadingTaskMap.put(valueOf, downloadTask);
        this.mDownloadUrlTaskIdMap.put(url, valueOf);
        this.mDownloadTaskIdUrlMap.put(valueOf, url);
        if (!TextUtils.isEmpty(title)) {
            this.mDownloadTagUrlMap.put(title, url);
        }
        return downloadTask.getId();
    }

    @Deprecated
    public void enqueue(List<String> urlList, XpDownloadListener xpDownloadListener) {
        if (urlList == null || urlList.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList(urlList.size());
        for (String str : urlList) {
            String str2 = this.mDownloadUrlTaskIdMap.get(str);
            DownloadTask downloadTask = TextUtils.isEmpty(str2) ? null : this.mDownloadingTaskMap.get(str2);
            if (downloadTask != null) {
                StatusUtil.Status status = StatusUtil.getStatus(downloadTask);
                if (status == StatusUtil.Status.IDLE || status == StatusUtil.Status.UNKNOWN) {
                    arrayList.add(downloadTask);
                } else {
                    OkDownloadListenerWrapper okDownloadListenerWrapper = this.mDownloadListenerWrapper;
                    if (okDownloadListenerWrapper != null) {
                        downloadTask.replaceListener(okDownloadListenerWrapper);
                    }
                }
            } else {
                DownloadTask findSameTask = OkDownload.with().downloadDispatcher().findSameTask(createFinder(str));
                if (findSameTask != null) {
                    findSameTask.cancel();
                }
                downloadTask = createTask(str);
                arrayList.add(downloadTask);
            }
            String valueOf = String.valueOf(downloadTask.getId());
            this.mDownloadingTaskMap.put(valueOf, downloadTask);
            this.mDownloadUrlTaskIdMap.put(str, valueOf);
            this.mDownloadTaskIdUrlMap.put(valueOf, str);
        }
        if (arrayList.isEmpty()) {
            return;
        }
        DownloadTask[] downloadTaskArr = new DownloadTask[arrayList.size()];
        arrayList.toArray(downloadTaskArr);
        DownloadTask.enqueue(downloadTaskArr, this.mDownloadListenerWrapper);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void pause(String url) {
        DownloadTask existTask = getExistTask(url);
        if (existTask != null) {
            existTask.cancel();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void pause(long id) {
        DownloadTask downloadTask = this.mDownloadingTaskMap.get(String.valueOf(id));
        if (downloadTask != null) {
            downloadTask.cancel();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void cancel(String url) {
        DownloadTask createFinder = createFinder(url);
        removeLocalData(createFinder);
        OkDownload.with().downloadDispatcher().cancel(createFinder);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void cancel(long id) {
        removeLocalData(id);
        OkDownload.with().downloadDispatcher().cancel((int) id);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public long getTotalBytes(long id) {
        BreakpointInfo breakpointInfo = OkDownload.with().breakpointStore().get((int) id);
        if (breakpointInfo != null) {
            return breakpointInfo.getTotalLength();
        }
        return 0L;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public long getTotalBytes(String url) {
        int stringToInt = NumberUtils.stringToInt(this.mDownloadUrlTaskIdMap.get(url), -1);
        if (stringToInt > 0) {
            return getTotalBytes(stringToInt);
        }
        return 0L;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public long getDownloadedBytes(long id) {
        BreakpointInfo breakpointInfo = OkDownload.with().breakpointStore().get((int) id);
        if (breakpointInfo != null) {
            return breakpointInfo.getTotalOffset();
        }
        return 0L;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public long getDownloadedBytes(String url) {
        int stringToInt = NumberUtils.stringToInt(this.mDownloadUrlTaskIdMap.get(url), -1);
        if (stringToInt > 0) {
            return getDownloadedBytes(stringToInt);
        }
        return 0L;
    }

    public boolean downloadCompleted(String url) {
        return StatusUtil.isCompleted(createFinder(url));
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public File getLocalFile(String url) {
        DownloadTask createFinder = createFinder(url);
        BreakpointStore breakpointStore = OkDownload.with().breakpointStore();
        BreakpointInfo breakpointInfo = breakpointStore.get(createFinder.getId());
        String filename = createFinder.getFilename();
        File parentFile = createFinder.getParentFile();
        File file = createFinder.getFile();
        if (breakpointInfo != null) {
            if (file != null && file.equals(breakpointInfo.getFile()) && file.exists() && breakpointInfo.getTotalOffset() == breakpointInfo.getTotalLength()) {
                return file;
            }
            if (!breakpointInfo.isChunked() && breakpointInfo.getTotalLength() <= 0) {
                return null;
            }
            if (file != null && file.equals(breakpointInfo.getFile()) && file.exists() && breakpointInfo.getTotalOffset() == breakpointInfo.getTotalLength()) {
                return file;
            }
            if ((filename != null || breakpointInfo.getFile() == null || !breakpointInfo.getFile().exists()) && file != null && file.equals(breakpointInfo.getFile()) && file.exists()) {
                return null;
            }
        } else if (!breakpointStore.isOnlyMemoryCache() && !breakpointStore.isFileDirty(createFinder.getId())) {
            if (file != null && file.exists()) {
                return file;
            }
            String responseFilename = breakpointStore.getResponseFilename(createFinder.getUrl());
            if (responseFilename != null) {
                File file2 = new File(parentFile, responseFilename);
                if (file2.exists()) {
                    return file2;
                }
            }
        }
        return null;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void removeLocalData(String url) {
        DownloadTask createFinder;
        if (TextUtils.isEmpty(url) || (createFinder = createFinder(url)) == null) {
            return;
        }
        removeLocalData(createFinder);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void removeLocalData(long taskId) {
        int i = (int) taskId;
        BreakpointInfo breakpointInfo = OkDownload.with().breakpointStore().get(i);
        if (breakpointInfo != null) {
            File file = breakpointInfo.getFile();
            if (file != null) {
                file.delete();
            }
            OkDownload.with().breakpointStore().remove(i);
        }
    }

    private void removeLocalData(IdentifiedTask task) {
        removeLocalData(task.getId());
    }

    private DownloadTask createFinder(String url) {
        return new DownloadTask.Builder(url, DownloadUtils.getDownloadDirFile()).setFilename(DownloadUtils.getNameFromUrl(url)).build();
    }

    private DownloadTask createTask(String url) {
        return new DownloadTask.Builder(url, DownloadUtils.getDownloadDirFile()).setFilename(DownloadUtils.getNameFromUrl(url)).setMinIntervalMillisCallbackProcess(300).setPassIfAlreadyCompleted(false).setAutoCallbackToUIThread(false).build();
    }

    private DownloadTask getExistTask(String url) {
        String str = this.mDownloadUrlTaskIdMap.get(url);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return this.mDownloadingTaskMap.get(str);
    }
}
