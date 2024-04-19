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
public final class DeveloperDataProto extends GeneratedMessageLite<DeveloperDataProto, Builder> implements DeveloperDataProtoOrBuilder {
    private static final DeveloperDataProto DEFAULT_INSTANCE;
    public static final int DEVELOPER_ID_FIELD_NUMBER = 1;
    public static final int DEVELOPER_NAME_FIELD_NUMBER = 2;
    private static volatile Parser<DeveloperDataProto> PARSER;
    private int developerId_;
    private String developerName_ = "";

    private DeveloperDataProto() {
    }

    @Override // com.xiaopeng.appstore.protobuf.DeveloperDataProtoOrBuilder
    public int getDeveloperId() {
        return this.developerId_;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDeveloperId(int value) {
        this.developerId_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearDeveloperId() {
        this.developerId_ = 0;
    }

    @Override // com.xiaopeng.appstore.protobuf.DeveloperDataProtoOrBuilder
    public String getDeveloperName() {
        return this.developerName_;
    }

    @Override // com.xiaopeng.appstore.protobuf.DeveloperDataProtoOrBuilder
    public ByteString getDeveloperNameBytes() {
        return ByteString.copyFromUtf8(this.developerName_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDeveloperName(String value) {
        Objects.requireNonNull(value);
        this.developerName_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearDeveloperName() {
        this.developerName_ = getDefaultInstance().getDeveloperName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDeveloperNameBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.developerName_ = value.toStringUtf8();
    }

    public static DeveloperDataProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (DeveloperDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static DeveloperDataProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (DeveloperDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DeveloperDataProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (DeveloperDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static DeveloperDataProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (DeveloperDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DeveloperDataProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (DeveloperDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static DeveloperDataProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (DeveloperDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DeveloperDataProto parseFrom(InputStream input) throws IOException {
        return (DeveloperDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static DeveloperDataProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (DeveloperDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static DeveloperDataProto parseDelimitedFrom(InputStream input) throws IOException {
        return (DeveloperDataProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static DeveloperDataProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (DeveloperDataProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static DeveloperDataProto parseFrom(CodedInputStream input) throws IOException {
        return (DeveloperDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static DeveloperDataProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (DeveloperDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(DeveloperDataProto prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<DeveloperDataProto, Builder> implements DeveloperDataProtoOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(DeveloperDataProto.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.DeveloperDataProtoOrBuilder
        public int getDeveloperId() {
            return ((DeveloperDataProto) this.instance).getDeveloperId();
        }

        public Builder setDeveloperId(int value) {
            copyOnWrite();
            ((DeveloperDataProto) this.instance).setDeveloperId(value);
            return this;
        }

        public Builder clearDeveloperId() {
            copyOnWrite();
            ((DeveloperDataProto) this.instance).clearDeveloperId();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.DeveloperDataProtoOrBuilder
        public String getDeveloperName() {
            return ((DeveloperDataProto) this.instance).getDeveloperName();
        }

        @Override // com.xiaopeng.appstore.protobuf.DeveloperDataProtoOrBuilder
        public ByteString getDeveloperNameBytes() {
            return ((DeveloperDataProto) this.instance).getDeveloperNameBytes();
        }

        public Builder setDeveloperName(String value) {
            copyOnWrite();
            ((DeveloperDataProto) this.instance).setDeveloperName(value);
            return this;
        }

        public Builder clearDeveloperName() {
            copyOnWrite();
            ((DeveloperDataProto) this.instance).clearDeveloperName();
            return this;
        }

        public Builder setDeveloperNameBytes(ByteString value) {
            copyOnWrite();
            ((DeveloperDataProto) this.instance).setDeveloperNameBytes(value);
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.DeveloperDataProto$1  reason: invalid class name */
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
                return new DeveloperDataProto();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0002Ȉ", new Object[]{"developerId_", "developerName_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<DeveloperDataProto> parser = PARSER;
                if (parser == null) {
                    synchronized (DeveloperDataProto.class) {
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
        DeveloperDataProto developerDataProto = new DeveloperDataProto();
        DEFAULT_INSTANCE = developerDataProto;
        GeneratedMessageLite.registerDefaultInstance(DeveloperDataProto.class, developerDataProto);
    }

    public static DeveloperDataProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<DeveloperDataProto> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
