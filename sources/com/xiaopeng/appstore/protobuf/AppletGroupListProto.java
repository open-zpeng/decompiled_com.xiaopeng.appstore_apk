package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.xiaopeng.appstore.protobuf.AppletGroupProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
/* loaded from: classes2.dex */
public final class AppletGroupListProto extends GeneratedMessageLite<AppletGroupListProto, Builder> implements AppletGroupListProtoOrBuilder {
    public static final int APPLETGROUPLIST_FIELD_NUMBER = 1;
    private static final AppletGroupListProto DEFAULT_INSTANCE;
    private static volatile Parser<AppletGroupListProto> PARSER;
    private Internal.ProtobufList<AppletGroupProto> appletGroupList_ = emptyProtobufList();

    private AppletGroupListProto() {
    }

    @Override // com.xiaopeng.appstore.protobuf.AppletGroupListProtoOrBuilder
    public List<AppletGroupProto> getAppletGroupListList() {
        return this.appletGroupList_;
    }

    public List<? extends AppletGroupProtoOrBuilder> getAppletGroupListOrBuilderList() {
        return this.appletGroupList_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppletGroupListProtoOrBuilder
    public int getAppletGroupListCount() {
        return this.appletGroupList_.size();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppletGroupListProtoOrBuilder
    public AppletGroupProto getAppletGroupList(int index) {
        return this.appletGroupList_.get(index);
    }

    public AppletGroupProtoOrBuilder getAppletGroupListOrBuilder(int index) {
        return this.appletGroupList_.get(index);
    }

    private void ensureAppletGroupListIsMutable() {
        if (this.appletGroupList_.isModifiable()) {
            return;
        }
        this.appletGroupList_ = GeneratedMessageLite.mutableCopy(this.appletGroupList_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAppletGroupList(int index, AppletGroupProto value) {
        Objects.requireNonNull(value);
        ensureAppletGroupListIsMutable();
        this.appletGroupList_.set(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAppletGroupList(int index, AppletGroupProto.Builder builderForValue) {
        ensureAppletGroupListIsMutable();
        this.appletGroupList_.set(index, builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAppletGroupList(AppletGroupProto value) {
        Objects.requireNonNull(value);
        ensureAppletGroupListIsMutable();
        this.appletGroupList_.add(value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAppletGroupList(int index, AppletGroupProto value) {
        Objects.requireNonNull(value);
        ensureAppletGroupListIsMutable();
        this.appletGroupList_.add(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAppletGroupList(AppletGroupProto.Builder builderForValue) {
        ensureAppletGroupListIsMutable();
        this.appletGroupList_.add(builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAppletGroupList(int index, AppletGroupProto.Builder builderForValue) {
        ensureAppletGroupListIsMutable();
        this.appletGroupList_.add(index, builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllAppletGroupList(Iterable<? extends AppletGroupProto> values) {
        ensureAppletGroupListIsMutable();
        AbstractMessageLite.addAll((Iterable) values, (List) this.appletGroupList_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAppletGroupList() {
        this.appletGroupList_ = emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeAppletGroupList(int index) {
        ensureAppletGroupListIsMutable();
        this.appletGroupList_.remove(index);
    }

    public static AppletGroupListProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (AppletGroupListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppletGroupListProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppletGroupListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppletGroupListProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (AppletGroupListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppletGroupListProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppletGroupListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppletGroupListProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (AppletGroupListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppletGroupListProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppletGroupListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppletGroupListProto parseFrom(InputStream input) throws IOException {
        return (AppletGroupListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppletGroupListProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppletGroupListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppletGroupListProto parseDelimitedFrom(InputStream input) throws IOException {
        return (AppletGroupListProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static AppletGroupListProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppletGroupListProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppletGroupListProto parseFrom(CodedInputStream input) throws IOException {
        return (AppletGroupListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppletGroupListProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppletGroupListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(AppletGroupListProto prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<AppletGroupListProto, Builder> implements AppletGroupListProtoOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(AppletGroupListProto.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.AppletGroupListProtoOrBuilder
        public List<AppletGroupProto> getAppletGroupListList() {
            return Collections.unmodifiableList(((AppletGroupListProto) this.instance).getAppletGroupListList());
        }

        @Override // com.xiaopeng.appstore.protobuf.AppletGroupListProtoOrBuilder
        public int getAppletGroupListCount() {
            return ((AppletGroupListProto) this.instance).getAppletGroupListCount();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppletGroupListProtoOrBuilder
        public AppletGroupProto getAppletGroupList(int index) {
            return ((AppletGroupListProto) this.instance).getAppletGroupList(index);
        }

        public Builder setAppletGroupList(int index, AppletGroupProto value) {
            copyOnWrite();
            ((AppletGroupListProto) this.instance).setAppletGroupList(index, value);
            return this;
        }

        public Builder setAppletGroupList(int index, AppletGroupProto.Builder builderForValue) {
            copyOnWrite();
            ((AppletGroupListProto) this.instance).setAppletGroupList(index, builderForValue);
            return this;
        }

        public Builder addAppletGroupList(AppletGroupProto value) {
            copyOnWrite();
            ((AppletGroupListProto) this.instance).addAppletGroupList(value);
            return this;
        }

        public Builder addAppletGroupList(int index, AppletGroupProto value) {
            copyOnWrite();
            ((AppletGroupListProto) this.instance).addAppletGroupList(index, value);
            return this;
        }

        public Builder addAppletGroupList(AppletGroupProto.Builder builderForValue) {
            copyOnWrite();
            ((AppletGroupListProto) this.instance).addAppletGroupList(builderForValue);
            return this;
        }

        public Builder addAppletGroupList(int index, AppletGroupProto.Builder builderForValue) {
            copyOnWrite();
            ((AppletGroupListProto) this.instance).addAppletGroupList(index, builderForValue);
            return this;
        }

        public Builder addAllAppletGroupList(Iterable<? extends AppletGroupProto> values) {
            copyOnWrite();
            ((AppletGroupListProto) this.instance).addAllAppletGroupList(values);
            return this;
        }

        public Builder clearAppletGroupList() {
            copyOnWrite();
            ((AppletGroupListProto) this.instance).clearAppletGroupList();
            return this;
        }

        public Builder removeAppletGroupList(int index) {
            copyOnWrite();
            ((AppletGroupListProto) this.instance).removeAppletGroupList(index);
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.AppletGroupListProto$1  reason: invalid class name */
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
                return new AppletGroupListProto();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"appletGroupList_", AppletGroupProto.class});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<AppletGroupListProto> parser = PARSER;
                if (parser == null) {
                    synchronized (AppletGroupListProto.class) {
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
        AppletGroupListProto appletGroupListProto = new AppletGroupListProto();
        DEFAULT_INSTANCE = appletGroupListProto;
        GeneratedMessageLite.registerDefaultInstance(AppletGroupListProto.class, appletGroupListProto);
    }

    public static AppletGroupListProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AppletGroupListProto> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
