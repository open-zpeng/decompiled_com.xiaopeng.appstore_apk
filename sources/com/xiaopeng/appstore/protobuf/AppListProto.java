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
public final class AppListProto extends GeneratedMessageLite<AppListProto, Builder> implements AppListProtoOrBuilder {
    public static final int APPITEM_FIELD_NUMBER = 1;
    private static final AppListProto DEFAULT_INSTANCE;
    private static volatile Parser<AppListProto> PARSER;
    private Internal.ProtobufList<AppItemProto> appItem_ = emptyProtobufList();

    private AppListProto() {
    }

    @Override // com.xiaopeng.appstore.protobuf.AppListProtoOrBuilder
    public List<AppItemProto> getAppItemList() {
        return this.appItem_;
    }

    public List<? extends AppItemProtoOrBuilder> getAppItemOrBuilderList() {
        return this.appItem_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppListProtoOrBuilder
    public int getAppItemCount() {
        return this.appItem_.size();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppListProtoOrBuilder
    public AppItemProto getAppItem(int index) {
        return this.appItem_.get(index);
    }

    public AppItemProtoOrBuilder getAppItemOrBuilder(int index) {
        return this.appItem_.get(index);
    }

    private void ensureAppItemIsMutable() {
        if (this.appItem_.isModifiable()) {
            return;
        }
        this.appItem_ = GeneratedMessageLite.mutableCopy(this.appItem_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAppItem(int index, AppItemProto value) {
        Objects.requireNonNull(value);
        ensureAppItemIsMutable();
        this.appItem_.set(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAppItem(int index, AppItemProto.Builder builderForValue) {
        ensureAppItemIsMutable();
        this.appItem_.set(index, builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAppItem(AppItemProto value) {
        Objects.requireNonNull(value);
        ensureAppItemIsMutable();
        this.appItem_.add(value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAppItem(int index, AppItemProto value) {
        Objects.requireNonNull(value);
        ensureAppItemIsMutable();
        this.appItem_.add(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAppItem(AppItemProto.Builder builderForValue) {
        ensureAppItemIsMutable();
        this.appItem_.add(builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAppItem(int index, AppItemProto.Builder builderForValue) {
        ensureAppItemIsMutable();
        this.appItem_.add(index, builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllAppItem(Iterable<? extends AppItemProto> values) {
        ensureAppItemIsMutable();
        AbstractMessageLite.addAll((Iterable) values, (List) this.appItem_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAppItem() {
        this.appItem_ = emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeAppItem(int index) {
        ensureAppItemIsMutable();
        this.appItem_.remove(index);
    }

    public static AppListProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (AppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppListProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppListProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (AppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppListProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppListProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (AppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppListProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppListProto parseFrom(InputStream input) throws IOException {
        return (AppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppListProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppListProto parseDelimitedFrom(InputStream input) throws IOException {
        return (AppListProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static AppListProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppListProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppListProto parseFrom(CodedInputStream input) throws IOException {
        return (AppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppListProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(AppListProto prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<AppListProto, Builder> implements AppListProtoOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(AppListProto.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.AppListProtoOrBuilder
        public List<AppItemProto> getAppItemList() {
            return Collections.unmodifiableList(((AppListProto) this.instance).getAppItemList());
        }

        @Override // com.xiaopeng.appstore.protobuf.AppListProtoOrBuilder
        public int getAppItemCount() {
            return ((AppListProto) this.instance).getAppItemCount();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppListProtoOrBuilder
        public AppItemProto getAppItem(int index) {
            return ((AppListProto) this.instance).getAppItem(index);
        }

        public Builder setAppItem(int index, AppItemProto value) {
            copyOnWrite();
            ((AppListProto) this.instance).setAppItem(index, value);
            return this;
        }

        public Builder setAppItem(int index, AppItemProto.Builder builderForValue) {
            copyOnWrite();
            ((AppListProto) this.instance).setAppItem(index, builderForValue);
            return this;
        }

        public Builder addAppItem(AppItemProto value) {
            copyOnWrite();
            ((AppListProto) this.instance).addAppItem(value);
            return this;
        }

        public Builder addAppItem(int index, AppItemProto value) {
            copyOnWrite();
            ((AppListProto) this.instance).addAppItem(index, value);
            return this;
        }

        public Builder addAppItem(AppItemProto.Builder builderForValue) {
            copyOnWrite();
            ((AppListProto) this.instance).addAppItem(builderForValue);
            return this;
        }

        public Builder addAppItem(int index, AppItemProto.Builder builderForValue) {
            copyOnWrite();
            ((AppListProto) this.instance).addAppItem(index, builderForValue);
            return this;
        }

        public Builder addAllAppItem(Iterable<? extends AppItemProto> values) {
            copyOnWrite();
            ((AppListProto) this.instance).addAllAppItem(values);
            return this;
        }

        public Builder clearAppItem() {
            copyOnWrite();
            ((AppListProto) this.instance).clearAppItem();
            return this;
        }

        public Builder removeAppItem(int index) {
            copyOnWrite();
            ((AppListProto) this.instance).removeAppItem(index);
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.AppListProto$1  reason: invalid class name */
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
                return new AppListProto();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"appItem_", AppItemProto.class});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<AppListProto> parser = PARSER;
                if (parser == null) {
                    synchronized (AppListProto.class) {
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
        AppListProto appListProto = new AppListProto();
        DEFAULT_INSTANCE = appListProto;
        GeneratedMessageLite.registerDefaultInstance(AppListProto.class, appListProto);
    }

    public static AppListProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AppListProto> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
