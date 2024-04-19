package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.xiaopeng.appstore.protobuf.AppDataProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Objects;
/* loaded from: classes2.dex */
public final class AppStoreHomeListProto extends GeneratedMessageLite<AppStoreHomeListProto, Builder> implements AppStoreHomeListProtoOrBuilder {
    public static final int CONTENT_TYPE_FIELD_NUMBER = 1;
    public static final int DATA_FIELD_NUMBER = 3;
    private static final AppStoreHomeListProto DEFAULT_INSTANCE;
    public static final int LAYOUT_FIELD_NUMBER = 2;
    private static volatile Parser<AppStoreHomeListProto> PARSER;
    private int contentType_;
    private AppDataProto data_;
    private String layout_ = "";

    private AppStoreHomeListProto() {
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeListProtoOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeListProtoOrBuilder
    public String getLayout() {
        return this.layout_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeListProtoOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeListProtoOrBuilder
    public boolean hasData() {
        return this.data_ != null;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeListProtoOrBuilder
    public AppDataProto getData() {
        AppDataProto appDataProto = this.data_;
        return appDataProto == null ? AppDataProto.getDefaultInstance() : appDataProto;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setData(AppDataProto value) {
        Objects.requireNonNull(value);
        this.data_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setData(AppDataProto.Builder builderForValue) {
        this.data_ = builderForValue.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mergeData(AppDataProto value) {
        Objects.requireNonNull(value);
        AppDataProto appDataProto = this.data_;
        if (appDataProto != null && appDataProto != AppDataProto.getDefaultInstance()) {
            this.data_ = AppDataProto.newBuilder(this.data_).mergeFrom((AppDataProto.Builder) value).buildPartial();
        } else {
            this.data_ = value;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearData() {
        this.data_ = null;
    }

    public static AppStoreHomeListProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (AppStoreHomeListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppStoreHomeListProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppStoreHomeListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppStoreHomeListProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (AppStoreHomeListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppStoreHomeListProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppStoreHomeListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppStoreHomeListProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (AppStoreHomeListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppStoreHomeListProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppStoreHomeListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppStoreHomeListProto parseFrom(InputStream input) throws IOException {
        return (AppStoreHomeListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppStoreHomeListProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppStoreHomeListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppStoreHomeListProto parseDelimitedFrom(InputStream input) throws IOException {
        return (AppStoreHomeListProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static AppStoreHomeListProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppStoreHomeListProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppStoreHomeListProto parseFrom(CodedInputStream input) throws IOException {
        return (AppStoreHomeListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppStoreHomeListProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppStoreHomeListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(AppStoreHomeListProto prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<AppStoreHomeListProto, Builder> implements AppStoreHomeListProtoOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(AppStoreHomeListProto.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeListProtoOrBuilder
        public int getContentType() {
            return ((AppStoreHomeListProto) this.instance).getContentType();
        }

        public Builder setContentType(int value) {
            copyOnWrite();
            ((AppStoreHomeListProto) this.instance).setContentType(value);
            return this;
        }

        public Builder clearContentType() {
            copyOnWrite();
            ((AppStoreHomeListProto) this.instance).clearContentType();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeListProtoOrBuilder
        public String getLayout() {
            return ((AppStoreHomeListProto) this.instance).getLayout();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeListProtoOrBuilder
        public ByteString getLayoutBytes() {
            return ((AppStoreHomeListProto) this.instance).getLayoutBytes();
        }

        public Builder setLayout(String value) {
            copyOnWrite();
            ((AppStoreHomeListProto) this.instance).setLayout(value);
            return this;
        }

        public Builder clearLayout() {
            copyOnWrite();
            ((AppStoreHomeListProto) this.instance).clearLayout();
            return this;
        }

        public Builder setLayoutBytes(ByteString value) {
            copyOnWrite();
            ((AppStoreHomeListProto) this.instance).setLayoutBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeListProtoOrBuilder
        public boolean hasData() {
            return ((AppStoreHomeListProto) this.instance).hasData();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeListProtoOrBuilder
        public AppDataProto getData() {
            return ((AppStoreHomeListProto) this.instance).getData();
        }

        public Builder setData(AppDataProto value) {
            copyOnWrite();
            ((AppStoreHomeListProto) this.instance).setData(value);
            return this;
        }

        public Builder setData(AppDataProto.Builder builderForValue) {
            copyOnWrite();
            ((AppStoreHomeListProto) this.instance).setData(builderForValue);
            return this;
        }

        public Builder mergeData(AppDataProto value) {
            copyOnWrite();
            ((AppStoreHomeListProto) this.instance).mergeData(value);
            return this;
        }

        public Builder clearData() {
            copyOnWrite();
            ((AppStoreHomeListProto) this.instance).clearData();
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.AppStoreHomeListProto$1  reason: invalid class name */
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
                return new AppStoreHomeListProto();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003\t", new Object[]{"contentType_", "layout_", "data_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<AppStoreHomeListProto> parser = PARSER;
                if (parser == null) {
                    synchronized (AppStoreHomeListProto.class) {
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
        AppStoreHomeListProto appStoreHomeListProto = new AppStoreHomeListProto();
        DEFAULT_INSTANCE = appStoreHomeListProto;
        GeneratedMessageLite.registerDefaultInstance(AppStoreHomeListProto.class, appStoreHomeListProto);
    }

    public static AppStoreHomeListProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AppStoreHomeListProto> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
