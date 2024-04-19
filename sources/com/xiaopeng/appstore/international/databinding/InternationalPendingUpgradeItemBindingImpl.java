package com.xiaopeng.appstore.international.databinding;

import android.graphics.drawable.Drawable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.xiaopeng.appstore.appstore_biz.logic.ItemLogicHelper;
import com.xiaopeng.appstore.appstore_biz.model2.AppBaseModel;
import com.xiaopeng.appstore.appstore_ui.bindingextension.CommonBindingExtension;
import com.xiaopeng.appstore.appstore_ui.bindingextension.CustomBinding;
import com.xiaopeng.appstore.appstore_ui.common.BindingHelper;
import com.xiaopeng.appstore.appstore_ui.viewmodel.PendingUpgradeViewModel;
import com.xiaopeng.appstore.common_ui.common.widget.MaskImageView;
import com.xiaopeng.appstore.international.BR;
import com.xiaopeng.appstore.international.R;
import com.xiaopeng.appstore.international.generated.callback.OnClickListener;
import com.xiaopeng.appstore.international.upgrade.InternationalAppItemViewHolder;
import com.xiaopeng.xui.view.XView;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XCircularProgressBar;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class InternationalPendingUpgradeItemBindingImpl extends InternationalPendingUpgradeItemBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback1;
    private final View.OnClickListener mCallback2;
    private final View.OnClickListener mCallback3;
    private final View.OnClickListener mCallback4;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final FrameLayout mboundView2;
    private final XCircularProgressBar mboundView3;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_upgrade_info_container, 11);
        sparseIntArray.put(R.id.expand_content_container, 12);
        sparseIntArray.put(R.id.expand_divider, 13);
    }

    public InternationalPendingUpgradeItemBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }

    private InternationalPendingUpgradeItemBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (XButton) bindings[9], (XLinearLayout) bindings[7], (XLinearLayout) bindings[12], (XTextView) bindings[10], (XView) bindings[13], (XTextView) bindings[8], (MaskImageView) bindings[1], (XTextView) bindings[4], (XTextView) bindings[5], (XLinearLayout) bindings[11], (XTextView) bindings[6]);
        this.mDirtyFlags = -1L;
        this.btnExecute.setTag(null);
        this.expandButtonContainer.setTag(null);
        this.expandContentText.setTag(null);
        this.internationExpand.setTag(null);
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
        this.mCallback2 = new OnClickListener(this, 2);
        this.mCallback1 = new OnClickListener(this, 1);
        this.mCallback4 = new OnClickListener(this, 4);
        this.mCallback3 = new OnClickListener(this, 3);
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
        if (BR.model == variableId) {
            setModel((AppBaseModel) variable);
        } else if (BR.callback == variableId) {
            setCallback((InternationalAppItemViewHolder.OnItemEventCallback) variable);
        } else if (BR.expanded == variableId) {
            setExpanded((Boolean) variable);
        } else if (BR.state == variableId) {
            setState((Integer) variable);
        } else if (BR.viewHolder == variableId) {
            setViewHolder((InternationalAppItemViewHolder) variable);
        } else if (BR.position == variableId) {
            setPosition((Integer) variable);
        } else if (BR.progress != variableId) {
            return false;
        } else {
            setProgress((Integer) variable);
        }
        return true;
    }

    @Override // com.xiaopeng.appstore.international.databinding.InternationalPendingUpgradeItemBinding
    public void setModel(AppBaseModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(BR.model);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.international.databinding.InternationalPendingUpgradeItemBinding
    public void setCallback(InternationalAppItemViewHolder.OnItemEventCallback Callback) {
        this.mCallback = Callback;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(BR.callback);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.international.databinding.InternationalPendingUpgradeItemBinding
    public void setExpanded(Boolean Expanded) {
        this.mExpanded = Expanded;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(BR.expanded);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.international.databinding.InternationalPendingUpgradeItemBinding
    public void setState(Integer State) {
        this.mState = State;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(BR.state);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.international.databinding.InternationalPendingUpgradeItemBinding
    public void setViewHolder(InternationalAppItemViewHolder ViewHolder) {
        this.mViewHolder = ViewHolder;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(BR.viewHolder);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.international.databinding.InternationalPendingUpgradeItemBinding
    public void setPosition(Integer Position) {
        this.mPosition = Position;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(BR.position);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.international.databinding.InternationalPendingUpgradeItemBinding
    public void setProgress(Integer Progress) {
        this.mProgress = Progress;
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        notifyPropertyChanged(BR.progress);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        int i;
        Drawable drawable;
        String str6;
        String str7;
        boolean z;
        boolean z2;
        int i2;
        int i3;
        boolean z3;
        boolean z4;
        int i4;
        String str8;
        boolean z5;
        int i5;
        boolean z6;
        boolean z7;
        String str9;
        long j2;
        long j3;
        String str10;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        AppBaseModel appBaseModel = this.mModel;
        InternationalAppItemViewHolder.OnItemEventCallback onItemEventCallback = this.mCallback;
        Boolean bool = this.mExpanded;
        Integer num = this.mState;
        InternationalAppItemViewHolder internationalAppItemViewHolder = this.mViewHolder;
        Integer num2 = this.mPosition;
        Integer num3 = this.mProgress;
        if ((j & 129) != 0) {
            if (appBaseModel != null) {
                str10 = appBaseModel.getVersionName();
                str3 = appBaseModel.getSize();
                str4 = appBaseModel.getVersionDesc();
                str5 = appBaseModel.getIconUrl();
                str = appBaseModel.getAppName();
            } else {
                str = null;
                str10 = null;
                str3 = null;
                str4 = null;
                str5 = null;
            }
            str2 = PendingUpgradeViewModel.getVersionName(str10);
        } else {
            str = null;
            str2 = null;
            str3 = null;
            str4 = null;
            str5 = null;
        }
        int i6 = ((j & 132) > 0L ? 1 : ((j & 132) == 0L ? 0 : -1));
        int i7 = 0;
        if (i6 != 0) {
            boolean z8 = ViewDataBinding.safeUnbox(bool);
            if (i6 != 0) {
                if (z8) {
                    j2 = j | 512;
                    j3 = PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
                } else {
                    j2 = j | 256;
                    j3 = 1024;
                }
                j = j2 | j3;
            }
            i = z8 ? 0 : 8;
            drawable = z8 ? AppCompatResources.getDrawable(this.internationExpand.getContext(), R.drawable.expand_small_upper) : AppCompatResources.getDrawable(this.internationExpand.getContext(), R.drawable.expand_small_lower);
        } else {
            i = 0;
            drawable = null;
        }
        if ((j & 200) != 0) {
            int safeUnbox = ViewDataBinding.safeUnbox(num);
            int safeUnbox2 = ViewDataBinding.safeUnbox(num3);
            int i8 = ((j & 136) > 0L ? 1 : ((j & 136) == 0L ? 0 : -1));
            if (i8 != 0) {
                z5 = ItemLogicHelper.isProgressEnable(safeUnbox);
                str9 = ItemLogicHelper.getStartOrCancelDesc(safeUnbox);
                int progressType = BindingHelper.getProgressType(safeUnbox);
                z6 = ItemLogicHelper.isBtnEnable(safeUnbox);
                z7 = ItemLogicHelper.showProgress(safeUnbox);
                if (i8 != 0) {
                    j |= z7 ? PlaybackStateCompat.ACTION_PLAY_FROM_URI : PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
                }
                i5 = z7 ? 0 : 8;
                i7 = progressType;
            } else {
                z5 = false;
                i5 = 0;
                z6 = false;
                z7 = false;
                str9 = null;
            }
            z4 = ItemLogicHelper.isIndeterminate(safeUnbox2, safeUnbox);
            i4 = safeUnbox2;
            str8 = str9;
            z = z6;
            z2 = z7;
            str7 = str3;
            z3 = z5;
            i3 = i5;
            str6 = str2;
            i2 = i7;
        } else {
            str6 = str2;
            str7 = str3;
            z = false;
            z2 = false;
            i2 = 0;
            i3 = 0;
            z3 = false;
            z4 = false;
            i4 = 0;
            str8 = null;
        }
        if ((j & 136) != 0) {
            this.btnExecute.setEnabled(z);
            TextViewBindingAdapter.setText(this.btnExecute, str8);
            CommonBindingExtension.setImageShowMask(this.ivIcon, z2);
            this.mboundView2.setVisibility(i3);
            this.mboundView3.setEnabled(z3);
            CustomBinding.setIndicatorType(this.mboundView3, i2);
        }
        if ((128 & j) != 0) {
            this.btnExecute.setOnClickListener(this.mCallback4);
            this.expandButtonContainer.setOnClickListener(this.mCallback3);
            this.mboundView0.setOnClickListener(this.mCallback1);
            this.mboundView2.setOnClickListener(this.mCallback2);
        }
        if ((j & 132) != 0) {
            this.expandContentText.setVisibility(i);
            TextViewBindingAdapter.setDrawableEnd(this.internationExpand, drawable);
        }
        if ((129 & j) != 0) {
            TextViewBindingAdapter.setText(this.expandContentText, str4);
            Drawable drawable2 = null;
            CommonBindingExtension.setIcon(this.ivIcon, str5, drawable2, drawable2);
            TextViewBindingAdapter.setText(this.tvAppTitle, str);
            TextViewBindingAdapter.setText(this.tvUpgradeInfo, str6);
            TextViewBindingAdapter.setText(this.tvUpgradeSize, str7);
        }
        if ((j & 200) != 0) {
            this.mboundView3.setIndeterminate(z4);
        }
        if ((j & 192) != 0) {
            this.mboundView3.setProgress(i4);
        }
    }

    @Override // com.xiaopeng.appstore.international.generated.callback.OnClickListener.Listener
    public final void _internalCallbackOnClick(int sourceId, View callbackArg_0) {
        if (sourceId == 1) {
            AppBaseModel appBaseModel = this.mModel;
            Integer num = this.mPosition;
            InternationalAppItemViewHolder internationalAppItemViewHolder = this.mViewHolder;
            InternationalAppItemViewHolder.OnItemEventCallback onItemEventCallback = this.mCallback;
            if (onItemEventCallback != null) {
                onItemEventCallback.onExpandClick(internationalAppItemViewHolder, callbackArg_0, num.intValue(), appBaseModel);
            }
        } else if (sourceId == 2) {
            AppBaseModel appBaseModel2 = this.mModel;
            Integer num2 = this.mPosition;
            InternationalAppItemViewHolder internationalAppItemViewHolder2 = this.mViewHolder;
            InternationalAppItemViewHolder.OnItemEventCallback onItemEventCallback2 = this.mCallback;
            if (onItemEventCallback2 != null) {
                onItemEventCallback2.onProgressBtnClick(internationalAppItemViewHolder2, callbackArg_0, num2.intValue(), appBaseModel2);
            }
        } else if (sourceId == 3) {
            AppBaseModel appBaseModel3 = this.mModel;
            Integer num3 = this.mPosition;
            InternationalAppItemViewHolder internationalAppItemViewHolder3 = this.mViewHolder;
            InternationalAppItemViewHolder.OnItemEventCallback onItemEventCallback3 = this.mCallback;
            if (onItemEventCallback3 != null) {
                onItemEventCallback3.onExpandClick(internationalAppItemViewHolder3, callbackArg_0, num3.intValue(), appBaseModel3);
            }
        } else if (sourceId != 4) {
        } else {
            AppBaseModel appBaseModel4 = this.mModel;
            Integer num4 = this.mPosition;
            InternationalAppItemViewHolder internationalAppItemViewHolder4 = this.mViewHolder;
            InternationalAppItemViewHolder.OnItemEventCallback onItemEventCallback4 = this.mCallback;
            if (onItemEventCallback4 != null) {
                onItemEventCallback4.onBtnClick(internationalAppItemViewHolder4, callbackArg_0, num4.intValue(), appBaseModel4);
            }
        }
    }
}
