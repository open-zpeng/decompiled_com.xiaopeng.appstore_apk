package com.xiaopeng.appstore.appstore_ui.adapter;

import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvAppModel;
import com.xiaopeng.appstore.appstore_ui.adapter.binding.BindableAdapter;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemCardVH;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemGridVH;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemHorizontalVH;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemNarrowGridVH;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemNormalCardVH;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemWideGridVH;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundListAdapter;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
import java.util.List;
/* loaded from: classes2.dex */
public abstract class AdvItemListAdapter extends BaseBoundListAdapter<AdvAppModel> implements BindableAdapter<AdvAppModel> {
    private static final String TAG = "AdvItemListAdapter";
    private final AdvItemViewHolder.OnItemEventCallback mOnItemEventCallback;

    public AdvItemListAdapter(AdvItemViewHolder.OnItemEventCallback itemOnEventCallback) {
        super(new DiffUtil.ItemCallback<AdvAppModel>() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.AdvItemListAdapter.1
            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public boolean areItemsTheSame(AdvAppModel oldItem, AdvAppModel newItem) {
                return oldItem.getPackageName().equals(newItem.getPackageName());
            }

            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public boolean areContentsTheSame(AdvAppModel oldItem, AdvAppModel newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.mOnItemEventCallback = itemOnEventCallback;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseBoundViewHolder<AdvAppModel, ?> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 5) {
            return new AdvItemCardVH(parent, this.mOnItemEventCallback);
        }
        if (viewType == 6) {
            return new AdvItemGridVH(parent, this.mOnItemEventCallback);
        }
        if (viewType == 8) {
            return new AdvItemWideGridVH(parent, this.mOnItemEventCallback);
        }
        if (viewType == 10) {
            return new AdvItemHorizontalVH(parent, this.mOnItemEventCallback);
        }
        if (viewType == 11) {
            return new AdvItemNormalCardVH(parent, this.mOnItemEventCallback);
        }
        if (viewType == 12) {
            return new AdvItemNarrowGridVH(parent, this.mOnItemEventCallback);
        }
        Logger.t(TAG).w("create unsupported holder:" + viewType, new Object[0]);
        return new AdvItemGridVH(parent, this.mOnItemEventCallback);
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.binding.BindableAdapter
    public void setList(List<AdvAppModel> list) {
        submitList(list);
    }
}
