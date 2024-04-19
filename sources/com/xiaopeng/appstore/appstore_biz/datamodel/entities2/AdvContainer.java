package com.xiaopeng.appstore.appstore_biz.datamodel.entities2;

import com.google.gson.annotations.SerializedName;
@Deprecated
/* loaded from: classes2.dex */
public class AdvContainer {
    @SerializedName("data")
    private AdvData mAdvData;
    @SerializedName("content_type")
    private String mContentType;
    @SerializedName("layout")
    private String mLayout;

    public String getLayout() {
        return this.mLayout;
    }

    public void setLayout(String layout) {
        this.mLayout = layout;
    }

    public String getContentType() {
        return this.mContentType;
    }

    public void setContentType(String contentType) {
        this.mContentType = contentType;
    }

    public AdvData getAdvData() {
        return this.mAdvData;
    }

    public void setAdvData(AdvData advData) {
        this.mAdvData = advData;
    }
}
