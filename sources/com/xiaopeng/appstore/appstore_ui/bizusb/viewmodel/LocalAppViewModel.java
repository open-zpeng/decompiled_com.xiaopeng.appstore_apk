package com.xiaopeng.appstore.appstore_ui.bizusb.viewmodel;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.appstore.appstore_biz.bizusb.datamodel.entities.LocalApkInfo;
import com.xiaopeng.appstore.appstore_biz.bizusb.logic.USBStateManager;
import com.xiaopeng.appstore.appstore_biz.bizusb.model.LocalAppModel;
import com.xiaopeng.appstore.common_ui.common.adapter.AdapterData;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class LocalAppViewModel extends ViewModel {
    private final MutableLiveData<List<AdapterData<?>>> mListMutableLiveData = new MutableLiveData<>();
    private final List<AdapterData<?>> mAdapterDataList = new ArrayList();

    public MutableLiveData<List<AdapterData<?>>> getListLiveData() {
        return this.mListMutableLiveData;
    }

    public void loadData(USBStateManager usbStateManager) {
        this.mAdapterDataList.clear();
        addList(usbStateManager);
        this.mListMutableLiveData.postValue(this.mAdapterDataList);
    }

    /* JADX WARN: Type inference failed for: r12v0, types: [T, com.xiaopeng.appstore.appstore_biz.bizusb.model.LocalAppModel] */
    private void addList(USBStateManager usbStateManager) {
        List<LocalApkInfo> allApkList = usbStateManager.getAllApkList();
        if (allApkList == null || allApkList.size() == 0) {
            Log.i("LocalAppViewModel", "usbStateManager.getAllApkList() is null or empty");
            return;
        }
        ArrayList arrayList = new ArrayList(allApkList.size());
        for (LocalApkInfo localApkInfo : allApkList) {
            ?? localAppModel = new LocalAppModel(localApkInfo.getAppName(), localApkInfo.getPackageName(), localApkInfo.getIconUrl(), localApkInfo.isInstalled(), localApkInfo.getFile(), localApkInfo.getConfigUrl(), localApkInfo.getBitmap(), localApkInfo.getConfigMd5(), localApkInfo.getMd5());
            AdapterData adapterData = new AdapterData();
            adapterData.data = localAppModel;
            arrayList.add(adapterData);
        }
        if (arrayList.isEmpty()) {
            return;
        }
        this.mAdapterDataList.addAll(arrayList);
    }
}
