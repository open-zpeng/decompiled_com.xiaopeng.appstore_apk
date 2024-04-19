package com.xiaopeng.appstore.xpcommon.eventtracking;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class AppInstalledEvent {
    @SerializedName("name")
    private String mAppName;
    @SerializedName("result")
    private String mPackageName;

    public String getAppName() {
        return this.mAppName;
    }

    public void setAppName(String appName) {
        this.mAppName = appName;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }
}
