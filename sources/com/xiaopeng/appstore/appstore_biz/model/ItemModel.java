package com.xiaopeng.appstore.appstore_biz.model;

import java.io.Serializable;
/* loaded from: classes2.dex */
public abstract class ItemModel implements Serializable {
    protected String mDownloadUrl;
    protected String mIconUrl;
    protected String mItemIntro;
    protected String mLabel;

    public abstract String getKey();

    public String getLabel() {
        return this.mLabel;
    }

    public String getItemIntro() {
        return this.mItemIntro;
    }

    public String getIconUrl() {
        return this.mIconUrl;
    }

    public String getDownloadUrl() {
        return this.mDownloadUrl;
    }
}
