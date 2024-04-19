package com.xiaopeng.appstore.applist_biz.logic.comparator;

import com.xiaopeng.appstore.bizcommon.entities.AppInfo;
import java.util.Comparator;
/* loaded from: classes2.dex */
public class InstallTimeComparator implements Comparator<AppInfo> {
    @Override // java.util.Comparator
    public int compare(AppInfo a, AppInfo b) {
        return Long.compare(a.firstInstallTime, b.firstInstallTime);
    }
}
