package com.xiaopeng.appstore.appstore_ui.bindingextension;

import com.xiaopeng.appstore.appstore_ui.view.ShadowLayout;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
/* loaded from: classes2.dex */
public class ShadowLayoutBindingExtension {
    public static void setShadowColor(ShadowLayout shadowLayout, int color) {
        shadowLayout.setShadowColor(ResUtils.getColor(color));
    }
}
