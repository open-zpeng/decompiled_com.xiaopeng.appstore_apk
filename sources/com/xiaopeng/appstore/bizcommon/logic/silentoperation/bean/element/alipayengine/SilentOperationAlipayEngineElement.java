package com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine;

import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.alipayengine.SilentOperationAPKDownloadAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.alipayengine.SilentOperationAlipayEngineInitServiceAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.alipayengine.SilentOperationAlipayEngineInstallAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.alipayengine.SilentOperationConfigDownloadAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.SilentOperationElement;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.result.SilentOperationActionResult;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.result.SilentOperationElementResult;
import java.io.File;
import java.util.Iterator;
/* loaded from: classes2.dex */
public class SilentOperationAlipayEngineElement extends SilentOperationElement {
    private static final String TAG = "SilentOperationAlipayEngineElement";
    private SilentOperationListener mSilentOperationListener;

    /* loaded from: classes2.dex */
    public interface SilentOperationListener {
        void onApkDownloadFinish(File file);

        void onApkDownloadProgress(long totalBytes, long downloadedBytes, long speedPerSecond);

        void onApkDownloadStart();

        void onApkInstallFinish(boolean success);

        void onApkInstallProgress(float progress);

        void onApkInstallStart();

        void onConfigDownloadFinish(File file);

        void onConfigDownloadProgress(long totalBytes, long downloadedBytes, long speedPerSecond);

        void onConfigDownloadStart();

        void onInitServiceCompleted(boolean success);

        void onInitServiceStart();
    }

    public void setSilentOperationListener(SilentOperationListener silentOperationListener) {
        this.mSilentOperationListener = silentOperationListener;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.SilentOperationElement, com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.SilentOperationBaseElement
    public SilentOperationElementResult execute() {
        Iterator<SilentOperationBaseAction> it = this.mSilentOperationActionsQueue.iterator();
        boolean z = true;
        while (it.hasNext()) {
            SilentOperationBaseAction next = it.next();
            prepareForNextAction(this.mLastActionResult, next);
            setProgressListener(next);
            Logger.t(TAG).d("executing action = " + next);
            SilentOperationActionResult execute = next.execute();
            this.mLastActionResult = execute;
            z = execute.isSuccess();
            it.remove();
        }
        this.mElementResult.setSuccess(z);
        return this.mElementResult;
    }

    private void setProgressListener(SilentOperationBaseAction action) {
        if (action instanceof SilentOperationConfigDownloadAction) {
            ((SilentOperationConfigDownloadAction) action).setOperationListener(this.mSilentOperationListener);
        } else if (action instanceof SilentOperationAPKDownloadAction) {
            ((SilentOperationAPKDownloadAction) action).setOperationListener(this.mSilentOperationListener);
        } else if (action instanceof SilentOperationAlipayEngineInstallAction) {
            ((SilentOperationAlipayEngineInstallAction) action).setOperationListener(this.mSilentOperationListener);
        } else if (action instanceof SilentOperationAlipayEngineInitServiceAction) {
            ((SilentOperationAlipayEngineInitServiceAction) action).setOperationListener(this.mSilentOperationListener);
        }
    }
}
