package com.liulishuo.okdownload.core.breakpoint;

import android.content.Context;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.Util;
import com.liulishuo.okdownload.core.cause.EndCause;
import java.io.IOException;
/* loaded from: classes2.dex */
public class BreakpointStoreOnSQLite implements DownloadStore {
    private static final String TAG = "BreakpointStoreOnSQLite";
    protected final BreakpointSQLiteHelper helper;
    protected final BreakpointStoreOnCache onCache;

    @Override // com.liulishuo.okdownload.core.breakpoint.DownloadStore
    public BreakpointInfo getAfterCompleted(int i) {
        return null;
    }

    @Override // com.liulishuo.okdownload.core.breakpoint.BreakpointStore
    public boolean isOnlyMemoryCache() {
        return false;
    }

    BreakpointStoreOnSQLite(BreakpointSQLiteHelper breakpointSQLiteHelper, BreakpointStoreOnCache breakpointStoreOnCache) {
        this.helper = breakpointSQLiteHelper;
        this.onCache = breakpointStoreOnCache;
    }

    public BreakpointStoreOnSQLite(Context context) {
        BreakpointSQLiteHelper breakpointSQLiteHelper = new BreakpointSQLiteHelper(context.getApplicationContext());
        this.helper = breakpointSQLiteHelper;
        this.onCache = new BreakpointStoreOnCache(breakpointSQLiteHelper.loadToCache(), breakpointSQLiteHelper.loadDirtyFileList(), breakpointSQLiteHelper.loadResponseFilenameToMap());
    }

    @Override // com.liulishuo.okdownload.core.breakpoint.BreakpointStore
    public BreakpointInfo get(int i) {
        return this.onCache.get(i);
    }

    @Override // com.liulishuo.okdownload.core.breakpoint.BreakpointStore
    public BreakpointInfo createAndInsert(DownloadTask downloadTask) throws IOException {
        BreakpointInfo createAndInsert = this.onCache.createAndInsert(downloadTask);
        this.helper.insert(createAndInsert);
        return createAndInsert;
    }

    @Override // com.liulishuo.okdownload.core.breakpoint.DownloadStore
    public void onTaskStart(int i) {
        this.onCache.onTaskStart(i);
    }

    @Override // com.liulishuo.okdownload.core.breakpoint.DownloadStore
    public void onSyncToFilesystemSuccess(BreakpointInfo breakpointInfo, int i, long j) throws IOException {
        this.onCache.onSyncToFilesystemSuccess(breakpointInfo, i, j);
        this.helper.updateBlockIncrease(breakpointInfo, i, breakpointInfo.getBlock(i).getCurrentOffset());
    }

    @Override // com.liulishuo.okdownload.core.breakpoint.BreakpointStore
    public boolean update(BreakpointInfo breakpointInfo) throws IOException {
        boolean update = this.onCache.update(breakpointInfo);
        this.helper.updateInfo(breakpointInfo);
        String filename = breakpointInfo.getFilename();
        Util.d(TAG, "update " + breakpointInfo);
        if (breakpointInfo.isTaskOnlyProvidedParentPath() && filename != null) {
            this.helper.updateFilename(breakpointInfo.getUrl(), filename);
        }
        return update;
    }

    @Override // com.liulishuo.okdownload.core.breakpoint.DownloadStore
    public void onTaskEnd(int i, EndCause endCause, Exception exc) {
        this.onCache.onTaskEnd(i, endCause, exc);
        if (endCause == EndCause.COMPLETED) {
            this.helper.removeInfo(i);
        }
    }

    @Override // com.liulishuo.okdownload.core.breakpoint.DownloadStore
    public boolean markFileDirty(int i) {
        if (this.onCache.markFileDirty(i)) {
            this.helper.markFileDirty(i);
            return true;
        }
        return false;
    }

    @Override // com.liulishuo.okdownload.core.breakpoint.DownloadStore
    public boolean markFileClear(int i) {
        if (this.onCache.markFileClear(i)) {
            this.helper.markFileClear(i);
            return true;
        }
        return false;
    }

    @Override // com.liulishuo.okdownload.core.breakpoint.BreakpointStore
    public void remove(int i) {
        this.onCache.remove(i);
        this.helper.removeInfo(i);
    }

    @Override // com.liulishuo.okdownload.core.breakpoint.BreakpointStore
    public int findOrCreateId(DownloadTask downloadTask) {
        return this.onCache.findOrCreateId(downloadTask);
    }

    @Override // com.liulishuo.okdownload.core.breakpoint.BreakpointStore
    public BreakpointInfo findAnotherInfoFromCompare(DownloadTask downloadTask, BreakpointInfo breakpointInfo) {
        return this.onCache.findAnotherInfoFromCompare(downloadTask, breakpointInfo);
    }

    @Override // com.liulishuo.okdownload.core.breakpoint.BreakpointStore
    public boolean isFileDirty(int i) {
        return this.onCache.isFileDirty(i);
    }

    @Override // com.liulishuo.okdownload.core.breakpoint.BreakpointStore
    public String getResponseFilename(String str) {
        return this.onCache.getResponseFilename(str);
    }

    void close() {
        this.helper.close();
    }

    public DownloadStore createRemitSelf() {
        return new RemitStoreOnSQLite(this);
    }
}
