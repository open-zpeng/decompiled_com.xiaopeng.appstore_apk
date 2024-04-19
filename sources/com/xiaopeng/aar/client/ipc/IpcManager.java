package com.xiaopeng.aar.client.ipc;
/* loaded from: classes2.dex */
public class IpcManager {
    private final Ipc mIpc;

    public void init() {
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final IpcManager INSTANCE = new IpcManager();

        private SingletonHolder() {
        }
    }

    public static IpcManager get() {
        return SingletonHolder.INSTANCE;
    }

    private IpcManager() {
        this.mIpc = IpcImpl.get();
    }

    public Ipc getIpc() {
        return this.mIpc;
    }
}
