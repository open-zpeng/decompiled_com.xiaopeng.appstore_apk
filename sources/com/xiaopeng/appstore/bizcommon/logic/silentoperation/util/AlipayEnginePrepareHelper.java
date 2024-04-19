package com.xiaopeng.appstore.bizcommon.logic.silentoperation.util;

import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.IMiniProgramListener;
import com.xiaopeng.appstore.bizcommon.logic.ProgressHolder;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine.SilentOperationAlipayEngineElement;
import com.xiaopeng.xuimanager.xapp.MiniProgramResponse;
import java.io.File;
import java.util.HashMap;
/* loaded from: classes2.dex */
public class AlipayEnginePrepareHelper {
    public static final String ALIPAY_MINI_PROGRAM_NEW_PACKAGE_NAME = "com.alipay.arome.app";
    public static final String ALIPAY_MINI_PROGRAM_PACKAGE_NAME = "com.eg.android.AlipayGphone";
    private static final long INSTALL_DURATION = 10000;
    private static final String TAG = "AlipayHelper";
    private String mAlipayEngineDownloadUrl;
    private String mAlipayEnginePackageName;
    private SilentOperationAlipayEngineElement.SilentOperationListener mAlipayListener;
    private IMiniProgramListener mIMiniProgramListener;
    public ProgressHolder sProgressHolder;

    public void setAlipayEnginePackageName(String alipayEnginePackageName) {
        this.mAlipayEnginePackageName = alipayEnginePackageName;
    }

    public void setAlipayEngineDownloadUrl(String alipayEngineDownloadUrl) {
        this.mAlipayEngineDownloadUrl = alipayEngineDownloadUrl;
    }

    private AlipayEnginePrepareHelper() {
        this.sProgressHolder = new ProgressHolder(new HashMap<String, Float>() { // from class: com.xiaopeng.appstore.bizcommon.logic.silentoperation.util.AlipayEnginePrepareHelper.1
            {
                Float valueOf = Float.valueOf(0.2f);
                put("download_config", valueOf);
                put("download_engine", Float.valueOf(0.5f));
                put("install_app", valueOf);
                put("init_service", Float.valueOf(0.1f));
            }
        });
        this.mIMiniProgramListener = new IMiniProgramListener() { // from class: com.xiaopeng.appstore.bizcommon.logic.silentoperation.util.-$$Lambda$AlipayEnginePrepareHelper$hRk_GBDk_gQK1cOLaz4GICzknRc
            @Override // com.xiaopeng.appstore.bizcommon.logic.IMiniProgramListener
            public final void onMiniProgramCallBack(int i, MiniProgramResponse miniProgramResponse) {
                AlipayEnginePrepareHelper.this.lambda$new$0$AlipayEnginePrepareHelper(i, miniProgramResponse);
            }
        };
        this.mAlipayListener = new SilentOperationAlipayEngineElement.SilentOperationListener() { // from class: com.xiaopeng.appstore.bizcommon.logic.silentoperation.util.AlipayEnginePrepareHelper.2
            @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine.SilentOperationAlipayEngineElement.SilentOperationListener
            public void onConfigDownloadStart() {
                Logger.t(AlipayEnginePrepareHelper.TAG).d("onConfigDownloadStart");
                AlipayEnginePrepareHelper.this.sProgressHolder.startNext();
            }

            @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine.SilentOperationAlipayEngineElement.SilentOperationListener
            public void onConfigDownloadProgress(long totalBytes, long downloadedBytes, long speedPerSecond) {
                float f = totalBytes != 0 ? ((float) downloadedBytes) / ((float) totalBytes) : 0.0f;
                long j = speedPerSecond != 0 ? (totalBytes - downloadedBytes) / speedPerSecond : 0L;
                Logger.t(AlipayEnginePrepareHelper.TAG).d("onConfigDownloadProgress, progress=" + f + " remain=" + j);
                AlipayEnginePrepareHelper.this.sProgressHolder.setCurrentTask(0);
                AlipayEnginePrepareHelper.this.sProgressHolder.processCurrentProgress(f, j * 1000);
            }

            @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine.SilentOperationAlipayEngineElement.SilentOperationListener
            public void onConfigDownloadFinish(File file) {
                Logger.t(AlipayEnginePrepareHelper.TAG).d("onConfigDownloadFinish, file=" + file);
                if (file == null) {
                    AlipayEnginePrepareHelper.this.sProgressHolder.finish(false);
                }
            }

            @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine.SilentOperationAlipayEngineElement.SilentOperationListener
            public void onApkDownloadStart() {
                Logger.t(AlipayEnginePrepareHelper.TAG).d("onApkDownloadStart");
                AlipayEnginePrepareHelper.this.sProgressHolder.startNext();
            }

            @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine.SilentOperationAlipayEngineElement.SilentOperationListener
            public void onApkDownloadProgress(long totalBytes, long downloadedBytes, long speedPerSecond) {
                float f = totalBytes != 0 ? ((float) downloadedBytes) / ((float) totalBytes) : 0.0f;
                long j = speedPerSecond != 0 ? (totalBytes - downloadedBytes) / speedPerSecond : 0L;
                Logger.t(AlipayEnginePrepareHelper.TAG).d("onApkDownloadProgress, progress=" + f + " remain=" + j);
                AlipayEnginePrepareHelper.this.sProgressHolder.setCurrentTask(1);
                AlipayEnginePrepareHelper.this.sProgressHolder.processCurrentProgress(f, j * 1000);
            }

            @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine.SilentOperationAlipayEngineElement.SilentOperationListener
            public void onApkDownloadFinish(File file) {
                Logger.t(AlipayEnginePrepareHelper.TAG).d("onApkDownloadFinish, file=" + file);
                if (file == null) {
                    AlipayEnginePrepareHelper.this.sProgressHolder.finish(false);
                }
            }

            @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine.SilentOperationAlipayEngineElement.SilentOperationListener
            public void onApkInstallStart() {
                Logger.t(AlipayEnginePrepareHelper.TAG).d("onApkInstallStart");
                AlipayEnginePrepareHelper.this.sProgressHolder.startNext();
            }

            @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine.SilentOperationAlipayEngineElement.SilentOperationListener
            public void onApkInstallProgress(float progress) {
                Logger.t(AlipayEnginePrepareHelper.TAG).d("onApkInstallProgress, progress=" + progress);
                AlipayEnginePrepareHelper.this.sProgressHolder.setCurrentTask(2);
                AlipayEnginePrepareHelper.this.sProgressHolder.processCurrentProgress(progress, Math.round((1.0f - progress) * 10000.0f));
            }

            @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine.SilentOperationAlipayEngineElement.SilentOperationListener
            public void onApkInstallFinish(boolean success) {
                Logger.t(AlipayEnginePrepareHelper.TAG).d("onApkInstallFinished, success=" + success);
                if (success) {
                    return;
                }
                AlipayEnginePrepareHelper.this.sProgressHolder.finish(false);
            }

            @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine.SilentOperationAlipayEngineElement.SilentOperationListener
            public void onInitServiceStart() {
                Logger.t(AlipayEnginePrepareHelper.TAG).d("onInitServiceStart");
                AlipayEnginePrepareHelper.this.sProgressHolder.startNext();
            }

            @Override // com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine.SilentOperationAlipayEngineElement.SilentOperationListener
            public void onInitServiceCompleted(boolean success) {
                Logger.t(AlipayEnginePrepareHelper.TAG).d("onInitServiceCompleted , success = " + success);
                AlipayEnginePrepareHelper.this.sProgressHolder.finish(success);
            }
        };
    }

    /* loaded from: classes2.dex */
    private static final class SingletonHolder {
        private static final AlipayEnginePrepareHelper instance = new AlipayEnginePrepareHelper();

        private SingletonHolder() {
        }
    }

    public static AlipayEnginePrepareHelper getInstance() {
        return SingletonHolder.instance;
    }

    public /* synthetic */ void lambda$new$0$AlipayEnginePrepareHelper(int i, MiniProgramResponse miniProgramResponse) {
        if (i != 3) {
            return;
        }
        if (miniProgramResponse.getCode() == 3 || miniProgramResponse.getCode() == 4) {
            PrepareProgress checkState = checkState(this.mAlipayEngineDownloadUrl, this.mAlipayEnginePackageName);
            this.sProgressHolder.startProgress();
            this.sProgressHolder.setCurrentTask(checkState.mTaskIndex);
            Logger.t(TAG).d("IMiniProgramListener    type = " + i);
            this.sProgressHolder.processCurrentProgress(checkState.mProgress, checkState.mRemainTime);
        }
    }

    public void init() {
        XuiMgrHelper.get().addMiniProgramListener(this.mIMiniProgramListener);
        SilentInstallHelper.setAlipayListener(this.mAlipayListener);
    }

    public void release() {
        XuiMgrHelper.get().removeMiniProgramListener(this.mIMiniProgramListener);
        this.sProgressHolder.setOnProgressChangeListener(null);
        SilentInstallHelper.setAlipayListener(null);
    }

    public static boolean isAlipayAppOperation(String packageName) {
        return packageName.toLowerCase().equals(ALIPAY_MINI_PROGRAM_NEW_PACKAGE_NAME.toLowerCase()) || packageName.toLowerCase().equals(ALIPAY_MINI_PROGRAM_PACKAGE_NAME.toLowerCase());
    }

    private PrepareProgress checkState(String downloadUrl, String packageName) {
        PrepareProgress prepareProgress = new PrepareProgress();
        if (SilentInstallHelper.isPackageInstalled(packageName)) {
            Logger.t(TAG).d("package name " + packageName + " is installed");
            XuiMgrHelper.get().initAromeService();
            prepareProgress.mTaskIndex = 2;
            prepareProgress.mRemainTime = 2000;
            prepareProgress.mProgress = 0;
        } else {
            Logger.t(TAG).d("package name " + packageName + " not install");
            if (!isMiniEngineDownload(downloadUrl)) {
                Logger.t(TAG).d("package name " + packageName + " not downloading, download url = " + downloadUrl);
                prepareProgress.mTaskIndex = 0;
                prepareProgress.mProgress = 1;
                prepareProgress.mRemainTime = 120000;
                SilentInstallHelper.tryRunAlipayOperation();
            } else {
                Logger.t(TAG).d("package name " + packageName + " downloading , download url = " + downloadUrl);
                prepareProgress.mTaskIndex = 0;
                prepareProgress.mProgress = 0;
                prepareProgress.mRemainTime = 0;
            }
        }
        return prepareProgress;
    }

    private void isMiniServiceInit() {
        XuiMgrHelper.get().initAromeService();
    }

    private int getDownloadStatus(String downloadUrl) {
        return IXpDownloader.Factory.get().getDownloadStatus(downloadUrl);
    }

    private boolean isMiniEngineDownload(String downloadUrl) {
        int downloadStatus = getDownloadStatus(downloadUrl);
        return downloadStatus == 1 || downloadStatus == 2;
    }

    public boolean isEngineDownloadComplete(String downloadUrl) {
        return getDownloadStatus(downloadUrl) == 4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class PrepareProgress {
        public int mProgress;
        public int mRemainTime;
        public int mTaskIndex;

        private PrepareProgress() {
        }
    }
}
