package com.xiaopeng.appstore.appstore_biz.datamodel.entities2.interpreload;

import com.google.gson.annotations.SerializedName;
import com.irdeto.securesdk.core.SSUtils;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppIconData;
import java.io.Serializable;
import java.util.List;
/* loaded from: classes2.dex */
public class InterAppPreloadDetailData implements Serializable {
    @SerializedName("ad_img")
    private List<String> ad_img;
    @SerializedName("app_icons")
    private AppIconData mAppIconData;
    @SerializedName("id")
    private String mAppId;
    @SerializedName("config_md5")
    private String mConfigMd5;
    @SerializedName("config_url")
    private String mConfigUrl;
    @SerializedName("desc")
    private String mDesc;
    @SerializedName("download_url")
    private String mDownloadUrl;
    @SerializedName("package_name")
    private String mPackageName;
    @SerializedName("size")
    private String mSize;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("title")
    private String mTitle;
    @SerializedName(SSUtils.O000OOOo)
    private String mVersionCode;
    @SerializedName("md5")
    private String md5;

    public String getId() {
        return this.mAppId;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public AppIconData getIconData() {
        return this.mAppIconData;
    }

    public String getAppName() {
        return this.mTitle;
    }

    public String getMd5() {
        return this.md5;
    }

    public String getStatus() {
        return this.mStatus;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getVersionCode() {
        return this.mVersionCode;
    }

    public String getDownloadUrl() {
        return this.mDownloadUrl;
    }

    public String getConfigUrl() {
        return this.mConfigUrl;
    }

    public String getConfigMd5() {
        return this.mConfigMd5;
    }

    public String getSize() {
        return this.mSize;
    }

    public String getDesc() {
        return this.mDesc;
    }
}
