package com.xiaopeng.appstore.appstore_biz.datamodel.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;
/* loaded from: classes2.dex */
public final class ItemDetailBean extends ItemBean {
    @SerializedName("descImageList")
    private List<String> mDescImageList;
    @SerializedName("developer")
    private DeveloperBean mDeveloper;
    @SerializedName("downloadSize")
    private long mDownloadSize;
    @SerializedName("itemDesc")
    private String mItemDesc;
    @SerializedName("title")
    private String mTitle;

    public String getTitle() {
        return this.mTitle;
    }

    public String getItemDesc() {
        return this.mItemDesc;
    }

    public long getDownloadSize() {
        return this.mDownloadSize;
    }

    public List<String> getDescImageList() {
        return this.mDescImageList;
    }

    public DeveloperBean getDeveloper() {
        return this.mDeveloper;
    }

    /* loaded from: classes2.dex */
    public static class DeveloperBean {
        @SerializedName("id")
        private String mId;
        @SerializedName("name")
        private String mName;

        public String getId() {
            return this.mId;
        }

        public String getName() {
            return this.mName;
        }
    }
}
