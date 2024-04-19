package com.xiaopeng.appstore.libcommon.utils;

import android.os.Looper;
/* loaded from: classes2.dex */
public class Preconditions {
    public static void assertNotNull(Object o) {
        if (o == null) {
            throw new IllegalStateException();
        }
    }

    public static void assertUIThread() {
        if (!isSameLooper(Looper.getMainLooper())) {
            throw new IllegalStateException();
        }
    }

    public static void assertNonUiThread() {
        if (isSameLooper(Looper.getMainLooper())) {
            throw new IllegalStateException();
        }
    }

    private static boolean isSameLooper(Looper looper) {
        return Looper.myLooper() == looper;
    }
}
