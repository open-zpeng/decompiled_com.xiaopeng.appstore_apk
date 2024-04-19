package com.xiaopeng.appstore.libcommon.utils;

import android.util.Log;
import android.view.MotionEvent;
/* loaded from: classes2.dex */
public class HintGestureDetector {
    private static final long INTERVAL = 500;
    private static final String TAG = "HintGestureDetector";
    private int mClickThreshold;
    private OnHintGestureListener mListener;
    private long mLastDownTime = 0;
    private int mClickCount = 0;

    /* loaded from: classes2.dex */
    public interface OnHintGestureListener {
        boolean isHintValid(MotionEvent event);

        void onTrig();
    }

    /* loaded from: classes2.dex */
    public static abstract class SimpleHintGestureListener implements OnHintGestureListener {
        @Override // com.xiaopeng.appstore.libcommon.utils.HintGestureDetector.OnHintGestureListener
        public boolean isHintValid(MotionEvent event) {
            return true;
        }

        @Override // com.xiaopeng.appstore.libcommon.utils.HintGestureDetector.OnHintGestureListener
        public abstract void onTrig();
    }

    public HintGestureDetector(int clickThreshold, OnHintGestureListener listener) {
        this.mClickThreshold = 9;
        this.mClickThreshold = clickThreshold;
        this.mListener = listener;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0) {
            long currentTimeMillis = System.currentTimeMillis();
            long j = currentTimeMillis - this.mLastDownTime;
            if (j < INTERVAL) {
                this.mClickCount++;
                Log.d(TAG, "onTouchEvent clickCount=" + this.mClickCount);
                if (this.mClickCount >= this.mClickThreshold) {
                    OnHintGestureListener onHintGestureListener = this.mListener;
                    if (onHintGestureListener != null) {
                        onHintGestureListener.onTrig();
                    }
                    this.mClickCount = 0;
                    this.mLastDownTime = currentTimeMillis;
                    return true;
                }
            } else {
                Log.d(TAG, "onTouchEvent reset deltaTime=" + j);
                this.mClickCount = 0;
            }
            this.mLastDownTime = currentTimeMillis;
        }
        return false;
    }
}
