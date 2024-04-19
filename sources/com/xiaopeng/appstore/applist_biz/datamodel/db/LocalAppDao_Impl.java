package com.xiaopeng.appstore.applist_biz.datamodel.db;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.xiaopeng.appstore.applist_biz.datamodel.entities.LocalData;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
/* loaded from: classes2.dex */
public final class LocalAppDao_Impl extends LocalAppDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<LocalData> __insertionAdapterOfLocalData;
    private final SharedSQLiteStatement __preparedStmtOfRemoveAll;
    private final SharedSQLiteStatement __preparedStmtOfRemoveData;
    private final SharedSQLiteStatement __preparedStmtOfSetIndex;

    public LocalAppDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfLocalData = new EntityInsertionAdapter<LocalData>(__db) { // from class: com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `LocalData` (`key`,`package_name`,`list_index`) VALUES (?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, LocalData value) {
                if (value.key == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, value.key);
                }
                if (value.packageName == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.packageName);
                }
                stmt.bindLong(3, value.listIndex);
            }
        };
        this.__preparedStmtOfSetIndex = new SharedSQLiteStatement(__db) { // from class: com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "update localdata set list_index = ? where `key` = ?";
            }
        };
        this.__preparedStmtOfRemoveData = new SharedSQLiteStatement(__db) { // from class: com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM localdata WHERE `key` = ?";
            }
        };
        this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) { // from class: com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao_Impl.4
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE from localData";
            }
        };
    }

    @Override // com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao
    public long addLocalData(final LocalData localData) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            long insertAndReturnId = this.__insertionAdapterOfLocalData.insertAndReturnId(localData);
            this.__db.setTransactionSuccessful();
            return insertAndReturnId;
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao
    public void addDbList(final List<LocalData> list) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfLocalData.insert(list);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao
    public void clearThenAddList(final List<LocalData> list) {
        this.__db.beginTransaction();
        try {
            super.clearThenAddList(list);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao
    public int setIndex(final String key, final int index) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfSetIndex.acquire();
        acquire.bindLong(1, index);
        if (key == null) {
            acquire.bindNull(2);
        } else {
            acquire.bindString(2, key);
        }
        this.__db.beginTransaction();
        try {
            int executeUpdateDelete = acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
            return executeUpdateDelete;
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfSetIndex.release(acquire);
        }
    }

    @Override // com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao
    public void removeData(final String key) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfRemoveData.acquire();
        if (key == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, key);
        }
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfRemoveData.release(acquire);
        }
    }

    @Override // com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao
    public void removeAll() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfRemoveAll.acquire();
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfRemoveAll.release(acquire);
        }
    }

    @Override // com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao
    public LiveData<List<LocalData>> getLocalData() {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select * from localdata", 0);
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"localdata"}, false, new Callable<List<LocalData>>() { // from class: com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao_Impl.5
            @Override // java.util.concurrent.Callable
            public List<LocalData> call() throws Exception {
                Cursor query = DBUtil.query(LocalAppDao_Impl.this.__db, acquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "key");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "package_name");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "list_index");
                    ArrayList arrayList = new ArrayList(query.getCount());
                    while (query.moveToNext()) {
                        LocalData localData = new LocalData();
                        localData.key = query.getString(columnIndexOrThrow);
                        localData.packageName = query.getString(columnIndexOrThrow2);
                        localData.listIndex = query.getInt(columnIndexOrThrow3);
                        arrayList.add(localData);
                    }
                    return arrayList;
                } finally {
                    query.close();
                }
            }

            protected void finalize() {
                acquire.release();
            }
        });
    }

    @Override // com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao
    public List<LocalData> getPureLocalData() {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select * from localdata", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "key");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "package_name");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "list_index");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                LocalData localData = new LocalData();
                localData.key = query.getString(columnIndexOrThrow);
                localData.packageName = query.getString(columnIndexOrThrow2);
                localData.listIndex = query.getInt(columnIndexOrThrow3);
                arrayList.add(localData);
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao
    public LocalData getDbData(final String key) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select * from localdata where `key` = ?", 1);
        if (key == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, key);
        }
        this.__db.assertNotSuspendingTransaction();
        LocalData localData = null;
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "key");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "package_name");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "list_index");
            if (query.moveToFirst()) {
                localData = new LocalData();
                localData.key = query.getString(columnIndexOrThrow);
                localData.packageName = query.getString(columnIndexOrThrow2);
                localData.listIndex = query.getInt(columnIndexOrThrow3);
            }
            return localData;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao
    public LocalData findDbData(final String key) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select * from localdata where `key` like ?", 1);
        if (key == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, key);
        }
        this.__db.assertNotSuspendingTransaction();
        LocalData localData = null;
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "key");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "package_name");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "list_index");
            if (query.moveToFirst()) {
                localData = new LocalData();
                localData.key = query.getString(columnIndexOrThrow);
                localData.packageName = query.getString(columnIndexOrThrow2);
                localData.listIndex = query.getInt(columnIndexOrThrow3);
            }
            return localData;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao
    public List<LocalData> getDbLocalData(final List<String> keyList) {
        StringBuilder newStringBuilder = StringUtil.newStringBuilder();
        newStringBuilder.append("select ");
        newStringBuilder.append("*");
        newStringBuilder.append(" from localdata where `key` in (");
        int size = keyList.size();
        StringUtil.appendPlaceholders(newStringBuilder, size);
        newStringBuilder.append(")");
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire(newStringBuilder.toString(), size + 0);
        int i = 1;
        for (String str : keyList) {
            if (str == null) {
                acquire.bindNull(i);
            } else {
                acquire.bindString(i, str);
            }
            i++;
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "key");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "package_name");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "list_index");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                LocalData localData = new LocalData();
                localData.key = query.getString(columnIndexOrThrow);
                localData.packageName = query.getString(columnIndexOrThrow2);
                localData.listIndex = query.getInt(columnIndexOrThrow3);
                arrayList.add(localData);
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao
    public int getIndex(final String key) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select list_index from localdata where `key` = ?", 1);
        if (key == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, key);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            return query.moveToFirst() ? query.getInt(0) : 0;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao
    public int getMaxIndex() {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select MAX(list_index) from localdata", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            return query.moveToFirst() ? query.getInt(0) : 0;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.xiaopeng.appstore.applist_biz.datamodel.db.LocalAppDao
    public void removeData(final List<String> keyList) {
        this.__db.assertNotSuspendingTransaction();
        StringBuilder newStringBuilder = StringUtil.newStringBuilder();
        newStringBuilder.append("DELETE FROM localdata WHERE `key` in (");
        StringUtil.appendPlaceholders(newStringBuilder, keyList.size());
        newStringBuilder.append(")");
        SupportSQLiteStatement compileStatement = this.__db.compileStatement(newStringBuilder.toString());
        int i = 1;
        for (String str : keyList) {
            if (str == null) {
                compileStatement.bindNull(i);
            } else {
                compileStatement.bindString(i, str);
            }
            i++;
        }
        this.__db.beginTransaction();
        try {
            compileStatement.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }
}
