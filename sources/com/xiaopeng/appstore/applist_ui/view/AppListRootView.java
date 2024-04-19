package com.xiaopeng.appstore.applist_ui.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import androidx.recyclerview.widget.RecyclerView;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.applist_biz.logic.AppListUtils;
import com.xiaopeng.appstore.applist_biz.model.AppGroupItemModel;
import com.xiaopeng.appstore.applist_biz.model.BaseAppItem;
import com.xiaopeng.appstore.applist_biz.model.FixedAppItem;
import com.xiaopeng.appstore.applist_biz.model.LoadingAppItem;
import com.xiaopeng.appstore.applist_biz.model.NapaAppItem;
import com.xiaopeng.appstore.applist_biz.model.NormalAppItem;
import com.xiaopeng.appstore.applist_ui.R;
import com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper;
import com.xiaopeng.appstore.applist_ui.adapter.AppIconViewHolder;
import com.xiaopeng.appstore.applist_ui.adapter.AppListAdapter;
import com.xiaopeng.appstore.applist_ui.adapter.OnAppInteractCallback;
import com.xiaopeng.appstore.applist_ui.view.AppListRootView;
import com.xiaopeng.appstore.bizcommon.entities.PackageUserKey;
import com.xiaopeng.appstore.bizcommon.logic.AgreementManager;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.Constants;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LaunchParam;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.lib.LaunchResult;
import com.xiaopeng.appstore.bizcommon.utils.AccountUtils;
import com.xiaopeng.appstore.bizcommon.utils.DeviceUtils;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.common_ui.NotificationObserverManager;
import com.xiaopeng.appstore.common_ui.OpenAppMgr;
import com.xiaopeng.appstore.common_ui.base.PanelDialog;
import com.xiaopeng.appstore.common_ui.common.EnhancedListAdapter;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
import com.xiaopeng.appstore.common_ui.icon.AppIconView;
import com.xiaopeng.appstore.common_ui.icon.OnLauncherResumeCallback;
import com.xiaopeng.appstore.common_ui.logic.LogicUIUtils;
import com.xiaopeng.appstore.libcommon.utils.AnimationUtils;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XToast;
import com.xiaopeng.xui.widget.XRecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
/* loaded from: classes2.dex */
public class AppListRootView implements OnAppInteractCallback, View.OnTouchListener, NotificationObserverManager.Launcher {
    private static final String TAG = "AppListRootView";
    private static final String TAG_IDLE_DETECT = "AppListRootView_IDLE";
    private static final int TITLE_ANIM_DURATION = 400;
    private static volatile AppListRootView sInstance1;
    private static volatile AppListRootView sInstance2;
    private static InstanceType sInstanceType = InstanceType.FIRST;
    private Context context;
    private AppListAdapter mAppListAdapter;
    private AppListRootViewCallback mCallback;
    private GestureDetector mClickDetector;
    private View mExitEditTips;
    private View mLoadingView;
    private OnLauncherResumeCallback mOnLauncherResumeCallback;
    private XRecyclerView mRecyclerView;
    private View mRootLayout;
    private AlphaAnimation mShowAnimation;
    private PanelDialog mUninstallDialog;
    private UninstallDialogClickListener mUninstallDialogClickListener;
    private Handler mMainHandler = new Handler(Looper.getMainLooper());
    private final AppGroupHelper mAppGroupHelper = new AppGroupHelper();
    private long enterEdit = -1;
    private boolean mScrolled = false;
    private int mContainerHashCode = -1;
    private final Animation.AnimationListener mShowAnimListener = new Animation.AnimationListener() { // from class: com.xiaopeng.appstore.applist_ui.view.AppListRootView.1
        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            if (AppListRootView.this.mExitEditTips != null) {
                AppListRootView.this.mExitEditTips.setVisibility(0);
            }
        }
    };
    private final RecyclerView.AdapterDataObserver mAdapterDataObserver = new RecyclerView.AdapterDataObserver() { // from class: com.xiaopeng.appstore.applist_ui.view.AppListRootView.2
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

    /* loaded from: classes2.dex */
    public interface AppListRootViewCallback {
        void onActivityStart(NormalAppItem info);

        void onEnterEdit(View pressedView);

        void onExitEdit();

        void onIconDrop(int fromPos, int toPos);

        void onIconMove(int fromPos, int toPos);
    }

    /* loaded from: classes2.dex */
    public enum InstanceType {
        FIRST,
        SECOND
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.OnAppInteractCallback
    public void onIconDragChanged(RecyclerView.ViewHolder viewHolder, int pos) {
    }

    public static void setInstance(InstanceType instance) {
        Logger.t(TAG).i("setInstance:" + instance, new Object[0]);
        sInstanceType = instance;
    }

    public static AppListRootView get() {
        if (sInstanceType == InstanceType.FIRST) {
            return get1();
        }
        if (sInstanceType == InstanceType.SECOND) {
            return get2();
        }
        return get1();
    }

    private static AppListRootView get1() {
        if (sInstance1 == null) {
            synchronized (AppListRootView.class) {
                if (sInstance1 == null) {
                    sInstance1 = new AppListRootView();
                }
            }
        }
        return sInstance1;
    }

    private static AppListRootView get2() {
        if (sInstance2 == null) {
            synchronized (AppListRootView.class) {
                if (sInstance2 == null) {
                    sInstance2 = new AppListRootView();
                }
            }
        }
        return sInstance2;
    }

    private AppListRootView() {
        Logger.t(TAG).i("Instructor:" + hashCode(), new Object[0]);
    }

    public void tryInitCache(int hashCode) {
        Logger.t(TAG).i(hashCode() + " tryInitCache hashcode:" + hashCode + ", current:" + this.mContainerHashCode, new Object[0]);
        int i = this.mContainerHashCode;
        if (i != 0) {
            destroy(i);
        }
        this.mContainerHashCode = hashCode;
        this.context = Utils.getApp();
        this.mMainHandler = new Handler(Looper.getMainLooper());
        if (this.mRootLayout == null) {
            Logger.t(TAG).i("init layout", new Object[0]);
            View inflate = LayoutInflater.from(this.context).inflate(R.layout.layout_applist_root, (ViewGroup) null);
            this.mRootLayout = inflate;
            inflate.setClickable(true);
            initView();
            initAnimation();
        }
        if (this.mRecyclerView.getAdapter() != null) {
            this.mAppListAdapter = (AppListAdapter) this.mRecyclerView.getAdapter();
            recyclerViewReset();
            View view = this.mLoadingView;
            if (view != null) {
                view.setVisibility(8);
            }
            restore();
        } else {
            AppListAdapter appListAdapter = new AppListAdapter(this.context, this.mAppGroupHelper, new EnhancedListAdapter.AdapterListUpdateCallback2(this.mRecyclerView));
            this.mAppListAdapter = appListAdapter;
            this.mRecyclerView.setAdapter(appListAdapter);
            this.mAppListAdapter.registerAdapterDataObserver(this.mAdapterDataObserver);
        }
        if (this.mRecyclerView.getLayoutManager() == null) {
            this.mRecyclerView.setLayoutManager(this.mAppListAdapter.getLayoutManager());
        }
        this.mAppListAdapter.setOnAppInteractCallback(this);
    }

    public void restore() {
        Logger.t(TAG).d("restore");
        if (this.mAppListAdapter != null) {
            PayloadData payloadData = new PayloadData();
            payloadData.type = 8;
            AppListAdapter appListAdapter = this.mAppListAdapter;
            appListAdapter.notifyItemRangeChanged(0, appListAdapter.getItemCount(), payloadData);
        }
    }

    public void release() {
        Logger.t(TAG).d("release");
        if (this.mAppListAdapter != null) {
            PayloadData payloadData = new PayloadData();
            payloadData.type = 9;
            AppListAdapter appListAdapter = this.mAppListAdapter;
            appListAdapter.notifyItemRangeChanged(0, appListAdapter.getItemCount(), payloadData);
        }
    }

    public void setCallback(AppListRootViewCallback mCallback) {
        this.mCallback = mCallback;
    }

    public AppGroupHelper getAppGroupHelper() {
        return this.mAppGroupHelper;
    }

    public AppListAdapter getAppListAdapter() {
        return this.mAppListAdapter;
    }

    public View getLoadingView() {
        return this.mLoadingView;
    }

    private void initAnimation() {
        if (this.mShowAnimation == null) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            this.mShowAnimation = alphaAnimation;
            alphaAnimation.setDuration(400L);
            this.mShowAnimation.setAnimationListener(this.mShowAnimListener);
        }
    }

    private void initView() {
        XRecyclerView xRecyclerView = (XRecyclerView) this.mRootLayout.findViewById(R.id.recycler_view);
        this.mRecyclerView = xRecyclerView;
        xRecyclerView.setVuiCanControlScroll(true);
        this.mLoadingView = this.mRootLayout.findViewById(R.id.loading_view);
        XRecyclerView xRecyclerView2 = this.mRecyclerView;
        if (xRecyclerView2 != null) {
            xRecyclerView2.getRecycledViewPool().setMaxRecycledViews(100, 50);
        }
    }

    private boolean tryEnterEdit(View pressedView) {
        if (AccountUtils.get().isLogin() || Build.VERSION.SDK_INT >= 31) {
            Logger.t(TAG).i("tryEnterEdit, already login.", new Object[0]);
            if (this.mAppListAdapter.isEditMode()) {
                return false;
            }
            enterEdit(pressedView);
            return true;
        }
        Logger.t(TAG).i("tryEnterEdit, not login.", new Object[0]);
        AccountUtils.get().goLoginByService();
        return true;
    }

    private void enterEdit(View pressedView) {
        Logger.t(TAG).i("enterEdit, pressed:" + pressedView, new Object[0]);
        AppListAdapter appListAdapter = this.mAppListAdapter;
        if (appListAdapter != null && !appListAdapter.isEditMode()) {
            this.mAppListAdapter.setIsEditMode(true);
        }
        if (this.mExitEditTips == null) {
            this.mExitEditTips = this.mRootLayout.findViewById(R.id.exit_edit_tips);
        }
        View view = this.mExitEditTips;
        if (view != null) {
            view.startAnimation(this.mShowAnimation);
        }
        if (pressedView != null) {
            tryStartDrag(this.mRecyclerView.getChildViewHolder(pressedView));
        }
        Logger.t(TAG_IDLE_DETECT).i("onAppLongClick, enable idle detect.", new Object[0]);
        startSpaceClickDetect();
        AppListRootViewCallback appListRootViewCallback = this.mCallback;
        if (appListRootViewCallback != null) {
            appListRootViewCallback.onEnterEdit(pressedView);
        }
        this.enterEdit = System.currentTimeMillis();
    }

    public void recyclerViewReset() {
        XRecyclerView xRecyclerView = this.mRecyclerView;
        if (xRecyclerView != null) {
            xRecyclerView.scrollToPosition(0);
        }
    }

    public View getRootView() {
        return this.mRootLayout;
    }

    public XRecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    public void exitEdit() {
        stopSpaceClickDetect();
        PanelDialog panelDialog = this.mUninstallDialog;
        if (panelDialog != null) {
            panelDialog.dismiss();
        }
        AppListAdapter appListAdapter = this.mAppListAdapter;
        if (appListAdapter != null && appListAdapter.isEditMode()) {
            Logger.t(TAG_IDLE_DETECT).i("exit edit, stopping idle detect.", new Object[0]);
            this.mAppListAdapter.setIsEditMode(false);
            AppListRootViewCallback appListRootViewCallback = this.mCallback;
            if (appListRootViewCallback != null) {
                appListRootViewCallback.onExitEdit();
            }
        }
        View view = this.mExitEditTips;
        if (view != null) {
            view.setVisibility(8);
        }
        if (this.enterEdit > 0) {
            long currentTimeMillis = System.currentTimeMillis();
            final ArrayList arrayList = new ArrayList();
            this.mAppListAdapter.getCurrentList().forEach(new Consumer() { // from class: com.xiaopeng.appstore.applist_ui.view.-$$Lambda$AppListRootView$fcsTAcHFvPHWRMrynJiMEq0t_Xc
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AppListRootView.lambda$exitEdit$0(arrayList, (AppGroupItemModel) obj);
                }
            });
            EventTrackingHelper.sendMolecast(PagesEnum.STORE_MAIN_APP_LIST, EventEnum.APP_LIST_EDIT, arrayList.toString(), Long.valueOf(this.enterEdit), Long.valueOf(currentTimeMillis));
        }
        this.enterEdit = -1L;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$exitEdit$0(List list, AppGroupItemModel appGroupItemModel) {
        if (appGroupItemModel.type == 100) {
            list.add(((BaseAppItem) appGroupItemModel.data).title.toString());
        }
    }

    private void stopSpaceClickDetect() {
        this.mRecyclerView.setOnTouchListener(null);
        this.mRootLayout.setOnTouchListener(null);
    }

    private void startSpaceClickDetect() {
        if (this.mClickDetector == null) {
            this.mClickDetector = new GestureDetector(this.context, new GestureDetector.SimpleOnGestureListener() { // from class: com.xiaopeng.appstore.applist_ui.view.AppListRootView.3
                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public boolean onSingleTapUp(MotionEvent e) {
                    AppListRootView.this.exitEdit();
                    return true;
                }
            });
        }
        this.mRecyclerView.setOnTouchListener(this);
        this.mRootLayout.setOnTouchListener(this);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v, MotionEvent event) {
        GestureDetector gestureDetector;
        if ((!(v instanceof RecyclerView) || this.mRecyclerView.findChildViewUnder(event.getX(), event.getY()) == null) && (gestureDetector = this.mClickDetector) != null) {
            return gestureDetector.onTouchEvent(event);
        }
        return false;
    }

    public void destroy(int hashCode) {
        if (this.mContainerHashCode == hashCode) {
            Logger.t(TAG).i(hashCode() + " destroy mContainerHashCode == hashCode " + hashCode, new Object[0]);
            release();
            AnimationUtils.cancelAnimation(this.mShowAnimation);
            setCallback(null);
            this.context = null;
            this.mContainerHashCode = 0;
            return;
        }
        Logger.t(TAG).w(hashCode() + " destroy mContainerHashCode != hashCode " + hashCode + ", current:" + this.mContainerHashCode, new Object[0]);
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.OnAppInteractCallback
    public boolean onAppIconClick(AppIconViewHolder viewHolder, BaseAppItem appItem) {
        if (this.mAppListAdapter.isEditMode() || viewHolder.isDeleting()) {
            Logger.t(TAG).i("AppIconClick ignored, isEdit=" + this.mAppListAdapter.isEditMode() + " isDel=" + viewHolder.isDeleting() + " app=" + appItem, new Object[0]);
            return false;
        }
        if (appItem instanceof NormalAppItem) {
            Logger.t(TAG).i("AppIconClick, launch app=>" + appItem.getKey(), new Object[0]);
            NormalAppItem normalAppItem = (NormalAppItem) appItem;
            startActivity(viewHolder.getIconView(), normalAppItem);
            if (Arrays.asList(DeviceUtils.isInter() ? Constants.XP_INTER_APP_LIST : Constants.XP_APP_LIST).contains(normalAppItem.packageName)) {
                EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_LIST, EventEnum.OPEN_XP_APP_ENTRANCE, normalAppItem.packageName, normalAppItem.title);
            } else {
                EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_LIST, EventEnum.OPEN_APP_FROM_APP_LIST, 3, normalAppItem.packageName, normalAppItem.title);
            }
            AppListUtils.removeNewAppFlag(normalAppItem.packageName);
            viewHolder.refreshNameIndicator(appItem);
            viewHolder.setUnreadCount(0);
        } else if (appItem instanceof NapaAppItem) {
            ((NapaAppItem) appItem).startActivity();
            EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_LIST, EventEnum.OPEN_XP_APP_ENTRANCE, appItem.packageName, appItem.title);
        } else if (appItem instanceof FixedAppItem) {
            if (AppStoreAssembleManager.get().isRunning(appItem.packageName)) {
                Logger.t(TAG).i("AppIconClick, app is assembling, ignore clicking. download app=>" + appItem.packageName, new Object[0]);
                return false;
            }
            Logger.t(TAG).i("AppIconClick, download app=>" + appItem.packageName, new Object[0]);
            final FixedAppItem fixedAppItem = (FixedAppItem) appItem;
            final String downloadUrl = fixedAppItem.getDownloadUrl();
            final int state = AppStoreAssembleManager.get().getState(fixedAppItem.packageName, downloadUrl, fixedAppItem.getConfigUrl());
            AgreementManager.get().tryExecute(this.context, state, new Runnable() { // from class: com.xiaopeng.appstore.applist_ui.view.-$$Lambda$AppListRootView$Z8pW1e6VaQ_F-4ZlP7sdENoVBkY
                @Override // java.lang.Runnable
                public final void run() {
                    AppListRootView.this.lambda$onAppIconClick$2$AppListRootView(fixedAppItem, state, downloadUrl);
                }
            }, true);
            EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_LIST, EventEnum.OPEN_APP_FROM_APP_LIST, Integer.valueOf(LogicUtils.convertAppOperationStateCancel(state)), fixedAppItem.packageName, fixedAppItem.title.toString());
            return true;
        } else {
            Logger.t(TAG).i("AppIconClick ignored, app=" + appItem, new Object[0]);
        }
        return false;
    }

    public /* synthetic */ void lambda$onAppIconClick$2$AppListRootView(final FixedAppItem fixedAppItem, int i, final String str) {
        LogicUIUtils.tryExecuteSuspendApp(fixedAppItem.packageName, i, this.context, fixedAppItem.isSuspended(), new Runnable() { // from class: com.xiaopeng.appstore.applist_ui.view.-$$Lambda$AppListRootView$2zoVsk30_4sjM9rNwH3HLyQzUHk
            @Override // java.lang.Runnable
            public final void run() {
                AppStoreAssembleManager.get().startWithConfig(str, r1.getMd5(), r1.getConfigUrl(), r1.getConfigMd5(), r1.packageName, r1.title.toString(), fixedAppItem.getIconUrl());
            }
        });
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.OnAppInteractCallback
    public boolean onAppIconLongClick(View v, BaseAppItem info) {
        return tryEnterEdit(v);
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.OnAppInteractCallback
    public void onAppDeleteClick(BaseAppItem appItem) {
        if (this.mUninstallDialog == null && this.context != null) {
            this.mUninstallDialog = new PanelDialog(this.context);
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
        AppListRootViewCallback appListRootViewCallback = this.mCallback;
        if (appListRootViewCallback != null) {
            appListRootViewCallback.onIconMove(fromPos, toPos);
        }
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.OnAppInteractCallback
    public void onIconDrop(RecyclerView.ViewHolder viewHolder, int fromPos, int toPos) {
        AppListRootViewCallback appListRootViewCallback = this.mCallback;
        if (appListRootViewCallback != null) {
            appListRootViewCallback.onIconDrop(fromPos, toPos);
        }
    }

    private void tryStartDrag(RecyclerView.ViewHolder viewHolder) {
        this.mAppListAdapter.tryStartDrag(viewHolder);
    }

    private void startActivity(AppIconView iconView, NormalAppItem info) {
        iconView.setStayPressed(true);
        if (this.context != null) {
            LaunchParam launchParam = new LaunchParam(info.intent, info.user, (String) info.title);
            LaunchResult launch = OpenAppMgr.get().launch(this.context, launchParam);
            Logger.t(TAG).i("lunch app:" + launchParam + ", result:" + launch, new Object[0]);
            if (launch.isSuccess()) {
                AppListRootViewCallback appListRootViewCallback = this.mCallback;
                if (appListRootViewCallback != null) {
                    appListRootViewCallback.onActivityStart(info);
                }
                setOnLauncherResumeCallback(iconView);
                return;
            }
        }
        iconView.setStayPressed(false);
    }

    public void changeTheme() {
        PanelDialog panelDialog = this.mUninstallDialog;
        if (panelDialog == null || panelDialog.getDialog() == null || this.mUninstallDialog.getDialog().getWindow() == null) {
            return;
        }
        this.mUninstallDialog.getDialog().getWindow().setBackgroundDrawableResource(R.drawable.x_bg_dialog);
    }

    public void resume() {
        setOnLauncherResumeCallback(null);
        tryScrollToLastInstalledItem();
    }

    public void setOnLauncherResumeCallback(OnLauncherResumeCallback callback) {
        OnLauncherResumeCallback onLauncherResumeCallback = this.mOnLauncherResumeCallback;
        if (onLauncherResumeCallback != null) {
            onLauncherResumeCallback.onLauncherResume();
        }
        this.mOnLauncherResumeCallback = callback;
    }

    public void pause() {
        this.mScrolled = false;
        this.mMainHandler.removeCallbacksAndMessages(null);
        exitEdit();
    }

    @Override // com.xiaopeng.appstore.common_ui.NotificationObserverManager.Launcher
    public void updateIconBadges(Set<PackageUserKey> updatedBadges) {
        refreshUnreadCount(updatedBadges);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void refreshUnreadCount(Set<PackageUserKey> updatedBadges) {
        if (this.mAppListAdapter != null) {
            PayloadData payloadData = new PayloadData();
            payloadData.type = 5;
            payloadData.data = updatedBadges;
            Logger.t("AppListRootView_notify").i("notifyItemRangeChanged refreshUnreadCount" + updatedBadges, new Object[0]);
            AppListAdapter appListAdapter = this.mAppListAdapter;
            appListAdapter.notifyItemRangeChanged(0, appListAdapter.getItemCount(), payloadData);
        }
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
        AppListAdapter appListAdapter = this.mAppListAdapter;
        if (appListAdapter == null) {
            Logger.t(TAG).i("tryScrollToLastInstalledItem, adapter not init", new Object[0]);
            return;
        }
        final int index = appListAdapter.getIndex(lastInstalledApp);
        Logger.t(TAG).i("tryScrollToLastInstalledItem, index=" + index, new Object[0]);
        if (index <= -1 || index >= this.mAppListAdapter.getItemCount()) {
            return;
        }
        this.mMainHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.appstore.applist_ui.view.-$$Lambda$AppListRootView$icRrY3wHE8Tvv7WzkNPQHgXnGO4
            @Override // java.lang.Runnable
            public final void run() {
                AppListRootView.this.lambda$tryScrollToLastInstalledItem$3$AppListRootView(index);
            }
        }, 300L);
        LogicUtils.setLastInstalledApp("");
    }

    public /* synthetic */ void lambda$tryScrollToLastInstalledItem$3$AppListRootView(int i) {
        Logger.t(TAG).i("smoothScrollToPosition, index=" + i, new Object[0]);
        this.mRecyclerView.smoothScrollToPosition(i);
        this.mScrolled = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
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
            AppStoreAssembleManager.get().delete(packageName, userHandle, name, new Consumer() { // from class: com.xiaopeng.appstore.applist_ui.view.-$$Lambda$AppListRootView$UninstallDialogClickListener$XS5Jp36_sIlxeRGkvQEQOIm9QII
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AppListRootView.UninstallDialogClickListener.this.toastError((String) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void toastError(final String msg) {
            AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.applist_ui.view.-$$Lambda$AppListRootView$UninstallDialogClickListener$A-jmKgJKXgbEBGfjshWYr-ICDwc
                @Override // java.lang.Runnable
                public final void run() {
                    XToast.show(msg, 0, XuiMgrHelper.get().getShareId());
                }
            });
        }
    }
}
