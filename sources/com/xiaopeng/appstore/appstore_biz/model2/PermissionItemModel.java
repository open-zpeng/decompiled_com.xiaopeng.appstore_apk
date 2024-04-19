package com.xiaopeng.appstore.appstore_biz.model2;

import java.io.Serializable;
/* loaded from: classes2.dex */
public class PermissionItemModel implements Serializable {
    private String mContent;
    private boolean mIsShowTitle;
    private String mTitleName;

    public PermissionItemModel() {
    }

    public PermissionItemModel(boolean isShowTitle, String titleName, String content) {
        this.mIsShowTitle = isShowTitle;
        this.mTitleName = titleName;
        this.mContent = content;
    }

    public void setShowTitle(boolean showTitle) {
        this.mIsShowTitle = showTitle;
    }

    public void setTitleName(String titleName) {
        this.mTitleName = titleName;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public boolean isShowTitle() {
        return this.mIsShowTitle;
    }

    public String getTitleName() {
        return this.mTitleName;
    }

    public String getContent() {
        return this.mContent;
    }
}
