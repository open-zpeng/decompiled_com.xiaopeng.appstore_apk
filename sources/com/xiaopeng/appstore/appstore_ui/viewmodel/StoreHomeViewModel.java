package com.xiaopeng.appstore.appstore_ui.viewmodel;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PackageWithItems;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvContainerModel;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel;
import com.xiaopeng.appstore.appstore_biz.parser.AdDataParser;
import com.xiaopeng.appstore.appstore_ui.logic.StoreHomeRepository;
import com.xiaopeng.appstore.common_ui.common.Resource;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import java.util.List;
/* loaded from: classes2.dex */
public class StoreHomeViewModel extends ViewModel {
    private static final long LOADING_CACHE_MIN_DURATION = 800;
    private static final long LOADING_ERROR_MIN_DURATION = 500;
    private static final String TAG = "StoreVM";
    private List<AdvContainerModel<?>> mAdapterDataList;
    private PostTask mErrorPostTask;
    private Handler mHandler;
    private long mLoadingStart;
    private List<PackageWithItems> mPackageList;
    private PostTask mSuccessPostTask;
    private final MediatorLiveData<Resource<List<AdvContainerModel<?>>>> mViewDataListLiveData = new MediatorLiveData<>();
    private long mCachePostStart = 0;
    private long mRemoteDataRcv = 0;
    private final StoreHomeRepository mRepository = new StoreHomeRepository();

    public MediatorLiveData<Resource<List<AdvContainerModel<?>>>> getAdapterDataList() {
        return this.mViewDataListLiveData;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        cancelPostTask();
    }

    public void requestData() {
        this.mLoadingStart = SystemClock.uptimeMillis();
        cancelPostTask();
        startRequest();
    }

    private void startRequest() {
        this.mCachePostStart = 0L;
        this.mRemoteDataRcv = 0L;
        Logger.t(TAG).i("startRequest:" + hashCode(), new Object[0]);
        this.mViewDataListLiveData.addSource(this.mRepository.getHomeDataWithCache(), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.-$$Lambda$StoreHomeViewModel$O96ILBV0TAlpGZDEao43n84LYVU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                StoreHomeViewModel.this.lambda$startRequest$2$StoreHomeViewModel((Resource) obj);
            }
        });
    }

    public /* synthetic */ void lambda$startRequest$2$StoreHomeViewModel(Resource resource) {
        if (resource.status == Resource.Status.SUCCESS) {
            cancelPostTask();
            List<PackageWithItems> list = (List) resource.data;
            this.mPackageList = list;
            if (list == null || list.isEmpty()) {
                Logger.t(TAG).d("Load success but data is empty.");
                schedulePostError(resource.message, true);
                return;
            }
            this.mRemoteDataRcv = SystemClock.uptimeMillis();
            Logger.t(TAG).d("Load success and data is not empty(%s).", Integer.valueOf(this.mPackageList.size()));
            AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.-$$Lambda$StoreHomeViewModel$RBmxJprbkvnO5nl9SW1qodeCprc
                @Override // java.lang.Runnable
                public final void run() {
                    StoreHomeViewModel.this.lambda$startRequest$0$StoreHomeViewModel();
                }
            });
        } else if (resource.status == Resource.Status.LOADING) {
            cancelPostTask();
            List<PackageWithItems> list2 = (List) resource.data;
            this.mPackageList = list2;
            if (list2 == null || list2.isEmpty()) {
                Logger.t(TAG).d("Loading and data is empty.");
                this.mViewDataListLiveData.postValue(Resource.loading(null));
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            this.mCachePostStart = uptimeMillis;
            if (this.mRemoteDataRcv > uptimeMillis) {
                Logger.t(TAG).w("Remote data already received", new Object[0]);
            } else {
                AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.-$$Lambda$StoreHomeViewModel$cymmWQq7pwz2pJp8yeERldUngSw
                    @Override // java.lang.Runnable
                    public final void run() {
                        StoreHomeViewModel.this.lambda$startRequest$1$StoreHomeViewModel();
                    }
                });
            }
        } else {
            String str = resource.message;
            List<PackageWithItems> list3 = this.mPackageList;
            if (list3 == null || list3.isEmpty()) {
                Logger.t(TAG).d("Load error and data is empty, post error(%s).", str);
                schedulePostError(str, true);
                return;
            }
            Logger.t(TAG).d("Load error(%s) but data is not empty, ignored.", str);
        }
    }

    public /* synthetic */ void lambda$startRequest$0$StoreHomeViewModel() {
        this.mAdapterDataList = AdDataParser.parsePackageListV2(this.mPackageList);
        schedulePostSuccess(true);
    }

    public /* synthetic */ void lambda$startRequest$1$StoreHomeViewModel() {
        Logger.t(TAG).d("Loading and local data is not empty(%s).", Integer.valueOf(this.mPackageList.size()));
        List<AdvContainerModel<? extends AdvModel>> parsePackageListV2 = AdDataParser.parsePackageListV2(this.mPackageList);
        this.mAdapterDataList = parsePackageListV2;
        this.mViewDataListLiveData.postValue(Resource.success(parsePackageListV2));
    }

    private Handler getHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
        return this.mHandler;
    }

    private void schedulePostError(String msg, boolean limitMinConsumed) {
        if (limitMinConsumed) {
            if (this.mLoadingStart <= 0) {
                Logger.t(TAG).w("schedulePostError, cannot schedule this task due to loading not start.", new Object[0]);
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis() - this.mLoadingStart;
            long max = Math.max(LOADING_ERROR_MIN_DURATION - uptimeMillis, 0L);
            Logger.t(TAG).d("schedulePostError prepare, loading consumed %s ms.", Long.valueOf(uptimeMillis));
            schedulePostErrorTask(msg, max);
            return;
        }
        Logger.t(TAG).d("schedulePostError prepare, no limit, post error directly.");
        schedulePostErrorTask(msg, 0L);
    }

    private void schedulePostErrorTask(String msg, long delay) {
        PostTask postTask = this.mErrorPostTask;
        if (postTask == null) {
            this.mErrorPostTask = new PostTask(Resource.error(msg, this.mAdapterDataList));
        } else {
            postTask.setResource(Resource.error(msg, this.mAdapterDataList));
        }
        Logger.t(TAG).d("schedulePostErrorTask, schedule a task(%s, delay %s) to display the layout.", Integer.valueOf(this.mErrorPostTask.hashCode()), Long.valueOf(delay));
        getHandler().postDelayed(this.mErrorPostTask, delay);
    }

    private void cancelPostTask() {
        if (this.mHandler != null) {
            if (this.mErrorPostTask != null) {
                Logger.t(TAG).d("cancelPostTask, task(%s) removed.", Integer.valueOf(this.mErrorPostTask.hashCode()));
                this.mHandler.removeCallbacks(this.mErrorPostTask);
            }
            if (this.mSuccessPostTask != null) {
                Logger.t(TAG).d("cancelPostTask, task(%s) removed.", Integer.valueOf(this.mSuccessPostTask.hashCode()));
                this.mHandler.removeCallbacks(this.mSuccessPostTask);
            }
        }
    }

    private void schedulePostSuccess(boolean limitMinConsumed) {
        if (limitMinConsumed) {
            if (this.mCachePostStart <= 0) {
                Logger.t(TAG).w("schedulePostSuccess, cache not loaded.", new Object[0]);
                this.mCachePostStart = 0L;
            }
            long uptimeMillis = SystemClock.uptimeMillis() - this.mCachePostStart;
            long max = Math.max(LOADING_CACHE_MIN_DURATION - uptimeMillis, 0L);
            Logger.t(TAG).d("schedulePostSuccess prepare, loading consumed %s ms.", Long.valueOf(uptimeMillis));
            schedulePostSuccess(max);
            return;
        }
        Logger.t(TAG).d("schedulePostSuccess prepare, no limit, post directly.");
        schedulePostSuccess(0L);
    }

    private void schedulePostSuccess(long delay) {
        PostTask postTask = this.mSuccessPostTask;
        if (postTask == null) {
            this.mSuccessPostTask = new PostTask(Resource.success(this.mAdapterDataList));
        } else {
            postTask.setResource(Resource.success(this.mAdapterDataList));
        }
        Logger.t(TAG).d("schedulePostSuccess, schedule a task(%s, delay %s) to display the layout.", Integer.valueOf(this.mSuccessPostTask.hashCode()), Long.valueOf(delay));
        getHandler().postDelayed(this.mSuccessPostTask, delay);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class PostTask implements Runnable {
        private Resource<List<AdvContainerModel<?>>> mResource;

        private PostTask(Resource<List<AdvContainerModel<?>>> resource) {
            this.mResource = resource;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setResource(Resource<List<AdvContainerModel<?>>> resource) {
            this.mResource = resource;
        }

        @Override // java.lang.Runnable
        public void run() {
            Logger.t(StoreHomeViewModel.TAG).d("PostTask running, meanwhile, remove callbacks. " + this.mResource.status);
            StoreHomeViewModel.this.mViewDataListLiveData.setValue(this.mResource);
            if (StoreHomeViewModel.this.mHandler != null) {
                StoreHomeViewModel.this.mHandler.removeCallbacks(this);
            }
        }
    }
}
