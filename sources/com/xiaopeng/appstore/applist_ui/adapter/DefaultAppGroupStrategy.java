package com.xiaopeng.appstore.applist_ui.adapter;

import com.xiaopeng.appstore.applist_biz.model.BaseAppItem;
import com.xiaopeng.appstore.applist_biz.model.NormalAppItem;
import com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper;
import com.xiaopeng.appstore.bizcommon.utils.PackageUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
/* loaded from: classes2.dex */
public class DefaultAppGroupStrategy implements AppGroupHelper.IAppGroupStrategy {
    private static final int COUNTS_PER_ROW = 7;
    private static final int GROUP_COUNT = 2;
    private static final int GROUP_SYSTEM_APP = 1;
    private static final int GROUP_THIRD_APP = 2;
    private static final String STR_SYSTEM_APP = "系统应用";
    private static final String STR_THIRD_APP = "其他应用";

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public boolean filter(BaseAppItem appItem) {
        return false;
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public int getCountsPerRow() {
        return 7;
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public int getGroupCount() {
        return 2;
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public int getGroupIndex(BaseAppItem appItem) {
        return -1;
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public String getGroupTitle(int group) {
        return group == 1 ? STR_SYSTEM_APP : STR_THIRD_APP;
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public int getGroup(BaseAppItem appItem) {
        return ((appItem instanceof NormalAppItem) && PackageUtils.isSystemApp(Utils.getApp().getApplicationContext(), ((NormalAppItem) appItem).componentName.getPackageName())) ? 1 : 2;
    }
}
