package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.xiaopeng.appstore.protobuf.AppDataContentProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
/* loaded from: classes2.dex */
public final class AppDataProto extends GeneratedMessageLite<AppDataProto, Builder> implements AppDataProtoOrBuilder {
    private static final AppDataProto DEFAULT_INSTANCE;
    public static final int ID_FIELD_NUMBER = 1;
    public static final int LIST_FIELD_NUMBER = 2;
    private static volatile Parser<AppDataProto> PARSER = null;
    public static final int TITLE_FIELD_NUMBER = 3;
    private String id_ = "";
    private Internal.ProtobufList<AppDataContentProto> list_ = emptyProtobufList();
    private String title_ = "";

    private AppDataProto() {
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDataProtoOrBuilder
    public String getId() {
        return this.id_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDataProtoOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.AppDataProtoOrBuilder
    public List<AppDataContentProto> getListList() {
        return this.list_;
    }

    public List<? extends AppDataContentProtoOrBuilder> getListOrBuilderList() {
        return this.list_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDataProtoOrBuilder
    public int getListCount() {
        return this.list_.size();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDataProtoOrBuilder
    public AppDataContentProto getList(int index) {
        return this.list_.get(index);
    }

    public AppDataContentProtoOrBuilder getListOrBuilder(int index) {
        return this.list_.get(index);
    }

    private void ensureListIsMutable() {
        if (this.list_.isModifiable()) {
            return;
        }
        this.list_ = GeneratedMessageLite.mutableCopy(this.list_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setList(int index, AppDataContentProto value) {
        Objects.requireNonNull(value);
        ensureListIsMutable();
        this.list_.set(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setList(int index, AppDataContentProto.Builder builderForValue) {
        ensureListIsMutable();
        this.list_.set(index, builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addList(AppDataContentProto value) {
        Objects.requireNonNull(value);
        ensureListIsMutable();
        this.list_.add(value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addList(int index, AppDataContentProto value) {
        Objects.requireNonNull(value);
        ensureListIsMutable();
        this.list_.add(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addList(AppDataContentProto.Builder builderForValue) {
        ensureListIsMutable();
        this.list_.add(builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addList(int index, AppDataContentProto.Builder builderForValue) {
        ensureListIsMutable();
        this.list_.add(index, builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllList(Iterable<? extends AppDataContentProto> values) {
        ensureListIsMutable();
        AbstractMessageLite.addAll((Iterable) values, (List) this.list_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearList() {
        this.list_ = emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeList(int index) {
        ensureListIsMutable();
        this.list_.remove(index);
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDataProtoOrBuilder
    public String getTitle() {
        return this.title_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppDataProtoOrBuilder
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

    public static AppDataProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (AppDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppDataProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppDataProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (AppDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppDataProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppDataProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (AppDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppDataProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppDataProto parseFrom(InputStream input) throws IOException {
        return (AppDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppDataProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppDataProto parseDelimitedFrom(InputStream input) throws IOException {
        return (AppDataProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static AppDataProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppDataProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppDataProto parseFrom(CodedInputStream input) throws IOException {
        return (AppDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppDataProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(AppDataProto prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<AppDataProto, Builder> implements AppDataProtoOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(AppDataProto.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDataProtoOrBuilder
        public String getId() {
            return ((AppDataProto) this.instance).getId();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDataProtoOrBuilder
        public ByteString getIdBytes() {
            return ((AppDataProto) this.instance).getIdBytes();
        }

        public Builder setId(String value) {
            copyOnWrite();
            ((AppDataProto) this.instance).setId(value);
            return this;
        }

        public Builder clearId() {
            copyOnWrite();
            ((AppDataProto) this.instance).clearId();
            return this;
        }

        public Builder setIdBytes(ByteString value) {
            copyOnWrite();
            ((AppDataProto) this.instance).setIdBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDataProtoOrBuilder
        public List<AppDataContentProto> getListList() {
            return Collections.unmodifiableList(((AppDataProto) this.instance).getListList());
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDataProtoOrBuilder
        public int getListCount() {
            return ((AppDataProto) this.instance).getListCount();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDataProtoOrBuilder
        public AppDataContentProto getList(int index) {
            return ((AppDataProto) this.instance).getList(index);
        }

        public Builder setList(int index, AppDataContentProto value) {
            copyOnWrite();
            ((AppDataProto) this.instance).setList(index, value);
            return this;
        }

        public Builder setList(int index, AppDataContentProto.Builder builderForValue) {
            copyOnWrite();
            ((AppDataProto) this.instance).setList(index, builderForValue);
            return this;
        }

        public Builder addList(AppDataContentProto value) {
            copyOnWrite();
            ((AppDataProto) this.instance).addList(value);
            return this;
        }

        public Builder addList(int index, AppDataContentProto value) {
            copyOnWrite();
            ((AppDataProto) this.instance).addList(index, value);
            return this;
        }

        public Builder addList(AppDataContentProto.Builder builderForValue) {
            copyOnWrite();
            ((AppDataProto) this.instance).addList(builderForValue);
            return this;
        }

        public Builder addList(int index, AppDataContentProto.Builder builderForValue) {
            copyOnWrite();
            ((AppDataProto) this.instance).addList(index, builderForValue);
            return this;
        }

        public Builder addAllList(Iterable<? extends AppDataContentProto> values) {
            copyOnWrite();
            ((AppDataProto) this.instance).addAllList(values);
            return this;
        }

        public Builder clearList() {
            copyOnWrite();
            ((AppDataProto) this.instance).clearList();
            return this;
        }

        public Builder removeList(int index) {
            copyOnWrite();
            ((AppDataProto) this.instance).removeList(index);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDataProtoOrBuilder
        public String getTitle() {
            return ((AppDataProto) this.instance).getTitle();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppDataProtoOrBuilder
        public ByteString getTitleBytes() {
            return ((AppDataProto) this.instance).getTitleBytes();
        }

        public Builder setTitle(String value) {
            copyOnWrite();
            ((AppDataProto) this.instance).setTitle(value);
            return this;
        }

        public Builder clearTitle() {
            copyOnWrite();
            ((AppDataProto) this.instance).clearTitle();
            return this;
        }

        public Builder setTitleBytes(ByteString value) {
            copyOnWrite();
            ((AppDataProto) this.instance).setTitleBytes(value);
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.AppDataProto$1  reason: invalid class name */
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
                return new AppDataProto();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0001\u0000\u0001Ȉ\u0002\u001b\u0003Ȉ", new Object[]{"id_", "list_", AppDataContentProto.class, "title_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<AppDataProto> parser = PARSER;
                if (parser == null) {
                    synchronized (AppDataProto.class) {
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
        AppDataProto appDataProto = new AppDataProto();
        DEFAULT_INSTANCE = appDataProto;
        GeneratedMessageLite.registerDefaultInstance(AppDataProto.class, appDataProto);
    }

    public static AppDataProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AppDataProto> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
