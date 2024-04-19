package com.liulishuo.okdownload.core.dispatcher;

import android.os.SystemClock;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.OkDownload;
import com.liulishuo.okdownload.StatusUtil;
import com.liulishuo.okdownload.core.IdentifiedTask;
import com.liulishuo.okdownload.core.Util;
import com.liulishuo.okdownload.core.breakpoint.DownloadStore;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.download.DownloadCall;
import java.io.File;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/* loaded from: classes2.dex */
public class DownloadDispatcher {
    private static final String TAG = "DownloadDispatcher";
    private volatile ExecutorService executorService;
    private final List<DownloadCall> finishingCalls;
    private final AtomicInteger flyingCanceledAsyncCallCount;
    int maxParallelRunningCount;
    private final List<DownloadCall> readyAsyncCalls;
    private final List<DownloadCall> runningAsyncCalls;
    private final List<DownloadCall> runningSyncCalls;
    private final AtomicInteger skipProceedCallCount;
    private DownloadStore store;

    public DownloadDispatcher() {
        this(new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList());
    }

    DownloadDispatcher(List<DownloadCall> list, List<DownloadCall> list2, List<DownloadCall> list3, List<DownloadCall> list4) {
        this.maxParallelRunningCount = 5;
        this.flyingCanceledAsyncCallCount = new AtomicInteger();
        this.skipProceedCallCount = new AtomicInteger();
        this.readyAsyncCalls = list;
        this.runningAsyncCalls = list2;
        this.runningSyncCalls = list3;
        this.finishingCalls = list4;
    }

    public void setDownloadStore(DownloadStore downloadStore) {
        this.store = downloadStore;
    }

    synchronized ExecutorService getExecutorService() {
        if (this.executorService == null) {
            this.executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkDownload Download", false));
        }
        return this.executorService;
    }

    public void enqueue(DownloadTask[] downloadTaskArr) {
        this.skipProceedCallCount.incrementAndGet();
        enqueueLocked(downloadTaskArr);
        this.skipProceedCallCount.decrementAndGet();
    }

    public void enqueue(DownloadTask downloadTask) {
        this.skipProceedCallCount.incrementAndGet();
        enqueueLocked(downloadTask);
        this.skipProceedCallCount.decrementAndGet();
    }

    private synchronized void enqueueLocked(DownloadTask[] downloadTaskArr) {
        long uptimeMillis = SystemClock.uptimeMillis();
        Util.d(TAG, "start enqueueLocked for bunch task: " + downloadTaskArr.length);
        ArrayList<DownloadTask> arrayList = new ArrayList();
        Collections.addAll(arrayList, downloadTaskArr);
        if (arrayList.size() > 1) {
            Collections.sort(arrayList);
        }
        int size = this.readyAsyncCalls.size();
        try {
            OkDownload.with().downloadStrategy().inspectNetworkAvailable();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            for (DownloadTask downloadTask : arrayList) {
                if (!inspectCompleted(downloadTask, arrayList2) && !inspectForConflict(downloadTask, arrayList3, arrayList4)) {
                    enqueueIgnorePriority(downloadTask);
                }
            }
            OkDownload.with().callbackDispatcher().endTasks(arrayList2, arrayList3, arrayList4);
        } catch (UnknownHostException e) {
            OkDownload.with().callbackDispatcher().endTasksWithError(new ArrayList(arrayList), e);
        }
        if (size != this.readyAsyncCalls.size()) {
            Collections.sort(this.readyAsyncCalls);
        }
        Util.d(TAG, "end enqueueLocked for bunch task: " + downloadTaskArr.length + " consume " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms");
    }

    private synchronized void enqueueLocked(DownloadTask downloadTask) {
        Util.d(TAG, "enqueueLocked for single task: " + downloadTask);
        if (inspectCompleted(downloadTask)) {
            return;
        }
        if (inspectForConflict(downloadTask)) {
            return;
        }
        int size = this.readyAsyncCalls.size();
        enqueueIgnorePriority(downloadTask);
        if (size != this.readyAsyncCalls.size()) {
            Collections.sort(this.readyAsyncCalls);
        }
    }

    private synchronized void enqueueIgnorePriority(DownloadTask downloadTask) {
        DownloadCall create = DownloadCall.create(downloadTask, true, this.store);
        if (runningAsyncSize() < this.maxParallelRunningCount) {
            this.runningAsyncCalls.add(create);
            getExecutorService().execute(create);
        } else {
            this.readyAsyncCalls.add(create);
        }
    }

    public void execute(DownloadTask downloadTask) {
        Util.d(TAG, "execute: " + downloadTask);
        synchronized (this) {
            if (inspectCompleted(downloadTask)) {
                return;
            }
            if (inspectForConflict(downloadTask)) {
                return;
            }
            DownloadCall create = DownloadCall.create(downloadTask, false, this.store);
            this.runningSyncCalls.add(create);
            syncRunCall(create);
        }
    }

    public void cancelAll() {
        this.skipProceedCallCount.incrementAndGet();
        ArrayList arrayList = new ArrayList();
        for (DownloadCall downloadCall : this.readyAsyncCalls) {
            arrayList.add(downloadCall.task);
        }
        for (DownloadCall downloadCall2 : this.runningAsyncCalls) {
            arrayList.add(downloadCall2.task);
        }
        for (DownloadCall downloadCall3 : this.runningSyncCalls) {
            arrayList.add(downloadCall3.task);
        }
        if (!arrayList.isEmpty()) {
            cancelLocked((IdentifiedTask[]) arrayList.toArray(new DownloadTask[arrayList.size()]));
        }
        this.skipProceedCallCount.decrementAndGet();
    }

    public void cancel(IdentifiedTask[] identifiedTaskArr) {
        this.skipProceedCallCount.incrementAndGet();
        cancelLocked(identifiedTaskArr);
        this.skipProceedCallCount.decrementAndGet();
        processCalls();
    }

    public boolean cancel(IdentifiedTask identifiedTask) {
        this.skipProceedCallCount.incrementAndGet();
        boolean cancelLocked = cancelLocked(identifiedTask);
        this.skipProceedCallCount.decrementAndGet();
        processCalls();
        return cancelLocked;
    }

    public boolean cancel(int i) {
        this.skipProceedCallCount.incrementAndGet();
        boolean cancelLocked = cancelLocked(DownloadTask.mockTaskForCompare(i));
        this.skipProceedCallCount.decrementAndGet();
        processCalls();
        return cancelLocked;
    }

    private synchronized void cancelLocked(IdentifiedTask[] identifiedTaskArr) {
        long uptimeMillis = SystemClock.uptimeMillis();
        Util.d(TAG, "start cancel bunch task manually: " + identifiedTaskArr.length);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (IdentifiedTask identifiedTask : identifiedTaskArr) {
            filterCanceledCalls(identifiedTask, arrayList, arrayList2);
        }
        handleCanceledCalls(arrayList, arrayList2);
        Util.d(TAG, "finish cancel bunch task manually: " + identifiedTaskArr.length + " consume " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms");
    }

    synchronized boolean cancelLocked(IdentifiedTask identifiedTask) {
        boolean z;
        Util.d(TAG, "cancel manually: " + identifiedTask.getId());
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        filterCanceledCalls(identifiedTask, arrayList, arrayList2);
        handleCanceledCalls(arrayList, arrayList2);
        if (arrayList.size() <= 0) {
            z = arrayList2.size() > 0;
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0046 A[Catch: all -> 0x0090, TryCatch #0 {, blocks: (B:3:0x0001, B:4:0x0007, B:6:0x000d, B:8:0x0017, B:10:0x0023, B:12:0x0029, B:15:0x0030, B:20:0x003a, B:21:0x0040, B:23:0x0046, B:25:0x0050, B:27:0x005c, B:30:0x0064, B:31:0x006a, B:33:0x0070, B:35:0x007a, B:37:0x0086), top: B:45:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0070 A[Catch: all -> 0x0090, TryCatch #0 {, blocks: (B:3:0x0001, B:4:0x0007, B:6:0x000d, B:8:0x0017, B:10:0x0023, B:12:0x0029, B:15:0x0030, B:20:0x003a, B:21:0x0040, B:23:0x0046, B:25:0x0050, B:27:0x005c, B:30:0x0064, B:31:0x006a, B:33:0x0070, B:35:0x007a, B:37:0x0086), top: B:45:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:6:0x000d A[Catch: all -> 0x0090, TryCatch #0 {, blocks: (B:3:0x0001, B:4:0x0007, B:6:0x000d, B:8:0x0017, B:10:0x0023, B:12:0x0029, B:15:0x0030, B:20:0x003a, B:21:0x0040, B:23:0x0046, B:25:0x0050, B:27:0x005c, B:30:0x0064, B:31:0x006a, B:33:0x0070, B:35:0x007a, B:37:0x0086), top: B:45:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private synchronized void filterCanceledCalls(com.liulishuo.okdownload.core.IdentifiedTask r5, java.util.List<com.liulishuo.okdownload.core.download.DownloadCall> r6, java.util.List<com.liulishuo.okdownload.core.download.DownloadCall> r7) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.util.List<com.liulishuo.okdownload.core.download.DownloadCall> r0 = r4.readyAsyncCalls     // Catch: java.lang.Throwable -> L90
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L90
        L7:
            boolean r1 = r0.hasNext()     // Catch: java.lang.Throwable -> L90
            if (r1 == 0) goto L3a
            java.lang.Object r1 = r0.next()     // Catch: java.lang.Throwable -> L90
            com.liulishuo.okdownload.core.download.DownloadCall r1 = (com.liulishuo.okdownload.core.download.DownloadCall) r1     // Catch: java.lang.Throwable -> L90
            com.liulishuo.okdownload.DownloadTask r2 = r1.task     // Catch: java.lang.Throwable -> L90
            if (r2 == r5) goto L23
            com.liulishuo.okdownload.DownloadTask r2 = r1.task     // Catch: java.lang.Throwable -> L90
            int r2 = r2.getId()     // Catch: java.lang.Throwable -> L90
            int r3 = r5.getId()     // Catch: java.lang.Throwable -> L90
            if (r2 != r3) goto L7
        L23:
            boolean r5 = r1.isCanceled()     // Catch: java.lang.Throwable -> L90
            if (r5 != 0) goto L38
            boolean r5 = r1.isFinishing()     // Catch: java.lang.Throwable -> L90
            if (r5 == 0) goto L30
            goto L38
        L30:
            r0.remove()     // Catch: java.lang.Throwable -> L90
            r6.add(r1)     // Catch: java.lang.Throwable -> L90
            monitor-exit(r4)
            return
        L38:
            monitor-exit(r4)
            return
        L3a:
            java.util.List<com.liulishuo.okdownload.core.download.DownloadCall> r0 = r4.runningAsyncCalls     // Catch: java.lang.Throwable -> L90
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L90
        L40:
            boolean r1 = r0.hasNext()     // Catch: java.lang.Throwable -> L90
            if (r1 == 0) goto L64
            java.lang.Object r1 = r0.next()     // Catch: java.lang.Throwable -> L90
            com.liulishuo.okdownload.core.download.DownloadCall r1 = (com.liulishuo.okdownload.core.download.DownloadCall) r1     // Catch: java.lang.Throwable -> L90
            com.liulishuo.okdownload.DownloadTask r2 = r1.task     // Catch: java.lang.Throwable -> L90
            if (r2 == r5) goto L5c
            com.liulishuo.okdownload.DownloadTask r2 = r1.task     // Catch: java.lang.Throwable -> L90
            int r2 = r2.getId()     // Catch: java.lang.Throwable -> L90
            int r3 = r5.getId()     // Catch: java.lang.Throwable -> L90
            if (r2 != r3) goto L40
        L5c:
            r6.add(r1)     // Catch: java.lang.Throwable -> L90
            r7.add(r1)     // Catch: java.lang.Throwable -> L90
            monitor-exit(r4)
            return
        L64:
            java.util.List<com.liulishuo.okdownload.core.download.DownloadCall> r0 = r4.runningSyncCalls     // Catch: java.lang.Throwable -> L90
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L90
        L6a:
            boolean r1 = r0.hasNext()     // Catch: java.lang.Throwable -> L90
            if (r1 == 0) goto L8e
            java.lang.Object r1 = r0.next()     // Catch: java.lang.Throwable -> L90
            com.liulishuo.okdownload.core.download.DownloadCall r1 = (com.liulishuo.okdownload.core.download.DownloadCall) r1     // Catch: java.lang.Throwable -> L90
            com.liulishuo.okdownload.DownloadTask r2 = r1.task     // Catch: java.lang.Throwable -> L90
            if (r2 == r5) goto L86
            com.liulishuo.okdownload.DownloadTask r2 = r1.task     // Catch: java.lang.Throwable -> L90
            int r2 = r2.getId()     // Catch: java.lang.Throwable -> L90
            int r3 = r5.getId()     // Catch: java.lang.Throwable -> L90
            if (r2 != r3) goto L6a
        L86:
            r6.add(r1)     // Catch: java.lang.Throwable -> L90
            r7.add(r1)     // Catch: java.lang.Throwable -> L90
            monitor-exit(r4)
            return
        L8e:
            monitor-exit(r4)
            return
        L90:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.liulishuo.okdownload.core.dispatcher.DownloadDispatcher.filterCanceledCalls(com.liulishuo.okdownload.core.IdentifiedTask, java.util.List, java.util.List):void");
    }

    private synchronized void handleCanceledCalls(List<DownloadCall> list, List<DownloadCall> list2) {
        Util.d(TAG, "handle cancel calls, cancel calls: " + list2.size());
        if (!list2.isEmpty()) {
            for (DownloadCall downloadCall : list2) {
                if (!downloadCall.cancel()) {
                    list.remove(downloadCall);
                }
            }
        }
        Util.d(TAG, "handle cancel calls, callback cancel event: " + list.size());
        if (!list.isEmpty()) {
            if (list.size() <= 1) {
                OkDownload.with().callbackDispatcher().dispatch().taskEnd(list.get(0).task, EndCause.CANCELED, null);
            } else {
                ArrayList arrayList = new ArrayList();
                for (DownloadCall downloadCall2 : list) {
                    arrayList.add(downloadCall2.task);
                }
                OkDownload.with().callbackDispatcher().endTasksWithCanceled(arrayList);
            }
        }
    }

    public synchronized DownloadTask findSameTask(DownloadTask downloadTask) {
        Util.d(TAG, "findSameTask: " + downloadTask.getId());
        for (DownloadCall downloadCall : this.readyAsyncCalls) {
            if (!downloadCall.isCanceled() && downloadCall.equalsTask(downloadTask)) {
                return downloadCall.task;
            }
        }
        for (DownloadCall downloadCall2 : this.runningAsyncCalls) {
            if (!downloadCall2.isCanceled() && downloadCall2.equalsTask(downloadTask)) {
                return downloadCall2.task;
            }
        }
        for (DownloadCall downloadCall3 : this.runningSyncCalls) {
            if (!downloadCall3.isCanceled() && downloadCall3.equalsTask(downloadTask)) {
                return downloadCall3.task;
            }
        }
        return null;
    }

    public synchronized boolean isRunning(DownloadTask downloadTask) {
        Util.d(TAG, "isRunning: " + downloadTask.getId());
        for (DownloadCall downloadCall : this.runningSyncCalls) {
            if (!downloadCall.isCanceled() && downloadCall.equalsTask(downloadTask)) {
                return !downloadCall.isFinishing();
            }
        }
        for (DownloadCall downloadCall2 : this.runningAsyncCalls) {
            if (!downloadCall2.isCanceled() && downloadCall2.equalsTask(downloadTask)) {
                return !downloadCall2.isFinishing();
            }
        }
        return false;
    }

    public synchronized boolean isPending(DownloadTask downloadTask) {
        Util.d(TAG, "isPending: " + downloadTask.getId());
        for (DownloadCall downloadCall : this.readyAsyncCalls) {
            if (!downloadCall.isCanceled() && downloadCall.equalsTask(downloadTask)) {
                return true;
            }
        }
        return false;
    }

    void syncRunCall(DownloadCall downloadCall) {
        downloadCall.run();
    }

    public synchronized void flyingCanceled(DownloadCall downloadCall) {
        Util.d(TAG, "flying canceled: " + downloadCall.task.getId());
        if (downloadCall.asyncExecuted) {
            this.flyingCanceledAsyncCallCount.incrementAndGet();
        }
    }

    public synchronized void finish(DownloadCall downloadCall) {
        List<DownloadCall> list;
        boolean z = downloadCall.asyncExecuted;
        if (this.finishingCalls.contains(downloadCall)) {
            list = this.finishingCalls;
        } else if (z) {
            list = this.runningAsyncCalls;
        } else {
            list = this.runningSyncCalls;
        }
        if (!list.remove(downloadCall)) {
            throw new AssertionError("Call wasn't in-flight!");
        }
        if (z && downloadCall.isCanceled()) {
            this.flyingCanceledAsyncCallCount.decrementAndGet();
        }
        if (z) {
            processCalls();
        }
    }

    public synchronized boolean isFileConflictAfterRun(DownloadTask downloadTask) {
        File file;
        File file2;
        Util.d(TAG, "is file conflict after run: " + downloadTask.getId());
        File file3 = downloadTask.getFile();
        if (file3 == null) {
            return false;
        }
        for (DownloadCall downloadCall : this.runningSyncCalls) {
            if (!downloadCall.isCanceled() && downloadCall.task != downloadTask && (file2 = downloadCall.task.getFile()) != null && file3.equals(file2)) {
                return true;
            }
        }
        for (DownloadCall downloadCall2 : this.runningAsyncCalls) {
            if (!downloadCall2.isCanceled() && downloadCall2.task != downloadTask && (file = downloadCall2.task.getFile()) != null && file3.equals(file)) {
                return true;
            }
        }
        return false;
    }

    private boolean inspectForConflict(DownloadTask downloadTask) {
        return inspectForConflict(downloadTask, null, null);
    }

    private boolean inspectForConflict(DownloadTask downloadTask, Collection<DownloadTask> collection, Collection<DownloadTask> collection2) {
        return inspectForConflict(downloadTask, this.readyAsyncCalls, collection, collection2) || inspectForConflict(downloadTask, this.runningAsyncCalls, collection, collection2) || inspectForConflict(downloadTask, this.runningSyncCalls, collection, collection2);
    }

    boolean inspectCompleted(DownloadTask downloadTask) {
        return inspectCompleted(downloadTask, null);
    }

    boolean inspectCompleted(DownloadTask downloadTask, Collection<DownloadTask> collection) {
        if (downloadTask.isPassIfAlreadyCompleted() && StatusUtil.isCompleted(downloadTask)) {
            if (downloadTask.getFilename() != null || OkDownload.with().downloadStrategy().validFilenameFromStore(downloadTask)) {
                OkDownload.with().downloadStrategy().validInfoOnCompleted(downloadTask, this.store);
                if (collection != null) {
                    collection.add(downloadTask);
                    return true;
                }
                OkDownload.with().callbackDispatcher().dispatch().taskEnd(downloadTask, EndCause.COMPLETED, null);
                return true;
            }
            return false;
        }
        return false;
    }

    boolean inspectForConflict(DownloadTask downloadTask, Collection<DownloadCall> collection, Collection<DownloadTask> collection2, Collection<DownloadTask> collection3) {
        CallbackDispatcher callbackDispatcher = OkDownload.with().callbackDispatcher();
        Iterator<DownloadCall> it = collection.iterator();
        while (it.hasNext()) {
            DownloadCall next = it.next();
            if (!next.isCanceled()) {
                if (next.equalsTask(downloadTask)) {
                    if (next.isFinishing()) {
                        Util.d(TAG, "task: " + downloadTask.getId() + " is finishing, move it to finishing list");
                        this.finishingCalls.add(next);
                        it.remove();
                        return false;
                    }
                    if (collection2 != null) {
                        collection2.add(downloadTask);
                    } else {
                        callbackDispatcher.dispatch().taskEnd(downloadTask, EndCause.SAME_TASK_BUSY, null);
                    }
                    return true;
                }
                File file = next.getFile();
                File file2 = downloadTask.getFile();
                if (file != null && file2 != null && file.equals(file2)) {
                    if (collection3 != null) {
                        collection3.add(downloadTask);
                    } else {
                        callbackDispatcher.dispatch().taskEnd(downloadTask, EndCause.FILE_BUSY, null);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private synchronized void processCalls() {
        if (this.skipProceedCallCount.get() > 0) {
            return;
        }
        if (runningAsyncSize() >= this.maxParallelRunningCount) {
            return;
        }
        if (this.readyAsyncCalls.isEmpty()) {
            return;
        }
        Iterator<DownloadCall> it = this.readyAsyncCalls.iterator();
        while (it.hasNext()) {
            DownloadCall next = it.next();
            it.remove();
            DownloadTask downloadTask = next.task;
            if (isFileConflictAfterRun(downloadTask)) {
                OkDownload.with().callbackDispatcher().dispatch().taskEnd(downloadTask, EndCause.FILE_BUSY, null);
            } else {
                this.runningAsyncCalls.add(next);
                getExecutorService().execute(next);
                if (runningAsyncSize() >= this.maxParallelRunningCount) {
                    return;
                }
            }
        }
    }

    private int runningAsyncSize() {
        return this.runningAsyncCalls.size() - this.flyingCanceledAsyncCallCount.get();
    }

    public static void setMaxParallelRunningCount(int i) {
        DownloadDispatcher downloadDispatcher = OkDownload.with().downloadDispatcher();
        if (downloadDispatcher.getClass() != DownloadDispatcher.class) {
            throw new IllegalStateException("The current dispatcher is " + downloadDispatcher + " not DownloadDispatcher exactly!");
        }
        downloadDispatcher.maxParallelRunningCount = Math.max(1, i);
    }
}
