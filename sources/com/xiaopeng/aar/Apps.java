package com.xiaopeng.aar;

import android.content.Context;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/* loaded from: classes2.dex */
public class Apps {
    public static final String ServerTest = "ServerTest";
    private static final HashMap<String, String> msMap;

    static {
        HashMap<String, String> hashMap = new HashMap<>();
        msMap = hashMap;
        hashMap.put("Demo", "com.xiaopeng.server.demo");
        hashMap.put("carcontrol", "com.xiaopeng.carcontrol");
        hashMap.put("settings", "com.xiaopeng.car.settings");
        hashMap.put("btphone", IpcConfig.App.CAR_BT_PHONE);
        hashMap.put("oobe", "com.xiaopeng.oobe");
        hashMap.put("systemui", "com.xiaopeng.systemui");
        hashMap.put("aiot", "com.xiaopeng.aiot");
        hashMap.put("musicradio", "com.xiaopeng.musicradio");
        hashMap.put("aiassistant", "com.xiaopeng.aiassistant");
        hashMap.put("powercenter", "com.xiaopeng.chargecontrol");
        hashMap.put("caraccount", "com.xiaopeng.caraccount");
        hashMap.put("homespace", "com.xiaopeng.homespace");
        hashMap.put("xsport", "com.xiaopeng.xsport");
        hashMap.put("appstore", "com.xiaopeng.appstore");
        hashMap.put("speech", "com.xiaopeng.carspeechservice");
    }

    public static Set<String> getApps() {
        return msMap.keySet();
    }

    public static String getPackageNames(String str) {
        return msMap.get(str);
    }

    public static String getAppId(Context context) {
        return getAppId(context.getPackageName());
    }

    private static String getAppId(String str) {
        for (Map.Entry<String, String> entry : msMap.entrySet()) {
            if (entry.getValue().equals(str)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
