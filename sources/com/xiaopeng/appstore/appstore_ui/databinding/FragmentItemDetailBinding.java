package com.xiaopeng.appstore.appstore_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.view.BizProgressButton;
import com.xiaopeng.appstore.appstore_ui.view.ExpandableTextView;
import com.xiaopeng.appstore.appstore_ui.viewmodel.ItemDetailViewModel;
import com.xiaopeng.xui.view.XView;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public abstract class FragmentItemDetailBinding extends ViewDataBinding {
    public final XButton btnCancel;
    public final XButton btnRetry;
    public final ConstraintLayout constraintRoot;
    public final XView divider1;
    public final XView divider2;
    public final XView divider3;
    public final XView divider4;
    public final ImageView ivIcon;
    @Bindable
    protected Integer mDividerColor;
    @Bindable
    protected ItemDetailViewModel mViewModel;
    public final BizProgressButton progressBtn;
    public final RecyclerView rvImage;
    public final XTextView tvContact;
    public final ExpandableTextView tvDesc1;
    public final ExpandableTextView tvDesc2;
    public final XTextView tvDetailTitle;
    public final XTextView tvDeveloper;
    public final XTextView tvDeveloperTitle;
    public final XTextView tvEmptyTips;
    public final XTextView tvErrorTips;
    public final XTextView tvFileSize;
    public final XTextView tvImageTitle;
    public final XTextView tvPermission;
    public final XTextView tvPrivate;
    public final XTextView tvProtocol;
    public final XTextView tvSource;
    public final XTextView tvSpeed;
    public final XTextView tvSubTitle;
    public final XTextView tvSubTitle1;
    public final XTextView tvSubTitle2;
    public final XTextView tvTitle1;
    public final XTextView tvTitle2;

    public abstract void setDividerColor(Integer dividerColor);

    public abstract void setViewModel(ItemDetailViewModel viewModel);

    /* JADX INFO: Access modifiers changed from: protected */
    public FragmentItemDetailBinding(Object _bindingComponent, View _root, int _localFieldCount, XButton btnCancel, XButton btnRetry, ConstraintLayout constraintRoot, XView divider1, XView divider2, XView divider3, XView divider4, ImageView ivIcon, BizProgressButton progressBtn, RecyclerView rvImage, XTextView tvContact, ExpandableTextView tvDesc1, ExpandableTextView tvDesc2, XTextView tvDetailTitle, XTextView tvDeveloper, XTextView tvDeveloperTitle, XTextView tvEmptyTips, XTextView tvErrorTips, XTextView tvFileSize, XTextView tvImageTitle, XTextView tvPermission, XTextView tvPrivate, XTextView tvProtocol, XTextView tvSource, XTextView tvSpeed, XTextView tvSubTitle, XTextView tvSubTitle1, XTextView tvSubTitle2, XTextView tvTitle1, XTextView tvTitle2) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btnCancel = btnCancel;
        this.btnRetry = btnRetry;
        this.constraintRoot = constraintRoot;
        this.divider1 = divider1;
        this.divider2 = divider2;
        this.divider3 = divider3;
        this.divider4 = divider4;
        this.ivIcon = ivIcon;
        this.progressBtn = progressBtn;
        this.rvImage = rvImage;
        this.tvContact = tvContact;
        this.tvDesc1 = tvDesc1;
        this.tvDesc2 = tvDesc2;
        this.tvDetailTitle = tvDetailTitle;
        this.tvDeveloper = tvDeveloper;
        this.tvDeveloperTitle = tvDeveloperTitle;
        this.tvEmptyTips = tvEmptyTips;
        this.tvErrorTips = tvErrorTips;
        this.tvFileSize = tvFileSize;
        this.tvImageTitle = tvImageTitle;
        this.tvPermission = tvPermission;
        this.tvPrivate = tvPrivate;
        this.tvProtocol = tvProtocol;
        this.tvSource = tvSource;
        this.tvSpeed = tvSpeed;
        this.tvSubTitle = tvSubTitle;
        this.tvSubTitle1 = tvSubTitle1;
        this.tvSubTitle2 = tvSubTitle2;
        this.tvTitle1 = tvTitle1;
        this.tvTitle2 = tvTitle2;
    }

    public ItemDetailViewModel getViewModel() {
        return this.mViewModel;
    }

    public Integer getDividerColor() {
        return this.mDividerColor;
    }

    public static FragmentItemDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentItemDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FragmentItemDetailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_item_detail, root, attachToRoot, component);
    }

    public static FragmentItemDetailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentItemDetailBinding inflate(LayoutInflater inflater, Object component) {
        return (FragmentItemDetailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_item_detail, null, false, component);
    }

    public static FragmentItemDetailBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentItemDetailBinding bind(View view, Object component) {
        return (FragmentItemDetailBinding) bind(component, view, R.layout.fragment_item_detail);
    }
}
