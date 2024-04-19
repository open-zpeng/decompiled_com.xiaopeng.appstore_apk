package com.xiaopeng.appstore.common_ui;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import com.xiaopeng.appstore.bizcommon.entities.BadgeInfo;
import com.xiaopeng.appstore.bizcommon.entities.NotificationKeyData;
import com.xiaopeng.appstore.bizcommon.entities.PackageUserKey;
import com.xiaopeng.appstore.common_ui.NotificationListener;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
/* loaded from: classes.dex */
public class NotificationObserverManager implements NotificationListener.NotificationsChangedListener {
    private static final String TAG = "NotificationObserverManager";
    private Launcher mLauncher;
    private Map<PackageUserKey, BadgeInfo> mPackageUserToBadgeInfos = new HashMap();
    private NotificationManager mNm = (NotificationManager) Utils.getApp().getSystemService("notification");
    private ComponentName mComponentName = new ComponentName(Utils.getPackageName(), NotificationListener.class.getName());

    /* loaded from: classes.dex */
    public interface Launcher {
        void updateIconBadges(final Set<PackageUserKey> updatedBadges);
    }

    public void stopObserve() {
    }

    public NotificationObserverManager(Launcher launcher) {
        this.mLauncher = launcher;
    }

    public void startObserve() {
        NotificationListener.getInstanceIfConnected();
    }

    @Override // com.xiaopeng.appstore.common_ui.NotificationListener.NotificationsChangedListener
    public void onNotificationPosted(PackageUserKey postedPackageUserKey, NotificationKeyData notificationKey, boolean shouldBeFilteredOut) {
        boolean addOrUpdateNotificationKey;
        BadgeInfo badgeInfo = this.mPackageUserToBadgeInfos.get(postedPackageUserKey);
        if (badgeInfo != null) {
            if (shouldBeFilteredOut) {
                addOrUpdateNotificationKey = badgeInfo.removeNotificationKey(notificationKey);
            } else {
                addOrUpdateNotificationKey = badgeInfo.addOrUpdateNotificationKey(notificationKey);
            }
            if (badgeInfo.getNotificationKeys().size() == 0) {
                this.mPackageUserToBadgeInfos.remove(postedPackageUserKey);
            }
        } else if (shouldBeFilteredOut) {
            addOrUpdateNotificationKey = false;
        } else {
            BadgeInfo badgeInfo2 = new BadgeInfo(postedPackageUserKey);
            badgeInfo2.addOrUpdateNotificationKey(notificationKey);
            this.mPackageUserToBadgeInfos.put(postedPackageUserKey, badgeInfo2);
            addOrUpdateNotificationKey = true;
        }
        if (addOrUpdateNotificationKey) {
            this.mLauncher.updateIconBadges(Utils.singletonHashSet(postedPackageUserKey));
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.NotificationListener.NotificationsChangedListener
    public void onNotificationRemoved(PackageUserKey removedPackageUserKey, NotificationKeyData notificationKey) {
        BadgeInfo badgeInfo = this.mPackageUserToBadgeInfos.get(removedPackageUserKey);
        if (badgeInfo == null || !badgeInfo.removeNotificationKey(notificationKey)) {
            return;
        }
        if (badgeInfo.getNotificationKeys().size() == 0) {
            this.mPackageUserToBadgeInfos.remove(removedPackageUserKey);
        }
        this.mLauncher.updateIconBadges(Utils.singletonHashSet(removedPackageUserKey));
    }

    @Override // com.xiaopeng.appstore.common_ui.NotificationListener.NotificationsChangedListener
    public void onNotificationFullRefresh(List<StatusBarNotification> activeNotifications) {
        if (activeNotifications == null) {
            return;
        }
        HashMap hashMap = new HashMap(this.mPackageUserToBadgeInfos);
        this.mPackageUserToBadgeInfos.clear();
        for (StatusBarNotification statusBarNotification : activeNotifications) {
            PackageUserKey fromNotification = PackageUserKey.fromNotification(statusBarNotification);
            BadgeInfo badgeInfo = this.mPackageUserToBadgeInfos.get(fromNotification);
            if (badgeInfo == null) {
                badgeInfo = new BadgeInfo(fromNotification);
                this.mPackageUserToBadgeInfos.put(fromNotification, badgeInfo);
            }
            badgeInfo.addOrUpdateNotificationKey(NotificationKeyData.fromNotification(statusBarNotification));
        }
        for (PackageUserKey packageUserKey : this.mPackageUserToBadgeInfos.keySet()) {
            BadgeInfo badgeInfo2 = (BadgeInfo) hashMap.get(packageUserKey);
            BadgeInfo badgeInfo3 = this.mPackageUserToBadgeInfos.get(packageUserKey);
            if (badgeInfo2 == null) {
                hashMap.put(packageUserKey, badgeInfo3);
            } else if (!badgeInfo2.shouldBeInvalidated(badgeInfo3)) {
                hashMap.remove(packageUserKey);
            }
        }
        if (hashMap.isEmpty()) {
            return;
        }
        this.mLauncher.updateIconBadges(hashMap.keySet());
    }

    public BadgeInfo getBadgeInfoForItem(String packageName, UserHandle user) {
        return this.mPackageUserToBadgeInfos.get(new PackageUserKey(packageName, user));
    }

    public BadgeInfo getBadgeInfo(PackageUserKey key) {
        return this.mPackageUserToBadgeInfos.get(key);
    }

    public List<StatusBarNotification> getStatusBarNotificationsForKeys(List<NotificationKeyData> notificationKeys) {
        NotificationListener instanceIfConnected = NotificationListener.getInstanceIfConnected();
        if (instanceIfConnected == null) {
            return Collections.EMPTY_LIST;
        }
        return instanceIfConnected.getNotificationsForKeys(notificationKeys);
    }

    public void cancelNotification(String notificationKey) {
        NotificationListener instanceIfConnected = NotificationListener.getInstanceIfConnected();
        if (instanceIfConnected == null) {
            return;
        }
        instanceIfConnected.cancelNotificationFromLauncher(notificationKey);
    }
}
