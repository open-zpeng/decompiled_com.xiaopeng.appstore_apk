package com.xiaopeng.appstore.bizcommon.logic.download;

import androidx.lifecycle.LifecycleOwner;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/* loaded from: classes2.dex */
public interface IXpDownloader {
    public static final int DOWNLOAD_STATUS_COMPLETED = 4;
    public static final int DOWNLOAD_STATUS_DOWNLOADING = 1;
    public static final int DOWNLOAD_STATUS_ERROR = 5;
    public static final int DOWNLOAD_STATUS_IDLE = 0;
    public static final int DOWNLOAD_STATUS_PAUSE = 3;
    public static final int DOWNLOAD_STATUS_PENDING = 2;

    static String statusToString(int downloadStatus) {
        return downloadStatus != 0 ? downloadStatus != 1 ? downloadStatus != 2 ? downloadStatus != 3 ? downloadStatus != 4 ? downloadStatus != 5 ? "UNKNOWN" : "DOWNLOAD_STATUS_ERROR" : "DOWNLOAD_STATUS_COMPLETED" : "DOWNLOAD_STATUS_PAUSE" : "DOWNLOAD_STATUS_PENDING" : "DOWNLOAD_STATUS_DOWNLOADING" : "DOWNLOAD_STATUS_IDLE";
    }

    void cancel(long id);

    void cancel(String url);

    long enqueue(String url, String title);

    int fetchDownloadStatus(long id);

    int fetchDownloadStatus(String url);

    int getDownloadStatus(long id);

    int getDownloadStatus(String url);

    long getDownloadedBytes(long id);

    long getDownloadedBytes(String url);

    File getLocalFile(String url);

    long getTotalBytes(long id);

    long getTotalBytes(String url);

    default void init() {
    }

    boolean isReady();

    void observeDownload(String url, XpDownloadListener xpDownloadListener);

    void pause(long id);

    void pause(String url);

    default void registerLifeCycle(LifecycleOwner owner) {
    }

    default void release() {
    }

    void removeLocalData(long id);

    void removeLocalData(String url);

    void removeObserver(XpDownloadListener xpDownloadListener);

    void removeObserver(String url, XpDownloadListener xpDownloadListener);

    default void unregisterLifeCycle(LifecycleOwner owner) {
    }

    /* loaded from: classes2.dex */
    public static class Factory {
        private static final String TAG = "XpDownloaderFactory";
        public static final int TYPE_DM_ANDROID = 1;
        @Deprecated
        public static final int TYPE_DM_OKDOWNLOAD = 0;
        public static final int TYPE_OKDOWNLOAD = 2;
        static int sDownloaderType = 1;

        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes2.dex */
        public @interface DownloaderType {
        }

        public static void setDownloader(int type) {
            sDownloaderType = type;
            Logger.t(TAG).d("setDownloader=" + type);
        }

        public static void initAsync(final Runnable onFinish) {
            AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.download.-$$Lambda$IXpDownloader$Factory$HxTpU9-BP3qFO0oK8IsALPNOZ4g
                @Override // java.lang.Runnable
                public final void run() {
                    IXpDownloader.Factory.lambda$initAsync$0(onFinish);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ void lambda$initAsync$0(Runnable runnable) {
            get().init();
            if (runnable != null) {
                runnable.run();
            }
        }

        public static void release() {
            get().release();
        }

        public static int getDownloaderType() {
            return sDownloaderType;
        }

        public static IXpDownloader get() {
            Logger.t(TAG).v("getDownloader, type=" + sDownloaderType, new Object[0]);
            int i = sDownloaderType;
            if (i == 0 || i == 1) {
                return ResMgrDownloader.get();
            }
            return XpOkDownloader.get();
        }
    }
}
