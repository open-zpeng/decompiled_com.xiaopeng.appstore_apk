package com.xiaopeng.appstore.international.list;

import android.view.ViewGroup;
import com.xiaopeng.appstore.applist_biz.model.BaseAppItem;
import com.xiaopeng.appstore.applist_ui.adapter.AppIconViewHolder;
/* loaded from: classes.dex */
public class InternationalAppIconViewHolder extends AppIconViewHolder {
    public InternationalAppIconViewHolder(ViewGroup parent) {
        super(parent);
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppIconViewHolder
    public void refreshNameIndicator(BaseAppItem appItem) {
        super.refreshNameIndicator(appItem);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppIconViewHolder
    public void refreshIcon(BaseAppItem appItem) {
        super.refreshIcon(appItem);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppIconViewHolder
    public void refreshAppDesc(BaseAppItem appItem, int state) {
        super.refreshAppDesc(appItem, state);
    }

    public BaseAppItem getBaseAppItem() {
        return this.mAppItem;
    }
}
