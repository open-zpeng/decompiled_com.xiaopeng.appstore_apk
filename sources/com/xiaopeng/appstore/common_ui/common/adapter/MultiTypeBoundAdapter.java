package com.xiaopeng.appstore.common_ui.common.adapter;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/* loaded from: classes.dex */
public abstract class MultiTypeBoundAdapter extends ListAdapter<AdapterData<?>, BaseBoundViewHolder<?, ?>> {
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        onBindViewHolder((BaseBoundViewHolder) holder, position, (List<Object>) payloads);
    }

    public MultiTypeBoundAdapter() {
        super(new DiffUtil.ItemCallback<AdapterData<?>>() { // from class: com.xiaopeng.appstore.common_ui.common.adapter.MultiTypeBoundAdapter.1
            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public /* bridge */ /* synthetic */ boolean areContentsTheSame(AdapterData<?> oldItem, AdapterData<?> newItem) {
                return areContentsTheSame2((AdapterData) oldItem, (AdapterData) newItem);
            }

            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public /* bridge */ /* synthetic */ boolean areItemsTheSame(AdapterData<?> oldItem, AdapterData<?> newItem) {
                return areItemsTheSame2((AdapterData) oldItem, (AdapterData) newItem);
            }

            /* renamed from: areItemsTheSame  reason: avoid collision after fix types in other method */
            public boolean areItemsTheSame2(AdapterData oldItem, AdapterData newItem) {
                return oldItem.type == newItem.type;
            }

            /* renamed from: areContentsTheSame  reason: avoid collision after fix types in other method */
            public boolean areContentsTheSame2(AdapterData oldItem, AdapterData newItem) {
                return MultiTypeBoundAdapter.dataEquals(oldItem, newItem);
            }
        });
    }

    public static boolean dataEquals(Object oldItem, Object newItem) {
        if (oldItem == newItem) {
            return true;
        }
        if (newItem == null || oldItem.getClass() != newItem.getClass()) {
            return false;
        }
        AdapterData adapterData = (AdapterData) oldItem;
        AdapterData adapterData2 = (AdapterData) newItem;
        return adapterData.type == adapterData2.type && Objects.equals(adapterData.data, adapterData2.data);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseBoundViewHolder<?, ?> holder, int position) {
        onBindViewHolderInternal(holder, position);
    }

    public void onBindViewHolder(BaseBoundViewHolder<?, ?> holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            onBindViewHolderInternal(holder, position, payloads);
        }
    }

    private <T> void onBindViewHolderInternal(BaseBoundViewHolder<T, ?> holder, int position) {
        AdapterData<?> item = getItem(position);
        if (item == null || item.data == null) {
            return;
        }
        holder.setData(item.data);
    }

    private <T> void onBindViewHolderInternal(BaseBoundViewHolder<T, ?> holder, int position, List<Object> payloads) {
        AdapterData<?> item = getItem(position);
        if (item == null || item.data == null) {
            return;
        }
        holder.setData(item.data, payloads);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        AdapterData<?> item = getItem(position);
        if (item != null) {
            return item.type;
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(BaseBoundViewHolder<?, ?> holder) {
        super.onViewRecycled((MultiTypeBoundAdapter) holder);
        holder.onViewRecycled();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(BaseBoundViewHolder<?, ?> holder) {
        super.onViewAttachedToWindow((MultiTypeBoundAdapter) holder);
        holder.markAttached();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(BaseBoundViewHolder<?, ?> holder) {
        super.onViewDetachedFromWindow((MultiTypeBoundAdapter) holder);
        holder.markDetached();
    }

    @Override // androidx.recyclerview.widget.ListAdapter
    public void submitList(List<AdapterData<?>> list) {
        super.submitList(list != null ? new ArrayList(list) : null);
    }
}
