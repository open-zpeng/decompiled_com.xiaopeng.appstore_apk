package com.xiaopeng.appstore.appstore_biz.logic;

import android.view.View;
import com.xiaopeng.appstore.appstore_biz.model2.AppBaseModel;
/* loaded from: classes2.dex */
public interface OnItemEventCallback {
    void onBtnClick(int position, AppBaseModel model);

    void onItemClick(View view, AppBaseModel model);
}
