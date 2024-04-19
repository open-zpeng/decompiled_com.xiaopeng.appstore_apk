package com.xiaopeng.appstore.libcommon.utils;

import android.text.TextUtils;
import com.orhanobut.logger.Logger;
/* loaded from: classes2.dex */
public class NumberUtils {
    private static final String TAG = "NumberUtils";

    private NumberUtils() {
    }

    public static int stringToInt(String numStr) {
        return stringToInt(numStr, 0);
    }

    public static int stringToInt(String numStr, int defaultValue) {
        try {
            return Integer.parseInt(numStr);
        } catch (Exception e) {
            Logger.t(TAG).w("stringToInt str=" + numStr + " " + e.getMessage(), new Object[0]);
            return defaultValue;
        }
    }

    public static long stringToLong(String numStr) {
        return stringToLong(numStr, 0L);
    }

    public static long stringToLong(String numStr, long defaultValue) {
        if (TextUtils.isEmpty(numStr)) {
            return defaultValue;
        }
        try {
            return Long.parseLong(numStr);
        } catch (Exception e) {
            Logger.t(TAG).w("stringToLong str=" + numStr + " " + e.getMessage(), new Object[0]);
            return defaultValue;
        }
    }

    public static float stringToFloat(String param) {
        return stringToFloat(param, 0.0f);
    }

    public static float stringToFloat(String param, float defaultValue) {
        try {
            return Float.parseFloat(param.trim());
        } catch (Exception e) {
            Logger.t(TAG).w("stringToFloat str=" + param + " " + e.getMessage(), new Object[0]);
            return defaultValue;
        }
    }

    public static double stringToDouble(String param) {
        return stringToDouble(param, 0.0d);
    }

    public static double stringToDouble(String param, double defaultValue) {
        try {
            return Double.parseDouble(param.trim());
        } catch (Exception e) {
            Logger.t(TAG).w("stringToFloat str=" + param + " " + e.getMessage(), new Object[0]);
            return defaultValue;
        }
    }
}
