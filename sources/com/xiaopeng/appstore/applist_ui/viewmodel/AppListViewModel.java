package com.xiaopeng.appstore.applist_ui.viewmodel;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.applist_biz.logic.AppListUtils;
import com.xiaopeng.appstore.applist_biz.model.AdapterChangeModel;
import com.xiaopeng.appstore.applist_biz.model.AppGroupItemModel;
import com.xiaopeng.appstore.applist_biz.model.AppGroupItemModelWithIndex;
import com.xiaopeng.appstore.applist_biz.model.AppItemChangeEvent;
import com.xiaopeng.appstore.applist_biz.model.AppListChangeModel;
import com.xiaopeng.appstore.applist_biz.model.BaseAppItem;
import com.xiaopeng.appstore.applist_biz.model.LocalAppData;
import com.xiaopeng.appstore.applist_ui.AppListRepository;
import com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper;
import com.xiaopeng.appstore.bizcommon.utils.FeatureOptions;
import com.xiaopeng.appstore.common_ui.common.base.SingleLiveEvent;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
/* loaded from: classes2.dex */
public class AppListViewModel extends ViewModel {
    private static final String TAG = "AppVM";
    private AppGroupHelper mAppGroupHelper;
    private final Executor mExecutor;
    protected final AppListRepository mRepository;
    private final SingleLiveEvent<AppListChangeModel> mAppListChangeEvent = new SingleLiveEvent<>();
    private final MediatorLiveData<List<AppGroupItemModel>> mAdapterDataListLiveData = new MediatorLiveData<>();
    private final MediatorLiveData<Boolean> mAppIconChangeEvent = new MediatorLiveData<>();
    private final MediatorLiveData<AdapterChangeModel<BaseAppItem>> mAppItemChanged = new MediatorLiveData<>();

    /* renamed from: lambda$mNwv73xq0DgMA-ynadCPvTc9_yw  reason: not valid java name */
    public static /* synthetic */ AppGroupItemModelWithIndex m17lambda$mNwv73xq0DgMAynadCPvTc9_yw(int i, AppGroupItemModel appGroupItemModel) {
        return new AppGroupItemModelWithIndex(i, appGroupItemModel);
    }

    protected List filterData(List<AppGroupItemModel> list) {
        return list;
    }

    public MutableLiveData<List<AppGroupItemModel>> getAdapterDataListLiveData() {
        return this.mAdapterDataListLiveData;
    }

    public SingleLiveEvent<AppListChangeModel> getAppListChangeEvent() {
        return this.mAppListChangeEvent;
    }

    public MutableLiveData<Boolean> getAppItemIconChangeEvent() {
        return this.mAppIconChangeEvent;
    }

    public MediatorLiveData<AdapterChangeModel<BaseAppItem>> getAppItemChanged() {
        return this.mAppItemChanged;
    }

    public AppListViewModel() {
        FeatureOptions.addFeature(FeatureOptions.FEATURE_SHOW_PRELOAD_APPS);
        this.mExecutor = Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.xiaopeng.appstore.applist_ui.viewmodel.-$$Lambda$AppListViewModel$0pRc40MkbNncA4D2AbCeQ-LgE5I
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return AppListViewModel.lambda$new$0(runnable);
            }
        });
        this.mRepository = new AppListRepository();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Thread lambda$new$0(Runnable runnable) {
        Thread newThread = Executors.defaultThreadFactory().newThread(runnable);
        newThread.setName("AppVmThread");
        return newThread;
    }

    public void init(AppGroupHelper appGroupHelper) {
        this.mAppGroupHelper = appGroupHelper;
        this.mAppItemChanged.addSource(this.mRepository.getOnAppItemChangeEvent(), new Observer() { // from class: com.xiaopeng.appstore.applist_ui.viewmodel.-$$Lambda$AppListViewModel$G8jcpztuWNSCE0rOP9NctFcSb_8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppListViewModel.this.lambda$init$9$AppListViewModel((AppItemChangeEvent) obj);
            }
        });
        this.mAdapterDataListLiveData.addSource(this.mRepository.getAppList(), new Observer() { // from class: com.xiaopeng.appstore.applist_ui.viewmodel.-$$Lambda$AppListViewModel$VPzAaY-M4nAJ4AKk_CAcGYxfUIk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppListViewModel.this.lambda$init$11$AppListViewModel((List) obj);
            }
        });
        this.mAppIconChangeEvent.addSource(this.mRepository.getAppItemIconChangeEvent(), new Observer() { // from class: com.xiaopeng.appstore.applist_ui.viewmodel.-$$Lambda$AppListViewModel$NhYpsZkh5WZjtjb4K9ziAZb0VWc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppListViewModel.this.lambda$init$12$AppListViewModel((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$init$9$AppListViewModel(final AppItemChangeEvent appItemChangeEvent) {
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_ui.viewmodel.-$$Lambda$AppListViewModel$50XkA1JnKVA6s8LJYyqNaFgsGsU
            @Override // java.lang.Runnable
            public final void run() {
                AppListViewModel.this.lambda$init$8$AppListViewModel(appItemChangeEvent);
            }
        });
    }

    public /* synthetic */ void lambda$init$8$AppListViewModel(AppItemChangeEvent appItemChangeEvent) {
        Logger.t(TAG).i("OnAppItemChangeEvent:" + this.mAppGroupHelper.getDataList(), new Object[0]);
        if (this.mAppGroupHelper.getDataList() == null) {
            return;
        }
        final ArrayList arrayList = new ArrayList(this.mAppGroupHelper.getDataList());
        Observable.just(appItemChangeEvent).filter(new Predicate() { // from class: com.xiaopeng.appstore.applist_ui.viewmodel.-$$Lambda$AppListViewModel$2T6gBpOr0nEGXiO8up4mrOq6kHw
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                return AppListViewModel.lambda$init$1((AppItemChangeEvent) obj);
            }
        }).map(new Function() { // from class: com.xiaopeng.appstore.applist_ui.viewmodel.-$$Lambda$AppListViewModel$DPtwy8jAQ4KkOuyXZ0y6w66kJ_E
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return AppListViewModel.lambda$init$2((AppItemChangeEvent) obj);
            }
        }).subscribe(new Consumer() { // from class: com.xiaopeng.appstore.applist_ui.viewmodel.-$$Lambda$AppListViewModel$XRRnI4H9mSC-ErmaX9-zwCIy4WM
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AppListViewModel.this.lambda$init$6$AppListViewModel(arrayList, (BaseAppItem) obj);
            }
        }, new Consumer() { // from class: com.xiaopeng.appstore.applist_ui.viewmodel.-$$Lambda$AppListViewModel$EKzG2Xf6FaAbXwymsCZA2QAEm60
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AppListViewModel.lambda$init$7((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$init$1(AppItemChangeEvent appItemChangeEvent) throws Exception {
        return appItemChangeEvent.getType() == 1 && (appItemChangeEvent.getData() instanceof BaseAppItem);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ BaseAppItem lambda$init$2(AppItemChangeEvent appItemChangeEvent) throws Exception {
        return (BaseAppItem) appItemChangeEvent.getData();
    }

    public /* synthetic */ void lambda$init$6$AppListViewModel(List list, final BaseAppItem baseAppItem) throws Exception {
        Logger.t(TAG).i("RxJava appItem Changed:" + baseAppItem, new Object[0]);
        Observable.zip(Observable.range(0, list.size()), Observable.fromIterable(list), new BiFunction() { // from class: com.xiaopeng.appstore.applist_ui.viewmodel.-$$Lambda$AppListViewModel$mNwv73xq0DgMA-ynadCPvTc9_yw
            @Override // io.reactivex.functions.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AppListViewModel.m17lambda$mNwv73xq0DgMAynadCPvTc9_yw(((Integer) obj).intValue(), (AppGroupItemModel) obj2);
            }
        }).filter(new Predicate() { // from class: com.xiaopeng.appstore.applist_ui.viewmodel.-$$Lambda$AppListViewModel$iUq3LHe9IFW-yd922HCjpgctANo
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                return AppListViewModel.lambda$init$3((AppGroupItemModelWithIndex) obj);
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaopeng.appstore.applist_ui.viewmodel.-$$Lambda$AppListViewModel$yB6OatMcIIqK7ggR8xRHOAZRSWo
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AppListViewModel.this.lambda$init$4$AppListViewModel(baseAppItem, (AppGroupItemModelWithIndex) obj);
            }
        }, new Consumer() { // from class: com.xiaopeng.appstore.applist_ui.viewmodel.-$$Lambda$AppListViewModel$cp0isuuMQlOmjv1BvVBdraI4DpQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AppListViewModel.lambda$init$5((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$init$3(AppGroupItemModelWithIndex appGroupItemModelWithIndex) throws Exception {
        return appGroupItemModelWithIndex.getAppItemChangeEvent().type == 100 && (appGroupItemModelWithIndex.getAppItemChangeEvent().data instanceof BaseAppItem);
    }

    public /* synthetic */ void lambda$init$4$AppListViewModel(BaseAppItem baseAppItem, AppGroupItemModelWithIndex appGroupItemModelWithIndex) throws Exception {
        BaseAppItem baseAppItem2 = (BaseAppItem) appGroupItemModelWithIndex.getAppItemChangeEvent().data;
        if (baseAppItem.equals(baseAppItem2)) {
            Logger.t(TAG).d("Item change index=" + appGroupItemModelWithIndex.getIndex() + " key=" + baseAppItem2.getKey());
            AdapterChangeModel<BaseAppItem> adapterChangeModel = new AdapterChangeModel<>();
            adapterChangeModel.setIndex(appGroupItemModelWithIndex.getIndex());
            adapterChangeModel.setData(baseAppItem2);
            this.mAppItemChanged.postValue(adapterChangeModel);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$init$5(Throwable th) throws Exception {
        Logger.t(TAG).e("ItemChange ex:" + th, new Object[0]);
        th.printStackTrace();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$init$7(Throwable th) throws Exception {
        Logger.t(TAG).e("ItemChange zip ex:" + th, new Object[0]);
        th.printStackTrace();
    }

    public /* synthetic */ void lambda$init$11$AppListViewModel(final List list) {
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_ui.viewmodel.-$$Lambda$AppListViewModel$QW_JCtavgwGkRGinfRjdPBTh09w
            @Override // java.lang.Runnable
            public final void run() {
                AppListViewModel.this.lambda$init$10$AppListViewModel(list);
            }
        });
    }

    public /* synthetic */ void lambda$init$10$AppListViewModel(List list) {
        Logger.t(TAG).d("Receive app list, size=" + list.size());
        if (this.mAppGroupHelper == null || list.isEmpty()) {
            return;
        }
        this.mAppGroupHelper.initData(list);
        this.mAdapterDataListLiveData.postValue(filterData(this.mAppGroupHelper.getDataList()));
    }

    public /* synthetic */ void lambda$init$12$AppListViewModel(Boolean bool) {
        if (this.mAppGroupHelper != null) {
            Logger.t(TAG).i("refreshTitle", new Object[0]);
            this.mAppGroupHelper.refreshTitle();
        }
        this.mAppIconChangeEvent.postValue(bool);
    }

    public AppGroupHelper getAppGroupHelper() {
        return this.mAppGroupHelper;
    }

    private void runOnBg(Runnable runnable) {
        this.mExecutor.execute(runnable);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        this.mRepository.unsubscribe();
    }

    public void onStop() {
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_ui.viewmodel.-$$Lambda$AppListViewModel$i2Kocx3lG9caRJsrGJDXsTI6oVU
            @Override // java.lang.Runnable
            public final void run() {
                AppListViewModel.this.reloadPreloadListForce();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reloadPreloadListForce() {
        this.mRepository.reloadPreloadList(true);
    }

    public void loadData() {
        this.mRepository.tryLoadData();
    }

    public void loadAllIconsAsync() {
        this.mRepository.loadAllIconsAsync();
    }

    public void moveData(int from, int to) {
        List<AppGroupItemModel> value = this.mAdapterDataListLiveData.getValue();
        if (value != null) {
            AppListUtils.move(value, from, to);
        }
    }

    public void persistOrderAsync() {
        runOnBg(new Runnable() { // from class: com.xiaopeng.appstore.applist_ui.viewmodel.-$$Lambda$AppListViewModel$fzvOsVY-2Wuqxeusqn3HQeLYyak
            @Override // java.lang.Runnable
            public final void run() {
                AppListViewModel.this.lambda$persistOrderAsync$13$AppListViewModel();
            }
        });
    }

    public /* synthetic */ void lambda$persistOrderAsync$13$AppListViewModel() {
        persistOrder(this.mAdapterDataListLiveData.getValue());
    }

    private void persistOrder(List<AppGroupItemModel> modelList) {
        if (modelList == null || modelList.isEmpty()) {
            return;
        }
        this.mRepository.refreshOrder(parseToLocalList(modelList));
    }

    private static List<LocalAppData> parseToLocalList(List<AppGroupItemModel> modelList) {
        if (modelList == null || modelList.isEmpty()) {
            return null;
        }
        ArrayList<AppGroupItemModel> arrayList = new ArrayList(modelList);
        ArrayList arrayList2 = new ArrayList();
        int i = 0;
        for (AppGroupItemModel appGroupItemModel : arrayList) {
            if (appGroupItemModel.type == 100 && (appGroupItemModel.data instanceof BaseAppItem)) {
                BaseAppItem baseAppItem = (BaseAppItem) appGroupItemModel.data;
                LocalAppData localAppData = new LocalAppData();
                localAppData.index = i;
                localAppData.key = baseAppItem.getKey();
                localAppData.packageName = baseAppItem.packageName;
                arrayList2.add(localAppData);
                i++;
            }
        }
        return arrayList2;
    }
}
