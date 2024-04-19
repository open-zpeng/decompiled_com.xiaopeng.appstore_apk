package com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule;

import android.view.ViewGroup;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemActivityHeadBinding;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
import java.util.List;
/* loaded from: classes2.dex */
public class SceneHeaderViewHolder extends BaseBoundViewHolder<AdvModel, ItemActivityHeadBinding> {
    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public /* bridge */ /* synthetic */ void setData(AdvModel data, List payloads) {
        setData2(data, (List<Object>) payloads);
    }

    public SceneHeaderViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_activity_head);
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void setData(AdvModel data) {
        getBinding().setModel(data);
        getBinding().setDividerColor(Integer.valueOf(R.color.divider_color));
    }

    /* renamed from: setData  reason: avoid collision after fix types in other method */
    public void setData2(AdvModel data, List<Object> payloads) {
        super.setData((SceneHeaderViewHolder) data, payloads);
        if (payloads.isEmpty()) {
            return;
        }
        Object obj = payloads.get(0);
        if ((obj instanceof PayloadData) && ((PayloadData) obj).type == 1003) {
            onThemeChanged();
        }
    }

    private void onThemeChanged() {
        getBinding().setDividerColor(Integer.valueOf(R.color.divider_color));
        getBinding().executePendingBindings();
    }
}
