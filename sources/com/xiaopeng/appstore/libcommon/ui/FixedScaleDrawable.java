package com.xiaopeng.appstore.libcommon.ui;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
/* loaded from: classes2.dex */
public class FixedScaleDrawable extends DrawableWrapper {
    private static final float LEGACY_ICON_SCALE = 0.67f;
    private static final String TAG = "FixedScaleDrawable";
    private float mScaleX;
    private float mScaleY;

    public FixedScaleDrawable(Drawable drawable) {
        super(drawable);
        this.mScaleX = LEGACY_ICON_SCALE;
        this.mScaleY = LEGACY_ICON_SCALE;
    }

    public FixedScaleDrawable() {
        this(new ColorDrawable());
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int save = canvas.save();
        canvas.scale(this.mScaleX, this.mScaleY, getBounds().exactCenterX(), getBounds().exactCenterY());
        super.draw(canvas);
        canvas.restoreToCount(save);
    }

    public void setScale(float scale) {
        float intrinsicHeight = getIntrinsicHeight();
        float intrinsicWidth = getIntrinsicWidth();
        float f = scale * LEGACY_ICON_SCALE;
        this.mScaleX = f;
        this.mScaleY = f;
        if (intrinsicHeight > intrinsicWidth && intrinsicWidth > 0.0f) {
            this.mScaleX = f * (intrinsicWidth / intrinsicHeight);
        } else if (intrinsicWidth <= intrinsicHeight || intrinsicHeight <= 0.0f) {
        } else {
            this.mScaleY = f * (intrinsicHeight / intrinsicWidth);
        }
    }
}
