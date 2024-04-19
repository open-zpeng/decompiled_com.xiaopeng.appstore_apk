package com.xiaopeng.appstore.common_ui.common.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import com.orhanobut.logger.Logger;
/* loaded from: classes.dex */
public class MaskImageView extends AppCompatImageView implements MaskableView {
    private static final int MASK_COLOR = Color.argb(179, 0, 0, 0);
    private static final String TAG = "MaskImageView";
    private boolean mShowMask;

    public MaskImageView(Context context) {
        super(context);
        this.mShowMask = false;
    }

    public MaskImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mShowMask = false;
    }

    public MaskImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mShowMask = false;
    }

    protected int getMaskColor() {
        return MASK_COLOR;
    }

    @Override // com.xiaopeng.appstore.common_ui.common.widget.MaskableView
    public void setShowMask(boolean showMask) {
        Logger.t(TAG).d("setShowMask " + hashCode() + " " + showMask);
        if (this.mShowMask == showMask) {
            return;
        }
        this.mShowMask = showMask;
        if (showMask) {
            setColorFilter(getMaskColor(), PorterDuff.Mode.SRC_ATOP);
        } else {
            clearColorFilter();
        }
    }
}
