package com.xiaopeng.appstore.bizcommon.utils;

import android.database.sqlite.SQLiteFullException;
import android.util.Log;
/* loaded from: classes2.dex */
public class DatabaseUtils {
    private static final String TAG = "DatabaseUtils";

    public static void tryExecute(Runnable executeRunnable) {
        try {
            executeRunnable.run();
        } catch (SQLiteFullException e) {
            Log.e(TAG, "open database failed, ex: " + e);
            e.printStackTrace();
        }
    }
}
