package com.xiaopeng.appstore.libcommon.utils;

import android.content.Context;
import android.content.pm.PackageInstaller;
import android.os.Handler;
import android.text.TextUtils;
import android.util.SparseArray;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.libcommon.utils.PackageInstallMgr;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
/* loaded from: classes2.dex */
public class PackageInstallMgr extends PackageInstaller.SessionCallback {
    private static final String TAG = "PackageInstallMgr";
    private final Object mCbLock;
    private final Set<OnPackageInstallCallback> mCbs;
    private Context mContext;
    private final SparseArray<String> mInstallSessionPackageMap;
    private PackageInstaller mPackageInstaller;
    private final PackageMonitor mPackageMonitor;

    /* loaded from: classes2.dex */
    public interface OnPackageInstallCallback {
        void onInstallCreate(int sessionId, String pkgName);

        void onInstallFinished(int sessionId, String pkgName, boolean success);

        void onInstallProgressChanged(int sessionId, String pkgName, float progress);

        void onPackageAdded(String pkgName);

        void onPackageRemoved(String packageName);

        void onPackageUpdated(String packageName);
    }

    @Override // android.content.pm.PackageInstaller.SessionCallback
    public void onActiveChanged(int sessionId, boolean active) {
    }

    @Override // android.content.pm.PackageInstaller.SessionCallback
    public void onBadgingChanged(int sessionId) {
    }

    /* synthetic */ PackageInstallMgr(AnonymousClass1 anonymousClass1) {
        this();
    }

    private PackageInstallMgr() {
        this.mCbs = new HashSet();
        this.mCbLock = new Object();
        this.mInstallSessionPackageMap = new SparseArray<>();
        this.mPackageMonitor = new AnonymousClass1();
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        static final PackageInstallMgr sInstance = new PackageInstallMgr(null);

        private SingletonHolder() {
        }
    }

    public static PackageInstallMgr get() {
        return SingletonHolder.sInstance;
    }

    /* renamed from: com.xiaopeng.appstore.libcommon.utils.PackageInstallMgr$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    class AnonymousClass1 extends PackageMonitor {
        AnonymousClass1() {
        }

        @Override // com.xiaopeng.appstore.libcommon.utils.PackageMonitor
        public void onPackageAdded(final String packageName) {
            PackageInstallMgr.this.dispatchPackageEvents(packageName, new Consumer() { // from class: com.xiaopeng.appstore.libcommon.utils.-$$Lambda$PackageInstallMgr$1$znttxCTSOyr3FplXtjTL21G9hgg
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((PackageInstallMgr.OnPackageInstallCallback) obj).onPackageAdded(packageName);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaopeng.appstore.libcommon.utils.PackageMonitor
        public void onPackageUpdated(final String packageName) {
            PackageInstallMgr.this.dispatchPackageEvents(packageName, new Consumer() { // from class: com.xiaopeng.appstore.libcommon.utils.-$$Lambda$PackageInstallMgr$1$KgBuV6sn-TGc2mbqZ4d1XyFJRh0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((PackageInstallMgr.OnPackageInstallCallback) obj).onPackageUpdated(packageName);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaopeng.appstore.libcommon.utils.PackageMonitor
        public void onPackageRemoved(final String packageName) {
            PackageInstallMgr.this.dispatchPackageEvents(packageName, new Consumer() { // from class: com.xiaopeng.appstore.libcommon.utils.-$$Lambda$PackageInstallMgr$1$fSRbvQUvO1XCESO587bXrcFf7H4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((PackageInstallMgr.OnPackageInstallCallback) obj).onPackageRemoved(packageName);
                }
            });
        }
    }

    public void init(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mPackageInstaller = applicationContext.getPackageManager().getPackageInstaller();
    }

    public void release() {
        synchronized (this.mCbLock) {
            this.mCbs.clear();
        }
    }

    private void assertInit() {
        if (this.mContext == null) {
            throw new IllegalStateException("Should call init first!");
        }
    }

    public void register(String trackingPackage, OnPackageInstallCallback callback, Handler handler) {
        assertInit();
        Logger.t(TAG).i("register, tracking:" + trackingPackage + ", callback:" + callback, new Object[0]);
        synchronized (this.mCbLock) {
            if (this.mCbs.isEmpty()) {
                this.mPackageInstaller.registerSessionCallback(this, handler);
                this.mPackageMonitor.register(this.mContext, handler);
            }
            this.mCbs.add(new CallbackWrapper(trackingPackage, callback));
        }
    }

    public void register(OnPackageInstallCallback callback, Handler handler) {
        assertInit();
        Logger.t(TAG).i("register, callback:" + callback, new Object[0]);
        synchronized (this.mCbLock) {
            if (this.mCbs.isEmpty()) {
                this.mPackageInstaller.registerSessionCallback(this, handler);
                this.mPackageMonitor.register(this.mContext, handler);
            }
            this.mCbs.add(callback);
        }
    }

    public void unregister(final OnPackageInstallCallback callback) {
        assertInit();
        Logger.t(TAG).i("unregister, callback:" + callback, new Object[0]);
        synchronized (this.mCbLock) {
            this.mCbs.removeIf(new Predicate() { // from class: com.xiaopeng.appstore.libcommon.utils.-$$Lambda$PackageInstallMgr$-MafRD4S6OxozSDc1HOx4PemYPk
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return PackageInstallMgr.lambda$unregister$0(PackageInstallMgr.OnPackageInstallCallback.this, (PackageInstallMgr.OnPackageInstallCallback) obj);
                }
            });
            if (this.mCbs.isEmpty()) {
                this.mPackageInstaller.unregisterSessionCallback(this);
                this.mPackageMonitor.unregister();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$unregister$0(OnPackageInstallCallback onPackageInstallCallback, OnPackageInstallCallback onPackageInstallCallback2) {
        return (onPackageInstallCallback2 instanceof CallbackWrapper) && ((CallbackWrapper) onPackageInstallCallback2).innerCallback == onPackageInstallCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchPackageEvents(String packageName, Consumer<OnPackageInstallCallback> consumer) {
        OnPackageInstallCallback[] threadSafeArrayCbs = threadSafeArrayCbs();
        if (threadSafeArrayCbs == null) {
            return;
        }
        for (OnPackageInstallCallback onPackageInstallCallback : threadSafeArrayCbs) {
            if (onPackageInstallCallback != null) {
                if (onPackageInstallCallback instanceof CallbackWrapper) {
                    CallbackWrapper callbackWrapper = (CallbackWrapper) onPackageInstallCallback;
                    if (callbackWrapper.trackingPackage.equals(packageName)) {
                        consumer.accept(callbackWrapper);
                    } else {
                        Logger.t(TAG).w("dispatchPackageEvents, ignore," + packageName, new Object[0]);
                    }
                } else {
                    consumer.accept(onPackageInstallCallback);
                }
            }
        }
    }

    private OnPackageInstallCallback[] threadSafeArrayCbs() {
        if (this.mCbs.isEmpty()) {
            return null;
        }
        OnPackageInstallCallback[] onPackageInstallCallbackArr = new OnPackageInstallCallback[this.mCbs.size()];
        this.mCbs.toArray(onPackageInstallCallbackArr);
        return onPackageInstallCallbackArr;
    }

    @Override // android.content.pm.PackageInstaller.SessionCallback
    public void onCreated(int sessionId) {
        PackageInstaller.SessionInfo sessionInfo = this.mPackageInstaller.getSessionInfo(sessionId);
        if (sessionInfo != null && !TextUtils.isEmpty(sessionInfo.getAppPackageName())) {
            this.mInstallSessionPackageMap.put(sessionId, sessionInfo.getAppPackageName());
        }
        OnPackageInstallCallback[] threadSafeArrayCbs = threadSafeArrayCbs();
        if (threadSafeArrayCbs == null) {
            return;
        }
        for (OnPackageInstallCallback onPackageInstallCallback : threadSafeArrayCbs) {
            if (onPackageInstallCallback != null) {
                String str = this.mInstallSessionPackageMap.get(sessionId);
                if (onPackageInstallCallback instanceof CallbackWrapper) {
                    CallbackWrapper callbackWrapper = (CallbackWrapper) onPackageInstallCallback;
                    if (callbackWrapper.trackingPackage.equals(str)) {
                        callbackWrapper.onInstallCreate(sessionId, str);
                    }
                } else {
                    onPackageInstallCallback.onInstallCreate(sessionId, str);
                }
            }
        }
    }

    @Override // android.content.pm.PackageInstaller.SessionCallback
    public void onProgressChanged(int sessionId, float progress) {
        OnPackageInstallCallback[] threadSafeArrayCbs = threadSafeArrayCbs();
        if (threadSafeArrayCbs == null) {
            return;
        }
        for (OnPackageInstallCallback onPackageInstallCallback : threadSafeArrayCbs) {
            if (onPackageInstallCallback != null) {
                String str = this.mInstallSessionPackageMap.get(sessionId);
                if (onPackageInstallCallback instanceof CallbackWrapper) {
                    CallbackWrapper callbackWrapper = (CallbackWrapper) onPackageInstallCallback;
                    if (callbackWrapper.trackingPackage.equals(str)) {
                        callbackWrapper.onInstallProgressChanged(sessionId, str, progress);
                    }
                } else {
                    onPackageInstallCallback.onInstallProgressChanged(sessionId, str, progress);
                }
            }
        }
    }

    @Override // android.content.pm.PackageInstaller.SessionCallback
    public void onFinished(int sessionId, boolean success) {
        OnPackageInstallCallback[] threadSafeArrayCbs = threadSafeArrayCbs();
        if (threadSafeArrayCbs == null) {
            return;
        }
        for (OnPackageInstallCallback onPackageInstallCallback : threadSafeArrayCbs) {
            if (onPackageInstallCallback != null) {
                String str = this.mInstallSessionPackageMap.get(sessionId);
                if (onPackageInstallCallback instanceof CallbackWrapper) {
                    CallbackWrapper callbackWrapper = (CallbackWrapper) onPackageInstallCallback;
                    if (callbackWrapper.trackingPackage.equals(str)) {
                        callbackWrapper.onInstallFinished(sessionId, str, success);
                    }
                } else {
                    onPackageInstallCallback.onInstallFinished(sessionId, str, success);
                }
            }
        }
        this.mInstallSessionPackageMap.remove(sessionId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class CallbackWrapper implements OnPackageInstallCallback {
        public final OnPackageInstallCallback innerCallback;
        public final String trackingPackage;

        public CallbackWrapper(String trackingPackage, OnPackageInstallCallback callback) {
            this.trackingPackage = trackingPackage;
            this.innerCallback = callback;
        }

        @Override // com.xiaopeng.appstore.libcommon.utils.PackageInstallMgr.OnPackageInstallCallback
        public void onInstallCreate(int sessionId, String pkgName) {
            this.innerCallback.onInstallCreate(sessionId, pkgName);
        }

        @Override // com.xiaopeng.appstore.libcommon.utils.PackageInstallMgr.OnPackageInstallCallback
        public void onInstallProgressChanged(int sessionId, String pkgName, float progress) {
            this.innerCallback.onInstallProgressChanged(sessionId, pkgName, progress);
        }

        @Override // com.xiaopeng.appstore.libcommon.utils.PackageInstallMgr.OnPackageInstallCallback
        public void onInstallFinished(int sessionId, String pkgName, boolean success) {
            this.innerCallback.onInstallFinished(sessionId, pkgName, success);
        }

        @Override // com.xiaopeng.appstore.libcommon.utils.PackageInstallMgr.OnPackageInstallCallback
        public void onPackageAdded(String pkgName) {
            this.innerCallback.onPackageAdded(pkgName);
        }

        @Override // com.xiaopeng.appstore.libcommon.utils.PackageInstallMgr.OnPackageInstallCallback
        public void onPackageRemoved(String packageName) {
            this.innerCallback.onPackageRemoved(packageName);
        }

        @Override // com.xiaopeng.appstore.libcommon.utils.PackageInstallMgr.OnPackageInstallCallback
        public void onPackageUpdated(String packageName) {
            this.innerCallback.onPackageUpdated(packageName);
        }
    }
}
