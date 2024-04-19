package com.xiaopeng.appstore.appstore_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvListModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.common_ui.common.base.FadingEdgeRecyclerView;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public abstract class ItemPackageBindingBinding extends ViewDataBinding {
    @Bindable
    protected AdvListModel mModel;
    public final FadingEdgeRecyclerView recyclerView;
    public final XTextView tvPackageTitle;

    public abstract void setModel(AdvListModel model);

    /* JADX INFO: Access modifiers changed from: protected */
    public ItemPackageBindingBinding(Object _bindingComponent, View _root, int _localFieldCount, FadingEdgeRecyclerView recyclerView, XTextView tvPackageTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.recyclerView = recyclerView;
        this.tvPackageTitle = tvPackageTitle;
    }

    public AdvListModel getModel() {
        return this.mModel;
    }

    public static ItemPackageBindingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPackageBindingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemPackageBindingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_package_binding, root, attachToRoot, component);
    }

    public static ItemPackageBindingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPackageBindingBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemPackageBindingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_package_binding, null, false, component);
    }

    public static ItemPackageBindingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPackageBindingBinding bind(View view, Object component) {
        return (ItemPackageBindingBinding) bind(component, view, R.layout.item_package_binding);
    }
}
