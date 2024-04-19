package com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action;

import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.result.SilentOperationActionResult;
import java.io.File;
/* loaded from: classes2.dex */
public class SilentOperationApplyConfigAction extends SilentOperationBaseAction {
    private static final String TAG = "SilentOperationApplyConfigAction";
    private String mConfigFilePath;
    private String mDownloadTaskID;
    private String mPackageName;

    public String getDownloadTaskID() {
        return this.mDownloadTaskID;
    }

    public void setDownloadTaskID(String downloadTaskID) {
        this.mDownloadTaskID = downloadTaskID;
    }

    public void setConfigFilePath(String configFilePath) {
        this.mConfigFilePath = configFilePath;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public SilentOperationApplyConfigAction(String packageName) {
        super(4);
        this.mPackageName = packageName;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public SilentOperationActionResult execute() {
        File file = new File(this.mConfigFilePath);
        boolean z = false;
        if (file.isFile() && file.exists()) {
            z = AppStoreAssembleManager.applyConfig(file, this.mPackageName);
        } else {
            Logger.t(TAG).e("config path not file or not exist, path = " + this.mConfigFilePath, new Object[0]);
        }
        this.mOperationResult.setSuccess(z);
        this.mOperationResult.setDownloadTaskID(this.mDownloadTaskID);
        this.mOperationResult.setDownloadFilePath(this.mConfigFilePath);
        return this.mOperationResult;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public String toString() {
        StringBuilder sb = new StringBuilder("SilentOperationApplyConfigAction{");
        sb.append("mConfigFilePath='").append(this.mConfigFilePath).append('\'');
        sb.append(", mPackageName='").append(this.mPackageName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
