package com.xiaopeng.aar.client;

import android.os.Binder;
import android.util.ArraySet;
import com.xiaopeng.aar.client.ipc.Ipc;
import com.xiaopeng.aar.client.ipc.IpcManager;
import com.xiaopeng.aar.utils.HandlerThreadHelper;
import com.xiaopeng.aar.utils.LogUtils;
import java.util.HashMap;
import java.util.Map;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class SubscribeManager {
    private static final String TAG = "Subscribe";
    private ArraySet<String> mDiedApps;
    private final HandlerThreadHelper mHandlerThread;
    private HashMap<String, HashMap<String, ArraySet<String>>> mSubscribes;

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final SubscribeManager INSTANCE = new SubscribeManager();

        private SingletonHolder() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static SubscribeManager get() {
        return SingletonHolder.INSTANCE;
    }

    private SubscribeManager() {
        this.mSubscribes = new HashMap<>();
        this.mDiedApps = new ArraySet<>();
        IpcManager.get().getIpc().addOnServerStatusListener(new Ipc.OnServerStatusListener() { // from class: com.xiaopeng.aar.client.-$$Lambda$SubscribeManager$hTnJVJoJrj7br1Uj9J7nUIrxpdQ
            @Override // com.xiaopeng.aar.client.ipc.Ipc.OnServerStatusListener
            public final void onServerStatus(String str, int i) {
                SubscribeManager.this.lambda$new$0$SubscribeManager(str, i);
            }
        });
        this.mHandlerThread = new HandlerThreadHelper("SubscribeManager");
    }

    public /* synthetic */ void lambda$new$0$SubscribeManager(String str, int i) {
        if (i == 2) {
            serverStop(str);
            serverStart(str);
        }
    }

    private synchronized void serverStop(String str) {
        HashMap<String, ArraySet<String>> hashMap = this.mSubscribes.get(str);
        if (hashMap != null && !hashMap.isEmpty()) {
            this.mDiedApps.add(str);
        }
    }

    private synchronized void serverStart(String str) {
        if (this.mDiedApps.remove(str)) {
            LogUtils.i(TAG, String.format("serverStart-- appId:%s, uid:%s, pid:%s", str, Integer.valueOf(Binder.getCallingUid()), Integer.valueOf(Binder.getCallingPid())));
            reSubscribe(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized boolean check(String str, String str2) {
        HashMap<String, ArraySet<String>> hashMap = this.mSubscribes.get(str);
        boolean z = false;
        if (hashMap != null && !hashMap.isEmpty()) {
            ArraySet<String> arraySet = hashMap.get(str2);
            if (arraySet != null) {
                if (!arraySet.isEmpty()) {
                    z = true;
                }
            }
            return z;
        }
        return false;
    }

    private synchronized void reSubscribe(final String str) {
        HashMap<String, ArraySet<String>> hashMap = this.mSubscribes.get(str);
        if (hashMap != null && !hashMap.isEmpty()) {
            final StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, ArraySet<String>> entry : hashMap.entrySet()) {
                String key = entry.getKey();
                ArraySet<String> value = entry.getValue();
                if (value != null && value.size() > 0) {
                    sb.append(key);
                    sb.append(";");
                }
            }
            if (sb.length() > 0) {
                this.mHandlerThread.post(new Runnable() { // from class: com.xiaopeng.aar.client.-$$Lambda$SubscribeManager$Bh4j9unptxEHnGW2TsKxcYoGtTg
                    @Override // java.lang.Runnable
                    public final void run() {
                        SubscribeManager.this.lambda$reSubscribe$1$SubscribeManager(str, sb);
                    }
                });
            }
        }
    }

    public /* synthetic */ void lambda$reSubscribe$1$SubscribeManager(String str, StringBuilder sb) {
        subscribeMulIpc(str, sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void subscribe(final String str, final String str2, String str3) {
        HashMap<String, ArraySet<String>> hashMap = this.mSubscribes.get(str);
        if (hashMap == null) {
            hashMap = new HashMap<>();
            this.mSubscribes.put(str, hashMap);
        }
        ArraySet<String> arraySet = hashMap.get(str2);
        if (arraySet == null) {
            arraySet = new ArraySet<>();
            hashMap.put(str2, arraySet);
        }
        if (arraySet.isEmpty()) {
            this.mHandlerThread.post(new Runnable() { // from class: com.xiaopeng.aar.client.-$$Lambda$SubscribeManager$2ghQpMMBWf3o1ctTUl5yeH5JUm0
                @Override // java.lang.Runnable
                public final void run() {
                    SubscribeManager.this.lambda$subscribe$2$SubscribeManager(str, str2);
                }
            });
        }
        arraySet.add(str3);
        LogUtils.d(TAG, String.format("subscribe-- appId:%s,module:%s，subscriber:%s", str, str2, str3));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void unSubscribe(final String str, final String str2, String str3) {
        HashMap<String, ArraySet<String>> hashMap = this.mSubscribes.get(str);
        if (hashMap != null && !hashMap.isEmpty()) {
            ArraySet<String> arraySet = hashMap.get(str2);
            if (arraySet != null && !arraySet.isEmpty()) {
                arraySet.remove(str3);
                if (arraySet.isEmpty()) {
                    this.mHandlerThread.post(new Runnable() { // from class: com.xiaopeng.aar.client.-$$Lambda$SubscribeManager$fUl9PDoc4RpS27-iUsyahguh9uw
                        @Override // java.lang.Runnable
                        public final void run() {
                            SubscribeManager.this.lambda$unSubscribe$3$SubscribeManager(str, str2);
                        }
                    });
                }
                LogUtils.d(TAG, String.format("unSubscribe-- appId:%s,module:%s，subscriber:%s", str, str2, str3));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: subscribeIpc */
    public void lambda$subscribe$2$SubscribeManager(String str, String str2) {
        LogUtils.i(TAG, String.format("subscribeIpc-- appId:%s ,module:%s", str, str2));
        IpcManager.get().getIpc().subscribe(str, str2);
    }

    private void subscribeMulIpc(String str, String str2) {
        LogUtils.i(TAG, String.format("subscribeMulIpc-- appId:%s ,module:%s", str, str2));
        IpcManager.get().getIpc().subscribes(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: unSubscribeIpc */
    public void lambda$unSubscribe$3$SubscribeManager(String str, String str2) {
        LogUtils.i(TAG, String.format("unSubscribeIpc-- appId:%s ,module:%s", str, str2));
        IpcManager.get().getIpc().unSubscribe(str, str2);
    }
}
