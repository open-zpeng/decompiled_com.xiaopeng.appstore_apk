package com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home;

import com.google.gson.annotations.SerializedName;
import com.irdeto.securesdk.core.SSUtils;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.DependentAppBean;
import java.util.List;
/* loaded from: classes2.dex */
public class ItemBean {
    @SerializedName("content_type")
    public String contentType;
    @SerializedName("data")
    public Data data;
    @SerializedName("layout")
    public String layout;

    /* loaded from: classes2.dex */
    public static class Data {
        @SerializedName("ad_img")
        public List<String> adImage;
        @SerializedName("app_icons")
        public Icons appIcons;
        @SerializedName("config_md5")
        public String configMd5;
        @SerializedName("config_url")
        public String configUrl;
        @SerializedName("dependent_apps")
        public List<DependentAppBean> dependentApps;
        @SerializedName("desc")
        public String desc;
        @SerializedName("download_url")
        public String downloadUrl;
        @SerializedName("id")
        public String id;
        @SerializedName("md5")
        public String md5;
        @SerializedName("package_name")
        public String packageName;
        @SerializedName("size")
        public String size;
        @SerializedName("status")
        public String status;
        @SerializedName("title")
        public String title;
        @SerializedName(SSUtils.O000OOOo)
        public String versionCode;
    }

    /* loaded from: classes2.dex */
    public static class Icons {
        @SerializedName("large_icon")
        public String largeIcon;
        @SerializedName("small_icon")
        public String smallIcon;
    }
}
