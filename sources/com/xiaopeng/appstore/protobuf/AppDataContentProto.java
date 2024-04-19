package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.xiaopeng.appstore.protobuf.AppItemProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Objects;
/* loaded from: classes2.dex */
public final class AppDataContentProto extends GeneratedMessageLite<AppDataContentProto, Builder> implements AppDataContentProtoOrBuilder {
    public static final int CONTENT_TYPE_FIELD_NUMBER = 1;
    public static final int DATA_FIELD_NUMBER = 2;
    private static final AppDataContentProto DEFAULT_INSTANCE;
    private static volatile Parser<AppDataContentProto> PARSER;
    private int contentType_;
    private AppItemProto data_;

    private AppDataContentProto() {
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDataContentProtoOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.AppDataContentProtoOrBuilder
    public boolean hasData() {
        return this.data_ != null;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDataContentProtoOrBuilder
    public AppItemProto getData() {
        AppItemProto appItemProto = this.data_;
        return appItemProto == null ? AppItemProto.getDefaultInstance() : appItemProto;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setData(AppItemProto value) {
        Objects.requireNonNull(value);
        this.data_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setData(AppItemProto.Builder builderForValue) {
        this.data_ = builderForValue.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mergeData(AppItemProto value) {
        Objects.requireNonNull(value);
        AppItemProto appItemProto = this.data_;
        if (appItemProto != null && appItemProto != AppItemProto.getDefaultInstance()) {
            this.data_ = AppItemProto.newBuilder(this.data_).mergeFrom((AppItemProto.Builder) value).buildPartial();
        } else {
            this.data_ = value;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearData() {
        this.data_ = null;
    }

    public static AppDataContentProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (AppDataContentProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppDataContentProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppDataContentProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppDataContentProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (AppDataContentProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppDataContentProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppDataContentProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppDataContentProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (AppDataContentProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppDataContentProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppDataContentProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppDataContentProto parseFrom(InputStream input) throws IOException {
        return (AppDataContentProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppDataContentProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppDataContentProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppDataContentProto parseDelimitedFrom(InputStream input) throws IOException {
        return (AppDataContentProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static AppDataContentProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppDataContentProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppDataContentProto parseFrom(CodedInputStream input) throws IOException {
        return (AppDataContentProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppDataContentProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppDataContentProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(AppDataContentProto prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<AppDataContentProto, Builder> implements AppDataContentProtoOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(AppDataContentProto.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDataContentProtoOrBuilder
        public int getContentType() {
            return ((AppDataContentProto) this.instance).getContentType();
        }

        public Builder setContentType(int value) {
            copyOnWrite();
            ((AppDataContentProto) this.instance).setContentType(value);
            return this;
        }

        public Builder clearContentType() {
            copyOnWrite();
            ((AppDataContentProto) this.instance).clearContentType();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDataContentProtoOrBuilder
        public boolean hasData() {
            return ((AppDataContentProto) this.instance).hasData();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDataContentProtoOrBuilder
        public AppItemProto getData() {
            return ((AppDataContentProto) this.instance).getData();
        }

        public Builder setData(AppItemProto value) {
            copyOnWrite();
            ((AppDataContentProto) this.instance).setData(value);
            return this;
        }

        public Builder setData(AppItemProto.Builder builderForValue) {
            copyOnWrite();
            ((AppDataContentProto) this.instance).setData(builderForValue);
            return this;
        }

        public Builder mergeData(AppItemProto value) {
            copyOnWrite();
            ((AppDataContentProto) this.instance).mergeData(value);
            return this;
        }

        public Builder clearData() {
            copyOnWrite();
            ((AppDataContentProto) this.instance).clearData();
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.AppDataContentProto$1  reason: invalid class name */
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
                return new AppDataContentProto();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0002\t", new Object[]{"contentType_", "data_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<AppDataContentProto> parser = PARSER;
                if (parser == null) {
                    synchronized (AppDataContentProto.class) {
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
        AppDataContentProto appDataContentProto = new AppDataContentProto();
        DEFAULT_INSTANCE = appDataContentProto;
        GeneratedMessageLite.registerDefaultInstance(AppDataContentProto.class, appDataContentProto);
    }

    public static AppDataContentProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AppDataContentProto> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
