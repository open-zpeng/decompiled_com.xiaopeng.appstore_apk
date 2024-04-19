package com.xiaopeng.appstore.bizcommon.entities;
/* loaded from: classes2.dex */
public class DownloadStatus {
    private boolean mAsync;
    private boolean mGoLoginByDownload;
    private Runnable mRunnable;

    public DownloadStatus(Runnable runnable, boolean async, boolean goLoginByDownload) {
        this.mGoLoginByDownload = false;
        this.mRunnable = runnable;
        this.mAsync = async;
        this.mGoLoginByDownload = goLoginByDownload;
    }

    public Runnable getRunnable() {
        return this.mRunnable;
    }

    public boolean isAsync() {
        return this.mAsync;
    }

    public boolean isGoLoginByDownload() {
        return this.mGoLoginByDownload;
    }
}
