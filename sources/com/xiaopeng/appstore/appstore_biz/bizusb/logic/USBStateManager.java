package com.xiaopeng.appstore.appstore_biz.bizusb.logic;

import android.content.IntentFilter;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.bizusb.datamodel.entities.LocalApkInfo;
import com.xiaopeng.appstore.appstore_biz.bizusb.logic.callback.IUSBJobCallback;
import com.xiaopeng.appstore.appstore_biz.bizusb.logic.callback.IUSBState;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
/* loaded from: classes2.dex */
public class USBStateManager implements IUSBState, IUSBJobCallback {
    public static final String TAG = "USBStateManager";
    private OnUsbMgrCallback mCallback;
    private boolean mShowEntry;
    private USBDriveBroadcast mUSBDriveBroadcast;
    private final Executor mSingleThreadExecutor = Executors.newSingleThreadExecutor();
    private final USBJobManager mJobManager = new USBJobManager(this);

    /* loaded from: classes2.dex */
    public interface OnUsbMgrCallback {
        void onHideUsbEntry();

        void onShowUsbEntry(boolean loading);
    }

    public List<LocalApkInfo> getAllApkList() {
        return this.mJobManager.getAllApkList();
    }

    public boolean isShowEntry() {
        return this.mShowEntry;
    }

    public void setCallback(OnUsbMgrCallback callback) {
        this.mCallback = callback;
    }

    public void loadUsbApp() {
        checkUSBState();
    }

    public void registerUSBState() {
        this.mSingleThreadExecutor.execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.bizusb.logic.-$$Lambda$USBStateManager$03tG5HB_eSDHe7B566EUey9v5CI
            @Override // java.lang.Runnable
            public final void run() {
                USBStateManager.this.registerUSBBroadcast();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerUSBBroadcast() {
        if (this.mUSBDriveBroadcast == null) {
            this.mUSBDriveBroadcast = new USBDriveBroadcast(this);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
        intentFilter.addAction("android.intent.action.MEDIA_EJECT");
        intentFilter.addDataScheme("file");
        Utils.getApp().registerReceiver(this.mUSBDriveBroadcast, intentFilter);
    }

    private void checkUSBState() {
        Executor backgroundThread = AppExecutors.get().backgroundThread();
        final USBJobManager uSBJobManager = this.mJobManager;
        Objects.requireNonNull(uSBJobManager);
        backgroundThread.execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.bizusb.logic.-$$Lambda$tCCeaU8grTQQs7bVuxFw9JL6bo8
            @Override // java.lang.Runnable
            public final void run() {
                USBJobManager.this.doCheckUSBDriveJob();
            }
        });
    }

    public void unRegisterUSBState() {
        this.mSingleThreadExecutor.execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.bizusb.logic.-$$Lambda$USBStateManager$l-UTxJnclowm01LuPp4HN8mTlmA
            @Override // java.lang.Runnable
            public final void run() {
                USBStateManager.this.unRegisterBroadcast();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unRegisterBroadcast() {
        if (this.mUSBDriveBroadcast != null) {
            Utils.getApp().unregisterReceiver(this.mUSBDriveBroadcast);
        }
        this.mUSBDriveBroadcast = null;
    }

    @Override // com.xiaopeng.appstore.appstore_biz.bizusb.logic.callback.IUSBJobCallback
    public void showLoadingView() {
        Logger.t(TAG).i("showLoadingView", new Object[0]);
        this.mShowEntry = true;
        OnUsbMgrCallback onUsbMgrCallback = this.mCallback;
        if (onUsbMgrCallback != null) {
            onUsbMgrCallback.onShowUsbEntry(true);
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.bizusb.logic.callback.IUSBJobCallback
    public void hiddenView() {
        Logger.t(TAG).i("hiddenView", new Object[0]);
        this.mShowEntry = false;
        OnUsbMgrCallback onUsbMgrCallback = this.mCallback;
        if (onUsbMgrCallback != null) {
            onUsbMgrCallback.onHideUsbEntry();
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.bizusb.logic.callback.IUSBJobCallback
    public void showView() {
        Logger.t(TAG).i("showView", new Object[0]);
        this.mShowEntry = true;
        OnUsbMgrCallback onUsbMgrCallback = this.mCallback;
        if (onUsbMgrCallback != null) {
            onUsbMgrCallback.onShowUsbEntry(false);
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.bizusb.logic.callback.IUSBState
    public void usbMounted(String filePath) {
        if (filePath != null) {
            try {
                this.mJobManager.doCheckUSBDriveJob(filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.bizusb.logic.callback.IUSBState
    public void usbEject() {
        USBJobManager uSBJobManager = this.mJobManager;
        if (uSBJobManager != null) {
            uSBJobManager.clearList();
        }
        USBJobManager uSBJobManager2 = this.mJobManager;
        if (uSBJobManager2 != null) {
            uSBJobManager2.setCancel(true);
        }
        hiddenView();
    }
}
