package com.xiaopeng.appstore.bizcommon.logic;

import android.content.Context;
import android.os.Build;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.entities.AgreementStateEntity;
import com.xiaopeng.appstore.bizcommon.entities.DownloadStatus;
import com.xiaopeng.appstore.bizcommon.entities.UserInfo;
import com.xiaopeng.appstore.bizcommon.entities.db.AgreementStateDao;
import com.xiaopeng.appstore.bizcommon.entities.db.XpAppStoreDatabase;
import com.xiaopeng.appstore.bizcommon.utils.AccountUtils;
import com.xiaopeng.appstore.bizcommon.utils.DatabaseUtils;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.appstore.xpcommon.CarUtils;
import com.xiaopeng.appstore.xpcommon.privacy.PrivacyUtils;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class AgreementManager implements IAccountUpdate {
    private static volatile AgreementManager sInstance;
    private DownloadStatus mDownloadStatus;
    private final String TAG = "AgreementManager";
    private final List<IAgreementViewListener> mIAgreementViewListeners = new ArrayList();

    public boolean ignoreLoginAndAgree(int state) {
        return state == 4 || state == 10001 || state == 5 || state == 1 || state == 2;
    }

    private AgreementManager() {
        AccountUtils.get().registerAccountObserver(this);
    }

    public static AgreementManager get() {
        if (sInstance == null) {
            synchronized (AgreementManager.class) {
                if (sInstance == null) {
                    sInstance = new AgreementManager();
                }
            }
        }
        return sInstance;
    }

    private boolean isNeedReconfirmAgreement() {
        return PrivacyUtils.isNeedReconfirm(Utils.getApp());
    }

    private void setAccountAgreement(final boolean hasAgreement) {
        final UserInfo userInfo = AccountUtils.get().getUserInfo();
        if (userInfo == null || !userInfo.isLegal(userInfo.getUserId())) {
            return;
        }
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AgreementManager$dLSLVLsABigBu5dBsIXwos2veG0
            @Override // java.lang.Runnable
            public final void run() {
                AgreementManager.this.lambda$setAccountAgreement$1$AgreementManager(userInfo, hasAgreement);
            }
        });
    }

    public /* synthetic */ void lambda$setAccountAgreement$1$AgreementManager(UserInfo userInfo, boolean z) {
        final AgreementStateDao agreementStateDao = XpAppStoreDatabase.getInstance().getAgreementStateDao();
        final AgreementStateEntity agreementStateEntity = new AgreementStateEntity(userInfo.getUserId());
        if (z) {
            agreementStateEntity.setAgreed(2);
        } else {
            agreementStateEntity.setAgreed(0);
        }
        Logger.t("AgreementManager").d("insert into db, entity=" + agreementStateEntity);
        DatabaseUtils.tryExecute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AgreementManager$DCFx2E5j5pYNuM-iD5CNEYxDP1M
            @Override // java.lang.Runnable
            public final void run() {
                AgreementStateDao.this.insert(agreementStateEntity);
            }
        });
    }

    public void tryExecute(Context context, int state, Runnable onSucceed, boolean async) {
        Logger.t("AgreementManager").d("tryExecute==>" + state);
        this.mDownloadStatus = null;
        if (ignoreLoginAndAgree(state)) {
            executeRunnable(onSucceed, async);
        } else if (!AccountUtils.get().isLogin() && Build.VERSION.SDK_INT < 31) {
            Logger.t("AgreementManager").d("tryExecute==> go Login");
            this.mDownloadStatus = new DownloadStatus(onSucceed, async, true);
            AccountUtils.get().goLoginByService();
        } else if (CarUtils.isEURegion()) {
            executeRunnable(onSucceed, async);
        } else {
            doDownloadInAgreement(onSucceed, async);
        }
    }

    private void doDownloadInAgreement(Runnable runnable, boolean async) {
        boolean isNeedReconfirmAgreement = isNeedReconfirmAgreement();
        Logger.t("AgreementManager").d("isNeedReconfirm==>" + isNeedReconfirmAgreement);
        if (isNeedReconfirmAgreement) {
            dispatchAgreementDialogListener(runnable, async);
        }
        executeRunnable(runnable, async);
    }

    private void executeRunnable(Runnable runnable, boolean async) {
        if (async) {
            AppExecutors.get().backgroundThread().execute(runnable);
        } else {
            runnable.run();
        }
    }

    public void agreement(Runnable runnable, boolean async) {
        setAccountAgreement(true);
        executeRunnable(runnable, async);
    }

    public void unRegisterAccountObserver() {
        AccountUtils.get().unRegisterAccountObserver(this);
    }

    public void registerAccountObserver() {
        AccountUtils.get().registerAccountObserver(this);
    }

    public void addAgreementViewListener(IAgreementViewListener agreementViewListener) {
        if (this.mIAgreementViewListeners.contains(agreementViewListener)) {
            return;
        }
        this.mIAgreementViewListeners.add(agreementViewListener);
    }

    public void removeAgreementViewListener(IAgreementViewListener agreementViewListener) {
        this.mIAgreementViewListeners.remove(agreementViewListener);
    }

    private void dispatchAgreementDialogListener(Runnable runnable, boolean async) {
        List<IAgreementViewListener> list = this.mIAgreementViewListeners;
        if (list != null) {
            for (IAgreementViewListener iAgreementViewListener : list) {
                iAgreementViewListener.onShowAgreementDialog(runnable, async);
            }
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.IAccountUpdate
    public void updateAccount(UserInfo userInfo) {
        boolean z = true;
        boolean z2 = (userInfo == null || userInfo.getUserType() == 0) ? false : true;
        Logger.t("AgreementManager").d("updateAccount==> " + z2);
        DownloadStatus downloadStatus = this.mDownloadStatus;
        if (downloadStatus == null || !downloadStatus.isGoLoginByDownload()) {
            z = false;
        }
        if (z2 && z) {
            doDownloadInAgreement(this.mDownloadStatus.getRunnable(), this.mDownloadStatus.isAsync());
        }
        if (z) {
            this.mDownloadStatus = null;
        }
    }
}
