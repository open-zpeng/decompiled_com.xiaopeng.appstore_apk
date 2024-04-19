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
public final class AppIconDataProto extends GeneratedMessageLite<AppIconDataProto, Builder> implements AppIconDataProtoOrBuilder {
    private static final AppIconDataProto DEFAULT_INSTANCE;
    public static final int LARGE_ICON_FIELD_NUMBER = 2;
    private static volatile Parser<AppIconDataProto> PARSER = null;
    public static final int SMALL_ICON_FIELD_NUMBER = 1;
    private String smallIcon_ = "";
    private String largeIcon_ = "";

    private AppIconDataProto() {
    }

    @Override // com.xiaopeng.appstore.protobuf.AppIconDataProtoOrBuilder
    public String getSmallIcon() {
        return this.smallIcon_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppIconDataProtoOrBuilder
    public ByteString getSmallIconBytes() {
        return ByteString.copyFromUtf8(this.smallIcon_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSmallIcon(String value) {
        Objects.requireNonNull(value);
        this.smallIcon_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearSmallIcon() {
        this.smallIcon_ = getDefaultInstance().getSmallIcon();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSmallIconBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.smallIcon_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppIconDataProtoOrBuilder
    public String getLargeIcon() {
        return this.largeIcon_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppIconDataProtoOrBuilder
    public ByteString getLargeIconBytes() {
        return ByteString.copyFromUtf8(this.largeIcon_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLargeIcon(String value) {
        Objects.requireNonNull(value);
        this.largeIcon_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearLargeIcon() {
        this.largeIcon_ = getDefaultInstance().getLargeIcon();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLargeIconBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.largeIcon_ = value.toStringUtf8();
    }

    public static AppIconDataProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (AppIconDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppIconDataProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppIconDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppIconDataProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (AppIconDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppIconDataProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppIconDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppIconDataProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (AppIconDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppIconDataProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppIconDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppIconDataProto parseFrom(InputStream input) throws IOException {
        return (AppIconDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppIconDataProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppIconDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppIconDataProto parseDelimitedFrom(InputStream input) throws IOException {
        return (AppIconDataProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static AppIconDataProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppIconDataProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppIconDataProto parseFrom(CodedInputStream input) throws IOException {
        return (AppIconDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppIconDataProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppIconDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(AppIconDataProto prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<AppIconDataProto, Builder> implements AppIconDataProtoOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(AppIconDataProto.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.AppIconDataProtoOrBuilder
        public String getSmallIcon() {
            return ((AppIconDataProto) this.instance).getSmallIcon();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppIconDataProtoOrBuilder
        public ByteString getSmallIconBytes() {
            return ((AppIconDataProto) this.instance).getSmallIconBytes();
        }

        public Builder setSmallIcon(String value) {
            copyOnWrite();
            ((AppIconDataProto) this.instance).setSmallIcon(value);
            return this;
        }

        public Builder clearSmallIcon() {
            copyOnWrite();
            ((AppIconDataProto) this.instance).clearSmallIcon();
            return this;
        }

        public Builder setSmallIconBytes(ByteString value) {
            copyOnWrite();
            ((AppIconDataProto) this.instance).setSmallIconBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppIconDataProtoOrBuilder
        public String getLargeIcon() {
            return ((AppIconDataProto) this.instance).getLargeIcon();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppIconDataProtoOrBuilder
        public ByteString getLargeIconBytes() {
            return ((AppIconDataProto) this.instance).getLargeIconBytes();
        }

        public Builder setLargeIcon(String value) {
            copyOnWrite();
            ((AppIconDataProto) this.instance).setLargeIcon(value);
            return this;
        }

        public Builder clearLargeIcon() {
            copyOnWrite();
            ((AppIconDataProto) this.instance).clearLargeIcon();
            return this;
        }

        public Builder setLargeIconBytes(ByteString value) {
            copyOnWrite();
            ((AppIconDataProto) this.instance).setLargeIconBytes(value);
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.AppIconDataProto$1  reason: invalid class name */
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
                return new AppIconDataProto();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ", new Object[]{"smallIcon_", "largeIcon_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<AppIconDataProto> parser = PARSER;
                if (parser == null) {
                    synchronized (AppIconDataProto.class) {
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
        AppIconDataProto appIconDataProto = new AppIconDataProto();
        DEFAULT_INSTANCE = appIconDataProto;
        GeneratedMessageLite.registerDefaultInstance(AppIconDataProto.class, appIconDataProto);
    }

    public static AppIconDataProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AppIconDataProto> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
