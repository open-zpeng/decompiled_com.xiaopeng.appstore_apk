package com.xiaopeng.appstore.common_ui;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.entities.NotificationGroup;
import com.xiaopeng.appstore.bizcommon.entities.NotificationKeyData;
import com.xiaopeng.appstore.bizcommon.entities.PackageUserKey;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.appstore.xpcommon.NotificationHiddenUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/* loaded from: classes.dex */
public class NotificationListener extends NotificationListenerService {
    public static final String TAG = "NotificationListener";
    private static boolean sIsConnected;
    private static boolean sIsCreated;
    private static NotificationListener sNotificationListenerInstance;
    private static NotificationsChangedListener sNotificationsChangedListener;
    private static StatusBarNotificationsChangedListener sStatusBarNotificationsChangedListener;
    private String mLastKeyDismissedByLauncher;
    private final NotificationListenerService.Ranking mTempRanking = new NotificationListenerService.Ranking();
    private final Map<String, NotificationGroup> mNotificationGroupMap = new HashMap();
    private final Map<String, String> mNotificationGroupKeyMap = new HashMap();

    /* loaded from: classes.dex */
    public interface NotificationsChangedListener {
        void onNotificationFullRefresh(List<StatusBarNotification> activeNotifications);

        void onNotificationPosted(PackageUserKey postedPackageUserKey, NotificationKeyData notificationKey, boolean shouldBeFilteredOut);

        void onNotificationRemoved(PackageUserKey removedPackageUserKey, NotificationKeyData notificationKey);
    }

    /* loaded from: classes.dex */
    public interface StatusBarNotificationsChangedListener {
        void onNotificationPosted(StatusBarNotification sbn);

        void onNotificationRemoved(StatusBarNotification sbn);
    }

    public NotificationListener() {
        sNotificationListenerInstance = this;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: this=" + getClass().getSimpleName() + " hashcode=" + hashCode());
        sIsCreated = true;
    }

    @Override // android.service.notification.NotificationListenerService, android.app.Service
    public void onDestroy() {
        Log.d(TAG, "onDestroy: this=" + getClass().getSimpleName() + " hashcode=" + hashCode());
        sIsCreated = false;
        super.onDestroy();
    }

    public static NotificationListener getInstanceIfConnected() {
        if (sIsConnected) {
            return sNotificationListenerInstance;
        }
        return null;
    }

    public static void setNotificationsChangedListener(NotificationsChangedListener listener) {
        NotificationsChangedListener notificationsChangedListener;
        sNotificationsChangedListener = listener;
        NotificationListener instanceIfConnected = getInstanceIfConnected();
        if (instanceIfConnected != null) {
            instanceIfConnected.onNotificationFullRefresh();
        } else if (sIsCreated || (notificationsChangedListener = sNotificationsChangedListener) == null) {
        } else {
            notificationsChangedListener.onNotificationFullRefresh(Collections.emptyList());
        }
    }

    public static void setStatusBarNotificationsChangedListener(StatusBarNotificationsChangedListener listener) {
        sStatusBarNotificationsChangedListener = listener;
    }

    public static void removeNotificationsChangedListener() {
        sNotificationsChangedListener = null;
    }

    public static void removeStatusBarNotificationsChangedListener() {
        sStatusBarNotificationsChangedListener = null;
    }

    @Override // android.service.notification.NotificationListenerService
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.d(TAG, "onListenerConnected: this=" + getClass().getSimpleName() + " hashcode=" + hashCode());
        sIsConnected = true;
        onNotificationFullRefresh();
    }

    private void onNotificationFullRefresh() {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$NotificationListener$Slwq57SDE-Co-VnwaFvbfuWuQU4
            @Override // java.lang.Runnable
            public final void run() {
                NotificationListener.this.lambda$onNotificationFullRefresh$1$NotificationListener();
            }
        });
    }

    public /* synthetic */ void lambda$onNotificationFullRefresh$1$NotificationListener() {
        final List<StatusBarNotification> arrayList;
        if (sIsConnected) {
            try {
                arrayList = filterNotifications(getActiveNotifications());
            } catch (SecurityException unused) {
                Log.e(TAG, "SecurityException: failed to fetch notifications");
                arrayList = new ArrayList<>();
            }
        } else {
            arrayList = new ArrayList<>();
        }
        Logger.t(TAG).d("onNotificationFullRefresh activeNotifications=" + arrayList);
        AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$NotificationListener$ZBEiaaG8ldCksgd932hr4kOrQsw
            @Override // java.lang.Runnable
            public final void run() {
                NotificationListener.lambda$onNotificationFullRefresh$0(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onNotificationFullRefresh$0(List list) {
        NotificationsChangedListener notificationsChangedListener = sNotificationsChangedListener;
        if (notificationsChangedListener != null) {
            notificationsChangedListener.onNotificationFullRefresh(list);
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public void onListenerDisconnected() {
        Log.d(TAG, "onListenerDisconnected: this=" + getClass().getSimpleName() + " hashcode=" + hashCode());
        sIsConnected = false;
        super.onListenerDisconnected();
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationPosted(final StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        if (sbn == null || Utils.getPackageName().equals(sbn.getPackageName())) {
            return;
        }
        Logger.t(TAG).d("onNotificationPosted pn=" + sbn.getPackageName() + " number=" + sbn.getNotification().number);
        AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$NotificationListener$eag81l6BEMuSGxPNuOss2S7y8I0
            @Override // java.lang.Runnable
            public final void run() {
                NotificationListener.this.lambda$onNotificationPosted$2$NotificationListener(sbn);
            }
        });
        StatusBarNotificationsChangedListener statusBarNotificationsChangedListener = sStatusBarNotificationsChangedListener;
        if (statusBarNotificationsChangedListener != null) {
            statusBarNotificationsChangedListener.onNotificationPosted(sbn);
        }
    }

    public /* synthetic */ void lambda$onNotificationPosted$2$NotificationListener(StatusBarNotification statusBarNotification) {
        if (sNotificationsChangedListener != null) {
            NotificationPostedMsg notificationPostedMsg = new NotificationPostedMsg(statusBarNotification);
            sNotificationsChangedListener.onNotificationPosted(notificationPostedMsg.packageUserKey, notificationPostedMsg.notificationKey, notificationPostedMsg.shouldBeFilteredOut);
            Logger.t(TAG).d("onNotificationPosted packageUserKey=" + notificationPostedMsg.packageUserKey + " notificationKey=" + notificationPostedMsg.notificationKey + " shouldBeFilteredOut=" + notificationPostedMsg.shouldBeFilteredOut);
        }
    }

    /* loaded from: classes.dex */
    private class NotificationPostedMsg {
        final NotificationKeyData notificationKey;
        final PackageUserKey packageUserKey;
        final boolean shouldBeFilteredOut;

        NotificationPostedMsg(StatusBarNotification sbn) {
            this.packageUserKey = PackageUserKey.fromNotification(sbn);
            this.notificationKey = NotificationKeyData.fromNotification(sbn);
            this.shouldBeFilteredOut = NotificationListener.this.shouldBeFilteredOut(sbn);
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRemoved(final StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        if (sbn == null) {
            return;
        }
        Logger.t(TAG).d("onNotificationRemoved pn=" + sbn.getPackageName() + " notification=" + sbn.getNotification());
        AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$NotificationListener$yaVxExZQufN8YOkIeDP3RvF2mcY
            @Override // java.lang.Runnable
            public final void run() {
                NotificationListener.lambda$onNotificationRemoved$3(sbn);
            }
        });
        StatusBarNotificationsChangedListener statusBarNotificationsChangedListener = sStatusBarNotificationsChangedListener;
        if (statusBarNotificationsChangedListener != null) {
            statusBarNotificationsChangedListener.onNotificationRemoved(sbn);
        }
        NotificationGroup notificationGroup = this.mNotificationGroupMap.get(sbn.getGroupKey());
        String key = sbn.getKey();
        if (notificationGroup != null) {
            notificationGroup.removeChildKey(key);
            if (notificationGroup.isEmpty()) {
                if (key.equals(this.mLastKeyDismissedByLauncher)) {
                    cancelNotification(notificationGroup.getGroupSummaryKey());
                }
                this.mNotificationGroupMap.remove(sbn.getGroupKey());
            }
        }
        if (key.equals(this.mLastKeyDismissedByLauncher)) {
            this.mLastKeyDismissedByLauncher = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onNotificationRemoved$3(StatusBarNotification statusBarNotification) {
        NotificationsChangedListener notificationsChangedListener = sNotificationsChangedListener;
        if (notificationsChangedListener != null) {
            notificationsChangedListener.onNotificationRemoved(PackageUserKey.fromNotification(statusBarNotification), NotificationKeyData.fromNotification(statusBarNotification));
        }
    }

    public void cancelNotificationFromLauncher(String key) {
        this.mLastKeyDismissedByLauncher = key;
        cancelNotification(key);
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        super.onNotificationRankingUpdate(rankingMap);
        Logger.t(TAG).d("onNotificationRankingUpdate rankingMap=" + rankingMap.toString());
        if (sIsConnected) {
            for (StatusBarNotification statusBarNotification : getActiveNotifications(rankingMap.getOrderedKeys())) {
                updateGroupKeyIfNecessary(statusBarNotification);
            }
        }
    }

    private void updateGroupKeyIfNecessary(StatusBarNotification sbn) {
        String key = sbn.getKey();
        String str = this.mNotificationGroupKeyMap.get(key);
        String groupKey = sbn.getGroupKey();
        if (str == null || !str.equals(groupKey)) {
            this.mNotificationGroupKeyMap.put(key, groupKey);
            if (str != null && this.mNotificationGroupMap.containsKey(str)) {
                NotificationGroup notificationGroup = this.mNotificationGroupMap.get(str);
                notificationGroup.removeChildKey(key);
                if (notificationGroup.isEmpty()) {
                    this.mNotificationGroupMap.remove(str);
                }
            }
        }
        if (!sbn.isGroup() || groupKey == null) {
            return;
        }
        NotificationGroup notificationGroup2 = this.mNotificationGroupMap.get(groupKey);
        if (notificationGroup2 == null) {
            notificationGroup2 = new NotificationGroup();
            this.mNotificationGroupMap.put(groupKey, notificationGroup2);
        }
        if ((sbn.getNotification().flags & 512) != 0) {
            notificationGroup2.setGroupSummaryKey(key);
        } else {
            notificationGroup2.addChildKey(key);
        }
    }

    public List<StatusBarNotification> getNotificationsForKeys(List<NotificationKeyData> keys) {
        StatusBarNotification[] activeNotifications = getActiveNotifications((String[]) NotificationKeyData.extractKeysOnly(keys).toArray(new String[keys.size()]));
        return activeNotifications == null ? Collections.emptyList() : Arrays.asList(activeNotifications);
    }

    private List<StatusBarNotification> filterNotifications(StatusBarNotification[] notifications) {
        if (notifications == null) {
            return null;
        }
        ArraySet arraySet = new ArraySet();
        for (int i = 0; i < notifications.length; i++) {
            if (shouldBeFilteredOut(notifications[i])) {
                arraySet.add(Integer.valueOf(i));
            }
        }
        ArrayList arrayList = new ArrayList(notifications.length - arraySet.size());
        for (int i2 = 0; i2 < notifications.length; i2++) {
            if (!arraySet.contains(Integer.valueOf(i2))) {
                arrayList.add(notifications[i2]);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldBeFilteredOut(StatusBarNotification sbn) {
        Notification notification = sbn.getNotification();
        updateGroupKeyIfNecessary(sbn);
        getCurrentRanking().getRanking(sbn.getKey(), this.mTempRanking);
        if (this.mTempRanking.canShowBadge()) {
            if (!this.mTempRanking.getChannel().getId().equals(NotificationChannelCompat.DEFAULT_CHANNEL_ID) || (notification.flags & 2) == 0) {
                if (NotificationHiddenUtils.hasDisplayListFlag(notification) || NotificationHiddenUtils.hasDisplayBadgeFlag(notification)) {
                    return ((notification.flags & 512) != 0) || (TextUtils.isEmpty(notification.extras.getCharSequence(NotificationCompat.EXTRA_TITLE)) && TextUtils.isEmpty(notification.extras.getCharSequence(NotificationCompat.EXTRA_TEXT)));
                }
                return true;
            }
            return true;
        }
        return true;
    }
}
