package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
/* loaded from: classes2.dex */
public interface AppStoreHomeResponseProtoOrBuilder extends MessageLiteOrBuilder {
    int getCode();

    AppStoreHomeDataWrapperProto getData();

    String getMsg();

    ByteString getMsgBytes();

    boolean hasData();
}
