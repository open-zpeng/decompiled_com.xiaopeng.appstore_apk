package com.xiaopeng.appstore.appserver_common;

import androidx.recyclerview.widget.DiffUtil;
import com.xiaopeng.appstore.protobuf.AppItemProto;
/* loaded from: classes2.dex */
public class AppItemDiffCallback extends DiffUtil.ItemCallback<AppItemProto> {
    @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
    public boolean areItemsTheSame(AppItemProto oldItem, AppItemProto newItem) {
        return oldItem.getPackageName().equals(newItem.getPackageName());
    }

    @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
    public boolean areContentsTheSame(AppItemProto oldItem, AppItemProto newItem) {
        return appItemEquals(oldItem, newItem);
    }

    private boolean appItemEquals(AppItemProto appItem1, AppItemProto appItem2) {
        return appItem1.getName().equals(appItem2.getName()) && appItem1.getType() == appItem2.getType();
    }
}
