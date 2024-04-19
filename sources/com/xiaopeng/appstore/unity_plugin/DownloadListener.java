package com.xiaopeng.appstore.unity_plugin;
/* loaded from: classes2.dex */
public interface DownloadListener {
    void onProgressChanged(String packageName, float progress);

    void onStateChanged(String packageName, int state);
}
