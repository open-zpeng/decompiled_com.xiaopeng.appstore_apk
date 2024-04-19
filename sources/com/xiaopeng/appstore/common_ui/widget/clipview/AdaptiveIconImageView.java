package com.xiaopeng.appstore.common_ui.widget.clipview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
/* loaded from: classes.dex */
public class AdaptiveIconImageView extends ClipImageView {
    private static final String DEFAULT_MASK_PATH = "M50,0 C10,0 0,10 0,50 0,90 10,100 50,100 90,100 100,90 100,50 100,10 90,0 50,0 Z";
    private static final int MASK_COLOR;
    private Paint mPaint;
    private boolean mShowMask;

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
    }

    static {
        setPathDataGlobal(DEFAULT_MASK_PATH);
        MASK_COLOR = Color.argb(179, 0, 0, 0);
    }

    public AdaptiveIconImageView(Context context) {
        this(context, null);
    }

    public AdaptiveIconImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdaptiveIconImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mShowMask = false;
        init(context, attrs, defStyleAttr);
    }

    public void setShowMask(boolean showMask) {
        if (this.mShowMask == showMask) {
            return;
        }
        this.mShowMask = showMask;
        if (this.mPaint == null && showMask) {
            Paint paint = new Paint(1);
            this.mPaint = paint;
            paint.setColor(MASK_COLOR);
        }
        invalidate();
    }

    @Override // com.xiaopeng.appstore.common_ui.widget.clipview.ClipImageView
    protected void drawOverlay(Canvas canvas) {
        Paint paint = this.mPaint;
        if (paint == null || !this.mShowMask) {
            return;
        }
        canvas.drawPaint(paint);
    }
}
