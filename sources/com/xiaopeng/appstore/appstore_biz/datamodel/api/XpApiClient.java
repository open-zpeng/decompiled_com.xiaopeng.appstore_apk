package com.xiaopeng.appstore.appstore_biz.datamodel.api;

import android.text.TextUtils;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;
import com.xiaopeng.appstore.bizcommon.http.ApiConstants;
import com.xiaopeng.appstore.bizcommon.http.LiveDataCallAdapterFactory;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.xpcommon.ApiEnvHelper;
import com.xiaopeng.appstore.xpcommon.CarUtils;
import com.xiaopeng.appstore.xpcommon.XpOkHttpClientGenerator;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/* loaded from: classes2.dex */
public class XpApiClient {
    private static final boolean MOCK_HTTP = false;
    private static final String TAG = "XpApiClient";
    private static OkHttpClient okHttpClientInstance;
    private static Retrofit retrofitInstance;
    private static XpAdvService sAdvService;
    private static XpAppService sAppService;
    private static CommonParamsProvider sCustomParamsProvider;

    /* loaded from: classes2.dex */
    public interface CommonParamsProvider {
        default String getModel(String originValue) {
            return null;
        }

        default String getSV(String originValue) {
            return null;
        }
    }

    public static XpAdvService getAdvService() {
        if (sAdvService == null) {
            Logger.t(TAG).d("getAdvService is null, init now.");
            sAdvService = (XpAdvService) initService(XpAdvService.class);
        }
        Printer t = Logger.t(TAG);
        StringBuilder append = new StringBuilder().append("getAdvService:");
        XpAdvService xpAdvService = sAdvService;
        t.i(append.append(xpAdvService != null ? Integer.valueOf(xpAdvService.hashCode()) : null).toString(), new Object[0]);
        return sAdvService;
    }

    public static XpAppService getAppService() {
        if (sAppService == null) {
            Logger.t(TAG).d("getAppService is null, init now.");
            sAppService = (XpAppService) initService(XpAppService.class);
        }
        Printer t = Logger.t(TAG);
        StringBuilder append = new StringBuilder().append("getAppService:");
        XpAppService xpAppService = sAppService;
        t.i(append.append(xpAppService != null ? Integer.valueOf(xpAppService.hashCode()) : null).toString(), new Object[0]);
        return sAppService;
    }

    public static void setCustomParamsProvider(CommonParamsProvider customParamsProvider) {
        sCustomParamsProvider = customParamsProvider;
    }

    public static <T> T initService(Class<T> clazz) {
        Logger.t(TAG).i("initService start: %s.", clazz.getSimpleName());
        T t = (T) getRetrofitInstance().create(clazz);
        Logger.t(TAG).i("initService end: %s(%s).", clazz.getSimpleName(), t);
        return t;
    }

    private static Retrofit getRetrofitInstance() {
        if (retrofitInstance == null) {
            synchronized (XpApiClient.class) {
                if (retrofitInstance == null) {
                    retrofitInstance = generateRetrofitInstance();
                }
            }
        }
        return retrofitInstance;
    }

    private static Retrofit generateRetrofitInstance() {
        return new Retrofit.Builder().baseUrl(ApiEnvHelper.getHost()).callbackExecutor(AppExecutors.get().backgroundThread()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(new LiveDataCallAdapterFactory()).client(getOkHttpClientInstance()).build();
    }

    public static void reset() {
        synchronized (XpApiClient.class) {
            retrofitInstance = generateRetrofitInstance();
            sAdvService = (XpAdvService) initService(XpAdvService.class);
            sAppService = (XpAppService) initService(XpAppService.class);
        }
    }

    public static void release() {
        retrofitInstance = null;
        okHttpClientInstance = null;
        sAppService = null;
        sAdvService = null;
    }

    private static OkHttpClient getOkHttpClientInstance() {
        if (okHttpClientInstance == null) {
            synchronized (XpApiClient.class) {
                if (okHttpClientInstance == null) {
                    okHttpClientInstance = getBuilder().build();
                }
            }
        }
        return okHttpClientInstance;
    }

    public static OkHttpClient.Builder getBuilder() {
        return XpOkHttpClientGenerator.getBuilder(new XpOkHttpClientGenerator.CommonParamsProvider() { // from class: com.xiaopeng.appstore.appstore_biz.datamodel.api.XpApiClient.1
            @Override // com.xiaopeng.appstore.xpcommon.XpOkHttpClientGenerator.CommonParamsProvider
            protected String getAppId() {
                return ApiConstants.APP_ID;
            }

            @Override // com.xiaopeng.appstore.xpcommon.XpOkHttpClientGenerator.CommonParamsProvider
            protected String getAppSecret() {
                return ApiConstants.getSecret();
            }

            @Override // com.xiaopeng.appstore.xpcommon.XpOkHttpClientGenerator.CommonParamsProvider
            protected String getModel() {
                if (XpApiClient.sCustomParamsProvider != null) {
                    String model = XpApiClient.sCustomParamsProvider.getModel(super.getModel());
                    if (!TextUtils.isEmpty(model)) {
                        return model;
                    }
                }
                String model2 = super.getModel();
                return model2.startsWith(CarUtils.CAR_TYPE_D20) ? "D21" : model2;
            }

            @Override // com.xiaopeng.appstore.xpcommon.XpOkHttpClientGenerator.CommonParamsProvider
            protected String getSV() {
                if (XpApiClient.sCustomParamsProvider != null) {
                    String sv = XpApiClient.sCustomParamsProvider.getSV(super.getSV());
                    if (!TextUtils.isEmpty(sv)) {
                        return sv;
                    }
                }
                return super.getSV();
            }
        });
    }
}
