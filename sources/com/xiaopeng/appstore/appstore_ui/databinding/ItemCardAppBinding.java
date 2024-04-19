package com.xiaopeng.appstore.appstore_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.ViewStubProxy;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvAppModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public abstract class ItemCardAppBinding extends ViewDataBinding {
    public final XButton btnExecute;
    public final ImageView ivIcon;
    public final XLinearLayout layoutContent;
    @Bindable
    protected AdvItemViewHolder.OnItemEventCallback mCallback;
    @Bindable
    protected AdvAppModel mModel;
    @Bindable
    protected Integer mPosition;
    @Bindable
    protected Integer mProgress;
    @Bindable
    protected Integer mState;
    public final ViewStubProxy progressBarViewStub;
    public final XTextView tvAppDesc;
    public final XTextView tvAppTitle;

    public abstract void setCallback(AdvItemViewHolder.OnItemEventCallback callback);

    public abstract void setModel(AdvAppModel model);

    public abstract void setPosition(Integer position);

    public abstract void setProgress(Integer progress);

    public abstract void setState(Integer state);

    /* JADX INFO: Access modifiers changed from: protected */
    public ItemCardAppBinding(Object _bindingComponent, View _root, int _localFieldCount, XButton btnExecute, ImageView ivIcon, XLinearLayout layoutContent, ViewStubProxy progressBarViewStub, XTextView tvAppDesc, XTextView tvAppTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btnExecute = btnExecute;
        this.ivIcon = ivIcon;
        this.layoutContent = layoutContent;
        this.progressBarViewStub = progressBarViewStub;
        this.tvAppDesc = tvAppDesc;
        this.tvAppTitle = tvAppTitle;
    }

    public AdvAppModel getModel() {
        return this.mModel;
    }

    public Integer getPosition() {
        return this.mPosition;
    }

    public Integer getState() {
        return this.mState;
    }

    public Integer getProgress() {
        return this.mProgress;
    }

    public AdvItemViewHolder.OnItemEventCallback getCallback() {
        return this.mCallback;
    }

    public static ItemCardAppBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCardAppBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemCardAppBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_card_app, root, attachToRoot, component);
    }

    public static ItemCardAppBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCardAppBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemCardAppBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_card_app, null, false, component);
    }

    public static ItemCardAppBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCardAppBinding bind(View view, Object component) {
        return (ItemCardAppBinding) bind(component, view, R.layout.item_card_app);
    }
}
