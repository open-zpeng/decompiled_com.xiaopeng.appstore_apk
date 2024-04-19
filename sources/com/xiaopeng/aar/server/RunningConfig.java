package com.xiaopeng.aar.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.xiaopeng.aar.Apps;
import com.xiaopeng.aar.utils.LogUtils;
import com.xiaopeng.aar.utils.Utils;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class RunningConfig {
    private static final String TAG = "RunCg";
    private BroadcastReceiver mReceiver;

    RunningConfig() {
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final RunningConfig INSTANCE = new RunningConfig();

        private SingletonHolder() {
        }
    }

    public static RunningConfig get() {
        return SingletonHolder.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void init(Context context) {
        if (this.mReceiver == null) {
            this.mReceiver = new Receiver();
            String str = Apps.getAppId(context) + "_CONFIG";
            LogUtils.d(TAG, "init action : " + str);
            context.registerReceiver(this.mReceiver, new IntentFilter(str));
        }
    }

    /* loaded from: classes2.dex */
    private class Receiver extends BroadcastReceiver {
        private Receiver() {
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
