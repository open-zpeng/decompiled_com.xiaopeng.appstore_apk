package com.xiaopeng.appstore.bizcommon.logic.applauncher.lib;

import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Process;
import android.os.UserHandle;
import android.view.View;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.BizCommon;
import com.xiaopeng.appstore.bizcommon.R;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LauncherInterceptor;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
/* loaded from: classes2.dex */
public class ActivityLaunchInterceptor implements LauncherInterceptor {
    private static final String ACTIVITY_NOT_FOUND = ResUtils.getString(R.string.activity_not_found);
    private static final String TAG = "ActivityLaunchIntercept";
    private LauncherApps mLauncherApps;

    public static Bundle getActivityLaunchOptionsAsBundle(Context context) {
        ActivityOptions activityLaunchOptions = getActivityLaunchOptions(context);
        if (activityLaunchOptions == null) {
            return null;
        }
        return activityLaunchOptions.toBundle();
    }

    public static ActivityOptions getActivityLaunchOptions(Context context) {
        return ActivityOptions.makeCustomAnimation(context, R.anim.task_open_enter, R.anim.no_anim);
    }

    public static Rect getViewBounds(View v) {
        int[] iArr = new int[2];
        v.getLocationOnScreen(iArr);
        return new Rect(iArr[0], iArr[1], iArr[0] + v.getWidth(), iArr[1] + v.getHeight());
    }

    private LauncherApps getLauncherApps(Context context) {
        if (this.mLauncherApps == null) {
            this.mLauncherApps = (LauncherApps) context.getSystemService("launcherapps");
        }
        return this.mLauncherApps;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LauncherInterceptor
    public LaunchResult intercept(LauncherInterceptor.Chain chain) {
        LaunchParam launchParam = chain.getLaunchParam();
        Logger.t(TAG).i("intercept param=" + launchParam, new Object[0]);
        return startActivitySafely(null, BizCommon.getApp(), launchParam.getIntent(), launchParam.getUser());
    }

    private LaunchResult startActivitySafely(View v, Context context, Intent intent, UserHandle user) throws ActivityNotFoundException, SecurityException {
        intent.addFlags(270532608);
        Logger.t(TAG).d("startActivitySafely--->" + intent.toString());
        if (v != null) {
            intent.setSourceBounds(getViewBounds(v));
        }
        if (user != null) {
            try {
                if (!user.equals(Process.myUserHandle())) {
                    getLauncherApps(context).startMainActivity(intent.getComponent(), user, intent.getSourceBounds(), null);
                    return new LaunchSuccess();
                }
            } catch (ActivityNotFoundException | SecurityException e) {
                Logger.t(TAG).e("Unable to launch. intent=" + intent, e);
                return new LaunchFail(0, ACTIVITY_NOT_FOUND);
            }
        }
        Utils.getApp().getApplicationContext().startActivity(intent, null);
        return new LaunchSuccess();
    }
}
