package com.xiaopeng.appstore.common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.libcommon.utils.IdleViewDetectHelper;
/* loaded from: classes.dex */
public class IdleDetectLayout extends FrameLayout {
    private static final String TAG = "IdleDetectLayout";
    private boolean mDetecting;
    private IdleViewDetectHelper mIdleViewCheckHelper;

    public IdleDetectLayout(Context context) {
        this(context, null);
    }

    public IdleDetectLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IdleDetectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDetecting = false;
        init(attrs);
    }

    public IdleDetectLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mDetecting = false;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        this.mIdleViewCheckHelper = new IdleViewDetectHelper();
        if (attrs != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.IdleDetectLayout);
            setIdleDelay(obtainStyledAttributes.getInteger(R.styleable.IdleDetectLayout_idleDelay, 0));
            obtainStyledAttributes.recycle();
        }
    }

    public void enableDetect() {
        IdleViewDetectHelper idleViewDetectHelper = this.mIdleViewCheckHelper;
        if (idleViewDetectHelper != null) {
            idleViewDetectHelper.enableDetect();
        }
    }

    public void disableDetect() {
        IdleViewDetectHelper idleViewDetectHelper = this.mIdleViewCheckHelper;
        if (idleViewDetectHelper != null) {
            idleViewDetectHelper.disableDetect();
        }
    }

    public void setIdleListener(IdleViewDetectHelper.IdleCallback callback) {
        this.mIdleViewCheckHelper.setCallback(callback);
    }

    public void setIdleDelay(long idleDelay) {
        IdleViewDetectHelper idleViewDetectHelper = this.mIdleViewCheckHelper;
        if (idleViewDetectHelper != null) {
            idleViewDetectHelper.setIdleDelay(idleDelay);
        }
    }

    public void tryStart() {
        IdleViewDetectHelper idleViewDetectHelper = this.mIdleViewCheckHelper;
        if (idleViewDetectHelper == null || !idleViewDetectHelper.start()) {
            return;
        }
        Logger.t(TAG).v("start detect", new Object[0]);
        this.mDetecting = true;
    }

    public void stop() {
        this.mDetecting = false;
        Logger.t(TAG).d("stop detect");
        IdleViewDetectHelper idleViewDetectHelper = this.mIdleViewCheckHelper;
        if (idleViewDetectHelper != null) {
            idleViewDetectHelper.stop();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        tryStart();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent event) {
        IdleViewDetectHelper idleViewDetectHelper = this.mIdleViewCheckHelper;
        if (idleViewDetectHelper != null) {
            idleViewDetectHelper.dispatchTouchEvent(event);
        }
        return super.dispatchTouchEvent(event);
    }
}
