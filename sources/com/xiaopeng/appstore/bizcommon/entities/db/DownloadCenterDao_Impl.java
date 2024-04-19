package com.xiaopeng.appstore.bizcommon.entities.db;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.xiaopeng.appstore.bizcommon.entities.DownloadCenterLocalInfo;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public final class DownloadCenterDao_Impl extends DownloadCenterDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<DownloadCenterLocalInfo> __insertionAdapterOfDownloadCenterLocalInfo;
    private final SharedSQLiteStatement __preparedStmtOfRemove;
    private final SharedSQLiteStatement __preparedStmtOfRemove_1;
    private final EntityDeletionOrUpdateAdapter<DownloadCenterLocalInfo> __updateAdapterOfDownloadCenterLocalInfo;

    public DownloadCenterDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfDownloadCenterLocalInfo = new EntityInsertionAdapter<DownloadCenterLocalInfo>(__db) { // from class: com.xiaopeng.appstore.bizcommon.entities.db.DownloadCenterDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `DownloadCenterLocalInfo` (`id`,`packageName`,`downloadId`,`downloadUrl`,`configUrl`,`appLabel`,`totalBytes`,`iconUrl`) VALUES (?,?,?,?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, DownloadCenterLocalInfo value) {
                stmt.bindLong(1, value.getId());
                if (value.getPackageName() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getPackageName());
                }
                stmt.bindLong(3, value.getDownloadId());
                if (value.getDownloadUrl() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getDownloadUrl());
                }
                if (value.getConfigUrl() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getConfigUrl());
                }
                if (value.getAppLabel() == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.getAppLabel());
                }
                stmt.bindLong(7, value.getTotalBytes());
                if (value.getIconUrl() == null) {
                    stmt.bindNull(8);
                } else {
                    stmt.bindString(8, value.getIconUrl());
                }
            }
        };
        this.__updateAdapterOfDownloadCenterLocalInfo = new EntityDeletionOrUpdateAdapter<DownloadCenterLocalInfo>(__db) { // from class: com.xiaopeng.appstore.bizcommon.entities.db.DownloadCenterDao_Impl.2
            @Override // androidx.room.EntityDeletionOrUpdateAdapter, androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE OR ABORT `DownloadCenterLocalInfo` SET `id` = ?,`packageName` = ?,`downloadId` = ?,`downloadUrl` = ?,`configUrl` = ?,`appLabel` = ?,`totalBytes` = ?,`iconUrl` = ? WHERE `id` = ?";
            }

            @Override // androidx.room.EntityDeletionOrUpdateAdapter
            public void bind(SupportSQLiteStatement stmt, DownloadCenterLocalInfo value) {
                stmt.bindLong(1, value.getId());
                if (value.getPackageName() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getPackageName());
                }
                stmt.bindLong(3, value.getDownloadId());
                if (value.getDownloadUrl() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getDownloadUrl());
                }
                if (value.getConfigUrl() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getConfigUrl());
                }
                if (value.getAppLabel() == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.getAppLabel());
                }
                stmt.bindLong(7, value.getTotalBytes());
                if (value.getIconUrl() == null) {
                    stmt.bindNull(8);
                } else {
                    stmt.bindString(8, value.getIconUrl());
                }
                stmt.bindLong(9, value.getId());
            }
        };
        this.__preparedStmtOfRemove = new SharedSQLiteStatement(__db) { // from class: com.xiaopeng.appstore.bizcommon.entities.db.DownloadCenterDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from downloadcenterlocalinfo where id=?";
            }
        };
        this.__preparedStmtOfRemove_1 = new SharedSQLiteStatement(__db) { // from class: com.xiaopeng.appstore.bizcommon.entities.db.DownloadCenterDao_Impl.4
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from downloadcenterlocalinfo where packageName=?";
            }
        };
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.DownloadCenterDao
    public void insert(final DownloadCenterLocalInfo info) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfDownloadCenterLocalInfo.insert((EntityInsertionAdapter<DownloadCenterLocalInfo>) info);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.DownloadCenterDao
    public void update(final DownloadCenterLocalInfo info) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__updateAdapterOfDownloadCenterLocalInfo.handle(info);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.DownloadCenterDao
    public boolean tryUpdate(final DownloadCenterLocalInfo info) {
        this.__db.beginTransaction();
        try {
            boolean tryUpdate = super.tryUpdate(info);
            this.__db.setTransactionSuccessful();
            return tryUpdate;
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.DownloadCenterDao
    public List<Integer> removeReturnOld(final String packageName, final int exceptId) {
        this.__db.beginTransaction();
        try {
            List<Integer> removeReturnOld = super.removeReturnOld(packageName, exceptId);
            this.__db.setTransactionSuccessful();
            return removeReturnOld;
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.DownloadCenterDao
    public void remove(final int id) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfRemove.acquire();
        acquire.bindLong(1, id);
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfRemove.release(acquire);
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.DownloadCenterDao
    public void remove(final String packageName) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfRemove_1.acquire();
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
            this.__preparedStmtOfRemove_1.release(acquire);
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.DownloadCenterDao
    public DownloadCenterLocalInfo query(final int id) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select * from downloadcenterlocalinfo where id=?", 1);
        acquire.bindLong(1, id);
        this.__db.assertNotSuspendingTransaction();
        DownloadCenterLocalInfo downloadCenterLocalInfo = null;
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, VuiConstants.SCENE_PACKAGE_NAME);
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "downloadId");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "downloadUrl");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "configUrl");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "appLabel");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "totalBytes");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "iconUrl");
            if (query.moveToFirst()) {
                downloadCenterLocalInfo = new DownloadCenterLocalInfo();
                downloadCenterLocalInfo.setId(query.getInt(columnIndexOrThrow));
                downloadCenterLocalInfo.setPackageName(query.getString(columnIndexOrThrow2));
                downloadCenterLocalInfo.setDownloadId(query.getInt(columnIndexOrThrow3));
                downloadCenterLocalInfo.setDownloadUrl(query.getString(columnIndexOrThrow4));
                downloadCenterLocalInfo.setConfigUrl(query.getString(columnIndexOrThrow5));
                downloadCenterLocalInfo.setAppLabel(query.getString(columnIndexOrThrow6));
                downloadCenterLocalInfo.setTotalBytes(query.getLong(columnIndexOrThrow7));
                downloadCenterLocalInfo.setIconUrl(query.getString(columnIndexOrThrow8));
            }
            return downloadCenterLocalInfo;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.DownloadCenterDao
    public List<DownloadCenterLocalInfo> query(final String packageName) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select * from downloadcenterlocalinfo where packageName=?", 1);
        if (packageName == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, packageName);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, VuiConstants.SCENE_PACKAGE_NAME);
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "downloadId");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "downloadUrl");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "configUrl");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "appLabel");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "totalBytes");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "iconUrl");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                DownloadCenterLocalInfo downloadCenterLocalInfo = new DownloadCenterLocalInfo();
                downloadCenterLocalInfo.setId(query.getInt(columnIndexOrThrow));
                downloadCenterLocalInfo.setPackageName(query.getString(columnIndexOrThrow2));
                downloadCenterLocalInfo.setDownloadId(query.getInt(columnIndexOrThrow3));
                downloadCenterLocalInfo.setDownloadUrl(query.getString(columnIndexOrThrow4));
                downloadCenterLocalInfo.setConfigUrl(query.getString(columnIndexOrThrow5));
                downloadCenterLocalInfo.setAppLabel(query.getString(columnIndexOrThrow6));
                downloadCenterLocalInfo.setTotalBytes(query.getLong(columnIndexOrThrow7));
                downloadCenterLocalInfo.setIconUrl(query.getString(columnIndexOrThrow8));
                arrayList.add(downloadCenterLocalInfo);
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.DownloadCenterDao
    public List<DownloadCenterLocalInfo> queryAll() {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select * from downloadcenterlocalinfo", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, VuiConstants.SCENE_PACKAGE_NAME);
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "downloadId");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "downloadUrl");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "configUrl");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "appLabel");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "totalBytes");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "iconUrl");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                DownloadCenterLocalInfo downloadCenterLocalInfo = new DownloadCenterLocalInfo();
                downloadCenterLocalInfo.setId(query.getInt(columnIndexOrThrow));
                downloadCenterLocalInfo.setPackageName(query.getString(columnIndexOrThrow2));
                downloadCenterLocalInfo.setDownloadId(query.getInt(columnIndexOrThrow3));
                downloadCenterLocalInfo.setDownloadUrl(query.getString(columnIndexOrThrow4));
                downloadCenterLocalInfo.setConfigUrl(query.getString(columnIndexOrThrow5));
                downloadCenterLocalInfo.setAppLabel(query.getString(columnIndexOrThrow6));
                downloadCenterLocalInfo.setTotalBytes(query.getLong(columnIndexOrThrow7));
                downloadCenterLocalInfo.setIconUrl(query.getString(columnIndexOrThrow8));
                arrayList.add(downloadCenterLocalInfo);
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }
}
