package com.xiaopeng.appstore.xpcommon;

import android.content.Intent;
import com.orhanobut.logger.Logger;
/* loaded from: classes2.dex */
public class XpUtils {
    private static final String TAG = "XpUtils";

    private XpUtils() {
    }

    public static void invokeSetSharedId(Intent intent, int sharedId) {
        try {
            Intent.class.getMethod("setSharedId", Integer.TYPE).invoke(intent, Integer.valueOf(sharedId));
        } catch (Exception e) {
            Logger.t(TAG).e("invokeSetSharedId error, it:" + intent + ", shareId:" + sharedId, new Object[0]);
            e.printStackTrace();
        }
    }
}
