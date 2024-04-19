package com.xiaopeng.appstore.xpcommon.privacy;

import android.content.Context;
import com.orhanobut.logger.Logger;
import com.xiaopeng.privacymanager.PrivacySettings;
/* loaded from: classes2.dex */
public class PrivacyUtils {
    private PrivacyUtils() {
    }

    public static boolean isAppStoreAgreed(Context context) {
        return isAgreed(context, 105);
    }

    public static boolean isNeedReconfirm(Context context) {
        boolean z;
        try {
            z = isNeedReconfirm(context, 105);
        } catch (Exception e) {
            Logger.t("PrivacyUtils").e("isNeedReconfirm ex:" + e.getMessage(), new Object[0]);
            e.printStackTrace();
            z = false;
        }
        Logger.t("PrivacyUtils").i("isNeedReconfirm = " + z, new Object[0]);
        return z;
    }

    public static void setNeedReconfirm(Context context) {
        PrivacySettings.setNeedReconfirm(context, 105, false);
    }

    public static boolean isNeedReconfirm(Context context, int type) {
        return PrivacySettings.isNeedReconfirm(context, type);
    }

    public static boolean isAgreed(Context context, int type) {
        return PrivacySettings.isAgreed(context, type);
    }
}
