package com.xiaopeng.appstore.appstore_ui.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.ViewStubProxy;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvAppModel;
import com.xiaopeng.appstore.appstore_ui.BR;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder;
import com.xiaopeng.appstore.appstore_ui.generated.callback.OnClickListener;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public class ItemGridAppBindingBindingImpl extends ItemGridAppBindingBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback23;
    private final View.OnClickListener mCallback24;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.progress_bar_view_stub, 5);
    }

    public ItemGridAppBindingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }

    private ItemGridAppBindingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (XButton) bindings[4], (FrameLayout) bindings[3], (ImageView) bindings[1], new ViewStubProxy((ViewStub) bindings[5]), null, (XTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        this.btnExecute.setTag(null);
        this.btnLayout.setTag(null);
        this.ivIcon.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.progressBarViewStub.setContainingBinding(this);
        this.tvAppTitle.setTag(null);
        setRootTag(root);
        this.mCallback23 = new OnClickListener(this, 1);
        this.mCallback24 = new OnClickListener(this, 2);
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

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemGridAppBindingBinding
    public void setModel(AdvAppModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(BR.model);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemGridAppBindingBinding
    public void setPosition(Integer Position) {
        this.mPosition = Position;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(BR.position);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemGridAppBindingBinding
    public void setProgress(Integer Progress) {
        this.mProgress = Progress;
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemGridAppBindingBinding
    public void setCallback(AdvItemViewHolder.OnItemEventCallback Callback) {
        this.mCallback = Callback;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(BR.callback);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemGridAppBindingBinding
    public void setState(Integer State) {
        this.mState = State;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(BR.state);
        super.requestRebind();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r11v9, types: [com.xiaopeng.xui.widget.XButton] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4, types: [int] */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void executeBindings() {
        /*
            r19 = this;
            r1 = r19
            monitor-enter(r19)
            long r2 = r1.mDirtyFlags     // Catch: java.lang.Throwable -> Lab
            r4 = 0
            r1.mDirtyFlags = r4     // Catch: java.lang.Throwable -> Lab
            monitor-exit(r19)     // Catch: java.lang.Throwable -> Lab
            com.xiaopeng.appstore.appstore_biz.model2.adv.AdvAppModel r0 = r1.mModel
            java.lang.Integer r6 = r1.mPosition
            com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder$OnItemEventCallback r6 = r1.mCallback
            java.lang.Integer r6 = r1.mState
            r7 = 33
            long r9 = r2 & r7
            int r9 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            r10 = 0
            if (r9 == 0) goto L2e
            if (r0 == 0) goto L22
            com.xiaopeng.appstore.appstore_biz.model2.AppBaseModel r0 = r0.getAppBaseInfo()
            goto L23
        L22:
            r0 = r10
        L23:
            if (r0 == 0) goto L2e
            java.lang.String r9 = r0.getIconUrl()
            java.lang.String r0 = r0.getAppName()
            goto L30
        L2e:
            r0 = r10
            r9 = r0
        L30:
            r11 = 48
            long r13 = r2 & r11
            int r13 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            r14 = 0
            if (r13 == 0) goto L5d
            int r6 = androidx.databinding.ViewDataBinding.safeUnbox(r6)
            java.lang.String r15 = com.xiaopeng.appstore.appstore_biz.logic.ItemLogicHelper.getExecuteDesc(r6)
            boolean r16 = com.xiaopeng.appstore.appstore_biz.logic.ItemLogicHelper.isBtnEnable(r6)
            boolean r6 = com.xiaopeng.appstore.appstore_biz.logic.ItemLogicHelper.showProgress(r6)
            if (r13 == 0) goto L54
            if (r6 == 0) goto L50
            r17 = 128(0x80, double:6.32E-322)
            goto L52
        L50:
            r17 = 64
        L52:
            long r2 = r2 | r17
        L54:
            if (r6 == 0) goto L59
            r6 = 8
            r14 = r6
        L59:
            r6 = r14
            r14 = r16
            goto L5f
        L5d:
            r15 = r10
            r6 = r14
        L5f:
            long r11 = r11 & r2
            int r11 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r11 == 0) goto L73
            com.xiaopeng.xui.widget.XButton r11 = r1.btnExecute
            r11.setEnabled(r14)
            com.xiaopeng.xui.widget.XButton r11 = r1.btnExecute
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r11, r15)
            com.xiaopeng.xui.widget.XButton r11 = r1.btnExecute
            r11.setVisibility(r6)
        L73:
            r11 = 32
            long r11 = r11 & r2
            int r6 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r6 == 0) goto L88
            android.widget.FrameLayout r6 = r1.btnLayout
            android.view.View$OnClickListener r11 = r1.mCallback24
            r6.setOnClickListener(r11)
            androidx.constraintlayout.widget.ConstraintLayout r6 = r1.mboundView0
            android.view.View$OnClickListener r11 = r1.mCallback23
            r6.setOnClickListener(r11)
        L88:
            long r2 = r2 & r7
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 == 0) goto L99
            android.widget.ImageView r2 = r1.ivIcon
            android.graphics.drawable.Drawable r10 = (android.graphics.drawable.Drawable) r10
            com.xiaopeng.appstore.appstore_ui.bindingextension.CommonBindingExtension.setIcon(r2, r9, r10, r10)
            com.xiaopeng.xui.widget.XTextView r2 = r1.tvAppTitle
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r2, r0)
        L99:
            androidx.databinding.ViewStubProxy r0 = r1.progressBarViewStub
            androidx.databinding.ViewDataBinding r0 = r0.getBinding()
            if (r0 == 0) goto Laa
            androidx.databinding.ViewStubProxy r0 = r1.progressBarViewStub
            androidx.databinding.ViewDataBinding r0 = r0.getBinding()
            executeBindingsOn(r0)
        Laa:
            return
        Lab:
            r0 = move-exception
            monitor-exit(r19)     // Catch: java.lang.Throwable -> Lab
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.appstore_ui.databinding.ItemGridAppBindingBindingImpl.executeBindings():void");
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
