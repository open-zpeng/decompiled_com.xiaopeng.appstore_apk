package com.xiaopeng.appstore.bizcommon.entities;
/* loaded from: classes2.dex */
public class WebAppInfo {
    private String mAppId;
    private String mAppName;
    private String mBriefDesc;
    private String mConfigMd5;
    private String mConfigUrl;
    private String mDownloadUrl;
    private String mIconUrl;
    private boolean mIsSuspended;
    private String mMd5;
    private String mPackageName;
    private String mSize;
    private int mStatus;
    private long mVersionCode;
    private long mVersionDate;
    private String mVersionName;

    public String getAppId() {
        return this.mAppId;
    }

    public void setAppId(String appId) {
        this.mAppId = appId;
    }

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

    public String getBriefDesc() {
        return this.mBriefDesc;
    }

    public void setBriefDesc(String briefDesc) {
        this.mBriefDesc = briefDesc;
    }

    public long getVersionCode() {
        return this.mVersionCode;
    }

    public void setVersionCode(long versionCode) {
        this.mVersionCode = versionCode;
    }

    public String getVersionName() {
        return this.mVersionName;
    }

    public void setVersionName(String versionName) {
        this.mVersionName = versionName;
    }

    public long getVersionDate() {
        return this.mVersionDate;
    }

    public void setVersionDate(long versionDate) {
        this.mVersionDate = versionDate;
    }

    public String getDownloadUrl() {
        return this.mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.mDownloadUrl = downloadUrl;
    }

    public String getConfigUrl() {
        return this.mConfigUrl;
    }

    public void setConfigUrl(String configUrl) {
        this.mConfigUrl = configUrl;
    }

    public String getIconUrl() {
        return this.mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public String getSize() {
        return this.mSize;
    }

    public void setSize(String size) {
        this.mSize = size;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void setStatus(int status) {
        this.mStatus = status;
        this.mIsSuspended = status == 4;
    }

    public boolean isSuspended() {
        return this.mIsSuspended;
    }

    public String getMd5() {
        return this.mMd5;
    }

    public void setMd5(String md5) {
        this.mMd5 = md5;
    }

    public String getConfigMd5() {
        return this.mConfigMd5;
    }

    public void setConfigMd5(String configMd5) {
        this.mConfigMd5 = configMd5;
    }

    public String toString() {
        return String.format("WebAppInfo[id=%s,name=%s,pn=%s]", this.mAppId, this.mAppName, this.mPackageName);
    }
}
