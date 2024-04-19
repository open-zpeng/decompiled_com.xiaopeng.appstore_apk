package com.xiaopeng.aar.client.app;

import com.xiaopeng.aar.client.ApiListener;
/* loaded from: classes2.dex */
public class ServerTestProcessorImpl extends AppProcessorImpl {
    @Override // com.xiaopeng.aar.client.app.AppProcessorImpl, com.xiaopeng.aar.client.app.AppProcessor
    public /* bridge */ /* synthetic */ void onReceived(ApiListener apiListener, String str, String str2, String str3, String str4, byte[] bArr) {
        super.onReceived(apiListener, str, str2, str3, str4, bArr);
    }

    @Override // com.xiaopeng.aar.client.app.AppProcessorImpl, com.xiaopeng.aar.client.app.AppProcessor
    public /* bridge */ /* synthetic */ void onServerStatus(ApiListener apiListener, String str, int i) {
        super.onServerStatus(apiListener, str, i);
    }

    ServerTestProcessorImpl(String str) {
        super(str);
    }

    @Override // com.xiaopeng.aar.client.app.AppProcessorImpl, com.xiaopeng.aar.client.app.AppProcessor
    public String call(String str, String str2, String str3, String str4, byte[] bArr) {
        return super.call(str, str2, str3, str4, bArr);
    }
}
