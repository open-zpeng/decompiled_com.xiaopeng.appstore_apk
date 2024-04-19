package com.xiaopeng.appstore.libcommon.utils.actionprocessor;
/* loaded from: classes2.dex */
public class OkResult<T> implements Result<T> {
    public T result;

    public OkResult(T result) {
        this.result = result;
    }

    public String toString() {
        return "OkResult{result=" + this.result + '}';
    }
}
