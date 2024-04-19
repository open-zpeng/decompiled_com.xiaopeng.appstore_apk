package com.xiaopeng.appstore.bizcommon.entities.common;

import android.text.TextUtils;
import android.util.Log;
import java.io.IOException;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class ApiResponse<T> {
    private static final String TAG = "ApiResponse";

    public static <T> ApiResponse<T> create(Throwable error) {
        String message = error.getMessage();
        if (TextUtils.isEmpty(message)) {
            message = "unknown error";
        }
        return new ApiErrorResponse(message);
    }

    public static <T> ApiResponse<T> create(Response<T> response) {
        if (response.isSuccessful()) {
            if (response.body() == null || response.code() == 204) {
                return new ApiEmptyResponse();
            }
            return new ApiSuccessResponse(response.body());
        }
        String str = null;
        if (response.errorBody() != null) {
            try {
                str = response.errorBody().string();
            } catch (IOException unused) {
                Log.e(TAG, "error while parsing response");
            }
        }
        if (str == null || str.trim().length() == 0) {
            str = response.message();
        }
        return new ApiErrorResponse(str);
    }
}
