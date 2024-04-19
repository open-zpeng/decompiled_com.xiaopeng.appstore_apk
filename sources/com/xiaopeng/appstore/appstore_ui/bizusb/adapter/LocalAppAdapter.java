package com.xiaopeng.appstore.appstore_ui.bizusb.adapter;

import android.view.ViewGroup;
import com.xiaopeng.appstore.appstore_ui.bizusb.INotifyDataChangedCallback;
import com.xiaopeng.appstore.common_ui.common.base.RecyclerAdapterBase;
import com.xiaopeng.appstore.common_ui.common.base.ViewHolderBase;
/* loaded from: classes2.dex */
public class LocalAppAdapter extends RecyclerAdapterBase implements INotifyDataChangedCallback {
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolderBase<?> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocalAppItemVH(this, parent);
    }

    @Override // com.xiaopeng.appstore.appstore_ui.bizusb.INotifyDataChangedCallback
    public void dataChanged(int position) {
        notifyItemChanged(position);
    }
}
