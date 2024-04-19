package com.xiaopeng.appstore.libcommon.utils;

import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.TextView;
import androidx.core.text.PrecomputedTextCompat;
import androidx.core.widget.TextViewCompat;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
/* loaded from: classes2.dex */
public class UIUtils {
    private UIUtils() {
    }

    public static float dpiFromPx(int size, DisplayMetrics metrics) {
        return size / (metrics.densityDpi / 160.0f);
    }

    public static int pxFromDp(float size, DisplayMetrics metrics) {
        return Math.round(TypedValue.applyDimension(1, size, metrics));
    }

    public static int pxFromSp(float size, DisplayMetrics metrics) {
        return Math.round(TypedValue.applyDimension(2, size, metrics));
    }

    public static int calculateTextHeight(float textSizePx) {
        Paint paint = new Paint();
        paint.setTextSize(textSizePx);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (int) Math.ceil(fontMetrics.bottom - fontMetrics.top);
    }

    public static void setTextAsync(TextView textView, final CharSequence longString, Executor bgExecutor) {
        if (TextUtils.isEmpty(longString)) {
            return;
        }
        final PrecomputedTextCompat.Params textMetricsParams = TextViewCompat.getTextMetricsParams(textView);
        final WeakReference weakReference = new WeakReference(textView);
        bgExecutor.execute(new Runnable() { // from class: com.xiaopeng.appstore.libcommon.utils.-$$Lambda$UIUtils$CH1FqThQvdiEbUJrbZWScNygqb4
            @Override // java.lang.Runnable
            public final void run() {
                UIUtils.lambda$setTextAsync$1(weakReference, longString, textMetricsParams);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$setTextAsync$1(final Reference reference, CharSequence charSequence, PrecomputedTextCompat.Params params) {
        TextView textView = (TextView) reference.get();
        if (textView == null) {
            return;
        }
        final PrecomputedTextCompat create = PrecomputedTextCompat.create(charSequence, params);
        textView.post(new Runnable() { // from class: com.xiaopeng.appstore.libcommon.utils.-$$Lambda$UIUtils$hBrU_01rF3GDu-N41gaSsoMqars
            @Override // java.lang.Runnable
            public final void run() {
                UIUtils.lambda$setTextAsync$0(reference, create);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$setTextAsync$0(Reference reference, PrecomputedTextCompat precomputedTextCompat) {
        TextView textView = (TextView) reference.get();
        if (textView == null) {
            return;
        }
        textView.setText(precomputedTextCompat);
    }

    public static void increaseHitArea(final View view, int dp) {
        if (view.getParent() instanceof View) {
            final int applyDimension = (int) TypedValue.applyDimension(1, dp, Resources.getSystem().getDisplayMetrics());
            final View view2 = (View) view.getParent();
            view2.post(new Runnable() { // from class: com.xiaopeng.appstore.libcommon.utils.-$$Lambda$UIUtils$Xo40ouCEy110Ru0wcXCjXwbMSaQ
                @Override // java.lang.Runnable
                public final void run() {
                    UIUtils.lambda$increaseHitArea$2(view, applyDimension, view2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$increaseHitArea$2(View view, int i, View view2) {
        Rect rect = new Rect();
        view.getHitRect(rect);
        rect.top -= i;
        rect.left -= i;
        rect.bottom += i;
        rect.right += i;
        view2.setTouchDelegate(new TouchDelegate(rect, view));
    }
}
