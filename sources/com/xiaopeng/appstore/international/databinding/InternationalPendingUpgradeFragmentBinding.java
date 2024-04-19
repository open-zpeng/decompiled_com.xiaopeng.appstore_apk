package com.xiaopeng.appstore.international.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.appstore_ui.viewmodel.PendingUpgradeViewModel;
import com.xiaopeng.appstore.international.R;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public abstract class InternationalPendingUpgradeFragmentBinding extends ViewDataBinding {
    public final XTextView fragmentPendingUpdateListTitle;
    public final LinearLayout fragmentPendingUpdateNoUpdateContainer;
    @Bindable
    protected PendingUpgradeViewModel mViewModel;
    public final RecyclerView recyclerView;

    public abstract void setViewModel(PendingUpgradeViewModel viewModel);

    /* JADX INFO: Access modifiers changed from: protected */
    public InternationalPendingUpgradeFragmentBinding(Object _bindingComponent, View _root, int _localFieldCount, XTextView fragmentPendingUpdateListTitle, LinearLayout fragmentPendingUpdateNoUpdateContainer, RecyclerView recyclerView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.fragmentPendingUpdateListTitle = fragmentPendingUpdateListTitle;
        this.fragmentPendingUpdateNoUpdateContainer = fragmentPendingUpdateNoUpdateContainer;
        this.recyclerView = recyclerView;
    }

    public PendingUpgradeViewModel getViewModel() {
        return this.mViewModel;
    }

    public static InternationalPendingUpgradeFragmentBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static InternationalPendingUpgradeFragmentBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (InternationalPendingUpgradeFragmentBinding) ViewDataBinding.inflateInternal(inflater, R.layout.international_pending_upgrade_fragment, root, attachToRoot, component);
    }

    public static InternationalPendingUpgradeFragmentBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static InternationalPendingUpgradeFragmentBinding inflate(LayoutInflater inflater, Object component) {
        return (InternationalPendingUpgradeFragmentBinding) ViewDataBinding.inflateInternal(inflater, R.layout.international_pending_upgrade_fragment, null, false, component);
    }

    public static InternationalPendingUpgradeFragmentBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static InternationalPendingUpgradeFragmentBinding bind(View view, Object component) {
        return (InternationalPendingUpgradeFragmentBinding) bind(component, view, R.layout.international_pending_upgrade_fragment);
    }
}
