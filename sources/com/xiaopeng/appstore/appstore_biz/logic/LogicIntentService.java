package com.xiaopeng.appstore.appstore_biz.logic;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.AppStoreApplication;
import com.xiaopeng.appstore.appstore_biz.R;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.DialogHelper;
import com.xiaopeng.appstore.bizcommon.utils.PackageUtils;
import com.xiaopeng.appstore.libcommon.utils.NumberUtils;
import com.xiaopeng.appstore.storeprovider.ResourceProviderContract;
/* loaded from: classes2.dex */
public class LogicIntentService extends IntentService {
    public static final String ACTION_XUI_EVENT = "com.xiaopeng.appstore.action.XUI_EVENT";
    public static final String EXTRA_APP_STORE_PACKAGE = "com.xiaopeng.appstore.intent.extra.PACKAGE";
    public static final String EXTRA_APP_STORE_SCREEN = "com.xiaopeng.appstore.intent.extra.SCREEN";
    public static final String EXTRA_APP_STORE_STATE = "com.xiaopeng.appstore.intent.extra.STATE";
    private static final String NOTIFICATION_CHANNEL_ID = "10001900";
    private static final int NOTIFICATION_ID = 10001900;
    public static final int START_FORCE_UPDATED_DENIED = 6;
    public static final int START_SUSPEND_DENIED = 5;
    private static final String TAG = "LogicIntentService";

    public LogicIntentService() {
        super(TAG);
    }

    @Override // android.app.IntentService, android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.t(TAG).i("onStartCommand, startId:" + startId + ", action:" + (intent != null ? intent.getAction() : ""), new Object[0]);
        startForeground(NOTIFICATION_ID, buildForegroundNotification());
        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotificationChannel() {
        ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel(NOTIFICATION_CHANNEL_ID, getString(R.string.logic_notification_channel), 3));
    }

    private Notification buildForegroundNotification() {
        createNotificationChannel();
        return new Notification.Builder(this, NOTIFICATION_CHANNEL_ID).setContentTitle(getText(R.string.logic_notification_title)).setSmallIcon(R.drawable.x_ic_logo).setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0)).build();
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_XUI_EVENT.equals(action)) {
                handleXUIEvent(intent);
            } else if (StoreLogicReceiver.ACTION_APP_UPDATE_DIALOG.equals(action)) {
                handleShowUpdateDialog(intent);
            }
        }
    }

    private void handleXUIEvent(Intent intent) {
        Bundle extras = intent.getExtras();
        boolean z = true;
        if (extras == null || NumberUtils.stringToInt(extras.getString("557847561"), -1) != 1) {
            z = false;
        } else {
            Logger.t(TAG).i("handleXUIEvent xui event:ig on", new Object[0]);
        }
        CheckUpdateManager.get().requestUpdateSync();
        if (z) {
            Flags.setPrivacyShown(false);
        }
        Logger.t(TAG).i("handleXUIEvent xui event END", new Object[0]);
    }

    private void handleShowUpdateDialog(Intent intent) {
        DialogHelper.DialogType dialogType;
        DialogHelper.DialogType dialogType2;
        String stringExtra = intent.getStringExtra(EXTRA_APP_STORE_PACKAGE);
        int intExtra = intent.getIntExtra("com.xiaopeng.appstore.intent.extra.SCREEN", 0);
        int intExtra2 = intent.getIntExtra(EXTRA_APP_STORE_STATE, 0);
        Logger.t(TAG).i("startActivity forbidden, package:" + stringExtra + ", state:" + intExtra2 + ", screen:" + intExtra, new Object[0]);
        if (TextUtils.isEmpty(stringExtra)) {
            return;
        }
        DialogHelper.DialogType dialogType3 = DialogHelper.DialogType.NONE;
        if (intExtra2 == 5) {
            dialogType2 = DialogHelper.DialogType.SUSPEND;
        } else if (intExtra2 == 6) {
            if (ResourceProviderContract.queryIsAssembling(getApplicationContext(), stringExtra, 1000)) {
                dialogType2 = DialogHelper.DialogType.FORCE_UPDATING;
            } else {
                dialogType2 = DialogHelper.DialogType.FORCE_UPDATE_GO_APPSTORE;
            }
        } else {
            dialogType = dialogType3;
            CharSequence applicationLabel = PackageUtils.getApplicationLabel(getApplicationContext(), stringExtra);
            Logger.t(TAG).i("startActivity forbidden, appname:" + ((Object) applicationLabel), new Object[0]);
            new DialogHelper(AppStoreApplication.get().dialog, AppStoreApplication.get().toast).show(getApplicationContext(), stringExtra, (String) applicationLabel, dialogType, intExtra);
        }
        dialogType = dialogType2;
        CharSequence applicationLabel2 = PackageUtils.getApplicationLabel(getApplicationContext(), stringExtra);
        Logger.t(TAG).i("startActivity forbidden, appname:" + ((Object) applicationLabel2), new Object[0]);
        new DialogHelper(AppStoreApplication.get().dialog, AppStoreApplication.get().toast).show(getApplicationContext(), stringExtra, (String) applicationLabel2, dialogType, intExtra);
    }
}
