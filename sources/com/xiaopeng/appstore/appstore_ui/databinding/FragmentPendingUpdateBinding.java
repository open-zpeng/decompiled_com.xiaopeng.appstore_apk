package com.xiaopeng.appstore.appstore_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.viewmodel.PendingUpgradeViewModel;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public abstract class FragmentPendingUpdateBinding extends ViewDataBinding {
    public final XTextView fragmentPendingUpdateListTitle;
    public final LinearLayout fragmentPendingUpdateNoUpdateContainer;
    public final LinearLayout fragmentPendingUpdateTitleLayout;
    @Bindable
    protected PendingUpgradeViewModel mViewModel;
    public final RecyclerView recyclerView;

    public abstract void setViewModel(PendingUpgradeViewModel viewModel);

    /* JADX INFO: Access modifiers changed from: protected */
    public FragmentPendingUpdateBinding(Object _bindingComponent, View _root, int _localFieldCount, XTextView fragmentPendingUpdateListTitle, LinearLayout fragmentPendingUpdateNoUpdateContainer, LinearLayout fragmentPendingUpdateTitleLayout, RecyclerView recyclerView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.fragmentPendingUpdateListTitle = fragmentPendingUpdateListTitle;
        this.fragmentPendingUpdateNoUpdateContainer = fragmentPendingUpdateNoUpdateContainer;
        this.fragmentPendingUpdateTitleLayout = fragmentPendingUpdateTitleLayout;
        this.recyclerView = recyclerView;
    }

    public PendingUpgradeViewModel getViewModel() {
        return this.mViewModel;
    }

    public static FragmentPendingUpdateBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentPendingUpdateBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FragmentPendingUpdateBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_pending_update, root, attachToRoot, component);
    }

    public static FragmentPendingUpdateBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentPendingUpdateBinding inflate(LayoutInflater inflater, Object component) {
        return (FragmentPendingUpdateBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_pending_update, null, false, component);
    }

    public static FragmentPendingUpdateBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentPendingUpdateBinding bind(View view, Object component) {
        return (FragmentPendingUpdateBinding) bind(component, view, R.layout.fragment_pending_update);
    }
}
