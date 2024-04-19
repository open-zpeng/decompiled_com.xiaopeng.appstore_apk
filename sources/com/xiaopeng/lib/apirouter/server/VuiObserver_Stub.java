package com.xiaopeng.lib.apirouter.server;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.core.app.NotificationCompat;
import com.xiaopeng.appstore.VuiObserver;
import com.xiaopeng.speech.vui.constants.VuiConstants;
/* loaded from: classes2.dex */
public class VuiObserver_Stub extends Binder implements IInterface {
    public VuiObserver provider = new VuiObserver();
    public VuiObserver_Manifest manifest = new VuiObserver_Manifest();

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (code == 0) {
            data.enforceInterface("com.xiaopeng.appstore.VuiObserver");
            Uri uri = (Uri) Uri.CREATOR.createFromParcel(data);
            try {
                String elementState = this.provider.getElementState((String) TransactTranslator.read(uri.getQueryParameter(VuiConstants.SCENE_ID), "java.lang.String"), (String) TransactTranslator.read(uri.getQueryParameter("elementId"), "java.lang.String"));
                reply.writeNoException();
                TransactTranslator.reply(reply, elementState);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                reply.writeException(new IllegalStateException(e.getMessage()));
                return true;
            }
        } else if (code != 1) {
            if (code == 1598968902) {
                reply.writeString("com.xiaopeng.appstore.VuiObserver");
                return true;
            }
            return super.onTransact(code, data, reply, flags);
        } else {
            data.enforceInterface("com.xiaopeng.appstore.VuiObserver");
            Uri uri2 = (Uri) Uri.CREATOR.createFromParcel(data);
            try {
                this.provider.onEvent((String) TransactTranslator.read(uri2.getQueryParameter(NotificationCompat.CATEGORY_EVENT), "java.lang.String"), (String) TransactTranslator.read(uri2.getQueryParameter("data"), "java.lang.String"));
                reply.writeNoException();
                TransactTranslator.reply(reply, null);
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                reply.writeException(new IllegalStateException(e2.getMessage()));
                return true;
            }
        }
    }
}
