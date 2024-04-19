package com.xiaopeng.appstore.appserver_common;

import android.app.Application;
import com.xiaopeng.aar.server.ServerConfig;
import com.xiaopeng.aar.server.ServerManager;
/* loaded from: classes2.dex */
public class AppServerHelper {
    private static final String TAG = "AppServerHelper";

    private AppServerHelper() {
    }

    public static void prepare() {
        ServerManager.get().initConfig(new ServerConfig.Builder().setWaitInit(true).useKeepAlive(true).useMock(true).setAutoUnSubscribeWhenNaPaDied(true).build());
    }

    public static void init(Application application) {
        init(application, true);
    }

    public static void init(Application application, boolean enableEvents) {
        ServerManager.get().setServerListener(new NapaHandler(application, enableEvents));
        ServerManager.get().init(application);
    }
}
