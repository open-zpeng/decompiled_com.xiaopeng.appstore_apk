package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
/* loaded from: classes2.dex */
public interface AppUpdateResponseProtoOrBuilder extends MessageLiteOrBuilder {
    int getCode();

    AppUpdateDataProto getData();

    String getMsg();

    ByteString getMsgBytes();

    boolean hasData();
}
