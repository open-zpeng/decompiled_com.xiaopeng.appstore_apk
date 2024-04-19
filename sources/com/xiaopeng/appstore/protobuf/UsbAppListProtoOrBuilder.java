package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;
/* loaded from: classes2.dex */
public interface UsbAppListProtoOrBuilder extends MessageLiteOrBuilder {
    AppItemProto getUsbAppList(int index);

    int getUsbAppListCount();

    List<AppItemProto> getUsbAppListList();
}
