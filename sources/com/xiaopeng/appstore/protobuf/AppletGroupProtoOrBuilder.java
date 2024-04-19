package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;
/* loaded from: classes2.dex */
public interface AppletGroupProtoOrBuilder extends MessageLiteOrBuilder {
    AppItemProto getAppletList(int index);

    int getAppletListCount();

    List<AppItemProto> getAppletListList();

    String getGroup();

    ByteString getGroupBytes();
}
