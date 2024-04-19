package com.xiaopeng.appstore.applist_ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.applist_biz.logic.AppListCache;
import com.xiaopeng.appstore.applist_biz.model.AppItemChangeEvent;
import com.xiaopeng.appstore.applist_biz.model.BaseAppItem;
import com.xiaopeng.appstore.applist_biz.model.LocalAppData;
import com.xiaopeng.appstore.common_ui.common.base.SingleLiveEvent;
import java.util.LinkedList;
import java.util.List;
/* loaded from: classes2.dex */
public class AppListRepository implements AppListCache.AppListListener {
    private static final String TAG = "AppRepo";
    private final AppListCache mAppListCache;
    private MutableLiveData<List<BaseAppItem>> mAppListLiveData;
    private final SingleLiveEvent<Boolean> mAppItemIconChangeEvent = new SingleLiveEvent<>();
    private final MutableLiveData<AppItemChangeEvent> mOnAppItemChangeEvent = new MutableLiveData<>();

    public LiveData<AppItemChangeEvent> getOnAppItemChangeEvent() {
        return this.mOnAppItemChangeEvent;
    }

    public SingleLiveEvent<Boolean> getAppItemIconChangeEvent() {
        return this.mAppItemIconChangeEvent;
    }

    public MutableLiveData<List<BaseAppItem>> getAppList() {
        return this.mAppListLiveData;
    }

    public AppListRepository() {
        this.mAppListLiveData = new MutableLiveData<>();
        AppListCache appListCache = AppListCache.get();
        this.mAppListCache = appListCache;
        this.mAppListLiveData = new MutableLiveData<>(new LinkedList(appListCache.getAppList()));
        subscribe();
    }

    private void subscribe() {
        Logger.t(TAG).d("subscribe this=" + hashCode());
        this.mAppListCache.registerAppListListener(this);
    }

    public void unsubscribe() {
        Logger.t(TAG).d("unsubscribe this=" + hashCode());
        this.mAppListCache.unregisterAppListListener(this);
    }

    public void tryLoadData() {
        this.mAppListCache.tryLoadData();
    }

    public void reloadPreloadList(boolean force) {
        AppListCache appListCache = this.mAppListCache;
        if (appListCache != null) {
            appListCache.lambda$loadPreloadListAsync$10$AppListCache(force);
        }
    }

    public void loadAllIconsAsync() {
        AppListCache appListCache = this.mAppListCache;
        if (appListCache != null) {
            appListCache.loadAllIconsAsync();
        }
    }

    public void refreshOrder(List<LocalAppData> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        this.mAppListCache.refreshOrder(list);
    }

    @Override // com.xiaopeng.appstore.applist_biz.logic.AppListCache.AppListListener
    public void onAppListChanged(List<BaseAppItem> appList) {
        Logger.t(TAG).d("onAppListChanged, appList=" + appList);
        this.mAppListLiveData.postValue(new LinkedList(appList));
    }

    @Override // com.xiaopeng.appstore.applist_biz.logic.AppListCache.AppListListener
    public void onAppItemChanged(BaseAppItem appItem) {
        AppItemChangeEvent appItemChangeEvent = new AppItemChangeEvent();
        appItemChangeEvent.setType(1);
        appItemChangeEvent.setData(appItem);
        this.mOnAppItemChangeEvent.postValue(appItemChangeEvent);
    }

    @Override // com.xiaopeng.appstore.applist_biz.logic.AppListCache.AppListListener
    public void onAllIconsChanged() {
        this.mAppItemIconChangeEvent.postValue(true);
    }
}
