package com.xiaopeng.appstore.bizcommon.logic;

import com.xiaopeng.appstore.bizcommon.entities.WebAppInfo;
import java.util.List;
/* loaded from: classes2.dex */
public interface PreloadLoadListener {
    void onFinish(List<WebAppInfo> appList, boolean isFullUpdate);
}
