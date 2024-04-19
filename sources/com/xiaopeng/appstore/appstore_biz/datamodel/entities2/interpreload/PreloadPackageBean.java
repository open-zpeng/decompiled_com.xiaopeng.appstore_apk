package com.xiaopeng.appstore.appstore_biz.datamodel.entities2.interpreload;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
/* loaded from: classes2.dex */
public class PreloadPackageBean implements Serializable {
    @SerializedName("data")
    private Data data;
    @SerializedName("layout")
    private String layout;
    @SerializedName("title")
    private String title;

    public Data getData() {
        return this.data;
    }

    /* loaded from: classes2.dex */
    public static class Data {
        @SerializedName("id")
        private String id;
        @SerializedName("list")
        private List<PreloadItemBean> list;

        public List<PreloadItemBean> getList() {
            return this.list;
        }
    }
}
