package com.xiaopeng.appstore.applet_biz.datamodel.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;
/* loaded from: classes2.dex */
public class MiniProgramsContainer {
    @SerializedName("data")
    private List<MiniProgramGroup> mData;

    public List<MiniProgramGroup> getData() {
        return this.mData;
    }
}
