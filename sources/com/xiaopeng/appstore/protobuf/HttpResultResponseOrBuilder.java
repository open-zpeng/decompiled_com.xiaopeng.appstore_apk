package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
/* loaded from: classes2.dex */
public interface HttpResultResponseOrBuilder extends MessageLiteOrBuilder {
    int getCode();

    String getMsg();

    ByteString getMsgBytes();
}
