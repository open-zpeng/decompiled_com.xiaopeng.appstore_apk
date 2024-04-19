package com.xiaopeng.appstore.common_ui.icon.extension;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import androidx.core.graphics.PathParser;
import com.xiaopeng.appstore.common_ui.R;
import com.xiaopeng.appstore.common_ui.icon.XpAdaptiveLayerDrawable;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
/* loaded from: classes.dex */
public class DefaultAdaptiveDrawStrategy extends BaseAdaptiveDrawStrategy {
    private static final float DEFAULT_VIEW_PORT_SCALE = 0.6666667f;
    private static final float EXTRA_INSET_PERCENTAGE = 0.25f;
    public static final float MASK_SIZE = 100.0f;
    protected static Path sMask;
    protected final Canvas mCanvas;
    protected Bitmap mLayersBitmap;
    protected Shader mLayersShader;
    protected final Matrix mMaskMatrix;
    protected Path mMaskPath;
    protected Paint mPaint;
    private Rect mTempBounds;

    public DefaultAdaptiveDrawStrategy(XpAdaptiveLayerDrawable xpAdaptiveLayerDrawable) {
        super(xpAdaptiveLayerDrawable);
        this.mPaint = new Paint(7);
        this.mCanvas = new Canvas();
        this.mMaskMatrix = new Matrix();
        this.mMaskPath = PathParser.createPathFromPathData(ResUtils.getString(R.string.app_config_icon_mask));
        if (sMask == null) {
            sMask = PathParser.createPathFromPathData(ResUtils.getString(R.string.app_config_icon_mask));
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.icon.extension.BaseAdaptiveDrawStrategy
    public void onBoundsChange(Rect bounds) {
        this.mTempBounds = bounds;
        updateMaskBounds(bounds);
        updateLayerBounds(bounds);
    }

    protected void updateMaskBounds(Rect bounds) {
        float width = bounds.width() / 1.3333334f;
        float height = bounds.height() / 1.3333334f;
        this.mMaskMatrix.setTranslate((bounds.width() / 2) - (width / 2.0f), (bounds.height() / 2) - (height / 2.0f));
        this.mMaskMatrix.postScale(width / 100.0f, height / 100.0f);
        sMask.transform(this.mMaskMatrix, this.mMaskPath);
    }

    protected void updateLayerBounds(Rect bounds) {
        Bitmap bitmap = this.mLayersBitmap;
        if (bitmap == null || bitmap.getWidth() != bounds.width() || this.mLayersBitmap.getHeight() != bounds.height()) {
            this.mLayersBitmap = Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ARGB_8888);
        }
        this.mCanvas.setBitmap(this.mLayersBitmap);
        this.mLayersBitmap.eraseColor(0);
        if (this.mLayersShader == null) {
            BitmapShader bitmapShader = new BitmapShader(this.mLayersBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.mLayersShader = bitmapShader;
            this.mPaint.setShader(bitmapShader);
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.icon.extension.BaseAdaptiveDrawStrategy
    public void draw(Canvas canvas) {
        Bitmap bitmap = this.mLayersBitmap;
        if (bitmap == null) {
            return;
        }
        bitmap.eraseColor(0);
        this.mCanvas.drawColor(0);
        drawBackgroundList(this.mCanvas);
        drawForegroundList(this.mCanvas);
        int save = canvas.save();
        canvas.drawPath(this.mMaskPath, this.mPaint);
        canvas.restoreToCount(save);
    }
}
