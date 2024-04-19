package com.xiaopeng.appstore.bizcommon.logic.app;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.SparseArray;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.media.MediaBrowserServiceCompat;
import com.orhanobut.logger.Logger;
import com.xiaopeng.app.xpAppInfo;
import com.xiaopeng.appstore.bizcommon.BizCommon;
import com.xiaopeng.appstore.bizcommon.R;
import com.xiaopeng.appstore.bizcommon.common.BizLifecycle;
import com.xiaopeng.appstore.bizcommon.entities.AppInfo;
import com.xiaopeng.appstore.bizcommon.logic.Constants;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.logic.base.BaseComponentManager;
import com.xiaopeng.appstore.bizcommon.router.RouterConstants;
import com.xiaopeng.appstore.bizcommon.router.RouterManager;
import com.xiaopeng.appstore.bizcommon.router.provider.AppListProvider;
import com.xiaopeng.appstore.bizcommon.utils.PackageUtils;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.OSUtils;
import com.xiaopeng.appstore.libcommon.utils.PackageMonitor;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.xuimanager.xapp.XAppManager;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
/* loaded from: classes2.dex */
public class AppComponentManager extends BaseComponentManager implements BizLifecycle {
    private static final int DEFAULT_APP_CAPACITY = 50;
    private static final int DEFAULT_LOADING_CAPACITY = 1;
    private static final String TAG = "AppMgr";
    private static volatile AppComponentManager sInstance;
    private final Context mAppContext;
    private final Set<AppInfo> mAppInfoList;
    private final MutableLiveData<Set<AppInfo>> mAppListLiveData;
    private final AppListProvider mAppListProvider;
    private final AppStorePackageMonitor mAppStorePackageMonitor;
    private final LauncherApps mLauncherApps;
    private Set<String> mMediaAppPackageNameList;
    private List<xpAppInfo> mNapaInfoList;
    private final Map<String, PackageInfo> mPackageInfoMap;
    private final PackageInstaller mPackageInstaller;
    private final MutableLiveData<Map<String, PackageInfo>> mPackageMapLive;
    private final Map<String, List<AppInfo>> mPackageNameMap;
    private final PackageManager mPm;
    private final UserManager mUserManager;
    private List<String> mXPAppList;
    private final Object mLock = new Object();
    private final SparseArray<String> mInstallingPackageMap = new SparseArray<>(1);
    private final Map<OnAppInstallCallback, AppInstallCallbackDelegate> mInstallCallbackMap = new ConcurrentHashMap();
    private final Map<OnAppInstallCallback, AppInstallCallbackDelegate> mInstallCallbackSoftMap = new ConcurrentHashMap();
    private final Set<OnAppsChangedCallback> mPackageCallbackList = new CopyOnWriteArraySet();
    private final Set<OnAppsChangedCallback> mPackageSoftCallbackList = new CopyOnWriteArraySet();
    private final InstallerCallback mInstallerCallback = new InstallerCallback();

    /* loaded from: classes2.dex */
    public interface OnAppInstallCallback {
        void onInstallCreate(int sessionId, String pkgName);

        void onInstallFinished(int sessionId, String pkgName, boolean success);

        void onInstallProgressChanged(int sessionId, String pkgName, float progress);
    }

    /* loaded from: classes2.dex */
    public interface OnAppOpenCallback {
        void onAppOpen(String packageName);

        void onAppOpenFinish(String packageName);
    }

    /* loaded from: classes2.dex */
    public interface OnApplyConfigCallback {
        void applyConfigError(String pkgName);
    }

    /* loaded from: classes2.dex */
    public interface OnAppsChangedCallback {
        void onPackageAdded(List<AppInfo> appInfoList);

        void onPackageChanged(String packageName, UserHandle user);

        void onPackageRemoved(String packageName, UserHandle user);

        void onPackageUpdated(String packageName);

        void onPackagesAvailable(String[] packageNames, UserHandle user, boolean replacing);

        void onPackagesUnavailable(String[] packageNames, UserHandle user, boolean replacing);
    }

    /* loaded from: classes2.dex */
    public static abstract class SimpleOnAppsChangedCallback implements OnAppsChangedCallback {
        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageUpdated(String packageName) {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackagesAvailable(String[] packageNames, UserHandle user, boolean replacing) {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackagesUnavailable(String[] packageNames, UserHandle user, boolean replacing) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean appFilter(String packageName) {
        return false;
    }

    private AppComponentManager() {
        Context app = BizCommon.getApp();
        this.mAppContext = app;
        PackageManager packageManager = app.getPackageManager();
        this.mPm = packageManager;
        this.mPackageInstaller = packageManager.getPackageInstaller();
        ArraySet arraySet = new ArraySet(50);
        this.mAppInfoList = arraySet;
        this.mNapaInfoList = new ArrayList(50);
        this.mAppListLiveData = new MutableLiveData<>(arraySet);
        HashMap hashMap = new HashMap();
        this.mPackageInfoMap = hashMap;
        this.mPackageMapLive = new MutableLiveData<>(hashMap);
        this.mPackageNameMap = new HashMap();
        this.mLauncherApps = (LauncherApps) app.getSystemService("launcherapps");
        this.mUserManager = (UserManager) app.getSystemService("user");
        this.mAppListProvider = (AppListProvider) RouterManager.get().getModule(RouterConstants.AppListProviderPaths.APP_LIST_MAIN_PROVIDER);
        this.mAppStorePackageMonitor = new AppStorePackageMonitor(this);
        this.mMediaAppPackageNameList = new ArraySet();
    }

    public static AppComponentManager get() {
        if (sInstance == null) {
            synchronized (AppComponentManager.class) {
                if (sInstance == null) {
                    sInstance = new AppComponentManager();
                }
            }
        }
        return sInstance;
    }

    public static boolean isSystemApp(PackageInfo info) {
        return (info == null || info.applicationInfo == null || (info.applicationInfo.flags & 1) == 0) ? false : true;
    }

    public static final Bundle getActivityLaunchOptionsAsBundle(Context context) {
        ActivityOptions activityLaunchOptions = getActivityLaunchOptions(context);
        if (activityLaunchOptions == null) {
            return null;
        }
        return activityLaunchOptions.toBundle();
    }

    public static ActivityOptions getActivityLaunchOptions(Context context) {
        return ActivityOptions.makeCustomAnimation(context, R.anim.task_open_enter, R.anim.no_anim);
    }

    public List<AppInfo> getAppInfoList() {
        return new ArrayList(this.mAppInfoList);
    }

    public LiveData<Set<AppInfo>> getAppListLiveData() {
        return this.mAppListLiveData;
    }

    public List<xpAppInfo> getNapaInfoList() {
        List<xpAppInfo> list = this.mNapaInfoList;
        return list == null ? new ArrayList() : list;
    }

    public MutableLiveData<Map<String, PackageInfo>> getPackageMapLive() {
        return this.mPackageMapLive;
    }

    public void addOnAppsChangedCallback(OnAppsChangedCallback callback) {
        this.mPackageCallbackList.add(callback);
    }

    public void removeOnAppsChangedCallback(OnAppsChangedCallback callback) {
        this.mPackageCallbackList.remove(callback);
    }

    public void addOnAppInstallCallback(OnAppInstallCallback callback) {
        addOnAppInstallCallback(callback, new Handler());
    }

    public void addOnAppInstallCallback(OnAppInstallCallback callback, Handler handler) {
        AppInstallCallbackDelegate appInstallCallbackDelegate = new AppInstallCallbackDelegate(callback, this);
        this.mInstallCallbackMap.put(callback, appInstallCallbackDelegate);
        this.mPackageInstaller.registerSessionCallback(appInstallCallbackDelegate, handler);
    }

    public void removeOnAppInstallCallback(OnAppInstallCallback callback) {
        AppInstallCallbackDelegate remove = this.mInstallCallbackMap.remove(callback);
        if (remove != null) {
            this.mPackageInstaller.unregisterSessionCallback(remove);
        }
    }

    public void addOnAppInstallSoftCallback(OnAppInstallCallback callback) {
        addOnAppInstallSoftCallback(callback, new Handler());
    }

    public void addOnAppInstallSoftCallback(OnAppInstallCallback callback, Handler handler) {
        AppInstallCallbackDelegate appInstallCallbackDelegate = new AppInstallCallbackDelegate(callback, this);
        this.mInstallCallbackSoftMap.put(callback, appInstallCallbackDelegate);
        this.mPackageInstaller.registerSessionCallback(appInstallCallbackDelegate, handler);
    }

    public void removeOnAppInstallSoftCallback(OnAppInstallCallback callback) {
        AppInstallCallbackDelegate remove = this.mInstallCallbackSoftMap.remove(callback);
        if (remove != null) {
            this.mPackageInstaller.unregisterSessionCallback(remove);
        }
    }

    public void clearInstallCallbacks() {
        for (Map.Entry<OnAppInstallCallback, AppInstallCallbackDelegate> entry : this.mInstallCallbackMap.entrySet()) {
            this.mPackageInstaller.unregisterSessionCallback(entry.getValue());
        }
        this.mInstallCallbackMap.clear();
    }

    public void clearInstallSoftCallbacks() {
        for (Map.Entry<OnAppInstallCallback, AppInstallCallbackDelegate> entry : this.mInstallCallbackSoftMap.entrySet()) {
            this.mPackageInstaller.unregisterSessionCallback(entry.getValue());
        }
        this.mInstallCallbackSoftMap.clear();
    }

    public void addOnAppsChangedSoftCallback(OnAppsChangedCallback callback) {
        this.mPackageSoftCallbackList.add(callback);
    }

    public void removeOnAppsChangedSoftCallback(OnAppsChangedCallback callback) {
        this.mPackageSoftCallbackList.remove(callback);
    }

    public void init(List<String> xpAppList) {
        this.mXPAppList = xpAppList;
        this.mPackageInstaller.registerSessionCallback(this.mInstallerCallback);
        Logger.t(TAG).d("init this=" + hashCode());
        this.mAppStorePackageMonitor.register(Utils.getApp(), null);
    }

    public void clear() {
        this.mPackageCallbackList.clear();
        this.mPackageSoftCallbackList.clear();
        this.mPackageInstaller.unregisterSessionCallback(this.mInstallerCallback);
        this.mAppInfoList.clear();
        this.mNapaInfoList.clear();
        this.mPackageNameMap.clear();
        LauncherIcons.obtain(Utils.getApp()).release();
        clearInstallCallbacks();
        clearInstallSoftCallbacks();
        this.mAppStorePackageMonitor.unregister();
    }

    public void loadLocalDataAsync(final Runnable onLocalComplete) {
        AppExecutors.get().diskIO().execute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.app.-$$Lambda$AppComponentManager$Nuj6sxeyaTcAZ_51001VYMtIS1s
            @Override // java.lang.Runnable
            public final void run() {
                AppComponentManager.this.lambda$loadLocalDataAsync$0$AppComponentManager(onLocalComplete);
            }
        });
    }

    public /* synthetic */ void lambda$loadLocalDataAsync$0$AppComponentManager(Runnable runnable) {
        synchronized (this.mLock) {
            this.mAppInfoList.clear();
            this.mNapaInfoList.clear();
            this.mPackageNameMap.clear();
            List<UserHandle> userProfiles = this.mUserManager.getUserProfiles();
            int size = userProfiles.size();
            LauncherIcons obtain = LauncherIcons.obtain(Utils.getApp());
            for (int i = 0; i < size; i++) {
                UserHandle userHandle = userProfiles.get(i);
                List<LauncherActivityInfo> activityList = this.mLauncherApps.getActivityList(null, userHandle);
                Logger.t(TAG).d("local app list user=" + userHandle + " size=" + activityList.size());
                for (LauncherActivityInfo launcherActivityInfo : activityList) {
                    if (!appFilter(launcherActivityInfo.getComponentName().getPackageName())) {
                        AppInfo appInfo = new AppInfo(launcherActivityInfo);
                        Logger.t(TAG).v("create icon packageName=" + launcherActivityInfo.getComponentName().getPackageName() + " class=" + launcherActivityInfo.getComponentName().getClassName(), new Object[0]);
                        appInfo.iconBitmap = obtain.createBadgedIconBitmap(getIcon(launcherActivityInfo), userHandle, wrapToAdaptive(launcherActivityInfo.getComponentName().getPackageName())).icon;
                        this.mAppInfoList.add(appInfo);
                        addAppToMap(appInfo);
                        Logger.t(TAG).i("Add AppInfo, launcher Activity:" + appInfo, new Object[0]);
                    }
                }
            }
            Intent intent = new Intent();
            intent.setAction(MediaBrowserServiceCompat.SERVICE_INTERFACE);
            for (ResolveInfo resolveInfo : this.mPm.queryIntentServices(intent, 64)) {
                String str = resolveInfo.serviceInfo.packageName;
                if (findAppInfo(str) != null) {
                    Logger.t(TAG).i("Add AppInfo, Ignore mediaService:" + resolveInfo.serviceInfo.packageName + "/" + resolveInfo.serviceInfo.name, new Object[0]);
                } else if (!mediaAppFilter(str)) {
                    this.mMediaAppPackageNameList.add(str);
                    AppInfo appInfo2 = new AppInfo(resolveInfo.serviceInfo, this.mPm, false);
                    Drawable loadIcon = resolveInfo.serviceInfo.loadIcon(this.mPm);
                    if (loadIcon != null) {
                        appInfo2.iconBitmap = obtain.createBadgedIconBitmap(loadIcon, appInfo2.user, !this.mXPAppList.contains(str)).icon;
                    }
                    this.mAppInfoList.add(appInfo2);
                    addAppToMap(appInfo2);
                    Logger.t(TAG).i("Add AppInfo, Media service:" + appInfo2, new Object[0]);
                }
            }
            obtain.recycle();
            this.mAppListLiveData.postValue(this.mAppInfoList);
            if (OSUtils.ATLEAST_Q) {
                loadNapaList();
            }
            if (runnable != null) {
                runnable.run();
            }
            loadPackageList();
            Logger.t(TAG).d("load local data complete:" + this.mAppInfoList);
        }
    }

    public List<xpAppInfo> loadNapaList() {
        try {
            List<xpAppInfo> xpAppPackageList = XAppManager.getXpAppPackageList(XuiMgrHelper.get().getShareId());
            if (xpAppPackageList != null) {
                this.mNapaInfoList = xpAppPackageList;
            }
            return this.mNapaInfoList;
        } catch (Exception | NoSuchMethodError e) {
            Logger.t(TAG).d("getNapaList Exception occurred : " + e.getMessage());
            return this.mNapaInfoList;
        }
    }

    private AppInfo findAppInfo(String packageName) {
        for (AppInfo appInfo : this.mAppInfoList) {
            if (appInfo.componentName.getPackageName().equals(packageName)) {
                return appInfo;
            }
        }
        return null;
    }

    public void loadPackageList() {
        List<PackageInfo> queryPackageList = queryPackageList();
        if (queryPackageList != null) {
            for (PackageInfo packageInfo : queryPackageList) {
                this.mPackageInfoMap.put(packageInfo.packageName, packageInfo);
            }
        }
        Logger.t(TAG).i("loadPackageList finish:" + this.mPackageInfoMap, new Object[0]);
        this.mPackageMapLive.postValue(this.mPackageInfoMap);
    }

    public Intent getMediaIntentForPackage(String packageName) {
        List<ServiceInfo> mediaServiceInfoList = getMediaServiceInfoList(packageName);
        if (mediaServiceInfoList == null || mediaServiceInfoList.isEmpty()) {
            return null;
        }
        return AppInfo.makeMediaLaunchIntent(new ComponentName(mediaServiceInfoList.get(0).packageName, mediaServiceInfoList.get(0).name));
    }

    public List<PackageInfo> queryPackageList() {
        return this.mPm.getInstalledPackages(0);
    }

    public PackageInfo queryPackageInfo(String packageName) {
        try {
            return this.mPm.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            Logger.t(TAG).w("queryPackageInfo error %s not found.", packageName);
            return null;
        }
    }

    public List<LauncherActivityInfo> getActivityList(String packageName, UserHandle user) {
        return this.mLauncherApps.getActivityList(packageName, user);
    }

    public List<ActivityInfo> getActivityList(String packageName) {
        PackageInfo queryPackageInfo = queryPackageInfo(packageName);
        if (queryPackageInfo.activities == null) {
            return null;
        }
        return Arrays.asList(queryPackageInfo.activities);
    }

    public List<AppInfo> getAppInfoList(String packageName, UserHandle user) {
        if (TextUtils.isEmpty(packageName) || user == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (AppInfo appInfo : this.mAppInfoList) {
            if (appInfo.componentName.getPackageName().equals(packageName) && appInfo.user.equals(user)) {
                arrayList.add(appInfo);
            }
        }
        return arrayList;
    }

    public LauncherActivityInfo resolveActivity(Intent intent, UserHandle user) {
        return this.mLauncherApps.resolveActivity(intent, user);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.base.BaseComponentManager
    public boolean isInstalled(String packageName) {
        Map<String, PackageInfo> map = this.mPackageInfoMap;
        if (map != null) {
            return map.containsKey(packageName);
        }
        return false;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.base.BaseComponentManager
    public int commitInstall(File file, String packageName, String iconUrl) {
        if (file != null) {
            Logger.t(TAG).d("start install packageName=" + packageName + " file=" + file.getPath());
            int install = PackageUtils.install(this.mAppContext, file, packageName, null);
            Logger.t(TAG).d("install complete sessionId=" + install);
            return install;
        }
        return -1;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.base.BaseComponentManager
    public void install(Uri fileUri) {
        PackageUtils.installXp(this.mAppContext, fileUri);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.base.BaseComponentManager
    public boolean isInstalling(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        int size = this.mInstallingPackageMap.size();
        for (int i = 0; i < size; i++) {
            if (packageName.equals(this.mInstallingPackageMap.valueAt(i))) {
                return true;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.base.BaseComponentManager
    public void uninstall(String packageName) {
        Logger.t(TAG).d("uninstall");
        PackageUtils.uninstallXp(this.mAppContext, packageName);
    }

    public void commitUninstall(String packageName) {
        Logger.t(TAG).d("commitUninstall start, pn=%s.", packageName);
        PackageUtils.uninstall(this.mAppContext, packageName, 1000);
        Logger.t(TAG).d("commitUninstall finish, pn=%s.", packageName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAppToMap(AppInfo appInfo) {
        String packageName = appInfo.componentName.getPackageName();
        List<AppInfo> list = this.mPackageNameMap.get(packageName);
        if (list == null) {
            list = new ArrayList<>();
            this.mPackageNameMap.put(packageName, list);
        }
        list.add(appInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAppToMap(String pn, List<AppInfo> infoList) {
        List<AppInfo> list = this.mPackageNameMap.get(pn);
        if (list == null) {
            list = new ArrayList<>();
            this.mPackageNameMap.put(pn, list);
        }
        list.addAll(infoList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeAppInfoFromMap(AppInfo appInfo) {
        String packageName = appInfo.componentName.getPackageName();
        List<AppInfo> list = this.mPackageNameMap.get(packageName);
        if (list == null) {
            return;
        }
        list.remove(appInfo);
        if (list.isEmpty()) {
            this.mPackageNameMap.remove(packageName);
        }
    }

    private boolean mediaAppFilter(String packageName) {
        return Arrays.asList(Constants.XP_INTER_MEDIA_APP_BLACK_LIST).contains(packageName);
    }

    public PackageInfo getPackageInfo(String packageName) {
        try {
            return this.mPm.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.t(TAG).w(packageName + " NameNotFoundException", e);
            return null;
        }
    }

    public boolean isSystemApp(Intent intent) {
        String packageName;
        PackageManager packageManager = this.mPm;
        ComponentName component = intent.getComponent();
        if (component == null) {
            ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 65536);
            packageName = (resolveActivity == null || resolveActivity.activityInfo == null) ? null : resolveActivity.activityInfo.packageName;
        } else {
            packageName = component.getPackageName();
        }
        return isSystemApp(packageName);
    }

    public boolean isSystemApp(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            PackageInfo packageInfo = this.mPm.getPackageInfo(packageName, 0);
            if (packageInfo == null || packageInfo.applicationInfo == null) {
                return false;
            }
            return (packageInfo.applicationInfo.flags & 1) != 0;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    private void parseToInfoList(List<LauncherActivityInfo> infoList, List<AppInfo> outList) {
        LauncherIcons obtain = LauncherIcons.obtain(Utils.getApp());
        for (LauncherActivityInfo launcherActivityInfo : infoList) {
            AppInfo appInfo = new AppInfo(launcherActivityInfo);
            appInfo.iconBitmap = obtain.createBadgedIconBitmap(getIcon(launcherActivityInfo), launcherActivityInfo.getUser(), wrapToAdaptive(launcherActivityInfo.getComponentName().getPackageName())).icon;
            outList.add(appInfo);
        }
        obtain.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseToLaunchAppInfoList(List<ActivityInfo> infoList, List<AppInfo> outList) {
        LauncherIcons obtain = LauncherIcons.obtain(Utils.getApp());
        for (ActivityInfo activityInfo : infoList) {
            AppInfo appInfo = new AppInfo(activityInfo, this.mPm, true);
            Drawable icon = getIcon(activityInfo);
            if (icon != null) {
                appInfo.iconBitmap = obtain.createBadgedIconBitmap(icon, Process.myUserHandle(), wrapToAdaptive(activityInfo.packageName)).icon;
                outList.add(appInfo);
            }
        }
        obtain.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseToMediaAppInfoList(List<ServiceInfo> infoList, List<AppInfo> outList) {
        LauncherIcons obtain = LauncherIcons.obtain(Utils.getApp());
        for (ServiceInfo serviceInfo : infoList) {
            AppInfo appInfo = new AppInfo(serviceInfo, this.mPm, false);
            Drawable icon = getIcon(serviceInfo);
            if (icon != null) {
                appInfo.iconBitmap = obtain.createBadgedIconBitmap(icon, Process.myUserHandle(), wrapToAdaptive(serviceInfo.packageName)).icon;
                outList.add(appInfo);
            }
        }
        obtain.recycle();
    }

    public void addAppInfo(AppInfo info) {
        if (findAppInfo(info.componentName, info.user) != null) {
            return;
        }
        this.mAppInfoList.add(info);
        addAppToMap(info);
    }

    private boolean findActivity(List<LauncherActivityInfo> apps, ComponentName component) {
        for (LauncherActivityInfo launcherActivityInfo : apps) {
            if (launcherActivityInfo.getComponentName().equals(component)) {
                return true;
            }
        }
        return false;
    }

    private boolean findActivity(List<LauncherActivityInfo> apps, String packageName) {
        for (LauncherActivityInfo launcherActivityInfo : apps) {
            if (launcherActivityInfo.getComponentName().getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    private AppInfo findAppInfo(ComponentName componentName, UserHandle user) {
        for (AppInfo appInfo : this.mAppInfoList) {
            if (componentName.getPackageName().equals(appInfo.componentName.getPackageName()) && user.equals(appInfo.user)) {
                return appInfo;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AppInfo findAppInfo(String packageName, UserHandle user) {
        for (AppInfo appInfo : this.mAppInfoList) {
            if (packageName.equals(appInfo.componentName.getPackageName()) && user.equals(appInfo.user)) {
                return appInfo;
            }
        }
        return null;
    }

    private List<AppInfo> loadAppInfoFromApkFile(String apkFilePath) {
        PackageInfo packageArchiveInfo = this.mPm.getPackageArchiveInfo(apkFilePath, 0);
        packageArchiveInfo.applicationInfo.sourceDir = apkFilePath;
        packageArchiveInfo.applicationInfo.publicSourceDir = apkFilePath;
        ArrayList arrayList = new ArrayList();
        String str = (String) packageArchiveInfo.applicationInfo.loadLabel(this.mPm);
        List<ResolveInfo> queryIntentActivities = this.mPm.queryIntentActivities(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setPackage(packageArchiveInfo.packageName), 786432);
        LauncherIcons obtain = LauncherIcons.obtain(Utils.getApp());
        for (ResolveInfo resolveInfo : queryIntentActivities) {
            AppInfo appInfo = new AppInfo();
            appInfo.title = str;
            appInfo.componentName = new ComponentName(packageArchiveInfo.packageName, resolveInfo.activityInfo.name);
            appInfo.user = Process.myUserHandle();
            Drawable loadIcon = resolveInfo.activityInfo.loadIcon(this.mPm);
            if (loadIcon != null) {
                appInfo.iconBitmap = obtain.createBadgedIconBitmap(loadIcon, appInfo.user, !this.mXPAppList.contains(packageArchiveInfo.packageName)).icon;
            }
            arrayList.add(appInfo);
        }
        obtain.recycle();
        return arrayList;
    }

    public Drawable getIcon(LauncherActivityInfo launcherActivityInfo) {
        if (this.mXPAppList.contains(launcherActivityInfo.getComponentName().getPackageName())) {
            return launcherActivityInfo.getIcon(this.mAppContext.getResources().getDisplayMetrics().densityDpi);
        }
        return launcherActivityInfo.getIcon(320);
    }

    public Drawable getIcon(ComponentInfo t) {
        if (this.mXPAppList.contains(t.packageName)) {
            return getIcon(t, this.mAppContext.getResources().getDisplayMetrics().densityDpi);
        }
        return getIcon(t, 320);
    }

    public Drawable getIcon(ComponentInfo t, int density) {
        int iconResource = t.getIconResource();
        if (density != 0 && iconResource != 0) {
            try {
                return this.mPm.getResourcesForApplication(t.applicationInfo).getDrawableForDensity(iconResource, density);
            } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
            }
        }
        return null;
    }

    public boolean wrapToAdaptive(String packageName) {
        return !this.mXPAppList.contains(packageName);
    }

    @Override // com.xiaopeng.appstore.bizcommon.common.BizLifecycle
    public void destroy() {
        this.mPackageSoftCallbackList.clear();
        clearInstallSoftCallbacks();
    }

    public PackageInstaller.SessionInfo getInstallSessionInfo(int sessionId) {
        return this.mPackageInstaller.getSessionInfo(sessionId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchOnPkgRemoved(String packageName, UserHandle user) {
        Set<OnAppsChangedCallback> set = this.mPackageCallbackList;
        if (set != null) {
            for (OnAppsChangedCallback onAppsChangedCallback : set) {
                onAppsChangedCallback.onPackageRemoved(packageName, user);
            }
        }
        Set<OnAppsChangedCallback> set2 = this.mPackageSoftCallbackList;
        if (set2 != null) {
            for (OnAppsChangedCallback onAppsChangedCallback2 : set2) {
                onAppsChangedCallback2.onPackageRemoved(packageName, user);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchOnPkgAdded(List<AppInfo> list) {
        Logger.t(TAG).d("dispatchOnPkgAdded list=" + list + " packageCallbacks=" + this.mPackageCallbackList);
        Set<OnAppsChangedCallback> set = this.mPackageCallbackList;
        if (set != null) {
            for (OnAppsChangedCallback onAppsChangedCallback : set) {
                onAppsChangedCallback.onPackageAdded(list);
            }
        }
        Set<OnAppsChangedCallback> set2 = this.mPackageSoftCallbackList;
        if (set2 != null) {
            for (OnAppsChangedCallback onAppsChangedCallback2 : set2) {
                onAppsChangedCallback2.onPackageAdded(list);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchOnPkgChanged(String packageName, UserHandle user) {
        Set<OnAppsChangedCallback> set = this.mPackageCallbackList;
        if (set != null) {
            for (OnAppsChangedCallback onAppsChangedCallback : set) {
                onAppsChangedCallback.onPackageChanged(packageName, user);
            }
        }
        Set<OnAppsChangedCallback> set2 = this.mPackageSoftCallbackList;
        if (set2 != null) {
            for (OnAppsChangedCallback onAppsChangedCallback2 : set2) {
                onAppsChangedCallback2.onPackageChanged(packageName, user);
            }
        }
    }

    private void dispatchPkgsAvailable(String[] packageNames, UserHandle user, boolean replacing) {
        Set<OnAppsChangedCallback> set = this.mPackageCallbackList;
        if (set != null) {
            for (OnAppsChangedCallback onAppsChangedCallback : set) {
                onAppsChangedCallback.onPackagesAvailable(packageNames, user, replacing);
            }
        }
        Set<OnAppsChangedCallback> set2 = this.mPackageSoftCallbackList;
        if (set2 != null) {
            for (OnAppsChangedCallback onAppsChangedCallback2 : set2) {
                onAppsChangedCallback2.onPackagesAvailable(packageNames, user, replacing);
            }
        }
    }

    private void dispatchPkgsUnavailable(String[] packageNames, UserHandle user, boolean replacing) {
        Set<OnAppsChangedCallback> set = this.mPackageCallbackList;
        if (set != null) {
            for (OnAppsChangedCallback onAppsChangedCallback : set) {
                onAppsChangedCallback.onPackagesUnavailable(packageNames, user, replacing);
            }
        }
        Set<OnAppsChangedCallback> set2 = this.mPackageSoftCallbackList;
        if (set2 != null) {
            for (OnAppsChangedCallback onAppsChangedCallback2 : set2) {
                onAppsChangedCallback2.onPackagesUnavailable(packageNames, user, replacing);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchPkgUpdated(String packageName) {
        PackageInfo queryPackageInfo = queryPackageInfo(packageName);
        if (queryPackageInfo != null) {
            this.mPackageInfoMap.put(packageName, queryPackageInfo);
            this.mPackageMapLive.postValue(this.mPackageInfoMap);
        }
        Set<OnAppsChangedCallback> set = this.mPackageCallbackList;
        if (set != null) {
            for (OnAppsChangedCallback onAppsChangedCallback : set) {
                onAppsChangedCallback.onPackageUpdated(packageName);
            }
        }
        Set<OnAppsChangedCallback> set2 = this.mPackageSoftCallbackList;
        if (set2 != null) {
            for (OnAppsChangedCallback onAppsChangedCallback2 : set2) {
                onAppsChangedCallback2.onPackageUpdated(packageName);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class AppInstallCallbackDelegate extends PackageInstaller.SessionCallback {
        private static final String TAG = "AppMgrInstall";
        private final OnAppInstallCallback mCallback;
        private final WeakReference<AppComponentManager> mMgrRef;
        private final SparseArray<String> mPkgList = new SparseArray<>(1);

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public void onActiveChanged(int sessionId, boolean active) {
        }

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public void onBadgingChanged(int sessionId) {
        }

        AppInstallCallbackDelegate(OnAppInstallCallback callback, AppComponentManager mgr) {
            this.mCallback = callback;
            this.mMgrRef = new WeakReference<>(mgr);
        }

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public void onCreated(int sessionId) {
            AppComponentManager appComponentManager = this.mMgrRef.get();
            String str = null;
            if (appComponentManager != null) {
                PackageInstaller.SessionInfo installSessionInfo = appComponentManager.getInstallSessionInfo(sessionId);
                if (installSessionInfo != null) {
                    str = installSessionInfo.getAppPackageName();
                    this.mPkgList.put(sessionId, str);
                    Logger.t(TAG).i("onCreate, sessionId=" + sessionId + " pn=" + str, new Object[0]);
                } else {
                    Logger.t(TAG).w("onCreate error, info is null. sessionId=" + sessionId, new Object[0]);
                }
            } else {
                Logger.t(TAG).w("onCreate error, AppComponentManager ref is null. sessionId=" + sessionId, new Object[0]);
            }
            OnAppInstallCallback onAppInstallCallback = this.mCallback;
            if (onAppInstallCallback != null) {
                onAppInstallCallback.onInstallCreate(sessionId, str);
            }
        }

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public void onProgressChanged(int sessionId, float progress) {
            if (this.mCallback != null) {
                this.mCallback.onInstallProgressChanged(sessionId, this.mPkgList.get(sessionId), progress);
            }
        }

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public void onFinished(int sessionId, boolean success) {
            String str;
            int indexOfKey = this.mPkgList.indexOfKey(sessionId);
            if (indexOfKey < 0 || indexOfKey >= this.mPkgList.size()) {
                str = null;
            } else {
                str = this.mPkgList.valueAt(indexOfKey);
                this.mPkgList.removeAt(indexOfKey);
            }
            Logger.t(TAG).i("onFinished, sessionId=" + sessionId + " pn=" + str + " listSize=" + this.mPkgList.size(), new Object[0]);
            OnAppInstallCallback onAppInstallCallback = this.mCallback;
            if (onAppInstallCallback != null) {
                onAppInstallCallback.onInstallFinished(sessionId, str, success);
            }
        }
    }

    /* loaded from: classes2.dex */
    private class InstallerCallback extends PackageInstaller.SessionCallback {
        private InstallerCallback() {
        }

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public void onCreated(int sessionId) {
            Logger.t(AppComponentManager.TAG).d("InstallerCallback onCreated id=" + sessionId);
            PackageInstaller.SessionInfo sessionInfo = AppComponentManager.this.mPackageInstaller.getSessionInfo(sessionId);
            if (sessionInfo != null) {
                AppComponentManager.this.mInstallingPackageMap.put(sessionId, sessionInfo.getAppPackageName());
            } else {
                Logger.t(AppComponentManager.TAG).w("InstallerCallback onCreated, error:info is null. id=" + sessionId, new Object[0]);
            }
        }

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public void onBadgingChanged(int sessionId) {
            Logger.t(AppComponentManager.TAG).d("InstallerCallback onBadgingChanged id=" + sessionId);
        }

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public void onActiveChanged(int sessionId, boolean active) {
            Logger.t(AppComponentManager.TAG).d("InstallerCallback onActiveChanged id=" + sessionId + " active=" + active);
        }

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public void onProgressChanged(int sessionId, float progress) {
            Logger.t(AppComponentManager.TAG).v("InstallerCallback onProgressChanged id=" + sessionId + " pn=" + ((String) AppComponentManager.this.mInstallingPackageMap.get(sessionId)) + " progress=" + progress, new Object[0]);
        }

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public void onFinished(int sessionId, boolean success) {
            Logger.t(AppComponentManager.TAG).d("InstallerCallback onFinished sessionId=" + sessionId + " pn=" + ((String) AppComponentManager.this.mInstallingPackageMap.get(sessionId)) + " success=" + success);
            AppComponentManager.this.mInstallingPackageMap.remove(sessionId);
        }
    }

    /* loaded from: classes2.dex */
    public class AppStorePackageMonitor extends PackageMonitor {
        private final WeakReference<AppComponentManager> mMgrRef;

        AppStorePackageMonitor(AppComponentManager mgr) {
            this.mMgrRef = new WeakReference<>(mgr);
        }

        @Override // com.xiaopeng.appstore.libcommon.utils.PackageMonitor, android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Logger.t(AppComponentManager.TAG).d("PackageMonitor onReceive: action=" + intent.getAction() + " data=" + intent.getData() + " uid=" + intent.getIntExtra("android.intent.extra.UID", -1) + " target=" + intent.getPackage());
            if (intent.getPackage() != null) {
                return;
            }
            super.onReceive(context, intent);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaopeng.appstore.libcommon.utils.PackageMonitor
        public void onPackageUpdated(String packageName) {
            super.onPackageUpdated(packageName);
            Logger.t(AppComponentManager.TAG).d("PackageMonitor onPackageUpdateFinished pn=" + packageName + " this=" + hashCode());
            AppComponentManager appComponentManager = this.mMgrRef.get();
            if (appComponentManager != null) {
                appComponentManager.dispatchPkgUpdated(packageName);
            } else {
                Logger.t(AppComponentManager.TAG).w("PackageMonitor onPackageUpdateFinished error, mgr is null. pn=" + packageName, new Object[0]);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaopeng.appstore.libcommon.utils.PackageMonitor
        public void onPackageAdded(final String packageName) {
            super.onPackageAdded(packageName);
            Logger.t(AppComponentManager.TAG).d("onPackageAdded packageName=" + packageName);
            if (AppComponentManager.this.appFilter(packageName)) {
                Logger.t(AppComponentManager.TAG).d("PackageAdded:appFilter:" + packageName);
            } else {
                AppExecutors.get().diskIO().execute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.app.-$$Lambda$AppComponentManager$AppStorePackageMonitor$Qu22PLDZ18fBXH0RRMLFD2JDCm4
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppComponentManager.AppStorePackageMonitor.this.lambda$onPackageAdded$0$AppComponentManager$AppStorePackageMonitor(packageName);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onPackageAdded$0$AppComponentManager$AppStorePackageMonitor(String str) {
            synchronized (AppComponentManager.this.mLock) {
                List<ActivityInfo> launcherActivity = AppComponentManager.this.getLauncherActivity(str);
                List<ServiceInfo> mediaServiceInfoList = AppComponentManager.this.getMediaServiceInfoList(str);
                Logger.t(AppComponentManager.TAG).d("PackageAdded:", "ActivityInfo Size :" + launcherActivity.size() + "ServiceInfo Size :" + mediaServiceInfoList.size());
                ArrayList arrayList = new ArrayList();
                if (launcherActivity.size() > 0) {
                    AppComponentManager.this.parseToLaunchAppInfoList(launcherActivity, arrayList);
                } else if (mediaServiceInfoList.size() <= 0) {
                    return;
                } else {
                    AppComponentManager.this.parseToMediaAppInfoList(mediaServiceInfoList, arrayList);
                    AppComponentManager.this.addMediaPackageToList(mediaServiceInfoList);
                }
                AppComponentManager.this.mAppInfoList.addAll(arrayList);
                AppComponentManager.this.addAppToMap(str, arrayList);
                PackageInfo queryPackageInfo = AppComponentManager.this.queryPackageInfo(str);
                if (queryPackageInfo != null) {
                    AppComponentManager.this.mPackageInfoMap.put(str, queryPackageInfo);
                    AppComponentManager.this.mPackageMapLive.postValue(AppComponentManager.this.mPackageInfoMap);
                }
                AppComponentManager.this.dispatchOnPkgAdded(arrayList);
                AppComponentManager.this.mAppListLiveData.postValue(AppComponentManager.this.mAppInfoList);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaopeng.appstore.libcommon.utils.PackageMonitor
        public void onPackageRemoved(final String packageName) {
            super.onPackageRemoved(packageName);
            Logger.t(AppComponentManager.TAG).d("onPackageRemoved packageName=" + packageName);
            if (AppComponentManager.this.appFilter(packageName)) {
                Logger.t(AppComponentManager.TAG).d("PackageRemoved:appFilter:" + packageName);
            } else {
                AppExecutors.get().diskIO().execute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.app.-$$Lambda$AppComponentManager$AppStorePackageMonitor$h4DdqUEMKVYXoseB19dMHbMd2Ww
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppComponentManager.AppStorePackageMonitor.this.lambda$onPackageRemoved$1$AppComponentManager$AppStorePackageMonitor(packageName);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onPackageRemoved$1$AppComponentManager$AppStorePackageMonitor(String str) {
            synchronized (AppComponentManager.this.mLock) {
                Iterator it = AppComponentManager.this.mAppInfoList.iterator();
                while (it.hasNext()) {
                    AppInfo appInfo = (AppInfo) it.next();
                    if (appInfo.componentName.getPackageName().equals(str) && appInfo.user.equals(Process.myUserHandle())) {
                        it.remove();
                        AppComponentManager.this.removeAppInfoFromMap(appInfo);
                    }
                }
                AppComponentManager.this.mMediaAppPackageNameList.remove(str);
                AppComponentManager.this.mPackageInfoMap.remove(str);
                AppComponentManager.this.mPackageMapLive.postValue(AppComponentManager.this.mPackageInfoMap);
                AppComponentManager.this.dispatchOnPkgRemoved(str, Process.myUserHandle());
                AppComponentManager.this.mAppListLiveData.postValue(AppComponentManager.this.mAppInfoList);
            }
        }

        @Override // com.xiaopeng.appstore.libcommon.utils.PackageMonitor
        public boolean onPackageChanged(final String packageName, String[] components) {
            Logger.t(AppComponentManager.TAG).d("onPackageChanged packageName=" + packageName);
            if (AppComponentManager.this.appFilter(packageName)) {
                Logger.t(AppComponentManager.TAG).d("PackageChanged:appFilter:" + packageName);
                return super.onPackageChanged(packageName, components);
            }
            AppExecutors.get().diskIO().execute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.app.-$$Lambda$AppComponentManager$AppStorePackageMonitor$1mvALfk9CI0UYfclPPvthg4Ax4U
                @Override // java.lang.Runnable
                public final void run() {
                    AppComponentManager.AppStorePackageMonitor.this.lambda$onPackageChanged$2$AppComponentManager$AppStorePackageMonitor(packageName);
                }
            });
            return super.onPackageChanged(packageName, components);
        }

        public /* synthetic */ void lambda$onPackageChanged$2$AppComponentManager$AppStorePackageMonitor(String str) {
            synchronized (AppComponentManager.this.mLock) {
                List<ActivityInfo> launcherActivity = AppComponentManager.this.getLauncherActivity(str);
                List<ServiceInfo> mediaServiceInfoList = AppComponentManager.this.getMediaServiceInfoList(str);
                Logger.t(AppComponentManager.TAG).d(" package changed", "ActivityInfo Size :" + launcherActivity.size() + "ServiceInfo Size :" + mediaServiceInfoList.size());
                if (launcherActivity.size() > 0) {
                    for (ActivityInfo activityInfo : launcherActivity) {
                        AppInfo findAppInfo = AppComponentManager.this.findAppInfo(activityInfo.packageName, Process.myUserHandle());
                        if (findAppInfo != null) {
                            findAppInfo.apply(activityInfo, AppComponentManager.this.mPm, true);
                        } else {
                            AppInfo appInfo = new AppInfo(activityInfo, AppComponentManager.this.mPm, true);
                            AppComponentManager.this.mAppInfoList.add(appInfo);
                            AppComponentManager.this.addAppToMap(appInfo);
                        }
                    }
                } else if (mediaServiceInfoList.size() <= 0) {
                    return;
                } else {
                    for (ServiceInfo serviceInfo : mediaServiceInfoList) {
                        AppInfo findAppInfo2 = AppComponentManager.this.findAppInfo(serviceInfo.packageName, Process.myUserHandle());
                        if (findAppInfo2 != null) {
                            findAppInfo2.apply(serviceInfo, AppComponentManager.this.mPm, false);
                        } else {
                            AppInfo appInfo2 = new AppInfo(serviceInfo, AppComponentManager.this.mPm, false);
                            AppComponentManager.this.mAppInfoList.add(appInfo2);
                            AppComponentManager.this.addAppToMap(appInfo2);
                            AppComponentManager.this.mMediaAppPackageNameList.add(appInfo2.componentName.getPackageName());
                        }
                    }
                }
                AppComponentManager.this.dispatchOnPkgChanged(str, Process.myUserHandle());
                AppComponentManager.this.mAppListLiveData.postValue(AppComponentManager.this.mAppInfoList);
            }
        }
    }

    public List<ServiceInfo> getMediaServiceInfoList(String packageName) {
        Intent intent = new Intent();
        ArrayList arrayList = new ArrayList();
        intent.setAction(MediaBrowserServiceCompat.SERVICE_INTERFACE);
        for (ResolveInfo resolveInfo : this.mPm.queryIntentServices(intent, 64)) {
            if (resolveInfo.serviceInfo.packageName.equals(packageName)) {
                arrayList.add(resolveInfo.serviceInfo);
            }
        }
        return arrayList;
    }

    public List<ActivityInfo> getLauncherActivity(String packageName) {
        ArrayList arrayList = new ArrayList();
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        for (ResolveInfo resolveInfo : this.mPm.queryIntentActivities(intent, 0)) {
            if (resolveInfo.activityInfo.packageName.equals(packageName)) {
                arrayList.add(resolveInfo.activityInfo);
            }
        }
        return arrayList;
    }

    public List<ResolveInfo> queryIntentActivities(Intent intent) {
        return this.mPm.queryIntentActivities(intent, 0);
    }

    public String getAppName(Intent intent) {
        List<ResolveInfo> queryIntentActivities = queryIntentActivities(intent);
        return !queryIntentActivities.isEmpty() ? queryIntentActivities.get(0).activityInfo.loadLabel(this.mPm).toString() : "";
    }

    public String getAppName(String packageName) {
        List<ActivityInfo> launcherActivity = getLauncherActivity(packageName);
        return launcherActivity.size() > 0 ? launcherActivity.get(0).loadLabel(this.mPm).toString() : packageName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addMediaPackageToList(List<ServiceInfo> list) {
        for (ServiceInfo serviceInfo : list) {
            this.mMediaAppPackageNameList.add(serviceInfo.packageName);
        }
    }

    public Set<String> getMediaAppList() {
        return this.mMediaAppPackageNameList;
    }
}
