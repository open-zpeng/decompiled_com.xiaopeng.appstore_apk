package com.xiaopeng.appstore.unity_plugin;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appserver_common.NapaHandler;
import com.xiaopeng.appstore.storeprovider.AssembleConstants;
import com.xiaopeng.appstore.storeprovider.AssembleInfo;
import com.xiaopeng.appstore.storeprovider.AssembleRequest;
import com.xiaopeng.appstore.storeprovider.AssembleResult;
import com.xiaopeng.appstore.storeprovider.IAssembleClientListener;
import com.xiaopeng.appstore.storeprovider.StoreProviderManager;
import com.xiaopeng.appstore.unity_plugin.IAppListListener;
import com.xiaopeng.appstore.unity_plugin.IAppStoreService;
import com.xiaopeng.appstore.unity_plugin.IAppletListener;
import com.xiaopeng.appstore.unity_plugin.IUsbAppListener;
import com.xiaopeng.appstore.unity_plugin.IUsbEntryListener;
import com.xiaopeng.appstore.unity_plugin.model.ByteArrayWrapper;
import com.xiaopeng.appstore.unity_plugin.utils.AppExecutors;
import com.xiaopeng.appstore.unity_plugin.utils.MyLogAdapter;
import com.xiaopeng.appstore.unity_plugin.utils.PackageUtils;
import com.xiaopeng.appstore.unity_plugin.v2.AppListListener;
import com.xiaopeng.appstore.unity_plugin.v2.RequestProtoListener;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
/* loaded from: classes2.dex */
public final class UnityService extends BaseUnityService implements IAssembleClientListener {
    public static final String PACKAGE_NAME = "com.xiaopeng.appstore";
    public static final String SERVICE = "com.xiaopeng.appstore.appserver_common.service.AppStoreService";
    private static final String TAG = "AppStoUnity";
    private static final long WAIT_CONNECTED_TIMEOUT = 45000;
    private AppListListener mAppListOutListener;
    private final IAppListListener.Stub mAppListRemoteListener;
    private IAppStoreService mAppStoreService;
    private AppletListener mAppletOutListener;
    private final IAppletListener.Stub mAppletRemoteListener;
    private final Object mConnectLock;
    private Context mContext;
    private final Set<DownloadListener> mDownloadListenerSet;
    private HandlerThread mHandlerTh;
    private volatile boolean mIsConnected;
    private volatile boolean mIsConnecting;
    private final Object mListenerLock;
    private final Map<String, Set<DownloadListener>> mListenerMap;
    private final ServiceConnection mServiceConnection;
    private UsbAppListener mUsbAppOutListener;
    private final IUsbAppListener.Stub mUsbAppRemoteListener;
    private UsbEntryListener mUsbEntryOutListener;
    private final IUsbEntryListener.Stub mUsbEntryRemoteListener;
    private Handler mWorkHandler;

    private UnityService() {
        this.mIsConnected = false;
        this.mConnectLock = new Object();
        this.mIsConnecting = false;
        this.mServiceConnection = new ServiceConnection() { // from class: com.xiaopeng.appstore.unity_plugin.UnityService.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(UnityService.TAG, "onServiceConnected: " + name);
                UnityService.this.mAppStoreService = IAppStoreService.Stub.asInterface(service);
                UnityService.this.notifyConnected();
                UnityService.this.registerListener();
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                Log.i(UnityService.TAG, "onServiceDisconnected: " + name);
                UnityService.this.notifyDisconnected();
                UnityService.this.mAppStoreService = null;
            }
        };
        this.mListenerLock = new Object();
        this.mDownloadListenerSet = new HashSet();
        this.mListenerMap = new HashMap();
        this.mAppListRemoteListener = new IAppListListener.Stub() { // from class: com.xiaopeng.appstore.unity_plugin.UnityService.2
            @Override // com.xiaopeng.appstore.unity_plugin.IAppListListener
            public void onAppInserted(int position, int count) throws RemoteException {
                Logger.t(UnityService.TAG).i("onAppInserted, pos:" + position + ", count:" + count + ", listener:" + UnityService.this.mAppListOutListener, new Object[0]);
                if (UnityService.this.mAppListOutListener != null) {
                    UnityService.this.mAppListOutListener.onAppInserted(position, count);
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppListListener
            public void onAppRemoved(int position, int count) throws RemoteException {
                Logger.t(UnityService.TAG).i("onAppRemoved, pos:" + position + ", count:" + count + ", listener:" + UnityService.this.mAppListOutListener, new Object[0]);
                if (UnityService.this.mAppListOutListener != null) {
                    UnityService.this.mAppListOutListener.onAppRemoved(position, count);
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppListListener
            public void onAppMoved(int fromPosition, int toPosition) throws RemoteException {
                if (UnityService.this.mAppListOutListener != null) {
                    UnityService.this.mAppListOutListener.onAppMoved(fromPosition, toPosition);
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppListListener
            public void onAppChanged(int position, int count) throws RemoteException {
                Logger.t(UnityService.TAG).i("onAppChanged, pos:" + position + ", count:" + count + ", listener:" + UnityService.this.mAppListOutListener, new Object[0]);
                if (UnityService.this.mAppListOutListener != null) {
                    UnityService.this.mAppListOutListener.onAppChanged(position, count, null);
                }
            }
        };
        this.mUsbEntryRemoteListener = new IUsbEntryListener.Stub() { // from class: com.xiaopeng.appstore.unity_plugin.UnityService.3
            @Override // com.xiaopeng.appstore.unity_plugin.IUsbEntryListener
            public void onShowUsbEntry(boolean isLoading) throws RemoteException {
                if (UnityService.this.mUsbEntryOutListener != null) {
                    UnityService.this.mUsbEntryOutListener.onShowUsbEntry(isLoading);
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IUsbEntryListener
            public void onHideUsbEntry() throws RemoteException {
                if (UnityService.this.mUsbEntryOutListener != null) {
                    UnityService.this.mUsbEntryOutListener.onHideUsbEntry();
                }
            }
        };
        this.mUsbAppRemoteListener = new IUsbAppListener.Stub() { // from class: com.xiaopeng.appstore.unity_plugin.UnityService.4
            @Override // com.xiaopeng.appstore.unity_plugin.IUsbAppListener
            public void onUsbAppChanged(int index, byte[] appItem) throws RemoteException {
                if (UnityService.this.mUsbAppOutListener != null) {
                    UnityService.this.mUsbAppOutListener.onUsbAppChanged(index, new ByteArrayWrapper(appItem));
                }
            }
        };
        this.mAppletRemoteListener = new IAppletListener.Stub() { // from class: com.xiaopeng.appstore.unity_plugin.UnityService.5
            @Override // com.xiaopeng.appstore.unity_plugin.IAppletListener
            public void onAppletListChanged(byte[] appletGroupListProto) throws RemoteException {
                if (UnityService.this.mAppletOutListener != null) {
                    UnityService.this.mAppletOutListener.onAppletListChanged(new ByteArrayWrapper(appletGroupListProto));
                }
            }
        };
    }

    /* loaded from: classes2.dex */
    private static final class SingletonHolder {
        static final UnityService sInstance = new UnityService();

        private SingletonHolder() {
        }
    }

    public static UnityService get() {
        return SingletonHolder.sInstance;
    }

    @Override // com.xiaopeng.appstore.unity_plugin.BaseUnityService
    protected void initInternal(Application unityApplication, Activity unityActivity) {
        HandlerThread handlerThread = this.mHandlerTh;
        if (handlerThread != null && handlerThread.isAlive()) {
            throw new IllegalStateException("AppStore unityService already initialized!");
        }
        initLogger();
        HandlerThread handlerThread2 = new HandlerThread("AppStoreUnityService");
        this.mHandlerTh = handlerThread2;
        handlerThread2.start();
        this.mWorkHandler = new Handler(this.mHandlerTh.getLooper());
        this.mContext = unityApplication;
        tryToBind();
        StoreProviderManager.get().initialize(this.mContext);
        dispatchInitialDownloads();
        Log.i(TAG, "initialize END:" + unityApplication);
    }

    private void initLogger() {
        Logger.addLogAdapter(new MyLogAdapter(MyLogAdapter.SimpleLogFormatStrategy.newBuilder().tag("AppStoLog-3D").showMethodInfo(false).build(), "AppStoLog-3D"));
    }

    private void tryToBind() {
        synchronized (this.mConnectLock) {
            if (!this.mIsConnected && !this.mIsConnecting) {
                bindService();
            } else {
                Logger.t(TAG).i("tryToBind: bind already.", new Object[0]);
            }
        }
    }

    private void bindService() {
        Context context = this.mContext;
        Logger.t(TAG).d("bindService, " + context + ", pn:com.xiaopeng.appstore");
        Intent intent = new Intent();
        intent.setClassName("com.xiaopeng.appstore", SERVICE);
        context.bindService(intent, this.mServiceConnection, 1);
    }

    private void unbindService(Context context) {
        Logger.t(TAG).d("unbindService: " + context);
        context.getApplicationContext().unbindService(this.mServiceConnection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerListener() {
        executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$imD9-P9sZGryEdSUhVB6A3RCQTQ
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$registerListener$0$UnityService();
            }
        });
    }

    public /* synthetic */ Object lambda$registerListener$0$UnityService() throws Exception {
        this.mAppStoreService.registerAppListListener(this.mAppListRemoteListener);
        this.mAppStoreService.registerUsbEntryListener(this.mUsbEntryRemoteListener);
        this.mAppStoreService.registerUsbAppListener(this.mUsbAppRemoteListener);
        this.mAppStoreService.registerAppletListener(this.mAppletRemoteListener);
        return null;
    }

    private void unregisterListener() {
        executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$JvRxVXJmnh5WjqsCKnXhFeMqsUE
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$unregisterListener$1$UnityService();
            }
        });
    }

    public /* synthetic */ Object lambda$unregisterListener$1$UnityService() throws Exception {
        this.mAppStoreService.unregisterAppListListener(this.mAppListRemoteListener);
        this.mAppStoreService.unregisterUsbEntryListener(this.mUsbEntryRemoteListener);
        this.mAppStoreService.unregisterUsbAppListener(this.mUsbAppRemoteListener);
        this.mAppStoreService.unregisterAppletListener(this.mAppletRemoteListener);
        return null;
    }

    private boolean isConnectedWait() {
        boolean z;
        synchronized (this.mConnectLock) {
            if (!this.mIsConnected) {
                try {
                    this.mConnectLock.wait(WAIT_CONNECTED_TIMEOUT);
                } catch (InterruptedException e) {
                    Logger.t(TAG).w("waitConnected ex:" + e, new Object[0]);
                    Thread.currentThread().interrupt();
                }
            }
            z = this.mIsConnected;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyConnected() {
        synchronized (this.mConnectLock) {
            this.mIsConnecting = false;
            this.mIsConnected = true;
            this.mConnectLock.notifyAll();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDisconnected() {
        synchronized (this.mConnectLock) {
            this.mIsConnected = false;
            this.mIsConnecting = false;
        }
    }

    private <T> T executeWaitConnected(Callable<T> callback) {
        T t;
        synchronized (this.mConnectLock) {
            boolean z = this.mIsConnected;
            if (!z || this.mAppStoreService == null) {
                if (!this.mIsConnecting) {
                    bindService();
                }
                Logger.t(TAG).d("executeWaitConnected wait");
                z = isConnectedWait();
            }
            t = null;
            if (z && this.mAppStoreService != null) {
                try {
                    t = callback.call();
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.t(TAG).w("executeWaitConnected error, callback ex:" + e, new Object[0]);
                }
            } else {
                Logger.t(TAG).w("executeWaitConnected error: bindService fail", new Object[0]);
                notifyDisconnected();
            }
        }
        return t;
    }

    @Override // com.xiaopeng.appstore.unity_plugin.BaseUnityService
    public void release() {
        Logger.t(TAG).i("release: " + this.mContext, new Object[0]);
        unregisterListener();
        unbindService(this.mContext);
        notifyDisconnected();
        synchronized (this.mListenerLock) {
            this.mDownloadListenerSet.clear();
            this.mListenerMap.clear();
        }
        StoreProviderManager.get().stopObserve();
        HandlerThread handlerThread = this.mHandlerTh;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
    }

    public void enableDebug(boolean debug) {
        Logger.t(TAG).i("enableDebug:" + debug, new Object[0]);
        IAppStoreService iAppStoreService = this.mAppStoreService;
        if (iAppStoreService != null) {
            try {
                iAppStoreService.enableDebug(debug);
            } catch (RemoteException e) {
                Logger.t(TAG).e("enableDebug ex:", new Object[0]);
                e.printStackTrace();
            }
        }
    }

    public boolean isInDebug() {
        IAppStoreService iAppStoreService = this.mAppStoreService;
        if (iAppStoreService != null) {
            try {
                return iAppStoreService.isInDebug();
            } catch (RemoteException e) {
                Logger.t(TAG).e("enableDebug ex:", new Object[0]);
                e.printStackTrace();
            }
        }
        return false;
    }

    public int getHttpHost() {
        return ((Integer) executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$IEeadGax22izHS0UPU59Unj42Bk
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$getHttpHost$2$UnityService();
            }
        })).intValue();
    }

    public /* synthetic */ Integer lambda$getHttpHost$2$UnityService() throws Exception {
        return Integer.valueOf(this.mAppStoreService.getHttpHost());
    }

    public void switchHttpPreHost() {
        Logger.t(TAG).i(NapaHandler.METHOD_SWITCH_HTTP_PRE_HOST, new Object[0]);
        executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$tLaeXXtaLSRtogCVXkFQ_kMyMvk
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$switchHttpPreHost$3$UnityService();
            }
        });
    }

    public /* synthetic */ Object lambda$switchHttpPreHost$3$UnityService() throws Exception {
        this.mAppStoreService.switchHttpPreHost();
        return null;
    }

    public void switchHttpTestHost() {
        Logger.t(TAG).i(NapaHandler.METHOD_SWITCH_HTTP_TEST_HOST, new Object[0]);
        executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$VWx0l6cP9YuBQuquTE6a0tKb60M
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$switchHttpTestHost$4$UnityService();
            }
        });
    }

    public /* synthetic */ Object lambda$switchHttpTestHost$4$UnityService() throws Exception {
        this.mAppStoreService.switchHttpTestHost();
        return null;
    }

    public void resetHttpHost() {
        Logger.t(TAG).i(NapaHandler.METHOD_RESET_HTTP_HOST, new Object[0]);
        executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$kJoicG4EDzIwDh0XIQBuWHN6-WA
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$resetHttpHost$5$UnityService();
            }
        });
    }

    public /* synthetic */ Object lambda$resetHttpHost$5$UnityService() throws Exception {
        this.mAppStoreService.resetHttpHost();
        return null;
    }

    @Override // com.xiaopeng.appstore.storeprovider.IAssembleClientListener
    public void onAssembleEvent(int eventType, final AssembleInfo info) {
        if (eventType == 1000) {
            Logger.t(TAG).i("onAssembleEvent, EVENT_TYPE_STATE_CHANGED:" + info, new Object[0]);
            postToUnityMain(new Runnable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$t2QNsmtVbTZvdYL74MDci2nN2rE
                @Override // java.lang.Runnable
                public final void run() {
                    UnityService.this.lambda$onAssembleEvent$6$UnityService(info);
                }
            });
        } else if (eventType == 1001) {
            Logger.t(TAG).v("onAssembleEvent, EVENT_TYPE_PROGRESS_CHANGED: " + info, new Object[0]);
            postToUnityMain(new Runnable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$Y6RS5Gin9KDTgYnX4141hCYA-60
                @Override // java.lang.Runnable
                public final void run() {
                    UnityService.this.lambda$onAssembleEvent$7$UnityService(info);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onAssembleEvent$6$UnityService(AssembleInfo assembleInfo) {
        Logger.t(TAG).i("dispatchStateChanged:" + assembleInfo, new Object[0]);
        dispatchStateChanged(assembleInfo.getKey(), assembleInfo.getState());
    }

    public /* synthetic */ void lambda$onAssembleEvent$7$UnityService(AssembleInfo assembleInfo) {
        dispatchProgressChanged(assembleInfo.getKey(), assembleInfo.getProgress());
    }

    public void create() {
        executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$IQmWfQpcsLztNw4RjpZgBwpDgoQ
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$create$8$UnityService();
            }
        });
    }

    public /* synthetic */ Object lambda$create$8$UnityService() throws Exception {
        this.mAppStoreService.create();
        return null;
    }

    public void start() {
        executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$cFWS1hXrn2bg7pEOxE2874gKh-I
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$start$9$UnityService();
            }
        });
    }

    public /* synthetic */ Object lambda$start$9$UnityService() throws Exception {
        this.mAppStoreService.start();
        return null;
    }

    public void stop() {
        executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$jkV_7cYgPRg_eZrTYUj1FB8YuaA
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$stop$10$UnityService();
            }
        });
    }

    public /* synthetic */ Object lambda$stop$10$UnityService() throws Exception {
        this.mAppStoreService.stop();
        return null;
    }

    public void destroy() {
        executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$kd4SOFW3xj5pt__Ydcm2o2IO1I4
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$destroy$11$UnityService();
            }
        });
    }

    public /* synthetic */ Object lambda$destroy$11$UnityService() throws Exception {
        this.mAppStoreService.destroy();
        return null;
    }

    public void setUsbEntryListener(UsbEntryListener listener) {
        this.mUsbEntryOutListener = listener;
    }

    public void setUsbAppListener(UsbAppListener listener) {
        this.mUsbAppOutListener = listener;
    }

    public void startLoadUsbApp() {
        executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$gh1tZ1q32lH2iIocbh5BbNhRSNU
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$startLoadUsbApp$12$UnityService();
            }
        });
    }

    public /* synthetic */ Object lambda$startLoadUsbApp$12$UnityService() throws Exception {
        this.mAppStoreService.startLoadUsbApp();
        return null;
    }

    public ByteArrayWrapper getUsbAppList() {
        return (ByteArrayWrapper) executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$9ZKU0DXYed1sFxNxadFVdMHB_tE
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$getUsbAppList$13$UnityService();
            }
        });
    }

    public /* synthetic */ ByteArrayWrapper lambda$getUsbAppList$13$UnityService() throws Exception {
        return new ByteArrayWrapper(this.mAppStoreService.getUsbAppList());
    }

    public void installUsbApp(final String packageName) {
        Logger.t(TAG).i("installUsbApp:" + packageName, new Object[0]);
        assertUnityServiceValid();
        this.mWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$icMrqbgrYYC8o08lQDLkkNAs_qE
            @Override // java.lang.Runnable
            public final void run() {
                UnityService.this.lambda$installUsbApp$14$UnityService(packageName);
            }
        });
    }

    public /* synthetic */ void lambda$installUsbApp$14$UnityService(String str) {
        IAppStoreService iAppStoreService = this.mAppStoreService;
        if (iAppStoreService != null) {
            try {
                iAppStoreService.installUsbApp(str);
                return;
            } catch (RemoteException e) {
                Logger.t(TAG).w("installUsbApp remoteEx:" + e, new Object[0]);
                e.printStackTrace();
                return;
            }
        }
        Logger.t(TAG).w("installUsbApp, not bind.", new Object[0]);
    }

    public void setAppletListener(AppletListener appletListener) {
        this.mAppletOutListener = appletListener;
    }

    public void loadAppletList() {
        executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$CDgoR_JiqArsMqkY3WFx1zrngiQ
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$loadAppletList$15$UnityService();
            }
        });
    }

    public /* synthetic */ Object lambda$loadAppletList$15$UnityService() throws Exception {
        this.mAppStoreService.loadAppletList();
        return null;
    }

    public void launchApplet(final String id, final String name) {
        executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$WJykGethrk565_UMLgtY3Uy84z4
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$launchApplet$16$UnityService(id, name);
            }
        });
    }

    public /* synthetic */ Object lambda$launchApplet$16$UnityService(String str, String str2) throws Exception {
        this.mAppStoreService.launchApplet(str, str2);
        return null;
    }

    public boolean isInstalled(String packageName) {
        return isInstalled(packageName, true);
    }

    public boolean isInstalled(final String packageName, final boolean useCache) {
        assertUnityServiceValid();
        return ((Boolean) executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$juwa6tQREB5yDmpcOTZOsb5qiLA
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$isInstalled$17$UnityService(packageName, useCache);
            }
        })).booleanValue();
    }

    public /* synthetic */ Boolean lambda$isInstalled$17$UnityService(String str, boolean z) throws Exception {
        return Boolean.valueOf(this.mAppStoreService.isInstalled(str, z));
    }

    public void persistAppOrder(final byte[] appIndexList) {
        if (appIndexList == null) {
            Logger.t(TAG).w("persistAppOrder, param is null", new Object[0]);
        } else {
            executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$Ljc58oH2_reMwmLr-4cWf4l1c24
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return UnityService.this.lambda$persistAppOrder$18$UnityService(appIndexList);
                }
            });
        }
    }

    public /* synthetic */ Object lambda$persistAppOrder$18$UnityService(byte[] bArr) throws Exception {
        this.mAppStoreService.persistAppOrder(bArr);
        return null;
    }

    public void setAppListListenerV2(AppListListener appListListener) {
        Logger.t(TAG).i("setAppListListenerV2: " + appListListener, new Object[0]);
        this.mAppListOutListener = appListListener;
    }

    public ByteArrayWrapper getAppListProto() {
        return (ByteArrayWrapper) executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$zlRs4vWlg7h1GiCnTtI-E6vlBew
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$getAppListProto$19$UnityService();
            }
        });
    }

    public /* synthetic */ ByteArrayWrapper lambda$getAppListProto$19$UnityService() throws Exception {
        return new ByteArrayWrapper(this.mAppStoreService.getAppListProto());
    }

    public boolean launchApp(final String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            Logger.t(TAG).w("launchApp error: " + packageName, new Object[0]);
            return false;
        }
        return ((Boolean) executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$TI3YxJpCl5vAyuTXknJvlDd4Mno
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$launchApp$20$UnityService(packageName);
            }
        })).booleanValue();
    }

    public /* synthetic */ Boolean lambda$launchApp$20$UnityService(String str) throws Exception {
        return Boolean.valueOf(this.mAppStoreService.launchApp(str));
    }

    public boolean uninstallApp(String packageName) {
        Logger.t(TAG).i("uninstallApp: " + packageName, new Object[0]);
        return PackageUtils.uninstall(this.mContext, packageName, 0);
    }

    public void startLoadAppList() {
        Logger.t(TAG).i(NapaHandler.METHOD_START_LOAD_APP_LIST, new Object[0]);
        executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$s1JGPdjLWFEFFQZsyzuG9lvKFgE
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$startLoadAppList$21$UnityService();
            }
        });
    }

    public /* synthetic */ Object lambda$startLoadAppList$21$UnityService() throws Exception {
        this.mAppStoreService.startLoadAppList();
        return null;
    }

    public boolean isPrivacyAgreed() {
        assertUnityServiceValid();
        return ((Boolean) executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$v2wnASI3Pw5X486GeBLoBGntE94
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$isPrivacyAgreed$22$UnityService();
            }
        })).booleanValue();
    }

    public /* synthetic */ Boolean lambda$isPrivacyAgreed$22$UnityService() throws Exception {
        boolean isPrivacyAgreed = this.mAppStoreService.isPrivacyAgreed();
        Logger.t(TAG).i("isPrivacyAgreed:" + isPrivacyAgreed, new Object[0]);
        return Boolean.valueOf(isPrivacyAgreed);
    }

    public void showPrivacyDialog(final boolean onlyContent) {
        Logger.t(TAG).i("showPrivacyDialog:" + onlyContent, new Object[0]);
        executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$39ORwu580r18IRJLB2255QaSdJI
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return UnityService.this.lambda$showPrivacyDialog$23$UnityService(onlyContent);
            }
        });
    }

    public /* synthetic */ Object lambda$showPrivacyDialog$23$UnityService(boolean z) throws Exception {
        this.mAppStoreService.showPrivacyDialog(z);
        return null;
    }

    public ByteArrayWrapper getStoreHomeProto() {
        if (this.mAppStoreService != null) {
            try {
                return new ByteArrayWrapper(this.mAppStoreService.getStoreHomeProto());
            } catch (RemoteException e) {
                Logger.t(TAG).i("getStoreHomeProto, remoteEx:" + e, new Object[0]);
                e.printStackTrace();
                return null;
            }
        }
        Logger.t(TAG).w("getStoreHomeProto, service not bind", new Object[0]);
        return null;
    }

    public void getStoreHomeProtoAsync(final RequestProtoListener listener) {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$APEAM5ewZLSUAPkXaaCd4_TjUkU
            @Override // java.lang.Runnable
            public final void run() {
                UnityService.this.lambda$getStoreHomeProtoAsync$25$UnityService(listener);
            }
        });
    }

    public /* synthetic */ void lambda$getStoreHomeProtoAsync$25$UnityService(final RequestProtoListener requestProtoListener) {
        final ByteArrayWrapper storeHomeProto = getStoreHomeProto();
        if (requestProtoListener != null) {
            postToUnityMain(new Runnable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$ioFD_8pXdve5IumvBEjOSdoqDoI
                @Override // java.lang.Runnable
                public final void run() {
                    RequestProtoListener.this.onResponse(storeHomeProto);
                }
            });
        }
    }

    public ByteArrayWrapper getAppDetailProto(String packageName) {
        if (this.mAppStoreService != null) {
            try {
                return new ByteArrayWrapper(this.mAppStoreService.getAppDetailProto(packageName));
            } catch (RemoteException e) {
                Logger.t(TAG).i("getAppDetailProto, remoteEx:" + e, new Object[0]);
                e.printStackTrace();
                return null;
            }
        }
        Logger.t(TAG).w("getAppDetailProto, service not bind", new Object[0]);
        return null;
    }

    public void getAppDetailProtoAsync(final String packageName, final RequestProtoListener listener) {
        Logger.t(TAG).i("getAppDetailProtoAsync start, pn:" + packageName, new Object[0]);
        if (TextUtils.isEmpty(packageName)) {
            Logger.t(TAG).w("getAppDetailProtoAsync: packageName is null", new Object[0]);
        } else {
            AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$UFB8IND6R7JVQy-CuHLFLYwwMwg
                @Override // java.lang.Runnable
                public final void run() {
                    UnityService.this.lambda$getAppDetailProtoAsync$27$UnityService(packageName, listener);
                }
            });
        }
    }

    public /* synthetic */ void lambda$getAppDetailProtoAsync$27$UnityService(String str, final RequestProtoListener requestProtoListener) {
        final ByteArrayWrapper appDetailProto = getAppDetailProto(str);
        if (requestProtoListener != null) {
            postToUnityMain(new Runnable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$j3QPWJlK4yj28xaRLYfcGt77OYo
                @Override // java.lang.Runnable
                public final void run() {
                    RequestProtoListener.this.onResponse(appDetailProto);
                }
            });
        }
    }

    public ByteArrayWrapper getUpdateProto() {
        if (this.mAppStoreService != null) {
            try {
                return new ByteArrayWrapper(this.mAppStoreService.getUpdateProto());
            } catch (RemoteException e) {
                Logger.t(TAG).i("getUpdateProto, remoteEx:" + e, new Object[0]);
                e.printStackTrace();
                return null;
            }
        }
        Logger.t(TAG).w("getUpdateProto, service not bind", new Object[0]);
        return null;
    }

    public void getUpdateProtoAsync(final RequestProtoListener listener) {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$35l6l2HIVxMGpxtW0e6333aZioA
            @Override // java.lang.Runnable
            public final void run() {
                UnityService.this.lambda$getUpdateProtoAsync$29$UnityService(listener);
            }
        });
    }

    public /* synthetic */ void lambda$getUpdateProtoAsync$29$UnityService(final RequestProtoListener requestProtoListener) {
        Logger.t(TAG).d("getUpdateProtoAsync start");
        final ByteArrayWrapper updateProto = getUpdateProto();
        if (requestProtoListener != null) {
            postToUnityMain(new Runnable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$2w8PdYhR4bDd7IPRIaufD-6LOvA
                @Override // java.lang.Runnable
                public final void run() {
                    RequestProtoListener.this.onResponse(updateProto);
                }
            });
        }
    }

    public void startDownloadAsync(final String downloadUrl, final String configUrl, final String packageName, final String iconUrl, final String label) {
        assertUnityServiceValid();
        this.mWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$C9dUtzfVXDTxs6KLlR6UwAO3i4Y
            @Override // java.lang.Runnable
            public final void run() {
                UnityService.this.lambda$startDownloadAsync$30$UnityService(downloadUrl, configUrl, packageName, iconUrl, label);
            }
        });
    }

    /* renamed from: startDownload */
    public boolean lambda$startDownloadAsync$30$UnityService(String downloadUrl, String configUrl, String packageName, String iconUrl, String label) {
        return startDownload(downloadUrl, null, configUrl, null, packageName, iconUrl, label);
    }

    public boolean startDownload(String downloadUrl, String md5, String configUrl, String configMd5, String packageName, String iconUrl, String label) {
        AssembleResult assemble = StoreProviderManager.get().assemble(AssembleRequest.enqueue(1000, packageName, label).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_URL, configUrl).setDownloadUrl(downloadUrl).setIconUrl(iconUrl).request(), null);
        if (handleAssembleResult(assemble)) {
            Logger.t(TAG).i("startDownload success:" + packageName + ", " + assemble, new Object[0]);
            return true;
        }
        Logger.t(TAG).i("startDownload fail:" + packageName + ", " + assemble, new Object[0]);
        return false;
    }

    public void pauseDownloadAsync(final String packageName) {
        assertUnityServiceValid();
        this.mWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$xsRICuMLrm81smZufOaxWxSC5eE
            @Override // java.lang.Runnable
            public final void run() {
                UnityService.this.lambda$pauseDownloadAsync$31$UnityService(packageName);
            }
        });
    }

    /* renamed from: pauseDownload */
    public boolean lambda$pauseDownloadAsync$31$UnityService(String packageName) {
        AssembleResult assemble = StoreProviderManager.get().assemble(AssembleRequest.pause(1000, packageName), null);
        if (handleAssembleResult(assemble)) {
            Logger.t(TAG).i("pauseDownload success:" + packageName + ", " + assemble, new Object[0]);
            return true;
        }
        Logger.t(TAG).i("pauseDownload fail:" + packageName + ", " + assemble, new Object[0]);
        return false;
    }

    public void resumeDownloadAsync(final String packageName) {
        assertUnityServiceValid();
        this.mWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$mTfqrb8fkgySbxuPlhTOXlqkXPI
            @Override // java.lang.Runnable
            public final void run() {
                UnityService.this.lambda$resumeDownloadAsync$32$UnityService(packageName);
            }
        });
    }

    /* renamed from: resumeDownload */
    public boolean lambda$resumeDownloadAsync$32$UnityService(String packageName) {
        AssembleResult assemble = StoreProviderManager.get().assemble(AssembleRequest.resume(1000, packageName), null);
        if (handleAssembleResult(assemble)) {
            Logger.t(TAG).i("resumeDownload success:" + packageName + ", " + assemble, new Object[0]);
            return true;
        }
        Logger.t(TAG).i("resumeDownload fail:" + packageName + ", " + assemble, new Object[0]);
        return false;
    }

    public void cancelDownloadAsync(final String packageName) {
        assertUnityServiceValid();
        this.mWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$AyXHJFLITLIchMYYlkmIyJ0E_rw
            @Override // java.lang.Runnable
            public final void run() {
                UnityService.this.lambda$cancelDownloadAsync$33$UnityService(packageName);
            }
        });
    }

    /* renamed from: cancelDownload */
    public boolean lambda$cancelDownloadAsync$33$UnityService(String packageName) {
        AssembleResult assemble = StoreProviderManager.get().assemble(AssembleRequest.cancel(1000, packageName), null);
        if (handleAssembleResult(assemble)) {
            Logger.t(TAG).i("cancelDownload success:" + packageName + ", " + assemble, new Object[0]);
            return true;
        }
        Logger.t(TAG).i("cancelDownload fail:" + packageName + ", " + assemble, new Object[0]);
        return false;
    }

    private void dispatchInitialDownloads() {
        this.mWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.unity_plugin.-$$Lambda$UnityService$N8SBs8RLFuUgWxUKMSIQhsLzD3A
            @Override // java.lang.Runnable
            public final void run() {
                UnityService.this.lambda$dispatchInitialDownloads$34$UnityService();
            }
        });
    }

    public /* synthetic */ void lambda$dispatchInitialDownloads$34$UnityService() {
        List<AssembleInfo> assembleInfoList = StoreProviderManager.get().getAssembleInfoList(1000, null);
        if (assembleInfoList == null) {
            return;
        }
        int i = 1;
        for (AssembleInfo assembleInfo : assembleInfoList) {
            Logger.t(TAG).d("dispatchInitialDownloads(" + i + "):" + assembleInfo);
            String key = assembleInfo.getKey();
            int state = assembleInfo.getState();
            dispatchStateChanged(key, state);
            dispatchProgressChanged(key, state);
            i++;
        }
    }

    public void addDownloadListener(String packageName, DownloadListener listener) {
        if (TextUtils.isEmpty(packageName)) {
            Logger.t(TAG).w("addDownloadListener: packageName is null", new Object[0]);
            return;
        }
        Logger.t(TAG).i("addDownloadListener, pn:" + packageName + ", lis:" + listener, new Object[0]);
        synchronized (this.mListenerLock) {
            tryStartObserveDownload();
            Set<DownloadListener> set = this.mListenerMap.get(packageName);
            if (set == null) {
                set = new HashSet<>();
            }
            set.add(listener);
        }
    }

    public void addDownloadListener(DownloadListener listener) {
        Logger.t(TAG).i("addDownloadListener: " + listener, new Object[0]);
        synchronized (this.mListenerLock) {
            tryStartObserveDownload();
            this.mDownloadListenerSet.add(listener);
        }
    }

    public void removeDownloadListener(DownloadListener listener) {
        Logger.t(TAG).i("removeDownloadListener: " + listener, new Object[0]);
        synchronized (this.mListenerLock) {
            this.mDownloadListenerSet.remove(listener);
            Iterator<Map.Entry<String, Set<DownloadListener>>> it = this.mListenerMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Set<DownloadListener>> next = it.next();
                Set<DownloadListener> value = next.getValue();
                value.remove(listener);
                if (value.isEmpty()) {
                    Logger.t(TAG).i("removeDownloadListener all, pn:" + next.getKey() + ", lis:" + listener, new Object[0]);
                    it.remove();
                }
            }
            tryRemoveObserveDownload();
        }
    }

    private void dispatchStateChanged(String packageName, int state) {
        synchronized (this.mListenerLock) {
            for (DownloadListener downloadListener : this.mDownloadListenerSet) {
                downloadListener.onStateChanged(packageName, state);
            }
            for (Map.Entry<String, Set<DownloadListener>> entry : this.mListenerMap.entrySet()) {
                for (DownloadListener downloadListener2 : entry.getValue()) {
                    downloadListener2.onStateChanged(packageName, state);
                }
            }
        }
    }

    private void dispatchProgressChanged(String packageName, float progress) {
        synchronized (this.mListenerLock) {
            for (DownloadListener downloadListener : this.mDownloadListenerSet) {
                downloadListener.onProgressChanged(packageName, progress);
            }
            for (Map.Entry<String, Set<DownloadListener>> entry : this.mListenerMap.entrySet()) {
                for (DownloadListener downloadListener2 : entry.getValue()) {
                    downloadListener2.onProgressChanged(packageName, progress);
                }
            }
        }
    }

    private void tryStartObserveDownload() {
        if (this.mListenerMap.isEmpty() && this.mDownloadListenerSet.isEmpty()) {
            Logger.t(TAG).i("startObserveDownload", new Object[0]);
            StoreProviderManager.get().startObserve();
            StoreProviderManager.get().registerListener(1000, null, this);
        }
    }

    private void tryRemoveObserveDownload() {
        if (this.mListenerMap.isEmpty() && this.mDownloadListenerSet.isEmpty()) {
            Logger.t(TAG).i("tryRemoveObserveDownload", new Object[0]);
            StoreProviderManager.get().stopObserve();
            StoreProviderManager.get().unregisterListener(this);
        }
    }

    private void assertUnityServiceValid() {
        HandlerThread handlerThread = this.mHandlerTh;
        if (handlerThread == null || !handlerThread.isAlive()) {
            throw new IllegalStateException("AppStore UnityService is invalid. call init first!");
        }
    }

    private boolean handleAssembleResult(AssembleResult assembleResult) {
        return assembleResult != null && assembleResult.isSuccessful();
    }

    private boolean callUnity(String gameObjectName, String functionName, String args) {
        try {
            Class<?> cls = Class.forName("com.unity3d.player.UnityPlayer");
            cls.getMethod("UnitySendMessage", String.class, String.class, String.class).invoke(cls, gameObjectName, functionName, args);
            return true;
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            return false;
        }
    }
}
