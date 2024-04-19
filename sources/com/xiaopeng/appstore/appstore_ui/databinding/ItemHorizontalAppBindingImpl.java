package com.xiaopeng.appstore.appstore_ui.databinding;

import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.ViewStubProxy;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.xiaopeng.appstore.appstore_biz.logic.ItemLogicHelper;
import com.xiaopeng.appstore.appstore_biz.model2.AppBaseModel;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvAppModel;
import com.xiaopeng.appstore.appstore_ui.BR;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder;
import com.xiaopeng.appstore.appstore_ui.bindingextension.CommonBindingExtension;
import com.xiaopeng.appstore.appstore_ui.generated.callback.OnClickListener;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XConstraintLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public class ItemHorizontalAppBindingImpl extends ItemHorizontalAppBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback7;
    private final View.OnClickListener mCallback8;
    private long mDirtyFlags;
    private final XConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.progress_bar_view_stub, 6);
    }

    public ItemHorizontalAppBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }

    private ItemHorizontalAppBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (XButton) bindings[5], (ImageView) bindings[1], (FrameLayout) bindings[4], new ViewStubProxy((ViewStub) bindings[6]), (XTextView) bindings[3], (XTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        this.btnExecute.setTag(null);
        this.ivIcon.setTag(null);
        this.layoutBtn.setTag(null);
        XConstraintLayout xConstraintLayout = (XConstraintLayout) bindings[0];
        this.mboundView0 = xConstraintLayout;
        xConstraintLayout.setTag(null);
        this.progressBarViewStub.setContainingBinding(this);
        this.tvAppDesc.setTag(null);
        this.tvAppTitle.setTag(null);
        setRootTag(root);
        this.mCallback8 = new OnClickListener(this, 2);
        this.mCallback7 = new OnClickListener(this, 1);
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
            setModel((AdvAppModel) variable);
        } else if (BR.position == variableId) {
            setPosition((Integer) variable);
        } else if (BR.progress == variableId) {
            setProgress((Integer) variable);
        } else if (BR.callback == variableId) {
            setCallback((AdvItemViewHolder.OnItemEventCallback) variable);
        } else if (BR.state != variableId) {
            return false;
        } else {
            setState((Integer) variable);
        }
        return true;
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemHorizontalAppBinding
    public void setModel(AdvAppModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(BR.model);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemHorizontalAppBinding
    public void setPosition(Integer Position) {
        this.mPosition = Position;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(BR.position);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemHorizontalAppBinding
    public void setProgress(Integer Progress) {
        this.mProgress = Progress;
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemHorizontalAppBinding
    public void setCallback(AdvItemViewHolder.OnItemEventCallback Callback) {
        this.mCallback = Callback;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(BR.callback);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemHorizontalAppBinding
    public void setState(Integer State) {
        this.mState = State;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(BR.state);
        super.requestRebind();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v5, types: [com.xiaopeng.xui.widget.XButton] */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4, types: [int] */
    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        String str2;
        String str3;
        String str4;
        ?? r6;
        AppBaseModel appBaseModel;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        AdvAppModel advAppModel = this.mModel;
        Integer num = this.mPosition;
        AdvItemViewHolder.OnItemEventCallback onItemEventCallback = this.mCallback;
        Integer num2 = this.mState;
        if ((j & 33) != 0) {
            if (advAppModel != null) {
                str2 = advAppModel.getIconUrl();
                appBaseModel = advAppModel.getAppBaseInfo();
            } else {
                appBaseModel = null;
                str2 = null;
            }
            if (appBaseModel != null) {
                str3 = appBaseModel.getBriefDesc();
                str = appBaseModel.getAppName();
            } else {
                str = null;
                str3 = null;
            }
        } else {
            str = null;
            str2 = null;
            str3 = null;
        }
        int i = ((j & 48) > 0L ? 1 : ((j & 48) == 0L ? 0 : -1));
        if (i != 0) {
            int safeUnbox = ViewDataBinding.safeUnbox(num2);
            String executeDesc = ItemLogicHelper.getExecuteDesc(safeUnbox);
            boolean isBtnEnable = ItemLogicHelper.isBtnEnable(safeUnbox);
            boolean showProgress = ItemLogicHelper.showProgress(safeUnbox);
            if (i != 0) {
                j |= showProgress ? 128L : 64L;
            }
            r6 = showProgress ? true : false;
            str4 = executeDesc;
            r15 = isBtnEnable;
        } else {
            str4 = null;
            r6 = 0;
        }
        if ((48 & j) != 0) {
            this.btnExecute.setEnabled(r15);
            TextViewBindingAdapter.setText(this.btnExecute, str4);
            this.btnExecute.setVisibility(r6);
        }
        if ((j & 33) != 0) {
            Drawable drawable = null;
            CommonBindingExtension.setIcon(this.ivIcon, str2, drawable, drawable);
            TextViewBindingAdapter.setText(this.tvAppDesc, str3);
            TextViewBindingAdapter.setText(this.tvAppTitle, str);
        }
        if ((j & 32) != 0) {
            this.layoutBtn.setOnClickListener(this.mCallback8);
            this.mboundView0.setOnClickListener(this.mCallback7);
        }
        if (this.progressBarViewStub.getBinding() != null) {
            executeBindingsOn(this.progressBarViewStub.getBinding());
        }
    }

    @Override // com.xiaopeng.appstore.appstore_ui.generated.callback.OnClickListener.Listener
    public final void _internalCallbackOnClick(int sourceId, View callbackArg_0) {
        if (sourceId == 1) {
            AdvAppModel advAppModel = this.mModel;
            Integer num = this.mPosition;
            AdvItemViewHolder.OnItemEventCallback onItemEventCallback = this.mCallback;
            if (onItemEventCallback != null) {
                onItemEventCallback.onItemClick(callbackArg_0, num.intValue(), advAppModel);
            }
        } else if (sourceId != 2) {
        } else {
            AdvAppModel advAppModel2 = this.mModel;
            Integer num2 = this.mPosition;
            AdvItemViewHolder.OnItemEventCallback onItemEventCallback2 = this.mCallback;
            if (onItemEventCallback2 != null) {
                onItemEventCallback2.onBtnClick(callbackArg_0, num2.intValue(), advAppModel2);
            }
        }
    }
}
