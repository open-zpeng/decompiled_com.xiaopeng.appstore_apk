package com.xiaopeng.appstore.applist_biz.datamodel.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.util.HashMap;
import java.util.HashSet;
/* loaded from: classes2.dex */
public final class LocalAppDatabase_Impl extends LocalAppDatabase {
    private volatile LocalAppDao _localAppDao;

    @Override // androidx.room.RoomDatabase
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
        return configuration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(configuration.context).name(configuration.name).callback(new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) { // from class: com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDatabase_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPostMigrate(SupportSQLiteDatabase _db) {
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("CREATE TABLE IF NOT EXISTS `LocalData` (`key` TEXT NOT NULL, `package_name` TEXT, `list_index` INTEGER NOT NULL, PRIMARY KEY(`key`))");
                _db.execSQL(RoomMasterTable.CREATE_QUERY);
                _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'f0b72345dc9d303a187b257d380eb2e7')");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("DROP TABLE IF EXISTS `LocalData`");
                if (LocalAppDatabase_Impl.this.mCallbacks != null) {
                    int size = LocalAppDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) LocalAppDatabase_Impl.this.mCallbacks.get(i)).onDestructiveMigration(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected void onCreate(SupportSQLiteDatabase _db) {
                if (LocalAppDatabase_Impl.this.mCallbacks != null) {
                    int size = LocalAppDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) LocalAppDatabase_Impl.this.mCallbacks.get(i)).onCreate(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(SupportSQLiteDatabase _db) {
                LocalAppDatabase_Impl.this.mDatabase = _db;
                LocalAppDatabase_Impl.this.internalInitInvalidationTracker(_db);
                if (LocalAppDatabase_Impl.this.mCallbacks != null) {
                    int size = LocalAppDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) LocalAppDatabase_Impl.this.mCallbacks.get(i)).onOpen(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPreMigrate(SupportSQLiteDatabase _db) {
                DBUtil.dropFtsSyncTriggers(_db);
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
                HashMap hashMap = new HashMap(3);
                hashMap.put("key", new TableInfo.Column("key", "TEXT", true, 1, null, 1));
                hashMap.put("package_name", new TableInfo.Column("package_name", "TEXT", false, 0, null, 1));
                hashMap.put("list_index", new TableInfo.Column("list_index", "INTEGER", true, 0, null, 1));
                TableInfo tableInfo = new TableInfo("LocalData", hashMap, new HashSet(0), new HashSet(0));
                TableInfo read = TableInfo.read(_db, "LocalData");
                if (!tableInfo.equals(read)) {
                    return new RoomOpenHelper.ValidationResult(false, "LocalData(com.xiaopeng.appstore.applist_biz.datamodel.entities.LocalData).\n Expected:\n" + tableInfo + "\n Found:\n" + read);
                }
                return new RoomOpenHelper.ValidationResult(true, null);
            }
        }, "f0b72345dc9d303a187b257d380eb2e7", "082342883786a4e608f06d753a66f98a")).build());
    }

    @Override // androidx.room.RoomDatabase
    protected InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "LocalData");
    }

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            writableDatabase.execSQL("DELETE FROM `LocalData`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            writableDatabase.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!writableDatabase.inTransaction()) {
                writableDatabase.execSQL("VACUUM");
            }
        }
    }

    @Override // com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDatabase
    public LocalAppDao getLocalAppDao() {
        LocalAppDao localAppDao;
        if (this._localAppDao != null) {
            return this._localAppDao;
        }
        synchronized (this) {
            if (this._localAppDao == null) {
                this._localAppDao = new LocalAppDao_Impl(this);
            }
            localAppDao = this._localAppDao;
        }
        return localAppDao;
    }
}
