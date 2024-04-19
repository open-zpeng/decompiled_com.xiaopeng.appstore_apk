package com.xiaopeng.aar.client.ipc;

import android.net.Uri;
import android.os.Binder;
import android.os.RemoteException;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.xiaopeng.aar.Apps;
import com.xiaopeng.aar.client.ipc.Ipc;
import com.xiaopeng.aar.server.ipc.ServerObserver;
import com.xiaopeng.aar.utils.LogUtils;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.lib.apirouter.server.ApiPublisherProvider;
import com.xiaopeng.lib.apirouter.server.IManifestHandler;
import com.xiaopeng.lib.apirouter.server.IManifestHelper;
import com.xiaopeng.lib.apirouter.server.ManifestHelper_aar;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
/* loaded from: classes2.dex */
public class IpcImpl implements Ipc {
    private static final String TAG = "Ipc";
    private Ipc.OnServerListener mOnAppListener;
    private ConcurrentHashMap<String, Integer> mPids;
    private String mServerName;
    private final CopyOnWriteArraySet<Ipc.OnServerStatusListener> mStatusListeners;

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final IpcImpl INSTANCE = new IpcImpl();

        private SingletonHolder() {
        }
    }

    public static IpcImpl get() {
        return SingletonHolder.INSTANCE;
    }

    private IpcImpl() {
        this.mStatusListeners = new CopyOnWriteArraySet<>();
        this.mPids = new ConcurrentHashMap<>();
        LogUtils.d(TAG, "IpcImpl");
        ApiPublisherProvider.addManifestHandler(new IManifestHandler() { // from class: com.xiaopeng.aar.client.ipc.-$$Lambda$IpcImpl$o6MF8lrQkQqjyEO8MCpx_fjvIo4
            @Override // com.xiaopeng.lib.apirouter.server.IManifestHandler
            public final IManifestHelper[] getManifestHelpers() {
                return IpcImpl.lambda$new$0();
            }
        });
        this.mServerName = ServerObserver.class.getSimpleName();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ IManifestHelper[] lambda$new$0() {
        return new IManifestHelper[]{new ManifestHelper_aar()};
    }

    @Override // com.xiaopeng.aar.client.ipc.Ipc
    public void setOnServerListener(Ipc.OnServerListener onServerListener) {
        this.mOnAppListener = onServerListener;
    }

    @Override // com.xiaopeng.aar.client.ipc.Ipc
    public void addOnServerStatusListener(Ipc.OnServerStatusListener onServerStatusListener) {
        this.mStatusListeners.add(onServerStatusListener);
    }

    @Override // com.xiaopeng.aar.client.ipc.Ipc
    public void removeOnServerStatusListener(Ipc.OnServerStatusListener onServerStatusListener) {
        this.mStatusListeners.remove(onServerStatusListener);
    }

    @Override // com.xiaopeng.aar.client.ipc.Ipc
    public String call(String str, String str2, String str3, String str4, byte[] bArr) {
        return callApiRouter(0, str, str2, str3, str4, bArr);
    }

    @Override // com.xiaopeng.aar.client.ipc.Ipc
    public void onReceived(int i, String str, String str2, String str3, String str4, byte[] bArr) {
        int i2;
        if (i == 202) {
            int callingPid = Binder.getCallingPid();
            if (this.mPids.get(str) == null) {
                this.mPids.put(str, Integer.valueOf(callingPid));
                i2 = 1;
            } else {
                i2 = 2;
            }
            LogUtils.i(TAG, String.format("onReceived--SERVER_NOTIFY_STARTED appId:%s, uid:%s, pid:%s, status:%s, %s", str, Integer.valueOf(Binder.getCallingUid()), Integer.valueOf(callingPid), Integer.valueOf(i2), this.mStatusListeners));
            Iterator<Ipc.OnServerStatusListener> it = this.mStatusListeners.iterator();
            while (it.hasNext()) {
                it.next().onServerStatus(str, i2);
            }
            return;
        }
        if (this.mPids.get(str) == null) {
            this.mPids.put(str, Integer.valueOf(Binder.getCallingPid()));
        }
        Ipc.OnServerListener onServerListener = this.mOnAppListener;
        if (onServerListener != null) {
            onServerListener.onEvent(i, str, str2, str3, str4, bArr);
        }
    }

    private String callApiRouter(int i, String str, String str2, String str3, String str4, byte[] bArr) {
        String packageNames = Apps.getPackageNames(str);
        if (TextUtils.isEmpty(packageNames)) {
            LogUtils.e(TAG, String.format("call--appId : %s,  pkg is null", str));
            return null;
        }
        LogUtils.d(TAG, String.format("callApiRouter %s , %s , %s, %s", packageNames, Integer.valueOf(i), str2, str3));
        try {
            Uri.Builder appendQueryParameter = new Uri.Builder().authority(packageNames + "." + this.mServerName).appendQueryParameter(VuiConstants.ELEMENT_TYPE, String.valueOf(i)).appendQueryParameter("module", str2).appendQueryParameter("method", str3).appendQueryParameter("param", str4);
            if (bArr == null) {
                return (String) ApiRouter.route(appendQueryParameter.path(NotificationCompat.CATEGORY_CALL).build());
            }
            return (String) ApiRouter.route(appendQueryParameter.path("callBlob").build(), bArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaopeng.aar.client.ipc.Ipc
    public void subscribe(String str, String str2) {
        callApiRouter(101, str, str2, null, null, null);
    }

    @Override // com.xiaopeng.aar.client.ipc.Ipc
    public void unSubscribe(String str, String str2) {
        callApiRouter(102, str, str2, null, null, null);
    }

    @Override // com.xiaopeng.aar.client.ipc.Ipc
    public void subscribes(String str, String str2) {
        callApiRouter(103, str, str2, null, null, null);
    }

    @Override // com.xiaopeng.aar.client.ipc.Ipc
    public void unSubscribes(String str, String str2) {
        callApiRouter(104, str, str2, null, null, null);
    }
}
