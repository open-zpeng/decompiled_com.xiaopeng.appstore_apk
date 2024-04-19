package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.xiaopeng.appstore.protobuf.AppIndex;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
/* loaded from: classes2.dex */
public final class AppIndexList extends GeneratedMessageLite<AppIndexList, Builder> implements AppIndexListOrBuilder {
    private static final AppIndexList DEFAULT_INSTANCE;
    public static final int INDEX_LIST_FIELD_NUMBER = 1;
    private static volatile Parser<AppIndexList> PARSER;
    private Internal.ProtobufList<AppIndex> indexList_ = emptyProtobufList();

    private AppIndexList() {
    }

    @Override // com.xiaopeng.appstore.protobuf.AppIndexListOrBuilder
    public List<AppIndex> getIndexListList() {
        return this.indexList_;
    }

    public List<? extends AppIndexOrBuilder> getIndexListOrBuilderList() {
        return this.indexList_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppIndexListOrBuilder
    public int getIndexListCount() {
        return this.indexList_.size();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppIndexListOrBuilder
    public AppIndex getIndexList(int index) {
        return this.indexList_.get(index);
    }

    public AppIndexOrBuilder getIndexListOrBuilder(int index) {
        return this.indexList_.get(index);
    }

    private void ensureIndexListIsMutable() {
        if (this.indexList_.isModifiable()) {
            return;
        }
        this.indexList_ = GeneratedMessageLite.mutableCopy(this.indexList_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setIndexList(int index, AppIndex value) {
        Objects.requireNonNull(value);
        ensureIndexListIsMutable();
        this.indexList_.set(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setIndexList(int index, AppIndex.Builder builderForValue) {
        ensureIndexListIsMutable();
        this.indexList_.set(index, builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addIndexList(AppIndex value) {
        Objects.requireNonNull(value);
        ensureIndexListIsMutable();
        this.indexList_.add(value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addIndexList(int index, AppIndex value) {
        Objects.requireNonNull(value);
        ensureIndexListIsMutable();
        this.indexList_.add(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addIndexList(AppIndex.Builder builderForValue) {
        ensureIndexListIsMutable();
        this.indexList_.add(builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addIndexList(int index, AppIndex.Builder builderForValue) {
        ensureIndexListIsMutable();
        this.indexList_.add(index, builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllIndexList(Iterable<? extends AppIndex> values) {
        ensureIndexListIsMutable();
        AbstractMessageLite.addAll((Iterable) values, (List) this.indexList_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearIndexList() {
        this.indexList_ = emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeIndexList(int index) {
        ensureIndexListIsMutable();
        this.indexList_.remove(index);
    }

    public static AppIndexList parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (AppIndexList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppIndexList parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppIndexList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppIndexList parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (AppIndexList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppIndexList parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppIndexList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppIndexList parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (AppIndexList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppIndexList parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppIndexList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppIndexList parseFrom(InputStream input) throws IOException {
        return (AppIndexList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppIndexList parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppIndexList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppIndexList parseDelimitedFrom(InputStream input) throws IOException {
        return (AppIndexList) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static AppIndexList parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppIndexList) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppIndexList parseFrom(CodedInputStream input) throws IOException {
        return (AppIndexList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppIndexList parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppIndexList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(AppIndexList prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<AppIndexList, Builder> implements AppIndexListOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(AppIndexList.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.AppIndexListOrBuilder
        public List<AppIndex> getIndexListList() {
            return Collections.unmodifiableList(((AppIndexList) this.instance).getIndexListList());
        }

        @Override // com.xiaopeng.appstore.protobuf.AppIndexListOrBuilder
        public int getIndexListCount() {
            return ((AppIndexList) this.instance).getIndexListCount();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppIndexListOrBuilder
        public AppIndex getIndexList(int index) {
            return ((AppIndexList) this.instance).getIndexList(index);
        }

        public Builder setIndexList(int index, AppIndex value) {
            copyOnWrite();
            ((AppIndexList) this.instance).setIndexList(index, value);
            return this;
        }

        public Builder setIndexList(int index, AppIndex.Builder builderForValue) {
            copyOnWrite();
            ((AppIndexList) this.instance).setIndexList(index, builderForValue);
            return this;
        }

        public Builder addIndexList(AppIndex value) {
            copyOnWrite();
            ((AppIndexList) this.instance).addIndexList(value);
            return this;
        }

        public Builder addIndexList(int index, AppIndex value) {
            copyOnWrite();
            ((AppIndexList) this.instance).addIndexList(index, value);
            return this;
        }

        public Builder addIndexList(AppIndex.Builder builderForValue) {
            copyOnWrite();
            ((AppIndexList) this.instance).addIndexList(builderForValue);
            return this;
        }

        public Builder addIndexList(int index, AppIndex.Builder builderForValue) {
            copyOnWrite();
            ((AppIndexList) this.instance).addIndexList(index, builderForValue);
            return this;
        }

        public Builder addAllIndexList(Iterable<? extends AppIndex> values) {
            copyOnWrite();
            ((AppIndexList) this.instance).addAllIndexList(values);
            return this;
        }

        public Builder clearIndexList() {
            copyOnWrite();
            ((AppIndexList) this.instance).clearIndexList();
            return this;
        }

        public Builder removeIndexList(int index) {
            copyOnWrite();
            ((AppIndexList) this.instance).removeIndexList(index);
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.AppIndexList$1  reason: invalid class name */
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
                return new AppIndexList();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"indexList_", AppIndex.class});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<AppIndexList> parser = PARSER;
                if (parser == null) {
                    synchronized (AppIndexList.class) {
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
        AppIndexList appIndexList = new AppIndexList();
        DEFAULT_INSTANCE = appIndexList;
        GeneratedMessageLite.registerDefaultInstance(AppIndexList.class, appIndexList);
    }

    public static AppIndexList getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AppIndexList> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
