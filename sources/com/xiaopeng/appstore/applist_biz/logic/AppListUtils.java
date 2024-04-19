package com.xiaopeng.appstore.applist_biz.logic;

import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.appstore.storeprovider.ResourceProviderContract;
import java.util.List;
/* loaded from: classes2.dex */
public class AppListUtils {
    private AppListUtils() {
    }

    public static <T> void move(List<T> list, int from, int to) {
        int size = list.size();
        if (size <= from || size <= to) {
            return;
        }
        list.add(to, list.remove(from));
    }

    public static void removeNewAppFlag(final String packageName) {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.applist_biz.logic.-$$Lambda$AppListUtils$SMskP3O8MuFTJtaRYg72paDUbNY
            @Override // java.lang.Runnable
            public final void run() {
                ResourceProviderContract.clearState(Utils.getApp(), 1000, packageName);
            }
        });
    }
}
