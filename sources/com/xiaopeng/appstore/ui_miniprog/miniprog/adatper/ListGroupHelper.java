package com.xiaopeng.appstore.ui_miniprog.miniprog.adatper;

import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class ListGroupHelper<T> {
    private SparseArray<List<T>> mGroupMap;
    private IGroupStrategy<T> mGroupStrategy;

    /* loaded from: classes2.dex */
    public interface IGroupStrategy<T> {
        int getGroupIndex(T item);
    }

    public ListGroupHelper(IGroupStrategy<T> strategy, List<T> list) {
        this.mGroupStrategy = strategy;
        initData(list);
    }

    public void initData(List<T> list) {
        this.mGroupMap = new SparseArray<>(2);
        for (T t : list) {
            int groupIndex = this.mGroupStrategy.getGroupIndex(t);
            List<T> list2 = this.mGroupMap.get(groupIndex);
            if (list2 == null) {
                list2 = new ArrayList<>();
                this.mGroupMap.put(groupIndex, list2);
            }
            list2.add(t);
        }
    }

    public SparseArray<List<T>> getGroupData() {
        return this.mGroupMap;
    }
}
