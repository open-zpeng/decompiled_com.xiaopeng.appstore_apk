package com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule;

import android.view.ViewGroup;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemTitleLayoutBinding;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
/* loaded from: classes2.dex */
public class ItemPackageTitleVH extends BaseBoundViewHolder<AdvModel, ItemTitleLayoutBinding> {
    public ItemPackageTitleVH(ViewGroup parent) {
        super(parent, R.layout.item_title_layout);
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void setData(AdvModel data) {
        getBinding().setTitle(data.getTitle());
        getBinding().executePendingBindings();
    }
}
