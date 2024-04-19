package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Objects;
/* loaded from: classes2.dex */
public final class DependentAppProto extends GeneratedMessageLite<DependentAppProto, Builder> implements DependentAppProtoOrBuilder {
    public static final int APP_ID_FIELD_NUMBER = 2;
    private static final DependentAppProto DEFAULT_INSTANCE;
    public static final int MD5_FIELD_NUMBER = 4;
    public static final int PACKAGE_NAME_FIELD_NUMBER = 1;
    private static volatile Parser<DependentAppProto> PARSER = null;
    public static final int TYPE_FIELD_NUMBER = 3;
    private String packageName_ = "";
    private String appId_ = "";
    private String type_ = "";
    private String md5_ = "";

    private DependentAppProto() {
    }

    @Override // com.xiaopeng.appstore.protobuf.DependentAppProtoOrBuilder
    public String getPackageName() {
        return this.packageName_;
    }

    @Override // com.xiaopeng.appstore.protobuf.DependentAppProtoOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.DependentAppProtoOrBuilder
    public String getAppId() {
        return this.appId_;
    }

    @Override // com.xiaopeng.appstore.protobuf.DependentAppProtoOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.DependentAppProtoOrBuilder
    public String getType() {
        return this.type_;
    }

    @Override // com.xiaopeng.appstore.protobuf.DependentAppProtoOrBuilder
    public ByteString getTypeBytes() {
        return ByteString.copyFromUtf8(this.type_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setType(String value) {
        Objects.requireNonNull(value);
        this.type_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearType() {
        this.type_ = getDefaultInstance().getType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTypeBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.type_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.DependentAppProtoOrBuilder
    public String getMd5() {
        return this.md5_;
    }

    @Override // com.xiaopeng.appstore.protobuf.DependentAppProtoOrBuilder
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

    public static DependentAppProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (DependentAppProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static DependentAppProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (DependentAppProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DependentAppProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (DependentAppProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static DependentAppProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (DependentAppProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DependentAppProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (DependentAppProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static DependentAppProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (DependentAppProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DependentAppProto parseFrom(InputStream input) throws IOException {
        return (DependentAppProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static DependentAppProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (DependentAppProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static DependentAppProto parseDelimitedFrom(InputStream input) throws IOException {
        return (DependentAppProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static DependentAppProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (DependentAppProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static DependentAppProto parseFrom(CodedInputStream input) throws IOException {
        return (DependentAppProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static DependentAppProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (DependentAppProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(DependentAppProto prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<DependentAppProto, Builder> implements DependentAppProtoOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(DependentAppProto.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.DependentAppProtoOrBuilder
        public String getPackageName() {
            return ((DependentAppProto) this.instance).getPackageName();
        }

        @Override // com.xiaopeng.appstore.protobuf.DependentAppProtoOrBuilder
        public ByteString getPackageNameBytes() {
            return ((DependentAppProto) this.instance).getPackageNameBytes();
        }

        public Builder setPackageName(String value) {
            copyOnWrite();
            ((DependentAppProto) this.instance).setPackageName(value);
            return this;
        }

        public Builder clearPackageName() {
            copyOnWrite();
            ((DependentAppProto) this.instance).clearPackageName();
            return this;
        }

        public Builder setPackageNameBytes(ByteString value) {
            copyOnWrite();
            ((DependentAppProto) this.instance).setPackageNameBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.DependentAppProtoOrBuilder
        public String getAppId() {
            return ((DependentAppProto) this.instance).getAppId();
        }

        @Override // com.xiaopeng.appstore.protobuf.DependentAppProtoOrBuilder
        public ByteString getAppIdBytes() {
            return ((DependentAppProto) this.instance).getAppIdBytes();
        }

        public Builder setAppId(String value) {
            copyOnWrite();
            ((DependentAppProto) this.instance).setAppId(value);
            return this;
        }

        public Builder clearAppId() {
            copyOnWrite();
            ((DependentAppProto) this.instance).clearAppId();
            return this;
        }

        public Builder setAppIdBytes(ByteString value) {
            copyOnWrite();
            ((DependentAppProto) this.instance).setAppIdBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.DependentAppProtoOrBuilder
        public String getType() {
            return ((DependentAppProto) this.instance).getType();
        }

        @Override // com.xiaopeng.appstore.protobuf.DependentAppProtoOrBuilder
        public ByteString getTypeBytes() {
            return ((DependentAppProto) this.instance).getTypeBytes();
        }

        public Builder setType(String value) {
            copyOnWrite();
            ((DependentAppProto) this.instance).setType(value);
            return this;
        }

        public Builder clearType() {
            copyOnWrite();
            ((DependentAppProto) this.instance).clearType();
            return this;
        }

        public Builder setTypeBytes(ByteString value) {
            copyOnWrite();
            ((DependentAppProto) this.instance).setTypeBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.DependentAppProtoOrBuilder
        public String getMd5() {
            return ((DependentAppProto) this.instance).getMd5();
        }

        @Override // com.xiaopeng.appstore.protobuf.DependentAppProtoOrBuilder
        public ByteString getMd5Bytes() {
            return ((DependentAppProto) this.instance).getMd5Bytes();
        }

        public Builder setMd5(String value) {
            copyOnWrite();
            ((DependentAppProto) this.instance).setMd5(value);
            return this;
        }

        public Builder clearMd5() {
            copyOnWrite();
            ((DependentAppProto) this.instance).clearMd5();
            return this;
        }

        public Builder setMd5Bytes(ByteString value) {
            copyOnWrite();
            ((DependentAppProto) this.instance).setMd5Bytes(value);
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.DependentAppProto$1  reason: invalid class name */
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
                return new DependentAppProto();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ\u0004Ȉ", new Object[]{"packageName_", "appId_", "type_", "md5_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<DependentAppProto> parser = PARSER;
                if (parser == null) {
                    synchronized (DependentAppProto.class) {
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
        DependentAppProto dependentAppProto = new DependentAppProto();
        DEFAULT_INSTANCE = dependentAppProto;
        GeneratedMessageLite.registerDefaultInstance(DependentAppProto.class, dependentAppProto);
    }

    public static DependentAppProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<DependentAppProto> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
