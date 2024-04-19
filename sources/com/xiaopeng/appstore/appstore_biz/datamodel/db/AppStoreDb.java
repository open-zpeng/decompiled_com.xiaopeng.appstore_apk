package com.xiaopeng.appstore.appstore_biz.datamodel.db;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.orhanobut.logger.Logger;
/* loaded from: classes2.dex */
public abstract class AppStoreDb extends RoomDatabase {
    private static volatile AppStoreDb INSTANCE = null;
    private static final Migration MIGRATION_9_10 = new Migration(9, 10) { // from class: com.xiaopeng.appstore.appstore_biz.datamodel.db.AppStoreDb.1
        @Override // androidx.room.migration.Migration
        public void migrate(SupportSQLiteDatabase database) {
            Logger.t(AppStoreDb.TAG).i("migrate: 9_10", new Object[0]);
            try {
                database.execSQL("ALTER TABLE AppRemoteStates ADD COLUMN promptTitle TEXT");
                database.execSQL("ALTER TABLE AppRemoteStates ADD COLUMN promptText TEXT");
            } catch (Exception e) {
                Logger.t(AppStoreDb.TAG).e("migrate, 9_10 ex:" + e, new Object[0]);
                e.printStackTrace();
            }
        }
    };
    private static final String TAG = "AppStoreDb";

    public abstract AppRemoteStateDao getAppRemoteStateDao();

    public abstract HomeDao getHomeDao();

    public static AppStoreDb getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppStoreDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    private static AppStoreDb buildDatabase(Context context) {
        return (AppStoreDb) Room.databaseBuilder(context.getApplicationContext(), AppStoreDb.class, "AppStore.db").addMigrations(MIGRATION_9_10).fallbackToDestructiveMigration().build();
    }
}
