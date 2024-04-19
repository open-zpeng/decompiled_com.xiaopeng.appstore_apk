package com.xiaopeng.appstore.bizcommon.entities.db;

import android.database.Cursor;
import androidx.sqlite.db.SupportSQLiteQuery;
import com.xiaopeng.appstore.bizcommon.entities.AgreementStateEntity;
import java.util.List;
/* loaded from: classes2.dex */
public abstract class AgreementStateDao {
    public abstract void insert(AgreementStateEntity data);

    public abstract void insert(List<AgreementStateEntity> list);

    public abstract Cursor query(SupportSQLiteQuery query);

    public abstract Cursor queryAll();

    public abstract AgreementStateEntity queryByUserId(String userId);
}
