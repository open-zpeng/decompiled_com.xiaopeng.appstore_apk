package com.xiaopeng.appstore.ui;

import com.xiaopeng.appstore.ui.HomeTabAdapter;
/* loaded from: classes2.dex */
public class InterHomeTabAdapterFactory implements HomeTabAdapter.Factory {
    @Override // com.xiaopeng.appstore.ui.HomeTabAdapter.Factory
    public HomeTabAdapter build() {
        return new InterHomeTabAdapter();
    }
}
