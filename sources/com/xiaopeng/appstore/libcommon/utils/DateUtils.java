package com.xiaopeng.appstore.libcommon.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
/* loaded from: classes2.dex */
public class DateUtils {
    private static final long TIME = 1000;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getDate(String format, long millis) {
        return new SimpleDateFormat(format).format(new Date(millis));
    }

    public static String stringToTimestamp(String dateString) {
        if (dateString == null) {
            return "";
        }
        try {
            return String.valueOf(sdf.parse(dateString).getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
