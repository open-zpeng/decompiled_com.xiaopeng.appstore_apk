package com.xiaopeng.appstore.libcommon.utils.actionprocessor;

import com.orhanobut.logger.Logger;
import java.time.Duration;
/* loaded from: classes2.dex */
public class RetryExecutor extends PolicyExecutor<RetryPolicy> {
    private static final String TAG = "RetryExecutor";
    private volatile boolean mAborted;
    private Thread mExecuteThread;
    private int mFailedAttempts;

    public static long randomDelayInRange(long delayMin, long delayMax, double random) {
        return ((long) (random * (delayMax - delayMin))) + delayMin;
    }

    public RetryExecutor(RetryPolicy policy) {
        super(policy);
        this.mAborted = false;
        this.mFailedAttempts = 0;
    }

    @Override // com.xiaopeng.appstore.libcommon.utils.actionprocessor.PolicyExecutor
    public <T> Result<T> run(Callable<T> runnable) {
        Result<T> result;
        if (this.mAborted) {
            Logger.t(TAG).i("run, aborted before run, policy is " + (this.mPolicy != 0 ? Integer.valueOf(((RetryPolicy) this.mPolicy).hashCode()) : null) + " currentThread " + this.mExecuteThread.getId(), new Object[0]);
            this.mAborted = false;
            return null;
        }
        this.mExecuteThread = Thread.currentThread();
        Logger.t(TAG).i("run, policy is " + (this.mPolicy != 0 ? Integer.valueOf(((RetryPolicy) this.mPolicy).hashCode()) : null) + " currentThread " + this.mExecuteThread.getId(), new Object[0]);
        if (this.mPolicy != 0) {
            result = executeWithPolicy(runnable);
        } else {
            try {
                result = new OkResult(runnable.run(this));
            } catch (Throwable unused) {
                result = null;
            }
        }
        if (this.mAborted) {
            Logger.t(TAG).i("run, aborted after, policy is " + (this.mPolicy != 0 ? Integer.valueOf(((RetryPolicy) this.mPolicy).hashCode()) : null) + " currentThread " + this.mExecuteThread.getId(), new Object[0]);
            this.mAborted = false;
            result = null;
        }
        this.mExecuteThread = null;
        return result;
    }

    @Override // com.xiaopeng.appstore.libcommon.utils.actionprocessor.PolicyExecutor
    public void abort() {
        Logger.t(TAG).i("abort, policy is " + (this.mPolicy != 0 ? Integer.valueOf(((RetryPolicy) this.mPolicy).hashCode()) : null), new Object[0]);
        this.mAborted = true;
        Thread thread = this.mExecuteThread;
        if (thread == null || thread.isInterrupted()) {
            return;
        }
        Logger.t(TAG).i("abort, policy is " + (this.mPolicy != 0 ? Integer.valueOf(((RetryPolicy) this.mPolicy).hashCode()) : null) + " interrupt thread " + this.mExecuteThread.getId(), new Object[0]);
        this.mExecuteThread.interrupt();
    }

    public boolean isAborted() {
        return this.mAborted;
    }

    public <T> Result<T> executeWithPolicy(Callable<T> runnable) {
        if (this.mPolicy == 0) {
            Logger.t(TAG).w("executeWithPolicy policy is null.", new Object[0]);
            return null;
        }
        this.mAborted = false;
        Object obj = null;
        while (!this.mAborted) {
            if (obj == null && this.mFailedAttempts <= ((RetryPolicy) this.mPolicy).getMaxRetryCount()) {
                Logger.t(TAG).i("RetryPolicy(" + ((RetryPolicy) this.mPolicy).hashCode() + ") retry start, attempts=" + this.mFailedAttempts, new Object[0]);
                obj = retry(runnable);
                this.mFailedAttempts++;
                if (!this.mAborted && obj == null && this.mFailedAttempts <= ((RetryPolicy) this.mPolicy).getMaxRetryCount()) {
                    long fixedOrRandomDelayMillis = getFixedOrRandomDelayMillis();
                    Logger.t(TAG).i("RetryPolicy(" + ((RetryPolicy) this.mPolicy).hashCode() + ") retry fail, attempts=" + this.mFailedAttempts + " retry after " + fixedOrRandomDelayMillis + " ms", new Object[0]);
                    if (fixedOrRandomDelayMillis > 0) {
                        try {
                            Thread.sleep(fixedOrRandomDelayMillis);
                        } catch (InterruptedException unused) {
                            Logger.t(TAG).w("RetryPolicy(" + ((RetryPolicy) this.mPolicy).hashCode() + ") abort, thread interrupted(" + Thread.currentThread().getId() + "), attempts=" + this.mFailedAttempts + " " + obj, new Object[0]);
                        }
                    } else {
                        continue;
                    }
                }
            } else {
                Logger.t(TAG).i("RetryPolicy(" + ((RetryPolicy) this.mPolicy).hashCode() + ") retry finish, attempt=" + this.mFailedAttempts + " " + obj, new Object[0]);
                this.mFailedAttempts = 0;
                break;
            }
        }
        Logger.t(TAG).w("RetryPolicy(" + ((RetryPolicy) this.mPolicy).hashCode() + ") abort, attempts=" + this.mFailedAttempts + " " + obj, new Object[0]);
        if (obj != null) {
            return new OkResult(obj);
        }
        return null;
    }

    private <T> T retry(Callable<T> runnable) {
        try {
            return runnable.run(this);
        } catch (Throwable unused) {
            return null;
        }
    }

    private long getFixedOrRandomDelayMillis() {
        Duration delayMin = ((RetryPolicy) this.mPolicy).getDelayMin();
        Duration delayMax = ((RetryPolicy) this.mPolicy).getDelayMax();
        if (delayMin == null || delayMax == null) {
            return 0L;
        }
        return randomDelayInRange(delayMin.toMillis(), delayMax.toMillis(), Math.random());
    }
}
