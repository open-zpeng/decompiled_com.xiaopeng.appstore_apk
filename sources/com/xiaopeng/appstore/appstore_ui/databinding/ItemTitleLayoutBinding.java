package com.xiaopeng.appstore.appstore_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public abstract class ItemTitleLayoutBinding extends ViewDataBinding {
    @Bindable
    protected String mTitle;
    public final XTextView tvPackageTitle;

    public abstract void setTitle(String title);

    /* JADX INFO: Access modifiers changed from: protected */
    public ItemTitleLayoutBinding(Object _bindingComponent, View _root, int _localFieldCount, XTextView tvPackageTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvPackageTitle = tvPackageTitle;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public static ItemTitleLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTitleLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemTitleLayoutBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_title_layout, root, attachToRoot, component);
    }

    public static ItemTitleLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTitleLayoutBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemTitleLayoutBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_title_layout, null, false, component);
    }

    public static ItemTitleLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTitleLayoutBinding bind(View view, Object component) {
        return (ItemTitleLayoutBinding) bind(component, view, R.layout.item_title_layout);
    }
}
