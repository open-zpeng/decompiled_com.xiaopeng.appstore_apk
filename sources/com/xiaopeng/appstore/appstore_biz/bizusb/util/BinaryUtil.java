package com.xiaopeng.appstore.appstore_biz.bizusb.util;

import com.google.common.base.Ascii;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
/* loaded from: classes2.dex */
public class BinaryUtil {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String toBase64String(byte[] binaryData) {
        return new String(Base64.getEncoder().encode(binaryData));
    }

    public static byte[] fromBase64String(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }

    private static byte[] calculateMd5(byte[] binaryData) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(binaryData);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException unused) {
            throw new RuntimeException("MD5 algorithm not found.");
        }
    }

    public static String encodeMD5(byte[] binaryData) {
        byte[] calculateMd5 = calculateMd5(binaryData);
        int length = calculateMd5.length;
        char[] cArr = new char[length * 2];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            char[] cArr2 = HEX_DIGITS;
            cArr[i2] = cArr2[(calculateMd5[i] >>> 4) & 15];
            cArr[i2 + 1] = cArr2[calculateMd5[i] & Ascii.SI];
        }
        return new String(cArr);
    }
}
