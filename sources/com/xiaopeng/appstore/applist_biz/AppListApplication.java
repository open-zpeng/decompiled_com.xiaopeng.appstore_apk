package com.xiaopeng.appstore.applist_biz;

import android.os.UserHandle;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.applist_biz.logic.AppListCache;
import com.xiaopeng.appstore.applist_biz.router.AppListProviderImpl;
import com.xiaopeng.appstore.bizcommon.entities.AppInfo;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.router.RouterConstants;
import com.xiaopeng.appstore.bizcommon.router.RouterManager;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import java.util.List;
/* loaded from: classes2.dex */
public class AppListApplication implements AppComponentManager.OnAppsChangedCallback {
    private static final String TAG = "AppListApplication";
    private static volatile AppListApplication sInstance;

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackageChanged(String packageName, UserHandle user) {
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackagesAvailable(String[] packageNames, UserHandle user, boolean replacing) {
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackagesUnavailable(String[] packageNames, UserHandle user, boolean replacing) {
    }

    public static AppListApplication get() {
        if (sInstance == null) {
            synchronized (AppListApplication.class) {
                if (sInstance == null) {
                    sInstance = new AppListApplication();
                }
            }
        }
        return sInstance;
    }

    public void init(List<String> xpAppList) {
        RouterManager.get().registerModule(RouterConstants.AppListProviderPaths.APP_LIST_MAIN_PROVIDER, new AppListProviderImpl());
        AppComponentManager.get().addOnAppsChangedCallback(this);
        AppListCache.get().init(xpAppList);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackageRemoved(String packageName, UserHandle user) {
        Logger.t(TAG).d("onPackageRemoved pn=" + packageName);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackageAdded(List<AppInfo> appInfoList) {
        String str = "";
        for (AppInfo appInfo : appInfoList) {
            str = appInfo.componentName.getPackageName();
            Logger.t(TAG).d("onPackageAdded pn=" + str);
        }
        LogicUtils.setLastInstalledApp(str);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackageUpdated(String packageName) {
        Logger.t(TAG).d("onPackageUpdated pn=" + packageName);
        LogicUtils.setLastInstalledApp(packageName);
    }
}
