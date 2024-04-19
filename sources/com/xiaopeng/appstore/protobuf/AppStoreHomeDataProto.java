package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.xiaopeng.appstore.protobuf.AppStoreHomeListProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
/* loaded from: classes2.dex */
public final class AppStoreHomeDataProto extends GeneratedMessageLite<AppStoreHomeDataProto, Builder> implements AppStoreHomeDataProtoOrBuilder {
    private static final AppStoreHomeDataProto DEFAULT_INSTANCE;
    public static final int DESC_FIELD_NUMBER = 2;
    public static final int ID_FIELD_NUMBER = 1;
    public static final int LIST_FIELD_NUMBER = 4;
    private static volatile Parser<AppStoreHomeDataProto> PARSER = null;
    public static final int TITLE_FIELD_NUMBER = 3;
    private String id_ = "";
    private String desc_ = "";
    private String title_ = "";
    private Internal.ProtobufList<AppStoreHomeListProto> list_ = emptyProtobufList();

    private AppStoreHomeDataProto() {
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
    public String getId() {
        return this.id_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
    public String getDesc() {
        return this.desc_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
    public ByteString getDescBytes() {
        return ByteString.copyFromUtf8(this.desc_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDesc(String value) {
        Objects.requireNonNull(value);
        this.desc_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearDesc() {
        this.desc_ = getDefaultInstance().getDesc();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDescBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.desc_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
    public String getTitle() {
        return this.title_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
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

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
    public List<AppStoreHomeListProto> getListList() {
        return this.list_;
    }

    public List<? extends AppStoreHomeListProtoOrBuilder> getListOrBuilderList() {
        return this.list_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
    public int getListCount() {
        return this.list_.size();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
    public AppStoreHomeListProto getList(int index) {
        return this.list_.get(index);
    }

    public AppStoreHomeListProtoOrBuilder getListOrBuilder(int index) {
        return this.list_.get(index);
    }

    private void ensureListIsMutable() {
        if (this.list_.isModifiable()) {
            return;
        }
        this.list_ = GeneratedMessageLite.mutableCopy(this.list_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setList(int index, AppStoreHomeListProto value) {
        Objects.requireNonNull(value);
        ensureListIsMutable();
        this.list_.set(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setList(int index, AppStoreHomeListProto.Builder builderForValue) {
        ensureListIsMutable();
        this.list_.set(index, builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addList(AppStoreHomeListProto value) {
        Objects.requireNonNull(value);
        ensureListIsMutable();
        this.list_.add(value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addList(int index, AppStoreHomeListProto value) {
        Objects.requireNonNull(value);
        ensureListIsMutable();
        this.list_.add(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addList(AppStoreHomeListProto.Builder builderForValue) {
        ensureListIsMutable();
        this.list_.add(builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addList(int index, AppStoreHomeListProto.Builder builderForValue) {
        ensureListIsMutable();
        this.list_.add(index, builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllList(Iterable<? extends AppStoreHomeListProto> values) {
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

    public static AppStoreHomeDataProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (AppStoreHomeDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppStoreHomeDataProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppStoreHomeDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppStoreHomeDataProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (AppStoreHomeDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppStoreHomeDataProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppStoreHomeDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppStoreHomeDataProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (AppStoreHomeDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppStoreHomeDataProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppStoreHomeDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppStoreHomeDataProto parseFrom(InputStream input) throws IOException {
        return (AppStoreHomeDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppStoreHomeDataProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppStoreHomeDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppStoreHomeDataProto parseDelimitedFrom(InputStream input) throws IOException {
        return (AppStoreHomeDataProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static AppStoreHomeDataProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppStoreHomeDataProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppStoreHomeDataProto parseFrom(CodedInputStream input) throws IOException {
        return (AppStoreHomeDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppStoreHomeDataProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppStoreHomeDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(AppStoreHomeDataProto prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<AppStoreHomeDataProto, Builder> implements AppStoreHomeDataProtoOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(AppStoreHomeDataProto.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
        public String getId() {
            return ((AppStoreHomeDataProto) this.instance).getId();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
        public ByteString getIdBytes() {
            return ((AppStoreHomeDataProto) this.instance).getIdBytes();
        }

        public Builder setId(String value) {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).setId(value);
            return this;
        }

        public Builder clearId() {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).clearId();
            return this;
        }

        public Builder setIdBytes(ByteString value) {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).setIdBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
        public String getDesc() {
            return ((AppStoreHomeDataProto) this.instance).getDesc();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
        public ByteString getDescBytes() {
            return ((AppStoreHomeDataProto) this.instance).getDescBytes();
        }

        public Builder setDesc(String value) {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).setDesc(value);
            return this;
        }

        public Builder clearDesc() {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).clearDesc();
            return this;
        }

        public Builder setDescBytes(ByteString value) {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).setDescBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
        public String getTitle() {
            return ((AppStoreHomeDataProto) this.instance).getTitle();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
        public ByteString getTitleBytes() {
            return ((AppStoreHomeDataProto) this.instance).getTitleBytes();
        }

        public Builder setTitle(String value) {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).setTitle(value);
            return this;
        }

        public Builder clearTitle() {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).clearTitle();
            return this;
        }

        public Builder setTitleBytes(ByteString value) {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).setTitleBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
        public List<AppStoreHomeListProto> getListList() {
            return Collections.unmodifiableList(((AppStoreHomeDataProto) this.instance).getListList());
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
        public int getListCount() {
            return ((AppStoreHomeDataProto) this.instance).getListCount();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppStoreHomeDataProtoOrBuilder
        public AppStoreHomeListProto getList(int index) {
            return ((AppStoreHomeDataProto) this.instance).getList(index);
        }

        public Builder setList(int index, AppStoreHomeListProto value) {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).setList(index, value);
            return this;
        }

        public Builder setList(int index, AppStoreHomeListProto.Builder builderForValue) {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).setList(index, builderForValue);
            return this;
        }

        public Builder addList(AppStoreHomeListProto value) {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).addList(value);
            return this;
        }

        public Builder addList(int index, AppStoreHomeListProto value) {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).addList(index, value);
            return this;
        }

        public Builder addList(AppStoreHomeListProto.Builder builderForValue) {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).addList(builderForValue);
            return this;
        }

        public Builder addList(int index, AppStoreHomeListProto.Builder builderForValue) {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).addList(index, builderForValue);
            return this;
        }

        public Builder addAllList(Iterable<? extends AppStoreHomeListProto> values) {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).addAllList(values);
            return this;
        }

        public Builder clearList() {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).clearList();
            return this;
        }

        public Builder removeList(int index) {
            copyOnWrite();
            ((AppStoreHomeDataProto) this.instance).removeList(index);
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.AppStoreHomeDataProto$1  reason: invalid class name */
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
                return new AppStoreHomeDataProto();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0001\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ\u0004\u001b", new Object[]{"id_", "desc_", "title_", "list_", AppStoreHomeListProto.class});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<AppStoreHomeDataProto> parser = PARSER;
                if (parser == null) {
                    synchronized (AppStoreHomeDataProto.class) {
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
        AppStoreHomeDataProto appStoreHomeDataProto = new AppStoreHomeDataProto();
        DEFAULT_INSTANCE = appStoreHomeDataProto;
        GeneratedMessageLite.registerDefaultInstance(AppStoreHomeDataProto.class, appStoreHomeDataProto);
    }

    public static AppStoreHomeDataProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AppStoreHomeDataProto> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
