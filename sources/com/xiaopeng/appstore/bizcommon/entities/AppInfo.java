package com.xiaopeng.appstore.bizcommon.entities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import com.orhanobut.logger.Logger;
import java.util.Objects;
/* loaded from: classes2.dex */
public class AppInfo extends ItemInfoWithIcon {
    public static final String CAR_EXTRA_MEDIA_COMPONENT = "android.car.intent.extra.MEDIA_COMPONENT";
    public static final String CAR_INTENT_ACTION_MEDIA_TEMPLATE = "android.car.intent.action.MEDIA_TEMPLATE";
    public static final String KEY_MEDIA_PACKAGE = "media_package";
    private static final String TAG = "AppInfo";
    public ComponentName componentName;
    public long firstInstallTime;
    public Intent intent;
    public boolean isLauncher;

    public AppInfo() {
    }

    public AppInfo(LauncherActivityInfo info) {
        this.componentName = info.getComponentName();
        this.user = info.getUser();
        this.title = info.getLabel();
        this.intent = makeLaunchIntent(info);
        this.firstInstallTime = info.getFirstInstallTime();
    }

    public AppInfo(ComponentInfo t, PackageManager pm, boolean isLauncher) {
        this.componentName = new ComponentName(t.packageName, t.name);
        this.user = Process.myUserHandle();
        this.title = t.loadLabel(pm);
        this.intent = isLauncher ? makeLaunchIntent(this.componentName) : makeMediaLaunchIntent(this.componentName);
        this.firstInstallTime = getFirstInstallTime(pm, t);
    }

    public void apply(LauncherActivityInfo info) {
        this.componentName = info.getComponentName();
        this.user = info.getUser();
        this.title = info.getLabel();
        this.intent = makeLaunchIntent(info);
        this.firstInstallTime = info.getFirstInstallTime();
    }

    public void apply(ComponentInfo t, PackageManager pm, boolean isLauncher) {
        this.componentName = new ComponentName(t.packageName, t.name);
        this.user = Process.myUserHandle();
        this.title = t.loadLabel(pm);
        this.intent = isLauncher ? makeLaunchIntent(this.componentName) : makeMediaLaunchIntent(this.componentName);
        this.firstInstallTime = getFirstInstallTime(pm, t);
    }

    public ComponentKey toComponentKey() {
        return new ComponentKey(this.componentName, this.user);
    }

    public static Intent makeLaunchIntent(LauncherActivityInfo info) {
        return makeLaunchIntent(info.getComponentName());
    }

    public static Intent makeLaunchIntent(ComponentName cn) {
        return new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setComponent(cn).setFlags(270532608);
    }

    public String toString() {
        return "AppInfo{component=" + this.componentName + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof AppInfo) {
            AppInfo appInfo = (AppInfo) o;
            return Objects.equals(this.componentName, appInfo.componentName) && Objects.equals(this.user, appInfo.user);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.componentName, this.user);
    }

    public static Intent makeMediaLaunchIntent(ComponentName cn) {
        return new Intent(CAR_INTENT_ACTION_MEDIA_TEMPLATE).putExtra(KEY_MEDIA_PACKAGE, cn.getPackageName()).putExtra(CAR_EXTRA_MEDIA_COMPONENT, cn.flattenToString()).setFlags(270532608);
    }

    public long getFirstInstallTime(PackageManager pm, ComponentInfo t) {
        try {
            return pm.getPackageInfo(t.packageName, 8192).firstInstallTime;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.t(TAG).d("Exception:" + e.getMessage());
            return 0L;
        }
    }
}
