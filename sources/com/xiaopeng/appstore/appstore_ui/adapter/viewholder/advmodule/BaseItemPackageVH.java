package com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvAppModel;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvListModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.AdvItemListAdapter;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.DividerItemDecoration;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemPackageBindingBinding;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import java.util.List;
import java.util.Map;
/* loaded from: classes2.dex */
public abstract class BaseItemPackageVH extends BaseBoundViewHolder<AdvListModel<AdvAppModel>, ItemPackageBindingBinding> {
    private static final String TAG = "BaseItemPackageVH";
    private final Map<String, RecyclerView.Adapter<?>> mAdapterCache;
    private final AdvItemViewHolder.OnItemEventCallback mOnItemEventCallback;

    protected abstract RecyclerView.LayoutManager initChildLayoutManager();

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public /* bridge */ /* synthetic */ void setData(AdvListModel<AdvAppModel> data, List payloads) {
        setData2(data, (List<Object>) payloads);
    }

    public BaseItemPackageVH(ViewGroup parent, Map<String, RecyclerView.Adapter<?>> adapterCache, AdvItemViewHolder.OnItemEventCallback onItemEventCallback, RecyclerView.RecycledViewPool viewPool) {
        super(parent, R.layout.item_package_binding);
        this.mOnItemEventCallback = onItemEventCallback;
        this.mAdapterCache = adapterCache;
        if (viewPool != null) {
            ((ItemPackageBindingBinding) this.mBinding).recyclerView.setRecycledViewPool(viewPool);
        }
        ((ItemPackageBindingBinding) this.mBinding).recyclerView.setLayoutManager(initChildLayoutManager());
        ((ItemPackageBindingBinding) this.mBinding).recyclerView.addItemDecoration(new DividerItemDecoration(1, (int) ResUtils.getDimen(R.dimen.app_store_horizontal_item_decoration_width)));
        Logger.t(TAG).d("create:" + getClass().getSimpleName() + "," + hashCode() + ", pos:" + getAdapterPosition());
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void setData(AdvListModel<AdvAppModel> data) {
        Logger.t(TAG).d("bind:" + getClass().getSimpleName() + "," + hashCode() + ", pos:" + getAdapterPosition());
        int viewType = data.getViewType();
        String dataId = data.getDataId();
        RecyclerView.Adapter<?> adapter = this.mAdapterCache.get(dataId);
        if (adapter == null && (adapter = createAdapter(viewType)) != null) {
            this.mAdapterCache.put(dataId, adapter);
        }
        if (adapter != null) {
            if (((ItemPackageBindingBinding) this.mBinding).recyclerView.getAdapter() != null) {
                ((ItemPackageBindingBinding) this.mBinding).recyclerView.swapAdapter(adapter, false);
            } else {
                ((ItemPackageBindingBinding) this.mBinding).recyclerView.setAdapter(adapter);
            }
        } else {
            ((ItemPackageBindingBinding) this.mBinding).recyclerView.setVisibility(8);
        }
        ((ItemPackageBindingBinding) this.mBinding).setModel(data);
        ((ItemPackageBindingBinding) this.mBinding).executePendingBindings();
    }

    /* renamed from: setData  reason: avoid collision after fix types in other method */
    public void setData2(AdvListModel<AdvAppModel> data, List<Object> payloads) {
        super.setData((BaseItemPackageVH) data, payloads);
        for (Object obj : payloads) {
            RecyclerView.Adapter adapter = ((ItemPackageBindingBinding) this.mBinding).recyclerView.getAdapter();
            if (adapter != null) {
                adapter.notifyItemRangeChanged(0, adapter.getItemCount(), obj);
            }
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void onViewRecycled() {
        super.onViewRecycled();
        Logger.t(TAG).d("recycled:" + getClass().getSimpleName() + "," + hashCode() + ", pos:" + getAdapterPosition());
        ((ItemPackageBindingBinding) this.mBinding).recyclerView.setAdapter(null);
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void markAttached() {
        super.markAttached();
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void markDetached() {
        super.markDetached();
    }

    private RecyclerView.Adapter<?> createAdapter(final int viewType) {
        return new AdvItemListAdapter(this.mOnItemEventCallback) { // from class: com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BaseItemPackageVH.1
            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemViewType(int position) {
                return viewType;
            }
        };
    }
}
