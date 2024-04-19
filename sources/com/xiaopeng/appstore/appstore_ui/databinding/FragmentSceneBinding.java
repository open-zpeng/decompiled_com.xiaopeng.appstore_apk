package com.xiaopeng.appstore.appstore_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.viewmodel.SceneViewModel;
import com.xiaopeng.appstore.common_ui.common.base.FadingEdgeRecyclerView;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public abstract class FragmentSceneBinding extends ViewDataBinding {
    public final View layoutError;
    public final ProgressBar loadingProgress;
    @Bindable
    protected SceneViewModel mViewModel;
    public final FadingEdgeRecyclerView recyclerView;
    public final XTextView tvEmptyTips;

    public abstract void setViewModel(SceneViewModel viewModel);

    /* JADX INFO: Access modifiers changed from: protected */
    public FragmentSceneBinding(Object _bindingComponent, View _root, int _localFieldCount, View layoutError, ProgressBar loadingProgress, FadingEdgeRecyclerView recyclerView, XTextView tvEmptyTips) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutError = layoutError;
        this.loadingProgress = loadingProgress;
        this.recyclerView = recyclerView;
        this.tvEmptyTips = tvEmptyTips;
    }

    public SceneViewModel getViewModel() {
        return this.mViewModel;
    }

    public static FragmentSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FragmentSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_scene, root, attachToRoot, component);
    }

    public static FragmentSceneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentSceneBinding inflate(LayoutInflater inflater, Object component) {
        return (FragmentSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_scene, null, false, component);
    }

    public static FragmentSceneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentSceneBinding bind(View view, Object component) {
        return (FragmentSceneBinding) bind(component, view, R.layout.fragment_scene);
    }
}
