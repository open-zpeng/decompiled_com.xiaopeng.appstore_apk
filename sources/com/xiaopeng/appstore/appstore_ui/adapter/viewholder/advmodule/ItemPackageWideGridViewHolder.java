package com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule;

import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvAppModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.AdvItemListAdapterAbs;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemPackageBindingBinding;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
import com.xiaopeng.appstore.common_ui.common.base.SCommonItemDecoration;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
@Deprecated
/* loaded from: classes2.dex */
public class ItemPackageWideGridViewHolder extends ItemPackageBaseViewHolder {
    public ItemPackageWideGridViewHolder(ViewGroup parent) {
        super(parent);
        ((ItemPackageBindingBinding) this.mBinding).recyclerView.addItemDecoration(SCommonItemDecoration.builder().type(0).prop(0, (int) ResUtils.getDimen(R.dimen.item_grid_app_bottom_space), false, false).buildType().build());
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.ItemPackageBaseViewHolder
    protected RecyclerView.Adapter<BaseBoundViewHolder<AdvAppModel, ?>> initChildAdapter() {
        return new GridWideItemListBoundAdapter();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.ItemPackageBaseViewHolder
    protected RecyclerView.LayoutManager initChildLayoutManager() {
        return new GridLayoutManager(this.itemView.getContext(), 2);
    }

    /* loaded from: classes2.dex */
    public static class GridWideItemListBoundAdapter extends AdvItemListAdapterAbs {
        @Override // com.xiaopeng.appstore.appstore_ui.adapter.AdvItemListAdapterAbs
        protected int getBindingLayoutId() {
            return R.layout.item_wide_app;
        }
    }
}
