package com.xiaopeng.appstore.applist_ui.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.applist_biz.logic.AppListCache;
import com.xiaopeng.appstore.applist_biz.model.AdapterChangeModel;
import com.xiaopeng.appstore.applist_biz.model.AppListChangeModel;
import com.xiaopeng.appstore.applist_biz.model.NormalAppItem;
import com.xiaopeng.appstore.applist_ui.R;
import com.xiaopeng.appstore.applist_ui.adapter.AppIconViewHolder;
import com.xiaopeng.appstore.applist_ui.adapter.AppListAdapter;
import com.xiaopeng.appstore.applist_ui.view.AppListRootView;
import com.xiaopeng.appstore.applist_ui.view.IconAnimHelper;
import com.xiaopeng.appstore.applist_ui.viewmodel.AppListViewModel;
import com.xiaopeng.appstore.bizcommon.logic.AgreementDialogHelper;
import com.xiaopeng.appstore.bizcommon.logic.AgreementManager;
import com.xiaopeng.appstore.bizcommon.logic.Constants;
import com.xiaopeng.appstore.bizcommon.logic.IAgreementViewListener;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.common_ui.BaseBizFragment;
import com.xiaopeng.appstore.common_ui.BizRootActivity;
import com.xiaopeng.appstore.common_ui.IdleDetectLayout;
import com.xiaopeng.appstore.common_ui.common.AndroidLifecycleManager;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
import com.xiaopeng.appstore.libcommon.utils.IdleViewDetectHelper;
import com.xiaopeng.appstore.xpcommon.VuiEngineUtils;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.speech.vui.Helper.IVuiSceneHelper;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.widget.XLinearLayout;
import java.util.Collections;
import java.util.List;
/* loaded from: classes2.dex */
public class AppListFragment extends BaseBizFragment implements IdleViewDetectHelper.IdleCallback, IAgreementViewListener, AppListRootView.AppListRootViewCallback, IVuiSceneHelper {
    private static final String TAG = "AppFrag";
    private static final String TAG_IDLE_DETECT = "AppFrag_IDLE";
    private static final long UNUSED_TIMEOUT = 10000;
    private AppListAdapter mAppListAdapter;
    private IVuiSceneHelper mIVuiSceneHelper;
    private IdleDetectLayout mIdleDetectLayout;
    private View mLoadingView;
    private AppListViewModel mViewModel;
    private ViewTreeObserverInterface mViewTreeObserverInterface;
    private BizRootActivity rootActivity;
    private XLinearLayout xLinearLayout;
    private long mPageStayStart = 0;
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());
    private final Runnable mPostAppStoreInit = new Runnable() { // from class: com.xiaopeng.appstore.applist_ui.fragment.-$$Lambda$AppListFragment$K2yaQIBspkqsACB6aVShSA9qqBI
        @Override // java.lang.Runnable
        public final void run() {
            AppListFragment.this.tryNotifyAppStoreInit();
        }
    };
    private boolean mIsFirstLoad = true;
    private final AndroidLifecycleManager mAndroidLifecycleManager = new AndroidLifecycleManager();

    public AppListFragment() {
        Logger.t(TAG).i("create:" + hashCode() + ", " + this, new Object[0]);
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.t(TAG).d("AppListFragment onCreate:" + hashCode() + ", screenId:" + XuiMgrHelper.get().getShareId());
        this.rootActivity = (BizRootActivity) requireActivity();
        getLifecycle().addObserver(this.mAndroidLifecycleManager);
        this.mAndroidLifecycleManager.addObserver(AppListCache.get());
        getLifecycle().addObserver(IconAnimHelper.IconAnimHelperGenerator.get());
        this.mViewModel = createViewModel();
        AppListRootView.setInstance(XuiMgrHelper.get().getShareId() == 1 ? AppListRootView.InstanceType.SECOND : AppListRootView.InstanceType.FIRST);
        AppListRootView.get().tryInitCache(hashCode());
        AppListRootView.get().setCallback(this);
        this.mIVuiSceneHelper = this;
        this.mAppListAdapter = AppListRootView.get().getAppListAdapter();
        this.mLoadingView = AppListRootView.get().getLoadingView();
        AppListViewModel appListViewModel = this.mViewModel;
        if (appListViewModel != null) {
            appListViewModel.init(AppListRootView.get().getAppGroupHelper());
            this.mViewModel.loadData();
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.BizRootActivity.OnActivityEventListener
    public void onWindowReset() {
        Logger.t(TAG).i("onWindowReset, scroll to top", new Object[0]);
        AppListRootView.get().recyclerViewReset();
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mIdleDetectLayout = (IdleDetectLayout) inflater.inflate(R.layout.app_list_fragment, container, false);
        ViewParent parent = AppListRootView.get().getRootView().getParent();
        if (parent instanceof IdleDetectLayout) {
            Logger.t(TAG).w("onCreateView, AppListRootView has parent:" + parent, new Object[0]);
            ((IdleDetectLayout) parent).removeAllViews();
        }
        Logger.t(TAG).i("onCreateView, AppListRootView add to:" + this.mIdleDetectLayout, new Object[0]);
        this.mIdleDetectLayout.addView(AppListRootView.get().getRootView());
        this.mIdleDetectLayout.setIdleDelay(UNUSED_TIMEOUT);
        this.mIdleDetectLayout.setIdleListener(this);
        this.mIdleDetectLayout.disableDetect();
        return this.mIdleDetectLayout;
    }

    protected AppListViewModel createViewModel() {
        return (AppListViewModel) new ViewModelProvider(this).get(AppListViewModel.class);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subscribeViewModel();
        ViewTreeObserver viewTreeObserver = AppListRootView.get().getRecyclerView().getViewTreeObserver();
        ViewTreeObserverInterface viewTreeObserverInterface = new ViewTreeObserverInterface(getContext(), view, getLifecycle(), getSceneId(), this.mIVuiSceneHelper);
        this.mViewTreeObserverInterface = viewTreeObserverInterface;
        viewTreeObserver.addOnGlobalLayoutListener(viewTreeObserverInterface);
    }

    private void subscribeViewModel() {
        Logger.t(TAG).i("subscribeViewModel", new Object[0]);
        this.mViewModel.getAdapterDataListLiveData().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.applist_ui.fragment.-$$Lambda$AppListFragment$S0wSCdJygtO9SZajW8JyJZ27T9k
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppListFragment.this.lambda$subscribeViewModel$0$AppListFragment((List) obj);
            }
        });
        this.mViewModel.getAppListChangeEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.applist_ui.fragment.-$$Lambda$AppListFragment$vCGOysL8lKbyYDVhha4YMewXLDc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppListFragment.this.lambda$subscribeViewModel$1$AppListFragment((AppListChangeModel) obj);
            }
        });
        this.mViewModel.getAppItemChanged().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.applist_ui.fragment.-$$Lambda$AppListFragment$tirwOruJud0Jeaa3OJznVoEiJig
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppListFragment.this.lambda$subscribeViewModel$2$AppListFragment((AdapterChangeModel) obj);
            }
        });
        this.mViewModel.getAppItemIconChangeEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.applist_ui.fragment.-$$Lambda$AppListFragment$rzoLLoM_UrOAIp5efmClgdnje28
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppListFragment.this.lambda$subscribeViewModel$3$AppListFragment((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$subscribeViewModel$0$AppListFragment(List list) {
        Logger.t(TAG).i("submitList, data=" + list, new Object[0]);
        postNotifyAppStoreInit();
        this.mAppListAdapter.submitList(list);
        View view = this.mLoadingView;
        if (view != null) {
            view.setVisibility(8);
        }
    }

    public /* synthetic */ void lambda$subscribeViewModel$1$AppListFragment(AppListChangeModel appListChangeModel) {
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

    public /* synthetic */ void lambda$subscribeViewModel$2$AppListFragment(AdapterChangeModel adapterChangeModel) {
        Logger.t("AppFrag_notify").i("notifyItemChanged index=" + adapterChangeModel.getIndex(), new Object[0]);
        this.mAppListAdapter.notifyItemChanged(adapterChangeModel.getIndex());
    }

    public /* synthetic */ void lambda$subscribeViewModel$3$AppListFragment(Boolean bool) {
        if (bool.booleanValue()) {
            PayloadData payloadData = new PayloadData();
            payloadData.type = 4;
            Logger.t("AppFrag_notify").i("notifyItemRangeChanged iconChanged", new Object[0]);
            AppListAdapter appListAdapter = this.mAppListAdapter;
            appListAdapter.notifyItemRangeChanged(0, appListAdapter.getItemCount(), payloadData);
        }
    }

    private void postNotifyAppStoreInit() {
        this.mMainHandler.postDelayed(this.mPostAppStoreInit, 1000L);
    }

    private void cancelNotifyAppStoreInit() {
        Logger.t(TAG).i("cancelNotifyAppStoreInit", new Object[0]);
        this.mMainHandler.removeCallbacks(this.mPostAppStoreInit);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryNotifyAppStoreInit() {
        if (this.mIsFirstLoad) {
            Logger.t(TAG).i("tryNotifyAppStoreInit", new Object[0]);
            this.rootActivity.notifyTabInit(BizRootActivity.TabDefine.AppStore);
            this.mIsFirstLoad = false;
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStart() {
        Logger.t(TAG).i("AppListFragment onStart Start:" + hashCode(), new Object[0]);
        super.onStart();
        Logger.t(TAG).i("AppListFragment onStart End", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        Logger.t(TAG).i("AppListFragment onResume Start:" + hashCode(), new Object[0]);
        super.onResume();
        this.mPageStayStart = System.currentTimeMillis();
        AppListRootView.get().resume();
        this.mRootActivity.setCanNavBack(false);
        AgreementManager.get().addAgreementViewListener(this);
        Logger.t(TAG).i("AppListFragment onResume End", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onPause() {
        Logger.t(TAG).i("AppListFragment OnPause Start", new Object[0]);
        super.onPause();
        EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_PANEL, EventEnum.MAIN_ENTRANCE, 1, Long.valueOf(System.currentTimeMillis() - this.mPageStayStart));
        AppListRootView.get().pause();
        this.mMainHandler.removeCallbacksAndMessages(null);
        Logger.t(TAG).i("AppListFragment OnPause End", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        Logger.t(TAG).i("AppListFragment onStop Start", new Object[0]);
        super.onStop();
        destroyAgreement();
        cancelNotifyAppStoreInit();
        this.mIsFirstLoad = true;
        AppListViewModel appListViewModel = this.mViewModel;
        if (appListViewModel != null) {
            appListViewModel.onStop();
        }
        Logger.t(TAG).i("AppListFragment onStop End", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        Logger.t(TAG).i("AppListFragment onDestroy Start:" + hashCode() + ", screenId:" + XuiMgrHelper.get().getShareId(), new Object[0]);
        super.onDestroy();
        getLifecycle().removeObserver(this.mAndroidLifecycleManager);
        AppListRootView.get().destroy(hashCode());
        AppListRootView.get().getRecyclerView().getViewTreeObserver().removeOnGlobalLayoutListener(this.mViewTreeObserverInterface);
        this.mIdleDetectLayout.removeAllViews();
        Logger.t(TAG).i("AppListFragment onDestroy End", new Object[0]);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (ThemeManager.isThemeChanged(newConfig)) {
            this.mViewModel.loadAllIconsAsync();
            AppListRootView.get().changeTheme();
        }
    }

    private void destroyAgreement() {
        AgreementManager.get().removeAgreementViewListener(this);
        AgreementDialogHelper.getInstance().dismiss();
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.IAgreementViewListener
    public void onShowAgreementDialog(final Runnable runnable, final boolean async) {
        AgreementDialogHelper.getInstance().show(new Runnable() { // from class: com.xiaopeng.appstore.applist_ui.fragment.-$$Lambda$AppListFragment$T9Kn8sKaql83gMsbfGBXl3tBQfk
            @Override // java.lang.Runnable
            public final void run() {
                AgreementManager.get().agreement(runnable, async);
            }
        });
    }

    @Override // com.xiaopeng.appstore.libcommon.utils.IdleViewDetectHelper.IdleCallback
    public void onIdle() {
        Logger.t(TAG_IDLE_DETECT).i("Idle timeout.", new Object[0]);
        AppListRootView.get().exitEdit();
    }

    @Override // com.xiaopeng.appstore.libcommon.utils.IdleViewDetectHelper.IdleCallback
    public void onIdleDetectStart() {
        Logger.t(TAG_IDLE_DETECT).i("Idle detect started.", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.libcommon.utils.IdleViewDetectHelper.IdleCallback
    public void onIdleDetectStop() {
        Logger.t(TAG_IDLE_DETECT).i("Idle detect stopped.", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.applist_ui.view.AppListRootView.AppListRootViewCallback
    public void onIconMove(int fromPos, int toPos) {
        this.mViewModel.moveData(fromPos, toPos);
    }

    @Override // com.xiaopeng.appstore.applist_ui.view.AppListRootView.AppListRootViewCallback
    public void onIconDrop(int fromPos, int toPos) {
        this.mViewModel.persistOrderAsync();
    }

    @Override // com.xiaopeng.appstore.applist_ui.view.AppListRootView.AppListRootViewCallback
    public void onEnterEdit(View pressedView) {
        this.mIdleDetectLayout.enableDetect();
        this.rootActivity.setTitleVisible(false);
    }

    @Override // com.xiaopeng.appstore.applist_ui.view.AppListRootView.AppListRootViewCallback
    public void onExitEdit() {
        this.rootActivity.setTitleVisible(true);
        this.mIdleDetectLayout.disableDetect();
    }

    @Override // com.xiaopeng.appstore.applist_ui.view.AppListRootView.AppListRootViewCallback
    public void onActivityStart(NormalAppItem info) {
        if (Constants.CLOSE_PANEL_APP_LIST.contains(info.packageName)) {
            this.rootActivity.closeWindow();
        }
    }

    @Override // com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return getClass().getSimpleName();
    }

    @Override // com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public List<View> getBuildViews() {
        return Collections.singletonList(getView());
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent event) {
        Log.i(TAG, "onInterceptVuiEvent: " + event.getEventValue(event));
        if (view instanceof XLinearLayout) {
            ((AppIconViewHolder) AppListRootView.get().getRecyclerView().getChildViewHolder(view)).onVuiEvent((XLinearLayout) view);
            return false;
        }
        return false;
    }

    /* loaded from: classes2.dex */
    private static class ViewTreeObserverInterface implements ViewTreeObserver.OnGlobalLayoutListener {
        private Context context;
        IVuiSceneHelper iVuiSceneHelper;
        private Lifecycle lifecycle;
        private String sceneId;
        private View view;

        public ViewTreeObserverInterface(Context context, View view, Lifecycle lifecycle, String sceneId, IVuiSceneHelper iVuiSceneHelper) {
            this.view = view;
            this.context = context;
            this.sceneId = sceneId;
            this.lifecycle = lifecycle;
            this.iVuiSceneHelper = iVuiSceneHelper;
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            VuiEngineUtils.initScene(this.context, this.lifecycle, this.sceneId, this.view, this.iVuiSceneHelper);
        }
    }
}
