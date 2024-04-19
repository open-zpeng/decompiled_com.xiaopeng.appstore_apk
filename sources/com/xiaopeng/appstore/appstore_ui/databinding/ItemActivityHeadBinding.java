package com.xiaopeng.appstore.appstore_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.xui.view.XView;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public abstract class ItemActivityHeadBinding extends ViewDataBinding {
    public final XView dividerBottom;
    public final XView dividerImage;
    public final ImageView ivTop;
    @Bindable
    protected Integer mDividerColor;
    @Bindable
    protected AdvModel mModel;
    public final XTextView tvActivityDesc;
    public final XTextView tvActivityTitle;

    public abstract void setDividerColor(Integer dividerColor);

    public abstract void setModel(AdvModel model);

    /* JADX INFO: Access modifiers changed from: protected */
    public ItemActivityHeadBinding(Object _bindingComponent, View _root, int _localFieldCount, XView dividerBottom, XView dividerImage, ImageView ivTop, XTextView tvActivityDesc, XTextView tvActivityTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dividerBottom = dividerBottom;
        this.dividerImage = dividerImage;
        this.ivTop = ivTop;
        this.tvActivityDesc = tvActivityDesc;
        this.tvActivityTitle = tvActivityTitle;
    }

    public AdvModel getModel() {
        return this.mModel;
    }

    public Integer getDividerColor() {
        return this.mDividerColor;
    }

    public static ItemActivityHeadBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemActivityHeadBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemActivityHeadBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_activity_head, root, attachToRoot, component);
    }

    public static ItemActivityHeadBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemActivityHeadBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemActivityHeadBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_activity_head, null, false, component);
    }

    public static ItemActivityHeadBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemActivityHeadBinding bind(View view, Object component) {
        return (ItemActivityHeadBinding) bind(component, view, R.layout.item_activity_head);
    }
}
