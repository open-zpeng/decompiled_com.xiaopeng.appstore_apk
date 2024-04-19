package com.xiaopeng.appstore.appstore_biz.datamodel.entities;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class ConfigResponse<T> {
    @SerializedName("configName")
    private String mConfigName;
    @SerializedName("value")
    private T mData;

    public String getConfigName() {
        return this.mConfigName;
    }

    public T getData() {
        return this.mData;
    }
}
