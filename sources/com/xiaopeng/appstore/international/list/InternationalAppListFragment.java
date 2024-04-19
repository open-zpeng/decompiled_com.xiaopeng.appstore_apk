package com.xiaopeng.appstore.international.list;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.applist_biz.logic.AppListCache;
import com.xiaopeng.appstore.applist_biz.logic.AppListUtils;
import com.xiaopeng.appstore.applist_biz.model.AdapterChangeModel;
import com.xiaopeng.appstore.applist_biz.model.AppListChangeModel;
import com.xiaopeng.appstore.applist_biz.model.BaseAppItem;
import com.xiaopeng.appstore.applist_biz.model.FixedAppItem;
import com.xiaopeng.appstore.applist_biz.model.LoadingAppItem;
import com.xiaopeng.appstore.applist_biz.model.NapaAppItem;
import com.xiaopeng.appstore.applist_biz.model.NormalAppItem;
import com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper;
import com.xiaopeng.appstore.applist_ui.adapter.AppIconViewHolder;
import com.xiaopeng.appstore.applist_ui.adapter.OnAppInteractCallback;
import com.xiaopeng.appstore.applist_ui.view.IconAnimHelper;
import com.xiaopeng.appstore.applist_ui.viewmodel.AppListViewModel;
import com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer;
import com.xiaopeng.appstore.bizcommon.entities.PackageUserKey;
import com.xiaopeng.appstore.bizcommon.logic.AgreementDialogHelper;
import com.xiaopeng.appstore.bizcommon.logic.AgreementManager;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.Constants;
import com.xiaopeng.appstore.bizcommon.logic.IAgreementViewListener;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LaunchParam;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LaunchResult;
import com.xiaopeng.appstore.bizcommon.router.RouterConstants;
import com.xiaopeng.appstore.bizcommon.router.RouterManager;
import com.xiaopeng.appstore.bizcommon.router.provider.AppStoreProvider;
import com.xiaopeng.appstore.bizcommon.utils.AccountUtils;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.common_ui.BaseBizFragment;
import com.xiaopeng.appstore.common_ui.IdleDetectLayout;
import com.xiaopeng.appstore.common_ui.NotificationListener;
import com.xiaopeng.appstore.common_ui.NotificationObserverManager;
import com.xiaopeng.appstore.common_ui.OpenAppMgr;
import com.xiaopeng.appstore.common_ui.base.PanelDialog;
import com.xiaopeng.appstore.common_ui.common.AndroidLifecycleManager;
import com.xiaopeng.appstore.common_ui.common.EnhancedListAdapter;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
import com.xiaopeng.appstore.common_ui.icon.AppIconView;
import com.xiaopeng.appstore.common_ui.icon.OnLauncherResumeCallback;
import com.xiaopeng.appstore.international.R;
import com.xiaopeng.appstore.international.list.InternationalAppListFragment;
import com.xiaopeng.appstore.libcommon.utils.AnimationUtils;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.IdleViewDetectHelper;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.xpcommon.CarUtils;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XToast;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
/* loaded from: classes.dex */
public class InternationalAppListFragment extends BaseBizFragment implements OnAppInteractCallback, IdleViewDetectHelper.IdleCallback, NotificationObserverManager.Launcher, View.OnTouchListener, IAgreementViewListener, IProtocolClickListener {
    private static final String TAG = "AppFrag";
    private static final String TAG_IDLE_DETECT = "AppFrag_IDLE";
    private static final int TITLE_ANIM_DURATION = 400;
    private static final long UNUSED_TIMEOUT = 10000;
    private InternationalAppListAdapter mAppListAdapter;
    private AppStoreProvider mAppStoreProvider;
    private GestureDetector mClickDetector;
    private View mExitEditTips;
    private IdleDetectLayout mIdleDetectLayout;
    private View mLoadingView;
    private NotificationObserverManager mNotificationObserverManager;
    private OnLauncherResumeCallback mOnLauncherResumeCallback;
    private RecyclerView mRecyclerView;
    private View mRootLayout;
    private AlphaAnimation mShowAnimation;
    private PanelDialog mUninstallDialog;
    private UninstallDialogClickListener mUninstallDialogClickListener;
    protected AppListViewModel mViewModel;
    private View tvUpgrade;
    private final Handler mScrollToHandler = new Handler();
    private final RecyclerView.AdapterDataObserver mAdapterDataObserver = new RecyclerView.AdapterDataObserver() { // from class: com.xiaopeng.appstore.international.list.InternationalAppListFragment.1
        private static final String TAG = "AppListObserver";

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            super.onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            Logger.t(TAG).i("onItemRangeChanged, start=" + positionStart + " count=" + itemCount, new Object[0]);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            Logger.t(TAG).i("onItemRangeChanged start=" + positionStart + " count=" + itemCount + " payload=" + payload, new Object[0]);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            Logger.t(TAG).d("onItemRangeInserted, start=" + positionStart + " count=" + itemCount);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            Logger.t(TAG).d("onItemRangeRemoved, start=" + positionStart + " count=" + itemCount);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            Logger.t(TAG).d("onItemRangeRemoved, form=" + fromPosition + " to=" + toPosition + " count=" + itemCount);
        }
    };
    private long mPageStayStart = 0;
    private final Animation.AnimationListener mShowAnimListener = new Animation.AnimationListener() { // from class: com.xiaopeng.appstore.international.list.InternationalAppListFragment.2
        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            InternationalAppListFragment.this.mExitEditTips.setVisibility(0);
        }
    };
    private boolean mScrolled = false;
    private final AndroidLifecycleManager mAndroidLifecycleManager = new AndroidLifecycleManager();

    @Override // com.xiaopeng.appstore.applist_ui.adapter.OnAppInteractCallback
    public void onIconDragChanged(RecyclerView.ViewHolder viewHolder, int pos) {
    }

    public InternationalAppListFragment() {
        Logger.t(TAG).i("create:" + hashCode() + ", " + this, new Object[0]);
        if (this.mAppStoreProvider == null) {
            this.mAppStoreProvider = (AppStoreProvider) RouterManager.get().getModule(RouterConstants.AppStoreProviderPaths.APP_STORE_MAIN_PROVIDER);
        }
    }

    public void setOnLauncherResumeCallback(OnLauncherResumeCallback callback) {
        OnLauncherResumeCallback onLauncherResumeCallback = this.mOnLauncherResumeCallback;
        if (onLauncherResumeCallback != null) {
            onLauncherResumeCallback.onLauncherResume();
        }
        this.mOnLauncherResumeCallback = callback;
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(this.mAndroidLifecycleManager);
        this.mAndroidLifecycleManager.addObserver(AppListCache.get());
        getLifecycle().addObserver(IconAnimHelper.IconAnimHelperGenerator.get());
        this.mNotificationObserverManager = new NotificationObserverManager(this);
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.BizRootActivity.OnActivityEventListener
    public void onWindowReset() {
        Logger.t(TAG).i("onWindowReset, scroll to top", new Object[0]);
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.scrollToPosition(0);
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.international_app_list_fragment, container, false);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if (view != null) {
            View findViewById = view.findViewById(R.id.root_layout);
            this.mRootLayout = findViewById;
            findViewById.setClickable(true);
            IdleDetectLayout idleDetectLayout = (IdleDetectLayout) view;
            this.mIdleDetectLayout = idleDetectLayout;
            idleDetectLayout.setIdleDelay(UNUSED_TIMEOUT);
            this.mIdleDetectLayout.setIdleListener(this);
            this.mIdleDetectLayout.disableDetect();
            this.mExitEditTips = view.findViewById(R.id.exit_edit_tips);
            this.mLoadingView = view.findViewById(R.id.loading_view);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            this.mRecyclerView = recyclerView;
            recyclerView.getRecycledViewPool().setMaxRecycledViews(100, 50);
            initViewModel();
            AppGroupHelper appGroupHelper = new AppGroupHelper();
            this.mViewModel.init(appGroupHelper);
            InternationalAppListAdapter internationalAppListAdapter = new InternationalAppListAdapter(getContext(), appGroupHelper, new EnhancedListAdapter.AdapterListUpdateCallback2(this.mRecyclerView));
            this.mAppListAdapter = internationalAppListAdapter;
            this.mRecyclerView.setLayoutManager(internationalAppListAdapter.getLayoutManager());
            this.mAppListAdapter.registerAdapterDataObserver(this.mAdapterDataObserver);
            this.mAppListAdapter.setOnAppInteractCallback(this);
            this.mAppListAdapter.setOnProtocolClickListener(this);
            this.mRecyclerView.setAdapter(this.mAppListAdapter);
            subscribeViewModel();
            initAnimation();
            this.tvUpgrade = initUpgradeEntry();
            updateUpgradeEntrance();
        }
    }

    protected void initViewModel() {
        AppListViewModel appListViewModel = (AppListViewModel) new ViewModelProvider(this).get(AppListViewModel.class);
        this.mViewModel = appListViewModel;
        appListViewModel.loadData();
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStart() {
        Logger.t(TAG).i("AppListFragment onStart Start:" + hashCode(), new Object[0]);
        super.onStart();
        updateUpgradeEntrance();
        Logger.t(TAG).i("AppListFragment onStart End", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        Logger.t(TAG).i("AppListFragment onResume Start:" + hashCode(), new Object[0]);
        super.onResume();
        this.mPageStayStart = System.currentTimeMillis();
        setOnLauncherResumeCallback(null);
        tryScrollToLastInstalledItem();
        this.mRootActivity.setCanNavBack(false);
        AgreementManager.get().addAgreementViewListener(this);
        Logger.t(TAG).i("AppListFragment onResume End", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onPause() {
        Logger.t(TAG).i("AppListFragment OnPause Start", new Object[0]);
        super.onPause();
        this.mScrollToHandler.removeCallbacksAndMessages(null);
        this.mScrolled = false;
        EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_PANEL, EventEnum.MAIN_ENTRANCE, 1, Long.valueOf(System.currentTimeMillis() - this.mPageStayStart));
        exitEdit();
        Logger.t(TAG).i("AppListFragment OnPause End", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        Logger.t(TAG).i("AppListFragment onStop Start", new Object[0]);
        super.onStop();
        destroyAgreement();
        AppListViewModel appListViewModel = this.mViewModel;
        if (appListViewModel != null) {
            appListViewModel.onStop();
        }
        Logger.t(TAG).i("AppListFragment onStop End", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        Logger.t(TAG).i("AppListFragment onDestroy Start:" + hashCode(), new Object[0]);
        super.onDestroy();
        AnimationUtils.cancelAnimation(this.mShowAnimation);
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.setAdapter(null);
        }
        getLifecycle().removeObserver(this.mAndroidLifecycleManager);
        this.mAppListAdapter.unregisterAdapterDataObserver(this.mAdapterDataObserver);
        Logger.t(TAG).i("AppListFragment onDestroy End", new Object[0]);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (ThemeManager.isThemeChanged(newConfig)) {
            this.mViewModel.loadAllIconsAsync();
            PanelDialog panelDialog = this.mUninstallDialog;
            if (panelDialog == null || panelDialog.getDialog() == null || this.mUninstallDialog.getDialog().getWindow() == null) {
                return;
            }
            this.mUninstallDialog.getDialog().getWindow().setBackgroundDrawableResource(R.drawable.x_bg_dialog);
        }
    }

    private void destroyAgreement() {
        AgreementManager.get().removeAgreementViewListener(this);
        AgreementDialogHelper.getInstance().dismiss();
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.IAgreementViewListener
    public void onShowAgreementDialog(final Runnable runnable, final boolean async) {
        AgreementDialogHelper.getInstance().show(new Runnable() { // from class: com.xiaopeng.appstore.international.list.-$$Lambda$InternationalAppListFragment$1hv2ikfzPQP7ddcpvvdXV7zu2bk
            @Override // java.lang.Runnable
            public final void run() {
                AgreementManager.get().agreement(runnable, async);
            }
        });
    }

    @Override // com.xiaopeng.appstore.libcommon.utils.IdleViewDetectHelper.IdleCallback
    public void onIdle() {
        Logger.t(TAG_IDLE_DETECT).i("Idle timeout.", new Object[0]);
        exitEdit();
    }

    @Override // com.xiaopeng.appstore.libcommon.utils.IdleViewDetectHelper.IdleCallback
    public void onIdleDetectStart() {
        Logger.t(TAG_IDLE_DETECT).i("Idle detect started.", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.libcommon.utils.IdleViewDetectHelper.IdleCallback
    public void onIdleDetectStop() {
        Logger.t(TAG_IDLE_DETECT).i("Idle detect stopped.", new Object[0]);
    }

    private void initAnimation() {
        if (this.mShowAnimation == null) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            this.mShowAnimation = alphaAnimation;
            alphaAnimation.setDuration(400L);
            this.mShowAnimation.setAnimationListener(this.mShowAnimListener);
        }
    }

    private boolean tryEnterEdit(View pressedView) {
        if (AccountUtils.get().isLogin()) {
            if (this.mAppListAdapter.isEditMode()) {
                return false;
            }
            enterEdit(pressedView);
            return true;
        }
        AccountUtils.get().goLoginByService();
        return true;
    }

    protected void enterEdit(View pressedView) {
        InternationalAppListAdapter internationalAppListAdapter = this.mAppListAdapter;
        if (internationalAppListAdapter != null && !internationalAppListAdapter.isEditMode()) {
            this.mAppListAdapter.setIsEditMode(true);
        }
        View view = this.mExitEditTips;
        if (view != null) {
            view.startAnimation(this.mShowAnimation);
        }
        getRootActivity().setTitleVisible(false);
        if (pressedView != null) {
            tryStartDrag(this.mRecyclerView.getChildViewHolder(pressedView));
        }
        Logger.t(TAG_IDLE_DETECT).i("onAppLongClick, enable idle detect.", new Object[0]);
        this.mIdleDetectLayout.enableDetect();
        updateUpgradeEntrance();
        startSpaceClickDetect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void exitEdit() {
        stopSpaceClickDetect();
        PanelDialog panelDialog = this.mUninstallDialog;
        if (panelDialog != null) {
            panelDialog.dismiss();
        }
        InternationalAppListAdapter internationalAppListAdapter = this.mAppListAdapter;
        if (internationalAppListAdapter != null && internationalAppListAdapter.isEditMode()) {
            Logger.t(TAG_IDLE_DETECT).i("exit edit, stopping idle detect.", new Object[0]);
            this.mAppListAdapter.setIsEditMode(false);
            this.mIdleDetectLayout.disableDetect();
        }
        View view = this.mExitEditTips;
        if (view != null) {
            view.setVisibility(8);
        }
        updateUpgradeEntrance();
        getRootActivity().setTitleVisible(true);
    }

    private void subscribeViewModel() {
        Logger.t(TAG).i("subscribeViewModel", new Object[0]);
        this.mViewModel.getAdapterDataListLiveData().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.international.list.-$$Lambda$InternationalAppListFragment$4CfaViD9AkbfjlI9_bEDREiqkZg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                InternationalAppListFragment.this.lambda$subscribeViewModel$1$InternationalAppListFragment((List) obj);
            }
        });
        this.mViewModel.getAppListChangeEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.international.list.-$$Lambda$InternationalAppListFragment$NYC0JBK7hDPwhIFaNcOZuA-opnk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                InternationalAppListFragment.this.lambda$subscribeViewModel$2$InternationalAppListFragment((AppListChangeModel) obj);
            }
        });
        this.mViewModel.getAppItemChanged().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.international.list.-$$Lambda$InternationalAppListFragment$xEv8Yfk-Nmx0R1PEfk_qtLHfWhw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                InternationalAppListFragment.this.lambda$subscribeViewModel$3$InternationalAppListFragment((AdapterChangeModel) obj);
            }
        });
        this.mViewModel.getAppItemIconChangeEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.international.list.-$$Lambda$InternationalAppListFragment$HQmQw3QOh-V4w4WhbtcCsNTo1hw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                InternationalAppListFragment.this.lambda$subscribeViewModel$4$InternationalAppListFragment((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$subscribeViewModel$1$InternationalAppListFragment(List list) {
        Logger.t(TAG).i("submitList, data=" + list, new Object[0]);
        this.mAppListAdapter.submitList(list);
        View view = this.mLoadingView;
        if (view != null) {
            view.setVisibility(8);
        }
    }

    public /* synthetic */ void lambda$subscribeViewModel$2$InternationalAppListFragment(AppListChangeModel appListChangeModel) {
        int i = appListChangeModel.type;
        List<Integer> list = appListChangeModel.data;
        if (i == 1) {
            for (Integer num : list) {
                Logger.t("AppFrag_notify").i("notifyItemInserted index=" + num, new Object[0]);
                this.mAppListAdapter.notifyItemInserted(num.intValue());
            }
        } else if (i == 2) {
            for (Integer num2 : list) {
                Logger.t("AppFrag_notify").i("notifyItemRemoved index=" + num2, new Object[0]);
                this.mAppListAdapter.notifyItemRemoved(num2.intValue());
            }
        } else if (i == 3) {
            for (Integer num3 : list) {
                Logger.t("AppFrag_notify").i("notifyItemChanged index=" + num3, new Object[0]);
                this.mAppListAdapter.notifyItemChanged(num3.intValue());
            }
        }
    }

    public /* synthetic */ void lambda$subscribeViewModel$3$InternationalAppListFragment(AdapterChangeModel adapterChangeModel) {
        Logger.t("AppFrag_notify").i("notifyItemChanged index=" + adapterChangeModel.getIndex(), new Object[0]);
        this.mAppListAdapter.notifyItemChanged(adapterChangeModel.getIndex());
    }

    public /* synthetic */ void lambda$subscribeViewModel$4$InternationalAppListFragment(Boolean bool) {
        if (bool == null || !bool.booleanValue()) {
            return;
        }
        PayloadData payloadData = new PayloadData();
        payloadData.type = 4;
        Logger.t("AppFrag_notify").i("notifyItemRangeChanged iconChanged", new Object[0]);
        InternationalAppListAdapter internationalAppListAdapter = this.mAppListAdapter;
        internationalAppListAdapter.notifyItemRangeChanged(0, internationalAppListAdapter.getItemCount(), payloadData);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void refreshUnreadCount(Set<PackageUserKey> updatedBadges) {
        if (this.mAppListAdapter != null) {
            PayloadData payloadData = new PayloadData();
            payloadData.type = 5;
            payloadData.data = updatedBadges;
            Logger.t("AppFrag_notify").i("notifyItemRangeChanged refreshUnreadCount" + updatedBadges, new Object[0]);
            InternationalAppListAdapter internationalAppListAdapter = this.mAppListAdapter;
            internationalAppListAdapter.notifyItemRangeChanged(0, internationalAppListAdapter.getItemCount(), payloadData);
        }
    }

    private void registerNotificationObserver() {
        NotificationListener.setNotificationsChangedListener(this.mNotificationObserverManager);
        this.mNotificationObserverManager.startObserve();
    }

    private void unregisterNotification() {
        NotificationListener.removeNotificationsChangedListener();
        this.mNotificationObserverManager.stopObserve();
    }

    private void tryScrollToLastInstalledItem() {
        if (this.mScrolled) {
            Logger.t(TAG).i("tryScrollToLastInstalledItem, already scrolled", new Object[0]);
            return;
        }
        String lastInstalledApp = LogicUtils.getLastInstalledApp();
        if (TextUtils.isEmpty(lastInstalledApp)) {
            return;
        }
        InternationalAppListAdapter internationalAppListAdapter = this.mAppListAdapter;
        if (internationalAppListAdapter == null) {
            Logger.t(TAG).i("tryScrollToLastInstalledItem, adapter not init", new Object[0]);
            return;
        }
        final int index = internationalAppListAdapter.getIndex(lastInstalledApp);
        Logger.t(TAG).i("tryScrollToLastInstalledItem, index=" + index, new Object[0]);
        if (index <= -1 || index >= this.mAppListAdapter.getItemCount()) {
            return;
        }
        this.mScrollToHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.appstore.international.list.-$$Lambda$InternationalAppListFragment$-s-tSR8rKpk5_vLJE_1X2NGQ5Dw
            @Override // java.lang.Runnable
            public final void run() {
                InternationalAppListFragment.this.lambda$tryScrollToLastInstalledItem$5$InternationalAppListFragment(index);
            }
        }, 300L);
        LogicUtils.setLastInstalledApp("");
    }

    public /* synthetic */ void lambda$tryScrollToLastInstalledItem$5$InternationalAppListFragment(int i) {
        Logger.t(TAG).i("smoothScrollToPosition, index=" + i, new Object[0]);
        this.mRecyclerView.smoothScrollToPosition(i);
        this.mScrolled = true;
    }

    private void startSpaceClickDetect() {
        if (this.mClickDetector == null) {
            this.mClickDetector = new GestureDetector(requireContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.xiaopeng.appstore.international.list.InternationalAppListFragment.3
                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public boolean onSingleTapUp(MotionEvent e) {
                    InternationalAppListFragment.this.exitEdit();
                    return true;
                }
            });
        }
        this.mRecyclerView.setOnTouchListener(this);
        this.mRootLayout.setOnTouchListener(this);
    }

    private void stopSpaceClickDetect() {
        this.mRecyclerView.setOnTouchListener(null);
        this.mRootLayout.setOnTouchListener(null);
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.OnAppInteractCallback
    public boolean onAppIconClick(AppIconViewHolder viewHolder, BaseAppItem appItem) {
        if (!this.mAppListAdapter.isEditMode() && !viewHolder.isDeleting()) {
            if (appItem instanceof NormalAppItem) {
                Logger.t(TAG).i("AppIconClick, launch app=>" + appItem.getKey(), new Object[0]);
                NormalAppItem normalAppItem = (NormalAppItem) appItem;
                startActivity(viewHolder.getIconView(), normalAppItem);
                if (Arrays.asList(CarUtils.isEURegion() ? Constants.XP_INTER_APP_LIST : Constants.XP_APP_LIST).contains(normalAppItem.packageName)) {
                    EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_LIST, EventEnum.OPEN_XP_APP_ENTRANCE, normalAppItem.packageName, normalAppItem.title);
                } else {
                    EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_LIST, EventEnum.OPEN_APP_FROM_APP_LIST, 3, normalAppItem.packageName, normalAppItem.title);
                }
                AppListUtils.removeNewAppFlag(normalAppItem.packageName);
                viewHolder.refreshNameIndicator(appItem);
                viewHolder.setUnreadCount(0);
            } else if (appItem instanceof FixedAppItem) {
                AssembleDataContainer findWithPn = AppStoreAssembleManager.get().findWithPn(appItem.packageName);
                if (findWithPn != null && findWithPn.getState() != 2) {
                    Logger.t(TAG).i("AppIconClick, app is assembling, ignore clicking. download app=>" + appItem.packageName + " state=" + findWithPn.getState(), new Object[0]);
                    return false;
                }
                navigateToDetail(appItem.packageName);
                return true;
            } else if (appItem instanceof NapaAppItem) {
                ((NapaAppItem) appItem).startActivity();
                EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_LIST, EventEnum.OPEN_XP_APP_ENTRANCE, appItem.packageName, appItem.title);
            } else {
                Logger.t(TAG).i("AppIconClick ignored, app=" + appItem, new Object[0]);
            }
        } else {
            Logger.t(TAG).i("AppIconClick ignored, isEdit=" + this.mAppListAdapter.isEditMode() + " isDel=" + viewHolder.isDeleting() + " app=" + appItem, new Object[0]);
        }
        return false;
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.OnAppInteractCallback
    public boolean onAppIconLongClick(View v, BaseAppItem info) {
        return tryEnterEdit(v);
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.OnAppInteractCallback
    public void onAppDeleteClick(BaseAppItem appItem) {
        if (this.mUninstallDialog == null && getContext() != null) {
            this.mUninstallDialog = new PanelDialog(getContext());
            this.mUninstallDialogClickListener = new UninstallDialogClickListener();
            this.mUninstallDialog.setPositiveButton(ResUtils.getString(R.string.dialog_positive_btn), this.mUninstallDialogClickListener).setNegativeButton(ResUtils.getString(R.string.dialog_negative_btn), this.mUninstallDialogClickListener);
        }
        PanelDialog panelDialog = this.mUninstallDialog;
        if (panelDialog != null) {
            panelDialog.setMessage(String.format(ResUtils.getString(R.string.dialog_uninstall_tips), appItem.title));
            this.mUninstallDialogClickListener.setAppItem(appItem);
            this.mUninstallDialog.show();
        }
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.OnAppInteractCallback
    public void onIconDrag(RecyclerView.ViewHolder viewHolder) {
        tryStartDrag(viewHolder);
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.OnAppInteractCallback
    public void onIconMove(RecyclerView.ViewHolder viewHolder, int fromPos, int toPos) {
        if (fromPos == -1 || toPos == -1 || fromPos == toPos) {
            return;
        }
        this.mViewModel.moveData(fromPos, toPos);
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.OnAppInteractCallback
    public void onIconDrop(RecyclerView.ViewHolder viewHolder, int fromPos, int toPos) {
        if (fromPos == -1 || toPos == -1 || fromPos == toPos) {
            return;
        }
        this.mViewModel.persistOrderAsync();
    }

    private boolean tryStartDrag(RecyclerView.ViewHolder viewHolder) {
        return this.mAppListAdapter.tryStartDrag(viewHolder);
    }

    private void startActivity(AppIconView iconView, NormalAppItem info) {
        iconView.setStayPressed(true);
        if (getContext() != null) {
            LaunchParam launchParam = new LaunchParam(info.intent, info.user, (String) info.title);
            LaunchResult launch = OpenAppMgr.get().launch(getContext(), launchParam);
            Logger.t(TAG).i("lunch app:" + launchParam + ", result:" + launch, new Object[0]);
            if (launch.isSuccess()) {
                if (Constants.CLOSE_PANEL_APP_LIST.contains(info.packageName)) {
                    getRootActivity().closeWindow();
                }
                setOnLauncherResumeCallback(iconView);
                return;
            }
        }
        iconView.setStayPressed(false);
    }

    @Override // com.xiaopeng.appstore.common_ui.NotificationObserverManager.Launcher
    public void updateIconBadges(Set<PackageUserKey> updatedBadges) {
        refreshUnreadCount(updatedBadges);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v, MotionEvent event) {
        View findChildViewUnder;
        if ((v instanceof RecyclerView) && (findChildViewUnder = this.mRecyclerView.findChildViewUnder(event.getX(), event.getY())) != null) {
            RecyclerView.ViewHolder childViewHolder = this.mRecyclerView.getChildViewHolder(findChildViewUnder);
            if (childViewHolder instanceof AppIconViewHolder) {
                return false;
            }
            Logger.t(TAG).i("touch ViewHolder not icon:" + childViewHolder, new Object[0]);
        }
        GestureDetector gestureDetector = this.mClickDetector;
        if (gestureDetector != null) {
            return gestureDetector.onTouchEvent(event);
        }
        return false;
    }

    @Override // com.xiaopeng.appstore.international.list.IProtocolClickListener
    public void onUserProtocolClicked(View view) {
        navigateToProtocol();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class UninstallDialogClickListener implements XDialogInterface.OnClickListener {
        private BaseAppItem mAppItem;

        private UninstallDialogClickListener() {
        }

        public void setAppItem(BaseAppItem appItem) {
            this.mAppItem = appItem;
        }

        @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
        public void onClick(XDialog xDialog, int i) {
            BaseAppItem baseAppItem;
            if (i != -1 || (baseAppItem = this.mAppItem) == null) {
                return;
            }
            if (baseAppItem instanceof NormalAppItem) {
                NormalAppItem normalAppItem = (NormalAppItem) baseAppItem;
                delete(normalAppItem.packageName, normalAppItem.user, normalAppItem.title.toString());
            } else if (baseAppItem instanceof LoadingAppItem) {
                LoadingAppItem loadingAppItem = (LoadingAppItem) baseAppItem;
                delete(loadingAppItem.packageName, null, loadingAppItem.title.toString());
            }
        }

        private void delete(String packageName, UserHandle userHandle, String name) {
            AppStoreAssembleManager.get().delete(packageName, userHandle, name, new Consumer() { // from class: com.xiaopeng.appstore.international.list.-$$Lambda$InternationalAppListFragment$UninstallDialogClickListener$GTpnnmdty-iTAPCXWqY7aEJcCzY
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    InternationalAppListFragment.UninstallDialogClickListener.this.toastError((String) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void toastError(final String msg) {
            AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.international.list.-$$Lambda$InternationalAppListFragment$UninstallDialogClickListener$Va9F9H0QUHmhPCa3TzHL8enyNCE
                @Override // java.lang.Runnable
                public final void run() {
                    XToast.show(msg);
                }
            });
        }
    }

    private boolean canNav() {
        NavController findNavController = NavHostFragment.findNavController(this);
        return findNavController.getCurrentDestination() == null || findNavController.getCurrentDestination().getId() == R.id.appListFragment;
    }

    private void updateUpgradeEntrance() {
        if (this.mAppListAdapter.isEditMode()) {
            this.tvUpgrade.setVisibility(8);
            return;
        }
        Integer value = this.mAppStoreProvider.getPendingUpgradeCount().getValue();
        if (value != null && value.intValue() > 0) {
            showWithFadeIn(this.tvUpgrade);
        } else {
            hideWithFadeOut(this.tvUpgrade);
        }
    }

    private View initUpgradeEntry() {
        final View findViewById = getView().findViewById(R.id.upgradeEntry);
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.international.list.-$$Lambda$InternationalAppListFragment$fVq800oP18SuTBkd_V2Vg081n5Q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InternationalAppListFragment.this.lambda$initUpgradeEntry$6$InternationalAppListFragment(view);
            }
        });
        this.mAppStoreProvider.getPendingUpgradeCount().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.international.list.-$$Lambda$InternationalAppListFragment$o7jnJ8U4Z8BCxGmUvUfy-TquMVo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                InternationalAppListFragment.this.lambda$initUpgradeEntry$7$InternationalAppListFragment(findViewById, (Integer) obj);
            }
        });
        return findViewById;
    }

    public /* synthetic */ void lambda$initUpgradeEntry$6$InternationalAppListFragment(View view) {
        navigateToUpgradePage();
    }

    public /* synthetic */ void lambda$initUpgradeEntry$7$InternationalAppListFragment(View view, Integer num) {
        if (num.intValue() > 0) {
            showWithFadeIn(view);
        } else {
            hideWithFadeOut(view);
        }
    }

    private void showWithFadeIn(View view) {
        if (view.getVisibility() == 0) {
            return;
        }
        view.setVisibility(0);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(400L);
        view.startAnimation(alphaAnimation);
    }

    private void hideWithFadeOut(final View view) {
        if (view.getVisibility() == 4 || view.getVisibility() == 8) {
            return;
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(400L);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.xiaopeng.appstore.international.list.InternationalAppListFragment.4
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                if (InternationalAppListFragment.this.getActivity() != null) {
                    view.setVisibility(4);
                }
            }
        });
        view.startAnimation(alphaAnimation);
    }

    private void navigateToUpgradePage() {
        if (canNav()) {
            NavHostFragment.findNavController(this).navigate(R.id.action_appListFragment_to_pendingUpdateFragment);
        }
    }

    private void navigateToDetail(String packageName) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("detail_page_app_package_name", packageName);
        NavHostFragment.findNavController(this).navigate(R.id.action_appListFragment_to_itemDetailFragment, bundle);
    }

    private void navigateToProtocol() {
        AgreementDialogHelper.getInstance().show(null, true);
    }
}
