package com.xiaopeng.appstore.unity_plugin;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
/* loaded from: classes2.dex */
public interface IUsbEntryListener extends IInterface {

    /* loaded from: classes2.dex */
    public static class Default implements IUsbEntryListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IUsbEntryListener
        public void onHideUsbEntry() throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IUsbEntryListener
        public void onShowUsbEntry(boolean isLoading) throws RemoteException {
        }
    }

    void onHideUsbEntry() throws RemoteException;

    void onShowUsbEntry(boolean isLoading) throws RemoteException;

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements IUsbEntryListener {
        private static final String DESCRIPTOR = "com.xiaopeng.appstore.unity_plugin.IUsbEntryListener";
        static final int TRANSACTION_onHideUsbEntry = 2;
        static final int TRANSACTION_onShowUsbEntry = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUsbEntryListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface queryLocalInterface = obj.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUsbEntryListener)) {
                return (IUsbEntryListener) queryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1) {
                data.enforceInterface(DESCRIPTOR);
                onShowUsbEntry(data.readInt() != 0);
                reply.writeNoException();
                return true;
            } else if (code != 2) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            } else {
                data.enforceInterface(DESCRIPTOR);
                onHideUsbEntry();
                reply.writeNoException();
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static class Proxy implements IUsbEntryListener {
            public static IUsbEntryListener sDefaultImpl;
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IUsbEntryListener
            public void onShowUsbEntry(boolean isLoading) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(isLoading ? 1 : 0);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onShowUsbEntry(isLoading);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IUsbEntryListener
            public void onHideUsbEntry() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onHideUsbEntry();
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IUsbEntryListener impl) {
            if (Proxy.sDefaultImpl == null) {
                if (impl != null) {
                    Proxy.sDefaultImpl = impl;
                    return true;
                }
                return false;
            }
            throw new IllegalStateException("setDefaultImpl() called twice");
        }

        public static IUsbEntryListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
