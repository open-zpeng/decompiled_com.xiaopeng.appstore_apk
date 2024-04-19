package com.xiaopeng.appstore.appstore_biz.datamodel.db;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppRemoteStateEntity;
import java.util.List;
/* loaded from: classes2.dex */
public final class AppRemoteStateDao_Impl extends AppRemoteStateDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<AppRemoteStateEntity> __insertionAdapterOfAppRemoteStateEntity;
    private final SharedSQLiteStatement __preparedStmtOfRemove;
    private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

    public AppRemoteStateDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfAppRemoteStateEntity = new EntityInsertionAdapter<AppRemoteStateEntity>(__db) { // from class: com.xiaopeng.appstore.appstore_biz.datamodel.db.AppRemoteStateDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `AppRemoteStates` (`packageName`,`state`,`hasUpdate`,`promptTitle`,`promptText`) VALUES (?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, AppRemoteStateEntity value) {
                if (value.getPackageName() == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, value.getPackageName());
                }
                stmt.bindLong(2, value.getState());
                stmt.bindLong(3, value.getHasUpdate());
                if (value.getPromptTitle() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getPromptTitle());
                }
                if (value.getPromptText() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getPromptText());
                }
            }
        };
        this.__preparedStmtOfRemove = new SharedSQLiteStatement(__db) { // from class: com.xiaopeng.appstore.appstore_biz.datamodel.db.AppRemoteStateDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from appremotestates where packageName=?";
            }
        };
        this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) { // from class: com.xiaopeng.appstore.appstore_biz.datamodel.db.AppRemoteStateDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from appremotestates";
            }
        };
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.AppRemoteStateDao
    public void insert(final AppRemoteStateEntity data) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfAppRemoteStateEntity.insert((EntityInsertionAdapter<AppRemoteStateEntity>) data);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.AppRemoteStateDao
    public void insert(final List<AppRemoteStateEntity> list) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfAppRemoteStateEntity.insert(list);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.AppRemoteStateDao
    public void clearThenInsert(final List<AppRemoteStateEntity> list) {
        this.__db.beginTransaction();
        try {
            super.clearThenInsert(list);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.AppRemoteStateDao
    public void remove(final String packageName) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfRemove.acquire();
        if (packageName == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, packageName);
        }
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfRemove.release(acquire);
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.AppRemoteStateDao
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

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.AppRemoteStateDao
    public Cursor queryAll() {
        return this.__db.query(RoomSQLiteQuery.acquire("select * from appremotestates", 0));
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.AppRemoteStateDao
    public Cursor queryAll(final SupportSQLiteQuery query) {
        return this.__db.query(query);
    }
}
