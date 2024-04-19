package com.xiaopeng.appstore.bizcommon.logic.download;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import androidx.collection.ArraySet;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.bizcommon.utils.DownloadUtils;
import com.xiaopeng.appstore.libcommon.utils.FileUtils;
import com.xiaopeng.appstore.storeprovider.store.RMDownloadListener;
import com.xiaopeng.appstore.storeprovider.store.StoreResourceProvider;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceDownloadInfo;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceType;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/* loaded from: classes2.dex */
public class ResMgrDownloader implements IXpDownloader, RMDownloadListener {
    private static final String TAG = "ResMgrDownloader";
    private final ArraySet<Long> mDownloadStartedIds;
    private volatile boolean mIsMemCacheLoaded;
    private volatile boolean mIsResServiceConnected;
    private final Object mMemCacheReady;
    private final Map<String, ResourceDownloadInfo> mMemMapCache;
    private final Object mResourceReady;
    private StoreResourceProvider mStoreResourceProvider;
    private final Map<String, XpDownloadListener> mStringXpDownloadListenerMap;

    private int parseDownloadStatus(long downloadStatus) {
        int i = (int) downloadStatus;
        if (i != 1) {
            if (i != 2) {
                if (i != 4) {
                    return i != 8 ? 0 : 4;
                }
                return 3;
            }
            return 1;
        }
        return 2;
    }

    private int parseResInfoStatus(int resStatus) {
        if (resStatus != 2) {
            if (resStatus != 3) {
                if (resStatus != 5) {
                    if (resStatus != 6) {
                        return resStatus != 7 ? 0 : 5;
                    }
                    return 4;
                }
                return 3;
            }
            return 1;
        }
        return 2;
    }

    @Override // com.xiaopeng.appstore.storeprovider.store.RMDownloadListener
    public void onMenuOpenCallback(String s) {
    }

    @Override // com.xiaopeng.appstore.storeprovider.store.RMDownloadListener
    public void unbindService() {
    }

    private ResMgrDownloader() {
        this.mDownloadStartedIds = new ArraySet<>();
        this.mMemCacheReady = new Object();
        this.mIsMemCacheLoaded = false;
        this.mResourceReady = new Object();
        this.mIsResServiceConnected = false;
        this.mStringXpDownloadListenerMap = new ConcurrentHashMap();
        this.mMemMapCache = new ConcurrentHashMap();
    }

    public static ResMgrDownloader get() {
        return SingletonHandler.sResMgrDownloader;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public boolean isReady() {
        return waitResServiceConnected() && waitMemCacheReady();
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void init() {
        StoreResourceProvider storeResProvider = XuiMgrHelper.get().getStoreResProvider();
        if (storeResProvider == null) {
            Logger.t(TAG).w("init error: StoreResourceProvider is null, maybe XUIService not connected.", new Object[0]);
            return;
        }
        this.mStoreResourceProvider = storeResProvider;
        storeResProvider.registerDownloadListener(this);
        if (storeResProvider.isConnected()) {
            Logger.t(TAG).i("store res provider already connected", new Object[0]);
            notifyResConnected();
            return;
        }
        storeResProvider.setServiceConnectionListenerClient(new ServiceConnection() { // from class: com.xiaopeng.appstore.bizcommon.logic.download.ResMgrDownloader.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                Logger.t(ResMgrDownloader.TAG).d("store res provider connected:" + service + ", " + name);
                ResMgrDownloader.this.notifyResConnected();
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                Logger.t(ResMgrDownloader.TAG).d("store res provider disconnected:" + name);
                ResMgrDownloader.this.notifyResDisconnected();
            }

            @Override // android.content.ServiceConnection
            public void onBindingDied(ComponentName name) {
                Logger.t(ResMgrDownloader.TAG).d("store res provider onBindingDied:" + name);
            }
        });
        if (!storeResProvider.isConnecting()) {
            storeResProvider.connect();
        } else {
            Logger.t(TAG).i("store res provider connecting", new Object[0]);
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void release() {
        if (this.mStoreResourceProvider != null) {
            Logger.t(TAG).i("release resProvider:" + this.mStoreResourceProvider, new Object[0]);
            this.mStoreResourceProvider.unregisterDownloadListener(this);
            this.mStoreResourceProvider.unbindService();
            this.mStoreResourceProvider = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyResConnected() {
        synchronized (this.mResourceReady) {
            this.mIsResServiceConnected = true;
            this.mResourceReady.notifyAll();
        }
        loadMemCache();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyResDisconnected() {
        this.mIsResServiceConnected = false;
    }

    public boolean waitResServiceConnected() {
        synchronized (this.mResourceReady) {
            while (!this.mIsResServiceConnected) {
                try {
                    Logger.t(TAG).d("Waiting Res service connected");
                    this.mResourceReady.wait();
                } catch (InterruptedException e) {
                    Logger.t(TAG).e("isResServiceConnected error:" + e, new Object[0]);
                    Thread.currentThread().interrupt();
                }
            }
        }
        return true;
    }

    private void loadMemCache() {
        if (this.mIsMemCacheLoaded) {
            Logger.t(TAG).d("loadMemCache, already loaded.");
            return;
        }
        StoreResourceProvider storeResourceProvider = this.mStoreResourceProvider;
        if (storeResourceProvider == null) {
            Logger.t(TAG).w("loadMemCache error: StoreResProvider is null.", new Object[0]);
            return;
        }
        List<ResourceDownloadInfo> queryAllInfo = storeResourceProvider.queryAllInfo();
        if (queryAllInfo != null) {
            for (ResourceDownloadInfo resourceDownloadInfo : queryAllInfo) {
                this.mMemMapCache.put(resourceDownloadInfo.getUrl(), resourceDownloadInfo);
            }
        }
        synchronized (this.mMemCacheReady) {
            this.mIsMemCacheLoaded = true;
            this.mMemCacheReady.notifyAll();
        }
        Logger.t(TAG).i("loadMemCache finish:" + this.mMemMapCache, new Object[0]);
    }

    public boolean waitMemCacheReady() {
        if (this.mIsMemCacheLoaded) {
            Logger.t(TAG).d("waitMemCacheReady, ready.");
            return true;
        }
        Logger.t(TAG).d("waitMemCacheReady start, Waiting mem cache ready.");
        synchronized (this.mMemCacheReady) {
            if (!this.mIsMemCacheLoaded) {
                try {
                    this.mMemCacheReady.wait();
                } catch (InterruptedException e) {
                    Logger.e(TAG, "waitMemCacheReady error:" + e);
                }
            }
            Logger.d(TAG, "waitMemCacheReady end, Mem cache ready.");
        }
        return true;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public int fetchDownloadStatus(long id) {
        StoreResourceProvider storeResourceProvider = this.mStoreResourceProvider;
        if (storeResourceProvider == null) {
            Logger.t(TAG).w("fetchDownloadStatus error: StoreResProvider is null." + id, new Object[0]);
            return 0;
        }
        return storeResourceProvider.fetchDownloadStatus(id);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public int fetchDownloadStatus(String url) {
        StoreResourceProvider storeResourceProvider = this.mStoreResourceProvider;
        if (storeResourceProvider == null) {
            Logger.t(TAG).w("fetchDownloadStatus error: StoreResProvider is null." + url, new Object[0]);
            return 0;
        }
        int fetchDownloadStatus = storeResourceProvider.fetchDownloadStatus(url);
        if (fetchDownloadStatus != 1) {
            if (fetchDownloadStatus != 2) {
                if (fetchDownloadStatus != 4) {
                    if (fetchDownloadStatus != 8) {
                        return fetchDownloadStatus != 16 ? 0 : 5;
                    }
                    return 4;
                }
                return 3;
            }
            return 1;
        }
        return 2;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public long enqueue(String url, String title) {
        StoreResourceProvider storeResourceProvider = this.mStoreResourceProvider;
        if (storeResourceProvider == null) {
            Logger.t(TAG).w("enqueue error: StoreResProvider is null." + url, new Object[0]);
            return -1L;
        }
        return storeResourceProvider.enqueue(url, title);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public int getDownloadStatus(String url) {
        Map<String, ResourceDownloadInfo> map = this.mMemMapCache;
        int i = 0;
        if (map != null) {
            ResourceDownloadInfo resourceDownloadInfo = map.get(url);
            if (resourceDownloadInfo != null) {
                i = resourceDownloadInfo.getStatus();
                Logger.t(TAG).d("getDownloadStatus status:" + i + ", name:" + resourceDownloadInfo.getTitle());
            } else {
                Logger.t(TAG).d("getDownloadStatus info not found:" + url);
            }
        } else {
            Logger.t(TAG).w("getDownloadStatus, mem cache not init:" + url, new Object[0]);
        }
        return parseResInfoStatus(i);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public int getDownloadStatus(long id) {
        StoreResourceProvider storeResourceProvider = this.mStoreResourceProvider;
        if (storeResourceProvider == null) {
            Logger.t(TAG).w("getDownloadStatus error: StoreResProvider is null." + id, new Object[0]);
            return 0;
        }
        return storeResourceProvider.getDownloadStatus(id);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void observeDownload(String url, XpDownloadListener xpDownloadListener) {
        if (isReady()) {
            this.mStringXpDownloadListenerMap.put(url, xpDownloadListener);
            Logger.t(TAG).d("observeDownload url = " + url + "   listener = " + xpDownloadListener + " mStringXpDownloadListenerMap = " + this.mStringXpDownloadListenerMap);
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void removeObserver(XpDownloadListener xpDownloadListener) {
        Logger.t(TAG).d("removeObserver  listener = " + xpDownloadListener);
        this.mStringXpDownloadListenerMap.values().remove(xpDownloadListener);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void removeObserver(String url, XpDownloadListener xpDownloadListener) {
        Iterator<Map.Entry<String, XpDownloadListener>> it = this.mStringXpDownloadListenerMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, XpDownloadListener> next = it.next();
            if (next == null) {
                Logger.t(TAG).d("removeObserver ignore null entry.");
            } else if (next.getKey().equals(url) && next.getValue().equals(xpDownloadListener)) {
                Logger.t(TAG).i("removeObserver," + xpDownloadListener + ", " + url, new Object[0]);
                it.remove();
            }
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void pause(String url) {
        if (!TextUtils.isEmpty(url)) {
            String str = TAG;
            Logger.t(str).i("pause  url = " + url, new Object[0]);
            StoreResourceProvider storeResourceProvider = this.mStoreResourceProvider;
            if (storeResourceProvider == null) {
                Logger.t(str).w("pause error: StoreResProvider is null." + url, new Object[0]);
                return;
            } else {
                storeResourceProvider.pauseDownload(url);
                return;
            }
        }
        Logger.t(TAG).w("pause error, url is null: " + url, new Object[0]);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void pause(long id) {
        String str = TAG;
        Logger.t(str).i("pause id=" + id, new Object[0]);
        StoreResourceProvider storeResourceProvider = this.mStoreResourceProvider;
        if (storeResourceProvider == null) {
            Logger.t(str).w("pause error: StoreResProvider is null." + id, new Object[0]);
        } else {
            storeResourceProvider.pauseResDownload(ResourceType.APP, String.valueOf(id));
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void cancel(String url) {
        String str = TAG;
        Logger.t(str).i("cancel url=" + url, new Object[0]);
        if (!TextUtils.isEmpty(url)) {
            StoreResourceProvider storeResourceProvider = this.mStoreResourceProvider;
            if (storeResourceProvider == null) {
                Logger.t(str).w("cancel error: StoreResProvider is null." + url, new Object[0]);
                return;
            }
            storeResourceProvider.cancelDownload(url);
        } else {
            Logger.t(str).w("cancel error, url is null: " + url, new Object[0]);
        }
        removeLocalData(url);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void cancel(long id) {
        String str = TAG;
        Logger.t(str).i("cancel id=" + id, new Object[0]);
        StoreResourceProvider storeResourceProvider = this.mStoreResourceProvider;
        if (storeResourceProvider == null) {
            Logger.t(str).w("cancel error: StoreResProvider is null." + id, new Object[0]);
        } else {
            storeResourceProvider.cancelResDownload(ResourceType.APP, String.valueOf(id));
        }
        removeLocalData(id);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public long getTotalBytes(long id) {
        StoreResourceProvider storeResourceProvider = this.mStoreResourceProvider;
        if (storeResourceProvider == null) {
            Logger.t(TAG).w("getTotalBytes error: StoreResProvider is null." + id, new Object[0]);
            return 0L;
        }
        return storeResourceProvider.getTotalBytes(id);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public long getTotalBytes(String url) {
        Map<String, ResourceDownloadInfo> map = this.mMemMapCache;
        if (map != null) {
            ResourceDownloadInfo resourceDownloadInfo = map.get(url);
            if (resourceDownloadInfo != null) {
                return resourceDownloadInfo.getTotalBytes();
            }
            Logger.t(TAG).d("getTotalBytes info not found:" + url);
            return 0L;
        }
        Logger.t(TAG).w("getTotalBytes, mem cache not init:" + url, new Object[0]);
        return 0L;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public long getDownloadedBytes(long id) {
        StoreResourceProvider storeResourceProvider = this.mStoreResourceProvider;
        if (storeResourceProvider == null) {
            Logger.t(TAG).w("getDownloadedBytes error: StoreResProvider is null." + id, new Object[0]);
            return 0L;
        }
        return storeResourceProvider.getDownloadedBytes(id);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public long getDownloadedBytes(String url) {
        Map<String, ResourceDownloadInfo> map = this.mMemMapCache;
        if (map != null) {
            ResourceDownloadInfo resourceDownloadInfo = map.get(url);
            if (resourceDownloadInfo != null) {
                return resourceDownloadInfo.getDownloadedBytes();
            }
            Logger.t(TAG).d("getDownloadedBytes info not found:" + url);
            return 0L;
        }
        Logger.t(TAG).d("getDownloadedBytes, mem cache not init:" + url);
        return 0L;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void removeLocalData(String url) {
        Map<String, ResourceDownloadInfo> map = this.mMemMapCache;
        if (map != null) {
            map.remove(url);
        }
        StoreResourceProvider storeResourceProvider = this.mStoreResourceProvider;
        if (storeResourceProvider == null) {
            Logger.t(TAG).w("removeLocalData error: StoreResProvider is null." + url, new Object[0]);
        } else {
            storeResourceProvider.removeLocalData(url);
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public void removeLocalData(long id) {
        StoreResourceProvider storeResourceProvider = this.mStoreResourceProvider;
        if (storeResourceProvider == null) {
            Logger.t(TAG).w("removeLocalData error: StoreResProvider is null." + id, new Object[0]);
        } else {
            storeResourceProvider.removeLocalData(id);
        }
        Map<String, ResourceDownloadInfo> map = this.mMemMapCache;
        if (map != null) {
            Iterator<Map.Entry<String, ResourceDownloadInfo>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                if (it.next().getValue().getDownloadId() == id) {
                    it.remove();
                }
            }
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader
    public File getLocalFile(String url) {
        StoreResourceProvider storeResourceProvider = this.mStoreResourceProvider;
        if (storeResourceProvider == null) {
            Logger.t(TAG).w("getLocalFile error: StoreResProvider is null." + url, new Object[0]);
            return null;
        }
        return storeResourceProvider.getLocalFile(url);
    }

    @Override // com.xiaopeng.appstore.storeprovider.store.RMDownloadListener
    public void onDownloadCallback(int type, ResourceDownloadInfo resourceDownloadInfo) {
        String str = TAG;
        Logger.t(str).d("onDownloadCallback start type = " + type + " resourceDownloadInfo = " + resourceDownloadInfo);
        if (resourceDownloadInfo == null) {
            Logger.t(str).w("onDownloadCall ignore, info is null. type=" + type, new Object[0]);
            return;
        }
        String url = resourceDownloadInfo.getUrl();
        if (TextUtils.isEmpty(url)) {
            Logger.t(str).w("onDownloadCall ignore, url is null. type=" + type + " " + resourceDownloadInfo, new Object[0]);
            return;
        }
        XpDownloadListener xpDownloadListener = this.mStringXpDownloadListenerMap.get(url);
        Logger.t(str).d("XpDownloadListenerMap = " + this.mStringXpDownloadListenerMap);
        long downloadId = resourceDownloadInfo.getDownloadId();
        synchronized (this.mStringXpDownloadListenerMap) {
            if (xpDownloadListener != null) {
                switch (type) {
                    case 2:
                        this.mMemMapCache.put(url, resourceDownloadInfo);
                        break;
                    case 3:
                        this.mMemMapCache.put(url, resourceDownloadInfo);
                        if (this.mDownloadStartedIds.contains(Long.valueOf(downloadId))) {
                            Logger.t(str).d("onDownloadCallback end, progress changed, resourceDownloadInfo = " + resourceDownloadInfo);
                            xpDownloadListener.onDownloadProgress((int) downloadId, resourceDownloadInfo.getUrl(), resourceDownloadInfo.getTotalBytes(), resourceDownloadInfo.getDownloadedBytes(), resourceDownloadInfo.getSpeed());
                            break;
                        } else if (resourceDownloadInfo.getTotalBytes() > 0) {
                            Logger.t(str).d("onDownloadRunning, onDownloadStart.");
                            xpDownloadListener.onDownloadStart((int) downloadId, resourceDownloadInfo.getUrl(), DownloadUtils.getNameFromUrl(resourceDownloadInfo.getUrl()), resourceDownloadInfo.getTotalBytes());
                            this.mDownloadStartedIds.add(Long.valueOf(downloadId));
                            break;
                        } else {
                            Logger.t(str).d("onDownloadRunning, totalBytes is invalid, ignored.");
                            break;
                        }
                    case 4:
                        this.mMemMapCache.remove(url);
                        this.mDownloadStartedIds.remove(Long.valueOf(downloadId));
                        Logger.t(str).i("onDownloadCallback end, cancel, resourceDownloadInfo = " + resourceDownloadInfo, new Object[0]);
                        xpDownloadListener.onDownloadCancel((int) resourceDownloadInfo.getDownloadId(), resourceDownloadInfo.getUrl());
                        break;
                    case 5:
                        this.mMemMapCache.put(url, resourceDownloadInfo);
                        this.mDownloadStartedIds.remove(Long.valueOf(downloadId));
                        xpDownloadListener.onDownloadPause((int) resourceDownloadInfo.getDownloadId(), resourceDownloadInfo.getUrl());
                        break;
                    case 6:
                        this.mMemMapCache.put(url, resourceDownloadInfo);
                        this.mDownloadStartedIds.remove(Long.valueOf(downloadId));
                        File file = new File(FileUtils.getPath(resourceDownloadInfo.getFileUri()));
                        Logger.t(str).i("onDownloadCallback end, finished, downloadUrl = " + url + "       resourceDownloadInfo file uri = " + resourceDownloadInfo.getFileUri() + "   completed file = " + file + " file exist = " + file.exists(), new Object[0]);
                        xpDownloadListener.onDownloadComplete((int) resourceDownloadInfo.getDownloadId(), resourceDownloadInfo.getUrl(), file);
                        break;
                    case 7:
                        this.mMemMapCache.put(url, resourceDownloadInfo);
                        this.mDownloadStartedIds.remove(Long.valueOf(downloadId));
                        xpDownloadListener.onDownloadError((int) resourceDownloadInfo.getDownloadId(), resourceDownloadInfo.getUrl(), "download error");
                        break;
                }
            } else {
                Logger.t(str).w("onDownloadCall listener is null. type=" + type + "   url = " + url, new Object[0]);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingletonHandler {
        private static final ResMgrDownloader sResMgrDownloader = new ResMgrDownloader();

        private SingletonHandler() {
        }
    }
}
