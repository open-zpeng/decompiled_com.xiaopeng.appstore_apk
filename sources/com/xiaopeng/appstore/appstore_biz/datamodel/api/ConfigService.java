package com.xiaopeng.appstore.appstore_biz.datamodel.api;

import androidx.lifecycle.LiveData;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities.ActivityBean;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities.ConfigResponse;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities.HomeBean;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities.UpgradeBean;
import com.xiaopeng.appstore.bizcommon.entities.common.ApiResponse;
import com.xiaopeng.appstore.bizcommon.entities.common.XpApiResponse;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;
/* loaded from: classes2.dex */
public interface ConfigService {
    @GET("config/v2/config")
    LiveData<ApiResponse<XpApiResponse<List<ConfigResponse<ActivityBean>>>>> getActivityData(@Query("configName") String idStr);

    @GET("config/v2/config?configName=store_home")
    LiveData<ApiResponse<XpApiResponse<List<ConfigResponse<HomeBean>>>>> getHomeData();

    @GET("config/v2/config")
    LiveData<ApiResponse<XpApiResponse<List<ConfigResponse<UpgradeBean>>>>> getUpdateData(@Query("configName") String config);
}
