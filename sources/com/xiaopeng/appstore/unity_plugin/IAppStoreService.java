package com.xiaopeng.appstore.unity_plugin;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaopeng.appstore.unity_plugin.IAppListListener;
import com.xiaopeng.appstore.unity_plugin.IAppletListener;
import com.xiaopeng.appstore.unity_plugin.IUsbAppListener;
import com.xiaopeng.appstore.unity_plugin.IUsbEntryListener;
/* loaded from: classes2.dex */
public interface IAppStoreService extends IInterface {

    /* loaded from: classes2.dex */
    public static class Default implements IAppStoreService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void create() throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void destroy() throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void enableDebug(boolean debug) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public byte[] getAppDetailProto(String packageName) throws RemoteException {
            return null;
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public byte[] getAppListProto() throws RemoteException {
            return null;
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public int getHttpHost() throws RemoteException {
            return 0;
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public byte[] getStoreHomeProto() throws RemoteException {
            return null;
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public byte[] getUpdateProto() throws RemoteException {
            return null;
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public byte[] getUsbAppList() throws RemoteException {
            return null;
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void installUsbApp(String packageName) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public boolean isInDebug() throws RemoteException {
            return false;
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public boolean isInstalled(String packageName, boolean useCache) throws RemoteException {
            return false;
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public boolean isPrivacyAgreed() throws RemoteException {
            return false;
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public boolean launchApp(String packageName) throws RemoteException {
            return false;
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void launchApplet(String id, String name) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void loadAppletList() throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void persistAppOrder(byte[] appIndexList) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void registerAppListListener(IAppListListener listener) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void registerAppletListener(IAppletListener listener) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void registerUsbAppListener(IUsbAppListener listener) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void registerUsbEntryListener(IUsbEntryListener listener) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void resetHttpHost() throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void showPrivacyDialog(boolean onlyContent) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void start() throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void startLoadAppList() throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void startLoadUsbApp() throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void stop() throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void switchHttpPreHost() throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void switchHttpTestHost() throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public boolean uninstallApp(String packageName) throws RemoteException {
            return false;
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void unregisterAppListListener(IAppListListener listener) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void unregisterAppletListener(IAppletListener listener) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void unregisterUsbAppListener(IUsbAppListener listener) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
        public void unregisterUsbEntryListener(IUsbEntryListener listener) throws RemoteException {
        }
    }

    void create() throws RemoteException;

    void destroy() throws RemoteException;

    void enableDebug(boolean debug) throws RemoteException;

    byte[] getAppDetailProto(String packageName) throws RemoteException;

    byte[] getAppListProto() throws RemoteException;

    int getHttpHost() throws RemoteException;

    byte[] getStoreHomeProto() throws RemoteException;

    byte[] getUpdateProto() throws RemoteException;

    byte[] getUsbAppList() throws RemoteException;

    void installUsbApp(String packageName) throws RemoteException;

    boolean isInDebug() throws RemoteException;

    boolean isInstalled(String packageName, boolean useCache) throws RemoteException;

    boolean isPrivacyAgreed() throws RemoteException;

    boolean launchApp(String packageName) throws RemoteException;

    void launchApplet(String id, String name) throws RemoteException;

    void loadAppletList() throws RemoteException;

    void persistAppOrder(byte[] appIndexList) throws RemoteException;

    void registerAppListListener(IAppListListener listener) throws RemoteException;

    void registerAppletListener(IAppletListener listener) throws RemoteException;

    void registerUsbAppListener(IUsbAppListener listener) throws RemoteException;

    void registerUsbEntryListener(IUsbEntryListener listener) throws RemoteException;

    void resetHttpHost() throws RemoteException;

    void showPrivacyDialog(boolean onlyContent) throws RemoteException;

    void start() throws RemoteException;

    void startLoadAppList() throws RemoteException;

    void startLoadUsbApp() throws RemoteException;

    void stop() throws RemoteException;

    void switchHttpPreHost() throws RemoteException;

    void switchHttpTestHost() throws RemoteException;

    boolean uninstallApp(String packageName) throws RemoteException;

    void unregisterAppListListener(IAppListListener listener) throws RemoteException;

    void unregisterAppletListener(IAppletListener listener) throws RemoteException;

    void unregisterUsbAppListener(IUsbAppListener listener) throws RemoteException;

    void unregisterUsbEntryListener(IUsbEntryListener listener) throws RemoteException;

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements IAppStoreService {
        private static final String DESCRIPTOR = "com.xiaopeng.appstore.unity_plugin.IAppStoreService";
        static final int TRANSACTION_create = 1;
        static final int TRANSACTION_destroy = 4;
        static final int TRANSACTION_enableDebug = 6;
        static final int TRANSACTION_getAppDetailProto = 16;
        static final int TRANSACTION_getAppListProto = 12;
        static final int TRANSACTION_getHttpHost = 20;
        static final int TRANSACTION_getStoreHomeProto = 15;
        static final int TRANSACTION_getUpdateProto = 17;
        static final int TRANSACTION_getUsbAppList = 25;
        static final int TRANSACTION_installUsbApp = 26;
        static final int TRANSACTION_isInDebug = 5;
        static final int TRANSACTION_isInstalled = 13;
        static final int TRANSACTION_isPrivacyAgreed = 18;
        static final int TRANSACTION_launchApp = 9;
        static final int TRANSACTION_launchApplet = 32;
        static final int TRANSACTION_loadAppletList = 31;
        static final int TRANSACTION_persistAppOrder = 14;
        static final int TRANSACTION_registerAppListListener = 7;
        static final int TRANSACTION_registerAppletListener = 33;
        static final int TRANSACTION_registerUsbAppListener = 29;
        static final int TRANSACTION_registerUsbEntryListener = 27;
        static final int TRANSACTION_resetHttpHost = 23;
        static final int TRANSACTION_showPrivacyDialog = 19;
        static final int TRANSACTION_start = 2;
        static final int TRANSACTION_startLoadAppList = 11;
        static final int TRANSACTION_startLoadUsbApp = 24;
        static final int TRANSACTION_stop = 3;
        static final int TRANSACTION_switchHttpPreHost = 21;
        static final int TRANSACTION_switchHttpTestHost = 22;
        static final int TRANSACTION_uninstallApp = 10;
        static final int TRANSACTION_unregisterAppListListener = 8;
        static final int TRANSACTION_unregisterAppletListener = 34;
        static final int TRANSACTION_unregisterUsbAppListener = 30;
        static final int TRANSACTION_unregisterUsbEntryListener = 28;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAppStoreService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface queryLocalInterface = obj.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAppStoreService)) {
                return (IAppStoreService) queryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    create();
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    start();
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    stop();
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    destroy();
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isInDebug = isInDebug();
                    reply.writeNoException();
                    reply.writeInt(isInDebug ? 1 : 0);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    enableDebug(data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    registerAppListListener(IAppListListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    unregisterAppListListener(IAppListListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    boolean launchApp = launchApp(data.readString());
                    reply.writeNoException();
                    reply.writeInt(launchApp ? 1 : 0);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    boolean uninstallApp = uninstallApp(data.readString());
                    reply.writeNoException();
                    reply.writeInt(uninstallApp ? 1 : 0);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    startLoadAppList();
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] appListProto = getAppListProto();
                    reply.writeNoException();
                    reply.writeByteArray(appListProto);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isInstalled = isInstalled(data.readString(), data.readInt() != 0);
                    reply.writeNoException();
                    reply.writeInt(isInstalled ? 1 : 0);
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    persistAppOrder(data.createByteArray());
                    reply.writeNoException();
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] storeHomeProto = getStoreHomeProto();
                    reply.writeNoException();
                    reply.writeByteArray(storeHomeProto);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] appDetailProto = getAppDetailProto(data.readString());
                    reply.writeNoException();
                    reply.writeByteArray(appDetailProto);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] updateProto = getUpdateProto();
                    reply.writeNoException();
                    reply.writeByteArray(updateProto);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isPrivacyAgreed = isPrivacyAgreed();
                    reply.writeNoException();
                    reply.writeInt(isPrivacyAgreed ? 1 : 0);
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    showPrivacyDialog(data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    int httpHost = getHttpHost();
                    reply.writeNoException();
                    reply.writeInt(httpHost);
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    switchHttpPreHost();
                    reply.writeNoException();
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    switchHttpTestHost();
                    reply.writeNoException();
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    resetHttpHost();
                    reply.writeNoException();
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    startLoadUsbApp();
                    reply.writeNoException();
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] usbAppList = getUsbAppList();
                    reply.writeNoException();
                    reply.writeByteArray(usbAppList);
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    installUsbApp(data.readString());
                    reply.writeNoException();
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    registerUsbEntryListener(IUsbEntryListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 28:
                    data.enforceInterface(DESCRIPTOR);
                    unregisterUsbEntryListener(IUsbEntryListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 29:
                    data.enforceInterface(DESCRIPTOR);
                    registerUsbAppListener(IUsbAppListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 30:
                    data.enforceInterface(DESCRIPTOR);
                    unregisterUsbAppListener(IUsbAppListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 31:
                    data.enforceInterface(DESCRIPTOR);
                    loadAppletList();
                    reply.writeNoException();
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    launchApplet(data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case 33:
                    data.enforceInterface(DESCRIPTOR);
                    registerAppletListener(IAppletListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 34:
                    data.enforceInterface(DESCRIPTOR);
                    unregisterAppletListener(IAppletListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static class Proxy implements IAppStoreService {
            public static IAppStoreService sDefaultImpl;
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

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void create() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().create();
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void start() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().start();
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void stop() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stop();
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void destroy() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().destroy();
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public boolean isInDebug() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInDebug();
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void enableDebug(boolean debug) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(debug ? 1 : 0);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableDebug(debug);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void registerAppListListener(IAppListListener listener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerAppListListener(listener);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void unregisterAppListListener(IAppListListener listener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterAppListListener(listener);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public boolean launchApp(String packageName) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(packageName);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().launchApp(packageName);
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public boolean uninstallApp(String packageName) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(packageName);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().uninstallApp(packageName);
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void startLoadAppList() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startLoadAppList();
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public byte[] getAppListProto() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppListProto();
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public boolean isInstalled(String packageName, boolean useCache) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(packageName);
                    obtain.writeInt(useCache ? 1 : 0);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInstalled(packageName, useCache);
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void persistAppOrder(byte[] appIndexList) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(appIndexList);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().persistAppOrder(appIndexList);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public byte[] getStoreHomeProto() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStoreHomeProto();
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public byte[] getAppDetailProto(String packageName) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(packageName);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppDetailProto(packageName);
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public byte[] getUpdateProto() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(17, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUpdateProto();
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public boolean isPrivacyAgreed() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPrivacyAgreed();
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void showPrivacyDialog(boolean onlyContent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(onlyContent ? 1 : 0);
                    if (!this.mRemote.transact(19, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showPrivacyDialog(onlyContent);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public int getHttpHost() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(20, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHttpHost();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void switchHttpPreHost() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(21, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().switchHttpPreHost();
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void switchHttpTestHost() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().switchHttpTestHost();
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void resetHttpHost() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(23, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resetHttpHost();
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void startLoadUsbApp() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(24, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startLoadUsbApp();
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public byte[] getUsbAppList() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(25, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUsbAppList();
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void installUsbApp(String packageName) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(packageName);
                    if (!this.mRemote.transact(26, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().installUsbApp(packageName);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void registerUsbEntryListener(IUsbEntryListener listener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (!this.mRemote.transact(27, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerUsbEntryListener(listener);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void unregisterUsbEntryListener(IUsbEntryListener listener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (!this.mRemote.transact(28, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterUsbEntryListener(listener);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void registerUsbAppListener(IUsbAppListener listener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (!this.mRemote.transact(29, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerUsbAppListener(listener);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void unregisterUsbAppListener(IUsbAppListener listener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (!this.mRemote.transact(30, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterUsbAppListener(listener);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void loadAppletList() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(31, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().loadAppletList();
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void launchApplet(String id, String name) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(id);
                    obtain.writeString(name);
                    if (!this.mRemote.transact(32, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().launchApplet(id, name);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void registerAppletListener(IAppletListener listener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (!this.mRemote.transact(33, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerAppletListener(listener);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
            public void unregisterAppletListener(IAppletListener listener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (!this.mRemote.transact(34, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterAppletListener(listener);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAppStoreService impl) {
            if (Proxy.sDefaultImpl == null) {
                if (impl != null) {
                    Proxy.sDefaultImpl = impl;
                    return true;
                }
                return false;
            }
            throw new IllegalStateException("setDefaultImpl() called twice");
        }

        public static IAppStoreService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
