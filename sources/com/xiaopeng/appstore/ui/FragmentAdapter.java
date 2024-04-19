package com.xiaopeng.appstore.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.Set;
/* loaded from: classes2.dex */
public class FragmentAdapter extends FragmentPagerAdapter {
    private static final String TAG = "FragmentAdapter";
    private final FragmentManager mFragmentManager;
    private ArrayList<Fragment> mFragments;
    private final HomeTabAdapter mHomeTabAdapter;

    public FragmentAdapter(FragmentManager fm, HomeTabAdapter adapter) {
        super(fm, 1);
        this.mFragments = new ArrayList<>();
        this.mFragmentManager = fm;
        this.mHomeTabAdapter = adapter;
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter, androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public Fragment getItem(int position) {
        Fragment orCreateFragment = this.mHomeTabAdapter.getOrCreateFragment(position, true);
        Logger.t(TAG).i("getItem, pos:" + position + ", frag:" + orCreateFragment.getClass().getSimpleName() + ", " + orCreateFragment.hashCode(), new Object[0]);
        return orCreateFragment;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public CharSequence getPageTitle(int position) {
        return this.mHomeTabAdapter.getTitle(position);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.mHomeTabAdapter.getCount();
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter, androidx.viewpager.widget.PagerAdapter
    public Parcelable saveState() {
        Logger.t(TAG).i("saveState", new Object[0]);
        return super.saveState();
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter, androidx.viewpager.widget.PagerAdapter
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
        if (state != null) {
            Bundle bundle = (Bundle) state;
            Set<String> keySet = bundle.keySet();
            this.mFragments.clear();
            for (String str : keySet) {
                if (str.startsWith("f")) {
                    int parseInt = Integer.parseInt(str.substring(1));
                    Fragment fragment = this.mFragmentManager.getFragment(bundle, str);
                    if (fragment != null) {
                        while (this.mFragments.size() <= parseInt) {
                            this.mFragments.add(null);
                        }
                        this.mFragments.set(parseInt, fragment);
                    } else {
                        Logger.t(TAG).w("Bad fragment at key " + str, new Object[0]);
                    }
                }
            }
        }
        HomeTabAdapter homeTabAdapter = this.mHomeTabAdapter;
        if (homeTabAdapter != null) {
            homeTabAdapter.restoreTabs(this.mFragments);
        }
        Logger.t(TAG).i("restoreState frags:" + this.mFragments, new Object[0]);
    }
}
