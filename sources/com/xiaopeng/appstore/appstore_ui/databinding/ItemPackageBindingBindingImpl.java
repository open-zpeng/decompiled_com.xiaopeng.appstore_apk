package com.xiaopeng.appstore.appstore_ui.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvListModel;
import com.xiaopeng.appstore.appstore_ui.BR;
import com.xiaopeng.appstore.appstore_ui.bindingextension.CommonBindingExtension;
import com.xiaopeng.appstore.common_ui.common.base.FadingEdgeRecyclerView;
import com.xiaopeng.xui.widget.XTextView;
import java.util.List;
/* loaded from: classes2.dex */
public class ItemPackageBindingBindingImpl extends ItemPackageBindingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public ItemPackageBindingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }

    private ItemPackageBindingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (FadingEdgeRecyclerView) bindings[2], (XTextView) bindings[1]);
        this.mDirtyFlags = -1L;
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        this.recyclerView.setTag(null);
        this.tvPackageTitle.setTag(null);
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
            setModel((AdvListModel) variable);
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemPackageBindingBinding
    public void setModel(AdvListModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(BR.model);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        AdvListModel advListModel = this.mModel;
        int i = ((j & 3) > 0L ? 1 : ((j & 3) == 0L ? 0 : -1));
        List list = null;
        if (i == 0 || advListModel == null) {
            str = null;
        } else {
            str = advListModel.getTitle();
            list = advListModel.getList();
        }
        if (i != 0) {
            CommonBindingExtension.setRecyclerData(this.recyclerView, list);
            TextViewBindingAdapter.setText(this.tvPackageTitle, str);
        }
    }
}
