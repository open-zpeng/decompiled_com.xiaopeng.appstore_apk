package com.xiaopeng.aar.server;
/* loaded from: classes2.dex */
public class ServerConfig {
    private final boolean autoUnSubscribeWhenNaPaDied;
    private final boolean interceptSendWhenNotSubscribed;
    private final boolean keepAlive;
    private final int logLevel;
    private final boolean useMock;
    private final boolean waitInit;
    private final int waitTimeout;

    private ServerConfig(Builder builder) {
        this.waitInit = builder.waitInit;
        this.waitTimeout = builder.waitTimeout;
        this.keepAlive = builder.keepAlive;
        this.useMock = builder.useMock;
        this.autoUnSubscribeWhenNaPaDied = builder.autoUnSubscribeWhenNaPaDied;
        this.interceptSendWhenNotSubscribed = builder.interceptSendWhenNotSubscribed;
        this.logLevel = builder.logLevel;
    }

    public boolean isWaitInit() {
        return this.waitInit;
    }

    public int getWaitTimeout() {
        return this.waitTimeout;
    }

    public boolean isKeepAlive() {
        return this.keepAlive;
    }

    public boolean isUseMock() {
        return this.useMock;
    }

    public boolean isAutoUnSubscribeWhenNaPaDied() {
        return this.autoUnSubscribeWhenNaPaDied;
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public boolean isInterceptSendWhenNotSubscribed() {
        return this.interceptSendWhenNotSubscribed;
    }

    public String toString() {
        return "ServerConfig{waitInit=" + this.waitInit + ", waitTimeout=" + this.waitTimeout + ", keepAlive=" + this.keepAlive + ", useMock=" + this.useMock + ", autoUnSubscribeWhenNaPaDied=" + this.autoUnSubscribeWhenNaPaDied + ", interceptSendWhenNotSubscribed=" + this.interceptSendWhenNotSubscribed + ", logLevel=" + this.logLevel + '}';
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        public static final int LOG_D = 3;
        public static final int LOG_E = 6;
        public static final int LOG_I = 4;
        public static final int LOG_W = 5;
        private boolean autoUnSubscribeWhenNaPaDied;
        private boolean interceptSendWhenNotSubscribed;
        private boolean keepAlive;
        private int logLevel = 4;
        private boolean useMock;
        private boolean waitInit;
        private int waitTimeout;

        public Builder setWaitInit(boolean z) {
            this.waitInit = z;
            return this;
        }

        public Builder setWaitTimeout(int i) {
            this.waitTimeout = i;
            return this;
        }

        public Builder useKeepAlive(boolean z) {
            this.keepAlive = z;
            return this;
        }

        public Builder useMock(boolean z) {
            this.useMock = z;
            return this;
        }

        public Builder setAutoUnSubscribeWhenNaPaDied(boolean z) {
            this.autoUnSubscribeWhenNaPaDied = z;
            return this;
        }

        public Builder setLogLevel(int i) {
            this.logLevel = i;
            return this;
        }

        public Builder setInterceptSendWhenNotSubscribed(boolean z) {
            this.interceptSendWhenNotSubscribed = z;
            return this;
        }

        public ServerConfig build() {
            return new ServerConfig(this);
        }
    }
}
