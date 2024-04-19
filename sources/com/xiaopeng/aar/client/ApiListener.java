package com.xiaopeng.aar.client;
/* loaded from: classes2.dex */
public interface ApiListener {
    void onReceived(String str, String str2, String str3, String str4, byte[] bArr);

    default void onServerStatus(String str, int i) {
    }
}
