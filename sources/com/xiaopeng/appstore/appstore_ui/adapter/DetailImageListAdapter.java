package com.xiaopeng.appstore.appstore_ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.binding.BindableAdapter;
import com.xiaopeng.appstore.appstore_ui.adapter.binding.DataBoundListAdapter;
import com.xiaopeng.appstore.appstore_ui.adapter.binding.DataBoundViewHolder;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemDetailImageBinding;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import java.util.List;
/* loaded from: classes2.dex */
public class DetailImageListAdapter extends DataBoundListAdapter<String, ItemDetailImageBinding> implements BindableAdapter<String> {
    public DetailImageListAdapter() {
        super(AppExecutors.get(), new DiffUtil.ItemCallback<String>() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.DetailImageListAdapter.1
            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public boolean areItemsTheSame(String oldItem, String newItem) {
                return oldItem.equals(newItem);
            }

            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public boolean areContentsTheSame(String oldItem, String newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.appstore_ui.adapter.binding.DataBoundListAdapter
    public ItemDetailImageBinding createBinding(ViewGroup parent) {
        return (ItemDetailImageBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_detail_image, parent, false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.appstore_ui.adapter.binding.DataBoundListAdapter
    public void bind(ItemDetailImageBinding binding, String item, DataBoundViewHolder<? extends ItemDetailImageBinding> holder) {
        binding.setUrl(item);
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.binding.BindableAdapter
    public void setList(List<String> list) {
        submitList(list);
    }
}
