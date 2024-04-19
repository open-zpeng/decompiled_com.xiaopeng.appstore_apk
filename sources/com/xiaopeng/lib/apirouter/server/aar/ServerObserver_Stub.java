package com.xiaopeng.lib.apirouter.server.aar;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaopeng.aar.server.ipc.ServerObserver;
import com.xiaopeng.lib.apirouter.ParcelUtils;
import com.xiaopeng.lib.apirouter.server.TransactTranslator;
import com.xiaopeng.speech.vui.constants.VuiConstants;
/* loaded from: classes2.dex */
public class ServerObserver_Stub extends Binder implements IInterface {
    public ServerObserver provider = new ServerObserver();
    public ServerObserver_Manifest manifest = new ServerObserver_Manifest();

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i == 0) {
            parcel.enforceInterface(ServerObserver_Manifest.DESCRIPTOR);
            Uri uri = (Uri) Uri.CREATOR.createFromParcel(parcel);
            try {
                String call = this.provider.call(((Integer) TransactTranslator.read(uri.getQueryParameter(VuiConstants.ELEMENT_TYPE), "int")).intValue(), (String) TransactTranslator.read(uri.getQueryParameter("module"), "java.lang.String"), (String) TransactTranslator.read(uri.getQueryParameter("method"), "java.lang.String"), (String) TransactTranslator.read(uri.getQueryParameter("param"), "java.lang.String"));
                parcel2.writeNoException();
                TransactTranslator.reply(parcel2, call);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                parcel2.writeException(new IllegalStateException(e.getMessage()));
                return true;
            }
        } else if (i != 1) {
            if (i == 1598968902) {
                parcel2.writeString(ServerObserver_Manifest.DESCRIPTOR);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        } else {
            parcel.enforceInterface(ServerObserver_Manifest.DESCRIPTOR);
            Uri uri2 = (Uri) Uri.CREATOR.createFromParcel(parcel);
            try {
                byte[] readBlob = ParcelUtils.readBlob(parcel);
                String callBlob = this.provider.callBlob(((Integer) TransactTranslator.read(uri2.getQueryParameter(VuiConstants.ELEMENT_TYPE), "int")).intValue(), (String) TransactTranslator.read(uri2.getQueryParameter("module"), "java.lang.String"), (String) TransactTranslator.read(uri2.getQueryParameter("method"), "java.lang.String"), (String) TransactTranslator.read(uri2.getQueryParameter("param"), "java.lang.String"), readBlob);
                parcel2.writeNoException();
                TransactTranslator.reply(parcel2, callBlob);
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                parcel2.writeException(new IllegalStateException(e2.getMessage()));
                return true;
            }
        }
    }
}
