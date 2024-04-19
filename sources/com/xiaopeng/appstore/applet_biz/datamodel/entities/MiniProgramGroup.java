package com.xiaopeng.appstore.applet_biz.datamodel.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;
/* loaded from: classes2.dex */
public class MiniProgramGroup {
    @SerializedName("mini_list")
    private List<MiniProgramData> mData;
    @SerializedName("group_name")
    private String mGroupName;
    public int mId;

    public void setGroupName(String groupName) {
        this.mGroupName = groupName;
    }

    public void setData(List<MiniProgramData> data) {
        this.mData = data;
    }

    public String getGroupName() {
        return this.mGroupName;
    }

    public List<MiniProgramData> getData() {
        return this.mData;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MiniProgramGroup miniProgramGroup = (MiniProgramGroup) o;
        return this.mGroupName.equals(miniProgramGroup.mGroupName) && Objects.equals(this.mData, miniProgramGroup.mData);
    }

    public int hashCode() {
        return Objects.hash(this.mGroupName, this.mData);
    }
}
