package com.xiaopeng.appstore.libcommon.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
/* loaded from: classes2.dex */
public abstract class CountDownTimer {
    private static final int EMPTY_WHAT = 1;
    private long mCountDownInterval;
    private long mMillisInFuture;
    private final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.xiaopeng.appstore.libcommon.utils.CountDownTimer.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (CountDownTimer.this.mPaused) {
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            CountDownTimer.this.mLastTickTime = currentTimeMillis;
            CountDownTimer.access$214(CountDownTimer.this, currentTimeMillis - CountDownTimer.this.mLastTickTime);
            long j = CountDownTimer.this.mMillisInFuture - CountDownTimer.this.mUsedTime;
            if (j <= 0) {
                CountDownTimer.this.onFinish();
                return;
            }
            CountDownTimer.this.onTick(j);
            CountDownTimer.this.mHandler.sendEmptyMessageDelayed(1, CountDownTimer.this.mCountDownInterval);
        }
    };
    private long mLastTickTime = -1;
    private long mUsedTime = 0;
    private boolean mPaused = false;

    protected abstract void onFinish();

    protected abstract void onTick(long time);

    static /* synthetic */ long access$214(CountDownTimer countDownTimer, long j) {
        long j2 = countDownTimer.mUsedTime + j;
        countDownTimer.mUsedTime = j2;
        return j2;
    }

    public CountDownTimer(long millisInFuture, long countDownInterval) {
        this.mMillisInFuture = millisInFuture;
        this.mCountDownInterval = countDownInterval;
    }

    public void setMillisInFuture(long millisInFuture, long countDownInterval) {
        this.mHandler.removeCallbacksAndMessages(null);
        this.mPaused = true;
        this.mMillisInFuture = millisInFuture;
        this.mCountDownInterval = countDownInterval;
    }

    public long getUsedTime() {
        return this.mUsedTime;
    }

    public void resume() {
        if (this.mPaused) {
            this.mLastTickTime = System.currentTimeMillis();
            this.mPaused = false;
            this.mHandler.sendEmptyMessage(1);
        }
    }

    public void pause() {
        if (this.mPaused) {
            return;
        }
        this.mPaused = true;
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public void start() {
        this.mPaused = false;
        this.mUsedTime = 0L;
        this.mLastTickTime = System.currentTimeMillis();
        this.mHandler.sendEmptyMessage(1);
    }

    public void cancel() {
        this.mPaused = true;
        this.mUsedTime = 0L;
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public boolean isRunning() {
        return !this.mPaused;
    }
}
