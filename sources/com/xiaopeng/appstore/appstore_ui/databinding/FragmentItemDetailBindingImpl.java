package com.xiaopeng.appstore.appstore_ui.databinding;

import android.support.v4.media.session.PlaybackStateCompat;
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
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.appstore_biz.model2.AppDetailModel;
import com.xiaopeng.appstore.appstore_ui.BR;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.view.BizProgressButton;
import com.xiaopeng.appstore.appstore_ui.view.ExpandableTextView;
import com.xiaopeng.appstore.appstore_ui.viewmodel.ItemDetailViewModel;
import com.xiaopeng.appstore.common_ui.common.Resource;
import com.xiaopeng.xui.view.XView;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XLoading;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public class FragmentItemDetailBindingImpl extends FragmentItemDetailBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final FrameLayout mboundView0;
    private final ScrollView mboundView1;
    private final LinearLayout mboundView22;
    private final XLoading mboundView26;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.constraint_root, 27);
        sparseIntArray.put(R.id.tv_image_title, 28);
        sparseIntArray.put(R.id.tv_developer_title, 29);
        sparseIntArray.put(R.id.tv_private, 30);
        sparseIntArray.put(R.id.tv_permission, 31);
        sparseIntArray.put(R.id.tv_contact, 32);
        sparseIntArray.put(R.id.tv_protocol, 33);
    }

    public FragmentItemDetailBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 34, sIncludes, sViewsWithIds));
    }

    private FragmentItemDetailBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 12, (XButton) bindings[6], (XButton) bindings[24], (ConstraintLayout) bindings[27], (XView) bindings[10], (XView) bindings[14], (XView) bindings[16], (XView) bindings[20], (ImageView) bindings[2], (BizProgressButton) bindings[5], (RecyclerView) bindings[15], (XTextView) bindings[32], (ExpandableTextView) bindings[13], (ExpandableTextView) bindings[19], (XTextView) bindings[3], (XTextView) bindings[21], (XTextView) bindings[29], (XTextView) bindings[25], (XTextView) bindings[23], (XTextView) bindings[8], (XTextView) bindings[28], (XTextView) bindings[31], (XTextView) bindings[30], (XTextView) bindings[33], (XTextView) bindings[9], (XTextView) bindings[7], (XTextView) bindings[4], (XTextView) bindings[12], (XTextView) bindings[18], (XTextView) bindings[11], (XTextView) bindings[17]);
        this.mDirtyFlags = -1L;
        this.btnCancel.setTag(null);
        this.btnRetry.setTag(null);
        this.divider1.setTag(null);
        this.divider2.setTag(null);
        this.divider3.setTag(null);
        this.divider4.setTag(null);
        this.ivIcon.setTag(null);
        FrameLayout frameLayout = (FrameLayout) bindings[0];
        this.mboundView0 = frameLayout;
        frameLayout.setTag(null);
        ScrollView scrollView = (ScrollView) bindings[1];
        this.mboundView1 = scrollView;
        scrollView.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[22];
        this.mboundView22 = linearLayout;
        linearLayout.setTag(null);
        XLoading xLoading = (XLoading) bindings[26];
        this.mboundView26 = xLoading;
        xLoading.setTag(null);
        this.progressBtn.setTag(null);
        this.rvImage.setTag(null);
        this.tvDesc1.setTag(null);
        this.tvDesc2.setTag(null);
        this.tvDetailTitle.setTag(null);
        this.tvDeveloper.setTag(null);
        this.tvEmptyTips.setTag(null);
        this.tvErrorTips.setTag(null);
        this.tvFileSize.setTag(null);
        this.tvSource.setTag(null);
        this.tvSpeed.setTag(null);
        this.tvSubTitle.setTag(null);
        this.tvSubTitle1.setTag(null);
        this.tvSubTitle2.setTag(null);
        this.tvTitle1.setTag(null);
        this.tvTitle2.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = PlaybackStateCompat.ACTION_PREPARE;
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
            setViewModel((ItemDetailViewModel) variable);
        }
        return true;
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.FragmentItemDetailBinding
    public void setDividerColor(Integer DividerColor) {
        this.mDividerColor = DividerColor;
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        }
        notifyPropertyChanged(BR.dividerColor);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.FragmentItemDetailBinding
    public void setViewModel(ItemDetailViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_URI;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeViewModelProgress((MutableLiveData) object, fieldId);
            case 1:
                return onChangeViewModelState((MutableLiveData) object, fieldId);
            case 2:
                return onChangeViewModelResourceLiveData((MediatorLiveData) object, fieldId);
            case 3:
                return onChangeViewModelDesc2((MutableLiveData) object, fieldId);
            case 4:
                return onChangeViewModelSubTitle1((MutableLiveData) object, fieldId);
            case 5:
                return onChangeViewModelSpeed((MutableLiveData) object, fieldId);
            case 6:
                return onChangeViewModelTitle2((MutableLiveData) object, fieldId);
            case 7:
                return onChangeViewModelSpeedVisibility((MutableLiveData) object, fieldId);
            case 8:
                return onChangeViewModelSourceIsVisibleModel((MutableLiveData) object, fieldId);
            case 9:
                return onChangeViewModelDesc1((MutableLiveData) object, fieldId);
            case 10:
                return onChangeViewModelSubTitle2((MutableLiveData) object, fieldId);
            case 11:
                return onChangeViewModelTitle1((MutableLiveData) object, fieldId);
            default:
                return false;
        }
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

    private boolean onChangeViewModelDesc2(MutableLiveData<String> ViewModelDesc2, int fieldId) {
        if (fieldId == BR._all) {
            synchronized (this) {
                this.mDirtyFlags |= 8;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewModelSubTitle1(MutableLiveData<String> ViewModelSubTitle1, int fieldId) {
        if (fieldId == BR._all) {
            synchronized (this) {
                this.mDirtyFlags |= 16;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewModelSpeed(MutableLiveData<Long> ViewModelSpeed, int fieldId) {
        if (fieldId == BR._all) {
            synchronized (this) {
                this.mDirtyFlags |= 32;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewModelTitle2(MutableLiveData<String> ViewModelTitle2, int fieldId) {
        if (fieldId == BR._all) {
            synchronized (this) {
                this.mDirtyFlags |= 64;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewModelSpeedVisibility(MutableLiveData<Boolean> ViewModelSpeedVisibility, int fieldId) {
        if (fieldId == BR._all) {
            synchronized (this) {
                this.mDirtyFlags |= 128;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewModelSourceIsVisibleModel(MutableLiveData<Boolean> ViewModelSourceIsVisibleModel, int fieldId) {
        if (fieldId == BR._all) {
            synchronized (this) {
                this.mDirtyFlags |= 256;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewModelDesc1(MutableLiveData<String> ViewModelDesc1, int fieldId) {
        if (fieldId == BR._all) {
            synchronized (this) {
                this.mDirtyFlags |= 512;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewModelSubTitle2(MutableLiveData<String> ViewModelSubTitle2, int fieldId) {
        if (fieldId == BR._all) {
            synchronized (this) {
                this.mDirtyFlags |= 1024;
            }
            return true;
        }
        return false;
    }

    private boolean onChangeViewModelTitle1(MutableLiveData<String> ViewModelTitle1, int fieldId) {
        if (fieldId == BR._all) {
            synchronized (this) {
                this.mDirtyFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
            }
            return true;
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:134:0x0260, code lost:
        if (r15 != false) goto L181;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0263  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x026e  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x02ac  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x02ba  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01c8  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01e2  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 1164
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.appstore_ui.databinding.FragmentItemDetailBindingImpl.executeBindings():void");
    }
}
