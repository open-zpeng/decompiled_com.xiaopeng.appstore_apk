package com.xiaopeng.appstore.common_ui;

import android.app.Application;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.DialogHelper;
/* loaded from: classes.dex */
public class CommonUI {
    private static Application sApplication;

    private CommonUI() {
    }

    public static void init(Application application, DialogHelper.IDialog dialog, DialogHelper.IToast toast) {
        sApplication = application;
        OpenAppMgr.get().init(application, dialog, toast);
    }

    public static Application getApplication() {
        Application application = sApplication;
        if (application != null) {
            return application;
        }
        throw new IllegalStateException("CommonUI not initialized, please call init first!");
    }
}
