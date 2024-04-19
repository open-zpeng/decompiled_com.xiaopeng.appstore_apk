package com.xiaopeng.appstore.common_ui.common.base;

import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
/* loaded from: classes.dex */
public class SCommonItemDecoration extends RecyclerView.ItemDecoration {
    private SparseArray<ItemDecorationProps> mPropMap;

    public static ItemDecorationBuilder builder() {
        return new ItemDecorationBuilder();
    }

    SCommonItemDecoration(SparseArray<ItemDecorationProps> propMap) {
        this.mPropMap = propMap;
    }

    SparseArray<ItemDecorationProps> getPropMap() {
        return this.mPropMap;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        ItemDecorationProps itemDecorationProps;
        int i;
        int i2;
        int i3;
        int i4;
        int topBottomSpace;
        int topBottomSpace2;
        int i5;
        int leftRightSpace;
        Rect rect;
        int leftRightSpace2;
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter == null || childAdapterPosition == -1) {
            return;
        }
        int itemViewType = adapter.getItemViewType(childAdapterPosition);
        SparseArray<ItemDecorationProps> sparseArray = this.mPropMap;
        if (sparseArray == null || (itemDecorationProps = sparseArray.get(itemViewType)) == null) {
            return;
        }
        int i6 = 0;
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            i = layoutParams.getSpanIndex();
            i2 = layoutParams.getSpanSize();
            GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
            i4 = gridLayoutManager.getSpanCount();
            i3 = gridLayoutManager.getOrientation();
        } else if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager.LayoutParams layoutParams2 = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            i = layoutParams2.getSpanIndex();
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) parent.getLayoutManager();
            i4 = staggeredGridLayoutManager.getSpanCount();
            i2 = layoutParams2.isFullSpan() ? i4 : 1;
            i3 = staggeredGridLayoutManager.getOrientation();
        } else {
            i = 0;
            i2 = 1;
            i3 = 1;
            i4 = 1;
        }
        int i7 = childAdapterPosition > 0 ? childAdapterPosition - 1 : -1;
        int i8 = childAdapterPosition < adapter.getItemCount() - 1 ? childAdapterPosition + 1 : -1;
        int i9 = childAdapterPosition > i ? childAdapterPosition - (i + 1) : -1;
        int i10 = i4 - i;
        int i11 = childAdapterPosition < adapter.getItemCount() - i10 ? ((childAdapterPosition + i10) - i2) + 1 : -1;
        boolean z = childAdapterPosition == 0 || i7 == -1 || itemViewType != adapter.getItemViewType(i7) || i9 == -1 || itemViewType != adapter.getItemViewType(i9);
        boolean z2 = childAdapterPosition == adapter.getItemCount() - 1 || i8 == -1 || itemViewType != adapter.getItemViewType(i8) || i11 == -1 || itemViewType != adapter.getItemViewType(i11);
        if (i3 == 1) {
            if (itemDecorationProps.getHasLeftRightEdge()) {
                leftRightSpace = (itemDecorationProps.getLeftRightSpace() * i10) / i4;
                leftRightSpace2 = (itemDecorationProps.getLeftRightSpace() * ((i + (i2 - 1)) + 1)) / i4;
            } else {
                leftRightSpace = (itemDecorationProps.getLeftRightSpace() * i) / i4;
                leftRightSpace2 = (itemDecorationProps.getLeftRightSpace() * ((i4 - ((i + i2) - 1)) - 1)) / i4;
            }
            i5 = (z && itemDecorationProps.getHasTopBottomEdge()) ? itemDecorationProps.getTopBottomSpace() : 0;
            if (z2) {
                if (itemDecorationProps.getHasTopBottomEdge()) {
                    i6 = itemDecorationProps.getTopBottomSpace();
                }
            } else {
                i6 = itemDecorationProps.getTopBottomSpace();
            }
            rect = outRect;
            int i12 = i6;
            i6 = leftRightSpace2;
            topBottomSpace2 = i12;
        } else {
            if (itemDecorationProps.getHasTopBottomEdge()) {
                topBottomSpace = (itemDecorationProps.getTopBottomSpace() * i10) / i4;
                topBottomSpace2 = (itemDecorationProps.getTopBottomSpace() * ((i + (i2 - 1)) + 1)) / i4;
            } else {
                topBottomSpace = (itemDecorationProps.getTopBottomSpace() * i) / i4;
                topBottomSpace2 = (itemDecorationProps.getTopBottomSpace() * ((i4 - ((i + i2) - 1)) - 1)) / i4;
            }
            i5 = topBottomSpace;
            leftRightSpace = (z && itemDecorationProps.getHasLeftRightEdge()) ? itemDecorationProps.getLeftRightSpace() : 0;
            if (z2) {
                if (itemDecorationProps.getHasLeftRightEdge()) {
                    i6 = itemDecorationProps.getLeftRightSpace();
                }
            } else {
                i6 = itemDecorationProps.getLeftRightSpace();
            }
            rect = outRect;
        }
        rect.set(leftRightSpace, i5, i6, topBottomSpace2);
    }

    /* loaded from: classes.dex */
    public static class ItemDecorationProps {
        private boolean mHasLeftRightEdge;
        private boolean mHasTopBottomEdge;
        private int mLeftRightSpace;
        private int mTopBottomSpace;

        ItemDecorationProps() {
        }

        public ItemDecorationProps(int topBottomSpace, int leftRightSpace, boolean hasTopBottomEdge, boolean hasLeftRightEdge) {
            this.mLeftRightSpace = leftRightSpace;
            this.mTopBottomSpace = topBottomSpace;
            this.mHasTopBottomEdge = hasTopBottomEdge;
            this.mHasLeftRightEdge = hasLeftRightEdge;
        }

        int getTopBottomSpace() {
            return this.mTopBottomSpace;
        }

        int getLeftRightSpace() {
            return this.mLeftRightSpace;
        }

        boolean getHasTopBottomEdge() {
            return this.mHasTopBottomEdge;
        }

        boolean getHasLeftRightEdge() {
            return this.mHasLeftRightEdge;
        }

        void setLeftRightSpace(int leftRightSpace) {
            this.mLeftRightSpace = leftRightSpace;
        }

        void setTopBottomSpace(int topBottomSpace) {
            this.mTopBottomSpace = topBottomSpace;
        }

        void setHasLeftRightEdge(boolean hasLeftRightEdge) {
            this.mHasLeftRightEdge = hasLeftRightEdge;
        }

        void setHasTopBottomEdge(boolean hasTopBottomEdge) {
            this.mHasTopBottomEdge = hasTopBottomEdge;
        }
    }

    /* loaded from: classes.dex */
    public static class ItemDecorationBuilder {
        private SparseArray<ItemDecorationProps> mPropMap = new SparseArray<>();

        SparseArray<ItemDecorationProps> getPropMap() {
            return this.mPropMap;
        }

        public ItemType type(int type) {
            return new ItemType(type, this);
        }

        public SCommonItemDecoration build() {
            return new SCommonItemDecoration(this.mPropMap);
        }
    }

    /* loaded from: classes.dex */
    public static class ItemType {
        private ItemDecorationBuilder mBuilder;
        private ItemDecorationProps mProps = new ItemDecorationProps();
        private int mType;

        ItemType(int type, ItemDecorationBuilder builder) {
            this.mType = type;
            this.mBuilder = builder;
        }

        public ItemType prop(int leftRightSpace, int topBottomSpace, boolean hasLeftRightEdge, boolean hasTopBottomEdge) {
            this.mProps.setLeftRightSpace(leftRightSpace);
            this.mProps.setTopBottomSpace(topBottomSpace);
            this.mProps.setHasLeftRightEdge(hasLeftRightEdge);
            this.mProps.setHasTopBottomEdge(hasTopBottomEdge);
            return this;
        }

        public ItemType leftRightSpace(int space) {
            this.mProps.setLeftRightSpace(space);
            return this;
        }

        public ItemType topBottomSpace(int space) {
            this.mProps.setTopBottomSpace(space);
            return this;
        }

        public ItemType hasLeftRightEdge(boolean hasEdge) {
            this.mProps.setHasLeftRightEdge(hasEdge);
            return this;
        }

        public ItemType hasTopBottomEdge(boolean hasEdge) {
            this.mProps.setHasTopBottomEdge(hasEdge);
            return this;
        }

        public ItemDecorationBuilder buildType() {
            this.mBuilder.getPropMap().put(this.mType, this.mProps);
            return this.mBuilder;
        }
    }
}
