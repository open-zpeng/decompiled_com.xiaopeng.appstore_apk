package com.xiaopeng.aar.server;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArraySet;
import com.xiaopeng.aar.Apps;
import com.xiaopeng.aar.BuildConfig;
import com.xiaopeng.aar.server.ipc.IpcServer;
import com.xiaopeng.aar.server.ipc.IpcServerManager;
import com.xiaopeng.aar.utils.HandlerThreadHelper;
import com.xiaopeng.aar.utils.LogUtils;
import com.xiaopeng.aar.utils.Utils;
import java.util.Arrays;
import java.util.Iterator;
/* loaded from: classes2.dex */
public class ServerManager {
    private static final String TAG = "SerMg";
    private boolean mInit;
    private LockHelper mLockHelper;
    private ServerConfig mServerConfig;
    private ServerListener mServerListener;
    private final ArraySet<String> mSubscribeModules;

    private ServerManager() {
        this.mSubscribeModules = new ArraySet<>();
        IpcServerManager.get().init();
        IpcServerManager.get().getIpc().setOnClientListener(new IpcServer.OnClientListener() { // from class: com.xiaopeng.aar.server.ServerManager.1
            @Override // com.xiaopeng.aar.server.ipc.IpcServer.OnClientListener
            public String onCall(int i, String str, String str2, String str3, byte[] bArr) {
                return ServerManager.this.call(i, str, str2, str3, bArr);
            }

            @Override // com.xiaopeng.aar.server.ipc.IpcServer.OnClientListener
            public void onClientDied() {
                LogUtils.w(ServerManager.TAG, "napa is dead !!! onUnSubscribe all module ");
                ServerConfig serverConfig = ServerManager.this.mServerConfig;
                if (serverConfig == null || !serverConfig.isAutoUnSubscribeWhenNaPaDied()) {
                    return;
                }
                synchronized (ServerManager.this.mSubscribeModules) {
                    Iterator it = ServerManager.this.mSubscribeModules.iterator();
                    while (it.hasNext()) {
                        ServerManager.this.unSubscribe((String) it.next());
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingletonHolder {
        private static final ServerManager INSTANCE = new ServerManager();

        private SingletonHolder() {
        }
    }

    public static ServerManager get() {
        return SingletonHolder.INSTANCE;
    }

    public synchronized void initConfig(ServerConfig serverConfig) {
        if (serverConfig == null) {
            LogUtils.w(TAG, "initConfig config is null !!!");
        } else if (this.mServerConfig != null) {
            LogUtils.w(TAG, "initConfig config already initialized !!!");
        } else {
            this.mServerConfig = serverConfig;
            if (serverConfig.isWaitInit()) {
                this.mLockHelper = new LockHelper(serverConfig.getWaitTimeout());
            }
            LogUtils.setLogTag("sdks-");
            LogUtils.setLogLevel(serverConfig.getLogLevel());
            LogUtils.i(TAG, "initConfig " + serverConfig.toString());
        }
    }

    public synchronized void init(final Context context) {
        if (this.mInit) {
            LogUtils.w(TAG, "init already initialized  !!!");
            return;
        }
        this.mInit = true;
        String appId = Apps.getAppId(context.getApplicationContext());
        if (appId == null) {
            LogUtils.e(TAG, "init appId is null !!!");
        } else {
            LogUtils.i(TAG, "init appId : " + appId + " ," + BuildConfig.BUILD_VERSION);
            LogUtils.setLogTag(appId + "-");
        }
        IpcServerManager.get().getIpc().setAppId(appId);
        LockHelper lockHelper = this.mLockHelper;
        if (lockHelper != null) {
            lockHelper.unLock();
        }
        new HandlerThreadHelper("server").postDelayed(new Runnable() { // from class: com.xiaopeng.aar.server.-$$Lambda$ServerManager$9KmYEkUr2PHchWuxDWa7zpNx7uM
            @Override // java.lang.Runnable
            public final void run() {
                ServerManager.this.lambda$init$0$ServerManager(context);
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$init$0$ServerManager(Context context) {
        Context applicationContext = context.getApplicationContext();
        ServerConfig serverConfig = this.mServerConfig;
        if (serverConfig != null && serverConfig.isKeepAlive()) {
            IpcServerManager.get().getIpc().keepAlive(applicationContext);
        }
        boolean isUserRelease = Utils.isUserRelease();
        if (!isUserRelease) {
            RunningConfig.get().init(applicationContext);
        }
        ServerConfig serverConfig2 = this.mServerConfig;
        if (serverConfig2 == null || !serverConfig2.isUseMock() || isUserRelease) {
            return;
        }
        MockTest.get().init(applicationContext);
    }

    public void setServerListener(ServerListener serverListener) {
        this.mServerListener = serverListener;
    }

    private void subscribes(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String[] split = str.split(";");
        LogUtils.d(TAG, "subscribes: " + Arrays.toString(split));
        for (String str2 : split) {
            subscribe(str2);
        }
    }

    private void subscribe(String str) {
        LogUtils.d(TAG, String.format("subscribe-- module:%s", str));
        ServerListener serverListener = this.mServerListener;
        if (serverListener != null) {
            synchronized (this.mSubscribeModules) {
                this.mSubscribeModules.add(str);
            }
            try {
                serverListener.onSubscribe(str);
            } catch (Exception e) {
                LogUtils.e(TAG, "onSubscribe exception=" + e.getMessage());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unSubscribe(String str) {
        LogUtils.d(TAG, String.format("unSubscribe-- module:%s", str));
        ServerListener serverListener = this.mServerListener;
        if (serverListener != null) {
            synchronized (this.mSubscribeModules) {
                this.mSubscribeModules.remove(str);
            }
            try {
                serverListener.onUnSubscribe(str);
            } catch (Exception e) {
                LogUtils.e(TAG, "onUnSubscribe exception=" + e.getMessage());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String call(int i, String str, String str2, String str3, byte[] bArr) {
        LockHelper lockHelper = this.mLockHelper;
        if (lockHelper != null) {
            lockHelper.checkLock();
        }
        Object[] objArr = new Object[5];
        objArr[0] = Integer.valueOf(i);
        objArr[1] = str;
        objArr[2] = str2;
        objArr[3] = str3;
        objArr[4] = bArr == null ? "" : Integer.valueOf(bArr.length);
        LogUtils.i(TAG, String.format("call-- type:%s ,module:%s ,method:%s ,param:%s ,blob-length:%s", objArr));
        ServerListener serverListener = this.mServerListener;
        if (serverListener == null) {
            LogUtils.e(TAG, "call listener is null");
            return null;
        } else if (i == 0) {
            try {
                return serverListener.onCall(str, str2, str3, bArr);
            } catch (Exception e) {
                LogUtils.e(TAG, "onCall exception=" + e.getMessage());
                return null;
            }
        } else {
            switch (i) {
                case 101:
                    subscribe(str);
                    return null;
                case 102:
                    unSubscribe(str);
                    return null;
                case 103:
                    subscribes(str);
                    return null;
                default:
                    return null;
            }
        }
    }

    public void send(String str, String str2, String str3, byte[] bArr) {
        send(false, str, str2, str3, bArr);
    }

    public void send(boolean z, String str, String str2, String str3, byte[] bArr) {
        if (z || !interceptEnable()) {
            Object[] objArr = new Object[5];
            objArr[0] = Boolean.valueOf(z);
            objArr[1] = str;
            objArr[2] = str2;
            objArr[3] = str3;
            objArr[4] = bArr != null ? Integer.valueOf(bArr.length) : "";
            LogUtils.i(TAG, String.format("send  ignoreSubscribe %s, module:%s ,msgId:%s ,data:%s ,blob-length:%s", objArr));
            IpcServerManager.get().getIpc().send(201, str, str2, str3, bArr);
        } else if (interceptSend(str)) {
            Object[] objArr2 = new Object[4];
            objArr2[0] = str;
            objArr2[1] = str2;
            objArr2[2] = str3;
            objArr2[3] = bArr != null ? Integer.valueOf(bArr.length) : "";
            LogUtils.w(TAG, String.format("send interceptSend module:%s ,msgId:%s ,data:%s ,blob-length:%s", objArr2));
        } else {
            Object[] objArr3 = new Object[4];
            objArr3[0] = str;
            objArr3[1] = str2;
            objArr3[2] = str3;
            objArr3[3] = bArr != null ? Integer.valueOf(bArr.length) : "";
            LogUtils.i(TAG, String.format("send  module:%s ,msgId:%s ,data:%s ,blob-length:%s", objArr3));
            IpcServerManager.get().getIpc().send(0, str, str2, str3, bArr);
        }
    }

    private boolean interceptEnable() {
        ServerConfig serverConfig = this.mServerConfig;
        return serverConfig != null && serverConfig.isInterceptSendWhenNotSubscribed();
    }

    private boolean interceptSend(String str) {
        synchronized (this.mSubscribeModules) {
            return !this.mSubscribeModules.contains(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class LockHelper {
        private static final int WAIT_MAX_TIMEOUT = 3000;
        private static final int WAIT_TIMEOUT = 100;
        private int mTimeout;
        private final Object mLock = new Object();
        private boolean mInit = false;

        LockHelper(int i) {
            this.mTimeout = 100;
            if (i > 0) {
                this.mTimeout = i;
            }
            if (this.mTimeout > 3000) {
                this.mTimeout = 3000;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unLock() {
            synchronized (this.mLock) {
                this.mInit = true;
                this.mLock.notifyAll();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void checkLock() {
            if (this.mInit) {
                return;
            }
            synchronized (this.mLock) {
                if (!this.mInit) {
                    LogUtils.i(ServerManager.TAG, "wait.......");
                    try {
                        this.mLock.wait(this.mTimeout);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
