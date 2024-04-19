package com.xiaopeng.appstore.libcommon.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import com.orhanobut.logger.Logger;
import com.xiaopeng.lib.framework.aiassistantmodule.sensor.ContextSensor;
/* loaded from: classes2.dex */
public class PackageMonitor extends BroadcastReceiver {
    private static final String TAG = "PackageMonitor";
    static final IntentFilter sPackageFilt;
    private Context mRegisteredContext;
    private Handler mRegisteredHandler;

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPackageAdded(String packageName) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPackageRemoved(String packageName) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPackageUpdated(String packageName) {
    }

    static {
        IntentFilter intentFilter = new IntentFilter();
        sPackageFilt = intentFilter;
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme(ContextSensor.DATA_PACKAGE);
    }

    public boolean isRegistered() {
        return this.mRegisteredContext != null;
    }

    public void register(Context context, Handler handler) {
        Logger.t(TAG).i("register, ctx:" + context, new Object[0]);
        if (this.mRegisteredContext != null) {
            throw new IllegalStateException("Already registered");
        }
        this.mRegisteredContext = context;
        this.mRegisteredHandler = handler;
        context.registerReceiver(this, sPackageFilt, null, handler);
    }

    public void unregister() {
        Logger.t(TAG).i("unregister, ctx:" + this.mRegisteredContext, new Object[0]);
        Context context = this.mRegisteredContext;
        if (context == null) {
            throw new IllegalStateException("Not registered");
        }
        context.unregisterReceiver(this);
        this.mRegisteredContext = null;
    }

    public Handler getRegisteredHandler() {
        return this.mRegisteredHandler;
    }

    public boolean onPackageChanged(String packageName, String[] components) {
        if (components != null) {
            for (String str : components) {
                if (packageName.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (intent.getPackage() != null) {
            return;
        }
        Logger.t(TAG).i("onReceive:" + action, new Object[0]);
        if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            String packageName = getPackageName(intent);
            if (packageName != null) {
                if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                    Logger.t(TAG).i("Package updated:" + packageName, new Object[0]);
                    onPackageUpdated(packageName);
                    return;
                }
                Logger.t(TAG).i("Package added:" + packageName, new Object[0]);
                onPackageAdded(packageName);
            }
        } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
            String packageName2 = getPackageName(intent);
            if (packageName2 != null) {
                if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                    Logger.t(TAG).i("Package removed:" + packageName2 + ", replacing.", new Object[0]);
                    return;
                }
                Logger.t(TAG).i("Package removed:" + packageName2, new Object[0]);
                onPackageRemoved(packageName2);
            }
        } else if ("android.intent.action.PACKAGE_CHANGED".equals(action)) {
            onPackageChanged(getPackageName(intent), intent.getStringArrayExtra("android.intent.extra.changed_component_name_list"));
        }
    }

    String getPackageName(Intent intent) {
        Uri data = intent.getData();
        if (data != null) {
            return data.getSchemeSpecificPart();
        }
        return null;
    }
}
