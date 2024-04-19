package com.xiaopeng.appstore.appstore_biz.datamodel.api;

import android.text.TextUtils;
import androidx.lifecycle.LiveData;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailData;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailListRequest;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailRequest;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppListRequest;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppRequestContainer;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.SubmitRequest;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.UpdateRespContainer;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.UploadAppRequest;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.interpreload.InterUpdateBean;
import com.xiaopeng.appstore.bizcommon.entities.common.ApiResponse;
import com.xiaopeng.appstore.bizcommon.entities.common.XpApiResponse;
import com.xiaopeng.appstore.bizcommon.http.ApiConstants;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
/* loaded from: classes2.dex */
public interface XpAppService {
    @POST(ApiConstants.URL.API_URL_DETAILS)
    Call<XpApiResponse<ArrayList<AppDetailData>>> checkAppDetails(@Body AppDetailListRequest request);

    @POST(ApiConstants.URL.API_URL_DETAIL)
    LiveData<ApiResponse<XpApiResponse<AppDetailData>>> getAppDetail(@Body AppDetailRequest request);

    @POST(ApiConstants.URL.API_URL_DETAIL)
    Call<ResponseBody> getAppDetailCall(@Body AppDetailRequest request);

    @POST(ApiConstants.URL.API_URL_DETAILS)
    LiveData<ApiResponse<XpApiResponse<ArrayList<AppDetailData>>>> getAppDetails(@Body AppDetailListRequest request);

    @POST(ApiConstants.URL.API_URL_DETAIL)
    Call<XpApiResponse<AppDetailData>> getDetail(@Body AppDetailRequest request);

    @GET(ApiConstants.URL.API_URL_PRELOAD_APPS)
    Call<XpApiResponse<UpdateRespContainer>> getPreloadAppList();

    @GET(ApiConstants.URL.API_URL_PRELOAD_INTER_APPS)
    Call<XpApiResponse<InterUpdateBean>> getPreloadInterAppList();

    @POST(ApiConstants.URL.API_URL_CHECK_UPDATE)
    Call<XpApiResponse<UpdateRespContainer>> getUpdateCall(@Body AppListRequest request);

    @POST(ApiConstants.URL.API_URL_CHECK_UPDATE)
    Call<ResponseBody> getUpdateCallResp(@Body AppListRequest request);

    @POST(ApiConstants.URL.API_URL_SUBMIT)
    Call<Void> submit(@Body SubmitRequest submitRequest);

    @POST(ApiConstants.URL.API_URL_UPLOAD_APP)
    Call<XpApiResponse<Integer>> uploadApps(@Body UploadAppRequest request);

    static void downloadSubmit(XpAppService service, String packageName, String versionName, long versionCode) {
        submit(service, packageName, versionName, versionCode, 1);
    }

    static void installFailedSubmit(XpAppService service, String packageName, String versionName, long versionCode) {
        submit(service, packageName, versionName, versionCode, 2);
    }

    static void submit(XpAppService service, String packageName, String versionName, long versionCode, int reportType) {
        if (service != null && !TextUtils.isEmpty(packageName) && !TextUtils.isEmpty(versionName) && versionCode > 0) {
            SubmitRequest submitRequest = new SubmitRequest();
            ArrayList arrayList = new ArrayList(1);
            AppRequestContainer appRequestContainer = new AppRequestContainer();
            appRequestContainer.setPackageName(packageName);
            appRequestContainer.setVersionCode(versionCode);
            appRequestContainer.setVersionName(versionName);
            appRequestContainer.setReportType(reportType);
            arrayList.add(appRequestContainer);
            submitRequest.setParams(arrayList);
            try {
                service.submit(submitRequest).execute();
                return;
            } catch (IOException unused) {
                return;
            }
        }
        Logger.t("XpAppService").i("Some param illegal skip call api, Service = %1$s, package name = %2$s, version name = %3$s, version code %4$s, report type = %5$s", service, packageName, versionName, Long.valueOf(versionCode), Integer.valueOf(reportType));
    }
}
