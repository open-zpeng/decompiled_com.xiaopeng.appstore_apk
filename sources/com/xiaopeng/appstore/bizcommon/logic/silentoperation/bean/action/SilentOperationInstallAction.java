package com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action;

import android.os.Handler;
import android.os.HandlerThread;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine.SilentOperationAlipayEngineElement;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.result.SilentOperationActionResult;
import com.xiaopeng.appstore.bizcommon.utils.PackageUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
/* loaded from: classes2.dex */
public class SilentOperationInstallAction extends SilentOperationBaseAction {
    private static final String TAG = "SilentOperationInstallAction";
    private String mAPKFilePath;
    private String mDownloadTaskID;
    private Handler mHandler;
    protected SilentOperationInstallCallback mInstallCallback;
    private HandlerThread mInstallCallbackThread;
    private int mInstallSessionId;
    protected SilentOperationAlipayEngineElement.SilentOperationListener mOperationListener;
    private String mPackageName;

    public SilentOperationInstallAction(String packageName) {
        super(1);
        this.mPackageName = packageName;
        this.mActionOperationSync = new CountDownLatch(1);
        this.mInstallCallback = new SilentOperationInstallCallback(this);
        HandlerThread handlerThread = new HandlerThread("silent_install_action_callback_thread");
        this.mInstallCallbackThread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(this.mInstallCallbackThread.getLooper());
    }

    public void setOperationListener(SilentOperationAlipayEngineElement.SilentOperationListener operationListener) {
        this.mOperationListener = operationListener;
    }

    public void setDownloadTaskID(String downloadTaskID) {
        this.mDownloadTaskID = downloadTaskID;
    }

    public void setAPKFilePath(String APKFilePath) {
        this.mAPKFilePath = APKFilePath;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public SilentOperationActionResult execute() {
        AppComponentManager.get().addOnAppInstallCallback(this.mInstallCallback, this.mHandler);
        File file = new File(this.mAPKFilePath);
        boolean z = false;
        if (file.isFile() && file.exists()) {
            int install = PackageUtils.install(Utils.getApp(), file, this.mPackageName, null);
            this.mInstallSessionId = install;
            boolean z2 = install != 0;
            try {
                try {
                    String str = TAG;
                    Logger.t(str).d("start await for installing package name = %1$S, file path = %2$s", this.mPackageName, this.mAPKFilePath);
                    this.mActionOperationSync.await(5L, TimeUnit.MINUTES);
                    Logger.t(str).d("finish await for installing package name = %1$S, file path = %2$s", this.mPackageName, this.mAPKFilePath);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.mInstallCallbackThread.quitSafely();
                z = z2;
            } catch (Throwable th) {
                this.mInstallCallbackThread.quitSafely();
                throw th;
            }
        } else {
            Logger.t(TAG).e("apk path not file or not exist, path = " + this.mAPKFilePath, new Object[0]);
        }
        this.mOperationResult.setSuccess(z);
        this.mOperationResult.setDownloadTaskID(this.mDownloadTaskID);
        this.mOperationResult.setDownloadFilePath(this.mAPKFilePath);
        return this.mOperationResult;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public String toString() {
        StringBuilder sb = new StringBuilder("SilentOperationInstallAction{");
        sb.append("mAPKFilePath='").append(this.mAPKFilePath).append('\'');
        sb.append(", mPackageName='").append(this.mPackageName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void installFinished(int sessionId, String packageName, boolean success) {
        if (this.mInstallSessionId == sessionId) {
            this.mActionOperationSync.countDown();
        }
    }

    /* loaded from: classes2.dex */
    protected static class SilentOperationInstallCallback implements AppComponentManager.OnAppInstallCallback {
        private SilentOperationInstallAction mInstallAction;

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppInstallCallback
        public void onInstallCreate(int sessionId, String pkgName) {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppInstallCallback
        public void onInstallProgressChanged(int sessionId, String pkgName, float progress) {
        }

        public SilentOperationInstallCallback(SilentOperationInstallAction operationInstallAction) {
            this.mInstallAction = operationInstallAction;
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppInstallCallback
        public void onInstallFinished(int sessionId, String packageName, boolean success) {
            AppComponentManager.get().removeOnAppInstallCallback(this);
            Logger.t(SilentOperationInstallAction.TAG).d("onPackageAdded , packageName = " + packageName + "  is success = " + success);
            this.mInstallAction.installFinished(sessionId, packageName, success);
        }
    }
}
