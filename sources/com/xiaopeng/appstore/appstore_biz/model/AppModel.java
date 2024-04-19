package com.xiaopeng.appstore.appstore_biz.model;

import com.xiaopeng.appstore.appstore_biz.datamodel.entities.ItemBean;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities.ItemDetailBean;
import com.xiaopeng.appstore.libcommon.utils.NumberUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Deprecated
/* loaded from: classes2.dex */
public class AppModel extends ItemModel {
    private List<String> mDescImageList;
    private DeveloperModel mDeveloper;
    private String mDownloadSize;
    private String mItemDesc;
    private String mPackageName;
    private String mTitle;
    private String mVersionCode;
    private long mVersionDate;
    private String mVersionDesc;
    private String mVersionName;

    public static AppModel from(ItemBean bean) {
        AppModel appModel = new AppModel();
        appModel.mPackageName = bean.getPackageName();
        appModel.mLabel = bean.getLabel();
        appModel.mItemIntro = bean.getItemIntro();
        appModel.mIconUrl = bean.getIconUrl();
        appModel.mDownloadUrl = bean.getDownloadUrl();
        if (bean instanceof ItemDetailBean) {
            ItemDetailBean itemDetailBean = (ItemDetailBean) bean;
            appModel.mTitle = itemDetailBean.getTitle();
            appModel.mItemDesc = itemDetailBean.getItemDesc();
            appModel.setDescImageList(itemDetailBean.getDescImageList() != null ? new ArrayList(itemDetailBean.getDescImageList()) : null);
            appModel.mDownloadSize = String.format("%sMB", Long.valueOf(itemDetailBean.getDownloadSize() / 1000));
            DeveloperModel developerModel = new DeveloperModel();
            developerModel.mId = itemDetailBean.getDeveloper().getId();
            developerModel.mName = itemDetailBean.getDeveloper().getName();
            appModel.mDeveloper = developerModel;
        }
        appModel.setVersionCode(bean.getVersionCode());
        appModel.setVersionName(bean.getVersionName());
        appModel.setVersionDate(NumberUtils.stringToLong(bean.getVersionDate()));
        appModel.setVersionDesc(bean.getVersionDesc());
        return appModel;
    }

    @Override // com.xiaopeng.appstore.appstore_biz.model.ItemModel
    public String getKey() {
        return this.mPackageName;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getItemDesc() {
        return this.mItemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.mItemDesc = itemDesc;
    }

    public String getDownloadSize() {
        return this.mDownloadSize;
    }

    public void setDownloadSize(String downloadSize) {
        this.mDownloadSize = downloadSize;
    }

    public List<String> getDescImageList() {
        return this.mDescImageList;
    }

    public void setDescImageList(List<String> descImageList) {
        this.mDescImageList = descImageList;
    }

    public DeveloperModel getDeveloper() {
        return this.mDeveloper;
    }

    public void setDeveloper(DeveloperModel developer) {
        this.mDeveloper = developer;
    }

    public String getVersionName() {
        return this.mVersionName;
    }

    public void setVersionName(String versionName) {
        this.mVersionName = versionName;
    }

    public String getVersionCode() {
        return this.mVersionCode;
    }

    public void setVersionCode(String versionCode) {
        this.mVersionCode = versionCode;
    }

    public long getVersionDate() {
        return this.mVersionDate;
    }

    public void setVersionDate(long versionDate) {
        this.mVersionDate = versionDate;
    }

    public String getVersionDesc() {
        return this.mVersionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.mVersionDesc = versionDesc;
    }

    /* loaded from: classes2.dex */
    public static class DeveloperModel implements Serializable {
        private String mId;
        private String mName;

        public String getId() {
            return this.mId;
        }

        public String getName() {
            return this.mName;
        }

        public void setId(String id) {
            this.mId = id;
        }

        public void setName(String name) {
            this.mName = name;
        }
    }
}
