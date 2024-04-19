package com.xiaopeng.appstore.appstore_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public abstract class ItemActivityFooterBinding extends ViewDataBinding {
    public final XTextView tvContact;
    public final XTextView tvProtocol;

    /* JADX INFO: Access modifiers changed from: protected */
    public ItemActivityFooterBinding(Object _bindingComponent, View _root, int _localFieldCount, XTextView tvContact, XTextView tvProtocol) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvContact = tvContact;
        this.tvProtocol = tvProtocol;
    }

    public static ItemActivityFooterBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemActivityFooterBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemActivityFooterBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_activity_footer, root, attachToRoot, component);
    }

    public static ItemActivityFooterBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemActivityFooterBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemActivityFooterBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_activity_footer, null, false, component);
    }

    public static ItemActivityFooterBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemActivityFooterBinding bind(View view, Object component) {
        return (ItemActivityFooterBinding) bind(component, view, R.layout.item_activity_footer);
    }
}
