package com.xiaopeng.appstore.applist_biz.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/* loaded from: classes2.dex */
public class AppGroupItemModel<T> {
    public static final int ITEM_TYPE_APP = 100;
    public static final int ITEM_TYPE_FOOTER = 103;
    public static final int ITEM_TYPE_SPACE = 0;
    public static final int ITEM_TYPE_TITLE = 101;
    public T data;
    public int groupId;
    public int type;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface GroupItemType {
    }

    public AppGroupItemModel(int type, T data, int groupId) {
        this.type = type;
        this.data = data;
        this.groupId = groupId;
    }

    public String toString() {
        return "{t=" + this.type + ", d=" + this.data + ", g=" + this.groupId + '}';
    }
}
