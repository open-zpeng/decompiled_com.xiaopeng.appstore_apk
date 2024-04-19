package com.xiaopeng.appstore.applet_biz.datamodel.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import java.util.HashMap;
import java.util.HashSet;
/* loaded from: classes2.dex */
public final class MiniProgramDb_Impl extends MiniProgramDb {
    private volatile MiniProgramDao _miniProgramDao;

    @Override // androidx.room.RoomDatabase
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
        return configuration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(configuration.context).name(configuration.name).callback(new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) { // from class: com.xiaopeng.appstore.applet_biz.datamodel.db.MiniProgramDb_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPostMigrate(SupportSQLiteDatabase _db) {
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("CREATE TABLE IF NOT EXISTS `MiniProgramGroup` (`mId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `group_name` TEXT NOT NULL, `mini_list` TEXT)");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `MiniProgramData` (`name` TEXT, `id` TEXT NOT NULL, `iconName` TEXT, `params` TEXT, PRIMARY KEY(`id`))");
                _db.execSQL(RoomMasterTable.CREATE_QUERY);
                _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'dd39d769fd299ced5a6e7112fc8e2456')");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("DROP TABLE IF EXISTS `MiniProgramGroup`");
                _db.execSQL("DROP TABLE IF EXISTS `MiniProgramData`");
                if (MiniProgramDb_Impl.this.mCallbacks != null) {
                    int size = MiniProgramDb_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) MiniProgramDb_Impl.this.mCallbacks.get(i)).onDestructiveMigration(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected void onCreate(SupportSQLiteDatabase _db) {
                if (MiniProgramDb_Impl.this.mCallbacks != null) {
                    int size = MiniProgramDb_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) MiniProgramDb_Impl.this.mCallbacks.get(i)).onCreate(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(SupportSQLiteDatabase _db) {
                MiniProgramDb_Impl.this.mDatabase = _db;
                MiniProgramDb_Impl.this.internalInitInvalidationTracker(_db);
                if (MiniProgramDb_Impl.this.mCallbacks != null) {
                    int size = MiniProgramDb_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) MiniProgramDb_Impl.this.mCallbacks.get(i)).onOpen(_db);
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
                hashMap.put("mId", new TableInfo.Column("mId", "INTEGER", true, 1, null, 1));
                hashMap.put("group_name", new TableInfo.Column("group_name", "TEXT", true, 0, null, 1));
                hashMap.put("mini_list", new TableInfo.Column("mini_list", "TEXT", false, 0, null, 1));
                TableInfo tableInfo = new TableInfo("MiniProgramGroup", hashMap, new HashSet(0), new HashSet(0));
                TableInfo read = TableInfo.read(_db, "MiniProgramGroup");
                if (!tableInfo.equals(read)) {
                    return new RoomOpenHelper.ValidationResult(false, "MiniProgramGroup(com.xiaopeng.appstore.applet_biz.datamodel.entities.MiniProgramGroup).\n Expected:\n" + tableInfo + "\n Found:\n" + read);
                }
                HashMap hashMap2 = new HashMap(4);
                hashMap2.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, 1));
                hashMap2.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, 1));
                hashMap2.put("iconName", new TableInfo.Column("iconName", "TEXT", false, 0, null, 1));
                hashMap2.put(IpcConfig.DeviceCommunicationConfig.KEY_APP_MESSAGE_PARAMS, new TableInfo.Column(IpcConfig.DeviceCommunicationConfig.KEY_APP_MESSAGE_PARAMS, "TEXT", false, 0, null, 1));
                TableInfo tableInfo2 = new TableInfo("MiniProgramData", hashMap2, new HashSet(0), new HashSet(0));
                TableInfo read2 = TableInfo.read(_db, "MiniProgramData");
                if (!tableInfo2.equals(read2)) {
                    return new RoomOpenHelper.ValidationResult(false, "MiniProgramData(com.xiaopeng.appstore.applet_biz.datamodel.entities.MiniProgramData).\n Expected:\n" + tableInfo2 + "\n Found:\n" + read2);
                }
                return new RoomOpenHelper.ValidationResult(true, null);
            }
        }, "dd39d769fd299ced5a6e7112fc8e2456", "e092a1df491d8c4317cd812e32868549")).build());
    }

    @Override // androidx.room.RoomDatabase
    protected InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "MiniProgramGroup", "MiniProgramData");
    }

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            writableDatabase.execSQL("DELETE FROM `MiniProgramGroup`");
            writableDatabase.execSQL("DELETE FROM `MiniProgramData`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            writableDatabase.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!writableDatabase.inTransaction()) {
                writableDatabase.execSQL("VACUUM");
            }
        }
    }

    @Override // com.xiaopeng.appstore.applet_biz.datamodel.db.MiniProgramDb
    public MiniProgramDao getMiniProgramDao() {
        MiniProgramDao miniProgramDao;
        if (this._miniProgramDao != null) {
            return this._miniProgramDao;
        }
        synchronized (this) {
            if (this._miniProgramDao == null) {
                this._miniProgramDao = new MiniProgramDao_Impl(this);
            }
            miniProgramDao = this._miniProgramDao;
        }
        return miniProgramDao;
    }
}
