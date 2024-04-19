package com.xiaopeng.appstore.common_ui.widget.clipview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
/* loaded from: classes.dex */
public class ClipViewHelperXfermode extends BaseClipViewHelper {
    private static final float PATH_OFFSET = 10.0f;
    private final Paint mPaint;

    public ClipViewHelperXfermode(Path path, float pathWidth, float pathHeight, ClipDrawCallback callback) {
        super(path, pathWidth, pathHeight, callback);
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    @Override // com.xiaopeng.appstore.common_ui.widget.clipview.BaseClipViewHelper
    public void onSizeChanged(int width, int height) {
        super.onSizeChanged(width, height);
        this.mDrawPath.addRect(-10.0f, -10.0f, width + PATH_OFFSET, height + PATH_OFFSET, Path.Direction.CW);
        this.mDrawPath.setFillType(Path.FillType.EVEN_ODD);
    }

    @Override // com.xiaopeng.appstore.common_ui.widget.clipview.BaseClipViewHelper
    public void draw(Canvas canvas) {
        canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null);
        if (this.mBgColor != 0) {
            canvas.drawColor(this.mBgColor);
        }
        this.mCallback.drawContent(canvas);
        canvas.drawPath(this.mDrawPath, this.mPaint);
        canvas.restore();
    }
}
