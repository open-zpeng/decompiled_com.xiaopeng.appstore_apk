package com.xiaopeng.appstore.protobuf;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.xiaopeng.appstore.protobuf.AppDetailDataProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
/* loaded from: classes2.dex */
public final class AppUpdateDataProto extends GeneratedMessageLite<AppUpdateDataProto, Builder> implements AppUpdateDataProtoOrBuilder {
    public static final int APPS_FIELD_NUMBER = 1;
    private static final AppUpdateDataProto DEFAULT_INSTANCE;
    private static volatile Parser<AppUpdateDataProto> PARSER;
    private Internal.ProtobufList<AppDetailDataProto> apps_ = emptyProtobufList();

    private AppUpdateDataProto() {
    }

    @Override // com.xiaopeng.appstore.protobuf.AppUpdateDataProtoOrBuilder
    public List<AppDetailDataProto> getAppsList() {
        return this.apps_;
    }

    public List<? extends AppDetailDataProtoOrBuilder> getAppsOrBuilderList() {
        return this.apps_;
    }

    @Override // com.xiaopeng.appstore.protobuf.AppUpdateDataProtoOrBuilder
    public int getAppsCount() {
        return this.apps_.size();
    }

    @Override // com.xiaopeng.appstore.protobuf.AppUpdateDataProtoOrBuilder
    public AppDetailDataProto getApps(int index) {
        return this.apps_.get(index);
    }

    public AppDetailDataProtoOrBuilder getAppsOrBuilder(int index) {
        return this.apps_.get(index);
    }

    private void ensureAppsIsMutable() {
        if (this.apps_.isModifiable()) {
            return;
        }
        this.apps_ = GeneratedMessageLite.mutableCopy(this.apps_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setApps(int index, AppDetailDataProto value) {
        Objects.requireNonNull(value);
        ensureAppsIsMutable();
        this.apps_.set(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setApps(int index, AppDetailDataProto.Builder builderForValue) {
        ensureAppsIsMutable();
        this.apps_.set(index, builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addApps(AppDetailDataProto value) {
        Objects.requireNonNull(value);
        ensureAppsIsMutable();
        this.apps_.add(value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addApps(int index, AppDetailDataProto value) {
        Objects.requireNonNull(value);
        ensureAppsIsMutable();
        this.apps_.add(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addApps(AppDetailDataProto.Builder builderForValue) {
        ensureAppsIsMutable();
        this.apps_.add(builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addApps(int index, AppDetailDataProto.Builder builderForValue) {
        ensureAppsIsMutable();
        this.apps_.add(index, builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllApps(Iterable<? extends AppDetailDataProto> values) {
        ensureAppsIsMutable();
        AbstractMessageLite.addAll((Iterable) values, (List) this.apps_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearApps() {
        this.apps_ = emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeApps(int index) {
        ensureAppsIsMutable();
        this.apps_.remove(index);
    }

    public static AppUpdateDataProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (AppUpdateDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppUpdateDataProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppUpdateDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppUpdateDataProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (AppUpdateDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppUpdateDataProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppUpdateDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppUpdateDataProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (AppUpdateDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AppUpdateDataProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AppUpdateDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AppUpdateDataProto parseFrom(InputStream input) throws IOException {
        return (AppUpdateDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppUpdateDataProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppUpdateDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppUpdateDataProto parseDelimitedFrom(InputStream input) throws IOException {
        return (AppUpdateDataProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static AppUpdateDataProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppUpdateDataProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AppUpdateDataProto parseFrom(CodedInputStream input) throws IOException {
        return (AppUpdateDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AppUpdateDataProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AppUpdateDataProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(AppUpdateDataProto prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<AppUpdateDataProto, Builder> implements AppUpdateDataProtoOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(AppUpdateDataProto.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.AppUpdateDataProtoOrBuilder
        public List<AppDetailDataProto> getAppsList() {
            return Collections.unmodifiableList(((AppUpdateDataProto) this.instance).getAppsList());
        }

        @Override // com.xiaopeng.appstore.protobuf.AppUpdateDataProtoOrBuilder
        public int getAppsCount() {
            return ((AppUpdateDataProto) this.instance).getAppsCount();
        }

        @Override // com.xiaopeng.appstore.protobuf.AppUpdateDataProtoOrBuilder
        public AppDetailDataProto getApps(int index) {
            return ((AppUpdateDataProto) this.instance).getApps(index);
        }

        public Builder setApps(int index, AppDetailDataProto value) {
            copyOnWrite();
            ((AppUpdateDataProto) this.instance).setApps(index, value);
            return this;
        }

        public Builder setApps(int index, AppDetailDataProto.Builder builderForValue) {
            copyOnWrite();
            ((AppUpdateDataProto) this.instance).setApps(index, builderForValue);
            return this;
        }

        public Builder addApps(AppDetailDataProto value) {
            copyOnWrite();
            ((AppUpdateDataProto) this.instance).addApps(value);
            return this;
        }

        public Builder addApps(int index, AppDetailDataProto value) {
            copyOnWrite();
            ((AppUpdateDataProto) this.instance).addApps(index, value);
            return this;
        }

        public Builder addApps(AppDetailDataProto.Builder builderForValue) {
            copyOnWrite();
            ((AppUpdateDataProto) this.instance).addApps(builderForValue);
            return this;
        }

        public Builder addApps(int index, AppDetailDataProto.Builder builderForValue) {
            copyOnWrite();
            ((AppUpdateDataProto) this.instance).addApps(index, builderForValue);
            return this;
        }

        public Builder addAllApps(Iterable<? extends AppDetailDataProto> values) {
            copyOnWrite();
            ((AppUpdateDataProto) this.instance).addAllApps(values);
            return this;
        }

        public Builder clearApps() {
            copyOnWrite();
            ((AppUpdateDataProto) this.instance).clearApps();
            return this;
        }

        public Builder removeApps(int index) {
            copyOnWrite();
            ((AppUpdateDataProto) this.instance).removeApps(index);
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.AppUpdateDataProto$1  reason: invalid class name */
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
                return new AppUpdateDataProto();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"apps_", AppDetailDataProto.class});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<AppUpdateDataProto> parser = PARSER;
                if (parser == null) {
                    synchronized (AppUpdateDataProto.class) {
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
        AppUpdateDataProto appUpdateDataProto = new AppUpdateDataProto();
        DEFAULT_INSTANCE = appUpdateDataProto;
        GeneratedMessageLite.registerDefaultInstance(AppUpdateDataProto.class, appUpdateDataProto);
    }

    public static AppUpdateDataProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AppUpdateDataProto> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
