package com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.task.alipayengine;

import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.SilentCellBean;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationApplyConfigAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationClearResourceAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.alipayengine.SilentOperationAPKDownloadAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.alipayengine.SilentOperationAlipayEngineInitServiceAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.alipayengine.SilentOperationAlipayEngineInstallAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.alipayengine.SilentOperationConfigDownloadAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.SilentOperationElement;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine.SilentOperationAlipayEngineElement;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.task.SilentOperationTask;
/* loaded from: classes2.dex */
public class SilentOperationAlipayEngineTask extends SilentOperationTask {
    private SilentOperationAlipayEngineElement.SilentOperationListener mSilentOperationListener;

    public SilentOperationAlipayEngineTask(SilentCellBean originTaskCellBean) {
        super(originTaskCellBean);
    }

    public void setSilentOperationListener(SilentOperationAlipayEngineElement.SilentOperationListener silentOperationListener) {
        this.mSilentOperationListener = silentOperationListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.task.SilentOperationTask
    public void executeElementDelegate(SilentOperationElement element) {
        if (element instanceof SilentOperationAlipayEngineElement) {
            ((SilentOperationAlipayEngineElement) element).setSilentOperationListener(this.mSilentOperationListener);
        }
        super.executeElementDelegate(element);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.task.SilentOperationTask
    protected SilentOperationElement generateElementDelegate() {
        return new SilentOperationAlipayEngineElement();
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.task.SilentOperationTask
    protected void generateActionForDownloadInstall(SilentOperationElement operationElement, SilentCellBean silentCellBean) {
        String configUrl = silentCellBean.getConfigUrl();
        if (configUrl != null) {
            SilentOperationConfigDownloadAction silentOperationConfigDownloadAction = new SilentOperationConfigDownloadAction(configUrl);
            SilentOperationApplyConfigAction silentOperationApplyConfigAction = new SilentOperationApplyConfigAction(silentCellBean.getOperationPackageName());
            SilentOperationClearResourceAction silentOperationClearResourceAction = new SilentOperationClearResourceAction();
            operationElement.offer(silentOperationConfigDownloadAction);
            operationElement.offer(silentOperationApplyConfigAction);
            operationElement.offer(silentOperationClearResourceAction);
        }
        String apkFileUrl = silentCellBean.getApkFileUrl();
        if (apkFileUrl != null) {
            SilentOperationAPKDownloadAction silentOperationAPKDownloadAction = new SilentOperationAPKDownloadAction(apkFileUrl);
            silentOperationAPKDownloadAction.setTargetFileMd5(silentCellBean.getMd5());
            SilentOperationAlipayEngineInstallAction silentOperationAlipayEngineInstallAction = new SilentOperationAlipayEngineInstallAction(silentCellBean.getOperationPackageName());
            SilentOperationClearResourceAction silentOperationClearResourceAction2 = new SilentOperationClearResourceAction();
            SilentOperationAlipayEngineInitServiceAction silentOperationAlipayEngineInitServiceAction = new SilentOperationAlipayEngineInitServiceAction();
            operationElement.offer(silentOperationAPKDownloadAction);
            operationElement.offer(silentOperationAlipayEngineInstallAction);
            operationElement.offer(silentOperationClearResourceAction2);
            operationElement.offer(silentOperationAlipayEngineInitServiceAction);
        }
    }
}
