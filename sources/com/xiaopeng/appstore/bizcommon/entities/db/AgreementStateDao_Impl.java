package com.xiaopeng.appstore.bizcommon.entities.db;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.xiaopeng.appstore.bizcommon.entities.AgreementStateEntity;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreContract;
import java.util.List;
/* loaded from: classes2.dex */
public final class AgreementStateDao_Impl extends AgreementStateDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<AgreementStateEntity> __insertionAdapterOfAgreementStateEntity;

    public AgreementStateDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfAgreementStateEntity = new EntityInsertionAdapter<AgreementStateEntity>(__db) { // from class: com.xiaopeng.appstore.bizcommon.entities.db.AgreementStateDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `UserAgreementStates` (`userId`,`agreed`) VALUES (?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, AgreementStateEntity value) {
                if (value.getUserId() == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, value.getUserId());
                }
                stmt.bindLong(2, value.getAgreed());
            }
        };
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.AgreementStateDao
    public void insert(final AgreementStateEntity data) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfAgreementStateEntity.insert((EntityInsertionAdapter<AgreementStateEntity>) data);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.AgreementStateDao
    public void insert(final List<AgreementStateEntity> list) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfAgreementStateEntity.insert(list);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.AgreementStateDao
    public AgreementStateEntity queryByUserId(final String userId) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select * from UserAgreementStates where userId=?", 1);
        if (userId == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, userId);
        }
        this.__db.assertNotSuspendingTransaction();
        AgreementStateEntity agreementStateEntity = null;
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, AppStoreContract.AgreementStage.USER_ID_COLUMN);
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "agreed");
            if (query.moveToFirst()) {
                agreementStateEntity = new AgreementStateEntity(query.getString(columnIndexOrThrow));
                agreementStateEntity.setAgreed(query.getInt(columnIndexOrThrow2));
            }
            return agreementStateEntity;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.AgreementStateDao
    public Cursor queryAll() {
        return this.__db.query(RoomSQLiteQuery.acquire("select * from UserAgreementStates", 0));
    }

    @Override // com.xiaopeng.appstore.bizcommon.entities.db.AgreementStateDao
    public Cursor query(final SupportSQLiteQuery query) {
        return this.__db.query(query);
    }
}
