package com.xiaopeng.appstore.bizcommon.logic.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Process;
import android.os.UserHandle;
import com.xiaopeng.appstore.bizcommon.R;
import com.xiaopeng.appstore.bizcommon.entities.BitmapInfo;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.libcommon.ui.FixedScaleDrawable;
import com.xiaopeng.appstore.libcommon.utils.ColorExtractor;
import com.xiaopeng.appstore.libcommon.utils.OSUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import java.util.Objects;
/* loaded from: classes2.dex */
public class LauncherIcons implements AutoCloseable {
    private static final boolean BG_COLOR_USE_BORDER = true;
    private static final float BLUR_FACTOR = 0.010416667f;
    private static final int DEFAULT_WRAPPER_BACKGROUND = -1;
    private static LauncherIcons sPool;
    public static final Object sPoolSync = new Object();
    private final Canvas mCanvas;
    private final Context mContext;
    private final int mFillResIconDpi;
    private final int mIconBitmapSize;
    private IconNormalizer mNormalizer;
    private final PackageManager mPm;
    private Drawable mWrapperIcon;
    private LauncherIcons next;
    private final Rect mOldBounds = new Rect();
    private int mWrapperBackgroundColor = -1;

    public static LauncherIcons obtain(Context context) {
        synchronized (sPoolSync) {
            LauncherIcons launcherIcons = sPool;
            if (launcherIcons != null) {
                sPool = launcherIcons.next;
                launcherIcons.next = null;
                return launcherIcons;
            }
            return new LauncherIcons(context);
        }
    }

    public void recycle() {
        synchronized (sPoolSync) {
            this.mWrapperBackgroundColor = -1;
            this.next = sPool;
            sPool = this;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        recycle();
    }

    public void release() {
        this.mWrapperIcon = null;
        this.mNormalizer = null;
    }

    private LauncherIcons(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mPm = applicationContext.getPackageManager();
        this.mFillResIconDpi = Utils.getDensityDpi();
        this.mIconBitmapSize = context.getResources().getDimensionPixelSize(R.dimen.icon_bitmap_size);
        Canvas canvas = new Canvas();
        this.mCanvas = canvas;
        canvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
    }

    public IconNormalizer getNormalizer() {
        if (this.mNormalizer == null) {
            this.mNormalizer = new IconNormalizer();
        }
        return this.mNormalizer;
    }

    public BitmapInfo createIconBitmap(Intent.ShortcutIconResource iconRes) {
        try {
            Resources resourcesForApplication = this.mPm.getResourcesForApplication(iconRes.packageName);
            if (resourcesForApplication != null) {
                return createBadgedIconBitmap(resourcesForApplication.getDrawableForDensity(resourcesForApplication.getIdentifier(iconRes.resourceName, null, null), this.mFillResIconDpi), Process.myUserHandle(), true);
            }
        } catch (Exception unused) {
        }
        return null;
    }

    public BitmapInfo createIconBitmap(Bitmap icon) {
        if (this.mIconBitmapSize == icon.getWidth() && this.mIconBitmapSize == icon.getHeight()) {
            return BitmapInfo.fromBitmap(icon);
        }
        return BitmapInfo.fromBitmap(createIconBitmap(new BitmapDrawable(this.mContext.getResources(), icon), 1.0f));
    }

    public BitmapInfo createBadgedIconBitmap(Drawable icon, UserHandle user, boolean wrapToAdaptiveIcon) {
        return createBadgedIconBitmap(icon, user, wrapToAdaptiveIcon, false);
    }

    public BitmapInfo createBadgedIconBitmap(Drawable icon, UserHandle user, boolean wrapToAdaptiveIcon, boolean isInstantApp) {
        if (wrapToAdaptiveIcon) {
            icon = normalizeAndWrapToAdaptiveIcon(icon, null);
        }
        Bitmap createIconBitmap = createIconBitmap(icon, 1.0f);
        if (user != null && !Process.myUserHandle().equals(user)) {
            Drawable userBadgedIcon = this.mPm.getUserBadgedIcon(new FixedSizeBitmapDrawable(createIconBitmap), user);
            createIconBitmap = userBadgedIcon instanceof BitmapDrawable ? ((BitmapDrawable) userBadgedIcon).getBitmap() : createIconBitmap(userBadgedIcon, 1.0f);
        } else if (isInstantApp) {
            badgeWithDrawable(createIconBitmap, this.mContext.getDrawable(R.drawable.ic_instant_app_badge));
        }
        return BitmapInfo.fromBitmap(createIconBitmap);
    }

    public BitmapInfo createWebIconBitmap(Bitmap icon) {
        return BitmapInfo.fromBitmap(createIconBitmap(LogicUtils.generateAdaptiveIcon(new BitmapDrawable((Resources) null, icon)), 1.0f));
    }

    public Bitmap createScaledBitmapWithoutShadow(Drawable icon, int iconAppTargetSdk) {
        return createIconBitmap(normalizeAndWrapToAdaptiveIcon(icon, new RectF()), 1.0f);
    }

    public void setWrapperBackgroundColor(int color) {
        if (Color.alpha(color) < 255) {
            color = -1;
        }
        this.mWrapperBackgroundColor = color;
    }

    private Drawable normalizeAndWrapToAdaptiveIcon(Drawable icon, RectF outIconBounds) {
        boolean[] zArr = new boolean[1];
        if (this.mWrapperIcon == null) {
            this.mWrapperIcon = ((Drawable) Objects.requireNonNull(this.mContext.getDrawable(R.drawable.adaptive_icon_drawable_wrapper))).mutate();
        }
        AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) this.mWrapperIcon;
        adaptiveIconDrawable.setBounds(0, 0, 1, 1);
        float xPScale = getNormalizer().getXPScale(icon, outIconBounds, adaptiveIconDrawable.getIconMask(), zArr);
        if (!OSUtils.ATLEAST_OREO || zArr[0] || (icon instanceof AdaptiveIconDrawable)) {
            return icon;
        }
        int i = this.mWrapperBackgroundColor;
        if (icon instanceof BitmapDrawable) {
            i = ColorExtractor.findBorderColor(((BitmapDrawable) icon).getBitmap());
        }
        FixedScaleDrawable fixedScaleDrawable = (FixedScaleDrawable) adaptiveIconDrawable.getForeground();
        fixedScaleDrawable.setDrawable(icon);
        fixedScaleDrawable.setScale(xPScale);
        ((ColorDrawable) adaptiveIconDrawable.getBackground()).setColor(i);
        return adaptiveIconDrawable;
    }

    public void badgeWithDrawable(Bitmap target, Drawable badge) {
        this.mCanvas.setBitmap(target);
        badgeWithDrawable(this.mCanvas, badge);
        this.mCanvas.setBitmap(null);
    }

    private void badgeWithDrawable(Canvas target, Drawable badge) {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.profile_badge_size);
        int i = this.mIconBitmapSize;
        badge.setBounds(i - dimensionPixelSize, i - dimensionPixelSize, i, i);
        badge.draw(target);
    }

    private Bitmap createIconBitmap(Drawable icon, float scale) {
        BitmapDrawable bitmapDrawable;
        Bitmap bitmap;
        int i;
        int i2 = this.mIconBitmapSize;
        if (icon instanceof PaintDrawable) {
            PaintDrawable paintDrawable = (PaintDrawable) icon;
            paintDrawable.setIntrinsicWidth(i2);
            paintDrawable.setIntrinsicHeight(i2);
        } else if ((icon instanceof BitmapDrawable) && (bitmap = (bitmapDrawable = (BitmapDrawable) icon).getBitmap()) != null && bitmap.getDensity() == 0) {
            bitmapDrawable.setTargetDensity(this.mContext.getResources().getDisplayMetrics());
        }
        int intrinsicWidth = icon.getIntrinsicWidth();
        int intrinsicHeight = icon.getIntrinsicHeight();
        if (intrinsicWidth > 0 && intrinsicHeight > 0) {
            float f = intrinsicWidth / intrinsicHeight;
            if (intrinsicWidth > intrinsicHeight) {
                i = (int) (i2 / f);
            } else if (intrinsicHeight > intrinsicWidth) {
                i = i2;
                i2 = (int) (i2 * f);
            }
            int i3 = this.mIconBitmapSize;
            Bitmap createBitmap = Bitmap.createBitmap(i3, i3, Bitmap.Config.ARGB_8888);
            this.mCanvas.setBitmap(createBitmap);
            int i4 = (i3 - i2) / 2;
            int i5 = (i3 - i) / 2;
            this.mOldBounds.set(icon.getBounds());
            if (!OSUtils.ATLEAST_OREO && (icon instanceof AdaptiveIconDrawable)) {
                int max = Math.max(i2, i) + 0;
                icon.setBounds(0, 0, max, max);
            } else {
                icon.setBounds(i4, i5, i2 + i4, i + i5);
            }
            this.mCanvas.save();
            this.mCanvas.scale(scale, scale, i3 >> 1, i3 >> 1);
            icon.draw(this.mCanvas);
            this.mCanvas.restore();
            icon.setBounds(this.mOldBounds);
            this.mCanvas.setBitmap(null);
            return createBitmap;
        }
        i = i2;
        int i32 = this.mIconBitmapSize;
        Bitmap createBitmap2 = Bitmap.createBitmap(i32, i32, Bitmap.Config.ARGB_8888);
        this.mCanvas.setBitmap(createBitmap2);
        int i42 = (i32 - i2) / 2;
        int i52 = (i32 - i) / 2;
        this.mOldBounds.set(icon.getBounds());
        if (!OSUtils.ATLEAST_OREO) {
        }
        icon.setBounds(i42, i52, i2 + i42, i + i52);
        this.mCanvas.save();
        this.mCanvas.scale(scale, scale, i32 >> 1, i32 >> 1);
        icon.draw(this.mCanvas);
        this.mCanvas.restore();
        icon.setBounds(this.mOldBounds);
        this.mCanvas.setBitmap(null);
        return createBitmap2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class FixedSizeBitmapDrawable extends BitmapDrawable {
        public FixedSizeBitmapDrawable(Bitmap bitmap) {
            super((Resources) null, bitmap);
        }

        @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return getBitmap().getWidth();
        }

        @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return getBitmap().getWidth();
        }
    }
}
