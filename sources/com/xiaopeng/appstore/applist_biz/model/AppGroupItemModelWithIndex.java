package com.xiaopeng.appstore.applist_biz.model;
/* loaded from: classes2.dex */
public class AppGroupItemModelWithIndex {
    private final AppGroupItemModel mAppGroupItemModel;
    private final int mIndex;

    public AppGroupItemModelWithIndex(int index, AppGroupItemModel appItemChangeEvent) {
        this.mIndex = index;
        this.mAppGroupItemModel = appItemChangeEvent;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public AppGroupItemModel getAppItemChangeEvent() {
        return this.mAppGroupItemModel;
    }
}
