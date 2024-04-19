package com.xiaopeng.appstore.bizcommon.entities.common;
/* loaded from: classes2.dex */
public class ApiSuccessResponse<T> extends ApiResponse<T> {
    private final T mBody;

    public ApiSuccessResponse(T body) {
        this.mBody = body;
    }

    public T getBody() {
        return this.mBody;
    }

    public String toString() {
        return "ApiSuccessResponse{body=" + this.mBody + '}';
    }
}
