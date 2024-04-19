package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;
/* loaded from: classes2.dex */
public interface AppStoreHomeDataProtoOrBuilder extends MessageLiteOrBuilder {
    String getDesc();

    ByteString getDescBytes();

    String getId();

    ByteString getIdBytes();

    AppStoreHomeListProto getList(int index);

    int getListCount();

    List<AppStoreHomeListProto> getListList();

    String getTitle();

    ByteString getTitleBytes();
}
