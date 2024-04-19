package com.xiaopeng.appstore.xpcommon;

import android.os.SystemProperties;
/* loaded from: classes2.dex */
public class SystemPropUtils {
    private SystemPropUtils() {
    }

    public static String get(String key) {
        return SystemProperties.get(key);
    }

    public static String get(String key, String def) {
        return SystemProperties.get(key, def);
    }
}
