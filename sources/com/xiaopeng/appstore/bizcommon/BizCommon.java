package com.xiaopeng.appstore.bizcommon;

import android.content.Context;
/* loaded from: classes2.dex */
public class BizCommon {
    private static Context sApp;

    private BizCommon() {
    }

    public static void init(Context app) {
        sApp = app.getApplicationContext();
    }

    public static Context getApp() {
        return sApp;
    }
}
