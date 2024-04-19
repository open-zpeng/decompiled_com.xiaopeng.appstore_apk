package com.xiaopeng.appstore.applist_ui.adapter;

import androidx.recyclerview.widget.DiffUtil;
import com.xiaopeng.appstore.applist_biz.model.AppGroupItemModel;
import com.xiaopeng.appstore.applist_biz.model.BaseAppItem;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
/* loaded from: classes2.dex */
public class AppItemDiffCallback extends DiffUtil.ItemCallback<AppGroupItemModel> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
    public boolean areItemsTheSame(AppGroupItemModel oldItem, AppGroupItemModel newItem) {
        if (oldItem.type == newItem.type) {
            if (oldItem.data == 0 && newItem.data == 0) {
                return true;
            }
            if ((oldItem.data instanceof BaseAppItem) && (newItem.data instanceof BaseAppItem)) {
                return ((BaseAppItem) oldItem.data).packageName.equals(((BaseAppItem) newItem.data).packageName);
            }
            if ((oldItem.data instanceof String) && (newItem.data instanceof String)) {
                return oldItem.data.equals(newItem.data);
            }
            if ((oldItem.data instanceof CharSequence) && (newItem.data instanceof CharSequence)) {
                return ((CharSequence) oldItem.data).toString().equals(((CharSequence) newItem.data).toString());
            }
            return false;
        }
        return false;
    }

    @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
    public boolean areContentsTheSame(AppGroupItemModel oldItem, AppGroupItemModel newItem) {
        if (oldItem.type == newItem.type) {
            if (oldItem.data == 0 && newItem.data == 0) {
                return true;
            }
            if ((oldItem.data instanceof BaseAppItem) && (newItem.data instanceof BaseAppItem)) {
                return appItemEquals((BaseAppItem) oldItem.data, (BaseAppItem) newItem.data);
            }
            if ((oldItem.data instanceof String) && (newItem.data instanceof String)) {
                return ((String) oldItem.data).equals((String) newItem.data);
            }
            if (!(oldItem.data instanceof CharSequence) || (newItem.data instanceof CharSequence)) {
            }
        }
        return false;
    }

    private boolean appItemEquals(BaseAppItem appItem1, BaseAppItem appItem2) {
        return appItem1.equals(appItem2) && appItem1.iconBitmap == appItem2.iconBitmap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
    public Object getChangePayload(AppGroupItemModel oldItem, AppGroupItemModel newItem) {
        PayloadData payloadData = new PayloadData();
        payloadData.type = 7;
        payloadData.data = newItem;
        return payloadData;
    }
}
