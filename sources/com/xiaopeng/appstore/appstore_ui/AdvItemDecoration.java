package com.xiaopeng.appstore.appstore_ui;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
/* loaded from: classes2.dex */
public class AdvItemDecoration extends RecyclerView.ItemDecoration {
    private final int mEdge = ResUtils.getDimenPixel(R.dimen.content_margin_horizontal);
    private final int mGridItemSpace = ResUtils.getDimenPixel(R.dimen.item_grid_app_item_space);

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int i;
        int i2;
        int i3;
        int i4;
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter == null || childAdapterPosition == -1 || !(parent.getLayoutManager() instanceof GridLayoutManager)) {
            return;
        }
        int itemViewType = adapter.getItemViewType(childAdapterPosition);
        if (itemViewType == 9) {
            if (((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex() == 0) {
                i3 = this.mEdge;
                i4 = this.mGridItemSpace / 2;
            } else {
                i3 = this.mGridItemSpace / 2;
                i4 = this.mEdge;
            }
            outRect.set(i3, 0, i4, 0);
        } else if (itemViewType == 13 || itemViewType == 7) {
            GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            int spanIndex = layoutParams.getSpanIndex();
            int spanSize = layoutParams.getSpanSize();
            int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
            if (spanIndex == 0) {
                i = this.mEdge;
                i2 = 0;
            } else if (spanIndex == spanCount - spanSize) {
                i2 = this.mEdge;
                i = 0;
            } else {
                int i5 = this.mEdge;
                int i6 = (spanCount / spanSize) - 2;
                if (i6 > 0) {
                    int i7 = i6 + 1;
                    int i8 = spanIndex / spanSize;
                    i = ((i7 - i8) * i5) / i7;
                    i2 = (i5 * i8) / i7;
                } else {
                    i = 0;
                    i2 = 0;
                }
            }
            outRect.set(i, 0, i2, 0);
        } else {
            super.getItemOffsets(outRect, view, parent, state);
        }
    }
}
