package com.xiaopeng.appstore.applist_biz.model;

import java.util.Arrays;
import java.util.Objects;
/* loaded from: classes2.dex */
public final class LoadingAppItem extends BaseAppItem {
    public String iconUrl;
    private final int mHashCode;

    public LoadingAppItem(String packageName, String iconUrl, CharSequence title) {
        this.packageName = packageName;
        this.iconUrl = iconUrl;
        this.title = title;
        this.mHashCode = Arrays.hashCode(new Object[]{packageName, iconUrl});
    }

    @Override // com.xiaopeng.appstore.applist_biz.model.BaseAppItem
    public String toString() {
        return "LoadingAppItem{title=" + ((Object) this.title) + ", packageName='" + this.packageName + "', icon='" + this.iconUrl + "'}";
    }

    @Override // com.xiaopeng.appstore.applist_biz.model.BaseAppItem
    public String getItemKey() {
        return this.packageName;
    }

    @Override // com.xiaopeng.appstore.applist_biz.model.BaseAppItem
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoadingAppItem loadingAppItem = (LoadingAppItem) o;
        return Objects.equals(this.packageName, loadingAppItem.packageName) && Objects.equals(this.iconUrl, loadingAppItem.iconUrl);
    }

    @Override // com.xiaopeng.appstore.applist_biz.model.BaseAppItem
    public int hashCode() {
        return this.mHashCode;
    }
}
