package com.xiaopeng.appstore.installer;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailData;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppRequestContainer;
import com.xiaopeng.appstore.appstore_biz.logic.CheckUpdateManager;
import com.xiaopeng.appstore.bizcommon.utils.PackageUtils;
import com.xiaopeng.appstore.libcommon.utils.NumberUtils;
import com.xiaopeng.lib.framework.aiassistantmodule.sensor.ContextSensor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/* loaded from: classes.dex */
public class StartInstallService extends IntentService {
    private static final String ACTION_INSTALL = "com.xiaopeng.appstore.installer.action.install";
    private static final String TAG = "StartInstallService";
    private File mStagedFile;

    public StartInstallService() {
        super(TAG);
        Logger.t(TAG).i("Construct service:" + this, new Object[0]);
    }

    public static void startActionInstall(Context context, Uri fileUri, int callingUid) {
        Intent intent = new Intent(context, StartInstallService.class);
        intent.setAction(ACTION_INSTALL);
        intent.setData(fileUri);
        intent.putExtra(Constants.EXTRA_ORIGINATING_UID, callingUid);
        intent.addFlags(1);
        context.startService(intent);
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        if (intent == null || !ACTION_INSTALL.equals(intent.getAction())) {
            return;
        }
        Uri data = intent.getData();
        int intExtra = intent.getIntExtra(Constants.EXTRA_ORIGINATING_UID, -1);
        Set<String> callingPackages = getCallingPackages((ActivityManager) getSystemService("activity"), intExtra);
        Logger.t(TAG).d("ACTION_INSTALL start, uri:" + data + ", calling:" + callingPackages);
        InstallBean handleActionInstall = handleActionInstall(data, callingPackages);
        Logger.t(TAG).d("ACTION_INSTALL, ret:" + handleActionInstall);
        Intent intent2 = new Intent(this, InstallDialogActivity.class);
        intent2.putExtra(InstallDialogActivity.EXTRA_RESULT, handleActionInstall);
        intent2.putExtra(Constants.EXTRA_ORIGINATING_UID, intExtra);
        intent2.addFlags(268435456);
        intent2.setData(data);
        startActivity(intent2);
        clearTempFile();
    }

    @Override // android.app.IntentService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Logger.t(TAG).i("Destroy:" + this, new Object[0]);
    }

    private InstallBean handleActionInstall(Uri fileUri, Set<String> callingPkgs) {
        if (fileUri == null) {
            Logger.t(TAG).w("handleActionInstall, fail, uri is null", new Object[0]);
            return new InstallBean(0);
        }
        File apkFile = getApkFile(fileUri);
        if (apkFile == null) {
            Logger.t(TAG).w("handleActionInstall, fail, apkFile get fail, uri:" + fileUri, new Object[0]);
            return new InstallBean(0);
        }
        PackageInfo parsePackageInfo = parsePackageInfo(getPackageManager(), apkFile);
        if (parsePackageInfo == null) {
            Logger.t(TAG).w("handleActionInstall, fail, parse apk fail, uri:" + fileUri, new Object[0]);
            return new InstallBean(0);
        }
        PackageInfo queryPackageInfo = queryPackageInfo(parsePackageInfo.packageName);
        if (!(queryPackageInfo != null) || callingPkgs == null || !callingPkgs.contains(queryPackageInfo.packageName)) {
            return new InstallBean(100, queryPackageInfo);
        }
        Logger.t(TAG).d("handleActionInstall, start request detail data: " + queryPackageInfo.packageName + ", ver:" + queryPackageInfo.getLongVersionCode());
        List<AppDetailData> requestData = requestData(queryPackageInfo);
        if (requestData == null) {
            Logger.t(TAG).w("handleActionInstall, fail, request fail, uri:" + fileUri, new Object[0]);
            return new InstallBean(200, parsePackageInfo);
        }
        AppDetailData findAppDetailData = findAppDetailData(requestData, parsePackageInfo.packageName);
        if (findAppDetailData == null) {
            Logger.t(TAG).w("handleActionInstall, fail, no data in appstore, uri:" + fileUri, new Object[0]);
            return new InstallBean(100, parsePackageInfo);
        }
        if (PackageUtils.isUpdate(queryPackageInfo.getLongVersionCode(), NumberUtils.stringToLong(findAppDetailData.getVersionCode()))) {
            Logger.t(TAG).i("handleActionInstall, suggest to go to xpeng appstore:" + findAppDetailData, new Object[0]);
            return new InstallBean(305, parsePackageInfo);
        }
        Logger.t(TAG).i("handleActionInstall, upgrade is forbidden:" + fileUri, new Object[0]);
        return new InstallBean(300, parsePackageInfo);
    }

    private File getApkFile(Uri fileUri) {
        if (this.mStagedFile == null) {
            try {
                this.mStagedFile = getStagedFile(this);
            } catch (IOException e) {
                Logger.t(TAG).w("getStagedFile ex:" + e + ", uri:" + fileUri, new Object[0]);
                e.printStackTrace();
                return null;
            }
        }
        if (!this.mStagedFile.exists()) {
            Logger.t(TAG).w("StagedFile not exist:" + this.mStagedFile, new Object[0]);
            return null;
        }
        try {
            InputStream openInputStream = getContentResolver().openInputStream(fileUri);
            if (openInputStream == null) {
                if (openInputStream != null) {
                    openInputStream.close();
                }
                return null;
            }
            try {
                File file = this.mStagedFile;
                if (file == null) {
                    if (openInputStream != null) {
                        openInputStream.close();
                    }
                    return null;
                }
                OutputStream newOutputStream = Files.newOutputStream(file.toPath(), new OpenOption[0]);
                byte[] bArr = new byte[1048576];
                while (true) {
                    int read = openInputStream.read(bArr);
                    if (read < 0) {
                        break;
                    }
                    newOutputStream.write(bArr, 0, read);
                }
                if (newOutputStream != null) {
                    newOutputStream.close();
                }
                if (openInputStream != null) {
                    openInputStream.close();
                }
                return this.mStagedFile;
            } catch (Throwable th) {
                if (openInputStream != null) {
                    try {
                        openInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException | IllegalStateException | SecurityException e2) {
            Logger.t(TAG).w("Error staging apk from content URI, ex: %s", e2);
            e2.printStackTrace();
            return null;
        }
    }

    private void clearTempFile() {
        File file = this.mStagedFile;
        if (file == null || !file.exists()) {
            return;
        }
        Logger.t(TAG).i("delete file:" + this.mStagedFile + ", deleted:" + this.mStagedFile.delete(), new Object[0]);
    }

    private PackageInfo parsePackageInfo(PackageManager packageManager, File apkFile) {
        ApplicationInfo applicationInfo;
        PackageInfo packageInfo = null;
        try {
            String absolutePath = apkFile.getAbsolutePath();
            packageInfo = packageManager.getPackageArchiveInfo(absolutePath, 0);
            if (packageInfo != null && (applicationInfo = packageInfo.applicationInfo) != null) {
                applicationInfo.sourceDir = absolutePath;
                applicationInfo.publicSourceDir = absolutePath;
            }
        } catch (Exception e) {
            Logger.t(TAG).w("parsePackageInfo ex:" + e, new Object[0]);
            e.printStackTrace();
        }
        return packageInfo;
    }

    private List<AppDetailData> requestData(PackageInfo pkgInfo) {
        return CheckUpdateManager.requestUpdateSync(parsePackageInfoList(Collections.singletonList(pkgInfo)));
    }

    private AppDetailData findAppDetailData(List<AppDetailData> remoteData, String packageName) {
        for (AppDetailData appDetailData : remoteData) {
            if (packageName.equals(appDetailData.getPackageName())) {
                Logger.t(TAG).d("findAppDetailData, return first match:" + appDetailData);
                return appDetailData;
            }
        }
        Logger.t(TAG).d("findAppDetailData, no match data:" + packageName + ", list:" + remoteData);
        return null;
    }

    private PackageInfo queryPackageInfo(String packageName) {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(packageName, 0);
            Logger.t(TAG).i("queryPackageInfo, pkg:" + packageName + ", verCode:" + packageInfo.getLongVersionCode() + ", verName:" + packageInfo.versionName, new Object[0]);
            return packageInfo;
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0083  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean queryIsAssembling(java.lang.String r9) {
        /*
            r8 = this;
            android.content.ContentResolver r0 = r8.getContentResolver()
            android.net.Uri r1 = com.xiaopeng.appstore.storeprovider.ResourceProviderContract.ASSEMBLE_URI
            r6 = 1
            java.lang.String[] r4 = new java.lang.String[r6]
            r7 = 0
            r4[r7] = r9
            r2 = 0
            java.lang.String r3 = "key LIKE ?"
            r5 = 0
            android.database.Cursor r0 = r0.query(r1, r2, r3, r4, r5)
            java.lang.String r1 = "StartInstallService"
            if (r0 == 0) goto L67
            int r2 = r0.getCount()     // Catch: java.lang.Throwable -> L65
            if (r2 == 0) goto L67
            boolean r2 = r0.moveToFirst()     // Catch: java.lang.Throwable -> L65
            if (r2 == 0) goto L81
            r2 = 5
            int r2 = r0.getInt(r2)     // Catch: java.lang.Throwable -> L65
            com.orhanobut.logger.Printer r1 = com.orhanobut.logger.Logger.t(r1)     // Catch: java.lang.Throwable -> L65
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L65
            r3.<init>()     // Catch: java.lang.Throwable -> L65
            java.lang.String r4 = "queryIsAssembling finish: pn="
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Throwable -> L65
            java.lang.StringBuilder r9 = r3.append(r9)     // Catch: java.lang.Throwable -> L65
            java.lang.String r3 = " state="
            java.lang.StringBuilder r9 = r9.append(r3)     // Catch: java.lang.Throwable -> L65
            java.lang.String r3 = com.xiaopeng.appstore.storeprovider.AssembleInfo.stateToStr(r2)     // Catch: java.lang.Throwable -> L65
            java.lang.StringBuilder r9 = r9.append(r3)     // Catch: java.lang.Throwable -> L65
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L65
            r1.d(r9)     // Catch: java.lang.Throwable -> L65
            boolean r9 = com.xiaopeng.appstore.storeprovider.AssembleInfo.isRunning(r2)     // Catch: java.lang.Throwable -> L65
            if (r9 != 0) goto L5f
            boolean r9 = com.xiaopeng.appstore.storeprovider.AssembleInfo.isPreparing(r2)     // Catch: java.lang.Throwable -> L65
            if (r9 == 0) goto L5e
            goto L5f
        L5e:
            r6 = r7
        L5f:
            if (r0 == 0) goto L64
            r0.close()
        L64:
            return r6
        L65:
            r9 = move-exception
            goto L87
        L67:
            com.orhanobut.logger.Printer r1 = com.orhanobut.logger.Logger.t(r1)     // Catch: java.lang.Throwable -> L65
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L65
            r2.<init>()     // Catch: java.lang.Throwable -> L65
            java.lang.String r3 = "queryIsAssembling finish: no data relative to "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch: java.lang.Throwable -> L65
            java.lang.StringBuilder r9 = r2.append(r9)     // Catch: java.lang.Throwable -> L65
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L65
            r1.d(r9)     // Catch: java.lang.Throwable -> L65
        L81:
            if (r0 == 0) goto L86
            r0.close()
        L86:
            return r7
        L87:
            if (r0 == 0) goto L91
            r0.close()     // Catch: java.lang.Throwable -> L8d
            goto L91
        L8d:
            r0 = move-exception
            r9.addSuppressed(r0)
        L91:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.installer.StartInstallService.queryIsAssembling(java.lang.String):boolean");
    }

    private Set<String> getCallingPackages(ActivityManager am, int uid) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        if (runningAppProcesses == null || runningAppProcesses.isEmpty()) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.uid == uid) {
                return new HashSet(Arrays.asList(runningAppProcessInfo.pkgList));
            }
        }
        return null;
    }

    public static File getStagedFile(Context context) throws IOException {
        Logger.t(TAG).i("getStagedFile", new Object[0]);
        return File.createTempFile(ContextSensor.DATA_PACKAGE, ".apk", context.getNoBackupFilesDir());
    }

    private static List<AppRequestContainer> parsePackageInfoList(Collection<PackageInfo> packageList) {
        if (packageList == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (PackageInfo packageInfo : packageList) {
            AppRequestContainer appRequestContainer = new AppRequestContainer();
            String str = packageInfo.packageName;
            long longVersionCode = packageInfo.getLongVersionCode();
            appRequestContainer.setPackageName(str);
            appRequestContainer.setVersionCode(longVersionCode);
            arrayList.add(appRequestContainer);
        }
        return arrayList;
    }
}
