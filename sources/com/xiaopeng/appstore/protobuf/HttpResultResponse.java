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
public final class HttpResultResponse extends GeneratedMessageLite<HttpResultResponse, Builder> implements HttpResultResponseOrBuilder {
    public static final int CODE_FIELD_NUMBER = 1;
    private static final HttpResultResponse DEFAULT_INSTANCE;
    public static final int MSG_FIELD_NUMBER = 2;
    private static volatile Parser<HttpResultResponse> PARSER;
    private int code_;
    private String msg_ = "";

    private HttpResultResponse() {
    }

    @Override // com.xiaopeng.appstore.protobuf.HttpResultResponseOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.HttpResultResponseOrBuilder
    public String getMsg() {
        return this.msg_;
    }

    @Override // com.xiaopeng.appstore.protobuf.HttpResultResponseOrBuilder
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

    public static HttpResultResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (HttpResultResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static HttpResultResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (HttpResultResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static HttpResultResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (HttpResultResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static HttpResultResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (HttpResultResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static HttpResultResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (HttpResultResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static HttpResultResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (HttpResultResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static HttpResultResponse parseFrom(InputStream input) throws IOException {
        return (HttpResultResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static HttpResultResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (HttpResultResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static HttpResultResponse parseDelimitedFrom(InputStream input) throws IOException {
        return (HttpResultResponse) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static HttpResultResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (HttpResultResponse) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static HttpResultResponse parseFrom(CodedInputStream input) throws IOException {
        return (HttpResultResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static HttpResultResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (HttpResultResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(HttpResultResponse prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<HttpResultResponse, Builder> implements HttpResultResponseOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(HttpResultResponse.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.HttpResultResponseOrBuilder
        public int getCode() {
            return ((HttpResultResponse) this.instance).getCode();
        }

        public Builder setCode(int value) {
            copyOnWrite();
            ((HttpResultResponse) this.instance).setCode(value);
            return this;
        }

        public Builder clearCode() {
            copyOnWrite();
            ((HttpResultResponse) this.instance).clearCode();
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.HttpResultResponseOrBuilder
        public String getMsg() {
            return ((HttpResultResponse) this.instance).getMsg();
        }

        @Override // com.xiaopeng.appstore.protobuf.HttpResultResponseOrBuilder
        public ByteString getMsgBytes() {
            return ((HttpResultResponse) this.instance).getMsgBytes();
        }

        public Builder setMsg(String value) {
            copyOnWrite();
            ((HttpResultResponse) this.instance).setMsg(value);
            return this;
        }

        public Builder clearMsg() {
            copyOnWrite();
            ((HttpResultResponse) this.instance).clearMsg();
            return this;
        }

        public Builder setMsgBytes(ByteString value) {
            copyOnWrite();
            ((HttpResultResponse) this.instance).setMsgBytes(value);
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.HttpResultResponse$1  reason: invalid class name */
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
                return new HttpResultResponse();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0002Ȉ", new Object[]{"code_", "msg_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<HttpResultResponse> parser = PARSER;
                if (parser == null) {
                    synchronized (HttpResultResponse.class) {
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
        HttpResultResponse httpResultResponse = new HttpResultResponse();
        DEFAULT_INSTANCE = httpResultResponse;
        GeneratedMessageLite.registerDefaultInstance(HttpResultResponse.class, httpResultResponse);
    }

    public static HttpResultResponse getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<HttpResultResponse> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
