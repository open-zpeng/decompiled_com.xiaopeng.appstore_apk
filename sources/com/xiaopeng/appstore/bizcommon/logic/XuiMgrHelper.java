package com.xiaopeng.appstore.bizcommon.logic;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.AbstractApplicationInitializer;
import com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloader;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.OSUtils;
import com.xiaopeng.appstore.storeprovider.store.StoreResourceProvider;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
import com.xiaopeng.view.WindowManagerFactory;
import com.xiaopeng.xuimanager.XUIManager;
import com.xiaopeng.xuimanager.XUIServiceNotConnectedException;
import com.xiaopeng.xuimanager.xapp.MiniProgramResponse;
import com.xiaopeng.xuimanager.xapp.XAppManager;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes2.dex */
public class XuiMgrHelper {
    public static final int SHARE_ID_PRIMARY = 0;
    public static final int SHARE_ID_SECONDARY = 1;
    private static final String TAG = "XuiMgrHelper";
    private static volatile XuiMgrHelper sInstance;
    private XAppManager mAppManager;
    private Context mContext;
    private StoreResourceProvider mStoreResProvider;
    private WindowManagerFactory mWm;
    private XUIManager mXUIManager;
    private String mCurrentLaunchMiniAppId = "";
    private final List<IMiniProgramListener> mIMiniProgramListener = new CopyOnWriteArrayList();
    private final XAppManager.XMiniProgEventListener mXMiniProgEventListener = new XAppManager.XMiniProgEventListener() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$XuiMgrHelper$3ySd2UTWJfloieBYCqHXLnIYtCM
        public final void onMiniProgCallBack(int i, MiniProgramResponse miniProgramResponse) {
            XuiMgrHelper.this.lambda$new$1$XuiMgrHelper(i, miniProgramResponse);
        }
    };
    private final OpenAppletTask mOpenAppletTask = new OpenAppletTask();
    private boolean mEnableApplet = false;

    public /* synthetic */ void lambda$new$1$XuiMgrHelper(final int i, final MiniProgramResponse miniProgramResponse) {
        if (i == 1) {
            Logger.t(TAG).i("mini program account login = true", new Object[0]);
            EventTrackingHelper.sendMolecast(PagesEnum.STORE_MINI_PROGRAM_LOGIN_STATE, EventEnum.MINI_PROGRAM_LOGIN_STATE, "1");
        } else if (i == 2) {
            Logger.t(TAG).i("mini program account login = false", new Object[0]);
            EventTrackingHelper.sendMolecast(PagesEnum.STORE_MINI_PROGRAM_LOGIN_STATE, EventEnum.MINI_PROGRAM_LOGIN_STATE, "2");
        }
        AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$XuiMgrHelper$V-E__ad4FLkMLCHHYV5JYe_dA4w
            @Override // java.lang.Runnable
            public final void run() {
                XuiMgrHelper.this.lambda$new$0$XuiMgrHelper(miniProgramResponse, i);
            }
        });
    }

    public /* synthetic */ void lambda$new$0$XuiMgrHelper(MiniProgramResponse miniProgramResponse, int i) {
        if (miniProgramResponse != null) {
            Logger.t(TAG).i("type:" + i + "  response:" + miniProgramResponse.toString(), new Object[0]);
            for (IMiniProgramListener iMiniProgramListener : this.mIMiniProgramListener) {
                if (iMiniProgramListener != null) {
                    iMiniProgramListener.onMiniProgramCallBack(i, miniProgramResponse);
                }
            }
        }
    }

    private XuiMgrHelper() {
    }

    public static XuiMgrHelper get() {
        if (sInstance == null) {
            synchronized (XuiMgrHelper.class) {
                if (sInstance == null) {
                    sInstance = new XuiMgrHelper();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context, boolean enableApplet) {
        Logger.t(TAG).i("init, ctx:" + context + ", enableApplet:" + enableApplet, new Object[0]);
        this.mContext = context;
        this.mEnableApplet = enableApplet;
        if (OSUtils.ATLEAST_Q) {
            this.mWm = WindowManagerFactory.create(context);
        }
        if (this.mXUIManager == null) {
            this.mXUIManager = XUIManager.createXUIManager(context, new ServiceConnection() { // from class: com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper.1
                @Override // android.content.ServiceConnection
                public void onServiceConnected(ComponentName name, IBinder service) {
                    Logger.t(XuiMgrHelper.TAG).d("XUI service connected.");
                    XuiMgrHelper.this.initAppManager();
                    XuiMgrHelper.this.initStoreResourceProvider();
                }

                @Override // android.content.ServiceConnection
                public void onServiceDisconnected(ComponentName name) {
                    Logger.t(XuiMgrHelper.TAG).w("XUI service disconnected.", new Object[0]);
                    XuiMgrHelper.this.releaseAppManager();
                    XuiMgrHelper.this.releaseResProvider();
                }
            });
        }
        this.mXUIManager.connect();
    }

    public void release() {
        Logger.t(TAG).d("release ...");
        XUIManager xUIManager = this.mXUIManager;
        if (xUIManager != null) {
            xUIManager.disconnect();
        }
        releaseAppManager();
    }

    public void setPackageSettings() {
        if (OSUtils.ATLEAST_Q) {
            if (this.mContext == null) {
                Logger.t(TAG).i("setPackageSettings, XUIMgrHelper not init yet", new Object[0]);
                return;
            }
            Logger.t(TAG).i("initAppStore", new Object[0]);
            Bundle bundle = new Bundle();
            bundle.putInt("flags", 512);
            this.mWm.setPackageSettings(this.mContext.getPackageName(), bundle);
        }
    }

    public int getShareId() {
        if (OSUtils.ATLEAST_Q) {
            Context context = this.mContext;
            if (context == null) {
                Logger.t(TAG).i("getShareId, XUIMgrHelper not init yet", new Object[0]);
                return 0;
            }
            int sharedId = this.mWm.getSharedId(context.getPackageName());
            if (sharedId == -1) {
                return 0;
            }
            return sharedId;
        }
        return 0;
    }

    public boolean isPrimaryScreen() {
        return getShareId() == 0;
    }

    public StoreResourceProvider getStoreResProvider() {
        assertResServiceReady();
        return this.mStoreResProvider;
    }

    private void assertResServiceReady() {
        if (this.mStoreResProvider == null) {
            throw new IllegalStateException("ResProvide is null, XUIService maybe not connected?");
        }
    }

    public void addMiniProgramListener(IMiniProgramListener miniProgramListener) {
        if (this.mIMiniProgramListener.contains(miniProgramListener)) {
            return;
        }
        this.mIMiniProgramListener.add(miniProgramListener);
    }

    public void removeMiniProgramListener(IMiniProgramListener miniProgramListener) {
        this.mIMiniProgramListener.remove(miniProgramListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initAppManager() {
        if (isResProcess()) {
            Logger.t(TAG).i("initAppManager return since process in resource", new Object[0]);
            return;
        }
        try {
            XAppManager xAppManager = (XAppManager) this.mXUIManager.getXUIServiceManager("xapp");
            this.mAppManager = xAppManager;
            if (this.mEnableApplet) {
                if (xAppManager != null) {
                    xAppManager.registerMiniProgListener(this.mXMiniProgEventListener);
                    this.mAppManager.initService();
                } else {
                    Logger.t(TAG).w("initAppManager error, cannot get app manager.", new Object[0]);
                }
            }
        } catch (XUIServiceNotConnectedException e) {
            Logger.t(TAG).d("initAppManager " + e.getMessage());
        }
    }

    private boolean isResProcess() {
        return Application.getProcessName().equals(AbstractApplicationInitializer.PROCESS_RESOURCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initStoreResourceProvider() {
        if (isResProcess()) {
            Logger.t(TAG).i("initStoreResourceProvider return since process in resource", new Object[0]);
            return;
        }
        this.mStoreResProvider = new StoreResourceProvider(this.mContext);
        Logger.t(TAG).d("initStoreResourceProvider mStoreResProvider = " + this.mStoreResProvider);
        IXpDownloader.Factory.initAsync(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseResProvider() {
        IXpDownloader.Factory.release();
    }

    public void initAromeService() {
        try {
            XAppManager xAppManager = this.mAppManager;
            if (xAppManager != null) {
                xAppManager.initService();
            } else {
                Logger.t(TAG).d("initAromeService mAppManager is null");
            }
        } catch (XUIServiceNotConnectedException e) {
            e.printStackTrace();
        }
    }

    public void loginMini() {
        try {
            XAppManager xAppManager = this.mAppManager;
            if (xAppManager != null) {
                xAppManager.loginAccount();
            } else {
                Logger.t(TAG).d("loginMini mAppManager is null");
            }
        } catch (XUIServiceNotConnectedException e) {
            Logger.t(TAG).d("loginMini " + e.getMessage());
        }
    }

    public void logoutMini() {
        try {
            XAppManager xAppManager = this.mAppManager;
            if (xAppManager != null) {
                xAppManager.logoutAccount();
            } else {
                Logger.t(TAG).d("logoutMini mAppManager is null");
            }
        } catch (XUIServiceNotConnectedException e) {
            Logger.t(TAG).d("logoutMini " + e.getMessage());
        }
    }

    public void requestLoginInfo() {
        if (this.mAppManager != null) {
            AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$XuiMgrHelper$lwzjtPXeiKiQHRoIvbtF2N-1VBY
                @Override // java.lang.Runnable
                public final void run() {
                    XuiMgrHelper.this.lambda$requestLoginInfo$2$XuiMgrHelper();
                }
            });
        } else {
            Logger.t(TAG).d("requestLoginInfo mAppManager is null");
        }
    }

    public /* synthetic */ void lambda$requestLoginInfo$2$XuiMgrHelper() {
        try {
            this.mAppManager.requestLoginInfo();
        } catch (XUIServiceNotConnectedException e) {
            Logger.t(TAG).d("requestLoginInfo " + e.getMessage());
        }
    }

    public void exitMini() {
        try {
            XAppManager xAppManager = this.mAppManager;
            if (xAppManager != null) {
                xAppManager.exitApp(this.mCurrentLaunchMiniAppId);
            } else {
                Logger.t(TAG).d("exitMini mAppManager is null");
            }
        } catch (XUIServiceNotConnectedException e) {
            Logger.t(TAG).d("exitMini " + e.getMessage());
        }
    }

    public void preLoadMini(boolean isPreload) {
        try {
            XAppManager xAppManager = this.mAppManager;
            if (xAppManager != null) {
                xAppManager.preloadApp(this.mCurrentLaunchMiniAppId, false);
            } else {
                Logger.t(TAG).d("preLoadMini mAppManager is null");
            }
        } catch (XUIServiceNotConnectedException e) {
            Logger.t(TAG).d("preloadApp " + e.getMessage());
        }
    }

    public void startLaunchMiniAsync(String appId) {
        String isOpening = this.mOpenAppletTask.isOpening();
        if (!TextUtils.isEmpty(isOpening)) {
            Logger.t(TAG).i("startLaunchMiniAsync ignore, opening:" + isOpening, new Object[0]);
            return;
        }
        this.mOpenAppletTask.setAppId(appId);
        AppExecutors.get().backgroundThread().execute(this.mOpenAppletTask);
    }

    public boolean startLaunchMini(String appId) {
        try {
            this.mCurrentLaunchMiniAppId = appId;
            XAppManager xAppManager = this.mAppManager;
            if (xAppManager != null) {
                xAppManager.startMiniProgram(appId, "", (Map) null);
            } else {
                Logger.t(TAG).d("startLaunchMini mAppManager is null");
            }
            return true;
        } catch (XUIServiceNotConnectedException e) {
            Logger.t(TAG).d("startLaunchMini " + e.getMessage());
            return true;
        }
    }

    public boolean requestMiniList(String alipayVersion) {
        try {
            XAppManager xAppManager = this.mAppManager;
            if (xAppManager != null) {
                xAppManager.requestMiniList(alipayVersion);
            } else {
                Logger.t(TAG).d("requestMiniList mAppManager is null");
            }
            return true;
        } catch (XUIServiceNotConnectedException e) {
            Logger.t(TAG).d("requestMiniList " + e.getMessage());
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseAppManager() {
        XAppManager xAppManager = this.mAppManager;
        if (xAppManager != null) {
            try {
                xAppManager.unregisterMiniProgListener(this.mXMiniProgEventListener);
            } catch (XUIServiceNotConnectedException e) {
                Logger.t(TAG).d("releaseAppManager " + e.getMessage());
            }
        }
    }

    public boolean setAppConfig(String packageName, String configStr) {
        if (this.mAppManager != null) {
            try {
                Logger.t(TAG).i("setAppConfig: execute config=" + configStr, new Object[0]);
                this.mAppManager.onAppConfigUpload(packageName, configStr);
                return true;
            } catch (XUIServiceNotConnectedException e) {
                Logger.t(TAG).e(e, "setAppConfig: xui service not connected. pn=%s, msg=%s", packageName, e.getMessage());
            }
        } else {
            Logger.t(TAG).i("setAppConfig: appMgr is null. config=%s", configStr);
        }
        return false;
    }

    public boolean startXPApp(String packageName) {
        if (this.mAppManager != null) {
            Logger.t(TAG).i("startXPApp: packageName=" + packageName, new Object[0]);
            this.mAppManager.startXpApp(packageName, (Intent) null);
            return true;
        }
        Logger.t(TAG).i("startXPApp: appMgr is null. packageName=%s", packageName);
        return false;
    }

    /* loaded from: classes2.dex */
    public static class OpenAppletTask implements Runnable {
        private static final String TAG = "OpenAppletTask";
        private volatile String mAppId;

        public void setAppId(String appId) {
            Logger.t(TAG).i("setAppId:" + appId + " old:" + this.mAppId, new Object[0]);
            this.mAppId = appId;
        }

        public String isOpening() {
            return this.mAppId;
        }

        @Override // java.lang.Runnable
        public void run() {
            String str = this.mAppId;
            Logger.t(TAG).i("run start:" + str, new Object[0]);
            synchronized (this) {
                if (!TextUtils.isEmpty(str)) {
                    XuiMgrHelper.get().startLaunchMini(str);
                } else {
                    Logger.t(TAG).w("Cannot open, id is empty:" + str, new Object[0]);
                }
                this.mAppId = null;
            }
            Logger.t(TAG).i("run end:" + str, new Object[0]);
        }
    }
}
