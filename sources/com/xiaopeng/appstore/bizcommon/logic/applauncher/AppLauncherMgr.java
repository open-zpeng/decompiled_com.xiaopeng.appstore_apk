package com.xiaopeng.appstore.bizcommon.logic.applauncher;

import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.AppLauncher;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LaunchParam;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LaunchResult;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LauncherInterceptor;
/* loaded from: classes2.dex */
public class AppLauncherMgr {
    private static final String TAG = "AppLauncherMgr";
    private final AppLauncher mAppLauncher;

    public void release() {
    }

    /* loaded from: classes2.dex */
    static class SingletonHolder {
        static final AppLauncherMgr sInstance = new AppLauncherMgr();

        SingletonHolder() {
        }
    }

    public static AppLauncherMgr get() {
        return SingletonHolder.sInstance;
    }

    private AppLauncherMgr() {
        Logger.t(TAG).d("AppLauncherMgr <init> start");
        this.mAppLauncher = new AppLauncher();
        Logger.t(TAG).d("AppLauncherMgr <init> end");
    }

    public void addInterceptor(LauncherInterceptor interceptor) {
        this.mAppLauncher.addInterceptor(interceptor);
    }

    public void addInterceptor(LauncherInterceptor interceptor, boolean interceptFirst) {
        this.mAppLauncher.addInterceptor(interceptor, interceptFirst);
    }

    public void removeInterceptor(LauncherInterceptor interceptor) {
        this.mAppLauncher.removeInterceptor(interceptor);
    }

    public LaunchResult launch(LaunchParam param) {
        return this.mAppLauncher.launch(param);
    }
}
