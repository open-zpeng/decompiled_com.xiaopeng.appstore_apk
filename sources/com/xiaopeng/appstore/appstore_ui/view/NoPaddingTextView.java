package com.xiaopeng.appstore.appstore_ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public class NoPaddingTextView extends XTextView {
    public NoPaddingTextView(Context context) {
        this(context, null);
    }

    public NoPaddingTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoPaddingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        float textSize = getTextSize();
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        setTextSize(0, textSize);
        setPadding(0, -(Math.abs(fontMetricsInt.top - fontMetricsInt.ascent) + ((int) Math.ceil(Math.abs((fontMetricsInt.top - fontMetricsInt.ascent) / 2.0d)))), 0, fontMetricsInt.top - fontMetricsInt.ascent);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
