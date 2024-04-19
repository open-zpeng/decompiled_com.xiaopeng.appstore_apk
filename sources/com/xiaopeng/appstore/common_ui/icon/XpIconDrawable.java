package com.xiaopeng.appstore.common_ui.icon;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.util.Property;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import com.xiaopeng.appstore.common_ui.R;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
/* loaded from: classes.dex */
public class XpIconDrawable extends DrawableWrapper {
    private static final int CLICK_FEEDBACK_DURATION = 200;
    private static final float DEFAULT_VIEW_PORT_SCALE = 0.6666667f;
    private static final float EXTRA_INSET_PERCENTAGE = 0.25f;
    private static final float PRESSED_SCALE = 1.1f;
    protected boolean mEnableInset;
    protected boolean mEnablePress;
    private float mIconSize;
    private boolean mIsPressed;
    private float mScale;
    protected ObjectAnimator mScaleAnimation;
    private final Rect mTmpOutRect;
    private static final Interpolator ACCEL = new AccelerateInterpolator();
    private static final Property<XpIconDrawable, Float> SCALE = new Property<XpIconDrawable, Float>(Float.TYPE, "scale") { // from class: com.xiaopeng.appstore.common_ui.icon.XpIconDrawable.1
        @Override // android.util.Property
        public Float get(XpIconDrawable appIconDrawable) {
            return Float.valueOf(appIconDrawable.mScale);
        }

        @Override // android.util.Property
        public void set(XpIconDrawable appIconDrawable, Float value) {
            appIconDrawable.mScale = value.floatValue();
            appIconDrawable.invalidateSelf();
        }
    };

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    public XpIconDrawable(Drawable dr) {
        super(dr);
        this.mScale = 1.0f;
        this.mEnablePress = true;
        this.mEnableInset = true;
        this.mTmpOutRect = new Rect();
        this.mIconSize = ResUtils.getDimen(R.dimen.icon_view_size);
        updateDrawableBounds(getBounds());
    }

    @Override // android.graphics.drawable.DrawableWrapper
    public void setDrawable(Drawable dr) {
        super.setDrawable(dr);
        updateDrawableBounds(getBounds());
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect bounds) {
        updateDrawableBounds(bounds);
    }

    public void setEnablePress(boolean enablePress) {
        this.mEnablePress = enablePress;
    }

    public void setEnableInset(boolean enableInset) {
        this.mEnableInset = enableInset;
    }

    protected void transformPreDraw(Canvas canvas) {
        Rect bounds = getBounds();
        float f = this.mScale;
        canvas.scale(f, f, bounds.exactCenterX(), bounds.exactCenterY());
    }

    protected void internalDraw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.mEnablePress && this.mScaleAnimation != null) {
            transformPreDraw(canvas);
            internalDraw(canvas);
            return;
        }
        internalDraw(canvas);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return (int) this.mIconSize;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return (int) this.mIconSize;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] state) {
        boolean z;
        boolean z2;
        if (!this.mEnablePress) {
            return super.onStateChange(state);
        }
        int length = state.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            } else if (state[i] == 16842919) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (this.mIsPressed != z) {
            this.mIsPressed = z;
            ObjectAnimator objectAnimator = this.mScaleAnimation;
            if (objectAnimator != null) {
                objectAnimator.cancel();
                this.mScaleAnimation = null;
            }
            if (this.mIsPressed) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, SCALE, PRESSED_SCALE);
                this.mScaleAnimation = ofFloat;
                ofFloat.setDuration(200L);
                this.mScaleAnimation.setInterpolator(ACCEL);
                this.mScaleAnimation.start();
            } else {
                this.mScale = 1.0f;
                invalidateSelf();
            }
            z2 = true;
        } else {
            z2 = false;
        }
        return super.onStateChange(state) || z2;
    }

    private void updateDrawableBounds(Rect bounds) {
        if (this.mEnableInset) {
            this.mTmpOutRect.set(bounds);
            this.mTmpOutRect.inset(((int) (bounds.width() * EXTRA_INSET_PERCENTAGE)) / 2, ((int) (bounds.height() * EXTRA_INSET_PERCENTAGE)) / 2);
            super.onBoundsChange(this.mTmpOutRect);
            return;
        }
        super.onBoundsChange(bounds);
    }
}
