package com.xiaopeng.aar.server;
/* loaded from: classes2.dex */
public interface ServerListener {
    String onCall(String str, String str2, String str3, byte[] bArr);

    void onSubscribe(String str);

    void onUnSubscribe(String str);
}
