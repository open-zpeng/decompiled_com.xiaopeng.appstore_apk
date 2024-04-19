package com.xiaopeng.appstore.appstore_biz.model2;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
/* loaded from: classes2.dex */
public class PermissionData implements Serializable {
    @SerializedName("general")
    public List<String> mGeneralList;
    @SerializedName("important")
    public List<String> mImportantList;

    public List<String> getImportantList() {
        return this.mImportantList;
    }

    public List<String> getGeneralList() {
        return this.mGeneralList;
    }
}
