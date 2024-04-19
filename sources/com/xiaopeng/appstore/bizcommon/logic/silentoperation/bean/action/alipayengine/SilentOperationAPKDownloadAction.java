package com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.alipayengine;

import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationDownloadAction;
import java.io.File;
/* loaded from: classes2.dex */
public class SilentOperationAPKDownloadAction extends SilentOperationDownloadAction {
    public SilentOperationAPKDownloadAction(String downloadUrl) {
        super(downloadUrl);
        this.mSilentOperationDownloadListener = new SilentOperationApkDownloadListener(this);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationDownloadAction, com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public String toString() {
        StringBuilder sb = new StringBuilder("SilentOperationAPKDownloadAction{");
        sb.append("mOperationListener=").append(this.mOperationListener);
        sb.append('}');
        return sb.toString();
    }

    /* loaded from: classes2.dex */
    private static class SilentOperationApkDownloadListener extends SilentOperationDownloadAction.SilentOperationDownloadListener {
        private static final String TAG = "SilentOperationApkDownloadListener";
        private SilentOperationAPKDownloadAction mOperationDownloadAction;

        @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationDownloadAction.SilentOperationDownloadListener, com.xiaopeng.appstore.bizcommon.logic.download.XpDownloadListener
        public void onDownloadPause(int id, String downloadUrl) {
        }

        public SilentOperationApkDownloadListener(SilentOperationAPKDownloadAction downloadAction) {
            super(downloadAction);
            this.mOperationDownloadAction = downloadAction;
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationDownloadAction.SilentOperationDownloadListener, com.xiaopeng.appstore.bizcommon.logic.download.XpDownloadListener
        public void onDownloadStart(int id, String downloadUrl, String fileName, long totalBytes) {
            if (this.mOperationDownloadAction.mOperationListener != null) {
                this.mOperationDownloadAction.mOperationListener.onApkDownloadStart();
            } else {
                Logger.t(TAG).w("alipay engine apk download start callback, but listener is null", new Object[0]);
            }
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationDownloadAction.SilentOperationDownloadListener, com.xiaopeng.appstore.bizcommon.logic.download.XpDownloadListener
        public void onDownloadCancel(int id, String downloadUrl) {
            this.mOperationDownloadAction.downloadFail();
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationDownloadAction.SilentOperationDownloadListener, com.xiaopeng.appstore.bizcommon.logic.download.XpDownloadListener
        public void onDownloadProgress(int id, String downloadUrl, long totalBytes, long downloadedBytes, long speedPerSecond) {
            String str = TAG;
            Logger.t(str).d("id = " + id + "  download url = " + downloadUrl + "  totalBytes = " + totalBytes + "  downloadedBytes = " + downloadedBytes);
            if (this.mOperationDownloadAction.mOperationListener != null) {
                this.mOperationDownloadAction.mOperationListener.onApkDownloadProgress(totalBytes, downloadedBytes, speedPerSecond);
            } else {
                Logger.t(str).w("alipay engine apk download progress callback, but listener is null", new Object[0]);
            }
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationDownloadAction.SilentOperationDownloadListener, com.xiaopeng.appstore.bizcommon.logic.download.XpDownloadListener
        public void onDownloadComplete(int id, String downloadUrl, File file) {
            this.mOperationDownloadAction.downloadSuccess(file.getAbsolutePath());
            if (this.mOperationDownloadAction.mOperationListener != null) {
                this.mOperationDownloadAction.mOperationListener.onApkDownloadFinish(file);
            } else {
                Logger.t(TAG).w("alipay engine apk download complete callback, but listener is null", new Object[0]);
            }
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationDownloadAction.SilentOperationDownloadListener, com.xiaopeng.appstore.bizcommon.logic.download.XpDownloadListener
        public void onDownloadError(int id, String downloadUrl, String msg) {
            this.mOperationDownloadAction.downloadFail();
        }
    }
}
