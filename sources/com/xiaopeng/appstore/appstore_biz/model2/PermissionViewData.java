package com.xiaopeng.appstore.appstore_biz.model2;

import java.io.Serializable;
import java.util.List;
/* loaded from: classes2.dex */
public class PermissionViewData implements Serializable {
    private List<String> mGeneralPermission;
    private String mIconUrl;
    private List<String> mImportantPermission;

    public List<String> getImportantPermission() {
        return this.mImportantPermission;
    }

    public void setImportantPermission(List<String> importantPermission) {
        this.mImportantPermission = importantPermission;
    }

    public List<String> getGeneralPermission() {
        return this.mGeneralPermission;
    }

    public void setGeneralPermission(List<String> generalPermission) {
        this.mGeneralPermission = generalPermission;
    }

    public String getIconUrl() {
        return this.mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }
}
