package com.xiaopeng.appstore.appserver_common;

import com.xiaopeng.appstore.protobuf.AppItemProto;
/* loaded from: classes2.dex */
public interface UsbAppListener {
    void onUsbAppChanged(int index, AppItemProto appItem);
}
