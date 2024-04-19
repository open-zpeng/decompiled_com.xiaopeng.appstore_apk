package com.xiaopeng.appstore.appstore_biz.model2;
/* loaded from: classes2.dex */
public class AppStateContainer {
    public static final int STATE_FORCE_UPDATE = 2;
    public static final int STATE_SUSPENDED = 1;
    public static final int STATE_TIPS = 3;
    private boolean mHasUpdate;
    private String mPackageName;
    private String mPromptText;
    private String mPromptTitle;
    private String mRemoteVersionCode;
    private String mRemoteVersionName;
    private int mState;

    public static AppStateContainer create(String packageName) {
        AppStateContainer appStateContainer = new AppStateContainer();
        appStateContainer.mPackageName = packageName;
        return appStateContainer;
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

    public boolean isHasUpdate() {
        return this.mHasUpdate;
    }

    public void setHasUpdate(boolean hasUpdate) {
        this.mHasUpdate = hasUpdate;
    }

    public String getRemoteVersionCode() {
        return this.mRemoteVersionCode;
    }

    public void setRemoteVersionCode(String remoteVersionCode) {
        this.mRemoteVersionCode = remoteVersionCode;
    }

    public String getRemoteVersionName() {
        return this.mRemoteVersionName;
    }

    public void setRemoteVersionName(String remoteVersionName) {
        this.mRemoteVersionName = remoteVersionName;
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
        return "AppStateContainer{pn='" + this.mPackageName + "', state=" + this.mState + ", update=" + this.mHasUpdate + ", vc='" + this.mRemoteVersionCode + "', vn='" + this.mRemoteVersionName + "'}";
    }
}
