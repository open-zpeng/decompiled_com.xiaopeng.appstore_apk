package com.xiaopeng.appstore.bizcommon.entities.common;
/* loaded from: classes2.dex */
public class XpApiResponse<T> {
    public String code;
    public T data;
    public String msg;

    public boolean isSuccessful() {
        return "200".equals(this.code);
    }

    public String toString() {
        return "XpApiResp{code='" + this.code + "', msg='" + this.msg + "', data=" + this.data + '}';
    }
}
