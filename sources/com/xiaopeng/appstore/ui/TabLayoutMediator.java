package com.xiaopeng.appstore.ui;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
/* loaded from: classes2.dex */
public final class TabLayoutMediator {
    private static final String SELECT_TAB_NAME = "TabLayout.selectTab(TabLayout.Tab, boolean)";
    private static final String SET_SCROLL_POSITION_NAME = "TabLayout.setScrollPosition(int, float, boolean, boolean)";
    private static Method sSelectTab;
    private static Method sSetScrollPosition;
    private RecyclerView.Adapter mAdapter;
    private boolean mAttached;
    private final boolean mAutoRefresh;
    private final OnConfigureTabCallback mOnConfigureTabCallback;
    private TabLayoutOnPageChangeCallback mOnPageChangeCallback;
    private TabLayout.OnTabSelectedListener mOnTabSelectedListener;
    private RecyclerView.AdapterDataObserver mPagerAdapterObserver;
    private final TabLayout mTabLayout;
    private final ViewPager2 mViewPager;

    /* loaded from: classes2.dex */
    public interface OnConfigureTabCallback {
        void onConfigureTab(TabLayout.Tab tab, int position);
    }

    public TabLayoutMediator(TabLayout tabLayout, ViewPager2 viewPager, OnConfigureTabCallback onConfigureTabCallback) {
        this(tabLayout, viewPager, true, onConfigureTabCallback);
    }

    public TabLayoutMediator(TabLayout tabLayout, ViewPager2 viewPager, boolean autoRefresh, OnConfigureTabCallback onConfigureTabCallback) {
        this.mTabLayout = tabLayout;
        this.mViewPager = viewPager;
        this.mAutoRefresh = autoRefresh;
        this.mOnConfigureTabCallback = onConfigureTabCallback;
    }

    public void attach() {
        if (this.mAttached) {
            throw new IllegalStateException("TabLayoutMediator is already attached");
        }
        RecyclerView.Adapter adapter = this.mViewPager.getAdapter();
        this.mAdapter = adapter;
        if (adapter == null) {
            throw new IllegalStateException("TabLayoutMediator attached before ViewPager2 has an adapter");
        }
        this.mAttached = true;
        TabLayoutOnPageChangeCallback tabLayoutOnPageChangeCallback = new TabLayoutOnPageChangeCallback(this.mTabLayout);
        this.mOnPageChangeCallback = tabLayoutOnPageChangeCallback;
        this.mViewPager.registerOnPageChangeCallback(tabLayoutOnPageChangeCallback);
        ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener = new ViewPagerOnTabSelectedListener(this.mViewPager);
        this.mOnTabSelectedListener = viewPagerOnTabSelectedListener;
        this.mTabLayout.addOnTabSelectedListener((TabLayout.OnTabSelectedListener) viewPagerOnTabSelectedListener);
        if (this.mAutoRefresh) {
            PagerAdapterObserver pagerAdapterObserver = new PagerAdapterObserver();
            this.mPagerAdapterObserver = pagerAdapterObserver;
            this.mAdapter.registerAdapterDataObserver(pagerAdapterObserver);
        }
        populateTabsFromPagerAdapter();
        this.mTabLayout.setScrollPosition(this.mViewPager.getCurrentItem(), 0.0f, true);
    }

    public void detach() {
        this.mAdapter.unregisterAdapterDataObserver(this.mPagerAdapterObserver);
        this.mTabLayout.removeOnTabSelectedListener(this.mOnTabSelectedListener);
        this.mViewPager.unregisterOnPageChangeCallback(this.mOnPageChangeCallback);
        this.mPagerAdapterObserver = null;
        this.mOnTabSelectedListener = null;
        this.mOnPageChangeCallback = null;
        this.mAttached = false;
    }

    void populateTabsFromPagerAdapter() {
        int currentItem;
        this.mTabLayout.removeAllTabs();
        RecyclerView.Adapter adapter = this.mAdapter;
        if (adapter != null) {
            int itemCount = adapter.getItemCount();
            for (int i = 0; i < itemCount; i++) {
                TabLayout.Tab newTab = this.mTabLayout.newTab();
                this.mOnConfigureTabCallback.onConfigureTab(newTab, i);
                this.mTabLayout.addTab(newTab, false);
            }
            if (itemCount <= 0 || (currentItem = this.mViewPager.getCurrentItem()) == this.mTabLayout.getSelectedTabPosition()) {
                return;
            }
            this.mTabLayout.getTabAt(currentItem).select();
        }
    }

    /* loaded from: classes2.dex */
    private static class TabLayoutOnPageChangeCallback extends ViewPager2.OnPageChangeCallback {
        private int mPreviousScrollState;
        private int mScrollState;
        private final WeakReference<TabLayout> mTabLayoutRef;

        TabLayoutOnPageChangeCallback(TabLayout tabLayout) {
            this.mTabLayoutRef = new WeakReference<>(tabLayout);
            reset();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageScrollStateChanged(final int state) {
            this.mPreviousScrollState = this.mScrollState;
            this.mScrollState = state;
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            TabLayout tabLayout = this.mTabLayoutRef.get();
            if (tabLayout != null) {
                int i = this.mScrollState;
                boolean z = false;
                boolean z2 = i != 2 || this.mPreviousScrollState == 1;
                if (i != 2 || this.mPreviousScrollState != 0) {
                    z = true;
                }
                TabLayoutMediator.setScrollPosition(tabLayout, position, positionOffset, z2, z);
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageSelected(final int position) {
            TabLayout tabLayout = this.mTabLayoutRef.get();
            if (tabLayout == null || tabLayout.getSelectedTabPosition() == position || position >= tabLayout.getTabCount()) {
                return;
            }
            int i = this.mScrollState;
            TabLayoutMediator.selectTab(tabLayout, tabLayout.getTabAt(position), i == 0 || (i == 2 && this.mPreviousScrollState == 0));
        }

        void reset() {
            this.mScrollState = 0;
            this.mPreviousScrollState = 0;
        }
    }

    static {
        try {
            Method declaredMethod = TabLayout.class.getDeclaredMethod("setScrollPosition", Integer.TYPE, Float.TYPE, Boolean.TYPE, Boolean.TYPE);
            sSetScrollPosition = declaredMethod;
            declaredMethod.setAccessible(true);
            Method declaredMethod2 = TabLayout.class.getDeclaredMethod("selectTab", TabLayout.Tab.class, Boolean.TYPE);
            sSelectTab = declaredMethod2;
            declaredMethod2.setAccessible(true);
        } catch (NoSuchMethodException unused) {
            throw new IllegalStateException("Can't reflect into method TabLayout.setScrollPosition(int, float, boolean, boolean)");
        }
    }

    static void setScrollPosition(TabLayout tabLayout, int position, float positionOffset, boolean updateSelectedText, boolean updateIndicatorPosition) {
        try {
            Method method = sSetScrollPosition;
            if (method != null) {
                method.invoke(tabLayout, Integer.valueOf(position), Float.valueOf(positionOffset), Boolean.valueOf(updateSelectedText), Boolean.valueOf(updateIndicatorPosition));
            } else {
                throwMethodNotFound(SET_SCROLL_POSITION_NAME);
            }
        } catch (Exception unused) {
            throwInvokeFailed(SET_SCROLL_POSITION_NAME);
        }
    }

    static void selectTab(TabLayout tabLayout, TabLayout.Tab tab, boolean updateIndicator) {
        try {
            Method method = sSelectTab;
            if (method != null) {
                method.invoke(tabLayout, tab, Boolean.valueOf(updateIndicator));
            } else {
                throwMethodNotFound(SELECT_TAB_NAME);
            }
        } catch (Exception unused) {
            throwInvokeFailed(SELECT_TAB_NAME);
        }
    }

    private static void throwMethodNotFound(String method) {
        throw new IllegalStateException("Method " + method + " not found");
    }

    private static void throwInvokeFailed(String method) {
        throw new IllegalStateException("Couldn't invoke method " + method);
    }

    /* loaded from: classes2.dex */
    private static class ViewPagerOnTabSelectedListener implements TabLayout.OnTabSelectedListener {
        private final ViewPager2 mViewPager;

        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public void onTabReselected(TabLayout.Tab tab) {
        }

        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        ViewPagerOnTabSelectedListener(ViewPager2 viewPager) {
            this.mViewPager = viewPager;
        }

        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public void onTabSelected(TabLayout.Tab tab) {
            this.mViewPager.setCurrentItem(tab.getPosition(), true);
        }
    }

    /* loaded from: classes2.dex */
    private class PagerAdapterObserver extends RecyclerView.AdapterDataObserver {
        PagerAdapterObserver() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            TabLayoutMediator.this.populateTabsFromPagerAdapter();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int positionStart, int itemCount) {
            TabLayoutMediator.this.populateTabsFromPagerAdapter();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            TabLayoutMediator.this.populateTabsFromPagerAdapter();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeInserted(int positionStart, int itemCount) {
            TabLayoutMediator.this.populateTabsFromPagerAdapter();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            TabLayoutMediator.this.populateTabsFromPagerAdapter();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            TabLayoutMediator.this.populateTabsFromPagerAdapter();
        }
    }
}
