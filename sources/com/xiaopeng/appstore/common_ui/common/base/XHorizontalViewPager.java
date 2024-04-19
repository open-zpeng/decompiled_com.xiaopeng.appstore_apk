package com.xiaopeng.appstore.common_ui.common.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;
import com.xiaopeng.appstore.libcommon.utils.InterceptTouchHelper;
/* loaded from: classes.dex */
public class XHorizontalViewPager extends ViewPager {
    private InterceptTouchHelper mInterceptTouchHelper;

    public XHorizontalViewPager(Context context) {
        super(context);
        this.mInterceptTouchHelper = new InterceptTouchHelper(1);
    }

    public XHorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mInterceptTouchHelper = new InterceptTouchHelper(1);
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.mInterceptTouchHelper.onInterceptTouchEvent(ev)) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }
}
