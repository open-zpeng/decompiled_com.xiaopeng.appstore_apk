package com.xiaopeng.appstore.applist_ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.applist_biz.model.AppGroupItemModel;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
/* loaded from: classes2.dex */
public abstract class BaseAppListViewHolder<T> extends RecyclerView.ViewHolder {
    public void attach() {
    }

    public void detach() {
    }

    public abstract void onViewRecycled();

    public void setData(AppGroupItemModel data, PayloadData payloadData) {
    }

    public abstract void setData(T data);

    public BaseAppListViewHolder(ViewGroup parent, int layoutId) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    public BaseAppListViewHolder(View itemView) {
        super(itemView);
    }
}
