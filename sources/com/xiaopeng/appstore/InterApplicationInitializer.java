package com.xiaopeng.appstore;

import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.Constants;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/* loaded from: classes2.dex */
public class InterApplicationInitializer extends AbstractApplicationInitializer {
    @Override // com.xiaopeng.appstore.AbstractApplicationInitializer
    protected void deInitAccountServices() {
    }

    @Override // com.xiaopeng.appstore.AbstractApplicationInitializer
    protected void deInitXuiManager() {
    }

    @Override // com.xiaopeng.appstore.AbstractApplicationInitializer
    protected void initAccountServices() {
    }

    @Override // com.xiaopeng.appstore.AbstractApplicationInitializer
    protected void initCheckUpdateManager() {
    }

    @Override // com.xiaopeng.appstore.AbstractApplicationInitializer
    public void init() {
        super.init();
        if (isMainProcess(this.mApplication)) {
            AppStoreAssembleManager.get().init();
        }
    }

    @Override // com.xiaopeng.appstore.AbstractApplicationInitializer
    protected List<String> getXpAppList() {
        return Collections.unmodifiableList(Arrays.asList(Constants.XP_INTER_APP_LIST));
    }

    @Override // com.xiaopeng.appstore.AbstractApplicationInitializer
    protected void initXuiManager() {
        XuiMgrHelper.get().init(this.mApplication, true);
    }
}
