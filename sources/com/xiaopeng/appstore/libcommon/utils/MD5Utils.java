package com.xiaopeng.appstore.libcommon.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/* loaded from: classes2.dex */
public class MD5Utils {
    public static String getMD5(String info) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(info.getBytes(StandardCharsets.UTF_8));
            byte[] digest = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                if (Integer.toHexString(digest[i] & 255).length() == 1) {
                    sb.append("0").append(Integer.toHexString(digest[i] & 255));
                } else {
                    sb.append(Integer.toHexString(digest[i] & 255));
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException unused) {
            return "";
        }
    }

    public static String getFileMd5(File file) throws FileNotFoundException {
        RandomAccessFile randomAccessFile = null;
        try {
            try {
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                    if (file != null && file.exists()) {
                        RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "r");
                        try {
                            byte[] bArr = new byte[10485760];
                            while (true) {
                                int read = randomAccessFile2.read(bArr);
                                if (read == -1) {
                                    break;
                                }
                                messageDigest.update(bArr, 0, read);
                            }
                            StringBuilder sb = new StringBuilder(new BigInteger(1, messageDigest.digest()).toString(16));
                            while (sb.length() < 32) {
                                sb.insert(0, "0");
                            }
                            String sb2 = sb.toString();
                            try {
                                randomAccessFile2.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return sb2;
                        } catch (FileNotFoundException e2) {
                            e = e2;
                            randomAccessFile = randomAccessFile2;
                            e.printStackTrace();
                            if (randomAccessFile != null) {
                                randomAccessFile.close();
                            }
                            return "";
                        } catch (IOException e3) {
                            e = e3;
                            randomAccessFile = randomAccessFile2;
                            e.printStackTrace();
                            if (randomAccessFile != null) {
                                randomAccessFile.close();
                            }
                            return "";
                        } catch (NoSuchAlgorithmException e4) {
                            e = e4;
                            randomAccessFile = randomAccessFile2;
                            e.printStackTrace();
                            if (randomAccessFile != null) {
                                randomAccessFile.close();
                            }
                            return "";
                        } catch (Throwable th) {
                            th = th;
                            randomAccessFile = randomAccessFile2;
                            if (randomAccessFile != null) {
                                try {
                                    randomAccessFile.close();
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    }
                    return "";
                } catch (IOException e6) {
                    e6.printStackTrace();
                    return "";
                }
            } catch (FileNotFoundException e7) {
                e = e7;
            } catch (IOException e8) {
                e = e8;
            } catch (NoSuchAlgorithmException e9) {
                e = e9;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
