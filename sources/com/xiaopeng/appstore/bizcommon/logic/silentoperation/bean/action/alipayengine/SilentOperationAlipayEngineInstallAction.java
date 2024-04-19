package com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.alipayengine;

import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationInstallAction;
import com.xiaopeng.appstore.ui_miniprog.miniprog.fragment.MiniProgramFragment;
/* loaded from: classes2.dex */
public class SilentOperationAlipayEngineInstallAction extends SilentOperationInstallAction {
    public SilentOperationAlipayEngineInstallAction(String packageName) {
        super(packageName);
        this.mInstallCallback = new SilentOperationAlipayEngineInstallCallback(this);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationInstallAction, com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public String toString() {
        StringBuilder sb = new StringBuilder("SilentOperationAlipayEngineInstallAction{");
        sb.append("mOperationListener=").append(this.mOperationListener);
        sb.append('}');
        return sb.toString();
    }

    /* loaded from: classes2.dex */
    private static class SilentOperationAlipayEngineInstallCallback extends SilentOperationInstallAction.SilentOperationInstallCallback {
        private static final String TAG = SilentOperationAlipayEngineInstallAction.class.getSimpleName();
        private SilentOperationAlipayEngineInstallAction mInstallAction;

        public SilentOperationAlipayEngineInstallCallback(SilentOperationAlipayEngineInstallAction operationInstallAction) {
            super(operationInstallAction);
            this.mInstallAction = operationInstallAction;
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationInstallAction.SilentOperationInstallCallback, com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppInstallCallback
        public void onInstallCreate(int sessionId, String pkgName) {
            if (this.mInstallAction.mOperationListener != null) {
                this.mInstallAction.mOperationListener.onApkInstallStart();
            } else {
                Logger.t(TAG).w("alipay engine install create callback, but listener is null", new Object[0]);
            }
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationInstallAction.SilentOperationInstallCallback, com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppInstallCallback
        public void onInstallProgressChanged(int sessionId, String pkgName, float progress) {
            if (this.mInstallAction.mOperationListener != null) {
                this.mInstallAction.mOperationListener.onApkInstallProgress(progress);
            } else {
                Logger.t(TAG).w("alipay engine install progress callback, but listener is null", new Object[0]);
            }
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationInstallAction.SilentOperationInstallCallback, com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppInstallCallback
        public void onInstallFinished(int sessionId, String packageName, boolean success) {
            AppComponentManager.get().removeOnAppInstallCallback(this);
            String str = TAG;
            Logger.t(str).d("onPackageAdded , packageName = " + packageName + "  is success = " + success);
            try {
                Logger.t(str).d("sleep thread delay for next action : init alipay arome service");
                Thread.sleep(MiniProgramFragment.TIME_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.mInstallAction.installFinished(sessionId, packageName, success);
            if (this.mInstallAction.mOperationListener != null) {
                this.mInstallAction.mOperationListener.onApkInstallFinish(success);
            } else {
                Logger.t(TAG).w("alipay engine install finished callback, but listener is null", new Object[0]);
            }
        }
    }
}
