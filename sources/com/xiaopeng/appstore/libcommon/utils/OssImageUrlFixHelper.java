package com.xiaopeng.appstore.libcommon.utils;

import java.util.Locale;
/* loaded from: classes2.dex */
public class OssImageUrlFixHelper {
    private static final String FORMAT_OSS_URL_SIZE_FIX = "%s?x-oss-process=image/resize,h_%d,w_%d";

    private OssImageUrlFixHelper() {
    }

    public static String fix(String url, int width, int height) {
        return (width > 0 || height > 0) ? String.format(Locale.getDefault(), FORMAT_OSS_URL_SIZE_FIX, url, Integer.valueOf(width), Integer.valueOf(height)) : url;
    }
}
