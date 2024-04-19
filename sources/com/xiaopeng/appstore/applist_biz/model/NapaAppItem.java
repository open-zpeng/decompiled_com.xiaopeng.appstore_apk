package com.xiaopeng.appstore.applist_biz.model;

import android.graphics.Bitmap;
import com.xiaopeng.app.xpAppInfo;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import java.util.Arrays;
import java.util.Objects;
/* loaded from: classes2.dex */
public final class NapaAppItem extends BaseAppItem {
    private final int mHashCode;
    public Bitmap mXpAppIcon;
    public String mXpAppId;
    public String mXpAppPage;
    public String resId;
    public String url;

    public NapaAppItem(xpAppInfo info) {
        this.mHashCode = Arrays.hashCode(new Object[]{info.mXpAppTitle, info.mXpAppId, info.resId});
        this.title = info.mXpAppTitle;
        this.packageName = info.mXpAppId;
        this.resId = info.resId;
        this.mXpAppId = info.mXpAppId;
        this.mXpAppPage = info.mXpAppPage;
        this.mXpAppIcon = info.mXpAppIcon;
        this.url = "appID=" + info.mXpAppId + "&page=" + info.mXpAppPage;
    }

    @Override // com.xiaopeng.appstore.applist_biz.model.BaseAppItem
    public String toString() {
        return "NapaAppItem{title=" + ((Object) this.title) + ", resId='" + this.resId + "', mXpAppId='" + this.mXpAppId + "', mXpAppPage='" + this.mXpAppPage + "', mXpAppIcon='" + this.mXpAppIcon + "'}";
    }

    @Override // com.xiaopeng.appstore.applist_biz.model.BaseAppItem
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Objects.equals(this.mXpAppIcon, ((NapaAppItem) o).mXpAppIcon);
    }

    @Override // com.xiaopeng.appstore.applist_biz.model.BaseAppItem
    public int hashCode() {
        return this.mHashCode;
    }

    @Override // com.xiaopeng.appstore.applist_biz.model.BaseAppItem
    public String getItemKey() {
        return this.mXpAppId + "/" + this.mXpAppPage;
    }

    public void startActivity() {
        XuiMgrHelper.get().startXPApp(this.url);
    }
}
