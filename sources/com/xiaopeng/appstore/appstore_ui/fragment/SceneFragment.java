package com.xiaopeng.appstore.appstore_ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.ScenePageAdapter;
import com.xiaopeng.appstore.appstore_ui.databinding.FragmentSceneBinding;
import com.xiaopeng.appstore.appstore_ui.viewmodel.SceneViewModel;
import com.xiaopeng.appstore.common_ui.BaseBizFragment;
import com.xiaopeng.appstore.common_ui.common.Resource;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
import com.xiaopeng.libtheme.ThemeManager;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: SceneFragment.kt */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J&\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\b\u0010\u0018\u001a\u00020\fH\u0016J\b\u0010\u0019\u001a\u00020\fH\u0016J\b\u0010\u001a\u001a\u00020\fH\u0002J\b\u0010\u001b\u001a\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lcom/xiaopeng/appstore/appstore_ui/fragment/SceneFragment;", "Lcom/xiaopeng/appstore/common_ui/BaseBizFragment;", "()V", "mActivityId", "", "mAdapter", "Lcom/xiaopeng/appstore/appstore_ui/adapter/ScenePageAdapter;", "mBinding", "Lcom/xiaopeng/appstore/appstore_ui/databinding/FragmentSceneBinding;", "mViewModel", "Lcom/xiaopeng/appstore/appstore_ui/viewmodel/SceneViewModel;", "onActivityCreated", "", "savedInstanceState", "Landroid/os/Bundle;", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroy", "onResume", "requestData", "subscribeViewModel", "Companion", "appstore_ui_D55V1Release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class SceneFragment extends BaseBizFragment {
    public static final String ACTIVITY_ID = "activity_id";
    public static final Companion Companion = new Companion(null);
    public static final String TAG = "SceneFragment";
    private String mActivityId;
    private final ScenePageAdapter mAdapter = new ScenePageAdapter();
    private FragmentSceneBinding mBinding;
    private SceneViewModel mViewModel;

    private final void requestData() {
    }

    /* compiled from: SceneFragment.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lcom/xiaopeng/appstore/appstore_ui/fragment/SceneFragment$Companion;", "", "()V", "ACTIVITY_ID", "", "TAG", "appstore_ui_D55V1Release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        ViewDataBinding inflate = DataBindingUtil.inflate(inflater, R.layout.fragment_scene, viewGroup, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(inflater, R.layo…_scene, container, false)");
        FragmentSceneBinding fragmentSceneBinding = (FragmentSceneBinding) inflate;
        this.mBinding = fragmentSceneBinding;
        FragmentSceneBinding fragmentSceneBinding2 = null;
        if (fragmentSceneBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mBinding");
            fragmentSceneBinding = null;
        }
        fragmentSceneBinding.setLifecycleOwner(this);
        FragmentSceneBinding fragmentSceneBinding3 = this.mBinding;
        if (fragmentSceneBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mBinding");
        } else {
            fragmentSceneBinding2 = fragmentSceneBinding3;
        }
        return fragmentSceneBinding2.getRoot();
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ViewModel viewModel = new ViewModelProvider(this).get(SceneViewModel.class);
        Intrinsics.checkNotNullExpressionValue(viewModel, "ViewModelProvider(this).…eneViewModel::class.java)");
        this.mViewModel = (SceneViewModel) viewModel;
        FragmentSceneBinding fragmentSceneBinding = this.mBinding;
        SceneViewModel sceneViewModel = null;
        if (fragmentSceneBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mBinding");
            fragmentSceneBinding = null;
        }
        fragmentSceneBinding.recyclerView.setAdapter(this.mAdapter);
        FragmentSceneBinding fragmentSceneBinding2 = this.mBinding;
        if (fragmentSceneBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mBinding");
            fragmentSceneBinding2 = null;
        }
        fragmentSceneBinding2.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));
        FragmentSceneBinding fragmentSceneBinding3 = this.mBinding;
        if (fragmentSceneBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mBinding");
            fragmentSceneBinding3 = null;
        }
        SceneViewModel sceneViewModel2 = this.mViewModel;
        if (sceneViewModel2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mViewModel");
        } else {
            sceneViewModel = sceneViewModel2;
        }
        fragmentSceneBinding3.setViewModel(sceneViewModel);
        subscribeViewModel();
        Bundle arguments = getArguments();
        if (arguments != null) {
            String string = arguments.getString(ACTIVITY_ID);
            Intrinsics.checkNotNull(string);
            this.mActivityId = string;
            requestData();
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.mRootActivity.setCanNavBack(true);
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        FragmentSceneBinding fragmentSceneBinding = this.mBinding;
        if (fragmentSceneBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mBinding");
            fragmentSceneBinding = null;
        }
        fragmentSceneBinding.recyclerView.setAdapter(null);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        if (ThemeManager.isThemeChanged(newConfig)) {
            PayloadData payloadData = new PayloadData();
            payloadData.type = 1003;
            ScenePageAdapter scenePageAdapter = this.mAdapter;
            scenePageAdapter.notifyItemRangeChanged(0, scenePageAdapter.getItemCount(), payloadData);
        }
    }

    private final void subscribeViewModel() {
        SceneViewModel sceneViewModel = this.mViewModel;
        SceneViewModel sceneViewModel2 = null;
        if (sceneViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mViewModel");
            sceneViewModel = null;
        }
        sceneViewModel.getAdapterDataList().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$SceneFragment$TWIAEGiiC8OGSjbpqqMzSIWYaQw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SceneFragment.subscribeViewModel$lambda$0(SceneFragment.this, (Resource) obj);
            }
        });
        SceneViewModel sceneViewModel3 = this.mViewModel;
        if (sceneViewModel3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mViewModel");
        } else {
            sceneViewModel2 = sceneViewModel3;
        }
        sceneViewModel2.getOnBackEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$SceneFragment$44pfHzvK9PianYgdNqzZK4BncZI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SceneFragment.subscribeViewModel$lambda$1(SceneFragment.this, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void subscribeViewModel$lambda$0(SceneFragment this$0, Resource resource) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentSceneBinding fragmentSceneBinding = null;
        if (resource.status == Resource.Status.SUCCESS) {
            FragmentSceneBinding fragmentSceneBinding2 = this$0.mBinding;
            if (fragmentSceneBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBinding");
                fragmentSceneBinding2 = null;
            }
            fragmentSceneBinding2.layoutError.setVisibility(8);
            FragmentSceneBinding fragmentSceneBinding3 = this$0.mBinding;
            if (fragmentSceneBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBinding");
                fragmentSceneBinding3 = null;
            }
            fragmentSceneBinding3.recyclerView.setVisibility(0);
            FragmentSceneBinding fragmentSceneBinding4 = this$0.mBinding;
            if (fragmentSceneBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBinding");
                fragmentSceneBinding4 = null;
            }
            fragmentSceneBinding4.tvEmptyTips.setVisibility(8);
            Collection collection = (Collection) resource.data;
            if (!(collection == null || collection.isEmpty())) {
                this$0.mAdapter.submitList((List) resource.data);
            } else if (this$0.mAdapter.getItemCount() <= 0) {
                Logger.t(TAG).d("data is empty", new Object[0]);
                FragmentSceneBinding fragmentSceneBinding5 = this$0.mBinding;
                if (fragmentSceneBinding5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mBinding");
                } else {
                    fragmentSceneBinding = fragmentSceneBinding5;
                }
                fragmentSceneBinding.tvEmptyTips.setVisibility(0);
            }
        } else if (resource.status == Resource.Status.LOADING) {
            FragmentSceneBinding fragmentSceneBinding6 = this$0.mBinding;
            if (fragmentSceneBinding6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBinding");
                fragmentSceneBinding6 = null;
            }
            fragmentSceneBinding6.loadingProgress.setVisibility(8);
            FragmentSceneBinding fragmentSceneBinding7 = this$0.mBinding;
            if (fragmentSceneBinding7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBinding");
                fragmentSceneBinding7 = null;
            }
            if (fragmentSceneBinding7.layoutError.isShown()) {
                FragmentSceneBinding fragmentSceneBinding8 = this$0.mBinding;
                if (fragmentSceneBinding8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mBinding");
                } else {
                    fragmentSceneBinding = fragmentSceneBinding8;
                }
                fragmentSceneBinding.loadingProgress.setVisibility(0);
            }
        } else if (resource.status == Resource.Status.ERROR) {
            FragmentSceneBinding fragmentSceneBinding9 = this$0.mBinding;
            if (fragmentSceneBinding9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBinding");
                fragmentSceneBinding9 = null;
            }
            fragmentSceneBinding9.layoutError.setVisibility(0);
            FragmentSceneBinding fragmentSceneBinding10 = this$0.mBinding;
            if (fragmentSceneBinding10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBinding");
                fragmentSceneBinding10 = null;
            }
            fragmentSceneBinding10.recyclerView.setVisibility(8);
            FragmentSceneBinding fragmentSceneBinding11 = this$0.mBinding;
            if (fragmentSceneBinding11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBinding");
            } else {
                fragmentSceneBinding = fragmentSceneBinding11;
            }
            fragmentSceneBinding.loadingProgress.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void subscribeViewModel$lambda$1(SceneFragment this$0, String str) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        NavHostFragment.findNavController(this$0).popBackStack();
    }
}
