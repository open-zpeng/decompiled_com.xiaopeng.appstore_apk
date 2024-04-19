package com.xiaopeng.appstore.appserver_common;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.ListUpdateCallback;
import com.google.gson.reflect.TypeToken;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.applet_biz.datamodel.MiniProgRepository;
import com.xiaopeng.appstore.applist_biz.logic.AppListCache;
import com.xiaopeng.appstore.applist_biz.model.BaseAppItem;
import com.xiaopeng.appstore.applist_biz.model.FixedAppItem;
import com.xiaopeng.appstore.applist_biz.model.LoadingAppItem;
import com.xiaopeng.appstore.applist_biz.model.LocalAppData;
import com.xiaopeng.appstore.applist_biz.model.NapaAppItem;
import com.xiaopeng.appstore.applist_biz.model.NormalAppItem;
import com.xiaopeng.appstore.appserver_common.ServerProvider;
import com.xiaopeng.appstore.appserver_common.module.AppStore;
import com.xiaopeng.appstore.appserver_common.parser.Parser;
import com.xiaopeng.appstore.appserver_common.utils.LocalUtils;
import com.xiaopeng.appstore.appserver_common.v2.AppListListener;
import com.xiaopeng.appstore.appserver_common.v2.RequestProtoListener;
import com.xiaopeng.appstore.appstore_biz.bizusb.datamodel.entities.LocalApkInfo;
import com.xiaopeng.appstore.appstore_biz.bizusb.logic.USBStateManager;
import com.xiaopeng.appstore.appstore_biz.datamodel.api.XpApiClient;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailData;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailRequest;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppListRequest;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppRequestContainer;
import com.xiaopeng.appstore.appstore_biz.logic.CheckUpdateManager;
import com.xiaopeng.appstore.bizcommon.logic.AgreementDialogHelper;
import com.xiaopeng.appstore.bizcommon.logic.AppStateContract;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.utils.AccountUtils;
import com.xiaopeng.appstore.bizcommon.utils.PackageUtils;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.FileUtils;
import com.xiaopeng.appstore.libcommon.utils.GsonUtil;
import com.xiaopeng.appstore.libcommon.utils.NumberUtils;
import com.xiaopeng.appstore.libcommon.utils.PackageMonitor;
import com.xiaopeng.appstore.libcommon.utils.SPUtils;
import com.xiaopeng.appstore.protobuf.AppDetailResponseProto;
import com.xiaopeng.appstore.protobuf.AppIconDataProto;
import com.xiaopeng.appstore.protobuf.AppIndex;
import com.xiaopeng.appstore.protobuf.AppIndexList;
import com.xiaopeng.appstore.protobuf.AppItemProto;
import com.xiaopeng.appstore.protobuf.AppListProto;
import com.xiaopeng.appstore.protobuf.AppStoreHomeResponseProto;
import com.xiaopeng.appstore.protobuf.AppUpdateResponseProto;
import com.xiaopeng.appstore.protobuf.AppletGroupListProto;
import com.xiaopeng.appstore.protobuf.UsbAppListProto;
import com.xiaopeng.appstore.storeprovider.AssembleConstants;
import com.xiaopeng.appstore.storeprovider.AssembleInfo;
import com.xiaopeng.appstore.storeprovider.AssembleRequest;
import com.xiaopeng.appstore.storeprovider.AssembleResult;
import com.xiaopeng.appstore.storeprovider.IAssembleClientListener;
import com.xiaopeng.appstore.storeprovider.StoreProviderManager;
import com.xiaopeng.appstore.xpcommon.ApiEnvHelper;
import com.xiaopeng.appstore.xpcommon.OpenAppConfigBean;
import com.xiaopeng.appstore.xpcommon.privacy.PrivacyUtils;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.lib.apirouter.server.IServicePublisher;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import okhttp3.ResponseBody;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class ServerProvider implements IServicePublisher, IAssembleClientListener {
    private static final String DEBUG_DIALOG_ACCOUNT_SWITCH_KEY = "debug_dialog_account_switch_key";
    private static final String TAG = "ServerProvider";
    private AppListCache.AppListListener appListListener;
    private HandlerThread mAppHandlerTh;
    private AppListListener mAppListOutListener;
    private AppListProto mAppListProto;
    private Handler mAppWorkHandler;
    private AppletListener mAppletOutListener;
    private Context mContext;
    private AsyncListDiffer<AppItemProto> mDiffer;
    private HandlerThread mHandlerTh;
    private MiniProgRepository mMiniProgRepository;
    private Map<String, OpenAppConfigBean> mOpenAppConfigs;
    private PackageMonitor mPackageMonitor;
    private USBStateManager mUSBStateManager;
    private Consumer<AppUpdateResponseProto> mUpdateListener;
    private UsbAppListener mUsbAppOutListener;
    private UsbEntryListener mUsbEntryOutListener;
    private Handler mWorkHandler;
    private List<BaseAppItem> mOriginList = new LinkedList();
    private final Object mListenerLock = new Object();
    private final Set<DownloadListener> mDownloadListenerSet = new HashSet();
    private final Map<String, Set<DownloadListener>> mListenerMap = new HashMap();
    private final Observer<List<AppDetailData>> mUpdateDataObserver = new Observer() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$it9nplnr3FKeugNxo3oUhLikum8
        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            ServerProvider.this.lambda$new$10$ServerProvider((List) obj);
        }
    };
    private final Observer<Integer> mUpdateCountObserver = new Observer() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$qe-pz00piTkKbAwZgcJI8PkpRbA
        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            ServerProvider.lambda$new$11((Integer) obj);
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$new$11(Integer num) {
    }

    public ServerProvider() {
        Logger.t(TAG).i("create :" + hashCode(), new Object[0]);
    }

    public void init(Context context) {
        this.mContext = context;
        initInternal();
    }

    protected void initInternal() {
        HandlerThread handlerThread = this.mHandlerTh;
        if (handlerThread != null && handlerThread.isAlive()) {
            Log.w(TAG, "AppStore ServerProvider already initialized!");
            return;
        }
        Logger.t(TAG).i("init:" + hashCode(), new Object[0]);
        HandlerThread handlerThread2 = new HandlerThread("AppStoreUnityService");
        this.mHandlerTh = handlerThread2;
        handlerThread2.start();
        this.mWorkHandler = new Handler(this.mHandlerTh.getLooper());
        HandlerThread handlerThread3 = this.mAppHandlerTh;
        if (handlerThread3 != null) {
            handlerThread3.quit();
        }
        HandlerThread handlerThread4 = new HandlerThread("UnityAppHandler");
        this.mAppHandlerTh = handlerThread4;
        handlerThread4.start();
        this.mAppWorkHandler = new Handler(this.mAppHandlerTh.getLooper());
        this.mDiffer = new AsyncListDiffer<>(new AnonymousClass1(), new AsyncDifferConfig.Builder(new AppItemDiffCallback()).build());
        this.appListListener = new AnonymousClass2();
        AppListCache.get().registerAppListListener(this.appListListener);
        MiniProgRepository miniProgRepository = new MiniProgRepository();
        this.mMiniProgRepository = miniProgRepository;
        miniProgRepository.setLoadListener(new MiniProgRepository.AppletLoadListener() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$K6hcyqyS8XG6ZLiynLP739HAcns
            @Override // com.xiaopeng.appstore.applet_biz.datamodel.MiniProgRepository.AppletLoadListener
            public final void onAppletLoaded(List list) {
                ServerProvider.this.lambda$initInternal$0$ServerProvider(list);
            }
        });
        USBStateManager uSBStateManager = new USBStateManager();
        this.mUSBStateManager = uSBStateManager;
        uSBStateManager.registerUSBState();
        this.mUSBStateManager.setCallback(new USBStateManager.OnUsbMgrCallback() { // from class: com.xiaopeng.appstore.appserver_common.ServerProvider.3
            @Override // com.xiaopeng.appstore.appstore_biz.bizusb.logic.USBStateManager.OnUsbMgrCallback
            public void onShowUsbEntry(boolean loading) {
                if (ServerProvider.this.mUsbEntryOutListener != null) {
                    ServerProvider.this.mUsbEntryOutListener.onShowUsbEntry(loading);
                }
            }

            @Override // com.xiaopeng.appstore.appstore_biz.bizusb.logic.USBStateManager.OnUsbMgrCallback
            public void onHideUsbEntry() {
                if (ServerProvider.this.mUsbEntryOutListener != null) {
                    ServerProvider.this.mUsbEntryOutListener.onHideUsbEntry();
                }
            }
        });
        PackageMonitor packageMonitor = new PackageMonitor() { // from class: com.xiaopeng.appstore.appserver_common.ServerProvider.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaopeng.appstore.libcommon.utils.PackageMonitor
            public void onPackageUpdated(String packageName) {
                tryDispatchUsbAppChanged(packageName, 1);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaopeng.appstore.libcommon.utils.PackageMonitor
            public void onPackageAdded(String packageName) {
                tryDispatchUsbAppChanged(packageName, 1);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaopeng.appstore.libcommon.utils.PackageMonitor
            public void onPackageRemoved(String packageName) {
                tryDispatchUsbAppChanged(packageName, 101);
            }

            private void tryDispatchUsbAppChanged(String packageName, int changeToAppType) {
                List<LocalApkInfo> allApkList = ServerProvider.this.mUSBStateManager.getAllApkList();
                if (allApkList == null) {
                    return;
                }
                int i = 0;
                for (LocalApkInfo localApkInfo : allApkList) {
                    if (localApkInfo.getPackageName().equals(packageName)) {
                        Logger.t(ServerProvider.TAG).i("Dispatch usb app add:" + packageName + ", appType:" + changeToAppType, new Object[0]);
                        if (ServerProvider.this.mUsbAppOutListener != null) {
                            ServerProvider.this.mUsbAppOutListener.onUsbAppChanged(i, Parser.parseUsbAppItem(localApkInfo, changeToAppType));
                        }
                    }
                    i++;
                }
            }
        };
        this.mPackageMonitor = packageMonitor;
        packageMonitor.register(this.mContext, this.mWorkHandler);
        readConfigs();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.appserver_common.ServerProvider$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass1 implements ListUpdateCallback {
        AnonymousClass1() {
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onInserted(final int position, final int count) {
            ServerProvider.this.mAppWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$1$8Ly7MVJ0lozX5bhjqTPPpDEOyzI
                @Override // java.lang.Runnable
                public final void run() {
                    ServerProvider.AnonymousClass1.this.lambda$onInserted$0$ServerProvider$1(position, count);
                }
            });
        }

        public /* synthetic */ void lambda$onInserted$0$ServerProvider$1(int i, int i2) {
            Logger.t(ServerProvider.TAG).i("onAppInserted, pos:" + i + ", count:" + i2, new Object[0]);
            if (ServerProvider.this.mAppListOutListener != null) {
                ServerProvider.this.mAppListOutListener.onAppInserted(i, i2);
            }
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onRemoved(final int position, final int count) {
            ServerProvider.this.mAppWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$1$6egElsr0PVsNeGqfkYu7xXQuvwY
                @Override // java.lang.Runnable
                public final void run() {
                    ServerProvider.AnonymousClass1.this.lambda$onRemoved$1$ServerProvider$1(position, count);
                }
            });
        }

        public /* synthetic */ void lambda$onRemoved$1$ServerProvider$1(int i, int i2) {
            Logger.t(ServerProvider.TAG).i("onAppRemoved, pos:" + i + ", count:" + i2, new Object[0]);
            if (ServerProvider.this.mAppListOutListener != null) {
                ServerProvider.this.mAppListOutListener.onAppRemoved(i, i2);
            }
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onMoved(final int fromPosition, final int toPosition) {
            ServerProvider.this.mAppWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$1$PEGBrw7wHxOcbUGJPosiNuILxj0
                @Override // java.lang.Runnable
                public final void run() {
                    ServerProvider.AnonymousClass1.this.lambda$onMoved$2$ServerProvider$1(fromPosition, toPosition);
                }
            });
        }

        public /* synthetic */ void lambda$onMoved$2$ServerProvider$1(int i, int i2) {
            Logger.t(ServerProvider.TAG).i("onAppMoved, from:" + i + ", to:" + i2, new Object[0]);
            if (ServerProvider.this.mAppListOutListener != null) {
                ServerProvider.this.mAppListOutListener.onAppMoved(i, i2);
            }
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onChanged(final int position, final int count, final Object payload) {
            ServerProvider.this.mAppWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$1$l3wO_gQM8012RZk1XVbEIdWxtK4
                @Override // java.lang.Runnable
                public final void run() {
                    ServerProvider.AnonymousClass1.this.lambda$onChanged$3$ServerProvider$1(position, count, payload);
                }
            });
        }

        public /* synthetic */ void lambda$onChanged$3$ServerProvider$1(int i, int i2, Object obj) {
            Logger.t(ServerProvider.TAG).i("onAppChanged, pos:" + i + ", count:" + i2 + ", payload:" + obj, new Object[0]);
            if (ServerProvider.this.mAppListOutListener != null) {
                ServerProvider.this.mAppListOutListener.onAppChanged(i, i2, obj);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.appserver_common.ServerProvider$2  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass2 implements AppListCache.AppListListener {
        @Override // com.xiaopeng.appstore.applist_biz.logic.AppListCache.AppListListener
        public void onAllIconsChanged() {
        }

        AnonymousClass2() {
        }

        @Override // com.xiaopeng.appstore.applist_biz.logic.AppListCache.AppListListener
        public void onAppListChanged(final List<BaseAppItem> appList) {
            ServerProvider.this.mAppWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$2$qmqOAOU0DXl249bWBTWiyGcpiU8
                @Override // java.lang.Runnable
                public final void run() {
                    ServerProvider.AnonymousClass2.this.lambda$onAppListChanged$0$ServerProvider$2(appList);
                }
            });
        }

        public /* synthetic */ void lambda$onAppListChanged$0$ServerProvider$2(List list) {
            List emptyList;
            ServerProvider.this.mOriginList.clear();
            if (list != null && !list.isEmpty()) {
                ArrayList<BaseAppItem> arrayList = new ArrayList(list);
                Logger.t(ServerProvider.TAG).i("onAppListChanged: " + arrayList, new Object[0]);
                ServerProvider.this.mOriginList.addAll(arrayList);
                emptyList = new ArrayList(arrayList.size());
                for (BaseAppItem baseAppItem : arrayList) {
                    if (!(baseAppItem instanceof NapaAppItem)) {
                        emptyList.add(ServerProvider.this.parseAppItemProto(baseAppItem));
                    }
                }
            } else {
                emptyList = Collections.emptyList();
            }
            ServerProvider.this.mAppListProto = AppListProto.newBuilder().addAllAppItem(emptyList).build();
            ServerProvider.this.mDiffer.submitList(ServerProvider.this.mAppListProto.getAppItemList());
            Logger.t(ServerProvider.TAG).i("onAppListChanged dispatch", new Object[0]);
        }

        @Override // com.xiaopeng.appstore.applist_biz.logic.AppListCache.AppListListener
        public void onAppItemChanged(BaseAppItem appItem) {
            Logger.t(ServerProvider.TAG).i("onAppItemChanged: " + appItem, new Object[0]);
        }

        @Override // com.xiaopeng.appstore.applist_biz.logic.AppListCache.AppListListener
        public void onAppAdded(int index, BaseAppItem appItem) {
            Logger.t(ServerProvider.TAG).i("onAppAdded dispatch: " + index + ", " + appItem, new Object[0]);
        }

        @Override // com.xiaopeng.appstore.applist_biz.logic.AppListCache.AppListListener
        public void onAppChanged(int index, BaseAppItem appItem) {
            Logger.t(ServerProvider.TAG).i("onAppChanged dispatch: " + index + ", " + appItem, new Object[0]);
        }

        @Override // com.xiaopeng.appstore.applist_biz.logic.AppListCache.AppListListener
        public void onAppRemoved(int index, BaseAppItem appItem) {
            Logger.t(ServerProvider.TAG).i("onAppRemoved dispatch: " + index + ", " + appItem, new Object[0]);
        }
    }

    public /* synthetic */ void lambda$initInternal$0$ServerProvider(List list) {
        AppletGroupListProto parseAppletGroupList = Parser.parseAppletGroupList(list);
        if (parseAppletGroupList != null) {
            AppletListener appletListener = this.mAppletOutListener;
            if (appletListener != null) {
                appletListener.onAppletListChanged(parseAppletGroupList);
                return;
            }
            return;
        }
        Logger.t(TAG).w("OnAppletListChanged, list is null.", new Object[0]);
    }

    private void readConfigs() {
        this.mWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$y1thQyndr6b0Wwq8cFqvms_4Bgw
            @Override // java.lang.Runnable
            public final void run() {
                ServerProvider.this.lambda$readConfigs$1$ServerProvider();
            }
        });
    }

    public /* synthetic */ void lambda$readConfigs$1$ServerProvider() {
        Logger.t(TAG).i("readConfigs start", new Object[0]);
        this.mOpenAppConfigs = (Map) GsonUtil.fromJson(FileUtils.loadFileFromAsset("openAppConfigs.json"), new TypeToken<Map<String, OpenAppConfigBean>>() { // from class: com.xiaopeng.appstore.appserver_common.ServerProvider.5
        }.getType());
        Logger.t(TAG).i("readConfigs:" + this.mOpenAppConfigs, new Object[0]);
    }

    public void release() {
        Logger.t(TAG).i("release, ctx:" + this.mContext + ", this:" + hashCode(), new Object[0]);
        this.mAppListProto = null;
        MiniProgRepository miniProgRepository = this.mMiniProgRepository;
        if (miniProgRepository != null) {
            miniProgRepository.release();
        }
        USBStateManager uSBStateManager = this.mUSBStateManager;
        if (uSBStateManager != null) {
            uSBStateManager.unRegisterUSBState();
        }
        PackageMonitor packageMonitor = this.mPackageMonitor;
        if (packageMonitor != null) {
            packageMonitor.unregister();
        }
        AppListCache.get().unregisterAppListListener(this.appListListener);
        synchronized (this.mListenerLock) {
            this.mDownloadListenerSet.clear();
            this.mListenerMap.clear();
        }
        StoreProviderManager.get().stopObserve();
        HandlerThread handlerThread = this.mHandlerTh;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
    }

    public String openNapaApp(String idString, String param) {
        String str = "";
        if (this.mOpenAppConfigs == null) {
            Logger.t(TAG).w("openNapaApp error, config not init yet! id:" + idString + ", param:" + param, new Object[0]);
            return "";
        }
        Uri.Builder builder = new Uri.Builder();
        OpenAppConfigBean openAppConfigBean = this.mOpenAppConfigs.get(idString);
        if (openAppConfigBean != null) {
            if (!TextUtils.isEmpty(openAppConfigBean.authority)) {
                builder.authority(openAppConfigBean.authority).path("openNapaApp").appendQueryParameter("param", param);
                try {
                    str = (String) ApiRouter.route(builder.build());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            if (TextUtils.isEmpty(str)) {
                str = openAppConfigBean.fallbackUrl;
            }
        }
        Logger.t(TAG).i("openApp, id:" + idString + ", with:" + openAppConfigBean + ", ret:" + str, new Object[0]);
        return str;
    }

    public void enableDebug(boolean debug) {
        Logger.t(TAG).i("enableDebug:" + debug, new Object[0]);
        SPUtils.getInstance(TAG).put("debug_dialog_account_switch_key", debug);
        AccountUtils.DEBUG = debug;
    }

    public boolean isInDebug() {
        return SPUtils.getInstance(TAG).getBoolean("debug_dialog_account_switch_key");
    }

    public int getHttpHost() {
        String host = ApiEnvHelper.getHost();
        if (host.equals(ApiEnvHelper.APP_STORE_HOST_TEST)) {
            return 1;
        }
        return host.equals(ApiEnvHelper.APP_STORE_HOST_PRE) ? 2 : 0;
    }

    public void switchHttpPreHost() {
        Logger.t(TAG).i("switchHttpPreHost and resetClient", new Object[0]);
        ApiEnvHelper.switchPre();
        XpApiClient.reset();
    }

    public void switchHttpTestHost() {
        Logger.t(TAG).i("switchHttpTestHost and resetClient", new Object[0]);
        ApiEnvHelper.switchTest();
        XpApiClient.reset();
    }

    public void resetHttpHost() {
        Logger.t(TAG).i("resetHttpHost and resetClient", new Object[0]);
        ApiEnvHelper.resetEnv();
        XpApiClient.reset();
    }

    @Override // com.xiaopeng.appstore.storeprovider.IAssembleClientListener
    public void onAssembleEvent(int eventType, AssembleInfo info) {
        if (eventType == 1000) {
            Logger.t(TAG).i("onAssembleEvent, EVENT_TYPE_STATE_CHANGED:" + info, new Object[0]);
            Logger.t(TAG).i("dispatchStateChanged:" + info, new Object[0]);
            dispatchStateChanged(info.getKey(), info.getState());
        } else if (eventType == 1001) {
            Logger.t(TAG).v("onAssembleEvent, EVENT_TYPE_PROGRESS_CHANGED: " + info, new Object[0]);
            dispatchProgressChanged(info.getKey(), info.getProgress());
        }
    }

    public void create() {
        AppComponentManager.get().create();
        AppListCache.get().create();
        AppStoreAssembleManager.get().create();
    }

    public void start() {
        AppComponentManager.get().start();
        AppListCache.get().start();
        AppStoreAssembleManager.get().start();
        observeUpdateData();
    }

    public void stop() {
        AppComponentManager.get().stop();
        AppListCache.get().stop();
        AppStoreAssembleManager.get().stop();
        stopObserveUpdateData();
    }

    public void destroy() {
        AppComponentManager.get().destroy();
        AppListCache.get().destroy();
        AppStoreAssembleManager.get().destroy();
    }

    public void setUsbEntryListener(UsbEntryListener listener) {
        this.mUsbEntryOutListener = listener;
    }

    public void setUsbAppListener(UsbAppListener listener) {
        this.mUsbAppOutListener = listener;
    }

    public void startLoadUsbApp() {
        USBStateManager uSBStateManager = this.mUSBStateManager;
        if (uSBStateManager != null) {
            uSBStateManager.loadUsbApp();
        } else {
            Logger.t(TAG).w("UsbStateManger not init", new Object[0]);
        }
    }

    public UsbAppListProto getUsbAppList() {
        USBStateManager uSBStateManager = this.mUSBStateManager;
        if (uSBStateManager != null) {
            return Parser.parseUsbAppList(uSBStateManager.getAllApkList(), new Function() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$uQ6xGnZ9pbMvt2qWWU2nCXeW4UY
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Boolean valueOf;
                    valueOf = Boolean.valueOf(AppComponentManager.get().isInstalled((String) obj));
                    return valueOf;
                }
            });
        }
        return null;
    }

    public void installUsbApp(final String packageName) {
        Logger.t(TAG).i("installUsbApp:" + packageName, new Object[0]);
        assertInit();
        this.mWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$q9fQ7Z7yY7ZUA3pyp1rQZyVaFRQ
            @Override // java.lang.Runnable
            public final void run() {
                ServerProvider.this.lambda$installUsbApp$3$ServerProvider(packageName);
            }
        });
    }

    public /* synthetic */ void lambda$installUsbApp$3$ServerProvider(String str) {
        LocalApkInfo localApkInfo;
        List<LocalApkInfo> allApkList = this.mUSBStateManager.getAllApkList();
        if (allApkList == null) {
            Logger.t(TAG).i("installUsbApp NO APK:" + str, new Object[0]);
            return;
        }
        Iterator<LocalApkInfo> it = allApkList.iterator();
        while (true) {
            if (!it.hasNext()) {
                localApkInfo = null;
                break;
            }
            localApkInfo = it.next();
            if (localApkInfo.getPackageName().equals(str)) {
                break;
            }
        }
        if (localApkInfo != null) {
            File file = localApkInfo.getFile();
            if (file != null && file.exists()) {
                PackageUtils.installAsync(this.mContext, file, localApkInfo.getPackageName(), null);
                return;
            } else {
                Logger.t(TAG).i("installUsbApp, apk file not exists:" + str + ", " + file, new Object[0]);
                return;
            }
        }
        Logger.t(TAG).i("installUsbApp NOT FOUND apk file:" + str, new Object[0]);
    }

    public void setAppletListener(AppletListener appletListener) {
        this.mAppletOutListener = appletListener;
    }

    public void loadAppletList() {
        MiniProgRepository miniProgRepository = this.mMiniProgRepository;
        if (miniProgRepository != null) {
            miniProgRepository.loadData();
        } else {
            Logger.t(TAG).w("loadAppletList error, not init.", new Object[0]);
        }
    }

    public void launchApplet(String id, String name) {
        MiniProgRepository miniProgRepository = this.mMiniProgRepository;
        if (miniProgRepository != null) {
            miniProgRepository.launch(id, name);
        } else {
            Logger.t(TAG).w("launchApplet error, not init. id:" + id, new Object[0]);
        }
    }

    public boolean isInstalled(String packageName, boolean useCache) {
        assertInit();
        if (useCache) {
            return AppComponentManager.get().isInstalled(packageName);
        }
        return AppComponentManager.get().getPackageInfo(packageName) != null;
    }

    public void setAppListListenerV2(AppListListener appListListener) {
        Logger.t(TAG).i("setAppListListenerV2: " + appListListener, new Object[0]);
        this.mAppListOutListener = appListListener;
    }

    public AppListProto getAppListProto() {
        return this.mAppListProto;
    }

    public boolean launchApp(String packageName) {
        return launchApp(packageName, 0);
    }

    public boolean launchApp(String packageName, String shareIdString) {
        return launchApp(packageName, NumberUtils.stringToInt(shareIdString, 0));
    }

    public boolean launchApp(String packageName, int shareId) {
        if (TextUtils.isEmpty(packageName)) {
            Logger.t(TAG).w("launchApp error: " + packageName + ", shareId:" + shareId, new Object[0]);
            return false;
        }
        if (shareId < 0) {
            Logger.t(TAG).w("launchApp, invalid shareId:" + shareId + ", pn:" + packageName, new Object[0]);
            shareId = 0;
        }
        for (BaseAppItem baseAppItem : this.mOriginList) {
            if ((baseAppItem instanceof NormalAppItem) && baseAppItem.packageName.equals(packageName)) {
                NormalAppItem normalAppItem = (NormalAppItem) baseAppItem;
                invokeSetSharedId(normalAppItem.intent, shareId);
                boolean openApp = openApp(this.mContext, normalAppItem.intent, (String) normalAppItem.title);
                Logger.t(TAG).i("launchApp: " + normalAppItem.intent + ", ret:" + openApp, new Object[0]);
                return openApp;
            }
        }
        Logger.t(TAG).w("launchApp, none app found " + packageName, new Object[0]);
        return false;
    }

    private static void invokeSetSharedId(Intent intent, int sharedId) {
        try {
            Intent.class.getMethod("setSharedId", Integer.TYPE).invoke(intent, Integer.valueOf(sharedId));
        } catch (Exception e) {
            Logger.t(TAG).e("invokeSetSharedId error, it:" + intent + ", shareId:" + sharedId, new Object[0]);
            e.printStackTrace();
        }
    }

    private boolean openApp(Context context, Intent intent, String lable) {
        context.startActivity(intent);
        Logger.t(TAG).i("openApp, it:" + intent, new Object[0]);
        return true;
    }

    private boolean openAppByContentProvider(Context context, Intent intent) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri parse = Uri.parse("content://appstore/open_app_stage");
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppStateContract.LAUNCH_APP_KEY, intent);
        bundle.putBoolean(AppStateContract.EXTRA_KEY_PARAMS_UPDATE_IN_BACKGROUND, false);
        Bundle call = contentResolver.call(parse, "openApp", "", bundle);
        int i = call.getInt("code", 0);
        Logger.t(TAG).i("openApp, it:" + intent + ", code:" + i + ", msg:" + call.getString(NotificationCompat.CATEGORY_MESSAGE), new Object[0]);
        return i == 1000;
    }

    public boolean uninstallApp(String packageName) {
        Logger.t(TAG).i("uninstallApp: " + packageName, new Object[0]);
        return com.xiaopeng.appstore.appserver_common.utils.PackageUtils.uninstall(this.mContext, packageName, 0);
    }

    public void startLoadAppList() {
        Logger.t(TAG).i(NapaHandler.METHOD_START_LOAD_APP_LIST, new Object[0]);
        AppListCache.get().tryLoadData();
    }

    public void loadAppList() {
        Logger.t(TAG).i("loadAppList", new Object[0]);
        assertInit();
        AppListCache.get().reload();
    }

    public void persistAppOrder(byte[] appIndexList) {
        List<LocalAppData> parseAppIndexList;
        if (appIndexList == null) {
            Log.w(TAG, "persistAppOrder, param is null");
            return;
        }
        AppIndexList appIndexList2 = null;
        try {
            appIndexList2 = AppIndexList.parseFrom(appIndexList);
            Log.i(TAG, "persistAppOrder, list:" + appIndexList2);
        } catch (InvalidProtocolBufferException e) {
            Log.w(TAG, "persistAppOrder, param error:");
            e.printStackTrace();
        }
        if (appIndexList2 == null || (parseAppIndexList = parseAppIndexList(appIndexList2)) == null) {
            return;
        }
        AppListCache.get().refreshOrder(parseAppIndexList);
    }

    private List<LocalAppData> parseAppIndexList(AppIndexList appIndexList) {
        if (appIndexList == null || appIndexList.getIndexListList() == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(appIndexList.getIndexListCount());
        for (AppIndex appIndex : appIndexList.getIndexListList()) {
            LocalAppData localAppData = new LocalAppData();
            localAppData.packageName = appIndex.getPackageName();
            localAppData.index = appIndex.getIndex();
            arrayList.add(localAppData);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AppItemProto parseAppItemProto(BaseAppItem baseAppItem) {
        int i;
        boolean z = baseAppItem instanceof FixedAppItem;
        if (z) {
            i = 103;
        } else if (baseAppItem instanceof LoadingAppItem) {
            i = 102;
        } else {
            i = baseAppItem instanceof NormalAppItem ? 1 : 0;
        }
        AppItemProto.Builder icon = AppItemProto.newBuilder().setType(i).setName(baseAppItem.title.toString()).setTitle(baseAppItem.title.toString()).setPackageName(baseAppItem.packageName).setIcon(baseAppItem.iconBitmap != null ? LocalUtils.bitmap2ByteString(baseAppItem.iconBitmap) : ByteString.EMPTY);
        String str = null;
        if (z) {
            FixedAppItem fixedAppItem = (FixedAppItem) baseAppItem;
            str = fixedAppItem.getIconUrl();
            icon.setDownloadUrl(fixedAppItem.getDownloadUrl());
            icon.setConfigUrl(fixedAppItem.getConfigUrl());
        } else if (baseAppItem instanceof LoadingAppItem) {
            str = ((LoadingAppItem) baseAppItem).iconUrl;
        }
        if (!TextUtils.isEmpty(str)) {
            icon.setAppIcons(AppIconDataProto.newBuilder().setSmallIcon(str).setLargeIcon(str).build());
        }
        return icon.build();
    }

    public String getStoreHome() {
        Response<ResponseBody> response;
        String str = null;
        try {
            response = XpApiClient.getAdvService().getPageCall().execute();
        } catch (IOException e) {
            e.printStackTrace();
            response = null;
        }
        if (response == null) {
            Log.w(TAG, "response is null");
            return null;
        }
        ResponseBody body = response.body();
        if (body != null) {
            try {
                str = body.string();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        Log.i(TAG, "getStoreHome: " + str);
        return str;
    }

    public void getStoreHomeAsync(final RequestListener listener) {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$jCroYRYXv_ruGeFH5-3eyXaSyWc
            @Override // java.lang.Runnable
            public final void run() {
                ServerProvider.this.lambda$getStoreHomeAsync$4$ServerProvider(listener);
            }
        });
    }

    public /* synthetic */ void lambda$getStoreHomeAsync$4$ServerProvider(RequestListener requestListener) {
        String storeHome = getStoreHome();
        if (requestListener != null) {
            requestListener.onResponse(storeHome);
        }
    }

    public AppStoreHomeResponseProto getStoreHomeProto() {
        AppStoreHomeResponseProto parseStoreHome = AppStore.parseStoreHome(AppStore.requestStoreHome());
        Logger.t(TAG).i("getStoreHomeProto: " + parseStoreHome, new Object[0]);
        return parseStoreHome;
    }

    public void getStoreHomeProtoAsync(final RequestProtoListener listener) {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$ukZkZoOKvwdn09c9VBnH2dB7bmw
            @Override // java.lang.Runnable
            public final void run() {
                ServerProvider.this.lambda$getStoreHomeProtoAsync$5$ServerProvider(listener);
            }
        });
    }

    public /* synthetic */ void lambda$getStoreHomeProtoAsync$5$ServerProvider(RequestProtoListener requestProtoListener) {
        AppStoreHomeResponseProto storeHomeProto = getStoreHomeProto();
        if (requestProtoListener != null) {
            requestProtoListener.onResponse(storeHomeProto);
        }
    }

    public String getAppDetail(String packageName) {
        Response<ResponseBody> response;
        String str = null;
        if (TextUtils.isEmpty(packageName)) {
            Log.w(TAG, "getAppDetail error: " + packageName);
            return null;
        }
        AppRequestContainer appRequestContainer = new AppRequestContainer();
        appRequestContainer.setPackageName(packageName);
        AppDetailRequest appDetailRequest = new AppDetailRequest();
        appDetailRequest.setParam(appRequestContainer);
        try {
            response = XpApiClient.getAppService().getAppDetailCall(appDetailRequest).execute();
        } catch (IOException e) {
            e.printStackTrace();
            response = null;
        }
        if (response == null) {
            Log.w(TAG, "response is null");
            return null;
        }
        ResponseBody body = response.body();
        if (body != null) {
            try {
                str = body.string();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        Log.i(TAG, "getAppDetail, pn:" + packageName + ", resp:" + str);
        return str;
    }

    public void getAppDetailAsync(final String packageName, final RequestListener listener) {
        Log.i(TAG, "getAppDetailAsync start, pn:" + packageName);
        if (TextUtils.isEmpty(packageName)) {
            Log.w(TAG, "getAppDetailAsync: packageName is null");
        } else {
            AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$VXtq-_h3TLV6OGInhESwkGXO3B0
                @Override // java.lang.Runnable
                public final void run() {
                    ServerProvider.this.lambda$getAppDetailAsync$6$ServerProvider(packageName, listener);
                }
            });
        }
    }

    public /* synthetic */ void lambda$getAppDetailAsync$6$ServerProvider(String str, RequestListener requestListener) {
        String appDetail = getAppDetail(str);
        if (requestListener != null) {
            requestListener.onResponse(appDetail);
        }
    }

    public AppDetailResponseProto getAppDetailProto(String packageName) {
        AppDetailData requestAppDetail = AppStore.requestAppDetail(packageName);
        if (requestAppDetail != null) {
            AppDetailResponseProto build = AppDetailResponseProto.newBuilder().setData(AppStore.parseAppDetail(requestAppDetail)).build();
            Logger.t(TAG).i("getAppDetailProto:" + build, new Object[0]);
            return build;
        }
        Logger.t(TAG).w("getAppDetailProto error, request data is null.", new Object[0]);
        return null;
    }

    public void getAppDetailProtoAsync(final String packageName, final RequestProtoListener listener) {
        Logger.t(TAG).i("getAppDetailProtoAsync start, pn:" + packageName, new Object[0]);
        if (TextUtils.isEmpty(packageName)) {
            Logger.t(TAG).w(TAG, "getAppDetailProtoAsync: packageName is null");
        } else {
            AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$NCNAD09yhcsvl_nIXj0VCgeViTk
                @Override // java.lang.Runnable
                public final void run() {
                    ServerProvider.this.lambda$getAppDetailProtoAsync$7$ServerProvider(packageName, listener);
                }
            });
        }
    }

    public /* synthetic */ void lambda$getAppDetailProtoAsync$7$ServerProvider(String str, RequestProtoListener requestProtoListener) {
        AppDetailResponseProto appDetailProto = getAppDetailProto(str);
        if (requestProtoListener != null) {
            requestProtoListener.onResponse(appDetailProto);
        }
    }

    public void requestUpdate() {
        Logger.t(TAG).i(NapaHandler.METHOD_REQUEST_UPDATE, new Object[0]);
        AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$ADEU15GXL5fkgIr5gqkfojji0es
            @Override // java.lang.Runnable
            public final void run() {
                CheckUpdateManager.get().requestUpdate();
            }
        });
    }

    public void setUpdateListener(Consumer<AppUpdateResponseProto> onChanged) {
        this.mUpdateListener = onChanged;
    }

    public /* synthetic */ void lambda$new$10$ServerProvider(final List list) {
        Logger.t(TAG).i("Update data changed:" + list + ", lis:" + this.mUpdateListener, new Object[0]);
        if (this.mUpdateListener != null) {
            AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$z0yvCv0Rd0mgAJUr67zDTAIvO7o
                @Override // java.lang.Runnable
                public final void run() {
                    ServerProvider.this.lambda$new$9$ServerProvider(list);
                }
            });
        }
    }

    public /* synthetic */ void lambda$new$9$ServerProvider(List list) {
        this.mUpdateListener.accept(AppStore.parseAppListToUpdate(list));
    }

    private void observeUpdateData() {
        Logger.t(TAG).i("observeUpdateData:" + hashCode(), new Object[0]);
        AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$WwEDohLI-VHEeATMHnINKibS4oA
            @Override // java.lang.Runnable
            public final void run() {
                ServerProvider.this.lambda$observeUpdateData$12$ServerProvider();
            }
        });
    }

    public /* synthetic */ void lambda$observeUpdateData$12$ServerProvider() {
        Logger.t(TAG).i("observeUpdateData IN:" + hashCode(), new Object[0]);
        CheckUpdateManager.get().getUpdateListLive().observeForever(this.mUpdateDataObserver);
        CheckUpdateManager.get().getPendingUpgradeCount().observeForever(this.mUpdateCountObserver);
    }

    private void stopObserveUpdateData() {
        Logger.t(TAG).i("stopObserveUpdateData:" + hashCode(), new Object[0]);
        AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$KvpM0wAx2Vlp2J-bEsK80FV0Byc
            @Override // java.lang.Runnable
            public final void run() {
                ServerProvider.this.lambda$stopObserveUpdateData$13$ServerProvider();
            }
        });
    }

    public /* synthetic */ void lambda$stopObserveUpdateData$13$ServerProvider() {
        CheckUpdateManager.get().getUpdateListLive().removeObserver(this.mUpdateDataObserver);
        CheckUpdateManager.get().getPendingUpgradeCount().removeObserver(this.mUpdateCountObserver);
    }

    private String getUpdate() {
        List<PackageInfo> queryPackageList = AppComponentManager.get().queryPackageList();
        if (queryPackageList != null) {
            return requestUpdateSync(queryPackageList);
        }
        return null;
    }

    public void getUpdateAsync(final RequestListener listener) {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$Vj_8kyc0AhHHX3hv9bk71DlSqoo
            @Override // java.lang.Runnable
            public final void run() {
                ServerProvider.this.lambda$getUpdateAsync$14$ServerProvider(listener);
            }
        });
    }

    public /* synthetic */ void lambda$getUpdateAsync$14$ServerProvider(RequestListener requestListener) {
        Log.d(TAG, "getUpdateAsync start");
        String update = getUpdate();
        if (requestListener != null) {
            requestListener.onResponse(update);
        }
    }

    public AppUpdateResponseProto getUpdateProto() {
        AppUpdateResponseProto parseUpdateData = AppStore.parseUpdateData(AppStore.requestUpdateSync(AppComponentManager.get().queryPackageList()));
        Logger.t(TAG).i("getUpdateProto:" + parseUpdateData, new Object[0]);
        return parseUpdateData;
    }

    public void getUpdateProtoAsync(final RequestProtoListener listener) {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$_2Sn8MDNbdx8-i-9o9msEQwbM3Q
            @Override // java.lang.Runnable
            public final void run() {
                ServerProvider.this.lambda$getUpdateProtoAsync$15$ServerProvider(listener);
            }
        });
    }

    public /* synthetic */ void lambda$getUpdateProtoAsync$15$ServerProvider(RequestProtoListener requestProtoListener) {
        Log.d(TAG, "getUpdateProtoAsync start");
        AppUpdateResponseProto updateProto = getUpdateProto();
        if (requestProtoListener != null) {
            requestProtoListener.onResponse(updateProto);
        }
    }

    private String requestUpdateSync(Collection<PackageInfo> packageList) {
        Response<ResponseBody> response;
        List<AppRequestContainer> parsePackageInfoList = parsePackageInfoList(packageList);
        String str = null;
        if (parsePackageInfoList == null || parsePackageInfoList.isEmpty()) {
            Log.w(TAG, "requestUpdateSync: requestParam is empty " + parsePackageInfoList);
            return null;
        }
        AppListRequest appListRequest = new AppListRequest();
        appListRequest.setParams(parsePackageInfoList);
        Log.d(TAG, "requestUpdateSync, request:" + appListRequest);
        try {
            response = XpApiClient.getAppService().getUpdateCallResp(appListRequest).execute();
        } catch (IOException e) {
            Log.w(TAG, "requestUpdateSync ex:" + e);
            response = null;
        }
        if (response == null) {
            Log.w(TAG, "requestUpdateSync, response is null");
            return null;
        }
        ResponseBody body = response.body();
        if (body != null) {
            try {
                str = body.string();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        Log.i(TAG, "requestUpdateSync: " + str);
        return str;
    }

    private List<AppRequestContainer> parsePackageInfoList(Collection<PackageInfo> packageList) {
        long j;
        if (packageList == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (PackageInfo packageInfo : packageList) {
            AppRequestContainer appRequestContainer = new AppRequestContainer();
            String str = packageInfo.packageName;
            if (Build.VERSION.SDK_INT >= 28) {
                j = packageInfo.getLongVersionCode();
            } else {
                j = packageInfo.versionCode;
            }
            appRequestContainer.setPackageName(str);
            appRequestContainer.setVersionCode(j);
            arrayList.add(appRequestContainer);
        }
        return arrayList;
    }

    public boolean isPrivacyAgreed() {
        assertInit();
        boolean isAppStoreAgreed = PrivacyUtils.isAppStoreAgreed(this.mContext);
        Log.i(TAG, "isPrivacyAgreed:" + isAppStoreAgreed);
        return isAppStoreAgreed;
    }

    public void showPrivacyDialog(boolean onlyContent) {
        Log.i(TAG, "showPrivacyDialog:" + onlyContent);
        AgreementDialogHelper.getInstance().show(null, onlyContent);
    }

    public void showPrivacyDialog(Runnable afterAgreed, boolean onlyContent) {
        Log.i(TAG, "showPrivacyDialog with afterAgreed:" + onlyContent);
        AgreementDialogHelper.getInstance().show(afterAgreed, onlyContent);
    }

    public void startDownloadAsync(final boolean showToast, final int screenShareId, final String downloadUrl, final String md5, final String configUrl, final String configMd5, final String packageName, final String iconUrl, final String label) {
        assertInit();
        this.mWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$k3Ut1gExXdt1XP2v2zW8LZdcseM
            @Override // java.lang.Runnable
            public final void run() {
                ServerProvider.this.lambda$startDownloadAsync$16$ServerProvider(showToast, screenShareId, downloadUrl, md5, configUrl, configMd5, packageName, iconUrl, label);
            }
        });
    }

    /* renamed from: startDownload */
    public boolean lambda$startDownloadAsync$16$ServerProvider(boolean showToast, int toastScreenShareId, String downloadUrl, String md5, String configUrl, String configMd5, String packageName, String iconUrl, String label) {
        AssembleResult assemble = StoreProviderManager.get().assemble(AssembleRequest.enqueue(1000, packageName, label).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_START_DOWNLOAD_SHOW_TOAST, showToast).putExtra("extra_key_show_dialog", false).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_TOAST_SHARE_ID, toastScreenShareId).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_URL, configUrl).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_MD5_2, md5).putExtra(AssembleConstants.EXTRA_KEY_PARAMS_MD5_1, configMd5).setDownloadUrl(downloadUrl).setIconUrl(iconUrl).request(), null);
        if (handleAssembleResult(assemble)) {
            Logger.t(TAG).i("startDownload success:" + packageName + ", " + assemble, new Object[0]);
            return true;
        }
        Logger.t(TAG).i("startDownload fail:" + packageName + ", " + assemble, new Object[0]);
        return false;
    }

    public void pauseDownloadAsync(final String packageName) {
        assertInit();
        this.mWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$HpmmEImr2G3PKPshnUqh6bs0ljs
            @Override // java.lang.Runnable
            public final void run() {
                ServerProvider.this.lambda$pauseDownloadAsync$17$ServerProvider(packageName);
            }
        });
    }

    /* renamed from: pauseDownload */
    public boolean lambda$pauseDownloadAsync$17$ServerProvider(String packageName) {
        AssembleResult assemble = StoreProviderManager.get().assemble(AssembleRequest.pause(1000, packageName), null);
        if (handleAssembleResult(assemble)) {
            Logger.t(TAG).i("pauseDownload success:" + packageName + ", " + assemble, new Object[0]);
            return true;
        }
        Logger.t(TAG).i("pauseDownload fail:" + packageName + ", " + assemble, new Object[0]);
        return false;
    }

    public void resumeDownloadAsync(final String packageName) {
        assertInit();
        this.mWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$n8zU_igoqmu82q9eRMGfXJcoGYM
            @Override // java.lang.Runnable
            public final void run() {
                ServerProvider.this.lambda$resumeDownloadAsync$18$ServerProvider(packageName);
            }
        });
    }

    /* renamed from: resumeDownload */
    public boolean lambda$resumeDownloadAsync$18$ServerProvider(String packageName) {
        AssembleResult assemble = StoreProviderManager.get().assemble(AssembleRequest.resume(1000, packageName), null);
        if (handleAssembleResult(assemble)) {
            Logger.t(TAG).i("resumeDownload success:" + packageName + ", " + assemble, new Object[0]);
            return true;
        }
        Logger.t(TAG).i("resumeDownload fail:" + packageName + ", " + assemble, new Object[0]);
        return false;
    }

    public void cancelDownloadAsync(final String packageName) {
        assertInit();
        this.mWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$EQzWcidkCqO5ic6CQ0kcJ7UtgiI
            @Override // java.lang.Runnable
            public final void run() {
                ServerProvider.this.lambda$cancelDownloadAsync$19$ServerProvider(packageName);
            }
        });
    }

    /* renamed from: cancelDownload */
    public boolean lambda$cancelDownloadAsync$19$ServerProvider(String packageName) {
        AssembleResult assemble = StoreProviderManager.get().assemble(AssembleRequest.cancel(1000, packageName), null);
        if (handleAssembleResult(assemble)) {
            Logger.t(TAG).i("cancelDownload success:" + packageName + ", " + assemble, new Object[0]);
            return true;
        }
        Logger.t(TAG).i("cancelDownload fail:" + packageName + ", " + assemble, new Object[0]);
        return false;
    }

    public void dispatchInitialDownloads() {
        this.mWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$ServerProvider$R7FZrxLTnYRWXxIrITPwtzRYQTw
            @Override // java.lang.Runnable
            public final void run() {
                ServerProvider.this.lambda$dispatchInitialDownloads$20$ServerProvider();
            }
        });
    }

    public /* synthetic */ void lambda$dispatchInitialDownloads$20$ServerProvider() {
        List<AssembleInfo> assembleInfoList = StoreProviderManager.get().getAssembleInfoList(1000);
        if (assembleInfoList == null) {
            Logger.t(TAG).i("dispatchInitialDownloads, none downloads", new Object[0]);
            return;
        }
        Logger.t(TAG).i("dispatchInitialDownloads, downloads:" + assembleInfoList.size(), new Object[0]);
        int i = 1;
        for (AssembleInfo assembleInfo : assembleInfoList) {
            Logger.t(TAG).d("dispatchInitialDownloads(" + i + "):" + assembleInfo);
            String key = assembleInfo.getKey();
            int state = assembleInfo.getState();
            dispatchStateChanged(key, state);
            dispatchProgressChanged(key, state);
            i++;
        }
    }

    public void addDownloadListener(String packageName, DownloadListener listener) {
        if (TextUtils.isEmpty(packageName)) {
            Logger.t(TAG).w("addDownloadListener: packageName is null", new Object[0]);
            return;
        }
        Logger.t(TAG).i("addDownloadListener, pn:" + packageName + ", lis:" + listener, new Object[0]);
        synchronized (this.mListenerLock) {
            tryStartObserveDownload();
            Set<DownloadListener> set = this.mListenerMap.get(packageName);
            if (set == null) {
                set = new HashSet<>();
            }
            set.add(listener);
        }
    }

    public void addDownloadListener(DownloadListener listener) {
        Logger.t(TAG).i("addDownloadListener: " + listener, new Object[0]);
        synchronized (this.mListenerLock) {
            tryStartObserveDownload();
            this.mDownloadListenerSet.add(listener);
        }
    }

    public void removeDownloadListener(DownloadListener listener) {
        Logger.t(TAG).i("removeDownloadListener: " + listener, new Object[0]);
        synchronized (this.mListenerLock) {
            this.mDownloadListenerSet.remove(listener);
            Iterator<Map.Entry<String, Set<DownloadListener>>> it = this.mListenerMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Set<DownloadListener>> next = it.next();
                Set<DownloadListener> value = next.getValue();
                value.remove(listener);
                if (value.isEmpty()) {
                    Logger.t(TAG).i("removeDownloadListener all, pn:" + next.getKey() + ", lis:" + listener, new Object[0]);
                    it.remove();
                }
            }
            tryRemoveObserveDownload();
        }
    }

    private void dispatchStateChanged(String packageName, int state) {
        synchronized (this.mListenerLock) {
            for (DownloadListener downloadListener : this.mDownloadListenerSet) {
                downloadListener.onStateChanged(packageName, state);
            }
            for (Map.Entry<String, Set<DownloadListener>> entry : this.mListenerMap.entrySet()) {
                for (DownloadListener downloadListener2 : entry.getValue()) {
                    downloadListener2.onStateChanged(packageName, state);
                }
            }
        }
    }

    private void dispatchProgressChanged(String packageName, float progress) {
        synchronized (this.mListenerLock) {
            for (DownloadListener downloadListener : this.mDownloadListenerSet) {
                downloadListener.onProgressChanged(packageName, progress);
            }
            for (Map.Entry<String, Set<DownloadListener>> entry : this.mListenerMap.entrySet()) {
                for (DownloadListener downloadListener2 : entry.getValue()) {
                    downloadListener2.onProgressChanged(packageName, progress);
                }
            }
        }
    }

    private void tryStartObserveDownload() {
        if (this.mListenerMap.isEmpty() && this.mDownloadListenerSet.isEmpty()) {
            Logger.t(TAG).i("startObserveDownload", new Object[0]);
            StoreProviderManager.get().startObserve();
            StoreProviderManager.get().registerListener(1000, null, this);
        }
    }

    private void tryRemoveObserveDownload() {
        if (this.mListenerMap.isEmpty() && this.mDownloadListenerSet.isEmpty()) {
            Logger.t(TAG).i("tryRemoveObserveDownload", new Object[0]);
            StoreProviderManager.get().stopObserve();
            StoreProviderManager.get().unregisterListener(this);
        }
    }

    private void assertInit() {
        HandlerThread handlerThread = this.mHandlerTh;
        if (handlerThread == null || !handlerThread.isAlive()) {
            Logger.t(TAG).e("AppStore ServiceProvider not init, this:" + hashCode(), new Object[0]);
            throw new IllegalStateException("AppStore ServiceProvider is invalid. call init first! this:" + hashCode());
        }
    }

    private boolean handleAssembleResult(AssembleResult assembleResult) {
        return assembleResult != null && assembleResult.isSuccessful();
    }
}
