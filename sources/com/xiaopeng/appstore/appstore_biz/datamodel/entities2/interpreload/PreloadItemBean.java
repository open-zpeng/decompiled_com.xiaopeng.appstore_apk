package com.xiaopeng.appstore.appstore_biz.datamodel.entities2.interpreload;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
/* loaded from: classes2.dex */
public class PreloadItemBean implements Serializable {
    @SerializedName("content_type")
    private String content_type;
    @SerializedName("data")
    private InterAppPreloadDetailData data;

    public InterAppPreloadDetailData getData() {
        return this.data;
    }
}
