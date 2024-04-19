package com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule;

import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.AdvItemListAdapterAbs;
@Deprecated
/* loaded from: classes2.dex */
public class ItemPackageNormalCardVH extends ItemPackageBaseViewHolder {
    public ItemPackageNormalCardVH(ViewGroup parent) {
        super(parent);
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.ItemPackageBaseViewHolder
    protected RecyclerView.Adapter initChildAdapter() {
        return new NormalCardListBoundAdapter();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.ItemPackageBaseViewHolder
    protected RecyclerView.LayoutManager initChildLayoutManager() {
        return new LinearLayoutManager(this.itemView.getContext(), 0, false);
    }

    /* loaded from: classes2.dex */
    public static class NormalCardListBoundAdapter extends AdvItemListAdapterAbs {
        @Override // com.xiaopeng.appstore.appstore_ui.adapter.AdvItemListAdapterAbs
        protected int getBindingLayoutId() {
            return R.layout.item_normal_card_app;
        }
    }
}
