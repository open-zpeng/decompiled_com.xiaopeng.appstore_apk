package com.xiaopeng.appstore.appstore_ui.adapter.viewholder;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
/* loaded from: classes2.dex */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = {16843284};
    public static final int BOTH_SET = 2;
    public static final int HORIZONTAL_LIST = 0;
    public static final int VERTICAL_LIST = 1;
    private int mDividerHeight;
    private Drawable mDrawable;
    private int mOrientation;
    private Paint mPaint;

    public DividerItemDecoration(Context context, int orientation) {
        this.mDividerHeight = 2;
        setOrientation(orientation);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(ATTRS);
        this.mDrawable = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
    }

    public DividerItemDecoration(int orientation, int dividerHeight) {
        this.mDividerHeight = 2;
        setOrientation(orientation);
        this.mDividerHeight = dividerHeight;
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setColor(Color.parseColor("#00000000"));
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    public DividerItemDecoration(Context context, int orientation, int dividerHeight, int dividerColor) {
        this.mDividerHeight = 2;
        setOrientation(orientation);
        this.mDividerHeight = dividerHeight;
        Log.e("mDividerHeight", this.mDividerHeight + "===================");
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setColor(dividerColor);
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    public void setOrientation(int orientation) {
        if (orientation < 0 || orientation > 2) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.mOrientation = orientation;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int viewLayoutPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int itemCount = parent.getAdapter().getItemCount();
        int i = this.mOrientation;
        if (i == 0) {
            outRect.set(0, 0, 0, viewLayoutPosition != itemCount + (-1) ? this.mDividerHeight : 0);
        } else if (i == 1) {
            outRect.set(0, 0, viewLayoutPosition != itemCount + (-1) ? this.mDividerHeight : 0, 0);
        } else if (i != 2) {
        } else {
            int spanCount = getSpanCount(parent);
            if (isLastRaw(parent, viewLayoutPosition, spanCount, itemCount)) {
                outRect.set(0, 0, this.mDividerHeight, 0);
            } else if (isLastColum(parent, viewLayoutPosition, spanCount, itemCount)) {
                outRect.set(0, 0, 0, this.mDividerHeight);
            } else {
                int i2 = this.mDividerHeight;
                outRect.set(0, 0, i2, i2);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int i = this.mOrientation;
        if (i == 1) {
            drawVertical(c, parent);
        } else if (i == 0) {
            drawHorizontal(c, parent);
        } else {
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int paddingLeft = parent.getPaddingLeft();
        int measuredWidth = parent.getMeasuredWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);
            int bottom = childAt.getBottom() + ((RecyclerView.LayoutParams) childAt.getLayoutParams()).bottomMargin;
            int i2 = this.mDividerHeight + bottom;
            Log.e("height", i2 + "===================");
            Drawable drawable = this.mDrawable;
            if (drawable != null) {
                drawable.setBounds(paddingLeft, bottom, measuredWidth, i2);
                this.mDrawable.draw(canvas);
            }
            Paint paint = this.mPaint;
            if (paint != null) {
                canvas.drawRect(paddingLeft, bottom, measuredWidth, i2, paint);
            }
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int paddingTop = parent.getPaddingTop();
        int measuredHeight = parent.getMeasuredHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);
            int right = childAt.getRight() + ((RecyclerView.LayoutParams) childAt.getLayoutParams()).rightMargin;
            int i2 = this.mDividerHeight + right;
            Drawable drawable = this.mDrawable;
            if (drawable != null) {
                drawable.setBounds(right, paddingTop, i2, measuredHeight);
                this.mDrawable.draw(canvas);
            }
            Paint paint = this.mPaint;
            if (paint != null) {
                canvas.drawRect(right, paddingTop, i2, measuredHeight, paint);
            }
        }
    }

    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return -1;
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getOrientation() == 1 ? (pos + 1) % spanCount == 0 : pos >= childCount - (childCount % spanCount);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getOrientation() == 1 ? (pos + 1) % spanCount == 0 : pos >= childCount - (childCount % spanCount);
        } else {
            return false;
        }
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int i = childCount - (childCount % spanCount);
            return ((GridLayoutManager) layoutManager).getOrientation() == 1 ? pos >= i - (i % spanCount) : (pos + 1) % spanCount == 0;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getOrientation() == 1 ? pos >= childCount - (childCount % spanCount) : (pos + 1) % spanCount == 0;
        } else {
            return false;
        }
    }
}
