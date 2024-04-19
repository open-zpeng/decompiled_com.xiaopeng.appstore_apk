package com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule;

import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvListModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemActivityFooterBinding;
import com.xiaopeng.appstore.bizcommon.logic.AgreementDialogHelper;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
/* loaded from: classes2.dex */
public class FooterViewHolder extends BaseBoundViewHolder<AdvListModel, ItemActivityFooterBinding> {
    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void setData(AdvListModel data) {
    }

    public FooterViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_activity_footer);
        if (((ItemActivityFooterBinding) this.mBinding).tvProtocol != null) {
            ((ItemActivityFooterBinding) this.mBinding).tvProtocol.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.-$$Lambda$FooterViewHolder$1RQ--ce33zqT4joUsGdzcYcyKiQ
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FooterViewHolder.this.lambda$new$0$FooterViewHolder(view);
                }
            });
        }
    }

    public /* synthetic */ void lambda$new$0$FooterViewHolder(View view) {
        showProtocolDialog();
    }

    private void showProtocolDialog() {
        EventTrackingHelper.sendMolecast(PagesEnum.STORE_MAIN, EventEnum.USER_AGREEMENT, 2);
        AgreementDialogHelper.getInstance().show(null, true);
    }
}
