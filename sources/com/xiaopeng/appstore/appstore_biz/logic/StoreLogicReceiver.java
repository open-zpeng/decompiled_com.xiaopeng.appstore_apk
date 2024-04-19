package com.xiaopeng.appstore.appstore_biz.logic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.datamodel.api.XpApiClient;
import com.xiaopeng.appstore.appstore_biz.datamodel.api.XpAppService;
import com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.AssembleDataCache;
import com.xiaopeng.appstore.installer.Constants;
import com.xiaopeng.appstore.installer.XpInstallForbiddenActivity;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.Collection;
/* loaded from: classes2.dex */
public class StoreLogicReceiver extends BroadcastReceiver {
    public static final String ACTION_APP_UPDATE_DIALOG = "com.xiaopeng.intent.action.SHOW_APP_UPDATE_DIALOG";
    private static final String ACTION_CLEAR_DATA = "com.xiaopeng.intent.action.CLEAN_APP_CACHES";
    private static final String ACTION_INSTALL_FORBIDDEN = "com.xiaopeng.INSTALL_FORBIDDEN";
    private static final String ACTION_XUI_EVENT = "com.xiaopeng.xui.businessevent";
    private static final String TAG = "StoreLogicReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_XUI_EVENT.equals(action)) {
            Logger.t(TAG).i("onReceive xui event", new Object[0]);
            Intent intent2 = new Intent(context, LogicIntentService.class);
            intent2.setAction(LogicIntentService.ACTION_XUI_EVENT);
            intent2.putExtras(intent);
            context.startForegroundService(intent2);
            Logger.t(TAG).i("onReceive xui event END", new Object[0]);
        } else if (ACTION_INSTALL_FORBIDDEN.equals(action)) {
            handleInstallForbidden(context, intent);
        } else if (ACTION_CLEAR_DATA.equals(action)) {
            final BroadcastReceiver.PendingResult goAsync = goAsync();
            AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.logic.-$$Lambda$StoreLogicReceiver$MRluCdfrSjLD4gY2IG5H1oIDJ5Y
                @Override // java.lang.Runnable
                public final void run() {
                    StoreLogicReceiver.lambda$onReceive$0(goAsync);
                }
            });
        } else if (ACTION_APP_UPDATE_DIALOG.equals(action)) {
            Intent intent3 = new Intent(context, LogicIntentService.class);
            intent3.setAction(ACTION_APP_UPDATE_DIALOG);
            intent3.putExtras(intent);
            context.startForegroundService(intent3);
        } else {
            Logger.t(TAG).w("onReceive unhandled action:" + action, new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onReceive$0(BroadcastReceiver.PendingResult pendingResult) {
        Collection<AssembleDataContainer> urlAssembleMap = AssembleDataCache.get().getUrlAssembleMap();
        if (urlAssembleMap != null) {
            for (AssembleDataContainer assembleDataContainer : urlAssembleMap) {
                Logger.t(TAG).i("ClearData, cancel assemble task:" + assembleDataContainer.getPackageName(), new Object[0]);
                AppStoreAssembleManager.get().cancelWithPackage(assembleDataContainer.getPackageName());
            }
        } else {
            Logger.t(TAG).i("ClearData, no assemble task", new Object[0]);
        }
        Logger.t(TAG).i("onReceive clear data event END", new Object[0]);
        pendingResult.finish();
    }

    private void handleInstallForbidden(Context context, Intent intent) {
        ApplicationInfo applicationInfo;
        String string = intent.getExtras().getString(XpInstallForbiddenActivity.EXTRA_CALLING_PACKAGE);
        int i = intent.getExtras().getInt(Constants.EXTRA_ORIGINATING_UID);
        PackageInfo packageInfo = (PackageInfo) intent.getExtras().getParcelable("install_forbidden_broadcast_target_package_key");
        Logger.t(TAG).e("broadcast receiver calling package = " + string + "    source info = " + ((ApplicationInfo) intent.getExtras().getParcelable("EXTRA_ORIGINAL_SOURCE_INFO")) + "       uid  = " + i + "   Uri  = " + ((Uri) intent.getExtras().getParcelable("install_forbidden_broadcast_uri_data_key")), new Object[0]);
        PackageManager packageManager = context.getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(string, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            applicationInfo = null;
        }
        String str = packageInfo.applicationInfo.name;
        final String str2 = packageInfo.packageName;
        final String str3 = packageInfo.versionName;
        final long longVersionCode = packageInfo.getLongVersionCode();
        EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_MAIN, EventEnum.STORE_APP_SELF_UPGRADE_FAILED, (String) packageManager.getApplicationLabel(applicationInfo), string, str, str2);
        Observable.just("").observeOn(Schedulers.io()).subscribe(new Consumer() { // from class: com.xiaopeng.appstore.appstore_biz.logic.-$$Lambda$StoreLogicReceiver$zpUq9ML0docKxhSLbtYFftea4oA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                String str4 = (String) obj;
                XpAppService.installFailedSubmit(XpApiClient.getAppService(), str2, str3, longVersionCode);
            }
        });
        Logger.t(TAG).d("broadcast receiver installAppName = " + str + "    version name = " + str3 + "       version code  = " + longVersionCode + "   package name  = " + str2);
    }
}
