package com.xiaopeng.appstore.appserver_common.v2;
/* loaded from: classes2.dex */
public interface AppListListener {
    void onAppChanged(int position, int count, Object payload);

    void onAppInserted(int position, int count);

    void onAppMoved(int fromPosition, int toPosition);

    void onAppRemoved(int position, int count);
}
