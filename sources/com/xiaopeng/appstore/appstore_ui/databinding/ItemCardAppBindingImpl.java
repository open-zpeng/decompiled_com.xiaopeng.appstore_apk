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
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public class ItemCardAppBindingImpl extends ItemCardAppBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback19;
    private final View.OnClickListener mCallback20;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final FrameLayout mboundView3;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_content, 6);
        sparseIntArray.put(R.id.progress_bar_view_stub, 7);
    }

    public ItemCardAppBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private ItemCardAppBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (XButton) bindings[4], (ImageView) bindings[5], (XLinearLayout) bindings[6], new ViewStubProxy((ViewStub) bindings[7]), (XTextView) bindings[2], (XTextView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.btnExecute.setTag(null);
        this.ivIcon.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        FrameLayout frameLayout = (FrameLayout) bindings[3];
        this.mboundView3 = frameLayout;
        frameLayout.setTag(null);
        this.progressBarViewStub.setContainingBinding(this);
        this.tvAppDesc.setTag(null);
        this.tvAppTitle.setTag(null);
        setRootTag(root);
        this.mCallback19 = new OnClickListener(this, 1);
        this.mCallback20 = new OnClickListener(this, 2);
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

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemCardAppBinding
    public void setModel(AdvAppModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(BR.model);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemCardAppBinding
    public void setPosition(Integer Position) {
        this.mPosition = Position;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(BR.position);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemCardAppBinding
    public void setProgress(Integer Progress) {
        this.mProgress = Progress;
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemCardAppBinding
    public void setCallback(AdvItemViewHolder.OnItemEventCallback Callback) {
        this.mCallback = Callback;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(BR.callback);
        super.requestRebind();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.databinding.ItemCardAppBinding
    public void setState(Integer State) {
        this.mState = State;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(BR.state);
        super.requestRebind();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r12v5, types: [com.xiaopeng.xui.widget.XButton] */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4, types: [int] */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void executeBindings() {
        /*
            r20 = this;
            r1 = r20
            monitor-enter(r20)
            long r2 = r1.mDirtyFlags     // Catch: java.lang.Throwable -> Lb8
            r4 = 0
            r1.mDirtyFlags = r4     // Catch: java.lang.Throwable -> Lb8
            monitor-exit(r20)     // Catch: java.lang.Throwable -> Lb8
            com.xiaopeng.appstore.appstore_biz.model2.adv.AdvAppModel r0 = r1.mModel
            java.lang.Integer r6 = r1.mPosition
            com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder$OnItemEventCallback r6 = r1.mCallback
            java.lang.Integer r6 = r1.mState
            r7 = 33
            long r9 = r2 & r7
            int r9 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            r10 = 0
            if (r9 == 0) goto L32
            if (r0 == 0) goto L22
            com.xiaopeng.appstore.appstore_biz.model2.AppBaseModel r0 = r0.getAppBaseInfo()
            goto L23
        L22:
            r0 = r10
        L23:
            if (r0 == 0) goto L32
            java.lang.String r9 = r0.getIconUrl()
            java.lang.String r11 = r0.getBriefDesc()
            java.lang.String r0 = r0.getAppName()
            goto L35
        L32:
            r0 = r10
            r9 = r0
            r11 = r9
        L35:
            r12 = 48
            long r14 = r2 & r12
            int r14 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
            r15 = 0
            if (r14 == 0) goto L64
            int r6 = androidx.databinding.ViewDataBinding.safeUnbox(r6)
            java.lang.String r16 = com.xiaopeng.appstore.appstore_biz.logic.ItemLogicHelper.getExecuteDesc(r6)
            boolean r17 = com.xiaopeng.appstore.appstore_biz.logic.ItemLogicHelper.isBtnEnable(r6)
            boolean r6 = com.xiaopeng.appstore.appstore_biz.logic.ItemLogicHelper.showProgress(r6)
            if (r14 == 0) goto L59
            if (r6 == 0) goto L55
            r18 = 128(0x80, double:6.32E-322)
            goto L57
        L55:
            r18 = 64
        L57:
            long r2 = r2 | r18
        L59:
            if (r6 == 0) goto L5e
            r6 = 8
            r15 = r6
        L5e:
            r6 = r15
            r14 = r16
            r15 = r17
            goto L66
        L64:
            r14 = r10
            r6 = r15
        L66:
            long r12 = r12 & r2
            int r12 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r12 == 0) goto L7a
            com.xiaopeng.xui.widget.XButton r12 = r1.btnExecute
            r12.setEnabled(r15)
            com.xiaopeng.xui.widget.XButton r12 = r1.btnExecute
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r12, r14)
            com.xiaopeng.xui.widget.XButton r12 = r1.btnExecute
            r12.setVisibility(r6)
        L7a:
            long r6 = r2 & r7
            int r6 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r6 == 0) goto L91
            android.widget.ImageView r6 = r1.ivIcon
            android.graphics.drawable.Drawable r10 = (android.graphics.drawable.Drawable) r10
            com.xiaopeng.appstore.appstore_ui.bindingextension.CommonBindingExtension.setIcon(r6, r9, r10, r10)
            com.xiaopeng.xui.widget.XTextView r6 = r1.tvAppDesc
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r6, r11)
            com.xiaopeng.xui.widget.XTextView r6 = r1.tvAppTitle
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r6, r0)
        L91:
            r6 = 32
            long r2 = r2 & r6
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 == 0) goto La6
            androidx.constraintlayout.widget.ConstraintLayout r0 = r1.mboundView0
            android.view.View$OnClickListener r2 = r1.mCallback19
            r0.setOnClickListener(r2)
            android.widget.FrameLayout r0 = r1.mboundView3
            android.view.View$OnClickListener r2 = r1.mCallback20
            r0.setOnClickListener(r2)
        La6:
            androidx.databinding.ViewStubProxy r0 = r1.progressBarViewStub
            androidx.databinding.ViewDataBinding r0 = r0.getBinding()
            if (r0 == 0) goto Lb7
            androidx.databinding.ViewStubProxy r0 = r1.progressBarViewStub
            androidx.databinding.ViewDataBinding r0 = r0.getBinding()
            executeBindingsOn(r0)
        Lb7:
            return
        Lb8:
            r0 = move-exception
            monitor-exit(r20)     // Catch: java.lang.Throwable -> Lb8
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.appstore_ui.databinding.ItemCardAppBindingImpl.executeBindings():void");
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
