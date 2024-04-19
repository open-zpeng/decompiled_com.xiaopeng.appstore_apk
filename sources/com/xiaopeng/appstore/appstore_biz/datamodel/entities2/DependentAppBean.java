package com.xiaopeng.appstore.appstore_biz.datamodel.entities2;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.libconfig.ipc.AccountConfig;
import com.xiaopeng.speech.vui.constants.VuiConstants;
/* loaded from: classes2.dex */
public class DependentAppBean {
    @SerializedName(AccountConfig.KEY_APP_ID)
    private String mAppId;
    @SerializedName("md5")
    private String mMd5;
    @SerializedName("package_name")
    private String mPackageName;
    @SerializedName(VuiConstants.ELEMENT_TYPE)
    private int mType;

    public String getPackageName() {
        return this.mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public String getAppId() {
        return this.mAppId;
    }

    public void setAppId(String appId) {
        this.mAppId = appId;
    }

    public int getType() {
        return this.mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public String getMd5() {
        return this.mMd5;
    }

    public void setMd5(String md5) {
        this.mMd5 = md5;
    }
}
