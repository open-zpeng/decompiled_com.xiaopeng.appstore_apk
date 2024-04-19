package com.xiaopeng.appstore.applist_biz.model;
/* loaded from: classes2.dex */
public class AppItemChangeEvent<T> {
    public static final int EVENT_ADD_LOADING = 3;
    public static final int EVENT_CHANGE = 1;
    public static final int EVENT_REMOVE = 2;
    private T mData;
    private int mType;

    public int getType() {
        return this.mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public T getData() {
        return this.mData;
    }

    public void setData(T data) {
        this.mData = data;
    }
}
