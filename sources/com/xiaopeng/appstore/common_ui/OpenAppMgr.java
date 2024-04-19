package com.xiaopeng.appstore.common_ui;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.ArraySet;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.AppStateContract;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.AppLauncherMgr;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.DialogHelper;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.SuspendLaunchInterceptor;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LaunchFail;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LaunchParam;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LaunchResult;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.storeprovider.ResourceProviderContract;
import com.xiaopeng.xui.app.XToast;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
/* loaded from: classes.dex */
public class OpenAppMgr implements DefaultLifecycleObserver {
    private static final int OPEN_APP_TIMEOUT = 8000;
    private static final String TAG = "OpenAppMgr";
    private DialogHelper.IDialog mDialog;
    private DialogHelper mDialogHelper;
    private Handler mMainHandler;
    private final Set<AppComponentManager.OnAppOpenCallback> mOnAppOpenCallbackList = new ArraySet();
    private final Map<String, Timer> mOpeningAppList = new HashMap();
    private DialogHelper.IToast mToast;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class SingletonHolder {
        static final OpenAppMgr sInstance = new OpenAppMgr();

        SingletonHolder() {
        }
    }

    public static OpenAppMgr get() {
        return SingletonHolder.sInstance;
    }

    public void init(Application application, DialogHelper.IDialog dialog, DialogHelper.IToast toast) {
        AppStateContract.Cache.get().init(application.getContentResolver());
        AppLauncherMgr.get();
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mDialog = dialog;
        this.mToast = toast;
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public void onStop(LifecycleOwner owner) {
        DialogHelper dialogHelper = this.mDialogHelper;
        if (dialogHelper != null) {
            dialogHelper.dismissDialog();
        }
        clearOpenAppTask();
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public void onDestroy(LifecycleOwner owner) {
        this.mOnAppOpenCallbackList.clear();
    }

    private void runOnUiThread(Runnable action) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            action.run();
        } else {
            this.mMainHandler.post(action);
        }
    }

    private void toastError(final String msg) {
        runOnUiThread(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$OpenAppMgr$9HYngq1pEHFVxpDZX8kwuX9Qag8
            @Override // java.lang.Runnable
            public final void run() {
                XToast.show(msg, 0, XuiMgrHelper.get().getShareId());
            }
        });
    }

    public void addOnAppOpenCallback(AppComponentManager.OnAppOpenCallback callback) {
        this.mOnAppOpenCallbackList.add(callback);
    }

    public void removeOnAppOpenCallback(AppComponentManager.OnAppOpenCallback callback) {
        this.mOnAppOpenCallbackList.remove(callback);
    }

    public boolean open(Context context, String packageName, String label) {
        boolean z = false;
        if (TextUtils.isEmpty(packageName)) {
            Logger.t(TAG).w("open app warning: packageName is null.", new Object[0]);
            return false;
        }
        dispatchAppOpen(packageName);
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntentForPackage == null) {
            launchIntentForPackage = AppComponentManager.get().getMediaIntentForPackage(packageName);
        }
        if (launchIntentForPackage != null) {
            launchIntentForPackage.setPackage(null);
            launchIntentForPackage.addFlags(268435456);
            LaunchParam launchParam = new LaunchParam(launchIntentForPackage, null, label);
            LaunchResult launch = launch(context, launchParam);
            if (launch.isSuccess()) {
                z = true;
            } else {
                Logger.t(TAG).e("Unable to launch. param=" + launchParam + " result=" + launch, new Object[0]);
            }
            dispatchAppOpenFinish(packageName);
            return z;
        }
        Logger.t(TAG).e("Unable to launch app(%s), due to no LaunchIntent or Media Intent.", packageName);
        dispatchAppOpenFinish(packageName);
        return false;
    }

    public LaunchResult launch(final Context context, final LaunchParam param) {
        LaunchResult launch = AppLauncherMgr.get().launch(param);
        if (launch instanceof LaunchFail) {
            final LaunchFail launchFail = (LaunchFail) launch;
            runOnUiThread(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$OpenAppMgr$Z2_Kp0zcNIxzVEUlfTtrKYssvhs
                @Override // java.lang.Runnable
                public final void run() {
                    OpenAppMgr.this.lambda$launch$1$OpenAppMgr(context, launchFail, param);
                }
            });
        } else {
            ComponentName component = param.getIntent().getComponent();
            if (component != null) {
                tryToast(context, component.getPackageName());
            }
        }
        return launch;
    }

    private void clearOpenAppTask() {
        if (this.mOpeningAppList.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Timer> entry : this.mOpeningAppList.entrySet()) {
            String key = entry.getKey();
            Logger.t(TAG).d("clearOpenAppTask, dispatchAppOpenFinish, pn=%s.", key);
            dispatchAppOpenFinish(key);
        }
        this.mOpeningAppList.clear();
    }

    private void dispatchAppOpen(final String packageName) {
        Logger.t(TAG).d("dispatchAppOpen pn=%s.", packageName);
        Set<AppComponentManager.OnAppOpenCallback> set = this.mOnAppOpenCallbackList;
        if (set != null) {
            for (AppComponentManager.OnAppOpenCallback onAppOpenCallback : set) {
                onAppOpenCallback.onAppOpen(packageName);
            }
        }
        Timer timer = this.mOpeningAppList.get(packageName);
        if (timer != null) {
            timer.cancel();
        }
        Timer timer2 = new Timer(packageName);
        timer2.schedule(new TimerTask() { // from class: com.xiaopeng.appstore.common_ui.OpenAppMgr.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                Logger.t(OpenAppMgr.TAG).d("Open app timeout, dispatchAppOpenFinish pn=%s.", packageName);
                OpenAppMgr.this.dispatchAppOpenFinish(packageName);
            }
        }, 8000L);
        this.mOpeningAppList.put(packageName, timer2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchAppOpenFinish(String packageName) {
        Timer timer = this.mOpeningAppList.get(packageName);
        if (timer != null) {
            timer.cancel();
        }
        this.mOpeningAppList.remove(packageName);
        Set<AppComponentManager.OnAppOpenCallback> set = this.mOnAppOpenCallbackList;
        if (set != null) {
            for (AppComponentManager.OnAppOpenCallback onAppOpenCallback : set) {
                onAppOpenCallback.onAppOpenFinish(packageName);
            }
        }
    }

    private void tryToast(final Context context, final String pn) {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$OpenAppMgr$ScSrsF2cuwhbtg_ofwMXXZtc8UU
            @Override // java.lang.Runnable
            public final void run() {
                OpenAppMgr.lambda$tryToast$3(context, pn);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$tryToast$3(Context context, String str) {
        if (ResourceProviderContract.queryIsAssembling(context, str, 1000)) {
            Logger.t(TAG).i("Opening assembling app:" + str, new Object[0]);
            AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$OpenAppMgr$XT8KxZIDip9M-4d7Ye2aKEjHNjc
                @Override // java.lang.Runnable
                public final void run() {
                    XToast.show(ResUtils.getString(R.string.open_updating_app_toast), 0, XuiMgrHelper.get().getShareId());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleLaunchFail */
    public void lambda$launch$1$OpenAppMgr(Context context, LaunchFail launchFail, LaunchParam param) {
        if (launchFail instanceof SuspendLaunchInterceptor.SuspendLaunchResult) {
            Logger.t(TAG).i("handleLaunchFail, suspend result, " + launchFail, new Object[0]);
            if (this.mDialogHelper == null) {
                this.mDialogHelper = new DialogHelper(this.mDialog, this.mToast);
            }
            SuspendLaunchInterceptor.SuspendLaunchResult suspendLaunchResult = (SuspendLaunchInterceptor.SuspendLaunchResult) launchFail;
            DialogHelper.DialogType dialogType = DialogHelper.DialogType.NONE;
            if (suspendLaunchResult.getCode() == 1) {
                dialogType = DialogHelper.DialogType.SUSPEND;
            } else if (suspendLaunchResult.getCode() == 2) {
                dialogType = DialogHelper.DialogType.FORCE_UPDATE_GO_APPSTORE;
            } else if (suspendLaunchResult.getCode() == 3) {
                dialogType = DialogHelper.DialogType.FORCE_UPDATING;
            }
            this.mDialogHelper.show(context.getApplicationContext(), param.getPackageName(), param.getAppName(), dialogType, XuiMgrHelper.get().getShareId());
            return;
        }
        Logger.t(TAG).i("handleLaunchFail, unknown fail, " + launchFail, new Object[0]);
        toastError(launchFail.getMsg());
    }
}
