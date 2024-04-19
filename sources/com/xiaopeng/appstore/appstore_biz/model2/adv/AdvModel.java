package com.xiaopeng.appstore.appstore_biz.model2.adv;

import java.util.List;
import java.util.Objects;
/* loaded from: classes2.dex */
public class AdvModel {
    private final String mDataId;
    private String mDesc;
    private List<String> mImageList;
    private String mTitle;
    private int mViewType;

    public AdvModel(String id) {
        this.mDataId = id;
    }

    public String getDataId() {
        return this.mDataId;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDesc() {
        return this.mDesc;
    }

    public void setDesc(String desc) {
        this.mDesc = desc;
    }

    public List<String> getImageList() {
        return this.mImageList;
    }

    public void setImageList(List<String> imageList) {
        this.mImageList = imageList;
    }

    public int getViewType() {
        return this.mViewType;
    }

    public void setViewType(int viewType) {
        this.mViewType = viewType;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof AdvModel) {
            AdvModel advModel = (AdvModel) o;
            return this.mViewType == advModel.mViewType && Objects.equals(this.mDataId, advModel.mDataId) && Objects.equals(this.mTitle, advModel.mTitle) && Objects.equals(this.mDesc, advModel.mDesc) && Objects.equals(this.mImageList, advModel.mImageList);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mDataId, this.mTitle, this.mDesc, this.mImageList, Integer.valueOf(this.mViewType));
    }

    public String toString() {
        return "AdvModel{id='" + this.mDataId + "', title='" + this.mTitle + "', desc='" + this.mDesc + "', img=" + this.mImageList + ", type=" + this.mViewType + '}';
    }
}
