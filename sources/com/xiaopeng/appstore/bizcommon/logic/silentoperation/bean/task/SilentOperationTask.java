package com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.task;

import android.os.Handler;
import android.os.HandlerThread;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.SilentCellBean;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationApplyConfigAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationClearResourceAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationDownloadAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationInstallAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationUninstallAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.SilentOperationElement;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.util.SilentInstallHelper;
import com.xiaopeng.appstore.bizcommon.utils.PackageUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
/* loaded from: classes2.dex */
public class SilentOperationTask {
    private static final String TAG = "SilentOperationTask";
    private final Handler mHandler;
    private ArrayBlockingQueue<SilentOperationElement> mSilentOperationElements;
    private HandlerThread mSilentOperationTaskThread = new HandlerThread("silent_operation_thread");

    public SilentOperationTask(SilentCellBean originTaskCellBean) {
        generateSilentOperationElement(originTaskCellBean);
        this.mSilentOperationTaskThread.start();
        this.mHandler = new Handler(this.mSilentOperationTaskThread.getLooper());
    }

    public void runTask() {
        this.mHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.task.-$$Lambda$SilentOperationTask$vQJA0GUDyHG7eyVA6mKYYOFLpxM
            @Override // java.lang.Runnable
            public final void run() {
                SilentOperationTask.this.lambda$runTask$0$SilentOperationTask();
            }
        });
        this.mSilentOperationTaskThread.quitSafely();
    }

    public /* synthetic */ void lambda$runTask$0$SilentOperationTask() {
        ArrayBlockingQueue<SilentOperationElement> arrayBlockingQueue = this.mSilentOperationElements;
        if (arrayBlockingQueue != null) {
            SilentOperationElement poll = arrayBlockingQueue.poll();
            while (poll != null) {
                Logger.t(TAG).d("executing element = " + poll);
                executeElementDelegate(poll);
                poll = this.mSilentOperationElements.poll();
            }
            return;
        }
        Logger.t(TAG).d("There is no dependencies here");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void executeElementDelegate(SilentOperationElement element) {
        element.execute();
    }

    protected SilentOperationElement generateElementDelegate() {
        return new SilentOperationElement();
    }

    private void generateSilentOperationElement(SilentCellBean originTaskCellBean) {
        ArrayList<SilentCellBean> silentCellBeans = originTaskCellBean.getSilentCellBeans();
        if (silentCellBeans == null || silentCellBeans.isEmpty()) {
            return;
        }
        this.mSilentOperationElements = new ArrayBlockingQueue<>(silentCellBeans.size());
        Iterator<SilentCellBean> it = silentCellBeans.iterator();
        while (it.hasNext()) {
            SilentCellBean next = it.next();
            SilentOperationElement generateElementDelegate = generateElementDelegate();
            int operation = next.getOperation();
            if (operation != 1) {
                if (operation != 2) {
                    if (operation == 3) {
                        if (SilentInstallHelper.isPackageInstalled(next.getOperationPackageName())) {
                            if (PackageUtils.isAppUpdate(next.getOperationAppVersionCode(), next.getOperationPackageName())) {
                                generateActionForDownloadInstall(generateElementDelegate, next);
                            } else {
                                Logger.t(TAG).d("upgrade operation : package name %1$s  version = %2$s is installed , skip generate download and install action", next.getOperationPackageName(), next.getOperationAppVersionCode());
                            }
                        } else {
                            Logger.t(TAG).d("upgrade operation : package name %1$s not install , generate download and install action", next.getOperationPackageName(), next.getOperationAppVersionCode());
                            generateActionForDownloadInstall(generateElementDelegate, next);
                        }
                    }
                } else if (SilentInstallHelper.isPackageInstalled(next.getOperationPackageName())) {
                    generateElementDelegate.offer(new SilentOperationUninstallAction(next.getOperationPackageName()));
                } else {
                    Logger.t(TAG).d("uninstall operation : package name %1$s is not install, skip generate uninstall action");
                }
            } else if (!SilentInstallHelper.isPackageInstalled(next.getOperationPackageName())) {
                generateActionForDownloadInstall(generateElementDelegate, next);
            } else {
                Logger.t(TAG).d("install operation : package name %1$s is installed , skip generate download and install action", next.getOperationPackageName());
            }
            this.mSilentOperationElements.offer(generateElementDelegate);
        }
    }

    protected void generateActionForDownloadInstall(SilentOperationElement operationElement, SilentCellBean silentCellBean) {
        String configUrl = silentCellBean.getConfigUrl();
        if (configUrl != null) {
            SilentOperationDownloadAction silentOperationDownloadAction = new SilentOperationDownloadAction(configUrl);
            SilentOperationApplyConfigAction silentOperationApplyConfigAction = new SilentOperationApplyConfigAction(silentCellBean.getOperationPackageName());
            SilentOperationClearResourceAction silentOperationClearResourceAction = new SilentOperationClearResourceAction();
            operationElement.offer(silentOperationDownloadAction);
            operationElement.offer(silentOperationApplyConfigAction);
            operationElement.offer(silentOperationClearResourceAction);
        }
        String apkFileUrl = silentCellBean.getApkFileUrl();
        if (apkFileUrl != null) {
            SilentOperationDownloadAction silentOperationDownloadAction2 = new SilentOperationDownloadAction(apkFileUrl);
            silentOperationDownloadAction2.setTargetFileMd5(silentCellBean.getMd5());
            SilentOperationInstallAction silentOperationInstallAction = new SilentOperationInstallAction(silentCellBean.getOperationPackageName());
            SilentOperationClearResourceAction silentOperationClearResourceAction2 = new SilentOperationClearResourceAction();
            operationElement.offer(silentOperationDownloadAction2);
            operationElement.offer(silentOperationInstallAction);
            operationElement.offer(silentOperationClearResourceAction2);
        }
    }
}
