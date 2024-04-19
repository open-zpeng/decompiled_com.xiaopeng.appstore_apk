package com.xiaopeng.appstore;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.LifecycleObserver;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appserver_common.AppServerHelper;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.ApplicationReadyHelper;
/* loaded from: classes2.dex */
public class StoreApplication extends Application implements LifecycleObserver {
    private static final boolean ENABLE_SERVER = false;
    private static final String TAG = "StoreApplication";
    private volatile AbstractApplicationInitializer mApplicationInitializer;

    @Override // android.content.ContextWrapper
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        this.mApplicationInitializer = AbstractApplicationInitializer.generateApplicationInitializer(this);
        this.mApplicationInitializer.init();
        ApplicationReadyHelper.notifyApplicationReady();
    }

    private void initAppServerAsync(final Runnable onFinish) {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.-$$Lambda$StoreApplication$MOMO2tO0itPOPJyeTfWDJST0X3g
            @Override // java.lang.Runnable
            public final void run() {
                StoreApplication.this.lambda$initAppServerAsync$1$StoreApplication(onFinish);
            }
        });
    }

    public /* synthetic */ void lambda$initAppServerAsync$1$StoreApplication(final Runnable runnable) {
        int appMode = ConfigReceiver.getAppMode(this);
        Logger.t(TAG).i("init:" + this + ", appMode:" + appMode, new Object[0]);
        AppServerHelper.init(this, appMode == 1);
        AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.-$$Lambda$StoreApplication$8PbdDC5sEKOhIO_yuUPJvicd4JU
            @Override // java.lang.Runnable
            public final void run() {
                StoreApplication.lambda$initAppServerAsync$0(runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initAppServerAsync$0(Runnable runnable) {
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override // android.app.Application
    public void onTerminate() {
        super.onTerminate();
        this.mApplicationInitializer.deInit();
    }
}
