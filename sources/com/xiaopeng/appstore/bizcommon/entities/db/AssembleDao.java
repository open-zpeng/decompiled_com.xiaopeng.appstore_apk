package com.xiaopeng.appstore.bizcommon.entities.db;

import com.xiaopeng.appstore.bizcommon.entities.AssembleLocalInfo;
import java.util.List;
/* loaded from: classes2.dex */
public abstract class AssembleDao {
    public abstract void insert(AssembleLocalInfo info);

    public abstract AssembleLocalInfo queryAssembleData(String downloadUrl);

    public abstract List<AssembleLocalInfo> queryAssembleDataList();

    public abstract void remove(String... downloadUrlList);

    public abstract void removeAll();

    public abstract void update(AssembleLocalInfo info);
}
