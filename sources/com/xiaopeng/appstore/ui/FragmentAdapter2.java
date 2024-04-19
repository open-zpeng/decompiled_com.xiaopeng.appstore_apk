package com.xiaopeng.appstore.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.xiaopeng.appstore.applist_ui.fragment.AppListFragment;
import com.xiaopeng.appstore.appstore_ui.fragment.StoreRootFragment;
/* loaded from: classes2.dex */
public class FragmentAdapter2 extends FragmentStateAdapter {
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return 2;
    }

    public FragmentAdapter2(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override // androidx.viewpager2.adapter.FragmentStateAdapter
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new AppListFragment();
        }
        if (position == 1) {
            return new StoreRootFragment();
        }
        return null;
    }
}
