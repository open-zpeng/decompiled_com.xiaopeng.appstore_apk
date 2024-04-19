package com.xiaopeng.aar.client.ipc;
/* loaded from: classes2.dex */
public interface Ipc {

    /* loaded from: classes2.dex */
    public interface OnServerListener {
        void onEvent(int i, String str, String str2, String str3, String str4, byte[] bArr);
    }

    /* loaded from: classes2.dex */
    public interface OnServerStatusListener {
        public static final int STATUS_RESTART = 2;
        public static final int STATUS_START = 1;

        void onServerStatus(String str, int i);
    }

    void addOnServerStatusListener(OnServerStatusListener onServerStatusListener);

    String call(String str, String str2, String str3, String str4, byte[] bArr);

    void onReceived(int i, String str, String str2, String str3, String str4, byte[] bArr);

    void removeOnServerStatusListener(OnServerStatusListener onServerStatusListener);

    void setOnServerListener(OnServerListener onServerListener);

    void subscribe(String str, String str2);

    void subscribes(String str, String str2);

    void unSubscribe(String str, String str2);

    void unSubscribes(String str, String str2);
}
