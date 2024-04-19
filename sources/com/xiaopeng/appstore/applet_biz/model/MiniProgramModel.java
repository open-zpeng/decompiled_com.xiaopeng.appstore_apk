package com.xiaopeng.appstore.applet_biz.model;

import java.util.Objects;
/* loaded from: classes2.dex */
public class MiniProgramModel {
    private String mIconUrl;
    private String mId;
    private String mJumpUrl;
    private String mName;

    public String getName() {
        return this.mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getId() {
        return this.mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getJumpUrl() {
        return this.mJumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.mJumpUrl = jumpUrl;
    }

    public String getIconUrl() {
        return this.mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MiniProgramModel miniProgramModel = (MiniProgramModel) o;
        return Objects.equals(this.mName, miniProgramModel.mName) && Objects.equals(this.mJumpUrl, miniProgramModel.mJumpUrl);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("MiniProgramModel{");
        sb.append("mName='").append(this.mName).append('\'');
        sb.append(", mId='").append(this.mId).append('\'');
        sb.append(", mJumpUrl='").append(this.mJumpUrl).append('\'');
        sb.append(", mIconUrl='").append(this.mIconUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public int hashCode() {
        return Objects.hash(this.mName, this.mJumpUrl);
    }
}
