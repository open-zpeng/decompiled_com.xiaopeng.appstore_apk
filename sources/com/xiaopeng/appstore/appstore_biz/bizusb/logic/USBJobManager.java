package com.xiaopeng.appstore.appstore_biz.bizusb.logic;

import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.bizusb.datamodel.entities.LocalApkInfo;
import com.xiaopeng.appstore.appstore_biz.bizusb.logic.USBJobManager;
import com.xiaopeng.appstore.appstore_biz.bizusb.logic.callback.IUSBJobCallback;
import com.xiaopeng.appstore.appstore_biz.bizusb.util.Utils;
import com.xiaopeng.appstore.appstore_biz.datamodel.api.XpApiClient;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailData;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailListRequest;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppRequestContainer;
import com.xiaopeng.appstore.bizcommon.entities.common.XpApiResponse;
import com.xiaopeng.appstore.bizcommon.http.ApiHelper;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Executor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class USBJobManager {
    public static final String TAG = "USBJobManager";
    private ArrayList<LocalApkInfo> allApkList;
    private volatile boolean isCancel = false;
    private final IUSBJobCallback mCallback;

    public USBJobManager(IUSBJobCallback callback) {
        this.mCallback = callback;
    }

    public ArrayList<LocalApkInfo> getAllApkList() {
        return this.allApkList;
    }

    public void clearList() {
        ArrayList<LocalApkInfo> arrayList = this.allApkList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    public void doCheckUSBDriveJob() {
        this.isCancel = false;
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaopeng.appstore.appstore_biz.bizusb.logic.-$$Lambda$USBJobManager$RWtbx_J_GJlccJzY0wDe8dzMUeQ
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                USBJobManager.this.lambda$doCheckUSBDriveJob$0$USBJobManager(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ArrayList<File>>() { // from class: com.xiaopeng.appstore.appstore_biz.bizusb.logic.USBJobManager.1
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d) {
            }

            @Override // io.reactivex.Observer
            public void onNext(ArrayList<File> list) {
                USBJobManager.this.handleApkList(list);
            }
        });
    }

    public /* synthetic */ void lambda$doCheckUSBDriveJob$0$USBJobManager(ObservableEmitter observableEmitter) throws Exception {
        if (this.isCancel) {
            return;
        }
        ArrayList<File> uSBFileFromStorage = Utils.getUSBFileFromStorage();
        if (this.isCancel) {
            return;
        }
        ArrayList<File> allApkFileFromList = Utils.getAllApkFileFromList(uSBFileFromStorage);
        Logger.t(TAG).i("allApkList size is " + allApkFileFromList.size(), new Object[0]);
        observableEmitter.onNext(allApkFileFromList);
    }

    public void setCancel(boolean cancel) {
        this.isCancel = cancel;
    }

    public void doCheckUSBDriveJob(final String filePath) {
        this.isCancel = false;
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaopeng.appstore.appstore_biz.bizusb.logic.-$$Lambda$USBJobManager$YIRS3_yXeysjAo-VkCEzKOEtsF4
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                USBJobManager.lambda$doCheckUSBDriveJob$1(filePath, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ArrayList<File>>() { // from class: com.xiaopeng.appstore.appstore_biz.bizusb.logic.USBJobManager.2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d) {
            }

            @Override // io.reactivex.Observer
            public void onNext(ArrayList<File> list) {
                USBJobManager.this.handleApkList(list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$doCheckUSBDriveJob$1(String str, ObservableEmitter observableEmitter) throws Exception {
        File file = new File(str);
        if (file.exists()) {
            ArrayList<File> allApkFile = Utils.getAllApkFile(file);
            Logger.t(TAG).i("allApkList size is " + allApkFile.size(), new Object[0]);
            observableEmitter.onNext(allApkFile);
            return;
        }
        Logger.t(TAG).i("usb File is null ", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleApkList(ArrayList<File> list) {
        if (this.isCancel) {
            return;
        }
        if (list.size() > 0) {
            Logger.t(TAG).i("showLoadingView", new Object[0]);
            this.mCallback.showLoadingView();
            doGetAllApkInfo(list);
            return;
        }
        Logger.t(TAG).i("hiddenView", new Object[0]);
        this.mCallback.hiddenView();
    }

    private void doGetAllApkInfo(final ArrayList<File> list) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaopeng.appstore.appstore_biz.bizusb.logic.-$$Lambda$USBJobManager$rsBalcdm6dYCGn04_L28vhQKCqQ
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                USBJobManager.this.lambda$doGetAllApkInfo$2$USBJobManager(list, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ArrayList<LocalApkInfo>>() { // from class: com.xiaopeng.appstore.appstore_biz.bizusb.logic.USBJobManager.3
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d) {
            }

            @Override // io.reactivex.Observer
            public void onNext(ArrayList<LocalApkInfo> localApkInfos) {
                USBJobManager.this.doCheckApkLegalJob(localApkInfos);
            }
        });
    }

    public /* synthetic */ void lambda$doGetAllApkInfo$2$USBJobManager(ArrayList arrayList, ObservableEmitter observableEmitter) throws Exception {
        if (this.isCancel) {
            return;
        }
        observableEmitter.onNext(Utils.getAllPackageInfo(arrayList));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doCheckApkLegalJob(ArrayList<LocalApkInfo> list) {
        if (this.isCancel || list == null || list.size() <= 0) {
            return;
        }
        AppDetailListRequest appDetailListRequest = new AppDetailListRequest();
        ArrayList<AppRequestContainer> arrayList = new ArrayList<>();
        Iterator<LocalApkInfo> it = list.iterator();
        while (it.hasNext()) {
            LocalApkInfo next = it.next();
            AppRequestContainer appRequestContainer = new AppRequestContainer();
            appRequestContainer.setPackageName(next.getPackageName());
            appRequestContainer.setMd5(next.getMd5());
            arrayList.add(appRequestContainer);
        }
        appDetailListRequest.setParam(arrayList);
        XpApiClient.getAppService().checkAppDetails(appDetailListRequest).enqueue(new AnonymousClass4(list, appDetailListRequest));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.appstore_biz.bizusb.logic.USBJobManager$4  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass4 implements Callback<XpApiResponse<ArrayList<AppDetailData>>> {
        final /* synthetic */ ArrayList val$list;
        final /* synthetic */ AppDetailListRequest val$request;

        AnonymousClass4(final ArrayList val$list, final AppDetailListRequest val$request) {
            this.val$list = val$list;
            this.val$request = val$request;
        }

        @Override // retrofit2.Callback
        public void onResponse(Call<XpApiResponse<ArrayList<AppDetailData>>> call, final Response<XpApiResponse<ArrayList<AppDetailData>>> response) {
            if (USBJobManager.this.isCancel) {
                return;
            }
            Executor mainThread = AppExecutors.get().mainThread();
            final ArrayList arrayList = this.val$list;
            final AppDetailListRequest appDetailListRequest = this.val$request;
            mainThread.execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.bizusb.logic.-$$Lambda$USBJobManager$4$vJ1xZYe1WahwVM16jZMCdRYeF9Y
                @Override // java.lang.Runnable
                public final void run() {
                    USBJobManager.AnonymousClass4.this.lambda$onResponse$0$USBJobManager$4(response, arrayList, appDetailListRequest);
                }
            });
        }

        public /* synthetic */ void lambda$onResponse$0$USBJobManager$4(Response response, ArrayList arrayList, AppDetailListRequest appDetailListRequest) {
            if (response.isSuccessful() && response.body() == null) {
                return;
            }
            ArrayList arrayList2 = (ArrayList) ApiHelper.getXpResponseData((XpApiResponse) response.body());
            if (arrayList2 != null && arrayList2.size() > 0) {
                ArrayList arrayList3 = new ArrayList();
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    AppDetailData appDetailData = (AppDetailData) it.next();
                    LocalApkInfo localApkInfo = new LocalApkInfo(appDetailData.getAppName(), appDetailData.getIconData().getLargeIcon(), appDetailData.getPackageName(), appDetailData.getVersionCode(), appDetailData.getMd5());
                    localApkInfo.setConfigUrl(appDetailData.getConfigUrl());
                    localApkInfo.setConfigMd5(appDetailData.getConfigMd5());
                    arrayList3.add(localApkInfo);
                }
                HashMap hashMap = new HashMap();
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    LocalApkInfo localApkInfo2 = (LocalApkInfo) it2.next();
                    hashMap.put(localApkInfo2.getPackageName(), localApkInfo2.getFile());
                }
                for (int i = 0; i < arrayList3.size(); i++) {
                    LocalApkInfo localApkInfo3 = (LocalApkInfo) arrayList3.get(i);
                    localApkInfo3.setFile((File) hashMap.get(localApkInfo3.getPackageName()));
                }
                USBJobManager.this.doCheckIsInstalled(arrayList3);
                return;
            }
            Logger.t(USBJobManager.TAG).w("No data requested:" + appDetailListRequest, new Object[0]);
            if (USBJobManager.this.mCallback != null) {
                USBJobManager.this.mCallback.hiddenView();
            }
        }

        @Override // retrofit2.Callback
        public void onFailure(Call<XpApiResponse<ArrayList<AppDetailData>>> call, Throwable t) {
            AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.bizusb.logic.-$$Lambda$USBJobManager$4$rqBXOcsaTIJI0eBaer8GTltY4GY
                @Override // java.lang.Runnable
                public final void run() {
                    USBJobManager.AnonymousClass4.this.lambda$onFailure$1$USBJobManager$4();
                }
            });
        }

        public /* synthetic */ void lambda$onFailure$1$USBJobManager$4() {
            if (USBJobManager.this.mCallback != null) {
                USBJobManager.this.mCallback.hiddenView();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doCheckIsInstalled(final ArrayList<LocalApkInfo> list) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaopeng.appstore.appstore_biz.bizusb.logic.-$$Lambda$USBJobManager$ZOlAoxip2BcVMVdSPJ3keGzuMZ8
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                USBJobManager.this.lambda$doCheckIsInstalled$3$USBJobManager(list, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ArrayList<LocalApkInfo>>() { // from class: com.xiaopeng.appstore.appstore_biz.bizusb.logic.USBJobManager.5
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d) {
            }

            @Override // io.reactivex.Observer
            public void onNext(ArrayList<LocalApkInfo> localApkInfos) {
                USBJobManager.this.allApkList = localApkInfos;
                USBJobManager.this.mCallback.showView();
            }
        });
    }

    public /* synthetic */ void lambda$doCheckIsInstalled$3$USBJobManager(ArrayList arrayList, ObservableEmitter observableEmitter) throws Exception {
        if (this.isCancel) {
            return;
        }
        observableEmitter.onNext(Utils.getInstalledApk(arrayList));
    }
}
