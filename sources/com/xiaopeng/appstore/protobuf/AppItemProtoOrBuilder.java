package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;
/* loaded from: classes2.dex */
public interface AppItemProtoOrBuilder extends MessageLiteOrBuilder {
    String getAdImg(int index);

    ByteString getAdImgBytes(int index);

    int getAdImgCount();

    List<String> getAdImgList();

    AppIconDataProto getAppIcons();

    String getConfigMd5();

    ByteString getConfigMd5Bytes();

    String getConfigUrl();

    ByteString getConfigUrlBytes();

    String getDesc();

    ByteString getDescBytes();

    String getDownloadUrl();

    ByteString getDownloadUrlBytes();

    ByteString getIcon();

    String getId();

    ByteString getIdBytes();

    String getMd5();

    ByteString getMd5Bytes();

    String getName();

    ByteString getNameBytes();

    String getPackageName();

    ByteString getPackageNameBytes();

    float getProgress();

    int getRemoteStatus();

    String getSize();

    ByteString getSizeBytes();

    int getStatus();

    String getTitle();

    ByteString getTitleBytes();

    int getType();

    long getVersionCode();

    String getVersionName();

    ByteString getVersionNameBytes();

    boolean hasAppIcons();
}
