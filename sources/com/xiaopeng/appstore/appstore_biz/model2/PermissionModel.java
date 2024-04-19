package com.xiaopeng.appstore.appstore_biz.model2;

import java.util.List;
/* loaded from: classes2.dex */
public class PermissionModel {
    private String mIconUrl;
    private List<PermissionItemModel> mItemModels;

    public String getIconUrl() {
        return this.mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public List<PermissionItemModel> getItemModels() {
        return this.mItemModels;
    }

    public void setItemModels(List<PermissionItemModel> itemModels) {
        this.mItemModels = itemModels;
    }
}
