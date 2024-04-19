package com.xiaopeng.appstore.appstore_ui.viewmodel;

import android.os.UserHandle;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailData;
import com.xiaopeng.appstore.appstore_biz.logic.CheckUpdateManager;
import com.xiaopeng.appstore.appstore_biz.model2.AppBaseModel;
import com.xiaopeng.appstore.appstore_biz.parser.ItemDetailParser;
import com.xiaopeng.appstore.appstore_ui.common.BindingHelper;
import com.xiaopeng.appstore.appstore_ui.viewmodel.PendingUpgradeViewModel;
import com.xiaopeng.appstore.bizcommon.entities.AppInfo;
import com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.SimpleItemAssembleObserver;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.common_ui.common.base.SingleLiveEvent;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes2.dex */
public class PendingUpgradeViewModel extends ViewModel {
    private static final String TAG = "UpgradeVM";
    private final SimpleItemAssembleObserver mAssembleObserver;
    private final Object mLock;
    private final AppComponentManager.OnAppsChangedCallback mOnAppsChangedCallback;
    private final View.OnClickListener mOnBackClick;
    private final SingleLiveEvent<String> mOnBackEvent;
    private final View.OnClickListener mOnUpgradeAllClick;
    private final MediatorLiveData<List<AppBaseModel>> mPersistentUpgradeList;
    private final MutableLiveData<Boolean> mUpdateAllVisible;
    private final SingleLiveEvent<String> mUpgradeAllEvent;
    private final MediatorLiveData<List<AppBaseModel>> mUpgradeList = new MediatorLiveData<>();

    public LiveData<List<AppBaseModel>> getUpgradeList() {
        return this.mUpgradeList;
    }

    public MediatorLiveData<List<AppBaseModel>> getPersistentUpgradeList() {
        return this.mPersistentUpgradeList;
    }

    public MutableLiveData<Boolean> getUpdateAllVisible() {
        return this.mUpdateAllVisible;
    }

    public SingleLiveEvent<String> getOnBackEvent() {
        return this.mOnBackEvent;
    }

    public View.OnClickListener getOnBackClick() {
        return this.mOnBackClick;
    }

    public View.OnClickListener getOnUpgradeAllClick() {
        return this.mOnUpgradeAllClick;
    }

    public SingleLiveEvent<String> getUpgradeAllEvent() {
        return this.mUpgradeAllEvent;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.appstore_ui.viewmodel.PendingUpgradeViewModel$3  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass3 extends SimpleItemAssembleObserver {
        AnonymousClass3() {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.SimpleItemAssembleObserver, com.xiaopeng.appstore.bizcommon.logic.ItemAssembleObserver
        public void onStateChange(final AssembleDataContainer data) {
            super.onStateChange(data);
            AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.-$$Lambda$PendingUpgradeViewModel$3$HbDb4NfaKsyy49PovFIgskB7ZOQ
                @Override // java.lang.Runnable
                public final void run() {
                    PendingUpgradeViewModel.AnonymousClass3.this.lambda$onStateChange$0$PendingUpgradeViewModel$3(data);
                }
            });
        }

        public /* synthetic */ void lambda$onStateChange$0$PendingUpgradeViewModel$3(AssembleDataContainer assembleDataContainer) {
            boolean checkUpdateList = PendingUpgradeViewModel.this.checkUpdateList(null);
            Logger.t(PendingUpgradeViewModel.TAG).d("onStateChange data=" + assembleDataContainer + ", has=" + checkUpdateList);
            PendingUpgradeViewModel.this.mUpdateAllVisible.postValue(Boolean.valueOf(checkUpdateList));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.appstore_ui.viewmodel.PendingUpgradeViewModel$4  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass4 implements AppComponentManager.OnAppsChangedCallback {
        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageAdded(List<AppInfo> appInfoList) {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageChanged(String packageName, UserHandle user) {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageUpdated(String packageName) {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackagesAvailable(String[] packageNames, UserHandle user, boolean replacing) {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackagesUnavailable(String[] packageNames, UserHandle user, boolean replacing) {
        }

        AnonymousClass4() {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageRemoved(final String packageName, UserHandle user) {
            AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.-$$Lambda$PendingUpgradeViewModel$4$MABJ13GDhN7BvpAFDhJEdLEjjE4
                @Override // java.lang.Runnable
                public final void run() {
                    PendingUpgradeViewModel.AnonymousClass4.this.lambda$onPackageRemoved$0$PendingUpgradeViewModel$4(packageName);
                }
            });
        }

        public /* synthetic */ void lambda$onPackageRemoved$0$PendingUpgradeViewModel$4(String str) {
            synchronized (PendingUpgradeViewModel.this.mLock) {
                List list = (List) PendingUpgradeViewModel.this.mPersistentUpgradeList.getValue();
                if (list != null) {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        if (((AppBaseModel) it.next()).getPackageName().equals(str)) {
                            it.remove();
                        }
                    }
                    PendingUpgradeViewModel.this.mPersistentUpgradeList.postValue(list);
                }
            }
        }
    }

    public PendingUpgradeViewModel() {
        MediatorLiveData<List<AppBaseModel>> mediatorLiveData = new MediatorLiveData<>();
        this.mPersistentUpgradeList = mediatorLiveData;
        this.mLock = new Object();
        this.mUpdateAllVisible = new MutableLiveData<>(false);
        this.mOnBackClick = new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.PendingUpgradeViewModel.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PendingUpgradeViewModel.this.mOnBackEvent.call();
            }
        };
        this.mOnBackEvent = new SingleLiveEvent<>();
        this.mOnUpgradeAllClick = new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.PendingUpgradeViewModel.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PendingUpgradeViewModel.this.mUpgradeAllEvent.call();
            }
        };
        this.mUpgradeAllEvent = new SingleLiveEvent<>();
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        this.mAssembleObserver = anonymousClass3;
        AnonymousClass4 anonymousClass4 = new AnonymousClass4();
        this.mOnAppsChangedCallback = anonymousClass4;
        mediatorLiveData.addSource(CheckUpdateManager.get().getUpdateListLive(), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.-$$Lambda$PendingUpgradeViewModel$d2u4DX78eDZhEIKunUOgiXT6noU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PendingUpgradeViewModel.this.lambda$new$1$PendingUpgradeViewModel((List) obj);
            }
        });
        AppStoreAssembleManager.get().addObserver(anonymousClass3);
        AppComponentManager.get().addOnAppsChangedCallback(anonymousClass4);
    }

    public /* synthetic */ void lambda$new$1$PendingUpgradeViewModel(final List list) {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.-$$Lambda$PendingUpgradeViewModel$5zv6o4eBT2zg0oxaOLdHd1yUsmg
            @Override // java.lang.Runnable
            public final void run() {
                PendingUpgradeViewModel.this.lambda$new$0$PendingUpgradeViewModel(list);
            }
        });
    }

    public /* synthetic */ void lambda$new$0$PendingUpgradeViewModel(List list) {
        synchronized (this.mLock) {
            ArrayList arrayList = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(ItemDetailParser.parseAppBase((AppDetailData) it.next()));
            }
            if (this.mPersistentUpgradeList.getValue() == null) {
                this.mPersistentUpgradeList.postValue(arrayList);
            }
            boolean checkUpdateList = checkUpdateList(arrayList);
            Logger.t(TAG).d("updateList changed, updateEmpty=" + (!checkUpdateList));
            this.mUpdateAllVisible.postValue(Boolean.valueOf(checkUpdateList));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        AppStoreAssembleManager.get().removeObserverInList(this.mAssembleObserver);
        AppComponentManager.get().removeOnAppsChangedCallback(this.mOnAppsChangedCallback);
    }

    private boolean checkList() {
        return checkList(null);
    }

    private boolean checkList(List<AppBaseModel> list) {
        if (list == null) {
            list = this.mUpgradeList.getValue();
        }
        if (list == null || list.isEmpty()) {
            return false;
        }
        List<String> assemblingPnList = AppStoreAssembleManager.get().getAssemblingPnList();
        if (assemblingPnList == null || assemblingPnList.isEmpty()) {
            return true;
        }
        for (AppBaseModel appBaseModel : list) {
            String packageName = appBaseModel.getPackageName();
            if (!assemblingPnList.contains(packageName) && AppComponentManager.get().isInstalled(packageName) && !AppStoreAssembleManager.get().isPending(packageName)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkUpdateList(List<AppBaseModel> list) {
        List<AppBaseModel> value = this.mPersistentUpgradeList.getValue();
        if (value == null || value.isEmpty()) {
            return exisitNoStart(list);
        }
        return exisitNoStart(value);
    }

    private boolean exisitNoStart(List<AppBaseModel> list) {
        if (list != null && !list.isEmpty()) {
            for (AppBaseModel appBaseModel : list) {
                int state = AppStoreAssembleManager.get().getState(appBaseModel.getPackageName(), appBaseModel.getDownloadUrl(), appBaseModel.getConfigUrl());
                String packageName = appBaseModel.getPackageName();
                if (AppComponentManager.get().isInstalled(packageName) && CheckUpdateManager.get().hasNewVersion(packageName) && (state == 6 || state == 2 || state == 0 || state == 101 || state == 10000)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getUpgradeDate(long versionDate) {
        return LogicUtils.getUpgradeDate(versionDate);
    }

    public static String getVersionName(String versionName) {
        return BindingHelper.getVersionDesc(versionName);
    }
}
