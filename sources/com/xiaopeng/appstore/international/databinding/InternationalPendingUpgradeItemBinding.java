package com.xiaopeng.appstore.international.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaopeng.appstore.appstore_biz.model2.AppBaseModel;
import com.xiaopeng.appstore.common_ui.common.widget.MaskImageView;
import com.xiaopeng.appstore.international.R;
import com.xiaopeng.appstore.international.upgrade.InternationalAppItemViewHolder;
import com.xiaopeng.xui.view.XView;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public abstract class InternationalPendingUpgradeItemBinding extends ViewDataBinding {
    public final XButton btnExecute;
    public final XLinearLayout expandButtonContainer;
    public final XLinearLayout expandContentContainer;
    public final XTextView expandContentText;
    public final XView expandDivider;
    public final XTextView internationExpand;
    public final MaskImageView ivIcon;
    @Bindable
    protected InternationalAppItemViewHolder.OnItemEventCallback mCallback;
    @Bindable
    protected Boolean mExpanded;
    @Bindable
    protected AppBaseModel mModel;
    @Bindable
    protected Integer mPosition;
    @Bindable
    protected Integer mProgress;
    @Bindable
    protected Integer mState;
    @Bindable
    protected InternationalAppItemViewHolder mViewHolder;
    public final XTextView tvAppTitle;
    public final XTextView tvUpgradeInfo;
    public final XLinearLayout tvUpgradeInfoContainer;
    public final XTextView tvUpgradeSize;

    public abstract void setCallback(InternationalAppItemViewHolder.OnItemEventCallback callback);

    public abstract void setExpanded(Boolean expanded);

    public abstract void setModel(AppBaseModel model);

    public abstract void setPosition(Integer position);

    public abstract void setProgress(Integer progress);

    public abstract void setState(Integer state);

    public abstract void setViewHolder(InternationalAppItemViewHolder viewHolder);

    /* JADX INFO: Access modifiers changed from: protected */
    public InternationalPendingUpgradeItemBinding(Object _bindingComponent, View _root, int _localFieldCount, XButton btnExecute, XLinearLayout expandButtonContainer, XLinearLayout expandContentContainer, XTextView expandContentText, XView expandDivider, XTextView internationExpand, MaskImageView ivIcon, XTextView tvAppTitle, XTextView tvUpgradeInfo, XLinearLayout tvUpgradeInfoContainer, XTextView tvUpgradeSize) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btnExecute = btnExecute;
        this.expandButtonContainer = expandButtonContainer;
        this.expandContentContainer = expandContentContainer;
        this.expandContentText = expandContentText;
        this.expandDivider = expandDivider;
        this.internationExpand = internationExpand;
        this.ivIcon = ivIcon;
        this.tvAppTitle = tvAppTitle;
        this.tvUpgradeInfo = tvUpgradeInfo;
        this.tvUpgradeInfoContainer = tvUpgradeInfoContainer;
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

    public Boolean getExpanded() {
        return this.mExpanded;
    }

    public InternationalAppItemViewHolder.OnItemEventCallback getCallback() {
        return this.mCallback;
    }

    public InternationalAppItemViewHolder getViewHolder() {
        return this.mViewHolder;
    }

    public static InternationalPendingUpgradeItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static InternationalPendingUpgradeItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (InternationalPendingUpgradeItemBinding) ViewDataBinding.inflateInternal(inflater, R.layout.international_pending_upgrade_item, root, attachToRoot, component);
    }

    public static InternationalPendingUpgradeItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static InternationalPendingUpgradeItemBinding inflate(LayoutInflater inflater, Object component) {
        return (InternationalPendingUpgradeItemBinding) ViewDataBinding.inflateInternal(inflater, R.layout.international_pending_upgrade_item, null, false, component);
    }

    public static InternationalPendingUpgradeItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static InternationalPendingUpgradeItemBinding bind(View view, Object component) {
        return (InternationalPendingUpgradeItemBinding) bind(component, view, R.layout.international_pending_upgrade_item);
    }
}
