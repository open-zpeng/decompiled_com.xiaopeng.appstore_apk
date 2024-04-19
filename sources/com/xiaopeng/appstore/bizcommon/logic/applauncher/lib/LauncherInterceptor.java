package com.xiaopeng.appstore.bizcommon.logic.applauncher.lib;
/* loaded from: classes2.dex */
public interface LauncherInterceptor {

    /* loaded from: classes2.dex */
    public interface Chain {
        LaunchParam getLaunchParam();

        LaunchResult proceed();
    }

    LaunchResult intercept(Chain chain);
}
