package com.xiaopeng.appstore.appstore_ui.bindingextension;

import android.view.View;
/* loaded from: classes2.dex */
public class VisibilityBinding {
    public static void bindVisibility(View view, boolean visible) {
        bindVisibility(view, visible, 0, 8);
    }

    public static void bindVisibility(View view, boolean visible, int visibilityOffValue) {
        bindVisibility(view, visible, 0, visibilityOffValue);
    }

    public static void bindVisibility(View view, boolean visible, int visibilityOnValue, int visibilityOffValue) {
        if (!visible) {
            visibilityOnValue = visibilityOffValue;
        }
        view.setVisibility(visibilityOnValue);
    }

    public static void bindHiddenVisibility(View view, boolean hidden) {
        view.setVisibility(hidden ? 8 : 0);
    }

    public static void bindInvisibleVisibility(View view, boolean invisible) {
        view.setVisibility(invisible ? 4 : 0);
    }
}
