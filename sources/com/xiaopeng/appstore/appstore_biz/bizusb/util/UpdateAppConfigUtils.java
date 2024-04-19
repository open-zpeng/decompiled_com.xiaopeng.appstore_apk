package com.xiaopeng.appstore.appstore_biz.bizusb.util;

import android.content.Intent;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import androidx.media.MediaBrowserServiceCompat;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.datamodel.api.XpApiClient;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailData;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailListRequest;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppRequestContainer;
import com.xiaopeng.appstore.bizcommon.entities.AppConfigInfo;
import com.xiaopeng.appstore.bizcommon.entities.AppInfo;
import com.xiaopeng.appstore.bizcommon.entities.common.XpApiResponse;
import com.xiaopeng.appstore.bizcommon.entities.db.AppConfigDao;
import com.xiaopeng.appstore.bizcommon.entities.db.XpAppStoreDatabase;
import com.xiaopeng.appstore.bizcommon.http.ApiHelper;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.utils.DatabaseUtils;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class UpdateAppConfigUtils {
    private static final String FILTER_NAME = "xiaopeng";
    private static final String TAG = "UpdateAppConfigUtils";

    public static void checkAndUpdateAppConfig() {
        Logger.t(TAG).d("checkAndUpdateAppConfig");
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.bizusb.util.-$$Lambda$UpdateAppConfigUtils$_NG9muJSWgUnpcJOqavoLrnAefA
            @Override // java.lang.Runnable
            public final void run() {
                UpdateAppConfigUtils.checkConfigs(UpdateAppConfigUtils.getAllLocalApp());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkConfigs(ArrayList<AppInfo> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        AppDetailListRequest appDetailListRequest = new AppDetailListRequest();
        ArrayList<AppRequestContainer> arrayList = new ArrayList<>();
        Iterator<AppInfo> it = list.iterator();
        while (it.hasNext()) {
            AppRequestContainer appRequestContainer = new AppRequestContainer();
            appRequestContainer.setPackageName(it.next().componentName.getPackageName());
            arrayList.add(appRequestContainer);
        }
        appDetailListRequest.setParam(arrayList);
        XpApiClient.getAppService().checkAppDetails(appDetailListRequest).enqueue(new Callback<XpApiResponse<ArrayList<AppDetailData>>>() { // from class: com.xiaopeng.appstore.appstore_biz.bizusb.util.UpdateAppConfigUtils.1
            @Override // retrofit2.Callback
            public void onResponse(Call<XpApiResponse<ArrayList<AppDetailData>>> call, Response<XpApiResponse<ArrayList<AppDetailData>>> response) {
                if (response.isSuccessful() && response.body() == null) {
                    return;
                }
                UpdateAppConfigUtils.compareWithDB(response);
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<XpApiResponse<ArrayList<AppDetailData>>> call, Throwable t) {
                Logger.t(UpdateAppConfigUtils.TAG).d("checkConfigs--->  调用details接口失败");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void compareWithDB(Response<XpApiResponse<ArrayList<AppDetailData>>> response) {
        final ArrayList arrayList = (ArrayList) ApiHelper.getXpResponseData(response.body());
        final HashMap hashMap = new HashMap();
        final AppConfigDao appConfigDao = XpAppStoreDatabase.getInstance().getAppConfigDao();
        DatabaseUtils.tryExecute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.bizusb.util.-$$Lambda$UpdateAppConfigUtils$flSQ16197QTwzA1qqdK3EGdifFI
            @Override // java.lang.Runnable
            public final void run() {
                UpdateAppConfigUtils.lambda$compareWithDB$1(AppConfigDao.this, hashMap, arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$compareWithDB$1(AppConfigDao appConfigDao, HashMap hashMap, ArrayList arrayList) {
        List<AppConfigInfo> queryAppConfigInfoList = appConfigDao.queryAppConfigInfoList();
        if (queryAppConfigInfoList != null && queryAppConfigInfoList.size() > 0) {
            for (AppConfigInfo appConfigInfo : queryAppConfigInfoList) {
                hashMap.put(appConfigInfo.getPackageName(), appConfigInfo.getConfigUrl());
            }
        }
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            AppDetailData appDetailData = (AppDetailData) it.next();
            String packageName = appDetailData.getPackageName();
            String str = (String) hashMap.get(packageName);
            if (TextUtils.isEmpty(str) || !TextUtils.equals(str, appDetailData.getConfigUrl())) {
                AppStoreAssembleManager.get().updateLocalConfig(appDetailData.getConfigUrl(), appDetailData.getConfigMd5(), appDetailData.getPackageName());
            }
            Logger.t(TAG).d("compareWithDB--->  packageName ：" + packageName + "; dbConfigUrl  ： " + str + ";  configUrl ： " + appDetailData.getConfigUrl() + ", configMd5 = " + appDetailData.getConfigMd5());
        }
    }

    private static ArrayList<AppInfo> getAllLocalApp() {
        ArrayList<AppInfo> arrayList = new ArrayList<>();
        List<UserHandle> userProfiles = ((UserManager) com.xiaopeng.appstore.libcommon.utils.Utils.getApp().getSystemService("user")).getUserProfiles();
        int size = userProfiles.size();
        LauncherApps launcherApps = (LauncherApps) com.xiaopeng.appstore.libcommon.utils.Utils.getApp().getSystemService("launcherapps");
        for (int i = 0; i < size; i++) {
            UserHandle userHandle = userProfiles.get(i);
            for (LauncherActivityInfo launcherActivityInfo : launcherApps.getActivityList(null, userHandle)) {
                if (!appFilter(launcherActivityInfo.getComponentName().getPackageName())) {
                    arrayList.add(new AppInfo(launcherActivityInfo));
                    Logger.t(TAG).v("packageName=" + launcherActivityInfo.getComponentName().getPackageName(), new Object[0]);
                }
            }
            Logger.t(TAG).d("local app list user = " + userHandle + " size = " + arrayList.size());
        }
        Intent intent = new Intent();
        intent.setAction(MediaBrowserServiceCompat.SERVICE_INTERFACE);
        for (ResolveInfo resolveInfo : com.xiaopeng.appstore.libcommon.utils.Utils.getApp().getApplicationContext().getPackageManager().queryIntentServices(intent, 64)) {
            arrayList.add(new AppInfo(resolveInfo.serviceInfo, com.xiaopeng.appstore.libcommon.utils.Utils.getApp().getApplicationContext().getPackageManager(), false));
            Logger.t(TAG).v("packageName=" + resolveInfo.serviceInfo.packageName, new Object[0]);
        }
        return arrayList;
    }

    private static boolean appFilter(String packageName) {
        return packageName.contains(FILTER_NAME);
    }
}
