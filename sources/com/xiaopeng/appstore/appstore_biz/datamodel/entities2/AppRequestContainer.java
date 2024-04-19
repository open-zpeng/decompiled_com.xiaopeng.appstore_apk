package com.xiaopeng.appstore.appstore_biz.datamodel.entities2;

import com.google.gson.annotations.SerializedName;
import com.irdeto.securesdk.core.SSUtils;
import com.xiaopeng.libconfig.ipc.AccountConfig;
import java.io.Serializable;
/* loaded from: classes2.dex */
public class AppRequestContainer implements Serializable {
    @SerializedName(AccountConfig.KEY_APP_ID)
    private String mAppId;
    @SerializedName("md5")
    private String mMd5;
    @SerializedName("package_name")
    private String mPackageName;
    @SerializedName("report_type")
    private int mReportType;
    @SerializedName(SSUtils.O000OOOo)
    private Long mVersionCode;
    @SerializedName("version_name")
    private String mVersionName;

    public void setVersionCode(Long versionCode) {
        this.mVersionCode = versionCode;
    }

    public void setVersionName(String versionName) {
        this.mVersionName = versionName;
    }

    public void setReportType(int reportType) {
        this.mReportType = reportType;
    }

    public void setMd5(String md5) {
        this.mMd5 = md5;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public void setVersionCode(long versionCode) {
        this.mVersionCode = Long.valueOf(versionCode);
    }

    public void setAppId(String appId) {
        this.mAppId = appId;
    }

    public String toString() {
        return "{p='" + this.mPackageName + "', ver=" + this.mVersionCode + '}';
    }
}
