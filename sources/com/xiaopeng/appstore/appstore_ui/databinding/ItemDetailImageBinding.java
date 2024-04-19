package com.xiaopeng.appstore.appstore_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.xui.widget.XImageView;
/* loaded from: classes2.dex */
public abstract class ItemDetailImageBinding extends ViewDataBinding {
    public final XImageView ivDetail;
    @Bindable
    protected String mUrl;

    public abstract void setUrl(String url);

    /* JADX INFO: Access modifiers changed from: protected */
    public ItemDetailImageBinding(Object _bindingComponent, View _root, int _localFieldCount, XImageView ivDetail) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivDetail = ivDetail;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public static ItemDetailImageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDetailImageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDetailImageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_detail_image, root, attachToRoot, component);
    }

    public static ItemDetailImageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDetailImageBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDetailImageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_detail_image, null, false, component);
    }

    public static ItemDetailImageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDetailImageBinding bind(View view, Object component) {
        return (ItemDetailImageBinding) bind(component, view, R.layout.item_detail_image);
    }
}
