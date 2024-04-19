package com.xiaopeng.appstore.xpcommon;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.SystemClock;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appserver_common.NapaHandler;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.xuimanager.XUIManager;
import com.xiaopeng.xuimanager.XUIServiceNotConnectedException;
import com.xiaopeng.xuimanager.xapp.XAppManager;
/* loaded from: classes2.dex */
public class XUIClientManager {
    private static final long RELEASE_TIMEOUT = 60000;
    private static final String TAG = "XUIClientManager";
    private static final long WAIT_CONNECT_TIMEOUT = 10000;
    private volatile XAppManager mAppManager;
    private final Object mConnectLock;
    private volatile boolean mConnected;
    private volatile Handler mHandler;
    private final Object mHandlerInitLock;
    private volatile HandlerThread mHandlerThread;
    private volatile long mInitTime;
    private final Runnable mReleaseRunnable;
    private final XUIManager mXUIManager;

    /* loaded from: classes2.dex */
    static class SingletonHolder {
        private static final XUIClientManager sInstance = new XUIClientManager();

        SingletonHolder() {
        }
    }

    public static XUIClientManager get() {
        return SingletonHolder.sInstance;
    }

    private XUIClientManager() {
        this.mConnected = false;
        this.mConnectLock = new Object();
        this.mHandlerInitLock = new Object();
        this.mReleaseRunnable = new Runnable() { // from class: com.xiaopeng.appstore.xpcommon.-$$Lambda$XUIClientManager$gNfRBiDgZEycVzKi9ir5MB8eEcQ
            @Override // java.lang.Runnable
            public final void run() {
                XUIClientManager.this.tryRelease();
            }
        };
        this.mXUIManager = XUIManager.createXUIManager(Utils.getApp(), new ServiceConnection() { // from class: com.xiaopeng.appstore.xpcommon.XUIClientManager.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                Logger.t(XUIClientManager.TAG).i("XUI service connected. name:" + name + ", service:" + service, new Object[0]);
                XUIClientManager.this.init();
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                Logger.t(XUIClientManager.TAG).w("XUI service disconnected. name:" + name, new Object[0]);
                XUIClientManager.this.release();
            }
        });
    }

    public boolean applyAppConfig(String packageName, String configJson) {
        boolean z;
        cancelScheduler();
        synchronized (this.mConnectLock) {
            boolean z2 = this.mConnected;
            if (!z2) {
                connect();
                z2 = isConnectedWait();
            }
            z = false;
            if (z2 && this.mAppManager != null) {
                z = applyConfigInternal(packageName, configJson);
            } else {
                Logger.t(TAG).w("applyAppConfig error: connect fail, pn:" + packageName, new Object[0]);
            }
            scheduleRelease();
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void init() {
        Logger.t(TAG).i(NapaHandler.METHOD_INIT, new Object[0]);
        this.mInitTime = SystemClock.uptimeMillis();
        initAppManager();
        notifyConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void tryRelease() {
        if (SystemClock.uptimeMillis() - this.mInitTime < 60000) {
            Logger.t(TAG).i("release ignore, since release too fast after init.", new Object[0]);
            scheduleRelease();
            return;
        }
        release();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void release() {
        Logger.t(TAG).i("release", new Object[0]);
        XUIManager xUIManager = this.mXUIManager;
        if (xUIManager != null) {
            xUIManager.disconnect();
        }
        if (this.mHandlerThread != null) {
            this.mHandlerThread.quitSafely();
            this.mHandlerThread.interrupt();
        }
        if (this.mHandler != null) {
            this.mHandler = null;
        }
        releaseAppManager();
        notifyDisconnected();
    }

    private boolean applyConfigInternal(String packageName, String configJson) {
        if (this.mAppManager == null) {
            Logger.t(TAG).w("applyAppConfig error: appManager is null, pn:" + packageName, new Object[0]);
            return false;
        }
        Logger.t(TAG).i("applyAppConfig, pn:" + packageName + ", json:" + configJson, new Object[0]);
        try {
            this.mAppManager.onAppConfigUpload(packageName, configJson);
            return true;
        } catch (XUIServiceNotConnectedException e) {
            Logger.t(TAG).w("applyAppConfig ex:" + e + ", pn:" + packageName, new Object[0]);
            return false;
        }
    }

    private Handler getWorkHandler() {
        if (this.mHandler == null) {
            synchronized (this.mHandlerInitLock) {
                if (this.mHandler == null) {
                    this.mHandlerThread = new HandlerThread(TAG);
                    this.mHandlerThread.start();
                    this.mHandler = new Handler(this.mHandlerThread.getLooper());
                }
            }
        }
        return this.mHandler;
    }

    private void scheduleRelease() {
        getWorkHandler().postDelayed(this.mReleaseRunnable, 60000L);
    }

    private void cancelScheduler() {
        if (this.mHandler != null) {
            this.mHandler.removeCallbacks(this.mReleaseRunnable);
        }
    }

    private void connect() {
        try {
            this.mXUIManager.connect();
        } catch (Exception e) {
            Logger.t(TAG).w("connect ex:" + e, new Object[0]);
        }
    }

    private boolean isConnectedWait() {
        boolean z;
        synchronized (this.mConnectLock) {
            if (!this.mConnected) {
                try {
                    this.mConnectLock.wait(WAIT_CONNECT_TIMEOUT);
                } catch (InterruptedException e) {
                    Logger.t(TAG).w("waitConnected ex:" + e, new Object[0]);
                    Thread.currentThread().interrupt();
                }
            }
            z = this.mConnected;
        }
        return z;
    }

    boolean waitConnected() {
        synchronized (this.mConnectLock) {
            while (!this.mConnected) {
                try {
                    Logger.t(TAG).d("Waiting XUI connected");
                    this.mConnectLock.wait(WAIT_CONNECT_TIMEOUT);
                } catch (InterruptedException e) {
                    Logger.t(TAG).e("waitConnected error:" + e, new Object[0]);
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        }
        return true;
    }

    private void notifyConnected() {
        synchronized (this.mConnectLock) {
            this.mConnected = true;
            this.mConnectLock.notifyAll();
        }
    }

    private void notifyDisconnected() {
        synchronized (this.mConnectLock) {
            this.mConnected = false;
        }
    }

    private void initAppManager() {
        try {
            this.mAppManager = (XAppManager) this.mXUIManager.getXUIServiceManager("xapp");
            if (this.mAppManager == null) {
                Logger.t(TAG).w("initAppManager error, cannot get app manager.", new Object[0]);
            }
        } catch (XUIServiceNotConnectedException e) {
            Logger.t(TAG).d("initAppManager " + e.getMessage());
        }
    }

    private void releaseAppManager() {
        if (this.mAppManager != null) {
            this.mAppManager = null;
        }
    }
}
