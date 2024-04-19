package com.xiaopeng.appstore.appstore_ui.databinding;

import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel;
import com.xiaopeng.appstore.appstore_ui.BR;
import com.xiaopeng.appstore.appstore_ui.bindingextension.CommonBindingExtension;
import com.xiaopeng.xui.view.XView;
import com.xiaopeng.xui.widget.XTextView;
import java.util.List;
/* loaded from: classes2.dex */
public class ItemActivityHeadBindingImpl extends ItemActivityHeadBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public ItemActivityHeadBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }

    private ItemActivityHeadBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (XView) bindings[5], (XView) bindings[2], (ImageView) bindings[1], (XTextView) bindings[4], (XTextView) bindings[3]);
        this.mDirtyFlags = -1L;
        this.dividerBottom.setTag(null);
        this.dividerImage.setTag(null);
        this.ivTop.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        this.tvActivityDesc.setTag(null);
        this.tvActivityTitle.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
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
            setModel((AdvModel) variable);
        } else if (BR.dividerColor != variableId) {
            return false;
        } else {
            setDividerColor((Integer) variable);
        }
        return true;
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemActivityHeadBinding
    public void setModel(AdvModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(BR.model);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemActivityHeadBinding
    public void setDividerColor(Integer DividerColor) {
        this.mDividerColor = DividerColor;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(BR.dividerColor);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        String str2;
        List<String> list;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        AdvModel advModel = this.mModel;
        Integer num = this.mDividerColor;
        int i = ((5 & j) > 0L ? 1 : ((5 & j) == 0L ? 0 : -1));
        if (i == 0 || advModel == null) {
            str = null;
            str2 = null;
            list = null;
        } else {
            str2 = advModel.getTitle();
            list = advModel.getImageList();
            str = advModel.getDesc();
        }
        int i2 = ((j & 6) > 0L ? 1 : ((j & 6) == 0L ? 0 : -1));
        int safeUnbox = i2 != 0 ? ViewDataBinding.safeUnbox(num) : 0;
        if (i2 != 0) {
            CommonBindingExtension.setBackgroundColor(this.dividerBottom, safeUnbox);
            CommonBindingExtension.setBackgroundColor(this.dividerImage, safeUnbox);
        }
        if (i != 0) {
            Drawable drawable = null;
            CommonBindingExtension.setFirstImage(this.ivTop, list, drawable, drawable, 0.0f);
            TextViewBindingAdapter.setText(this.tvActivityDesc, str);
            TextViewBindingAdapter.setText(this.tvActivityTitle, str2);
        }
    }
}
