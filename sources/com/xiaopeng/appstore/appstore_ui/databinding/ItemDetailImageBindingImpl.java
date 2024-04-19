package com.xiaopeng.appstore.appstore_ui.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.xiaopeng.appstore.appstore_ui.BR;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.bindingextension.CommonBindingExtension;
import com.xiaopeng.appstore.appstore_ui.view.ShadowLayout;
import com.xiaopeng.xui.widget.XImageView;
/* loaded from: classes2.dex */
public class ItemDetailImageBindingImpl extends ItemDetailImageBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final ShadowLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public ItemDetailImageBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 2, sIncludes, sViewsWithIds));
    }

    private ItemDetailImageBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (XImageView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.ivDetail.setTag(null);
        ShadowLayout shadowLayout = (ShadowLayout) bindings[0];
        this.mboundView0 = shadowLayout;
        shadowLayout.setTag(null);
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
        if (BR.url == variableId) {
            setUrl((String) variable);
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemDetailImageBinding
    public void setUrl(String Url) {
        this.mUrl = Url;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(BR.url);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        String str = this.mUrl;
        if ((j & 3) != 0) {
            CommonBindingExtension.setImageSrc(this.ivDetail, str, AppCompatResources.getDrawable(this.ivDetail.getContext(), R.drawable.detail_image_placeholder), AppCompatResources.getDrawable(this.ivDetail.getContext(), R.drawable.detail_image_placeholder), 0.0f);
        }
    }
}
