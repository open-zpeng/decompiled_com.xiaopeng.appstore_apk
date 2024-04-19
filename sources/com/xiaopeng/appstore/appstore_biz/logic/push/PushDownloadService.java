package com.xiaopeng.appstore.appstore_biz.logic.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.R;
import com.xiaopeng.appstore.appstore_biz.logic.push.PushHandler;
import com.xiaopeng.appstore.bizcommon.logic.AssembleDataCache;
import com.xiaopeng.appstore.bizcommon.logic.StoreProviderHelper;
import com.xiaopeng.appstore.libcommon.utils.AndroidStateObserveMgr;
import com.xiaopeng.appstore.libcommon.utils.NumberUtils;
import com.xiaopeng.appstore.storeprovider.AssembleConstants;
import com.xiaopeng.appstore.storeprovider.AssembleInfo;
import com.xiaopeng.appstore.storeprovider.AssembleRequest;
import com.xiaopeng.appstore.storeprovider.AssembleResult;
import com.xiaopeng.appstore.storeprovider.IAssembleClientListener;
import com.xiaopeng.appstore.storeprovider.StoreProviderManager;
import com.xiaopeng.appstore.xpcommon.push.PushHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/* loaded from: classes2.dex */
public class PushDownloadService extends Service implements IAssembleClientListener {
    private static final String DOWNLOAD_TAG = "download_tag_from_mobile_app";
    private static final String NOTIFICATION_CHANNEL_ID = "1000555";
    private static final int NOTIFICATION_ID = 1000555;
    private static final String PARAMS_MSG = "params_msg";
    private static final String TAG = "PushDownloadService";
    private static boolean sCompleteMsgSent = false;
    private static final AndroidStateObserveMgr.StateListener sScreenOffListener = new AndroidStateObserveMgr.StateListener() { // from class: com.xiaopeng.appstore.appstore_biz.logic.push.-$$Lambda$PushDownloadService$Cg3vtFQwqgafBTMDuSqCCDv2V-s
        @Override // com.xiaopeng.appstore.libcommon.utils.AndroidStateObserveMgr.StateListener
        public final void onScreenOff() {
            PushDownloadService.lambda$static$0();
        }
    };
    private HandlerThread mHandlerThread;
    private Handler mWorkHandler;
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());
    private final Map<String, Integer> mDownloadingMap = new HashMap();

    public static void actionStart(Context context, PushHandler.DownloadAppMsg downloadAppMsg) {
        if (downloadAppMsg == null) {
            Logger.t(TAG).w("actionStart, invalid argument", new Object[0]);
            return;
        }
        Intent intent = new Intent(context, PushDownloadService.class);
        intent.putExtra(PARAMS_MSG, downloadAppMsg);
        context.startForegroundService(intent);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$static$0() {
        Logger.t(TAG).i("ScreenOff", new Object[0]);
        sCompleteMsgSent = false;
    }

    public static void observeScreenOff() {
        AndroidStateObserveMgr.get().addListener(sScreenOffListener);
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Logger.t(TAG).i("onCreate:" + hashCode(), new Object[0]);
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mWorkHandler = new Handler(this.mHandlerThread.getLooper());
        StoreProviderManager.get().registerListener(1000, null, this);
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.t(TAG).i("onStartCommand, intent:" + intent + ", startId:" + startId + ", this:" + hashCode(), new Object[0]);
        startForeground(NOTIFICATION_ID, buildForegroundNotification());
        if (intent == null) {
            stopSelf(startId);
            return 1;
        }
        final PushHandler.DownloadAppMsg downloadAppMsg = (PushHandler.DownloadAppMsg) intent.getParcelableExtra(PARAMS_MSG);
        if (downloadAppMsg != null) {
            this.mDownloadingMap.put(downloadAppMsg.package_name, Integer.valueOf(startId));
            Logger.t(TAG).i("startDownload:" + downloadAppMsg, new Object[0]);
            this.mWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.logic.push.-$$Lambda$PushDownloadService$8M40aavmzQc2qjpYEY0gE0a-OYE
                @Override // java.lang.Runnable
                public final void run() {
                    PushDownloadService.this.lambda$onStartCommand$1$PushDownloadService(downloadAppMsg);
                }
            });
        } else {
            stopSelf(startId);
        }
        return 1;
    }

    public /* synthetic */ void lambda$onStartCommand$1$PushDownloadService(PushHandler.DownloadAppMsg downloadAppMsg) {
        if (tryStartDownload(downloadAppMsg)) {
            return;
        }
        stopServiceWithPackage(downloadAppMsg.package_name);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Logger.t(TAG).i("onDestroy:" + hashCode(), new Object[0]);
        this.mDownloadingMap.clear();
        this.mHandlerThread.quit();
        StoreProviderManager.get().unregisterListener(this);
    }

    private boolean tryStartDownload(PushHandler.DownloadAppMsg downloadAppMsg) {
        String str = downloadAppMsg.package_name;
        long stringToLong = NumberUtils.stringToLong(downloadAppMsg.version_code, -1L);
        if (stringToLong == -1) {
            Logger.t(TAG).w("tryStartDownload," + str + ", versionCode error:" + downloadAppMsg, new Object[0]);
            return false;
        } else if (isInstalled(str, stringToLong)) {
            Logger.t(TAG).w("tryStartDownload," + str + ", this package is installed:" + downloadAppMsg, new Object[0]);
            return false;
        } else if (isDownloading(str)) {
            Logger.t(TAG).w("tryStartDownload," + str + ", this package is downloading:" + downloadAppMsg, new Object[0]);
            return false;
        } else {
            return startDownload(downloadAppMsg);
        }
    }

    private boolean isDownloading(String packageName) {
        List<AssembleInfo> assembleInfoList = StoreProviderManager.get().getAssembleInfoList();
        if (assembleInfoList == null) {
            return false;
        }
        for (AssembleInfo assembleInfo : assembleInfoList) {
            if (assembleInfo.getKey().equals(packageName)) {
                int state = assembleInfo.getState();
                if (AssembleInfo.isPreparing(state) || AssembleInfo.isRunning(state)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isInstalled(String packageName, long versionCode) {
        try {
            long longVersionCode = getPackageManager().getPackageInfo(packageName, 0).getLongVersionCode();
            Logger.t(TAG).i("Installed " + packageName + ", version:" + longVersionCode + ", tryInstall:" + versionCode, new Object[0]);
            return longVersionCode >= versionCode;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    private boolean startDownload(PushHandler.DownloadAppMsg downloadAppMsg) {
        if (downloadAppMsg == null || TextUtils.isEmpty(downloadAppMsg.package_name) || TextUtils.isEmpty(downloadAppMsg.app_name) || TextUtils.isEmpty(downloadAppMsg.download_url) || TextUtils.isEmpty(downloadAppMsg.config_url)) {
            Logger.t(TAG).w("startDownload, error argument:" + downloadAppMsg, new Object[0]);
            return false;
        }
        StoreProviderHelper.observe(downloadAppMsg.package_name);
        AssembleResult assemble = StoreProviderManager.get().assemble(AssembleRequest.enqueue(1000, downloadAppMsg.package_name, downloadAppMsg.app_name).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_START_DOWNLOAD_SHOW_TOAST, false).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_URL, downloadAppMsg.config_url).putExtra(DOWNLOAD_TAG, DOWNLOAD_TAG).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_MD5_1, downloadAppMsg.config_md5).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_MD5_2, downloadAppMsg.md5).setDownloadUrl(downloadAppMsg.download_url).setIconUrl(downloadAppMsg.app_icons != null ? downloadAppMsg.app_icons.small_icon : "").request());
        if (assemble == null || !assemble.isSuccessful()) {
            Logger.t(TAG).i("enqueueRequest fail:" + downloadAppMsg.app_name + ", " + assemble, new Object[0]);
            return false;
        }
        AssembleDataCache.get().findInMemOrCreate(downloadAppMsg.download_url, downloadAppMsg.config_url, downloadAppMsg.package_name, downloadAppMsg.app_icons != null ? downloadAppMsg.app_icons.small_icon : "", downloadAppMsg.app_name);
        Logger.t(TAG).i("enqueueRequest success:" + downloadAppMsg.app_name + ", " + assemble, new Object[0]);
        return true;
    }

    private Notification buildForegroundNotification() {
        createNotificationChannel();
        return new Notification.Builder(this, NOTIFICATION_CHANNEL_ID).setContentTitle(getText(R.string.notification_title)).setSmallIcon(R.drawable.x_ic_logo).setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0)).build();
    }

    private void createNotificationChannel() {
        ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel(NOTIFICATION_CHANNEL_ID, getString(R.string.notification_channel), 3));
    }

    private boolean isPushDownload(AssembleInfo assembleInfo) {
        if (assembleInfo == null) {
            return false;
        }
        return !TextUtils.isEmpty(assembleInfo.getExtraString(DOWNLOAD_TAG));
    }

    private boolean hasPushDownloading() {
        List<AssembleInfo> assembleInfoList = StoreProviderManager.get().getAssembleInfoList();
        if (assembleInfoList == null) {
            return false;
        }
        for (AssembleInfo assembleInfo : assembleInfoList) {
            if (isPushDownload(assembleInfo)) {
                int state = assembleInfo.getState();
                if (AssembleInfo.isPreparing(state) || AssembleInfo.isRunning(state)) {
                    Logger.t(TAG).i("Running download found:" + assembleInfo, new Object[0]);
                    return true;
                }
            }
        }
        return false;
    }

    private void trySendCompleteMsg() {
        boolean z = sCompleteMsgSent;
        if (!z && !hasPushDownloading()) {
            Logger.t(TAG).i("send complete msg", new Object[0]);
            sCompleteMsgSent = true;
            PushHelper.sendMessageToMessageCenter(getApplicationContext(), PushHelper.APP_DOWNLOAD_COMPLETE_CARD_CLICK_SCENE, getString(R.string.app_from_mobile_download_complete_title), getString(R.string.app_from_mobile_download_complete), getString(R.string.app_from_mobile_download_complete), getString(R.string.app_from_mobile_notification_wake_words), "", getString(R.string.app_from_mobile_notification_ok), 0L, true);
            return;
        }
        Logger.t(TAG).i("Do not send msg, hasSent:" + z, new Object[0]);
    }

    private void stopServiceWithPackage(final String packageName) {
        this.mMainHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.logic.push.-$$Lambda$PushDownloadService$WX-GpvzEfKfzJaC1QhWPht-hhUQ
            @Override // java.lang.Runnable
            public final void run() {
                PushDownloadService.this.lambda$stopServiceWithPackage$2$PushDownloadService(packageName);
            }
        });
    }

    public /* synthetic */ void lambda$stopServiceWithPackage$2$PushDownloadService(String str) {
        Integer num = !TextUtils.isEmpty(str) ? this.mDownloadingMap.get(str) : null;
        if (num != null) {
            Logger.t(TAG).i("stopSelf:" + str + ", startId:" + num, new Object[0]);
            stopSelf(num.intValue());
            this.mDownloadingMap.remove(str);
            return;
        }
        stopSelf();
        Logger.t(TAG).w("StartId not found for this complete:" + str + ", " + this.mDownloadingMap, new Object[0]);
    }

    @Override // com.xiaopeng.appstore.storeprovider.IAssembleClientListener
    public void onAssembleEvent(int event, AssembleInfo assembleInfo) {
        if (event == 1000 && isPushDownload(assembleInfo)) {
            Logger.t(TAG).i("App from push download:" + assembleInfo, new Object[0]);
            if (AssembleInfo.isCompleted(assembleInfo.getState())) {
                trySendCompleteMsg();
                StoreProviderHelper.removeObserve(assembleInfo.getKey());
                stopServiceWithPackage(assembleInfo.getKey());
            } else if (AssembleInfo.isError(assembleInfo.getState()) || AssembleInfo.isCancelled(assembleInfo.getState())) {
                StoreProviderHelper.removeObserve(assembleInfo.getKey());
            }
        }
    }
}
