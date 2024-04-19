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
public final class UsbAppListProto extends GeneratedMessageLite<UsbAppListProto, Builder> implements UsbAppListProtoOrBuilder {
    private static final UsbAppListProto DEFAULT_INSTANCE;
    private static volatile Parser<UsbAppListProto> PARSER = null;
    public static final int USBAPPLIST_FIELD_NUMBER = 1;
    private Internal.ProtobufList<AppItemProto> usbAppList_ = emptyProtobufList();

    private UsbAppListProto() {
    }

    @Override // com.xiaopeng.appstore.protobuf.UsbAppListProtoOrBuilder
    public List<AppItemProto> getUsbAppListList() {
        return this.usbAppList_;
    }

    public List<? extends AppItemProtoOrBuilder> getUsbAppListOrBuilderList() {
        return this.usbAppList_;
    }

    @Override // com.xiaopeng.appstore.protobuf.UsbAppListProtoOrBuilder
    public int getUsbAppListCount() {
        return this.usbAppList_.size();
    }

    @Override // com.xiaopeng.appstore.protobuf.UsbAppListProtoOrBuilder
    public AppItemProto getUsbAppList(int index) {
        return this.usbAppList_.get(index);
    }

    public AppItemProtoOrBuilder getUsbAppListOrBuilder(int index) {
        return this.usbAppList_.get(index);
    }

    private void ensureUsbAppListIsMutable() {
        if (this.usbAppList_.isModifiable()) {
            return;
        }
        this.usbAppList_ = GeneratedMessageLite.mutableCopy(this.usbAppList_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUsbAppList(int index, AppItemProto value) {
        Objects.requireNonNull(value);
        ensureUsbAppListIsMutable();
        this.usbAppList_.set(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUsbAppList(int index, AppItemProto.Builder builderForValue) {
        ensureUsbAppListIsMutable();
        this.usbAppList_.set(index, builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addUsbAppList(AppItemProto value) {
        Objects.requireNonNull(value);
        ensureUsbAppListIsMutable();
        this.usbAppList_.add(value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addUsbAppList(int index, AppItemProto value) {
        Objects.requireNonNull(value);
        ensureUsbAppListIsMutable();
        this.usbAppList_.add(index, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addUsbAppList(AppItemProto.Builder builderForValue) {
        ensureUsbAppListIsMutable();
        this.usbAppList_.add(builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addUsbAppList(int index, AppItemProto.Builder builderForValue) {
        ensureUsbAppListIsMutable();
        this.usbAppList_.add(index, builderForValue.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllUsbAppList(Iterable<? extends AppItemProto> values) {
        ensureUsbAppListIsMutable();
        AbstractMessageLite.addAll((Iterable) values, (List) this.usbAppList_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearUsbAppList() {
        this.usbAppList_ = emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeUsbAppList(int index) {
        ensureUsbAppListIsMutable();
        this.usbAppList_.remove(index);
    }

    public static UsbAppListProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (UsbAppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static UsbAppListProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (UsbAppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static UsbAppListProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (UsbAppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static UsbAppListProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (UsbAppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static UsbAppListProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (UsbAppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static UsbAppListProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (UsbAppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static UsbAppListProto parseFrom(InputStream input) throws IOException {
        return (UsbAppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static UsbAppListProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (UsbAppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static UsbAppListProto parseDelimitedFrom(InputStream input) throws IOException {
        return (UsbAppListProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static UsbAppListProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (UsbAppListProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static UsbAppListProto parseFrom(CodedInputStream input) throws IOException {
        return (UsbAppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static UsbAppListProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (UsbAppListProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(UsbAppListProto prototype) {
        return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<UsbAppListProto, Builder> implements UsbAppListProtoOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(UsbAppListProto.DEFAULT_INSTANCE);
        }

        @Override // com.xiaopeng.appstore.protobuf.UsbAppListProtoOrBuilder
        public List<AppItemProto> getUsbAppListList() {
            return Collections.unmodifiableList(((UsbAppListProto) this.instance).getUsbAppListList());
        }

        @Override // com.xiaopeng.appstore.protobuf.UsbAppListProtoOrBuilder
        public int getUsbAppListCount() {
            return ((UsbAppListProto) this.instance).getUsbAppListCount();
        }

        @Override // com.xiaopeng.appstore.protobuf.UsbAppListProtoOrBuilder
        public AppItemProto getUsbAppList(int index) {
            return ((UsbAppListProto) this.instance).getUsbAppList(index);
        }

        public Builder setUsbAppList(int index, AppItemProto value) {
            copyOnWrite();
            ((UsbAppListProto) this.instance).setUsbAppList(index, value);
            return this;
        }

        public Builder setUsbAppList(int index, AppItemProto.Builder builderForValue) {
            copyOnWrite();
            ((UsbAppListProto) this.instance).setUsbAppList(index, builderForValue);
            return this;
        }

        public Builder addUsbAppList(AppItemProto value) {
            copyOnWrite();
            ((UsbAppListProto) this.instance).addUsbAppList(value);
            return this;
        }

        public Builder addUsbAppList(int index, AppItemProto value) {
            copyOnWrite();
            ((UsbAppListProto) this.instance).addUsbAppList(index, value);
            return this;
        }

        public Builder addUsbAppList(AppItemProto.Builder builderForValue) {
            copyOnWrite();
            ((UsbAppListProto) this.instance).addUsbAppList(builderForValue);
            return this;
        }

        public Builder addUsbAppList(int index, AppItemProto.Builder builderForValue) {
            copyOnWrite();
            ((UsbAppListProto) this.instance).addUsbAppList(index, builderForValue);
            return this;
        }

        public Builder addAllUsbAppList(Iterable<? extends AppItemProto> values) {
            copyOnWrite();
            ((UsbAppListProto) this.instance).addAllUsbAppList(values);
            return this;
        }

        public Builder clearUsbAppList() {
            copyOnWrite();
            ((UsbAppListProto) this.instance).clearUsbAppList();
            return this;
        }

        public Builder removeUsbAppList(int index) {
            copyOnWrite();
            ((UsbAppListProto) this.instance).removeUsbAppList(index);
            return this;
        }
    }

    /* renamed from: com.xiaopeng.appstore.protobuf.UsbAppListProto$1  reason: invalid class name */
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
                return new UsbAppListProto();
            case 2:
                return new Builder(null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"usbAppList_", AppItemProto.class});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<UsbAppListProto> parser = PARSER;
                if (parser == null) {
                    synchronized (UsbAppListProto.class) {
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
        UsbAppListProto usbAppListProto = new UsbAppListProto();
        DEFAULT_INSTANCE = usbAppListProto;
        GeneratedMessageLite.registerDefaultInstance(UsbAppListProto.class, usbAppListProto);
    }

    public static UsbAppListProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<UsbAppListProto> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
