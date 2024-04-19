package com.xiaopeng.appstore.libcommon.utils;

import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.orhanobut.logger.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
/* loaded from: classes2.dex */
public class FileUtils {
    public static String loadFileFromAsset(String fileName) {
        try {
            return getStrFromStream(Utils.getApp().getAssets().open(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String loadFile(File file) {
        if (file == null) {
            return null;
        }
        try {
            return getStrFromStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isJson(File file) {
        String name;
        int lastIndexOf;
        return (file == null || (lastIndexOf = (name = file.getName()).lastIndexOf(".")) == -1 || TextUtils.isEmpty(name) || !name.substring(lastIndexOf).toLowerCase().equals(".json")) ? false : true;
    }

    public static String getPath(String uriString) {
        return Uri.parse(uriString).getPath();
    }

    public static boolean isApk(File file) {
        if (file != null) {
            String name = file.getName();
            return !TextUtils.isEmpty(name) && name.substring(name.lastIndexOf(".")).toLowerCase().equals(".apk");
        }
        return false;
    }

    public static String getStrFromStream(InputStream stream) {
        try {
            byte[] bArr = new byte[stream.available()];
            stream.read(bArr);
            stream.close();
            return new String(bArr, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String md5(File file) {
        int i;
        String str = "";
        if (file == null || !file.isFile() || !file.exists()) {
            return "";
        }
        FileInputStream fileInputStream = null;
        byte[] bArr = new byte[8192];
        try {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                FileInputStream fileInputStream2 = new FileInputStream(file);
                while (true) {
                    try {
                        int read = fileInputStream2.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        messageDigest.update(bArr, 0, read);
                    } catch (Exception e2) {
                        e = e2;
                        fileInputStream = fileInputStream2;
                        e.printStackTrace();
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        Logger.t("yjk").d("md5:  " + str);
                        return str;
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
                fileInputStream2.close();
                for (byte b : messageDigest.digest()) {
                    String hexString = Integer.toHexString(b & 255);
                    if (hexString.length() == 1) {
                        hexString = "0" + hexString;
                    }
                    str = str + hexString;
                }
                fileInputStream2.close();
            } catch (Exception e4) {
                e = e4;
            }
            Logger.t("yjk").d("md5:  " + str);
            return str;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static File getFileFromContentUri(Uri contentUri) {
        String str;
        if (contentUri == null) {
            return null;
        }
        String[] strArr = {"_data", "_display_name"};
        Cursor query = Utils.getApp().getContentResolver().query(contentUri, strArr, null, null, null);
        if (query == null || query.getCount() <= 0) {
            return null;
        }
        query.moveToFirst();
        try {
            str = query.getString(query.getColumnIndex(strArr[0]));
        } catch (Exception unused) {
            str = null;
        }
        query.close();
        return !TextUtils.isEmpty(str) ? new File(str) : TextUtils.isEmpty(str) ? null : new File(str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x005d, code lost:
        throw new java.io.FileNotFoundException("Failed to ensure directory: " + r4.getAbsolutePath());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void unzip(java.io.File r6, java.io.File r7) throws java.io.IOException {
        /*
            java.util.zip.ZipInputStream r0 = new java.util.zip.ZipInputStream
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream
            java.nio.file.Path r6 = r6.toPath()
            r2 = 0
            java.nio.file.OpenOption[] r3 = new java.nio.file.OpenOption[r2]
            java.io.InputStream r6 = java.nio.file.Files.newInputStream(r6, r3)
            r1.<init>(r6)
            r0.<init>(r1)
            r6 = 8192(0x2000, float:1.14794E-41)
            byte[] r6 = new byte[r6]     // Catch: java.lang.Throwable -> L90
        L19:
            java.util.zip.ZipEntry r1 = r0.getNextEntry()     // Catch: java.lang.Throwable -> L90
            if (r1 == 0) goto L8c
            java.io.File r3 = new java.io.File     // Catch: java.lang.Throwable -> L90
            java.lang.String r4 = r1.getName()     // Catch: java.lang.Throwable -> L90
            r3.<init>(r7, r4)     // Catch: java.lang.Throwable -> L90
            boolean r4 = r1.isDirectory()     // Catch: java.lang.Throwable -> L90
            if (r4 == 0) goto L30
            r4 = r3
            goto L34
        L30:
            java.io.File r4 = r3.getParentFile()     // Catch: java.lang.Throwable -> L90
        L34:
            boolean r5 = r4.isDirectory()     // Catch: java.lang.Throwable -> L90
            if (r5 != 0) goto L5e
            boolean r5 = r4.mkdirs()     // Catch: java.lang.Throwable -> L90
            if (r5 == 0) goto L41
            goto L5e
        L41:
            java.io.FileNotFoundException r6 = new java.io.FileNotFoundException     // Catch: java.lang.Throwable -> L90
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L90
            r7.<init>()     // Catch: java.lang.Throwable -> L90
            java.lang.String r1 = "Failed to ensure directory: "
            java.lang.StringBuilder r7 = r7.append(r1)     // Catch: java.lang.Throwable -> L90
            java.lang.String r1 = r4.getAbsolutePath()     // Catch: java.lang.Throwable -> L90
            java.lang.StringBuilder r7 = r7.append(r1)     // Catch: java.lang.Throwable -> L90
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L90
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L90
            throw r6     // Catch: java.lang.Throwable -> L90
        L5e:
            boolean r1 = r1.isDirectory()     // Catch: java.lang.Throwable -> L90
            if (r1 == 0) goto L65
            goto L19
        L65:
            java.nio.file.Path r1 = r3.toPath()     // Catch: java.lang.Throwable -> L90
            java.nio.file.OpenOption[] r3 = new java.nio.file.OpenOption[r2]     // Catch: java.lang.Throwable -> L90
            java.io.OutputStream r1 = java.nio.file.Files.newOutputStream(r1, r3)     // Catch: java.lang.Throwable -> L90
        L6f:
            int r3 = r0.read(r6)     // Catch: java.lang.Throwable -> L80
            r4 = -1
            if (r3 == r4) goto L7a
            r1.write(r6, r2, r3)     // Catch: java.lang.Throwable -> L80
            goto L6f
        L7a:
            if (r1 == 0) goto L19
            r1.close()     // Catch: java.lang.Throwable -> L90
            goto L19
        L80:
            r6 = move-exception
            if (r1 == 0) goto L8b
            r1.close()     // Catch: java.lang.Throwable -> L87
            goto L8b
        L87:
            r7 = move-exception
            r6.addSuppressed(r7)     // Catch: java.lang.Throwable -> L90
        L8b:
            throw r6     // Catch: java.lang.Throwable -> L90
        L8c:
            r0.close()
            return
        L90:
            r6 = move-exception
            r0.close()     // Catch: java.lang.Throwable -> L95
            goto L99
        L95:
            r7 = move-exception
            r6.addSuppressed(r7)
        L99:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.libcommon.utils.FileUtils.unzip(java.io.File, java.io.File):void");
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        FileChannel channel = new FileInputStream(sourceFile).getChannel();
        try {
            FileChannel channel2 = new FileOutputStream(destFile).getChannel();
            channel2.transferFrom(channel, 0L, channel.size());
            if (channel2 != null) {
                channel2.close();
            }
            if (channel != null) {
                channel.close();
            }
        } catch (Throwable th) {
            if (channel != null) {
                try {
                    channel.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
