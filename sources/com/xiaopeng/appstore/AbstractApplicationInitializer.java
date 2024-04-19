package com.xiaopeng.appstore;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.applist_biz.AppListApplication;
import com.xiaopeng.appstore.appstore_biz.AppStoreApplication;
import com.xiaopeng.appstore.appstore_biz.logic.push.PushHandler;
import com.xiaopeng.appstore.bizcommon.BizCommon;
import com.xiaopeng.appstore.bizcommon.logic.AgreementManager;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.DialogHelper;
import com.xiaopeng.appstore.bizcommon.utils.DeviceUtils;
import com.xiaopeng.appstore.bizcommon.utils.EventTrackingHelperWrapper;
import com.xiaopeng.appstore.common_ui.CommonUI;
import com.xiaopeng.appstore.common_ui.XpengUIFactory;
import com.xiaopeng.appstore.libcommon.utils.SimpleLogFormatStrategy;
import com.xiaopeng.appstore.libcommon.utils.StartPerformanceUtils;
import com.xiaopeng.appstore.libcommon.utils.TraceUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.appstore.xpcommon.VuiEngineUtils;
import com.xiaopeng.appstore.xpcommon.XpLibUtils;
import com.xiaopeng.appstore.xpcommon.car.CarApiManager;
import com.xiaopeng.appstore.xpcommon.push.PushHelper;
import com.xiaopeng.appstore.xpcommon.push.PushedMessageBean;
import com.xiaopeng.xui.Xui;
import java.util.List;
/* loaded from: classes2.dex */
public abstract class AbstractApplicationInitializer implements LifecycleObserver {
    private static final String LOG_TAG = "AppStoLog";
    private static final String LOG_TAG_RES = "AppStoLog-Res";
    public static final String PROCESS_MAIN = "com.xiaopeng.appstore";
    public static final String PROCESS_RESOURCE = "com.xiaopeng.appstore:resource";
    private static final String TAG = "StoreApp";
    Application mApplication;

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onFirstActResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void onFirstActStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void onLastActPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onLastActStop() {
    }

    protected abstract void deInitAccountServices();

    protected abstract void deInitXuiManager();

    protected abstract List<String> getXpAppList();

    protected abstract void initAccountServices();

    protected abstract void initCheckUpdateManager();

    protected abstract void initXuiManager();

    public static AbstractApplicationInitializer generateApplicationInitializer(Application application) {
        AbstractApplicationInitializer storeApplicationInitializer;
        DeviceUtils.init(application);
        if (DeviceUtils.isInter()) {
            storeApplicationInitializer = new InterApplicationInitializer();
        } else {
            storeApplicationInitializer = new StoreApplicationInitializer();
        }
        storeApplicationInitializer.setApplication(application);
        return storeApplicationInitializer;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isMainProcess(Context context) {
        return Application.getProcessName().equals(context.getPackageName());
    }

    public void init() {
        StartPerformanceUtils.appOnCreateBegin();
        String processName = Application.getProcessName();
        TraceUtils.alwaysTraceBegin("Application_Logger");
        initLogger(processName);
        TraceUtils.alwaysTraceEnd();
        Logger.t(TAG).i("onCreate hashcode=%s, %s", Integer.valueOf(hashCode()), AppUtils.getVerDesc(this.mApplication));
        TraceUtils.alwaysTraceBegin("Application_Utils");
        Utils.init(this.mApplication);
        TraceUtils.alwaysTraceEnd();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        TraceUtils.alwaysTraceBegin("Application_BugHunter");
        initBugHunter();
        TraceUtils.alwaysTraceEnd();
        if (processName.equals("com.xiaopeng.appstore")) {
            DialogHelper.IDialog createDialog = XpengUIFactory.createDialog(this.mApplication);
            DialogHelper.IToast createToast = XpengUIFactory.createToast(this.mApplication);
            CommonUI.init(this.mApplication, createDialog, createToast);
            BizCommon.init(this.mApplication);
            CarApiManager.getInstance().connect();
            initXuiManager();
            AppListApplication.get().init(getXpAppList());
            AppStoreApplication.get().init(this.mApplication, createDialog, createToast);
            TraceUtils.alwaysTraceBegin("Application_Xui");
            initXui();
            TraceUtils.alwaysTraceEnd();
            TraceUtils.alwaysTraceBegin("Application_AppComponentManager");
            AppComponentManager.get().init(getXpAppList());
            TraceUtils.alwaysTraceEnd();
            initCheckUpdateManager();
            initAccountServices();
            EventTrackingHelperWrapper.registerIgOffListenerForEventTracker();
            VuiEngineUtils.setVuiConfig(this.mApplication, 3, "com.xiaopeng.appstore.VuiObserver");
        }
    }

    private void setApplication(Application application) {
        this.mApplication = application;
    }

    public void deInit() {
        deInitAccountServices();
        deInitXuiManager();
        AgreementManager.get().unRegisterAccountObserver();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initPush() {
        XpLibUtils.PushInit initPush = XpLibUtils.initPush(this.mApplication);
        initPush.registerPushListener(new PushHelper.OnPushMsgListener() { // from class: com.xiaopeng.appstore.AbstractApplicationInitializer.1
            @Override // com.xiaopeng.appstore.xpcommon.push.PushHelper.OnPushMsgListener
            public void onPushMsgReceived(PushedMessageBean msg) {
                PushHandler.handlePush(AbstractApplicationInitializer.this.mApplication, msg);
            }

            @Override // com.xiaopeng.appstore.xpcommon.push.PushHelper.OnPushMsgListener
            public void onPushCardClick(int sceneId) {
                PushHandler.handleClick(AbstractApplicationInitializer.this.mApplication, sceneId);
            }
        });
        initPush.setApplicationReady();
    }

    private void initBugHunter() {
        XpLibUtils.initBugHunter(this.mApplication);
    }

    private void initLogger(String tag) {
        if (tag.equals("com.xiaopeng.appstore")) {
            tag = LOG_TAG;
        } else if (tag.equals(PROCESS_RESOURCE)) {
            tag = LOG_TAG_RES;
        }
        Logger.addLogAdapter(new AndroidLogAdapter(SimpleLogFormatStrategy.newBuilder().tag(tag).showMethodInfo(false).build()));
    }

    private void initXui() {
        Xui.init(this.mApplication);
    }
}
