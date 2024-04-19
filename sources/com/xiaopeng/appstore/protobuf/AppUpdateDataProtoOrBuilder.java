package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;
/* loaded from: classes2.dex */
public interface AppUpdateDataProtoOrBuilder extends MessageLiteOrBuilder {
    AppDetailDataProto getApps(int index);

    int getAppsCount();

    List<AppDetailDataProto> getAppsList();
}
