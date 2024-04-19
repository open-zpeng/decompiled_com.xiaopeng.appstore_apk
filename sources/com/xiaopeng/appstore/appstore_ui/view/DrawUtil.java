package com.xiaopeng.appstore.appstore_ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: DrawUtil.kt */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0012\u0010\u0007\u001a\u00020\b*\u00020\b2\u0006\u0010\t\u001a\u00020\b\u001a\u0012\u0010\n\u001a\u00020\u000b*\u00020\b2\u0006\u0010\t\u001a\u00020\b\u001a3\u0010\f\u001a\u00020\r\"\b\b\u0000\u0010\u000e*\u00020\u000f*\u0002H\u000e2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\n\b\u0003\u0010\u0012\u001a\u0004\u0018\u00010\bH\u0007¢\u0006\u0002\u0010\u0013\u001a\f\u0010\u0014\u001a\u00020\u0001*\u00020\u0001H\u0002\u001a\n\u0010\u0015\u001a\u00020\u0001*\u00020\u0001\u001a\u001a\u0010\u0016\u001a\u00020\u0017*\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u0001\u001a\n\u0010\u001b\u001a\u00020\u0001*\u00020\u0001\u001a\u0012\u0010\u001c\u001a\u00020\u0001*\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0001\u001a\n\u0010\u001f\u001a\u00020\u0001*\u00020\r\u001a\n\u0010 \u001a\u00020\u0001*\u00020\r\u001a\n\u0010!\u001a\u00020\u0001*\u00020\r\u001a\u0012\u0010\"\u001a\u00020\u0017*\u00020#2\u0006\u0010$\u001a\u00020\u000b\u001a\u0012\u0010%\u001a\u00020\b*\u00020\b2\u0006\u0010\t\u001a\u00020\b\u001a)\u0010&\u001a\u00020\u0017*\u00020\r2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\n\b\u0003\u0010\u0012\u001a\u0004\u0018\u00010\bH\u0007¢\u0006\u0002\u0010'\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004¨\u0006("}, d2 = {"height", "", "Landroid/graphics/RectF;", "getHeight", "(Landroid/graphics/RectF;)F", "width", "getWidth", "addFlag", "", "flag", "containsFlag", "", "createPaint", "Landroid/graphics/Paint;", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/view/View;", "colorString", "", TypedValues.Custom.S_COLOR, "(Landroid/view/View;Ljava/lang/String;Ljava/lang/Integer;)Landroid/graphics/Paint;", "degree2radian", "degreeCos", "degreePointF", "", "Landroid/graphics/PointF;", "outPointF", "degree", "degreeSin", "dpf2pxf", "Landroid/content/Context;", "dpValue", "getBottomedY", "getCenteredY", "getToppedY", "helpGreenCurtain", "Landroid/graphics/Canvas;", "debug", "removeFlag", "utilReset", "(Landroid/graphics/Paint;Ljava/lang/String;Ljava/lang/Integer;)V", "appstore_ui_D55V1Release"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class DrawUtil {
    public static final int addFlag(int i, int i2) {
        return i | i2;
    }

    public static final boolean containsFlag(int i, int i2) {
        return (i2 | i) == i;
    }

    public static final <T extends View> Paint createPaint(T t) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        return createPaint$default(t, null, null, 3, null);
    }

    public static final <T extends View> Paint createPaint(T t, String str) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        return createPaint$default(t, str, null, 2, null);
    }

    private static final float degree2radian(float f) {
        return (float) ((f / 180.0f) * 3.141592653589793d);
    }

    public static final int removeFlag(int i, int i2) {
        return i & (~i2);
    }

    public static final void utilReset(Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "<this>");
        utilReset$default(paint, null, null, 3, null);
    }

    public static final void utilReset(Paint paint, String str) {
        Intrinsics.checkNotNullParameter(paint, "<this>");
        utilReset$default(paint, str, null, 2, null);
    }

    public static /* synthetic */ Paint createPaint$default(View view, String str, Integer num, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        if ((i & 2) != 0) {
            num = null;
        }
        return createPaint(view, str, num);
    }

    public static final <T extends View> Paint createPaint(T t, String str, Integer num) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Paint paint = new Paint();
        utilReset(paint, str, num);
        return paint;
    }

    public static /* synthetic */ void utilReset$default(Paint paint, String str, Integer num, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        if ((i & 2) != 0) {
            num = null;
        }
        utilReset(paint, str, num);
    }

    public static final void utilReset(Paint paint, String str, Integer num) {
        int parseColor;
        Intrinsics.checkNotNullParameter(paint, "<this>");
        paint.reset();
        if (num != null) {
            parseColor = num.intValue();
        } else {
            if (str == null) {
                str = "#FFFFFF";
            }
            parseColor = Color.parseColor(str);
        }
        paint.setColor(parseColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0.0f);
    }

    public static final float getCenteredY(Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "<this>");
        return (paint.getFontSpacing() / 2) - paint.getFontMetrics().bottom;
    }

    public static final float getBottomedY(Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "<this>");
        return -paint.getFontMetrics().bottom;
    }

    public static final float getToppedY(Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "<this>");
        return -paint.getFontMetrics().ascent;
    }

    public static final float dpf2pxf(Context context, float f) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        if (f == 0.0f) {
            return 0.0f;
        }
        return (f * context.getResources().getDisplayMetrics().density) + 0.5f;
    }

    public static final float getWidth(RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        return rectF.right - rectF.left;
    }

    public static final float getHeight(RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        return rectF.bottom - rectF.top;
    }

    public static final void helpGreenCurtain(Canvas canvas, boolean z) {
        Intrinsics.checkNotNullParameter(canvas, "<this>");
        if (z) {
            canvas.drawColor(-16711936);
        }
    }

    public static final float degreeSin(float f) {
        return (float) Math.sin(degree2radian(f));
    }

    public static final float degreeCos(float f) {
        return (float) Math.cos(degree2radian(f));
    }

    public static final void degreePointF(PointF pointF, PointF outPointF, float f) {
        Intrinsics.checkNotNullParameter(pointF, "<this>");
        Intrinsics.checkNotNullParameter(outPointF, "outPointF");
        outPointF.x = (pointF.x * degreeCos(f)) - (pointF.y * degreeSin(f));
        outPointF.y = (pointF.x * degreeSin(f)) + (pointF.y * degreeCos(f));
    }
}
