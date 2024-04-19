package com.xiaopeng.appstore.appstore_biz;

import android.content.Context;
import com.xiaopeng.appstore.appstore_biz.router.AppStoreProviderImpl;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.DialogHelper;
import com.xiaopeng.appstore.bizcommon.router.RouterConstants;
import com.xiaopeng.appstore.bizcommon.router.RouterManager;
/* loaded from: classes2.dex */
public class AppStoreApplication {
    public DialogHelper.IDialog dialog;
    public DialogHelper.IToast toast;

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        static final AppStoreApplication sInstance = new AppStoreApplication();

        private SingletonHolder() {
        }
    }

    public static AppStoreApplication get() {
        return SingletonHolder.sInstance;
    }

    private AppStoreApplication() {
    }

    public void init(Context application, DialogHelper.IDialog dialog, DialogHelper.IToast toast) {
        RouterManager.get().registerModule(RouterConstants.AppStoreProviderPaths.APP_STORE_MAIN_PROVIDER, new AppStoreProviderImpl());
        this.dialog = dialog;
        this.toast = toast;
    }
}
