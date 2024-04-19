package com.xiaopeng.appstore.applist_biz.datamodel.db;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
/* loaded from: classes2.dex */
public abstract class LocalAppDatabase extends RoomDatabase {
    private static volatile LocalAppDatabase INSTANCE;

    public abstract LocalAppDao getLocalAppDao();

    public static LocalAppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LocalAppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    private static LocalAppDatabase buildDatabase(Context context) {
        return (LocalAppDatabase) Room.databaseBuilder(context.getApplicationContext(), LocalAppDatabase.class, "AppList.db").fallbackToDestructiveMigration().build();
    }
}
