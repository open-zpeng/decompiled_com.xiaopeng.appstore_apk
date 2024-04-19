package com.xiaopeng.appstore.libcommon.utils;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
/* loaded from: classes2.dex */
public class CameraDrawHelper {
    private int mMaxRotateDegree = 45;
    private float mCurrentAngleX = 0.0f;
    private float mCurrentAngleY = 0.0f;
    private Camera mCamera = new Camera();
    private Matrix mMatrix = new Matrix();

    public void setMaxRotateDegree(int maxRotateDegree) {
        this.mMaxRotateDegree = maxRotateDegree;
    }

    public int getMaxRotateDegree() {
        return this.mMaxRotateDegree;
    }

    public void drawDrawable(Canvas canvas, Drawable drawable, float zDistance) {
        Rect bounds = drawable.getBounds();
        int i = (bounds.left + bounds.right) / 2;
        int i2 = (bounds.top + bounds.bottom) / 2;
        this.mCamera.save();
        this.mCamera.rotate(this.mCurrentAngleX, this.mCurrentAngleY, 0.0f);
        this.mCamera.translate(0.0f, 0.0f, -zDistance);
        this.mCamera.getMatrix(this.mMatrix);
        this.mCamera.restore();
        this.mMatrix.postTranslate(i, i2);
        this.mMatrix.preTranslate(-i, -i2);
        int save = canvas.save();
        canvas.concat(this.mMatrix);
        drawable.draw(canvas);
        canvas.restoreToCount(save);
    }

    public void transformCanvas(Canvas canvas, Rect bounds, float zDistance) {
        int i = (bounds.left + bounds.right) / 2;
        int i2 = (bounds.top + bounds.bottom) / 2;
        this.mCamera.save();
        this.mCamera.rotate(this.mCurrentAngleX, this.mCurrentAngleY, 0.0f);
        this.mCamera.translate(0.0f, 0.0f, -zDistance);
        this.mCamera.getMatrix(this.mMatrix);
        this.mCamera.restore();
        this.mMatrix.postTranslate(i, i2);
        this.mMatrix.preTranslate(-i, -i2);
        canvas.concat(this.mMatrix);
    }

    public void rotateCamera(float angleX, float angleY) {
        this.mCurrentAngleX = limitAngle(angleX);
        this.mCurrentAngleY = limitAngle(angleY);
    }

    private float limitAngle(float angle) {
        if (angle < 0.0f) {
            return Math.max(angle, -this.mMaxRotateDegree);
        }
        return Math.min(angle, this.mMaxRotateDegree);
    }
}
