package com.xiaopeng.appstore.ui_miniprog.miniprog.adatper;

import android.view.ViewGroup;
import android.widget.TextView;
import com.xiaopeng.appstore.applet_ui.R;
import com.xiaopeng.appstore.common_ui.common.base.ViewHolderBase;
/* loaded from: classes2.dex */
public class MiniProgramTitleVH extends ViewHolderBase<String> {
    public MiniProgramTitleVH(ViewGroup parent) {
        super(parent, R.layout.layout_app_list_group_title);
    }

    @Override // com.xiaopeng.appstore.common_ui.common.base.ViewHolderBase
    public void setData(String data) {
        if (this.itemView instanceof TextView) {
            ((TextView) this.itemView).setText(data);
        }
    }
}
