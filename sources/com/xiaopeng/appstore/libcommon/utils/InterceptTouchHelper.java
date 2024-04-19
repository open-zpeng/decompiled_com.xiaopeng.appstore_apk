package com.xiaopeng.appstore.libcommon.utils;

import android.view.MotionEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/* loaded from: classes2.dex */
public class InterceptTouchHelper {
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    private float distanceX;
    private float distanceY;
    private int mDirection;
    private float startX;
    private float startY;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    @interface Direction {
    }

    private void log(String msg) {
    }

    public InterceptTouchHelper(int direction) {
        this.mDirection = direction;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == 0) {
            this.startX = ev.getX();
            this.startY = ev.getY();
            this.distanceX = 0.0f;
            this.distanceY = 0.0f;
        } else if (action == 2) {
            float x = ev.getX();
            float y = ev.getY();
            this.distanceX += Math.abs(x - this.startX);
            this.distanceY += Math.abs(y - this.startY);
            log("onInterceptTouchEvent---distanceX:" + this.distanceX + ", distanceY:" + this.distanceY);
            int i = this.mDirection;
            if (i == 1) {
                if (this.distanceX < this.distanceY) {
                    return false;
                }
            } else if (i == 2 && this.distanceX > this.distanceY) {
                return false;
            }
        }
        return true;
    }
}
