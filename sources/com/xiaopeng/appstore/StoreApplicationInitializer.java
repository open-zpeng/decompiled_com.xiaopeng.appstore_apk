package com.xiaopeng.appstore;

import com.xiaopeng.appstore.appstore_biz.logic.CheckUpdateManager;
import com.xiaopeng.appstore.appstore_biz.logic.push.PushDownloadService;
import com.xiaopeng.appstore.appstore_biz.logic.push.UploadAppManager;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.Constants;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.bizcommon.utils.AccountUtils;
import com.xiaopeng.appstore.libcommon.utils.AndroidStateObserveMgr;
import com.xiaopeng.appstore.libcommon.utils.TraceUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/* loaded from: classes2.dex */
public class StoreApplicationInitializer extends AbstractApplicationInitializer {
    @Override // com.xiaopeng.appstore.AbstractApplicationInitializer
    public void init() {
        super.init();
        if (isMainProcess(this.mApplication)) {
            AndroidStateObserveMgr.get().init(this.mApplication);
            AppStoreAssembleManager.get().init();
            PushDownloadService.observeScreenOff();
            initPush();
            UploadAppManager.get().init(this.mApplication);
        }
    }

    @Override // com.xiaopeng.appstore.AbstractApplicationInitializer
    public void deInit() {
        super.deInit();
        isMainProcess(this.mApplication);
    }

    @Override // com.xiaopeng.appstore.AbstractApplicationInitializer
    protected List<String> getXpAppList() {
        return Collections.unmodifiableList(Arrays.asList(Constants.XP_APP_LIST));
    }

    @Override // com.xiaopeng.appstore.AbstractApplicationInitializer
    protected void initXuiManager() {
        TraceUtils.alwaysTraceBegin("Application_XuiManager");
        XuiMgrHelper.get().init(this.mApplication, true);
        TraceUtils.alwaysTraceEnd();
    }

    @Override // com.xiaopeng.appstore.AbstractApplicationInitializer
    protected void deInitXuiManager() {
        XuiMgrHelper.get().release();
    }

    @Override // com.xiaopeng.appstore.AbstractApplicationInitializer
    protected void initCheckUpdateManager() {
        TraceUtils.alwaysTraceBegin("Application_CheckUpdateManager");
        CheckUpdateManager.get().init();
        TraceUtils.alwaysTraceEnd();
    }

    @Override // com.xiaopeng.appstore.AbstractApplicationInitializer
    protected void initAccountServices() {
        TraceUtils.alwaysTraceBegin("Application_AccountUtils");
        AccountUtils.get().tryInit(this.mApplication);
        TraceUtils.alwaysTraceEnd();
    }

    @Override // com.xiaopeng.appstore.AbstractApplicationInitializer
    protected void deInitAccountServices() {
        AccountUtils.get().unInitAccount();
    }
}
