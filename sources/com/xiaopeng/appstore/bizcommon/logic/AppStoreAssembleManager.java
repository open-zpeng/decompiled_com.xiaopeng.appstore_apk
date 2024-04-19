package com.xiaopeng.appstore.bizcommon.logic;

import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.common.BizLifecycle;
import com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.router.RouterConstants;
import com.xiaopeng.appstore.bizcommon.router.RouterManager;
import com.xiaopeng.appstore.bizcommon.router.provider.AppStoreProvider;
import com.xiaopeng.appstore.libcommon.utils.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
/* loaded from: classes2.dex */
public abstract class AppStoreAssembleManager implements BizLifecycle {
    public static final int APP_STATE_DELETING = 8;
    public static final int APP_STATE_DOWNLOAD_PAUSE = 2;
    public static final int APP_STATE_DOWNLOAD_RUNNING = 1;
    public static final int APP_STATE_DOWNLOAD_SUCCEED = 7;
    public static final int APP_STATE_ERROR = 101;
    public static final int APP_STATE_HAS_NEW_VER = 6;
    public static final int APP_STATE_INSTALL_RUNNING = 3;
    public static final int APP_STATE_INSTALL_SUCCEED = 4;
    public static final int APP_STATE_LOADING = 5;
    public static final int APP_STATE_NORMAL = 0;
    public static final int APP_STATE_PENDING = 10001;
    public static final int APP_STATE_UNKNOWN = 10000;
    protected static final int MAX_RUNNING_TASK = 5;
    protected static final float PROGRESS_DOWNLOAD_PERCENT = 0.8f;
    protected static final String TAG = "AssembleMgr";
    protected static final String TAG_ASSEMBLE = "AssembleStage";
    private static volatile AppStoreAssembleManager sInstance;
    private static volatile AppStoreAssembleManager sInstanceRes;
    private AppStoreProvider mAppStoreProvider;
    private final Map<String, Set<ItemAssembleObserver>> mObserversMap = new ArrayMap();
    private final Map<String, Set<ItemAssembleObserver>> mSoftObserversMap = new ArrayMap();
    private final Set<ItemAssembleObserver> mObserverList = new LinkedHashSet();
    protected final Set<String> mDeletingPackageList = new HashSet(1);
    private final Map<String, AssembleDataContainer> mPendingMap = new LinkedHashMap();

    public boolean canExecute(int state) {
        return state == 0 || state == 1 || state == 2 || state == 4 || state == 5 || state == 6 || state == 7 || state == 101;
    }

    @Deprecated
    public void cancel(String downloadUrl) {
    }

    public void cancelWithPackage(String packageName) {
    }

    public abstract void delete(String packageName, UserHandle user, String appName, Consumer<String> onFailed);

    @Deprecated
    public boolean pause(String url) {
        return false;
    }

    public void pauseWithPackage(String packageName) {
    }

    public abstract void restoreDelay(long millis);

    public abstract void start(String downloadUrl, String packageName, String label, String iconUrl);

    public abstract void startLocalWithConfig(String configUrl, String configMd5, String packageName, String apkMd5, File file, AppComponentManager.OnApplyConfigCallback callback);

    public abstract void startWithConfig(String downloadUrl, String md5, String configUrl, String configMd5, String packageName, String label, String iconUrl);

    public abstract void startWithConfig(List<AssembleDataContainer> dataList);

    public abstract void updateLocalConfig(String configUrl, String configMd5, String packageName);

    @Deprecated
    public void waitCancel(String downloadUrl) {
    }

    @Deprecated
    public boolean waitPause(String url) {
        return false;
    }

    public static String stateToStr(int state) {
        return state == 5 ? "APP_STATE_LOADING" : state == 1 ? "APP_STATE_DOWNLOAD_RUNNING" : state == 2 ? "APP_STATE_DOWNLOAD_PAUSE" : state == 7 ? "APP_STATE_DOWNLOAD_SUCCEED" : state == 6 ? "APP_STATE_HAS_NEW_VER" : state == 0 ? "APP_STATE_NORMAL" : state == 3 ? "APP_STATE_INSTALL_RUNNING" : state == 4 ? "APP_STATE_INSTALL_SUCCEED" : state == 8 ? "APP_STATE_DELETING" : state == 101 ? "APP_STATE_ERROR" : state == 10001 ? "APP_STATE_PENDING" : state + "";
    }

    public static AppStoreAssembleManager get() {
        return getRes();
    }

    public static AppStoreAssembleManager getRes() {
        if (sInstanceRes == null) {
            synchronized (AppStoreAssembleManager.class) {
                if (sInstanceRes == null) {
                    sInstanceRes = new AssembleManagerRes();
                }
            }
        }
        return sInstanceRes;
    }

    public void init() {
        this.mAppStoreProvider = (AppStoreProvider) RouterManager.get().getModule(RouterConstants.AppStoreProviderPaths.APP_STORE_MAIN_PROVIDER);
    }

    public void release() {
        this.mObserversMap.clear();
        clearSoftObservers();
        this.mObserverList.clear();
    }

    public void insertPending(AssembleDataContainer data) {
        synchronized (this.mPendingMap) {
            Logger.t(TAG).i("insertPending:" + data.getPackageName(), new Object[0]);
            this.mPendingMap.put(data.getPackageName(), data);
        }
    }

    public boolean isPending(String packageName) {
        boolean containsKey;
        synchronized (this.mPendingMap) {
            containsKey = this.mPendingMap.containsKey(packageName);
        }
        return containsKey;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013 A[Catch: all -> 0x003f, TryCatch #0 {, blocks: (B:4:0x0003, B:5:0x000d, B:7:0x0013, B:9:0x0029, B:12:0x003a, B:15:0x003d), top: B:20:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean isPendingWithUrl(java.lang.String r5) {
        /*
            r4 = this;
            java.util.Map<java.lang.String, com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer> r0 = r4.mPendingMap
            monitor-enter(r0)
            java.util.Map<java.lang.String, com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer> r1 = r4.mPendingMap     // Catch: java.lang.Throwable -> L3f
            java.util.Set r1 = r1.entrySet()     // Catch: java.lang.Throwable -> L3f
            java.util.Iterator r1 = r1.iterator()     // Catch: java.lang.Throwable -> L3f
        Ld:
            boolean r2 = r1.hasNext()     // Catch: java.lang.Throwable -> L3f
            if (r2 == 0) goto L3c
            java.lang.Object r2 = r1.next()     // Catch: java.lang.Throwable -> L3f
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2     // Catch: java.lang.Throwable -> L3f
            java.lang.Object r3 = r2.getValue()     // Catch: java.lang.Throwable -> L3f
            com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer r3 = (com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer) r3     // Catch: java.lang.Throwable -> L3f
            java.lang.String r3 = r3.getDownloadUrl()     // Catch: java.lang.Throwable -> L3f
            boolean r3 = r3.equals(r5)     // Catch: java.lang.Throwable -> L3f
            if (r3 != 0) goto L39
            java.lang.Object r2 = r2.getValue()     // Catch: java.lang.Throwable -> L3f
            com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer r2 = (com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer) r2     // Catch: java.lang.Throwable -> L3f
            java.lang.String r2 = r2.getConfigUrl()     // Catch: java.lang.Throwable -> L3f
            boolean r2 = r2.equals(r5)     // Catch: java.lang.Throwable -> L3f
            if (r2 == 0) goto Ld
        L39:
            r5 = 1
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3f
            return r5
        L3c:
            r5 = 0
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3f
            return r5
        L3f:
            r5 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3f
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager.isPendingWithUrl(java.lang.String):boolean");
    }

    protected AssembleDataContainer removePending(String packageName) {
        Logger.t(TAG).i("removePending:" + packageName, new Object[0]);
        return this.mPendingMap.remove(packageName);
    }

    protected boolean removePendingWithUrl(String downloadUrl, boolean dispatchNormalState) {
        AssembleDataContainer value;
        synchronized (this.mPendingMap) {
            Iterator<Map.Entry<String, AssembleDataContainer>> it = this.mPendingMap.entrySet().iterator();
            do {
                if (!it.hasNext()) {
                    return false;
                }
                value = it.next().getValue();
            } while (!value.getDownloadUrl().equals(downloadUrl));
            it.remove();
            Logger.t(TAG).d("remove pending url:" + downloadUrl);
            if (dispatchNormalState) {
                value.setState(hasNewVersion(value.getPackageName()) ? 6 : 0);
                dispatchStateChange(value);
            }
            return true;
        }
    }

    protected boolean taskFull() {
        return getAssemblingPnList().size() >= 5;
    }

    protected void processTask() {
        List<String> assemblingPnList = getAssemblingPnList();
        Logger.t(TAG).i("processTask, assembling:" + assemblingPnList.size() + ", pending:" + this.mPendingMap.size(), new Object[0]);
        if (assemblingPnList.size() >= 5) {
            return;
        }
        synchronized (this.mPendingMap) {
            Map<String, AssembleDataContainer> map = this.mPendingMap;
            if (map.isEmpty()) {
                return;
            }
            Iterator<Map.Entry<String, AssembleDataContainer>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, AssembleDataContainer> next = it.next();
                Logger.t(TAG).i("processTask:" + next.getValue(), new Object[0]);
                it.remove();
                startWithConfig(Collections.singletonList(next.getValue()));
                if (assemblingPnList.size() >= 5) {
                    return;
                }
            }
        }
    }

    public void addObserver(String key, ItemAssembleObserver observer) {
        addObserverToMap(this.mObserversMap, key, observer);
    }

    public void addObserver(ItemAssembleObserver observer) {
        this.mObserverList.add(observer);
    }

    public void addSoftObserver(String key, ItemAssembleObserver observer) {
        addObserverToMap(this.mSoftObserversMap, key, observer);
    }

    public void removeObserver(String key) {
        this.mObserversMap.remove(key);
        this.mSoftObserversMap.remove(key);
    }

    public void removeObserverAll(ItemAssembleObserver observer) {
        removeObserverInMap(this.mObserversMap, observer);
        removeObserverInMap(this.mSoftObserversMap, observer);
        this.mObserverList.remove(observer);
    }

    public void removeObserverInList(ItemAssembleObserver observer) {
        this.mObserverList.remove(observer);
    }

    public void removeObserver(String packageName, ItemAssembleObserver observer) {
        if (observer == null) {
            return;
        }
        Set<ItemAssembleObserver> set = this.mObserversMap.get(packageName);
        if (set != null) {
            set.remove(observer);
            if (set.isEmpty()) {
                this.mObserversMap.remove(packageName);
            }
        }
        removeSoftObserver(packageName, observer);
    }

    public void removeSoftObserver(String packageName, Function<ItemAssembleObserver, Boolean> check) {
        Set<ItemAssembleObserver> set;
        if (TextUtils.isEmpty(packageName) || check == null || (set = this.mSoftObserversMap.get(packageName)) == null) {
            return;
        }
        Iterator<ItemAssembleObserver> it = set.iterator();
        while (it.hasNext()) {
            ItemAssembleObserver next = it.next();
            if (next != null && ((Boolean) Optional.ofNullable(check.apply(next)).orElse(false)).booleanValue()) {
                Logger.t(TAG).i("removeSoftObserver with check, pn:" + packageName + ", observer:" + next + ", list:" + set, new Object[0]);
                it.remove();
            }
        }
    }

    public void removeSoftObserver(String packageName, ItemAssembleObserver observer) {
        Set<ItemAssembleObserver> set;
        if (TextUtils.isEmpty(packageName) || observer == null || (set = this.mSoftObserversMap.get(packageName)) == null) {
            return;
        }
        set.remove(observer);
        if (set.isEmpty()) {
            this.mSoftObserversMap.remove(packageName);
        }
    }

    public void clearSoftObservers() {
        this.mSoftObserversMap.clear();
    }

    public boolean isDeleting(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        return this.mDeletingPackageList.contains(packageName);
    }

    public boolean hasNewVersion(String packageName) {
        return this.mAppStoreProvider.hasNewVersion(packageName);
    }

    public List<String> getAssemblingPnList() {
        return getAssemblingPnList(false);
    }

    public List<String> getAssemblingPnList(boolean includePausedItem) {
        Collection<AssembleDataContainer> urlAssembleMap = AssembleDataCache.get().getUrlAssembleMap();
        if (urlAssembleMap == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(urlAssembleMap.size());
        for (AssembleDataContainer assembleDataContainer : urlAssembleMap) {
            if (assembleDataContainer.getState() != 2 || includePausedItem) {
                arrayList.add(assembleDataContainer.getPackageName());
            }
        }
        return arrayList;
    }

    public float getProgress(String packageName, String downloadUrl) {
        AssembleDataContainer findWithPn = findWithPn(packageName);
        if (findWithPn != null) {
            Logger.t(TAG).d("getProgress, return assembleData:" + findWithPn);
            return findWithPn.getProgress();
        }
        return 0.0f;
    }

    public int getState(String packageName, String downloadUrl, String configUrl) {
        if (isPending(packageName)) {
            return 10001;
        }
        AssembleDataContainer findWithPn = findWithPn(packageName);
        if (findWithPn != null) {
            Logger.t(TAG).d("getState, return " + stateToStr(parseAssembleState(findWithPn)) + ": " + findWithPn);
            return parseAssembleState(findWithPn);
        } else if (AppComponentManager.get().isInstalled(packageName)) {
            if (hasNewVersion(packageName)) {
                Logger.t(TAG).d("getState, return APP_STATE_HAS_NEW_VER:" + packageName + " " + downloadUrl);
                return 6;
            }
            Logger.t(TAG).d("getState, return APP_STATE_INSTALL_SUCCEED:" + packageName + " " + downloadUrl);
            return 4;
        } else {
            Logger.t(TAG).d("getState, return APP_STATE_NORMAL:" + packageName + " " + downloadUrl);
            return 0;
        }
    }

    private int parseAssembleState(AssembleDataContainer data) {
        if (data.getState() == 4) {
            return hasNewVersion(data.getPackageName()) ? 6 : 4;
        }
        return data.getState();
    }

    public void removeCacheAsync(String downloadUrl) {
        AssembleDataCache.get().removeAsync(downloadUrl);
    }

    public void removeCache(String downloadUrl) {
        AssembleDataCache.get().lambda$removeAsync$3$AssembleDataCache(downloadUrl);
    }

    public void removeCache(List<String> urlList) {
        AssembleDataCache.get().remove(urlList);
    }

    protected void updateCacheAsync(AssembleDataContainer data, int downloadId) {
        data.setDownloadId(downloadId);
        AssembleDataCache.get().updateAsync(data);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateCacheAsync(AssembleDataContainer data) {
        AssembleDataCache.get().updateAsync(data);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateCache(AssembleDataContainer data) {
        AssembleDataCache.get().lambda$updateAsync$6$AssembleDataCache(data);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AssembleDataContainer findOrCreate(String downloadUrl, String configUrl, String packageName, String iconUrl, String label) {
        return AssembleDataCache.get().findInMemOrCreate(downloadUrl, configUrl, packageName, iconUrl, label);
    }

    protected AssembleDataContainer find(String downloadUrl) {
        return AssembleDataCache.get().findInMem(downloadUrl);
    }

    public AssembleDataContainer findWithPn(String pn) {
        return AssembleDataCache.get().findInMemWithPn(pn);
    }

    public boolean isRunning(String pn) {
        AssembleDataContainer findWithPn = findWithPn(pn);
        if (findWithPn != null) {
            int state = findWithPn(pn).getState();
            boolean z = (state == 2 || state == 101) ? false : true;
            Logger.t(TAG).i("AppIconClick, app is assembling, ignore clicking. download app=>" + pn + " state=" + findWithPn.getState(), new Object[0]);
            return z;
        }
        return false;
    }

    public void dispatchStateChange(AssembleDataContainer data) {
        ItemAssembleObserver[] threadSafeItemObserverList = getThreadSafeItemObserverList(this.mObserversMap, data.getPackageName());
        if (threadSafeItemObserverList != null) {
            for (ItemAssembleObserver itemAssembleObserver : threadSafeItemObserverList) {
                if (itemAssembleObserver != null) {
                    itemAssembleObserver.onStateChange(data);
                }
            }
        }
        ItemAssembleObserver[] threadSafeItemObserverList2 = getThreadSafeItemObserverList(this.mSoftObserversMap, data.getPackageName());
        if (threadSafeItemObserverList2 != null) {
            for (ItemAssembleObserver itemAssembleObserver2 : threadSafeItemObserverList2) {
                if (itemAssembleObserver2 != null) {
                    itemAssembleObserver2.onStateChange(data);
                }
            }
        }
        ItemAssembleObserver[] threadSafeObserverList = getThreadSafeObserverList(this.mObserverList);
        if (threadSafeObserverList != null) {
            for (ItemAssembleObserver itemAssembleObserver3 : threadSafeObserverList) {
                if (itemAssembleObserver3 != null) {
                    itemAssembleObserver3.onStateChange(data);
                }
            }
        }
    }

    public void dispatchProgressChange(AssembleDataContainer data) {
        Logger.t(TAG).v("dispatchProgressChange:" + data, new Object[0]);
        ItemAssembleObserver[] threadSafeItemObserverList = getThreadSafeItemObserverList(this.mObserversMap, data.getPackageName());
        if (threadSafeItemObserverList != null) {
            for (ItemAssembleObserver itemAssembleObserver : threadSafeItemObserverList) {
                if (itemAssembleObserver != null) {
                    itemAssembleObserver.onProgressChange(data);
                }
            }
        }
        ItemAssembleObserver[] threadSafeItemObserverList2 = getThreadSafeItemObserverList(this.mSoftObserversMap, data.getPackageName());
        if (threadSafeItemObserverList2 != null) {
            for (ItemAssembleObserver itemAssembleObserver2 : threadSafeItemObserverList2) {
                if (itemAssembleObserver2 != null) {
                    itemAssembleObserver2.onProgressChange(data);
                }
            }
        }
        ItemAssembleObserver[] threadSafeObserverList = getThreadSafeObserverList(this.mObserverList);
        if (threadSafeObserverList != null) {
            for (ItemAssembleObserver itemAssembleObserver3 : threadSafeObserverList) {
                if (itemAssembleObserver3 != null) {
                    itemAssembleObserver3.onProgressChange(data);
                }
            }
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.common.BizLifecycle
    public void stop() {
        Logger.t(TAG).d("stop callback");
        clearSoftObservers();
    }

    protected static ItemAssembleObserver[] getThreadSafeItemObserverList(Map<String, Set<ItemAssembleObserver>> observerMap, String packageName) {
        Set<ItemAssembleObserver> set;
        if (TextUtils.isEmpty(packageName) || (set = observerMap.get(packageName)) == null || set.size() <= 0) {
            return null;
        }
        ItemAssembleObserver[] itemAssembleObserverArr = new ItemAssembleObserver[set.size()];
        set.toArray(itemAssembleObserverArr);
        return itemAssembleObserverArr;
    }

    protected static ItemAssembleObserver[] getThreadSafeObserverList(Set<ItemAssembleObserver> listenerList) {
        if (listenerList == null || listenerList.size() <= 0) {
            return null;
        }
        ItemAssembleObserver[] itemAssembleObserverArr = new ItemAssembleObserver[listenerList.size()];
        listenerList.toArray(itemAssembleObserverArr);
        return itemAssembleObserverArr;
    }

    protected static void removeObserverInMap(Map<String, Set<ItemAssembleObserver>> map, ItemAssembleObserver observer) {
        if (map == null || observer == null) {
            return;
        }
        ArrayList<String> arrayList = new ArrayList();
        for (Map.Entry<String, Set<ItemAssembleObserver>> entry : map.entrySet()) {
            Set<ItemAssembleObserver> value = entry.getValue();
            if (value != null) {
                value.remove(observer);
                if (value.isEmpty()) {
                    arrayList.add(entry.getKey());
                }
            }
        }
        for (String str : arrayList) {
            map.remove(str);
        }
    }

    protected static void addObserverToMap(Map<String, Set<ItemAssembleObserver>> map, String packageName, ItemAssembleObserver observer) {
        Set<ItemAssembleObserver> set = map.get(packageName);
        if (set == null) {
            set = new LinkedHashSet<>();
            map.put(packageName, set);
        }
        set.add(observer);
    }

    public static boolean applyConfig(File file, String packageName) {
        if (FileUtils.isJson(file)) {
            return XuiMgrHelper.get().setAppConfig(packageName, FileUtils.loadFile(file));
        }
        if (file != null) {
            Logger.t(TAG).w("applyConfig: error not json file, path= + " + file.getAbsolutePath(), new Object[0]);
        } else {
            Logger.t(TAG).w("applyConfig: error not json file is null = ", new Object[0]);
        }
        return false;
    }
}
