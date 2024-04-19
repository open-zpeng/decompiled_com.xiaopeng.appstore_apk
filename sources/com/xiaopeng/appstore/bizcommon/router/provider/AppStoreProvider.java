package com.xiaopeng.appstore.bizcommon.router.provider;

import androidx.lifecycle.LiveData;
import com.xiaopeng.appstore.bizcommon.logic.PreloadLoadListener;
import com.xiaopeng.appstore.bizcommon.router.IModuleService;
/* loaded from: classes2.dex */
public interface AppStoreProvider extends IModuleService {
    LiveData<Integer> getPendingUpgradeCount();

    boolean hasNewVersion(String packageName);

    void requestPreloadList(PreloadLoadListener listener, boolean force);

    void requestPreloadList(PreloadLoadListener listener, boolean force, boolean isFullUpdate);

    void requestUpdateData();
}
