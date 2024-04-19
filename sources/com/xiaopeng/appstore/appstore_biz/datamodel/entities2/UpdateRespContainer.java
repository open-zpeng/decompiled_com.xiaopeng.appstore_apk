package com.xiaopeng.appstore.appstore_biz.datamodel.entities2;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
/* loaded from: classes2.dex */
public class UpdateRespContainer implements Serializable {
    @SerializedName("apps")
    private List<AppDetailData> mAppList;

    public List<AppDetailData> getAppList() {
        return this.mAppList;
    }

    public String toString() {
        return "UpdateRespContainer{list=" + this.mAppList + '}';
    }
}
