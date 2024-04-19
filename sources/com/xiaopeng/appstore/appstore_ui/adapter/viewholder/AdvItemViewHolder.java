package com.xiaopeng.appstore.appstore_ui.adapter.viewholder;

import android.os.UserHandle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import androidx.databinding.ViewDataBinding;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.datamodel.api.XpApiClient;
import com.xiaopeng.appstore.appstore_biz.datamodel.api.XpAppService;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailData;
import com.xiaopeng.appstore.appstore_biz.logic.CheckUpdateManager;
import com.xiaopeng.appstore.appstore_biz.logic.ItemLogicHelper;
import com.xiaopeng.appstore.appstore_biz.model.AppUIModel;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvAppModel;
import com.xiaopeng.appstore.appstore_ui.BR;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder;
import com.xiaopeng.appstore.appstore_ui.common.BindingHelper;
import com.xiaopeng.appstore.bizcommon.entities.AppInfo;
import com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.ItemAssembleObserver;
import com.xiaopeng.appstore.bizcommon.logic.SimpleItemAssembleObserver;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.common_ui.OpenAppMgr;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.xui.widget.XCircularProgressBar;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
/* loaded from: classes2.dex */
public class AdvItemViewHolder<T extends ViewDataBinding> extends BaseBoundViewHolder<AdvAppModel, T> {
    private static final String TAG = "AdvItemVH";
    private final AppUIModel mAppUIModel;
    private final Set<String> mBoundPackages;
    private final ItemAssembleObserver mItemAssembleObserver;
    private AdvAppModel mItemModel;
    private final AppComponentManager.OnAppOpenCallback mOnAppOpenCallback;
    private final AppComponentManager.OnAppsChangedCallback mOnAppsChangedCallback;
    private int mProgress;
    private XCircularProgressBar mProgressBar;
    protected final ViewStub mProgressBarViewStub;
    private int mState;
    private final CheckUpdateManager.CheckUpdateListener mUpdateListener;
    private XpAppService mXpAppService;

    /* loaded from: classes2.dex */
    public interface OnItemEventCallback {
        void onBtnClick(View btn, int position, AdvAppModel model);

        void onItemClick(View view, int position, AdvAppModel model);
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public /* bridge */ /* synthetic */ void setData(AdvAppModel data, List payloads) {
        setData2(data, (List<Object>) payloads);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder$3  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass3 extends SimpleItemAssembleObserver {
        AnonymousClass3() {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.SimpleItemAssembleObserver, com.xiaopeng.appstore.bizcommon.logic.ItemAssembleObserver
        public void onStateChange(final AssembleDataContainer data) {
            if (AdvItemViewHolder.this.isCurrentApp(data)) {
                if (data.getState() == 1) {
                    Observable.just("").observeOn(Schedulers.io()).subscribe(new Consumer() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.viewholder.-$$Lambda$AdvItemViewHolder$3$xMZmU7rE72cKefCcKgfCV8Lpq_g
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            AdvItemViewHolder.AnonymousClass3.this.lambda$onStateChange$0$AdvItemViewHolder$3((String) obj);
                        }
                    });
                } else if (data.getState() == 7) {
                    Logger.t(AdvItemViewHolder.TAG).i("onStateChange ignore APP_STATE_DOWNLOAD_SUCCEED", new Object[0]);
                    return;
                }
                AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.viewholder.-$$Lambda$AdvItemViewHolder$3$p0jSyqWWooSeWdvJvTFN0tyrYxA
                    @Override // java.lang.Runnable
                    public final void run() {
                        AdvItemViewHolder.AnonymousClass3.this.lambda$onStateChange$1$AdvItemViewHolder$3(data);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onStateChange$0$AdvItemViewHolder$3(String str) throws Exception {
            XpAppService.downloadSubmit(AdvItemViewHolder.this.getXpAppService(), AdvItemViewHolder.this.mItemModel.getPackageName(), AdvItemViewHolder.this.mItemModel.getVersionName(), AdvItemViewHolder.this.mItemModel.getVersionCode());
        }

        /* JADX WARN: Type inference failed for: r5v4, types: [androidx.databinding.ViewDataBinding] */
        /* JADX WARN: Type inference failed for: r5v6, types: [androidx.databinding.ViewDataBinding] */
        /* JADX WARN: Type inference failed for: r5v9, types: [androidx.databinding.ViewDataBinding] */
        public /* synthetic */ void lambda$onStateChange$1$AdvItemViewHolder$3(AssembleDataContainer assembleDataContainer) {
            if (assembleDataContainer == null) {
                return;
            }
            int state = assembleDataContainer.getState();
            AdvItemViewHolder.this.mState = state;
            Logger.t(AdvItemViewHolder.TAG).d("onStateChange=" + AppStoreAssembleManager.stateToStr(state) + " model=" + assembleDataContainer.getPackageName() + " pos=" + AdvItemViewHolder.this.getAdapterPosition());
            AdvItemViewHolder advItemViewHolder = AdvItemViewHolder.this;
            advItemViewHolder.mProgress = advItemViewHolder.parseProgress(assembleDataContainer.getProgress());
            AdvItemViewHolder.this.getBinding().setVariable(BR.progress, Integer.valueOf(AdvItemViewHolder.this.mProgress));
            AdvItemViewHolder.this.getBinding().setVariable(BR.state, Integer.valueOf(state));
            AdvItemViewHolder advItemViewHolder2 = AdvItemViewHolder.this;
            advItemViewHolder2.refreshProgressBar(state, advItemViewHolder2.mProgress);
            AdvItemViewHolder.this.getBinding().executePendingBindings();
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.SimpleItemAssembleObserver, com.xiaopeng.appstore.bizcommon.logic.ItemAssembleObserver
        public void onProgressChange(AssembleDataContainer data) {
            AdvItemViewHolder advItemViewHolder = AdvItemViewHolder.this;
            advItemViewHolder.applyProgress(advItemViewHolder.parseProgress(data.getProgress()));
        }
    }

    public /* synthetic */ void lambda$new$0$AdvItemViewHolder(List list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (((AppDetailData) it.next()).getPackageName().equals(this.mItemModel.getPackageName())) {
                refreshState();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v6, types: [androidx.databinding.ViewDataBinding] */
    public AdvItemViewHolder(ViewGroup parent, int layoutId, OnItemEventCallback itemEventCallback) {
        super(parent, layoutId);
        this.mOnAppsChangedCallback = new AppComponentManager.SimpleOnAppsChangedCallback() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder.1
            @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
            public void onPackageRemoved(String packageName, UserHandle user) {
                if (AdvItemViewHolder.this.mItemModel.getPackageName().equals(packageName)) {
                    AdvItemViewHolder.this.refreshState();
                }
            }

            @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
            public void onPackageAdded(List<AppInfo> appInfoList) {
                if (appInfoList == null || appInfoList.isEmpty()) {
                    return;
                }
                for (AppInfo appInfo : appInfoList) {
                    String packageName = appInfo.componentName.getPackageName();
                    if (packageName.equals(AdvItemViewHolder.this.mItemModel.getPackageName())) {
                        Logger.t(AdvItemViewHolder.TAG).d("onPackageAdded pn=" + packageName + " pos=" + AdvItemViewHolder.this.getAdapterPosition());
                        AdvItemViewHolder.this.refreshState();
                    }
                }
            }

            @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
            public void onPackageChanged(String packageName, UserHandle user) {
                if (packageName.equals(AdvItemViewHolder.this.mItemModel.getPackageName())) {
                    Logger.t(AdvItemViewHolder.TAG).d("onPackageChanged pn=" + packageName + " pos=" + AdvItemViewHolder.this.getAdapterPosition());
                    AdvItemViewHolder.this.refreshState();
                }
            }
        };
        this.mOnAppOpenCallback = new AppComponentManager.OnAppOpenCallback() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder.2
            @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppOpenCallback
            public void onAppOpen(String packageName) {
                if (AdvItemViewHolder.this.isCurrentApp(packageName)) {
                    AdvItemViewHolder.this.applyState(5);
                }
            }

            @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppOpenCallback
            public void onAppOpenFinish(String packageName) {
                if (AdvItemViewHolder.this.isCurrentApp(packageName)) {
                    AdvItemViewHolder.this.refreshState();
                }
            }
        };
        this.mItemAssembleObserver = new AnonymousClass3();
        this.mUpdateListener = new CheckUpdateManager.CheckUpdateListener() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.viewholder.-$$Lambda$AdvItemViewHolder$8K83wJdcyCyCJh1yLngbbjcysoY
            @Override // com.xiaopeng.appstore.appstore_biz.logic.CheckUpdateManager.CheckUpdateListener
            public final void onUpdate(List list) {
                AdvItemViewHolder.this.lambda$new$0$AdvItemViewHolder(list);
            }
        };
        this.mBoundPackages = new HashSet();
        getBinding().setVariable(BR.callback, itemEventCallback);
        this.mAppUIModel = new AppUIModel();
        this.mProgressBarViewStub = (ViewStub) this.itemView.findViewById(R.id.progress_bar_view_stub);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    /* JADX WARN: Type inference failed for: r2v2, types: [androidx.databinding.ViewDataBinding] */
    /* JADX WARN: Type inference failed for: r5v1, types: [androidx.databinding.ViewDataBinding] */
    /* JADX WARN: Type inference failed for: r5v3, types: [androidx.databinding.ViewDataBinding] */
    /* JADX WARN: Type inference failed for: r5v4, types: [androidx.databinding.ViewDataBinding] */
    /* JADX WARN: Type inference failed for: r5v6, types: [androidx.databinding.ViewDataBinding] */
    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void setData(AdvAppModel model) {
        if (TextUtils.isEmpty(model.getPackageName())) {
            Logger.t(TAG).e("setData: invalid data since packageName is empty:" + model, new Object[0]);
            return;
        }
        this.mItemModel = model;
        addCallbacks(model.getPackageName());
        int state = AppStoreAssembleManager.get().getState(model.getPackageName(), model.getDownloadUrl(), model.getConfigUrl());
        float progress = AppStoreAssembleManager.get().getProgress(model.getPackageName(), model.getDownloadUrl());
        getBinding().setVariable(BR.model, model);
        getBinding().setVariable(BR.position, Integer.valueOf(getAdapterPosition()));
        this.mProgress = parseProgress(progress);
        this.mState = state;
        getBinding().setVariable(BR.state, Integer.valueOf(state));
        getBinding().setVariable(BR.progress, Integer.valueOf(this.mProgress));
        refreshProgressBar(state, this.mProgress);
        refreshUIModel();
        getBinding().executePendingBindings();
    }

    /* renamed from: setData  reason: avoid collision after fix types in other method */
    public void setData2(AdvAppModel data, List<Object> payloads) {
        super.setData((AdvItemViewHolder<T>) data, payloads);
        if (payloads.isEmpty()) {
            return;
        }
        Object obj = payloads.get(0);
        if (obj instanceof PayloadData) {
            PayloadData payloadData = (PayloadData) obj;
            if (payloadData.type == 1003) {
                onThemeChanged();
            } else if (payloadData.type == 1001) {
                refreshState();
            } else if (payloadData.type == 1004) {
                setData(this.mItemModel);
            } else {
                int i = payloadData.type;
            }
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void onViewRecycled() {
        super.onViewRecycled();
        removeCallbacks();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public XpAppService getXpAppService() {
        if (this.mXpAppService == null) {
            this.mXpAppService = XpApiClient.getAppService();
        }
        return this.mXpAppService;
    }

    private void addCallbacks(String pn) {
        if (!this.mBoundPackages.isEmpty()) {
            Logger.t(TAG).w("Already bind, but not recycled:" + this.mBoundPackages, new Object[0]);
            Iterator<String> it = this.mBoundPackages.iterator();
            while (it.hasNext()) {
                it.remove();
                AppStoreAssembleManager.get().removeSoftObserver(it.next(), this.mItemAssembleObserver);
            }
        }
        this.mBoundPackages.add(pn);
        AppStoreAssembleManager.get().addSoftObserver(pn, this.mItemAssembleObserver);
        AppComponentManager.get().removeOnAppsChangedSoftCallback(this.mOnAppsChangedCallback);
        AppComponentManager.get().addOnAppsChangedSoftCallback(this.mOnAppsChangedCallback);
        OpenAppMgr.get().removeOnAppOpenCallback(this.mOnAppOpenCallback);
        OpenAppMgr.get().addOnAppOpenCallback(this.mOnAppOpenCallback);
        CheckUpdateManager.get().removeSoftListener(this.mUpdateListener);
        CheckUpdateManager.get().addSoftListener(this.mUpdateListener);
    }

    private void removeCallbacks() {
        Iterator<String> it = this.mBoundPackages.iterator();
        while (it.hasNext()) {
            String next = it.next();
            Logger.t(TAG).d("removeCallbacks, pn:" + next);
            it.remove();
            AppStoreAssembleManager.get().removeSoftObserver(next, this.mItemAssembleObserver);
        }
        AppComponentManager.get().removeOnAppsChangedSoftCallback(this.mOnAppsChangedCallback);
        OpenAppMgr.get().removeOnAppOpenCallback(this.mOnAppOpenCallback);
        CheckUpdateManager.get().removeSoftListener(this.mUpdateListener);
    }

    private void refreshUIModel() {
        this.mAppUIModel.setShadowColor(R.color.card_shadow_color);
        this.mAppUIModel.setCardBgDrawable(R.drawable.x_list_item_selector);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.databinding.ViewDataBinding] */
    private void onThemeChanged() {
        refreshUIModel();
        getBinding().executePendingBindings();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCurrentApp(AssembleDataContainer data) {
        AdvAppModel advAppModel;
        return (data == null || (advAppModel = this.mItemModel) == null || !advAppModel.getPackageName().equals(data.getPackageName())) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCurrentApp(String packageName) {
        AdvAppModel advAppModel;
        return (TextUtils.isEmpty(packageName) || (advAppModel = this.mItemModel) == null || !advAppModel.getPackageName().equals(packageName)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshState() {
        AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.viewholder.-$$Lambda$AdvItemViewHolder$-QxPmnDT4bhcAB-ulMI6SECEW0Q
            @Override // java.lang.Runnable
            public final void run() {
                AdvItemViewHolder.this.lambda$refreshState$1$AdvItemViewHolder();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [androidx.databinding.ViewDataBinding] */
    /* JADX WARN: Type inference failed for: r1v5, types: [androidx.databinding.ViewDataBinding] */
    public /* synthetic */ void lambda$refreshState$1$AdvItemViewHolder() {
        if (this.mItemModel == null) {
            return;
        }
        int state = AppStoreAssembleManager.get().getState(this.mItemModel.getPackageName(), this.mItemModel.getDownloadUrl(), this.mItemModel.getConfigUrl());
        this.mState = state;
        Logger.t(TAG).d("refreshState=" + AppStoreAssembleManager.stateToStr(state) + " model=" + this.mItemModel.getPackageName() + " pos=" + getAdapterPosition());
        getBinding().setVariable(BR.state, Integer.valueOf(state));
        getBinding().executePendingBindings();
        refreshProgressBar(state, this.mProgress);
    }

    private void tryInitProgressBar() {
        ViewStub viewStub = this.mProgressBarViewStub;
        if (viewStub == null || this.mProgressBar != null) {
            return;
        }
        this.mProgressBar = (XCircularProgressBar) viewStub.inflate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshProgressBar(int state, int progress) {
        boolean showProgress = ItemLogicHelper.showProgress(state);
        if (showProgress) {
            tryInitProgressBar();
        }
        XCircularProgressBar xCircularProgressBar = this.mProgressBar;
        if (xCircularProgressBar != null) {
            xCircularProgressBar.setVisibility(showProgress ? 0 : 8);
            this.mProgressBar.setEnabled(ItemLogicHelper.isProgressEnable(state));
            this.mProgressBar.setIndeterminate(ItemLogicHelper.isIndeterminate(progress, state));
            this.mProgressBar.setIndicatorType(BindingHelper.getProgressType(state));
            this.mProgressBar.setProgress(progress);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyState(final int state) {
        AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.viewholder.-$$Lambda$AdvItemViewHolder$69M47xD1eEWp93K43jbmpUBuxqU
            @Override // java.lang.Runnable
            public final void run() {
                AdvItemViewHolder.this.lambda$applyState$2$AdvItemViewHolder(state);
            }
        });
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.databinding.ViewDataBinding] */
    /* JADX WARN: Type inference failed for: r4v2, types: [androidx.databinding.ViewDataBinding] */
    public /* synthetic */ void lambda$applyState$2$AdvItemViewHolder(int i) {
        Logger.t(TAG).d("applyState=" + i + " pn=" + this.mItemModel.getPackageName() + " pos=" + getAdapterPosition());
        getBinding().setVariable(BR.state, Integer.valueOf(i));
        getBinding().executePendingBindings();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyProgress(final int progress) {
        AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.viewholder.-$$Lambda$AdvItemViewHolder$Dy-SgAwq_TnoTHgeK7GogjNAv7Y
            @Override // java.lang.Runnable
            public final void run() {
                AdvItemViewHolder.this.lambda$applyProgress$3$AdvItemViewHolder(progress);
            }
        });
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.databinding.ViewDataBinding] */
    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.databinding.ViewDataBinding] */
    public /* synthetic */ void lambda$applyProgress$3$AdvItemViewHolder(int i) {
        this.mProgress = i;
        Logger.t(TAG).v("applyProgress progress=" + i + " pn=" + this.mItemModel.getPackageName(), new Object[0]);
        getBinding().setVariable(BR.progress, Integer.valueOf(i));
        getBinding().executePendingBindings();
        refreshProgressBar(this.mState, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int parseProgress(float progress) {
        float f = progress * 100.0f;
        return f < 1.0f ? (int) Math.ceil(f) : (int) f;
    }
}
