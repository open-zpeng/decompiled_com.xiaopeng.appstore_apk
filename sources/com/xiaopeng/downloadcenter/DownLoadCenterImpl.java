package com.xiaopeng.downloadcenter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcelable;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import androidx.core.app.NotificationCompat;
import com.xiaopeng.appstore.appserver_common.NapaHandler;
import com.xiaopeng.downloadcenter.DownLoadCenterService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/* loaded from: classes2.dex */
class DownLoadCenterImpl implements DownLoadCenterService {
    private static final String CRITICAL = "Critical";
    private DownLoadCenterService.CallBack mCallBack;
    private DownLoadCenterService.CallBack2 mCallBack2;
    private Context mContext;
    private int mIcon;
    private NotificationManager mManager;
    private SparseArray<Bundle> mBundleSparseArray = new SparseArray<>();
    private SparseArray<Icon> mIconSparseArray = new SparseArray<>();
    private ExecutorService mExecutorService = Executors.newSingleThreadExecutor();

    @Override // com.xiaopeng.downloadcenter.DownLoadCenterService
    public void init(Context context, int i, DownLoadCenterService.CallBack callBack, DownLoadCenterService.CallBack2 callBack2) {
        log(NapaHandler.METHOD_INIT);
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mIcon = i;
        NotificationManager notificationManager = (NotificationManager) applicationContext.getSystemService("notification");
        this.mManager = notificationManager;
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(new NotificationChannel(CRITICAL, CRITICAL, 4));
        }
        this.mCallBack = callBack;
        this.mCallBack2 = callBack2;
    }

    @Override // com.xiaopeng.downloadcenter.DownLoadCenterService
    public void initItem(int i, String str, long j, int i2, boolean z, boolean z2, String str2) {
        log("initItem--id:" + i + ",name:" + str + ",progressMax:" + i2 + ",successText:" + str2);
        Bundle bundle = this.mBundleSparseArray.get(i);
        if (bundle == null) {
            bundle = new Bundle();
            this.mBundleSparseArray.put(i, bundle);
            bundle.putParcelable("xp.download.action.resume", DownLoadReceiver.resumePendingIntent(this.mContext, i));
            bundle.putParcelable("xp.download.action.retry", DownLoadReceiver.retryPendingIntent(this.mContext, i));
            bundle.putParcelable("xp.download.action.cancel", DownLoadReceiver.cancelPendingIntent(this.mContext, i));
            bundle.putInt("android.displayFlag", 64);
            bundle.putInt("xp.download.progressMax", i2);
            bundle.putInt("xp.download.button.status", 1);
        }
        if (z) {
            if (!bundle.containsKey("xp.download.action.pause")) {
                bundle.putParcelable("xp.download.action.pause", DownLoadReceiver.pausePendingIntent(this.mContext, i));
            }
        } else {
            bundle.remove("xp.download.action.pause");
        }
        if (z2) {
            if (!existAction(bundle, str2)) {
                bundle.putParcelable("xp.download.action.success", new Notification.Action.Builder((Icon) null, str2, DownLoadReceiver.successPendingIntent(this.mContext, i)).build());
            }
        } else {
            bundle.remove("xp.download.action.success");
        }
        bundle.putString("xp.download.name", str);
        bundle.putLong("xp.download.size", j);
    }

    private boolean existAction(Bundle bundle, String str) {
        if (bundle.containsKey("xp.download.action.success")) {
            Parcelable parcelable = bundle.getParcelable("xp.download.action.success");
            if (parcelable instanceof Notification.Action) {
                Notification.Action action = (Notification.Action) parcelable;
                return (action.title == null && str == null) || (action.title != null && action.title.equals(str));
            }
            return false;
        }
        return false;
    }

    @Override // com.xiaopeng.downloadcenter.DownLoadCenterService
    public Icon getIcon(int i) {
        return this.mIconSparseArray.get(i);
    }

    @Override // com.xiaopeng.downloadcenter.DownLoadCenterService
    public void setIcon(int i, Icon icon) {
        this.mIconSparseArray.put(i, icon);
    }

    @Override // com.xiaopeng.downloadcenter.DownLoadCenterService
    public NotifyBuilder notifyBuilder(int i) {
        return new NotifyBuilder(i, this);
    }

    @Override // com.xiaopeng.downloadcenter.DownLoadCenterService
    public void notifyProgressChanged(int i, int i2, int i3) {
        Bundle item = getItem(i);
        if (item == null) {
            return;
        }
        item.putInt("xp.download.status", 1);
        item.putInt("xp.download.progress", i2);
        item.putInt("xp.download.remaining.time", i3);
        notifyChanged(i, true, item);
    }

    @Override // com.xiaopeng.downloadcenter.DownLoadCenterService
    public void notifyStatusChanged(int i, int i2) {
        log("statusChanged--id:" + i + ",status:" + i2);
        Bundle item = getItem(i);
        if (item == null) {
            return;
        }
        if (i2 == 5) {
            item.putInt("xp.download.remaining.time", -1);
        }
        item.putInt("xp.download.status", i2);
        notifyChanged(i, false, item);
    }

    @Override // com.xiaopeng.downloadcenter.DownLoadCenterService
    public void notifyButtonChanged(int i, boolean z) {
        log("notifyButtonChanged--id:" + i + ",disable:" + z);
        Bundle item = getItem(i);
        if (item == null) {
            return;
        }
        item.putInt("xp.download.button.status", !z ? 1 : 0);
        notifyChanged(i, false, item);
    }

    @Override // com.xiaopeng.downloadcenter.DownLoadCenterService
    public ArrayList<Integer> loadNotifyIds() {
        Context context;
        ArrayList<Integer> arrayList = new ArrayList<>();
        if (this.mManager != null && (context = this.mContext) != null) {
            String packageName = context.getPackageName();
            for (StatusBarNotification statusBarNotification : this.mManager.getNotificationList(64)) {
                if (statusBarNotification.getPackageName().equals(packageName)) {
                    log("loadNotifyIds--sb " + statusBarNotification.getId() + " , " + statusBarNotification.getNotification().extras.getString(NotificationCompat.EXTRA_TITLE));
                    arrayList.add(Integer.valueOf(statusBarNotification.getId()));
                }
            }
        } else {
            log("loadNotifyIds--mManager or mContext is null ");
        }
        return arrayList;
    }

    @Override // com.xiaopeng.downloadcenter.DownLoadCenterService
    public void cancelAll() {
        this.mExecutorService.execute(new Runnable() { // from class: com.xiaopeng.downloadcenter.DownLoadCenterImpl.1
            @Override // java.lang.Runnable
            public void run() {
                DownLoadCenterImpl.this.log("cancelAll");
                if (DownLoadCenterImpl.this.mManager != null) {
                    Iterator<Integer> it = DownLoadCenterImpl.this.loadNotifyIds().iterator();
                    while (it.hasNext()) {
                        Integer next = it.next();
                        DownLoadCenterImpl.this.mManager.cancel(next.intValue());
                        DownLoadCenterImpl.this.log("cancelAll id " + next);
                    }
                }
            }
        });
    }

    @Override // com.xiaopeng.downloadcenter.DownLoadCenterService
    public DownLoadCenterService.CallBack getCallBack() {
        return this.mCallBack;
    }

    @Override // com.xiaopeng.downloadcenter.DownLoadCenterService
    public void cancel(final int i) {
        this.mExecutorService.execute(new Runnable() { // from class: com.xiaopeng.downloadcenter.DownLoadCenterImpl.2
            @Override // java.lang.Runnable
            public void run() {
                if (DownLoadCenterImpl.this.mManager != null) {
                    DownLoadCenterImpl.this.mManager.cancel(i);
                }
                DownLoadCenterImpl.this.log("cancel--id:" + i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Bundle getItem(int i) {
        DownLoadCenterService.CallBack2 callBack2;
        Bundle bundle = this.mBundleSparseArray.get(i);
        if (bundle != null || (callBack2 = this.mCallBack2) == null) {
            return bundle;
        }
        callBack2.onInitItem(i);
        return this.mBundleSparseArray.get(i);
    }

    private void notifyChanged(final int i, final boolean z, final Bundle bundle) {
        final Notification.Builder extras = new Notification.Builder(this.mContext, CRITICAL).setContentTitle(bundle.getString("xp.download.name")).setExtras(bundle);
        Icon icon = this.mIconSparseArray.get(i);
        if (icon != null) {
            extras.setSmallIcon(icon);
        } else {
            extras.setSmallIcon(this.mIcon);
        }
        if (z) {
            extras.setProgress(bundle.getInt("xp.download.progress"), bundle.getInt("xp.download.progressMax"), true);
        } else {
            extras.setProgress(100, 100, true);
        }
        this.mExecutorService.execute(new Runnable() { // from class: com.xiaopeng.downloadcenter.DownLoadCenterImpl.3
            @Override // java.lang.Runnable
            public void run() {
                if (DownLoadCenterImpl.this.mManager != null) {
                    DownLoadCenterImpl.this.mManager.notify(i, extras.build());
                }
                if (z) {
                    return;
                }
                DownLoadCenterImpl.this.log("notifyChanged--id:" + i + ", status:" + bundle.getInt("xp.download.status") + ",name:" + bundle.getString("xp.download.name"));
            }
        });
    }

    void notifyChanged(int i, String str, Icon icon, Bundle bundle) {
        notifyChanged(i, false, str, icon, bundle);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void notifyChanged(final int i, boolean z, String str, Icon icon, Bundle bundle) {
        if (TextUtils.isEmpty(str)) {
            str = bundle.getString("xp.download.name");
        }
        final Notification.Builder extras = new Notification.Builder(this.mContext, CRITICAL).setContentTitle(str).setExtras(bundle);
        if (z) {
            extras.setProgress(bundle.getInt("xp.download.progress"), bundle.getInt("xp.download.progressMax"), true);
        } else {
            extras.setProgress(100, 100, true);
        }
        if (icon != null) {
            extras.setSmallIcon(icon);
        } else {
            Icon icon2 = this.mIconSparseArray.get(i);
            if (icon2 != null) {
                extras.setSmallIcon(icon2);
            } else {
                extras.setSmallIcon(this.mIcon);
            }
        }
        extras.setDeleteIntent(DownLoadReceiver.deletePendingIntent(this.mContext, i));
        this.mExecutorService.execute(new Runnable() { // from class: com.xiaopeng.downloadcenter.DownLoadCenterImpl.4
            @Override // java.lang.Runnable
            public void run() {
                if (DownLoadCenterImpl.this.mManager != null) {
                    DownLoadCenterImpl.this.mManager.notify(i, extras.build());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log(String str) {
        Log.d("downloadCenter", str);
    }
}
