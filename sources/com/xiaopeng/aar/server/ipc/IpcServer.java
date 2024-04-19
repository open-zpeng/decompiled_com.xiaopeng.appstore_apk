package com.xiaopeng.aar.server.ipc;

import android.content.Context;
/* loaded from: classes2.dex */
public interface IpcServer {

    /* loaded from: classes2.dex */
    public interface OnClientListener {
        String onCall(int i, String str, String str2, String str3, byte[] bArr);

        void onClientDied();
    }

    default void keepAlive(Context context) {
    }

    String onCall(int i, String str, String str2, String str3, byte[] bArr);

    void send(int i, String str, String str2, String str3, byte[] bArr);

    void setAppId(String str);

    void setOnClientListener(OnClientListener onClientListener);
}
