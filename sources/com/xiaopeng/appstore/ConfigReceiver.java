package com.xiaopeng.appstore;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.libcommon.utils.SPUtils;
/* loaded from: classes2.dex */
public class ConfigReceiver extends BroadcastReceiver {
    public static final String ACTION_CONFIG_MODE = "com.xiaopeng.appstore.intent.action.CONFIG_MODE";
    public static final int ANDROID_UI = 0;
    private static final int DEFAULT_APP_MODE = 0;
    public static final String EXTRA_APPSTORE_MODE = "com.xiaopeng.appstore.extra.MODE";
    public static final int SERVER_APP = 1;
    private static final String TAG = "AppServerConfigReceiver";

    private boolean checkAppMode(int appMode) {
        return appMode == 0 || appMode == 1;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (ACTION_CONFIG_MODE.equals(intent.getAction())) {
            int intExtra = intent.getIntExtra(EXTRA_APPSTORE_MODE, 0);
            if (!checkAppMode(intExtra)) {
                Logger.t(TAG).w("Invalid appMode receive:" + intExtra, new Object[0]);
                return;
            }
            Logger.t(TAG).i("Set appMode:" + intExtra, new Object[0]);
            SPUtils.getInstance(context.getApplicationContext(), TAG).put(EXTRA_APPSTORE_MODE, intExtra);
        }
    }

    public static int getAppMode(Context context) {
        return SPUtils.getInstance(context.getApplicationContext(), TAG).getInt(EXTRA_APPSTORE_MODE, 0);
    }
}
