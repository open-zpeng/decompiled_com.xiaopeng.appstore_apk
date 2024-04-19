package com.xiaopeng.appstore.applist_biz.model;

import android.content.ComponentName;
import android.content.Intent;
import android.os.UserHandle;
import com.xiaopeng.appstore.bizcommon.entities.AppInfo;
import com.xiaopeng.appstore.bizcommon.entities.ComponentKey;
import java.util.Arrays;
import java.util.Objects;
/* loaded from: classes2.dex */
public final class NormalAppItem extends BaseAppItem {
    public ComponentName componentName;
    public CharSequence contentDescription;
    public Intent intent;
    private ComponentKey mComponentKey;
    private final int mHashCode;
    public boolean newInstalled;
    public UserHandle user;

    public NormalAppItem(ComponentName componentName, UserHandle user) {
        this.mHashCode = Arrays.hashCode(new Object[]{componentName, user});
        this.componentName = componentName;
        this.user = user;
        this.mComponentKey = new ComponentKey(componentName, user);
    }

    @Override // com.xiaopeng.appstore.applist_biz.model.BaseAppItem
    public String toString() {
        return "NormalAppItem{title=" + ((Object) this.title) + ", packageName='" + this.packageName + "', new=" + this.newInstalled + ", comp=" + this.componentName + '}';
    }

    public void apply(AppInfo appInfo) {
        this.componentName = appInfo.componentName;
        this.user = appInfo.user;
        this.intent = appInfo.intent;
        this.iconBitmap = appInfo.iconBitmap;
        this.title = appInfo.title;
        this.contentDescription = appInfo.contentDescription;
        this.mComponentKey = new ComponentKey(this.componentName, this.user);
    }

    @Override // com.xiaopeng.appstore.applist_biz.model.BaseAppItem
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NormalAppItem normalAppItem = (NormalAppItem) o;
        return this.user.equals(normalAppItem.user) && this.componentName.equals(normalAppItem.componentName) && Objects.equals(this.iconBitmap, normalAppItem.iconBitmap);
    }

    @Override // com.xiaopeng.appstore.applist_biz.model.BaseAppItem
    public int hashCode() {
        return this.mHashCode;
    }

    @Override // com.xiaopeng.appstore.applist_biz.model.BaseAppItem
    public String getItemKey() {
        return this.mComponentKey.toString();
    }
}
