package com.xiaopeng.appstore.applet_biz.datamodel.db;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
/* loaded from: classes2.dex */
public abstract class MiniProgramDb extends RoomDatabase {
    private static volatile MiniProgramDb INSTANCE;

    public abstract MiniProgramDao getMiniProgramDao();

    public static MiniProgramDb getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MiniProgramDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    private static MiniProgramDb buildDatabase(Context context) {
        return (MiniProgramDb) Room.databaseBuilder(context.getApplicationContext(), MiniProgramDb.class, "MiniProgram.db").fallbackToDestructiveMigration().build();
    }
}
