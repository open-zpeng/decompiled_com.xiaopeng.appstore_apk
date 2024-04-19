package com.xiaopeng.appstore.bizcommon.logic.applauncher;

import android.content.ComponentName;
import android.text.TextUtils;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.BizCommon;
import com.xiaopeng.appstore.bizcommon.R;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LaunchFail;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LaunchParam;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LaunchResult;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LauncherInterceptor;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.storeprovider.ResourceProviderContract;
import java.util.Map;
import java.util.Optional;
/* loaded from: classes2.dex */
public class SuspendLaunchInterceptor implements LauncherInterceptor {
    public static final int INTERCEPTOR_TYPE_FORCE_UPDATE = 2;
    public static final int INTERCEPTOR_TYPE_FORCE_UPDATING = 3;
    public static final int INTERCEPTOR_TYPE_SUSPEND = 1;
    private static final String SUSPEND_APP = ResUtils.getString(R.string.app_suspend);
    private static final String TAG = "SuspendLaunchInterceptor";
    private Map<String, Integer> mPackageStateMap;

    /* loaded from: classes2.dex */
    public static class SuspendLaunchResult extends LaunchFail {
        public SuspendLaunchResult(int code, String msg) {
            super(code, msg);
        }
    }

    public void setPackageStates(Map<String, Integer> states) {
        this.mPackageStateMap = states;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LauncherInterceptor
    public LaunchResult intercept(LauncherInterceptor.Chain chain) {
        ComponentName component;
        LaunchParam launchParam = chain.getLaunchParam();
        Logger.t(TAG).d("intercept param:" + launchParam);
        Map<String, Integer> map = this.mPackageStateMap;
        if (map != null && !map.isEmpty() && (component = launchParam.getIntent().getComponent()) != null) {
            String packageName = component.getPackageName();
            if (!TextUtils.isEmpty(packageName)) {
                int intValue = ((Integer) Optional.ofNullable(this.mPackageStateMap.get(packageName)).orElse(-1)).intValue();
                if (intValue == 1001) {
                    return new SuspendLaunchResult(1, SUSPEND_APP);
                }
                if (intValue == 1051) {
                    if (queryIsAssembling(packageName)) {
                        return new SuspendLaunchResult(3, SUSPEND_APP);
                    }
                    return new SuspendLaunchResult(2, SUSPEND_APP);
                }
            }
        }
        Logger.t(TAG).d("intercept ignore, param:" + launchParam);
        return chain.proceed();
    }

    private boolean queryIsAssembling(String packageName) {
        return ResourceProviderContract.queryIsAssembling(BizCommon.getApp(), packageName, 1000);
    }
}
