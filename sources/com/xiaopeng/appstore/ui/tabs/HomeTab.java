package com.xiaopeng.appstore.ui.tabs;

import androidx.fragment.app.Fragment;
/* loaded from: classes2.dex */
public interface HomeTab {
    Fragment getPageFragment();

    CharSequence getTitle();

    void initFragment();

    void initTitle();

    void setFragment(Fragment fragment);
}
