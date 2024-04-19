package com.xiaopeng.appstore.appstore_biz.datamodel.entities2;

import com.google.gson.annotations.SerializedName;
import com.irdeto.securesdk.core.SSUtils;
/* loaded from: classes2.dex */
public class AdvAppData extends AdvData {
    @SerializedName("size")
    private String mAppSize;
    @SerializedName("config_url")
    private String mConfigUrl;
    @SerializedName("download_url")
    private String mDownloadUrl;
    @SerializedName("app_icons")
    private AppIconData mIconData;
    @SerializedName("md5")
    private String mMd5;
    @SerializedName("package_name")
    private String mPackageName;
    @SerializedName(SSUtils.O000OOOo)
    private String mVersionCode;

    public AppIconData getIconData() {
        return this.mIconData;
    }

    public String getAppSize() {
        return this.mAppSize;
    }

    public String getMd5() {
        return this.mMd5;
    }

    public String getPackageName() {
        return this.mPackageName;
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
}
