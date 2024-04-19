package com.xiaopeng.appstore.appstore_ui.adapter;

import android.view.ViewGroup;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.SceneHeaderViewHolder;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
/* loaded from: classes2.dex */
public class ScenePageAdapter extends BaseAdvListAdapter {
    @Override // com.xiaopeng.appstore.appstore_ui.adapter.BaseAdvListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseBoundViewHolder<? extends AdvModel, ?> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 4) {
            return new SceneHeaderViewHolder(parent);
        }
        return super.onCreateViewHolder(parent, viewType);
    }
}
