package com.xiaopeng.appstore.bizcommon.router;

import java.util.HashMap;
import java.util.Map;
/* loaded from: classes2.dex */
public class RouterManager {
    private final Map<String, ? super IModuleService> mServiceMap;

    private RouterManager() {
        this.mServiceMap = new HashMap();
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        static final RouterManager sInstance = new RouterManager();

        private SingletonHolder() {
        }
    }

    public static RouterManager get() {
        return SingletonHolder.sInstance;
    }

    public <T extends IModuleService> void registerModule(String path, T service) {
        this.mServiceMap.put(path, service);
    }

    public void unregisterModule(String path) {
        this.mServiceMap.remove(path);
    }

    public Object getModule(String path) {
        if (!this.mServiceMap.containsKey(path)) {
            throw new IllegalArgumentException("Path not registered:" + path);
        }
        return this.mServiceMap.get(path);
    }

    public <T extends IModuleService> T getModule(String path, Class<T> clazz) {
        IModuleService iModuleService = this.mServiceMap.get(path);
        if (clazz.isInstance(iModuleService)) {
            return clazz.cast(iModuleService);
        }
        return null;
    }
}
