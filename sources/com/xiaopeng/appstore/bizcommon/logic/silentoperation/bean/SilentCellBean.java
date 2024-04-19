package com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.irdeto.securesdk.core.SSUtils;
import com.xiaopeng.appstore.libcommon.utils.GsonUtil;
import com.xiaopeng.libconfig.ipc.AccountConfig;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.ArrayList;
import java.util.Objects;
/* loaded from: classes2.dex */
public class SilentCellBean implements Parcelable {
    public static final Parcelable.Creator<SilentCellBean> CREATOR = new Parcelable.Creator<SilentCellBean>() { // from class: com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.SilentCellBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SilentCellBean createFromParcel(Parcel source) {
            return new SilentCellBean(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SilentCellBean[] newArray(int size) {
            return new SilentCellBean[size];
        }
    };
    @SerializedName("url")
    private String mApkFileUrl;
    @SerializedName(AccountConfig.KEY_APP_ID)
    private String mAppId;
    @SerializedName("config_url")
    private String mConfigUrl;
    @SerializedName("md5")
    private String mMd5;
    @SerializedName(VuiConstants.ELEMENT_TYPE)
    private int mOperation;
    @SerializedName(SSUtils.O000OOOo)
    private String mOperationAppVersionCode;
    @SerializedName("package_name")
    private String mOperationPackageName;
    @SerializedName("dependent_apps")
    private ArrayList<SilentCellBean> mSilentCellBeans;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SilentCellBean() {
    }

    public String toString() {
        return GsonUtil.toJson(this);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SilentCellBean silentCellBean = (SilentCellBean) o;
        return this.mOperation == silentCellBean.mOperation && Objects.equals(this.mAppId, silentCellBean.mAppId) && Objects.equals(this.mApkFileUrl, silentCellBean.mApkFileUrl) && Objects.equals(this.mOperationAppVersionCode, silentCellBean.mOperationAppVersionCode) && Objects.equals(this.mOperationPackageName, silentCellBean.mOperationPackageName) && Objects.equals(this.mConfigUrl, silentCellBean.mConfigUrl) && Objects.equals(this.mMd5, silentCellBean.mMd5) && Objects.equals(this.mSilentCellBeans, silentCellBean.mSilentCellBeans);
    }

    public String getConfigUrl() {
        return this.mConfigUrl;
    }

    public void setConfigUrl(String configUrl) {
        this.mConfigUrl = configUrl;
    }

    public String getAppId() {
        return this.mAppId;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mOperation), this.mAppId, this.mApkFileUrl, this.mOperationAppVersionCode, this.mOperationPackageName, this.mConfigUrl, this.mMd5, this.mSilentCellBeans);
    }

    public String getMd5() {
        return this.mMd5;
    }

    public void setMd5(String md5) {
        this.mMd5 = md5;
    }

    public void setAppId(String appId) {
        this.mAppId = appId;
    }

    public ArrayList<SilentCellBean> getSilentCellBeans() {
        return this.mSilentCellBeans;
    }

    public void setSilentCellBeans(ArrayList<SilentCellBean> silentCellBeans) {
        this.mSilentCellBeans = silentCellBeans;
    }

    public String getOperationAppVersionCode() {
        return this.mOperationAppVersionCode;
    }

    public void setOperationAppVersionCode(String operationAppVersionCode) {
        this.mOperationAppVersionCode = operationAppVersionCode;
    }

    public int getOperation() {
        return this.mOperation;
    }

    public void setOperation(int operation) {
        this.mOperation = operation;
    }

    public String getOperationPackageName() {
        return this.mOperationPackageName;
    }

    public void setOperationPackageName(String operationPackageName) {
        this.mOperationPackageName = operationPackageName;
    }

    public String getApkFileUrl() {
        return this.mApkFileUrl;
    }

    public void setApkFileUrl(String apkFileUrl) {
        this.mApkFileUrl = apkFileUrl;
    }

    public static Parcelable.Creator<SilentCellBean> getCREATOR() {
        return CREATOR;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mOperation);
        dest.writeString(this.mAppId);
        dest.writeString(this.mApkFileUrl);
        dest.writeString(this.mOperationAppVersionCode);
        dest.writeString(this.mOperationPackageName);
        dest.writeString(this.mConfigUrl);
        dest.writeString(this.mMd5);
        dest.writeTypedList(this.mSilentCellBeans);
    }

    protected SilentCellBean(Parcel in) {
        this.mOperation = in.readInt();
        this.mAppId = in.readString();
        this.mApkFileUrl = in.readString();
        this.mOperationAppVersionCode = in.readString();
        this.mOperationPackageName = in.readString();
        this.mConfigUrl = in.readString();
        this.mMd5 = in.readString();
        this.mSilentCellBeans = in.createTypedArrayList(CREATOR);
    }
}
