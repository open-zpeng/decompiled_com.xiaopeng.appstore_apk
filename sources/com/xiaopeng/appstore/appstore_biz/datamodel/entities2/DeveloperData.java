package com.xiaopeng.appstore.appstore_biz.datamodel.entities2;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
/* loaded from: classes2.dex */
public class DeveloperData implements Serializable {
    @SerializedName("developer_name")
    private String mName;

    public String getName() {
        return this.mName;
    }
}
