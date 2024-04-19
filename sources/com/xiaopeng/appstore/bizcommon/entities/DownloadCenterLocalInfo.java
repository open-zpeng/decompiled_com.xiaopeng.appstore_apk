package com.xiaopeng.appstore.bizcommon.entities;
/* loaded from: classes2.dex */
public class DownloadCenterLocalInfo {
    private String mAppLabel;
    private String mConfigUrl;
    private int mDownloadId;
    private String mDownloadUrl;
    private String mIconUrl;
    private int mId;
    private String mPackageName;
    private long mTotalBytes;

    public int getId() {
        return this.mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getDownloadId() {
        return this.mDownloadId;
    }

    public void setDownloadId(int downloadId) {
        this.mDownloadId = downloadId;
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

    public String getPackageName() {
        return this.mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public String getAppLabel() {
        return this.mAppLabel;
    }

    public void setAppLabel(String appLabel) {
        this.mAppLabel = appLabel;
    }

    public long getTotalBytes() {
        return this.mTotalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.mTotalBytes = totalBytes;
    }

    public String getIconUrl() {
        return this.mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public String toString() {
        return "DCLocalInfo{id=" + this.mId + ", pn='" + this.mPackageName + "', download=" + this.mDownloadId + '}';
    }
}
