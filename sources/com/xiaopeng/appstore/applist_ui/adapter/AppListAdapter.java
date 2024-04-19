package com.xiaopeng.appstore.applist_ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.applist_biz.model.AppGroupItemModel;
import com.xiaopeng.appstore.applist_biz.model.NormalAppItem;
import com.xiaopeng.appstore.applist_ui.adapter.IconTouchHelperCallback;
import com.xiaopeng.appstore.common_ui.common.EnhancedListAdapter;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
import java.util.List;
/* loaded from: classes2.dex */
public class AppListAdapter extends EnhancedListAdapter<AppGroupItemModel, BaseAppListViewHolder<?>> implements IconTouchHelperCallback.IconDragCallback {
    public static final int CHANGE_TYPE_LOADING = 2;
    public static final int CHANGE_TYPE_MODE = 3;
    public static final int PAYLOAD_TYPE_RELEASE = 9;
    public static final int PAYLOAD_TYPE_RESTORE = 8;
    public static final int REFRESH_APP_TYPE = 7;
    public static final int REFRESH_DRAGGING_GROUP = 6;
    public static final int REFRESH_THEME = 4;
    public static final int REFRESH_UNREAD_COUNT = 5;
    private static final String TAG = "AppListAdapter";
    private final AppGroupHelper mAppGroupHelper;
    protected final int mCountsPerRow;
    private int mDraggingGroup;
    private int mDraggingPosition;
    private boolean mIsEditMode;
    private final ItemTouchHelper mItemTouchHelper;
    private final GridLayoutManager mLayoutManager;
    private OnAppInteractCallback mOnAppInteractCallback;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        onBindViewHolder((BaseAppListViewHolder) holder, position, (List<Object>) payloads);
    }

    public AppListAdapter(Context context, AppGroupHelper appGroupHelper, ListUpdateCallback listUpdateCallback) {
        super(new AppItemDiffCallback(), listUpdateCallback);
        this.mIsEditMode = false;
        this.mDraggingGroup = -1;
        this.mDraggingPosition = -1;
        this.mAppGroupHelper = appGroupHelper;
        int countsPerRow = appGroupHelper.getStrategy().getCountsPerRow();
        this.mCountsPerRow = countsPerRow;
        this.mLayoutManager = initLayoutManager(context, countsPerRow);
        this.mItemTouchHelper = new ItemTouchHelper(new IconTouchHelperCallback(appGroupHelper, this));
    }

    protected GridLayoutManager initLayoutManager(Context context, int countsPerRow) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, countsPerRow);
        gridLayoutManager.setSpanSizeLookup(new GridSpanSizer());
        return gridLayoutManager;
    }

    public GridLayoutManager getLayoutManager() {
        return this.mLayoutManager;
    }

    public void setOnAppInteractCallback(OnAppInteractCallback callback) {
        this.mOnAppInteractCallback = callback;
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [T, java.lang.Boolean] */
    public void setIsEditMode(boolean isEditMode) {
        this.mIsEditMode = isEditMode;
        PayloadData payloadData = new PayloadData();
        payloadData.type = 3;
        payloadData.data = Boolean.valueOf(this.mIsEditMode);
        notifyItemRangeChanged(0, getItemCount(), payloadData);
        Logger.t(TAG).i("setIsEditMode notify, isEditMode=" + isEditMode, new Object[0]);
    }

    public boolean isEditMode() {
        return this.mIsEditMode;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseAppListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 100) {
            AppIconViewHolder appIconViewHolder = new AppIconViewHolder(parent);
            appIconViewHolder.setOnAppInteractCallback(this.mOnAppInteractCallback);
            return appIconViewHolder;
        } else if (viewType == 101) {
            return new GroupDividerHolder(parent);
        } else {
            return new EmptyViewHolder(new View(parent.getContext()));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseAppListViewHolder<?> holder, int position) {
        AppGroupItemModel item = getItem(position);
        if (holder instanceof AppIconViewHolder) {
            AppIconViewHolder appIconViewHolder = (AppIconViewHolder) holder;
            appIconViewHolder.setData(item);
            appIconViewHolder.setDraggingGroup(this.mDraggingGroup);
            appIconViewHolder.setInEdit(this.mIsEditMode);
            appIconViewHolder.setOnAppInteractCallback(this.mOnAppInteractCallback);
        } else if (holder instanceof GroupDividerHolder) {
            ((GroupDividerHolder) holder).setTitle((CharSequence) item.data);
        }
    }

    public void onBindViewHolder(BaseAppListViewHolder<?> holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
            return;
        }
        for (Object obj : payloads) {
            if (obj instanceof PayloadData) {
                holder.setData(getItem(position), (PayloadData) obj);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(BaseAppListViewHolder<?> holder) {
        super.onViewAttachedToWindow((AppListAdapter) holder);
        holder.attach();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(BaseAppListViewHolder<?> holder) {
        super.onViewDetachedFromWindow((AppListAdapter) holder);
        holder.detach();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(BaseAppListViewHolder<?> holder) {
        super.onViewRecycled((AppListAdapter) holder);
        holder.onViewRecycled();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        return getItem(position).type;
    }

    public int getIndex(String packageName) {
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            AppGroupItemModel item = getItem(i);
            if (item.type == 100 && (item.data instanceof NormalAppItem) && ((NormalAppItem) item.data).packageName.equals(packageName)) {
                return i;
            }
        }
        return -1;
    }

    public boolean tryStartDrag(RecyclerView.ViewHolder viewHolder) {
        Logger.t(TAG).i("tryStartDrag, isEdit:" + isEditMode() + ", vh:" + viewHolder, new Object[0]);
        if (isEditMode() && (viewHolder instanceof AppIconViewHolder) && !((AppIconViewHolder) viewHolder).isDeleting()) {
            this.mItemTouchHelper.startDrag(viewHolder);
            viewOnDragging(viewHolder);
            return true;
        }
        return false;
    }

    private void viewOnDragging(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof AppIconViewHolder) {
            ((AppIconViewHolder) viewHolder).viewOnDragging();
        }
    }

    public void viewOnDrop(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof AppIconViewHolder) {
            ((AppIconViewHolder) viewHolder).viewOnDrop();
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [T, java.lang.Integer] */
    private void refreshDraggingGroup(int group) {
        this.mDraggingGroup = group;
        PayloadData payloadData = new PayloadData();
        payloadData.type = 6;
        payloadData.data = Integer.valueOf(group);
        notifyItemRangeChanged(0, getItemCount(), payloadData);
        Logger.t(TAG).i("refreshDraggingGroup notify, group=" + group, new Object[0]);
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.IconTouchHelperCallback.IconDragCallback
    public void onIconDragChanged(RecyclerView.ViewHolder viewHolder, int pos) {
        this.mDraggingPosition = pos;
        if (pos > -1) {
            refreshDraggingGroup(this.mAppGroupHelper.getGroupId(pos));
        }
        OnAppInteractCallback onAppInteractCallback = this.mOnAppInteractCallback;
        if (onAppInteractCallback != null) {
            onAppInteractCallback.onIconDragChanged(viewHolder, pos);
        }
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.IconTouchHelperCallback.IconDragCallback
    public void onIconMove(RecyclerView.ViewHolder viewHolder, int fromPos, int toPos) {
        notifyItemMoved(fromPos, toPos);
        Logger.t(TAG).i("onIconMove notify, from=" + fromPos + " to=" + toPos, new Object[0]);
        OnAppInteractCallback onAppInteractCallback = this.mOnAppInteractCallback;
        if (onAppInteractCallback != null) {
            onAppInteractCallback.onIconMove(viewHolder, fromPos, toPos);
        }
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.IconTouchHelperCallback.IconDragCallback
    public void onIconDrop(RecyclerView.ViewHolder viewHolder, int fromPos, int toPos) {
        viewOnDrop(viewHolder);
        if (this.mDraggingPosition == -1) {
            refreshDraggingGroup(-1);
        }
        OnAppInteractCallback onAppInteractCallback = this.mOnAppInteractCallback;
        if (onAppInteractCallback != null) {
            onAppInteractCallback.onIconDrop(viewHolder, fromPos, toPos);
        }
    }

    /* loaded from: classes2.dex */
    public class GridSpanSizer extends GridLayoutManager.SpanSizeLookup {
        public GridSpanSizer() {
            setSpanIndexCacheEnabled(true);
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanSize(int position) {
            if (AppListAdapter.this.getItemViewType(position) == 101) {
                return AppListAdapter.this.mCountsPerRow;
            }
            return 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes2.dex */
    public static class EmptyViewHolder extends BaseAppListViewHolder<Object> {
        @Override // com.xiaopeng.appstore.applist_ui.adapter.BaseAppListViewHolder
        public void onViewRecycled() {
        }

        @Override // com.xiaopeng.appstore.applist_ui.adapter.BaseAppListViewHolder
        public void setData(Object data) {
        }

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
