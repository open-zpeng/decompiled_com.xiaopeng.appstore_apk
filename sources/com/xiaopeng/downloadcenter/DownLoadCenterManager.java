package com.xiaopeng.downloadcenter;
/* loaded from: classes2.dex */
public class DownLoadCenterManager {

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final DownLoadCenterService INSTANCE = new DownLoadCenterImpl();

        private SingletonHolder() {
        }
    }

    public static DownLoadCenterService get() {
        return SingletonHolder.INSTANCE;
    }
}
