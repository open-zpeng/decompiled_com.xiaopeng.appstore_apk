package com.xiaopeng.appstore.common_ui.common.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.xiaopeng.appstore.libcommon.utils.InterceptTouchHelper;
/* loaded from: classes.dex */
public class XVerticalRecyclerView extends FadingEdgeRecyclerView {
    private InterceptTouchHelper mInterceptTouchHelper;

    public XVerticalRecyclerView(Context context) {
        super(context);
        this.mInterceptTouchHelper = new InterceptTouchHelper(2);
    }

    public XVerticalRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mInterceptTouchHelper = new InterceptTouchHelper(2);
    }

    public XVerticalRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mInterceptTouchHelper = new InterceptTouchHelper(2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.mInterceptTouchHelper.onInterceptTouchEvent(ev)) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }
}
