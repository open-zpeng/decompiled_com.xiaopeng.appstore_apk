package com.xiaopeng.appstore.xpcommon;

import android.app.Notification;
/* loaded from: classes2.dex */
public class NotificationHiddenUtils {
    private NotificationHiddenUtils() {
    }

    public static boolean hasDisplayListFlag(Notification notification) {
        return notification.hasDisplayFlag(4);
    }

    public static boolean hasDisplayBadgeFlag(Notification notification) {
        return notification.hasDisplayFlag(8);
    }
}
