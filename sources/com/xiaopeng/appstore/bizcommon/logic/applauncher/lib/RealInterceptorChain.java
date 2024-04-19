package com.xiaopeng.appstore.bizcommon.logic.applauncher.lib;

import com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LauncherInterceptor;
import java.util.List;
/* loaded from: classes2.dex */
public class RealInterceptorChain implements LauncherInterceptor.Chain {
    private int mIndex;
    private List<LauncherInterceptor> mInterceptorList;
    private LaunchParam mLaunchParam;

    public RealInterceptorChain(int index, List<LauncherInterceptor> interceptorList, LaunchParam param) {
        this.mIndex = index;
        this.mInterceptorList = interceptorList;
        this.mLaunchParam = param;
    }

    public static RealInterceptorChain copy(int index, List<LauncherInterceptor> interceptorList, LaunchParam param) {
        return new RealInterceptorChain(index, interceptorList, param);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LauncherInterceptor.Chain
    public LaunchParam getLaunchParam() {
        return this.mLaunchParam;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LauncherInterceptor.Chain
    public LaunchResult proceed() {
        check(this.mIndex < this.mInterceptorList.size());
        RealInterceptorChain copy = copy(this.mIndex + 1, this.mInterceptorList, this.mLaunchParam);
        LauncherInterceptor launcherInterceptor = this.mInterceptorList.get(this.mIndex);
        if (launcherInterceptor != null) {
            return launcherInterceptor.intercept(copy);
        }
        throw new NullPointerException("interceptor returned null, intent:" + this.mLaunchParam.getIntent());
    }

    private static void check(boolean value) {
        check(value, "Check failed.");
    }

    private static void check(boolean value, String msg) {
        if (!value) {
            throw new IllegalStateException(msg);
        }
    }
}
