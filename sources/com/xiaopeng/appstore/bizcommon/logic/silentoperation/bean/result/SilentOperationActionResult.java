package com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.result;
/* loaded from: classes2.dex */
public class SilentOperationActionResult extends SilentOperationBaseResult {
    private String mDownloadFilePath;
    private String mDownloadTaskID;

    public String getDownloadTaskID() {
        return this.mDownloadTaskID;
    }

    public void setDownloadTaskID(String downloadTaskID) {
        this.mDownloadTaskID = downloadTaskID;
    }

    public String getDownloadFilePath() {
        return this.mDownloadFilePath;
    }

    public void setDownloadFilePath(String downloadFilePath) {
        this.mDownloadFilePath = downloadFilePath;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SilentOperationActionResult{");
        sb.append("mDownloadTaskID='").append(this.mDownloadTaskID).append('\'');
        sb.append(", mDownloadFilePath='").append(this.mDownloadFilePath).append('\'');
        sb.append(", isSuccess=").append(this.isSuccess);
        sb.append('}');
        return sb.toString();
    }
}
