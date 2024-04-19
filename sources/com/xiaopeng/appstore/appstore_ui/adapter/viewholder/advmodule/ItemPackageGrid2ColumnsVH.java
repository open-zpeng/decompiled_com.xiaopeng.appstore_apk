package com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule;

import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvAppModel;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvListModel;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder;
import java.util.Map;
@Deprecated
/* loaded from: classes2.dex */
public class ItemPackageGrid2ColumnsVH extends BaseItemPackageVH {
    private static final String TAG = "ItemPackageGrid2Columns";

    public ItemPackageGrid2ColumnsVH(ViewGroup parent, Map<String, RecyclerView.Adapter<?>> adapterCache, AdvItemViewHolder.OnItemEventCallback onItemEventCallback, RecyclerView.RecycledViewPool viewPool) {
        super(parent, adapterCache, onItemEventCallback, viewPool);
        Logger.t(TAG).d("create:" + hashCode() + ", pos:" + getAdapterPosition());
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BaseItemPackageVH
    protected RecyclerView.LayoutManager initChildLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.itemView.getContext(), 2);
        gridLayoutManager.setRecycleChildrenOnDetach(true);
        return gridLayoutManager;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BaseItemPackageVH, com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void setData(AdvListModel<AdvAppModel> data) {
        super.setData(data);
        Logger.t(TAG).d("bind:" + hashCode() + ", pos:" + getAdapterPosition());
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BaseItemPackageVH, com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void markAttached() {
        super.markAttached();
        Logger.t(TAG).d("attached:" + hashCode() + ", pos:" + getAdapterPosition());
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BaseItemPackageVH, com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void markDetached() {
        super.markDetached();
        Logger.t(TAG).d("detached:" + hashCode() + ", pos:" + getAdapterPosition());
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BaseItemPackageVH, com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void onViewRecycled() {
        super.onViewRecycled();
        Logger.t(TAG).d("recycled:" + hashCode() + ", pos:" + getAdapterPosition());
    }
}
