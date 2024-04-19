package com.xiaopeng.appstore.bizcommon.logic.applauncher.lib;

import android.content.Intent;
import android.os.UserHandle;
/* loaded from: classes2.dex */
public class LaunchParam {
    private final String appName;
    private final Intent mIntent;
    private final UserHandle mUser;

    public LaunchParam(Intent intent, UserHandle user) {
        this.mIntent = intent;
        this.mUser = user;
        this.appName = "";
    }

    public LaunchParam(Intent intent, UserHandle user, String appName) {
        this.mIntent = intent;
        this.mUser = user;
        this.appName = appName;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public UserHandle getUser() {
        return this.mUser;
    }

    public String getAppName() {
        return this.appName;
    }

    public String getPackageName() {
        return this.mIntent.getComponent() != null ? this.mIntent.getComponent().getPackageName() : "";
    }

    public String toString() {
        return "LaunchParam{, intent=" + this.mIntent + ", user=" + this.mUser + '}';
    }
}
