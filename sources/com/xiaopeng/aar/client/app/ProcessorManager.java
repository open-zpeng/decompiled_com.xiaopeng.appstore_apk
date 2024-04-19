package com.xiaopeng.aar.client.app;

import com.xiaopeng.aar.Apps;
import com.xiaopeng.aar.utils.LogUtils;
import java.util.HashMap;
/* loaded from: classes2.dex */
public class ProcessorManager {
    private final HashMap<String, AppProcessor> mApps;

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final ProcessorManager INSTANCE = new ProcessorManager();

        private SingletonHolder() {
        }
    }

    public static ProcessorManager get() {
        return SingletonHolder.INSTANCE;
    }

    private ProcessorManager() {
        HashMap<String, AppProcessor> hashMap = new HashMap<>();
        this.mApps = hashMap;
        loadProcessor();
        LogUtils.i("Processor", "mApps : " + hashMap.size());
    }

    private void loadProcessor() {
        for (String str : Apps.getApps()) {
            if (!this.mApps.containsKey(str)) {
                this.mApps.put(str, new AppProcessorImpl(str));
            }
        }
    }

    public AppProcessor getAppProcessor(String str) {
        return this.mApps.get(str);
    }
}
