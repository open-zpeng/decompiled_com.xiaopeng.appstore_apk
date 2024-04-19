package com.xiaopeng.appstore.appstore_ui.adapter.viewholder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
/* loaded from: classes2.dex */
public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = {16843284};
    private int dividerHeight;
    private int dividerWidth;
    private boolean hasBorder;
    private Rect mBounds;
    private Paint mPaint;

    private boolean isFirstRow(int position, int spanCount) {
        return position < spanCount;
    }

    public GridItemDecoration(Context context) {
        this(context, false);
    }

    public GridItemDecoration(Context context, float dividerWidth, float dividerHeight) {
        this(context, false);
        this.dividerHeight = (int) dividerHeight;
        this.dividerWidth = (int) dividerWidth;
    }

    public GridItemDecoration(Context context, boolean hasBorder) {
        this.dividerHeight = 0;
        this.dividerWidth = 0;
        this.mBounds = new Rect();
        this.hasBorder = false;
        this.hasBorder = hasBorder;
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setColor(Color.parseColor("#00000000"));
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int i;
        int i2;
        int itemCount = parent.getAdapter().getItemCount();
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        int spanCount = getSpanCount(parent);
        int i3 = childAdapterPosition % spanCount;
        boolean z = this.hasBorder;
        int i4 = z ? 1 : -1;
        int i5 = this.dividerWidth;
        int i6 = ((i4 + spanCount) * i5) / spanCount;
        int i7 = z ? ((i3 + 1) * i5) - (i3 * i6) : i3 * (i5 - i6);
        int i8 = i6 - i7;
        if (z) {
            i = isFirstRow(childAdapterPosition, spanCount) ? this.dividerHeight : this.dividerHeight / 2;
            i2 = isLastRow(childAdapterPosition, itemCount, spanCount) ? this.dividerHeight : this.dividerHeight / 2;
        } else {
            i = isFirstRow(childAdapterPosition, spanCount) ? 0 : this.dividerHeight / 2;
            i2 = isLastRow(childAdapterPosition, itemCount, spanCount) ? 0 : this.dividerHeight / 2;
        }
        outRect.set(i7, i, i8, i2);
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

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVertical(c, parent);
        drawHorizontal(c, parent);
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int i;
        canvas.save();
        int itemCount = parent.getAdapter().getItemCount();
        int spanCount = getSpanCount(parent);
        int width = ((parent.getWidth() - parent.getPaddingLeft()) - parent.getPaddingRight()) / spanCount;
        int childCount = parent.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = parent.getChildAt(i2);
            int childAdapterPosition = parent.getChildAdapterPosition(childAt);
            parent.getDecoratedBoundsWithMargins(childAt, this.mBounds);
            int i3 = this.mBounds.left;
            int i4 = i3 + width;
            int i5 = this.mBounds.bottom;
            if (isLastRow(childAdapterPosition, itemCount, spanCount)) {
                i = i5 + (this.hasBorder ? -this.dividerHeight : 0);
            } else {
                i = i5 - (this.dividerHeight / 2);
            }
            int i6 = (childAdapterPosition < itemCount - spanCount || this.hasBorder) ? this.dividerHeight + i : i;
            Paint paint = this.mPaint;
            if (paint != null) {
                canvas.drawRect(i3, i, i4, i6, paint);
            }
            if (isFirstRow(childAdapterPosition, spanCount) && this.hasBorder) {
                int i7 = this.mBounds.top;
                int i8 = this.dividerHeight + i7;
                Paint paint2 = this.mPaint;
                if (paint2 != null) {
                    canvas.drawRect(i3, i7, i4, i8, paint2);
                }
            }
        }
        canvas.restore();
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int i;
        int i2;
        int i3;
        int i4;
        RecyclerView recyclerView = parent;
        canvas.save();
        int itemCount = parent.getAdapter().getItemCount();
        int spanCount = getSpanCount(recyclerView);
        int width = ((parent.getWidth() - parent.getPaddingLeft()) - parent.getPaddingRight()) / spanCount;
        int i5 = (((this.hasBorder ? 1 : -1) + spanCount) * this.dividerWidth) / spanCount;
        int i6 = 0;
        for (int childCount = parent.getChildCount(); i6 < childCount; childCount = i3) {
            View childAt = recyclerView.getChildAt(i6);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(childAt, this.mBounds);
            int childAdapterPosition = recyclerView.getChildAdapterPosition(childAt);
            int i7 = this.mBounds.top;
            int i8 = this.mBounds.bottom;
            boolean z = this.hasBorder;
            if (!z) {
                if (childAdapterPosition + spanCount == itemCount) {
                    i8 += this.dividerHeight / 2;
                } else {
                    int i9 = itemCount % spanCount;
                    if (i9 != 0 && i9 < childAdapterPosition % spanCount && childAdapterPosition > itemCount - spanCount) {
                        i8 -= this.dividerHeight / 2;
                    }
                }
            }
            int i10 = childAdapterPosition % spanCount;
            if (z) {
                int i11 = this.mBounds.left;
                i = this.dividerWidth;
                i2 = i11 - ((i5 - i) * i10);
            } else {
                int i12 = this.mBounds.left;
                i = this.dividerWidth;
                i2 = i12 - (i - ((i - i5) * i10));
                if (i10 == 0) {
                    i = 0;
                }
            }
            int i13 = i + i2;
            Paint paint = this.mPaint;
            if (paint != null) {
                i3 = childCount;
                canvas.drawRect(i2, i7, i13, i8, paint);
            } else {
                i3 = childCount;
            }
            if (this.hasBorder && isLastColumn(childAdapterPosition, spanCount, itemCount)) {
                if ((i10 + 1) % spanCount == 0) {
                    i4 = (parent.getWidth() - parent.getPaddingRight()) - this.dividerWidth;
                } else {
                    i4 = (i2 + width) - (i5 - this.dividerWidth);
                }
                int i14 = this.dividerWidth + i4;
                Paint paint2 = this.mPaint;
                if (paint2 != null) {
                    canvas.drawRect(i4, i7, i14, i8, paint2);
                }
            }
            i6++;
            recyclerView = parent;
        }
        canvas.restore();
    }

    private boolean isLastRow(int position, int itemCount, int spanCount) {
        int i;
        return position < itemCount && (((i = itemCount % spanCount) == 0 && position >= itemCount - spanCount) || i >= itemCount - position);
    }

    private boolean isLastColumn(int position, int spanCount, int itemCount) {
        int i = position + 1;
        return i % spanCount == 0 || i == itemCount;
    }

    public void setHasBorder(boolean hasBorder) {
        this.hasBorder = hasBorder;
    }
}
