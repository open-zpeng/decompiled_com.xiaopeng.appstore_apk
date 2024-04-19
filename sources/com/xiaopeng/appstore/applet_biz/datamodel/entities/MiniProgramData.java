package com.xiaopeng.appstore.applet_biz.datamodel.entities;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import java.util.Objects;
/* loaded from: classes2.dex */
public class MiniProgramData {
    @SerializedName("iconName")
    private String mIconName;
    @SerializedName("id")
    private String mId = "";
    @SerializedName("name")
    private String mName;
    @SerializedName(IpcConfig.DeviceCommunicationConfig.KEY_APP_MESSAGE_PARAMS)
    private String mParams;

    public String getName() {
        return this.mName;
    }

    public String getId() {
        return this.mId;
    }

    public String getIconName() {
        return this.mIconName;
    }

    public String getParams() {
        return this.mParams;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public void setIconName(String iconName) {
        this.mIconName = iconName;
    }

    public void setParams(String params) {
        this.mParams = params;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MiniProgramData miniProgramData = (MiniProgramData) o;
        return Objects.equals(this.mName, miniProgramData.mName) && this.mId.equals(miniProgramData.mId) && Objects.equals(this.mIconName, miniProgramData.mIconName) && Objects.equals(this.mParams, miniProgramData.mParams);
    }

    public int hashCode() {
        return Objects.hash(this.mName, this.mId, this.mIconName, this.mParams);
    }
}
