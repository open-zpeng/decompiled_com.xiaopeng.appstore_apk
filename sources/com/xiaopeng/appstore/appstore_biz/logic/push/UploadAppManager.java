package com.xiaopeng.appstore.appstore_biz.logic.push;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.HandlerThread;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;
import com.xiaopeng.appstore.appstore_biz.datamodel.api.XpApiClient;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.UploadAppRequest;
import com.xiaopeng.appstore.bizcommon.entities.common.XpApiResponse;
import com.xiaopeng.appstore.libcommon.utils.PackageMonitor;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class UploadAppManager {
    private static final String TAG = "UploadAppManager";
    private Context mContext;
    private HandlerThread mHandlerThread;
    private final PackageMonitor mPackageMonitor;
    private Handler mWorkHandler;

    private UploadAppManager() {
        this.mPackageMonitor = new PackageMonitor() { // from class: com.xiaopeng.appstore.appstore_biz.logic.push.UploadAppManager.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaopeng.appstore.libcommon.utils.PackageMonitor
            public void onPackageAdded(String packageName) {
                super.onPackageAdded(packageName);
                UploadAppManager.this.uploadInstalledApps();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaopeng.appstore.libcommon.utils.PackageMonitor
            public void onPackageUpdated(String packageName) {
                super.onPackageUpdated(packageName);
                UploadAppManager.this.uploadInstalledApps();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaopeng.appstore.libcommon.utils.PackageMonitor
            public void onPackageRemoved(String packageName) {
                super.onPackageRemoved(packageName);
                UploadAppManager.this.uploadInstalledApps();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingletonHolder {
        static UploadAppManager sInstance = new UploadAppManager();

        private SingletonHolder() {
        }
    }

    public static UploadAppManager get() {
        return SingletonHolder.sInstance;
    }

    public void init(Context context) {
        this.mContext = context;
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        Handler handler = new Handler(this.mHandlerThread.getLooper());
        this.mWorkHandler = handler;
        this.mPackageMonitor.register(context, handler);
        this.mWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.logic.push.-$$Lambda$UploadAppManager$VC6hhp-7KatLt7NdOrzgx4x7IpU
            @Override // java.lang.Runnable
            public final void run() {
                UploadAppManager.this.uploadInstalledApps();
            }
        });
    }

    public void release() {
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quit();
        }
        this.mPackageMonitor.unregister();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadInstalledApps() {
        if (this.mContext == null) {
            Logger.t(TAG).w("uploadInstalledApps return, not init", new Object[0]);
            return;
        }
        List<PackageInfo> queryPackageList = queryPackageList();
        if (queryPackageList == null) {
            Logger.t(TAG).w("None app found", new Object[0]);
            return;
        }
        UploadAppRequest uploadAppRequest = new UploadAppRequest();
        uploadAppRequest.version = System.currentTimeMillis() + "";
        HashMap hashMap = new HashMap();
        for (PackageInfo packageInfo : queryPackageList) {
            if (!filterPackage(this.mContext, packageInfo)) {
                UploadAppRequest.UploadAppInfo uploadAppInfo = new UploadAppRequest.UploadAppInfo();
                uploadAppInfo.versionCode = packageInfo.getLongVersionCode() + "";
                hashMap.put(packageInfo.packageName, uploadAppInfo);
            }
        }
        uploadAppRequest.app_infos = new Gson().toJson(hashMap);
        Logger.t(TAG).i("Upload apps:" + uploadAppRequest, new Object[0]);
        XpApiClient.getAppService().uploadApps(uploadAppRequest).enqueue(new Callback<XpApiResponse<Integer>>() { // from class: com.xiaopeng.appstore.appstore_biz.logic.push.UploadAppManager.2
            @Override // retrofit2.Callback
            public void onResponse(Call<XpApiResponse<Integer>> call, Response<XpApiResponse<Integer>> response) {
                if (response.isSuccessful()) {
                    Logger.t(UploadAppManager.TAG).i("Upload apps success:" + response.body(), new Object[0]);
                } else {
                    Logger.t(UploadAppManager.TAG).i("Upload apps finish, but return server error:" + response.body(), new Object[0]);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<XpApiResponse<Integer>> call, Throwable t) {
                Logger.t(UploadAppManager.TAG).i("Upload apps fail:" + t, new Object[0]);
            }
        });
    }

    private List<PackageInfo> queryPackageList() {
        Context context = this.mContext;
        if (context == null) {
            Logger.t(TAG).i("queryPackageList error, not init", new Object[0]);
            return null;
        }
        return context.getPackageManager().getInstalledPackages(0);
    }

    private static boolean filterPackage(Context context, PackageInfo info) {
        String str = info.packageName;
        return str.startsWith("com.android") || str.startsWith("android") || context.getPackageManager().getLaunchIntentForPackage(str) == null;
    }

    /* loaded from: classes2.dex */
    public static class BootCompleteReceiver extends BroadcastReceiver {
        private static final String TAG = "UploadAppBootCompleteReceiver";

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Logger.t(TAG).i("BootComplete", new Object[0]);
            setComponentEnabled(context, BootCompleteReceiver.class, false);
        }

        private static void setComponentEnabled(Context context, Class<?> klazz, boolean enabled) {
            String str = VuiConstants.ELEMENT_ENABLED;
            try {
                context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, klazz.getName()), enabled ? 1 : 2, 1);
                Printer t = Logger.t(TAG);
                Object[] objArr = new Object[2];
                objArr[0] = klazz.getName();
                objArr[1] = enabled ? VuiConstants.ELEMENT_ENABLED : "disabled";
                t.d(String.format("%s %s", objArr));
            } catch (Exception e) {
                Printer t2 = Logger.t(TAG);
                StringBuilder sb = new StringBuilder();
                Object[] objArr2 = new Object[2];
                objArr2[0] = klazz.getName();
                if (!enabled) {
                    str = "disabled";
                }
                objArr2[1] = str;
                t2.d(sb.append(String.format("%s could not be %s", objArr2)).append("ex:").append(e).toString());
            }
        }
    }
}
