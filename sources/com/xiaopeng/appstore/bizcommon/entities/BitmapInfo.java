package com.xiaopeng.appstore.bizcommon.entities;

import android.graphics.Bitmap;
/* loaded from: classes2.dex */
public class BitmapInfo {
    public int color;
    public Bitmap icon;

    public void applyTo(BitmapInfo info) {
        info.icon = this.icon;
        info.color = this.color;
    }

    public static BitmapInfo fromBitmap(Bitmap bitmap) {
        BitmapInfo bitmapInfo = new BitmapInfo();
        bitmapInfo.icon = bitmap;
        return bitmapInfo;
    }
}
