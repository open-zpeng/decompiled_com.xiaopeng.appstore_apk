package com.xiaopeng.appstore.applist_biz.model;
/* loaded from: classes2.dex */
public class AdapterChangeModel<T> {
    private T mData;
    private int mIndex;

    public int getIndex() {
        return this.mIndex;
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }

    public T getData() {
        return this.mData;
    }

    public void setData(T data) {
        this.mData = data;
    }
}
