package com.xiaopeng.appstore.xpcommon;

import android.app.NotificationManager;
import android.service.notification.StatusBarNotification;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes2.dex */
public class NotificationUtils {
    public static final int FLAG_DISPLAY_DOWNLOAD = 64;

    private NotificationUtils() {
    }

    public static List<StatusBarNotification> getDownloadNotifications(NotificationManager nm) {
        StatusBarNotification[] activeNotifications = nm.getActiveNotifications();
        if (activeNotifications != null) {
            ArrayList arrayList = new ArrayList(Arrays.asList(activeNotifications));
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (((StatusBarNotification) it.next()).getNotification().extras.getInt("android.displayFlag") != 64) {
                    it.remove();
                }
            }
            return arrayList;
        }
        return null;
    }
}
