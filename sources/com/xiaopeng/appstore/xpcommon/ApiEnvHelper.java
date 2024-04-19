package com.xiaopeng.appstore.xpcommon;

import com.xiaopeng.appstore.libcommon.utils.SPUtils;
/* loaded from: classes2.dex */
public class ApiEnvHelper {
    public static final String APP_STORE_HOST;
    public static final String APP_STORE_HOST_PRE;
    public static final String APP_STORE_HOST_TEST = "https://10.0.13.28:18553";
    private static final String KEY_MAIN_HOST = "main_host";
    public static String sHost;

    static {
        APP_STORE_HOST_PRE = CarUtils.isEURegion() ? "https://xmart-eu.deploy-test.xiaopeng.com/" : "https://xmart.deploy-test.xiaopeng.com/";
        String str = CarUtils.isEURegion() ? "https://xmart-eu.xiaopeng.com" : "https://xmart.xiaopeng.com";
        APP_STORE_HOST = str;
        sHost = SPUtils.getInstance().getString(KEY_MAIN_HOST, str);
    }

    public static String getHost() {
        return sHost;
    }

    public static void switchTest() {
        SPUtils.getInstance().put(KEY_MAIN_HOST, APP_STORE_HOST_TEST);
        sHost = APP_STORE_HOST_TEST;
    }

    public static void switchPre() {
        SPUtils sPUtils = SPUtils.getInstance();
        String str = APP_STORE_HOST_PRE;
        sPUtils.put(KEY_MAIN_HOST, str);
        sHost = str;
    }

    public static void resetEnv() {
        SPUtils sPUtils = SPUtils.getInstance();
        String str = APP_STORE_HOST;
        sPUtils.put(KEY_MAIN_HOST, str);
        sHost = str;
    }
}
