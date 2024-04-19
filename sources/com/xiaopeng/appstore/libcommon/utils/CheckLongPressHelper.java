package com.xiaopeng.appstore.libcommon.utils;

import android.view.View;
/* loaded from: classes2.dex */
public class CheckLongPressHelper {
    public static final int DEFAULT_LONG_PRESS_TIMEOUT = 300;
    boolean mHasPerformedLongPress;
    private View.OnLongClickListener mListener;
    private int mLongPressTimeout = 300;
    private CheckForLongPress mPendingCheckForLongPress;
    private View mView;

    /* loaded from: classes2.dex */
    class CheckForLongPress implements Runnable {
        CheckForLongPress() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (CheckLongPressHelper.this.mView.getParent() == null || !CheckLongPressHelper.this.mView.hasWindowFocus() || CheckLongPressHelper.this.mHasPerformedLongPress) {
                return;
            }
            if (CheckLongPressHelper.this.mListener != null ? CheckLongPressHelper.this.mListener.onLongClick(CheckLongPressHelper.this.mView) : CheckLongPressHelper.this.mView.performLongClick()) {
                CheckLongPressHelper.this.mView.setPressed(false);
                CheckLongPressHelper.this.mHasPerformedLongPress = true;
            }
        }
    }

    public CheckLongPressHelper(View v) {
        this.mView = v;
    }

    public CheckLongPressHelper(View v, View.OnLongClickListener listener) {
        this.mView = v;
        this.mListener = listener;
    }

    public void setLongPressTimeout(int longPressTimeout) {
        this.mLongPressTimeout = longPressTimeout;
    }

    public void postCheckForLongPress() {
        this.mHasPerformedLongPress = false;
        if (this.mPendingCheckForLongPress == null) {
            this.mPendingCheckForLongPress = new CheckForLongPress();
        }
        this.mView.postDelayed(this.mPendingCheckForLongPress, this.mLongPressTimeout);
    }

    public void cancelLongPress() {
        this.mHasPerformedLongPress = false;
        CheckForLongPress checkForLongPress = this.mPendingCheckForLongPress;
        if (checkForLongPress != null) {
            this.mView.removeCallbacks(checkForLongPress);
            this.mPendingCheckForLongPress = null;
        }
    }

    public boolean hasPerformedLongPress() {
        return this.mHasPerformedLongPress;
    }
}
