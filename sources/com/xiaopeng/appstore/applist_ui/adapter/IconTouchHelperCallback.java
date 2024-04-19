package com.xiaopeng.appstore.applist_ui.adapter;

import android.graphics.Canvas;
import android.view.View;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.orhanobut.logger.Logger;
/* loaded from: classes2.dex */
public class IconTouchHelperCallback extends ItemTouchHelper.SimpleCallback {
    private static final String TAG = "ITHC";
    private final AppGroupHelper mAppGroupHelper;
    private final IconDragCallback mDragCallback;
    private int mDragFrom;
    private int mDragTo;

    /* loaded from: classes2.dex */
    public interface IconDragCallback {
        void onIconDragChanged(RecyclerView.ViewHolder viewHolder, int pos);

        void onIconDrop(RecyclerView.ViewHolder viewHolder, int fromPos, int toPos);

        void onIconMove(RecyclerView.ViewHolder viewHolder, int fromPos, int toPos);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int viewSize, int viewSizeOutOfBounds, int totalSize, long msSinceStartScroll) {
        return 0;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    public IconTouchHelperCallback(AppGroupHelper helper, IconDragCallback callback) {
        super(51, 0);
        this.mDragFrom = -1;
        this.mDragTo = -1;
        this.mAppGroupHelper = helper;
        this.mDragCallback = callback;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof AppIconViewHolder) {
            return super.getMovementFlags(recyclerView, viewHolder);
        }
        return makeMovementFlags(0, 0);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View view = viewHolder.itemView;
        float f = -view.getLeft();
        float width = recyclerView.getWidth() - view.getRight();
        int height = view.getHeight();
        float f2 = (-view.getTop()) - height;
        float height2 = (recyclerView.getHeight() - view.getBottom()) + height;
        float f3 = dX < f ? f : dX > width ? width : dX;
        if (dY >= f2) {
            f2 = dY > height2 ? height2 : dY;
        }
        super.onChildDraw(c, recyclerView, viewHolder, f3, f2, actionState, isCurrentlyActive);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        IconDragCallback iconDragCallback;
        super.onSelectedChanged(viewHolder, actionState);
        int adapterPosition = viewHolder != null ? viewHolder.getAdapterPosition() : -1;
        Logger.t(TAG).v("onSelectedChanged pos=" + adapterPosition + " actionState=" + actionState, new Object[0]);
        if ((actionState == 2 || actionState == 0) && (iconDragCallback = this.mDragCallback) != null) {
            iconDragCallback.onIconDragChanged(viewHolder, adapterPosition);
        }
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int adapterPosition = viewHolder.getAdapterPosition();
        int adapterPosition2 = target.getAdapterPosition();
        if (this.mDragFrom == -1) {
            this.mDragFrom = adapterPosition;
        }
        this.mDragTo = adapterPosition2;
        IconDragCallback iconDragCallback = this.mDragCallback;
        if (iconDragCallback != null) {
            iconDragCallback.onIconMove(viewHolder, adapterPosition, adapterPosition2);
            return true;
        }
        return true;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        IconDragCallback iconDragCallback = this.mDragCallback;
        if (iconDragCallback != null) {
            iconDragCallback.onIconDrop(viewHolder, this.mDragFrom, this.mDragTo);
        }
        this.mDragTo = -1;
        this.mDragFrom = -1;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
        if (this.mAppGroupHelper != null) {
            int adapterPosition = current.getAdapterPosition();
            int adapterPosition2 = target.getAdapterPosition();
            if (adapterPosition < 0 || adapterPosition2 < 0 || !this.mAppGroupHelper.isContentSameGroup(adapterPosition, adapterPosition2)) {
                return false;
            }
        }
        return super.canDropOver(recyclerView, current, target);
    }
}
