package com.xiaopeng.appstore.bizcommon.entities.download;
/* loaded from: classes2.dex */
public class XpOkDownloadInfo {
    private long mDownloadId;
    private int mId;
    private int mStatus;
    private String mTag;
    private String mUrl;

    public int getId() {
        return this.mId;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public String getTag() {
        return this.mTag;
    }

    public long getDownloadId() {
        return this.mDownloadId;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public void setTag(String tag) {
        this.mTag = tag;
    }

    public void setDownloadId(long downloadId) {
        this.mDownloadId = downloadId;
    }

    public void setStatus(int status) {
        this.mStatus = status;
    }
}
