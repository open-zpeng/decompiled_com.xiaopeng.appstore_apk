package com.xiaopeng.appstore.appstore_ui.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvBannerModel;
import com.xiaopeng.appstore.appstore_ui.BR;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.view.ShadowLayout;
import com.xiaopeng.appstore.appstore_ui.view.ViewPagerIndicator;
import com.xiaopeng.appstore.common_ui.IdleDetectLayout;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public class ItemBannerPackageBindingImpl extends ItemBannerPackageBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.shadow_layout, 1);
        sparseIntArray.put(R.id.layout_pending_update, 2);
        sparseIntArray.put(R.id.iv_update_bg, 3);
        sparseIntArray.put(R.id.tv_upgrade_title, 4);
        sparseIntArray.put(R.id.tv_upgrade_desc, 5);
        sparseIntArray.put(R.id.btn_enter, 6);
        sparseIntArray.put(R.id.banner_shadow_parent, 7);
        sparseIntArray.put(R.id.viewPager_detectLayout, 8);
        sparseIntArray.put(R.id.vp_banner, 9);
        sparseIntArray.put(R.id.indicator, 10);
    }

    public ItemBannerPackageBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private ItemBannerPackageBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (ShadowLayout) bindings[7], (XButton) bindings[6], (ViewPagerIndicator) bindings[10], (ImageView) bindings[3], (XRelativeLayout) bindings[2], (ShadowLayout) bindings[1], (XTextView) bindings[5], (XTextView) bindings[4], (IdleDetectLayout) bindings[8], (ViewPager2) bindings[9]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.mDirtyFlags != 0;
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (BR.model == variableId) {
            setModel((AdvBannerModel) variable);
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemBannerPackageBinding
    public void setModel(AdvBannerModel Model) {
        this.mModel = Model;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}
