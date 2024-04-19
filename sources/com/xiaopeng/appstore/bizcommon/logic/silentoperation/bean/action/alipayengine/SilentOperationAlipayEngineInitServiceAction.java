package com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.alipayengine;

import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.IMiniProgramListener;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine.SilentOperationAlipayEngineElement;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.result.SilentOperationActionResult;
import com.xiaopeng.xuimanager.xapp.MiniProgramResponse;
/* loaded from: classes2.dex */
public class SilentOperationAlipayEngineInitServiceAction extends SilentOperationBaseAction {
    private static final String TAG = "SilentOperationAlipayEngineInitServiceAction";
    private IMiniProgramListener mIMiniProgramListener;
    protected SilentOperationAlipayEngineElement.SilentOperationListener mOperationListener;

    public SilentOperationAlipayEngineInitServiceAction() {
        super(6);
        this.mIMiniProgramListener = new IMiniProgramListener() { // from class: com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.alipayengine.SilentOperationAlipayEngineInitServiceAction.1
            @Override // com.xiaopeng.appstore.bizcommon.logic.IMiniProgramListener
            public void onMiniProgramCallBack(int type, MiniProgramResponse response) {
                if (type == 0) {
                    boolean z = response.getCode() == 0;
                    SilentOperationAlipayEngineInitServiceAction.this.mOperationResult.setSuccess(z);
                    if (SilentOperationAlipayEngineInitServiceAction.this.mOperationListener == null) {
                        Logger.t(SilentOperationAlipayEngineInitServiceAction.TAG).i("onInitServiceCompleted callback but listener is null", new Object[0]);
                    } else {
                        SilentOperationAlipayEngineInitServiceAction.this.mOperationListener.onInitServiceCompleted(z);
                    }
                }
                XuiMgrHelper.get().removeMiniProgramListener(SilentOperationAlipayEngineInitServiceAction.this.mIMiniProgramListener);
            }
        };
    }

    public void setOperationListener(SilentOperationAlipayEngineElement.SilentOperationListener operationListener) {
        this.mOperationListener = operationListener;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public SilentOperationActionResult execute() {
        XuiMgrHelper.get().addMiniProgramListener(this.mIMiniProgramListener);
        SilentOperationAlipayEngineElement.SilentOperationListener silentOperationListener = this.mOperationListener;
        if (silentOperationListener != null) {
            silentOperationListener.onInitServiceStart();
        } else {
            Logger.t(TAG).i("onInitServiceStart callback but listener is null", new Object[0]);
        }
        XuiMgrHelper.get().initAromeService();
        return this.mOperationResult;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public String toString() {
        return "SilentOperationAlipayEngineInitServiceAction{}";
    }
}
