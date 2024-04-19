package com.xiaopeng.appstore.appserver_common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.storeprovider.AssembleConstants;
import com.xiaopeng.appstore.storeprovider.AssembleRequest;
import com.xiaopeng.appstore.storeprovider.AssembleResult;
import com.xiaopeng.appstore.storeprovider.StoreProviderManager;
/* loaded from: classes2.dex */
public class OpenAppStoreInNapaReceiver extends BroadcastReceiver {
    private static final String ACTION_APPLET_LIST = "com.xiaopeng.intent.action.SHOW_APPLET_LIST";
    private static final String ACTION_APP_LIST = "com.xiaopeng.intent.action.SHOW_APP_LIST";
    private static final String ACTION_APP_STORE = "com.xiaopeng.intent.action.SHOW_APP_STORE";
    public static final String PAGE_ACTION_APPLET = "applet";
    public static final String PAGE_ACTION_APPLIST = "applist";
    public static final String PAGE_ACTION_APPSTORE = "appstore";
    public static final String PAGE_ACTION_APPSTORE_DETAIL = "appstore_detail";
    private static final String SPLIT_CHAR = ",";
    private static final String TAG = "AppStoreReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Logger.t(TAG).e("intent is null", new Object[0]);
        } else {
            handleReceive(context, intent);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void handleReceive(android.content.Context r10, android.content.Intent r11) {
        /*
            Method dump skipped, instructions count: 363
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.appserver_common.OpenAppStoreInNapaReceiver.handleReceive(android.content.Context, android.content.Intent):void");
    }

    private static void startDownload(final String packageName, final int screenId) {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$OpenAppStoreInNapaReceiver$gPUfI_wBJbfFya-33X1lWjlL80M
            @Override // java.lang.Runnable
            public final void run() {
                OpenAppStoreInNapaReceiver.lambda$startDownload$0(packageName, screenId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$startDownload$0(String str, int i) {
        AssembleResult assemble = StoreProviderManager.get().assemble(AssembleRequest.enqueue(1000, str, str).putExtra("extra_key_show_dialog", false).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_START_DOWNLOAD_SHOW_TOAST, true).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_TOAST_SHARE_ID, i).request(), null);
        if (assemble.isSuccessful()) {
            Logger.t(TAG).i("startDownload success:" + str, new Object[0]);
        } else {
            Logger.t(TAG).w("startDownload fail:" + assemble, new Object[0]);
        }
    }
}
