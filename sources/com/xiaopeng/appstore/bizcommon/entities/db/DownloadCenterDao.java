package com.xiaopeng.appstore.bizcommon.entities.db;

import android.database.sqlite.SQLiteFullException;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.entities.DownloadCenterLocalInfo;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public abstract class DownloadCenterDao {
    private static final String TAG = "DownloadCenterDao";

    public abstract void insert(DownloadCenterLocalInfo info);

    public abstract DownloadCenterLocalInfo query(int id);

    public abstract List<DownloadCenterLocalInfo> query(String packageName);

    public abstract List<DownloadCenterLocalInfo> queryAll();

    public abstract void remove(int id);

    public abstract void remove(String packageName);

    public abstract void update(DownloadCenterLocalInfo info);

    public boolean tryUpdate(DownloadCenterLocalInfo info) {
        try {
            if (query(info.getId()) != null) {
                update(info);
                return true;
            }
            return false;
        } catch (SQLiteFullException e) {
            Logger.t(TAG).e("tryUpdate fail, ex: " + e, new Object[0]);
            e.printStackTrace();
            return false;
        }
    }

    public List<Integer> removeReturnOld(String packageName, int exceptId) {
        ArrayList arrayList = null;
        try {
            List<DownloadCenterLocalInfo> query = query(packageName);
            if (query != null) {
                Logger.t(TAG).d("removeReturnOld, list=" + query);
                ArrayList arrayList2 = new ArrayList(query.size());
                try {
                    for (DownloadCenterLocalInfo downloadCenterLocalInfo : query) {
                        if (downloadCenterLocalInfo.getId() != exceptId) {
                            arrayList2.add(Integer.valueOf(downloadCenterLocalInfo.getId()));
                        }
                    }
                    arrayList = arrayList2;
                } catch (SQLiteFullException e) {
                    e = e;
                    arrayList = arrayList2;
                    Logger.t(TAG).e("query and remove failed, ex: " + e, new Object[0]);
                    e.printStackTrace();
                    return arrayList;
                }
            }
            remove(packageName);
        } catch (SQLiteFullException e2) {
            e = e2;
        }
        return arrayList;
    }
}
