package com.xiaopeng.aar.client.app;

import com.xiaopeng.aar.client.ApiListener;
import com.xiaopeng.aar.client.ipc.IpcManager;
import com.xiaopeng.aar.utils.HandlerThreadHelper;
import com.xiaopeng.aar.utils.LogUtils;
/* loaded from: classes2.dex */
class AppProcessorImpl implements AppProcessor {
    private static final String TAG = "AppProcessor";
    private static final byte[] mDefaultBytes = new byte[0];
    private static final String mDefaultString = "";
    private final HandlerThreadHelper mHandlerThreadHelper;

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppProcessorImpl(String str) {
        this.mHandlerThreadHelper = new HandlerThreadHelper(str);
    }

    @Override // com.xiaopeng.aar.client.app.AppProcessor
    public String call(String str, String str2, String str3, String str4, byte[] bArr) {
        String call = IpcManager.get().getIpc().call(str, str2, str3, str4, bArr);
        return call == null ? "" : call;
    }

    @Override // com.xiaopeng.aar.client.app.AppProcessor
    public void onReceived(final ApiListener apiListener, final String str, final String str2, final String str3, final String str4, final byte[] bArr) {
        this.mHandlerThreadHelper.post(new Runnable() { // from class: com.xiaopeng.aar.client.app.-$$Lambda$AppProcessorImpl$sRiMi2uLR56R9BewmXTQbMmIrA4
            @Override // java.lang.Runnable
            public final void run() {
                AppProcessorImpl.lambda$onReceived$0(ApiListener.this, str, str2, str3, str4, bArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onReceived$0(ApiListener apiListener, String str, String str2, String str3, String str4, byte[] bArr) {
        byte[] bArr2;
        String str5 = str4 == null ? "" : str4;
        if (bArr == null) {
            try {
                bArr2 = mDefaultBytes;
            } catch (Exception e) {
                LogUtils.e(TAG, "onReceived exception=" + e.getMessage());
                return;
            }
        } else {
            bArr2 = bArr;
        }
        apiListener.onReceived(str, str2, str3, str5, bArr2);
        Object[] objArr = new Object[5];
        objArr[0] = str;
        objArr[1] = str2;
        objArr[2] = str3;
        objArr[3] = Boolean.valueOf(str4 == null);
        objArr[4] = Boolean.valueOf(bArr == null);
        LogUtils.i(TAG, String.format("onReceived post appId:%s ,module:%s ,msgId:%s,data:%s ,blob:%s", objArr));
    }

    @Override // com.xiaopeng.aar.client.app.AppProcessor
    public void onServerStatus(final ApiListener apiListener, final String str, final int i) {
        this.mHandlerThreadHelper.post(new Runnable() { // from class: com.xiaopeng.aar.client.app.-$$Lambda$AppProcessorImpl$rU0C_QEVLeq2J49CvxlarXY72Vw
            @Override // java.lang.Runnable
            public final void run() {
                AppProcessorImpl.lambda$onServerStatus$1(ApiListener.this, str, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onServerStatus$1(ApiListener apiListener, String str, int i) {
        try {
            apiListener.onServerStatus(str, i);
        } catch (Exception e) {
            LogUtils.e(TAG, "onServerStatus exception=" + e.getMessage());
        }
    }
}
