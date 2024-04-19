package com.xiaopeng.appstore.applist_biz.model;

import java.util.Arrays;
/* loaded from: classes2.dex */
public class FixedAppItem extends BaseAppItem {
    private String mConfigMd5;
    private String mConfigUrl;
    private String mDownloadUrl;
    private final int mHashCode;
    private String mIconUrl;
    private boolean mIsSuspended;
    private String mMd5;

    public FixedAppItem(String packageName, String iconUrl, CharSequence title) {
        this.packageName = packageName;
        this.title = title;
        this.mIconUrl = iconUrl;
        this.mHashCode = Arrays.hashCode(new Object[]{packageName, iconUrl});
    }

    @Override // com.xiaopeng.appstore.applist_biz.model.BaseAppItem
    public String toString() {
        return "FixedAppItem{title=" + ((Object) this.title) + ", packageName='" + this.packageName + "'}";
    }

    @Override // com.xiaopeng.appstore.applist_biz.model.BaseAppItem
    public String getItemKey() {
        return this.packageName;
    }

    @Override // com.xiaopeng.appstore.applist_biz.model.BaseAppItem
    public boolean equals(Object obj) {
        if (obj instanceof FixedAppItem) {
            FixedAppItem fixedAppItem = (FixedAppItem) obj;
            return fixedAppItem.packageName.equals(this.packageName) && fixedAppItem.mIconUrl.equals(this.mIconUrl);
        }
        return false;
    }

    @Override // com.xiaopeng.appstore.applist_biz.model.BaseAppItem
    public int hashCode() {
        return this.mHashCode;
    }

    public String getIconUrl() {
        return this.mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public String getDownloadUrl() {
        return this.mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.mDownloadUrl = downloadUrl;
    }

    public String getConfigUrl() {
        return this.mConfigUrl;
    }

    public void setConfigUrl(String configUrl) {
        this.mConfigUrl = configUrl;
    }

    public void setSuspended(boolean suspended) {
        this.mIsSuspended = suspended;
    }

    public boolean isSuspended() {
        return this.mIsSuspended;
    }

    public String getMd5() {
        return this.mMd5;
    }

    public void setMd5(String md5) {
        this.mMd5 = md5;
    }

    public String getConfigMd5() {
        return this.mConfigMd5;
    }

    public void setConfigMd5(String configMd5) {
        this.mConfigMd5 = configMd5;
    }
}
