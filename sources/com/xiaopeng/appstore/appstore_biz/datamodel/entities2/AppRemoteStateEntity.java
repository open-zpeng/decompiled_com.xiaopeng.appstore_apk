package com.xiaopeng.appstore.appstore_biz.datamodel.entities2;
/* loaded from: classes2.dex */
public class AppRemoteStateEntity {
    public static final String TABLE_NAME = "AppRemoteStates";
    private int mHasUpdate;
    private final String mPackageName;
    private String mPromptText;
    private String mPromptTitle;
    private int mState;

    public AppRemoteStateEntity(String packageName) {
        this.mPackageName = packageName;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int state) {
        this.mState = state;
    }

    public int getHasUpdate() {
        return this.mHasUpdate;
    }

    public void setHasUpdate(int hasUpdate) {
        this.mHasUpdate = hasUpdate;
    }

    public void setHasUpdate(boolean hasUpdate) {
        this.mHasUpdate = hasUpdate ? 1 : 0;
    }

    public String getPromptTitle() {
        return this.mPromptTitle;
    }

    public void setPromptTitle(String mPromptTitle) {
        this.mPromptTitle = mPromptTitle;
    }

    public String getPromptText() {
        return this.mPromptText;
    }

    public void setPromptText(String mPromptText) {
        this.mPromptText = mPromptText;
    }

    public String toString() {
        return "RmSt{pn='" + this.mPackageName + "', st=" + this.mState + ", update=" + this.mHasUpdate + ", pt=" + this.mPromptTitle + ", pc=" + this.mPromptText + '}';
    }
}
