package com.xiaopeng.appstore.applist_ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.applist_ui.adapter.AppListAdapter;
import com.xiaopeng.appstore.common_ui.common.base.FadingEdgeRecyclerView;
/* loaded from: classes2.dex */
public class AppRecyclerView extends FadingEdgeRecyclerView {
    private AppListAdapter mAppListAdapter;
    private View mPressedView;

    public AppRecyclerView(Context context) {
        this(context, null);
    }

    public AppRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mPressedView = null;
        this.mAppListAdapter = null;
    }

    @Override // com.xiaopeng.xui.vui.VuiRecyclerView, androidx.recyclerview.widget.RecyclerView
    public void setAdapter(RecyclerView.Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof AppListAdapter) {
            this.mAppListAdapter = (AppListAdapter) adapter;
        } else {
            this.mAppListAdapter = null;
        }
    }

    private boolean isEditMode() {
        AppListAdapter appListAdapter = this.mAppListAdapter;
        if (appListAdapter != null) {
            return appListAdapter.isEditMode();
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0029, code lost:
        if (r0 != 3) goto L23;
     */
    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            boolean r0 = r5.isEditMode()
            if (r0 == 0) goto L3e
            boolean r0 = super.onInterceptTouchEvent(r6)
            int r1 = r5.getScrollState()
            r2 = 1
            if (r1 != 0) goto L14
            if (r0 == 0) goto L14
            return r2
        L14:
            int r0 = r6.getActionMasked()
            float r1 = r6.getX()
            float r3 = r6.getY()
            r4 = 0
            if (r0 == 0) goto L30
            if (r0 == r2) goto L2c
            r1 = 2
            if (r0 == r1) goto L39
            r1 = 3
            if (r0 == r1) goto L2c
            goto L3e
        L2c:
            r0 = 0
            r5.mPressedView = r0
            goto L3e
        L30:
            android.view.View r0 = r5.findChildViewUnder(r1, r3)
            if (r0 == 0) goto L39
            r5.mPressedView = r0
            return r4
        L39:
            android.view.View r0 = r5.mPressedView
            if (r0 == 0) goto L3e
            return r4
        L3e:
            boolean r6 = super.onInterceptTouchEvent(r6)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.applist_ui.view.AppRecyclerView.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }
}
