package com.xiaopeng.appstore.bizcommon.entities;
/* loaded from: classes2.dex */
public class AssembleLocalInfo {
    private int mAssembleId;
    private String mConfigMd5;
    private String mConfigUrl;
    private int mDownloadId;
    private String mDownloadUrl;
    private long mDownloadedBytes;
    private String mIconUrl;
    private String mLabel;
    private String mMd5;
    private String mPackageName;
    private float mProgress;
    private int mState;
    private long mTotalBytes;

    public int getAssembleId() {
        return this.mAssembleId;
    }

    public void setAssembleId(int assembleId) {
        this.mAssembleId = assembleId;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
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

    public String getIconUrl() {
        return this.mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public String getLabel() {
        return this.mLabel;
    }

    public void setLabel(String label) {
        this.mLabel = label;
    }

    public int getDownloadId() {
        return this.mDownloadId;
    }

    public void setDownloadId(int downloadId) {
        this.mDownloadId = downloadId;
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int state) {
        this.mState = state;
    }

    public long getDownloadedBytes() {
        return this.mDownloadedBytes;
    }

    public void setDownloadedBytes(long downloadedBytes) {
        this.mDownloadedBytes = downloadedBytes;
    }

    public long getTotalBytes() {
        return this.mTotalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.mTotalBytes = totalBytes;
    }

    public float getProgress() {
        return this.mProgress;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
    }
}
