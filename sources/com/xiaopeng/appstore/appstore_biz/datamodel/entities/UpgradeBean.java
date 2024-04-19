package com.xiaopeng.appstore.appstore_biz.datamodel.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;
/* loaded from: classes2.dex */
public class UpgradeBean {
    @SerializedName("upgradeList")
    private List<ItemDetailBean> mUpgradeList;

    public List<ItemDetailBean> getUpgradeList() {
        return this.mUpgradeList;
    }
}
