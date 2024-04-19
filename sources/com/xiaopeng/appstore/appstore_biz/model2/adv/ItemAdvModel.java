package com.xiaopeng.appstore.appstore_biz.model2.adv;

import java.util.List;
import java.util.Objects;
/* loaded from: classes2.dex */
public class ItemAdvModel {
    private String mAppId;
    private String mAppName;
    private String mBriefDesc;
    private String mConfigUrl;
    private String mDataId;
    private String mDesc;
    private String mDownloadUrl;
    private String mIconUrl;
    private List<String> mImageList;
    private String mPackageName;
    private String mSize;
    private String mTitle;
    private long mVersionCode;
    private long mVersionDate;
    private String mVersionName;

    public String getDataId() {
        return this.mDataId;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDesc() {
        return this.mDesc;
    }

    public void setDesc(String desc) {
        this.mDesc = desc;
    }

    public List<String> getImageList() {
        return this.mImageList;
    }

    public void setImageList(List<String> imageList) {
        this.mImageList = imageList;
    }

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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ItemAdvModel itemAdvModel = (ItemAdvModel) o;
        return this.mVersionCode == itemAdvModel.mVersionCode && this.mVersionDate == itemAdvModel.mVersionDate && Objects.equals(this.mSize, itemAdvModel.mSize) && Objects.equals(this.mAppId, itemAdvModel.mAppId) && Objects.equals(this.mAppName, itemAdvModel.mAppName) && Objects.equals(this.mPackageName, itemAdvModel.mPackageName) && Objects.equals(this.mBriefDesc, itemAdvModel.mBriefDesc) && Objects.equals(this.mVersionName, itemAdvModel.mVersionName) && Objects.equals(this.mDownloadUrl, itemAdvModel.mDownloadUrl) && Objects.equals(this.mIconUrl, itemAdvModel.mIconUrl) && Objects.equals(this.mDataId, itemAdvModel.mDataId) && Objects.equals(this.mTitle, itemAdvModel.mTitle) && Objects.equals(this.mDesc, itemAdvModel.mDesc) && Objects.equals(this.mImageList, itemAdvModel.mImageList);
    }

    public int hashCode() {
        return Objects.hash(this.mAppId, this.mAppName, this.mPackageName, this.mBriefDesc, Long.valueOf(this.mVersionCode), this.mVersionName, Long.valueOf(this.mVersionDate), this.mDownloadUrl, this.mIconUrl, this.mSize, this.mDataId, this.mTitle, this.mDesc, this.mImageList);
    }
}
