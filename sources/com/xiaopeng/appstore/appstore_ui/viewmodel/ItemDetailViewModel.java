package com.xiaopeng.appstore.appstore_ui.viewmodel;

import android.os.UserHandle;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.datamodel.api.XpApiClient;
import com.xiaopeng.appstore.appstore_biz.datamodel.api.XpAppService;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailData;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailListRequest;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailRequest;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppRequestContainer;
import com.xiaopeng.appstore.appstore_biz.model2.AppDetailModel;
import com.xiaopeng.appstore.appstore_biz.parser.ItemDetailParser;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.common.BindingHelper;
import com.xiaopeng.appstore.bizcommon.entities.AppInfo;
import com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer;
import com.xiaopeng.appstore.bizcommon.entities.common.ApiEmptyResponse;
import com.xiaopeng.appstore.bizcommon.entities.common.ApiErrorResponse;
import com.xiaopeng.appstore.bizcommon.entities.common.ApiResponse;
import com.xiaopeng.appstore.bizcommon.entities.common.ApiSuccessResponse;
import com.xiaopeng.appstore.bizcommon.entities.common.XpApiResponse;
import com.xiaopeng.appstore.bizcommon.http.ApiHelper;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.ItemAssembleObserver;
import com.xiaopeng.appstore.bizcommon.logic.SimpleItemAssembleObserver;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.util.SilentInstallHelper;
import com.xiaopeng.appstore.common_ui.OpenAppMgr;
import com.xiaopeng.appstore.common_ui.common.Resource;
import com.xiaopeng.appstore.common_ui.common.base.SingleLiveEvent;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class ItemDetailViewModel extends ViewModel {
    private static final String ID_PREFIX = "app_detail_";
    private static final String TAG = "DetailVM";
    private AppDetailModel mAppModel;
    private static final String INTRO_TITLE = ResUtils.getString(R.string.item_detail_desc_title);
    private static final String UPDATE_TITLE = ResUtils.getString(R.string.item_detail_update_title);
    private final MutableLiveData<Boolean> mSourceIsVisibleModel = new MutableLiveData<>();
    private final SingleLiveEvent<String> mOnBackEvent = new SingleLiveEvent<>();
    private final View.OnClickListener mOnBackClick = new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.ItemDetailViewModel.1
        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            ItemDetailViewModel.this.mOnBackEvent.call();
        }
    };
    private final SingleLiveEvent<AppDetailModel> mOnExecuteEvent = new SingleLiveEvent<>();
    private final View.OnClickListener mExecuteClick = new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.ItemDetailViewModel.2
        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            ItemDetailViewModel.this.mOnExecuteEvent.setValue(ItemDetailViewModel.this.mAppModel);
        }
    };
    private final SingleLiveEvent<AppDetailModel> mOnCancelEvent = new SingleLiveEvent<>();
    private final View.OnClickListener mCancelClick = new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.ItemDetailViewModel.3
        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            ItemDetailViewModel.this.mOnCancelEvent.setValue(ItemDetailViewModel.this.mAppModel);
        }
    };
    private final SingleLiveEvent<String> mOnRetryEvent = new SingleLiveEvent<>();
    private final View.OnClickListener mRetryClick = new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.ItemDetailViewModel.4
        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            ItemDetailViewModel.this.mOnRetryEvent.call();
        }
    };
    private final MutableLiveData<Integer> mState = new MutableLiveData<>();
    private final MutableLiveData<Integer> mProgress = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mSpeedVisibility = new MutableLiveData<>();
    private final MutableLiveData<Long> mSpeed = new MutableLiveData<>();
    private final MutableLiveData<String> mTitle1 = new MutableLiveData<>();
    private final MutableLiveData<String> mSubTitle1 = new MutableLiveData<>();
    private final MutableLiveData<String> mDesc1 = new MutableLiveData<>();
    private final MutableLiveData<String> mTitle2 = new MutableLiveData<>();
    private final MutableLiveData<String> mSubTitle2 = new MutableLiveData<>();
    private final MutableLiveData<String> mDesc2 = new MutableLiveData<>();
    private final MediatorLiveData<Object> mOnDownloadFinish = new MediatorLiveData<>();
    private final ItemAssembleObserver mItemAssembleObserver = new SimpleItemAssembleObserver() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.ItemDetailViewModel.5
        @Override // com.xiaopeng.appstore.bizcommon.logic.SimpleItemAssembleObserver, com.xiaopeng.appstore.bizcommon.logic.ItemAssembleObserver
        public void onStateChange(AssembleDataContainer data) {
            super.onStateChange(data);
            int state = data.getState();
            ItemDetailViewModel.this.applyState(state);
            if (state == 7 || state == 0 || state == 6) {
                ItemDetailViewModel.this.mOnDownloadFinish.postValue(null);
            }
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.SimpleItemAssembleObserver, com.xiaopeng.appstore.bizcommon.logic.ItemAssembleObserver
        public void onProgressChange(AssembleDataContainer data) {
            super.onProgressChange(data);
            ItemDetailViewModel.this.applyProgress(data.getProgress());
            ItemDetailViewModel.this.mSpeed.postValue(Long.valueOf(data.getSpeedPerSecond()));
        }
    };
    private final AppComponentManager.OnAppsChangedCallback mOnAppsChangedCallback = new AppComponentManager.SimpleOnAppsChangedCallback() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.ItemDetailViewModel.6
        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageChanged(String packageName, UserHandle user) {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageRemoved(String packageName, UserHandle user) {
            if (ItemDetailViewModel.this.mAppModel.getPackageName().equals(packageName)) {
                ItemDetailViewModel.this.applyStateChange();
            }
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageAdded(List<AppInfo> appInfoList) {
            Logger.t(ItemDetailViewModel.TAG).d("onPackageAdded");
            if (appInfoList == null || appInfoList.isEmpty()) {
                return;
            }
            for (AppInfo appInfo : appInfoList) {
                if (appInfo.componentName.getPackageName().equals(ItemDetailViewModel.this.mAppModel.getPackageName())) {
                    ItemDetailViewModel.this.applyStateChange();
                }
            }
        }
    };
    private final AppComponentManager.OnAppOpenCallback mOnAppOpenCallback = new AppComponentManager.OnAppOpenCallback() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.ItemDetailViewModel.7
        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppOpenCallback
        public void onAppOpen(String packageName) {
            if (ItemDetailViewModel.this.mAppModel.getPackageName().equals(packageName)) {
                ItemDetailViewModel.this.startLoading();
            }
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppOpenCallback
        public void onAppOpenFinish(String packageName) {
            if (ItemDetailViewModel.this.mAppModel.getPackageName().equals(packageName)) {
                ItemDetailViewModel.this.applyStateChange();
            }
        }
    };
    private final XpAppService mXpAppService = XpApiClient.getAppService();
    private final MediatorLiveData<Resource<AppDetailModel>> mResourceLiveData = new MediatorLiveData<>();

    public MutableLiveData<String> getTitle1() {
        return this.mTitle1;
    }

    public MutableLiveData<String> getSubTitle1() {
        return this.mSubTitle1;
    }

    public MutableLiveData<String> getDesc1() {
        return this.mDesc1;
    }

    public MutableLiveData<String> getTitle2() {
        return this.mTitle2;
    }

    public MutableLiveData<String> getSubTitle2() {
        return this.mSubTitle2;
    }

    public MutableLiveData<String> getDesc2() {
        return this.mDesc2;
    }

    public AppDetailModel getAppModel() {
        return this.mAppModel;
    }

    public MutableLiveData<Boolean> getSourceIsVisibleModel() {
        return this.mSourceIsVisibleModel;
    }

    public MediatorLiveData<Resource<AppDetailModel>> getResourceLiveData() {
        return this.mResourceLiveData;
    }

    public MutableLiveData<Object> getOnDownloadFinish() {
        return this.mOnDownloadFinish;
    }

    public SingleLiveEvent<String> getOnBackEvent() {
        return this.mOnBackEvent;
    }

    public SingleLiveEvent<String> getOnRetryEvent() {
        return this.mOnRetryEvent;
    }

    public View.OnClickListener getOnBackClick() {
        return this.mOnBackClick;
    }

    public SingleLiveEvent<AppDetailModel> getOnExecuteEvent() {
        return this.mOnExecuteEvent;
    }

    public View.OnClickListener getExecuteClick() {
        return this.mExecuteClick;
    }

    public SingleLiveEvent<AppDetailModel> getOnCancelEvent() {
        return this.mOnCancelEvent;
    }

    public View.OnClickListener getCancelClick() {
        return this.mCancelClick;
    }

    public View.OnClickListener getRetryClick() {
        return this.mRetryClick;
    }

    public MutableLiveData<Integer> getState() {
        return this.mState;
    }

    public MutableLiveData<Integer> getProgress() {
        return this.mProgress;
    }

    public MutableLiveData<Boolean> getSpeedVisibility() {
        return this.mSpeedVisibility;
    }

    public MutableLiveData<Long> getSpeed() {
        return this.mSpeed;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        if (this.mAppModel != null) {
            AppStoreAssembleManager.get().removeSoftObserver(this.mAppModel.getPackageName(), this.mItemAssembleObserver);
        }
        AppComponentManager.get().removeOnAppsChangedSoftCallback(this.mOnAppsChangedCallback);
        OpenAppMgr.get().removeOnAppOpenCallback(this.mOnAppOpenCallback);
    }

    public void request(String id) {
        if (TextUtils.isEmpty(id)) {
            Logger.t(TAG).i("request error, appId is null.", new Object[0]);
            this.mResourceLiveData.postValue(Resource.empty(ResUtils.getString(R.string.detail_page_no_app), null));
            return;
        }
        Logger.t(TAG).i("request detail:" + id, new Object[0]);
        this.mResourceLiveData.postValue(Resource.loading(null));
        AppRequestContainer appRequestContainer = new AppRequestContainer();
        appRequestContainer.setPackageName(id);
        if (SilentInstallHelper.USE_DETAILS_LIST_API) {
            ArrayList<AppRequestContainer> arrayList = new ArrayList<>();
            arrayList.add(appRequestContainer);
            AppDetailListRequest appDetailListRequest = new AppDetailListRequest();
            appDetailListRequest.setParam(arrayList);
            this.mResourceLiveData.addSource(this.mXpAppService.getAppDetails(appDetailListRequest), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.-$$Lambda$ItemDetailViewModel$Cn17mwtPJ8nnbQlaz7sy3_qq6nY
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ItemDetailViewModel.this.onListResponseChanged((ApiResponse) obj);
                }
            });
            return;
        }
        AppDetailRequest appDetailRequest = new AppDetailRequest();
        appDetailRequest.setParam(appRequestContainer);
        this.mResourceLiveData.addSource(this.mXpAppService.getAppDetail(appDetailRequest), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.-$$Lambda$ItemDetailViewModel$VkXP-lTvKDWQ9qMRDZdmneK8wOo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ItemDetailViewModel.this.onSingleResponseChanged((ApiResponse) obj);
            }
        });
    }

    private void initData(AppDetailModel model) {
        if (model == null) {
            return;
        }
        this.mAppModel = model;
        this.mSourceIsVisibleModel.setValue(Boolean.valueOf(!TextUtils.isEmpty(model.getAppSource())));
        AppStoreAssembleManager.get().addSoftObserver(model.getPackageName(), this.mItemAssembleObserver);
        AppComponentManager.get().addOnAppsChangedSoftCallback(this.mOnAppsChangedCallback);
        OpenAppMgr.get().addOnAppOpenCallback(this.mOnAppOpenCallback);
        applyProgress();
        applyStateChange();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startLoading() {
        this.mState.postValue(10000);
        applyProgress(AppStoreAssembleManager.get().getProgress(this.mAppModel.getPackageName(), this.mAppModel.getDownloadUrl()));
    }

    private int convertProgress(float progress) {
        return Math.round(progress * 1000.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyStateChange() {
        if (this.mAppModel == null) {
            return;
        }
        applyState(AppStoreAssembleManager.get().getState(this.mAppModel.getPackageName(), this.mAppModel.getDownloadUrl(), this.mAppModel.getConfigUrl()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyState(int state) {
        if (state == 7) {
            Logger.t(TAG).i("applyState ignore APP_STATE_DOWNLOAD_SUCCEED", new Object[0]);
            return;
        }
        Logger.t(TAG).i("applyState state=" + AppStoreAssembleManager.stateToStr(state), new Object[0]);
        this.mState.postValue(Integer.valueOf(state));
        this.mSpeedVisibility.postValue(Boolean.valueOf(state == 1));
    }

    private void applyProgress() {
        if (this.mAppModel == null) {
            return;
        }
        applyProgress(AppStoreAssembleManager.get().getProgress(this.mAppModel.getPackageName(), this.mAppModel.getDownloadUrl()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyProgress(float progress) {
        this.mProgress.postValue(Integer.valueOf(convertProgress(progress)));
    }

    private void handleResponseFailed(ApiResponse apiResponse) {
        if (apiResponse instanceof ApiEmptyResponse) {
            Logger.t(TAG).i("request error, empty body.", new Object[0]);
            this.mResourceLiveData.postValue(Resource.empty(ResUtils.getString(R.string.detail_page_no_app), null));
        } else if (apiResponse instanceof ApiErrorResponse) {
            Logger.t(TAG).i("request error, msg=\"%s\".", ((ApiErrorResponse) apiResponse).getErrorMsg());
            this.mResourceLiveData.postValue(Resource.error(ResUtils.getString(R.string.net_error_page), null));
        } else {
            Logger.t(TAG).i("request error, msg(unknown error).", new Object[0]);
            this.mResourceLiveData.postValue(Resource.error(ResUtils.getString(R.string.net_error_page), null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSingleResponseChanged(ApiResponse<XpApiResponse<AppDetailData>> apiResponse) {
        if (apiResponse instanceof ApiSuccessResponse) {
            AppDetailData appDetailData = (AppDetailData) ApiHelper.getResponseData((ApiSuccessResponse) apiResponse);
            if (appDetailData != null) {
                AppDetailModel parseAppDetail = ItemDetailParser.parseAppDetail(appDetailData);
                initData(parseAppDetail);
                Logger.t(TAG).i("request success, app=%s.", appDetailData.getPackageName());
                this.mResourceLiveData.postValue(Resource.success(parseAppDetail));
                handleUpdateAndIntro(parseAppDetail);
                return;
            }
            Logger.t(TAG).i("request error, msg(data is null).", new Object[0]);
            this.mResourceLiveData.postValue(Resource.error(ResUtils.getString(R.string.net_error_page), null));
            return;
        }
        handleResponseFailed(apiResponse);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onListResponseChanged(ApiResponse<XpApiResponse<ArrayList<AppDetailData>>> apiResponse) {
        if (apiResponse instanceof ApiSuccessResponse) {
            ArrayList arrayList = (ArrayList) ApiHelper.getResponseData((ApiSuccessResponse) apiResponse);
            AppDetailData appDetailData = arrayList != null ? (AppDetailData) arrayList.get(0) : null;
            if (appDetailData != null) {
                AppDetailModel parseAppDetail = ItemDetailParser.parseAppDetail(appDetailData);
                initData(parseAppDetail);
                Logger.t(TAG).i("request success, app=%s.", appDetailData.getPackageName());
                this.mResourceLiveData.postValue(Resource.success(parseAppDetail));
                handleUpdateAndIntro(parseAppDetail);
                return;
            }
            Logger.t(TAG).i("request error, msg(data is null).", new Object[0]);
            this.mResourceLiveData.postValue(Resource.error(ResUtils.getString(R.string.net_error_page), null));
            return;
        }
        handleResponseFailed(apiResponse);
    }

    private void handleUpdateAndIntro(AppDetailModel model) {
        if (AppComponentManager.get().isInstalled(model.getPackageName())) {
            this.mTitle1.postValue(UPDATE_TITLE);
            this.mSubTitle1.postValue(BindingHelper.getDetailVersionDesc(model.getVersionName(), model.getVersionDate()));
            this.mDesc1.postValue(model.getVersionDesc());
            this.mTitle2.postValue(INTRO_TITLE);
            this.mSubTitle2.postValue("");
            this.mDesc2.postValue(model.getDetailDesc());
            return;
        }
        this.mTitle2.postValue(UPDATE_TITLE);
        this.mSubTitle2.postValue(BindingHelper.getDetailVersionDesc(model.getVersionName(), model.getVersionDate()));
        this.mDesc2.postValue(model.getVersionDesc());
        this.mTitle1.postValue(INTRO_TITLE);
        this.mSubTitle1.postValue("");
        this.mDesc1.postValue(model.getDetailDesc());
    }
}
