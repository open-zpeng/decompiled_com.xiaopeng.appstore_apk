package com.xiaopeng.appstore.international.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.appstore.appstore_biz.model2.AppDetailModel;
import com.xiaopeng.appstore.appstore_ui.view.BizProgressButton;
import com.xiaopeng.appstore.common_ui.common.Resource;
import com.xiaopeng.appstore.international.BR;
import com.xiaopeng.appstore.international.InternationalItemDetailViewModel;
import com.xiaopeng.appstore.international.R;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class InternationalFragmentItemDetailBindingImpl extends InternationalFragmentItemDetailBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final FrameLayout mboundView0;
    private final ScrollView mboundView1;
    private final LinearLayout mboundView10;
    private final FrameLayout mboundView14;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.constraint_root, 15);
        sparseIntArray.put(R.id.tv_developer_title, 16);
        sparseIntArray.put(R.id.tv_private, 17);
    }

    public InternationalFragmentItemDetailBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }

    private InternationalFragmentItemDetailBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 5, (XButton) bindings[12], (ConstraintLayout) bindings[15], (ImageView) bindings[2], (BizProgressButton) bindings[4], (XTextView) bindings[3], (XTextView) bindings[9], (XTextView) bindings[16], (XTextView) bindings[13], (XTextView) bindings[11], (XTextView) bindings[6], (XTextView) bindings[17], (XTextView) bindings[7], (XTextView) bindings[5], (XTextView) bindings[8]);
        this.mDirtyFlags = -1L;
        this.btnRetry.setTag(null);
        this.ivIcon.setTag(null);
        FrameLayout frameLayout = (FrameLayout) bindings[0];
        this.mboundView0 = frameLayout;
        frameLayout.setTag(null);
        ScrollView scrollView = (ScrollView) bindings[1];
        this.mboundView1 = scrollView;
        scrollView.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[10];
        this.mboundView10 = linearLayout;
        linearLayout.setTag(null);
        FrameLayout frameLayout2 = (FrameLayout) bindings[14];
        this.mboundView14 = frameLayout2;
        frameLayout2.setTag(null);
        this.progressBtn.setTag(null);
        this.tvDetailTitle.setTag(null);
        this.tvDeveloper.setTag(null);
        this.tvEmptyTips.setTag(null);
        this.tvErrorTips.setTag(null);
        this.tvFileSize.setTag(null);
        this.tvSource.setTag(null);
        this.tvSpeed.setTag(null);
        this.tvSubTitle1.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 128L;
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
        if (BR.dividerColor == variableId) {
            setDividerColor((Integer) variable);
        } else if (BR.viewModel != variableId) {
            return false;
        } else {
            setViewModel((InternationalItemDetailViewModel) variable);
        }
        return true;
    }

    @Override // com.xiaopeng.appstore.international.databinding.InternationalFragmentItemDetailBinding
    public void setDividerColor(Integer DividerColor) {
        this.mDividerColor = DividerColor;
    }

    @Override // com.xiaopeng.appstore.international.databinding.InternationalFragmentItemDetailBinding
    public void setViewModel(InternationalItemDetailViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            if (localFieldId != 1) {
                if (localFieldId != 2) {
                    if (localFieldId != 3) {
                        if (localFieldId != 4) {
                            return false;
                        }
                        return onChangeViewModelSourceIsVisibleModel((MutableLiveData) object, fieldId);
                    }
                    return onChangeViewModelSpeed((MutableLiveData) object, fieldId);
                }
                return onChangeViewModelResourceLiveData((MediatorLiveData) object, fieldId);
            }
            return onChangeViewModelState((MutableLiveData) object, fieldId);
        }
        return onChangeViewModelProgress((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewModelProgress(MutableLiveData<Integer> ViewModelProgress, int fieldId) {
        if (fieldId == BR._all) {
            synchronized (this) {
                this.mDirtyFlags |= 1;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewModelState(MutableLiveData<Integer> ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized (this) {
                this.mDirtyFlags |= 2;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewModelResourceLiveData(MediatorLiveData<Resource<AppDetailModel>> ViewModelResourceLiveData, int fieldId) {
        if (fieldId == BR._all) {
            synchronized (this) {
                this.mDirtyFlags |= 4;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewModelSpeed(MutableLiveData<Long> ViewModelSpeed, int fieldId) {
        if (fieldId == BR._all) {
            synchronized (this) {
                this.mDirtyFlags |= 8;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewModelSourceIsVisibleModel(MutableLiveData<Boolean> ViewModelSourceIsVisibleModel, int fieldId) {
        if (fieldId == BR._all) {
            synchronized (this) {
                this.mDirtyFlags |= 16;
            }
            return true;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x016f  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 613
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.international.databinding.InternationalFragmentItemDetailBindingImpl.executeBindings():void");
    }
}
