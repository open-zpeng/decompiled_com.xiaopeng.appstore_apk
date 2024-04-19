package com.xiaopeng.appstore.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.ConfigReceiver;
import com.xiaopeng.appstore.applist_biz.logic.AppListCache;
/* loaded from: classes2.dex */
public class AppListChangeReceiver extends BroadcastReceiver {
    private static final String ACTION_NAPA_PROV_CHANGE = "com.xiaopeng.intent.action.XP_NAPA_APP_LIST_CHANGE";
    private static final String TAG = "AppListChangeReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (ConfigReceiver.getAppMode(context) == 0) {
            String action = intent.getAction();
            Logger.t(TAG).w("onReceive action : " + action, new Object[0]);
            if (ACTION_NAPA_PROV_CHANGE.equals(action)) {
                AppListCache.get().getNapaAppList(true);
            }
        }
    }
}
