package com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element;

import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationApplyConfigAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationClearResourceAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationInstallAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.result.SilentOperationActionResult;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.result.SilentOperationElementResult;
import java.util.Iterator;
/* loaded from: classes2.dex */
public class SilentOperationElement extends SilentOperationBaseElement {
    private static final String TAG = "SilentOperationElement";
    protected SilentOperationActionResult mLastActionResult;

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.SilentOperationBaseElement
    public SilentOperationElementResult execute() {
        Iterator<SilentOperationBaseAction> it = this.mSilentOperationActionsQueue.iterator();
        boolean z = true;
        while (it.hasNext()) {
            SilentOperationBaseAction next = it.next();
            prepareForNextAction(this.mLastActionResult, next);
            Logger.t(TAG).d("executing action = " + next);
            SilentOperationActionResult execute = next.execute();
            this.mLastActionResult = execute;
            z = execute.isSuccess();
            it.remove();
        }
        this.mElementResult.setSuccess(z);
        return this.mElementResult;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.SilentOperationBaseElement
    public String toString() {
        StringBuilder sb = new StringBuilder("SilentOperationElement{");
        sb.append("mLastActionResult=").append(this.mLastActionResult);
        sb.append(", mSilentOperationActionsQueue=").append(this.mSilentOperationActionsQueue);
        sb.append(", mElementResult=").append(this.mElementResult);
        sb.append('}');
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void prepareForNextAction(SilentOperationActionResult mLastActionResult, SilentOperationBaseAction nextAction) {
        if (nextAction instanceof SilentOperationInstallAction) {
            SilentOperationInstallAction silentOperationInstallAction = (SilentOperationInstallAction) nextAction;
            if (mLastActionResult != null) {
                Logger.t(TAG).i("prepare for install action package name = %1$s , last action result is %2$s", silentOperationInstallAction.getPackageName(), mLastActionResult.toString());
                silentOperationInstallAction.setDownloadTaskID(mLastActionResult.getDownloadTaskID());
                silentOperationInstallAction.setAPKFilePath(mLastActionResult.getDownloadFilePath());
                return;
            }
            Logger.t(TAG).e("prepare for install action package name = %1$s , last action result is null", silentOperationInstallAction.getPackageName());
        } else if (nextAction instanceof SilentOperationApplyConfigAction) {
            SilentOperationApplyConfigAction silentOperationApplyConfigAction = (SilentOperationApplyConfigAction) nextAction;
            if (mLastActionResult != null) {
                Logger.t(TAG).i("prepare for apply action package name = %1$s , last action result is %2$s", silentOperationApplyConfigAction.getPackageName(), mLastActionResult.toString());
                silentOperationApplyConfigAction.setDownloadTaskID(mLastActionResult.getDownloadTaskID());
                silentOperationApplyConfigAction.setConfigFilePath(mLastActionResult.getDownloadFilePath());
                return;
            }
            Logger.t(TAG).e("prepare for apply config action package name = %1$s , last action result is null", silentOperationApplyConfigAction.getPackageName());
        } else if (nextAction instanceof SilentOperationClearResourceAction) {
            SilentOperationClearResourceAction silentOperationClearResourceAction = (SilentOperationClearResourceAction) nextAction;
            if (mLastActionResult != null) {
                Logger.t(TAG).i("prepare for delete file action , last action result is %1$s", mLastActionResult.toString());
                silentOperationClearResourceAction.setDownloadTaskID(mLastActionResult.getDownloadTaskID());
                return;
            }
            Logger.t(TAG).e("prepare for delete file action , last action result is null", new Object[0]);
        }
    }
}
