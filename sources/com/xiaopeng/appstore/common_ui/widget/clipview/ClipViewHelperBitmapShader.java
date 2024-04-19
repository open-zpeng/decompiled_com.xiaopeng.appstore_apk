package com.xiaopeng.appstore.common_ui.widget.clipview;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Shader;
/* loaded from: classes.dex */
public class ClipViewHelperBitmapShader extends BaseClipViewHelper {
    private final Canvas mCanvas;
    private Bitmap mLayerBitmap;
    private Shader mLayerShader;
    private final Paint mPaint;

    public ClipViewHelperBitmapShader(Path path, float pathWidth, float pathHeight, ClipDrawCallback callback) {
        super(path, pathWidth, pathHeight, callback);
        this.mPaint = new Paint(7);
        this.mCanvas = new Canvas();
    }

    @Override // com.xiaopeng.appstore.common_ui.widget.clipview.BaseClipViewHelper
    public void onSizeChanged(int width, int height) {
        super.onSizeChanged(width, height);
        Bitmap bitmap = this.mLayerBitmap;
        if (bitmap != null && bitmap.getWidth() == width && this.mLayerBitmap.getHeight() == height) {
            return;
        }
        this.mLayerBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }

    @Override // com.xiaopeng.appstore.common_ui.widget.clipview.BaseClipViewHelper
    public void draw(Canvas canvas) {
        if (this.mCallback == null) {
            return;
        }
        Bitmap bitmap = this.mLayerBitmap;
        if (bitmap == null) {
            this.mCallback.drawContent(canvas);
            return;
        }
        if (this.mLayerShader == null) {
            this.mCanvas.setBitmap(bitmap);
            BitmapShader bitmapShader = new BitmapShader(this.mLayerBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.mLayerShader = bitmapShader;
            this.mPaint.setShader(bitmapShader);
        }
        this.mCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        if (this.mBgColor != 0) {
            this.mCanvas.drawColor(this.mBgColor);
        }
        this.mCallback.drawContent(this.mCanvas);
        canvas.drawPath(this.mDrawPath, this.mPaint);
    }
}
