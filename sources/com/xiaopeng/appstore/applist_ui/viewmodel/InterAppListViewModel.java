package com.xiaopeng.appstore.applist_ui.viewmodel;

import com.xiaopeng.appstore.applist_biz.model.AppGroupItemModel;
import com.xiaopeng.appstore.bizcommon.utils.FeatureOptions;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
/* loaded from: classes2.dex */
public class InterAppListViewModel extends AppListViewModel {
    public InterAppListViewModel() {
        FeatureOptions.removeFeature(FeatureOptions.FEATURE_SHOW_PRELOAD_APPS);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$filterData$0(AppGroupItemModel appGroupItemModel) {
        return appGroupItemModel.type != 101;
    }

    @Override // com.xiaopeng.appstore.applist_ui.viewmodel.AppListViewModel
    protected List filterData(List<AppGroupItemModel> list) {
        return (List) list.stream().filter(new Predicate() { // from class: com.xiaopeng.appstore.applist_ui.viewmodel.-$$Lambda$InterAppListViewModel$qaWoXrROZ8q7mnjVMA88dnr74tA
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return InterAppListViewModel.lambda$filterData$0((AppGroupItemModel) obj);
            }
        }).collect(Collectors.toList());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.applist_ui.viewmodel.AppListViewModel, androidx.lifecycle.ViewModel
    public void onCleared() {
        this.mRepository.unsubscribe();
    }

    @Override // com.xiaopeng.appstore.applist_ui.viewmodel.AppListViewModel
    public void loadData() {
        this.mRepository.tryLoadData();
    }
}
