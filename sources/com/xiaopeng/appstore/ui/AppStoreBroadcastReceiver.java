package com.xiaopeng.appstore.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.ConfigReceiver;
import com.xiaopeng.appstore.appserver_common.OpenAppStoreInNapaReceiver;
import com.xiaopeng.appstore.bizcommon.logic.Constants;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.common_ui.BizRootActivity;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.storeprovider.AssembleConstants;
import com.xiaopeng.appstore.storeprovider.AssembleRequest;
import com.xiaopeng.appstore.storeprovider.AssembleResult;
import com.xiaopeng.appstore.storeprovider.StoreProviderManager;
import com.xiaopeng.appstore.xpcommon.XpUtils;
/* loaded from: classes2.dex */
public class AppStoreBroadcastReceiver extends BroadcastReceiver {
    private static final String ACTION_OPEN_APPLET_LIST = "com.xiaopeng.intent.action.SHOW_APPLET_LIST";
    private static final String ACTION_OPEN_APP_LIST = "com.xiaopeng.intent.action.SHOW_APP_LIST";
    private static final String ACTION_OPEN_APP_STORE = "com.xiaopeng.intent.action.SHOW_APP_STORE";
    private static final String TAG = "AppStoreBroadcastReceiver";
    private static BizRootActivity sBizRootActivity;
    private Context mContext;

    public static void setBizRootActivity(BizRootActivity bizRootActivity) {
        Logger.t(TAG).i("setBizRootActivity:" + bizRootActivity, new Object[0]);
        sBizRootActivity = bizRootActivity;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(final Context context, final Intent intent) {
        this.mContext = context;
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.ui.-$$Lambda$AppStoreBroadcastReceiver$JdO-jC9TOVsM51mq5c_zR2E-X-4
            @Override // java.lang.Runnable
            public final void run() {
                AppStoreBroadcastReceiver.this.lambda$onReceive$0$AppStoreBroadcastReceiver(context, intent);
            }
        });
    }

    public /* synthetic */ void lambda$onReceive$0$AppStoreBroadcastReceiver(Context context, Intent intent) {
        int appMode = ConfigReceiver.getAppMode(context);
        if (appMode == 1) {
            handleAsServer(context, intent);
        } else if (appMode == 0) {
            handleAsAndroidUI(context, intent);
        }
    }

    private void startDownload(final String packageName, final int screenId) {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.ui.-$$Lambda$AppStoreBroadcastReceiver$O5PVqSmSlwyO03aYNWP_u8E3r7g
            @Override // java.lang.Runnable
            public final void run() {
                AppStoreBroadcastReceiver.lambda$startDownload$1(packageName, screenId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$startDownload$1(String str, int i) {
        AssembleResult assemble = StoreProviderManager.get().assemble(AssembleRequest.enqueue(1000, str, str).putExtra("extra_key_show_dialog", false).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_START_DOWNLOAD_SHOW_TOAST, true).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_TOAST_SHARE_ID, i).request(), null);
        if (assemble.isSuccessful()) {
            Logger.t(TAG).i("startDownload success:" + str, new Object[0]);
        } else {
            Logger.t(TAG).w("startDownload fail:" + assemble, new Object[0]);
        }
    }

    private void handleAsAndroidUI(final Context context, final Intent intent) {
        final String action = intent.getAction();
        final Uri data = intent.getData();
        final int intExtra = intent.getIntExtra("com.xiaopeng.appstore.intent.extra.SCREEN", 0);
        if (ACTION_OPEN_APP_LIST.equals(action) || "com.xiaopeng.intent.action.SHOW_APP_STORE".equals(action) || ACTION_OPEN_APPLET_LIST.equals(action)) {
            if ("com.xiaopeng.intent.action.SHOW_APP_STORE".equals(action)) {
                Uri data2 = intent.getData();
                String str = TAG;
                Logger.t(str).i("onReceive, action:" + action + ", data:" + data2, new Object[0]);
                if (data2 != null && data2.getScheme().equals("market") && data2.getHost().equals("details")) {
                    String queryParameter = data2.getQueryParameter("id");
                    if (TextUtils.isEmpty(queryParameter)) {
                        Logger.t(str).w("error id:" + queryParameter, new Object[0]);
                        return;
                    }
                    int intExtra2 = intent.getIntExtra(Constants.OPEN_PAGE_WITH_ACTION_, 0);
                    Logger.t(str).i("open detail:" + queryParameter + ", openAction:" + intExtra2, new Object[0]);
                    if (intExtra2 == 2) {
                        startDownload(queryParameter, intExtra);
                        return;
                    }
                }
            }
            AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.ui.-$$Lambda$AppStoreBroadcastReceiver$aFQZ2CmXEyuvUS1tdZujaaRmh90
                @Override // java.lang.Runnable
                public final void run() {
                    AppStoreBroadcastReceiver.lambda$handleAsAndroidUI$2(intExtra, action, data, intent, context);
                }
            });
            return;
        }
        Logger.t(TAG).w("onReceive unhandled action:" + action, new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$handleAsAndroidUI$2(int i, String str, Uri uri, Intent intent, Context context) {
        int shareId = XuiMgrHelper.get().getShareId();
        if (sBizRootActivity != null && shareId == i) {
            Logger.t(TAG).i("onReceive action=" + str + ",data=" + uri + ". Use Activity instance:" + sBizRootActivity.hashCode() + ", currentScreen:" + shareId + ", requestScreen:" + i, new Object[0]);
            sBizRootActivity.handleStart(intent);
            return;
        }
        Logger.t(TAG).i("onReceive action=" + str + ",data=" + uri + ". No Activity instance or different screen requested, startActivity directly. currentScreen:" + shareId + ", requestScreen:" + i, new Object[0]);
        Intent intent2 = new Intent(context, MainActivity.class);
        intent2.addFlags(268435456);
        intent2.setAction(intent.getAction());
        intent2.setData(uri);
        if (intent.getExtras() != null) {
            intent2.putExtras(intent.getExtras());
        }
        XpUtils.invokeSetSharedId(intent2, i);
        context.startActivity(intent2);
    }

    private void handleAsServer(Context context, Intent intent) {
        OpenAppStoreInNapaReceiver.handleReceive(context, intent);
    }
}
