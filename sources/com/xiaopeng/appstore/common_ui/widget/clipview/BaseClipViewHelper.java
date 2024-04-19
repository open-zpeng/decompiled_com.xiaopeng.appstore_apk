package com.xiaopeng.appstore.common_ui.widget.clipview;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
/* loaded from: classes.dex */
public abstract class BaseClipViewHelper {
    protected int mBgColor;
    protected ClipDrawCallback mCallback;
    protected Path mClipPath;
    protected Path mDrawPath;
    protected Matrix mMatrix;
    protected float mPathOriginHeight;
    protected float mPathOriginWidth;

    public abstract void draw(Canvas canvas);

    BaseClipViewHelper(Path path, ClipDrawCallback callback) {
        this(path, -1.0f, -1.0f, callback);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BaseClipViewHelper(Path path, float pathWidth, float pathHeight, ClipDrawCallback callback) {
        this.mBgColor = 0;
        this.mPathOriginWidth = pathWidth;
        this.mPathOriginHeight = pathHeight;
        setCallback(callback);
        setClipPath(path);
        this.mMatrix = new Matrix();
        this.mDrawPath = new Path();
    }

    public void setBgColor(int bgColor) {
        this.mBgColor = bgColor;
    }

    public void setCallback(ClipDrawCallback callback) {
        this.mCallback = callback;
    }

    public void setClipPath(Path path) {
        this.mClipPath = path;
        if (this.mPathOriginWidth <= 0.0f || this.mPathOriginHeight <= 0.0f) {
            RectF rectF = new RectF();
            this.mClipPath.computeBounds(rectF, true);
            this.mPathOriginWidth = rectF.width();
            this.mPathOriginHeight = rectF.height();
        }
    }

    public void onSizeChanged(int width, int height) {
        if (this.mClipPath == null) {
            throw new IllegalArgumentException("Path must set first.");
        }
        float min = Math.min(width / this.mPathOriginWidth, height / this.mPathOriginHeight);
        this.mMatrix.reset();
        this.mMatrix.setScale(min, min);
        this.mClipPath.transform(this.mMatrix, this.mDrawPath);
    }

    public static Path generateRoundRect(float left, float top, float right, float bottom, float rx, float ry, Path.Direction dir) {
        Path path = new Path();
        path.addRoundRect(left, top, right, bottom, rx, ry, dir);
        return path;
    }

    public static Path generateRect(float left, float top, float right, float bottom, Path.Direction dir) {
        Path path = new Path();
        path.addRect(left, top, right, bottom, dir);
        return path;
    }
}
