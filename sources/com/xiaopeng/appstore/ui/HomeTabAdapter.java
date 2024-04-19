package com.xiaopeng.appstore.ui;

import android.content.Intent;
import androidx.fragment.app.Fragment;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.AppUtils;
import com.xiaopeng.appstore.bizcommon.utils.DeviceUtils;
import com.xiaopeng.appstore.ui.tabs.HomeTab;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/* loaded from: classes2.dex */
public abstract class HomeTabAdapter {
    protected static final String ACTION_APPLET_LIST = "com.xiaopeng.intent.action.SHOW_APPLET_LIST";
    protected static final String ACTION_APP_LIST = "com.xiaopeng.intent.action.SHOW_APP_LIST";
    protected static final String ACTION_APP_STORE = "com.xiaopeng.intent.action.SHOW_APP_STORE";
    private static final String TAG = "HomeTabAdapter";
    protected int mDefaultIndex = 0;
    private List<HomeTab> mTabList = new LinkedList();

    /* JADX INFO: Access modifiers changed from: protected */
    public HomeTabAdapter() {
        initTitles();
    }

    public void restoreTabs(ArrayList<Fragment> fragments) {
        int size = fragments.size();
        for (int i = 0; i < this.mTabList.size(); i++) {
            if (i >= size) {
                Logger.t(TAG).w("restoreTabs, no fragment offered for this tab:" + i, new Object[0]);
                return;
            }
            HomeTab homeTab = this.mTabList.get(i);
            Fragment fragment = fragments.get(i);
            Logger.t(TAG).i("restore tab:" + homeTab + ", with frag:" + fragment.hashCode(), new Object[0]);
            homeTab.setFragment(fragment);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public <T extends HomeTab> void addTab(T tab) {
        if (tab == null) {
            throw new IllegalArgumentException("Cannot add tab which is null.");
        }
        this.mTabList.add(tab);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCount() {
        return this.mTabList.size();
    }

    public boolean handleStart(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            Logger.t(TAG).i("handleStart action=" + action, new Object[0]);
            if (AppUtils.isGoToDetail(intent.getData())) {
                action = "com.xiaopeng.intent.action.SHOW_APP_STORE";
            }
            return initDefaultTab(action);
        }
        return initDefaultTab(null);
    }

    protected boolean initDefaultTab(String action) {
        this.mDefaultIndex = 0;
        return true;
    }

    public void initTitles() {
        for (HomeTab homeTab : this.mTabList) {
            homeTab.initTitle();
        }
    }

    public int getDefaultSelected() {
        return this.mDefaultIndex;
    }

    public CharSequence getTitle(int position) {
        if (position >= getCount() || position < 0) {
            return null;
        }
        return this.mTabList.get(position).getTitle();
    }

    public int getIndexOf(Class<?> tabClz) {
        int i = -1;
        for (HomeTab homeTab : this.mTabList) {
            i++;
            if (homeTab.getClass().equals(tabClz)) {
                break;
            }
        }
        return i;
    }

    @Deprecated
    public Fragment getFragment(int position) {
        return getOrCreateFragment(position, true);
    }

    public Fragment getOrCreateFragment(int position, boolean createIfNull) {
        if (position >= getCount() || position < 0) {
            return null;
        }
        HomeTab homeTab = this.mTabList.get(position);
        Fragment pageFragment = homeTab.getPageFragment();
        if (pageFragment == null && createIfNull) {
            Logger.t(TAG).i("getOrCreateFragment, CREATE, pos:" + position, new Object[0]);
            homeTab.initFragment();
            return homeTab.getPageFragment();
        }
        return pageFragment;
    }

    /* loaded from: classes2.dex */
    interface Factory {
        HomeTabAdapter build();

        static Factory create() {
            return DeviceUtils.isInter() ? new InterHomeTabAdapterFactory() : new HomeTabAdapterFactory();
        }
    }
}
