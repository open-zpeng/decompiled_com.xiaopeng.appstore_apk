package com.xiaopeng.aar.server.ipc;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.xiaopeng.aar.R;
/* loaded from: classes2.dex */
public class KeepAliveService extends Service {
    private static final String CRITICAL = "Critical";

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        startForeground(1, buildForegroundNotification());
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
    }

    private Notification buildForegroundNotification() {
        return new Notification.Builder(this, CRITICAL).setContentTitle("KeepAliveService").setSmallIcon(R.drawable.x_ic_logo).build();
    }

    private void createNotificationChannel() {
        ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel(CRITICAL, CRITICAL, 3));
    }
}
