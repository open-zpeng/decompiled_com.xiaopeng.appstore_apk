package com.xiaopeng.appstore.bizcommon.logic.applauncher.lib;

import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class AppLauncher {
    private static final String TAG = "AppLauncher";
    private ActivityLaunchInterceptor mActivityLaunchInterceptor = new ActivityLaunchInterceptor();
    private List<LauncherInterceptor> mInterceptorList;

    public void addInterceptor(LauncherInterceptor interceptor) {
        addInterceptor(interceptor, false);
    }

    public void addInterceptor(LauncherInterceptor interceptor, boolean interceptFirst) {
        Logger.t(TAG).d("addInterceptor " + interceptor + " first:" + interceptFirst);
        if (this.mInterceptorList == null) {
            this.mInterceptorList = new ArrayList();
        }
        if (interceptFirst) {
            this.mInterceptorList.add(0, interceptor);
        } else {
            this.mInterceptorList.add(interceptor);
        }
    }

    public void removeInterceptor(LauncherInterceptor interceptor) {
        Logger.t(TAG).d("removeInterceptor " + interceptor);
        List<LauncherInterceptor> list = this.mInterceptorList;
        if (list != null) {
            list.remove(interceptor);
        }
    }

    private LaunchResult getResultWithInterceptChain(LaunchParam param) {
        ArrayList arrayList = new ArrayList();
        List<LauncherInterceptor> list = this.mInterceptorList;
        if (list != null && !list.isEmpty()) {
            arrayList.addAll(this.mInterceptorList);
        }
        arrayList.add(this.mActivityLaunchInterceptor);
        return new RealInterceptorChain(0, arrayList, param).proceed();
    }

    public LaunchResult launch(LaunchParam param) {
        Logger.t(TAG).d("launch " + param);
        return getResultWithInterceptChain(param);
    }
}
