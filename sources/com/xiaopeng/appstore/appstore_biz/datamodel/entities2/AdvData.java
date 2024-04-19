package com.xiaopeng.appstore.appstore_biz.datamodel.entities2;

import com.google.gson.annotations.SerializedName;
import java.util.List;
/* loaded from: classes2.dex */
public class AdvData {
    @SerializedName("ad_img")
    protected List<String> mAdvImages;
    @SerializedName("desc")
    protected String mDesc;
    @SerializedName("id")
    protected String mId;
    @SerializedName("title")
    protected String mTitle;

    public String getId() {
        return this.mId;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getDesc() {
        return this.mDesc;
    }

    public List<String> getAdvImages() {
        return this.mAdvImages;
    }
}
