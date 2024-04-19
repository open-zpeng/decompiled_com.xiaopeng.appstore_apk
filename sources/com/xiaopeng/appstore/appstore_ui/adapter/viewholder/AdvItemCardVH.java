package com.xiaopeng.appstore.appstore_ui.adapter.viewholder;

import android.view.ViewGroup;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvAppModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemCardAppBinding;
/* loaded from: classes2.dex */
public class AdvItemCardVH extends AdvItemViewHolder<ItemCardAppBinding> {
    private static final String TAG = "AdvItemCardVH";

    public AdvItemCardVH(ViewGroup parent, AdvItemViewHolder.OnItemEventCallback itemEventCallback) {
        super(parent, R.layout.item_card_app, itemEventCallback);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder, com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void setData(AdvAppModel model) {
        super.setData(model);
        Logger.t(TAG).i("onBind:" + getBindingAdapterPosition() + ", " + hashCode(), new Object[0]);
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder, com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void onViewRecycled() {
        super.onViewRecycled();
        Logger.t(TAG).i("Recycled:" + getBindingAdapterPosition() + ", " + hashCode(), new Object[0]);
    }
}
