package com.xiaopeng.appstore.appstore_biz.router;

import androidx.lifecycle.LiveData;
import com.xiaopeng.appstore.appstore_biz.logic.CheckUpdateManager;
import com.xiaopeng.appstore.bizcommon.logic.PreloadLoadListener;
import com.xiaopeng.appstore.bizcommon.router.provider.AppStoreProvider;
/* loaded from: classes2.dex */
public class AppStoreProviderImpl implements AppStoreProvider {
    private static final String TAG = "AppStoreProviderImpl";

    @Override // com.xiaopeng.appstore.bizcommon.router.provider.AppStoreProvider
    public void requestUpdateData() {
        CheckUpdateManager.get().requestUpdate();
    }

    @Override // com.xiaopeng.appstore.bizcommon.router.provider.AppStoreProvider
    public LiveData<Integer> getPendingUpgradeCount() {
        return CheckUpdateManager.get().getPendingUpgradeCount();
    }

    @Override // com.xiaopeng.appstore.bizcommon.router.provider.AppStoreProvider
    public boolean hasNewVersion(String packageName) {
        return CheckUpdateManager.get().hasNewVersion(packageName);
    }

    @Override // com.xiaopeng.appstore.bizcommon.router.provider.AppStoreProvider
    public void requestPreloadList(PreloadLoadListener listener, boolean force) {
        requestPreloadList(listener, force, true);
    }

    @Override // com.xiaopeng.appstore.bizcommon.router.provider.AppStoreProvider
    public void requestPreloadList(PreloadLoadListener listener, boolean force, boolean isFullUpdate) {
        CheckUpdateManager.get().requestPreloadList(listener, force, isFullUpdate);
    }
}
