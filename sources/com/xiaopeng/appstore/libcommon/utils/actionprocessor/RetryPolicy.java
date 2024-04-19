package com.xiaopeng.appstore.libcommon.utils.actionprocessor;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
/* loaded from: classes2.dex */
public class RetryPolicy implements Policy {
    private Duration mDelayMax;
    private Duration mDelayMin;
    private int mMaxRetryCount = 3;

    public RetryPolicy withDelay(long delayMin, long delayMax) {
        this.mDelayMin = Duration.of(delayMin, ChronoUnit.MILLIS);
        this.mDelayMax = Duration.of(delayMax, ChronoUnit.MILLIS);
        return this;
    }

    public RetryPolicy withMaxRetryCount(int maxRetryCount) {
        this.mMaxRetryCount = maxRetryCount;
        return this;
    }

    public Duration getDelayMin() {
        return this.mDelayMin;
    }

    public Duration getDelayMax() {
        return this.mDelayMax;
    }

    public int getMaxRetryCount() {
        return this.mMaxRetryCount;
    }

    @Override // com.xiaopeng.appstore.libcommon.utils.actionprocessor.Policy
    public PolicyExecutor<? extends Policy> toExecutor() {
        return new RetryExecutor(this);
    }
}
