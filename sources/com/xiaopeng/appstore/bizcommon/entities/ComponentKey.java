package com.xiaopeng.appstore.bizcommon.entities;

import android.content.ComponentName;
import android.os.UserHandle;
import java.util.Arrays;
/* loaded from: classes2.dex */
public class ComponentKey {
    public final ComponentName componentName;
    private final int mHashCode;
    public final UserHandle user;

    public ComponentKey(ComponentName componentName, UserHandle user) {
        this.componentName = componentName;
        this.user = user;
        this.mHashCode = Arrays.hashCode(new Object[]{componentName, user});
    }

    public int hashCode() {
        return this.mHashCode;
    }

    public boolean equals(Object o) {
        ComponentKey componentKey = (ComponentKey) o;
        return componentKey.componentName.equals(this.componentName) && componentKey.user.equals(this.user);
    }

    public String toString() {
        return this.componentName.flattenToString() + "#" + this.user;
    }
}
