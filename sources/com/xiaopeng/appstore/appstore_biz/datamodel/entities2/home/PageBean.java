package com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home;

import com.google.gson.annotations.SerializedName;
import java.util.List;
/* loaded from: classes2.dex */
public class PageBean {
    @SerializedName("content_type")
    public String contentType;
    @SerializedName("data")
    public Data data;
    @SerializedName("layout")
    public String layout;

    /* loaded from: classes2.dex */
    public static class Data {
        @SerializedName("ad_img")
        public List<String> adImg;
        @SerializedName("desc")
        public String desc;
        @SerializedName("id")
        public String id;
        @SerializedName("list")
        public List<PackageBean> list;
    }
}
