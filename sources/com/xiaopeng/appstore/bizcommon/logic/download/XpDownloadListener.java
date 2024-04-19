package com.xiaopeng.appstore.bizcommon.logic.download;

import java.io.File;
/* loaded from: classes2.dex */
public interface XpDownloadListener {
    void onDownloadCancel(int id, String downloadUrl);

    void onDownloadComplete(int id, String downloadUrl, File file);

    void onDownloadError(int id, String downloadUrl, String msg);

    void onDownloadPause(int id, String downloadUrl);

    void onDownloadProgress(int id, String downloadUrl, long totalBytes, long downloadedBytes, long speedPerSecond);

    void onDownloadStart(int id, String downloadUrl, String fileName, long totalBytes);
}
