package com.xiaopeng.appstore.appstore_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvBannerModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.view.ShadowLayout;
import com.xiaopeng.appstore.appstore_ui.view.ViewPagerIndicator;
import com.xiaopeng.appstore.common_ui.IdleDetectLayout;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public abstract class ItemBannerPackageBinding extends ViewDataBinding {
    public final ShadowLayout bannerShadowParent;
    public final XButton btnEnter;
    public final ViewPagerIndicator indicator;
    public final ImageView ivUpdateBg;
    public final XRelativeLayout layoutPendingUpdate;
    @Bindable
    protected AdvBannerModel mModel;
    public final ShadowLayout shadowLayout;
    public final XTextView tvUpgradeDesc;
    public final XTextView tvUpgradeTitle;
    public final IdleDetectLayout viewPagerDetectLayout;
    public final ViewPager2 vpBanner;

    public abstract void setModel(AdvBannerModel model);

    /* JADX INFO: Access modifiers changed from: protected */
    public ItemBannerPackageBinding(Object _bindingComponent, View _root, int _localFieldCount, ShadowLayout bannerShadowParent, XButton btnEnter, ViewPagerIndicator indicator, ImageView ivUpdateBg, XRelativeLayout layoutPendingUpdate, ShadowLayout shadowLayout, XTextView tvUpgradeDesc, XTextView tvUpgradeTitle, IdleDetectLayout viewPagerDetectLayout, ViewPager2 vpBanner) {
        super(_bindingComponent, _root, _localFieldCount);
        this.bannerShadowParent = bannerShadowParent;
        this.btnEnter = btnEnter;
        this.indicator = indicator;
        this.ivUpdateBg = ivUpdateBg;
        this.layoutPendingUpdate = layoutPendingUpdate;
        this.shadowLayout = shadowLayout;
        this.tvUpgradeDesc = tvUpgradeDesc;
        this.tvUpgradeTitle = tvUpgradeTitle;
        this.viewPagerDetectLayout = viewPagerDetectLayout;
        this.vpBanner = vpBanner;
    }

    public AdvBannerModel getModel() {
        return this.mModel;
    }

    public static ItemBannerPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBannerPackageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemBannerPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_banner_package, root, attachToRoot, component);
    }

    public static ItemBannerPackageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBannerPackageBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemBannerPackageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_banner_package, null, false, component);
    }

    public static ItemBannerPackageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBannerPackageBinding bind(View view, Object component) {
        return (ItemBannerPackageBinding) bind(component, view, R.layout.item_banner_package);
    }
}
