package com.xiaopeng.lib.apirouter.server;

import android.os.IBinder;
import android.util.Pair;
import com.xiaopeng.lib.apirouter.server.aar.ClientObserver_Manifest;
import com.xiaopeng.lib.apirouter.server.aar.ClientObserver_Stub;
import com.xiaopeng.lib.apirouter.server.aar.ServerObserver_Manifest;
import com.xiaopeng.lib.apirouter.server.aar.ServerObserver_Stub;
import java.util.HashMap;
/* loaded from: classes2.dex */
public class ManifestHelper_aar implements IManifestHelper {
    public HashMap<String, Pair<IBinder, String>> mapping = new HashMap<>();

    @Override // com.xiaopeng.lib.apirouter.server.IManifestHelper
    public HashMap<String, Pair<IBinder, String>> getMapping() {
        Pair<IBinder, String> pair = new Pair<>(new ClientObserver_Stub(), ClientObserver_Manifest.toJsonManifest());
        for (String str : ClientObserver_Manifest.getKey()) {
            this.mapping.put(str, pair);
        }
        Pair<IBinder, String> pair2 = new Pair<>(new ServerObserver_Stub(), ServerObserver_Manifest.toJsonManifest());
        for (String str2 : ServerObserver_Manifest.getKey()) {
            this.mapping.put(str2, pair2);
        }
        return this.mapping;
    }
}
