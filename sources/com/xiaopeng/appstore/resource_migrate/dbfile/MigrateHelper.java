package com.xiaopeng.appstore.resource_migrate.dbfile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.FileProvider;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes2.dex */
public class MigrateHelper {
    private static final String CACHE_FOLDER = "migrate_db/";
    public static final String PACKAGE_NAME = "com.xiaopeng.resourceservice";
    private static final String RES_DB_NAME = "Resource.db";
    public static final String SERVICE = "com.xiaopeng.appstore.resourceservice.migrate.MigrateService";
    private static final String TAG = "MigrateHelper";
    private static final String TASK_DB_NAME = "TaskDatabase.db";

    public static void startMigrate(final Context context) {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.resource_migrate.dbfile.-$$Lambda$MigrateHelper$kMD9e-4hgCkhLxkgoMYUBLjK-nQ
            @Override // java.lang.Runnable
            public final void run() {
                MigrateHelper.lambda$startMigrate$0(context);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$startMigrate$0(Context context) {
        ArrayList arrayList = new ArrayList();
        List<Uri> copyDbFileToCache = copyDbFileToCache(context, RES_DB_NAME);
        if (copyDbFileToCache != null) {
            arrayList.addAll(copyDbFileToCache);
        }
        List<Uri> copyDbFileToCache2 = copyDbFileToCache(context, TASK_DB_NAME);
        if (copyDbFileToCache2 != null) {
            arrayList.addAll(copyDbFileToCache2);
        }
        startService(context, arrayList);
    }

    public static void deleteCache(final Context context) {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.resource_migrate.dbfile.-$$Lambda$MigrateHelper$axWTgSLi0y5qN43IvSDCLfkOD3Y
            @Override // java.lang.Runnable
            public final void run() {
                MigrateHelper.lambda$deleteCache$1(context);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$deleteCache$1(Context context) {
        ArrayList<File> arrayList = new ArrayList();
        File file = new File(context.getCacheDir(), CACHE_FOLDER);
        List<File> cacheFile = getCacheFile(file, RES_DB_NAME);
        if (cacheFile != null) {
            arrayList.addAll(cacheFile);
        }
        List<File> cacheFile2 = getCacheFile(file, TASK_DB_NAME);
        if (cacheFile2 != null) {
            arrayList.addAll(cacheFile2);
        }
        arrayList.add(file);
        for (File file2 : arrayList) {
            if (file2 != null && file2.exists()) {
                Logger.t(TAG).i("delete cache file:" + file2 + ", deleted:" + file2.delete(), new Object[0]);
            }
        }
    }

    private static List<File> getCacheFile(File cacheFolder, String dbName) {
        String[] strArr = {dbName, dbName + "-wal", dbName + "-shm"};
        ArrayList arrayList = new ArrayList(3);
        for (int i = 0; i < 3; i++) {
            arrayList.add(new File(cacheFolder, strArr[i]));
        }
        return arrayList;
    }

    private static List<Uri> copyDbFileToCache(Context context, String dbFileName) {
        String absolutePath = context.getDatabasePath(dbFileName).getAbsolutePath();
        String[] strArr = {absolutePath, absolutePath + "-shm", absolutePath + "-wal"};
        File file = new File(context.getCacheDir(), CACHE_FOLDER);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            Logger.t(TAG).i("create dir:" + file + ", ret:" + mkdirs, new Object[0]);
            if (!mkdirs) {
                return null;
            }
        }
        ArrayList arrayList = new ArrayList(3);
        for (int i = 0; i < 3; i++) {
            File file2 = new File(strArr[i]);
            if (!file2.exists()) {
                Logger.t(TAG).i("copyDbFileToCache, db:" + dbFileName + ", not exists:" + file2, new Object[0]);
            } else {
                File file3 = new File(file, file2.getName());
                if (!file3.exists()) {
                    file3.delete();
                }
                try {
                    Logger.t(TAG).i("copyDbFileToCache, db:" + dbFileName + ", origin:" + file2 + ", dest:" + file3, new Object[0]);
                    FileUtils.copyFile(file2, file3);
                    Logger.t(TAG).i("copyDbFileToCache, dest size:" + file3.length(), new Object[0]);
                    arrayList.add(getContentUri(context, file3));
                } catch (IOException e) {
                    Logger.t(TAG).w("copyDbToCache ex, file:" + file2 + ", dest:" + file3, new Object[0]);
                    e.printStackTrace();
                }
            }
        }
        return arrayList;
    }

    private static Uri getContentUri(Context context, File file) {
        return FileProvider.getUriForFile(context, "com.xiaopeng.appstore.fileprovider", file);
    }

    public static void startService(Context context, Uri file) {
        Intent intent = new Intent("com.xiaopeng.appstore.resource.action.migrate");
        intent.setClassName("com.xiaopeng.resourceservice", SERVICE);
        intent.setData(file);
        intent.addFlags(3);
        context.startForegroundService(intent);
    }

    public static void startService(Context context, ArrayList<Uri> fileList) {
        Iterator<Uri> it = fileList.iterator();
        while (it.hasNext()) {
            context.grantUriPermission("com.xiaopeng.resourceservice", it.next(), 1);
        }
        Intent intent = new Intent("com.xiaopeng.resourceservice.action.MIGRATE");
        intent.setClassName("com.xiaopeng.resourceservice", SERVICE);
        intent.putParcelableArrayListExtra("android.intent.extra.STREAM", fileList);
        context.startForegroundService(intent);
    }
}
