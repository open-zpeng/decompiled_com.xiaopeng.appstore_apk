package com.xiaopeng.appstore.common_ui.icon;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Property;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import com.xiaopeng.appstore.bizcommon.R;
import com.xiaopeng.appstore.common_ui.icon.extension.BaseAdaptiveDrawStrategy;
import com.xiaopeng.appstore.common_ui.icon.extension.DefaultAdaptiveDrawStrategy;
import com.xiaopeng.appstore.libcommon.ui.FixedScaleDrawable;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class XpAdaptiveLayerDrawable extends Drawable implements Drawable.Callback {
    private static final int CLICK_FEEDBACK_DURATION = 200;
    private static final float PRESSED_SCALE = 1.1f;
    private static final String TAG = "XpAdaptiveLayerDrawable";
    private AdaptiveIconDrawable mAdaptiveIconDrawable;
    private List<ChildDrawable> mBackgroundList;
    private boolean mCheckedOpacity;
    private boolean mChildRequestedInvalidation;
    private List<ChildDrawable> mChildren;
    private List<ChildDrawable> mForegroundList;
    private boolean mIsPressed;
    private int mOpacity;
    private ObjectAnimator mScaleAnimation;
    private boolean mSuspendChildInvalidation;
    private Drawable mWrapperDrawable;
    private static final Interpolator ACCEL = new AccelerateInterpolator();
    private static final Property<XpAdaptiveLayerDrawable, Float> SCALE = new Property<XpAdaptiveLayerDrawable, Float>(Float.TYPE, "scale") { // from class: com.xiaopeng.appstore.common_ui.icon.XpAdaptiveLayerDrawable.1
        @Override // android.util.Property
        public Float get(XpAdaptiveLayerDrawable appIconDrawable) {
            return Float.valueOf(appIconDrawable.mScale);
        }

        @Override // android.util.Property
        public void set(XpAdaptiveLayerDrawable appIconDrawable, Float value) {
            appIconDrawable.mScale = value.floatValue();
            appIconDrawable.invalidateSelf();
        }
    };
    private float mScale = 1.0f;
    private int mDensity = Utils.getApp().getResources().getDisplayMetrics().densityDpi;
    private BaseAdaptiveDrawStrategy mAdaptiveDrawStrategy = new DefaultAdaptiveDrawStrategy(this);

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    public XpAdaptiveLayerDrawable(Drawable dr) {
        initDrawable(dr);
    }

    public void setDrawable(Drawable dr) {
        mutate();
        initDrawable(dr);
        invalidateSelf();
    }

    public List<ChildDrawable> getForegroundList() {
        return this.mForegroundList;
    }

    public List<ChildDrawable> getBackgroundList() {
        return this.mBackgroundList;
    }

    public List<ChildDrawable> getChildren() {
        return this.mChildren;
    }

    public BaseAdaptiveDrawStrategy getAdaptiveDrawStrategy() {
        return this.mAdaptiveDrawStrategy;
    }

    private void initDrawable(Drawable dr) {
        AdaptiveIconDrawable adaptiveIconDrawable;
        List<ChildDrawable> list = this.mChildren;
        if (list == null) {
            this.mChildren = new ArrayList();
        } else {
            list.clear();
        }
        List<ChildDrawable> list2 = this.mForegroundList;
        if (list2 == null) {
            this.mForegroundList = new ArrayList();
        } else {
            list2.clear();
        }
        List<ChildDrawable> list3 = this.mBackgroundList;
        if (list3 == null) {
            this.mBackgroundList = new ArrayList();
        } else {
            list3.clear();
        }
        if (dr instanceof AdaptiveIconDrawable) {
            adaptiveIconDrawable = (AdaptiveIconDrawable) dr;
        } else {
            if (this.mWrapperDrawable == null) {
                this.mWrapperDrawable = ResUtils.getDrawable(R.drawable.adaptive_icon_drawable_wrapper).mutate();
            }
            AdaptiveIconDrawable adaptiveIconDrawable2 = (AdaptiveIconDrawable) this.mWrapperDrawable;
            FixedScaleDrawable fixedScaleDrawable = (FixedScaleDrawable) adaptiveIconDrawable2.getForeground();
            fixedScaleDrawable.setDrawable(dr);
            fixedScaleDrawable.setScale(0.88f);
            adaptiveIconDrawable = adaptiveIconDrawable2;
        }
        this.mAdaptiveIconDrawable = adaptiveIconDrawable;
        parseAdaptiveDrawable(adaptiveIconDrawable);
        updateLayerBoundsInternal(getBounds());
    }

    private ChildDrawable createChildDrawable(Drawable drawable) {
        ChildDrawable childDrawable = new ChildDrawable(this.mDensity);
        childDrawable.mDrawable = drawable;
        childDrawable.mDrawable.setCallback(this);
        return childDrawable;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (super.mutate() == this) {
            for (ChildDrawable childDrawable : this.mChildren) {
                childDrawable.mDrawable.mutate();
            }
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.mScaleAnimation != null) {
            int save = canvas.save();
            Rect bounds = getBounds();
            float f = this.mScale;
            canvas.scale(f, f, bounds.exactCenterX(), bounds.exactCenterY());
            BaseAdaptiveDrawStrategy baseAdaptiveDrawStrategy = this.mAdaptiveDrawStrategy;
            if (baseAdaptiveDrawStrategy != null) {
                baseAdaptiveDrawStrategy.draw(canvas);
            }
            canvas.restoreToCount(save);
            return;
        }
        BaseAdaptiveDrawStrategy baseAdaptiveDrawStrategy2 = this.mAdaptiveDrawStrategy;
        if (baseAdaptiveDrawStrategy2 != null) {
            baseAdaptiveDrawStrategy2.draw(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect bounds) {
        BaseAdaptiveDrawStrategy baseAdaptiveDrawStrategy = this.mAdaptiveDrawStrategy;
        if (baseAdaptiveDrawStrategy != null) {
            baseAdaptiveDrawStrategy.onBoundsChange(bounds);
        }
        if (bounds.isEmpty()) {
            return;
        }
        try {
            suspendChildInvalidation();
            updateLayerBoundsInternal(bounds);
        } finally {
            resumeChildInvalidation();
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] state) {
        boolean z;
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
            return true;
        }
        return false;
    }

    private void suspendChildInvalidation() {
        this.mSuspendChildInvalidation = true;
    }

    private void resumeChildInvalidation() {
        this.mSuspendChildInvalidation = false;
        if (this.mChildRequestedInvalidation) {
            this.mChildRequestedInvalidation = false;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        super.invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        for (ChildDrawable childDrawable : this.mChildren) {
            Drawable drawable = childDrawable.mDrawable;
            if (drawable != null) {
                drawable.setAlpha(alpha);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        for (ChildDrawable childDrawable : this.mChildren) {
            Drawable drawable = childDrawable.mDrawable;
            if (drawable != null) {
                drawable.setColorFilter(colorFilter);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        if (this.mCheckedOpacity) {
            return this.mOpacity;
        }
        int i = -1;
        int i2 = 0;
        while (true) {
            if (i2 >= this.mChildren.size()) {
                break;
            } else if (this.mChildren.get(i2).mDrawable != null) {
                i = i2;
                break;
            } else {
                i2++;
            }
        }
        int opacity = i >= 0 ? this.mChildren.get(i).mDrawable.getOpacity() : -2;
        for (int i3 = i + 1; i3 < this.mChildren.size(); i3++) {
            Drawable drawable = this.mChildren.get(i3).mDrawable;
            if (drawable != null) {
                opacity = Drawable.resolveOpacity(opacity, drawable.getOpacity());
            }
        }
        this.mOpacity = opacity;
        this.mCheckedOpacity = true;
        return opacity;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable who) {
        if (this.mSuspendChildInvalidation) {
            this.mChildRequestedInvalidation = true;
        } else {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable who, Runnable what) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
    }

    private void parseAdaptiveDrawable(AdaptiveIconDrawable drawable) {
        List<ChildDrawable> tryParseLayerDrawable = tryParseLayerDrawable(drawable.getForeground());
        if (tryParseLayerDrawable == null) {
            this.mForegroundList.add(createChildDrawable(drawable.getForeground()));
            drawable.getForeground().setCallback(this);
        } else {
            this.mForegroundList.addAll(tryParseLayerDrawable);
        }
        List<ChildDrawable> tryParseLayerDrawable2 = tryParseLayerDrawable(drawable.getBackground());
        if (tryParseLayerDrawable2 == null) {
            this.mBackgroundList.add(createChildDrawable(drawable.getBackground()));
            drawable.getBackground().setCallback(this);
        } else {
            this.mBackgroundList.addAll(tryParseLayerDrawable2);
        }
        this.mChildren.addAll(this.mBackgroundList);
        this.mChildren.addAll(this.mForegroundList);
        invalidateCache();
    }

    private List<ChildDrawable> tryParseLayerDrawable(Drawable drawable) {
        if (drawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            ArrayList arrayList = new ArrayList(layerDrawable.getNumberOfLayers());
            for (int i = 0; i < layerDrawable.getNumberOfLayers(); i++) {
                arrayList.add(createChildDrawable(layerDrawable.getDrawable(i)));
                layerDrawable.getDrawable(i).setCallback(this);
            }
            return arrayList;
        }
        return null;
    }

    private void updateLayerBoundsInternal(Rect bounds) {
        this.mWrapperDrawable.setBounds(bounds);
        for (ChildDrawable childDrawable : this.mChildren) {
            childDrawable.mDrawable.setBounds(bounds);
        }
    }

    static int resolveDensity(Resources r, int parentDensity) {
        if (r != null) {
            parentDensity = r.getDisplayMetrics().densityDpi;
        }
        if (parentDensity == 0) {
            return 160;
        }
        return parentDensity;
    }

    public void invalidateCache() {
        this.mCheckedOpacity = false;
    }

    /* loaded from: classes.dex */
    public static class ChildDrawable {
        public int mDensity;
        public Drawable mDrawable;
        public int[] mThemeAttrs;

        ChildDrawable(int density) {
            this.mDensity = 160;
            this.mDensity = density;
        }

        ChildDrawable(ChildDrawable orig, XpAdaptiveLayerDrawable owner, Resources res) {
            Drawable drawable;
            this.mDensity = 160;
            Drawable drawable2 = orig.mDrawable;
            if (drawable2 != null) {
                Drawable.ConstantState constantState = drawable2.getConstantState();
                if (constantState == null) {
                    drawable = drawable2;
                } else if (res != null) {
                    drawable = constantState.newDrawable(res);
                } else {
                    drawable = constantState.newDrawable();
                }
                drawable.setCallback(owner);
                drawable.setBounds(drawable2.getBounds());
                drawable.setLevel(drawable2.getLevel());
            } else {
                drawable = null;
            }
            this.mDrawable = drawable;
            this.mThemeAttrs = orig.mThemeAttrs;
            this.mDensity = XpAdaptiveLayerDrawable.resolveDensity(res, orig.mDensity);
        }

        public boolean canApplyTheme() {
            Drawable drawable;
            return this.mThemeAttrs != null || ((drawable = this.mDrawable) != null && drawable.canApplyTheme());
        }

        public final void setDensity(int targetDensity) {
            if (this.mDensity != targetDensity) {
                this.mDensity = targetDensity;
            }
        }
    }
}
