package com.xiaopeng.appstore.appstore_ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.appstore_biz.model2.PermissionItemModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.PermissionDetailVH;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class PermissionDetailAdapter extends RecyclerView.Adapter<PermissionDetailVH> {
    private ArrayList<PermissionItemModel> mDataModels;

    public PermissionDetailAdapter(List<PermissionItemModel> dataModels) {
        this.mDataModels = new ArrayList<>(dataModels);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public PermissionDetailVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PermissionDetailVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_permission_detail, parent, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(PermissionDetailVH holder, int position) {
        holder.updateView(this.mDataModels.get(position));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<PermissionItemModel> arrayList = this.mDataModels;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }
}
