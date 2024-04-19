package com.xiaopeng.appstore.appstore_biz.datamodel.api.mock;

import android.text.TextUtils;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.http.ApiConstants;
import com.xiaopeng.appstore.libcommon.utils.FileUtils;
import com.xiaopeng.libconfig.ipc.AccountConfig;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
/* loaded from: classes2.dex */
public class MockInterceptor implements Interceptor {
    private static final String TAG = "MockInterceptor";

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        String str;
        String uri = chain.request().url().uri().toString();
        if (uri.contains(ApiConstants.URL.API_URL_PRELOAD_APPS)) {
            str = "preload.json";
        } else if (uri.contains(ApiConstants.URL.API_URL_HOME)) {
            str = "home.json";
        } else if (uri.contains(ApiConstants.URL.API_URL_CHECK_UPDATE)) {
            str = "update.json";
        } else {
            str = uri.contains(ApiConstants.URL.API_URL_PRELOAD_INTER_APPS) ? "interPreload.json" : null;
        }
        String loadMockJSONFromAsset = TextUtils.isEmpty(str) ? "" : loadMockJSONFromAsset(str);
        if (!TextUtils.isEmpty(loadMockJSONFromAsset)) {
            Logger.t(TAG).d("intercept: url=" + uri);
            return new Response.Builder().request(chain.request()).code(200).message("success").protocol(Protocol.HTTP_1_1).body(ResponseBody.create(MediaType.parse(AccountConfig.VALUE_DEFAULT_TYPE_JSON), loadMockJSONFromAsset.getBytes(StandardCharsets.UTF_8))).addHeader("content-type", AccountConfig.VALUE_DEFAULT_TYPE_JSON).build();
        }
        return chain.proceed(chain.request());
    }

    private String loadMockJSONFromAsset(String jsonFileName) {
        return FileUtils.loadFileFromAsset("mock/" + jsonFileName);
    }
}
