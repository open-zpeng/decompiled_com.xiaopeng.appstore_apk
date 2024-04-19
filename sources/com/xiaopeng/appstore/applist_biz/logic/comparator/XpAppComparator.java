package com.xiaopeng.appstore.applist_biz.logic.comparator;

import com.xiaopeng.appstore.applist_biz.model.BaseAppItem;
import com.xiaopeng.appstore.applist_biz.model.NormalAppItem;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
/* loaded from: classes2.dex */
public class XpAppComparator implements Comparator<BaseAppItem> {
    private final LinkedList<String> mXpAppList;

    public XpAppComparator(List<String> xpAppList) {
        this.mXpAppList = new LinkedList<>(xpAppList);
    }

    @Override // java.util.Comparator
    public int compare(BaseAppItem o1, BaseAppItem o2) {
        int findIndex = findIndex(o1);
        int findIndex2 = findIndex(o2);
        if (findIndex > findIndex2) {
            return 1;
        }
        return findIndex < findIndex2 ? -1 : 0;
    }

    private int findIndex(BaseAppItem appItem) {
        if (appItem instanceof NormalAppItem) {
            String str = ((NormalAppItem) appItem).packageName;
            if (this.mXpAppList.contains(str)) {
                return this.mXpAppList.indexOf(str);
            }
            return Integer.MAX_VALUE;
        }
        return Integer.MAX_VALUE;
    }
}
