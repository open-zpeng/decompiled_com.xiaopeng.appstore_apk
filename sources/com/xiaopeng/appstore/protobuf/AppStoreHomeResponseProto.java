package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.xiaopeng.appstore.protobuf.AppStoreHomeDataWrapperProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Objects;
/* loaded from: classes2.dex */
public final class AppStoreHomeResponseProto extends GeneratedMessageLite<AppStoreHomeResponseProto, Builder> implements AppStoreHomeResponseProtoOrBuilder {
    public static final int CODE_FIELD_NUMBER = 1;
    public static final int DATA_FIELD_NUMBER = 3;
    private static final AppStoreHomeResponseProto DEFAULT_INSTANCE;
    public static final int MSG_FIELD_NUMBER = 2;
    private static volatile Parser<AppStoreHomeResponseProto> PARSER;
    private int code_;
    private AppStoreHomeDataWrapperProto data_;
    private String msg_ = "";

    private AppStoreHomeResponseProto() {
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeResponseProtoOrBuilder
    public int getCode() {
        return this.code_;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCode(int value) {
        this.code_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearCode() {
        this.code_ = 0;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeResponseProtoOrBuilder
    public String getMsg() {
        return this.msg_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeResponseProtoOrBuilder
    public ByteString getMsgBytes() {
        return ByteString.copyFromUtf8(this.msg_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMsg(String value) {
        Objects.requireNonNull(value);
        this.msg_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearMsg() {
        this.msg_ = getDefaultInstance().getMsg();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMsgBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.msg_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeResponseProtoOrBuilder
    public boolean hasData() {
        return this.data_ != null;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeResponseProtoOrBuilder
    public AppStoreHomeDataWrapperProto getData() {
        AppStoreHomeDataWrapperProto appStoreHomeDataWrapperProto = this.data_;
        return appStoreHomeDataWrapperProto == null ? AppStoreHomeDataWrapperProto.getDefaultInstance() : appStoreHomeDataWrapperProto;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setData(AppStoreHomeDataWrapperProto value) {
        Objects.requireNonNull(value);
        this.data_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setData(AppStoreHomeDataWrapperProto.Builder builderForValue) {
        this.data_ = builderForValue.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mergeData(AppStoreHomeDataWrapperProto value) {
        Objects.requireNonNull(value);
        AppStoreHomeDataWrapperProto appStoreHomeDataWrapperProto = this.data_;
        if (appStoreHomeDataWrapperProto != null && appStoreHomeDataWrapperProto != AppStoreHomeDataWrapperProto.getDefaultInstance()) {
            this.data_ = AppStoreHomeDataWrapperProto.newBuilder(this.data_).mergeFrom((AppStoreHomeDataWrapperProto.Builder) value).buildPartial();
        } else {
            this.data_ = value;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearData() {
        this.data_ = null;
    }

    public static AppStoreHomeResponseProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (AppStoreHomeResponseProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppStoreHomeResponseProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppStoreHomeResponseProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppStoreHomeResponseProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (AppStoreHomeResponseProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppStoreHomeResponseProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppStoreHomeResponseProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppStoreHomeResponseProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (AppStoreHomeResponseProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppStoreHomeResponseProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppStoreHomeResponseProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppStoreHomeResponseProto parseFrom(InputStream input) throws IOException {
        return (AppStoreHomeResponseProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppStoreHomeResponseProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppStoreHomeResponseProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppStoreHomeResponseProto parseDelimitedFrom(InputStream input) throws IOException {
        return (AppStoreHomeResponseProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static AppStoreHomeResponseProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppStoreHomeResponseProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppStoreHomeResponseProto parseFrom(CodedInputStream input) throws IOException {
        return (AppStoreHomeResponseProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppStoreHomeResponseProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppStoreHomeResponseProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(AppStoreHomeResponseProto prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<AppStoreHomeResponseProto, Builder> implements AppStoreHomeResponseProtoOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(AppStoreHomeResponseProto.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeResponseProtoOrBuilder
        public int getCode() {
            return ((AppStoreHomeResponseProto) this.instance).getCode();
        }

        public Builder setCode(int value) {
            copyOnWrite();
            ((AppStoreHomeResponseProto) this.instance).setCode(value);
            return this;
        }

        public Builder clearCode() {
            copyOnWrite();
            ((AppStoreHomeResponseProto) this.instance).clearCode();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeResponseProtoOrBuilder
        public String getMsg() {
            return ((AppStoreHomeResponseProto) this.instance).getMsg();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeResponseProtoOrBuilder
        public ByteString getMsgBytes() {
            return ((AppStoreHomeResponseProto) this.instance).getMsgBytes();
        }

        public Builder setMsg(String value) {
            copyOnWrite();
            ((AppStoreHomeResponseProto) this.instance).setMsg(value);
            return this;
        }

        public Builder clearMsg() {
            copyOnWrite();
            ((AppStoreHomeResponseProto) this.instance).clearMsg();
            return this;
        }

        public Builder setMsgBytes(ByteString value) {
            copyOnWrite();
            ((AppStoreHomeResponseProto) this.instance).setMsgBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeResponseProtoOrBuilder
        public boolean hasData() {
            return ((AppStoreHomeResponseProto) this.instance).hasData();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeResponseProtoOrBuilder
        public AppStoreHomeDataWrapperProto getData() {
            return ((AppStoreHomeResponseProto) this.instance).getData();
        }

        public Builder setData(AppStoreHomeDataWrapperProto value) {
            copyOnWrite();
            ((AppStoreHomeResponseProto) this.instance).setData(value);
            return this;
        }

        public Builder setData(AppStoreHomeDataWrapperProto.Builder builderForValue) {
            copyOnWrite();
            ((AppStoreHomeResponseProto) this.instance).setData(builderForValue);
            return this;
        }

        public Builder mergeData(AppStoreHomeDataWrapperProto value) {
            copyOnWrite();
            ((AppStoreHomeResponseProto) this.instance).mergeData(value);
            return this;
        }

        public Builder clearData() {
            copyOnWrite();
            ((AppStoreHomeResponseProto) this.instance).clearData();
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.AppStoreHomeResponseProto$1  reason: invalid class name */
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
                return new AppStoreHomeResponseProto();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003\t", new Object[]{"code_", "msg_", "data_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<AppStoreHomeResponseProto> parser = PARSER;
                if (parser == null) {
                    synchronized (AppStoreHomeResponseProto.class) {
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
        AppStoreHomeResponseProto appStoreHomeResponseProto = new AppStoreHomeResponseProto();
        DEFAULT_INSTANCE = appStoreHomeResponseProto;
        GeneratedMessageLite.registerDefaultInstance(AppStoreHomeResponseProto.class, appStoreHomeResponseProto);
    }

    public static AppStoreHomeResponseProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AppStoreHomeResponseProto> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
