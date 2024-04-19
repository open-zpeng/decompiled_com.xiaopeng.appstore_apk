package com.xiaopeng.appstore.bizcommon.entities.db;

import com.xiaopeng.appstore.bizcommon.entities.AppConfigInfo;
import java.util.List;
/* loaded from: classes2.dex */
public abstract class AppConfigDao {
    public abstract void insert(AppConfigInfo info);

    public abstract List<AppConfigInfo> queryAppConfigInfoList();
}
