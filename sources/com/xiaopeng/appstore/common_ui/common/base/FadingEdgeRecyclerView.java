package com.xiaopeng.appstore.common_ui.common.base;

import android.content.Context;
import android.util.AttributeSet;
import com.xiaopeng.xui.widget.XRecyclerView;
/* loaded from: classes.dex */
public class FadingEdgeRecyclerView extends XRecyclerView {
    @Override // android.view.View
    protected boolean isPaddingOffsetRequired() {
        return true;
    }

    public FadingEdgeRecyclerView(Context context) {
        super(context);
    }

    public FadingEdgeRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FadingEdgeRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        tryEnableDraw();
    }

    @Override // android.view.View
    protected int getTopPaddingOffset() {
        return -getPaddingTop();
    }

    @Override // android.view.View
    protected int getBottomPaddingOffset() {
        return getPaddingBottom();
    }

    @Override // android.view.View
    protected int getLeftPaddingOffset() {
        return -getPaddingStart();
    }

    @Override // android.view.View
    protected int getRightPaddingOffset() {
        return getPaddingEnd();
    }

    private void tryEnableDraw() {
        if (isHorizontalFadingEdgeEnabled() || isVerticalFadingEdgeEnabled() || isVerticalScrollBarEnabled() || isHorizontalScrollBarEnabled()) {
            setWillNotDraw(false);
        }
    }
}
