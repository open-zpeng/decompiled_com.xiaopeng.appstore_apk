package com.xiaopeng.appstore.appstore_biz.bizusb.logic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.bizusb.logic.callback.IUSBState;
/* loaded from: classes2.dex */
public class USBDriveBroadcast extends BroadcastReceiver {
    private static final String TAG = "USBDriveBroadcast";
    private final IUSBState mState;

    public USBDriveBroadcast(IUSBState state) {
        this.mState = state;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        action.hashCode();
        if (!action.equals("android.intent.action.MEDIA_MOUNTED")) {
            if (action.equals("android.intent.action.MEDIA_EJECT")) {
                Logger.t(TAG).i("usb action_media_eject", new Object[0]);
                this.mState.usbEject();
                return;
            }
            return;
        }
        Uri data = intent.getData();
        if (data != null) {
            Logger.t(TAG).i("usb mounted url : " + data.getPath(), new Object[0]);
            this.mState.usbMounted(data.getPath());
            return;
        }
        Logger.t(TAG).i("usb mounted , data is  null", new Object[0]);
    }
}
