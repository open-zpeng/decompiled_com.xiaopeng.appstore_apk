package com.xiaopeng.appstore.appstore_biz.datamodel.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;
/* loaded from: classes2.dex */
public class PackageBean {
    @SerializedName("itemList")
    private List<String> mItemList;
    @SerializedName("title")
    private String mTitle;

    public String getTitle() {
        return this.mTitle;
    }

    public List<String> getItemList() {
        return this.mItemList;
    }
}
