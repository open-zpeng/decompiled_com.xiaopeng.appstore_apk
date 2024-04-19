package com.xiaopeng.appstore.bizcommon.entities.db;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.xiaopeng.appstore.bizcommon.entities.AssembleLocalInfo;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public final class AssembleDao_Impl extends AssembleDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<AssembleLocalInfo> __insertionAdapterOfAssembleLocalInfo;
    private final SharedSQLiteStatement __preparedStmtOfRemoveAll;
    private final EntityDeletionOrUpdateAdapter<AssembleLocalInfo> __updateAdapterOfAssembleLocalInfo;

    public AssembleDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfAssembleLocalInfo = new EntityInsertionAdapter<AssembleLocalInfo>(__db) { // from class: com.xiaopeng.appstore.bizcommon.entities.db.AssembleDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `AssembleLocalInfo` (`assembleId`,`packageName`,`downloadUrl`,`md5`,`configUrl`,`configMd5`,`iconUrl`,`label`,`downloadId`,`state`,`downloadedBytes`,`totalBytes`,`progress`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, AssembleLocalInfo value) {
                stmt.bindLong(1, value.getAssembleId());
                if (value.getPackageName() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getPackageName());
                }
                if (value.getDownloadUrl() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getDownloadUrl());
                }
                if (value.getMd5() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getMd5());
                }
                if (value.getConfigUrl() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getConfigUrl());
                }
                if (value.getConfigMd5() == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.getConfigMd5());
                }
                if (value.getIconUrl() == null) {
                    stmt.bindNull(7);
                } else {
                    stmt.bindString(7, value.getIconUrl());
                }
                if (value.getLabel() == null) {
                    stmt.bindNull(8);
                } else {
                    stmt.bindString(8, value.getLabel());
                }
                stmt.bindLong(9, value.getDownloadId());
                stmt.bindLong(10, value.getState());
                stmt.bindLong(11, value.getDownloadedBytes());
                stmt.bindLong(12, value.getTotalBytes());
                stmt.bindDouble(13, value.getProgress());
            }
        };
        this.__updateAdapterOfAssembleLocalInfo = new EntityDeletionOrUpdateAdapter<AssembleLocalInfo>(__db) { // from class: com.xiaopeng.appstore.bizcommon.entities.db.AssembleDao_Impl.2
            @Override // androidx.room.EntityDeletionOrUpdateAdapter, androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE OR REPLACE `AssembleLocalInfo` SET `assembleId` = ?,`packageName` = ?,`downloadUrl` = ?,`md5` = ?,`configUrl` = ?,`configMd5` = ?,`iconUrl` = ?,`label` = ?,`downloadId` = ?,`state` = ?,`downloadedBytes` = ?,`totalBytes` = ?,`progress` = ? WHERE `assembleId` = ?";
            }

            @Override // androidx.room.EntityDeletionOrUpdateAdapter
            public void bind(SupportSQLiteStatement stmt, AssembleLocalInfo value) {
                stmt.bindLong(1, value.getAssembleId());
                if (value.getPackageName() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getPackageName());
                }
                if (value.getDownloadUrl() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getDownloadUrl());
                }
                if (value.getMd5() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getMd5());
                }
                if (value.getConfigUrl() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getConfigUrl());
                }
                if (value.getConfigMd5() == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.getConfigMd5());
                }
                if (value.getIconUrl() == null) {
                    stmt.bindNull(7);
                } else {
                    stmt.bindString(7, value.getIconUrl());
                }
                if (value.getLabel() == null) {
                    stmt.bindNull(8);
                } else {
                    stmt.bindString(8, value.getLabel());
                }
                stmt.bindLong(9, value.getDownloadId());
                stmt.bindLong(10, value.getState());
                stmt.bindLong(11, value.getDownloadedBytes());
                stmt.bindLong(12, value.getTotalBytes());
                stmt.bindDouble(13, value.getProgress());
                stmt.bindLong(14, value.getAssembleId());
            }
        };
        this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) { // from class: com.xiaopeng.appstore.bizcommon.entities.db.AssembleDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from assemblelocalinfo";
            }
        };
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.AssembleDao
    public void insert(final AssembleLocalInfo info) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfAssembleLocalInfo.insert((EntityInsertionAdapter<AssembleLocalInfo>) info);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.AssembleDao
    public void update(final AssembleLocalInfo info) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__updateAdapterOfAssembleLocalInfo.handle(info);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.AssembleDao
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

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.AssembleDao
    public List<AssembleLocalInfo> queryAssembleDataList() {
        RoomSQLiteQuery roomSQLiteQuery;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select * from assemblelocalinfo", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "assembleId");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, VuiConstants.SCENE_PACKAGE_NAME);
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "downloadUrl");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "md5");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "configUrl");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "configMd5");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "iconUrl");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, VuiConstants.ELEMENT_LABEL);
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "downloadId");
            int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "state");
            int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "downloadedBytes");
            int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(query, "totalBytes");
            int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(query, "progress");
            roomSQLiteQuery = acquire;
            try {
                ArrayList arrayList = new ArrayList(query.getCount());
                while (query.moveToNext()) {
                    AssembleLocalInfo assembleLocalInfo = new AssembleLocalInfo();
                    ArrayList arrayList2 = arrayList;
                    assembleLocalInfo.setAssembleId(query.getInt(columnIndexOrThrow));
                    assembleLocalInfo.setPackageName(query.getString(columnIndexOrThrow2));
                    assembleLocalInfo.setDownloadUrl(query.getString(columnIndexOrThrow3));
                    assembleLocalInfo.setMd5(query.getString(columnIndexOrThrow4));
                    assembleLocalInfo.setConfigUrl(query.getString(columnIndexOrThrow5));
                    assembleLocalInfo.setConfigMd5(query.getString(columnIndexOrThrow6));
                    assembleLocalInfo.setIconUrl(query.getString(columnIndexOrThrow7));
                    assembleLocalInfo.setLabel(query.getString(columnIndexOrThrow8));
                    assembleLocalInfo.setDownloadId(query.getInt(columnIndexOrThrow9));
                    assembleLocalInfo.setState(query.getInt(columnIndexOrThrow10));
                    int i = columnIndexOrThrow;
                    assembleLocalInfo.setDownloadedBytes(query.getLong(columnIndexOrThrow11));
                    assembleLocalInfo.setTotalBytes(query.getLong(columnIndexOrThrow12));
                    assembleLocalInfo.setProgress(query.getFloat(columnIndexOrThrow13));
                    arrayList2.add(assembleLocalInfo);
                    arrayList = arrayList2;
                    columnIndexOrThrow = i;
                }
                ArrayList arrayList3 = arrayList;
                query.close();
                roomSQLiteQuery.release();
                return arrayList3;
            } catch (Throwable th) {
                th = th;
                query.close();
                roomSQLiteQuery.release();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            roomSQLiteQuery = acquire;
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.AssembleDao
    public AssembleLocalInfo queryAssembleData(final String downloadUrl) {
        AssembleLocalInfo assembleLocalInfo;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select * from assemblelocalinfo where downloadUrl=?", 1);
        if (downloadUrl == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, downloadUrl);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "assembleId");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, VuiConstants.SCENE_PACKAGE_NAME);
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "downloadUrl");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "md5");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "configUrl");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "configMd5");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "iconUrl");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, VuiConstants.ELEMENT_LABEL);
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "downloadId");
            int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "state");
            int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "downloadedBytes");
            int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(query, "totalBytes");
            int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(query, "progress");
            if (query.moveToFirst()) {
                AssembleLocalInfo assembleLocalInfo2 = new AssembleLocalInfo();
                assembleLocalInfo2.setAssembleId(query.getInt(columnIndexOrThrow));
                assembleLocalInfo2.setPackageName(query.getString(columnIndexOrThrow2));
                assembleLocalInfo2.setDownloadUrl(query.getString(columnIndexOrThrow3));
                assembleLocalInfo2.setMd5(query.getString(columnIndexOrThrow4));
                assembleLocalInfo2.setConfigUrl(query.getString(columnIndexOrThrow5));
                assembleLocalInfo2.setConfigMd5(query.getString(columnIndexOrThrow6));
                assembleLocalInfo2.setIconUrl(query.getString(columnIndexOrThrow7));
                assembleLocalInfo2.setLabel(query.getString(columnIndexOrThrow8));
                assembleLocalInfo2.setDownloadId(query.getInt(columnIndexOrThrow9));
                assembleLocalInfo2.setState(query.getInt(columnIndexOrThrow10));
                assembleLocalInfo2.setDownloadedBytes(query.getLong(columnIndexOrThrow11));
                assembleLocalInfo2.setTotalBytes(query.getLong(columnIndexOrThrow12));
                assembleLocalInfo2.setProgress(query.getFloat(columnIndexOrThrow13));
                assembleLocalInfo = assembleLocalInfo2;
            } else {
                assembleLocalInfo = null;
            }
            return assembleLocalInfo;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.AssembleDao
    public void remove(final String... downloadUrlList) {
        this.__db.assertNotSuspendingTransaction();
        StringBuilder newStringBuilder = StringUtil.newStringBuilder();
        newStringBuilder.append("delete from assemblelocalinfo where downloadUrl in (");
        StringUtil.appendPlaceholders(newStringBuilder, downloadUrlList.length);
        newStringBuilder.append(")");
        SupportSQLiteStatement compileStatement = this.__db.compileStatement(newStringBuilder.toString());
        int i = 1;
        for (String str : downloadUrlList) {
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
