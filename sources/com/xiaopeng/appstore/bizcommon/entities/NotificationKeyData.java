package com.xiaopeng.appstore.bizcommon.entities;

import android.app.Notification;
import android.service.notification.StatusBarNotification;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class NotificationKeyData {
    public int count;
    public final String notificationKey;
    public final String shortcutId;

    private NotificationKeyData(String notificationKey, String shortcutId, int count) {
        this.notificationKey = notificationKey;
        this.shortcutId = shortcutId;
        this.count = Math.max(1, count);
    }

    public static NotificationKeyData fromNotification(StatusBarNotification sbn) {
        Notification notification = sbn.getNotification();
        return new NotificationKeyData(sbn.getKey(), notification.getShortcutId(), notification.number);
    }

    public static List<String> extractKeysOnly(List<NotificationKeyData> notificationKeys) {
        ArrayList arrayList = new ArrayList(notificationKeys.size());
        for (NotificationKeyData notificationKeyData : notificationKeys) {
            arrayList.add(notificationKeyData.notificationKey);
        }
        return arrayList;
    }

    public String toString() {
        return "notificationKey=" + this.notificationKey + "#shortcutId=" + this.shortcutId;
    }

    public boolean equals(Object obj) {
        if (obj instanceof NotificationKeyData) {
            return ((NotificationKeyData) obj).notificationKey.equals(this.notificationKey);
        }
        return false;
    }
}
