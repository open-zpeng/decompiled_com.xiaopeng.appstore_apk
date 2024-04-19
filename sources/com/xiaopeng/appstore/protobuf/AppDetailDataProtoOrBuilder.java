package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;
/* loaded from: classes2.dex */
public interface AppDetailDataProtoOrBuilder extends MessageLiteOrBuilder {
    String getApkPermissions();

    ByteString getApkPermissionsBytes();

    AppIconDataProto getAppIcons();

    String getAppId();

    ByteString getAppIdBytes();

    String getAppName();

    ByteString getAppNameBytes();

    String getBriefDesc();

    ByteString getBriefDescBytes();

    String getConfigMd5();

    ByteString getConfigMd5Bytes();

    String getConfigUrl();

    ByteString getConfigUrlBytes();

    String getDetailDesc();

    ByteString getDetailDescBytes();

    String getDetailImg(int index);

    ByteString getDetailImgBytes(int index);

    int getDetailImgCount();

    List<String> getDetailImgList();

    DeveloperDataProto getDeveloper();

    String getDownloadUrl();

    ByteString getDownloadUrlBytes();

    int getIsForceUpdate();

    int getIsHidden();

    int getIsSilentInstall();

    String getMd5();

    ByteString getMd5Bytes();

    String getPackageName();

    ByteString getPackageNameBytes();

    String getPrivacyPolicy();

    ByteString getPrivacyPolicyBytes();

    String getSize();

    ByteString getSizeBytes();

    String getSource();

    ByteString getSourceBytes();

    int getStatus();

    long getUpdateTime();

    long getVersionCode();

    String getVersionDesc();

    ByteString getVersionDescBytes();

    String getVersionName();

    ByteString getVersionNameBytes();

    boolean hasAppIcons();

    boolean hasDeveloper();
}
