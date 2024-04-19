package com.xiaopeng.appstore.appstore_biz.datamodel.api;

import android.text.TextUtils;
import com.xiaopeng.appstore.bizcommon.http.ApiConstants;
import com.xiaopeng.appstore.libcommon.utils.FileUtils;
import com.xiaopeng.appstore.xpcommon.ApiEnvHelper;
import com.xiaopeng.libconfig.ipc.AccountConfig;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
/* loaded from: classes2.dex */
public class PreloadAppsInterceptor implements Interceptor {
    private static final String TAG = "PreloadAppsInterceptor";

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        if (chain.request().url().uri().toString().contains(ApiConstants.URL.API_URL_PRELOAD_APPS)) {
            String str = "";
            if (ApiEnvHelper.getHost().equals(ApiEnvHelper.APP_STORE_HOST_TEST)) {
                String loadFileFromAsset = TextUtils.isEmpty("mock/preload.json") ? "" : FileUtils.loadFileFromAsset("mock/preload.json");
                if (loadFileFromAsset != null) {
                    str = loadFileFromAsset;
                }
            }
            return new Response.Builder().request(chain.request()).code(200).message("success").protocol(Protocol.HTTP_1_1).body(ResponseBody.create(MediaType.parse(AccountConfig.VALUE_DEFAULT_TYPE_JSON), str.getBytes(StandardCharsets.UTF_8))).addHeader("content-type", AccountConfig.VALUE_DEFAULT_TYPE_JSON).build();
        }
        return chain.proceed(chain.request());
    }
}
