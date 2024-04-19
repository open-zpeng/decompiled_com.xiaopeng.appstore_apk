package com.xiaopeng.appstore.resource_migrate;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;
import com.xiaopeng.appstore.resource_migrate.dbfile.MigrateHelper;
import com.xiaopeng.speech.vui.constants.VuiConstants;
/* loaded from: classes2.dex */
public class MigrateBroadcastReceiver extends BroadcastReceiver {
    private static final String ACTION_MIGRATE_COMPLETED = "com.xiaopeng.appstore.intent.action.MIGRATE_COMPLETED";
    private static final String EXTRA_KEY_MIGRATE_RESULT = "extra_key_migrate_result";
    private static final String TAG = "MigrateBroadcastReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
            MigrateHelper.startMigrate(context);
        } else if (action.equals(ACTION_MIGRATE_COMPLETED)) {
            boolean booleanExtra = intent.getBooleanExtra(EXTRA_KEY_MIGRATE_RESULT, false);
            Logger.t(TAG).i("Migrate completed, ret:" + booleanExtra, new Object[0]);
            if (booleanExtra) {
                MigrateHelper.deleteCache(context);
                setComponentEnabled(context, MigrateBroadcastReceiver.class, false);
            }
        }
    }

    private static void setComponentEnabled(Context context, Class<?> klazz, boolean enabled) {
        String str = VuiConstants.ELEMENT_ENABLED;
        try {
            context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, klazz.getName()), enabled ? 1 : 2, 1);
            Printer t = Logger.t(TAG);
            Object[] objArr = new Object[2];
            objArr[0] = klazz.getName();
            objArr[1] = enabled ? VuiConstants.ELEMENT_ENABLED : "disabled";
            t.d(String.format("%s %s", objArr));
        } catch (Exception e) {
            Printer t2 = Logger.t(TAG);
            StringBuilder sb = new StringBuilder();
            Object[] objArr2 = new Object[2];
            objArr2[0] = klazz.getName();
            if (!enabled) {
                str = "disabled";
            }
            objArr2[1] = str;
            t2.d(sb.append(String.format("%s could not be %s", objArr2)).append("ex:").append(e).toString());
        }
    }
}
