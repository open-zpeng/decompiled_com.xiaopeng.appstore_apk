package com.xiaopeng.downloadcenter;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
/* loaded from: classes2.dex */
public class DownLoadReceiver extends BroadcastReceiver {
    public static final String ACTION_CANCEL = "xp.action.cancel";
    public static final String ACTION_DELETE = "xp.action.delete";
    public static final String ACTION_PAUSE = "xp.action.pause";
    public static final String ACTION_RESUME = "xp.action.resume";
    public static final String ACTION_RETRY = "xp.action.retry";
    public static final String ACTION_SUCCESS = "xp.action.success";
    private static final String DOWNLOAD_ACTION_DATA_SCHEMA = "xpdm://";
    public static final String EXTRA_KEY_ID = "xp.id";
    private static final String SCHEMA_ACTION_CANCEL = "cancel/";
    private static final String SCHEMA_ACTION_DELETE = "cancel/";
    private static final String SCHEMA_ACTION_OPEN = "open/";
    private static final String SCHEMA_ACTION_PAUSE = "pause/";
    private static final String SCHEMA_ACTION_RESUME = "resume/";
    private static final String SCHEMA_ACTION_RETRY = "retry/";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int intExtra = intent.getIntExtra(EXTRA_KEY_ID, -1);
        log("action:" + action + ",id:" + intExtra);
        if (intExtra == -1) {
            return;
        }
        if (DownLoadCenterManager.get().getCallBack() == null) {
            log("call back is null");
        } else if (ACTION_PAUSE.equals(action)) {
            DownLoadCenterManager.get().getCallBack().onPause(intExtra);
        } else if (ACTION_RESUME.equals(action)) {
            DownLoadCenterManager.get().getCallBack().onResume(intExtra);
        } else if (ACTION_RETRY.equals(action)) {
            DownLoadCenterManager.get().getCallBack().onRetry(intExtra);
        } else if (ACTION_SUCCESS.equals(action)) {
            DownLoadCenterManager.get().getCallBack().onOpen(intExtra);
        } else if (ACTION_CANCEL.equals(action)) {
            DownLoadCenterManager.get().getCallBack().onCancel(intExtra);
        } else if (ACTION_DELETE.equals(action)) {
            DownLoadCenterManager.get().getCallBack().onDelete(intExtra);
        }
    }

    public static PendingIntent pausePendingIntent(Context context, int i) {
        Intent intent = new Intent(ACTION_PAUSE);
        intent.setComponent(new ComponentName(context, DownLoadReceiver.class));
        intent.putExtra(EXTRA_KEY_ID, i);
        intent.setData(Uri.parse("xpdm://pause/" + i));
        return PendingIntent.getBroadcast(context, 0, intent, 134217728);
    }

    public static PendingIntent resumePendingIntent(Context context, int i) {
        Intent intent = new Intent(ACTION_RESUME);
        intent.setComponent(new ComponentName(context, DownLoadReceiver.class));
        intent.putExtra(EXTRA_KEY_ID, i);
        intent.setData(Uri.parse("xpdm://resume/" + i));
        return PendingIntent.getBroadcast(context, 0, intent, 134217728);
    }

    public static PendingIntent retryPendingIntent(Context context, int i) {
        Intent intent = new Intent(ACTION_RETRY);
        intent.setComponent(new ComponentName(context, DownLoadReceiver.class));
        intent.putExtra(EXTRA_KEY_ID, i);
        intent.setData(Uri.parse("xpdm://retry/" + i));
        return PendingIntent.getBroadcast(context, 0, intent, 134217728);
    }

    public static PendingIntent successPendingIntent(Context context, int i) {
        Intent intent = new Intent(ACTION_SUCCESS);
        intent.setComponent(new ComponentName(context, DownLoadReceiver.class));
        intent.putExtra(EXTRA_KEY_ID, i);
        intent.setData(Uri.parse("xpdm://open/" + i));
        return PendingIntent.getBroadcast(context, 0, intent, 134217728);
    }

    public static PendingIntent cancelPendingIntent(Context context, int i) {
        Intent intent = new Intent(ACTION_CANCEL);
        intent.setComponent(new ComponentName(context, DownLoadReceiver.class));
        intent.putExtra(EXTRA_KEY_ID, i);
        intent.setData(Uri.parse("xpdm://cancel/" + i));
        return PendingIntent.getBroadcast(context, 0, intent, 134217728);
    }

    public static PendingIntent deletePendingIntent(Context context, int i) {
        Intent intent = new Intent(ACTION_DELETE);
        intent.setComponent(new ComponentName(context, DownLoadReceiver.class));
        intent.putExtra(EXTRA_KEY_ID, i);
        intent.setData(Uri.parse("xpdm://cancel/" + i));
        return PendingIntent.getBroadcast(context, 0, intent, 134217728);
    }

    private void log(String str) {
        Log.d("downloadCenter-Receiver", str);
    }
}
