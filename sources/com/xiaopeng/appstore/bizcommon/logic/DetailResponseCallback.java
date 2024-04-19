package com.xiaopeng.appstore.bizcommon.logic;

import com.xiaopeng.appstore.bizcommon.entities.WebAppInfo;
/* loaded from: classes2.dex */
public interface DetailResponseCallback {
    void onError(String code, String errorMessage);

    void onSuccess(WebAppInfo appInfo);
}
