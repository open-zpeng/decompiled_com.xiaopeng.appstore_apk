package com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action;

import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.result.SilentOperationActionResult;
import com.xiaopeng.appstore.bizcommon.utils.PackageUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
/* loaded from: classes2.dex */
public class SilentOperationUninstallAction extends SilentOperationBaseAction {
    private static final int UNINSTALL_REQUEST_CODE = 100001;
    private String mPackageName;

    public SilentOperationUninstallAction(String packageName) {
        super(2);
        this.mPackageName = packageName;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public SilentOperationActionResult execute() {
        this.mOperationResult.setSuccess(PackageUtils.uninstall(Utils.getApp(), this.mPackageName, 100001));
        return this.mOperationResult;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public String toString() {
        StringBuilder sb = new StringBuilder("SilentOperationUninstallAction{");
        sb.append("mPackageName='").append(this.mPackageName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
