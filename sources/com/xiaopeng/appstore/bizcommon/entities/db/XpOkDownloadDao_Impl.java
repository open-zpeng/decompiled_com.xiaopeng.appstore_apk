package com.xiaopeng.appstore.bizcommon.entities.db;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.xiaopeng.appstore.bizcommon.entities.download.XpOkDownloadInfo;
/* loaded from: classes2.dex */
public final class XpOkDownloadDao_Impl extends XpOkDownloadDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<XpOkDownloadInfo> __insertionAdapterOfXpOkDownloadInfo;
    private final SharedSQLiteStatement __preparedStmtOfDelete;

    public XpOkDownloadDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfXpOkDownloadInfo = new EntityInsertionAdapter<XpOkDownloadInfo>(__db) { // from class: com.xiaopeng.appstore.bizcommon.entities.db.XpOkDownloadDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `XpOkDownloadInfo` (`id`,`url`,`tag`,`downloadId`,`status`) VALUES (?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, XpOkDownloadInfo value) {
                stmt.bindLong(1, value.getId());
                if (value.getUrl() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getUrl());
                }
                if (value.getTag() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getTag());
                }
                stmt.bindLong(4, value.getDownloadId());
                stmt.bindLong(5, value.getStatus());
            }
        };
        this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) { // from class: com.xiaopeng.appstore.bizcommon.entities.db.XpOkDownloadDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from xpokdownloadinfo where url is ?";
            }
        };
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.XpOkDownloadDao
    public void insert(final XpOkDownloadInfo info) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfXpOkDownloadInfo.insert((EntityInsertionAdapter<XpOkDownloadInfo>) info);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.XpOkDownloadDao
    public void delete(final String url) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfDelete.acquire();
        if (url == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, url);
        }
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDelete.release(acquire);
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.XpOkDownloadDao
    public String getUrl(final String tag) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select url from xpokdownloadinfo where tag is ?", 1);
        if (tag == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, tag);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            return query.moveToFirst() ? query.getString(0) : null;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.XpOkDownloadDao
    public int getDownloadId(final String url) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select downloadId from xpokdownloadinfo where url is ?", 1);
        if (url == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, url);
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
}
