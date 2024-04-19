package com.xiaopeng.appstore.bizcommon.logic.base;

import com.xiaopeng.appstore.bizcommon.logic.base.BaseComponentManager;
/* loaded from: classes2.dex */
public abstract class BaseComponent<M extends BaseComponentManager> {
    private M mManager;

    public M getComponentManager() {
        return this.mManager;
    }

    public void setComponentManager(M manager) {
        this.mManager = manager;
    }
}
