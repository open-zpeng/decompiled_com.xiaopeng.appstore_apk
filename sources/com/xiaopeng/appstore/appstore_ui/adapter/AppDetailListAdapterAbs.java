package com.xiaopeng.appstore.appstore_ui.adapter;

import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import com.xiaopeng.appstore.appstore_biz.model2.AppDetailModel;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundListAdapter;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
/* loaded from: classes2.dex */
public class AppDetailListAdapterAbs extends BaseBoundListAdapter<AppDetailModel> {
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseBoundViewHolder<AppDetailModel, ?> onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    protected AppDetailListAdapterAbs(DiffUtil.ItemCallback<AppDetailModel> diffCallback) {
        super(new DiffUtil.ItemCallback<AppDetailModel>() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.AppDetailListAdapterAbs.1
            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public boolean areItemsTheSame(AppDetailModel oldItem, AppDetailModel newItem) {
                return oldItem.getPackageName().equals(newItem.getPackageName());
            }

            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public boolean areContentsTheSame(AppDetailModel oldItem, AppDetailModel newItem) {
                return AppDetailModel.appEquals(oldItem, newItem);
            }
        });
    }
}
