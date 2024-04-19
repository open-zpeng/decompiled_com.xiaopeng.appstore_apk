package com.xiaopeng.aar.utils;

import android.util.Log;
import java.util.HashMap;
/* loaded from: classes2.dex */
public class LogUtils {
    private static final boolean DEBUG = false;
    public static final int LOG_D_LEVEL = 3;
    public static final int LOG_E_LEVEL = 6;
    public static final int LOG_I_LEVEL = 4;
    private static int LOG_LEVEL = 4;
    public static final int LOG_W_LEVEL = 5;
    private static String TAG = "aar=";
    private static HashMap<String, Integer> sHashMap = new HashMap<>();

    public static void setLogTagLevel(String str, Integer num) {
        sHashMap.put(str, num);
    }

    public static void clearTagLevel() {
        sHashMap.clear();
    }

    public static void setLogTag(String str) {
        TAG += str;
    }

    public static void setLogLevel(int i) {
        LOG_LEVEL = i;
    }

    private static boolean isLogTagEnabled(String str, int i) {
        Integer num = sHashMap.get(str);
        return num == null || i >= num.intValue();
    }

    private static boolean isLogLevelEnabled(int i) {
        return LOG_LEVEL <= i;
    }

    public static void d(String str, String str2) {
        if (isLogTagEnabled(str, 3) && isLogLevelEnabled(3)) {
            StringBuilder append = new StringBuilder().append(TAG);
            if (str == null) {
                str = "";
            }
            Log.d(append.append(str).toString(), stackTraceLog(str2));
        }
    }

    public static void d(String str, String str2, int i) {
        if (isLogTagEnabled(str, 3) && isLogLevelEnabled(3)) {
            StringBuilder append = new StringBuilder().append(TAG);
            if (str == null) {
                str = "";
            }
            Log.d(append.append(str).toString(), stackTraceLog(str2, i));
        }
    }

    public static void i(String str, String str2) {
        if (isLogTagEnabled(str, 4) && isLogLevelEnabled(4)) {
            StringBuilder append = new StringBuilder().append(TAG);
            if (str == null) {
                str = "";
            }
            Log.i(append.append(str).toString(), stackTraceLog(str2));
        }
    }

    public static void i(String str, String str2, int i) {
        if (isLogTagEnabled(str, 4) && isLogLevelEnabled(4)) {
            StringBuilder append = new StringBuilder().append(TAG);
            if (str == null) {
                str = "";
            }
            Log.i(append.append(str).toString(), stackTraceLog(str2, i));
        }
    }

    public static void w(String str, String str2) {
        if (isLogTagEnabled(str, 5) && isLogLevelEnabled(5)) {
            StringBuilder append = new StringBuilder().append(TAG);
            if (str == null) {
                str = "";
            }
            Log.w(append.append(str).toString(), stackTraceLog(str2));
        }
    }

    public static void e(String str, String str2) {
        if (isLogTagEnabled(str, 6) && isLogLevelEnabled(6)) {
            StringBuilder append = new StringBuilder().append(TAG);
            if (str == null) {
                str = "";
            }
            Log.e(append.append(str).toString(), stackTraceLog(str2));
        }
    }

    private static String stackTraceLog(String str) {
        return str + "--" + Thread.currentThread();
    }

    private static String stackTraceLog(String str, int i) {
        return str + "--" + Thread.currentThread();
    }
}
