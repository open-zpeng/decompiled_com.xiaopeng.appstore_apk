package com.xiaopeng.appstore.appserver_common.utils;

import android.graphics.Bitmap;
import com.google.protobuf.ByteString;
import java.io.ByteArrayOutputStream;
/* loaded from: classes2.dex */
public class LocalUtils {
    public static byte[] bitmap2ByteArray(Bitmap bmp) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static ByteString bitmap2ByteString(Bitmap bmp) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return ByteString.copyFrom(byteArrayOutputStream.toByteArray());
    }
}
