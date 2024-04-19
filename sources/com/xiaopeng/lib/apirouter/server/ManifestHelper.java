package com.xiaopeng.lib.apirouter.server;

import android.os.IBinder;
import android.util.Pair;
import java.util.HashMap;
/* loaded from: classes2.dex */
public class ManifestHelper {
    public static HashMap<String, Pair<IBinder, String>> mapping = new HashMap<>();

    static {
        Pair<IBinder, String> pair = new Pair<>(new VuiObserver_Stub(), VuiObserver_Manifest.toJsonManifest());
        for (String str : VuiObserver_Manifest.getKey()) {
            mapping.put(str, pair);
        }
    }
}
