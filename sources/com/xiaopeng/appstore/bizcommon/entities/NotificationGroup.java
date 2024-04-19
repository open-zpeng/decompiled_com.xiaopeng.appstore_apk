package com.xiaopeng.appstore.bizcommon.entities;

import java.util.HashSet;
import java.util.Set;
/* loaded from: classes2.dex */
public class NotificationGroup {
    private Set<String> mChildKeys = new HashSet();
    private String mGroupSummaryKey;

    public void setGroupSummaryKey(String groupSummaryKey) {
        this.mGroupSummaryKey = groupSummaryKey;
    }

    public String getGroupSummaryKey() {
        return this.mGroupSummaryKey;
    }

    public void addChildKey(String childKey) {
        this.mChildKeys.add(childKey);
    }

    public void removeChildKey(String childKey) {
        this.mChildKeys.remove(childKey);
    }

    public boolean isEmpty() {
        return this.mChildKeys.isEmpty();
    }
}
