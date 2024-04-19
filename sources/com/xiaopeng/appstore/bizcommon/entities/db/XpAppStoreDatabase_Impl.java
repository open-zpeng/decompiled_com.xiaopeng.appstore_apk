package com.xiaopeng.appstore.bizcommon.entities.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.lzy.okgo.model.Progress;
import com.xiaopeng.appstore.bizcommon.entities.AgreementStateEntity;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreContract;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.HashMap;
import java.util.HashSet;
/* loaded from: classes2.dex */
public final class XpAppStoreDatabase_Impl extends XpAppStoreDatabase {
    private volatile AgreementStateDao _agreementStateDao;
    private volatile AppConfigDao _appConfigDao;
    private volatile AssembleDao _assembleDao;
    private volatile DownloadCenterDao _downloadCenterDao;
    private volatile XpOkDownloadDao _xpOkDownloadDao;

    @Override // androidx.room.RoomDatabase
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
        return configuration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(configuration.context).name(configuration.name).callback(new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(13) { // from class: com.xiaopeng.appstore.bizcommon.entities.db.XpAppStoreDatabase_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPostMigrate(SupportSQLiteDatabase _db) {
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("CREATE TABLE IF NOT EXISTS `XpOkDownloadInfo` (`id` INTEGER NOT NULL, `url` TEXT NOT NULL, `tag` TEXT NOT NULL, `downloadId` INTEGER NOT NULL, `status` INTEGER NOT NULL, PRIMARY KEY(`id`, `url`, `tag`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `AssembleLocalInfo` (`assembleId` INTEGER NOT NULL, `packageName` TEXT, `downloadUrl` TEXT, `md5` TEXT, `configUrl` TEXT, `configMd5` TEXT, `iconUrl` TEXT, `label` TEXT, `downloadId` INTEGER NOT NULL, `state` INTEGER NOT NULL, `downloadedBytes` INTEGER NOT NULL, `totalBytes` INTEGER NOT NULL, `progress` REAL NOT NULL, PRIMARY KEY(`assembleId`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `DownloadCenterLocalInfo` (`id` INTEGER NOT NULL, `packageName` TEXT, `downloadId` INTEGER NOT NULL, `downloadUrl` TEXT, `configUrl` TEXT, `appLabel` TEXT, `totalBytes` INTEGER NOT NULL, `iconUrl` TEXT, PRIMARY KEY(`id`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `AppConfigInfo` (`packageName` TEXT NOT NULL, `configUrl` TEXT NOT NULL, PRIMARY KEY(`packageName`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `UserAgreementStates` (`userId` TEXT NOT NULL, `agreed` INTEGER NOT NULL, PRIMARY KEY(`userId`))");
                _db.execSQL(RoomMasterTable.CREATE_QUERY);
                _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7c7bcf80fc127d0fe9e7adbd906b77fa')");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("DROP TABLE IF EXISTS `XpOkDownloadInfo`");
                _db.execSQL("DROP TABLE IF EXISTS `AssembleLocalInfo`");
                _db.execSQL("DROP TABLE IF EXISTS `DownloadCenterLocalInfo`");
                _db.execSQL("DROP TABLE IF EXISTS `AppConfigInfo`");
                _db.execSQL("DROP TABLE IF EXISTS `UserAgreementStates`");
                if (XpAppStoreDatabase_Impl.this.mCallbacks != null) {
                    int size = XpAppStoreDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) XpAppStoreDatabase_Impl.this.mCallbacks.get(i)).onDestructiveMigration(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected void onCreate(SupportSQLiteDatabase _db) {
                if (XpAppStoreDatabase_Impl.this.mCallbacks != null) {
                    int size = XpAppStoreDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) XpAppStoreDatabase_Impl.this.mCallbacks.get(i)).onCreate(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(SupportSQLiteDatabase _db) {
                XpAppStoreDatabase_Impl.this.mDatabase = _db;
                XpAppStoreDatabase_Impl.this.internalInitInvalidationTracker(_db);
                if (XpAppStoreDatabase_Impl.this.mCallbacks != null) {
                    int size = XpAppStoreDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) XpAppStoreDatabase_Impl.this.mCallbacks.get(i)).onOpen(_db);
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
                hashMap.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
                hashMap.put("url", new TableInfo.Column("url", "TEXT", true, 2, null, 1));
                hashMap.put(Progress.TAG, new TableInfo.Column(Progress.TAG, "TEXT", true, 3, null, 1));
                hashMap.put("downloadId", new TableInfo.Column("downloadId", "INTEGER", true, 0, null, 1));
                hashMap.put("status", new TableInfo.Column("status", "INTEGER", true, 0, null, 1));
                TableInfo tableInfo = new TableInfo("XpOkDownloadInfo", hashMap, new HashSet(0), new HashSet(0));
                TableInfo read = TableInfo.read(_db, "XpOkDownloadInfo");
                if (!tableInfo.equals(read)) {
                    return new RoomOpenHelper.ValidationResult(false, "XpOkDownloadInfo(com.xiaopeng.appstore.bizcommon.entities.download.XpOkDownloadInfo).\n Expected:\n" + tableInfo + "\n Found:\n" + read);
                }
                HashMap hashMap2 = new HashMap(13);
                hashMap2.put("assembleId", new TableInfo.Column("assembleId", "INTEGER", true, 1, null, 1));
                hashMap2.put(VuiConstants.SCENE_PACKAGE_NAME, new TableInfo.Column(VuiConstants.SCENE_PACKAGE_NAME, "TEXT", false, 0, null, 1));
                hashMap2.put("downloadUrl", new TableInfo.Column("downloadUrl", "TEXT", false, 0, null, 1));
                hashMap2.put("md5", new TableInfo.Column("md5", "TEXT", false, 0, null, 1));
                hashMap2.put("configUrl", new TableInfo.Column("configUrl", "TEXT", false, 0, null, 1));
                hashMap2.put("configMd5", new TableInfo.Column("configMd5", "TEXT", false, 0, null, 1));
                hashMap2.put("iconUrl", new TableInfo.Column("iconUrl", "TEXT", false, 0, null, 1));
                hashMap2.put(VuiConstants.ELEMENT_LABEL, new TableInfo.Column(VuiConstants.ELEMENT_LABEL, "TEXT", false, 0, null, 1));
                hashMap2.put("downloadId", new TableInfo.Column("downloadId", "INTEGER", true, 0, null, 1));
                hashMap2.put("state", new TableInfo.Column("state", "INTEGER", true, 0, null, 1));
                hashMap2.put("downloadedBytes", new TableInfo.Column("downloadedBytes", "INTEGER", true, 0, null, 1));
                hashMap2.put("totalBytes", new TableInfo.Column("totalBytes", "INTEGER", true, 0, null, 1));
                hashMap2.put("progress", new TableInfo.Column("progress", "REAL", true, 0, null, 1));
                TableInfo tableInfo2 = new TableInfo("AssembleLocalInfo", hashMap2, new HashSet(0), new HashSet(0));
                TableInfo read2 = TableInfo.read(_db, "AssembleLocalInfo");
                if (!tableInfo2.equals(read2)) {
                    return new RoomOpenHelper.ValidationResult(false, "AssembleLocalInfo(com.xiaopeng.appstore.bizcommon.entities.AssembleLocalInfo).\n Expected:\n" + tableInfo2 + "\n Found:\n" + read2);
                }
                HashMap hashMap3 = new HashMap(8);
                hashMap3.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
                hashMap3.put(VuiConstants.SCENE_PACKAGE_NAME, new TableInfo.Column(VuiConstants.SCENE_PACKAGE_NAME, "TEXT", false, 0, null, 1));
                hashMap3.put("downloadId", new TableInfo.Column("downloadId", "INTEGER", true, 0, null, 1));
                hashMap3.put("downloadUrl", new TableInfo.Column("downloadUrl", "TEXT", false, 0, null, 1));
                hashMap3.put("configUrl", new TableInfo.Column("configUrl", "TEXT", false, 0, null, 1));
                hashMap3.put("appLabel", new TableInfo.Column("appLabel", "TEXT", false, 0, null, 1));
                hashMap3.put("totalBytes", new TableInfo.Column("totalBytes", "INTEGER", true, 0, null, 1));
                hashMap3.put("iconUrl", new TableInfo.Column("iconUrl", "TEXT", false, 0, null, 1));
                TableInfo tableInfo3 = new TableInfo("DownloadCenterLocalInfo", hashMap3, new HashSet(0), new HashSet(0));
                TableInfo read3 = TableInfo.read(_db, "DownloadCenterLocalInfo");
                if (!tableInfo3.equals(read3)) {
                    return new RoomOpenHelper.ValidationResult(false, "DownloadCenterLocalInfo(com.xiaopeng.appstore.bizcommon.entities.DownloadCenterLocalInfo).\n Expected:\n" + tableInfo3 + "\n Found:\n" + read3);
                }
                HashMap hashMap4 = new HashMap(2);
                hashMap4.put(VuiConstants.SCENE_PACKAGE_NAME, new TableInfo.Column(VuiConstants.SCENE_PACKAGE_NAME, "TEXT", true, 1, null, 1));
                hashMap4.put("configUrl", new TableInfo.Column("configUrl", "TEXT", true, 0, null, 1));
                TableInfo tableInfo4 = new TableInfo("AppConfigInfo", hashMap4, new HashSet(0), new HashSet(0));
                TableInfo read4 = TableInfo.read(_db, "AppConfigInfo");
                if (!tableInfo4.equals(read4)) {
                    return new RoomOpenHelper.ValidationResult(false, "AppConfigInfo(com.xiaopeng.appstore.bizcommon.entities.AppConfigInfo).\n Expected:\n" + tableInfo4 + "\n Found:\n" + read4);
                }
                HashMap hashMap5 = new HashMap(2);
                hashMap5.put(AppStoreContract.AgreementStage.USER_ID_COLUMN, new TableInfo.Column(AppStoreContract.AgreementStage.USER_ID_COLUMN, "TEXT", true, 1, null, 1));
                hashMap5.put("agreed", new TableInfo.Column("agreed", "INTEGER", true, 0, null, 1));
                TableInfo tableInfo5 = new TableInfo(AgreementStateEntity.TABLE_NAME, hashMap5, new HashSet(0), new HashSet(0));
                TableInfo read5 = TableInfo.read(_db, AgreementStateEntity.TABLE_NAME);
                if (!tableInfo5.equals(read5)) {
                    return new RoomOpenHelper.ValidationResult(false, "UserAgreementStates(com.xiaopeng.appstore.bizcommon.entities.AgreementStateEntity).\n Expected:\n" + tableInfo5 + "\n Found:\n" + read5);
                }
                return new RoomOpenHelper.ValidationResult(true, null);
            }
        }, "7c7bcf80fc127d0fe9e7adbd906b77fa", "73cd77a5b9def58d27a15e52650ab836")).build());
    }

    @Override // androidx.room.RoomDatabase
    protected InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "XpOkDownloadInfo", "AssembleLocalInfo", "DownloadCenterLocalInfo", "AppConfigInfo", AgreementStateEntity.TABLE_NAME);
    }

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            writableDatabase.execSQL("DELETE FROM `XpOkDownloadInfo`");
            writableDatabase.execSQL("DELETE FROM `AssembleLocalInfo`");
            writableDatabase.execSQL("DELETE FROM `DownloadCenterLocalInfo`");
            writableDatabase.execSQL("DELETE FROM `AppConfigInfo`");
            writableDatabase.execSQL("DELETE FROM `UserAgreementStates`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            writableDatabase.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!writableDatabase.inTransaction()) {
                writableDatabase.execSQL("VACUUM");
            }
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.XpAppStoreDatabase
    public XpOkDownloadDao getXpOkDownloadDao() {
        XpOkDownloadDao xpOkDownloadDao;
        if (this._xpOkDownloadDao != null) {
            return this._xpOkDownloadDao;
        }
        synchronized (this) {
            if (this._xpOkDownloadDao == null) {
                this._xpOkDownloadDao = new XpOkDownloadDao_Impl(this);
            }
            xpOkDownloadDao = this._xpOkDownloadDao;
        }
        return xpOkDownloadDao;
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.XpAppStoreDatabase
    public AssembleDao getAssembleDao() {
        AssembleDao assembleDao;
        if (this._assembleDao != null) {
            return this._assembleDao;
        }
        synchronized (this) {
            if (this._assembleDao == null) {
                this._assembleDao = new AssembleDao_Impl(this);
            }
            assembleDao = this._assembleDao;
        }
        return assembleDao;
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.XpAppStoreDatabase
    public DownloadCenterDao getDownloadCenterDao() {
        DownloadCenterDao downloadCenterDao;
        if (this._downloadCenterDao != null) {
            return this._downloadCenterDao;
        }
        synchronized (this) {
            if (this._downloadCenterDao == null) {
                this._downloadCenterDao = new DownloadCenterDao_Impl(this);
            }
            downloadCenterDao = this._downloadCenterDao;
        }
        return downloadCenterDao;
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.XpAppStoreDatabase
    public AppConfigDao getAppConfigDao() {
        AppConfigDao appConfigDao;
        if (this._appConfigDao != null) {
            return this._appConfigDao;
        }
        synchronized (this) {
            if (this._appConfigDao == null) {
                this._appConfigDao = new AppConfigDao_Impl(this);
            }
            appConfigDao = this._appConfigDao;
        }
        return appConfigDao;
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.XpAppStoreDatabase
    public AgreementStateDao getAgreementStateDao() {
        AgreementStateDao agreementStateDao;
        if (this._agreementStateDao != null) {
            return this._agreementStateDao;
        }
        synchronized (this) {
            if (this._agreementStateDao == null) {
                this._agreementStateDao = new AgreementStateDao_Impl(this);
            }
            agreementStateDao = this._agreementStateDao;
        }
        return agreementStateDao;
    }
}
