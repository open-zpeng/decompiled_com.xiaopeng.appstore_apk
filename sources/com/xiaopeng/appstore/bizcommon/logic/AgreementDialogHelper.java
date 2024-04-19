package com.xiaopeng.appstore.bizcommon.logic;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.appstore.xpcommon.CarUtils;
import com.xiaopeng.appstore.xpcommon.privacy.PrivacyUtils;
import com.xiaopeng.privacymanager.PrivacyDialogListener;
import com.xiaopeng.privacymanager.PrivacyServiceManager;
import com.xiaopeng.privacymanager.ProtocolType;
/* loaded from: classes2.dex */
public class AgreementDialogHelper {
    private static final String TAG = "AgreementDialogHelper";
    public static AgreementDialogHelper sInstance;
    private Runnable mAgreedContinueRunnable;
    private final Context mContext;
    private String mDialogId;
    private final PrivacyServiceManager mPrivacyServiceManager;
    private final ServiceConnection mServiceConnection = new ServiceConnection() { // from class: com.xiaopeng.appstore.bizcommon.logic.AgreementDialogHelper.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            Logger.t(AgreementDialogHelper.TAG).i("On connected, connectedContinue:" + AgreementDialogHelper.this.mConnectedContinue, new Object[0]);
            if (AgreementDialogHelper.this.mConnectedContinue != null) {
                AgreementDialogHelper.this.mConnectedContinue.run();
                AgreementDialogHelper.this.mConnectedContinue = null;
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            Logger.t(AgreementDialogHelper.TAG).i("On disconnected", new Object[0]);
            if (AgreementDialogHelper.this.mConnectedContinue != null) {
                AgreementDialogHelper.this.mConnectedContinue = null;
            }
        }
    };
    private final PrivacyDialogListener mPrivacyDialogListener = new PrivacyDialogListener() { // from class: com.xiaopeng.appstore.bizcommon.logic.AgreementDialogHelper.2
        @Override // com.xiaopeng.privacymanager.PrivacyDialogListener
        public void onDialogClosed(int type, boolean clickOnOK, int error) {
            Logger.t(AgreementDialogHelper.TAG).i("onDialogClosed clickOnOK:" + clickOnOK + ", continueRunnable:" + AgreementDialogHelper.this.mAgreedContinueRunnable, new Object[0]);
            AgreementDialogHelper.this.mDialogId = null;
            if (clickOnOK) {
                if (AgreementDialogHelper.this.mAgreedContinueRunnable != null) {
                    AgreementDialogHelper.this.mAgreedContinueRunnable.run();
                    AgreementDialogHelper.this.mAgreedContinueRunnable = null;
                }
            } else {
                AgreementDialogHelper.this.mAgreedContinueRunnable = null;
            }
            AgreementDialogHelper.this.disconnect();
        }
    };
    private Runnable mConnectedContinue = null;

    private AgreementDialogHelper(Context context) {
        this.mContext = context;
        this.mPrivacyServiceManager = new PrivacyServiceManager(context.getApplicationContext());
        connect();
    }

    public static synchronized AgreementDialogHelper getInstance(Context context) {
        AgreementDialogHelper agreementDialogHelper;
        synchronized (AgreementDialogHelper.class) {
            if (sInstance == null) {
                sInstance = new AgreementDialogHelper(context);
            }
            agreementDialogHelper = sInstance;
        }
        return agreementDialogHelper;
    }

    public static synchronized AgreementDialogHelper getInstance() {
        AgreementDialogHelper agreementDialogHelper;
        synchronized (AgreementDialogHelper.class) {
            agreementDialogHelper = getInstance(Utils.getApp());
        }
        return agreementDialogHelper;
    }

    public void resetAgreeContinue() {
        Logger.t(TAG).i("resetAgreeContinue, runnable:" + this.mAgreedContinueRunnable, new Object[0]);
        this.mAgreedContinueRunnable = null;
    }

    public void show(Runnable afterAgreed) {
        show(afterAgreed, false);
    }

    public void show(Runnable afterAgreed, boolean readOnly) {
        show(afterAgreed, XuiMgrHelper.get().getShareId(), readOnly);
    }

    public void show(final Runnable afterAgreed, final int screenShareId, final boolean readOnly) {
        Logger.t(TAG).d("show start, continueRunnable:" + afterAgreed + ", screen:" + screenShareId + ", readOnly:" + readOnly);
        PrivacyServiceManager privacyServiceManager = this.mPrivacyServiceManager;
        if (privacyServiceManager != null) {
            if (!privacyServiceManager.isConnected()) {
                Logger.t(TAG).d("privacy service NOT connected, connect now");
                connectAndContinue(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AgreementDialogHelper$yhx4LO39phDF_xp0CObGpH9uaQg
                    @Override // java.lang.Runnable
                    public final void run() {
                        AgreementDialogHelper.this.lambda$show$0$AgreementDialogHelper(afterAgreed, screenShareId, readOnly);
                    }
                });
                return;
            }
            Logger.t(TAG).d("privacy service connected");
            lambda$show$0$AgreementDialogHelper(afterAgreed, screenShareId, readOnly);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: showDialog */
    public void lambda$show$0$AgreementDialogHelper(Runnable continueRunnable, int screenShareId, boolean readOnly) {
        if (this.mPrivacyServiceManager.isConnected()) {
            int i = CarUtils.isEURegion() ? ProtocolType.EN_APP_STORE : 105;
            int i2 = screenShareId == 1 ? 128 : 0;
            if (readOnly) {
                this.mDialogId = this.mPrivacyServiceManager.showPrivacyDialog(i, i2, null);
                Logger.t(TAG).i("Show dialog readOnly:" + this.mDialogId + ", flags:" + i2, new Object[0]);
                return;
            }
            this.mAgreedContinueRunnable = continueRunnable;
            if (PrivacyUtils.isNeedReconfirm(this.mContext.getApplicationContext())) {
                if (this.mDialogId != null) {
                    Logger.t(TAG).d("mDialogId is not null, mDialogId = " + this.mDialogId);
                    return;
                }
                this.mDialogId = this.mPrivacyServiceManager.showPrivacyDialog(i, i2, this.mPrivacyDialogListener);
                Logger.t(TAG).i("Show dialog:" + this.mDialogId + ", flags:" + i2, new Object[0]);
                return;
            }
            Logger.t(TAG).i("Show dialog IGNORE, since already agreed:" + this.mAgreedContinueRunnable, new Object[0]);
            Runnable runnable = this.mAgreedContinueRunnable;
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    private void connect() {
        connectAndContinue(null);
    }

    private void connectAndContinue(Runnable connectedContinue) {
        PrivacyServiceManager privacyServiceManager = this.mPrivacyServiceManager;
        if (privacyServiceManager != null) {
            privacyServiceManager.connect(this.mServiceConnection);
        } else {
            Logger.t(TAG).w("connect error, mgr is null", new Object[0]);
        }
        setConnectedContinue(connectedContinue);
    }

    public void disconnect() {
        Logger.t(TAG).i("disconnect, dialogId:" + this.mDialogId + ", connectedContinue:" + this.mConnectedContinue + ", agreedContinue:" + this.mAgreedContinueRunnable, new Object[0]);
        PrivacyServiceManager privacyServiceManager = this.mPrivacyServiceManager;
        if (privacyServiceManager == null || !privacyServiceManager.isConnected()) {
            return;
        }
        this.mPrivacyServiceManager.disconnect();
    }

    public void dismiss() {
        Logger.t(TAG).d("dismiss dialog:" + this.mDialogId);
        if (this.mPrivacyServiceManager != null && needDismiss()) {
            if (this.mPrivacyServiceManager.isConnected()) {
                dismissDialog();
                return;
            }
            Logger.t(TAG).d("privacy service NOT connected, connect and dismiss.");
            connectAndContinue(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AgreementDialogHelper$OaedUQXqhd3aVJSFets4uxuxHh0
                @Override // java.lang.Runnable
                public final void run() {
                    AgreementDialogHelper.this.dismissDialog();
                }
            });
            return;
        }
        Logger.t(TAG).d("dismiss, ignore");
    }

    private boolean needDismiss() {
        return !TextUtils.isEmpty(this.mDialogId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissDialog() {
        if (this.mPrivacyServiceManager != null && !TextUtils.isEmpty(this.mDialogId)) {
            Logger.t(TAG).i("dismissDialog:" + this.mDialogId, new Object[0]);
            this.mPrivacyServiceManager.cancelPrivacyDialog(this.mDialogId);
            this.mDialogId = null;
            return;
        }
        Logger.t(TAG).w("dismissDialog error, mgr:" + this.mPrivacyServiceManager + ", id:" + this.mDialogId, new Object[0]);
    }

    private void setConnectedContinue(Runnable runnable) {
        if (this.mConnectedContinue != null) {
            Logger.t(TAG).w("setConnectedContinue, continue not run yet:" + this.mConnectedContinue, new Object[0]);
        }
        this.mConnectedContinue = runnable;
        Logger.t(TAG).d("ConnectedContinue set:" + runnable);
    }
}
