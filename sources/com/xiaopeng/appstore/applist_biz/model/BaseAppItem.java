package com.xiaopeng.appstore.applist_biz.model;

import android.graphics.Bitmap;
/* loaded from: classes2.dex */
public abstract class BaseAppItem {
    public Bitmap iconBitmap;
    public String packageName;
    public CharSequence title;

    public abstract boolean equals(Object o);

    public abstract String getItemKey();

    public abstract int hashCode();

    public String getKey() {
        return getClass().getSimpleName() + "#" + getItemKey();
    }

    public String toString() {
        return getClass().getSimpleName() + "{title=" + ((Object) this.title) + ", packageName='" + this.packageName + "'}";
    }
}
