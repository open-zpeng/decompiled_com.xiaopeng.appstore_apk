package com.xiaopeng.aar.server.ipc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.RemoteException;
import com.xiaopeng.aar.client.ipc.ClientObserver;
import com.xiaopeng.aar.server.ipc.IpcServer;
import com.xiaopeng.aar.utils.LogUtils;
import com.xiaopeng.aar.utils.ThreadUtils;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.lib.apirouter.server.ApiPublisherProvider;
import com.xiaopeng.lib.apirouter.server.IManifestHandler;
import com.xiaopeng.lib.apirouter.server.IManifestHelper;
import com.xiaopeng.lib.apirouter.server.ManifestHelper_aar;
import com.xiaopeng.lib.framework.aiassistantmodule.interactive.Constants;
import com.xiaopeng.speech.vui.constants.VuiConstants;
/* loaded from: classes2.dex */
public class IpcServerImpl implements IpcServer {
    private static final String TAG = "IpcSer";
    private String mAppId;
    private String mClientName;
    private IpcServer.OnClientListener mOnClientListener;

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final IpcServerImpl INSTANCE = new IpcServerImpl();

        private SingletonHolder() {
        }
    }

    public static IpcServerImpl get() {
        return SingletonHolder.INSTANCE;
    }

    private IpcServerImpl() {
        LogUtils.d(TAG, "IpcServerImpl");
        ApiPublisherProvider.addManifestHandler(new IManifestHandler() { // from class: com.xiaopeng.aar.server.ipc.-$$Lambda$IpcServerImpl$UxffyduKas_kH5GNsdIRoQdRVAA
            @Override // com.xiaopeng.lib.apirouter.server.IManifestHandler
            public final IManifestHelper[] getManifestHelpers() {
                return IpcServerImpl.lambda$new$0();
            }
        });
        this.mClientName = ClientObserver.class.getSimpleName();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ IManifestHelper[] lambda$new$0() {
        return new IManifestHelper[]{new ManifestHelper_aar()};
    }

    @Override // com.xiaopeng.aar.server.ipc.IpcServer
    public void send(int i, String str, String str2, String str3, byte[] bArr) {
        callApiRouter(i, str, str2, str3, bArr);
    }

    @Override // com.xiaopeng.aar.server.ipc.IpcServer
    public void setOnClientListener(IpcServer.OnClientListener onClientListener) {
        this.mOnClientListener = onClientListener;
    }

    @Override // com.xiaopeng.aar.server.ipc.IpcServer
    public void keepAlive(Context context) {
        context.startForegroundService(new Intent(context, KeepAliveService.class));
    }

    @Override // com.xiaopeng.aar.server.ipc.IpcServer
    public void setAppId(String str) {
        if (this.mAppId != null) {
            return;
        }
        this.mAppId = str;
        ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.aar.server.ipc.-$$Lambda$IpcServerImpl$-268LCc-sVnbIQBG_t8PWWSq4wk
            @Override // java.lang.Runnable
            public final void run() {
                IpcServerImpl.this.lambda$setAppId$1$IpcServerImpl();
            }
        });
    }

    public /* synthetic */ void lambda$setAppId$1$IpcServerImpl() {
        LogUtils.i(TAG, "send SERVER_NOTIFY_STARTED");
        send(202, this.mAppId, null, null, null);
    }

    @Override // com.xiaopeng.aar.server.ipc.IpcServer
    public String onCall(int i, String str, String str2, String str3, byte[] bArr) {
        IpcServer.OnClientListener onClientListener = this.mOnClientListener;
        if (onClientListener != null) {
            return onClientListener.onCall(i, str, str2, str3, bArr);
        }
        return null;
    }

    private void callApiRouter(int i, String str, String str2, String str3, byte[] bArr) {
        try {
            Uri.Builder appendQueryParameter = new Uri.Builder().authority("com.xiaopeng.napa." + this.mClientName).appendQueryParameter(VuiConstants.ELEMENT_TYPE, String.valueOf(i)).appendQueryParameter("appId", this.mAppId).appendQueryParameter("module", str).appendQueryParameter(Constants.MSG_ID, str2).appendQueryParameter("data", str3);
            if (bArr == null) {
                ApiRouter.route(appendQueryParameter.path("onReceived").build());
            } else {
                ApiRouter.route(appendQueryParameter.path("onReceivedBlob").build(), bArr);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
