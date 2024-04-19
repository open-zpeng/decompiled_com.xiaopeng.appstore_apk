package com.xiaopeng.appstore.appstore_biz.datamodel.api;

import androidx.lifecycle.LiveData;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities.ConfigResponse;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AdvContainer;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PageBean;
import com.xiaopeng.appstore.bizcommon.entities.common.ApiResponse;
import com.xiaopeng.appstore.bizcommon.entities.common.XpApiResponse;
import com.xiaopeng.appstore.bizcommon.http.ApiConstants;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
/* loaded from: classes2.dex */
public interface XpAdvService {
    @GET(ApiConstants.URL.API_URL_HOME)
    LiveData<ApiResponse<XpApiResponse<PageBean>>> getPage();

    @GET(ApiConstants.URL.API_URL_HOME)
    Call<ResponseBody> getPageCall();

    @GET(ApiConstants.URL.API_URL_HOME)
    Call<XpApiResponse<PageBean>> getPageDataCall();

    @GET("config/v2/config")
    LiveData<ApiResponse<XpApiResponse<List<ConfigResponse<AdvContainer>>>>> getSceneData(@Query("configName") String idStr);
}
