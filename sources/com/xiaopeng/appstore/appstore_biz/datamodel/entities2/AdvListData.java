package com.xiaopeng.appstore.appstore_biz.datamodel.entities2;

import com.google.gson.annotations.SerializedName;
import java.util.List;
/* loaded from: classes2.dex */
public class AdvListData extends AdvData {
    @SerializedName("list")
    private List<AdvContainer> mList;

    public List<AdvContainer> getList() {
        return this.mList;
    }
}
