package com.xiaopeng.appstore.appstore_ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.logic.AppStoreLogicUtils;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.StoreHomeAdapter2;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder;
import com.xiaopeng.appstore.appstore_ui.common.NavUtils;
import com.xiaopeng.appstore.appstore_ui.viewmodel.StoreHomeViewModel;
import com.xiaopeng.appstore.bizcommon.logic.AgreementDialogHelper;
import com.xiaopeng.appstore.bizcommon.logic.AgreementManager;
import com.xiaopeng.appstore.bizcommon.logic.IAgreementViewListener;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.common_ui.BaseTabFragment;
import com.xiaopeng.appstore.common_ui.common.Resource;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
import com.xiaopeng.appstore.libcommon.utils.NetworkUtils;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.app.XToast;
import java.lang.ref.WeakReference;
import java.util.List;
/* loaded from: classes2.dex */
public class StoreHomeFragment extends BaseTabFragment implements BannerViewHolder.OnBannerItemListener, IAgreementViewListener {
    public static final String ARGS_KEY_REFRESH_DATA = "args_key_refresh_data";
    private static final int FADE_ANIM_TAG = R.string.store_home_anim_view_tag;
    private static final long FADE_IN_DURATION = 300;
    private static final long FADE_OUT_DURATION = 100;
    private static final int STORE_INDEX_IN_TAB = 2;
    private static final String TAG = "StoreHomeFragment";
    private static AccelerateInterpolator sAccelerateInterpolator;
    private static DecelerateInterpolator sDecelerateInterpolator;
    private Button mBtnRetry;
    private View mErrorLayout;
    private View mLoadingLayout;
    private RecyclerView mRecyclerView;
    private ViewGroup mRecyclerViewParent;
    private Button mReturnLastViewBtn;
    private StoreHomeAdapter2 mStoreHomeAdapter;
    private StoreHomeViewModel mViewModel;
    private long mPageStayStart = 0;
    private boolean mRefreshed = false;

    @Override // com.xiaopeng.appstore.common_ui.BaseTabFragment
    protected ViewStub getViewStub(View view) {
        return null;
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseTabFragment
    protected void initViewStubContent(View inflatedViewStub) {
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder.OnBannerItemListener
    public void onBannerChanged(int position, AdvModel bannerData) {
    }

    private static AccelerateInterpolator getAccelerateInterpolator() {
        if (sAccelerateInterpolator == null) {
            sAccelerateInterpolator = new AccelerateInterpolator();
        }
        return sAccelerateInterpolator;
    }

    private static DecelerateInterpolator getDecelerateInterpolator() {
        if (sDecelerateInterpolator == null) {
            sDecelerateInterpolator = new DecelerateInterpolator();
        }
        return sDecelerateInterpolator;
    }

    public void reset() {
        Button button = this.mReturnLastViewBtn;
        if (button != null) {
            button.setVisibility(8);
        }
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.setVisibility(8);
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseTabFragment
    protected int getLayoutViewStub() {
        return R.layout.fragment_store_home;
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseTabFragment
    protected void initOtherView(View view) {
        this.mReturnLastViewBtn = (Button) view.findViewById(R.id.btn_last_view);
        this.mRecyclerViewParent = (ViewGroup) view.findViewById(R.id.recycler_view_parent);
        this.mLoadingLayout = view.findViewById(R.id.loading_layout);
        this.mErrorLayout = view.findViewById(R.id.error_layout);
        Button button = (Button) view.findViewById(R.id.btn_retry);
        this.mBtnRetry = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$StoreHomeFragment$BzDeOUfCRCya4cAyyoq1X665p3g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StoreHomeFragment.this.lambda$initOtherView$0$StoreHomeFragment(view2);
            }
        });
        RecyclerView recyclerView = StoreHomeViewCache.get().getRecyclerView();
        if (recyclerView != null) {
            Logger.t(TAG).d("use cache rv:" + recyclerView);
            clearCacheRvParent();
            this.mRecyclerViewParent.addView(recyclerView);
        }
    }

    public /* synthetic */ void lambda$initOtherView$0$StoreHomeFragment(View view) {
        if (NetworkUtils.isConnected()) {
            StoreHomeViewModel storeHomeViewModel = this.mViewModel;
            if (storeHomeViewModel != null) {
                storeHomeViewModel.requestData();
                return;
            }
            return;
        }
        XToast.show(ResUtils.getString(R.string.net_not_connect));
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseTabFragment
    protected boolean isCurrentTab(int index, Class<?> selectedFragmentClz) {
        return selectedFragmentClz == StoreRootFragment.class;
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mViewModel = (StoreHomeViewModel) new ViewModelProvider(this).get(StoreHomeViewModel.class);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subscribeViewModel();
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Logger.t(TAG).i("StoreHomeFragment onStart " + hashCode(), new Object[0]);
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseTabFragment, com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        Logger.t(TAG).i("StoreHomeFragment onResume Start " + hashCode(), new Object[0]);
        super.onResume();
        StoreHomeAdapter2 storeHomeAdapter2 = this.mStoreHomeAdapter;
        if (storeHomeAdapter2 != null) {
            storeHomeAdapter2.startBannerTimer();
        }
        this.mPageStayStart = System.currentTimeMillis();
        AgreementManager.get().addAgreementViewListener(this);
        this.mRootActivity.setCanNavBack(false);
        LogicUtils.setLastInstalledApp("");
        Logger.t(TAG).i("StoreHomeFragment onResume End", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.BaseTabFragment
    public boolean inflateViewStub() {
        boolean inflateViewStub = super.inflateViewStub();
        tryInitRecyclerView();
        return inflateViewStub;
    }

    private void tryRefresh() {
        if (this.mRefreshed) {
            return;
        }
        this.mRefreshed = true;
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView == null || recyclerView.getChildCount() == 0) {
            showView(this.mLoadingLayout);
        } else {
            hideView(this.mLoadingLayout);
        }
        this.mViewModel.requestData();
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onPause() {
        Logger.t(TAG).i("StoreHomeFragment onPause Start " + hashCode(), new Object[0]);
        super.onPause();
        EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_PANEL, EventEnum.MAIN_ENTRANCE, 2, Long.valueOf(System.currentTimeMillis() - this.mPageStayStart));
        Button button = this.mReturnLastViewBtn;
        if (button != null) {
            button.setVisibility(4);
        }
        StoreHomeAdapter2 storeHomeAdapter2 = this.mStoreHomeAdapter;
        if (storeHomeAdapter2 != null) {
            storeHomeAdapter2.stopBannerTimer();
        }
        Logger.t(TAG).i("StoreHomeFragment onPause End", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        AgreementManager.get().removeAgreementViewListener(this);
        AgreementDialogHelper.getInstance().resetAgreeContinue();
        AgreementDialogHelper.getInstance().dismiss();
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        Logger.t(TAG).i("StoreHomeFragment onDestroy Start " + hashCode(), new Object[0]);
        StoreHomeViewCache.get().release();
        StoreHomeAdapter2 storeHomeAdapter2 = this.mStoreHomeAdapter;
        if (storeHomeAdapter2 != null) {
            storeHomeAdapter2.setOnBannerItemListener(null);
        }
        ViewGroup viewGroup = this.mRecyclerViewParent;
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        super.onDestroy();
        Logger.t(TAG).i("StoreHomeFragment onDestroy End", new Object[0]);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (!ThemeManager.isThemeChanged(newConfig) || this.mStoreHomeAdapter == null) {
            return;
        }
        PayloadData payloadData = new PayloadData();
        payloadData.type = 1003;
        if (this.mRecyclerView.isComputingLayout()) {
            return;
        }
        StoreHomeAdapter2 storeHomeAdapter2 = this.mStoreHomeAdapter;
        storeHomeAdapter2.notifyItemRangeChanged(0, storeHomeAdapter2.getItemCount(), payloadData);
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity.OnActivityEventListener
    public void onTabInitRequest() {
        super.onTabInitRequest();
        Logger.t(TAG).i("onTabInitRequest:" + hashCode(), new Object[0]);
        tryInitRecyclerView();
    }

    private void tryInitRecyclerView() {
        if (this.mRecyclerView == null) {
            if (!StoreHomeViewCache.get().hasCache()) {
                showView(this.mLoadingLayout);
                StoreHomeViewCache.get().tryInitViewCache();
            }
            if (StoreHomeViewCache.get().hasCache()) {
                StoreHomeViewCache.get().restore();
                this.mRecyclerView = StoreHomeViewCache.get().getRecyclerView();
                this.mStoreHomeAdapter = StoreHomeViewCache.get().getStoreHomeAdapter();
                if (this.mRecyclerViewParent.getChildCount() == 0) {
                    clearCacheRvParent();
                    this.mRecyclerViewParent.addView(this.mRecyclerView);
                }
            }
            this.mErrorLayout.setVisibility(8);
            this.mRecyclerView.setVisibility(0);
        }
    }

    private void clearCacheRvParent() {
        ViewParent parent = StoreHomeViewCache.get().getRecyclerView().getParent();
        if (parent instanceof ViewGroup) {
            Logger.t(TAG).d("clearCacheRvParent, cache rv has parent:" + parent);
            ((ViewGroup) parent).removeAllViews();
        }
    }

    private void subscribeViewModel() {
        this.mViewModel.getAdapterDataList().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$StoreHomeFragment$FHEhIH3HymQL-4YA4XhakmRl7X4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                StoreHomeFragment.this.lambda$subscribeViewModel$3$StoreHomeFragment((Resource) obj);
            }
        });
    }

    public /* synthetic */ void lambda$subscribeViewModel$3$StoreHomeFragment(Resource resource) {
        if (resource.status == Resource.Status.SUCCESS) {
            List list = (List) resource.data;
            if (list != null) {
                Logger.t(TAG).d("adapterDataList request success, submitList." + hashCode());
                StoreHomeAdapter2 storeHomeAdapter2 = this.mStoreHomeAdapter;
                if (storeHomeAdapter2 != null) {
                    storeHomeAdapter2.setOnBannerItemListener(this);
                    this.mStoreHomeAdapter.submitList(list, new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$StoreHomeFragment$ptMWhJVdkZfliV7j4DMzTLCxnSc
                        @Override // java.lang.Runnable
                        public final void run() {
                            StoreHomeFragment.this.lambda$subscribeViewModel$2$StoreHomeFragment();
                        }
                    });
                    return;
                }
                return;
            }
            Logger.t(TAG).d("adapterDataList request success, but is null.");
            performError(ResUtils.getString(R.string.net_error));
        } else if (resource.status == Resource.Status.LOADING) {
            Logger.t(TAG).d("adapterDataList request loading");
            fadeOut(this.mErrorLayout);
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null || recyclerView.getChildCount() != 0) {
                return;
            }
            fadeIn(this.mLoadingLayout);
            RecyclerView recyclerView2 = this.mRecyclerView;
            if (recyclerView2 != null) {
                fadeOut(recyclerView2);
            }
        } else {
            String str = resource.message;
            Logger.t(TAG).d("adapterDataList request error(%s).", str);
            performError(ResUtils.getString(R.string.net_error) + " " + str);
        }
    }

    public /* synthetic */ void lambda$subscribeViewModel$2$StoreHomeFragment() {
        fadeOut(this.mErrorLayout);
        this.mLoadingLayout.postDelayed(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$StoreHomeFragment$fm-wLaQIvuupVIG3yetMJipliCg
            @Override // java.lang.Runnable
            public final void run() {
                StoreHomeFragment.this.lambda$subscribeViewModel$1$StoreHomeFragment();
            }
        }, 20L);
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            showView(recyclerView);
        }
        restoreLastViewEntry();
    }

    public /* synthetic */ void lambda$subscribeViewModel$1$StoreHomeFragment() {
        fadeOut(this.mLoadingLayout);
    }

    private void performError(String msg) {
        Logger.t(TAG).d("performError msg=%s.", msg);
        fadeOut(this.mLoadingLayout);
        StoreHomeAdapter2 storeHomeAdapter2 = this.mStoreHomeAdapter;
        if (storeHomeAdapter2 == null || storeHomeAdapter2.getItemCount() <= 0) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                fadeOut(recyclerView);
            }
            fadeIn(this.mErrorLayout);
            return;
        }
        RecyclerView recyclerView2 = this.mRecyclerView;
        if (recyclerView2 != null) {
            fadeIn(recyclerView2);
        }
        fadeOut(this.mErrorLayout);
    }

    private void fadeIn(View view) {
        if (view.getVisibility() == 0) {
            return;
        }
        view.animate().cancel();
        view.animate().alpha(1.0f).setDuration(FADE_IN_DURATION).setInterpolator(getDecelerateInterpolator()).setListener(getFadeAnimatorListener(view, true)).start();
    }

    private void fadeOut(View view) {
        if (view.getVisibility() == 8) {
            return;
        }
        view.animate().cancel();
        view.animate().alpha(0.0f).setDuration(FADE_OUT_DURATION).setInterpolator(getAccelerateInterpolator()).setListener(getFadeAnimatorListener(view, false)).start();
    }

    private FadeAnimatorListener getFadeAnimatorListener(View view, boolean fadeIn) {
        FadeAnimatorListener fadeAnimatorListener;
        int i = FADE_ANIM_TAG;
        Object tag = view.getTag(i);
        if (tag instanceof FadeAnimatorListener) {
            fadeAnimatorListener = (FadeAnimatorListener) tag;
        } else {
            fadeAnimatorListener = new FadeAnimatorListener();
            view.setTag(i, fadeAnimatorListener);
        }
        fadeAnimatorListener.setView(view, fadeIn);
        return fadeAnimatorListener;
    }

    private void showView(View view) {
        view.animate().cancel();
        view.setAlpha(1.0f);
        view.setVisibility(0);
    }

    private void hideView(View view) {
        view.animate().cancel();
        view.setVisibility(8);
    }

    private void navigateToActivityPage(String id) {
        NavController findNavController = NavHostFragment.findNavController(this);
        if (findNavController.getCurrentDestination() == null || findNavController.getCurrentDestination().getId() == R.id.storeHomeFragment) {
            Bundle bundle = new Bundle();
            bundle.putString(SceneFragment.ACTIVITY_ID, id);
            findNavController.navigate(R.id.action_storeHomeFragment_to_activityFragment, bundle);
        }
    }

    private void navigateToUpgradePage() {
        if (canNav()) {
            NavHostFragment.findNavController(this).navigate(R.id.action_storeHomeFragment_to_pendingUpdateFragment);
        }
    }

    private boolean canNav() {
        NavController findNavController = NavHostFragment.findNavController(this);
        return findNavController.getCurrentDestination() == null || findNavController.getCurrentDestination().getId() == R.id.storeHomeFragment;
    }

    private void restoreLastViewEntry() {
        final String lastViewPackage = AppStoreLogicUtils.getLastViewPackage();
        Logger.t(TAG).d("restoreLastViewEntry pn=" + lastViewPackage);
        if (TextUtils.isEmpty(lastViewPackage)) {
            return;
        }
        fadeIn(this.mReturnLastViewBtn);
        this.mReturnLastViewBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$StoreHomeFragment$-ftrspkACMJVnJD8zSSec2O2Jwo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StoreHomeFragment.this.lambda$restoreLastViewEntry$4$StoreHomeFragment(lastViewPackage, view);
            }
        });
    }

    public /* synthetic */ void lambda$restoreLastViewEntry$4$StoreHomeFragment(String str, View view) {
        this.mReturnLastViewBtn.setVisibility(8);
        NavUtils.goToDetail(NavHostFragment.findNavController(this), str);
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder.OnBannerItemListener
    public void onUpdateLayoutClick() {
        EventTrackingHelper.sendMolecast(PagesEnum.STORE_STORE_MAIN, EventEnum.STORE_APP_PENDING_UPGRADE, new Object[0]);
        navigateToUpgradePage();
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder.OnBannerItemListener
    public void onSceneBannerClick(View v, String id) {
        navigateToActivityPage(id);
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder.OnBannerItemListener
    public void onAppDetailBannerClick(View v, String packageName) {
        NavController findNavController = NavHostFragment.findNavController(this);
        if (findNavController.getCurrentDestination() == null || findNavController.getCurrentDestination().getId() == R.id.storeHomeFragment) {
            NavUtils.goToDetail(NavHostFragment.findNavController(this), packageName);
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseTabFragment, com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.BizRootActivity.OnActivityEventListener
    public void onTabSelected(int index, Class<?> clz) {
        super.onTabSelected(index, clz);
        if (isCurrentTab(index, clz)) {
            tryRefresh();
            StoreHomeAdapter2 storeHomeAdapter2 = this.mStoreHomeAdapter;
            if (storeHomeAdapter2 == null || storeHomeAdapter2.getItemCount() == 0) {
                return;
            }
            restoreLastViewEntry();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.IAgreementViewListener
    public void onShowAgreementDialog(final Runnable runnable, final boolean async) {
        AgreementDialogHelper.getInstance().show(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$StoreHomeFragment$K4ri255J1H5yq4LQXUSWtlVP8hA
            @Override // java.lang.Runnable
            public final void run() {
                AgreementManager.get().agreement(runnable, async);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class FadeAnimatorListener extends AnimatorListenerAdapter {
        private boolean mCanceled;
        private boolean mFadeIn;
        private float mSavedAlpha;
        private int mSavedVisibility;
        private WeakReference<View> mViewRef;

        private FadeAnimatorListener() {
            this.mCanceled = false;
            this.mSavedAlpha = -1.0f;
            this.mSavedVisibility = 4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setView(View view, boolean fadeIn) {
            this.mViewRef = new WeakReference<>(view);
            this.mFadeIn = fadeIn;
            this.mSavedAlpha = view.getAlpha();
            this.mSavedVisibility = view.getVisibility();
            if (this.mFadeIn) {
                view.setAlpha(0.0f);
            } else {
                view.setAlpha(1.0f);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animation) {
            this.mCanceled = false;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animation) {
            View view;
            this.mCanceled = true;
            WeakReference<View> weakReference = this.mViewRef;
            if (weakReference == null || (view = weakReference.get()) == null) {
                return;
            }
            float f = this.mSavedAlpha;
            if (f > 0.0f) {
                view.setAlpha(f);
            }
            int i = this.mSavedVisibility;
            if (i != 4) {
                view.setVisibility(i);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            WeakReference<View> weakReference;
            View view;
            if (this.mCanceled || (weakReference = this.mViewRef) == null || (view = weakReference.get()) == null) {
                return;
            }
            if (this.mFadeIn) {
                view.setAlpha(1.0f);
                view.setVisibility(0);
                return;
            }
            view.setAlpha(0.0f);
            view.setVisibility(8);
        }
    }
}
