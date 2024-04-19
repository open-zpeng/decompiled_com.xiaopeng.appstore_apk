package com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvListModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemPackageBindingBinding;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
import java.util.List;
/* loaded from: classes2.dex */
public abstract class ItemPackageBaseViewHolder extends BaseBoundViewHolder<AdvListModel, ItemPackageBindingBinding> {
    private final RecyclerView.Adapter mChildAdapter;
    private final RecyclerView.LayoutManager mChildLayoutManager;

    protected abstract RecyclerView.Adapter initChildAdapter();

    protected abstract RecyclerView.LayoutManager initChildLayoutManager();

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public /* bridge */ /* synthetic */ void setData(AdvListModel data, List payloads) {
        setData2(data, (List<Object>) payloads);
    }

    public ItemPackageBaseViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_package_binding);
        RecyclerView.Adapter initChildAdapter = initChildAdapter();
        this.mChildAdapter = initChildAdapter;
        ((ItemPackageBindingBinding) this.mBinding).recyclerView.setAdapter(initChildAdapter);
        RecyclerView.LayoutManager initChildLayoutManager = initChildLayoutManager();
        this.mChildLayoutManager = initChildLayoutManager;
        ((ItemPackageBindingBinding) this.mBinding).recyclerView.setLayoutManager(initChildLayoutManager);
    }

    public RecyclerView.Adapter getChildAdapter() {
        return this.mChildAdapter;
    }

    public RecyclerView.LayoutManager getChildLayoutManager() {
        return this.mChildLayoutManager;
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void setData(AdvListModel data) {
        if (data != null) {
            ((ItemPackageBindingBinding) this.mBinding).setModel(data);
            ((ItemPackageBindingBinding) this.mBinding).executePendingBindings();
        }
    }

    /* renamed from: setData  reason: avoid collision after fix types in other method */
    public void setData2(AdvListModel data, List<Object> payloads) {
        if (payloads.isEmpty()) {
            return;
        }
        Object obj = payloads.get(0);
        if (obj instanceof PayloadData) {
            RecyclerView.Adapter adapter = this.mChildAdapter;
            adapter.notifyItemRangeChanged(0, adapter.getItemCount(), obj);
        }
    }
}
