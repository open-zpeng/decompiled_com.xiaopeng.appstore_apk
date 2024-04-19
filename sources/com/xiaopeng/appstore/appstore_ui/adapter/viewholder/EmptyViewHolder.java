package com.xiaopeng.appstore.appstore_ui.adapter.viewholder;

import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.appstore.common_ui.common.base.ViewHolderBase;
/* loaded from: classes2.dex */
public class EmptyViewHolder extends ViewHolderBase<Object> {
    @Override // com.xiaopeng.appstore.common_ui.common.base.ViewHolderBase
    public void setData(Object data) {
    }

    public EmptyViewHolder(ViewGroup parent) {
        super(new View(parent.getContext()));
    }
}
