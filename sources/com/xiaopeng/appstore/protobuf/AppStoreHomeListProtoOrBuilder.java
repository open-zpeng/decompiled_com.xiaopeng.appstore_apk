package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
/* loaded from: classes2.dex */
public interface AppStoreHomeListProtoOrBuilder extends MessageLiteOrBuilder {
    int getContentType();

    AppDataProto getData();

    String getLayout();

    ByteString getLayoutBytes();

    boolean hasData();
}
