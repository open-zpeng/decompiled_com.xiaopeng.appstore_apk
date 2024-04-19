package com.xiaopeng.aar.server.ipc;
/* loaded from: classes2.dex */
public class IpcServerManager {
    private IpcServer mIpcServer;

    public void init() {
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final IpcServerManager INSTANCE = new IpcServerManager();

        private SingletonHolder() {
        }
    }

    public static IpcServerManager get() {
        return SingletonHolder.INSTANCE;
    }

    private IpcServerManager() {
        this.mIpcServer = IpcServerImpl.get();
    }

    public IpcServer getIpc() {
        return this.mIpcServer;
    }
}
