package com.xiaopeng.appstore.appstore_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaopeng.appstore.appstore_biz.model2.AppBaseModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AppItemViewHolder;
import com.xiaopeng.appstore.common_ui.common.widget.MaskImageView;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public abstract class ItemUpgradeLayoutBinding extends ViewDataBinding {
    public final XButton btnExecute;
    public final MaskImageView ivIcon;
    @Bindable
    protected AppItemViewHolder.OnItemEventCallback mCallback;
    @Bindable
    protected AppBaseModel mModel;
    @Bindable
    protected Integer mPosition;
    @Bindable
    protected Integer mProgress;
    @Bindable
    protected Integer mState;
    public final XTextView tvAppTitle;
    public final XTextView tvUpgradeInfo;
    public final XTextView tvUpgradeSize;

    public abstract void setCallback(AppItemViewHolder.OnItemEventCallback callback);

    public abstract void setModel(AppBaseModel model);

    public abstract void setPosition(Integer position);

    public abstract void setProgress(Integer progress);

    public abstract void setState(Integer state);

    /* JADX INFO: Access modifiers changed from: protected */
    public ItemUpgradeLayoutBinding(Object _bindingComponent, View _root, int _localFieldCount, XButton btnExecute, MaskImageView ivIcon, XTextView tvAppTitle, XTextView tvUpgradeInfo, XTextView tvUpgradeSize) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btnExecute = btnExecute;
        this.ivIcon = ivIcon;
        this.tvAppTitle = tvAppTitle;
        this.tvUpgradeInfo = tvUpgradeInfo;
        this.tvUpgradeSize = tvUpgradeSize;
    }

    public AppBaseModel getModel() {
        return this.mModel;
    }

    public Integer getPosition() {
        return this.mPosition;
    }

    public Integer getState() {
        return this.mState;
    }

    public Integer getProgress() {
        return this.mProgress;
    }

    public AppItemViewHolder.OnItemEventCallback getCallback() {
        return this.mCallback;
    }

    public static ItemUpgradeLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemUpgradeLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemUpgradeLayoutBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_upgrade_layout, root, attachToRoot, component);
    }

    public static ItemUpgradeLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemUpgradeLayoutBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemUpgradeLayoutBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_upgrade_layout, null, false, component);
    }

    public static ItemUpgradeLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemUpgradeLayoutBinding bind(View view, Object component) {
        return (ItemUpgradeLayoutBinding) bind(component, view, R.layout.item_upgrade_layout);
    }
}
