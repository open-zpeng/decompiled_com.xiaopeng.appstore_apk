package com.xiaopeng.aar.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.xiaopeng.aar.utils.LogUtils;
import com.xiaopeng.aar.utils.Utils;
import com.xiaopeng.lib.apirouter.server.ApiPublisherProvider;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class RunningConfig {
    private static final String TAG = "RunCg";
    private BroadcastReceiver mReceiver;

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final RunningConfig INSTANCE = new RunningConfig();

        private SingletonHolder() {
        }
    }

    public static RunningConfig get() {
        return SingletonHolder.INSTANCE;
    }

    private RunningConfig() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void init() {
        if (this.mReceiver == null) {
            LogUtils.d(TAG, "init : ");
            this.mReceiver = new DemoReceiver();
            if (ApiPublisherProvider.CONTEXT != null) {
                ApiPublisherProvider.CONTEXT.registerReceiver(this.mReceiver, new IntentFilter("NAPA_CONFIG"));
            }
        }
    }

    void release() {
        if (this.mReceiver != null) {
            LogUtils.d(TAG, "release : ");
            if (ApiPublisherProvider.CONTEXT != null) {
                ApiPublisherProvider.CONTEXT.unregisterReceiver(this.mReceiver);
            }
            this.mReceiver = null;
        }
    }

    /* loaded from: classes2.dex */
    private class DemoReceiver extends BroadcastReceiver {
        private DemoReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int parse;
            String stringExtra = intent.getStringExtra("v1");
            String stringExtra2 = intent.getStringExtra("v2");
            LogUtils.i(RunningConfig.TAG, String.format("onReceive  v1 : %s , v2 : %s ", stringExtra, stringExtra2));
            if (!"logLevel".equals(stringExtra) || (parse = Utils.parse(stringExtra2)) < 3) {
                return;
            }
            LogUtils.setLogLevel(parse);
        }
    }
}
