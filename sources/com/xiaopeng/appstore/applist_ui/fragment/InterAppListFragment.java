package com.xiaopeng.appstore.applist_ui.fragment;

import androidx.lifecycle.ViewModelProvider;
import com.xiaopeng.appstore.applist_ui.viewmodel.InterAppListViewModel;
/* loaded from: classes2.dex */
public class InterAppListFragment extends AppListFragment {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.applist_ui.fragment.AppListFragment
    public InterAppListViewModel createViewModel() {
        return (InterAppListViewModel) new ViewModelProvider(this).get(InterAppListViewModel.class);
    }
}
