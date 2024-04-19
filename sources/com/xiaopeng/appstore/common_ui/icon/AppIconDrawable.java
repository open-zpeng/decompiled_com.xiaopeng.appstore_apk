package com.xiaopeng.appstore.common_ui.icon;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import com.xiaopeng.appstore.common_ui.logic.UIConfig;
/* loaded from: classes.dex */
public class AppIconDrawable extends BitmapDrawable {
    private static final int AMBIENT_SHADOW_ALPHA = 30;
    private static final int KEY_SHADOW_ALPHA = 77;
    private static final float KEY_SHADOW_DISTANCE = 0.052083332f;
    private static final int SHADOW_BLUR_RADIUS = 6;
    private static final boolean SHADOW_ENABLE = true;
    private boolean mEnableShadow;
    private int mIconSize;
    private Bitmap mShadowBitmap;
    private int[] mShadowOffset;
    private Paint mShadowPaint;

    protected void transformPreDraw(Canvas canvas) {
    }

    public AppIconDrawable(Resources res, Bitmap bitmap) {
        super(res, bitmap);
        boolean z = false;
        this.mEnableShadow = false;
        setTintMode(PorterDuff.Mode.MULTIPLY);
        if (UIConfig.iconEnableShadow() && bitmap != null) {
            z = true;
        }
        this.mEnableShadow = z;
        if (z) {
            this.mShadowPaint = new Paint(3);
            Paint paint = new Paint(3);
            paint.setMaskFilter(new BlurMaskFilter(6.0f, BlurMaskFilter.Blur.NORMAL));
            int[] iArr = new int[2];
            this.mShadowOffset = iArr;
            this.mShadowBitmap = bitmap.extractAlpha(paint, iArr);
            this.mIconSize = Math.max(bitmap.getWidth(), bitmap.getHeight());
        }
    }

    protected void internalDraw(Canvas canvas) {
        drawShadow(canvas);
        super.draw(canvas);
    }

    private void drawShadow(Canvas canvas) {
        if (this.mEnableShadow) {
            int save = canvas.save();
            this.mShadowPaint.setAlpha(77);
            Bitmap bitmap = this.mShadowBitmap;
            int[] iArr = this.mShadowOffset;
            canvas.drawBitmap(bitmap, iArr[0], iArr[1] + (this.mIconSize * KEY_SHADOW_DISTANCE), this.mShadowPaint);
            canvas.restoreToCount(save);
            this.mShadowPaint.setAlpha(255);
        }
    }

    @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        internalDraw(canvas);
    }
}
