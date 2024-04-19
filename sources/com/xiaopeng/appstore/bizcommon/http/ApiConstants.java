package com.xiaopeng.appstore.bizcommon.http;

import com.xiaopeng.appstore.xpcommon.ApiEnvHelper;
/* loaded from: classes2.dex */
public class ApiConstants {
    static final String ADI_SECRET_OFFICIAL = "CRr27OSurtPYhPLQ";
    static final String API_SECRET = "fCzH8UrJv1sxb4fZ";
    public static final String APP_ID = "xp_xui_app_store";

    /* loaded from: classes2.dex */
    public static final class URL {
        public static final String API_URL_CHECK_UPDATE = "app-store/v1/app/checkUpdate";
        public static final String API_URL_DETAIL = "app-store/v1/app/detail";
        public static final String API_URL_DETAILS = "app-store/v1/app/details";
        public static final String API_URL_DOWNLOAD_CONFIG = "";
        public static final String API_URL_HOME = "app-store/v1/item/homePage";
        public static final String API_URL_PRELOAD_APPS = "app-store/v1/app/preloaded";
        public static final String API_URL_PRELOAD_INTER_APPS = "app-store/v1/item/preloadList";
        public static final String API_URL_SUBMIT = "app-store/v1/report/submit";
        public static final String API_URL_UPLOAD_APP = "app-store/v1/report/status";
    }

    public static String getSecret() {
        return ApiEnvHelper.APP_STORE_HOST.equals(ApiEnvHelper.sHost) ? ADI_SECRET_OFFICIAL : API_SECRET;
    }
}
