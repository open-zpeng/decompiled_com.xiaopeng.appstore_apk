package com.xiaopeng.appstore.appstore_biz.model2.adv;

import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel;
import java.util.Arrays;
/* loaded from: classes2.dex */
public class AdvContainerModel<T extends AdvModel> {
    public static final int VIEW_TYPE_BANNER = 2;
    public static final int VIEW_TYPE_FOOTER = 15;
    @Deprecated
    public static final int VIEW_TYPE_GRID_CONTAINER = 6;
    public static final int VIEW_TYPE_GRID_ITEM = 7;
    @Deprecated
    public static final int VIEW_TYPE_GRID_NARROW_CONTAINER = 12;
    public static final int VIEW_TYPE_GRID_NARROW_ITEM = 13;
    @Deprecated
    public static final int VIEW_TYPE_GRID_WIDE_CONTAINER = 8;
    public static final int VIEW_TYPE_GRID_WIDE_ITEM = 9;
    public static final int VIEW_TYPE_HORIZONTAL_CARD_CONTAINER = 5;
    public static final int VIEW_TYPE_HORIZONTAL_ITEM_CONTAINER = 10;
    public static final int VIEW_TYPE_IMAGE_LIST = 3;
    public static final int VIEW_TYPE_NORMAL_CARD_CONTAINER = 11;
    public static final int VIEW_TYPE_PACKAGE_TITLE = 14;
    public static final int VIEW_TYPE_PAGE = 1;
    public static final int VIEW_TYPE_SCENE_HEAD = 4;
    private T mData;
    private int mType;

    public int getType() {
        return this.mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public T getData() {
        return this.mData;
    }

    public void setData(T data) {
        this.mData = data;
    }

    public boolean equals(Object obj) {
        if (obj instanceof AdvContainerModel) {
            AdvContainerModel advContainerModel = (AdvContainerModel) obj;
            if (this.mType == advContainerModel.mType) {
                T t = this.mData;
                if (t != null) {
                    return t.equals(advContainerModel.mData);
                }
                return advContainerModel.mData == null;
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.mType), Integer.valueOf(this.mData.hashCode())});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AdvContainerModel{");
        sb.append("mType=").append(this.mType);
        sb.append(", mData=").append(this.mData);
        sb.append('}');
        return sb.toString();
    }
}
