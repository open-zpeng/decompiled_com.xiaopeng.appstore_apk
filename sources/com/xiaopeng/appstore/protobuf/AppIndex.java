package com.xiaopeng.appstore.protobuf;

import androidx.room.util.TableInfo;
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
public final class AppIndex extends GeneratedMessageLite<AppIndex, Builder> implements AppIndexOrBuilder {
    private static final AppIndex DEFAULT_INSTANCE;
    public static final int INDEX_FIELD_NUMBER = 2;
    public static final int PACKAGE_NAME_FIELD_NUMBER = 1;
    private static volatile Parser<AppIndex> PARSER;
    private int index_;
    private String packageName_ = "";

    private AppIndex() {
    }

    @Override // com.xiaopeng.appstore.protobuf.AppIndexOrBuilder
    public String getPackageName() {
        return this.packageName_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppIndexOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.AppIndexOrBuilder
    public int getIndex() {
        return this.index_;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setIndex(int value) {
        this.index_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearIndex() {
        this.index_ = 0;
    }

    public static AppIndex parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (AppIndex) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppIndex parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppIndex) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppIndex parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (AppIndex) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppIndex parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppIndex) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppIndex parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (AppIndex) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppIndex parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppIndex) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppIndex parseFrom(InputStream input) throws IOException {
        return (AppIndex) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppIndex parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppIndex) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppIndex parseDelimitedFrom(InputStream input) throws IOException {
        return (AppIndex) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static AppIndex parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppIndex) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppIndex parseFrom(CodedInputStream input) throws IOException {
        return (AppIndex) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppIndex parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppIndex) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(AppIndex prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<AppIndex, Builder> implements AppIndexOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(AppIndex.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.AppIndexOrBuilder
        public String getPackageName() {
            return ((AppIndex) this.instance).getPackageName();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppIndexOrBuilder
        public ByteString getPackageNameBytes() {
            return ((AppIndex) this.instance).getPackageNameBytes();
        }

        public Builder setPackageName(String value) {
            copyOnWrite();
            ((AppIndex) this.instance).setPackageName(value);
            return this;
        }

        public Builder clearPackageName() {
            copyOnWrite();
            ((AppIndex) this.instance).clearPackageName();
            return this;
        }

        public Builder setPackageNameBytes(ByteString value) {
            copyOnWrite();
            ((AppIndex) this.instance).setPackageNameBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppIndexOrBuilder
        public int getIndex() {
            return ((AppIndex) this.instance).getIndex();
        }

        public Builder setIndex(int value) {
            copyOnWrite();
            ((AppIndex) this.instance).setIndex(value);
            return this;
        }

        public Builder clearIndex() {
            copyOnWrite();
            ((AppIndex) this.instance).clearIndex();
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.AppIndex$1  reason: invalid class name */
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
                return new AppIndex();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002\u0004", new Object[]{"packageName_", TableInfo.Index.DEFAULT_PREFIX});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<AppIndex> parser = PARSER;
                if (parser == null) {
                    synchronized (AppIndex.class) {
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
        AppIndex appIndex = new AppIndex();
        DEFAULT_INSTANCE = appIndex;
        GeneratedMessageLite.registerDefaultInstance(AppIndex.class, appIndex);
    }

    public static AppIndex getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AppIndex> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
