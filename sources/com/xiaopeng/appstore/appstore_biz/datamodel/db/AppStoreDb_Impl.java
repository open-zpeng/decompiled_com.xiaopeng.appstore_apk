package com.xiaopeng.appstore.appstore_biz.datamodel.db;

import android.os.Build;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppRemoteStateEntity;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
/* loaded from: classes2.dex */
public final class AppStoreDb_Impl extends AppStoreDb {
    private volatile AppRemoteStateDao _appRemoteStateDao;
    private volatile HomeDao _homeDao;

    @Override // androidx.room.RoomDatabase
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
        return configuration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(configuration.context).name(configuration.name).callback(new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(10) { // from class: com.xiaopeng.appstore.appstore_biz.datamodel.db.AppStoreDb_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPostMigrate(SupportSQLiteDatabase _db) {
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("CREATE TABLE IF NOT EXISTS `AppRemoteStates` (`packageName` TEXT NOT NULL, `state` INTEGER NOT NULL, `hasUpdate` INTEGER NOT NULL, `promptTitle` TEXT, `promptText` TEXT, PRIMARY KEY(`packageName`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `PagePackage` (`packageId` TEXT NOT NULL, `layoutType` INTEGER NOT NULL, `contentType` TEXT, `title` TEXT, `pageImageList` TEXT, PRIMARY KEY(`packageId`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `PackageItem` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `packageId` TEXT NOT NULL, `itemId` TEXT NOT NULL, `desc` TEXT, `size` TEXT, `md5` TEXT, `configMd5` TEXT, `status` TEXT, `title` TEXT, `packageName` TEXT, `downloadUrl` TEXT, `configUrl` TEXT, `versionCode` TEXT, `icon` TEXT, `adImage` TEXT)");
                _db.execSQL("CREATE INDEX IF NOT EXISTS `index_PackageItem_itemId_packageId` ON `PackageItem` (`itemId`, `packageId`)");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `ItemDependent` (`packageName` TEXT, `dependentId` TEXT NOT NULL, `dependentType` INTEGER NOT NULL, PRIMARY KEY(`dependentId`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `DbItemDependentCrossRef` (`itemId` TEXT NOT NULL, `dependentId` TEXT NOT NULL, PRIMARY KEY(`itemId`, `dependentId`), FOREIGN KEY(`dependentId`) REFERENCES `ItemDependent`(`dependentId`) ON UPDATE CASCADE ON DELETE CASCADE )");
                _db.execSQL("CREATE INDEX IF NOT EXISTS `index_DbItemDependentCrossRef_dependentId` ON `DbItemDependentCrossRef` (`dependentId`)");
                _db.execSQL(RoomMasterTable.CREATE_QUERY);
                _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '17463eeed5739d996773f9b386d6fd4a')");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("DROP TABLE IF EXISTS `AppRemoteStates`");
                _db.execSQL("DROP TABLE IF EXISTS `PagePackage`");
                _db.execSQL("DROP TABLE IF EXISTS `PackageItem`");
                _db.execSQL("DROP TABLE IF EXISTS `ItemDependent`");
                _db.execSQL("DROP TABLE IF EXISTS `DbItemDependentCrossRef`");
                if (AppStoreDb_Impl.this.mCallbacks != null) {
                    int size = AppStoreDb_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) AppStoreDb_Impl.this.mCallbacks.get(i)).onDestructiveMigration(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected void onCreate(SupportSQLiteDatabase _db) {
                if (AppStoreDb_Impl.this.mCallbacks != null) {
                    int size = AppStoreDb_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) AppStoreDb_Impl.this.mCallbacks.get(i)).onCreate(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(SupportSQLiteDatabase _db) {
                AppStoreDb_Impl.this.mDatabase = _db;
                _db.execSQL("PRAGMA foreign_keys = ON");
                AppStoreDb_Impl.this.internalInitInvalidationTracker(_db);
                if (AppStoreDb_Impl.this.mCallbacks != null) {
                    int size = AppStoreDb_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) AppStoreDb_Impl.this.mCallbacks.get(i)).onOpen(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPreMigrate(SupportSQLiteDatabase _db) {
                DBUtil.dropFtsSyncTriggers(_db);
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
                HashMap hashMap = new HashMap(5);
                hashMap.put(VuiConstants.SCENE_PACKAGE_NAME, new TableInfo.Column(VuiConstants.SCENE_PACKAGE_NAME, "TEXT", true, 1, null, 1));
                hashMap.put("state", new TableInfo.Column("state", "INTEGER", true, 0, null, 1));
                hashMap.put("hasUpdate", new TableInfo.Column("hasUpdate", "INTEGER", true, 0, null, 1));
                hashMap.put("promptTitle", new TableInfo.Column("promptTitle", "TEXT", false, 0, null, 1));
                hashMap.put("promptText", new TableInfo.Column("promptText", "TEXT", false, 0, null, 1));
                TableInfo tableInfo = new TableInfo(AppRemoteStateEntity.TABLE_NAME, hashMap, new HashSet(0), new HashSet(0));
                TableInfo read = TableInfo.read(_db, AppRemoteStateEntity.TABLE_NAME);
                if (!tableInfo.equals(read)) {
                    return new RoomOpenHelper.ValidationResult(false, "AppRemoteStates(com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppRemoteStateEntity).\n Expected:\n" + tableInfo + "\n Found:\n" + read);
                }
                HashMap hashMap2 = new HashMap(5);
                hashMap2.put("packageId", new TableInfo.Column("packageId", "TEXT", true, 1, null, 1));
                hashMap2.put("layoutType", new TableInfo.Column("layoutType", "INTEGER", true, 0, null, 1));
                hashMap2.put("contentType", new TableInfo.Column("contentType", "TEXT", false, 0, null, 1));
                hashMap2.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, 1));
                hashMap2.put("pageImageList", new TableInfo.Column("pageImageList", "TEXT", false, 0, null, 1));
                TableInfo tableInfo2 = new TableInfo("PagePackage", hashMap2, new HashSet(0), new HashSet(0));
                TableInfo read2 = TableInfo.read(_db, "PagePackage");
                if (!tableInfo2.equals(read2)) {
                    return new RoomOpenHelper.ValidationResult(false, "PagePackage(com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PagePackage).\n Expected:\n" + tableInfo2 + "\n Found:\n" + read2);
                }
                HashMap hashMap3 = new HashMap(15);
                hashMap3.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
                hashMap3.put("packageId", new TableInfo.Column("packageId", "TEXT", true, 0, null, 1));
                hashMap3.put("itemId", new TableInfo.Column("itemId", "TEXT", true, 0, null, 1));
                hashMap3.put("desc", new TableInfo.Column("desc", "TEXT", false, 0, null, 1));
                hashMap3.put("size", new TableInfo.Column("size", "TEXT", false, 0, null, 1));
                hashMap3.put("md5", new TableInfo.Column("md5", "TEXT", false, 0, null, 1));
                hashMap3.put("configMd5", new TableInfo.Column("configMd5", "TEXT", false, 0, null, 1));
                hashMap3.put("status", new TableInfo.Column("status", "TEXT", false, 0, null, 1));
                hashMap3.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, 1));
                hashMap3.put(VuiConstants.SCENE_PACKAGE_NAME, new TableInfo.Column(VuiConstants.SCENE_PACKAGE_NAME, "TEXT", false, 0, null, 1));
                hashMap3.put("downloadUrl", new TableInfo.Column("downloadUrl", "TEXT", false, 0, null, 1));
                hashMap3.put("configUrl", new TableInfo.Column("configUrl", "TEXT", false, 0, null, 1));
                hashMap3.put("versionCode", new TableInfo.Column("versionCode", "TEXT", false, 0, null, 1));
                hashMap3.put("icon", new TableInfo.Column("icon", "TEXT", false, 0, null, 1));
                hashMap3.put("adImage", new TableInfo.Column("adImage", "TEXT", false, 0, null, 1));
                HashSet hashSet = new HashSet(0);
                HashSet hashSet2 = new HashSet(1);
                hashSet2.add(new TableInfo.Index("index_PackageItem_itemId_packageId", false, Arrays.asList("itemId", "packageId")));
                TableInfo tableInfo3 = new TableInfo("PackageItem", hashMap3, hashSet, hashSet2);
                TableInfo read3 = TableInfo.read(_db, "PackageItem");
                if (!tableInfo3.equals(read3)) {
                    return new RoomOpenHelper.ValidationResult(false, "PackageItem(com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PackageItem).\n Expected:\n" + tableInfo3 + "\n Found:\n" + read3);
                }
                HashMap hashMap4 = new HashMap(3);
                hashMap4.put(VuiConstants.SCENE_PACKAGE_NAME, new TableInfo.Column(VuiConstants.SCENE_PACKAGE_NAME, "TEXT", false, 0, null, 1));
                hashMap4.put("dependentId", new TableInfo.Column("dependentId", "TEXT", true, 1, null, 1));
                hashMap4.put("dependentType", new TableInfo.Column("dependentType", "INTEGER", true, 0, null, 1));
                TableInfo tableInfo4 = new TableInfo("ItemDependent", hashMap4, new HashSet(0), new HashSet(0));
                TableInfo read4 = TableInfo.read(_db, "ItemDependent");
                if (!tableInfo4.equals(read4)) {
                    return new RoomOpenHelper.ValidationResult(false, "ItemDependent(com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.ItemDependent).\n Expected:\n" + tableInfo4 + "\n Found:\n" + read4);
                }
                HashMap hashMap5 = new HashMap(2);
                hashMap5.put("itemId", new TableInfo.Column("itemId", "TEXT", true, 1, null, 1));
                hashMap5.put("dependentId", new TableInfo.Column("dependentId", "TEXT", true, 2, null, 1));
                HashSet hashSet3 = new HashSet(1);
                hashSet3.add(new TableInfo.ForeignKey("ItemDependent", "CASCADE", "CASCADE", Arrays.asList("dependentId"), Arrays.asList("dependentId")));
                HashSet hashSet4 = new HashSet(1);
                hashSet4.add(new TableInfo.Index("index_DbItemDependentCrossRef_dependentId", false, Arrays.asList("dependentId")));
                TableInfo tableInfo5 = new TableInfo("DbItemDependentCrossRef", hashMap5, hashSet3, hashSet4);
                TableInfo read5 = TableInfo.read(_db, "DbItemDependentCrossRef");
                if (!tableInfo5.equals(read5)) {
                    return new RoomOpenHelper.ValidationResult(false, "DbItemDependentCrossRef(com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.DbItemDependentCrossRef).\n Expected:\n" + tableInfo5 + "\n Found:\n" + read5);
                }
                return new RoomOpenHelper.ValidationResult(true, null);
            }
        }, "17463eeed5739d996773f9b386d6fd4a", "05130522f9c8d32440c4165432ecf62a")).build());
    }

    @Override // androidx.room.RoomDatabase
    protected InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), AppRemoteStateEntity.TABLE_NAME, "PagePackage", "PackageItem", "ItemDependent", "DbItemDependentCrossRef");
    }

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        boolean z = Build.VERSION.SDK_INT >= 21;
        if (!z) {
            try {
                writableDatabase.execSQL("PRAGMA foreign_keys = FALSE");
            } finally {
                super.endTransaction();
                if (!z) {
                    writableDatabase.execSQL("PRAGMA foreign_keys = TRUE");
                }
                writableDatabase.query("PRAGMA wal_checkpoint(FULL)").close();
                if (!writableDatabase.inTransaction()) {
                    writableDatabase.execSQL("VACUUM");
                }
            }
        }
        super.beginTransaction();
        if (z) {
            writableDatabase.execSQL("PRAGMA defer_foreign_keys = TRUE");
        }
        writableDatabase.execSQL("DELETE FROM `AppRemoteStates`");
        writableDatabase.execSQL("DELETE FROM `PagePackage`");
        writableDatabase.execSQL("DELETE FROM `PackageItem`");
        writableDatabase.execSQL("DELETE FROM `ItemDependent`");
        writableDatabase.execSQL("DELETE FROM `DbItemDependentCrossRef`");
        super.setTransactionSuccessful();
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.AppStoreDb
    public HomeDao getHomeDao() {
        HomeDao homeDao;
        if (this._homeDao != null) {
            return this._homeDao;
        }
        synchronized (this) {
            if (this._homeDao == null) {
                this._homeDao = new HomeDao_Impl(this);
            }
            homeDao = this._homeDao;
        }
        return homeDao;
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.AppStoreDb
    public AppRemoteStateDao getAppRemoteStateDao() {
        AppRemoteStateDao appRemoteStateDao;
        if (this._appRemoteStateDao != null) {
            return this._appRemoteStateDao;
        }
        synchronized (this) {
            if (this._appRemoteStateDao == null) {
                this._appRemoteStateDao = new AppRemoteStateDao_Impl(this);
            }
            appRemoteStateDao = this._appRemoteStateDao;
        }
        return appRemoteStateDao;
    }
}
