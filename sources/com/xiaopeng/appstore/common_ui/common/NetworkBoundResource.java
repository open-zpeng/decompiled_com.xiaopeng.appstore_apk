package com.xiaopeng.appstore.common_ui.common;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.entities.common.ApiEmptyResponse;
import com.xiaopeng.appstore.bizcommon.entities.common.ApiErrorResponse;
import com.xiaopeng.appstore.bizcommon.entities.common.ApiResponse;
import com.xiaopeng.appstore.bizcommon.entities.common.ApiSuccessResponse;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
/* loaded from: classes.dex */
public abstract class NetworkBoundResource<ResultType, RequestType> {
    private static final String TAG = "NBR";
    private final AppExecutors mAppExecutors;
    private final MediatorLiveData<Resource<ResultType>> mResult;

    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    protected abstract LiveData<ResultType> loadFromCache();

    protected void onFetchFailed() {
    }

    protected abstract void saveCallResult(RequestType item);

    protected abstract boolean shouldFetch(ResultType cache);

    public NetworkBoundResource(AppExecutors appExecutors) {
        MediatorLiveData<Resource<ResultType>> mediatorLiveData = new MediatorLiveData<>();
        this.mResult = mediatorLiveData;
        this.mAppExecutors = appExecutors;
        mediatorLiveData.setValue(Resource.loading(null));
        Logger.t(TAG).d("loadFromCache.");
        final LiveData<ResultType> loadFromCache = loadFromCache();
        mediatorLiveData.addSource(loadFromCache, new Observer() { // from class: com.xiaopeng.appstore.common_ui.common.-$$Lambda$NetworkBoundResource$xnB5x9LiySTYvplkRlD19KLJDaM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NetworkBoundResource.this.lambda$new$1$NetworkBoundResource(loadFromCache, obj);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$new$1$NetworkBoundResource(LiveData liveData, Object obj) {
        this.mResult.removeSource(liveData);
        if (shouldFetch(obj)) {
            Logger.t(TAG).d("Fetch data.");
            fetchFromNetwork(liveData);
            return;
        }
        this.mResult.addSource(liveData, new Observer() { // from class: com.xiaopeng.appstore.common_ui.common.-$$Lambda$NetworkBoundResource$mUMq34C7SkjwHpDIDX3Xx-WpN4g
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj2) {
                NetworkBoundResource.this.lambda$new$0$NetworkBoundResource(obj2);
            }
        });
    }

    public /* synthetic */ void lambda$new$0$NetworkBoundResource(Object obj) {
        Logger.t(TAG).d("Should not fetch data, use db data.");
        setValue(Resource.success(obj));
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return this.mResult;
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        this.mResult.addSource(dbSource, new Observer() { // from class: com.xiaopeng.appstore.common_ui.common.-$$Lambda$NetworkBoundResource$JFLJ4UTDxyqcD3Kk_JKhlupJ5a8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NetworkBoundResource.this.lambda$fetchFromNetwork$2$NetworkBoundResource(dbSource, obj);
            }
        });
        final LiveData<ApiResponse<RequestType>> createCall = createCall();
        this.mResult.addSource(createCall, new Observer() { // from class: com.xiaopeng.appstore.common_ui.common.-$$Lambda$NetworkBoundResource$W9JjiSre9B9nrwwc67Lacj2NNbo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NetworkBoundResource.this.lambda$fetchFromNetwork$9$NetworkBoundResource(createCall, dbSource, (ApiResponse) obj);
            }
        });
    }

    public /* synthetic */ void lambda$fetchFromNetwork$2$NetworkBoundResource(LiveData liveData, Object obj) {
        this.mResult.removeSource(liveData);
        Logger.t(TAG).d("Loading with db data(%s).", obj);
        setValue(Resource.loading(obj));
    }

    public /* synthetic */ void lambda$fetchFromNetwork$9$NetworkBoundResource(LiveData liveData, final LiveData liveData2, final ApiResponse apiResponse) {
        this.mResult.removeSource(liveData);
        this.mResult.removeSource(liveData2);
        if (apiResponse instanceof ApiSuccessResponse) {
            this.mAppExecutors.backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.common.-$$Lambda$NetworkBoundResource$8yLFuzFLfG0Zq7rI1f7aqf5M74k
                @Override // java.lang.Runnable
                public final void run() {
                    NetworkBoundResource.this.lambda$fetchFromNetwork$5$NetworkBoundResource(apiResponse);
                }
            });
        } else if (apiResponse instanceof ApiEmptyResponse) {
            this.mAppExecutors.mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.common.-$$Lambda$NetworkBoundResource$5uSGanwxnriD0GP0SG4xwH2LWEo
                @Override // java.lang.Runnable
                public final void run() {
                    NetworkBoundResource.this.lambda$fetchFromNetwork$7$NetworkBoundResource();
                }
            });
        } else if (apiResponse instanceof ApiErrorResponse) {
            onFetchFailed();
            this.mResult.addSource(liveData2, new Observer() { // from class: com.xiaopeng.appstore.common_ui.common.-$$Lambda$NetworkBoundResource$LKYuI98u8MJ1XAI-BjHJ0prw9r0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    NetworkBoundResource.this.lambda$fetchFromNetwork$8$NetworkBoundResource(liveData2, apiResponse, obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$fetchFromNetwork$5$NetworkBoundResource(ApiResponse apiResponse) {
        saveCallResult(processResponse((ApiSuccessResponse) apiResponse));
        this.mAppExecutors.mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.common.-$$Lambda$NetworkBoundResource$k_y8TYbBezfAod_QecQHkZoS-88
            @Override // java.lang.Runnable
            public final void run() {
                NetworkBoundResource.this.lambda$fetchFromNetwork$4$NetworkBoundResource();
            }
        });
    }

    public /* synthetic */ void lambda$fetchFromNetwork$4$NetworkBoundResource() {
        final LiveData<ResultType> loadFromCache = loadFromCache();
        this.mResult.addSource(loadFromCache, new Observer() { // from class: com.xiaopeng.appstore.common_ui.common.-$$Lambda$NetworkBoundResource$yqn2Ggp9jatYUivvQjyFwbvCJI0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NetworkBoundResource.this.lambda$fetchFromNetwork$3$NetworkBoundResource(loadFromCache, obj);
            }
        });
    }

    public /* synthetic */ void lambda$fetchFromNetwork$3$NetworkBoundResource(LiveData liveData, Object obj) {
        this.mResult.removeSource(liveData);
        Logger.t(TAG).d("Load success, save to db then post(%s).", obj);
        setValue(Resource.success(obj));
    }

    public /* synthetic */ void lambda$fetchFromNetwork$7$NetworkBoundResource() {
        final LiveData<ResultType> loadFromCache = loadFromCache();
        this.mResult.addSource(loadFromCache, new Observer() { // from class: com.xiaopeng.appstore.common_ui.common.-$$Lambda$NetworkBoundResource$B8k5P_nGuiQ5_wLaeWyCRtuYBmg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NetworkBoundResource.this.lambda$fetchFromNetwork$6$NetworkBoundResource(loadFromCache, obj);
            }
        });
    }

    public /* synthetic */ void lambda$fetchFromNetwork$6$NetworkBoundResource(LiveData liveData, Object obj) {
        this.mResult.removeSource(liveData);
        Logger.t(TAG).d("Load empty data, use db data(%s).", obj);
        setValue(Resource.success(obj));
    }

    public /* synthetic */ void lambda$fetchFromNetwork$8$NetworkBoundResource(LiveData liveData, ApiResponse apiResponse, Object obj) {
        this.mResult.removeSource(liveData);
        String errorMsg = ((ApiErrorResponse) apiResponse).getErrorMsg();
        Logger.t(TAG).d("Load error(%s), use db data(%s).", errorMsg, obj);
        setValue(Resource.error(errorMsg, obj));
    }

    private void setValue(Resource<ResultType> newValue) {
        if (this.mResult.getValue() != newValue) {
            this.mResult.setValue(newValue);
        }
    }

    protected RequestType processResponse(ApiSuccessResponse<RequestType> response) {
        return response.getBody();
    }
}
