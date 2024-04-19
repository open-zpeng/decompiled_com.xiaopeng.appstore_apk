package com.xiaopeng.aar.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.xiaopeng.aar.client.MockTest;
import com.xiaopeng.aar.utils.LogUtils;
import com.xiaopeng.aar.utils.ThreadUtils;
import com.xiaopeng.lib.apirouter.server.ApiPublisherProvider;
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
    public void init() {
        if (this.mReceiver == null) {
            LogUtils.d(TAG, "init : ");
            this.mReceiver = new DemoReceiver();
            if (ApiPublisherProvider.CONTEXT != null) {
                IntentFilter intentFilter = new IntentFilter("NAPA_MOCK");
                intentFilter.addAction("NAPA_MOCK_SUB");
                ApiPublisherProvider.CONTEXT.registerReceiver(this.mReceiver, intentFilter);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void release() {
        if (this.mReceiver != null) {
            LogUtils.i(TAG, "release : ");
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
            String action = intent.getAction();
            if ("NAPA_MOCK".equals(action)) {
                final String stringExtra = intent.getStringExtra("v1");
                final String stringExtra2 = intent.getStringExtra("v2");
                final String stringExtra3 = intent.getStringExtra(BuildConfig.FLAVOR);
                final String stringExtra4 = intent.getStringExtra("v4");
                LogUtils.i(MockTest.TAG, "onReceive  appId : " + stringExtra + ", module : " + stringExtra2 + ",method : " + stringExtra3 + ",param : " + stringExtra4);
                ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.aar.client.-$$Lambda$MockTest$DemoReceiver$k1AdBUgYTpDS6e2g9wFMBof-wNw
                    @Override // java.lang.Runnable
                    public final void run() {
                        ApiManager.get().call(stringExtra, stringExtra2, stringExtra3, stringExtra4, null);
                    }
                });
            } else if ("NAPA_MOCK_SUB".equals(action)) {
                final String stringExtra5 = intent.getStringExtra("v1");
                final String stringExtra6 = intent.getStringExtra("v2");
                final String stringExtra7 = intent.getStringExtra(BuildConfig.FLAVOR);
                final String stringExtra8 = intent.getStringExtra("v4");
                ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.aar.client.-$$Lambda$MockTest$DemoReceiver$BLwMOHtIglV4GxT7dqZQOFSTVqQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        MockTest.DemoReceiver.lambda$onReceive$1(stringExtra5, stringExtra6, stringExtra7, stringExtra8);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ void lambda$onReceive$1(String str, String str2, String str3, String str4) {
            if ("1".equals(str)) {
                ApiManager.get().subscribe(str2, str3, str4);
            } else {
                ApiManager.get().unSubscribe(str2, str3, str4);
            }
        }
    }
}
