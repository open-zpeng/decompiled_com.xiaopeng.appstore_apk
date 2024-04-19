package com.xiaopeng.appstore.unity_plugin.utils;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
/* loaded from: classes2.dex */
public class AppExecutors {
    private static volatile AppExecutors sInstance;
    private final Executor backgroundThread;
    private final Executor diskIO;
    private final Executor mainThread;

    public static AppExecutors get() {
        if (sInstance == null) {
            synchronized (AppExecutors.class) {
                if (sInstance == null) {
                    sInstance = new AppExecutors();
                }
            }
        }
        return sInstance;
    }

    public AppExecutors(Executor diskIO, Executor backgroundThread, Executor mainThread) {
        this.diskIO = diskIO;
        this.backgroundThread = backgroundThread;
        this.mainThread = mainThread;
    }

    public AppExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3), new MainThreadExecutor());
    }

    public Executor diskIO() {
        return this.diskIO;
    }

    public Executor backgroundThread() {
        return this.backgroundThread;
    }

    public Executor mainThread() {
        return this.mainThread;
    }

    /* loaded from: classes2.dex */
    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler;

        private MainThreadExecutor() {
            this.mainThreadHandler = new Handler(Looper.getMainLooper());
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable command) {
            this.mainThreadHandler.post(command);
        }
    }
}
