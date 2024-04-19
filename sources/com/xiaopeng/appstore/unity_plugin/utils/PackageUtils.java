package com.xiaopeng.appstore.unity_plugin.utils;

import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.Log;
/* loaded from: classes2.dex */
public class PackageUtils {
    public static final String ACTION_REMOVE = "com.xiaopeng.appstore.resource.action.remove";
    private static final String ACTION_UNINSTALL_COMPLETE = "com.xiaopeng.xpstore.UNINSTALL_COMPLETE";
    public static final String INTENT_KEY_ASSEMBLE_KEY = "intent_key_assemble_key";
    public static final String INTENT_KEY_CALLING_PACKAGE = "intent_key_calling_package";
    public static final String INTENT_KEY_RES_TYPE = "intent_key_res_type";
    public static final String PACKAGE_NAME = "com.xiaopeng.appstore";
    public static final int RES_TYPE_APP = 1000;
    public static final String SERVICE = "com.xiaopeng.appstore.resourceservice.ResourceServiceV2";
    private static final String TAG = "PackageUtils";

    public static boolean startActivitySafely(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager.isSafeMode() && !isSystemApp(packageManager, intent)) {
            Log.w(TAG, "startActivitySafely, cannot start none system app in safe mode:" + intent);
            return false;
        }
        intent.addFlags(268435456);
        try {
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException | SecurityException e) {
            Log.e(TAG, "Unable to launch. intent=" + intent + ", ex:" + e);
            return false;
        }
    }

    public static boolean isSystemApp(PackageManager pm, Intent intent) {
        String packageName;
        ComponentName component = intent.getComponent();
        if (component == null) {
            ResolveInfo resolveActivity = pm.resolveActivity(intent, 65536);
            packageName = (resolveActivity == null || resolveActivity.activityInfo == null) ? null : resolveActivity.activityInfo.packageName;
        } else {
            packageName = component.getPackageName();
        }
        return isSystemApp(pm, packageName);
    }

    public static boolean isSystemApp(PackageManager pm, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
            if (packageInfo == null || packageInfo.applicationInfo == null) {
                return false;
            }
            return (packageInfo.applicationInfo.flags & 1) != 0;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    private static Intent removeIntent(int resType, String key, String callingPackage) {
        Intent intent = new Intent("com.xiaopeng.appstore.resource.action.remove");
        intent.setClassName("com.xiaopeng.appstore", "com.xiaopeng.appstore.resourceservice.ResourceServiceV2");
        intent.putExtra("intent_key_res_type", resType);
        intent.putExtra("intent_key_assemble_key", key);
        intent.putExtra("intent_key_calling_package", callingPackage);
        return intent;
    }

    public static boolean uninstallXP(Context context, String packageName) {
        Log.i(TAG, "uninstallXP: " + packageName);
        context.startForegroundService(removeIntent(1000, packageName, context.getPackageName()));
        return true;
    }

    public static boolean uninstall(Context context, String packageName, int requestCode) {
        Log.d(TAG, "uninstallAsync start, pn is " + packageName);
        context.getPackageManager().getPackageInstaller().uninstall(packageName, createUninstallIntentSender(context, packageName, requestCode));
        Log.d(TAG, "uninstallAsync finish, pn is " + packageName);
        return true;
    }

    private static IntentSender createUninstallIntentSender(Context context, String packageName, int requestCode) {
        Intent intent = new Intent(ACTION_UNINSTALL_COMPLETE);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", packageName);
        return PendingIntent.getBroadcast(context, requestCode, intent, 134217728).getIntentSender();
    }
}
