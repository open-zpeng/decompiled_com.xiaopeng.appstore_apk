package com.xiaopeng.appstore.libcommon.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appserver_common.NapaHandler;
/* loaded from: classes2.dex */
public class IdleViewDetectHelper {
    private static final String TAG = "IdleViewDetectHelper";
    private IdleCallback mCallback;
    private long mIdleDelay = 0;
    private boolean mEnable = true;
    private boolean mIsRunning = false;
    private Handler mTimeOutHandler = new Handler(Looper.myLooper());
    private Runnable mTimeOutRunnable = new Runnable() { // from class: com.xiaopeng.appstore.libcommon.utils.-$$Lambda$IdleViewDetectHelper$Cdse6jk1FfLwZMk_IkgdvtP7PAw
        @Override // java.lang.Runnable
        public final void run() {
            IdleViewDetectHelper.this.lambda$new$0$IdleViewDetectHelper();
        }
    };

    /* loaded from: classes2.dex */
    public interface IdleCallback {
        void onIdle();

        void onIdleDetectStart();

        void onIdleDetectStop();
    }

    public /* synthetic */ void lambda$new$0$IdleViewDetectHelper() {
        this.mIsRunning = false;
        IdleCallback idleCallback = this.mCallback;
        if (idleCallback != null) {
            idleCallback.onIdle();
        }
    }

    public void setCallback(IdleCallback callback) {
        this.mCallback = callback;
    }

    public void setIdleDelay(long idleDelay) {
        this.mIdleDelay = idleDelay;
    }

    public void enableDetect() {
        this.mEnable = true;
    }

    public void disableDetect() {
        this.mEnable = false;
        stop();
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }

    public void dispatchTouchEvent(MotionEvent event) {
        if (this.mEnable) {
            int actionMasked = event.getActionMasked();
            if (actionMasked == 0) {
                stop();
            } else if (actionMasked == 1 || actionMasked == 3) {
                start();
            }
        }
    }

    public boolean start() {
        if (this.mEnable) {
            if (this.mIsRunning) {
                return true;
            }
            Logger.t(TAG).v("start", new Object[0]);
            if (this.mIdleDelay >= 0) {
                this.mTimeOutHandler.removeCallbacks(this.mTimeOutRunnable);
                this.mIsRunning = true;
                this.mTimeOutHandler.postDelayed(this.mTimeOutRunnable, this.mIdleDelay);
                IdleCallback idleCallback = this.mCallback;
                if (idleCallback != null) {
                    idleCallback.onIdleDetectStart();
                }
            }
            return true;
        }
        return false;
    }

    public void stop() {
        Logger.t(TAG).v(NapaHandler.METHOD_STOP, new Object[0]);
        this.mIsRunning = false;
        this.mTimeOutHandler.removeCallbacks(this.mTimeOutRunnable);
        IdleCallback idleCallback = this.mCallback;
        if (idleCallback != null) {
            idleCallback.onIdleDetectStop();
        }
    }

    /* loaded from: classes2.dex */
    public abstract class SimpleIdleCallback implements IdleCallback {
        @Override // com.xiaopeng.appstore.libcommon.utils.IdleViewDetectHelper.IdleCallback
        public void onIdle() {
        }

        @Override // com.xiaopeng.appstore.libcommon.utils.IdleViewDetectHelper.IdleCallback
        public void onIdleDetectStart() {
        }

        @Override // com.xiaopeng.appstore.libcommon.utils.IdleViewDetectHelper.IdleCallback
        public void onIdleDetectStop() {
        }

        public SimpleIdleCallback() {
        }
    }
}
