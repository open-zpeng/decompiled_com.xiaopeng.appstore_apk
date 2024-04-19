package com.xiaopeng.appstore.bizcommon.logic.download;

import com.orhanobut.logger.Logger;
import java.io.File;
/* loaded from: classes2.dex */
public class XpDownloaderDefaultMonitor implements IXpDownloaderMonitor {
    @Override // com.xiaopeng.appstore.bizcommon.logic.download.IXpDownloaderMonitor
    public void onDownloadComplete(String id, String url, File file) {
        Logger.d("download complete id=" + id + " url=" + url + " filePath=" + file.getPath());
    }
}
