package com.xiaopeng.appstore.common_ui.common.adapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
/* loaded from: classes.dex */
class AdapterUtils {
    private static ExecutorService sDiffThreadPool;

    private AdapterUtils() {
    }

    public static ExecutorService getDiffThreadPool() {
        if (sDiffThreadPool == null) {
            sDiffThreadPool = Executors.newFixedThreadPool(3, new ThreadFactory() { // from class: com.xiaopeng.appstore.common_ui.common.adapter.AdapterUtils.1
                private static final String THREAD_NAME_STEM = "AdapterDiffThread_%d";
                private final AtomicInteger mThreadId = new AtomicInteger(0);

                @Override // java.util.concurrent.ThreadFactory
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName(String.format(THREAD_NAME_STEM, Integer.valueOf(this.mThreadId.getAndIncrement())));
                    return thread;
                }
            });
        }
        return sDiffThreadPool;
    }
}
