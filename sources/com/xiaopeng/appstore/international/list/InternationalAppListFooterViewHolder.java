package com.xiaopeng.appstore.international.list;

import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.appstore.applist_ui.adapter.BaseAppListViewHolder;
import com.xiaopeng.appstore.international.R;
/* loaded from: classes.dex */
public class InternationalAppListFooterViewHolder extends BaseAppListViewHolder<Object> {
    IProtocolClickListener protocolClickListener;

    @Override // com.xiaopeng.appstore.applist_ui.adapter.BaseAppListViewHolder
    public void onViewRecycled() {
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.BaseAppListViewHolder
    public void setData(Object data) {
    }

    public InternationalAppListFooterViewHolder(ViewGroup parent, IProtocolClickListener protocolClickListener) {
        super(parent, R.layout.international_app_list_footer);
        this.protocolClickListener = protocolClickListener;
        this.itemView.findViewById(R.id.tv_protocol).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.international.list.-$$Lambda$InternationalAppListFooterViewHolder$tSDE2vrA4J-w9ibyqZLMospiH0M
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InternationalAppListFooterViewHolder.this.lambda$new$0$InternationalAppListFooterViewHolder(view);
            }
        });
    }

    public /* synthetic */ void lambda$new$0$InternationalAppListFooterViewHolder(View view) {
        IProtocolClickListener iProtocolClickListener = this.protocolClickListener;
        if (iProtocolClickListener != null) {
            iProtocolClickListener.onUserProtocolClicked(view);
        }
    }
}
