package com.xiaopeng.appstore.common_ui.icon.extension;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Shader;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.common_ui.icon.XpAdaptiveLayerDrawable;
import com.xiaopeng.appstore.libcommon.utils.CameraDrawHelper;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class ParallaxAdaptiveDrawStrategy extends DefaultAdaptiveDrawStrategy {
    private static final int DEFAULT_Z_DISTANCE = 15;
    private static final String TAG = "ParallaxAdaptiveDrawStrategy";
    private List<Bitmap> mBgBitmapList;
    private CameraDrawHelper mCameraDrawHelper;
    private List<Bitmap> mFgBitmapList;
    private float mZDistance;

    public ParallaxAdaptiveDrawStrategy(XpAdaptiveLayerDrawable xpAdaptiveLayerDrawable) {
        super(xpAdaptiveLayerDrawable);
        CameraDrawHelper cameraDrawHelper = new CameraDrawHelper();
        this.mCameraDrawHelper = cameraDrawHelper;
        cameraDrawHelper.setMaxRotateDegree(90);
    }

    public void rotateCamera(float angleX, float angleY) {
        this.mCameraDrawHelper.rotateCamera(angleX, angleY);
        this.mXpAdaptiveLayerDrawable.invalidateSelf();
    }

    @Override // com.xiaopeng.appstore.common_ui.icon.extension.DefaultAdaptiveDrawStrategy
    protected void updateLayerBounds(Rect bounds) {
        int size = this.mXpAdaptiveLayerDrawable.getForegroundList().size();
        int size2 = this.mXpAdaptiveLayerDrawable.getBackgroundList().size();
        this.mFgBitmapList = new ArrayList(size);
        this.mBgBitmapList = new ArrayList(size2);
        for (int i = 0; i < size; i++) {
            this.mFgBitmapList.add(Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ARGB_8888));
        }
        for (int i2 = 0; i2 < size2; i2++) {
            this.mBgBitmapList.add(Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ARGB_8888));
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.icon.extension.DefaultAdaptiveDrawStrategy, com.xiaopeng.appstore.common_ui.icon.extension.BaseAdaptiveDrawStrategy
    public void draw(Canvas canvas) {
        this.mZDistance = 0.0f;
        Rect bounds = this.mXpAdaptiveLayerDrawable.getBounds();
        List<XpAdaptiveLayerDrawable.ChildDrawable> backgroundList = this.mXpAdaptiveLayerDrawable.getBackgroundList();
        if (backgroundList != null) {
            for (XpAdaptiveLayerDrawable.ChildDrawable childDrawable : backgroundList) {
                int indexOf = backgroundList.indexOf(childDrawable);
                if (indexOf > -1 && indexOf < this.mBgBitmapList.size()) {
                    int save = canvas.save();
                    Bitmap bitmap = this.mBgBitmapList.get(indexOf);
                    bitmap.eraseColor(0);
                    this.mCanvas.setBitmap(bitmap);
                    this.mLayersShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                    this.mPaint.setShader(this.mLayersShader);
                    childDrawable.mDrawable.draw(this.mCanvas);
                    this.mCanvas.restoreToCount(save);
                    int save2 = canvas.save();
                    this.mCameraDrawHelper.transformCanvas(canvas, bounds, this.mZDistance);
                    canvas.drawPath(this.mMaskPath, this.mPaint);
                    canvas.restoreToCount(save2);
                    this.mZDistance += 15.0f;
                } else {
                    Logger.t(TAG).e("bg index OutOfIndex. index=" + indexOf + " size=" + this.mBgBitmapList.size(), new Object[0]);
                }
            }
        }
        List<XpAdaptiveLayerDrawable.ChildDrawable> foregroundList = this.mXpAdaptiveLayerDrawable.getForegroundList();
        if (foregroundList != null) {
            for (XpAdaptiveLayerDrawable.ChildDrawable childDrawable2 : foregroundList) {
                int indexOf2 = foregroundList.indexOf(childDrawable2);
                if (indexOf2 > -1 && indexOf2 < this.mFgBitmapList.size()) {
                    int save3 = canvas.save();
                    Bitmap bitmap2 = this.mFgBitmapList.get(indexOf2);
                    bitmap2.eraseColor(0);
                    this.mCanvas.setBitmap(bitmap2);
                    this.mLayersShader = new BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                    this.mPaint.setShader(this.mLayersShader);
                    childDrawable2.mDrawable.draw(this.mCanvas);
                    this.mCanvas.restoreToCount(save3);
                    int save4 = canvas.save();
                    this.mCameraDrawHelper.transformCanvas(canvas, bounds, this.mZDistance);
                    canvas.drawPath(this.mMaskPath, this.mPaint);
                    canvas.restoreToCount(save4);
                    this.mZDistance += 15.0f;
                } else {
                    Logger.t(TAG).e("fg index OutOfIndex. index=" + indexOf2 + " size=" + this.mFgBitmapList.size(), new Object[0]);
                }
            }
        }
    }
}
