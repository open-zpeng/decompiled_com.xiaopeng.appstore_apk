package com.xiaopeng.appstore.appserver_common;

import android.app.Application;
import android.text.TextUtils;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.orhanobut.logger.Logger;
import com.xiaopeng.aar.server.ServerListener;
import com.xiaopeng.aar.server.ServerManager;
import com.xiaopeng.appstore.appserver_common.v2.AppListListener;
import com.xiaopeng.appstore.appserver_common.v2.RequestProtoListener;
import com.xiaopeng.appstore.libcommon.utils.NumberUtils;
import com.xiaopeng.appstore.protobuf.AppItemProto;
import com.xiaopeng.appstore.protobuf.AppListProto;
import com.xiaopeng.appstore.protobuf.AppUpdateResponseProto;
import com.xiaopeng.appstore.protobuf.AppletGroupListProto;
import com.xiaopeng.appstore.protobuf.UsbAppListProto;
import java.util.function.Consumer;
/* loaded from: classes2.dex */
public class NapaHandler implements ServerListener {
    public static final String METHOD_CANCEL_DOWNLOAD = "appstore_CancelDownload";
    public static final String METHOD_CREATE = "create";
    public static final String METHOD_DESTROY = "destroy";
    public static final String METHOD_ENABLE_DEBUG = "enableDebug";
    public static final String METHOD_GET_APPSTORE_DETAIL_ASYNC = "getAppDetailProtoAsync";
    public static final String METHOD_GET_APPSTORE_HOME_ASYNC = "getStoreHomeProtoAsync";
    public static final String METHOD_GET_APPSTORE_UPDATE_ASYNC = "getUpdateProtoAsync";
    public static final String METHOD_GET_APP_LIST_PROTO = "getAppListProto";
    public static final String METHOD_GET_HTTP_HOST = "getHttpHost";
    public static final String METHOD_GET_USB_APP_LIST = "getUsbAppList";
    public static final String METHOD_INIT = "init";
    public static final String METHOD_INSTALL_USB_APP = "installUsbApp";
    public static final String METHOD_IS_INSTALLED = "isInstalled";
    public static final String METHOD_IS_IN_DEBUG = "isInDebug";
    public static final String METHOD_IS_PRIVACY_AGREED = "isPrivacyAgreed";
    public static final String METHOD_LAUNCH_APP = "launchApp";
    public static final String METHOD_LAUNCH_APPLET = "launchApplet";
    public static final String METHOD_LOAD_APPLET_LIST = "loadAppletList";
    public static final String METHOD_PAUSE_DOWNLOAD = "appstore_PauseDownload";
    public static final String METHOD_PERSIST_APP_ORDER = "persistAppOrder";
    public static final String METHOD_RELEASE = "release";
    public static final String METHOD_REQUEST_UPDATE = "requestUpdate";
    public static final String METHOD_RESET_HTTP_HOST = "resetHttpHost";
    public static final String METHOD_RESUME_DOWNLOAD = "appstore_ResumeDownload";
    public static final String METHOD_SHOW_PRIVACY_DIALOG = "showPrivacyDialog";
    public static final String METHOD_START = "start";
    public static final String METHOD_START_DOWNLOAD = "appstore_StartDownload";
    public static final String METHOD_START_LOAD_APP_LIST = "startLoadAppList";
    public static final String METHOD_START_LOAD_USB_APP = "startLoadUsbApp";
    public static final String METHOD_STOP = "stop";
    public static final String METHOD_SWITCH_HTTP_PRE_HOST = "switchHttpPreHost";
    public static final String METHOD_SWITCH_HTTP_TEST_HOST = "switchHttpTestHost";
    public static final String METHOD_TRY_OPEN_NAPA_APP = "tryOpenNapaApp";
    public static final String METHOD_UNINSTALL_APP = "uninstallApp";
    public static final String MODULE_APPLET = "applet";
    public static final String MODULE_APPSTORE = "appstore";
    public static final String MODULE_APP_LIST = "applist";
    public static final String MODULE_DOWNLOAD = "download";
    public static final String MODULE_GLOBAL = "global";
    public static final String MODULE_MAIN = "main";
    public static final String MODULE_USB_APP = "usbapp";
    public static final String MSG_ID_APPLET_LIST_CHANGED = "onAppletListChanged";
    public static final String MSG_ID_DOWNLOAD_PROGRESS_CHANGED = "appstore_OnDownloadProgressChanged";
    public static final String MSG_ID_DOWNLOAD_STATE_CHANGED = "appstore_OnDownloadStateChanged";
    public static final String MSG_ID_ON_APP_CHANGED = "onAppChanged";
    public static final String MSG_ID_ON_APP_INSERTED = "onAppInserted";
    public static final String MSG_ID_ON_APP_MOVED = "onAppMoved";
    public static final String MSG_ID_ON_APP_REMOVED = "onAppRemoved";
    public static final String MSG_ID_OPEN_ACTION = "openAction";
    public static final String MSG_ID_PRIVACY_AGREED = "onPrivacyAgreed";
    public static final String MSG_ID_UPDATE_DATA_CHANGED = "onUpdateDataChanged";
    public static final String MSG_ID_USB_ENTRY_ON_HIDE = "onHideUsbEntry";
    public static final String MSG_ID_USB_ENTRY_ON_SHOW = "onShowUsbEntry";
    public static final ServerProvider SERVER_PROVIDER = new ServerProvider();
    private static final String SPLIT_CHAR = ",";
    private static final String TAG = "NapaHandler";
    private final Application mApp;
    private final AppListListener mAppListListener;
    private final AppletListener mAppletListener;
    private final DownloadListener mDownloadListener;
    private boolean mEnableEvents;
    private final ServerProvider mServerProvider;
    private final Consumer<AppUpdateResponseProto> mUpdateListener;
    private final UsbEntryListener mUsbEntryListener;

    private String handleDownloadModule(String method, String param, byte[] blob) {
        boolean z;
        method.hashCode();
        char c = 65535;
        switch (method.hashCode()) {
            case -2049854389:
                if (method.equals(METHOD_START_DOWNLOAD)) {
                    c = 0;
                    break;
                }
                break;
            case -582769663:
                if (method.equals(METHOD_CANCEL_DOWNLOAD)) {
                    c = 1;
                    break;
                }
                break;
            case 105372340:
                if (method.equals(METHOD_RESUME_DOWNLOAD)) {
                    c = 2;
                    break;
                }
                break;
            case 238961119:
                if (method.equals(METHOD_PAUSE_DOWNLOAD)) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                if (blob != null) {
                    AppItemProto appItemProto = null;
                    try {
                        appItemProto = AppItemProto.parseFrom(blob);
                    } catch (InvalidProtocolBufferException e) {
                        Logger.t(TAG).w("Download, startDownload, blob invalid", new Object[0]);
                        e.printStackTrace();
                    }
                    if (appItemProto != null) {
                        String[] parseStringParams = parseStringParams(param);
                        if (parseStringParams.length > 0) {
                            z = NumberUtils.stringToInt(parseStringParams[0], 0) > 0;
                        } else {
                            z = false;
                        }
                        int stringToInt = parseStringParams.length > 1 ? NumberUtils.stringToInt(parseStringParams[1], 0) : 0;
                        String smallIcon = appItemProto.getAppIcons() != null ? appItemProto.getAppIcons().getSmallIcon() : "";
                        Logger.t(TAG).i("Download, start, app:" + appItemProto + ", param:" + param, new Object[0]);
                        this.mServerProvider.startDownloadAsync(z, stringToInt, appItemProto.getDownloadUrl(), appItemProto.getMd5(), appItemProto.getConfigUrl(), appItemProto.getConfigMd5(), appItemProto.getPackageName(), smallIcon, appItemProto.getName());
                        return "true";
                    }
                    return "false";
                }
                return "false";
            case 1:
                if (!TextUtils.isEmpty(param)) {
                    this.mServerProvider.cancelDownloadAsync(param);
                    return "true";
                }
                break;
            case 2:
                if (!TextUtils.isEmpty(param)) {
                    this.mServerProvider.resumeDownloadAsync(param);
                    return "true";
                }
                break;
            case 3:
                if (!TextUtils.isEmpty(param)) {
                    this.mServerProvider.pauseDownloadAsync(param);
                    return "true";
                }
                break;
            default:
                Logger.t(TAG).w("Unknown method in Download module:" + method + ", param:" + param, new Object[0]);
                return "";
        }
        Logger.t(TAG).w("Error parameters for:" + method + ", param:" + param + " in Download Module", new Object[0]);
        return "";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$new$0(AppUpdateResponseProto appUpdateResponseProto) {
        Logger.t(TAG).i("UpdateListener onReceive:" + appUpdateResponseProto, new Object[0]);
        ServerManager.get().send(MODULE_MAIN, MSG_ID_UPDATE_DATA_CHANGED, null, appUpdateResponseProto != null ? appUpdateResponseProto.toByteArray() : null);
    }

    public NapaHandler(Application mApp, boolean enableEvents) {
        this.mDownloadListener = new DownloadListener() { // from class: com.xiaopeng.appstore.appserver_common.NapaHandler.1
            @Override // com.xiaopeng.appstore.appserver_common.DownloadListener
            public void onStateChanged(String packageName, int state) {
                ServerManager.get().send(NapaHandler.MODULE_DOWNLOAD, NapaHandler.MSG_ID_DOWNLOAD_STATE_CHANGED, packageName + NapaHandler.SPLIT_CHAR + state, null);
            }

            @Override // com.xiaopeng.appstore.appserver_common.DownloadListener
            public void onProgressChanged(String packageName, float progress) {
                ServerManager.get().send(NapaHandler.MODULE_DOWNLOAD, NapaHandler.MSG_ID_DOWNLOAD_PROGRESS_CHANGED, packageName + NapaHandler.SPLIT_CHAR + progress, null);
            }
        };
        this.mAppListListener = new AppListListener() { // from class: com.xiaopeng.appstore.appserver_common.NapaHandler.2
            @Override // com.xiaopeng.appstore.appserver_common.v2.AppListListener
            public void onAppInserted(int position, int count) {
                AppListProto appListProto = NapaHandler.this.mServerProvider.getAppListProto();
                ServerManager.get().send("applist", NapaHandler.MSG_ID_ON_APP_INSERTED, position + NapaHandler.SPLIT_CHAR + count, appListProto != null ? appListProto.toByteArray() : null);
            }

            @Override // com.xiaopeng.appstore.appserver_common.v2.AppListListener
            public void onAppRemoved(int position, int count) {
                AppListProto appListProto = NapaHandler.this.mServerProvider.getAppListProto();
                ServerManager.get().send("applist", NapaHandler.MSG_ID_ON_APP_REMOVED, position + NapaHandler.SPLIT_CHAR + count, appListProto != null ? appListProto.toByteArray() : null);
            }

            @Override // com.xiaopeng.appstore.appserver_common.v2.AppListListener
            public void onAppMoved(int fromPosition, int toPosition) {
                ServerManager.get().send("applist", NapaHandler.MSG_ID_ON_APP_MOVED, fromPosition + NapaHandler.SPLIT_CHAR + toPosition, null);
            }

            @Override // com.xiaopeng.appstore.appserver_common.v2.AppListListener
            public void onAppChanged(int position, int count, Object payload) {
                AppListProto appListProto = NapaHandler.this.mServerProvider.getAppListProto();
                ServerManager.get().send("applist", NapaHandler.MSG_ID_ON_APP_CHANGED, position + NapaHandler.SPLIT_CHAR + count, appListProto != null ? appListProto.toByteArray() : null);
            }
        };
        this.mAppletListener = new AppletListener() { // from class: com.xiaopeng.appstore.appserver_common.NapaHandler.3
            @Override // com.xiaopeng.appstore.appserver_common.AppletListener
            public void onAppletListChanged(AppletGroupListProto appletGroupListProto) {
                ServerManager.get().send("applet", NapaHandler.MSG_ID_APPLET_LIST_CHANGED, null, appletGroupListProto != null ? appletGroupListProto.toByteArray() : null);
            }
        };
        this.mUsbEntryListener = new UsbEntryListener() { // from class: com.xiaopeng.appstore.appserver_common.NapaHandler.4
            @Override // com.xiaopeng.appstore.appserver_common.UsbEntryListener
            public void onShowUsbEntry(boolean isLoading) {
                UsbAppListProto usbAppList;
                byte[] bArr = null;
                if (!isLoading && (usbAppList = NapaHandler.this.mServerProvider.getUsbAppList()) != null) {
                    bArr = usbAppList.toByteArray();
                }
                ServerManager.get().send(NapaHandler.MODULE_MAIN, NapaHandler.MSG_ID_USB_ENTRY_ON_SHOW, isLoading + "", bArr);
            }

            @Override // com.xiaopeng.appstore.appserver_common.UsbEntryListener
            public void onHideUsbEntry() {
                ServerManager.get().send(NapaHandler.MODULE_MAIN, NapaHandler.MSG_ID_USB_ENTRY_ON_HIDE, null, null);
            }
        };
        this.mUpdateListener = new Consumer() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$NapaHandler$JYmG6ie6_zTQV9UWwOdRBr7ec74
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NapaHandler.lambda$new$0((AppUpdateResponseProto) obj);
            }
        };
        this.mEnableEvents = true;
        this.mApp = mApp;
        ServerProvider serverProvider = SERVER_PROVIDER;
        this.mServerProvider = serverProvider;
        serverProvider.init(mApp);
        this.mEnableEvents = enableEvents;
    }

    public NapaHandler(Application app) {
        this(app, true);
    }

    @Override // com.xiaopeng.aar.server.ServerListener
    public String onCall(String module, String method, String param, byte[] blob) {
        module.hashCode();
        char c = 65535;
        switch (module.hashCode()) {
            case -1411061670:
                if (module.equals("applet")) {
                    c = 0;
                    break;
                }
                break;
            case -836135395:
                if (module.equals(MODULE_USB_APP)) {
                    c = 1;
                    break;
                }
                break;
            case -793234881:
                if (module.equals("applist")) {
                    c = 2;
                    break;
                }
                break;
            case 3343801:
                if (module.equals(MODULE_MAIN)) {
                    c = 3;
                    break;
                }
                break;
            case 1186311008:
                if (module.equals("appstore")) {
                    c = 4;
                    break;
                }
                break;
            case 1427818632:
                if (module.equals(MODULE_DOWNLOAD)) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return handleAppletModule(method, param, blob);
            case 1:
                return handleUsbAppModule(method, param, blob);
            case 2:
                return handleAppListModule(method, param, blob);
            case 3:
                return handleMainModule(method, param, blob);
            case 4:
                return handleAppStoreModule(method, param, blob);
            case 5:
                return handleDownloadModule(method, param, blob);
            default:
                Logger.t(TAG).w("Unknown module:" + module + ", method:" + method + ", param:" + param, new Object[0]);
                return "";
        }
    }

    @Override // com.xiaopeng.aar.server.ServerListener
    public void onSubscribe(String module) {
        if (this.mEnableEvents) {
            module.hashCode();
            char c = 65535;
            switch (module.hashCode()) {
                case -1411061670:
                    if (module.equals("applet")) {
                        c = 0;
                        break;
                    }
                    break;
                case -793234881:
                    if (module.equals("applist")) {
                        c = 1;
                        break;
                    }
                    break;
                case 3343801:
                    if (module.equals(MODULE_MAIN)) {
                        c = 2;
                        break;
                    }
                    break;
                case 1427818632:
                    if (module.equals(MODULE_DOWNLOAD)) {
                        c = 3;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    this.mServerProvider.setAppletListener(this.mAppletListener);
                    return;
                case 1:
                    this.mServerProvider.setAppListListenerV2(this.mAppListListener);
                    return;
                case 2:
                    this.mServerProvider.setUsbEntryListener(this.mUsbEntryListener);
                    this.mServerProvider.setUpdateListener(this.mUpdateListener);
                    return;
                case 3:
                    this.mServerProvider.addDownloadListener(this.mDownloadListener);
                    this.mServerProvider.dispatchInitialDownloads();
                    return;
                default:
                    Logger.t(TAG).w("Unknown module to subscribe:" + module, new Object[0]);
                    return;
            }
        }
    }

    @Override // com.xiaopeng.aar.server.ServerListener
    public void onUnSubscribe(String module) {
        module.hashCode();
        char c = 65535;
        switch (module.hashCode()) {
            case -1411061670:
                if (module.equals("applet")) {
                    c = 0;
                    break;
                }
                break;
            case -793234881:
                if (module.equals("applist")) {
                    c = 1;
                    break;
                }
                break;
            case 3343801:
                if (module.equals(MODULE_MAIN)) {
                    c = 2;
                    break;
                }
                break;
            case 1427818632:
                if (module.equals(MODULE_DOWNLOAD)) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mServerProvider.setAppletListener(null);
                return;
            case 1:
                this.mServerProvider.setAppListListenerV2(null);
                return;
            case 2:
                this.mServerProvider.setUsbEntryListener(null);
                this.mServerProvider.setUpdateListener(null);
                return;
            case 3:
                this.mServerProvider.removeDownloadListener(this.mDownloadListener);
                return;
            default:
                Logger.t(TAG).w("Unknown module to unsubscribe:" + module, new Object[0]);
                return;
        }
    }

    private String handleMainModule(String method, String param, byte[] blob) {
        method.hashCode();
        char c = 65535;
        switch (method.hashCode()) {
            case -1454212464:
                if (method.equals(METHOD_ENABLE_DEBUG)) {
                    c = 0;
                    break;
                }
                break;
            case -1374834497:
                if (method.equals(METHOD_RESET_HTTP_HOST)) {
                    c = 1;
                    break;
                }
                break;
            case -1352294148:
                if (method.equals(METHOD_CREATE)) {
                    c = 2;
                    break;
                }
                break;
            case -263673288:
                if (method.equals(METHOD_TRY_OPEN_NAPA_APP)) {
                    c = 3;
                    break;
                }
                break;
            case 3237136:
                if (method.equals(METHOD_INIT)) {
                    c = 4;
                    break;
                }
                break;
            case 3540994:
                if (method.equals(METHOD_STOP)) {
                    c = 5;
                    break;
                }
                break;
            case 109757538:
                if (method.equals("start")) {
                    c = 6;
                    break;
                }
                break;
            case 717070703:
                if (method.equals(METHOD_SWITCH_HTTP_PRE_HOST)) {
                    c = 7;
                    break;
                }
                break;
            case 923293526:
                if (method.equals(METHOD_SWITCH_HTTP_TEST_HOST)) {
                    c = '\b';
                    break;
                }
                break;
            case 1090594823:
                if (method.equals("release")) {
                    c = '\t';
                    break;
                }
                break;
            case 1333940132:
                if (method.equals(METHOD_IS_IN_DEBUG)) {
                    c = '\n';
                    break;
                }
                break;
            case 1456582712:
                if (method.equals(METHOD_REQUEST_UPDATE)) {
                    c = 11;
                    break;
                }
                break;
            case 1557372922:
                if (method.equals(METHOD_DESTROY)) {
                    c = '\f';
                    break;
                }
                break;
            case 1944464326:
                if (method.equals(METHOD_GET_HTTP_HOST)) {
                    c = '\r';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mServerProvider.enableDebug(NumberUtils.stringToInt(param, 0) > 0);
                break;
            case 1:
                this.mServerProvider.resetHttpHost();
                break;
            case 2:
                this.mServerProvider.create();
                break;
            case 3:
                String[] parseStringParams = parseStringParams(param);
                if (parseStringParams.length == 2) {
                    return this.mServerProvider.openNapaApp(parseStringParams[0], parseStringParams[1]);
                }
                logErrorParams(MODULE_MAIN, method, param);
                break;
            case 4:
                this.mServerProvider.init(this.mApp);
                break;
            case 5:
                this.mServerProvider.stop();
                break;
            case 6:
                this.mServerProvider.start();
                break;
            case 7:
                this.mServerProvider.switchHttpPreHost();
                break;
            case '\b':
                this.mServerProvider.switchHttpTestHost();
                break;
            case '\t':
                this.mServerProvider.release();
                break;
            case '\n':
                return this.mServerProvider.isInDebug() + "";
            case 11:
                this.mServerProvider.requestUpdate();
                break;
            case '\f':
                this.mServerProvider.destroy();
                break;
            case '\r':
                return this.mServerProvider.getHttpHost() + "";
            default:
                Logger.t(TAG).w("Unknown method in Main module:" + method + ", param:" + param, new Object[0]);
                break;
        }
        return "";
    }

    private String handleAppListModule(String method, String param, byte[] blob) {
        method.hashCode();
        char c = 65535;
        switch (method.hashCode()) {
            case -1272932897:
                if (method.equals(METHOD_UNINSTALL_APP)) {
                    c = 0;
                    break;
                }
                break;
            case -675127954:
                if (method.equals(METHOD_LAUNCH_APP)) {
                    c = 1;
                    break;
                }
                break;
            case 1207466352:
                if (method.equals(METHOD_IS_INSTALLED)) {
                    c = 2;
                    break;
                }
                break;
            case 1577472929:
                if (method.equals(METHOD_PERSIST_APP_ORDER)) {
                    c = 3;
                    break;
                }
                break;
            case 1767308503:
                if (method.equals(METHOD_START_LOAD_APP_LIST)) {
                    c = 4;
                    break;
                }
                break;
            case 2098850111:
                if (method.equals(METHOD_GET_APP_LIST_PROTO)) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                if (!TextUtils.isEmpty(param)) {
                    return this.mServerProvider.uninstallApp(param) + "";
                }
                Logger.t(TAG).w("Error parameters for:" + method + ", param:" + param + " in AppList Module", new Object[0]);
                return "";
            case 1:
                if (!TextUtils.isEmpty(param)) {
                    String[] parseStringParams = parseStringParams(param);
                    if (parseStringParams.length == 2) {
                        return this.mServerProvider.launchApp(parseStringParams[0], parseStringParams[1]) + "";
                    }
                    if (parseStringParams.length == 1) {
                        return this.mServerProvider.launchApp(parseStringParams[0]) + "";
                    }
                }
                Logger.t(TAG).w("Error parameters for:" + method + ", param:" + param + " in AppList Module", new Object[0]);
                return "";
            case 2:
                String[] parseStringParams2 = parseStringParams(param);
                if (parseStringParams2.length == 2) {
                    return this.mServerProvider.isInstalled(parseStringParams2[0], NumberUtils.stringToInt(parseStringParams2[1], 0) > 0) + "";
                }
                Logger.t(TAG).w("Error parameters for:" + method + ", param:" + param + " in AppList Module", new Object[0]);
                return "";
            case 3:
                if (blob != null) {
                    this.mServerProvider.persistAppOrder(blob);
                    return "";
                }
                Logger.t(TAG).w("Unknown method in AppList module:" + method + ", param:" + param, new Object[0]);
                return "";
            case 4:
                this.mServerProvider.startLoadAppList();
                return null;
            case 5:
                return "";
            default:
                Logger.t(TAG).w("Unknown method in AppList module:" + method + ", param:" + param, new Object[0]);
                return "";
        }
    }

    private String handleAppStoreModule(final String method, String param, byte[] blob) {
        method.hashCode();
        char c = 65535;
        switch (method.hashCode()) {
            case -1069156944:
                if (method.equals(METHOD_GET_APPSTORE_DETAIL_ASYNC)) {
                    c = 0;
                    break;
                }
                break;
            case -1024097322:
                if (method.equals(METHOD_IS_PRIVACY_AGREED)) {
                    c = 1;
                    break;
                }
                break;
            case -978992194:
                if (method.equals(METHOD_GET_APPSTORE_HOME_ASYNC)) {
                    c = 2;
                    break;
                }
                break;
            case 102305427:
                if (method.equals(METHOD_GET_APPSTORE_UPDATE_ASYNC)) {
                    c = 3;
                    break;
                }
                break;
            case 1247092467:
                if (method.equals(METHOD_SHOW_PRIVACY_DIALOG)) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                if (!TextUtils.isEmpty(param)) {
                    this.mServerProvider.getAppDetailProtoAsync(param, new RequestProtoListener() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$NapaHandler$MUBTwLsqxBVXzYgmIjN-0_TRV3k
                        @Override // com.xiaopeng.appstore.appserver_common.v2.RequestProtoListener
                        public final void onResponse(MessageLite messageLite) {
                            ServerManager.get().send("appstore", method, null, r4 != null ? messageLite.toByteArray() : null);
                        }
                    });
                    return "";
                }
                break;
            case 1:
                return this.mServerProvider.isPrivacyAgreed() + "";
            case 2:
                this.mServerProvider.getStoreHomeProtoAsync(new RequestProtoListener() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$NapaHandler$T1YN29W6Ipgyz_8qgW0KCPjEePU
                    @Override // com.xiaopeng.appstore.appserver_common.v2.RequestProtoListener
                    public final void onResponse(MessageLite messageLite) {
                        ServerManager.get().send("appstore", method, null, r4 != null ? messageLite.toByteArray() : null);
                    }
                });
                return "";
            case 3:
                this.mServerProvider.getUpdateProtoAsync(new RequestProtoListener() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$NapaHandler$f4jycGyQc69sKiJQZqm-s6RbcOM
                    @Override // com.xiaopeng.appstore.appserver_common.v2.RequestProtoListener
                    public final void onResponse(MessageLite messageLite) {
                        ServerManager.get().send("appstore", method, null, r4 != null ? messageLite.toByteArray() : null);
                    }
                });
                return "";
            case 4:
                if (!TextUtils.isEmpty(param)) {
                    this.mServerProvider.showPrivacyDialog(new Runnable() { // from class: com.xiaopeng.appstore.appserver_common.-$$Lambda$NapaHandler$MD2uxL5dPKEB7AX94e1c9GjxUvk
                        @Override // java.lang.Runnable
                        public final void run() {
                            ServerManager.get().send("appstore", NapaHandler.MSG_ID_PRIVACY_AGREED, "", null);
                        }
                    }, NumberUtils.stringToInt(param, 0) > 0);
                    return "";
                }
                break;
            default:
                Logger.t(TAG).w("Unknown method in AppStore module:" + method + ", param:" + param, new Object[0]);
                return "";
        }
        Logger.t(TAG).w("Error parameters for:" + method + ", param:" + param + " in AppStore Module", new Object[0]);
        return "";
    }

    private String handleAppletModule(String method, String param, byte[] blob) {
        method.hashCode();
        if (method.equals(METHOD_LOAD_APPLET_LIST)) {
            this.mServerProvider.loadAppletList();
            return "";
        } else if (method.equals(METHOD_LAUNCH_APPLET)) {
            if (!TextUtils.isEmpty(param)) {
                String[] parseStringParams = parseStringParams(param);
                if (parseStringParams.length == 2) {
                    this.mServerProvider.launchApplet(parseStringParams[0], parseStringParams[1]);
                    return "";
                }
            }
            Logger.t(TAG).w("Error parameters for:" + method + ", param:" + param + " in Applet Module", new Object[0]);
            return "";
        } else {
            Logger.t(TAG).w("Unknown method in Applet module:" + method + ", param:" + param, new Object[0]);
            return "";
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private String handleUsbAppModule(String method, String param, byte[] blob) {
        method.hashCode();
        char c = 65535;
        switch (method.hashCode()) {
            case -1615704200:
                if (method.equals(METHOD_INSTALL_USB_APP)) {
                    c = 0;
                    break;
                }
                break;
            case 648328241:
                if (method.equals(METHOD_GET_USB_APP_LIST)) {
                    c = 1;
                    break;
                }
                break;
            case 1740314757:
                if (method.equals(METHOD_START_LOAD_USB_APP)) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                if (!TextUtils.isEmpty(param)) {
                    this.mServerProvider.installUsbApp(param);
                    return "";
                }
                Logger.t(TAG).w("Error parameters for:" + method + ", param:" + param + " in UsbApp Module", new Object[0]);
                return "";
            case 1:
                break;
            case 2:
                this.mServerProvider.startLoadUsbApp();
                break;
            default:
                Logger.t(TAG).w("Unknown method in UsbApp module:" + method + ", param:" + param, new Object[0]);
                return "";
        }
        return "";
    }

    private String[] parseStringParams(String param) {
        return TextUtils.isEmpty(param) ? new String[0] : TextUtils.split(param, SPLIT_CHAR);
    }

    private void logErrorParams(String module, String method, String param) {
        Logger.t(TAG).w("Error parameters for:" + method + ", param:" + param + " in " + module + " Module", new Object[0]);
    }
}
