package com.xiaopeng.appstore.appstore_biz.model2.adv;

import com.xiaopeng.appstore.appstore_biz.model2.AppBaseModel;
import java.util.Objects;
/* loaded from: classes2.dex */
public class AdvAppModel extends AdvModel {
    private AppBaseModel mAppBaseInfo;

    public AdvAppModel(String id) {
        super(id);
    }

    public AppBaseModel getAppBaseInfo() {
        return this.mAppBaseInfo;
    }

    public void setAppBaseInfo(AppBaseModel appBaseInfo) {
        this.mAppBaseInfo = appBaseInfo;
    }

    public String getPackageName() {
        AppBaseModel appBaseModel = this.mAppBaseInfo;
        if (appBaseModel != null) {
            return appBaseModel.getPackageName();
        }
        return null;
    }

    public String getVersionName() {
        AppBaseModel appBaseModel = this.mAppBaseInfo;
        if (appBaseModel != null) {
            return appBaseModel.getVersionName();
        }
        return null;
    }

    public long getVersionCode() {
        AppBaseModel appBaseModel = this.mAppBaseInfo;
        if (appBaseModel != null) {
            return appBaseModel.getVersionCode();
        }
        return 0L;
    }

    public String getDownloadUrl() {
        AppBaseModel appBaseModel = this.mAppBaseInfo;
        if (appBaseModel != null) {
            return appBaseModel.getDownloadUrl();
        }
        return null;
    }

    public String getMd5() {
        AppBaseModel appBaseModel = this.mAppBaseInfo;
        if (appBaseModel != null) {
            return appBaseModel.getMd5();
        }
        return null;
    }

    public String getConfigUrl() {
        AppBaseModel appBaseModel = this.mAppBaseInfo;
        if (appBaseModel != null) {
            return appBaseModel.getConfigUrl();
        }
        return null;
    }

    public String getConfigMd5() {
        AppBaseModel appBaseModel = this.mAppBaseInfo;
        if (appBaseModel != null) {
            return appBaseModel.getConfigMd5();
        }
        return null;
    }

    public String getIconUrl() {
        AppBaseModel appBaseModel = this.mAppBaseInfo;
        if (appBaseModel != null) {
            return appBaseModel.getIconUrl();
        }
        return null;
    }

    @Override // com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && getClass() == o.getClass() && super.equals(o)) {
            return Objects.equals(this.mAppBaseInfo, ((AdvAppModel) o).mAppBaseInfo);
        }
        return false;
    }

    @Override // com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel
    public int hashCode() {
        return super.hashCode();
    }

    @Override // com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel
    public String toString() {
        StringBuilder sb = new StringBuilder("AdvAppModel{");
        sb.append("mAppBaseInfo=").append(this.mAppBaseInfo);
        sb.append('}');
        return sb.toString();
    }
}
