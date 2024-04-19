package com.xiaopeng.appstore.libcommon.utils;

import com.orhanobut.logger.Logger;
/* loaded from: classes2.dex */
public class ApplicationReadyHelper {
    private static final String TAG = "ApplicationReadyHelper";
    private static final long WAIT_TIMEOUT = 1000;
    private static boolean sApplicationReady = false;
    private static final Object sApplicationReadyWait = new Object();

    private ApplicationReadyHelper() {
    }

    public static boolean isApplicationReady() {
        return sApplicationReady;
    }

    public static void notifyApplicationReady() {
        Object obj = sApplicationReadyWait;
        synchronized (obj) {
            Logger.t(TAG).i("notifyApplicationReady", new Object[0]);
            sApplicationReady = true;
            obj.notifyAll();
        }
    }

    public static boolean waitApplicationReady() {
        boolean z;
        Object obj = sApplicationReadyWait;
        synchronized (obj) {
            if (!sApplicationReady) {
                try {
                    obj.wait(WAIT_TIMEOUT);
                } catch (InterruptedException e) {
                    Logger.t(TAG).w("waitApplication ex:" + e, new Object[0]);
                    Thread.currentThread().interrupt();
                }
            }
            z = sApplicationReady;
        }
        return z;
    }
}
