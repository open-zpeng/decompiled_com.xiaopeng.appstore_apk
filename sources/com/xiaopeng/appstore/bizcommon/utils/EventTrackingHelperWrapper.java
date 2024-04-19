package com.xiaopeng.appstore.bizcommon.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.icu.util.Calendar;
import android.os.Process;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.GsonUtil;
import com.xiaopeng.appstore.libcommon.utils.SPUtils;
import com.xiaopeng.appstore.xpcommon.car.CarApiManager;
import com.xiaopeng.appstore.xpcommon.car.IMcuChangeListener;
import com.xiaopeng.appstore.xpcommon.eventtracking.AppInstalledEvent;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class EventTrackingHelperWrapper {
    public static final String ALIPAY_ACCOUNT_LOGGED_IN = "1";
    public static final String ALIPAY_ACCOUNT_NOT_LOG_IN = "2";
    private static final String APP_LIST_SEND_DAY_OF_YEAR = "app_list_send_day_of_year";
    private static final String APP_LIST_SEND_YEAR = "app_list_send_year";
    public static final String SP_NAME = "EventTrackingSP";
    private static final String TAG = EventTrackingHelper.class.getSimpleName();
    private static final IMcuChangeListener I_MCU_CHANGE_LISTENER = new IMcuChangeListener() { // from class: com.xiaopeng.appstore.bizcommon.utils.-$$Lambda$EventTrackingHelperWrapper$B1TiJEkp5zMZSy6Zf6rudDC1Hx0
        @Override // com.xiaopeng.appstore.xpcommon.car.IMcuChangeListener
        public final void onIgOff() {
            EventTrackingHelperWrapper.lambda$static$0();
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$static$0() {
        Logger.t(TAG).i("ig off event for sendAppListMolecast", new Object[0]);
        sendAppListMolecast();
    }

    private EventTrackingHelperWrapper() {
    }

    public static void registerIgOffListenerForEventTracker() {
        CarApiManager.getInstance().addMcuChangeListener(I_MCU_CHANGE_LISTENER);
    }

    public static void sendAppListMolecast() {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.utils.-$$Lambda$EventTrackingHelperWrapper$Q5B0PNmFO945zFTmVIzEekxrJXE
            @Override // java.lang.Runnable
            public final void run() {
                EventTrackingHelperWrapper.lambda$sendAppListMolecast$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$sendAppListMolecast$1() {
        int i = Calendar.getInstance().get(6);
        int i2 = Calendar.getInstance().get(1);
        String str = TAG;
        Logger.t(str).i("sendAppListMolecast dayOfYear = " + i + "   year = " + i2, new Object[0]);
        if (i2 >= 2020) {
            int i3 = SPUtils.getInstance(SP_NAME).getInt(APP_LIST_SEND_YEAR);
            int i4 = SPUtils.getInstance(SP_NAME).getInt(APP_LIST_SEND_DAY_OF_YEAR);
            Logger.t(str).i("sendAppListMolecast sentYear = " + i3 + "   sentDay = " + i4, new Object[0]);
            if (i2 >= i3 && i > i4) {
                List<LauncherActivityInfo> activityList = AppComponentManager.get().getActivityList(null, Process.myUserHandle());
                String buildInstalledEventStr = buildInstalledEventStr(activityList);
                Logger.t(str).i("sendAppListMolecast activityList = " + activityList + "  installedEventStr = " + buildInstalledEventStr, new Object[0]);
                EventTrackingHelper.sendMolecast(PagesEnum.STORE_INSTALLED_APP_LIST, EventEnum.INSTALLED_APP_LIST, buildInstalledEventStr);
                SPUtils.getInstance(SP_NAME).put(APP_LIST_SEND_YEAR, i2);
                SPUtils.getInstance(SP_NAME).put(APP_LIST_SEND_DAY_OF_YEAR, i);
                return;
            }
            Logger.t(str).i("app list info already sent today, year = " + i2 + "    day of year = " + i, new Object[0]);
            return;
        }
        Logger.t(str).i("date year invalid, year = " + i2, new Object[0]);
    }

    private static String buildInstalledEventStr(List<LauncherActivityInfo> installedList) {
        ArrayList arrayList = new ArrayList();
        for (LauncherActivityInfo launcherActivityInfo : installedList) {
            ApplicationInfo applicationInfo = launcherActivityInfo.getApplicationInfo();
            if (applicationInfo != null && !AppComponentManager.get().isSystemApp(applicationInfo.packageName)) {
                AppInstalledEvent appInstalledEvent = new AppInstalledEvent();
                appInstalledEvent.setAppName(launcherActivityInfo.getLabel().toString());
                appInstalledEvent.setPackageName(applicationInfo.packageName);
                arrayList.add(appInstalledEvent);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return GsonUtil.toJson(arrayList);
    }
}
