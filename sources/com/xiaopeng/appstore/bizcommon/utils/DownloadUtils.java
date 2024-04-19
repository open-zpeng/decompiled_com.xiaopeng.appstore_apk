package com.xiaopeng.appstore.bizcommon.utils;

import android.net.Uri;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appserver_common.NapaHandler;
import com.xiaopeng.appstore.libcommon.utils.NumberUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import java.io.File;
/* loaded from: classes2.dex */
public class DownloadUtils {
    private static final String TAG = "DownloadUtils";

    public static File getDownloadDirFile() {
        return new File(Utils.getApp().getFilesDir(), NapaHandler.MODULE_DOWNLOAD);
    }

    public static String getNameFromUrl(final String url) {
        return Uri.parse(url).getLastPathSegment();
    }

    public static long getTaskIdFromUri(Uri uri) {
        try {
            return NumberUtils.stringToLong(uri.getLastPathSegment(), -1L);
        } catch (NumberFormatException unused) {
            Logger.t(TAG).e(" Uri never contains task id", new Object[0]);
            return -1L;
        }
    }
}
