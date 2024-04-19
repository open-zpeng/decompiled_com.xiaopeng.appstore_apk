package com.xiaopeng.appstore.appstore_biz.datamodel.entities2.interpreload;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
/* loaded from: classes2.dex */
public class InterUpdateBean implements Serializable {
    @SerializedName("content_type")
    private String content_type;
    @SerializedName("data")
    private Data data;
    @SerializedName("layout")
    private String layout;

    public Data getData() {
        return this.data;
    }

    /* loaded from: classes2.dex */
    public static class Data {
        @SerializedName("desc")
        private String desc;
        @SerializedName("id")
        private String id;
        @SerializedName("list")
        private List<PreloadPackageBean> list;
        @SerializedName("title")
        private String title;

        public List<PreloadPackageBean> getList() {
            return this.list;
        }
    }
}
