package com.xiaopeng.appstore.appstore_biz.model;

import java.util.List;
import java.util.Map;
@Deprecated
/* loaded from: classes2.dex */
public class HomeModel {
    private Map<String, AppModel> mAllAppMap;
    private List<BannerModel> mBannerList;
    private List<PackageModel> mPackageList;
    private PackageModel mTopPackage;

    public List<BannerModel> getBannerList() {
        return this.mBannerList;
    }

    public void setBannerList(List<BannerModel> bannerList) {
        this.mBannerList = bannerList;
    }

    public PackageModel getTopPackage() {
        return this.mTopPackage;
    }

    public void setTopPackage(PackageModel topPackage) {
        this.mTopPackage = topPackage;
    }

    public List<PackageModel> getPackageList() {
        return this.mPackageList;
    }

    public void setPackageList(List<PackageModel> packageList) {
        this.mPackageList = packageList;
    }

    public Map<String, AppModel> getAllAppMap() {
        return this.mAllAppMap;
    }

    public void setAllAppMap(Map<String, AppModel> allAppMap) {
        this.mAllAppMap = allAppMap;
    }

    /* loaded from: classes2.dex */
    public static class BannerModel {
        private List<BannerData> mData;
        private int mType;

        public int getType() {
            return this.mType;
        }

        public void setType(int type) {
            this.mType = type;
        }

        public List<BannerData> getData() {
            return this.mData;
        }

        public void setData(List<BannerData> data) {
            this.mData = data;
        }
    }

    /* loaded from: classes2.dex */
    public static class BannerData {
        public static final int BANNER_TYPE_ACTIVITY = 1;
        public static final int BANNER_TYPE_APP_DETAIL = 2;
        private AppModel mAppModel;
        private String mId;
        private String mImageUrl;
        private int mType;

        public int getType() {
            return this.mType;
        }

        public void setType(int type) {
            this.mType = type;
        }

        public String getImageUrl() {
            return this.mImageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.mImageUrl = imageUrl;
        }

        public String getId() {
            return this.mId;
        }

        public void setId(String id) {
            this.mId = id;
        }

        public AppModel getAppModel() {
            return this.mAppModel;
        }

        public void setAppModel(AppModel appModel) {
            this.mAppModel = appModel;
        }
    }
}
