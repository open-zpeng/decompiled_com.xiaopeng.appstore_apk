package com.xiaopeng.appstore.unity_plugin;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
/* loaded from: classes2.dex */
public interface IAppListListener extends IInterface {

    /* loaded from: classes2.dex */
    public static class Default implements IAppListListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppListListener
        public void onAppChanged(int position, int count) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppListListener
        public void onAppInserted(int position, int count) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppListListener
        public void onAppMoved(int fromPosition, int toPosition) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppListListener
        public void onAppRemoved(int position, int count) throws RemoteException {
        }
    }

    void onAppChanged(int position, int count) throws RemoteException;

    void onAppInserted(int position, int count) throws RemoteException;

    void onAppMoved(int fromPosition, int toPosition) throws RemoteException;

    void onAppRemoved(int position, int count) throws RemoteException;

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements IAppListListener {
        private static final String DESCRIPTOR = "com.xiaopeng.appstore.unity_plugin.IAppListListener";
        static final int TRANSACTION_onAppChanged = 4;
        static final int TRANSACTION_onAppInserted = 1;
        static final int TRANSACTION_onAppMoved = 3;
        static final int TRANSACTION_onAppRemoved = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAppListListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface queryLocalInterface = obj.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAppListListener)) {
                return (IAppListListener) queryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1) {
                data.enforceInterface(DESCRIPTOR);
                onAppInserted(data.readInt(), data.readInt());
                reply.writeNoException();
                return true;
            } else if (code == 2) {
                data.enforceInterface(DESCRIPTOR);
                onAppRemoved(data.readInt(), data.readInt());
                reply.writeNoException();
                return true;
            } else if (code == 3) {
                data.enforceInterface(DESCRIPTOR);
                onAppMoved(data.readInt(), data.readInt());
                reply.writeNoException();
                return true;
            } else if (code != 4) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            } else {
                data.enforceInterface(DESCRIPTOR);
                onAppChanged(data.readInt(), data.readInt());
                reply.writeNoException();
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static class Proxy implements IAppListListener {
            public static IAppListListener sDefaultImpl;
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

            @Override // com.xiaopeng.appstore.unity_plugin.IAppListListener
            public void onAppInserted(int position, int count) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(position);
                    obtain.writeInt(count);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAppInserted(position, count);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppListListener
            public void onAppRemoved(int position, int count) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(position);
                    obtain.writeInt(count);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAppRemoved(position, count);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppListListener
            public void onAppMoved(int fromPosition, int toPosition) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(fromPosition);
                    obtain.writeInt(toPosition);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAppMoved(fromPosition, toPosition);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppListListener
            public void onAppChanged(int position, int count) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(position);
                    obtain.writeInt(count);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAppChanged(position, count);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAppListListener impl) {
            if (Proxy.sDefaultImpl == null) {
                if (impl != null) {
                    Proxy.sDefaultImpl = impl;
                    return true;
                }
                return false;
            }
            throw new IllegalStateException("setDefaultImpl() called twice");
        }

        public static IAppListListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
