package com.xiaopeng.appstore.appstore_biz.model2;

import java.io.Serializable;
/* loaded from: classes2.dex */
public class PrivatePolicyModel implements Serializable {
    private String mIconUrl;
    private String mWebUrl;

    public String getIconUrl() {
        return this.mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public String getWebUrl() {
        return this.mWebUrl;
    }

    public void setWebUrl(String webUrl) {
        this.mWebUrl = webUrl;
    }
}
