package com.xiaopeng.appstore.storeprovider.store;

import com.xiaopeng.appstore.storeprovider.store.bean.ResourceDownloadInfo;
/* loaded from: classes2.dex */
public interface RMDownloadListener {
    void onDownloadCallback(int i, ResourceDownloadInfo resourceDownloadInfo);

    void onMenuOpenCallback(String str);

    void unbindService();
}
