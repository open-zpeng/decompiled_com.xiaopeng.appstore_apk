package com.xiaopeng.appstore.bizcommon.logic;

import com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer;
/* loaded from: classes2.dex */
public interface ItemAssembleObserver {
    void onProgressChange(AssembleDataContainer data);

    void onStateChange(AssembleDataContainer data);
}
