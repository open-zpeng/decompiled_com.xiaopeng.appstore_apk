package com.xiaopeng.appstore.bizcommon.entities.db;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.libcommon.utils.Utils;
/* loaded from: classes2.dex */
public abstract class XpAppStoreDatabase extends RoomDatabase {
    private static volatile XpAppStoreDatabase INSTANCE = null;
    private static final String TAG = "XpAppStoreDatabase";
    private static final Migration MIGRATION_6_7 = new Migration(6, 7) { // from class: com.xiaopeng.appstore.bizcommon.entities.db.XpAppStoreDatabase.1
        @Override // androidx.room.migration.Migration
        public void migrate(SupportSQLiteDatabase database) {
            Logger.t(XpAppStoreDatabase.TAG).i("migrate: 6_7", new Object[0]);
            database.execSQL("ALTER TABLE AssembleLocalInfo ADD COLUMN progress REAL NOT NULL DEFAULT 0");
        }
    };
    private static boolean sAppConfigInfoMigrated = false;
    private static final Migration MIGRATION_7_8 = new Migration(7, 8) { // from class: com.xiaopeng.appstore.bizcommon.entities.db.XpAppStoreDatabase.2
        @Override // androidx.room.migration.Migration
        public void migrate(SupportSQLiteDatabase database) {
            Logger.t(XpAppStoreDatabase.TAG).i("migrate: 7_8", new Object[0]);
            database.execSQL("ALTER TABLE DownloadCenterLocalInfo ADD COLUMN configUrl TEXT");
        }
    };
    private static final Migration MIGRATION_8_9 = new Migration(8, 9) { // from class: com.xiaopeng.appstore.bizcommon.entities.db.XpAppStoreDatabase.3
        @Override // androidx.room.migration.Migration
        public void migrate(SupportSQLiteDatabase database) {
            Logger.t(XpAppStoreDatabase.TAG).i("migrate: 8_9", new Object[0]);
            XpAppStoreDatabase.tryMigrateAppConfigInfo(database);
        }
    };
    private static final Migration MIGRATION_9_10 = new Migration(9, 10) { // from class: com.xiaopeng.appstore.bizcommon.entities.db.XpAppStoreDatabase.4
        @Override // androidx.room.migration.Migration
        public void migrate(SupportSQLiteDatabase database) {
            Logger.t(XpAppStoreDatabase.TAG).i("migrate: 9_10", new Object[0]);
            XpAppStoreDatabase.tryMigrateAppConfigInfo(database);
            database.execSQL("CREATE TABLE IF NOT EXISTS `UserAgreementStates` (`userId` TEXT NOT NULL, `agreed` INTEGER NOT NULL, PRIMARY KEY(`userId`))");
            try {
                database.execSQL("ALTER TABLE DownloadCenterLocalInfo ADD COLUMN configUrl TEXT");
            } catch (Exception unused) {
            }
            database.execSQL("DELETE FROM `DownloadCenterLocalInfo`");
        }
    };

    public abstract AgreementStateDao getAgreementStateDao();

    public abstract AppConfigDao getAppConfigDao();

    public abstract AssembleDao getAssembleDao();

    public abstract DownloadCenterDao getDownloadCenterDao();

    public abstract XpOkDownloadDao getXpOkDownloadDao();

    @Deprecated
    public static XpAppStoreDatabase getInstance() {
        if (INSTANCE == null) {
            synchronized (XpAppStoreDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(Utils.getApp());
                }
            }
        }
        return INSTANCE;
    }

    public static XpAppStoreDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (XpAppStoreDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    private static XpAppStoreDatabase buildDatabase(Context context) {
        return (XpAppStoreDatabase) Room.databaseBuilder(context.getApplicationContext(), XpAppStoreDatabase.class, "xpAppStore.db").addMigrations(MIGRATION_6_7, MIGRATION_7_8, MIGRATION_8_9, MIGRATION_9_10).fallbackToDestructiveMigration().build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void tryMigrateAppConfigInfo(SupportSQLiteDatabase database) {
        if (sAppConfigInfoMigrated) {
            Logger.t(TAG).i("tryMigrateAppConfigInfo, ignore", new Object[0]);
            return;
        }
        Logger.t(TAG).i("tryMigrateAppConfigInfo", new Object[0]);
        database.execSQL("CREATE TABLE `AppConfigInfo_NEW` (`packageName` TEXT NOT NULL PRIMARY KEY, `configUrl` TEXT NOT NULL)");
        database.execSQL("INSERT OR REPLACE INTO AppConfigInfo_NEW (packageName, configUrl) SELECT packageName, configUrl FROM AppConfigInfo");
        database.execSQL("DROP TABLE `AppConfigInfo`");
        database.execSQL("ALTER TABLE AppConfigInfo_NEW RENAME TO AppConfigInfo");
        sAppConfigInfoMigrated = true;
    }
}
