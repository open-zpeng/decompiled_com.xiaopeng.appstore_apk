package com.xiaopeng.appstore.ui_miniprog.miniprog.adatper;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.xiaopeng.appstore.applet_ui.R;
import com.xiaopeng.appstore.common_ui.common.base.ViewHolderBase;
/* loaded from: classes2.dex */
class MiniFooterViewHolder extends ViewHolderBase<String> {
    @Override // com.xiaopeng.appstore.common_ui.common.base.ViewHolderBase
    public void setData(String data) {
    }

    public MiniFooterViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_program_page_footer, parent, false));
    }
}
