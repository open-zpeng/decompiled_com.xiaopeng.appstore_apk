package com.xiaopeng.appstore.appstore_biz.datamodel.entities;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.List;
import java.util.Map;
/* loaded from: classes2.dex */
public class HomeBean {
    @SerializedName("bannerList")
    private List<BannerBean> mBannerList;
    @SerializedName("itemDetailList")
    private Map<String, ItemDetailBean> mItemDetailList;
    @SerializedName("packageList")
    private List<PackageBean> mPackageList;
    @SerializedName("topPackage")
    private PackageBean mTopPackage;

    public List<BannerBean> getBannerList() {
        return this.mBannerList;
    }

    public PackageBean getTopPackage() {
        return this.mTopPackage;
    }

    public List<PackageBean> getPackageList() {
        return this.mPackageList;
    }

    public Map<String, ItemDetailBean> getItemDetailList() {
        return this.mItemDetailList;
    }

    /* loaded from: classes2.dex */
    public static class BannerBean {
        @SerializedName("data")
        private List<BannerDataBean> mData;
        @SerializedName(VuiConstants.ELEMENT_TYPE)
        private int mType;

        public int getType() {
            return this.mType;
        }

        public List<BannerDataBean> getData() {
            return this.mData;
        }
    }

    /* loaded from: classes2.dex */
    public static class BannerDataBean {
        @SerializedName("id")
        private String mId;
        @SerializedName("imageUrl")
        private String mImageUrl;
        @SerializedName(VuiConstants.ELEMENT_TYPE)
        private String mType;

        public String getType() {
            return this.mType;
        }

        public String getImageUrl() {
            return this.mImageUrl;
        }

        public String getId() {
            return this.mId;
        }
    }
}
