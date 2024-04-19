package com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule;

import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvAppModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.AdvItemListAdapterAbs;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
@Deprecated
/* loaded from: classes2.dex */
public class ItemPackageHorizontalViewHolder extends ItemPackageBaseViewHolder {
    public ItemPackageHorizontalViewHolder(ViewGroup parent) {
        super(parent);
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.ItemPackageBaseViewHolder
    protected RecyclerView.Adapter<BaseBoundViewHolder<AdvAppModel, ?>> initChildAdapter() {
        return new HorizontalItemListBoundAdapter();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.ItemPackageBaseViewHolder
    protected RecyclerView.LayoutManager initChildLayoutManager() {
        return new LinearLayoutManager(this.itemView.getContext(), 0, false);
    }

    /* loaded from: classes2.dex */
    public static class HorizontalItemListBoundAdapter extends AdvItemListAdapterAbs {
        @Override // com.xiaopeng.appstore.appstore_ui.adapter.AdvItemListAdapterAbs
        protected int getBindingLayoutId() {
            return R.layout.item_horizontal_app;
        }
    }
}
