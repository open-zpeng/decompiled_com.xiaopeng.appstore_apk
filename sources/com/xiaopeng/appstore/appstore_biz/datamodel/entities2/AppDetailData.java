package com.xiaopeng.appstore.appstore_biz.datamodel.entities2;

import com.google.gson.annotations.SerializedName;
import com.irdeto.securesdk.core.SSUtils;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.libcommon.utils.GsonUtil;
import com.xiaopeng.appstore.libcommon.utils.NumberUtils;
import com.xiaopeng.libconfig.ipc.AccountConfig;
import java.io.Serializable;
import java.util.List;
/* loaded from: classes2.dex */
public class AppDetailData implements Serializable {
    @SerializedName(AccountConfig.KEY_APP_ID)
    private String mAppId;
    @SerializedName("app_name")
    private String mAppName;
    @SerializedName("brief_desc")
    private String mBriefDesc;
    @SerializedName("config_md5")
    private String mConfigMd5;
    @SerializedName("config_url")
    private String mConfigUrl;
    @SerializedName("dependent_apps")
    private List<DependentAppBean> mDependentApps;
    @SerializedName("detail_desc")
    private String mDetailDesc;
    @SerializedName("detail_img")
    private List<String> mDetailImg;
    @SerializedName("developer")
    private DeveloperData mDeveloper;
    @SerializedName("download_url")
    private String mDownloadUrl;
    @SerializedName("app_icons")
    private AppIconData mIconData;
    @SerializedName("is_force_update")
    private int mIsForceUpdate;
    @SerializedName("is_hidden")
    private int mIsHidden;
    @SerializedName("is_silent_install")
    private int mIsSilentInstall;
    @SerializedName("package_name")
    private String mPackageName;
    @SerializedName("apk_permissions")
    private String mPermissionData;
    @SerializedName("privacy_policy")
    private String mPrivatePolicyUrl;
    @SerializedName("prompt_text")
    private String mPromptText;
    @SerializedName("prompt_title")
    private String mPromptTitle;
    @SerializedName("size")
    private String mSize;
    @SerializedName("source")
    private String mSource;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("update_time")
    private String mUpdateTime;
    @SerializedName(SSUtils.O000OOOo)
    private String mVersionCode;
    @SerializedName("version_desc")
    private String mVersionDesc;
    @SerializedName("version_name")
    private String mVersionName;
    @SerializedName("md5")
    private String md5;

    public String getMd5() {
        return this.md5;
    }

    public String getPermissionData() {
        return this.mPermissionData;
    }

    public String getPrivatePolicyUrl() {
        return this.mPrivatePolicyUrl;
    }

    public String getAppId() {
        return this.mAppId;
    }

    public String getAppName() {
        return this.mAppName;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public AppIconData getIconData() {
        return this.mIconData;
    }

    public String getBriefDesc() {
        return this.mBriefDesc;
    }

    public String getDetailDesc() {
        return this.mDetailDesc;
    }

    public String getVersionCode() {
        return this.mVersionCode;
    }

    public String getVersionName() {
        return this.mVersionName;
    }

    public String getVersionDesc() {
        return this.mVersionDesc;
    }

    public String getUpdateTime() {
        return this.mUpdateTime;
    }

    public List<String> getDetailImg() {
        return this.mDetailImg;
    }

    public String getDownloadUrl() {
        return LogicUtils.replaceHttp(this.mDownloadUrl);
    }

    public String getConfigUrl() {
        return LogicUtils.replaceHttp(this.mConfigUrl);
    }

    public String getSize() {
        return this.mSize;
    }

    public DeveloperData getDeveloper() {
        return this.mDeveloper;
    }

    public String getSource() {
        return this.mSource;
    }

    public boolean isHidden() {
        return this.mIsHidden == 1;
    }

    public boolean isSilentInstall() {
        return this.mIsSilentInstall == 1;
    }

    public boolean isForceUninstall() {
        return NumberUtils.stringToInt(this.mStatus) == 5;
    }

    public List<DependentAppBean> getDependentApps() {
        return this.mDependentApps;
    }

    public String getStatus() {
        return this.mStatus;
    }

    public String getPromptTitle() {
        return this.mPromptTitle;
    }

    public String getPromptText() {
        return this.mPromptText;
    }

    public boolean isForceUpdate() {
        return this.mIsForceUpdate == 1;
    }

    public String getConfigMd5() {
        return this.mConfigMd5;
    }

    public void setAppId(String id) {
        this.mAppId = id;
    }

    public void setAppName(String name) {
        this.mAppName = name;
    }

    public void setBriefDesc(String desc) {
        this.mBriefDesc = desc;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.mDownloadUrl = downloadUrl;
    }

    public void setConfigUrl(String configUrl) {
        this.mConfigUrl = configUrl;
    }

    public void setAppIconData(AppIconData data) {
        this.mIconData = data;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public void setSize(String size) {
        this.mSize = size;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public void setUpdateTime(String updateTime) {
        this.mUpdateTime = updateTime;
    }

    public void setVersionCode(String versionCode) {
        this.mVersionCode = versionCode;
    }

    public void setVersionName(String versionName) {
        this.mVersionName = versionName;
    }

    public String toString() {
        return GsonUtil.toJson(this);
    }
}
