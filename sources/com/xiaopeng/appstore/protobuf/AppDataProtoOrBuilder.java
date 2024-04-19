package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;
/* loaded from: classes2.dex */
public interface AppDataProtoOrBuilder extends MessageLiteOrBuilder {
    String getId();

    ByteString getIdBytes();

    AppDataContentProto getList(int index);

    int getListCount();

    List<AppDataContentProto> getListList();

    String getTitle();

    ByteString getTitleBytes();
}
