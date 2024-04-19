package com.xiaopeng.appstore.appstore_biz.datamodel.entities2;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class AppDetailRequest {
    @SerializedName("param")
    private AppRequestContainer mParam;

    public void setParam(AppRequestContainer param) {
        this.mParam = param;
    }
}
