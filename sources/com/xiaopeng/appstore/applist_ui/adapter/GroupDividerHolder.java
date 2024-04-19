package com.xiaopeng.appstore.applist_ui.adapter;

import android.view.ViewGroup;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.applist_biz.model.AppGroupItemModel;
import com.xiaopeng.appstore.applist_ui.R;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
/* loaded from: classes2.dex */
public class GroupDividerHolder extends BaseAppListViewHolder<CharSequence> {
    private static final String TAG = "AppTitleVH";
    private final TextView mGroupTitle;

    @Override // com.xiaopeng.appstore.applist_ui.adapter.BaseAppListViewHolder
    public void onViewRecycled() {
    }

    public GroupDividerHolder(ViewGroup parent) {
        super(parent, R.layout.layout_app_list_group_title);
        this.mGroupTitle = (TextView) this.itemView;
    }

    public void setTitle(CharSequence title) {
        this.mGroupTitle.setText(title);
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.BaseAppListViewHolder
    public void setData(CharSequence title) {
        this.mGroupTitle.setText(title);
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.BaseAppListViewHolder
    public void setData(AppGroupItemModel data, PayloadData payloadData) {
        Logger.t(TAG).d("setDataWithPayload, payloadData=" + payloadData);
        if (payloadData.type == 7) {
            AppGroupItemModel appGroupItemModel = (AppGroupItemModel) payloadData.data;
            if (appGroupItemModel != null && (appGroupItemModel.data instanceof CharSequence)) {
                setData((CharSequence) appGroupItemModel.data);
            } else {
                Logger.t(TAG).w("setDataWithPayload warning, data is not String. ", new Object[0]);
            }
        } else if (payloadData.type == 4) {
            if (data != null && (data.data instanceof CharSequence)) {
                setData((CharSequence) data.data);
            } else {
                Logger.t(TAG).w("setDataWithPayload warning, data is not String, payload:" + payloadData, new Object[0]);
            }
        }
    }
}
