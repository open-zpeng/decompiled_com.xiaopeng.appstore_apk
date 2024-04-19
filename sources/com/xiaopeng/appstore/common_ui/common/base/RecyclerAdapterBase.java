package com.xiaopeng.appstore.common_ui.common.base;

import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.common_ui.common.adapter.AdapterData;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public abstract class RecyclerAdapterBase extends RecyclerView.Adapter<ViewHolderBase<?>> {
    public static final int TYPE_NONE = Integer.MIN_VALUE;
    protected List<AdapterData<?>> mDataList = new ArrayList();

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(ViewHolderBase<?> holder, int position, List payloads) {
        onBindViewHolder2(holder, position, (List<Object>) payloads);
    }

    public void clearData() {
        this.mDataList.clear();
    }

    public <T> void addData(T data) {
        addData(0, data);
    }

    public <T> int addData(int type, T data) {
        AdapterData<?> adapterData = new AdapterData<>();
        adapterData.type = type;
        adapterData.data = data;
        this.mDataList.add(adapterData);
        return this.mDataList.size() - 1;
    }

    public <T> void addData(int index, int type, T data) {
        AdapterData<?> adapterData = new AdapterData<>();
        adapterData.type = type;
        adapterData.data = data;
        this.mDataList.add(index, adapterData);
    }

    public void addAll(List<AdapterData<?>> dataList) {
        this.mDataList.addAll(dataList);
    }

    public void removeData(int position) {
        if (this.mDataList.size() > position) {
            this.mDataList.remove(position);
        }
    }

    public void removeDataRange(int startPosition, int itemCount) {
        if (startPosition <= -1 || this.mDataList.size() <= startPosition) {
            return;
        }
        int min = Math.min(itemCount, getItemCount() - startPosition);
        for (int i = 0; i < min; i++) {
            removeData(startPosition);
        }
    }

    public <T> void updateData(int type, T data) {
        for (AdapterData<?> adapterData : this.mDataList) {
            if (adapterData.type == type) {
                adapterData.data = data;
                notifyItemChanged(this.mDataList.indexOf(adapterData));
                return;
            }
        }
        addData(type, data);
        notifyItemInserted(this.mDataList.size());
    }

    public int containsType(int itemType) {
        List<AdapterData<?>> list = this.mDataList;
        if (list == null || list.isEmpty()) {
            return -1;
        }
        for (AdapterData<?> adapterData : this.mDataList) {
            if (itemType == adapterData.type) {
                return this.mDataList.indexOf(adapterData);
            }
        }
        return -1;
    }

    public AdapterData<?> getItemAdapterData(int position) {
        List<AdapterData<?>> list;
        if (position < 0 || (list = this.mDataList) == null || list.isEmpty()) {
            return null;
        }
        return this.mDataList.get(position);
    }

    public Object getItemData(int position) {
        List<AdapterData<?>> list;
        if (position < 0 || (list = this.mDataList) == null || list.isEmpty() || this.mDataList.size() <= position) {
            return null;
        }
        return this.mDataList.get(position).data;
    }

    public <T> T getItemData(int position, Class<T> cls) throws IllegalArgumentException {
        List<AdapterData<?>> list;
        if (position < 0 || (list = this.mDataList) == null || list.isEmpty() || this.mDataList.size() <= position) {
            return null;
        }
        AdapterData<?> adapterData = this.mDataList.get(position);
        if (cls.isInstance(adapterData.data)) {
            return cls.cast(adapterData.data);
        }
        throw new IllegalArgumentException("Class of the data at [position] is not compatible with [cls]");
    }

    public Object getItemData(int position, int type) {
        List<AdapterData<?>> list;
        if (position < 0 || (list = this.mDataList) == null || list.isEmpty() || this.mDataList.size() <= position) {
            return null;
        }
        AdapterData<?> adapterData = this.mDataList.get(position);
        if (adapterData.type == type) {
            return adapterData.data;
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolderBase<?> holder, int position) {
        onBindViewHolderInternal(holder, position);
    }

    /* renamed from: onBindViewHolder  reason: avoid collision after fix types in other method */
    public void onBindViewHolder2(ViewHolderBase<?> holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            onBindViewHolderInternal(holder, position, payloads);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> void onBindViewHolderInternal(ViewHolderBase<T> holder, int position) {
        holder.setData(getItemData(position));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> void onBindViewHolderInternal(ViewHolderBase<T> holder, int position, List<Object> payloads) {
        holder.setDataWithPayloads(getItemData(position), payloads);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(ViewHolderBase<?> holder) {
        super.onViewAttachedToWindow((RecyclerAdapterBase) holder);
        holder.onAttach();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(ViewHolderBase<?> holder) {
        super.onViewDetachedFromWindow((RecyclerAdapterBase) holder);
        holder.onDetach();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<AdapterData<?>> list = this.mDataList;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        List<AdapterData<?>> list = this.mDataList;
        if (list == null || list.size() <= position || position <= -1) {
            return Integer.MIN_VALUE;
        }
        return this.mDataList.get(position).type;
    }

    public void notifyItemRangeChanged(int positionStart, int itemCount, PayloadData<?> payload) {
        super.notifyItemRangeChanged(positionStart, itemCount, (Object) payload);
    }

    public void notifyAllItemChanged(int firstVisiblePosition, int lastVisiblePosition, PayloadData<?> payload) {
        notifyItemRangeChanged(0, getItemCount(), firstVisiblePosition, lastVisiblePosition, payload);
    }

    public void notifyItemRangeChanged(int positionStart, int itemCount, int firstVisiblePosition, int lastVisiblePosition, PayloadData<?> payload) {
        if (firstVisiblePosition == -1 || lastVisiblePosition == -1) {
            notifyItemRangeChanged(positionStart, itemCount);
        }
        if (positionStart < firstVisiblePosition) {
            notifyItemRangeChanged(positionStart, firstVisiblePosition - positionStart);
        }
        int i = (itemCount + positionStart) - 1;
        if (i > lastVisiblePosition) {
            notifyItemRangeChanged(lastVisiblePosition + 1, i - lastVisiblePosition);
        }
        int max = Math.max(firstVisiblePosition, positionStart);
        notifyItemRangeChanged(max, (Math.min(lastVisiblePosition, i) - max) + 1, payload);
    }
}
