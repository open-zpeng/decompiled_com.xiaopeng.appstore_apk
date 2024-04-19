package com.xiaopeng.appstore.ui.tabs;

import androidx.fragment.app.Fragment;
/* loaded from: classes2.dex */
public abstract class HomeTabAbs implements HomeTab {
    protected Fragment mFragment;
    protected CharSequence mTitle;

    public HomeTabAbs() {
        initTitle();
    }

    @Override // com.xiaopeng.appstore.ui.tabs.HomeTab
    public CharSequence getTitle() {
        return this.mTitle;
    }

    @Override // com.xiaopeng.appstore.ui.tabs.HomeTab
    public Fragment getPageFragment() {
        return this.mFragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setTitle(CharSequence title) {
        this.mTitle = title;
    }

    @Override // com.xiaopeng.appstore.ui.tabs.HomeTab
    public void setFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    public String toString() {
        StringBuilder append = new StringBuilder().append("HomeTab{title=").append((Object) this.mTitle).append(", frag=");
        Fragment fragment = this.mFragment;
        return append.append(fragment != null ? Integer.valueOf(fragment.hashCode()) : "null").append('}').toString();
    }
}
