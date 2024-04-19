package com.xiaopeng.appstore.applet_biz.datamodel.db;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.xiaopeng.appstore.applet_biz.datamodel.entities.MiniDataConvert;
import com.xiaopeng.appstore.applet_biz.datamodel.entities.MiniProgramGroup;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public final class MiniProgramDao_Impl extends MiniProgramDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<MiniProgramGroup> __insertionAdapterOfMiniProgramGroup;
    private final SharedSQLiteStatement __preparedStmtOfClearMiniProgramList;

    public MiniProgramDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfMiniProgramGroup = new EntityInsertionAdapter<MiniProgramGroup>(__db) { // from class: com.xiaopeng.appstore.applet_biz.datamodel.db.MiniProgramDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `MiniProgramGroup` (`mId`,`group_name`,`mini_list`) VALUES (nullif(?, 0),?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, MiniProgramGroup value) {
                stmt.bindLong(1, value.mId);
                if (value.getGroupName() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getGroupName());
                }
                String myObjectsToStoredString = MiniDataConvert.myObjectsToStoredString(value.getData());
                if (myObjectsToStoredString == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, myObjectsToStoredString);
                }
            }
        };
        this.__preparedStmtOfClearMiniProgramList = new SharedSQLiteStatement(__db) { // from class: com.xiaopeng.appstore.applet_biz.datamodel.db.MiniProgramDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM miniprogramgroup";
            }
        };
    }

    @Override // com.xiaopeng.appstore.applet_biz.datamodel.db.MiniProgramDao
    public void insertMiniProgramList(final List<MiniProgramGroup> packageList) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfMiniProgramGroup.insert(packageList);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.applet_biz.datamodel.db.MiniProgramDao
    public void clearThenInsert(final List<MiniProgramGroup> miniProgramDataList) {
        this.__db.beginTransaction();
        try {
            super.clearThenInsert(miniProgramDataList);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.applet_biz.datamodel.db.MiniProgramDao
    public void clearMiniProgramList() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfClearMiniProgramList.acquire();
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfClearMiniProgramList.release(acquire);
        }
    }

    @Override // com.xiaopeng.appstore.applet_biz.datamodel.db.MiniProgramDao
    public List<MiniProgramGroup> getMiniProgramDataList() {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM miniprogramgroup", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "mId");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "group_name");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "mini_list");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                MiniProgramGroup miniProgramGroup = new MiniProgramGroup();
                miniProgramGroup.mId = query.getInt(columnIndexOrThrow);
                miniProgramGroup.setGroupName(query.getString(columnIndexOrThrow2));
                miniProgramGroup.setData(MiniDataConvert.storedStringToMyObjects(query.getString(columnIndexOrThrow3)));
                arrayList.add(miniProgramGroup);
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }
}
