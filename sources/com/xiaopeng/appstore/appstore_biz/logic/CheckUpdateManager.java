package com.xiaopeng.appstore.appstore_biz.logic;

import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.TextUtils;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.datamodel.api.XpApiClient;
import com.xiaopeng.appstore.appstore_biz.datamodel.api.XpAppService;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailData;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppListRequest;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppRequestContainer;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.UpdateRespContainer;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.interpreload.InterUpdateBean;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.interpreload.PreloadItemBean;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.interpreload.PreloadPackageBean;
import com.xiaopeng.appstore.appstore_biz.logic.CheckUpdateManager;
import com.xiaopeng.appstore.appstore_biz.model2.AppStateContainer;
import com.xiaopeng.appstore.appstore_biz.parser.ItemDetailParser;
import com.xiaopeng.appstore.bizcommon.common.BizLifecycle;
import com.xiaopeng.appstore.bizcommon.entities.AppInfo;
import com.xiaopeng.appstore.bizcommon.entities.WebAppInfo;
import com.xiaopeng.appstore.bizcommon.entities.common.XpApiResponse;
import com.xiaopeng.appstore.bizcommon.http.ApiHelper;
import com.xiaopeng.appstore.bizcommon.logic.PreloadLoadListener;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.SilentCellBean;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.util.SilentInstallHelper;
import com.xiaopeng.appstore.bizcommon.utils.PackageUtils;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.NumberUtils;
import com.xiaopeng.appstore.xpcommon.CarUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class CheckUpdateManager implements BizLifecycle {
    private static final long PRELOAD_REQUEST_MIN_INTERVAL = 5000;
    private static final String TAG = "UpdateMgr";
    private static final long UPDATE_REQUEST_MIN_INTERVAL = 300000;
    private static volatile CheckUpdateManager sInstance;
    private XpAppService mInterXpAppService;
    private Call<XpApiResponse<InterUpdateBean>> mLastInterPreloadCall;
    private Call<XpApiResponse<UpdateRespContainer>> mLastPreloadCall;
    private List<WebAppInfo> mPreloadList;
    private List<AppDetailData> mUpdateList;
    private XpAppService mXpAppService;
    private volatile long mUpdateRequestTime = 0;
    private volatile long mPreloadRequestTime = 0;
    private final Object mUpdateListLock = new Object();
    private final Map<String, AppDetailData> mLocalSuspendList = new HashMap();
    private final MutableLiveData<Set<String>> mLocalSuspendLiveList = new MutableLiveData<>();
    private final Set<AppStateContainer> mAppStateList = new HashSet();
    private final MutableLiveData<List<AppDetailData>> mUpdateListLive = new MutableLiveData<>();
    private final MediatorLiveData<Integer> mPendingUpgradeCount = new MediatorLiveData<>();
    private final Set<CheckUpdateListener> mUpdateSoftListenerList = new CopyOnWriteArraySet();
    private final Set<CheckUpdateListener> mUpdateListenerList = new HashSet();
    private final Set<Consumer<Set<String>>> mSuspendListChangeListenerList = new CopyOnWriteArraySet();
    private final Set<AppStateChangeListener> mAppStateListChangeListener = new HashSet();
    private final AppComponentManager.OnAppsChangedCallback mOnAppsChangedCallback = new AppComponentManager.OnAppsChangedCallback() { // from class: com.xiaopeng.appstore.appstore_biz.logic.CheckUpdateManager.1
        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageAdded(List<AppInfo> appInfoList) {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageChanged(String packageName, UserHandle user) {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackagesAvailable(String[] packageNames, UserHandle user, boolean replacing) {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackagesUnavailable(String[] packageNames, UserHandle user, boolean replacing) {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageRemoved(String packageName, UserHandle user) {
            synchronized (CheckUpdateManager.this.mUpdateListLock) {
                if (CheckUpdateManager.this.mUpdateList != null) {
                    Iterator it = CheckUpdateManager.this.mUpdateList.iterator();
                    while (it.hasNext()) {
                        if (((AppDetailData) it.next()).getPackageName().equals(packageName)) {
                            it.remove();
                        }
                    }
                    CheckUpdateManager checkUpdateManager = CheckUpdateManager.this;
                    checkUpdateManager.handleUpdateListChange(checkUpdateManager.mUpdateList);
                    Logger.t(CheckUpdateManager.TAG).i("pnRemoved: pn=" + packageName + ", post count=" + CheckUpdateManager.this.mUpdateList.size(), new Object[0]);
                }
            }
            if (CheckUpdateManager.this.mAppStateList != null) {
                Iterator it2 = CheckUpdateManager.this.mAppStateList.iterator();
                boolean z = false;
                while (it2.hasNext()) {
                    if (packageName.equals(((AppStateContainer) it2.next()).getPackageName())) {
                        z = true;
                        it2.remove();
                    }
                }
                if (z) {
                    Logger.t(CheckUpdateManager.TAG).i("onPackageRemoved, " + packageName + ", dispatchAppStatesChanged " + CheckUpdateManager.this.mAppStateList, new Object[0]);
                    CheckUpdateManager checkUpdateManager2 = CheckUpdateManager.this;
                    checkUpdateManager2.dispatchAppStateListChanged(checkUpdateManager2.mAppStateList);
                }
            }
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageUpdated(String packageName) {
            synchronized (CheckUpdateManager.this.mUpdateListLock) {
                if (CheckUpdateManager.this.mUpdateList != null) {
                    CheckUpdateManager checkUpdateManager = CheckUpdateManager.this;
                    checkUpdateManager.mUpdateList = checkUpdateManager.checkUpdate(checkUpdateManager.mUpdateList);
                    Logger.t(CheckUpdateManager.TAG).d("onPackageUpdated: pn=" + packageName + ", post count=" + CheckUpdateManager.this.mUpdateList.size());
                    CheckUpdateManager checkUpdateManager2 = CheckUpdateManager.this;
                    checkUpdateManager2.handleUpdateListChange(checkUpdateManager2.mUpdateList);
                }
                if (CheckUpdateManager.this.mAppStateList != null) {
                    Iterator it = CheckUpdateManager.this.mAppStateList.iterator();
                    boolean z = false;
                    while (it.hasNext()) {
                        CheckUpdateManager checkUpdateManager3 = CheckUpdateManager.this;
                        if (!checkUpdateManager3.hasNewVersion(checkUpdateManager3.mUpdateList, ((AppStateContainer) it.next()).getPackageName())) {
                            z = true;
                            it.remove();
                        }
                    }
                    if (z) {
                        Logger.t(CheckUpdateManager.TAG).i("onPackageUpdated, " + packageName + ", dispatchAppStatesChanged " + CheckUpdateManager.this.mAppStateList, new Object[0]);
                        CheckUpdateManager checkUpdateManager4 = CheckUpdateManager.this;
                        checkUpdateManager4.dispatchAppStateListChanged(checkUpdateManager4.mAppStateList);
                    }
                }
            }
        }
    };

    /* loaded from: classes2.dex */
    public interface CheckUpdateListener {
        void onUpdate(List<AppDetailData> list);
    }

    public static CheckUpdateManager get() {
        if (sInstance == null) {
            synchronized (CheckUpdateManager.class) {
                if (sInstance == null) {
                    sInstance = new CheckUpdateManager();
                }
            }
        }
        return sInstance;
    }

    public void addSoftListener(CheckUpdateListener listener) {
        this.mUpdateSoftListenerList.add(listener);
    }

    public void removeSoftListener(CheckUpdateListener listener) {
        this.mUpdateSoftListenerList.remove(listener);
    }

    public void addListener(CheckUpdateListener listener) {
        this.mUpdateListenerList.add(listener);
    }

    public void removeListener(CheckUpdateListener listener) {
        this.mUpdateListenerList.remove(listener);
    }

    public void addSuspendListListener(Consumer<Set<String>> listener) {
        this.mSuspendListChangeListenerList.add(listener);
    }

    public void removeSuspendListListener(Consumer<Set<String>> listener) {
        this.mSuspendListChangeListenerList.remove(listener);
    }

    public void addAppStateListListener(Handler handler, Consumer<Set<AppStateContainer>> listener) {
        Looper looper;
        synchronized (this.mAppStateListChangeListener) {
            if (handler == null) {
                looper = Looper.getMainLooper();
            } else {
                looper = handler.getLooper();
            }
            this.mAppStateListChangeListener.add(new AppStateChangeListener(looper, listener));
        }
    }

    public void addAppStateListListener(Consumer<Set<AppStateContainer>> listener) {
        addAppStateListListener(new Handler(), listener);
    }

    public void removeAppStateListListener(Consumer<Set<AppStateContainer>> listener) {
        synchronized (this.mAppStateListChangeListener) {
            Iterator<AppStateChangeListener> it = this.mAppStateListChangeListener.iterator();
            while (it.hasNext()) {
                if (it.next().mListener == listener) {
                    it.remove();
                }
            }
        }
    }

    private CheckUpdateManager() {
    }

    public void init() {
        AppComponentManager.get().addOnAppsChangedCallback(this.mOnAppsChangedCallback);
    }

    private XpAppService getXpAppService() {
        if (this.mXpAppService == null) {
            this.mXpAppService = XpApiClient.getAppService();
        }
        return this.mXpAppService;
    }

    public void release() {
        AppComponentManager.get().removeOnAppsChangedCallback(this.mOnAppsChangedCallback);
        this.mXpAppService = null;
        this.mInterXpAppService = null;
        sInstance = null;
    }

    @Override // com.xiaopeng.appstore.bizcommon.common.BizLifecycle
    public void destroy() {
        this.mUpdateSoftListenerList.clear();
    }

    public MutableLiveData<List<AppDetailData>> getUpdateListLive() {
        return this.mUpdateListLive;
    }

    public MediatorLiveData<Integer> getPendingUpgradeCount() {
        return this.mPendingUpgradeCount;
    }

    public MutableLiveData<Set<String>> getLocalSuspendLiveList() {
        return this.mLocalSuspendLiveList;
    }

    public void requestUpdate() {
        if (canRequest()) {
            requestUpdateForce();
        }
    }

    public List<AppDetailData> requestUpdateSync() {
        if (!canRequest()) {
            Logger.t(TAG).i("requestUpdateSync, request too fast, use cache.", new Object[0]);
            return getCache();
        }
        List<PackageInfo> queryPackageList = AppComponentManager.get().queryPackageList();
        if (queryPackageList != null) {
            return requestUpdateSync(queryPackageList);
        }
        return null;
    }

    private List<AppDetailData> getCache() {
        return this.mUpdateList;
    }

    private boolean canRequest() {
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = uptimeMillis - this.mUpdateRequestTime;
        if (j < UPDATE_REQUEST_MIN_INTERVAL) {
            Logger.t(TAG).i("requestUpdate: request too fast, ignore current(interval=%s).", Long.valueOf(j));
            return false;
        }
        this.mUpdateRequestTime = uptimeMillis;
        return true;
    }

    private void requestUpdateForce() {
        MutableLiveData<Map<String, PackageInfo>> packageMapLive = AppComponentManager.get().getPackageMapLive();
        this.mPendingUpgradeCount.removeSource(packageMapLive);
        this.mPendingUpgradeCount.addSource(packageMapLive, new Observer() { // from class: com.xiaopeng.appstore.appstore_biz.logic.-$$Lambda$CheckUpdateManager$NpeDDOVrWJF_HU7dhAOMvYLiWKo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CheckUpdateManager.this.lambda$requestUpdateForce$1$CheckUpdateManager((Map) obj);
            }
        });
    }

    public /* synthetic */ void lambda$requestUpdateForce$1$CheckUpdateManager(final Map map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.logic.-$$Lambda$CheckUpdateManager$hogCcUDC8ilCZTyGkXbngxkQ55w
            @Override // java.lang.Runnable
            public final void run() {
                CheckUpdateManager.this.lambda$requestUpdateForce$0$CheckUpdateManager(map);
            }
        });
    }

    public /* synthetic */ void lambda$requestUpdateForce$0$CheckUpdateManager(Map map) {
        requestUpdateInternal(map.values());
    }

    private void requestUpdateInternal(Collection<PackageInfo> packageList) {
        List<AppRequestContainer> parsePackageInfoList = parsePackageInfoList(packageList);
        if (parsePackageInfoList == null || parsePackageInfoList.isEmpty()) {
            return;
        }
        AppListRequest appListRequest = new AppListRequest();
        appListRequest.setParams(parsePackageInfoList);
        getXpAppService().getUpdateCall(appListRequest).enqueue(new Callback<XpApiResponse<UpdateRespContainer>>() { // from class: com.xiaopeng.appstore.appstore_biz.logic.CheckUpdateManager.2
            @Override // retrofit2.Callback
            public void onResponse(Call<XpApiResponse<UpdateRespContainer>> call, Response<XpApiResponse<UpdateRespContainer>> response) {
                CheckUpdateManager.this.handleResp(response);
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<XpApiResponse<UpdateRespContainer>> call, Throwable t) {
                Logger.t(CheckUpdateManager.TAG).i("requestUpdateInternal, ex:" + t, new Object[0]);
                CheckUpdateManager.this.handleResp(null);
            }
        });
    }

    private List<AppDetailData> requestUpdateSync(Collection<PackageInfo> packageList) {
        List<AppRequestContainer> parsePackageInfoList = parsePackageInfoList(packageList);
        Response<XpApiResponse<UpdateRespContainer>> response = null;
        if (parsePackageInfoList == null || parsePackageInfoList.isEmpty()) {
            return null;
        }
        AppListRequest appListRequest = new AppListRequest();
        appListRequest.setParams(parsePackageInfoList);
        try {
            response = getXpAppService().getUpdateCall(appListRequest).execute();
        } catch (IOException e) {
            Logger.t(TAG).w("requestUpdateSync ex:" + e, new Object[0]);
        }
        return handleResp(response);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<AppDetailData> handleResp(Response<XpApiResponse<UpdateRespContainer>> resp) {
        return handleResp(resp, true);
    }

    private List<AppDetailData> handleResp(Response<XpApiResponse<UpdateRespContainer>> resp, boolean dispatchEvent) {
        if (resp == null) {
            synchronized (this.mUpdateListLock) {
                if (this.mUpdateList == null) {
                    this.mUpdateList = new ArrayList(0);
                }
                Logger.t(TAG).i("requestUpdate onFailure, post last list and count(%s).", Integer.valueOf(this.mUpdateList.size()));
                if (dispatchEvent) {
                    handleUpdateListChange(this.mUpdateList);
                }
            }
            return this.mUpdateList;
        }
        UpdateRespContainer updateRespContainer = (UpdateRespContainer) ApiHelper.getXpResponseData(resp.body());
        if (updateRespContainer != null) {
            List<AppDetailData> appList = updateRespContainer.getAppList();
            if (appList != null && !appList.isEmpty()) {
                handleLocalSuspendApp(appList);
                handleSilentOperation(appList, 3);
                handleSilentOperation(appList, 2);
                handleHiddenApps(appList);
                synchronized (this.mUpdateListLock) {
                    List<AppDetailData> checkUpdate = checkUpdate(appList);
                    this.mUpdateList = checkUpdate;
                    sort(checkUpdate);
                    if (dispatchEvent) {
                        handleUpdateListChange(this.mUpdateList);
                    }
                    handleAppStates(appList, this.mUpdateList);
                    Logger.t(TAG).i("requestUpdate success, size=%s, data=%s.", Integer.valueOf(this.mUpdateList.size()), this.mUpdateList);
                }
            } else {
                Logger.t(TAG).i("requestUpdate empty data, post 0 value.", new Object[0]);
                synchronized (this.mUpdateListLock) {
                    if (this.mUpdateList == null) {
                        this.mUpdateList = new ArrayList(0);
                    }
                    if (dispatchEvent) {
                        handleUpdateListChange(this.mUpdateList);
                    }
                }
            }
        } else {
            Logger.t(TAG).i("requestUpdate success but resp is null, post last list and count(0):" + updateRespContainer, new Object[0]);
            synchronized (this.mUpdateListLock) {
                if (this.mUpdateList == null) {
                    this.mUpdateList = new ArrayList(0);
                }
                if (dispatchEvent) {
                    handleUpdateListChange(this.mUpdateList);
                }
            }
        }
        return this.mUpdateList;
    }

    public static List<AppDetailData> requestUpdateSync(List<AppRequestContainer> requestParam) {
        AppListRequest appListRequest = new AppListRequest();
        appListRequest.setParams(requestParam);
        try {
            XpApiResponse<UpdateRespContainer> body = XpApiClient.getAppService().getUpdateCall(appListRequest).execute().body();
            if (body == null) {
                Logger.t(TAG).w("requestUpdateSync, body is null, request:" + requestParam, new Object[0]);
                return null;
            }
            UpdateRespContainer updateRespContainer = (UpdateRespContainer) ApiHelper.getXpResponseData(body);
            if (updateRespContainer == null) {
                Logger.t(TAG).w("requestUpdateSync, fail, request:" + requestParam + ", resp:" + body, new Object[0]);
                return null;
            }
            List<AppDetailData> appList = updateRespContainer.getAppList();
            return appList == null ? Collections.emptyList() : new ArrayList(appList);
        } catch (IOException e) {
            Logger.t(TAG).w("requestUpdateSync ex:" + e, new Object[0]);
            e.printStackTrace();
            return null;
        }
    }

    private void dispatchUpdateChanged(List<AppDetailData> list) {
        CheckUpdateListener[] threadSafeUpdateListenerList = getThreadSafeUpdateListenerList();
        if (threadSafeUpdateListenerList != null) {
            for (CheckUpdateListener checkUpdateListener : threadSafeUpdateListenerList) {
                if (checkUpdateListener != null) {
                    checkUpdateListener.onUpdate(list);
                }
            }
        }
        for (CheckUpdateListener checkUpdateListener2 : this.mUpdateListenerList) {
            checkUpdateListener2.onUpdate(list);
        }
    }

    private CheckUpdateListener[] getThreadSafeUpdateListenerList() {
        Set<CheckUpdateListener> set = this.mUpdateSoftListenerList;
        if (set == null || set.size() <= 0) {
            return null;
        }
        CheckUpdateListener[] checkUpdateListenerArr = new CheckUpdateListener[this.mUpdateSoftListenerList.size()];
        this.mUpdateSoftListenerList.toArray(checkUpdateListenerArr);
        return checkUpdateListenerArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUpdateListChange(List<AppDetailData> updateList) {
        ArrayList arrayList = new ArrayList(updateList);
        this.mUpdateListLive.postValue(arrayList);
        this.mPendingUpgradeCount.postValue(Integer.valueOf(arrayList.size()));
        dispatchUpdateChanged(arrayList);
    }

    public void requestPreloadList(PreloadLoadListener listener, boolean force, boolean isFullUpdate) {
        List<WebAppInfo> list;
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = uptimeMillis - this.mPreloadRequestTime;
        if (j < 5000) {
            Logger.t(TAG).i("requestPreloadList: requesting, ignore request(force=%s, interval=%s).", Boolean.valueOf(force), Long.valueOf(j));
            if (listener != null) {
                listener.onFinish(null, isFullUpdate);
                return;
            }
            return;
        }
        this.mPreloadRequestTime = uptimeMillis;
        if (force || (list = this.mPreloadList) == null || list.isEmpty()) {
            Logger.t(TAG).i("requestPreloadList: execute request", new Object[0]);
            if (!CarUtils.isEURegion()) {
                requestPreloadList(listener, isFullUpdate);
                return;
            } else {
                requestInterPreloadList(listener, isFullUpdate);
                return;
            }
        }
        Logger.t(TAG).i("requestPreloadList: ignore request, post last data, " + this.mPreloadList, new Object[0]);
        if (listener != null) {
            listener.onFinish(this.mPreloadList, isFullUpdate);
        }
    }

    private void requestPreloadList(PreloadLoadListener listener, boolean isFullUpdate) {
        Call<XpApiResponse<UpdateRespContainer>> call = this.mLastPreloadCall;
        if (call != null) {
            call.cancel();
        }
        Call<XpApiResponse<UpdateRespContainer>> preloadAppList = getXpAppService().getPreloadAppList();
        this.mLastPreloadCall = preloadAppList;
        Logger.t(TAG).i("requestPreloadList enqueue, call=" + preloadAppList.hashCode(), new Object[0]);
        preloadAppList.enqueue(new AnonymousClass3(listener, isFullUpdate));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.appstore_biz.logic.CheckUpdateManager$3  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass3 implements Callback<XpApiResponse<UpdateRespContainer>> {
        final /* synthetic */ boolean val$isFullUpdate;
        final /* synthetic */ PreloadLoadListener val$listener;

        AnonymousClass3(final PreloadLoadListener val$listener, final boolean val$isFullUpdate) {
            this.val$listener = val$listener;
            this.val$isFullUpdate = val$isFullUpdate;
        }

        @Override // retrofit2.Callback
        public void onResponse(final Call<XpApiResponse<UpdateRespContainer>> call, final Response<XpApiResponse<UpdateRespContainer>> response) {
            if (!response.isSuccessful() || response.body() == null) {
                PreloadLoadListener preloadLoadListener = this.val$listener;
                if (preloadLoadListener != null) {
                    preloadLoadListener.onFinish(null, this.val$isFullUpdate);
                }
                Logger.t(CheckUpdateManager.TAG).i("requestPreloadList fails, post 0 value. call=" + call.hashCode(), new Object[0]);
                return;
            }
            Executor backgroundThread = AppExecutors.get().backgroundThread();
            final PreloadLoadListener preloadLoadListener2 = this.val$listener;
            final boolean z = this.val$isFullUpdate;
            backgroundThread.execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.logic.-$$Lambda$CheckUpdateManager$3$WEK3ayeUal9SuLVgreHRCIaQXY8
                @Override // java.lang.Runnable
                public final void run() {
                    CheckUpdateManager.AnonymousClass3.this.lambda$onResponse$0$CheckUpdateManager$3(response, preloadLoadListener2, z, call);
                }
            });
        }

        public /* synthetic */ void lambda$onResponse$0$CheckUpdateManager$3(Response response, PreloadLoadListener preloadLoadListener, boolean z, Call call) {
            UpdateRespContainer updateRespContainer = (UpdateRespContainer) ApiHelper.getXpResponseData((XpApiResponse) response.body());
            List<AppDetailData> appList = updateRespContainer != null ? updateRespContainer.getAppList() : null;
            if (appList == null || appList.isEmpty()) {
                CheckUpdateManager.this.mPreloadList = new ArrayList(0);
                if (preloadLoadListener != null) {
                    preloadLoadListener.onFinish(CheckUpdateManager.this.mPreloadList, z);
                }
                Logger.t(CheckUpdateManager.TAG).i("requestPreloadList empty data, post 0 value. call=" + call.hashCode(), new Object[0]);
                return;
            }
            CheckUpdateManager.this.handleSilentOperation(appList, 1);
            CheckUpdateManager.this.handleHiddenApps(appList);
            int size = appList.size();
            CheckUpdateManager.this.mPreloadList = ItemDetailParser.parseToWebAppList(appList);
            if (preloadLoadListener != null) {
                preloadLoadListener.onFinish(CheckUpdateManager.this.mPreloadList, z);
            }
            Logger.t(CheckUpdateManager.TAG).i("requestPreloadList success, size=%s, data=%s, call=%s.", Integer.valueOf(size), CheckUpdateManager.this.mPreloadList, Integer.valueOf(call.hashCode()));
        }

        @Override // retrofit2.Callback
        public void onFailure(Call<XpApiResponse<UpdateRespContainer>> call, Throwable t) {
            if (call.isCanceled()) {
                Logger.t(CheckUpdateManager.TAG).i("requestPreloadList canceled, msg=" + t.getMessage() + " call=" + call.hashCode(), new Object[0]);
            } else {
                Logger.t(CheckUpdateManager.TAG).i("requestPreloadList failed, msg=" + t.getMessage() + " call=" + call.hashCode(), new Object[0]);
            }
            PreloadLoadListener preloadLoadListener = this.val$listener;
            if (preloadLoadListener != null) {
                preloadLoadListener.onFinish(null, this.val$isFullUpdate);
            }
        }
    }

    private void requestInterPreloadList(PreloadLoadListener listener, boolean isFullUpdate) {
        Call<XpApiResponse<InterUpdateBean>> call = this.mLastInterPreloadCall;
        if (call != null) {
            call.cancel();
        }
        Call<XpApiResponse<InterUpdateBean>> preloadInterAppList = getXpAppService().getPreloadInterAppList();
        this.mLastInterPreloadCall = preloadInterAppList;
        Logger.t(TAG).i("requestInterPreloadList enqueue, callInter=" + preloadInterAppList.hashCode(), new Object[0]);
        preloadInterAppList.enqueue(new AnonymousClass4(listener, isFullUpdate));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.appstore_biz.logic.CheckUpdateManager$4  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass4 implements Callback<XpApiResponse<InterUpdateBean>> {
        final /* synthetic */ boolean val$isFullUpdate;
        final /* synthetic */ PreloadLoadListener val$listener;

        AnonymousClass4(final PreloadLoadListener val$listener, final boolean val$isFullUpdate) {
            this.val$listener = val$listener;
            this.val$isFullUpdate = val$isFullUpdate;
        }

        @Override // retrofit2.Callback
        public void onResponse(final Call<XpApiResponse<InterUpdateBean>> call, final Response<XpApiResponse<InterUpdateBean>> response) {
            Logger.t(CheckUpdateManager.TAG).i("request InterPreloadList success", new Object[0]);
            if (!response.isSuccessful() || response.body() == null) {
                PreloadLoadListener preloadLoadListener = this.val$listener;
                if (preloadLoadListener != null) {
                    preloadLoadListener.onFinish(null, this.val$isFullUpdate);
                }
                Logger.t(CheckUpdateManager.TAG).i("requestInterPreloadList fails, post 0 value. call=" + call.hashCode(), new Object[0]);
                return;
            }
            Executor backgroundThread = AppExecutors.get().backgroundThread();
            final PreloadLoadListener preloadLoadListener2 = this.val$listener;
            final boolean z = this.val$isFullUpdate;
            backgroundThread.execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.logic.-$$Lambda$CheckUpdateManager$4$Z_VWdYgfGE4vLSNjs5pZ80BI62w
                @Override // java.lang.Runnable
                public final void run() {
                    CheckUpdateManager.AnonymousClass4.this.lambda$onResponse$0$CheckUpdateManager$4(response, preloadLoadListener2, z, call);
                }
            });
        }

        public /* synthetic */ void lambda$onResponse$0$CheckUpdateManager$4(Response response, PreloadLoadListener preloadLoadListener, boolean z, Call call) {
            List parseInterUpdateBean = CheckUpdateManager.this.parseInterUpdateBean((InterUpdateBean) ApiHelper.getXpResponseData((XpApiResponse) response.body()));
            if (parseInterUpdateBean == null || parseInterUpdateBean.isEmpty()) {
                CheckUpdateManager.this.mPreloadList = new ArrayList(0);
                if (preloadLoadListener != null) {
                    preloadLoadListener.onFinish(CheckUpdateManager.this.mPreloadList, z);
                }
                Logger.t(CheckUpdateManager.TAG).i("requestInterPreloadList empty data, post 0 value. call=" + call.hashCode(), new Object[0]);
                return;
            }
            CheckUpdateManager.this.handleSilentOperation(parseInterUpdateBean, 1);
            CheckUpdateManager.this.handleHiddenApps(parseInterUpdateBean);
            int size = parseInterUpdateBean.size();
            CheckUpdateManager.this.mPreloadList = ItemDetailParser.parseToWebAppList(parseInterUpdateBean);
            if (preloadLoadListener != null) {
                preloadLoadListener.onFinish(CheckUpdateManager.this.mPreloadList, z);
            }
            Logger.t(CheckUpdateManager.TAG).i("requestInterPreloadList success, size=%s, data=%s, call=%s.", Integer.valueOf(size), CheckUpdateManager.this.mPreloadList, Integer.valueOf(call.hashCode()));
        }

        @Override // retrofit2.Callback
        public void onFailure(Call<XpApiResponse<InterUpdateBean>> call, Throwable t) {
            Logger.t(CheckUpdateManager.TAG).i("request InterPreloadList fail", t.toString());
            if (call.isCanceled()) {
                Logger.t(CheckUpdateManager.TAG).i("requestInterPreloadList canceled, msg=" + t.getMessage() + " call=" + call.hashCode(), new Object[0]);
            } else {
                Logger.t(CheckUpdateManager.TAG).i("requestInterPreloadList failed, msg=" + t.getMessage() + " call=" + call.hashCode(), new Object[0]);
            }
            PreloadLoadListener preloadLoadListener = this.val$listener;
            if (preloadLoadListener != null) {
                preloadLoadListener.onFinish(null, this.val$isFullUpdate);
            }
        }
    }

    public boolean hasNewVersion(String packageName) {
        return hasNewVersion(this.mUpdateList, packageName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasNewVersion(List<AppDetailData> updateList, String packageName) {
        if (updateList == null || TextUtils.isEmpty(packageName)) {
            return false;
        }
        for (AppDetailData appDetailData : updateList) {
            if (appDetailData.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<AppDetailData> parseInterUpdateBean(InterUpdateBean bean) {
        List<PreloadItemBean> list;
        ArrayList arrayList = null;
        if (bean == null || bean.getData() == null) {
            return null;
        }
        List<PreloadPackageBean> list2 = bean.getData().getList();
        if (list2 != null && !list2.isEmpty()) {
            PreloadPackageBean preloadPackageBean = list2.get(0);
            if (preloadPackageBean.getData() == null || (list = preloadPackageBean.getData().getList()) == null) {
                return null;
            }
            arrayList = new ArrayList();
            for (PreloadItemBean preloadItemBean : list) {
                arrayList.add(ItemDetailParser.parseInterToNormalDetailData(preloadItemBean.getData()));
            }
        }
        return arrayList;
    }

    private List<AppRequestContainer> parseAppList(List<AppInfo> appList) {
        if (appList == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (AppInfo appInfo : appList) {
            AppRequestContainer appRequestContainer = new AppRequestContainer();
            String packageName = appInfo.componentName.getPackageName();
            PackageInfo packageInfo = AppComponentManager.get().getPackageInfo(packageName);
            if (packageInfo != null && !AppComponentManager.isSystemApp(packageInfo)) {
                long longVersionCode = packageInfo.getLongVersionCode();
                appRequestContainer.setPackageName(packageName);
                appRequestContainer.setVersionCode(longVersionCode);
                arrayList.add(appRequestContainer);
            }
        }
        return arrayList;
    }

    private List<AppRequestContainer> parsePackageInfoList(Collection<PackageInfo> packageList) {
        if (packageList == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        for (PackageInfo packageInfo : packageList) {
            String str = packageInfo.packageName;
            if (filterPackage(packageInfo)) {
                sb.append(str).append("|");
            } else {
                AppRequestContainer appRequestContainer = new AppRequestContainer();
                long longVersionCode = packageInfo.getLongVersionCode();
                appRequestContainer.setPackageName(str);
                appRequestContainer.setVersionCode(longVersionCode);
                arrayList.add(appRequestContainer);
            }
        }
        Logger.t(TAG).i("parsePackageInfoList, size:" + arrayList.size() + ",list:" + arrayList, new Object[0]);
        Logger.t(TAG).v("parsePackageInfoList, filter:" + ((Object) sb), new Object[0]);
        return arrayList;
    }

    private boolean filterPackage(PackageInfo info) {
        if (info == null) {
            return true;
        }
        String str = info.packageName;
        return TextUtils.isEmpty(str) || str.startsWith("com.android.") || str.startsWith("android.") || str.startsWith("com.qualcomm.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<AppDetailData> checkUpdate(List<AppDetailData> onlineList) {
        ArrayList arrayList = new ArrayList();
        if (onlineList != null) {
            for (AppDetailData appDetailData : onlineList) {
                if (!appDetailData.isHidden()) {
                    if (PackageUtils.isAppUpdate(appDetailData.getVersionCode(), appDetailData.getPackageName())) {
                        arrayList.add(appDetailData);
                    }
                }
            }
        }
        return arrayList;
    }

    private void sort(List<AppDetailData> list) {
        list.sort(new Comparator() { // from class: com.xiaopeng.appstore.appstore_biz.logic.-$$Lambda$CheckUpdateManager$dak3Xq_9erh4YsNY6Yk6Gg3jiEs
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return CheckUpdateManager.lambda$sort$2((AppDetailData) obj, (AppDetailData) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ int lambda$sort$2(AppDetailData appDetailData, AppDetailData appDetailData2) {
        int i = (NumberUtils.stringToLong(appDetailData.getUpdateTime()) > NumberUtils.stringToLong(appDetailData2.getUpdateTime()) ? 1 : (NumberUtils.stringToLong(appDetailData.getUpdateTime()) == NumberUtils.stringToLong(appDetailData2.getUpdateTime()) ? 0 : -1));
        if (i == 0) {
            return 0;
        }
        return i > 0 ? -1 : 1;
    }

    private void handleLocalSuspendApp(List<AppDetailData> remoteList) {
        if (remoteList == null || remoteList.isEmpty()) {
            return;
        }
        this.mLocalSuspendList.clear();
        for (AppDetailData appDetailData : remoteList) {
            if (NumberUtils.stringToInt(appDetailData.getStatus(), -1) == 4) {
                this.mLocalSuspendList.put(appDetailData.getPackageName(), appDetailData);
            }
        }
        if (!this.mLocalSuspendList.isEmpty()) {
            Logger.t(TAG).i("handleSuspendApp changed:" + this.mLocalSuspendList, new Object[0]);
        } else {
            Logger.t(TAG).i("handleSuspendApp no suspended app", new Object[0]);
        }
        dispatchSuspendAppsChanged(this.mLocalSuspendList);
    }

    private void dispatchSuspendAppsChanged(Map<String, AppDetailData> suspendList) {
        this.mLocalSuspendLiveList.postValue(new HashSet(suspendList.keySet()));
        for (Consumer<Set<String>> consumer : this.mSuspendListChangeListenerList) {
            consumer.accept(suspendList.keySet());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchAppStateListChanged(Set<AppStateContainer> appStateList) {
        for (AppStateChangeListener appStateChangeListener : this.mAppStateListChangeListener) {
            appStateChangeListener.accept((AppStateChangeListener) appStateList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSilentOperation(List<AppDetailData> detailDataList, int operation) {
        if (detailDataList != null) {
            Logger.t(TAG).d("preload detail list data = " + detailDataList + "   install = " + operation);
            for (AppDetailData appDetailData : detailDataList) {
                Logger.t(TAG).d("detail data is " + appDetailData.toString());
                boolean z = false;
                boolean z2 = appDetailData.isSilentInstall() && (operation == 1 || operation == 3);
                if (appDetailData.isForceUninstall() && operation == 2) {
                    z = true;
                }
                if (z2 || z) {
                    SilentCellBean silentCellBean = new SilentCellBean();
                    silentCellBean.setMd5(appDetailData.getMd5());
                    silentCellBean.setOperation(operation);
                    silentCellBean.setApkFileUrl(appDetailData.getDownloadUrl());
                    silentCellBean.setConfigUrl(appDetailData.getConfigUrl());
                    silentCellBean.setOperationPackageName(appDetailData.getPackageName());
                    silentCellBean.setOperationAppVersionCode(appDetailData.getVersionCode());
                    SilentCellBean silentCellBean2 = new SilentCellBean();
                    ArrayList<SilentCellBean> arrayList = new ArrayList<>();
                    arrayList.add(silentCellBean);
                    silentCellBean2.setApkFileUrl(silentCellBean.getApkFileUrl());
                    silentCellBean2.setOperationPackageName(silentCellBean.getOperationPackageName());
                    silentCellBean2.setSilentCellBeans(arrayList);
                    SilentInstallHelper.silentOperation(silentCellBean2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHiddenApps(List<AppDetailData> detailDataList) {
        if (detailDataList == null || detailDataList.isEmpty()) {
            return;
        }
        Iterator<AppDetailData> it = detailDataList.iterator();
        while (it.hasNext()) {
            AppDetailData next = it.next();
            if (next.isHidden()) {
                it.remove();
                SilentInstallHelper.appendHiddenPackageName(next.getPackageName());
            } else {
                SilentInstallHelper.removeHiddenPackageName(next.getPackageName());
            }
        }
    }

    private void handleAppStates(List<AppDetailData> detailDataList, List<AppDetailData> updateList) {
        if (detailDataList == null || detailDataList.isEmpty()) {
            return;
        }
        this.mAppStateList.clear();
        for (AppDetailData appDetailData : detailDataList) {
            String packageName = appDetailData.getPackageName();
            AppStateContainer appStateContainer = null;
            if (!appDetailData.isHidden()) {
                if (appDetailData.isForceUpdate()) {
                    appStateContainer = AppStateContainer.create(packageName);
                    appStateContainer.setState(2);
                    appStateContainer.setRemoteVersionName(appDetailData.getVersionName());
                    appStateContainer.setRemoteVersionCode(appDetailData.getVersionCode());
                }
                if (hasNewVersion(updateList, packageName)) {
                    if (appStateContainer == null) {
                        appStateContainer = AppStateContainer.create(packageName);
                    }
                    appStateContainer.setHasUpdate(true);
                    appStateContainer.setRemoteVersionName(appDetailData.getVersionName());
                    appStateContainer.setRemoteVersionCode(appDetailData.getVersionCode());
                }
            }
            int stringToInt = NumberUtils.stringToInt(appDetailData.getStatus(), -1);
            if (stringToInt == 4) {
                if (appStateContainer == null) {
                    appStateContainer = AppStateContainer.create(packageName);
                }
                appStateContainer.setState(1);
            } else if (stringToInt == 6) {
                if (appStateContainer == null) {
                    appStateContainer = AppStateContainer.create(packageName);
                }
                appStateContainer.setPromptTitle(appDetailData.getPromptTitle());
                appStateContainer.setPromptText(appDetailData.getPromptText());
                appStateContainer.setState(3);
            }
            if (appStateContainer != null) {
                this.mAppStateList.add(appStateContainer);
            }
        }
        Logger.t(TAG).i("handleAppStates, list=" + this.mAppStateList, new Object[0]);
        dispatchAppStateListChanged(this.mAppStateList);
    }

    /* loaded from: classes2.dex */
    public static class AppStateChangeListener implements Consumer<Set<AppStateContainer>>, Handler.Callback {
        private final Handler mHandler;
        private final Consumer<Set<AppStateContainer>> mListener;

        public AppStateChangeListener(Looper looper, Consumer<Set<AppStateContainer>> listener) {
            this.mHandler = new Handler(looper, this);
            this.mListener = listener;
        }

        @Override // java.util.function.Consumer
        public void accept(Set<AppStateContainer> appStateList) {
            this.mHandler.obtainMessage(0, appStateList).sendToTarget();
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message msg) {
            if (this.mListener != null) {
                this.mListener.accept((Set) msg.obj);
                return false;
            }
            return false;
        }
    }
}
