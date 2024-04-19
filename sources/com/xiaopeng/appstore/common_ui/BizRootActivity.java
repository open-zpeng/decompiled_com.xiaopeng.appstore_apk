package com.xiaopeng.appstore.common_ui;

import android.content.Intent;
/* loaded from: classes.dex */
public interface BizRootActivity {

    /* loaded from: classes.dex */
    public interface OnActivityEventListener {
        void onPageSelected(int index);

        default void onTabInitRequest() {
        }

        void onTabSelected(int index, Class<?> fragmentClz);

        void onWindowClose();

        void onWindowReset();
    }

    /* loaded from: classes.dex */
    public enum TabDefine {
        Applet,
        AppList,
        AppStore
    }

    void addOnActivityListener(OnActivityEventListener listener);

    void backToHome();

    void closeWindow();

    Class<?> getCurrentSelected();

    int getIndex();

    int getIndexOfTab(TabDefine tabDefine);

    void handleStart(Intent intent);

    boolean isClose();

    void moveTaskToBack();

    void notifyTabInit(TabDefine tab);

    void removeActivityListener(OnActivityEventListener listener);

    void setCanNavBack(boolean canNavBack);

    void setTitleVisible(boolean visible);
}
