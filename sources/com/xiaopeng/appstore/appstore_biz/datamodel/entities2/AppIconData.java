package com.xiaopeng.appstore.appstore_biz.datamodel.entities2;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
/* loaded from: classes2.dex */
public class AppIconData implements Serializable {
    @SerializedName("large_icon")
    private String mLargeIcon;
    @SerializedName("small_icon")
    private String mSmallIcon;

    public String getSmallIcon() {
        return this.mSmallIcon;
    }

    public String getLargeIcon() {
        return this.mLargeIcon;
    }

    public void setSmallIcon(String smallIcon) {
        this.mSmallIcon = smallIcon;
    }

    public void setLargeIcon(String largeIcon) {
        this.mLargeIcon = largeIcon;
    }
}
