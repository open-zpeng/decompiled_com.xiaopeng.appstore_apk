package com.xiaopeng.appstore.bizcommon.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import androidx.core.content.FileProvider;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.NumberUtils;
import com.xiaopeng.appstore.libcommon.utils.OSUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
/* loaded from: classes2.dex */
public class PackageUtils {
    public static final String ACTION_INSTALL_COMPLETE = "com.xiaopeng.xpstore.INSTALL_COMPLETE";
    private static final String ACTION_INSTALL_PACKAGE = "xp.intent.action.INSTALL_PACKAGE";
    private static final String ACTION_UNINSTALL_COMPLETE = "com.xiaopeng.xpstore.UNINSTALL_COMPLETE";
    private static final String ACTION_UNINSTALL_PACKAGE = "xp.intent.action.UNINSTALL_PACKAGE";
    public static final String EXTRA_XP_INSTALLER_PACKAGE_MANAGER = "xp.intent.extra.INSTALLER_PACKAGE_MANAGER";
    public static final int INSTALL_D_GEAR_APPLICATION_RUNNING = 33554432;
    public static final int INSTALL_N_GEAR_APPLICATION_RUNNING = 8388608;
    public static final int INSTALL_P_GEAR_APPLICATION_RUNNING = 16777216;
    public static final int INSTALL_R_GEAR_APPLICATION_RUNNING = 67108864;
    private static final String TAG = "PackageUtils";
    private static final String XP_APP_INSTALLER_PN = "com.xiaopeng.appinstaller";
    private static final String XP_INSTALLER_SERVICE = "com.xiaopeng.appinstaller.SilentInstallerService";

    public static boolean isUpdate(long localVersion, long remoteVersion) {
        return localVersion < remoteVersion;
    }

    public static int install(Context context, File file, String packageName, Bitmap icon) {
        Logger.t(TAG).d("install sync %s, file is [%s].", packageName, file.getAbsolutePath());
        PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
        PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(1);
        sessionParams.setAppIcon(icon);
        sessionParams.setAppPackageName(packageName);
        try {
            int createSession = packageInstaller.createSession(sessionParams);
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                PackageInstaller.Session openSession = packageInstaller.openSession(createSession);
                try {
                    long length = file.length();
                    OutputStream openWrite = openSession.openWrite(packageName, 0L, length);
                    byte[] bArr = new byte[32768];
                    while (length > 0) {
                        long j = 32768;
                        if (j >= length) {
                            j = length;
                        }
                        int read = fileInputStream.read(bArr, 0, (int) j);
                        openWrite.write(bArr, 0, read);
                        length -= read;
                    }
                    if (openWrite != null) {
                        openWrite.close();
                    }
                    openSession.commit(createInstallIntentSender(context, createSession));
                    if (openSession != null) {
                        openSession.close();
                    }
                    fileInputStream.close();
                } catch (Throwable th) {
                    if (openSession != null) {
                        try {
                            openSession.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (Exception e) {
                Logger.t(TAG).e("install sync error, msg is [%s]", e.getMessage());
                packageInstaller.abandonSession(createSession);
            }
            return createSession;
        } catch (IOException e2) {
            Logger.t(TAG).e("installAsync createSession error, msg is [%s]", e2.getMessage());
            return 0;
        }
    }

    public static void installAsync(final Context context, final File file, final String packageName, final Bitmap icon) {
        AppExecutors.get().diskIO().execute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.utils.-$$Lambda$PackageUtils$P5_2-Hi8skjZpXPgRsRvFwrh2CM
            @Override // java.lang.Runnable
            public final void run() {
                PackageUtils.install(context, file, packageName, icon);
            }
        });
    }

    public static int installSystem(Context context, File file, String packageName, Bitmap icon) throws IOException {
        Logger.d("installSystem file=" + file.getAbsolutePath() + " packageName=" + packageName);
        FileInputStream fileInputStream = new FileInputStream(file);
        PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
        PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(1);
        sessionParams.setAppIcon(icon);
        sessionParams.setAppPackageName(packageName);
        int createSession = packageInstaller.createSession(sessionParams);
        PackageInstaller.Session openSession = packageInstaller.openSession(createSession);
        OutputStream openWrite = openSession.openWrite(packageName, 0L, -1L);
        byte[] bArr = new byte[65536];
        while (true) {
            int read = fileInputStream.read(bArr);
            if (read != -1) {
                openWrite.write(bArr, 0, read);
            } else {
                openSession.fsync(openWrite);
                fileInputStream.close();
                openWrite.close();
                openSession.commit(createInstallIntentSender(context, createSession));
                return createSession;
            }
        }
    }

    public static void installNormal(Context context, File file) {
        Intent intent = new Intent(ACTION_INSTALL_PACKAGE);
        intent.setData(FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", file));
        intent.setFlags(268435456);
        intent.addFlags(1);
        context.startActivity(intent);
    }

    public static void uninstallNormal(Context context, String packageName) {
        Intent intent = new Intent("android.intent.action.UNINSTALL_PACKAGE");
        intent.setData(Uri.parse("package:" + packageName));
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void uninstallAsync(final Context context, final String packageName, final int requestCode) {
        AppExecutors.get().diskIO().execute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.utils.-$$Lambda$PackageUtils$uiy5gp6pBM_vIKD4EFnWp4wgv2Q
            @Override // java.lang.Runnable
            public final void run() {
                PackageUtils.uninstall(context, packageName, requestCode);
            }
        });
    }

    public static boolean uninstall(Context context, String packageName, int requestCode) {
        Logger.t(TAG).d("uninstallAsync start, pn is [%s].", packageName);
        context.getPackageManager().getPackageInstaller().uninstall(packageName, createUninstallIntentSender(context, packageName, requestCode));
        Logger.t(TAG).d("uninstallAsync finish, pn is [%s].", packageName);
        return true;
    }

    public static void installXp(Context context, Uri fileUri) {
        installWithService(context, fileUri);
    }

    private static void installWithActivity(Context context, File file) {
        Intent intent = new Intent(ACTION_INSTALL_PACKAGE);
        Uri uriForFile = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", file);
        context.grantUriPermission(XP_APP_INSTALLER_PN, uriForFile, 3);
        intent.setData(uriForFile);
        context.startActivity(intent);
    }

    private static void installWithService(Context context, Uri fileUri) {
        Intent intent = new Intent(ACTION_INSTALL_PACKAGE);
        intent.setComponent(new ComponentName(XP_APP_INSTALLER_PN, XP_INSTALLER_SERVICE));
        context.grantUriPermission(XP_APP_INSTALLER_PN, fileUri, 3);
        intent.setData(fileUri);
        context.startService(intent);
    }

    public static void uninstallXp(Context context, String packageName) {
        uninstallWithService(context, packageName);
    }

    private static void uninstallWithService(Context context, String packageName) {
        Intent intent = new Intent(ACTION_UNINSTALL_PACKAGE, Uri.parse("package:" + packageName));
        intent.setComponent(new ComponentName(XP_APP_INSTALLER_PN, XP_INSTALLER_SERVICE));
        context.startService(intent);
    }

    private static IntentSender createInstallIntentSender(Context context, int sessionId) {
        Intent intent = new Intent(ACTION_INSTALL_COMPLETE);
        intent.setFlags(268435456);
        return PendingIntent.getBroadcast(context, sessionId, intent, 134217728).getIntentSender();
    }

    private static IntentSender createUninstallIntentSender(Context context, String packageName, int requestCode) {
        Intent intent = new Intent(ACTION_UNINSTALL_COMPLETE);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", packageName);
        return PendingIntent.getBroadcast(context, requestCode, intent, 134217728).getIntentSender();
    }

    public static boolean isSystemApp(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        if (packageName != null) {
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
                if (packageInfo == null || packageInfo.applicationInfo == null) {
                    return false;
                }
                return (packageInfo.applicationInfo.flags & 1) != 0;
            } catch (PackageManager.NameNotFoundException unused) {
                return false;
            }
        }
        return false;
    }

    public static boolean isAppUpdate(String targetVersionCodeStr, String packageName) {
        PackageInfo packageInfo = AppComponentManager.get().getPackageInfo(packageName);
        if (packageInfo != null) {
            long longVersionCode = OSUtils.ATLEAST_P ? packageInfo.getLongVersionCode() : packageInfo.versionCode;
            Logger.t(TAG).i("isAppUpdate, pn=" + packageName + " remote=" + targetVersionCodeStr + ", local=" + longVersionCode, new Object[0]);
            return isUpdate(longVersionCode, NumberUtils.stringToLong(targetVersionCodeStr));
        }
        return false;
    }

    public static CharSequence getApplicationLabel(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, 0));
        } catch (PackageManager.NameNotFoundException unused) {
            Logger.t(TAG).w("getApplicationLabel not found this pkg:" + packageName, new Object[0]);
            return null;
        }
    }
}
