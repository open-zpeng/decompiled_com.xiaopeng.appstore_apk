package com.xiaopeng.appstore.applist_biz.logic.comparator;

import android.text.TextUtils;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.applist_biz.model.BaseAppItem;
import com.xiaopeng.appstore.applist_biz.model.FixedAppItem;
import com.xiaopeng.appstore.applist_biz.model.NormalAppItem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/* loaded from: classes2.dex */
public class AppIndexComparator implements Comparator<BaseAppItem> {
    private static final boolean ENABLE_LOG = false;
    private static final String END_STR = "#UserHandle{0}";
    private static final String PRE_STR = "NormalAppItem#";
    private static final String TAG = "AppIndexComparator";
    private final Map<String, Integer> mKeyIndexMap;
    private final Map<String, Integer> mPnIndexMap;
    private final LinkedList<String> mXpAppList;
    private final int mDefaultFixedItemIndex = Integer.MIN_VALUE;
    private final List<String> mFixedItemMap = new ArrayList();
    private final int mFirstXpAppSavedIndex = findFirstXpIndex(0);

    private void printDebug(String msg) {
    }

    public AppIndexComparator(List<String> xpAppList, Map<String, Integer> keyIndexMap, Map<String, Integer> pnIndexMap) {
        this.mXpAppList = new LinkedList<>(xpAppList);
        this.mKeyIndexMap = keyIndexMap;
        this.mPnIndexMap = pnIndexMap;
        logInfo("-----Start sort", true);
    }

    @Override // java.util.Comparator
    public int compare(BaseAppItem o1, BaseAppItem o2) {
        CompareHolder findLocalIndex = findLocalIndex(o1);
        CompareHolder findLocalIndex2 = findLocalIndex(o2);
        int compare = Integer.compare(findLocalIndex.index, findLocalIndex2.index);
        if (compare == 0) {
            printInfo("Index same, compare with priority, item1:" + o1 + "->" + findLocalIndex + ", item2:" + o2 + "->" + findLocalIndex2);
            return Integer.compare(findLocalIndex.priority, findLocalIndex2.priority);
        }
        return compare;
    }

    private CompareHolder findLocalIndex(BaseAppItem appItem) {
        String key = appItem.getKey();
        if (this.mKeyIndexMap != null && !TextUtils.isEmpty(key)) {
            Integer num = this.mKeyIndexMap.get(key);
            if (num == null && key.contains("#")) {
                num = this.mKeyIndexMap.get(PRE_STR + key.substring(key.indexOf("#")) + END_STR);
            }
            Optional ofNullable = Optional.ofNullable(num);
            if (!ofNullable.isPresent()) {
                ofNullable = Optional.ofNullable(this.mPnIndexMap.get(appItem.packageName));
            }
            if (ofNullable.isPresent()) {
                int intValue = ((Integer) ofNullable.orElse(Integer.MAX_VALUE)).intValue();
                printDebug("findLocalIndex sortedItem pn=" + appItem.packageName + " index=" + intValue);
                return CompareHolder.onlyIndex(intValue);
            }
        }
        if (appItem instanceof FixedAppItem) {
            int indexOf = this.mFixedItemMap.indexOf(appItem.packageName);
            if (indexOf < 0) {
                this.mFixedItemMap.add(appItem.packageName);
                indexOf = this.mFixedItemMap.size() - 1;
            }
            int i = indexOf - Integer.MIN_VALUE;
            printDebug("findLocalIndex fixedItem pn=" + appItem.packageName + " index=" + i + " map=" + this.mFixedItemMap);
            return CompareHolder.onlyIndex(i);
        } else if (appItem instanceof NormalAppItem) {
            String str = ((NormalAppItem) appItem).packageName;
            if (this.mXpAppList.contains(str)) {
                int i2 = this.mFirstXpAppSavedIndex;
                int indexOf2 = this.mXpAppList.indexOf(str);
                int i3 = indexOf2 + i2;
                printDebug("findLocalIndex normalItem pn=" + appItem.packageName + ", indexCurrent=" + i3 + ", firstItemIndex=" + i2 + ", inXpList=" + indexOf2);
                return CompareHolder.withPriority(i3, -1);
            }
            printDebug("findLocalIndex normalItem pn=" + appItem.packageName + " indexCurrent=2147483647");
            return CompareHolder.onlyIndex(Integer.MAX_VALUE);
        } else {
            return CompareHolder.onlyIndex(Integer.MAX_VALUE);
        }
    }

    private int findFirstXpIndex(int defaultIndex) {
        Iterator<String> it = this.mXpAppList.iterator();
        int i = Integer.MAX_VALUE;
        while (it.hasNext()) {
            int intValue = ((Integer) Optional.ofNullable(this.mPnIndexMap.get(it.next())).orElse(Integer.MAX_VALUE)).intValue();
            if (intValue < i) {
                i = intValue;
            }
        }
        return i == Integer.MAX_VALUE ? defaultIndex : i;
    }

    private void printInfo(String msg) {
        logInfo(msg, false);
    }

    private static void logInfo(String msg, boolean printLog) {
        if (printLog) {
            log(4, msg);
        }
    }

    private static void log(int priority, String msg) {
        Logger.log(priority, TAG, msg, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class CompareHolder {
        public static final int PRIORITY_HIGH = -2;
        public static final int PRIORITY_LOW = 0;
        public static final int PRIORITY_MEDIUM = -1;
        public int index;
        public int priority;

        public static CompareHolder onlyIndex(int index) {
            return new CompareHolder(index);
        }

        public static CompareHolder withPriority(int index, int priority) {
            return new CompareHolder(index, priority);
        }

        public String toString() {
            return "CompareHolder{index=" + this.index + ", priority=" + this.priority + '}';
        }

        private CompareHolder(int index) {
            this.priority = 0;
            this.index = index;
        }

        private CompareHolder(int index, int priority) {
            this.priority = 0;
            this.index = index;
            this.priority = priority;
        }
    }
}
