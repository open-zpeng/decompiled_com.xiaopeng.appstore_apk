package com.xiaopeng.appstore.common_ui.logic;

import com.xiaopeng.appstore.common_ui.R;
import com.xiaopeng.appstore.libcommon.utils.Utils;
/* loaded from: classes.dex */
public class UIConfig {
    private static boolean sEnableShadow = false;
    private static boolean sEnableShadowInit = false;

    private UIConfig() {
    }

    public static boolean iconEnableShadow() {
        if (!sEnableShadowInit) {
            sEnableShadow = Utils.getApp().getResources().getBoolean(R.bool.enable_icon_shadow);
            sEnableShadowInit = true;
        }
        return sEnableShadow;
    }
}
