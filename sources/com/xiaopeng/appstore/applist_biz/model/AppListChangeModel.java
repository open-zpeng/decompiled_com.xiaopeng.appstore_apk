package com.xiaopeng.appstore.applist_biz.model;

import java.util.List;
/* loaded from: classes2.dex */
public class AppListChangeModel {
    public static final int ADD = 1;
    public static final int CHANGE = 3;
    public static final int REMOVE = 2;
    public List<Integer> data;
    public int type;

    public static AppListChangeModel createData(int type, List<Integer> indexList) {
        AppListChangeModel appListChangeModel = new AppListChangeModel();
        appListChangeModel.type = type;
        appListChangeModel.data = indexList;
        return appListChangeModel;
    }
}
