package com.xiaopeng.aar.server.ipc;

import com.xiaopeng.aar.utils.LogUtils;
import com.xiaopeng.lib.apirouter.server.IServicePublisher;
/* loaded from: classes2.dex */
public class ServerObserver implements IServicePublisher {
    private static final String TAG = "ServerOb";

    public String call(int i, String str, String str2, String str3) {
        try {
            LogUtils.d(TAG, String.format("call-- type:%s , module:%s ,method:%s ,param:%s", Integer.valueOf(i), str, str2, str3));
            return IpcServerImpl.get().onCall(i, str, str2, str3, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String callBlob(int i, String str, String str2, String str3, byte[] bArr) {
        try {
            Object[] objArr = new Object[5];
            objArr[0] = Integer.valueOf(i);
            objArr[1] = str;
            objArr[2] = str2;
            objArr[3] = str3;
            objArr[4] = bArr == null ? "" : Integer.valueOf(bArr.length);
            LogUtils.d(TAG, String.format("callBlob-- type:%s , module:%s ,method:%s ,param:%s ,blob-length:%s", objArr));
            return IpcServerImpl.get().onCall(i, str, str2, str3, bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
