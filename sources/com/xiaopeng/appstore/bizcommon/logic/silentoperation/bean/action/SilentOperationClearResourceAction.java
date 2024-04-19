package com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action;

import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.result.SilentOperationActionResult;
/* loaded from: classes2.dex */
public class SilentOperationClearResourceAction extends SilentOperationBaseAction {
    private static final String TAG = "SilentOperationClearResourceAction";
    private String mDownloadTaskID;

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public boolean pause() {
        return false;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public boolean retry() {
        return false;
    }

    public SilentOperationClearResourceAction() {
        super(5);
    }

    public void setDownloadTaskID(String downloadTaskID) {
        this.mDownloadTaskID = downloadTaskID;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public String toString() {
        StringBuilder sb = new StringBuilder("SilentOperationClearResourceAction{");
        sb.append("mDownloadTaskID='").append(this.mDownloadTaskID).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public SilentOperationActionResult execute() {
        if (this.mDownloadTaskID != null) {
            IXpDownloader.Factory.get().removeLocalData(Long.parseLong(this.mDownloadTaskID));
            Logger.t(TAG).d("clear resource for download task id  = %1$s", this.mDownloadTaskID);
        } else {
            Logger.t(TAG).d("can not clear resource for download task id  = null");
        }
        this.mOperationResult.setSuccess(true);
        return this.mOperationResult;
    }
}
