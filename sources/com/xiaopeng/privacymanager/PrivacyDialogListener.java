package com.xiaopeng.privacymanager;
/* loaded from: classes3.dex */
public interface PrivacyDialogListener {
    void onDialogClosed(int i, boolean z, int i2);

    default void onDialogShown(int i) {
    }
}
