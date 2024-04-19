package com.xiaopeng.appstore.appserver_common.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.xiaopeng.appstore.appserver_common.NapaHandler;
import com.xiaopeng.appstore.appserver_common.ServerProvider;
/* loaded from: classes2.dex */
public class AppStoreService extends Service {
    private final ServerProvider mServerProvider = NapaHandler.SERVER_PROVIDER;
    private ServiceBinder mServiceBinder;

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mServerProvider.init(getApplicationContext());
        this.mServiceBinder = new ServiceBinder(this.mServerProvider);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mServiceBinder;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        this.mServiceBinder.clear();
    }
}
