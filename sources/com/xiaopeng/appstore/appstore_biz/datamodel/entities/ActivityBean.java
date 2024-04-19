package com.xiaopeng.appstore.appstore_biz.datamodel.entities;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.lib.apirouter.ClientConstants;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.List;
import java.util.Map;
/* loaded from: classes2.dex */
public class ActivityBean {
    @SerializedName(ClientConstants.BINDER.SCHEME)
    private ActivityContentBean mContent;
    @SerializedName("id")
    private String mId;
    @SerializedName(VuiConstants.ELEMENT_TYPE)
    private String mType;

    public String getId() {
        return this.mId;
    }

    public String getType() {
        return this.mType;
    }

    public ActivityContentBean getContent() {
        return this.mContent;
    }

    /* loaded from: classes2.dex */
    public static class ActivityContentBean {
        @SerializedName("desc")
        private String mDesc;
        @SerializedName("image")
        private String mImage;
        @SerializedName("itemDetailList")
        private Map<String, ItemDetailBean> mItemDetailList;
        @SerializedName("packageList")
        private List<PackageBean> mPackageList;
        @SerializedName("title")
        private String mTitle;

        public String getImage() {
            return this.mImage;
        }

        public String getTitle() {
            return this.mTitle;
        }

        public String getDesc() {
            return this.mDesc;
        }

        public List<PackageBean> getPackageList() {
            return this.mPackageList;
        }

        public Map<String, ItemDetailBean> getItemDetailList() {
            return this.mItemDetailList;
        }
    }
}
