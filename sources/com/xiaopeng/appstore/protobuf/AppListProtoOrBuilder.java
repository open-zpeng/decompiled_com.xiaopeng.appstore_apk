package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;
/* loaded from: classes2.dex */
public interface AppListProtoOrBuilder extends MessageLiteOrBuilder {
    AppItemProto getAppItem(int index);

    int getAppItemCount();

    List<AppItemProto> getAppItemList();
}
