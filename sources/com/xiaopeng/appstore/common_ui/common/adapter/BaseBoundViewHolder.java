package com.xiaopeng.appstore.common_ui.common.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
/* loaded from: classes.dex */
public abstract class BaseBoundViewHolder<D, B extends ViewDataBinding> extends RecyclerView.ViewHolder {
    protected B mBinding;

    public void markAttached() {
    }

    public void markDetached() {
    }

    public void onViewRecycled() {
    }

    public abstract void setData(D data);

    public void setData(D data, List<Object> payloads) {
    }

    public BaseBoundViewHolder(ViewGroup parent, int layoutId) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
        this.mBinding = (B) DataBindingUtil.bind(this.itemView);
    }

    public B getBinding() {
        return this.mBinding;
    }
}
