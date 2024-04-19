package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.xiaopeng.appstore.protobuf.AppItemProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
/* loaded from: classes2.dex */
public final class AppletGroupProto extends GeneratedMessageLite<AppletGroupProto, Builder> implements AppletGroupProtoOrBuilder {
    public static final int APPLETLIST_FIELD_NUMBER = 2;
    private static final AppletGroupProto DEFAULT_INSTANCE;
    public static final int GROUP_FIELD_NUMBER = 1;
    private static volatile Parser<AppletGroupProto> PARSER;
    private String group_ = "";
    private Internal.ProtobufList<AppItemProto> appletList_ = emptyProtobufList();

    private AppletGroupProto() {
    }

    @Override // com.xiaopeng.appstore.protobuf.AppletGroupProtoOrBuilder
    public String getGroup() {
        return this.group_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppletGroupProtoOrBuilder
    public ByteString getGroupBytes() {
        return ByteString.copyFromUtf8(this.group_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setGroup(String value) {
        Objects.requireNonNull(value);
        this.group_ = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearGroup() {
        this.group_ = getDefaultInstance().getGroup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setGroupBytes(ByteString value) {
        Objects.requireNonNull(value);
        checkByteStringIsUtf8(value);
        this.group_ = value.toStringUtf8();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppletGroupProtoOrBuilder
    public List<AppItemProto> getAppletListList() {
        return this.appletList_;
    }

    public List<? extends AppItemProtoOrBuilder> getAppletListOrBuilderList() {
        return this.appletList_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppletGroupProtoOrBuilder
    public int getAppletListCount() {
        return this.appletList_.size();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppletGroupProtoOrBuilder
    public AppItemProto getAppletList(int index) {
        return this.appletList_.get(index);
    }

    public AppItemProtoOrBuilder getAppletListOrBuilder(int index) {
        return this.appletList_.get(index);
    }

    private void ensureAppletListIsMutable() {
        if (this.appletList_.isModifiable()) {
            return;
        }
        this.appletList_ = GeneratedMessageLite.mutableCopy(this.appletList_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAppletList(int index, AppItemProto value) {
        Objects.requireNonNull(value);
        ensureAppletListIsMutable();
        this.appletList_.set(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAppletList(int index, AppItemProto.Builder builderForValue) {
        ensureAppletListIsMutable();
        this.appletList_.set(index, builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAppletList(AppItemProto value) {
        Objects.requireNonNull(value);
        ensureAppletListIsMutable();
        this.appletList_.add(value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAppletList(int index, AppItemProto value) {
        Objects.requireNonNull(value);
        ensureAppletListIsMutable();
        this.appletList_.add(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAppletList(AppItemProto.Builder builderForValue) {
        ensureAppletListIsMutable();
        this.appletList_.add(builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAppletList(int index, AppItemProto.Builder builderForValue) {
        ensureAppletListIsMutable();
        this.appletList_.add(index, builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllAppletList(Iterable<? extends AppItemProto> values) {
        ensureAppletListIsMutable();
        AbstractMessageLite.addAll((Iterable) values, (List) this.appletList_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAppletList() {
        this.appletList_ = emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeAppletList(int index) {
        ensureAppletListIsMutable();
        this.appletList_.remove(index);
    }

    public static AppletGroupProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (AppletGroupProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppletGroupProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppletGroupProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppletGroupProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (AppletGroupProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppletGroupProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppletGroupProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppletGroupProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (AppletGroupProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppletGroupProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppletGroupProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppletGroupProto parseFrom(InputStream input) throws IOException {
        return (AppletGroupProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppletGroupProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppletGroupProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppletGroupProto parseDelimitedFrom(InputStream input) throws IOException {
        return (AppletGroupProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static AppletGroupProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppletGroupProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppletGroupProto parseFrom(CodedInputStream input) throws IOException {
        return (AppletGroupProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppletGroupProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppletGroupProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(AppletGroupProto prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<AppletGroupProto, Builder> implements AppletGroupProtoOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(AppletGroupProto.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.AppletGroupProtoOrBuilder
        public String getGroup() {
            return ((AppletGroupProto) this.instance).getGroup();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppletGroupProtoOrBuilder
        public ByteString getGroupBytes() {
            return ((AppletGroupProto) this.instance).getGroupBytes();
        }

        public Builder setGroup(String value) {
            copyOnWrite();
            ((AppletGroupProto) this.instance).setGroup(value);
            return this;
        }

        public Builder clearGroup() {
            copyOnWrite();
            ((AppletGroupProto) this.instance).clearGroup();
            return this;
        }

        public Builder setGroupBytes(ByteString value) {
            copyOnWrite();
            ((AppletGroupProto) this.instance).setGroupBytes(value);
            return this;
        }

        @Override // com.xiaopeng.appstore.protobuf.AppletGroupProtoOrBuilder
        public List<AppItemProto> getAppletListList() {
            return Collections.unmodifiableList(((AppletGroupProto) this.instance).getAppletListList());
        }

        @Override // com.xiaopeng.appstore.protobuf.AppletGroupProtoOrBuilder
        public int getAppletListCount() {
            return ((AppletGroupProto) this.instance).getAppletListCount();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppletGroupProtoOrBuilder
        public AppItemProto getAppletList(int index) {
            return ((AppletGroupProto) this.instance).getAppletList(index);
        }

        public Builder setAppletList(int index, AppItemProto value) {
            copyOnWrite();
            ((AppletGroupProto) this.instance).setAppletList(index, value);
            return this;
        }

        public Builder setAppletList(int index, AppItemProto.Builder builderForValue) {
            copyOnWrite();
            ((AppletGroupProto) this.instance).setAppletList(index, builderForValue);
            return this;
        }

        public Builder addAppletList(AppItemProto value) {
            copyOnWrite();
            ((AppletGroupProto) this.instance).addAppletList(value);
            return this;
        }

        public Builder addAppletList(int index, AppItemProto value) {
            copyOnWrite();
            ((AppletGroupProto) this.instance).addAppletList(index, value);
            return this;
        }

        public Builder addAppletList(AppItemProto.Builder builderForValue) {
            copyOnWrite();
            ((AppletGroupProto) this.instance).addAppletList(builderForValue);
            return this;
        }

        public Builder addAppletList(int index, AppItemProto.Builder builderForValue) {
            copyOnWrite();
            ((AppletGroupProto) this.instance).addAppletList(index, builderForValue);
            return this;
        }

        public Builder addAllAppletList(Iterable<? extends AppItemProto> values) {
            copyOnWrite();
            ((AppletGroupProto) this.instance).addAllAppletList(values);
            return this;
        }

        public Builder clearAppletList() {
            copyOnWrite();
            ((AppletGroupProto) this.instance).clearAppletList();
            return this;
        }

        public Builder removeAppletList(int index) {
            copyOnWrite();
            ((AppletGroupProto) this.instance).removeAppletList(index);
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.AppletGroupProto$1  reason: invalid class name */
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
                return new AppletGroupProto();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0001\u0000\u0001Ȉ\u0002\u001b", new Object[]{"group_", "appletList_", AppItemProto.class});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<AppletGroupProto> parser = PARSER;
                if (parser == null) {
                    synchronized (AppletGroupProto.class) {
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
        AppletGroupProto appletGroupProto = new AppletGroupProto();
        DEFAULT_INSTANCE = appletGroupProto;
        GeneratedMessageLite.registerDefaultInstance(AppletGroupProto.class, appletGroupProto);
    }

    public static AppletGroupProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AppletGroupProto> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
