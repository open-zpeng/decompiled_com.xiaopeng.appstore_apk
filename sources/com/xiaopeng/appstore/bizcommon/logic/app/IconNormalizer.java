package com.xiaopeng.appstore.bizcommon.logic.app;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import com.xiaopeng.appstore.bizcommon.R;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import java.nio.ByteBuffer;
/* loaded from: classes2.dex */
public class IconNormalizer {
    private static final float BOUND_RATIO_MARGIN = 0.05f;
    private static final float CIRCLE_AREA_BY_RECT = 0.92f;
    private static final boolean DEBUG = false;
    public static final float ICON_VISIBLE_AREA_FACTOR = 0.92f;
    private static final float LINEAR_SCALE_SLOPE = 0.10850655f;
    private static final float MAX_CIRCLE_AREA_FACTOR = 0.6597222f;
    private static final float MAX_SQUARE_AREA_FACTOR = 0.6510417f;
    private static final int MIN_VISIBLE_ALPHA = 65;
    private static final float PIXEL_DIFF_PERCENTAGE_THRESHOLD = 0.004f;
    private static final float SCALE_NOT_INITIALIZED = 0.0f;
    private static final String TAG = "IconNormalizer";
    private static IconNormalizer sInstance;
    private final Rect mAdaptiveIconBounds;
    private float mAdaptiveIconScale;
    private final Bitmap mBitmap;
    private final Rect mBounds;
    private final Canvas mCanvas;
    private final float[] mLeftBorder;
    private final Matrix mMatrix;
    private final int mMaxSize;
    private final Paint mPaintMaskShape;
    private final Paint mPaintMaskShapeOutline;
    private final byte[] mPixels;
    private final float[] mRightBorder;
    private final Path mShapePath;

    public static IconNormalizer get() {
        if (sInstance == null) {
            synchronized (IconNormalizer.class) {
                if (sInstance == null) {
                    sInstance = new IconNormalizer();
                }
            }
        }
        return sInstance;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public IconNormalizer() {
        int dimensionPixelSize = Utils.getApp().getResources().getDimensionPixelSize(R.dimen.icon_max_size) * 2;
        this.mMaxSize = dimensionPixelSize;
        Bitmap createBitmap = Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, Bitmap.Config.ALPHA_8);
        this.mBitmap = createBitmap;
        this.mCanvas = new Canvas(createBitmap);
        this.mPixels = new byte[dimensionPixelSize * dimensionPixelSize];
        this.mLeftBorder = new float[dimensionPixelSize];
        this.mRightBorder = new float[dimensionPixelSize];
        this.mBounds = new Rect();
        this.mAdaptiveIconBounds = new Rect();
        Paint paint = new Paint();
        this.mPaintMaskShape = paint;
        paint.setColor(-65536);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        Paint paint2 = new Paint();
        this.mPaintMaskShapeOutline = paint2;
        paint2.setStrokeWidth(Utils.getApp().getResources().getDisplayMetrics().density * 2.0f);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(-16777216);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.mShapePath = new Path();
        this.mMatrix = new Matrix();
        this.mAdaptiveIconScale = 0.0f;
    }

    private boolean isShape(Path maskPath) {
        if (Math.abs((this.mBounds.width() / this.mBounds.height()) - 1.0f) > BOUND_RATIO_MARGIN) {
            return false;
        }
        this.mMatrix.reset();
        this.mMatrix.setScale(this.mBounds.width(), this.mBounds.height());
        this.mMatrix.postTranslate(this.mBounds.left, this.mBounds.top);
        maskPath.transform(this.mMatrix, this.mShapePath);
        this.mCanvas.drawPath(this.mShapePath, this.mPaintMaskShape);
        this.mCanvas.drawPath(this.mShapePath, this.mPaintMaskShapeOutline);
        return isTransparentBitmap();
    }

    private boolean isTransparentBitmap() {
        ByteBuffer wrap = ByteBuffer.wrap(this.mPixels);
        wrap.rewind();
        this.mBitmap.copyPixelsToBuffer(wrap);
        int i = this.mBounds.top;
        int i2 = this.mMaxSize;
        int i3 = i * i2;
        int i4 = i2 - this.mBounds.right;
        int i5 = 0;
        while (i < this.mBounds.bottom) {
            int i6 = i3 + this.mBounds.left;
            for (int i7 = this.mBounds.left; i7 < this.mBounds.right; i7++) {
                if ((this.mPixels[i6] & 255) > 65) {
                    i5++;
                }
                i6++;
            }
            i3 = i6 + i4;
            i++;
        }
        return ((float) i5) / ((float) (this.mBounds.width() * this.mBounds.height())) < PIXEL_DIFF_PERCENTAGE_THRESHOLD;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0046, code lost:
        if (r4 <= r17.mMaxSize) goto L88;
     */
    /* JADX WARN: Removed duplicated region for block: B:33:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00c8 A[Catch: all -> 0x0167, TryCatch #0 {, blocks: (B:4:0x0009, B:6:0x000e, B:8:0x0012, B:11:0x001a, B:12:0x001f, B:15:0x0023, B:19:0x0030, B:31:0x0052, B:35:0x0080, B:41:0x0090, B:42:0x0097, B:46:0x00aa, B:47:0x00b4, B:54:0x00c8, B:58:0x00dc, B:57:0x00d3, B:59:0x00df, B:61:0x00f2, B:63:0x00fa, B:65:0x0109, B:67:0x011b, B:69:0x013d, B:71:0x0140, B:72:0x0149, B:74:0x014d, B:76:0x0153, B:78:0x015a, B:22:0x0036, B:24:0x0044, B:28:0x004c, B:30:0x0050, B:26:0x0048), top: B:86:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x011b A[Catch: all -> 0x0167, TryCatch #0 {, blocks: (B:4:0x0009, B:6:0x000e, B:8:0x0012, B:11:0x001a, B:12:0x001f, B:15:0x0023, B:19:0x0030, B:31:0x0052, B:35:0x0080, B:41:0x0090, B:42:0x0097, B:46:0x00aa, B:47:0x00b4, B:54:0x00c8, B:58:0x00dc, B:57:0x00d3, B:59:0x00df, B:61:0x00f2, B:63:0x00fa, B:65:0x0109, B:67:0x011b, B:69:0x013d, B:71:0x0140, B:72:0x0149, B:74:0x014d, B:76:0x0153, B:78:0x015a, B:22:0x0036, B:24:0x0044, B:28:0x004c, B:30:0x0050, B:26:0x0048), top: B:86:0x0009 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized float getXPScale(android.graphics.drawable.Drawable r18, android.graphics.RectF r19, android.graphics.Path r20, boolean[] r21) {
        /*
            Method dump skipped, instructions count: 362
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.bizcommon.logic.app.IconNormalizer.getXPScale(android.graphics.drawable.Drawable, android.graphics.RectF, android.graphics.Path, boolean[]):float");
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0046, code lost:
        if (r4 <= r17.mMaxSize) goto L89;
     */
    /* JADX WARN: Removed duplicated region for block: B:33:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00d3 A[Catch: all -> 0x0176, TryCatch #0 {, blocks: (B:4:0x0009, B:6:0x000e, B:8:0x0012, B:11:0x001a, B:12:0x001f, B:15:0x0023, B:19:0x0030, B:31:0x0052, B:35:0x0080, B:41:0x0090, B:42:0x0097, B:46:0x00aa, B:47:0x00b4, B:52:0x00c4, B:54:0x00d3, B:58:0x00e7, B:57:0x00de, B:59:0x00ea, B:63:0x0109, B:65:0x011b, B:67:0x013e, B:69:0x0141, B:70:0x014a, B:72:0x0151, B:73:0x0158, B:75:0x015c, B:77:0x0162, B:79:0x0169, B:62:0x00ff, B:22:0x0036, B:24:0x0044, B:28:0x004c, B:30:0x0050, B:26:0x0048), top: B:87:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00ff A[Catch: all -> 0x0176, TryCatch #0 {, blocks: (B:4:0x0009, B:6:0x000e, B:8:0x0012, B:11:0x001a, B:12:0x001f, B:15:0x0023, B:19:0x0030, B:31:0x0052, B:35:0x0080, B:41:0x0090, B:42:0x0097, B:46:0x00aa, B:47:0x00b4, B:52:0x00c4, B:54:0x00d3, B:58:0x00e7, B:57:0x00de, B:59:0x00ea, B:63:0x0109, B:65:0x011b, B:67:0x013e, B:69:0x0141, B:70:0x014a, B:72:0x0151, B:73:0x0158, B:75:0x015c, B:77:0x0162, B:79:0x0169, B:62:0x00ff, B:22:0x0036, B:24:0x0044, B:28:0x004c, B:30:0x0050, B:26:0x0048), top: B:87:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x011b A[Catch: all -> 0x0176, TryCatch #0 {, blocks: (B:4:0x0009, B:6:0x000e, B:8:0x0012, B:11:0x001a, B:12:0x001f, B:15:0x0023, B:19:0x0030, B:31:0x0052, B:35:0x0080, B:41:0x0090, B:42:0x0097, B:46:0x00aa, B:47:0x00b4, B:52:0x00c4, B:54:0x00d3, B:58:0x00e7, B:57:0x00de, B:59:0x00ea, B:63:0x0109, B:65:0x011b, B:67:0x013e, B:69:0x0141, B:70:0x014a, B:72:0x0151, B:73:0x0158, B:75:0x015c, B:77:0x0162, B:79:0x0169, B:62:0x00ff, B:22:0x0036, B:24:0x0044, B:28:0x004c, B:30:0x0050, B:26:0x0048), top: B:87:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0151 A[Catch: all -> 0x0176, TryCatch #0 {, blocks: (B:4:0x0009, B:6:0x000e, B:8:0x0012, B:11:0x001a, B:12:0x001f, B:15:0x0023, B:19:0x0030, B:31:0x0052, B:35:0x0080, B:41:0x0090, B:42:0x0097, B:46:0x00aa, B:47:0x00b4, B:52:0x00c4, B:54:0x00d3, B:58:0x00e7, B:57:0x00de, B:59:0x00ea, B:63:0x0109, B:65:0x011b, B:67:0x013e, B:69:0x0141, B:70:0x014a, B:72:0x0151, B:73:0x0158, B:75:0x015c, B:77:0x0162, B:79:0x0169, B:62:0x00ff, B:22:0x0036, B:24:0x0044, B:28:0x004c, B:30:0x0050, B:26:0x0048), top: B:87:0x0009 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized float getScale(android.graphics.drawable.Drawable r18, android.graphics.RectF r19, android.graphics.Path r20, boolean[] r21) {
        /*
            Method dump skipped, instructions count: 377
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.bizcommon.logic.app.IconNormalizer.getScale(android.graphics.drawable.Drawable, android.graphics.RectF, android.graphics.Path, boolean[]):float");
    }

    private static void convertToConvexArray(float[] xCoordinates, int direction, int topY, int bottomY) {
        float[] fArr = new float[xCoordinates.length - 1];
        int i = -1;
        float f = Float.MAX_VALUE;
        for (int i2 = topY + 1; i2 <= bottomY; i2++) {
            if (xCoordinates[i2] > -1.0f) {
                if (f == Float.MAX_VALUE) {
                    i = topY;
                } else {
                    float f2 = ((xCoordinates[i2] - xCoordinates[i]) / (i2 - i)) - f;
                    float f3 = direction;
                    if (f2 * f3 < 0.0f) {
                        while (i > topY) {
                            i--;
                            if ((((xCoordinates[i2] - xCoordinates[i]) / (i2 - i)) - fArr[i]) * f3 >= 0.0f) {
                                break;
                            }
                        }
                    }
                }
                f = (xCoordinates[i2] - xCoordinates[i]) / (i2 - i);
                for (int i3 = i; i3 < i2; i3++) {
                    fArr[i3] = f;
                    xCoordinates[i3] = xCoordinates[i] + ((i3 - i) * f);
                }
                i = i2;
            }
        }
    }

    public static int getNormalizedCircleSize(int size) {
        return (int) Math.round(Math.sqrt((((size * size) * MAX_CIRCLE_AREA_FACTOR) * 4.0f) / 3.141592653589793d));
    }
}
