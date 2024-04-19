package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
/* loaded from: classes2.dex */
public interface DependentAppProtoOrBuilder extends MessageLiteOrBuilder {
    String getAppId();

    ByteString getAppIdBytes();

    String getMd5();

    ByteString getMd5Bytes();

    String getPackageName();

    ByteString getPackageNameBytes();

    String getType();

    ByteString getTypeBytes();
}
