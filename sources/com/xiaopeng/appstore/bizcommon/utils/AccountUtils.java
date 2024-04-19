package com.xiaopeng.appstore.bizcommon.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.entities.UserInfo;
import com.xiaopeng.appstore.bizcommon.logic.IAccountUpdate;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.appstore.xpcommon.CarUtils;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class AccountUtils implements OnAccountsUpdateListener {
    public static final String ACCOUNT_TYPE_XP_VEHICLE = "com.xiaopeng.accountservice.ACCOUNT_TYPE_XP_VEHICLE";
    public static final String ACCOUNT_USER_TYPE = "user_type";
    private static final String CARACCOUNT_NAME = "com.xiaopeng.caraccount";
    public static boolean DEBUG = false;
    private static final String DIALOG_QR_NAME = "com.xiaopeng.xvs.account.ACTION_ACCOUNT_DIALOG_QR_REQUEST";
    private static final String TAG = "AccountUtils";
    public static final String USER_DATA_EXTRA_AVATAR = "avatar";
    public static final String USER_DATA_EXTRA_UID = "uid";
    public static final String USER_DATA_EXTRA_UPDATE = "update";
    private static volatile AccountUtils sInstance;
    private AccountManager mAccountManager;
    private Activity mCurrentActivity;
    private UserInfo mUserInfo;
    private List<IAccountUpdate> mIAccountUpdateList = new ArrayList();
    private boolean mIsInit = false;
    private final Object mInitLock = new Object();

    private AccountUtils() {
    }

    public static AccountUtils get() {
        if (sInstance == null) {
            synchronized (AccountUtils.class) {
                if (sInstance == null) {
                    sInstance = new AccountUtils();
                }
            }
        }
        return sInstance;
    }

    public UserInfo getUserInfo() {
        return this.mUserInfo;
    }

    private void initAccountServices(Context context) {
        synchronized (this.mInitLock) {
            this.mAccountManager = (AccountManager) context.getSystemService("account");
            this.mUserInfo = new UserInfo();
            getCurrentAccountInfo();
            registerAccountUpdateListener();
            this.mIsInit = true;
        }
    }

    public void unInitAccount() {
        synchronized (this.mInitLock) {
            unRegisterAccountUpdateListener();
            this.mCurrentActivity = null;
            this.mIsInit = false;
        }
    }

    private void registerAccountUpdateListener() {
        AccountManager accountManager = this.mAccountManager;
        if (accountManager != null) {
            accountManager.addOnAccountsUpdatedListener(this, null, true, new String[]{ACCOUNT_TYPE_XP_VEHICLE});
        }
    }

    private void unRegisterAccountUpdateListener() {
        AccountManager accountManager = this.mAccountManager;
        if (accountManager != null) {
            accountManager.removeOnAccountsUpdatedListener(this);
        }
    }

    private void handlerAccountUpdate() {
        getCurrentAccountInfo();
        dispatchAccountUpdate(this.mUserInfo);
    }

    private void dispatchAccountUpdate(UserInfo userInfo) {
        for (IAccountUpdate iAccountUpdate : this.mIAccountUpdateList) {
            if (iAccountUpdate != null) {
                iAccountUpdate.updateAccount(userInfo);
            }
        }
    }

    public void registerAccountObserver(IAccountUpdate accountUpdate) {
        if (this.mIAccountUpdateList.contains(accountUpdate)) {
            return;
        }
        this.mIAccountUpdateList.add(accountUpdate);
    }

    public void unRegisterAccountObserver(IAccountUpdate accountUpdate) {
        this.mIAccountUpdateList.remove(accountUpdate);
    }

    public Account getCurrentAccountInfo() {
        Account[] accountsByType = this.mAccountManager.getAccountsByType(ACCOUNT_TYPE_XP_VEHICLE);
        if (accountsByType.length > 0) {
            Account account = accountsByType[0];
            printf("getCurrentAccountInfo accounts.length=" + accountsByType.length);
            try {
                String userData = this.mAccountManager.getUserData(account, ACCOUNT_USER_TYPE);
                String userData2 = this.mAccountManager.getUserData(account, USER_DATA_EXTRA_UPDATE);
                String userData3 = this.mAccountManager.getUserData(account, USER_DATA_EXTRA_UID);
                this.mUserInfo.setData(Integer.parseInt(userData2), Integer.parseInt(userData), userData3, account.name);
                printf("getCurrentAccountInfo uid=;是否是更新=" + userData2 + ";账号类型=" + userData + ";uid=" + userData3);
            } catch (Exception e) {
                printf("getCurrentAccountInfo Exception=" + e.getMessage());
            }
            return account;
        }
        this.mUserInfo.setData(0, 0, UserInfo.getDefaultUserId(), "");
        printf("getCurrentAccountInfo account is empty");
        return null;
    }

    public boolean isLogin() {
        UserInfo userInfo;
        return CarUtils.isEURegion() || DEBUG || !((userInfo = this.mUserInfo) == null || userInfo.getUserType() == 0);
    }

    public boolean isLoginSync(Context context) {
        Logger.t(TAG).i("isLoginSync IN", new Object[0]);
        tryInit(context);
        UserInfo userInfo = this.mUserInfo;
        boolean z = (userInfo == null || userInfo.getUserType() == 0) ? false : true;
        Logger.t(TAG).i("isLoginSync END:" + z, new Object[0]);
        return DEBUG || z || CarUtils.isEURegion();
    }

    public void tryInit(Context context) {
        synchronized (this.mInitLock) {
            if (!this.mIsInit) {
                Logger.t(TAG).i("Not init yet. Init now:" + context, new Object[0]);
                initAccountServices(context);
            }
        }
    }

    private void goLogin() {
        Activity activity = this.mCurrentActivity;
        if (activity != null) {
            this.mAccountManager.addAccount(ACCOUNT_TYPE_XP_VEHICLE, null, null, null, activity, null, null);
        } else {
            Logger.t(TAG).i("login failed --not init -- activity:" + this.mCurrentActivity, new Object[0]);
        }
    }

    public void goLoginByService(Context context, int screenShareId) {
        Logger.t(TAG).i("goLoginByService, screen_id:" + screenShareId, new Object[0]);
        Intent intent = new Intent();
        intent.setPackage("com.xiaopeng.caraccount");
        intent.setAction(DIALOG_QR_NAME);
        intent.putExtra("screen_id", screenShareId);
        context.startService(intent);
    }

    public void goLoginByService() {
        goLoginByService(Utils.getApp(), XuiMgrHelper.get().getShareId());
    }

    private void printf(String msg) {
        Logger.t(TAG).d(msg);
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.mCurrentActivity = currentActivity;
    }

    @Override // android.accounts.OnAccountsUpdateListener
    public void onAccountsUpdated(Account[] accounts) {
        printf("onAccountsUpdated accounts.length=" + accounts.length);
        handlerAccountUpdate();
    }
}
