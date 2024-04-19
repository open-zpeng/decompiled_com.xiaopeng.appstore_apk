package com.xiaopeng.aar.utils;

import android.os.Build;
import android.text.TextUtils;
/* loaded from: classes2.dex */
public class Utils {
    public static boolean isUserRelease() {
        return "user".equalsIgnoreCase(Build.TYPE);
    }

    public static int parse(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return 0;
        }
    }

    public static double parseDouble(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0.0d;
        }
        try {
            return Double.parseDouble(str);
        } catch (Exception unused) {
            return 0.0d;
        }
    }

    public static long parseLong(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        try {
            return Long.parseLong(str);
        } catch (Exception unused) {
            return 0L;
        }
    }

    public static int parse16(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return Integer.parseInt(str, 16);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
