package com.xiaopeng.appstore.appserver_common.service;

import android.os.RemoteCallbackList;
import android.os.RemoteException;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appserver_common.AppletListener;
import com.xiaopeng.appstore.appserver_common.ServerProvider;
import com.xiaopeng.appstore.appserver_common.UsbAppListener;
import com.xiaopeng.appstore.appserver_common.UsbEntryListener;
import com.xiaopeng.appstore.appserver_common.v2.AppListListener;
import com.xiaopeng.appstore.protobuf.AppDetailResponseProto;
import com.xiaopeng.appstore.protobuf.AppItemProto;
import com.xiaopeng.appstore.protobuf.AppListProto;
import com.xiaopeng.appstore.protobuf.AppStoreHomeResponseProto;
import com.xiaopeng.appstore.protobuf.AppUpdateResponseProto;
import com.xiaopeng.appstore.protobuf.AppletGroupListProto;
import com.xiaopeng.appstore.protobuf.UsbAppListProto;
import com.xiaopeng.appstore.unity_plugin.IAppListListener;
import com.xiaopeng.appstore.unity_plugin.IAppStoreService;
import com.xiaopeng.appstore.unity_plugin.IAppletListener;
import com.xiaopeng.appstore.unity_plugin.IUsbAppListener;
import com.xiaopeng.appstore.unity_plugin.IUsbEntryListener;
/* loaded from: classes2.dex */
class ServiceBinder extends IAppStoreService.Stub {
    private static final String TAG = "ServiceBinder";
    private final ServerProvider mServerProvider;
    private final RemoteCallbackList<IAppListListener> mAppListListenerList = new RemoteCallbackList<>();
    private final RemoteCallbackList<IUsbEntryListener> mUsbEntryListenerList = new RemoteCallbackList<>();
    private final RemoteCallbackList<IUsbAppListener> mUsbAppListenerList = new RemoteCallbackList<>();
    private final RemoteCallbackList<IAppletListener> mAppletListenerList = new RemoteCallbackList<>();
    private final Object mAppletListenerLock = new Object();

    /* JADX INFO: Access modifiers changed from: package-private */
    public ServiceBinder(ServerProvider serverProvider) {
        Logger.t(TAG).i("create:" + hashCode(), new Object[0]);
        this.mServerProvider = serverProvider;
    }

    public void clear() {
        Logger.t(TAG).i("clear:" + hashCode(), new Object[0]);
        this.mAppListListenerList.kill();
        this.mUsbEntryListenerList.kill();
        this.mUsbAppListenerList.kill();
        this.mAppletListenerList.kill();
    }

    private void setAppListListener() {
        Logger.t(TAG).i("setAppListListener", new Object[0]);
        this.mServerProvider.setAppListListenerV2(new AppListListener() { // from class: com.xiaopeng.appstore.appserver_common.service.ServiceBinder.1
            @Override // com.xiaopeng.appstore.appserver_common.v2.AppListListener
            public void onAppMoved(int fromPosition, int toPosition) {
            }

            @Override // com.xiaopeng.appstore.appserver_common.v2.AppListListener
            public void onAppInserted(int position, int count) {
                Logger.t(ServiceBinder.TAG).i("onAppInserted, pos:" + position + ", count:" + count + ", listeners:" + ServiceBinder.this.mAppletListenerList.getRegisteredCallbackCount(), new Object[0]);
                RemoteCallbackList remoteCallbackList = ServiceBinder.this.mAppListListenerList;
                int beginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        try {
                            ((IAppListListener) remoteCallbackList.getBroadcastItem(i)).onAppInserted(position, count);
                        } catch (RemoteException e) {
                            Logger.t(ServiceBinder.TAG).w("dispatchEvent onAppInserted ex:" + e + ", position:" + position + ", count:" + count, new Object[0]);
                            e.printStackTrace();
                        }
                    } finally {
                        remoteCallbackList.finishBroadcast();
                    }
                }
            }

            @Override // com.xiaopeng.appstore.appserver_common.v2.AppListListener
            public void onAppRemoved(int position, int count) {
                Logger.t(ServiceBinder.TAG).i("onAppRemoved, pos:" + position + ", count:" + count + ", listeners:" + ServiceBinder.this.mAppletListenerList.getRegisteredCallbackCount(), new Object[0]);
                RemoteCallbackList remoteCallbackList = ServiceBinder.this.mAppListListenerList;
                int beginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        try {
                            ((IAppListListener) remoteCallbackList.getBroadcastItem(i)).onAppRemoved(position, count);
                        } catch (RemoteException e) {
                            Logger.t(ServiceBinder.TAG).w("dispatchEvent onAppRemoved ex:" + e + ", position:" + position + ", count:" + count, new Object[0]);
                            e.printStackTrace();
                        }
                    } finally {
                        remoteCallbackList.finishBroadcast();
                    }
                }
            }

            @Override // com.xiaopeng.appstore.appserver_common.v2.AppListListener
            public void onAppChanged(int position, int count, Object payload) {
                Logger.t(ServiceBinder.TAG).i("onAppChanged, pos:" + position + ", count:" + count + ", listeners:" + ServiceBinder.this.mAppletListenerList.getRegisteredCallbackCount(), new Object[0]);
                RemoteCallbackList remoteCallbackList = ServiceBinder.this.mAppListListenerList;
                int beginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        try {
                            ((IAppListListener) remoteCallbackList.getBroadcastItem(i)).onAppChanged(position, count);
                        } catch (RemoteException e) {
                            Logger.t(ServiceBinder.TAG).w("dispatchEvent onAppChanged ex:" + e + ", position:" + position + ", count:" + count, new Object[0]);
                            e.printStackTrace();
                        }
                    } finally {
                        remoteCallbackList.finishBroadcast();
                    }
                }
            }
        });
    }

    private void clearAppListListener() {
        this.mServerProvider.setAppListListenerV2(null);
    }

    private void setUsbEntryListener() {
        this.mServerProvider.setUsbEntryListener(new UsbEntryListener() { // from class: com.xiaopeng.appstore.appserver_common.service.ServiceBinder.2
            @Override // com.xiaopeng.appstore.appserver_common.UsbEntryListener
            public void onShowUsbEntry(boolean isLoading) {
                RemoteCallbackList remoteCallbackList = ServiceBinder.this.mUsbEntryListenerList;
                int beginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        try {
                            ((IUsbEntryListener) remoteCallbackList.getBroadcastItem(i)).onShowUsbEntry(isLoading);
                        } catch (RemoteException e) {
                            Logger.t(ServiceBinder.TAG).w("dispatchEvent onShowUsbEntry ex:" + e + ", isLoading:" + isLoading, new Object[0]);
                            e.printStackTrace();
                        }
                    } finally {
                        remoteCallbackList.finishBroadcast();
                    }
                }
            }

            @Override // com.xiaopeng.appstore.appserver_common.UsbEntryListener
            public void onHideUsbEntry() {
                RemoteCallbackList remoteCallbackList = ServiceBinder.this.mUsbEntryListenerList;
                int beginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        try {
                            ((IUsbEntryListener) remoteCallbackList.getBroadcastItem(i)).onHideUsbEntry();
                        } catch (RemoteException e) {
                            Logger.t(ServiceBinder.TAG).w("dispatchEvent onHideUsbEntry ex:" + e, new Object[0]);
                            e.printStackTrace();
                        }
                    } finally {
                        remoteCallbackList.finishBroadcast();
                    }
                }
            }
        });
    }

    private void clearUsbEntryListener() {
        this.mServerProvider.setUsbEntryListener(null);
    }

    private void setUsbAppListener() {
        this.mServerProvider.setUsbAppListener(new UsbAppListener() { // from class: com.xiaopeng.appstore.appserver_common.service.ServiceBinder.3
            @Override // com.xiaopeng.appstore.appserver_common.UsbAppListener
            public void onUsbAppChanged(int index, AppItemProto appItem) {
                if (appItem == null) {
                    return;
                }
                RemoteCallbackList remoteCallbackList = ServiceBinder.this.mUsbAppListenerList;
                int beginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        try {
                            ((IUsbAppListener) remoteCallbackList.getBroadcastItem(i)).onUsbAppChanged(index, appItem.toByteArray());
                        } catch (RemoteException e) {
                            Logger.t(ServiceBinder.TAG).w("dispatchEvent onUsbAppChanged ex:" + e + ", index:" + index + ", item:" + appItem, new Object[0]);
                            e.printStackTrace();
                        }
                    } finally {
                        remoteCallbackList.finishBroadcast();
                    }
                }
            }
        });
    }

    private void clearUsbAppListener() {
        this.mServerProvider.setUsbAppListener(null);
    }

    private void setAppletListener() {
        this.mServerProvider.setAppletListener(new AppletListener() { // from class: com.xiaopeng.appstore.appserver_common.service.ServiceBinder.4
            @Override // com.xiaopeng.appstore.appserver_common.AppletListener
            public void onAppletListChanged(AppletGroupListProto appletGroupListProto) {
                synchronized (ServiceBinder.this.mAppletListenerLock) {
                    if (appletGroupListProto == null) {
                        return;
                    }
                    RemoteCallbackList remoteCallbackList = ServiceBinder.this.mAppletListenerList;
                    int beginBroadcast = remoteCallbackList.beginBroadcast();
                    for (int i = 0; i < beginBroadcast; i++) {
                        try {
                            ((IAppletListener) remoteCallbackList.getBroadcastItem(i)).onAppletListChanged(appletGroupListProto.toByteArray());
                        } catch (RemoteException e) {
                            Logger.t(ServiceBinder.TAG).w("dispatchEvent onUsbAppChanged ex:" + e + ", group:" + appletGroupListProto, new Object[0]);
                            e.printStackTrace();
                        }
                    }
                    remoteCallbackList.finishBroadcast();
                }
            }
        });
    }

    private void clearAppletListener() {
        this.mServerProvider.setAppletListener(null);
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void create() throws RemoteException {
        this.mServerProvider.create();
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void start() throws RemoteException {
        this.mServerProvider.start();
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void stop() throws RemoteException {
        this.mServerProvider.stop();
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void destroy() throws RemoteException {
        this.mServerProvider.destroy();
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void enableDebug(boolean debug) throws RemoteException {
        this.mServerProvider.enableDebug(debug);
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public boolean isInDebug() throws RemoteException {
        return this.mServerProvider.isInDebug();
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void registerAppListListener(IAppListListener listener) throws RemoteException {
        Logger.t(TAG).i("registerAppListListener:" + listener + ", ignore in this version", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void unregisterAppListListener(IAppListListener listener) throws RemoteException {
        Logger.t(TAG).i("unregisterAppListListener:" + listener, new Object[0]);
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public boolean launchApp(String packageName) throws RemoteException {
        return this.mServerProvider.launchApp(packageName);
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public boolean uninstallApp(String packageName) throws RemoteException {
        return this.mServerProvider.uninstallApp(packageName);
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void startLoadAppList() throws RemoteException {
        this.mServerProvider.startLoadAppList();
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public byte[] getAppListProto() throws RemoteException {
        AppListProto appListProto = this.mServerProvider.getAppListProto();
        if (appListProto != null) {
            return appListProto.toByteArray();
        }
        return null;
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public boolean isInstalled(String packageName, boolean useCache) throws RemoteException {
        return this.mServerProvider.isInstalled(packageName, useCache);
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void persistAppOrder(byte[] appIndexList) throws RemoteException {
        this.mServerProvider.persistAppOrder(appIndexList);
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public byte[] getStoreHomeProto() throws RemoteException {
        AppStoreHomeResponseProto storeHomeProto = this.mServerProvider.getStoreHomeProto();
        if (storeHomeProto != null) {
            return storeHomeProto.toByteArray();
        }
        return null;
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public byte[] getAppDetailProto(String packageName) throws RemoteException {
        AppDetailResponseProto appDetailProto = this.mServerProvider.getAppDetailProto(packageName);
        if (appDetailProto != null) {
            return appDetailProto.toByteArray();
        }
        return null;
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public byte[] getUpdateProto() throws RemoteException {
        AppUpdateResponseProto updateProto = this.mServerProvider.getUpdateProto();
        if (updateProto != null) {
            return updateProto.toByteArray();
        }
        return null;
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public boolean isPrivacyAgreed() throws RemoteException {
        return this.mServerProvider.isPrivacyAgreed();
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void showPrivacyDialog(boolean onlyContent) throws RemoteException {
        this.mServerProvider.showPrivacyDialog(onlyContent);
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public int getHttpHost() throws RemoteException {
        return this.mServerProvider.getHttpHost();
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void switchHttpPreHost() throws RemoteException {
        this.mServerProvider.switchHttpPreHost();
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void switchHttpTestHost() throws RemoteException {
        this.mServerProvider.switchHttpTestHost();
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void resetHttpHost() throws RemoteException {
        this.mServerProvider.resetHttpHost();
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void startLoadUsbApp() throws RemoteException {
        this.mServerProvider.startLoadUsbApp();
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public byte[] getUsbAppList() throws RemoteException {
        UsbAppListProto usbAppList = this.mServerProvider.getUsbAppList();
        if (usbAppList != null) {
            return usbAppList.toByteArray();
        }
        return null;
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void installUsbApp(String packageName) throws RemoteException {
        this.mServerProvider.installUsbApp(packageName);
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void registerUsbEntryListener(IUsbEntryListener listener) throws RemoteException {
        Logger.t(TAG).i("registerUsbEntryListener:" + listener + ", ignore in this version", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void unregisterUsbEntryListener(IUsbEntryListener listener) throws RemoteException {
        Logger.t(TAG).i("unregisterUsbEntryListener:" + listener, new Object[0]);
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void registerUsbAppListener(IUsbAppListener listener) throws RemoteException {
        Logger.t(TAG).i("registerUsbAppListener:" + listener + ", ignore in this version", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void unregisterUsbAppListener(IUsbAppListener listener) throws RemoteException {
        Logger.t(TAG).i("unregisterUsbAppListener:" + listener, new Object[0]);
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void loadAppletList() throws RemoteException {
        this.mServerProvider.loadAppletList();
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void launchApplet(String id, String name) throws RemoteException {
        this.mServerProvider.launchApplet(id, name);
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void registerAppletListener(IAppletListener listener) throws RemoteException {
        Logger.t(TAG).i("registerAppletListener:" + listener + ", ignore in this version", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.unity_plugin.IAppStoreService
    public void unregisterAppletListener(IAppletListener listener) throws RemoteException {
        Logger.t(TAG).i("unregisterAppletListener:" + listener, new Object[0]);
    }
}
