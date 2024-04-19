package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
/* loaded from: classes2.dex */
public interface AppIndexOrBuilder extends MessageLiteOrBuilder {
    int getIndex();

    String getPackageName();

    ByteString getPackageNameBytes();
}
