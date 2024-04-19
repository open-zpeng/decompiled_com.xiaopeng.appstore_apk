package com.xiaopeng.appstore.common_ui.common.adapter;

import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public abstract class BaseBoundListAdapter<T> extends ListAdapter<T, BaseBoundViewHolder<T, ?>> {
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder((BaseBoundViewHolder) ((BaseBoundViewHolder) holder), position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        onBindViewHolder((BaseBoundViewHolder) ((BaseBoundViewHolder) holder), position, (List<Object>) payloads);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        onViewAttachedToWindow((BaseBoundViewHolder) ((BaseBoundViewHolder) holder));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        onViewDetachedFromWindow((BaseBoundViewHolder) ((BaseBoundViewHolder) holder));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onViewRecycled(RecyclerView.ViewHolder holder) {
        onViewRecycled((BaseBoundViewHolder) ((BaseBoundViewHolder) holder));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public BaseBoundListAdapter(DiffUtil.ItemCallback<T> diffCallback) {
        super(new AsyncDifferConfig.Builder(diffCallback).setBackgroundThreadExecutor(AdapterUtils.getDiffThreadPool()).build());
    }

    public void onBindViewHolder(BaseBoundViewHolder<T, ?> holder, int position) {
        T item = getItem(holder.getAdapterPosition());
        if (item != null) {
            holder.setData(item);
        }
    }

    public void onBindViewHolder(BaseBoundViewHolder<T, ?> holder, int position, List<Object> payloads) {
        T item = getItem(holder.getAdapterPosition());
        if (item != null) {
            if (!payloads.isEmpty()) {
                holder.setData(item, payloads);
            } else {
                onBindViewHolder((BaseBoundViewHolder) holder, position);
            }
        }
    }

    public void onViewRecycled(BaseBoundViewHolder<T, ?> holder) {
        super.onViewRecycled((BaseBoundListAdapter<T>) holder);
        holder.onViewRecycled();
    }

    public void onViewAttachedToWindow(BaseBoundViewHolder<T, ?> holder) {
        super.onViewAttachedToWindow((BaseBoundListAdapter<T>) holder);
        holder.markAttached();
    }

    public void onViewDetachedFromWindow(BaseBoundViewHolder<T, ?> holder) {
        super.onViewDetachedFromWindow((BaseBoundListAdapter<T>) holder);
        holder.markDetached();
    }

    @Override // androidx.recyclerview.widget.ListAdapter
    public void submitList(List<T> list) {
        super.submitList(list != null ? new ArrayList(list) : null);
    }
}
