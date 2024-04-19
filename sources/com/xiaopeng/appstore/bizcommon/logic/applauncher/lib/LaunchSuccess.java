package com.xiaopeng.appstore.bizcommon.logic.applauncher.lib;
/* loaded from: classes2.dex */
public class LaunchSuccess implements LaunchResult {
    @Override // com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LaunchResult
    public boolean isSuccess() {
        return true;
    }

    public String toString() {
        return "LaunchSuccess";
    }
}
