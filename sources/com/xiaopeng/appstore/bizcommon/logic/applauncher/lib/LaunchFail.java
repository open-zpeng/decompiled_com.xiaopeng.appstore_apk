package com.xiaopeng.appstore.bizcommon.logic.applauncher.lib;
/* loaded from: classes2.dex */
public class LaunchFail implements LaunchResult {
    private final int mCode;
    private final String mMsg;

    @Override // com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LaunchResult
    public boolean isSuccess() {
        return false;
    }

    public LaunchFail(int code, String msg) {
        this.mCode = code;
        this.mMsg = msg;
    }

    public int getCode() {
        return this.mCode;
    }

    public String getMsg() {
        return this.mMsg;
    }

    public String toString() {
        return "LaunchFail{code=" + this.mCode + ", msg='" + this.mMsg + "'}";
    }
}
