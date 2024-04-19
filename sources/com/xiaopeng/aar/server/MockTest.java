package com.xiaopeng.aar.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.xiaopeng.aar.Apps;
import com.xiaopeng.aar.server.MockTest;
import com.xiaopeng.aar.utils.LogUtils;
import com.xiaopeng.aar.utils.ThreadUtils;
import com.xiaopeng.xpui.BuildConfig;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class MockTest {
    private static final String TAG = "Mock";
    private BroadcastReceiver mReceiver;

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final MockTest INSTANCE = new MockTest();

        private SingletonHolder() {
        }
    }

    public static MockTest get() {
        return SingletonHolder.INSTANCE;
    }

    private MockTest() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void init(Context context) {
        if (this.mReceiver == null) {
            this.mReceiver = new DemoReceiver();
            String str = Apps.getAppId(context) + "_MOCK";
            LogUtils.d(TAG, "init action : " + str);
            context.registerReceiver(this.mReceiver, new IntentFilter(str));
        }
    }

    void release(Context context) {
        if (this.mReceiver != null) {
            LogUtils.d(TAG, "release : ");
            context.unregisterReceiver(this.mReceiver);
            this.mReceiver = null;
        }
    }

    /* loaded from: classes2.dex */
    private class DemoReceiver extends BroadcastReceiver {
        private DemoReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            final String stringExtra = intent.getStringExtra("v1");
            final String stringExtra2 = intent.getStringExtra("v2");
            final String stringExtra3 = intent.getStringExtra(BuildConfig.FLAVOR);
            final String stringExtra4 = intent.getStringExtra("v4");
            LogUtils.i(MockTest.TAG, "onReceive  module : " + stringExtra + ", msgId : " + stringExtra2 + ",data : " + stringExtra3);
            ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.aar.server.-$$Lambda$MockTest$DemoReceiver$MzjPKqErKKSPwearp5-cb0yozGo
                @Override // java.lang.Runnable
                public final void run() {
                    MockTest.DemoReceiver.lambda$onReceive$0(stringExtra4, stringExtra, stringExtra2, stringExtra3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ void lambda$onReceive$0(String str, String str2, String str3, String str4) {
            if ("1".equals(str)) {
                ServerManager.get().send(true, str2, str3, str4, null);
            } else {
                ServerManager.get().send(str2, str3, str4, null);
            }
        }
    }
}
