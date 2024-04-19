package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.xiaopeng.appstore.protobuf.AppStoreHomeDataProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Objects;
/* loaded from: classes2.dex */
public final class AppStoreHomeDataWrapperProto extends GeneratedMessageLite<AppStoreHomeDataWrapperProto, Builder> implements AppStoreHomeDataWrapperProtoOrBuilder {
    public static final int CONTENT_TYPE_FIELD_NUMBER = 1;
    public static final int DATA_FIELD_NUMBER = 3;
    private static final AppStoreHomeDataWrapperProto DEFAULT_INSTANCE;
    public static final int LAYOUT_FIELD_NUMBER = 2;
    private static volatile Parser<AppStoreHomeDataWrapperProto> PARSER;
    private int contentType_;
    private AppStoreHomeDataProto data_;
    private String layout_ = "";

    private AppStoreHomeDataWrapperProto() {
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataWrapperProtoOrBuilder
    public int getContentType() {
        return this.contentType_;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setContentType(int value) {
        this.contentType_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearContentType() {
        this.contentType_ = 0;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataWrapperProtoOrBuilder
    public String getLayout() {
        return this.layout_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataWrapperProtoOrBuilder
    public ByteString getLayoutBytes() {
        return ByteString.copyFromUtf8(this.layout_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLayout(String value) {
        Objects.requireNonNull(value);
        this.layout_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearLayout() {
        this.layout_ = getDefaultInstance().getLayout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLayoutBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.layout_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataWrapperProtoOrBuilder
    public boolean hasData() {
        return this.data_ != null;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataWrapperProtoOrBuilder
    public AppStoreHomeDataProto getData() {
        AppStoreHomeDataProto appStoreHomeDataProto = this.data_;
        return appStoreHomeDataProto == null ? AppStoreHomeDataProto.getDefaultInstance() : appStoreHomeDataProto;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setData(AppStoreHomeDataProto value) {
        Objects.requireNonNull(value);
        this.data_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setData(AppStoreHomeDataProto.Builder builderForValue) {
        this.data_ = builderForValue.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mergeData(AppStoreHomeDataProto value) {
        Objects.requireNonNull(value);
        AppStoreHomeDataProto appStoreHomeDataProto = this.data_;
        if (appStoreHomeDataProto != null && appStoreHomeDataProto != AppStoreHomeDataProto.getDefaultInstance()) {
            this.data_ = AppStoreHomeDataProto.newBuilder(this.data_).mergeFrom((AppStoreHomeDataProto.Builder) value).buildPartial();
        } else {
            this.data_ = value;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearData() {
        this.data_ = null;
    }

    public static AppStoreHomeDataWrapperProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (AppStoreHomeDataWrapperProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppStoreHomeDataWrapperProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppStoreHomeDataWrapperProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppStoreHomeDataWrapperProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (AppStoreHomeDataWrapperProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppStoreHomeDataWrapperProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppStoreHomeDataWrapperProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppStoreHomeDataWrapperProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (AppStoreHomeDataWrapperProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppStoreHomeDataWrapperProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppStoreHomeDataWrapperProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppStoreHomeDataWrapperProto parseFrom(InputStream input) throws IOException {
        return (AppStoreHomeDataWrapperProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppStoreHomeDataWrapperProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppStoreHomeDataWrapperProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppStoreHomeDataWrapperProto parseDelimitedFrom(InputStream input) throws IOException {
        return (AppStoreHomeDataWrapperProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static AppStoreHomeDataWrapperProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppStoreHomeDataWrapperProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppStoreHomeDataWrapperProto parseFrom(CodedInputStream input) throws IOException {
        return (AppStoreHomeDataWrapperProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppStoreHomeDataWrapperProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppStoreHomeDataWrapperProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(AppStoreHomeDataWrapperProto prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<AppStoreHomeDataWrapperProto, Builder> implements AppStoreHomeDataWrapperProtoOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(AppStoreHomeDataWrapperProto.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataWrapperProtoOrBuilder
        public int getContentType() {
            return ((AppStoreHomeDataWrapperProto) this.instance).getContentType();
        }

        public Builder setContentType(int value) {
            copyOnWrite();
            ((AppStoreHomeDataWrapperProto) this.instance).setContentType(value);
            return this;
        }

        public Builder clearContentType() {
            copyOnWrite();
            ((AppStoreHomeDataWrapperProto) this.instance).clearContentType();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataWrapperProtoOrBuilder
        public String getLayout() {
            return ((AppStoreHomeDataWrapperProto) this.instance).getLayout();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataWrapperProtoOrBuilder
        public ByteString getLayoutBytes() {
            return ((AppStoreHomeDataWrapperProto) this.instance).getLayoutBytes();
        }

        public Builder setLayout(String value) {
            copyOnWrite();
            ((AppStoreHomeDataWrapperProto) this.instance).setLayout(value);
            return this;
        }

        public Builder clearLayout() {
            copyOnWrite();
            ((AppStoreHomeDataWrapperProto) this.instance).clearLayout();
            return this;
        }

        public Builder setLayoutBytes(ByteString value) {
            copyOnWrite();
            ((AppStoreHomeDataWrapperProto) this.instance).setLayoutBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataWrapperProtoOrBuilder
        public boolean hasData() {
            return ((AppStoreHomeDataWrapperProto) this.instance).hasData();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataWrapperProtoOrBuilder
        public AppStoreHomeDataProto getData() {
            return ((AppStoreHomeDataWrapperProto) this.instance).getData();
        }

        public Builder setData(AppStoreHomeDataProto value) {
            copyOnWrite();
            ((AppStoreHomeDataWrapperProto) this.instance).setData(value);
            return this;
        }

        public Builder setData(AppStoreHomeDataProto.Builder builderForValue) {
            copyOnWrite();
            ((AppStoreHomeDataWrapperProto) this.instance).setData(builderForValue);
            return this;
        }

        public Builder mergeData(AppStoreHomeDataProto value) {
            copyOnWrite();
            ((AppStoreHomeDataWrapperProto) this.instance).mergeData(value);
            return this;
        }

        public Builder clearData() {
            copyOnWrite();
            ((AppStoreHomeDataWrapperProto) this.instance).clearData();
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.AppStoreHomeDataWrapperProto$1  reason: invalid class name */
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
                return new AppStoreHomeDataWrapperProto();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003\t", new Object[]{"contentType_", "layout_", "data_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<AppStoreHomeDataWrapperProto> parser = PARSER;
                if (parser == null) {
                    synchronized (AppStoreHomeDataWrapperProto.class) {
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
        AppStoreHomeDataWrapperProto appStoreHomeDataWrapperProto = new AppStoreHomeDataWrapperProto();
        DEFAULT_INSTANCE = appStoreHomeDataWrapperProto;
        GeneratedMessageLite.registerDefaultInstance(AppStoreHomeDataWrapperProto.class, appStoreHomeDataWrapperProto);
    }

    public static AppStoreHomeDataWrapperProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AppStoreHomeDataWrapperProto> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
