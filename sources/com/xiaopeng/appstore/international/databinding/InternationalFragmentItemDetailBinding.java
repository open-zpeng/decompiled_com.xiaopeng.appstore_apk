package com.xiaopeng.appstore.international.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaopeng.appstore.appstore_ui.view.BizProgressButton;
import com.xiaopeng.appstore.international.InternationalItemDetailViewModel;
import com.xiaopeng.appstore.international.R;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public abstract class InternationalFragmentItemDetailBinding extends ViewDataBinding {
    public final XButton btnRetry;
    public final ConstraintLayout constraintRoot;
    public final ImageView ivIcon;
    @Bindable
    protected Integer mDividerColor;
    @Bindable
    protected InternationalItemDetailViewModel mViewModel;
    public final BizProgressButton progressBtn;
    public final XTextView tvDetailTitle;
    public final XTextView tvDeveloper;
    public final XTextView tvDeveloperTitle;
    public final XTextView tvEmptyTips;
    public final XTextView tvErrorTips;
    public final XTextView tvFileSize;
    public final XTextView tvPrivate;
    public final XTextView tvSource;
    public final XTextView tvSpeed;
    public final XTextView tvSubTitle1;

    public abstract void setDividerColor(Integer dividerColor);

    public abstract void setViewModel(InternationalItemDetailViewModel viewModel);

    /* JADX INFO: Access modifiers changed from: protected */
    public InternationalFragmentItemDetailBinding(Object _bindingComponent, View _root, int _localFieldCount, XButton btnRetry, ConstraintLayout constraintRoot, ImageView ivIcon, BizProgressButton progressBtn, XTextView tvDetailTitle, XTextView tvDeveloper, XTextView tvDeveloperTitle, XTextView tvEmptyTips, XTextView tvErrorTips, XTextView tvFileSize, XTextView tvPrivate, XTextView tvSource, XTextView tvSpeed, XTextView tvSubTitle1) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btnRetry = btnRetry;
        this.constraintRoot = constraintRoot;
        this.ivIcon = ivIcon;
        this.progressBtn = progressBtn;
        this.tvDetailTitle = tvDetailTitle;
        this.tvDeveloper = tvDeveloper;
        this.tvDeveloperTitle = tvDeveloperTitle;
        this.tvEmptyTips = tvEmptyTips;
        this.tvErrorTips = tvErrorTips;
        this.tvFileSize = tvFileSize;
        this.tvPrivate = tvPrivate;
        this.tvSource = tvSource;
        this.tvSpeed = tvSpeed;
        this.tvSubTitle1 = tvSubTitle1;
    }

    public InternationalItemDetailViewModel getViewModel() {
        return this.mViewModel;
    }

    public Integer getDividerColor() {
        return this.mDividerColor;
    }

    public static InternationalFragmentItemDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static InternationalFragmentItemDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (InternationalFragmentItemDetailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.international_fragment_item_detail, root, attachToRoot, component);
    }

    public static InternationalFragmentItemDetailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static InternationalFragmentItemDetailBinding inflate(LayoutInflater inflater, Object component) {
        return (InternationalFragmentItemDetailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.international_fragment_item_detail, null, false, component);
    }

    public static InternationalFragmentItemDetailBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static InternationalFragmentItemDetailBinding bind(View view, Object component) {
        return (InternationalFragmentItemDetailBinding) bind(component, view, R.layout.international_fragment_item_detail);
    }
}
