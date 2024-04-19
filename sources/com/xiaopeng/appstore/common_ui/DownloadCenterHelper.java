package com.xiaopeng.appstore.common_ui;

import android.app.NotificationManager;
import android.content.Context;
import android.database.sqlite.SQLiteFullException;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.SystemClock;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appserver_common.NapaHandler;
import com.xiaopeng.appstore.bizcommon.entities.AppInfo;
import com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer;
import com.xiaopeng.appstore.bizcommon.entities.BitmapInfo;
import com.xiaopeng.appstore.bizcommon.entities.DownloadCenterLocalInfo;
import com.xiaopeng.appstore.bizcommon.entities.Parser;
import com.xiaopeng.appstore.bizcommon.entities.db.DownloadCenterDao;
import com.xiaopeng.appstore.bizcommon.entities.db.XpAppStoreDatabase;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.SimpleItemAssembleObserver;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.logic.app.LauncherIcons;
import com.xiaopeng.appstore.bizcommon.utils.DatabaseUtils;
import com.xiaopeng.appstore.common_ui.DownloadCenterHelper;
import com.xiaopeng.appstore.libcommon.utils.ImageUtils;
import com.xiaopeng.appstore.libcommon.utils.SPUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.appstore.xpcommon.NotificationUtils;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
import com.xiaopeng.downloadcenter.DownLoadCenterManager;
import com.xiaopeng.downloadcenter.DownLoadCenterService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
@Deprecated
/* loaded from: classes.dex */
public class DownloadCenterHelper implements DefaultLifecycleObserver {
    private static final String SYNC_DATE = "sync_date";
    private static final long SYNC_INTERVAL = 86400000;
    private static final String TAG = "DownloadHelper";
    private static final long UPDATE_PROGRESS_DURATION = 300;
    private Context mContext;
    private DownloadCenterDao mDownloadCenterDao;
    private Executor mExecutor;
    private NotificationManager mNotificationManager;
    private long mLastProgressUpdate = 0;
    private final SparseArray<AssembleDataContainer> mDownloadCompleteList = new SparseArray<>();
    private final DownLoadCenterService.CallBack mCallBack = new AnonymousClass1();
    private final DownLoadCenterService.CallBack2 mCallBack2 = new DownLoadCenterService.CallBack2() { // from class: com.xiaopeng.appstore.common_ui.DownloadCenterHelper.2
        @Override // com.xiaopeng.downloadcenter.DownLoadCenterService.CallBack2
        public void onInitItem(int id) {
            Logger.t(DownloadCenterHelper.TAG).d("onInitItem, id: " + id);
            DownloadCenterHelper.this.initItem(DownloadCenterHelper.this.find(id));
        }
    };
    private final SimpleItemAssembleObserver mSimpleItemAssembleObserver = new AnonymousClass3();
    private final AppComponentManager.SimpleOnAppsChangedCallback mOnAppsChangedCallback = new AnonymousClass4();
    private final Runnable mSyncNotification = new Runnable() { // from class: com.xiaopeng.appstore.common_ui.DownloadCenterHelper.5
        @Override // java.lang.Runnable
        public void run() {
            long j = SPUtils.getInstance(getClass().getSimpleName()).getLong(DownloadCenterHelper.SYNC_DATE, 0L);
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - j > DownloadCenterHelper.SYNC_INTERVAL) {
                DownloadCenterHelper.this.syncWithNotifications();
                SPUtils.getInstance(getClass().getSimpleName()).put(DownloadCenterHelper.SYNC_DATE, currentTimeMillis);
            }
        }
    };

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        static DownloadCenterHelper sInstance = new DownloadCenterHelper();

        private SingletonHolder() {
        }
    }

    public static DownloadCenterHelper getInstance() {
        return SingletonHolder.sInstance;
    }

    public void init(Context context) {
        Logger.t(TAG).i(NapaHandler.METHOD_INIT, new Object[0]);
        this.mContext = context;
        this.mNotificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        DownLoadCenterManager.get().init(this.mContext, R.drawable.icon_placeholder, this.mCallBack, this.mCallBack2);
        AppStoreAssembleManager.get().addObserver(this.mSimpleItemAssembleObserver);
        AppComponentManager.get().addOnAppsChangedCallback(this.mOnAppsChangedCallback);
    }

    private Executor getExecutor() {
        if (this.mExecutor == null) {
            this.mExecutor = Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$DownloadCenterHelper$_hA3qbGEM6sj2wPjNnOHam6I_v4
                @Override // java.util.concurrent.ThreadFactory
                public final Thread newThread(Runnable runnable) {
                    return DownloadCenterHelper.lambda$getExecutor$0(runnable);
                }
            });
        }
        return this.mExecutor;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Thread lambda$getExecutor$0(Runnable runnable) {
        Thread newThread = Executors.defaultThreadFactory().newThread(runnable);
        newThread.setName("DownloadHelperThread");
        return newThread;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public DownloadCenterDao getDownloadCenterDao() {
        if (this.mDownloadCenterDao == null) {
            this.mDownloadCenterDao = XpAppStoreDatabase.getInstance().getDownloadCenterDao();
        }
        return this.mDownloadCenterDao;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.common_ui.DownloadCenterHelper$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements DownLoadCenterService.CallBack {
        AnonymousClass1() {
        }

        @Override // com.xiaopeng.downloadcenter.DownLoadCenterService.CallBack
        public void onPause(final int id) {
            if (!DownloadCenterHelper.this.isDownloadComplete(id)) {
                DownloadCenterHelper.this.execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$DownloadCenterHelper$1$O1Px1pOgX2mhU4OJ3XBK-a3aV9w
                    @Override // java.lang.Runnable
                    public final void run() {
                        DownloadCenterHelper.AnonymousClass1.this.lambda$onPause$0$DownloadCenterHelper$1(id);
                    }
                });
            } else {
                Logger.t(DownloadCenterHelper.TAG).i("onPause ignore, id: " + id + " download Complete.", new Object[0]);
            }
        }

        public /* synthetic */ void lambda$onPause$0$DownloadCenterHelper$1(int i) {
            DownloadCenterHelper.this.notifyDownloadCenterBtnDisable(i, true);
            AssembleDataContainer find = DownloadCenterHelper.this.find(i);
            if (find != null) {
                String downloadUrl = find.getDownloadUrl();
                Logger.t(DownloadCenterHelper.TAG).i("onPause, id: " + i + " downloadUrl=" + downloadUrl, new Object[0]);
                if (!TextUtils.isEmpty(downloadUrl)) {
                    if (AppStoreAssembleManager.get().waitPause(downloadUrl)) {
                        Logger.t(DownloadCenterHelper.TAG).i("onPause success, id=" + i + " downloadUrl=" + downloadUrl, new Object[0]);
                    } else {
                        Logger.t(DownloadCenterHelper.TAG).i("onPause error, already paused, id=" + i + " downloadUrl=" + downloadUrl, new Object[0]);
                        DownloadCenterHelper.this.notifyDownloadCenterBtnDisable(i, false);
                        return;
                    }
                } else {
                    Logger.t(DownloadCenterHelper.TAG).w("onPause, error, downloadUrl is null, id=" + i, new Object[0]);
                }
                EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_MAIN, EventEnum.OPEN_APP_FROM_DROPDOWN_ENTRANCE, 5, find.getPackageName(), find.getAppLabel());
            } else {
                Logger.t(DownloadCenterHelper.TAG).w("onPause error, data is null, id=" + i, new Object[0]);
            }
            DownloadCenterHelper.this.notifyDownloadCenterBtnDisable(i, false);
            DownloadCenterHelper.this.notifyDownloadPause(i);
        }

        @Override // com.xiaopeng.downloadcenter.DownLoadCenterService.CallBack
        public void onResume(final int id) {
            if (!DownloadCenterHelper.this.isDownloadComplete(id)) {
                DownloadCenterHelper.this.execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$DownloadCenterHelper$1$ztY_kBmXPxgjnDY7yo9eXbK-gfM
                    @Override // java.lang.Runnable
                    public final void run() {
                        DownloadCenterHelper.AnonymousClass1.this.lambda$onResume$1$DownloadCenterHelper$1(id);
                    }
                });
            } else {
                Logger.t(DownloadCenterHelper.TAG).i("onResume ignore, id: " + id + " download Complete.", new Object[0]);
            }
        }

        public /* synthetic */ void lambda$onResume$1$DownloadCenterHelper$1(int i) {
            DownloadCenterHelper.this.notifyDownloadCenterBtnDisable(i, true);
            Logger.t(DownloadCenterHelper.TAG).i("onResume, id: " + i, new Object[0]);
            AssembleDataContainer find = DownloadCenterHelper.this.find(i);
            if (find != null && !TextUtils.isEmpty(find.getDownloadUrl())) {
                AppStoreAssembleManager.get().start(find.getDownloadUrl(), find.getPackageName(), find.getAppLabel(), find.getIconUrl());
                EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_MAIN, EventEnum.OPEN_APP_FROM_DROPDOWN_ENTRANCE, 6, find.getPackageName(), find.getAppLabel());
                DownloadCenterHelper.this.notifyDownloadCenterBtnDisable(i, false);
                return;
            }
            Logger.t(DownloadCenterHelper.TAG).w("onResume: error, data is null", new Object[0]);
            DownloadCenterHelper.this.notifyDownloadCenterRemove(i);
        }

        @Override // com.xiaopeng.downloadcenter.DownLoadCenterService.CallBack
        public void onRetry(final int id) {
            if (!DownloadCenterHelper.this.isDownloadComplete(id)) {
                DownloadCenterHelper.this.execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$DownloadCenterHelper$1$IwJjRXGSfZriY4LnJYcJ2bwYlzs
                    @Override // java.lang.Runnable
                    public final void run() {
                        DownloadCenterHelper.AnonymousClass1.this.lambda$onRetry$2$DownloadCenterHelper$1(id);
                    }
                });
            } else {
                Logger.t(DownloadCenterHelper.TAG).i("onRetry ignore, id: " + id + " download Complete.", new Object[0]);
            }
        }

        public /* synthetic */ void lambda$onRetry$2$DownloadCenterHelper$1(int i) {
            Logger.t(DownloadCenterHelper.TAG).i("onRetry, id: " + i, new Object[0]);
            DownloadCenterHelper.this.notifyDownloadCenterBtnDisable(i, true);
            AssembleDataContainer find = DownloadCenterHelper.this.find(i);
            if (find != null && !TextUtils.isEmpty(find.getDownloadUrl())) {
                AppStoreAssembleManager.get().start(find.getDownloadUrl(), find.getPackageName(), find.getAppLabel(), find.getIconUrl());
                DownloadCenterHelper.this.notifyDownloadCenterBtnDisable(i, false);
                return;
            }
            Logger.t(DownloadCenterHelper.TAG).w("onRetry: error, data is null", new Object[0]);
            DownloadCenterHelper.this.notifyDownloadCenterRemove(i);
        }

        @Override // com.xiaopeng.downloadcenter.DownLoadCenterService.CallBack
        public void onOpen(final int id) {
            Logger.t(DownloadCenterHelper.TAG).i("onOpen, id: " + id, new Object[0]);
            DownloadCenterHelper.this.execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$DownloadCenterHelper$1$_u83v5Wuo0EsFFM47ezVaHlfhJg
                @Override // java.lang.Runnable
                public final void run() {
                    DownloadCenterHelper.AnonymousClass1.this.lambda$onOpen$3$DownloadCenterHelper$1(id);
                }
            });
        }

        public /* synthetic */ void lambda$onOpen$3$DownloadCenterHelper$1(int i) {
            AssembleDataContainer find = DownloadCenterHelper.this.find(i);
            if (find != null) {
                String packageName = find.getPackageName();
                if (!TextUtils.isEmpty(packageName)) {
                    Logger.t(DownloadCenterHelper.TAG).i("onOpen: pn=" + packageName, new Object[0]);
                    OpenAppMgr.get().open(Utils.getApp(), packageName, find.getAppLabel());
                    EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_MAIN, EventEnum.OPEN_APP_FROM_DROPDOWN_ENTRANCE, 3, find.getPackageName(), find.getAppLabel());
                }
                DownloadCenterHelper.this.removeData(i);
            } else {
                Logger.t(DownloadCenterHelper.TAG).w("onOpen error: data is null, id=" + i, new Object[0]);
            }
            DownloadCenterHelper.this.notifyDownloadCenterRemove(i);
        }

        @Override // com.xiaopeng.downloadcenter.DownLoadCenterService.CallBack
        public void onCancel(final int id) {
            Logger.t(DownloadCenterHelper.TAG).i("onCancel, id: " + id, new Object[0]);
            if (!DownloadCenterHelper.this.isDownloadComplete(id)) {
                DownloadCenterHelper.this.execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$DownloadCenterHelper$1$-ax_S_CbDtKAp453xuRQur7zyqM
                    @Override // java.lang.Runnable
                    public final void run() {
                        DownloadCenterHelper.AnonymousClass1.this.lambda$onCancel$4$DownloadCenterHelper$1(id);
                    }
                });
            } else {
                Logger.t(DownloadCenterHelper.TAG).i("onCancel ignore, id: " + id + " download Complete.", new Object[0]);
            }
        }

        public /* synthetic */ void lambda$onCancel$4$DownloadCenterHelper$1(int i) {
            AssembleDataContainer find = DownloadCenterHelper.this.find(i);
            if (find != null) {
                String downloadUrl = find.getDownloadUrl();
                String configUrl = find.getConfigUrl();
                Logger.t(DownloadCenterHelper.TAG).i("onCancel: downloadUrl=" + downloadUrl + " config=" + configUrl, new Object[0]);
                if (!TextUtils.isEmpty(configUrl)) {
                    AppStoreAssembleManager.get().waitCancel(configUrl);
                }
                if (!TextUtils.isEmpty(downloadUrl)) {
                    DownloadCenterHelper.this.notifyDownloadCenterBtnDisable(i, true);
                    AppStoreAssembleManager.get().waitCancel(downloadUrl);
                    EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_MAIN, EventEnum.OPEN_APP_FROM_DROPDOWN_ENTRANCE, 4, find.getPackageName(), find.getAppLabel());
                }
                DownloadCenterHelper.this.removeData(i);
            } else {
                Logger.t(DownloadCenterHelper.TAG).w("onCancel error: data is null, id=" + i, new Object[0]);
            }
            DownloadCenterHelper.this.notifyDownloadCenterRemove(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.common_ui.DownloadCenterHelper$3  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass3 extends SimpleItemAssembleObserver {
        AnonymousClass3() {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.SimpleItemAssembleObserver, com.xiaopeng.appstore.bizcommon.logic.ItemAssembleObserver
        public void onStateChange(final AssembleDataContainer data) {
            super.onStateChange(data);
            if (data == null) {
                return;
            }
            DownloadCenterHelper.this.execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$DownloadCenterHelper$3$RfKK1-Mk1HQ9Hvp5JkCYWvXFnQA
                @Override // java.lang.Runnable
                public final void run() {
                    DownloadCenterHelper.AnonymousClass3.this.lambda$onStateChange$1$DownloadCenterHelper$3(data);
                }
            });
        }

        public /* synthetic */ void lambda$onStateChange$1$DownloadCenterHelper$3(final AssembleDataContainer assembleDataContainer) {
            int state = assembleDataContainer.getState();
            if (state == 5) {
                Logger.t(DownloadCenterHelper.TAG).i("onStateChange, LOADING:" + assembleDataContainer, new Object[0]);
                DownloadCenterHelper.this.notifyDownloadCenterStart(assembleDataContainer);
            } else if (state == 1) {
                Logger.t(DownloadCenterHelper.TAG).i("onStateChange, DOWNLOAD_RUNNING:" + assembleDataContainer, new Object[0]);
                DatabaseUtils.tryExecute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$DownloadCenterHelper$3$ARH8TmHPZQy0fwdUy4awpwVXRTE
                    @Override // java.lang.Runnable
                    public final void run() {
                        DownloadCenterHelper.AnonymousClass3.this.lambda$onStateChange$0$DownloadCenterHelper$3(assembleDataContainer);
                    }
                });
            } else if (state == 2) {
                Logger.t(DownloadCenterHelper.TAG).i("onStateChange, DOWNLOAD_PAUSE:" + assembleDataContainer, new Object[0]);
                int id = assembleDataContainer.getId();
                DownloadCenterHelper.this.notifyDownloadPause(id);
                DownloadCenterHelper.this.notifyDownloadCenterBtnDisable(id, false);
            } else if (state == 7) {
                Logger.t(DownloadCenterHelper.TAG).i("onStateChange, DOWNLOAD_SUCCEED:" + assembleDataContainer, new Object[0]);
                DownloadCenterHelper.this.mDownloadCompleteList.put(assembleDataContainer.getId(), assembleDataContainer);
                DownloadCenterHelper.this.notifyDownloadCenterBtnDisable(assembleDataContainer.getId(), true);
            } else if (state == 4) {
                Logger.t(DownloadCenterHelper.TAG).i("onStateChange, INSTALL_SUCCEED:" + assembleDataContainer, new Object[0]);
                DownloadCenterHelper.this.notifyDownloadCenterComplete(assembleDataContainer.getId());
            } else if (state == 101) {
                Logger.t(DownloadCenterHelper.TAG).i("onStateChange, ERROR:" + assembleDataContainer, new Object[0]);
                DownloadCenterHelper.this.notifyDownloadCenterError(assembleDataContainer.getId());
            } else if (state == 0 || state == 6) {
                Logger.t(DownloadCenterHelper.TAG).i("onStateChange, NORMAL:" + assembleDataContainer, new Object[0]);
                DownloadCenterHelper.this.notifyDownloadCenterRemove(assembleDataContainer.getId());
            } else if (state == 10001) {
                Logger.t(DownloadCenterHelper.TAG).i("onStateChange, PENDING:" + assembleDataContainer, new Object[0]);
                DownloadCenterHelper.this.tryInitItem(assembleDataContainer);
                DownloadCenterHelper.this.notifyDownloadCenterWaiting(assembleDataContainer.getId());
            } else {
                Logger.t(DownloadCenterHelper.TAG).i("onStateChange, unhandled:" + assembleDataContainer, new Object[0]);
            }
        }

        public /* synthetic */ void lambda$onStateChange$0$DownloadCenterHelper$3(AssembleDataContainer assembleDataContainer) {
            DownloadCenterHelper.this.getDownloadCenterDao().update(Parser.parseAssembleData2DownloadLocal(assembleDataContainer));
            DownloadCenterHelper.this.notifyDownloadCenterDownloadStart(assembleDataContainer);
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.SimpleItemAssembleObserver, com.xiaopeng.appstore.bizcommon.logic.ItemAssembleObserver
        public void onProgressChange(AssembleDataContainer dataContainer) {
            super.onProgressChange(dataContainer);
            onProgressChangedAsync(dataContainer.getRemain(), dataContainer.getId(), dataContainer.getSpeedPerSecond(), dataContainer.getAppLabel(), dataContainer.getProgress());
        }

        private void onProgressChangedAsync(final long remain, final int id, final long currentSpeed, final String appLabel, final float progress) {
            DownloadCenterHelper.this.execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$DownloadCenterHelper$3$b_AnE0CZFBItaoqU4MYNOy1At7c
                @Override // java.lang.Runnable
                public final void run() {
                    DownloadCenterHelper.AnonymousClass3.this.lambda$onProgressChangedAsync$2$DownloadCenterHelper$3(appLabel, id, progress, currentSpeed, remain);
                }
            });
        }

        public /* synthetic */ void lambda$onProgressChangedAsync$2$DownloadCenterHelper$3(String str, int i, float f, long j, long j2) {
            long uptimeMillis = SystemClock.uptimeMillis();
            if (uptimeMillis - DownloadCenterHelper.this.mLastProgressUpdate >= DownloadCenterHelper.UPDATE_PROGRESS_DURATION) {
                DownloadCenterHelper.this.mLastProgressUpdate = uptimeMillis;
                Logger.t(DownloadCenterHelper.TAG).v("ProgressChanged[%s %s] updateProgress, progress=[%s], speed=[%s], smoothRemain=[%s]", str, Integer.valueOf(i), Float.valueOf(f), Long.valueOf(j), Long.valueOf(j2));
                DownLoadCenterManager.get().notifyProgressChanged(i, (int) (f * 100.0f), (int) j2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.common_ui.DownloadCenterHelper$4  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass4 extends AppComponentManager.SimpleOnAppsChangedCallback {
        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageAdded(List<AppInfo> appInfoList) {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageChanged(String packageName, UserHandle user) {
        }

        AnonymousClass4() {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
        public void onPackageRemoved(final String packageName, UserHandle user) {
            DownloadCenterHelper.this.execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$DownloadCenterHelper$4$8QfWkD7Mab3p2SIH3-UdvgAsiFc
                @Override // java.lang.Runnable
                public final void run() {
                    DownloadCenterHelper.AnonymousClass4.this.lambda$onPackageRemoved$0$DownloadCenterHelper$4(packageName);
                }
            });
        }

        public /* synthetic */ void lambda$onPackageRemoved$0$DownloadCenterHelper$4(String str) {
            List<DownloadCenterLocalInfo> list;
            try {
                list = DownloadCenterHelper.this.getDownloadCenterDao().query(str);
            } catch (SQLiteFullException e) {
                Logger.t(DownloadCenterHelper.TAG).e("onPackageRemoved: query faile, ex: " + e, new Object[0]);
                e.printStackTrace();
                list = null;
            }
            if (list != null) {
                for (DownloadCenterLocalInfo downloadCenterLocalInfo : list) {
                    AssembleDataContainer parseDownloadCenterLocal = Parser.parseDownloadCenterLocal(downloadCenterLocalInfo);
                    if (parseDownloadCenterLocal != null) {
                        Logger.t(DownloadCenterHelper.TAG).i("onPackageRemoved, id=" + parseDownloadCenterLocal.getId() + " pn=" + parseDownloadCenterLocal.getPackageName(), new Object[0]);
                        DownloadCenterHelper.this.notifyDownloadCenterRemove(parseDownloadCenterLocal.getId());
                        DownloadCenterHelper.this.removeData(parseDownloadCenterLocal.getId());
                    } else {
                        Logger.t(DownloadCenterHelper.TAG).d("PackageMoved(%s), did not put in downloadCenter, ignore.", str);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void execute(Runnable runnable) {
        getExecutor().execute(runnable);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public void onDestroy(LifecycleOwner owner) {
        getExecutor().execute(this.mSyncNotification);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AssembleDataContainer find(int id) {
        try {
            return Parser.parseDownloadCenterLocal(getDownloadCenterDao().query(id));
        } catch (SQLiteFullException e) {
            Logger.t(TAG).e("taskStart, delete failed, ex: " + e, new Object[0]);
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDownloadComplete(int id) {
        return this.mDownloadCompleteList.get(id) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDownloadCenterStart(AssembleDataContainer data) {
        int id = data.getId();
        Logger.t(TAG).d("notifyDownloadCenterStart id=" + id);
        tryInitItem(data);
        DownLoadCenterManager.get().notifyBuilder(id).setProgress((int) (data.getProgress() * 100.0f)).setRemainTime(-1).setStatus(1).setButtonEnabled(1).notifyChanged();
        trySetIcon(id, data.getIconUrl());
    }

    private void trySetIcon(int id, String iconUrl) {
        if (TextUtils.isEmpty(iconUrl) || DownLoadCenterManager.get().getIcon(id) != null) {
            return;
        }
        Logger.t(TAG).d("trySetIcon: id=%s, icon=%s.", id + "", iconUrl);
        Bitmap loadBitmap = ImageUtils.loadBitmap(iconUrl);
        if (loadBitmap != null) {
            LauncherIcons obtain = LauncherIcons.obtain(Utils.getApp());
            BitmapInfo createWebIconBitmap = obtain.createWebIconBitmap(loadBitmap);
            obtain.recycle();
            DownLoadCenterManager.get().setIcon(id, Icon.createWithBitmap(createWebIconBitmap.icon));
            return;
        }
        Logger.t(TAG).w("trySetIcon: iconBitmap is null, url=(%s).", iconUrl);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryInitItem(AssembleDataContainer data) {
        if (data == null) {
            Logger.t(TAG).w("tryInitItem error, data is null.", new Object[0]);
            return;
        }
        AssembleDataContainer find = find(data.getId());
        if (find != null) {
            if (assembleDataEquals(find, data)) {
                Logger.t(TAG).i("tryInitItem, init already:" + find, new Object[0]);
                return;
            }
            Logger.t(TAG).w("tryInitItem, init already, but not same, exist:" + find + ", new:" + data, new Object[0]);
        }
        initItem(data);
    }

    private boolean assembleDataEquals(AssembleDataContainer data, AssembleDataContainer other) {
        return (data == null || other == null || TextUtils.isEmpty(data.getDownloadUrl()) || !data.getDownloadUrl().equals(other.getDownloadUrl()) || TextUtils.isEmpty(data.getConfigUrl()) || !data.getConfigUrl().equals(other.getConfigUrl())) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initItem(AssembleDataContainer data) {
        if (data == null) {
            Logger.t(TAG).w("initItem error, data is null.", new Object[0]);
            return;
        }
        Logger.t(TAG).i("initItem " + data, new Object[0]);
        removeExist(data.getPackageName(), data.getId());
        DownLoadCenterManager.get().initItem(data.getId(), data.getAppLabel(), data.getTotalBytes(), 100, true, true, null);
        appendData(data);
        trySetIcon(data.getId(), data.getIconUrl());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDownloadCenterDownloadStart(AssembleDataContainer data) {
        int id = data.getId();
        DownLoadCenterManager.get().initItem(data.getId(), data.getAppLabel(), data.getTotalBytes(), 100, true, true, null);
        notifyDownloadCenterBtnDisable(id, false);
        trySetIcon(id, data.getIconUrl());
        Logger.t(TAG).d("notifyDownloadCenterDownloading id=" + id);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDownloadPause(int id) {
        Logger.t(TAG).d("notifyDownloadPause id=" + id);
        DownLoadCenterManager.get().notifyStatusChanged(id, 3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDownloadCenterComplete(int id) {
        DownLoadCenterManager.get().notifyBuilder(id).setButtonEnabled(1).setStatus(4).notifyChanged();
        Logger.t(TAG).d("notifyDownloadCenterComplete id=" + id);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDownloadCenterError(int id) {
        DownLoadCenterManager.get().notifyBuilder(id).setButtonEnabled(1).setStatus(5).notifyChanged();
        Logger.t(TAG).d("notifyDownloadCenterError id=" + id);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDownloadCenterWaiting(int id) {
        DownLoadCenterManager.get().notifyStatusChanged(id, 2);
        Logger.t(TAG).d("notifyDownloadCenterWaiting id=" + id);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDownloadCenterBtnDisable(int id, boolean disable) {
        DownLoadCenterManager.get().notifyButtonChanged(id, disable);
        Logger.t(TAG).d("notifyDownloadCenterBtnDisable id=" + id + " disable=" + disable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDownloadCenterRemove(int id) {
        DownLoadCenterManager.get().cancel(id);
        Logger.t(TAG).d("notifyDownloadCenterRemove id=" + id);
    }

    private void removeExist(String packageName, int exceptId) {
        if (TextUtils.isEmpty(packageName)) {
            return;
        }
        List<Integer> removeReturnOld = getDownloadCenterDao().removeReturnOld(packageName, exceptId);
        if (removeReturnOld != null) {
            for (Integer num : removeReturnOld) {
                notifyDownloadCenterRemove(num.intValue());
            }
        }
        Logger.t(TAG).i("removeExist " + removeReturnOld + " pn=" + packageName + " exceptId=" + exceptId, new Object[0]);
    }

    public void appendData(final AssembleDataContainer data) {
        Logger.t(TAG).i("appendData, data = " + data, new Object[0]);
        DatabaseUtils.tryExecute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$DownloadCenterHelper$ZO1W5xS5w9atJFgojvx-Qx5uP94
            @Override // java.lang.Runnable
            public final void run() {
                DownloadCenterHelper.this.lambda$appendData$1$DownloadCenterHelper(data);
            }
        });
    }

    public /* synthetic */ void lambda$appendData$1$DownloadCenterHelper(AssembleDataContainer assembleDataContainer) {
        getDownloadCenterDao().insert(Parser.parseAssembleData2DownloadLocal(assembleDataContainer));
    }

    public void removeData(final int id) {
        Logger.t(TAG).i("remove data, id = " + id, new Object[0]);
        DatabaseUtils.tryExecute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$DownloadCenterHelper$H-753XsRyi_q4mliiZ4_fbC5x0Y
            @Override // java.lang.Runnable
            public final void run() {
                DownloadCenterHelper.this.lambda$removeData$2$DownloadCenterHelper(id);
            }
        });
    }

    public /* synthetic */ void lambda$removeData$2$DownloadCenterHelper(int i) {
        getDownloadCenterDao().remove(i);
        this.mDownloadCompleteList.remove(i);
    }

    void removeCompleteNotification() {
        List<StatusBarNotification> findCompletedNotifications = findCompletedNotifications();
        if (findCompletedNotifications != null) {
            for (StatusBarNotification statusBarNotification : findCompletedNotifications) {
                Logger.t(TAG).i("removeCompleteNotification id=" + statusBarNotification.getId() + " tag=" + statusBarNotification.getTag(), new Object[0]);
                this.mNotificationManager.cancel(statusBarNotification.getTag(), statusBarNotification.getId());
                removeData(statusBarNotification.getId());
            }
        }
    }

    private List<StatusBarNotification> findCompletedNotifications() {
        StatusBarNotification[] activeNotifications = this.mNotificationManager.getActiveNotifications();
        if (activeNotifications != null) {
            ArrayList arrayList = new ArrayList(Arrays.asList(activeNotifications));
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (((StatusBarNotification) it.next()).getNotification().extras.getInt("xp.download.status") != 4) {
                    it.remove();
                }
            }
            return arrayList;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncWithNotifications() {
        List<StatusBarNotification> downloadNotifications = NotificationUtils.getDownloadNotifications(this.mNotificationManager);
        if (downloadNotifications != null) {
            HashSet hashSet = new HashSet();
            for (StatusBarNotification statusBarNotification : downloadNotifications) {
                hashSet.add(Integer.valueOf(statusBarNotification.getId()));
            }
            Logger.t(TAG).i("syncWithNotifications, notifs:" + hashSet, new Object[0]);
            List<DownloadCenterLocalInfo> list = null;
            try {
                list = getDownloadCenterDao().queryAll();
            } catch (SQLiteFullException e) {
                Logger.t(TAG).e("syncWithNotifications: queryAll failed, ex: " + e, new Object[0]);
                e.printStackTrace();
            }
            if (list != null) {
                for (DownloadCenterLocalInfo downloadCenterLocalInfo : list) {
                    if (!hashSet.contains(Integer.valueOf(downloadCenterLocalInfo.getId()))) {
                        removeData(downloadCenterLocalInfo.getId());
                        Logger.t(TAG).i("syncWithNotifications, remove:" + downloadCenterLocalInfo, new Object[0]);
                    }
                }
            }
        }
    }
}
