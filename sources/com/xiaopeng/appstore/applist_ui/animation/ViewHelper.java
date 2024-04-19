package com.xiaopeng.appstore.applist_ui.animation;

import android.view.View;
import androidx.core.view.ViewCompat;
/* loaded from: classes2.dex */
public final class ViewHelper {
    public static void clear(View v) {
        ViewCompat.setAlpha(v, 1.0f);
        ViewCompat.setScaleY(v, 1.0f);
        ViewCompat.setScaleX(v, 1.0f);
        ViewCompat.setTranslationY(v, 0.0f);
        ViewCompat.setTranslationX(v, 0.0f);
        ViewCompat.setRotation(v, 0.0f);
        ViewCompat.setRotationY(v, 0.0f);
        ViewCompat.setRotationX(v, 0.0f);
        ViewCompat.setPivotY(v, v.getMeasuredHeight() / 2);
        ViewCompat.setPivotX(v, v.getMeasuredWidth() / 2);
        ViewCompat.animate(v).setInterpolator(null).setStartDelay(0L);
    }
}
