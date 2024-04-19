package com.xiaopeng.appstore.common_ui.icon.extension;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.xiaopeng.appstore.common_ui.icon.XpAdaptiveLayerDrawable;
/* loaded from: classes.dex */
public abstract class BaseAdaptiveDrawStrategy {
    protected XpAdaptiveLayerDrawable mXpAdaptiveLayerDrawable;

    public void onBoundsChange(Rect bounds) {
    }

    public BaseAdaptiveDrawStrategy(XpAdaptiveLayerDrawable xpAdaptiveLayerDrawable) {
        this.mXpAdaptiveLayerDrawable = xpAdaptiveLayerDrawable;
    }

    public void draw(Canvas canvas) {
        drawBackgroundList(canvas);
        drawForegroundList(canvas);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void drawForegroundList(Canvas canvas) {
        XpAdaptiveLayerDrawable xpAdaptiveLayerDrawable = this.mXpAdaptiveLayerDrawable;
        if (xpAdaptiveLayerDrawable == null || xpAdaptiveLayerDrawable.getForegroundList() == null) {
            return;
        }
        for (XpAdaptiveLayerDrawable.ChildDrawable childDrawable : this.mXpAdaptiveLayerDrawable.getForegroundList()) {
            int save = canvas.save();
            childDrawable.mDrawable.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void drawBackgroundList(Canvas canvas) {
        XpAdaptiveLayerDrawable xpAdaptiveLayerDrawable = this.mXpAdaptiveLayerDrawable;
        if (xpAdaptiveLayerDrawable == null || xpAdaptiveLayerDrawable.getBackgroundList() == null) {
            return;
        }
        for (XpAdaptiveLayerDrawable.ChildDrawable childDrawable : this.mXpAdaptiveLayerDrawable.getBackgroundList()) {
            int save = canvas.save();
            childDrawable.mDrawable.draw(canvas);
            canvas.restoreToCount(save);
        }
    }
}
