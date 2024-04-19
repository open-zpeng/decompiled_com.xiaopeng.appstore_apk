package com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action;

import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader;
import com.xiaopeng.appstore.bizcommon.logic.download.XpDownloadListener;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine.SilentOperationAlipayEngineElement;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.result.SilentOperationActionResult;
import com.xiaopeng.appstore.libcommon.utils.FileUtils;
import java.io.File;
import java.util.concurrent.CountDownLatch;
/* loaded from: classes2.dex */
public class SilentOperationDownloadAction extends SilentOperationBaseAction {
    private static final String TAG = "SilentOperationDownloadAction";
    private long mDownloadId;
    private String mDownloadUrl;
    protected SilentOperationAlipayEngineElement.SilentOperationListener mOperationListener;
    protected SilentOperationDownloadListener mSilentOperationDownloadListener;
    private String mTargetFileMd5;

    public SilentOperationDownloadAction(String downloadUrl) {
        super(0);
        this.mDownloadUrl = downloadUrl;
        this.mActionOperationSync = new CountDownLatch(1);
        this.mSilentOperationDownloadListener = new SilentOperationDownloadListener(this);
    }

    public void setTargetFileMd5(String targetFileMd5) {
        this.mTargetFileMd5 = targetFileMd5;
    }

    public void setOperationListener(SilentOperationAlipayEngineElement.SilentOperationListener operationListener) {
        this.mOperationListener = operationListener;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public SilentOperationActionResult execute() {
        IXpDownloader.Factory.get().observeDownload(this.mDownloadUrl, this.mSilentOperationDownloadListener);
        if (shouldStartDownload(this.mDownloadUrl)) {
            IXpDownloader iXpDownloader = IXpDownloader.Factory.get();
            String str = this.mDownloadUrl;
            this.mDownloadId = iXpDownloader.enqueue(str, str);
            this.mOperationResult.setDownloadTaskID(String.valueOf(this.mDownloadId));
        } else {
            Logger.t(TAG).d("there is a download task running for url %1$s, remount download listener for url", this.mDownloadUrl);
        }
        try {
            this.mActionOperationSync.await();
        } catch (InterruptedException e) {
            Logger.t(TAG).i("mActionOperationSync is interrupted", new Object[0]);
            e.printStackTrace();
        }
        return this.mOperationResult;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public boolean retry() {
        IXpDownloader iXpDownloader = IXpDownloader.Factory.get();
        String str = this.mDownloadUrl;
        iXpDownloader.enqueue(str, str);
        return true;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public boolean pause() {
        IXpDownloader.Factory.get().pause(this.mDownloadId);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void downloadSuccess(String filePath) {
        this.mOperationResult.setSuccess(true);
        this.mOperationResult.setDownloadFilePath(filePath);
        IXpDownloader.Factory.get().removeObserver(this.mSilentOperationDownloadListener);
        this.mActionOperationSync.countDown();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void downloadFail() {
        this.mOperationResult.setSuccess(false);
        IXpDownloader.Factory.get().removeObserver(this.mSilentOperationDownloadListener);
        this.mActionOperationSync.countDown();
    }

    private boolean shouldStartDownload(String downloadUrl) {
        int downloadStatus = getDownloadStatus(downloadUrl);
        String str = TAG;
        Logger.t(str).d("download statue = %1$d for url = %2$s", Integer.valueOf(downloadStatus), downloadUrl);
        if (downloadStatus == 4) {
            File localFile = XuiMgrHelper.get().getStoreResProvider().getLocalFile(downloadUrl);
            String absolutePath = localFile.getAbsolutePath();
            String md5 = FileUtils.md5(localFile);
            Logger.t(str).d("target md5  = %1$s , downloaded file md5 = %2$s", this.mTargetFileMd5, md5);
            String str2 = this.mTargetFileMd5;
            if (str2 != null && str2.equals(md5)) {
                downloadSuccess(absolutePath);
                return false;
            }
        } else if (downloadStatus != 0) {
            return downloadStatus != 1 ? false : false;
        }
        return true;
    }

    private int getDownloadStatus(String downloadUrl) {
        return IXpDownloader.Factory.get().fetchDownloadStatus(downloadUrl);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction
    public String toString() {
        StringBuilder sb = new StringBuilder("SilentOperationDownloadAction{");
        sb.append("mDownloadUrl='").append(this.mDownloadUrl).append('\'');
        sb.append(", mActionOperationSync=").append(this.mActionOperationSync);
        sb.append(", mSilentOperationDownloadListener=").append(this.mSilentOperationDownloadListener);
        sb.append('}');
        return sb.toString();
    }

    /* loaded from: classes2.dex */
    public static class SilentOperationDownloadListener implements XpDownloadListener {
        private SilentOperationDownloadAction mOperationDownloadAction;

        @Override // com.xiaopeng.appstore.bizcommon.logic.download.XpDownloadListener
        public void onDownloadPause(int id, String downloadUrl) {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.download.XpDownloadListener
        public void onDownloadStart(int id, String downloadUrl, String fileName, long totalBytes) {
        }

        public SilentOperationDownloadListener(SilentOperationDownloadAction downloadAction) {
            this.mOperationDownloadAction = downloadAction;
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.download.XpDownloadListener
        public void onDownloadCancel(int id, String downloadUrl) {
            this.mOperationDownloadAction.downloadFail();
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.download.XpDownloadListener
        public void onDownloadProgress(int id, String downloadUrl, long totalBytes, long downloadedBytes, long speedPerSecond) {
            Logger.t(SilentOperationDownloadAction.TAG).d("id = " + id + "  download url = " + downloadUrl + "  totalBytes = " + totalBytes + "  downloadedBytes = " + downloadedBytes);
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.download.XpDownloadListener
        public void onDownloadComplete(int id, String downloadUrl, File file) {
            this.mOperationDownloadAction.downloadSuccess(file.getAbsolutePath());
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.download.XpDownloadListener
        public void onDownloadError(int id, String downloadUrl, String msg) {
            this.mOperationDownloadAction.downloadFail();
        }
    }
}
