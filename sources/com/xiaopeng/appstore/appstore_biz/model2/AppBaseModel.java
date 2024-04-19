package com.xiaopeng.appstore.appstore_biz.model2;

import java.util.List;
import java.util.Objects;
/* loaded from: classes2.dex */
public class AppBaseModel {
    private String mAppId;
    private String mAppName;
    private String mBriefDesc;
    private String mConfigMd5;
    private String mConfigUrl;
    private List<DependentAppModel> mDependentApps;
    private String mDownloadUrl;
    private String mIconUrl;
    private boolean mIsSuspended;
    private String mMd5;
    private String mPackageName;
    private String mSize;
    private int mStatus;
    private long mVersionCode;
    private long mVersionDate;
    private String mVersionDesc;
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

    public List<DependentAppModel> getDependentApps() {
        return this.mDependentApps;
    }

    public void setDependentApps(List<DependentAppModel> dependentApps) {
        this.mDependentApps = dependentApps;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void setStatus(int status) {
        this.mStatus = status;
        this.mIsSuspended = status == 4 || status == 6;
    }

    public void setVersionDesc(String versionDesc) {
        this.mVersionDesc = versionDesc;
    }

    public String getVersionDesc() {
        return this.mVersionDesc;
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppBaseModel appBaseModel = (AppBaseModel) o;
        return this.mVersionCode == appBaseModel.mVersionCode && this.mVersionDate == appBaseModel.mVersionDate && Objects.equals(this.mSize, appBaseModel.mSize) && Objects.equals(this.mAppId, appBaseModel.mAppId) && Objects.equals(this.mAppName, appBaseModel.mAppName) && Objects.equals(this.mPackageName, appBaseModel.mPackageName) && Objects.equals(this.mBriefDesc, appBaseModel.mBriefDesc) && Objects.equals(this.mVersionName, appBaseModel.mVersionName) && Objects.equals(this.mDownloadUrl, appBaseModel.mDownloadUrl) && Objects.equals(this.mIconUrl, appBaseModel.mIconUrl) && this.mStatus == appBaseModel.mStatus;
    }

    public int hashCode() {
        return Objects.hash(this.mAppId, this.mAppName, this.mPackageName, this.mBriefDesc, Long.valueOf(this.mVersionCode), this.mVersionName, Long.valueOf(this.mVersionDate), this.mDownloadUrl, this.mIconUrl, this.mSize, Integer.valueOf(this.mStatus));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AppBaseModel{");
        sb.append("mAppId='").append(this.mAppId).append('\'');
        sb.append(", mAppName='").append(this.mAppName).append('\'');
        sb.append(", mPackageName='").append(this.mPackageName).append('\'');
        sb.append(", mBriefDesc='").append(this.mBriefDesc).append('\'');
        sb.append(", mVersionCode=").append(this.mVersionCode);
        sb.append(", mVersionName='").append(this.mVersionName).append('\'');
        sb.append(", mVersionDate=").append(this.mVersionDate);
        sb.append(", mDownloadUrl='").append(this.mDownloadUrl).append('\'');
        sb.append(", mConfigUrl='").append(this.mConfigUrl).append('\'');
        sb.append(", mIconUrl='").append(this.mIconUrl).append('\'');
        sb.append(", mSize='").append(this.mSize).append('\'');
        sb.append(", mVersionDesc'").append(this.mVersionDesc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
