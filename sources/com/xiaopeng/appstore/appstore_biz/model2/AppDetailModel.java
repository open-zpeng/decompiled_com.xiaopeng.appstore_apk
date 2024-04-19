package com.xiaopeng.appstore.appstore_biz.model2;

import java.util.List;
import java.util.Objects;
/* loaded from: classes2.dex */
public class AppDetailModel {
    public static final int STATUS_DISK_INSTALL = 3;
    public static final int STATUS_DRAFT = 0;
    public static final int STATUS_FORCE_UNINSTALL = 5;
    public static final int STATUS_OFF_SHELF = 2;
    public static final int STATUS_ON_SHELF = 1;
    public static final int STATUS_SUSPEND = 4;
    public static final int STATUS_TIPS = 6;
    private String mAppId;
    private String mAppName;
    private String mAppSource;
    private String mBriefDesc;
    private String mConfigMd5;
    private String mConfigUrl;
    private List<DependentAppModel> mDependentApps;
    private String mDetailDesc;
    private List<String> mDetailImg;
    private String mDeveloperName;
    private String mDownloadUrl;
    private String mIconUrl;
    private boolean mIsSuspended;
    private String mMd5;
    private String mPackageName;
    private PermissionViewData mPermissionViewData;
    private PrivatePolicyModel mPolicyModel;
    private String mSize;
    private int mStatus;
    private long mVersionCode;
    private long mVersionDate;
    private String mVersionDesc;
    private String mVersionName;

    public PrivatePolicyModel getPolicyModel() {
        return this.mPolicyModel;
    }

    public void setPolicyModel(PrivatePolicyModel policyModel) {
        this.mPolicyModel = policyModel;
    }

    public PermissionViewData getPermissionModel() {
        return this.mPermissionViewData;
    }

    public void setPermissionModel(PermissionViewData permissionViewData) {
        this.mPermissionViewData = permissionViewData;
    }

    public String getAppSource() {
        return this.mAppSource;
    }

    public void setAppSource(String appSource) {
        this.mAppSource = appSource;
    }

    public String getAppId() {
        return this.mAppId;
    }

    public void setAppId(String appId) {
        this.mAppId = appId;
    }

    public String getAppName() {
        return this.mAppName;
    }

    public void setAppName(String appName) {
        this.mAppName = appName;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public String getIconUrl() {
        return this.mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public String getBriefDesc() {
        return this.mBriefDesc;
    }

    public void setBriefDesc(String briefDesc) {
        this.mBriefDesc = briefDesc;
    }

    public String getDetailDesc() {
        return this.mDetailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.mDetailDesc = detailDesc;
    }

    public long getVersionCode() {
        return this.mVersionCode;
    }

    public void setVersionCode(long versionCode) {
        this.mVersionCode = versionCode;
    }

    public String getVersionName() {
        return this.mVersionName;
    }

    public void setVersionName(String versionName) {
        this.mVersionName = versionName;
    }

    public String getVersionDesc() {
        return this.mVersionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.mVersionDesc = versionDesc;
    }

    public long getVersionDate() {
        return this.mVersionDate;
    }

    public void setVersionDate(long versionDate) {
        this.mVersionDate = versionDate;
    }

    public List<String> getDetailImg() {
        return this.mDetailImg;
    }

    public void setDetailImg(List<String> detailImg) {
        this.mDetailImg = detailImg;
    }

    public String getDownloadUrl() {
        return this.mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.mDownloadUrl = downloadUrl;
    }

    public String getConfigUrl() {
        return this.mConfigUrl;
    }

    public void setConfigUrl(String configUrl) {
        this.mConfigUrl = configUrl;
    }

    public String getSize() {
        return this.mSize;
    }

    public void setSize(String size) {
        this.mSize = size;
    }

    public String getDeveloperName() {
        return this.mDeveloperName;
    }

    public List<DependentAppModel> getDependentApps() {
        return this.mDependentApps;
    }

    public void setDependentApps(List<DependentAppModel> dependentApps) {
        this.mDependentApps = dependentApps;
    }

    public void setDeveloperName(String developerName) {
        this.mDeveloperName = developerName;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void setStatus(int status) {
        this.mStatus = status;
        this.mIsSuspended = status == 4 || status == 6;
    }

    public String getMd5() {
        return this.mMd5;
    }

    public void setMd5(String md5) {
        this.mMd5 = md5;
    }

    public String getConfigMd5() {
        return this.mConfigMd5;
    }

    public void setConfigMd5(String configMd5) {
        this.mConfigMd5 = configMd5;
    }

    public boolean isSuspended() {
        return this.mIsSuspended;
    }

    public static boolean appEquals(AppDetailModel o1, AppDetailModel o2) {
        if (o1 == o2) {
            return true;
        }
        if (o2 == null || o1.getClass() != o2.getClass()) {
            return false;
        }
        return o1.getVersionDate() == o2.getVersionDate() && Objects.equals(o1.getAppId(), o2.getAppId()) && Objects.equals(o1.getAppName(), o2.getAppName()) && Objects.equals(o1.getPackageName(), o2.getPackageName()) && Objects.equals(o1.getIconUrl(), o2.getIconUrl()) && Objects.equals(o1.getBriefDesc(), o2.getBriefDesc()) && Objects.equals(o1.getDetailDesc(), o2.getDetailDesc()) && o1.getVersionCode() == o2.getVersionCode() && Objects.equals(o1.getVersionName(), o2.getVersionName()) && Objects.equals(o1.getVersionDesc(), o2.getVersionDesc()) && Objects.equals(o1.getDetailImg(), o2.getDetailImg()) && Objects.equals(o1.getDownloadUrl(), o2.getDownloadUrl()) && Objects.equals(o1.getSize(), o2.getSize()) && Objects.equals(o1.getAppSource(), o2.getAppSource()) && Objects.equals(o1.getDeveloperName(), o2.getDeveloperName()) && o1.getStatus() == o2.getStatus();
    }
}
