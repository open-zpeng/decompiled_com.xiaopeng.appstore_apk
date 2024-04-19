package com.xiaopeng.appstore.appstore_ui.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.appstore_biz.model2.AppBaseModel;
import com.xiaopeng.appstore.appstore_ui.BR;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.bindingextension.CommonBindingExtension;
import com.xiaopeng.appstore.appstore_ui.bindingextension.VisibilityBinding;
import com.xiaopeng.appstore.appstore_ui.viewmodel.PendingUpgradeViewModel;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XTextView;
import java.util.List;
/* loaded from: classes2.dex */
public class FragmentPendingUpdateBindingImpl extends FragmentPendingUpdateBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final FrameLayout mboundView0;
    private final RelativeLayout mboundView1;
    private final XButton mboundView2;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.fragment_pending_update_title_layout, 5);
        sparseIntArray.put(R.id.fragment_pending_update_list_title, 6);
    }

    public FragmentPendingUpdateBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }

    private FragmentPendingUpdateBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (XTextView) bindings[6], (LinearLayout) bindings[4], (LinearLayout) bindings[5], (RecyclerView) bindings[3]);
        this.mDirtyFlags = -1L;
        this.fragmentPendingUpdateNoUpdateContainer.setTag(null);
        FrameLayout frameLayout = (FrameLayout) bindings[0];
        this.mboundView0 = frameLayout;
        frameLayout.setTag(null);
        RelativeLayout relativeLayout = (RelativeLayout) bindings[1];
        this.mboundView1 = relativeLayout;
        relativeLayout.setTag(null);
        XButton xButton = (XButton) bindings[2];
        this.mboundView2 = xButton;
        xButton.setTag(null);
        this.recyclerView.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
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
        if (BR.viewModel == variableId) {
            setViewModel((PendingUpgradeViewModel) variable);
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.FragmentPendingUpdateBinding
    public void setViewModel(PendingUpgradeViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            if (localFieldId != 1) {
                return false;
            }
            return onChangeViewModelUpdateAllVisible((MutableLiveData) object, fieldId);
        }
        return onChangeViewModelPersistentUpgradeList((MediatorLiveData) object, fieldId);
    }

    private boolean onChangeViewModelPersistentUpgradeList(MediatorLiveData<List<AppBaseModel>> ViewModelPersistentUpgradeList, int fieldId) {
        if (fieldId == BR._all) {
            synchronized (this) {
                this.mDirtyFlags |= 1;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewModelUpdateAllVisible(MutableLiveData<Boolean> ViewModelUpdateAllVisible, int fieldId) {
        if (fieldId == BR._all) {
            synchronized (this) {
                this.mDirtyFlags |= 2;
            }
            return true;
        }
        return false;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        boolean z;
        View.OnClickListener onClickListener;
        List<AppBaseModel> list;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        PendingUpgradeViewModel pendingUpgradeViewModel = this.mViewModel;
        if ((15 & j) != 0) {
            int i = ((j & 13) > 0L ? 1 : ((j & 13) == 0L ? 0 : -1));
            if (i != 0) {
                MediatorLiveData<List<AppBaseModel>> persistentUpgradeList = pendingUpgradeViewModel != null ? pendingUpgradeViewModel.getPersistentUpgradeList() : null;
                updateLiveDataRegistration(0, persistentUpgradeList);
                list = persistentUpgradeList != null ? persistentUpgradeList.getValue() : null;
                z3 = list != null;
                if (i != 0) {
                    j = z3 ? j | 32 | 128 : j | 16 | 64;
                }
            } else {
                z3 = false;
                list = null;
            }
            onClickListener = ((j & 12) == 0 || pendingUpgradeViewModel == null) ? null : pendingUpgradeViewModel.getOnUpgradeAllClick();
            if ((j & 14) != 0) {
                MutableLiveData<Boolean> updateAllVisible = pendingUpgradeViewModel != null ? pendingUpgradeViewModel.getUpdateAllVisible() : null;
                z = true;
                updateLiveDataRegistration(1, updateAllVisible);
                z2 = ViewDataBinding.safeUnbox(updateAllVisible != null ? updateAllVisible.getValue() : null);
            } else {
                z = true;
                z2 = false;
            }
        } else {
            z = true;
            onClickListener = null;
            list = null;
            z2 = false;
            z3 = false;
        }
        if ((j & 160) != 0) {
            z5 = list != null ? list.isEmpty() : false;
            z4 = (128 & j) != 0 ? !z5 : false;
        } else {
            z4 = false;
            z5 = false;
        }
        int i2 = ((j & 13) > 0L ? 1 : ((j & 13) == 0L ? 0 : -1));
        if (i2 != 0) {
            if (z3) {
                z = z5;
            }
            z6 = z3 ? z4 : false;
        } else {
            z6 = false;
            z = false;
        }
        if (i2 != 0) {
            VisibilityBinding.bindVisibility(this.fragmentPendingUpdateNoUpdateContainer, z);
            VisibilityBinding.bindVisibility(this.mboundView1, z6);
            CommonBindingExtension.setRecyclerData(this.recyclerView, list);
        }
        if ((j & 12) != 0) {
            this.mboundView2.setOnClickListener(onClickListener);
        }
        if ((j & 14) != 0) {
            VisibilityBinding.bindVisibility(this.mboundView2, z2);
        }
    }
}
