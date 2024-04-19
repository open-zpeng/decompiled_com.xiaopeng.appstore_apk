package com.xiaopeng.appstore.bizcommon.logic;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;
import com.xiaopeng.appstore.storeprovider.StoreProviderManager;
import java.util.HashSet;
import java.util.Set;
/* loaded from: classes2.dex */
public class StoreProviderHelper {
    private static final String TAG = "StoreProviderHelper";
    private static final Set<String> sObservers = new HashSet();
    private static final Object sLock = new Object();

    private StoreProviderHelper() {
    }

    public static void observe(String key) {
        synchronized (sLock) {
            Printer t = Logger.t(TAG);
            StringBuilder append = new StringBuilder().append("observe key:").append(key).append(", set:");
            Set<String> set = sObservers;
            t.i(append.append(set).toString(), new Object[0]);
            if (set.size() == 0) {
                StoreProviderManager.get().startObserve();
            }
            set.add(key);
        }
    }

    public static void removeObserve(String key) {
        synchronized (sLock) {
            Set<String> set = sObservers;
            if (set.size() <= 0) {
                Logger.t(TAG).w("removeObserve, already empty", new Object[0]);
                return;
            }
            set.remove(key);
            if (set.size() == 0) {
                StoreProviderManager.get().stopObserve();
            }
            Logger.t(TAG).i("removeObserve, key:" + key + ", set:" + set, new Object[0]);
        }
    }
}
