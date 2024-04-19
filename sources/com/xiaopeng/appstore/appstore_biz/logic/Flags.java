package com.xiaopeng.appstore.appstore_biz.logic;

import com.xiaopeng.appstore.libcommon.utils.SPUtils;
/* loaded from: classes2.dex */
public class Flags {
    private static final String SP_KEY_PRIVACY_SHOWN = "sp_key_privacy_shown";
    private static final String SP_TAG = "AppStoreFlags";

    private Flags() {
    }

    public static boolean privacyShown() {
        return SPUtils.getInstance(SP_TAG).getBoolean(SP_KEY_PRIVACY_SHOWN);
    }

    public static void setPrivacyShown(boolean shown) {
        SPUtils.getInstance(SP_TAG).put(SP_KEY_PRIVACY_SHOWN, shown);
    }
}
