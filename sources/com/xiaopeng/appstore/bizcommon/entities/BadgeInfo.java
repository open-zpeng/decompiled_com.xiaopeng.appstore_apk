package com.xiaopeng.appstore.bizcommon.entities;

import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class BadgeInfo {
    public static final int MAX_COUNT = 999;
    private List<NotificationKeyData> mNotificationKeys = new ArrayList();
    private PackageUserKey mPackageUserKey;
    private int mTotalCount;

    public boolean shouldBeInvalidated(BadgeInfo newBadge) {
        return true;
    }

    public BadgeInfo(PackageUserKey packageUserKey) {
        this.mPackageUserKey = packageUserKey;
    }

    public boolean addOrUpdateNotificationKey(NotificationKeyData notificationKey) {
        int indexOf = this.mNotificationKeys.indexOf(notificationKey);
        NotificationKeyData notificationKeyData = indexOf == -1 ? null : this.mNotificationKeys.get(indexOf);
        if (notificationKeyData != null) {
            if (notificationKeyData.count == notificationKey.count) {
                return false;
            }
            int i = this.mTotalCount - notificationKeyData.count;
            this.mTotalCount = i;
            this.mTotalCount = i + notificationKey.count;
            notificationKeyData.count = notificationKey.count;
            return true;
        }
        boolean add = this.mNotificationKeys.add(notificationKey);
        if (add) {
            this.mTotalCount += notificationKey.count;
        }
        return add;
    }

    public boolean removeNotificationKey(NotificationKeyData notificationKey) {
        boolean remove = this.mNotificationKeys.remove(notificationKey);
        if (remove) {
            this.mTotalCount -= notificationKey.count;
        }
        return remove;
    }

    public List<NotificationKeyData> getNotificationKeys() {
        return this.mNotificationKeys;
    }

    public int getNotificationCount() {
        return Math.min(this.mTotalCount, 999);
    }
}
