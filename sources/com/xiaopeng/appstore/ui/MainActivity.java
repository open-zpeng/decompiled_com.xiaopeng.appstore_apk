package com.xiaopeng.appstore.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Choreographer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;
import com.xiaopeng.appstore.AppUtils;
import com.xiaopeng.appstore.LayoutSelector;
import com.xiaopeng.appstore.R;
import com.xiaopeng.appstore.applist_biz.logic.AppListCache;
import com.xiaopeng.appstore.appstore_biz.bizusb.logic.USBStateManager;
import com.xiaopeng.appstore.appstore_biz.bizusb.util.UpdateAppConfigUtils;
import com.xiaopeng.appstore.appstore_biz.logic.CheckUpdateManager;
import com.xiaopeng.appstore.appstore_biz.model.TabBean;
import com.xiaopeng.appstore.appstore_ui.bizusb.fragment.LocalAppFragment;
import com.xiaopeng.appstore.appstore_ui.fragment.StoreRootFragment;
import com.xiaopeng.appstore.bizcommon.logic.AgreementManager;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.Constants;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.util.AlipayEnginePrepareHelper;
import com.xiaopeng.appstore.bizcommon.router.RouterConstants;
import com.xiaopeng.appstore.bizcommon.router.RouterManager;
import com.xiaopeng.appstore.bizcommon.router.provider.AppStoreProvider;
import com.xiaopeng.appstore.bizcommon.utils.AccountUtils;
import com.xiaopeng.appstore.bizcommon.utils.DeviceUtils;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.common_ui.BizRootActivity;
import com.xiaopeng.appstore.common_ui.BizRootActivityImpl;
import com.xiaopeng.appstore.common_ui.OpenAppMgr;
import com.xiaopeng.appstore.common_ui.common.AndroidLifecycleManager;
import com.xiaopeng.appstore.common_ui.widget.XTabLayout;
import com.xiaopeng.appstore.debug.DebugDialog;
import com.xiaopeng.appstore.international.InternationalRootFragment;
import com.xiaopeng.appstore.libcommon.utils.AnimationUtils;
import com.xiaopeng.appstore.libcommon.utils.AsyncInflateHelper;
import com.xiaopeng.appstore.libcommon.utils.HintGestureDetector;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.libcommon.utils.SPUtils;
import com.xiaopeng.appstore.libcommon.utils.StartPerformanceUtils;
import com.xiaopeng.appstore.libcommon.utils.TraceUtils;
import com.xiaopeng.appstore.ui.HomeTabAdapter;
import com.xiaopeng.appstore.ui.tabs.AppListTab;
import com.xiaopeng.appstore.ui.tabs.AppStoreTab;
import com.xiaopeng.appstore.ui.tabs.AppletListTab;
import com.xiaopeng.appstore.xpcommon.BuildInfoUtils;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XImageButton;
/* loaded from: classes2.dex */
public class MainActivity extends BizRootActivityImpl implements ViewPager.OnPageChangeListener, XTabLayout.OnTabChangeListener, View.OnClickListener, USBStateManager.OnUsbMgrCallback {
    private static final String FRAGMENTS_TAG = "android:support:fragments";
    private static final boolean INFLATE_ASYNC = true;
    private static final int REQUEST_CODE = 90000;
    private static final String SAVED_COMPONENTS_KEY = "androidx.lifecycle.BundlableSavedStateRegistry.key";
    private static final String TAG = "MainActivity";
    private static final String TAG_FRAGMENT_LOCAL = "fragment_local";
    private static final int TITLE_ANIM_DURATION = 400;
    public AppStoreProvider mAppStoreProvider;
    private ImageView mBackBtn;
    private View mBetaIndicator;
    private Drawable mBtnBackIcon;
    private Drawable mBtnBg;
    private Drawable mBtnCloseIcon;
    private XImageButton mBtnUSB;
    private Class<?> mClazz;
    private DebugDialog mDebugDialog;
    private HintGestureDetector mDebugGestureDetector;
    private FragmentAdapter mFragmentAdapter;
    private AlphaAnimation mHideAnimation;
    private HomeTabAdapter mHomeTabAdapter;
    private int mIndex;
    private AlphaAnimation mShowAnimation;
    private View mTabsParent;
    private Group mTitleGroup;
    private USBStateManager mUsbStateManager;
    private ViewPager mViewPager;
    private XTabLayout mXTabLayout;
    private boolean mCanNavBack = false;
    private boolean mTitleVisible = true;
    private boolean mWindowReset = true;
    private final ViewPager2.OnPageChangeCallback mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() { // from class: com.xiaopeng.appstore.ui.MainActivity.1
        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            if (!MainActivity.this.mOnActivityEventListenerList.isEmpty()) {
                for (BizRootActivity.OnActivityEventListener onActivityEventListener : MainActivity.this.mOnActivityEventListenerList) {
                    onActivityEventListener.onPageSelected(position);
                }
            }
            MainActivity.this.mXTabLayout.selectTab(position);
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    };
    private boolean isFirstFrame = true;
    private final Choreographer.FrameCallback mFrameCallback = new Choreographer.FrameCallback() { // from class: com.xiaopeng.appstore.ui.MainActivity.2
        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long frameTimeNanos) {
            if (MainActivity.this.isFirstFrame) {
                Choreographer.getInstance().postFrameCallback(MainActivity.this.mFrameCallback);
                MainActivity.this.isFirstFrame = false;
            }
        }
    };
    private final Animation.AnimationListener mShowAnimListener = new Animation.AnimationListener() { // from class: com.xiaopeng.appstore.ui.MainActivity.3
        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            int i = 0;
            if (MainActivity.this.mTitleGroup != null) {
                MainActivity.this.mTitleGroup.setVisibility(0);
            }
            if (MainActivity.this.mBtnUSB != null) {
                MainActivity.this.mBtnUSB.setVisibility((MainActivity.this.mUsbStateManager == null || !MainActivity.this.mUsbStateManager.isShowEntry()) ? 8 : 8);
            }
        }
    };
    private final Animation.AnimationListener mHideAnimListener = new Animation.AnimationListener() { // from class: com.xiaopeng.appstore.ui.MainActivity.4
        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            MainActivity.this.mTitleGroup.setVisibility(8);
            MainActivity.this.mBtnUSB.setVisibility(8);
        }
    };
    private final AndroidLifecycleManager mAndroidLifecycleManager = new AndroidLifecycleManager();
    private boolean mInflateFinish = false;

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int state) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override // com.xiaopeng.appstore.common_ui.widget.XTabLayout.OnTabChangeListener
    public void onTabChangeStart(XTabLayout xTabLayout, final int index, boolean tabChange, boolean fromUser) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.BizRootActivityImpl, com.xiaopeng.appstore.common_ui.base.BaseActivity, com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        Logger.t(TAG).d("onCreate start:" + hashCode() + ", savedInstanceState:" + savedInstanceState);
        TraceUtils.alwaysTraceBegin("MainActivity onCreate");
        if (AppListCache.get().getAppList().size() > 0) {
            AppListCache.get().reloadNapaAppList();
        }
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable(FRAGMENTS_TAG, null);
            Bundle bundle = savedInstanceState.getBundle(SAVED_COMPONENTS_KEY);
            if (bundle != null) {
                bundle.remove(FRAGMENTS_TAG);
            }
        }
        this.mInflateFinish = false;
        Choreographer.getInstance().postFrameCallback(this.mFrameCallback);
        StartPerformanceUtils.onCreateBegin();
        super.onCreate(savedInstanceState);
        Logger.t(TAG).d("onCreate super end:" + hashCode());
        setContentView(R.layout.activity_main_container);
        final ViewGroup viewGroup = (ViewGroup) findViewById(R.id.root_container);
        final View findViewById = findViewById(R.id.loading_view);
        this.mAppStoreProvider = (AppStoreProvider) RouterManager.get().getModule(RouterConstants.AppStoreProviderPaths.APP_STORE_MAIN_PROVIDER);
        AsyncLayoutInflater.OnInflateFinishedListener onInflateFinishedListener = new AsyncLayoutInflater.OnInflateFinishedListener() { // from class: com.xiaopeng.appstore.ui.-$$Lambda$MainActivity$dsEHluDFI-IHJKIZsm-VHrt5mv8
            @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
            public final void onInflateFinished(View view, int i, ViewGroup viewGroup2) {
                MainActivity.this.lambda$onCreate$3$MainActivity(viewGroup, findViewById, view, i, viewGroup2);
            }
        };
        int layoutId = LayoutSelector.getLayoutId();
        if (savedInstanceState == null) {
            AsyncInflateHelper.inflateAsync(this, layoutId, null, onInflateFinishedListener);
        } else {
            Logger.t(TAG).w("onCreate, inflate in MainThread", new Object[0]);
            onInflateFinishedListener.onInflateFinished(LayoutInflater.from(this).inflate(layoutId, (ViewGroup) null), layoutId, null);
        }
        Logger.t(TAG).d("onCreate setContent end:" + hashCode());
        getLifecycle().addObserver(this.mAndroidLifecycleManager);
        this.mAndroidLifecycleManager.addObserver(AppComponentManager.get());
        this.mAndroidLifecycleManager.addObserver(AppStoreAssembleManager.get());
        this.mAndroidLifecycleManager.addObserver(CheckUpdateManager.get());
        getLifecycle().addObserver(OpenAppMgr.get());
        AccountUtils.get().setCurrentActivity(this);
        this.mDebugGestureDetector = new HintGestureDetector(7, new HintGestureDetector.SimpleHintGestureListener() { // from class: com.xiaopeng.appstore.ui.MainActivity.5
            @Override // com.xiaopeng.appstore.libcommon.utils.HintGestureDetector.SimpleHintGestureListener, com.xiaopeng.appstore.libcommon.utils.HintGestureDetector.OnHintGestureListener
            public void onTrig() {
                if (MainActivity.this.mDebugDialog == null) {
                    MainActivity.this.mDebugDialog = new DebugDialog(MainActivity.this);
                }
                MainActivity.this.mDebugDialog.show();
            }
        });
        StartPerformanceUtils.onCreateEnd();
        TraceUtils.alwaysTraceEnd();
        AlipayEnginePrepareHelper.getInstance().init();
        Logger.t(TAG).d("onCreate end:" + hashCode());
    }

    public /* synthetic */ void lambda$onCreate$3$MainActivity(ViewGroup viewGroup, View view, View view2, int i, ViewGroup viewGroup2) {
        viewGroup.addView(view2);
        if (view != null) {
            view.setVisibility(8);
        }
        loadBtnDrawable();
        this.mViewPager = (ViewPager) findViewById(R.id.view_pager);
        this.mTabsParent = findViewById(R.id.tabs_parent);
        this.mBetaIndicator = findViewById(R.id.beta_indicator);
        ImageView imageView = (ImageView) findViewById(R.id.iv_back_btn);
        this.mBackBtn = imageView;
        imageView.setOnClickListener(this);
        this.mTitleGroup = (Group) findViewById(R.id.title_group);
        Intent intent = getIntent();
        this.mHomeTabAdapter = HomeTabAdapter.Factory.create().build();
        initTabTitles();
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), this.mHomeTabAdapter);
        this.mFragmentAdapter = fragmentAdapter;
        this.mViewPager.setAdapter(fragmentAdapter);
        this.mViewPager.setOffscreenPageLimit(this.mHomeTabAdapter.getCount() - 1);
        this.mViewPager.addOnPageChangeListener(this);
        if (BuildInfoUtils.isDebuggableVersion()) {
            this.mTabsParent.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.appstore.ui.-$$Lambda$MainActivity$qGN8VhBryj6f4zhazrGoZfooBY0
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view3, MotionEvent motionEvent) {
                    return MainActivity.this.lambda$onCreate$0$MainActivity(view3, motionEvent);
                }
            });
        }
        USBStateManager uSBStateManager = new USBStateManager();
        this.mUsbStateManager = uSBStateManager;
        uSBStateManager.setCallback(this);
        this.mUsbStateManager.loadUsbApp();
        this.mUsbStateManager.registerUSBState();
        XImageButton xImageButton = (XImageButton) findViewById(R.id.btn_usb);
        this.mBtnUSB = xImageButton;
        xImageButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.ui.-$$Lambda$MainActivity$bJr6xi6m64lGeBgjj-vsFmap2Os
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                MainActivity.this.lambda$onCreate$1$MainActivity(view3);
            }
        });
        if (this.mAppStoreProvider != null && !DeviceUtils.isInter()) {
            this.mAppStoreProvider.getPendingUpgradeCount().observe(this, new Observer() { // from class: com.xiaopeng.appstore.ui.-$$Lambda$MainActivity$OoBYSaavgUdXzzbBo103yQZD4x4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    MainActivity.this.lambda$onCreate$2$MainActivity((Integer) obj);
                }
            });
        }
        this.mInflateFinish = true;
        handleReset(intent);
    }

    public /* synthetic */ boolean lambda$onCreate$0$MainActivity(View view, MotionEvent motionEvent) {
        return this.mDebugGestureDetector.onTouchEvent(motionEvent);
    }

    public /* synthetic */ void lambda$onCreate$1$MainActivity(View view) {
        LocalAppFragment localAppFragment = (LocalAppFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LOCAL);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if (localAppFragment == null) {
            localAppFragment = new LocalAppFragment();
            beginTransaction.add((int) R.id.root_layout, localAppFragment, TAG_FRAGMENT_LOCAL);
        } else {
            beginTransaction.show(localAppFragment);
        }
        localAppFragment.setUsbStateManager(this.mUsbStateManager);
        beginTransaction.commitAllowingStateLoss();
    }

    public /* synthetic */ void lambda$onCreate$2$MainActivity(Integer num) {
        if (num.intValue() < 0) {
            return;
        }
        this.mXTabLayout.changeDotState(num.intValue() > 0 ? 0 : 8, this.mHomeTabAdapter.getIndexOf(AppStoreTab.class));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.t(TAG).i("onSaveInstanceState, " + outState, new Object[0]);
    }

    @Override // android.app.Activity
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logger.t(TAG).i("onRestoreInstanceState," + savedInstanceState, new Object[0]);
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivityImpl
    protected void onWindowReset() {
        Logger.t(TAG).i("MainActivity onWindowReset Start", new Object[0]);
        this.mWindowReset = true;
        if (LogicUtils.getSuspendStatusMap() != null) {
            LogicUtils.getSuspendStatusMap().clear();
        }
        Logger.t(TAG).i("MainActivity onWindowReset End", new Object[0]);
    }

    private void initTabTitles() {
        XTabLayout xTabLayout = (XTabLayout) findViewById(R.id.tab_layout);
        this.mXTabLayout = xTabLayout;
        xTabLayout.setOnTabChangeListener(this);
        if (this.mHomeTabAdapter != null) {
            for (int i = 0; i < this.mHomeTabAdapter.getCount(); i++) {
                this.mXTabLayout.addTab(this.mHomeTabAdapter.getTitle(i));
            }
        }
    }

    private void refreshTitles() {
        HomeTabAdapter homeTabAdapter = this.mHomeTabAdapter;
        if (homeTabAdapter != null) {
            homeTabAdapter.initTitles();
            for (int i = 0; i < this.mHomeTabAdapter.getCount(); i++) {
                this.mXTabLayout.updateTabTitle(i, this.mHomeTabAdapter.getTitle(i));
            }
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public void handleStart(Intent intent) {
        if (!this.mInflateFinish) {
            Logger.t(TAG).w("handleStart return since Inflate not finish yet! intent:" + intent, new Object[0]);
            return;
        }
        boolean handleStart = this.mHomeTabAdapter.handleStart(intent);
        int defaultSelected = this.mHomeTabAdapter.getDefaultSelected();
        if (handleStart) {
            Logger.t(TAG).i("handleStart intent=" + intent + " tab changed:" + defaultSelected, new Object[0]);
        } else {
            Logger.t(TAG).i("handleStart intent=" + intent + "  tab not changed.", new Object[0]);
        }
        Fragment orCreateFragment = this.mHomeTabAdapter.getOrCreateFragment(defaultSelected, true);
        if (orCreateFragment != null) {
            TabBean tabBean = new TabBean();
            Bundle arguments = orCreateFragment.getArguments();
            if (arguments == null) {
                arguments = new Bundle();
                orCreateFragment.setArguments(arguments);
            }
            if (intent != null) {
                Uri data = intent.getData();
                Log.d(TAG, "handleStart: Uri=" + data);
                if (data != null) {
                    String queryParameter = data.getQueryParameter("id");
                    int intExtra = intent.getIntExtra(Constants.OPEN_PAGE_WITH_ACTION_, 0);
                    Logger.t(TAG).d("action = " + intExtra);
                    if (AppUtils.isGoToDetail(data)) {
                        tabBean.setNavigatorType(200);
                        tabBean.setPackageName(queryParameter);
                        tabBean.setAction(intExtra);
                        arguments.putParcelable(Constants.STORE_FRAGMENT_PARAMS, tabBean);
                    }
                }
            }
        } else {
            Logger.t(TAG).w("Fragment is null for this index:" + defaultSelected, new Object[0]);
        }
        this.mXTabLayout.selectTab(defaultSelected, false);
        if (this.mViewPager.getCurrentItem() != defaultSelected) {
            Logger.t(TAG).d("handleStart viewPager setCurrentItem " + defaultSelected);
            this.mViewPager.setCurrentItem(defaultSelected, false);
            return;
        }
        Logger.t(TAG).d("handleStart viewPager, index not changed, ignore setCurrentItem " + defaultSelected);
        if (orCreateFragment instanceof StoreRootFragment) {
            StoreRootFragment storeRootFragment = (StoreRootFragment) orCreateFragment;
            if (storeRootFragment.isResumed()) {
                Logger.t(TAG).d("handleStart viewPager, StoreRootFragment resumed, handleNavigate");
                storeRootFragment.handleNavigate();
            }
        }
        if (orCreateFragment instanceof InternationalRootFragment) {
            InternationalRootFragment internationalRootFragment = (InternationalRootFragment) orCreateFragment;
            if (internationalRootFragment.isResumed()) {
                Logger.t(TAG).d("handleStart viewPager, InternationalStoreRootFragment resumed, handleNavigate");
                internationalRootFragment.handleNavigate();
            }
        }
    }

    private void handleReset(Intent intent) {
        if (this.mWindowReset || intent.getData() != null || intent.getAction() != null) {
            handleStart(intent);
        }
        AppStoreProvider appStoreProvider = this.mAppStoreProvider;
        if (appStoreProvider != null && this.mWindowReset) {
            appStoreProvider.requestUpdateData();
        }
        this.mWindowReset = false;
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public int getIndexOfTab(BizRootActivity.TabDefine tabDefine) {
        if (tabDefine == BizRootActivity.TabDefine.Applet) {
            return this.mHomeTabAdapter.getIndexOf(AppletListTab.class);
        }
        if (tabDefine == BizRootActivity.TabDefine.AppList) {
            return this.mHomeTabAdapter.getIndexOf(AppListTab.class);
        }
        if (tabDefine == BizRootActivity.TabDefine.AppStore) {
            return this.mHomeTabAdapter.getIndexOf(AppStoreTab.class);
        }
        return -1;
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public void notifyTabInit(BizRootActivity.TabDefine tab) {
        int indexOfTab = getIndexOfTab(tab);
        Fragment fragment = this.mHomeTabAdapter.getFragment(indexOfTab);
        Logger.t(TAG).i("notifyTabInit, tab:" + tab + ", index:" + indexOfTab + ", fragment:" + fragment, new Object[0]);
        if (fragment instanceof BizRootActivity.OnActivityEventListener) {
            ((BizRootActivity.OnActivityEventListener) fragment).onTabInitRequest();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        Logger.t(TAG).i("MainActivity onNewIntent Start", new Object[0]);
        super.onNewIntent(intent);
        Logger.t(TAG).d("intent = " + intent);
        handleReset(intent);
        Logger.t(TAG).i("MainActivity onNewIntent End", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.BizRootActivityImpl, com.xiaopeng.appstore.common_ui.base.BaseActivity, com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        Logger.t(TAG).i("MainActivity onStart Start", new Object[0]);
        TraceUtils.alwaysTraceBegin("MainActivity onStart");
        StartPerformanceUtils.onStartBegin();
        super.onStart();
        long j = SPUtils.getInstance().getLong(Constants.SP_UPDATE_CONFIG_TIME, 0L);
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - j > Constants.UPDATE_CONFIG_TIME) {
            UpdateAppConfigUtils.checkAndUpdateAppConfig();
            SPUtils.getInstance().put(Constants.SP_UPDATE_CONFIG_TIME, currentTimeMillis);
        }
        AppStoreBroadcastReceiver.setBizRootActivity(this);
        StartPerformanceUtils.onStartEnd();
        TraceUtils.alwaysTraceEnd();
        Logger.t(TAG).i("MainActivity onStart End", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.BizRootActivityImpl, com.xiaopeng.appstore.common_ui.base.BaseActivity, com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        Logger.t(TAG).d("MainActivity Intent Information:" + getIntent().toString());
        StartPerformanceUtils.onResumeBegin();
        Logger.t(TAG).i("MainActivity onResume Start", new Object[0]);
        TraceUtils.alwaysTraceBegin("MainActivity onResume");
        super.onResume();
        AgreementManager.get().registerAccountObserver();
        TraceUtils.alwaysTraceBegin("MainActivity onEnd");
        Logger.t(TAG).i("MainActivity onResume End", new Object[0]);
        StartPerformanceUtils.onResumeEnd();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.base.BaseActivity, com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        Logger.t(TAG).i("MainActivity onPause Start", new Object[0]);
        super.onPause();
        AgreementManager.get().unRegisterAccountObserver();
        Logger.t(TAG).i("MainActivity onPause End", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.BizRootActivityImpl, com.xiaopeng.appstore.common_ui.base.BaseActivity, com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        Logger.t(TAG).i("MainActivity onStop Start", new Object[0]);
        super.onStop();
        AppStoreBroadcastReceiver.setBizRootActivity(null);
        Logger.t(TAG).i("MainActivity onStop End", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.BizRootActivityImpl, com.xiaopeng.appstore.common_ui.base.BaseActivity, com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        Logger.t(TAG).i("MainActivity onDestroy Start", new Object[0]);
        super.onDestroy();
        if (LogicUtils.getSuspendStatusMap() != null) {
            LogicUtils.getSuspendStatusMap().clear();
        }
        AppStoreProvider appStoreProvider = this.mAppStoreProvider;
        if (appStoreProvider != null) {
            appStoreProvider.getPendingUpgradeCount().removeObservers(this);
        }
        AnimationUtils.cancelAnimation(this.mShowAnimation);
        AnimationUtils.cancelAnimation(this.mHideAnimation);
        AccountUtils.get().setCurrentActivity(null);
        DebugDialog debugDialog = this.mDebugDialog;
        if (debugDialog != null) {
            debugDialog.dismiss();
        }
        USBStateManager uSBStateManager = this.mUsbStateManager;
        if (uSBStateManager != null) {
            uSBStateManager.unRegisterUSBState();
            this.mUsbStateManager = null;
        }
        AlipayEnginePrepareHelper.getInstance().release();
        Logger.t(TAG).i("MainActivity onDestroy End", new Object[0]);
    }

    @Override // com.xiaopeng.xui.app.XActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        canNavBack();
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivityImpl, com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (ThemeManager.isThemeChanged(newConfig)) {
            Logger.t(TAG).d("MainActivity onConfigurationChanged theme");
            if (this.mInflateFinish) {
                loadBtnDrawable();
                refreshBackBtn();
                refreshTitles();
                View view = this.mBetaIndicator;
                if (view != null) {
                    view.setBackground(ResUtils.getDrawable(R.drawable.pic_label_beta));
                    return;
                }
                return;
            }
            Logger.t(TAG).w("onConfigurationChanged return since Inflate not finish yet!", new Object[0]);
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public int getIndex() {
        return this.mIndex;
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public Class<?> getCurrentSelected() {
        return this.mClazz;
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivityImpl, com.xiaopeng.appstore.common_ui.BizRootActivity
    public void setCanNavBack(boolean canNavBack) {
        Logger.t(TAG).d("setCanNavBack: canNavBack=" + canNavBack);
        this.mCanNavBack = canNavBack;
        refreshBackBtn();
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public void setTitleVisible(boolean visible) {
        this.mTitleVisible = visible;
        if (visible) {
            showTitle();
        } else {
            hideTitle();
        }
    }

    private void tryInitAnimation() {
        if (this.mShowAnimation == null) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            this.mShowAnimation = alphaAnimation;
            alphaAnimation.setDuration(400L);
            this.mShowAnimation.setAnimationListener(this.mShowAnimListener);
        }
        if (this.mHideAnimation == null) {
            AlphaAnimation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
            this.mHideAnimation = alphaAnimation2;
            alphaAnimation2.setDuration(400L);
            this.mHideAnimation.setAnimationListener(this.mHideAnimListener);
        }
    }

    private void hideTitle() {
        if (this.mTabsParent.getVisibility() != 0) {
            return;
        }
        this.mTitleGroup.setVisibility(8);
        this.mBtnUSB.setVisibility(8);
    }

    private void showTitle() {
        if (this.mTabsParent.getVisibility() == 0) {
            return;
        }
        tryInitAnimation();
        this.mBackBtn.startAnimation(this.mShowAnimation);
        this.mTabsParent.startAnimation(this.mShowAnimation);
    }

    private void refreshBackBtn() {
        Printer t = Logger.t(TAG);
        Object[] objArr = new Object[2];
        objArr[0] = Boolean.valueOf(this.mCanNavBack);
        objArr[1] = this.mCanNavBack ? this.mBtnBackIcon : this.mBtnCloseIcon;
        t.i("refreshBackBtn: canNavBack=%s, icon=%s.", objArr);
        if (this.mCanNavBack) {
            this.mBackBtn.setImageDrawable(this.mBtnBackIcon);
        } else {
            this.mBackBtn.setImageDrawable(this.mBtnCloseIcon);
        }
        this.mBackBtn.setBackground(this.mBtnBg);
    }

    private void loadBtnDrawable() {
        Logger.t(TAG).d("loadBtnDrawable start");
        this.mBtnBackIcon = ResUtils.getDrawable(R.drawable.x_ic_small_back);
        this.mBtnCloseIcon = ResUtils.getDrawable(R.drawable.x_ic_small_close);
        this.mBtnBg = ResUtils.getDrawable(R.drawable.x_btn_icon_bg_selector);
        Logger.t(TAG).i("loadBtnDrawable: backIcon=%s, closeIcon=%s", this.mBtnBackIcon, this.mBtnCloseIcon);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back_btn) {
            canNavBack();
        }
    }

    private void canNavBack() {
        if (this.mCanNavBack) {
            Fragment fragment = this.mHomeTabAdapter.getFragment(this.mViewPager.getCurrentItem());
            if (fragment instanceof NavHostFragment) {
                NavHostFragment navHostFragment = (NavHostFragment) fragment;
                NavController navController = null;
                try {
                    navController = navHostFragment.getNavController();
                } catch (IllegalStateException unused) {
                    Logger.t(TAG).e("NavController is not available before onCreate(), navHostFragment:" + navHostFragment.hashCode(), new Object[0]);
                }
                if (navController == null) {
                    Logger.t(TAG).w("NavController is null", new Object[0]);
                    return;
                } else if (navController.popBackStack()) {
                    return;
                } else {
                    navController.navigate(R.id.back_to_home);
                    return;
                }
            }
            Logger.t(TAG).e("Back click error, Not NavHostFragment:" + fragment, new Object[0]);
            return;
        }
        closeWindow();
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int position) {
        Logger.t(TAG).i("ViewPager onPageSelected " + position, new Object[0]);
        if (this.mOnActivityEventListenerList.isEmpty()) {
            return;
        }
        for (BizRootActivity.OnActivityEventListener onActivityEventListener : this.mOnActivityEventListenerList) {
            onActivityEventListener.onPageSelected(position);
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.widget.XTabLayout.OnTabChangeListener
    public boolean onInterceptTabChange(XTabLayout xTabLayout, final int index, boolean tabChange, boolean fromUser) {
        Logger.t(TAG).d("onInterceptTabChange index=" + index + " fromUser=" + fromUser);
        if (fromUser) {
            this.mViewPager.setCurrentItem(index);
            return false;
        }
        return false;
    }

    @Override // com.xiaopeng.appstore.common_ui.widget.XTabLayout.OnTabChangeListener
    public void onTabChangeEnd(XTabLayout xTabLayout, final int index, boolean tabChange, boolean fromUser) {
        Logger.t(TAG).d("onTabChangeEnd index=" + index + " tabChange=" + tabChange + " fromUser=" + fromUser);
        if (this.mOnActivityEventListenerList.isEmpty()) {
            return;
        }
        Fragment fragment = this.mHomeTabAdapter.getFragment(index);
        Class<?> cls = fragment != null ? fragment.getClass() : null;
        this.mClazz = cls;
        this.mIndex = index;
        for (BizRootActivity.OnActivityEventListener onActivityEventListener : this.mOnActivityEventListenerList) {
            onActivityEventListener.onTabSelected(index, cls);
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.bizusb.logic.USBStateManager.OnUsbMgrCallback
    public void onShowUsbEntry(boolean loading) {
        this.mBtnUSB.setEnabled(!loading);
        if (this.mTitleVisible) {
            this.mBtnUSB.setVisibility(0);
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.bizusb.logic.USBStateManager.OnUsbMgrCallback
    public void onHideUsbEntry() {
        this.mBtnUSB.setVisibility(8);
        Fragment findFragmentByTag = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LOCAL);
        if (findFragmentByTag != null) {
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.remove(findFragmentByTag);
            beginTransaction.commitAllowingStateLoss();
        }
    }
}
