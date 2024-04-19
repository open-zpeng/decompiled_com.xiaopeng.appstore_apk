package com.xiaopeng.appstore.appstore_biz.model2;
/* loaded from: classes2.dex */
public class DependentAppModel {
    private String mAppId;
    private String mPackageName;
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
}
