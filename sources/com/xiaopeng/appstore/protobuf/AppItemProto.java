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
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
/* loaded from: classes2.dex */
public final class AppItemProto extends GeneratedMessageLite<AppItemProto, Builder> implements AppItemProtoOrBuilder {
    public static final int ADIMG_FIELD_NUMBER = 13;
    public static final int APPICONS_FIELD_NUMBER = 14;
    public static final int CONFIGMD5_FIELD_NUMBER = 18;
    public static final int CONFIGURL_FIELD_NUMBER = 17;
    private static final AppItemProto DEFAULT_INSTANCE;
    public static final int DESC_FIELD_NUMBER = 8;
    public static final int DOWNLOADURL_FIELD_NUMBER = 16;
    public static final int ICON_FIELD_NUMBER = 4;
    public static final int ID_FIELD_NUMBER = 7;
    public static final int MD5_FIELD_NUMBER = 10;
    public static final int NAME_FIELD_NUMBER = 5;
    public static final int PACKAGENAME_FIELD_NUMBER = 6;
    private static volatile Parser<AppItemProto> PARSER = null;
    public static final int PROGRESS_FIELD_NUMBER = 3;
    public static final int REMOTESTATUS_FIELD_NUMBER = 11;
    public static final int SIZE_FIELD_NUMBER = 9;
    public static final int STATUS_FIELD_NUMBER = 2;
    public static final int TITLE_FIELD_NUMBER = 12;
    public static final int TYPE_FIELD_NUMBER = 1;
    public static final int VERSIONCODE_FIELD_NUMBER = 15;
    public static final int VERSION_NAME_FIELD_NUMBER = 19;
    private AppIconDataProto appIcons_;
    private float progress_;
    private int remoteStatus_;
    private int status_;
    private int type_;
    private long versionCode_;
    private ByteString icon_ = ByteString.EMPTY;
    private String name_ = "";
    private String packageName_ = "";
    private String id_ = "";
    private String desc_ = "";
    private String size_ = "";
    private String md5_ = "";
    private String title_ = "";
    private Internal.ProtobufList<String> adImg_ = GeneratedMessageLite.emptyProtobufList();
    private String downloadUrl_ = "";
    private String configUrl_ = "";
    private String configMd5_ = "";
    private String versionName_ = "";

    private AppItemProto() {
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public int getType() {
        return this.type_;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setType(int value) {
        this.type_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearType() {
        this.type_ = 0;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public float getProgress() {
        return this.progress_;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setProgress(float value) {
        this.progress_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearProgress() {
        this.progress_ = 0.0f;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public ByteString getIcon() {
        return this.icon_;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setIcon(ByteString value) {
        Objects.requireNonNull(value);
        this.icon_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearIcon() {
        this.icon_ = getDefaultInstance().getIcon();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public String getName() {
        return this.name_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public ByteString getNameBytes() {
        return ByteString.copyFromUtf8(this.name_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setName(String value) {
        Objects.requireNonNull(value);
        this.name_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearName() {
        this.name_ = getDefaultInstance().getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNameBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.name_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public String getPackageName() {
        return this.packageName_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public String getId() {
        return this.id_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public ByteString getIdBytes() {
        return ByteString.copyFromUtf8(this.id_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setId(String value) {
        Objects.requireNonNull(value);
        this.id_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearId() {
        this.id_ = getDefaultInstance().getId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setIdBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.id_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public String getDesc() {
        return this.desc_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public ByteString getDescBytes() {
        return ByteString.copyFromUtf8(this.desc_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDesc(String value) {
        Objects.requireNonNull(value);
        this.desc_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearDesc() {
        this.desc_ = getDefaultInstance().getDesc();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDescBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.desc_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public String getSize() {
        return this.size_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public String getMd5() {
        return this.md5_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public int getRemoteStatus() {
        return this.remoteStatus_;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRemoteStatus(int value) {
        this.remoteStatus_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearRemoteStatus() {
        this.remoteStatus_ = 0;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public String getTitle() {
        return this.title_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public ByteString getTitleBytes() {
        return ByteString.copyFromUtf8(this.title_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTitle(String value) {
        Objects.requireNonNull(value);
        this.title_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearTitle() {
        this.title_ = getDefaultInstance().getTitle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTitleBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.title_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public List<String> getAdImgList() {
        return this.adImg_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public int getAdImgCount() {
        return this.adImg_.size();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public String getAdImg(int index) {
        return this.adImg_.get(index);
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public ByteString getAdImgBytes(int index) {
        return ByteString.copyFromUtf8(this.adImg_.get(index));
    }

    private void ensureAdImgIsMutable() {
        if (this.adImg_.isModifiable()) {
            return;
        }
        this.adImg_ = GeneratedMessageLite.mutableCopy(this.adImg_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAdImg(int index, String value) {
        Objects.requireNonNull(value);
        ensureAdImgIsMutable();
        this.adImg_.set(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAdImg(String value) {
        Objects.requireNonNull(value);
        ensureAdImgIsMutable();
        this.adImg_.add(value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllAdImg(Iterable<String> values) {
        ensureAdImgIsMutable();
        AbstractMessageLite.addAll((Iterable) values, (List) this.adImg_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAdImg() {
        this.adImg_ = GeneratedMessageLite.emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAdImgBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        ensureAdImgIsMutable();
        this.adImg_.add(value.toStringUtf8());
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public boolean hasAppIcons() {
        return this.appIcons_ != null;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public String getDownloadUrl() {
        return this.downloadUrl_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public String getConfigUrl() {
        return this.configUrl_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public String getConfigMd5() {
        return this.configMd5_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
    public String getVersionName() {
        return this.versionName_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
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

    public static AppItemProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (AppItemProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppItemProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppItemProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppItemProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (AppItemProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppItemProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppItemProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppItemProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (AppItemProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppItemProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppItemProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppItemProto parseFrom(InputStream input) throws IOException {
        return (AppItemProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppItemProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppItemProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppItemProto parseDelimitedFrom(InputStream input) throws IOException {
        return (AppItemProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static AppItemProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppItemProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppItemProto parseFrom(CodedInputStream input) throws IOException {
        return (AppItemProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppItemProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppItemProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(AppItemProto prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<AppItemProto, Builder> implements AppItemProtoOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(AppItemProto.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public int getType() {
            return ((AppItemProto) this.instance).getType();
        }

        public Builder setType(int value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setType(value);
            return this;
        }

        public Builder clearType() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearType();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public int getStatus() {
            return ((AppItemProto) this.instance).getStatus();
        }

        public Builder setStatus(int value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setStatus(value);
            return this;
        }

        public Builder clearStatus() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearStatus();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public float getProgress() {
            return ((AppItemProto) this.instance).getProgress();
        }

        public Builder setProgress(float value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setProgress(value);
            return this;
        }

        public Builder clearProgress() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearProgress();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public ByteString getIcon() {
            return ((AppItemProto) this.instance).getIcon();
        }

        public Builder setIcon(ByteString value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setIcon(value);
            return this;
        }

        public Builder clearIcon() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearIcon();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public String getName() {
            return ((AppItemProto) this.instance).getName();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public ByteString getNameBytes() {
            return ((AppItemProto) this.instance).getNameBytes();
        }

        public Builder setName(String value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setName(value);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearName();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setNameBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public String getPackageName() {
            return ((AppItemProto) this.instance).getPackageName();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public ByteString getPackageNameBytes() {
            return ((AppItemProto) this.instance).getPackageNameBytes();
        }

        public Builder setPackageName(String value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setPackageName(value);
            return this;
        }

        public Builder clearPackageName() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearPackageName();
            return this;
        }

        public Builder setPackageNameBytes(ByteString value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setPackageNameBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public String getId() {
            return ((AppItemProto) this.instance).getId();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public ByteString getIdBytes() {
            return ((AppItemProto) this.instance).getIdBytes();
        }

        public Builder setId(String value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setId(value);
            return this;
        }

        public Builder clearId() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearId();
            return this;
        }

        public Builder setIdBytes(ByteString value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setIdBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public String getDesc() {
            return ((AppItemProto) this.instance).getDesc();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public ByteString getDescBytes() {
            return ((AppItemProto) this.instance).getDescBytes();
        }

        public Builder setDesc(String value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setDesc(value);
            return this;
        }

        public Builder clearDesc() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearDesc();
            return this;
        }

        public Builder setDescBytes(ByteString value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setDescBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public String getSize() {
            return ((AppItemProto) this.instance).getSize();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public ByteString getSizeBytes() {
            return ((AppItemProto) this.instance).getSizeBytes();
        }

        public Builder setSize(String value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setSize(value);
            return this;
        }

        public Builder clearSize() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearSize();
            return this;
        }

        public Builder setSizeBytes(ByteString value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setSizeBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public String getMd5() {
            return ((AppItemProto) this.instance).getMd5();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public ByteString getMd5Bytes() {
            return ((AppItemProto) this.instance).getMd5Bytes();
        }

        public Builder setMd5(String value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setMd5(value);
            return this;
        }

        public Builder clearMd5() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearMd5();
            return this;
        }

        public Builder setMd5Bytes(ByteString value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setMd5Bytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public int getRemoteStatus() {
            return ((AppItemProto) this.instance).getRemoteStatus();
        }

        public Builder setRemoteStatus(int value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setRemoteStatus(value);
            return this;
        }

        public Builder clearRemoteStatus() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearRemoteStatus();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public String getTitle() {
            return ((AppItemProto) this.instance).getTitle();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public ByteString getTitleBytes() {
            return ((AppItemProto) this.instance).getTitleBytes();
        }

        public Builder setTitle(String value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setTitle(value);
            return this;
        }

        public Builder clearTitle() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearTitle();
            return this;
        }

        public Builder setTitleBytes(ByteString value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setTitleBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public List<String> getAdImgList() {
            return Collections.unmodifiableList(((AppItemProto) this.instance).getAdImgList());
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public int getAdImgCount() {
            return ((AppItemProto) this.instance).getAdImgCount();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public String getAdImg(int index) {
            return ((AppItemProto) this.instance).getAdImg(index);
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public ByteString getAdImgBytes(int index) {
            return ((AppItemProto) this.instance).getAdImgBytes(index);
        }

        public Builder setAdImg(int index, String value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setAdImg(index, value);
            return this;
        }

        public Builder addAdImg(String value) {
            copyOnWrite();
            ((AppItemProto) this.instance).addAdImg(value);
            return this;
        }

        public Builder addAllAdImg(Iterable<String> values) {
            copyOnWrite();
            ((AppItemProto) this.instance).addAllAdImg(values);
            return this;
        }

        public Builder clearAdImg() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearAdImg();
            return this;
        }

        public Builder addAdImgBytes(ByteString value) {
            copyOnWrite();
            ((AppItemProto) this.instance).addAdImgBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public boolean hasAppIcons() {
            return ((AppItemProto) this.instance).hasAppIcons();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public AppIconDataProto getAppIcons() {
            return ((AppItemProto) this.instance).getAppIcons();
        }

        public Builder setAppIcons(AppIconDataProto value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setAppIcons(value);
            return this;
        }

        public Builder setAppIcons(AppIconDataProto.Builder builderForValue) {
            copyOnWrite();
            ((AppItemProto) this.instance).setAppIcons(builderForValue);
            return this;
        }

        public Builder mergeAppIcons(AppIconDataProto value) {
            copyOnWrite();
            ((AppItemProto) this.instance).mergeAppIcons(value);
            return this;
        }

        public Builder clearAppIcons() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearAppIcons();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public long getVersionCode() {
            return ((AppItemProto) this.instance).getVersionCode();
        }

        public Builder setVersionCode(long value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setVersionCode(value);
            return this;
        }

        public Builder clearVersionCode() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearVersionCode();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public String getDownloadUrl() {
            return ((AppItemProto) this.instance).getDownloadUrl();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public ByteString getDownloadUrlBytes() {
            return ((AppItemProto) this.instance).getDownloadUrlBytes();
        }

        public Builder setDownloadUrl(String value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setDownloadUrl(value);
            return this;
        }

        public Builder clearDownloadUrl() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearDownloadUrl();
            return this;
        }

        public Builder setDownloadUrlBytes(ByteString value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setDownloadUrlBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public String getConfigUrl() {
            return ((AppItemProto) this.instance).getConfigUrl();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public ByteString getConfigUrlBytes() {
            return ((AppItemProto) this.instance).getConfigUrlBytes();
        }

        public Builder setConfigUrl(String value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setConfigUrl(value);
            return this;
        }

        public Builder clearConfigUrl() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearConfigUrl();
            return this;
        }

        public Builder setConfigUrlBytes(ByteString value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setConfigUrlBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public String getConfigMd5() {
            return ((AppItemProto) this.instance).getConfigMd5();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public ByteString getConfigMd5Bytes() {
            return ((AppItemProto) this.instance).getConfigMd5Bytes();
        }

        public Builder setConfigMd5(String value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setConfigMd5(value);
            return this;
        }

        public Builder clearConfigMd5() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearConfigMd5();
            return this;
        }

        public Builder setConfigMd5Bytes(ByteString value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setConfigMd5Bytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public String getVersionName() {
            return ((AppItemProto) this.instance).getVersionName();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppItemProtoOrBuilder
        public ByteString getVersionNameBytes() {
            return ((AppItemProto) this.instance).getVersionNameBytes();
        }

        public Builder setVersionName(String value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setVersionName(value);
            return this;
        }

        public Builder clearVersionName() {
            copyOnWrite();
            ((AppItemProto) this.instance).clearVersionName();
            return this;
        }

        public Builder setVersionNameBytes(ByteString value) {
            copyOnWrite();
            ((AppItemProto) this.instance).setVersionNameBytes(value);
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.AppItemProto$1  reason: invalid class name */
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
                return new AppItemProto();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0013\u0000\u0000\u0001\u0013\u0013\u0000\u0001\u0000\u0001\u0004\u0002\u0004\u0003\u0001\u0004\n\u0005Ȉ\u0006Ȉ\u0007Ȉ\bȈ\tȈ\nȈ\u000b\u0004\fȈ\rȚ\u000e\t\u000f\u0002\u0010Ȉ\u0011Ȉ\u0012Ȉ\u0013Ȉ", new Object[]{"type_", "status_", "progress_", "icon_", "name_", "packageName_", "id_", "desc_", "size_", "md5_", "remoteStatus_", "title_", "adImg_", "appIcons_", "versionCode_", "downloadUrl_", "configUrl_", "configMd5_", "versionName_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<AppItemProto> parser = PARSER;
                if (parser == null) {
                    synchronized (AppItemProto.class) {
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
        AppItemProto appItemProto = new AppItemProto();
        DEFAULT_INSTANCE = appItemProto;
        GeneratedMessageLite.registerDefaultInstance(AppItemProto.class, appItemProto);
    }

    public static AppItemProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AppItemProto> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
