package com.xiaopeng.appstore.applist_biz.datamodel.db;

import androidx.lifecycle.LiveData;
import com.xiaopeng.appstore.applist_biz.datamodel.entities.LocalData;
import java.util.List;
/* loaded from: classes2.dex */
public abstract class LocalAppDao {
    public abstract void addDbList(List<LocalData> list);

    public abstract long addLocalData(LocalData localData);

    public abstract LocalData findDbData(String key);

    public abstract LocalData getDbData(String key);

    public abstract List<LocalData> getDbLocalData(List<String> keyList);

    public abstract int getIndex(String key);

    public abstract LiveData<List<LocalData>> getLocalData();

    public abstract int getMaxIndex();

    public abstract List<LocalData> getPureLocalData();

    public abstract void removeAll();

    public abstract void removeData(String key);

    public abstract void removeData(List<String> keyList);

    public abstract int setIndex(String key, int index);

    public void clearThenAddList(List<LocalData> list) {
        removeAll();
        addDbList(list);
    }
}
