package com.xiaopeng.appstore.bizcommon.logic.download;

import java.io.File;
/* loaded from: classes2.dex */
public interface IXpDownloaderMonitor {
    void onDownloadComplete(String id, String url, File file);
}
