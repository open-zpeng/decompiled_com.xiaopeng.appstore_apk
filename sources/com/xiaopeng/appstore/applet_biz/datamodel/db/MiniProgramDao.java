package com.xiaopeng.appstore.applet_biz.datamodel.db;

import com.xiaopeng.appstore.applet_biz.datamodel.entities.MiniProgramGroup;
import java.util.List;
/* loaded from: classes2.dex */
public abstract class MiniProgramDao {
    public abstract void clearMiniProgramList();

    public abstract List<MiniProgramGroup> getMiniProgramDataList();

    public abstract void insertMiniProgramList(List<MiniProgramGroup> packageList);

    public void clearThenInsert(List<MiniProgramGroup> miniProgramDataList) {
        clearMiniProgramList();
        insertMiniProgramList(miniProgramDataList);
    }
}
