package com.xiaopeng.appstore.ui;

import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.common_ui.Constants;
import com.xiaopeng.appstore.ui.tabs.AppListTab;
import com.xiaopeng.appstore.ui.tabs.AppStoreTab;
import com.xiaopeng.appstore.ui.tabs.AppletListTab;
/* loaded from: classes2.dex */
public class CarTypeHomeTabAdapter extends HomeTabAdapter {
    private static final String TAG = "CarTypeHomeTabAdapter";
    private static final boolean hasApplet = true;

    public CarTypeHomeTabAdapter() {
        addTab(new AppletListTab());
        addTab(new AppListTab());
        addTab(new AppStoreTab());
    }

    @Override // com.xiaopeng.appstore.ui.HomeTabAdapter
    protected boolean initDefaultTab(String action) {
        int i;
        Logger.t(TAG).i("initDefaultTab action:" + action + " defaultIndex=" + this.mDefaultIndex, new Object[0]);
        if (Constants.ACTION_TO_APP_STORE.equals(action)) {
            i = 2;
        } else {
            i = "com.xiaopeng.intent.action.SHOW_APPLET_LIST".equals(action) ? 0 : 1;
        }
        if (this.mDefaultIndex != i) {
            this.mDefaultIndex = i;
            return true;
        }
        return false;
    }
}
