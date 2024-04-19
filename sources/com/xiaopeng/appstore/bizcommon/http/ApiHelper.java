package com.xiaopeng.appstore.bizcommon.http;

import com.xiaopeng.appstore.bizcommon.entities.common.ApiSuccessResponse;
import com.xiaopeng.appstore.bizcommon.entities.common.XpApiResponse;
/* loaded from: classes2.dex */
public class ApiHelper {
    public static <T> T getResponseData(ApiSuccessResponse<XpApiResponse<T>> apiResponse) {
        XpApiResponse<T> body;
        if (apiResponse == null || (body = apiResponse.getBody()) == null || !body.isSuccessful()) {
            return null;
        }
        return body.data;
    }

    public static <T> T getXpResponseData(XpApiResponse<T> apiResponse) {
        if (apiResponse == null || !apiResponse.isSuccessful()) {
            return null;
        }
        return apiResponse.data;
    }
}
