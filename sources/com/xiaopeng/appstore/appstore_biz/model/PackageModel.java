package com.xiaopeng.appstore.appstore_biz.model;

import java.util.List;
/* loaded from: classes2.dex */
public class PackageModel {
    private List<AppModel> mItemList;
    private String mTitle;

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public List<AppModel> getItemList() {
        return this.mItemList;
    }

    public void setItemList(List<AppModel> itemList) {
        this.mItemList = itemList;
    }
}
