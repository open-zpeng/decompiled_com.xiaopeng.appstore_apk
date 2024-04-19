package com.xiaopeng.appstore.appstore_biz.bizusb.util;

import android.content.pm.PackageInfo;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.bizusb.datamodel.entities.LocalApkInfo;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.libcommon.utils.FileUtils;
import com.xiaopeng.appstore.libcommon.utils.ImageUtils;
import com.xiaopeng.appstore.libcommon.utils.OSUtils;
import com.xiaopeng.appstore.xpcommon.HiddenStorageMgrUtils;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes2.dex */
public class Utils {
    private static final String TAG = "USBUtils";

    public static ArrayList<File> getUSBFileFromStorage() {
        return HiddenStorageMgrUtils.getUSBFileFromStorage(com.xiaopeng.appstore.libcommon.utils.Utils.getApp());
    }

    public static ArrayList<File> getAllApkFileFromList(List<File> files) {
        ArrayList<File> arrayList = new ArrayList<>();
        if (files != null) {
            try {
                for (File file : files) {
                    arrayList.addAll(getAllApkFile(file));
                }
            } catch (Exception e) {
                Logger.t(TAG).e("getAllApkFileFromList:  " + e.getMessage(), new Object[0]);
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public static ArrayList<LocalApkInfo> getAllPackageInfo(List<File> files) {
        try {
            ArrayList<LocalApkInfo> arrayList = new ArrayList<>();
            if (files != null) {
                for (File file : files) {
                    LocalApkInfo packageNameFromApkFile = getPackageNameFromApkFile(file);
                    if (packageNameFromApkFile != null) {
                        arrayList.add(packageNameFromApkFile);
                    }
                }
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<File> getAllApkFile(File file) {
        return getAllFile(file, "apk", new ArrayList());
    }

    private static ArrayList<File> getAllFile(File file, final String type, final ArrayList<File> list) {
        if (file != null) {
            try {
                if (file.exists()) {
                    Logger.t(TAG).i("getAllFile, ret:" + file.listFiles(new FileFilter() { // from class: com.xiaopeng.appstore.appstore_biz.bizusb.util.-$$Lambda$Utils$0GuazqznHS9aCbapxrAoZ18nZL4
                        @Override // java.io.FileFilter
                        public final boolean accept(File file2) {
                            return Utils.lambda$getAllFile$0(type, list, file2);
                        }
                    }).length, new Object[0]);
                    return list;
                }
            } catch (Exception e) {
                Logger.t(TAG).e("getAllFile:  " + e.getMessage(), new Object[0]);
                e.printStackTrace();
                return list;
            }
        }
        Logger.t(TAG).i("getAllFile, file not available:" + file, new Object[0]);
        return list;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$getAllFile$0(String str, ArrayList arrayList, File file) {
        if (file.isFile() && file.getName().endsWith(str) && file.exists()) {
            arrayList.add(file);
            return true;
        }
        if (file.isDirectory()) {
            Logger.t(TAG).i("getAllFile, current is Directory:" + file.getPath(), new Object[0]);
            getAllFile(file, str, arrayList);
        } else {
            Logger.t(TAG).d("getAllFile, not correct file:" + file.getPath());
        }
        return false;
    }

    public static ArrayList<LocalApkInfo> getInstalledApk(ArrayList<LocalApkInfo> list) {
        ArrayList<LocalApkInfo> arrayList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            Iterator<LocalApkInfo> it = list.iterator();
            while (it.hasNext()) {
                LocalApkInfo next = it.next();
                next.setInstalled(AppComponentManager.get().isInstalled(next.getPackageName()));
                ImageUtils.loadBitmap(next.getIconUrl(), 104, 104);
                next.setBitmap(ImageUtils.loadBitmap(next.getIconUrl(), 104, 104));
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public static LocalApkInfo getPackageNameFromApkFile(File file) {
        if (file != null) {
            try {
                if (file.exists()) {
                    String md5 = FileUtils.md5(file);
                    PackageInfo packageArchiveInfo = com.xiaopeng.appstore.libcommon.utils.Utils.getApp().getPackageManager().getPackageArchiveInfo(file.getAbsolutePath(), 1);
                    if (packageArchiveInfo != null) {
                        String str = packageArchiveInfo.applicationInfo.packageName;
                        String str2 = packageArchiveInfo.versionName;
                        long longVersionCode = OSUtils.ATLEAST_P ? packageArchiveInfo.getLongVersionCode() : packageArchiveInfo.versionCode;
                        Logger.t(TAG).d(String.format("PackageName: %s,  Version: %s , AppName: %s ,VersionCode: %s , MD5: %s", str, str2, "", Long.valueOf(longVersionCode), md5));
                        return new LocalApkInfo(str, String.valueOf(longVersionCode), md5, file);
                    }
                    return null;
                }
                return null;
            } catch (Exception e) {
                Logger.t(TAG).e("getPackageNameFromApkFile:  " + e.getMessage(), new Object[0]);
                return null;
            }
        }
        return null;
    }
}
