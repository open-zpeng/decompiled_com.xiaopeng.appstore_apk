package com.xiaopeng.appstore.applist_biz.logic;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.LauncherActivityInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteFullException;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.UserHandle;
import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;
import com.xiaopeng.app.xpAppInfo;
import com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao;
import com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDatabase;
import com.xiaopeng.appstore.applist_biz.datamodel.entities.LocalData;
import com.xiaopeng.appstore.applist_biz.logic.AppDownloadObserver;
import com.xiaopeng.appstore.applist_biz.logic.comparator.AppIndexComparator;
import com.xiaopeng.appstore.applist_biz.logic.comparator.XpAppComparator;
import com.xiaopeng.appstore.applist_biz.model.BaseAppItem;
import com.xiaopeng.appstore.applist_biz.model.FixedAppItem;
import com.xiaopeng.appstore.applist_biz.model.LoadingAppItem;
import com.xiaopeng.appstore.applist_biz.model.LocalAppData;
import com.xiaopeng.appstore.applist_biz.model.NapaAppItem;
import com.xiaopeng.appstore.applist_biz.model.NormalAppItem;
import com.xiaopeng.appstore.applist_biz.parser.AppListParser;
import com.xiaopeng.appstore.bizcommon.common.BizLifecycle;
import com.xiaopeng.appstore.bizcommon.entities.AppInfo;
import com.xiaopeng.appstore.bizcommon.entities.ComponentKey;
import com.xiaopeng.appstore.bizcommon.entities.WebAppInfo;
import com.xiaopeng.appstore.bizcommon.logic.PreloadLoadListener;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.logic.app.LauncherIcons;
import com.xiaopeng.appstore.bizcommon.router.RouterConstants;
import com.xiaopeng.appstore.bizcommon.router.RouterManager;
import com.xiaopeng.appstore.bizcommon.router.provider.AppStoreProvider;
import com.xiaopeng.appstore.bizcommon.utils.DatabaseUtils;
import com.xiaopeng.appstore.bizcommon.utils.FeatureOptions;
import com.xiaopeng.appstore.libcommon.utils.FileUtils;
import com.xiaopeng.appstore.libcommon.utils.GsonUtil;
import com.xiaopeng.appstore.libcommon.utils.OSUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.appstore.storeprovider.ResourceProviderContract;
import com.xiaopeng.appstore.xpcommon.NapaUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;
/* loaded from: classes2.dex */
public class AppListCache implements AppComponentManager.OnAppsChangedCallback, BizLifecycle {
    private static final Uri ASSEMBLE_LOCAL_STATE_URI = ContentUris.withAppendedId(ResourceProviderContract.LOCAL_STATE_URI, 1000);
    private static final String FACTORY_TEST = "com.xiaopeng.factory";
    private static final int RETRY_LOAD_ICON_TIME = 60000;
    private static final String TAG = "AppListCache";
    private boolean hasNapaList;
    private final AppDownloadObserver.OnChangeListener mAppDownloadChangeListener;
    private final List<BaseAppItem> mAppList;
    private AppStoreProvider mAppStoreProvider;
    private int mCurrentScreen;
    private int mDataUiMode;
    private Executor mExecutor;
    private HandlerThread mHandlerThread;
    private long mLastRetryLoadIconTime;
    private final Set<AppListListener> mListeners;
    private volatile boolean mLoadingPreload;
    private LocalAppDao mLocalAppDao;
    private boolean mLocalAppListDirty;
    private final Set<String> mNewInstalledList;
    private ContentObserver mNewInstalledObserver;
    private final Set<String> mPackageNameList;
    private final List<BaseAppItem> mPendingLoadingApps;
    private final Map<String, BaseAppItem> mPreloadAppMap;
    private PreloadLoadListener mPreloadLoadListener;
    private List<String> mXPAppConfigList;

    /* loaded from: classes2.dex */
    public interface AppListListener {
        void onAllIconsChanged();

        default void onAppAdded(int index, BaseAppItem appItem) {
        }

        default void onAppChanged(int index, BaseAppItem appItem) {
        }

        void onAppItemChanged(BaseAppItem appItem);

        void onAppListChanged(List<BaseAppItem> appList);

        default void onAppRemoved(int index, BaseAppItem appItem) {
        }
    }

    private boolean filterPersist(xpAppInfo appItem) {
        return true;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackageUpdated(String packageName) {
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackagesAvailable(String[] packageNames, UserHandle user, boolean replacing) {
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackagesUnavailable(String[] packageNames, UserHandle user, boolean replacing) {
    }

    private AppListCache() {
        this.mAppList = new LinkedList();
        this.mPackageNameList = new HashSet();
        this.mPendingLoadingApps = new LinkedList();
        this.mPreloadAppMap = new LinkedHashMap(0);
        this.mNewInstalledList = new HashSet();
        this.mLocalAppListDirty = true;
        this.mLoadingPreload = false;
        this.mCurrentScreen = -1;
        this.mAppDownloadChangeListener = new AppDownloadObserver.OnChangeListener() { // from class: com.xiaopeng.appstore.applist_biz.logic.AppListCache.1
            @Override // com.xiaopeng.appstore.applist_biz.logic.AppDownloadObserver.OnChangeListener
            public void onDownloadTrackAdd(AppDownloadObserver.AppHolder appHolder) {
                Logger.t(AppListCache.TAG).i("onDownloadTrackAdd:" + appHolder, new Object[0]);
                AppListCache.this.tryAddLoadingApp(appHolder.getKey(), appHolder.getIconUrl(), appHolder.getName());
            }

            @Override // com.xiaopeng.appstore.applist_biz.logic.AppDownloadObserver.OnChangeListener
            public void onDownloadTrackRemove(AppDownloadObserver.AppHolder appHolder) {
                Logger.t(AppListCache.TAG).i("onDownloadTrackRemove:" + appHolder, new Object[0]);
                if (appHolder.getState() != 1100) {
                    AppListCache.this.removeLoadingAppAsync(appHolder.getKey());
                } else {
                    Logger.t(AppListCache.TAG).i("onDownloadTrackRemove, complete state, do not remove loading app," + appHolder, new Object[0]);
                }
            }
        };
        this.mLastRetryLoadIconTime = 0L;
        this.mPreloadLoadListener = new PreloadLoadListener() { // from class: com.xiaopeng.appstore.applist_biz.logic.AppListCache.2
            @Override // com.xiaopeng.appstore.bizcommon.logic.PreloadLoadListener
            public void onFinish(List<WebAppInfo> appList, boolean isFullUpdate) {
                AppListCache.this.onPreloadRequestFinish(appList, isFullUpdate);
            }
        };
        this.mListeners = new CopyOnWriteArraySet();
    }

    public static AppListCache get() {
        return SingletonHolder.sInstance;
    }

    public List<BaseAppItem> getAppList() {
        return this.mAppList;
    }

    public void init(List<String> xpAppConfigList) {
        this.mXPAppConfigList = new LinkedList(xpAppConfigList);
        AppComponentManager.get().addOnAppsChangedCallback(this);
        AppDownloadObserver.get().init(Utils.getApp());
        AppDownloadObserver.get().registerListener(this.mAppDownloadChangeListener);
        NapaUtils.initCOnfig();
    }

    public void clear() {
        AppComponentManager.get().removeOnAppsChangedCallback(this);
        AppDownloadObserver.get().release();
    }

    @Override // com.xiaopeng.appstore.bizcommon.common.BizLifecycle
    public void create() {
        this.mCurrentScreen = XuiMgrHelper.get().getShareId();
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
        HandlerThread handlerThread2 = new HandlerThread("AppListCache_NewInstalled");
        this.mHandlerThread = handlerThread2;
        handlerThread2.start();
        this.mNewInstalledObserver = new ContentObserver(new Handler(this.mHandlerThread.getLooper())) { // from class: com.xiaopeng.appstore.applist_biz.logic.AppListCache.3
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                AppListCache.this.loadNewInstalledAsync();
            }
        };
        Utils.getApp().getContentResolver().registerContentObserver(ASSEMBLE_LOCAL_STATE_URI, true, this.mNewInstalledObserver);
        loadNewInstalledAsync();
    }

    @Override // com.xiaopeng.appstore.bizcommon.common.BizLifecycle
    public void stop() {
        Logger.t(TAG).d("onStop");
        if (this.mCurrentScreen == 0) {
            persistOrderAsync(this.mAppList);
        }
        refreshUiMode();
    }

    public void reloadNapaAppList() {
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$wVayvQB5Dw6nRaF0qQfc4iBK3gk
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.lambda$reloadNapaAppList$0$AppListCache();
            }
        });
    }

    public /* synthetic */ void lambda$reloadNapaAppList$0$AppListCache() {
        getNapaAppList(false);
        sortAppListWithDb();
    }

    private boolean checkRetryIconTime() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLastRetryLoadIconTime > OkGo.DEFAULT_MILLISECONDS) {
            this.mLastRetryLoadIconTime = currentTimeMillis;
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.appstore.bizcommon.common.BizLifecycle
    public void destroy() {
        Utils.getApp().getContentResolver().unregisterContentObserver(this.mNewInstalledObserver);
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
    }

    private Executor getExecutor() {
        if (this.mExecutor == null) {
            this.mExecutor = Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$0FC9hXmP51b5LJTsxWb05uglbEQ
                @Override // java.util.concurrent.ThreadFactory
                public final Thread newThread(Runnable runnable) {
                    return AppListCache.lambda$getExecutor$1(runnable);
                }
            });
        }
        return this.mExecutor;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Thread lambda$getExecutor$1(Runnable runnable) {
        Thread newThread = Executors.defaultThreadFactory().newThread(runnable);
        newThread.setName("AppListCacheTh");
        return newThread;
    }

    private LocalAppDao getLocalAppDao() {
        if (this.mLocalAppDao == null) {
            this.mLocalAppDao = LocalAppDatabase.getInstance(Utils.getApp()).getLocalAppDao();
        }
        return this.mLocalAppDao;
    }

    public boolean refreshUiMode() {
        int i = Utils.getApp().getResources().getConfiguration().uiMode;
        Logger.t(TAG).i("refreshUiMode, uiMode(%s -> %s).", Integer.valueOf(this.mDataUiMode), Integer.valueOf(i));
        if (i != this.mDataUiMode) {
            this.mDataUiMode = i;
            return true;
        }
        return false;
    }

    private void runOnBg(Runnable runnable) {
        getExecutor().execute(runnable);
    }

    public void registerAppListListener(AppListListener listListener) {
        Logger.t(TAG).i("registerAppListListener, listener=" + listListener, new Object[0]);
        if (listListener != null) {
            this.mListeners.add(listListener);
        }
    }

    public void unregisterAppListListener(AppListListener listListener) {
        Logger.t(TAG).i("unregisterAppListListener, listener=" + listListener, new Object[0]);
        if (listListener != null) {
            this.mListeners.remove(listListener);
        }
    }

    private void dispatchAppListChanged(List<BaseAppItem> appList) {
        for (AppListListener appListListener : this.mListeners) {
            appListListener.onAppListChanged(appList);
        }
    }

    private void dispatchAppItemChanged(BaseAppItem appItem) {
        for (AppListListener appListListener : this.mListeners) {
            appListListener.onAppItemChanged(appItem);
        }
    }

    private void dispatchAllIconsChanged() {
        for (AppListListener appListListener : this.mListeners) {
            appListListener.onAllIconsChanged();
        }
    }

    private void dispatchAppAdded(int index, BaseAppItem appItem) {
        for (AppListListener appListListener : this.mListeners) {
            appListListener.onAppAdded(index, appItem);
        }
    }

    private void dispatchAppRemoved(int index, BaseAppItem appItem) {
        for (AppListListener appListListener : this.mListeners) {
            appListListener.onAppRemoved(index, appItem);
        }
    }

    private void dispatchAppChanged(int index, BaseAppItem appItem) {
        for (AppListListener appListListener : this.mListeners) {
            appListListener.onAppChanged(index, appItem);
        }
    }

    public boolean isLocalAppDirty() {
        return this.mLocalAppListDirty;
    }

    public void reload() {
        Logger.t(TAG).i("reload, appList size before load is " + this.mAppList.size(), new Object[0]);
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$8S_2rF9LO7Fn561UBrmQZIuFlWo
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.lambda$reload$2$AppListCache();
            }
        });
    }

    public /* synthetic */ void lambda$reload$2$AppListCache() {
        AppComponentManager.get().loadLocalDataAsync(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$MjlBvbAe8kMSUkpWpPv_EZ3atqY
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.onLocalAppLoadFinish();
            }
        });
    }

    public void tryLoadData() {
        if (this.mLocalAppListDirty) {
            refreshUiMode();
            reload();
        } else if (refreshUiMode()) {
            Logger.t(TAG).i("tryLoadData, loadAllIcons due to uiMode changed.", new Object[0]);
            loadAllIconsAsync();
        }
    }

    public void loadAllIconsAsync() {
        Logger.t(TAG).i("loadAllIconsAsync", new Object[0]);
        if (this.mAppList == null) {
            Logger.t(TAG).w("loadAllIconsAsync, list is null", new Object[0]);
        } else {
            runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$0g41sHrn7W0bgnXh1cRVgnmsorQ
                @Override // java.lang.Runnable
                public final void run() {
                    AppListCache.this.lambda$loadAllIconsAsync$3$AppListCache();
                }
            });
        }
    }

    public /* synthetic */ void lambda$loadAllIconsAsync$3$AppListCache() {
        LauncherIcons obtain = LauncherIcons.obtain(Utils.getApp());
        for (BaseAppItem baseAppItem : this.mAppList) {
            if (baseAppItem instanceof NormalAppItem) {
                NormalAppItem normalAppItem = (NormalAppItem) baseAppItem;
                for (LauncherActivityInfo launcherActivityInfo : AppComponentManager.get().getActivityList(normalAppItem.packageName, normalAppItem.user)) {
                    if (launcherActivityInfo.getComponentName().equals(normalAppItem.componentName)) {
                        normalAppItem.iconBitmap = obtain.createBadgedIconBitmap(AppComponentManager.get().getIcon(launcherActivityInfo), normalAppItem.user, AppComponentManager.get().wrapToAdaptive(normalAppItem.packageName)).icon;
                    }
                }
            }
        }
        if (OSUtils.ATLEAST_Q) {
            List<xpAppInfo> loadNapaList = AppComponentManager.get().loadNapaList();
            for (BaseAppItem baseAppItem2 : this.mAppList) {
                if (baseAppItem2 instanceof NapaAppItem) {
                    NapaAppItem napaAppItem = (NapaAppItem) baseAppItem2;
                    for (xpAppInfo xpappinfo : loadNapaList) {
                        if (xpappinfo.mXpAppId.equals(napaAppItem.mXpAppId) && xpappinfo.mXpAppPage.equals(napaAppItem.mXpAppPage)) {
                            napaAppItem.mXpAppIcon = xpappinfo.mXpAppIcon;
                        }
                    }
                }
            }
        }
        obtain.recycle();
        dispatchAllIconsChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadNewInstalledAsync() {
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$JU3qnd2vrNATKqDsO2BrkwEuYqA
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.loadNewInstalledData();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadNewInstalledData() {
        ContentResolver contentResolver = Utils.getApp().getContentResolver();
        this.mNewInstalledList.clear();
        Cursor query = contentResolver.query(ASSEMBLE_LOCAL_STATE_URI, null, null, null);
        if (query != null) {
            try {
                if (query.getCount() > 0) {
                    while (query.moveToNext()) {
                        if (query.getInt(2) == 1001) {
                            String string = query.getString(1);
                            Logger.t(TAG).i("loadNewInstalledData, " + string, new Object[0]);
                            this.mNewInstalledList.add(string);
                        }
                    }
                }
            } catch (Throwable th) {
                if (query != null) {
                    try {
                        query.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if (query != null) {
            query.close();
        }
        for (BaseAppItem baseAppItem : this.mAppList) {
            if (baseAppItem instanceof NormalAppItem) {
                NormalAppItem normalAppItem = (NormalAppItem) baseAppItem;
                boolean contains = this.mNewInstalledList.contains(normalAppItem.packageName);
                if (normalAppItem.newInstalled != contains) {
                    normalAppItem.newInstalled = contains;
                    Logger.t(TAG).i("loadNewInstalledData, dispatchAppItemChanged:" + baseAppItem, new Object[0]);
                    dispatchAppItemChanged(baseAppItem);
                }
            }
        }
    }

    private void addAppAsync(final List<AppInfo> list) {
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$TqAsQSZvgG0zG11ctFpzjO0jTOI
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.lambda$addAppAsync$4$AppListCache(list);
            }
        });
    }

    public /* synthetic */ void lambda$addAppAsync$4$AppListCache(List list) {
        if (this.mLocalAppListDirty) {
            Logger.t(TAG).i("addApp, ignore since local app is not ready. list=" + list, new Object[0]);
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AppInfo appInfo = (AppInfo) it.next();
            Iterator<BaseAppItem> it2 = this.mAppList.iterator();
            int i = 0;
            int i2 = -1;
            while (it2.hasNext()) {
                BaseAppItem next = it2.next();
                String str = next.packageName;
                if ((TextUtils.isEmpty(str) || str.equals(appInfo.componentName.getPackageName())) && ((next instanceof LoadingAppItem) || (next instanceof FixedAppItem))) {
                    it2.remove();
                    if (i2 == -1) {
                        i2 = i;
                    }
                    this.mPackageNameList.remove(str);
                }
                i++;
            }
            String packageName = appInfo.componentName.getPackageName();
            if (this.mPackageNameList.contains(packageName) || isHideSystemApp(appInfo)) {
                Logger.t(TAG).i("addApp(%s), ignore since exist or isHideSystemApp = %s ", packageName, Boolean.valueOf(isHideSystemApp(appInfo)));
            } else {
                NormalAppItem parseAppInfo = AppListParser.parseAppInfo(appInfo);
                parseAppInfo.newInstalled = this.mNewInstalledList.contains(parseAppInfo.packageName);
                Logger.t(TAG).d("addApp(%s), add to removedIndex=%s.", parseAppInfo, Integer.valueOf(i2));
                if (i2 > -1) {
                    this.mAppList.add(i2, parseAppInfo);
                    dispatchAppAdded(i2, parseAppInfo);
                } else {
                    int size = this.mAppList.size();
                    this.mAppList.add(parseAppInfo);
                    dispatchAppAdded(size - 1, parseAppInfo);
                }
                this.mPackageNameList.add(parseAppInfo.packageName);
            }
        }
        dispatchAppListChanged(this.mAppList);
    }

    private void removeAppAsync(final String packageName, final UserHandle user) {
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$ict7KoXlPT3bsn_oZRkIuFX85PE
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.lambda$removeAppAsync$5$AppListCache(packageName, user);
            }
        });
    }

    public /* synthetic */ void lambda$removeAppAsync$5$AppListCache(String str, UserHandle userHandle) {
        Iterator<BaseAppItem> it = this.mAppList.iterator();
        int i = -1;
        BaseAppItem baseAppItem = null;
        boolean z = false;
        int i2 = 0;
        while (it.hasNext()) {
            BaseAppItem next = it.next();
            if (next instanceof NormalAppItem) {
                String key = next.getKey();
                if (next.packageName.equals(str) && key.contains(userHandle.toString())) {
                    it.remove();
                    this.mPackageNameList.remove(str);
                    i = i2;
                    baseAppItem = next;
                    z = true;
                }
            }
            i2++;
        }
        if (z) {
            if (this.mPreloadAppMap.containsKey(str)) {
                BaseAppItem baseAppItem2 = this.mPreloadAppMap.get(str);
                Logger.t(TAG).i("removeApp, add preload icon, removedIndex=" + i + " preloadItem=" + baseAppItem2, new Object[0]);
                this.mAppList.add(i, baseAppItem2);
                this.mPackageNameList.add(str);
                dispatchAppChanged(i, baseAppItem2);
            } else {
                Logger.t(TAG).d("removeApp(%s), no preload icon, preloadApps=%s.", str, this.mPreloadAppMap.keySet());
                dispatchAppRemoved(i, baseAppItem);
            }
            persistOrder(this.mAppList);
            dispatchAppListChanged(this.mAppList);
        }
    }

    private void changeAppAsync(final String packageName, final UserHandle user) {
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$RBj-psYKOePsC6NXeTDfqDfI4bg
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.lambda$changeAppAsync$6$AppListCache(packageName, user);
            }
        });
    }

    public /* synthetic */ void lambda$changeAppAsync$6$AppListCache(String str, UserHandle userHandle) {
        List<AppInfo> appInfoList = AppComponentManager.get().getAppInfoList(str, userHandle);
        if (appInfoList == null) {
            return;
        }
        for (AppInfo appInfo : appInfoList) {
            int i = 0;
            for (BaseAppItem baseAppItem : this.mAppList) {
                if (baseAppItem instanceof NormalAppItem) {
                    NormalAppItem normalAppItem = (NormalAppItem) baseAppItem;
                    if (appInfo.componentName.getPackageName().equals(normalAppItem.componentName.getPackageName()) && appInfo.user.equals(normalAppItem.user)) {
                        normalAppItem.apply(appInfo);
                        dispatchAppItemChanged(baseAppItem);
                        dispatchAppChanged(i, baseAppItem);
                    }
                }
                i++;
            }
        }
    }

    private void addLoadingAppAsync(final String packageName, final String iconUrl, final CharSequence title) {
        if (TextUtils.isEmpty(packageName)) {
            return;
        }
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$oJqXrGh2m-siotCe6K64TQipzyo
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.lambda$addLoadingAppAsync$7$AppListCache(packageName, iconUrl, title);
            }
        });
    }

    public /* synthetic */ void lambda$addLoadingAppAsync$7$AppListCache(String str, String str2, CharSequence charSequence) {
        Logger.t(TAG).i("addLoadingAppAsync, pn=" + str, new Object[0]);
        if (!this.mPackageNameList.contains(str)) {
            LoadingAppItem parseLoadingApp = AppListParser.parseLoadingApp(str, str2, charSequence);
            this.mAppList.add(parseLoadingApp);
            this.mPackageNameList.add(parseLoadingApp.packageName);
            dispatchAppListChanged(this.mAppList);
            return;
        }
        Logger.t(TAG).w("addLoadingAppAsync, ignore this item, since already have, pn=" + str, new Object[0]);
    }

    public void tryAddLoadingApp(final String packageName, final String iconUrl, final CharSequence title) {
        if (TextUtils.isEmpty(packageName)) {
            return;
        }
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$ux63ILwrWT4Hb-2skDOGJMMhXOQ
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.lambda$tryAddLoadingApp$8$AppListCache(packageName, iconUrl, title);
            }
        });
    }

    public /* synthetic */ void lambda$tryAddLoadingApp$8$AppListCache(String str, String str2, CharSequence charSequence) {
        if (this.mLocalAppListDirty) {
            Logger.t(TAG).i("tryAddLoadingApp, local list is dirty, add to pending list. pn=" + str, new Object[0]);
            this.mPendingLoadingApps.add(AppListParser.parseLoadingApp(str, str2, charSequence));
            return;
        }
        Logger.t(TAG).i("tryAddLoadingApp, local list is ready, add to list. pn=" + str, new Object[0]);
        if (!this.mPackageNameList.contains(str)) {
            LoadingAppItem parseLoadingApp = AppListParser.parseLoadingApp(str, str2, charSequence);
            this.mAppList.add(parseLoadingApp);
            this.mPackageNameList.add(parseLoadingApp.packageName);
            sortAppListWithDb();
            dispatchAppListChanged(this.mAppList);
            return;
        }
        Logger.t(TAG).i("tryAddLoadingApp ignore, exist app " + str, new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeLoadingAppAsync(final String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return;
        }
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$WlctrCmArLwaPbXUjTywFwx_p2s
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.lambda$removeLoadingAppAsync$9$AppListCache(packageName);
            }
        });
    }

    public /* synthetic */ void lambda$removeLoadingAppAsync$9$AppListCache(String str) {
        Iterator<BaseAppItem> it = this.mAppList.iterator();
        boolean z = false;
        while (it.hasNext()) {
            BaseAppItem next = it.next();
            String str2 = next.packageName;
            if (str2.isEmpty() || str2.equals(str)) {
                if (next instanceof LoadingAppItem) {
                    Logger.t(TAG).d("removeLoadingApp pn=" + str);
                    it.remove();
                    this.mPackageNameList.remove(next.packageName);
                    z = true;
                }
            }
        }
        if (!this.mPendingLoadingApps.isEmpty()) {
            Iterator<BaseAppItem> it2 = this.mPendingLoadingApps.iterator();
            while (it2.hasNext()) {
                BaseAppItem next2 = it2.next();
                String str3 = next2.packageName;
                if (str3.isEmpty() || str3.equals(str)) {
                    if (next2 instanceof LoadingAppItem) {
                        Logger.t(TAG).d("removeLoadingApp in pending pn=" + str);
                        it2.remove();
                        z = true;
                    }
                }
            }
        }
        if (z) {
            dispatchAppListChanged(this.mAppList);
        }
    }

    /* renamed from: reloadPreloadList */
    public void lambda$loadPreloadListAsync$10$AppListCache(boolean force) {
        Logger.t(TAG).d("reloadPreloadList: force=" + force);
        if (!FeatureOptions.hasFeature(FeatureOptions.FEATURE_SHOW_PRELOAD_APPS)) {
            Logger.t(TAG).d("reloadPreloadList: showPreloadList=false");
            return;
        }
        if (this.mAppStoreProvider == null) {
            this.mAppStoreProvider = (AppStoreProvider) RouterManager.get().getModule(RouterConstants.AppStoreProviderPaths.APP_STORE_MAIN_PROVIDER);
        }
        if (this.mAppStoreProvider != null) {
            if (!this.mLoadingPreload) {
                Logger.t(TAG).i("reloadPreloadList, request loading start.", new Object[0]);
                this.mLoadingPreload = true;
                this.mAppStoreProvider.requestPreloadList(this.mPreloadLoadListener, force);
                return;
            }
            Logger.t(TAG).i("reloadPreloadList, loading not finish, ignore this request.", new Object[0]);
            return;
        }
        Logger.t(TAG).w("reloadPreloadList warning, appStoreProvider is null.", new Object[0]);
    }

    public void loadPreloadListAsync(final boolean force) {
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$dHAVwadbLcvnwVNlrk9XfBzg0rQ
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.lambda$loadPreloadListAsync$10$AppListCache(force);
            }
        });
    }

    public void refreshOrder(final List<LocalAppData> indexList) {
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$RRgSh3DYSBXgJjAi8iqFzIi3Cbs
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.lambda$refreshOrder$11$AppListCache(indexList);
            }
        });
    }

    public /* synthetic */ void lambda$refreshOrder$11$AppListCache(List list) {
        sortAppListWithIndex(list);
        persistOrder(this.mAppList);
    }

    private void persistOrderAsync(final List<BaseAppItem> appList) {
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$l70qAnVcBij4IJhhevKknRbCGG0
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.lambda$persistOrderAsync$12$AppListCache(appList);
            }
        });
    }

    public /* synthetic */ void lambda$persistOrderAsync$12$AppListCache(List list) {
        Logger.t(TAG).i("persistOrderAsync, appList=" + list, new Object[0]);
        persistOrder(list);
    }

    private void persistOrder(List<BaseAppItem> appList) {
        if (appList == null || appList.isEmpty()) {
            return;
        }
        int i = 0;
        final ArrayList arrayList = new ArrayList(appList.size());
        for (BaseAppItem baseAppItem : appList) {
            LocalData localData = new LocalData();
            localData.listIndex = i;
            localData.key = baseAppItem.getKey();
            localData.packageName = baseAppItem.packageName;
            arrayList.add(localData);
            i++;
        }
        DatabaseUtils.tryExecute(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$fkUar3xng0xSJMDwrW73v2iZEGE
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.lambda$persistOrder$13$AppListCache(arrayList);
            }
        });
    }

    public /* synthetic */ void lambda$persistOrder$13$AppListCache(List list) {
        getLocalAppDao().clearThenAddList(list);
    }

    private void clearThenInsert(List<LocalAppData> list) {
        final ArrayList arrayList = new ArrayList(list.size());
        list.forEach(new Consumer() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$4ujyesNFzNLmtsFeg8CzTNTaXuE
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add(AppListParser.parseToDbData((LocalAppData) obj));
            }
        });
        getLocalAppDao().clearThenAddList(arrayList);
    }

    private void insertIndexDbList(List<LocalAppData> list) {
        final ArrayList arrayList = new ArrayList(list.size());
        list.forEach(new Consumer() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$iKTBxh506ZO6mdDsjaEuiSSeYdc
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add(AppListParser.parseToDbData((LocalAppData) obj));
            }
        });
        getLocalAppDao().addDbList(arrayList);
    }

    private void sortXpApp() {
        Collections.sort(this.mAppList, new XpAppComparator(this.mXPAppConfigList));
    }

    public void sortAppListWithDbAsync() {
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$uyC28CrXCk9ag9q-DOQf3hm0BCw
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.sortAppListWithDb();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sortAppListWithDb() {
        List<LocalData> list;
        Logger.t(TAG).i("sortAppListWithDb, size=" + this.mAppList.size(), new Object[0]);
        try {
            list = getLocalAppDao().getPureLocalData();
        } catch (SQLiteFullException e) {
            Logger.t(TAG).e("sortAppListWithDb failed, ex: " + e, new Object[0]);
            e.printStackTrace();
            list = null;
        }
        final HashMap hashMap = new HashMap();
        final HashMap hashMap2 = new HashMap();
        if (list != null && !list.isEmpty()) {
            list.forEach(new Consumer() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$4CV0K_nK44Uxh35hw0GjoulosjQ
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AppListCache.lambda$sortAppListWithDb$16(hashMap, hashMap2, (LocalData) obj);
                }
            });
        }
        Collections.sort(this.mAppList, new AppIndexComparator(this.mXPAppConfigList, hashMap, hashMap2));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$sortAppListWithDb$16(Map map, Map map2, LocalData localData) {
        map.put(localData.key, Integer.valueOf(localData.listIndex));
        if (map2.containsKey(localData.packageName)) {
            return;
        }
        map2.put(localData.packageName, Integer.valueOf(localData.listIndex));
    }

    private void sortAppListWithIndex(List<LocalAppData> indexList) {
        if (indexList == null || indexList.isEmpty()) {
            sortXpApp();
            return;
        }
        final HashMap hashMap = new HashMap(indexList.size());
        final HashMap hashMap2 = new HashMap();
        indexList.forEach(new Consumer() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$tc7ncKR8L9u1jaCVqXQWLJAgFD4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppListCache.lambda$sortAppListWithIndex$17(hashMap, hashMap2, (LocalAppData) obj);
            }
        });
        Collections.sort(this.mAppList, new AppIndexComparator(this.mXPAppConfigList, hashMap, hashMap2));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$sortAppListWithIndex$17(Map map, Map map2, LocalAppData localAppData) {
        map.put(localAppData.key, Integer.valueOf(localAppData.index));
        if (map2.containsKey(localAppData.packageName)) {
            return;
        }
        map2.put(localAppData.packageName, Integer.valueOf(localAppData.index));
    }

    private List<NormalAppItem> findNormalApp(ComponentName componentName, UserHandle user) {
        if (this.mAppList != null) {
            ComponentKey componentKey = new ComponentKey(componentName, user);
            ArrayList<BaseAppItem> arrayList = new ArrayList(this.mAppList);
            ArrayList arrayList2 = new ArrayList();
            for (BaseAppItem baseAppItem : arrayList) {
                if (baseAppItem instanceof NormalAppItem) {
                    NormalAppItem normalAppItem = (NormalAppItem) baseAppItem;
                    if (normalAppItem.getItemKey().equals(componentKey.toString())) {
                        arrayList2.add(normalAppItem);
                    }
                }
            }
            return arrayList2;
        }
        return null;
    }

    private List<NormalAppItem> findNormalApp(String packageName) {
        if (this.mAppList == null || !this.mPackageNameList.contains(packageName)) {
            return null;
        }
        ArrayList<BaseAppItem> arrayList = new ArrayList(this.mAppList);
        ArrayList arrayList2 = new ArrayList();
        for (BaseAppItem baseAppItem : arrayList) {
            if ((baseAppItem instanceof NormalAppItem) && baseAppItem.packageName.equals(packageName)) {
                arrayList2.add((NormalAppItem) baseAppItem);
            }
        }
        return arrayList2;
    }

    private List<BaseAppItem> findApp(String packageName) {
        if (this.mAppList != null) {
            ArrayList arrayList = new ArrayList();
            for (BaseAppItem baseAppItem : this.mAppList) {
                if (baseAppItem.packageName.equals(packageName)) {
                    arrayList.add(baseAppItem);
                }
            }
            return arrayList;
        }
        return null;
    }

    private void removeApp(BaseAppItem appItem) {
        if (appItem == null) {
            return;
        }
        Iterator<BaseAppItem> it = this.mAppList.iterator();
        while (it.hasNext()) {
            if (it.next().equals(appItem)) {
                Logger.t(TAG).i("removeApp:" + appItem, new Object[0]);
                this.mPackageNameList.remove(appItem.packageName);
                it.remove();
            }
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackageRemoved(String packageName, UserHandle user) {
        removeAppAsync(packageName, user);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackageAdded(List<AppInfo> appInfoList) {
        addAppAsync(appInfoList);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackageChanged(String packageName, UserHandle user) {
        changeAppAsync(packageName, user);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLocalAppLoadFinish() {
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$p0zZtG3XZ6B7PMcQ4SmryAqcATk
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.lambda$onLocalAppLoadFinish$19$AppListCache();
            }
        });
        loadPreloadListAsync(false);
    }

    public /* synthetic */ void lambda$onLocalAppLoadFinish$19$AppListCache() {
        if (!this.mAppList.isEmpty()) {
            this.mAppList.clear();
            this.mPackageNameList.clear();
        }
        Logger.t(TAG).i("onLocalAppLoadFinish, appList size before add is " + this.mAppList.size(), new Object[0]);
        this.mLocalAppListDirty = false;
        this.hasNapaList = getNapaAppList(false);
        Logger.t(TAG).i("onLocalAppLoadFinish , hasNapaList = " + this.hasNapaList, new Object[0]);
        boolean normalAppList = getNormalAppList();
        Logger.t(TAG).i("onLocalAppLoadFinish , hasNormalList = " + normalAppList, new Object[0]);
        if (!this.hasNapaList && !normalAppList) {
            Logger.t(TAG).w("onLocalAppLoadFinish warning, local list is empty.", new Object[0]);
            return;
        }
        Logger.t(TAG).i("onLocalAppLoadFinish, local app list size=" + this.mAppList.size(), new Object[0]);
        List<String> list = this.mXPAppConfigList;
        if (list != null) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (!this.mPackageNameList.contains(next)) {
                    Logger.t(TAG).i("Remove xp config app:" + next, new Object[0]);
                    it.remove();
                }
            }
        }
        if (!this.mPendingLoadingApps.isEmpty()) {
            this.mPendingLoadingApps.forEach(new Consumer() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$F-Ol_vdP5TcR6p-qqR1HBIGKJc0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AppListCache.this.lambda$onLocalAppLoadFinish$18$AppListCache((BaseAppItem) obj);
                }
            });
            this.mPendingLoadingApps.clear();
        } else {
            Logger.t(TAG).i("onLocalAppLoadFinish, no loadingApps to add.", new Object[0]);
        }
        if (!this.mPreloadAppMap.isEmpty()) {
            for (Map.Entry<String, BaseAppItem> entry : this.mPreloadAppMap.entrySet()) {
                if (this.mPackageNameList.contains(entry.getKey())) {
                    Logger.t(TAG).i("onLocalAppLoadFinish, add preloadApp, ignore since exist, pn=" + entry.getKey(), new Object[0]);
                } else {
                    Logger.t(TAG).i("onLocalAppLoadFinish, add preloadApp, pn=" + entry.getKey(), new Object[0]);
                    this.mAppList.add(entry.getValue());
                    this.mPackageNameList.add(entry.getKey());
                }
            }
        } else {
            Logger.t(TAG).i("onLocalAppLoadFinish, no preloadApps to add.", new Object[0]);
        }
        sortAppListWithDb();
        dispatchAppListChanged(this.mAppList);
    }

    public /* synthetic */ void lambda$onLocalAppLoadFinish$18$AppListCache(BaseAppItem baseAppItem) {
        if (this.mPackageNameList.contains(baseAppItem.packageName)) {
            Logger.t(TAG).i("onLocalAppLoadFinish, add loadingApp, ignore since exist, pn=" + baseAppItem.packageName, new Object[0]);
            return;
        }
        Logger.t(TAG).i("onLocalAppLoadFinish, add loadingApp, pn=" + baseAppItem.packageName, new Object[0]);
        this.mAppList.add(baseAppItem);
        this.mPackageNameList.add(baseAppItem.packageName);
    }

    private boolean getNormalAppList() {
        List<AppInfo> appInfoList = AppComponentManager.get().getAppInfoList();
        if (appInfoList == null || appInfoList.isEmpty()) {
            Logger.t(TAG).w("getNormalAppList warning, local list is empty.", new Object[0]);
            return false;
        }
        Logger.t(TAG).i("getNormalAppList, local app list before parse, size=" + appInfoList.size(), new Object[0]);
        appInfoList.forEach(new Consumer() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$Zo_lQS-ajPn-SGnPgRdOY0xgY3Q
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppListCache.this.lambda$getNormalAppList$20$AppListCache((AppInfo) obj);
            }
        });
        return true;
    }

    public /* synthetic */ void lambda$getNormalAppList$20$AppListCache(AppInfo appInfo) {
        boolean z;
        List<NormalAppItem> findNormalApp = findNormalApp(appInfo.componentName.getPackageName());
        if (findNormalApp == null || findNormalApp.isEmpty()) {
            z = false;
        } else {
            Logger.t(TAG).i("getNormalAppList addItem ignore, since exists. list=" + findNormalApp + ", current:" + appInfo, new Object[0]);
            z = true;
        }
        if (z || isHideSystemApp(appInfo)) {
            return;
        }
        NormalAppItem parseAppInfo = AppListParser.parseAppInfo(appInfo);
        Logger.t(TAG).i("getNormalAppList addItem:" + parseAppInfo, new Object[0]);
        boolean contains = this.mNewInstalledList.contains(parseAppInfo.packageName);
        if (parseAppInfo.newInstalled != contains) {
            parseAppInfo.newInstalled = contains;
            Logger.t(TAG).d("getNormalAppList, refresh new installed indicator:" + parseAppInfo);
        }
        this.mAppList.add(parseAppInfo);
        this.mPackageNameList.add(parseAppInfo.packageName);
    }

    private boolean isHideSystemApp(AppInfo info) {
        boolean z = this.hasNapaList;
        return !z ? z : AppComponentManager.get().isSystemApp(info.intent) && !FACTORY_TEST.equals(info.componentName.getPackageName());
    }

    public boolean getNapaAppList(boolean isDispatch) {
        if (OSUtils.ATLEAST_Q) {
            List<xpAppInfo> loadNapaList = AppComponentManager.get().loadNapaList();
            Iterator<BaseAppItem> it = this.mAppList.iterator();
            while (it.hasNext()) {
                BaseAppItem next = it.next();
                if (next instanceof NapaAppItem) {
                    it.remove();
                    this.mPackageNameList.remove(((NapaAppItem) next).mXpAppId);
                }
            }
            if (loadNapaList.isEmpty()) {
                Logger.t(TAG).i("getNapaAppList: native load napa app list is empty", new Object[0]);
                Logger.t(TAG).i("napaInfoList is empty and debug is false", new Object[0]);
                loadNapaList = (List) GsonUtil.fromJson(FileUtils.loadFileFromAsset("xp_app_list.json"), new TypeToken<List<xpAppInfo>>() { // from class: com.xiaopeng.appstore.applist_biz.logic.AppListCache.4
                }.getType());
            }
            Logger.t(TAG).i("getNapaAppList : list size = " + loadNapaList.size(), new Object[0]);
            loadNapaList.forEach(new Consumer() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$ZPTsrmolYmtghvBPaV3JmYb9ceY
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AppListCache.this.lambda$getNapaAppList$21$AppListCache((xpAppInfo) obj);
                }
            });
            if (isDispatch) {
                dispatchAppListChanged(this.mAppList);
                return true;
            }
            return true;
        }
        return false;
    }

    public /* synthetic */ void lambda$getNapaAppList$21$AppListCache(xpAppInfo xpappinfo) {
        if (filterPersist(xpappinfo)) {
            this.mAppList.add(new NapaAppItem(xpappinfo));
            this.mPackageNameList.add(xpappinfo.mXpAppId);
            Logger.t(TAG).d("getNapaAppList,filterPersist success:" + xpappinfo);
            return;
        }
        Logger.t(TAG).d("getNapaAppList, filterPersist failed:" + xpappinfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPreloadRequestFinish(final List<WebAppInfo> appList, final boolean isFullUpdate) {
        if (appList == null) {
            Logger.t(TAG).i("onPreloadRequestFinish, applist is null.", new Object[0]);
            this.mLoadingPreload = false;
            return;
        }
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$QvEj9cZ8yo6NKwuDBXnRHMLWBZw
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.lambda$onPreloadRequestFinish$22$AppListCache(appList, isFullUpdate);
            }
        });
    }

    public /* synthetic */ void lambda$onPreloadRequestFinish$22$AppListCache(List list, boolean z) {
        LinkedList linkedList = new LinkedList(list);
        handlerPreLoadCache(z);
        boolean handlerDataParser = handlerDataParser(linkedList, z);
        if (z) {
            Logger.t(TAG).i("onPreloadRequestFinish: preloadApps=" + this.mPreloadAppMap.keySet(), new Object[0]);
            Logger.t(TAG).d("onPreloadRequestFinish appList before sort:" + this.mAppList);
            sortAppListWithDb();
            Logger.t(TAG).d("onPreloadRequestFinish appList after sort:" + this.mAppList);
        }
        if (handlerDataParser) {
            dispatchAppListChanged(this.mAppList);
        }
        this.mLoadingPreload = false;
    }

    private boolean handlerDataParser(List<WebAppInfo> threadSafeList, boolean isFullUpdate) {
        LinkedList linkedList = new LinkedList();
        boolean z = false;
        for (WebAppInfo webAppInfo : threadSafeList) {
            FixedAppItem parseWebApp = AppListParser.parseWebApp(webAppInfo);
            String str = parseWebApp.packageName;
            z = handlerPackageNameList(z, linkedList, webAppInfo, parseWebApp, str);
            this.mPreloadAppMap.put(str, parseWebApp);
        }
        if (isFullUpdate) {
            if (!linkedList.isEmpty()) {
                this.mAppList.addAll(linkedList);
            }
        } else {
            for (int i = 0; i < this.mAppList.size(); i++) {
                BaseAppItem baseAppItem = this.mAppList.get(i);
                if (baseAppItem.iconBitmap == null) {
                    String str2 = baseAppItem.packageName;
                    Logger.t(TAG).i("retryLoadIcon packageName : " + str2 + " , iconBitmap is null", new Object[0]);
                    BaseAppItem baseAppItem2 = this.mPreloadAppMap.get(str2);
                    if (baseAppItem2 != null) {
                        this.mAppList.set(i, baseAppItem2);
                        z = true;
                    } else {
                        Logger.t(TAG).i("retryLoadIcon info is null ", new Object[0]);
                    }
                }
            }
            Logger.t(TAG).d("retryLoadIcon appList  :" + this.mAppList);
        }
        return z;
    }

    private boolean handlerPackageNameList(boolean appListChanged, List<BaseAppItem> preloadApps, WebAppInfo info, BaseAppItem appItem, String pn) {
        if (!this.mLocalAppListDirty) {
            if (!this.mPackageNameList.contains(info.getPackageName())) {
                preloadApps.add(appItem);
                this.mPackageNameList.add(pn);
                Logger.t(TAG).i("onPreloadRequestFinish: add preload app:" + pn, new Object[0]);
                return true;
            }
            Logger.t(TAG).i("onPreloadRequestFinish: preloaded app is already in, ignore, pn=" + pn, new Object[0]);
            return appListChanged;
        }
        Logger.t(TAG).i("onPreloadRequestFinish: local app is not ready, ignore, pn=" + pn, new Object[0]);
        return appListChanged;
    }

    private void handlerPreLoadCache(boolean isFullUpdate) {
        Logger.t(TAG).d("handlerPreLoadCache isFullUpdate:" + isFullUpdate);
        if (isFullUpdate) {
            for (Map.Entry<String, BaseAppItem> entry : this.mPreloadAppMap.entrySet()) {
                BaseAppItem value = entry.getValue();
                if (value != null && !AppComponentManager.get().isInstalled(value.packageName)) {
                    removeApp(value);
                }
            }
            this.mPreloadAppMap.clear();
        }
    }

    private void retryLoadIcon() {
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListCache$Uw37QYqfkSYisorqwWlXeKJcDnM
            @Override // java.lang.Runnable
            public final void run() {
                AppListCache.this.lambda$retryLoadIcon$23$AppListCache();
            }
        });
    }

    public /* synthetic */ void lambda$retryLoadIcon$23$AppListCache() {
        boolean z;
        Iterator<Map.Entry<String, BaseAppItem>> it = this.mPreloadAppMap.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            } else if (it.next().getValue().iconBitmap == null) {
                z = true;
                break;
            }
        }
        Logger.t(TAG).w("retryLoadIcon isNeedRetry is " + z, new Object[0]);
        if (z) {
            if (this.mAppStoreProvider == null) {
                this.mAppStoreProvider = (AppStoreProvider) RouterManager.get().getModule(RouterConstants.AppStoreProviderPaths.APP_STORE_MAIN_PROVIDER);
            }
            AppStoreProvider appStoreProvider = this.mAppStoreProvider;
            if (appStoreProvider != null) {
                appStoreProvider.requestPreloadList(this.mPreloadLoadListener, false, false);
            } else {
                Logger.t(TAG).w("retryLoadIcon warning, appStoreProvider is null.", new Object[0]);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingletonHolder {
        static AppListCache sInstance = new AppListCache();

        private SingletonHolder() {
        }
    }
}
