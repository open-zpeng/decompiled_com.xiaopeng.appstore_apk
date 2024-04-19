package com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule;

import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.AdvItemListAdapterAbs;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
@Deprecated
/* loaded from: classes2.dex */
public class ItemPackageNarrowGridVH extends ItemPackageBaseViewHolder {
    public ItemPackageNarrowGridVH(ViewGroup parent) {
        super(parent);
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.ItemPackageBaseViewHolder
    protected RecyclerView.Adapter initChildAdapter() {
        return new NarrowGridListBoundAdapter();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.ItemPackageBaseViewHolder
    protected RecyclerView.LayoutManager initChildLayoutManager() {
        return new GridLayoutManager(this.itemView.getContext(), ResUtils.getInteger(R.integer.home_narrow_grid_column));
    }

    /* loaded from: classes2.dex */
    public static class NarrowGridListBoundAdapter extends AdvItemListAdapterAbs {
        @Override // com.xiaopeng.appstore.appstore_ui.adapter.AdvItemListAdapterAbs
        protected int getBindingLayoutId() {
            return R.layout.item_grid_narrow_app;
        }
    }
}
