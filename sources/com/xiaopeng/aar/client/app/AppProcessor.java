package com.xiaopeng.aar.client.app;

import com.xiaopeng.aar.client.ApiListener;
/* loaded from: classes2.dex */
public interface AppProcessor {
    String call(String str, String str2, String str3, String str4, byte[] bArr);

    void onReceived(ApiListener apiListener, String str, String str2, String str3, String str4, byte[] bArr);

    void onServerStatus(ApiListener apiListener, String str, int i);
}
