package com.xiaopeng.appstore.common_ui.common.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
/* loaded from: classes.dex */
public abstract class ViewHolderBase<T> extends RecyclerView.ViewHolder {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onAttach() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDetach() {
    }

    public abstract void setData(T data);

    public void setDataWithPayloads(T data, List<Object> payloads) {
    }

    public ViewHolderBase(View itemView) {
        super(itemView);
    }

    public ViewHolderBase(ViewGroup parent, int layoutId) {
        this(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    protected <V extends View> V findViewById(int id) {
        return (V) this.itemView.findViewById(id);
    }
}
