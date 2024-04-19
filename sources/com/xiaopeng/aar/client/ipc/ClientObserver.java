package com.xiaopeng.aar.client.ipc;

import com.xiaopeng.lib.apirouter.server.IServicePublisher;
/* loaded from: classes2.dex */
public class ClientObserver implements IServicePublisher {
    public void onReceived(int i, String str, String str2, String str3, String str4) {
        try {
            IpcImpl.get().onReceived(i, str, str2, str3, str4, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onReceivedBlob(int i, String str, String str2, String str3, String str4, byte[] bArr) {
        try {
            IpcImpl.get().onReceived(i, str, str2, str3, str4, bArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
