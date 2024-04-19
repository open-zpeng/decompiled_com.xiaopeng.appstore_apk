package com.xiaopeng.appstore.common_ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class DrawableLeftXTextView extends XTextView {
    public DrawableLeftXTextView(Context context) {
        super(context);
    }

    public DrawableLeftXTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableLeftXTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        handleLeftDrawable();
    }

    private void handleLeftDrawable() {
        Drawable drawable = getCompoundDrawablesRelative()[0];
        if (drawable != null && getHeight() > getLineHeight()) {
            int i = (-getLineHeight()) / 2;
            drawable.setBounds(0, i, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight() + i);
        }
        Drawable drawable2 = getCompoundDrawablesRelative()[2];
        if (drawable2 == null || getHeight() <= getLineHeight()) {
            return;
        }
        int i2 = (-getLineHeight()) / 2;
        drawable2.setBounds(0, i2, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight() + i2);
    }
}
