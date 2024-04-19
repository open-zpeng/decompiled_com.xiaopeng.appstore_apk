package com.xiaopeng.appstore.bizcommon.entities.db;

import com.xiaopeng.appstore.bizcommon.entities.download.XpOkDownloadInfo;
/* loaded from: classes2.dex */
public abstract class XpOkDownloadDao {
    public abstract void delete(String url);

    public abstract int getDownloadId(String url);

    public abstract String getUrl(String tag);

    public abstract void insert(XpOkDownloadInfo info);

    public void insert(String url, String tag, int downloadId) {
        XpOkDownloadInfo xpOkDownloadInfo = new XpOkDownloadInfo();
        xpOkDownloadInfo.setUrl(url);
        xpOkDownloadInfo.setTag(tag);
        xpOkDownloadInfo.setDownloadId(downloadId);
        xpOkDownloadInfo.setStatus(0);
        insert(xpOkDownloadInfo);
    }
}
