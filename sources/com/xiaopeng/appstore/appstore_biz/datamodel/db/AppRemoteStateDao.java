package com.xiaopeng.appstore.appstore_biz.datamodel.db;

import android.database.Cursor;
import androidx.sqlite.db.SupportSQLiteQuery;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppRemoteStateEntity;
import java.util.List;
/* loaded from: classes2.dex */
public abstract class AppRemoteStateDao {
    public abstract void insert(AppRemoteStateEntity data);

    public abstract void insert(List<AppRemoteStateEntity> list);

    public abstract Cursor queryAll();

    public abstract Cursor queryAll(SupportSQLiteQuery query);

    public abstract void remove(String packageName);

    public abstract void removeAll();

    public void insert(String packageName, int state) {
        AppRemoteStateEntity appRemoteStateEntity = new AppRemoteStateEntity(packageName);
        appRemoteStateEntity.setState(state);
        insert(appRemoteStateEntity);
    }

    public void clearThenInsert(List<AppRemoteStateEntity> list) {
        removeAll();
        insert(list);
    }
}
