package com.xiaopeng.appstore.libcommon.utils;

import android.os.Build;
/* loaded from: classes2.dex */
public class OSUtils {
    public static final boolean ATLEAST_OREO;
    public static final boolean ATLEAST_P;
    public static final boolean ATLEAST_Q;

    private OSUtils() {
    }

    static {
        ATLEAST_OREO = Build.VERSION.SDK_INT >= 26;
        ATLEAST_P = Build.VERSION.SDK_INT >= 28;
        ATLEAST_Q = Build.VERSION.SDK_INT >= 29;
    }
}
