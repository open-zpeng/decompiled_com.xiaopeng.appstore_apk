package com.xiaopeng.appstore.international.upgrade;

import android.os.UserHandle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.datamodel.api.XpApiClient;
import com.xiaopeng.appstore.appstore_biz.datamodel.api.XpAppService;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailData;
import com.xiaopeng.appstore.appstore_biz.logic.CheckUpdateManager;
import com.xiaopeng.appstore.appstore_biz.model.AppUIModel;
import com.xiaopeng.appstore.appstore_biz.model2.AppBaseModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.bizcommon.entities.AppInfo;
import com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.ItemAssembleObserver;
import com.xiaopeng.appstore.bizcommon.logic.SimpleItemAssembleObserver;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.common_ui.OpenAppMgr;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
import com.xiaopeng.appstore.international.BR;
import com.xiaopeng.appstore.international.upgrade.InternationalAppItemViewHolder;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
/* loaded from: classes.dex */
public class InternationalAppItemViewHolder<T extends ViewDataBinding> extends BaseBoundViewHolder<AppBaseModel, T> {
    private static final String TAG = "AppItemVH";
    private boolean expanded;
    private final AppUIModel mAppUIModel;
    private final Set<String> mBoundPackages;
    private final ItemAssembleObserver mItemAssembleObserver;
    private AppBaseModel mItemModel;
    private final Observer<List<AppDetailData>> mObserver;
    private final AppComponentManager.OnAppOpenCallback mOnAppOpenCallback;
    private final AppComponentManager.OnAppsChangedCallback mOnAppsChangedCallback;
    private XpAppService mXpAppService;

    /* loaded from: classes.dex */
    public interface OnItemEventCallback {
        void onBtnClick(InternationalAppItemViewHolder viewHolder, View btn, int position, AppBaseModel model);

        void onExpandClick(InternationalAppItemViewHolder viewHolder, View view, int position, AppBaseModel model);

        void onItemClick(InternationalAppItemViewHolder viewHolder, View view, int position, AppBaseModel model);

        void onProgressBtnClick(InternationalAppItemViewHolder viewHolder, View btn, int position, AppBaseModel model);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int parseProgress(float progress) {
        return (int) (progress * 100.0f);
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public /* bridge */ /* synthetic */ void setData(AppBaseModel data, List payloads) {
        setData2(data, (List<Object>) payloads);
    }

    public /* synthetic */ void lambda$new$0$InternationalAppItemViewHolder(List list) {
        refreshState();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.international.upgrade.InternationalAppItemViewHolder$3  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass3 extends SimpleItemAssembleObserver {
        AnonymousClass3() {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.SimpleItemAssembleObserver, com.xiaopeng.appstore.bizcommon.logic.ItemAssembleObserver
        public void onStateChange(final AssembleDataContainer data) {
            super.onStateChange(data);
            if (InternationalAppItemViewHolder.this.isCurrentApp(data)) {
                if (data.getState() == 1) {
                    Observable.just("").observeOn(Schedulers.io()).subscribe(new Consumer() { // from class: com.xiaopeng.appstore.international.upgrade.-$$Lambda$InternationalAppItemViewHolder$3$3HLrY38CrAdzO9SXZA2BMLAj5oU
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            InternationalAppItemViewHolder.AnonymousClass3.this.lambda$onStateChange$0$InternationalAppItemViewHolder$3((String) obj);
                        }
                    });
                } else if (data.getState() == 7) {
                    Logger.t(InternationalAppItemViewHolder.TAG).i("onStateChange ignore APP_STATE_DOWNLOAD_SUCCEED", new Object[0]);
                    return;
                }
                AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.international.upgrade.-$$Lambda$InternationalAppItemViewHolder$3$jrMKGQPMkmgTTlKZ1yg9LH4ujWc
                    @Override // java.lang.Runnable
                    public final void run() {
                        InternationalAppItemViewHolder.AnonymousClass3.this.lambda$onStateChange$1$InternationalAppItemViewHolder$3(data);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onStateChange$0$InternationalAppItemViewHolder$3(String str) throws Exception {
            XpAppService.downloadSubmit(InternationalAppItemViewHolder.this.getXpAppService(), InternationalAppItemViewHolder.this.mItemModel.getPackageName(), InternationalAppItemViewHolder.this.mItemModel.getVersionName(), InternationalAppItemViewHolder.this.mItemModel.getVersionCode());
        }

        /* JADX WARN: Type inference failed for: r1v3, types: [androidx.databinding.ViewDataBinding] */
        /* JADX WARN: Type inference failed for: r5v5, types: [androidx.databinding.ViewDataBinding] */
        /* JADX WARN: Type inference failed for: r5v7, types: [androidx.databinding.ViewDataBinding] */
        public /* synthetic */ void lambda$onStateChange$1$InternationalAppItemViewHolder$3(AssembleDataContainer assembleDataContainer) {
            if (assembleDataContainer == null) {
                return;
            }
            int state = assembleDataContainer.getState();
            Logger.t(InternationalAppItemViewHolder.TAG).d("onStateChange=" + AppStoreAssembleManager.stateToStr(state) + " model=" + assembleDataContainer.getPackageName() + " pos=" + InternationalAppItemViewHolder.this.getAdapterPosition());
            InternationalAppItemViewHolder.this.getBinding().setVariable(BR.progress, Integer.valueOf(InternationalAppItemViewHolder.this.parseProgress(assembleDataContainer.getProgress())));
            InternationalAppItemViewHolder.this.getBinding().setVariable(BR.state, Integer.valueOf(state));
            InternationalAppItemViewHolder.this.getBinding().executePendingBindings();
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.SimpleItemAssembleObserver, com.xiaopeng.appstore.bizcommon.logic.ItemAssembleObserver
        public void onProgressChange(AssembleDataContainer data) {
            super.onProgressChange(data);
            InternationalAppItemViewHolder internationalAppItemViewHolder = InternationalAppItemViewHolder.this;
            internationalAppItemViewHolder.applyProgress(internationalAppItemViewHolder.parseProgress(data.getProgress()));
        }
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [androidx.databinding.ViewDataBinding] */
    /* JADX WARN: Type inference failed for: r1v8, types: [androidx.databinding.ViewDataBinding] */
    public InternationalAppItemViewHolder(ViewGroup parent, int layoutId, OnItemEventCallback itemEventCallback) {
        super(parent, layoutId);
        this.mBoundPackages = new HashSet();
        this.expanded = false;
        this.mOnAppsChangedCallback = new AppComponentManager.SimpleOnAppsChangedCallback() { // from class: com.xiaopeng.appstore.international.upgrade.InternationalAppItemViewHolder.1
            @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
            public void onPackageChanged(String packageName, UserHandle user) {
            }

            @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
            public void onPackageRemoved(String packageName, UserHandle user) {
                if (InternationalAppItemViewHolder.this.mItemModel.getPackageName().equals(packageName)) {
                    InternationalAppItemViewHolder.this.refreshState();
                }
            }

            @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
            public void onPackageAdded(List<AppInfo> appInfoList) {
                if (appInfoList == null || appInfoList.isEmpty()) {
                    return;
                }
                for (AppInfo appInfo : appInfoList) {
                    if (appInfo.componentName.getPackageName().equals(InternationalAppItemViewHolder.this.mItemModel.getPackageName())) {
                        InternationalAppItemViewHolder.this.refreshState();
                    }
                }
            }
        };
        this.mOnAppOpenCallback = new AppComponentManager.OnAppOpenCallback() { // from class: com.xiaopeng.appstore.international.upgrade.InternationalAppItemViewHolder.2
            @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppOpenCallback
            public void onAppOpen(String packageName) {
                if (InternationalAppItemViewHolder.this.isCurrentApp(packageName)) {
                    InternationalAppItemViewHolder.this.applyState(5);
                }
            }

            @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppOpenCallback
            public void onAppOpenFinish(String packageName) {
                if (InternationalAppItemViewHolder.this.isCurrentApp(packageName)) {
                    InternationalAppItemViewHolder.this.refreshState();
                }
            }
        };
        this.mObserver = new Observer() { // from class: com.xiaopeng.appstore.international.upgrade.-$$Lambda$InternationalAppItemViewHolder$I4GMqx_KHZGocKu5KxMvU68OMAs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                InternationalAppItemViewHolder.this.lambda$new$0$InternationalAppItemViewHolder((List) obj);
            }
        };
        this.mItemAssembleObserver = new AnonymousClass3();
        getBinding().setVariable(BR.callback, itemEventCallback);
        getBinding().setVariable(BR.viewHolder, this);
        this.mAppUIModel = new AppUIModel();
    }

    /* JADX WARN: Type inference failed for: r4v2, types: [androidx.databinding.ViewDataBinding] */
    /* JADX WARN: Type inference failed for: r7v1, types: [androidx.databinding.ViewDataBinding] */
    /* JADX WARN: Type inference failed for: r7v2, types: [androidx.databinding.ViewDataBinding] */
    /* JADX WARN: Type inference failed for: r7v3, types: [androidx.databinding.ViewDataBinding] */
    /* JADX WARN: Type inference failed for: r7v4, types: [androidx.databinding.ViewDataBinding] */
    /* JADX WARN: Type inference failed for: r7v5, types: [androidx.databinding.ViewDataBinding] */
    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void setData(AppBaseModel model) {
        boolean z = model != this.mItemModel;
        this.mItemModel = model;
        int state = AppStoreAssembleManager.get().getState(model.getPackageName(), model.getDownloadUrl(), model.getConfigUrl());
        float progress = AppStoreAssembleManager.get().getProgress(model.getPackageName(), model.getDownloadUrl());
        getBinding().setVariable(BR.model, model);
        getBinding().setVariable(BR.position, Integer.valueOf(getAdapterPosition()));
        getBinding().setVariable(BR.state, Integer.valueOf(state));
        getBinding().setVariable(BR.progress, Integer.valueOf(parseProgress(progress)));
        if (z) {
            this.expanded = false;
            getBinding().setVariable(BR.expanded, false);
        }
        refreshUIModel();
        getBinding().executePendingBindings();
    }

    /* renamed from: setData  reason: avoid collision after fix types in other method */
    public void setData2(AppBaseModel data, List<Object> payloads) {
        super.setData((InternationalAppItemViewHolder<T>) data, payloads);
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
            }
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void onViewRecycled() {
        super.onViewRecycled();
        AppStoreAssembleManager.get().removeSoftObserver(this.mItemModel.getPackageName(), this.mItemAssembleObserver);
        AppComponentManager.get().removeOnAppsChangedSoftCallback(this.mOnAppsChangedCallback);
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void markAttached() {
        super.markAttached();
        String packageName = this.mItemModel.getPackageName();
        if (!this.mBoundPackages.isEmpty()) {
            Logger.t(TAG).w("Already attach, but not detached:" + this.mBoundPackages, new Object[0]);
            Iterator<String> it = this.mBoundPackages.iterator();
            while (it.hasNext()) {
                it.remove();
                AppStoreAssembleManager.get().removeSoftObserver(it.next(), this.mItemAssembleObserver);
            }
        }
        this.mBoundPackages.add(packageName);
        AppStoreAssembleManager.get().removeSoftObserver(packageName, this.mItemAssembleObserver);
        AppStoreAssembleManager.get().addSoftObserver(packageName, this.mItemAssembleObserver);
        AppComponentManager.get().removeOnAppsChangedSoftCallback(this.mOnAppsChangedCallback);
        AppComponentManager.get().addOnAppsChangedSoftCallback(this.mOnAppsChangedCallback);
        OpenAppMgr.get().removeOnAppOpenCallback(this.mOnAppOpenCallback);
        OpenAppMgr.get().addOnAppOpenCallback(this.mOnAppOpenCallback);
        CheckUpdateManager.get().getUpdateListLive().observeForever(this.mObserver);
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void markDetached() {
        super.markDetached();
        Iterator<String> it = this.mBoundPackages.iterator();
        while (it.hasNext()) {
            String next = it.next();
            Logger.t(TAG).d("removeCallbacks, pn:" + next);
            it.remove();
            AppStoreAssembleManager.get().removeSoftObserver(next, this.mItemAssembleObserver);
        }
        AppStoreAssembleManager.get().removeSoftObserver(this.mItemModel.getPackageName(), this.mItemAssembleObserver);
        AppComponentManager.get().removeOnAppsChangedSoftCallback(this.mOnAppsChangedCallback);
        OpenAppMgr.get().removeOnAppOpenCallback(this.mOnAppOpenCallback);
        CheckUpdateManager.get().getUpdateListLive().removeObserver(this.mObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public XpAppService getXpAppService() {
        if (this.mXpAppService == null) {
            this.mXpAppService = XpApiClient.getAppService();
        }
        return this.mXpAppService;
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
        AppBaseModel appBaseModel;
        return (data == null || (appBaseModel = this.mItemModel) == null || !appBaseModel.getPackageName().equals(data.getPackageName())) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCurrentApp(String packageName) {
        AppBaseModel appBaseModel;
        return (TextUtils.isEmpty(packageName) || (appBaseModel = this.mItemModel) == null || !appBaseModel.getPackageName().equals(packageName)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshState() {
        AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.international.upgrade.-$$Lambda$InternationalAppItemViewHolder$zuCBjHXr4DPxD53u7RASxsVaeYw
            @Override // java.lang.Runnable
            public final void run() {
                InternationalAppItemViewHolder.this.lambda$refreshState$1$InternationalAppItemViewHolder();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [androidx.databinding.ViewDataBinding] */
    /* JADX WARN: Type inference failed for: r1v4, types: [androidx.databinding.ViewDataBinding] */
    public /* synthetic */ void lambda$refreshState$1$InternationalAppItemViewHolder() {
        if (this.mItemModel == null) {
            return;
        }
        int state = AppStoreAssembleManager.get().getState(this.mItemModel.getPackageName(), this.mItemModel.getDownloadUrl(), this.mItemModel.getConfigUrl());
        Logger.t(TAG).d("refreshState=" + state + " model=" + this.mItemModel.getPackageName() + " pos=" + getAdapterPosition());
        getBinding().setVariable(BR.state, Integer.valueOf(state));
        getBinding().executePendingBindings();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyState(final int state) {
        AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.international.upgrade.-$$Lambda$InternationalAppItemViewHolder$3v-ZV1PZn38Of_wT6C2clbXNDPE
            @Override // java.lang.Runnable
            public final void run() {
                InternationalAppItemViewHolder.this.lambda$applyState$2$InternationalAppItemViewHolder(state);
            }
        });
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.databinding.ViewDataBinding] */
    /* JADX WARN: Type inference failed for: r3v2, types: [androidx.databinding.ViewDataBinding] */
    public /* synthetic */ void lambda$applyState$2$InternationalAppItemViewHolder(int i) {
        getBinding().setVariable(BR.state, Integer.valueOf(i));
        getBinding().executePendingBindings();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyProgress(final int progress) {
        AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.international.upgrade.-$$Lambda$InternationalAppItemViewHolder$J7DGzi14A9wG0dG4_3imgbIdxtw
            @Override // java.lang.Runnable
            public final void run() {
                InternationalAppItemViewHolder.this.lambda$applyProgress$3$InternationalAppItemViewHolder(progress);
            }
        });
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.databinding.ViewDataBinding] */
    /* JADX WARN: Type inference failed for: r3v2, types: [androidx.databinding.ViewDataBinding] */
    public /* synthetic */ void lambda$applyProgress$3$InternationalAppItemViewHolder(int i) {
        getBinding().setVariable(BR.progress, Integer.valueOf(i));
        getBinding().executePendingBindings();
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.databinding.ViewDataBinding] */
    public void toggleExpand() {
        this.expanded = !this.expanded;
        getBinding().setVariable(BR.expanded, Boolean.valueOf(this.expanded));
    }
}
