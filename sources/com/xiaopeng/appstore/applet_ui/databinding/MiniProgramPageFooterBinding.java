package com.xiaopeng.appstore.applet_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaopeng.appstore.applet_ui.R;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public abstract class MiniProgramPageFooterBinding extends ViewDataBinding {
    public final XTextView tvContact;

    /* JADX INFO: Access modifiers changed from: protected */
    public MiniProgramPageFooterBinding(Object _bindingComponent, View _root, int _localFieldCount, XTextView tvContact) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvContact = tvContact;
    }

    public static MiniProgramPageFooterBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static MiniProgramPageFooterBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (MiniProgramPageFooterBinding) ViewDataBinding.inflateInternal(inflater, R.layout.mini_program_page_footer, root, attachToRoot, component);
    }

    public static MiniProgramPageFooterBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static MiniProgramPageFooterBinding inflate(LayoutInflater inflater, Object component) {
        return (MiniProgramPageFooterBinding) ViewDataBinding.inflateInternal(inflater, R.layout.mini_program_page_footer, null, false, component);
    }

    public static MiniProgramPageFooterBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static MiniProgramPageFooterBinding bind(View view, Object component) {
        return (MiniProgramPageFooterBinding) bind(component, view, R.layout.mini_program_page_footer);
    }
}
