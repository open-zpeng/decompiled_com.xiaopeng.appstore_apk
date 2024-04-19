package com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule;

import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder;
import java.util.Map;
/* loaded from: classes2.dex */
public class ItemPackageHorizontalVH extends BaseItemPackageVH {
    public ItemPackageHorizontalVH(ViewGroup parent, Map<String, RecyclerView.Adapter<?>> adapterCache, AdvItemViewHolder.OnItemEventCallback onItemEventCallback, RecyclerView.RecycledViewPool viewPool) {
        super(parent, adapterCache, onItemEventCallback, viewPool);
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BaseItemPackageVH
    protected RecyclerView.LayoutManager initChildLayoutManager() {
        return new LinearLayoutManager(this.itemView.getContext(), 0, false);
    }
}
