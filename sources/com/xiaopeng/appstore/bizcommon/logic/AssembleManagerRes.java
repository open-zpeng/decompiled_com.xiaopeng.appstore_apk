package com.xiaopeng.appstore.bizcommon.logic;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.text.TextUtils;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appserver_common.NapaHandler;
import com.xiaopeng.appstore.bizcommon.R;
import com.xiaopeng.appstore.bizcommon.entities.AppConfigInfo;
import com.xiaopeng.appstore.bizcommon.entities.AppInfo;
import com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer;
import com.xiaopeng.appstore.bizcommon.entities.db.XpAppStoreDatabase;
import com.xiaopeng.appstore.bizcommon.logic.AssembleManagerRes;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.utils.DatabaseUtils;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.appstore.storeprovider.AssembleConstants;
import com.xiaopeng.appstore.storeprovider.AssembleInfo;
import com.xiaopeng.appstore.storeprovider.AssembleRequest;
import com.xiaopeng.appstore.storeprovider.AssembleResult;
import com.xiaopeng.appstore.storeprovider.IAssembleClientListener;
import com.xiaopeng.appstore.storeprovider.RequestContinuation;
import com.xiaopeng.appstore.storeprovider.StoreProviderManager;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
/* loaded from: classes2.dex */
public class AssembleManagerRes extends AppStoreAssembleManager implements IAssembleClientListener {
    private static final String CONFIG_DOWNLOAD_NAME_PREFIX = "config_";
    public static final String EXTRA_KEY_DOWNLOAD_URL = "extra_key_reserved_params_url";
    private static final int MSG_ASSEMBLE_PROGRESS_CHANGED = 200;
    private static final int MSG_ASSEMBLE_STATE_CHANGED = 100;
    private static final String TAG = "AssembleManagerRes";
    private static final String TAG_ASSEMBLE_INSTALL = "AssembleStageInstall";
    private HandlerThread mHandlerThread;
    private Handler mMainHandler;
    private Handler mWorkerHandler;
    private final Object mHandleEventLock = new Object();
    private final AppComponentManager.OnAppsChangedCallback mOnAppsChangedCallback = new AnonymousClass1();
    private final Map<String, AppComponentManager.OnApplyConfigCallback> mConfigCallback = new ConcurrentHashMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.bizcommon.logic.AssembleManagerRes$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass1 extends AppComponentManager.SimpleOnAppsChangedCallback {
        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageAdded(List<AppInfo> appInfoList) {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.SimpleOnAppsChangedCallback, com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageUpdated(String packageName) {
        }

        AnonymousClass1() {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageRemoved(final String packageName, UserHandle user) {
            Logger.t(AssembleManagerRes.TAG).i("onPackageRemoved pn=" + packageName, new Object[0]);
            AssembleManagerRes.this.mDeletingPackageList.remove(packageName);
            AssembleManagerRes.this.executeAssembleTask(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleManagerRes$1$FxhUA_o2JnjA_hD_6YRtk21sqJ4
                @Override // java.lang.Runnable
                public final void run() {
                    AssembleManagerRes.AnonymousClass1.this.lambda$onPackageRemoved$0$AssembleManagerRes$1(packageName);
                }
            });
            if (AssembleManagerRes.this.isDeleting(packageName)) {
                Logger.t(AssembleManagerRes.TAG).i("onPackageRemoved, dispatch delete complete event for deleting package:" + packageName, new Object[0]);
            }
        }

        public /* synthetic */ void lambda$onPackageRemoved$0$AssembleManagerRes$1(String str) {
            AssembleDataContainer findWithPn = AssembleManagerRes.this.findWithPn(str);
            if (findWithPn != null) {
                Logger.t(AssembleManagerRes.TAG).i("onPackageRemoved, remove downloader cache:" + str, new Object[0]);
                AssembleManagerRes.this.lambda$cancelWithPackage$7$AssembleManagerRes(str);
                AssembleManagerRes.this.removeCacheAsync(findWithPn.getDownloadUrl());
                findWithPn.setState(0);
                AssembleManagerRes.this.dispatchStateChange(findWithPn);
            }
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageChanged(String packageName, UserHandle user) {
            Logger.t(AssembleManagerRes.TAG).i("onPackageChanged, pn=" + packageName, new Object[0]);
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager
    public void init() {
        super.init();
        Logger.t(TAG).i(NapaHandler.METHOD_INIT, new Object[0]);
        StoreProviderManager.get().initialize(Utils.getApp());
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread == null || handlerThread.isInterrupted()) {
            HandlerThread handlerThread2 = new HandlerThread("AssembleHandlerTh");
            this.mHandlerThread = handlerThread2;
            handlerThread2.start();
        }
        this.mWorkerHandler = new Handler(this.mHandlerThread.getLooper(), new Handler.Callback() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleManagerRes$oJuMc0XsCFNCmxWKnjEGbrMgPaI
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return AssembleManagerRes.this.lambda$init$0$AssembleManagerRes(message);
            }
        });
        this.mMainHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleManagerRes$psiNJfzBkoaTIEhwGl0roGfoe6s
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return AssembleManagerRes.this.lambda$init$1$AssembleManagerRes(message);
            }
        });
        StoreProviderManager.get().registerListener(1000, null, this);
        AppComponentManager.get().addOnAppsChangedCallback(this.mOnAppsChangedCallback);
    }

    public /* synthetic */ boolean lambda$init$0$AssembleManagerRes(Message message) {
        int i = message.what;
        Object obj = message.obj;
        if (i == 100) {
            handleStateChange((AssembleInfo) obj);
            return false;
        } else if (i == 200) {
            handleAssembleProgress((AssembleInfo) obj);
            return false;
        } else {
            return false;
        }
    }

    public /* synthetic */ boolean lambda$init$1$AssembleManagerRes(Message message) {
        int i = message.what;
        Object obj = message.obj;
        if (i == 200) {
            dispatchProgressChange((AssembleDataContainer) obj);
            return false;
        }
        return false;
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager
    public void restoreDelay(long millis) {
        Logger.t(TAG).i("restoreDelay", new Object[0]);
        executeAssembleTask(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleManagerRes$4vR2TRFAhHlVgRZbdU99OCVRaxU
            @Override // java.lang.Runnable
            public final void run() {
                AssembleManagerRes.this.lambda$restoreDelay$2$AssembleManagerRes();
            }
        });
    }

    public /* synthetic */ void lambda$restoreDelay$2$AssembleManagerRes() {
        AssembleDataCache.get().load();
        List<AssembleInfo> assembleInfoList = StoreProviderManager.get().getAssembleInfoList(1000, null);
        HashSet hashSet = new HashSet();
        if (assembleInfoList != null && !assembleInfoList.isEmpty()) {
            for (AssembleInfo assembleInfo : assembleInfoList) {
                Logger.t(TAG).d("restoreDelay, infoItem:" + assembleInfo);
                handleStateChange(assembleInfo);
                hashSet.add(assembleInfo.getKey());
            }
        }
        ArrayList<AssembleDataContainer> arrayList = new ArrayList();
        for (AssembleDataContainer assembleDataContainer : AssembleDataCache.get().getUrlAssembleMap()) {
            if (!hashSet.contains(assembleDataContainer.getPackageName())) {
                arrayList.add(assembleDataContainer);
            }
        }
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        for (AssembleDataContainer assembleDataContainer2 : arrayList) {
            Logger.t(TAG).i("restore, remove:" + assembleDataContainer2, new Object[0]);
            arrayList2.add(assembleDataContainer2.getDownloadUrl());
        }
        removeCache(arrayList2);
        Logger.t(TAG).d("restoreDelay, END");
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager
    public void release() {
        super.release();
        Logger.t(TAG).i("release", new Object[0]);
        AppComponentManager.get().removeOnAppsChangedCallback(this.mOnAppsChangedCallback);
        StoreProviderManager.get().unregisterListener(this);
        this.mHandlerThread.quit();
        this.mHandlerThread = null;
    }

    @Override // com.xiaopeng.appstore.bizcommon.common.BizLifecycle
    public void start() {
        StoreProviderHelper.observe(getClass().getSimpleName());
        restoreDelay(0L);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager, com.xiaopeng.appstore.bizcommon.common.BizLifecycle
    public void stop() {
        Logger.t(TAG).i("onStop", new Object[0]);
        StoreProviderHelper.removeObserve(getClass().getSimpleName());
    }

    @Override // com.xiaopeng.appstore.bizcommon.common.BizLifecycle
    public void destroy() {
        clearSoftObservers();
    }

    @Override // com.xiaopeng.appstore.storeprovider.IAssembleClientListener
    public void onAssembleEvent(int eventType, AssembleInfo info) {
        if (info == null) {
            return;
        }
        if (eventType == 1000) {
            Logger.t(TAG).i("onAssembleEvent, EVENT_TYPE_STATE_CHANGED:" + info, new Object[0]);
            sendMsgToBg(100, info);
        } else if (eventType == 1001) {
            Logger.t(TAG).v("onAssembleEvent, EVENT_TYPE_PROGRESS_CHANGED: " + info, new Object[0]);
            sendMsgToBg(200, info);
        } else {
            Logger.t(TAG).w("onAssembleEvent, unhandled event:" + eventType + ", " + info, new Object[0]);
        }
    }

    private void handleXuiAppConfigAssembleState(AssembleInfo info) {
        String extraString = info.getExtraString(AssembleConstants.EXTRA_KEY_PARAMS_FILE_PATH);
        String extraString2 = info.getExtraString(AssembleConstants.EXTRA_KEY_PARAMS_URL);
        if (TextUtils.isEmpty(extraString) || TextUtils.isEmpty(extraString2)) {
            return;
        }
        String configAssemblePackageName = getConfigAssemblePackageName(info.getKey());
        if (TextUtils.isEmpty(configAssemblePackageName)) {
            return;
        }
        Logger.t(TAG).i("Persist app config data:" + configAssemblePackageName + ", configUrl:" + extraString2, new Object[0]);
        final AppConfigInfo appConfigInfo = new AppConfigInfo();
        appConfigInfo.setPackageName(configAssemblePackageName);
        appConfigInfo.setConfigUrl(extraString2);
        DatabaseUtils.tryExecute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleManagerRes$0xInd_v1UqnVjamzGau7XlBGjyk
            @Override // java.lang.Runnable
            public final void run() {
                XpAppStoreDatabase.getInstance().getAppConfigDao().insert(AppConfigInfo.this);
            }
        });
    }

    private void handleStateChange(AssembleInfo info) {
        if (AssembleInfo.isFinished(info.getState())) {
            handleXuiAppConfigAssembleState(info);
        }
        if (isConfigApplyAssemble(info)) {
            handleConfigAssembleState(info);
        } else {
            handleAppAssembleState(info);
        }
        if (AssembleInfo.isFinished(info.getState())) {
            this.mConfigCallback.remove(info.getKey());
        }
    }

    private String getConfigAssemblePackageName(String assembleKey) {
        return assembleKey.replace(CONFIG_DOWNLOAD_NAME_PREFIX, "");
    }

    private void handleConfigAssembleState(AssembleInfo info) {
        synchronized (this.mHandleEventLock) {
            int state = info.getState();
            if (AssembleInfo.isError(state)) {
                String key = info.getKey();
                String configAssemblePackageName = getConfigAssemblePackageName(key);
                AppComponentManager.OnApplyConfigCallback remove = this.mConfigCallback.remove(key);
                if (remove != null) {
                    remove.applyConfigError(configAssemblePackageName);
                }
            }
            if (AssembleInfo.isFinished(state)) {
                this.mConfigCallback.remove(info.getKey());
            }
        }
    }

    private void handleAppAssembleState(AssembleInfo info) {
        synchronized (this.mHandleEventLock) {
            String key = info.getKey();
            int state = info.getState();
            String extraString = info.getExtraString(EXTRA_KEY_DOWNLOAD_URL);
            Logger.t(TAG).i("handleAppAssembleState, url:" + extraString + ", " + info, new Object[0]);
            AssembleDataContainer findOrCreate = findOrCreate(extraString, null, key, null, info.getName());
            if (findOrCreate == null) {
                Logger.t(TAG).d("onStateChanged error data is null:" + key);
                return;
            }
            if (shouldRemoveCache(info)) {
                removeCacheAsync(findOrCreate.getDownloadUrl());
            } else {
                updateCacheAsync(findOrCreate);
            }
            findOrCreate.setProgress(info.getProgress());
            if (state == 1) {
                findOrCreate.setState(5);
                dispatchStateChange(findOrCreate);
            } else if (state == 2) {
                findOrCreate.setState(10001);
                dispatchStateChange(findOrCreate);
            } else if (AssembleInfo.isRunning(state)) {
                if (state == 101) {
                    findOrCreate.setState(1);
                    dispatchStateChange(findOrCreate);
                } else if (state == 102) {
                    findOrCreate.setState(3);
                    dispatchStateChange(findOrCreate);
                } else {
                    Logger.t(TAG).w("onStateChanged, unhandled this RUNNING state:" + key, new Object[0]);
                }
            } else if (AssembleInfo.isPaused(state)) {
                findOrCreate.setState(2);
                dispatchStateChange(findOrCreate);
            } else if (AssembleInfo.isCompleted(state)) {
                if (AppComponentManager.get().isInstalled(findOrCreate.getPackageName())) {
                    Logger.t(TAG).d("onStateChanged complete, installed:" + key);
                    r9 = hasNewVersion(findOrCreate.getPackageName()) ? 6 : 4;
                } else {
                    Logger.t(TAG).d("onStateChanged complete, not installed:" + key);
                }
                findOrCreate.setState(r9);
                dispatchStateChange(findOrCreate);
            } else if (AssembleInfo.isError(state)) {
                findOrCreate.setState(101);
                dispatchStateChange(findOrCreate);
            } else if (AssembleInfo.isCancelled(state)) {
                findOrCreate.setState(hasNewVersion(findOrCreate.getPackageName()) ? 6 : 0);
                dispatchStateChange(findOrCreate);
            } else {
                Logger.t(TAG).w("onStateChanged, unhandled this state:" + key, new Object[0]);
            }
        }
    }

    private void handleAssembleProgress(AssembleInfo info) {
        synchronized (this.mHandleEventLock) {
            Logger.t(TAG).v("handleAssembleProgress:" + info, new Object[0]);
            if (isConfigApplyAssemble(info)) {
                return;
            }
            AssembleDataContainer findWithPn = findWithPn(info.getKey());
            if (findWithPn == null) {
                Logger.t(TAG).d("handleAppAssembleProgress error data is null. info:" + info);
                return;
            }
            findWithPn.setProgress(info.getProgress());
            findWithPn.setSpeedPerSecond(info.getExtraLong(AssembleConstants.EXTRA_KEY_PARAMS_SPEED));
            updateCache(findWithPn);
            sendMsgToMain(200, findWithPn);
        }
    }

    private boolean isConfigApplyAssemble(AssembleInfo info) {
        return info.getKey().startsWith(CONFIG_DOWNLOAD_NAME_PREFIX);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeAssembleTask(Runnable runnable) {
        this.mWorkerHandler.post(runnable);
    }

    private void sendMsgToBg(int what, Object obj) {
        this.mWorkerHandler.obtainMessage(what, obj).sendToTarget();
    }

    private void sendMsgToMain(int what, Object obj) {
        this.mMainHandler.obtainMessage(what, obj).sendToTarget();
    }

    private boolean isAssembleFinished(AssembleInfo info) {
        if (info == null) {
            return false;
        }
        return AssembleInfo.isFinished(info.getState());
    }

    private boolean shouldRemoveCache(AssembleInfo info) {
        if (info == null) {
            return false;
        }
        int state = info.getState();
        return !AssembleInfo.isError(state) && AssembleInfo.isFinished(state);
    }

    private AssembleDataContainer generateData(String downloadUrl, String configUrl, String packageName, String label, String iconUrl) {
        AssembleDataContainer assembleDataContainer = new AssembleDataContainer();
        assembleDataContainer.setDownloadUrl(downloadUrl);
        assembleDataContainer.setConfigUrl(configUrl);
        assembleDataContainer.setPackageName(packageName);
        assembleDataContainer.setIconUrl(iconUrl);
        assembleDataContainer.setAppLabel(label);
        return assembleDataContainer;
    }

    private void enqueueRequest(String downloadUrl, String md5, String configUrl, String configMd5, String packageName, String iconUrl, String label) {
        Logger.t(TAG).i("enqueueRequest start:" + label, new Object[0]);
        AssembleDataContainer findOrCreate = findOrCreate(downloadUrl, configUrl, packageName, iconUrl, label);
        findOrCreate.setState(5);
        dispatchStateChange(findOrCreate);
        AssembleResult assemble = StoreProviderManager.get().assemble(AssembleRequest.enqueue(1000, packageName, label).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_START_DOWNLOAD_SHOW_TOAST, false).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_URL, configUrl).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_MD5_1, configMd5).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_MD5_2, md5).setDownloadUrl(downloadUrl).setIconUrl(iconUrl).request());
        if (assemble == null || !assemble.isSuccessful()) {
            Logger.t(TAG).i("enqueueRequest fail:" + label + ", " + assemble, new Object[0]);
            findOrCreate.setState(0);
            dispatchStateChange(findOrCreate);
            removeCacheAsync(downloadUrl);
            return;
        }
        Logger.t(TAG).i("enqueueRequest success:" + label + ", " + assemble, new Object[0]);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager
    public void start(final String downloadUrl, final String packageName, final String label, final String iconUrl) {
        executeAssembleTask(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleManagerRes$x2gO9gUQLRxBlw21oYxhLKo5POg
            @Override // java.lang.Runnable
            public final void run() {
                AssembleManagerRes.this.lambda$start$4$AssembleManagerRes(downloadUrl, packageName, iconUrl, label);
            }
        });
    }

    public /* synthetic */ void lambda$start$4$AssembleManagerRes(String str, String str2, String str3, String str4) {
        enqueueRequest(str, null, null, null, str2, str3, str4);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager
    public void startWithConfig(final List<AssembleDataContainer> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        executeAssembleTask(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleManagerRes$rXMR_vDRgkrBncUCffWsO2nSIvs
            @Override // java.lang.Runnable
            public final void run() {
                AssembleManagerRes.this.lambda$startWithConfig$5$AssembleManagerRes(dataList);
            }
        });
    }

    public /* synthetic */ void lambda$startWithConfig$5$AssembleManagerRes(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AssembleDataContainer assembleDataContainer = (AssembleDataContainer) it.next();
            startWithConfig(assembleDataContainer.getDownloadUrl(), assembleDataContainer.getMd5(), assembleDataContainer.getConfigUrl(), assembleDataContainer.getConfigMd5(), assembleDataContainer.getPackageName(), assembleDataContainer.getAppLabel(), assembleDataContainer.getIconUrl());
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager
    public void startWithConfig(final String downloadUrl, final String md5, final String configUrl, final String configMd5, final String packageName, final String label, final String iconUrl) {
        if (TextUtils.isEmpty(downloadUrl)) {
            Logger.t(TAG).d("startWithConfig: downloadUrl is null, return");
        } else if (TextUtils.isEmpty(configUrl)) {
            Logger.t(TAG).d("startWithConfig: configUrl is null, start download apk(%s) directly.", downloadUrl);
            start(downloadUrl, packageName, label, iconUrl);
        } else {
            executeAssembleTask(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleManagerRes$f9bVWcx_I-rLwe6kv-cN62fBtC0
                @Override // java.lang.Runnable
                public final void run() {
                    AssembleManagerRes.this.lambda$startWithConfig$6$AssembleManagerRes(packageName, configUrl, configMd5, downloadUrl, md5, iconUrl, label);
                }
            });
        }
    }

    public /* synthetic */ void lambda$startWithConfig$6$AssembleManagerRes(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        Logger.t("AssembleStage").i("Stage: 1.Start with config. Data: pn=" + str + " configUrl=" + str2 + ", configMd5:" + str3 + " url=" + str4 + ", md5:" + str5, new Object[0]);
        enqueueRequest(str4, str5, str2, str3, str, str6, str7);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager
    public void cancelWithPackage(final String packageName) {
        executeAssembleTask(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleManagerRes$HMRgjCYQzzvTErdwgNIIxQnuCv0
            @Override // java.lang.Runnable
            public final void run() {
                AssembleManagerRes.this.lambda$cancelWithPackage$7$AssembleManagerRes(packageName);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: cancelInternal */
    public void lambda$cancelWithPackage$7$AssembleManagerRes(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            Logger.t(TAG).w("cancelInternal error, pn is empty:" + packageName, new Object[0]);
        } else {
            StoreProviderManager.get().assemble(AssembleRequest.cancel(1000, packageName));
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager
    public void pauseWithPackage(final String packageName) {
        executeAssembleTask(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleManagerRes$j2ExJpDSlqxRh8Wo7h1Z_wo1chU
            @Override // java.lang.Runnable
            public final void run() {
                AssembleManagerRes.this.lambda$pauseWithPackage$8$AssembleManagerRes(packageName);
            }
        });
    }

    public /* synthetic */ void lambda$pauseWithPackage$8$AssembleManagerRes(String str) {
        if (findWithPn(str) == null) {
            Logger.t(TAG).w("pause error, packageName is null", new Object[0]);
        } else {
            StoreProviderManager.get().assemble(AssembleRequest.pause(1000, str));
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager
    public void delete(final String packageName, UserHandle user, final String appName, final Consumer<String> onFailed) {
        Logger.t(TAG).i("delete start:" + packageName + ", " + appName, new Object[0]);
        this.mDeletingPackageList.add(packageName);
        executeAssembleTask(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleManagerRes$y5yl5XzHGIE3FFQZpmU7RbI0KOI
            @Override // java.lang.Runnable
            public final void run() {
                AssembleManagerRes.this.lambda$delete$9$AssembleManagerRes(packageName, appName, onFailed);
            }
        });
    }

    public /* synthetic */ void lambda$delete$9$AssembleManagerRes(String str, String str2, Consumer consumer) {
        AssembleDataContainer findWithPn = findWithPn(str);
        if (findWithPn == null) {
            findWithPn = new AssembleDataContainer();
            findWithPn.setPackageName(str);
            findWithPn.setAppLabel(str2);
        }
        int state = findWithPn.getState();
        if (state == 3) {
            if (consumer != null) {
                consumer.accept(ResUtils.getString(R.string.delete_forbidden));
            }
            this.mDeletingPackageList.remove(str);
            Logger.t(TAG).i("delete forbidden, pn=" + str + "state = " + stateToStr(state), new Object[0]);
            return;
        }
        findWithPn.setState(8);
        dispatchStateChange(findWithPn);
        Logger.t(TAG).i("delete, cancel download first.", new Object[0]);
        lambda$cancelWithPackage$7$AssembleManagerRes(str);
        if (AppComponentManager.get().isInstalled(str)) {
            Logger.t(TAG).i("delete, start uninstall, pn=" + str, new Object[0]);
            Logger.t(TAG_ASSEMBLE_INSTALL).d("1.pendingUninstall, pn=%s, threadId=%s, name=%s.", str, Long.valueOf(Thread.currentThread().getId()), Thread.currentThread().getName());
            AppComponentManager.get().commitUninstall(str);
            EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_LIST, EventEnum.OPEN_APP_FROM_APP_LIST, 7, str, str2);
            Logger.t(TAG_ASSEMBLE_INSTALL).d("2.uninstallFinish, pn=%s, threadId=%s, name=%s.", str, Long.valueOf(Thread.currentThread().getId()), Thread.currentThread().getName());
            return;
        }
        Logger.t(TAG).i("delete warning, not installed. pn=" + str, new Object[0]);
        this.mDeletingPackageList.remove(str);
        if (findWithPn != null) {
            findWithPn.setState(0);
            dispatchStateChange(findWithPn);
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager
    public void updateLocalConfig(final String configUrl, final String configMd5, final String packageName) {
        executeAssembleTask(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleManagerRes$E8uF1FcuXvBkvokS5Zga-xPQN5g
            @Override // java.lang.Runnable
            public final void run() {
                AssembleManagerRes.lambda$updateLocalConfig$10(packageName, configUrl, configMd5);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$updateLocalConfig$10(String str, String str2, String str3) {
        AssembleResult assemble = StoreProviderManager.get().assemble(new RequestContinuation(1000, CONFIG_DOWNLOAD_NAME_PREFIX + str).setNotificationVisibility(1).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_URL, str2).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_MD5_1, str3).request());
        if (assemble == null || !assemble.isSuccessful()) {
            Logger.t(TAG).i("updateLocalConfig fail:" + str + ", url:" + str2, new Object[0]);
        } else {
            Logger.t(TAG).i("updateLocalConfig enqueue success:" + str + ", url:" + str2 + ", configMd5:" + str3, new Object[0]);
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager
    public void startLocalWithConfig(final String configUrl, final String configMd5, final String packageName, final String apkMd5, final File file, final AppComponentManager.OnApplyConfigCallback callback) {
        executeAssembleTask(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleManagerRes$nOu5xpoCDoJhk6AxZRJmNock64E
            @Override // java.lang.Runnable
            public final void run() {
                AssembleManagerRes.this.lambda$startLocalWithConfig$11$AssembleManagerRes(packageName, configUrl, configMd5, file, apkMd5, callback);
            }
        });
    }

    public /* synthetic */ void lambda$startLocalWithConfig$11$AssembleManagerRes(String str, String str2, String str3, File file, String str4, AppComponentManager.OnApplyConfigCallback onApplyConfigCallback) {
        String str5 = CONFIG_DOWNLOAD_NAME_PREFIX + str;
        AssembleResult assemble = StoreProviderManager.get().assemble(new RequestContinuation(1000, CONFIG_DOWNLOAD_NAME_PREFIX + str).setNotificationVisibility(1).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_URL, str2).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_MD5_1, str3).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_FILE_PATH, file.getPath()).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_MD5_2, str4).request());
        if (assemble == null || !assemble.isSuccessful()) {
            Logger.t(TAG).i("startLocalWithConfig fail:" + str + ", url:" + str2, new Object[0]);
            this.mConfigCallback.remove(str5);
            return;
        }
        Logger.t(TAG).i("startLocalWithConfig enqueue success:" + str + ", configUrl:" + str2 + ", configMd5:" + str3 + ", apkMd5:" + str4, new Object[0]);
        this.mConfigCallback.put(str5, onApplyConfigCallback);
    }
}
