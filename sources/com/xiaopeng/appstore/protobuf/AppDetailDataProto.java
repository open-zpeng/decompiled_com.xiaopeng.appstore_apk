package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.xiaopeng.appstore.protobuf.AppIconDataProto;
import com.xiaopeng.appstore.protobuf.DeveloperDataProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
/* loaded from: classes2.dex */
public final class AppDetailDataProto extends GeneratedMessageLite<AppDetailDataProto, Builder> implements AppDetailDataProtoOrBuilder {
    public static final int APK_PERMISSIONS_FIELD_NUMBER = 23;
    public static final int APP_ICONS_FIELD_NUMBER = 21;
    public static final int APP_ID_FIELD_NUMBER = 1;
    public static final int APP_NAME_FIELD_NUMBER = 2;
    public static final int BRIEF_DESC_FIELD_NUMBER = 4;
    public static final int CONFIG_MD5_FIELD_NUMBER = 13;
    public static final int CONFIG_URL_FIELD_NUMBER = 12;
    private static final AppDetailDataProto DEFAULT_INSTANCE;
    public static final int DETAIL_DESC_FIELD_NUMBER = 5;
    public static final int DETAIL_IMG_FIELD_NUMBER = 10;
    public static final int DEVELOPER_FIELD_NUMBER = 22;
    public static final int DOWNLOAD_URL_FIELD_NUMBER = 11;
    public static final int IS_FORCE_UPDATE_FIELD_NUMBER = 20;
    public static final int IS_HIDDEN_FIELD_NUMBER = 16;
    public static final int IS_SILENT_INSTALL_FIELD_NUMBER = 17;
    public static final int MD5_FIELD_NUMBER = 14;
    public static final int PACKAGE_NAME_FIELD_NUMBER = 3;
    private static volatile Parser<AppDetailDataProto> PARSER = null;
    public static final int PRIVACY_POLICY_FIELD_NUMBER = 24;
    public static final int SIZE_FIELD_NUMBER = 19;
    public static final int SOURCE_FIELD_NUMBER = 15;
    public static final int STATUS_FIELD_NUMBER = 18;
    public static final int UPDATE_TIME_FIELD_NUMBER = 9;
    public static final int VERSION_CODE_FIELD_NUMBER = 6;
    public static final int VERSION_DESC_FIELD_NUMBER = 8;
    public static final int VERSION_NAME_FIELD_NUMBER = 7;
    private AppIconDataProto appIcons_;
    private DeveloperDataProto developer_;
    private int isForceUpdate_;
    private int isHidden_;
    private int isSilentInstall_;
    private int status_;
    private long updateTime_;
    private long versionCode_;
    private String appId_ = "";
    private String appName_ = "";
    private String packageName_ = "";
    private String briefDesc_ = "";
    private String detailDesc_ = "";
    private String versionName_ = "";
    private String versionDesc_ = "";
    private Internal.ProtobufList<String> detailImg_ = GeneratedMessageLite.emptyProtobufList();
    private String downloadUrl_ = "";
    private String configUrl_ = "";
    private String configMd5_ = "";
    private String md5_ = "";
    private String source_ = "";
    private String size_ = "";
    private String apkPermissions_ = "";
    private String privacyPolicy_ = "";

    private AppDetailDataProto() {
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public String getAppId() {
        return this.appId_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public ByteString getAppIdBytes() {
        return ByteString.copyFromUtf8(this.appId_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAppId(String value) {
        Objects.requireNonNull(value);
        this.appId_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAppId() {
        this.appId_ = getDefaultInstance().getAppId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAppIdBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.appId_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public String getAppName() {
        return this.appName_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public ByteString getAppNameBytes() {
        return ByteString.copyFromUtf8(this.appName_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAppName(String value) {
        Objects.requireNonNull(value);
        this.appName_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAppName() {
        this.appName_ = getDefaultInstance().getAppName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAppNameBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.appName_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public String getPackageName() {
        return this.packageName_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public ByteString getPackageNameBytes() {
        return ByteString.copyFromUtf8(this.packageName_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPackageName(String value) {
        Objects.requireNonNull(value);
        this.packageName_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearPackageName() {
        this.packageName_ = getDefaultInstance().getPackageName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPackageNameBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.packageName_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public String getBriefDesc() {
        return this.briefDesc_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public ByteString getBriefDescBytes() {
        return ByteString.copyFromUtf8(this.briefDesc_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBriefDesc(String value) {
        Objects.requireNonNull(value);
        this.briefDesc_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearBriefDesc() {
        this.briefDesc_ = getDefaultInstance().getBriefDesc();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBriefDescBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.briefDesc_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public String getDetailDesc() {
        return this.detailDesc_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public ByteString getDetailDescBytes() {
        return ByteString.copyFromUtf8(this.detailDesc_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDetailDesc(String value) {
        Objects.requireNonNull(value);
        this.detailDesc_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearDetailDesc() {
        this.detailDesc_ = getDefaultInstance().getDetailDesc();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDetailDescBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.detailDesc_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public long getVersionCode() {
        return this.versionCode_;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVersionCode(long value) {
        this.versionCode_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearVersionCode() {
        this.versionCode_ = 0L;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public String getVersionName() {
        return this.versionName_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public ByteString getVersionNameBytes() {
        return ByteString.copyFromUtf8(this.versionName_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVersionName(String value) {
        Objects.requireNonNull(value);
        this.versionName_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearVersionName() {
        this.versionName_ = getDefaultInstance().getVersionName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVersionNameBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.versionName_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public String getVersionDesc() {
        return this.versionDesc_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public ByteString getVersionDescBytes() {
        return ByteString.copyFromUtf8(this.versionDesc_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVersionDesc(String value) {
        Objects.requireNonNull(value);
        this.versionDesc_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearVersionDesc() {
        this.versionDesc_ = getDefaultInstance().getVersionDesc();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVersionDescBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.versionDesc_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public long getUpdateTime() {
        return this.updateTime_;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUpdateTime(long value) {
        this.updateTime_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearUpdateTime() {
        this.updateTime_ = 0L;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public List<String> getDetailImgList() {
        return this.detailImg_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public int getDetailImgCount() {
        return this.detailImg_.size();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public String getDetailImg(int index) {
        return this.detailImg_.get(index);
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public ByteString getDetailImgBytes(int index) {
        return ByteString.copyFromUtf8(this.detailImg_.get(index));
    }

    private void ensureDetailImgIsMutable() {
        if (this.detailImg_.isModifiable()) {
            return;
        }
        this.detailImg_ = GeneratedMessageLite.mutableCopy(this.detailImg_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDetailImg(int index, String value) {
        Objects.requireNonNull(value);
        ensureDetailImgIsMutable();
        this.detailImg_.set(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addDetailImg(String value) {
        Objects.requireNonNull(value);
        ensureDetailImgIsMutable();
        this.detailImg_.add(value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllDetailImg(Iterable<String> values) {
        ensureDetailImgIsMutable();
        AbstractMessageLite.addAll((Iterable) values, (List) this.detailImg_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearDetailImg() {
        this.detailImg_ = GeneratedMessageLite.emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addDetailImgBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        ensureDetailImgIsMutable();
        this.detailImg_.add(value.toStringUtf8());
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public String getDownloadUrl() {
        return this.downloadUrl_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public ByteString getDownloadUrlBytes() {
        return ByteString.copyFromUtf8(this.downloadUrl_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDownloadUrl(String value) {
        Objects.requireNonNull(value);
        this.downloadUrl_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearDownloadUrl() {
        this.downloadUrl_ = getDefaultInstance().getDownloadUrl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDownloadUrlBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.downloadUrl_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public String getConfigUrl() {
        return this.configUrl_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public ByteString getConfigUrlBytes() {
        return ByteString.copyFromUtf8(this.configUrl_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setConfigUrl(String value) {
        Objects.requireNonNull(value);
        this.configUrl_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearConfigUrl() {
        this.configUrl_ = getDefaultInstance().getConfigUrl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setConfigUrlBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.configUrl_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public String getConfigMd5() {
        return this.configMd5_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public ByteString getConfigMd5Bytes() {
        return ByteString.copyFromUtf8(this.configMd5_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setConfigMd5(String value) {
        Objects.requireNonNull(value);
        this.configMd5_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearConfigMd5() {
        this.configMd5_ = getDefaultInstance().getConfigMd5();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setConfigMd5Bytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.configMd5_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public String getMd5() {
        return this.md5_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public ByteString getMd5Bytes() {
        return ByteString.copyFromUtf8(this.md5_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMd5(String value) {
        Objects.requireNonNull(value);
        this.md5_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearMd5() {
        this.md5_ = getDefaultInstance().getMd5();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMd5Bytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.md5_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public String getSource() {
        return this.source_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public ByteString getSourceBytes() {
        return ByteString.copyFromUtf8(this.source_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSource(String value) {
        Objects.requireNonNull(value);
        this.source_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearSource() {
        this.source_ = getDefaultInstance().getSource();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSourceBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.source_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public int getIsHidden() {
        return this.isHidden_;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setIsHidden(int value) {
        this.isHidden_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearIsHidden() {
        this.isHidden_ = 0;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public int getIsSilentInstall() {
        return this.isSilentInstall_;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setIsSilentInstall(int value) {
        this.isSilentInstall_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearIsSilentInstall() {
        this.isSilentInstall_ = 0;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public int getStatus() {
        return this.status_;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setStatus(int value) {
        this.status_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearStatus() {
        this.status_ = 0;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public String getSize() {
        return this.size_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public ByteString getSizeBytes() {
        return ByteString.copyFromUtf8(this.size_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSize(String value) {
        Objects.requireNonNull(value);
        this.size_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearSize() {
        this.size_ = getDefaultInstance().getSize();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSizeBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.size_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public int getIsForceUpdate() {
        return this.isForceUpdate_;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setIsForceUpdate(int value) {
        this.isForceUpdate_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearIsForceUpdate() {
        this.isForceUpdate_ = 0;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public boolean hasAppIcons() {
        return this.appIcons_ != null;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public AppIconDataProto getAppIcons() {
        AppIconDataProto appIconDataProto = this.appIcons_;
        return appIconDataProto == null ? AppIconDataProto.getDefaultInstance() : appIconDataProto;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAppIcons(AppIconDataProto value) {
        Objects.requireNonNull(value);
        this.appIcons_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAppIcons(AppIconDataProto.Builder builderForValue) {
        this.appIcons_ = builderForValue.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mergeAppIcons(AppIconDataProto value) {
        Objects.requireNonNull(value);
        AppIconDataProto appIconDataProto = this.appIcons_;
        if (appIconDataProto != null && appIconDataProto != AppIconDataProto.getDefaultInstance()) {
            this.appIcons_ = AppIconDataProto.newBuilder(this.appIcons_).mergeFrom((AppIconDataProto.Builder) value).buildPartial();
        } else {
            this.appIcons_ = value;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAppIcons() {
        this.appIcons_ = null;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public boolean hasDeveloper() {
        return this.developer_ != null;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public DeveloperDataProto getDeveloper() {
        DeveloperDataProto developerDataProto = this.developer_;
        return developerDataProto == null ? DeveloperDataProto.getDefaultInstance() : developerDataProto;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDeveloper(DeveloperDataProto value) {
        Objects.requireNonNull(value);
        this.developer_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDeveloper(DeveloperDataProto.Builder builderForValue) {
        this.developer_ = builderForValue.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mergeDeveloper(DeveloperDataProto value) {
        Objects.requireNonNull(value);
        DeveloperDataProto developerDataProto = this.developer_;
        if (developerDataProto != null && developerDataProto != DeveloperDataProto.getDefaultInstance()) {
            this.developer_ = DeveloperDataProto.newBuilder(this.developer_).mergeFrom((DeveloperDataProto.Builder) value).buildPartial();
        } else {
            this.developer_ = value;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearDeveloper() {
        this.developer_ = null;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public String getApkPermissions() {
        return this.apkPermissions_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public ByteString getApkPermissionsBytes() {
        return ByteString.copyFromUtf8(this.apkPermissions_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setApkPermissions(String value) {
        Objects.requireNonNull(value);
        this.apkPermissions_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearApkPermissions() {
        this.apkPermissions_ = getDefaultInstance().getApkPermissions();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setApkPermissionsBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.apkPermissions_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public String getPrivacyPolicy() {
        return this.privacyPolicy_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
    public ByteString getPrivacyPolicyBytes() {
        return ByteString.copyFromUtf8(this.privacyPolicy_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPrivacyPolicy(String value) {
        Objects.requireNonNull(value);
        this.privacyPolicy_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearPrivacyPolicy() {
        this.privacyPolicy_ = getDefaultInstance().getPrivacyPolicy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPrivacyPolicyBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.privacyPolicy_ = value.toStringUtf8();
    }

    public static AppDetailDataProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (AppDetailDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppDetailDataProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppDetailDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppDetailDataProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (AppDetailDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppDetailDataProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppDetailDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppDetailDataProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (AppDetailDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppDetailDataProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppDetailDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppDetailDataProto parseFrom(InputStream input) throws IOException {
        return (AppDetailDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppDetailDataProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppDetailDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppDetailDataProto parseDelimitedFrom(InputStream input) throws IOException {
        return (AppDetailDataProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static AppDetailDataProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppDetailDataProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppDetailDataProto parseFrom(CodedInputStream input) throws IOException {
        return (AppDetailDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppDetailDataProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppDetailDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(AppDetailDataProto prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<AppDetailDataProto, Builder> implements AppDetailDataProtoOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(AppDetailDataProto.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public String getAppId() {
            return ((AppDetailDataProto) this.instance).getAppId();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public ByteString getAppIdBytes() {
            return ((AppDetailDataProto) this.instance).getAppIdBytes();
        }

        public Builder setAppId(String value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setAppId(value);
            return this;
        }

        public Builder clearAppId() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearAppId();
            return this;
        }

        public Builder setAppIdBytes(ByteString value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setAppIdBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public String getAppName() {
            return ((AppDetailDataProto) this.instance).getAppName();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public ByteString getAppNameBytes() {
            return ((AppDetailDataProto) this.instance).getAppNameBytes();
        }

        public Builder setAppName(String value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setAppName(value);
            return this;
        }

        public Builder clearAppName() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearAppName();
            return this;
        }

        public Builder setAppNameBytes(ByteString value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setAppNameBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public String getPackageName() {
            return ((AppDetailDataProto) this.instance).getPackageName();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public ByteString getPackageNameBytes() {
            return ((AppDetailDataProto) this.instance).getPackageNameBytes();
        }

        public Builder setPackageName(String value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setPackageName(value);
            return this;
        }

        public Builder clearPackageName() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearPackageName();
            return this;
        }

        public Builder setPackageNameBytes(ByteString value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setPackageNameBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public String getBriefDesc() {
            return ((AppDetailDataProto) this.instance).getBriefDesc();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public ByteString getBriefDescBytes() {
            return ((AppDetailDataProto) this.instance).getBriefDescBytes();
        }

        public Builder setBriefDesc(String value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setBriefDesc(value);
            return this;
        }

        public Builder clearBriefDesc() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearBriefDesc();
            return this;
        }

        public Builder setBriefDescBytes(ByteString value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setBriefDescBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public String getDetailDesc() {
            return ((AppDetailDataProto) this.instance).getDetailDesc();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public ByteString getDetailDescBytes() {
            return ((AppDetailDataProto) this.instance).getDetailDescBytes();
        }

        public Builder setDetailDesc(String value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setDetailDesc(value);
            return this;
        }

        public Builder clearDetailDesc() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearDetailDesc();
            return this;
        }

        public Builder setDetailDescBytes(ByteString value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setDetailDescBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public long getVersionCode() {
            return ((AppDetailDataProto) this.instance).getVersionCode();
        }

        public Builder setVersionCode(long value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setVersionCode(value);
            return this;
        }

        public Builder clearVersionCode() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearVersionCode();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public String getVersionName() {
            return ((AppDetailDataProto) this.instance).getVersionName();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public ByteString getVersionNameBytes() {
            return ((AppDetailDataProto) this.instance).getVersionNameBytes();
        }

        public Builder setVersionName(String value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setVersionName(value);
            return this;
        }

        public Builder clearVersionName() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearVersionName();
            return this;
        }

        public Builder setVersionNameBytes(ByteString value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setVersionNameBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public String getVersionDesc() {
            return ((AppDetailDataProto) this.instance).getVersionDesc();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public ByteString getVersionDescBytes() {
            return ((AppDetailDataProto) this.instance).getVersionDescBytes();
        }

        public Builder setVersionDesc(String value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setVersionDesc(value);
            return this;
        }

        public Builder clearVersionDesc() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearVersionDesc();
            return this;
        }

        public Builder setVersionDescBytes(ByteString value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setVersionDescBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public long getUpdateTime() {
            return ((AppDetailDataProto) this.instance).getUpdateTime();
        }

        public Builder setUpdateTime(long value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setUpdateTime(value);
            return this;
        }

        public Builder clearUpdateTime() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearUpdateTime();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public List<String> getDetailImgList() {
            return Collections.unmodifiableList(((AppDetailDataProto) this.instance).getDetailImgList());
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public int getDetailImgCount() {
            return ((AppDetailDataProto) this.instance).getDetailImgCount();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public String getDetailImg(int index) {
            return ((AppDetailDataProto) this.instance).getDetailImg(index);
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public ByteString getDetailImgBytes(int index) {
            return ((AppDetailDataProto) this.instance).getDetailImgBytes(index);
        }

        public Builder setDetailImg(int index, String value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setDetailImg(index, value);
            return this;
        }

        public Builder addDetailImg(String value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).addDetailImg(value);
            return this;
        }

        public Builder addAllDetailImg(Iterable<String> values) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).addAllDetailImg(values);
            return this;
        }

        public Builder clearDetailImg() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearDetailImg();
            return this;
        }

        public Builder addDetailImgBytes(ByteString value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).addDetailImgBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public String getDownloadUrl() {
            return ((AppDetailDataProto) this.instance).getDownloadUrl();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public ByteString getDownloadUrlBytes() {
            return ((AppDetailDataProto) this.instance).getDownloadUrlBytes();
        }

        public Builder setDownloadUrl(String value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setDownloadUrl(value);
            return this;
        }

        public Builder clearDownloadUrl() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearDownloadUrl();
            return this;
        }

        public Builder setDownloadUrlBytes(ByteString value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setDownloadUrlBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public String getConfigUrl() {
            return ((AppDetailDataProto) this.instance).getConfigUrl();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public ByteString getConfigUrlBytes() {
            return ((AppDetailDataProto) this.instance).getConfigUrlBytes();
        }

        public Builder setConfigUrl(String value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setConfigUrl(value);
            return this;
        }

        public Builder clearConfigUrl() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearConfigUrl();
            return this;
        }

        public Builder setConfigUrlBytes(ByteString value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setConfigUrlBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public String getConfigMd5() {
            return ((AppDetailDataProto) this.instance).getConfigMd5();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public ByteString getConfigMd5Bytes() {
            return ((AppDetailDataProto) this.instance).getConfigMd5Bytes();
        }

        public Builder setConfigMd5(String value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setConfigMd5(value);
            return this;
        }

        public Builder clearConfigMd5() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearConfigMd5();
            return this;
        }

        public Builder setConfigMd5Bytes(ByteString value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setConfigMd5Bytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public String getMd5() {
            return ((AppDetailDataProto) this.instance).getMd5();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public ByteString getMd5Bytes() {
            return ((AppDetailDataProto) this.instance).getMd5Bytes();
        }

        public Builder setMd5(String value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setMd5(value);
            return this;
        }

        public Builder clearMd5() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearMd5();
            return this;
        }

        public Builder setMd5Bytes(ByteString value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setMd5Bytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public String getSource() {
            return ((AppDetailDataProto) this.instance).getSource();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public ByteString getSourceBytes() {
            return ((AppDetailDataProto) this.instance).getSourceBytes();
        }

        public Builder setSource(String value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setSource(value);
            return this;
        }

        public Builder clearSource() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearSource();
            return this;
        }

        public Builder setSourceBytes(ByteString value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setSourceBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public int getIsHidden() {
            return ((AppDetailDataProto) this.instance).getIsHidden();
        }

        public Builder setIsHidden(int value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setIsHidden(value);
            return this;
        }

        public Builder clearIsHidden() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearIsHidden();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public int getIsSilentInstall() {
            return ((AppDetailDataProto) this.instance).getIsSilentInstall();
        }

        public Builder setIsSilentInstall(int value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setIsSilentInstall(value);
            return this;
        }

        public Builder clearIsSilentInstall() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearIsSilentInstall();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public int getStatus() {
            return ((AppDetailDataProto) this.instance).getStatus();
        }

        public Builder setStatus(int value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setStatus(value);
            return this;
        }

        public Builder clearStatus() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearStatus();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public String getSize() {
            return ((AppDetailDataProto) this.instance).getSize();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public ByteString getSizeBytes() {
            return ((AppDetailDataProto) this.instance).getSizeBytes();
        }

        public Builder setSize(String value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setSize(value);
            return this;
        }

        public Builder clearSize() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearSize();
            return this;
        }

        public Builder setSizeBytes(ByteString value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setSizeBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public int getIsForceUpdate() {
            return ((AppDetailDataProto) this.instance).getIsForceUpdate();
        }

        public Builder setIsForceUpdate(int value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setIsForceUpdate(value);
            return this;
        }

        public Builder clearIsForceUpdate() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearIsForceUpdate();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public boolean hasAppIcons() {
            return ((AppDetailDataProto) this.instance).hasAppIcons();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public AppIconDataProto getAppIcons() {
            return ((AppDetailDataProto) this.instance).getAppIcons();
        }

        public Builder setAppIcons(AppIconDataProto value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setAppIcons(value);
            return this;
        }

        public Builder setAppIcons(AppIconDataProto.Builder builderForValue) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setAppIcons(builderForValue);
            return this;
        }

        public Builder mergeAppIcons(AppIconDataProto value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).mergeAppIcons(value);
            return this;
        }

        public Builder clearAppIcons() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearAppIcons();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public boolean hasDeveloper() {
            return ((AppDetailDataProto) this.instance).hasDeveloper();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public DeveloperDataProto getDeveloper() {
            return ((AppDetailDataProto) this.instance).getDeveloper();
        }

        public Builder setDeveloper(DeveloperDataProto value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setDeveloper(value);
            return this;
        }

        public Builder setDeveloper(DeveloperDataProto.Builder builderForValue) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setDeveloper(builderForValue);
            return this;
        }

        public Builder mergeDeveloper(DeveloperDataProto value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).mergeDeveloper(value);
            return this;
        }

        public Builder clearDeveloper() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearDeveloper();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public String getApkPermissions() {
            return ((AppDetailDataProto) this.instance).getApkPermissions();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public ByteString getApkPermissionsBytes() {
            return ((AppDetailDataProto) this.instance).getApkPermissionsBytes();
        }

        public Builder setApkPermissions(String value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setApkPermissions(value);
            return this;
        }

        public Builder clearApkPermissions() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearApkPermissions();
            return this;
        }

        public Builder setApkPermissionsBytes(ByteString value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setApkPermissionsBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public String getPrivacyPolicy() {
            return ((AppDetailDataProto) this.instance).getPrivacyPolicy();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDetailDataProtoOrBuilder
        public ByteString getPrivacyPolicyBytes() {
            return ((AppDetailDataProto) this.instance).getPrivacyPolicyBytes();
        }

        public Builder setPrivacyPolicy(String value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setPrivacyPolicy(value);
            return this;
        }

        public Builder clearPrivacyPolicy() {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).clearPrivacyPolicy();
            return this;
        }

        public Builder setPrivacyPolicyBytes(ByteString value) {
            copyOnWrite();
            ((AppDetailDataProto) this.instance).setPrivacyPolicyBytes(value);
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.AppDetailDataProto$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[method.ordinal()]) {
            case 1:
                return new AppDetailDataProto();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0018\u0000\u0000\u0001\u0018\u0018\u0000\u0001\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ\u0004Ȉ\u0005Ȉ\u0006\u0002\u0007Ȉ\bȈ\t\u0002\nȚ\u000bȈ\fȈ\rȈ\u000eȈ\u000fȈ\u0010\u0004\u0011\u0004\u0012\u0004\u0013Ȉ\u0014\u0004\u0015\t\u0016\t\u0017Ȉ\u0018Ȉ", new Object[]{"appId_", "appName_", "packageName_", "briefDesc_", "detailDesc_", "versionCode_", "versionName_", "versionDesc_", "updateTime_", "detailImg_", "downloadUrl_", "configUrl_", "configMd5_", "md5_", "source_", "isHidden_", "isSilentInstall_", "status_", "size_", "isForceUpdate_", "appIcons_", "developer_", "apkPermissions_", "privacyPolicy_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<AppDetailDataProto> parser = PARSER;
                if (parser == null) {
                    synchronized (AppDetailDataProto.class) {
                        parser = PARSER;
                        if (parser == null) {
                            parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                            PARSER = parser;
                        }
                    }
                }
                return parser;
            case 6:
                return (byte) 1;
            case 7:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    static {
        AppDetailDataProto appDetailDataProto = new AppDetailDataProto();
        DEFAULT_INSTANCE = appDetailDataProto;
        GeneratedMessageLite.registerDefaultInstance(AppDetailDataProto.class, appDetailDataProto);
    }

    public static AppDetailDataProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AppDetailDataProto> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
