package com.xiaopeng.aar.client;

import com.xiaopeng.aar.client.app.AppProcessor;
import com.xiaopeng.aar.client.app.ProcessorManager;
import com.xiaopeng.aar.client.ipc.Ipc;
import com.xiaopeng.aar.client.ipc.IpcManager;
import com.xiaopeng.aar.utils.LogUtils;
import com.xiaopeng.aar.utils.Utils;
/* loaded from: classes2.dex */
public class ApiManager {
    private static final String TAG = "ApiMg";
    private ApiListener mApiListener;

    private boolean receivedType(int i) {
        return i == 0 || i == 201;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingletonHolder {
        private static final ApiManager INSTANCE = new ApiManager();

        private SingletonHolder() {
        }
    }

    public static ApiManager get() {
        return SingletonHolder.INSTANCE;
    }

    private ApiManager() {
        LogUtils.setLogTag("sdkc-");
        LogUtils.i(TAG, "init ,0.0.3-SNAPSHOT_xpdev_7e38035_2022/03/25 18:11:13");
        IpcManager.get().init();
        IpcManager.get().getIpc().setOnServerListener(new Ipc.OnServerListener() { // from class: com.xiaopeng.aar.client.-$$Lambda$ApiManager$dhBX_iU8GUFgerWgJTxLdo9ebfg
            @Override // com.xiaopeng.aar.client.ipc.Ipc.OnServerListener
            public final void onEvent(int i, String str, String str2, String str3, String str4, byte[] bArr) {
                ApiManager.this.onReceived(i, str, str2, str3, str4, bArr);
            }
        });
        IpcManager.get().getIpc().addOnServerStatusListener(new Ipc.OnServerStatusListener() { // from class: com.xiaopeng.aar.client.ApiManager.1
            @Override // com.xiaopeng.aar.client.ipc.Ipc.OnServerStatusListener
            public void onServerStatus(String str, int i) {
                ApiListener apiListener = ApiManager.this.mApiListener;
                if (apiListener != null) {
                    AppProcessor appProcessor = ApiManager.this.getAppProcessor(str);
                    if (appProcessor == null) {
                        LogUtils.e(ApiManager.TAG, String.format("onServerStatus--not app!!!!   appId:%s ", str));
                        return;
                    } else {
                        appProcessor.onServerStatus(apiListener, str, i);
                        return;
                    }
                }
                LogUtils.e(ApiManager.TAG, "onServerStatus listener is null");
            }
        });
        RunningConfig.get().init();
    }

    public void setLogLevel(int i) {
        LogUtils.setLogLevel(i);
    }

    public void setMock(boolean z) {
        if (Utils.isUserRelease()) {
            return;
        }
        if (z) {
            MockTest.get().init();
        } else {
            MockTest.get().release();
        }
    }

    public synchronized void setApiListener(ApiListener apiListener) {
        this.mApiListener = apiListener;
    }

    public void subscribe(String str, String str2, String str3) {
        LogUtils.d(TAG, String.format("subscribe-- appId:%s ,module:%s ,subscriber:%s", str, str2, str3));
        SubscribeManager.get().subscribe(str, str2, str3);
    }

    public void unSubscribe(String str, String str2, String str3) {
        LogUtils.d(TAG, String.format("unSubscribe-- appId:%s ,module:%s ,subscriber:%s", str, str2, str3));
        SubscribeManager.get().unSubscribe(str, str2, str3);
    }

    public String call(String str, String str2, String str3, String str4, byte[] bArr) {
        Object[] objArr = new Object[4];
        objArr[0] = str;
        objArr[1] = str3;
        objArr[2] = str4;
        objArr[3] = bArr == null ? "" : Integer.valueOf(bArr.length);
        LogUtils.i(TAG, String.format("call-- appId:%s ,method:%s ,param:%s ,blob-length:%s", objArr));
        AppProcessor appProcessor = getAppProcessor(str);
        if (appProcessor == null) {
            LogUtils.e(TAG, String.format("call--not app!!!!   appId:%s ", str));
            return null;
        }
        return appProcessor.call(str, str2, str3, str4, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onReceived(int i, String str, String str2, String str3, String str4, byte[] bArr) {
        Object[] objArr = new Object[6];
        objArr[0] = Integer.valueOf(i);
        objArr[1] = str;
        objArr[2] = str2;
        objArr[3] = str3;
        objArr[4] = str4;
        objArr[5] = bArr == null ? "" : Integer.valueOf(bArr.length);
        LogUtils.i(TAG, String.format("onReceived-- type:%s ,appId:%s ,module:%s ,msgId:%s ,data:%s ,blob-length:%s", objArr));
        if (!receivedType(i)) {
            LogUtils.e(TAG, String.format("onReceived--type Undefined   appId:%s, type:%s ", str, Integer.valueOf(i)));
            return;
        }
        ApiListener apiListener = this.mApiListener;
        if (apiListener == null) {
            LogUtils.e(TAG, "onReceived listener is null");
            return;
        }
        AppProcessor appProcessor = getAppProcessor(str);
        if (appProcessor == null) {
            LogUtils.e(TAG, String.format("onReceived--not app!!!!   appId:%s ", str));
        } else if (i == 0) {
            if (SubscribeManager.get().check(str, str2)) {
                appProcessor.onReceived(apiListener, str, str2, str3, str4, bArr);
                return;
            }
            Object[] objArr2 = new Object[5];
            objArr2[0] = str;
            objArr2[1] = str2;
            objArr2[2] = str3;
            objArr2[3] = str4;
            objArr2[4] = bArr != null ? Integer.valueOf(bArr.length) : "";
            LogUtils.w(TAG, String.format("onReceived--not subscribe appId:%s ,module:%s,msgId:%s ,data:%s ,blob-length:%s", objArr2));
        } else {
            appProcessor.onReceived(apiListener, str, str2, str3, str4, bArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AppProcessor getAppProcessor(String str) {
        return ProcessorManager.get().getAppProcessor(str);
    }
}
