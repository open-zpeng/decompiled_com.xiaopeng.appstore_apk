package com.xiaopeng.appstore.common_ui.icon.extension;

import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import com.xiaopeng.appstore.bizcommon.logic.app.IconNormalizer;
import com.xiaopeng.appstore.common_ui.R;
import com.xiaopeng.appstore.common_ui.icon.XpIconDrawable;
import com.xiaopeng.appstore.libcommon.ui.FixedScaleDrawable;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
/* loaded from: classes.dex */
public class XpAdaptiveIconDrawable extends XpIconDrawable {
    private static final float FIXED_SCALE = 0.8f;
    public static final float MASK_SIZE = 100.0f;
    private static final String TAG = "XpAdaptiveIconDrawable";
    private static final int sLegacyBgColor = ResUtils.getColor(R.color.legacy_icon_background);

    public XpAdaptiveIconDrawable(Drawable dr) {
        super(parseDrawable(dr));
    }

    @Override // com.xiaopeng.appstore.common_ui.icon.XpIconDrawable, android.graphics.drawable.DrawableWrapper
    public void setDrawable(Drawable dr) {
        super.setDrawable(parseDrawable(dr));
    }

    private static Drawable parseDrawable(Drawable dr) {
        AdaptiveIconDrawable adaptiveIconDrawable;
        if (dr instanceof AdaptiveIconDrawable) {
            adaptiveIconDrawable = (AdaptiveIconDrawable) dr;
        } else {
            adaptiveIconDrawable = new AdaptiveIconDrawable(new ColorDrawable(sLegacyBgColor), new FixedScaleDrawable());
        }
        if (adaptiveIconDrawable.getForeground() instanceof FixedScaleDrawable) {
            FixedScaleDrawable fixedScaleDrawable = (FixedScaleDrawable) adaptiveIconDrawable.getForeground();
            fixedScaleDrawable.setDrawable(dr);
            fixedScaleDrawable.setScale(IconNormalizer.get().getXPScale(dr, null, null, null));
        }
        return adaptiveIconDrawable;
    }
}
