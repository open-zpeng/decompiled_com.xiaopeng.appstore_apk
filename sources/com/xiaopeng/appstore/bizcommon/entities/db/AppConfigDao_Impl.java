package com.xiaopeng.appstore.bizcommon.entities.db;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.xiaopeng.appstore.bizcommon.entities.AppConfigInfo;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public final class AppConfigDao_Impl extends AppConfigDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<AppConfigInfo> __insertionAdapterOfAppConfigInfo;

    public AppConfigDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfAppConfigInfo = new EntityInsertionAdapter<AppConfigInfo>(__db) { // from class: com.xiaopeng.appstore.bizcommon.entities.db.AppConfigDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `AppConfigInfo` (`packageName`,`configUrl`) VALUES (?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, AppConfigInfo value) {
                if (value.getPackageName() == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, value.getPackageName());
                }
                if (value.getConfigUrl() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getConfigUrl());
                }
            }
        };
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.AppConfigDao
    public void insert(final AppConfigInfo info) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfAppConfigInfo.insert((EntityInsertionAdapter<AppConfigInfo>) info);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.AppConfigDao
    public List<AppConfigInfo> queryAppConfigInfoList() {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select * from appConfigInfo", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, VuiConstants.SCENE_PACKAGE_NAME);
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "configUrl");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                AppConfigInfo appConfigInfo = new AppConfigInfo();
                appConfigInfo.setPackageName(query.getString(columnIndexOrThrow));
                appConfigInfo.setConfigUrl(query.getString(columnIndexOrThrow2));
                arrayList.add(appConfigInfo);
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }
}
