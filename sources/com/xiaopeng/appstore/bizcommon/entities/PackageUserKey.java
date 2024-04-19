package com.xiaopeng.appstore.bizcommon.entities;

import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import java.util.Arrays;
/* loaded from: classes2.dex */
public class PackageUserKey {
    private int mHashCode;
    public String mPackageName;
    public UserHandle mUser;

    public static PackageUserKey fromNotification(StatusBarNotification notification) {
        return new PackageUserKey(notification.getPackageName(), notification.getUser());
    }

    public PackageUserKey(String packageName, UserHandle user) {
        update(packageName, user);
    }

    private void update(String packageName, UserHandle user) {
        this.mPackageName = packageName;
        this.mUser = user;
        this.mHashCode = Arrays.hashCode(new Object[]{packageName, user});
    }

    public String toString() {
        return "pn=" + this.mPackageName + "#user=" + this.mUser;
    }

    public int hashCode() {
        return this.mHashCode;
    }

    public boolean equals(Object obj) {
        if (obj instanceof PackageUserKey) {
            PackageUserKey packageUserKey = (PackageUserKey) obj;
            return this.mPackageName.equals(packageUserKey.mPackageName) && this.mUser.equals(packageUserKey.mUser);
        }
        return false;
    }
}
