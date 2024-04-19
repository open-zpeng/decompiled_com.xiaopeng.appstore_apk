package com.xiaopeng.appstore;

import android.content.Context;
import android.net.Uri;
/* loaded from: classes2.dex */
public class AppUtils {
    private static final String HOST = "details";
    private static final String SCHEME = "market";

    public static boolean isPlatformInternational() {
        return false;
    }

    public static String getVerDesc(Context context) {
        return context.getString(R.string.version_desc_format, "VV1.6.0_20230915224106_Release", "372", BuildConfig.BUILD_TIME, BuildConfig.BUILD_BRANCH, BuildConfig.BUILD_COMMIT);
    }

    public static boolean isGoToDetail(Uri uri) {
        return uri != null && SCHEME.equals(uri.getScheme()) && HOST.equals(uri.getHost());
    }
}
