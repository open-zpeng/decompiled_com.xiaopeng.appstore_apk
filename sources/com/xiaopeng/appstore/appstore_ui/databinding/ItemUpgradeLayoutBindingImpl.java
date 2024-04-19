package com.xiaopeng.appstore.appstore_ui.databinding;

import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.xiaopeng.appstore.appstore_biz.logic.ItemLogicHelper;
import com.xiaopeng.appstore.appstore_biz.model2.AppBaseModel;
import com.xiaopeng.appstore.appstore_ui.BR;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AppItemViewHolder;
import com.xiaopeng.appstore.appstore_ui.bindingextension.CommonBindingExtension;
import com.xiaopeng.appstore.appstore_ui.bindingextension.CustomBinding;
import com.xiaopeng.appstore.appstore_ui.common.BindingHelper;
import com.xiaopeng.appstore.appstore_ui.generated.callback.OnClickListener;
import com.xiaopeng.appstore.appstore_ui.viewmodel.PendingUpgradeViewModel;
import com.xiaopeng.appstore.common_ui.common.widget.MaskImageView;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XCircularProgressBar;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public class ItemUpgradeLayoutBindingImpl extends ItemUpgradeLayoutBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private final View.OnClickListener mCallback11;
    private final View.OnClickListener mCallback12;
    private final View.OnClickListener mCallback13;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final FrameLayout mboundView2;
    private final XCircularProgressBar mboundView3;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public ItemUpgradeLayoutBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private ItemUpgradeLayoutBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (XButton) bindings[7], (MaskImageView) bindings[1], (XTextView) bindings[4], (XTextView) bindings[5], (XTextView) bindings[6]);
        this.mDirtyFlags = -1L;
        this.btnExecute.setTag(null);
        this.ivIcon.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        FrameLayout frameLayout = (FrameLayout) bindings[2];
        this.mboundView2 = frameLayout;
        frameLayout.setTag(null);
        XCircularProgressBar xCircularProgressBar = (XCircularProgressBar) bindings[3];
        this.mboundView3 = xCircularProgressBar;
        xCircularProgressBar.setTag(null);
        this.tvAppTitle.setTag(null);
        this.tvUpgradeInfo.setTag(null);
        this.tvUpgradeSize.setTag(null);
        setRootTag(root);
        this.mCallback13 = new OnClickListener(this, 3);
        this.mCallback11 = new OnClickListener(this, 1);
        this.mCallback12 = new OnClickListener(this, 2);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 32L;
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
            setModel((AppBaseModel) variable);
        } else if (BR.position == variableId) {
            setPosition((Integer) variable);
        } else if (BR.progress == variableId) {
            setProgress((Integer) variable);
        } else if (BR.callback == variableId) {
            setCallback((AppItemViewHolder.OnItemEventCallback) variable);
        } else if (BR.state != variableId) {
            return false;
        } else {
            setState((Integer) variable);
        }
        return true;
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemUpgradeLayoutBinding
    public void setModel(AppBaseModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(BR.model);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemUpgradeLayoutBinding
    public void setPosition(Integer Position) {
        this.mPosition = Position;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(BR.position);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemUpgradeLayoutBinding
    public void setProgress(Integer Progress) {
        this.mProgress = Progress;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(BR.progress);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemUpgradeLayoutBinding
    public void setCallback(AppItemViewHolder.OnItemEventCallback Callback) {
        this.mCallback = Callback;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(BR.callback);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemUpgradeLayoutBinding
    public void setState(Integer State) {
        this.mState = State;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(BR.state);
        super.requestRebind();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        String str2;
        String str3;
        String str4;
        boolean z;
        boolean z2;
        int i;
        int i2;
        boolean z3;
        boolean z4;
        int i3;
        String str5;
        int i4;
        int i5;
        boolean z5;
        boolean z6;
        String str6;
        String str7;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        AppBaseModel appBaseModel = this.mModel;
        Integer num = this.mPosition;
        Integer num2 = this.mProgress;
        AppItemViewHolder.OnItemEventCallback onItemEventCallback = this.mCallback;
        Integer num3 = this.mState;
        if ((j & 33) != 0) {
            if (appBaseModel != null) {
                str2 = appBaseModel.getIconUrl();
                str7 = appBaseModel.getVersionName();
                str4 = appBaseModel.getAppName();
                str = appBaseModel.getSize();
            } else {
                str = null;
                str2 = null;
                str7 = null;
                str4 = null;
            }
            str3 = PendingUpgradeViewModel.getVersionName(str7);
        } else {
            str = null;
            str2 = null;
            str3 = null;
            str4 = null;
        }
        if ((j & 52) != 0) {
            int safeUnbox = ViewDataBinding.safeUnbox(num2);
            int safeUnbox2 = ViewDataBinding.safeUnbox(num3);
            int i6 = ((j & 48) > 0L ? 1 : ((j & 48) == 0L ? 0 : -1));
            if (i6 != 0) {
                str6 = ItemLogicHelper.getUpgradeOrCancelDesc(safeUnbox2);
                i5 = BindingHelper.getProgressType(safeUnbox2);
                z5 = ItemLogicHelper.isProgressEnable(safeUnbox2);
                boolean isPendingBtnEnable = ItemLogicHelper.isPendingBtnEnable(safeUnbox2);
                z6 = ItemLogicHelper.showProgress(safeUnbox2);
                if (i6 != 0) {
                    j |= z6 ? 128L : 64L;
                }
                i4 = z6 ? 0 : 8;
                r19 = isPendingBtnEnable ? 1 : 0;
            } else {
                i4 = 0;
                i5 = 0;
                z5 = false;
                z6 = false;
                str6 = null;
            }
            i3 = safeUnbox;
            z4 = ItemLogicHelper.isIndeterminate(safeUnbox, safeUnbox2);
            i2 = i4;
            z = r19;
            str5 = str6;
            i = i5;
            z3 = z5;
            z2 = z6;
        } else {
            z = false;
            z2 = false;
            i = 0;
            i2 = 0;
            z3 = false;
            z4 = false;
            i3 = 0;
            str5 = null;
        }
        if ((j & 48) != 0) {
            this.btnExecute.setEnabled(z);
            TextViewBindingAdapter.setText(this.btnExecute, str5);
            CommonBindingExtension.setImageShowMask(this.ivIcon, z2);
            this.mboundView2.setVisibility(i2);
            this.mboundView3.setEnabled(z3);
            CustomBinding.setIndicatorType(this.mboundView3, i);
        }
        if ((32 & j) != 0) {
            this.btnExecute.setOnClickListener(this.mCallback13);
            this.mboundView0.setOnClickListener(this.mCallback11);
            this.mboundView2.setOnClickListener(this.mCallback12);
        }
        if ((33 & j) != 0) {
            Drawable drawable = null;
            CommonBindingExtension.setIcon(this.ivIcon, str2, drawable, drawable);
            TextViewBindingAdapter.setText(this.tvAppTitle, str4);
            TextViewBindingAdapter.setText(this.tvUpgradeInfo, str3);
            TextViewBindingAdapter.setText(this.tvUpgradeSize, str);
        }
        if ((52 & j) != 0) {
            this.mboundView3.setIndeterminate(z4);
        }
        if ((j & 36) != 0) {
            this.mboundView3.setProgress(i3);
        }
    }

    @Override // com.xiaopeng.appstore.appstore_ui.generated.callback.OnClickListener.Listener
    public final void _internalCallbackOnClick(int sourceId, View callbackArg_0) {
        if (sourceId == 1) {
            AppBaseModel appBaseModel = this.mModel;
            Integer num = this.mPosition;
            AppItemViewHolder.OnItemEventCallback onItemEventCallback = this.mCallback;
            if (onItemEventCallback != null) {
                onItemEventCallback.onItemClick(callbackArg_0, num.intValue(), appBaseModel);
            }
        } else if (sourceId == 2) {
            AppBaseModel appBaseModel2 = this.mModel;
            Integer num2 = this.mPosition;
            AppItemViewHolder.OnItemEventCallback onItemEventCallback2 = this.mCallback;
            if (onItemEventCallback2 != null) {
                onItemEventCallback2.onProgressBtnClick(callbackArg_0, num2.intValue(), appBaseModel2);
            }
        } else if (sourceId != 3) {
        } else {
            AppBaseModel appBaseModel3 = this.mModel;
            Integer num3 = this.mPosition;
            AppItemViewHolder.OnItemEventCallback onItemEventCallback3 = this.mCallback;
            if (onItemEventCallback3 != null) {
                onItemEventCallback3.onBtnClick(callbackArg_0, num3.intValue(), appBaseModel3);
            }
        }
    }
}
