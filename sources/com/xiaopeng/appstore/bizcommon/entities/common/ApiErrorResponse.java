package com.xiaopeng.appstore.bizcommon.entities.common;
/* loaded from: classes2.dex */
public class ApiErrorResponse<T> extends ApiResponse<T> {
    private final String mErrorMsg;

    public String getErrorMsg() {
        return this.mErrorMsg;
    }

    public ApiErrorResponse(String errorMsg) {
        this.mErrorMsg = errorMsg;
    }

    public String toString() {
        return "ApiErrorResponse{msg='" + this.mErrorMsg + "'}";
    }
}
