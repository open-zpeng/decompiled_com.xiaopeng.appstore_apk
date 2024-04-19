package com.xiaopeng.appstore.appstore_biz.model2;

import java.io.Serializable;
/* loaded from: classes2.dex */
public abstract class BaseItemModel implements Serializable {
    protected String mDownloadUrl;
    protected String mIconUrl;
    protected String mItemIntro;
    protected String mTitle;

    public abstract String getKey();

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getItemIntro() {
        return this.mItemIntro;
    }

    public void setItemIntro(String itemIntro) {
        this.mItemIntro = itemIntro;
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
}
