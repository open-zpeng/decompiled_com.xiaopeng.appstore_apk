package com.xiaopeng.appstore.appstore_biz.logic.dataexport;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.datamodel.db.AppRemoteStateDao;
import com.xiaopeng.appstore.appstore_biz.datamodel.db.AppStoreDb;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppRemoteStateEntity;
import com.xiaopeng.appstore.appstore_biz.logic.CheckUpdateManager;
import com.xiaopeng.appstore.appstore_biz.logic.dataexport.AppStoreExportProvider;
import com.xiaopeng.appstore.appstore_biz.model2.AppStateContainer;
import com.xiaopeng.appstore.bizcommon.entities.UserInfo;
import com.xiaopeng.appstore.bizcommon.entities.db.AgreementStateDao;
import com.xiaopeng.appstore.bizcommon.entities.db.XpAppStoreDatabase;
import com.xiaopeng.appstore.bizcommon.logic.AgreementDialogHelper;
import com.xiaopeng.appstore.bizcommon.logic.AppStateContract;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreContract;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.utils.AccountUtils;
import com.xiaopeng.appstore.bizcommon.utils.DatabaseUtils;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.ApplicationReadyHelper;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.appstore.xpcommon.privacy.PrivacyUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
/* loaded from: classes2.dex */
public class AppStoreExportProvider extends ContentProvider implements Consumer<Set<AppStateContainer>> {
    private static final String AGREEMENT_STATE_TYPE = "vnd.android.cursor.dir/vnd.com.xiaopeng.appstore.provider.agreement_states";
    private static final int APP_LOCAL_STATES = 2;
    private static final String METHOD_OPEN_APP = "openApp";
    private static final int OPEN_APP_STAGE = 100;
    private static final int REMOTE_ALL_STATES = 1;
    private static final String STATE_LIST_TYPE = "vnd.android.cursor.dir/vnd.com.xiaopeng.appstore.provider.remote_states";
    private static final String TAG = "AppStoreExportProvider";
    private static final int USER_AGREEMENT_STATES = 11;
    private static final UriMatcher sURIMatcher;
    private AgreementStateDao mAgreementStateDao;
    private AppRemoteStateDao mAppRemoteStateDao;
    private Context mContext;
    private HandlerThread mHandlerThread;
    private RefreshLocalDataRunnable<?> mRefreshFromAppDetails;
    private Set<AppStateContainer> mRemoteList;

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        sURIMatcher = uriMatcher;
        uriMatcher.addURI("appstore", AppStoreContract.RemoteState.PATH, 1);
        uriMatcher.addURI("appstore", AppStoreContract.AgreementStage.PATH, 11);
        uriMatcher.addURI("appstore", AppStoreContract.AppLocalState.PATH, 2);
        uriMatcher.addURI("appstore", "open_app_stage", 100);
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Logger.t(TAG).d("onCreate");
        this.mContext = getContext();
        CheckUpdateManager.get().addAppStateListListener(new Handler(getHandlerThread().getLooper()), this);
        return true;
    }

    private HandlerThread getHandlerThread() {
        if (this.mHandlerThread == null) {
            HandlerThread handlerThread = new HandlerThread(TAG);
            this.mHandlerThread = handlerThread;
            handlerThread.start();
        }
        return this.mHandlerThread;
    }

    private AppRemoteStateDao getAppRemoteStateDao() {
        if (this.mAppRemoteStateDao == null) {
            this.mAppRemoteStateDao = AppStoreDb.getInstance(this.mContext).getAppRemoteStateDao();
        }
        return this.mAppRemoteStateDao;
    }

    public AgreementStateDao getAgreementStateDao() {
        if (this.mAgreementStateDao == null) {
            this.mAgreementStateDao = XpAppStoreDatabase.getInstance(this.mContext).getAgreementStateDao();
        }
        return this.mAgreementStateDao;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x015b  */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public android.database.Cursor query(android.net.Uri r17, java.lang.String[] r18, java.lang.String r19, java.lang.String[] r20, java.lang.String r21) {
        /*
            Method dump skipped, instructions count: 550
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.appstore_biz.logic.dataexport.AppStoreExportProvider.query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String):android.database.Cursor");
    }

    private int canStartDownloadSync(boolean showPrivacyDialog, boolean showLogin, final int screenShareId) {
        boolean isLoginSync = AccountUtils.get().isLoginSync(getContext());
        int i = 0;
        Logger.t(TAG).i("canStartDownloadSync, query start, showPrivacyDialog:" + showPrivacyDialog + ", showLogin:" + showLogin + ", shareId:" + screenShareId, new Object[0]);
        UserInfo userInfo = AccountUtils.get().getUserInfo();
        if (isLoginSync && userInfo != null) {
            boolean isNeedReconfirm = PrivacyUtils.isNeedReconfirm(getContext());
            if (isNeedReconfirm && showPrivacyDialog) {
                AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.logic.dataexport.-$$Lambda$AppStoreExportProvider$En7csR5rBEEW-c4LIDpl0_vGst8
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppStoreExportProvider.this.lambda$canStartDownloadSync$0$AppStoreExportProvider(screenShareId);
                    }
                });
            }
            Logger.t(TAG).i("canStartDownloadSync, user login:" + userInfo + ", agreement state:" + isNeedReconfirm, new Object[0]);
        } else {
            Logger.t(TAG).i("canStartDownloadSync, NO user login", new Object[0]);
            i = 100;
            if (showLogin) {
                AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.logic.dataexport.-$$Lambda$AppStoreExportProvider$UwHxDL5s2RlRvLtjiXaBwqTFIP4
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppStoreExportProvider.this.lambda$canStartDownloadSync$1$AppStoreExportProvider(screenShareId);
                    }
                });
            }
        }
        return i;
    }

    public /* synthetic */ void lambda$canStartDownloadSync$0$AppStoreExportProvider(int i) {
        AgreementDialogHelper.getInstance(getContext()).show(null, i, false);
    }

    public /* synthetic */ void lambda$canStartDownloadSync$1$AppStoreExportProvider(int i) {
        AccountUtils.get().goLoginByService(getContext(), i);
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        int match = sURIMatcher.match(uri);
        if (match != 1) {
            if (match != 11) {
                return null;
            }
            return AGREEMENT_STATE_TYPE;
        }
        return STATE_LIST_TYPE;
    }

    @Override // android.content.ContentProvider
    public Bundle call(String method, String arg, Bundle extras) {
        if (METHOD_OPEN_APP.equals(method)) {
            Log.d(TAG, "call," + method + " Start");
            ApplicationReadyHelper.waitApplicationReady();
            Logger.t(TAG).d("call," + method + " Start ready");
            Intent intent = extras != null ? (Intent) extras.getParcelable(AppStateContract.LAUNCH_APP_KEY) : null;
            boolean z = true;
            boolean z2 = extras == null || extras.getBoolean("extra_key_show_dialog", true);
            if (extras != null && !extras.getBoolean(AppStateContract.EXTRA_KEY_PARAMS_UPDATE_IN_BACKGROUND, true)) {
                z = false;
            }
            Logger.t(TAG).e("openApp is deprecated, intent:" + intent + ", showDialog:" + z2 + ", updateInBg:" + z, new Object[0]);
            if (intent != null) {
                if (TextUtils.isEmpty(AppComponentManager.get().getAppName(intent))) {
                    Logger.t(TAG).w("openApp, app name not found:" + intent, new Object[0]);
                }
                Bundle bundle = new Bundle();
                bundle.putInt("code", 0);
                bundle.putString(NotificationCompat.CATEGORY_MESSAGE, "Current openApp is not supported. Use startActivity instead.");
                return bundle;
            }
            Logger.t(TAG).d("No intent was found");
        }
        return null;
    }

    @Override // java.util.function.Consumer
    public void accept(Set<AppStateContainer> list) {
        if (list == null) {
            return;
        }
        Logger.t(TAG).i("OnAppStatesChanged, list=" + list, new Object[0]);
        this.mRemoteList = list;
        if (this.mRefreshFromAppDetails == null) {
            this.mRefreshFromAppDetails = new RefreshWithAppStates(getAppRemoteStateDao());
        }
        ((RefreshWithAppStates) this.mRefreshFromAppDetails).setData(new ArrayList(list));
        this.mRefreshFromAppDetails.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static abstract class RefreshLocalDataRunnable<D> implements Runnable {
        private static final String TAG = "ProviderRefresh";
        private final WeakReference<AppRemoteStateDao> mAppRemoteStateDaoRef;
        private List<D> mList;

        protected abstract List<AppRemoteStateEntity> parseData(List<D> list);

        RefreshLocalDataRunnable(AppRemoteStateDao dao) {
            this.mAppRemoteStateDaoRef = new WeakReference<>(dao);
        }

        public void setData(List<D> list) {
            this.mList = list;
        }

        @Override // java.lang.Runnable
        public void run() {
            List<D> list = this.mList;
            if (list == null) {
                Logger.t(TAG).w("run error, list is null.", new Object[0]);
                return;
            }
            List<AppRemoteStateEntity> parseData = parseData(list);
            if (parseData != null) {
                executeRefresh(parseData);
            }
        }

        protected void executeRefresh(final List<AppRemoteStateEntity> entities) {
            WeakReference<AppRemoteStateDao> weakReference = this.mAppRemoteStateDaoRef;
            final AppRemoteStateDao appRemoteStateDao = weakReference != null ? weakReference.get() : null;
            if (appRemoteStateDao != null) {
                Logger.t(TAG).i("executeRefresh " + entities, new Object[0]);
                DatabaseUtils.tryExecute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.logic.dataexport.-$$Lambda$AppStoreExportProvider$RefreshLocalDataRunnable$Rw_nzibqLCq0k51oSZxL_GRqhA8
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppStoreExportProvider.RefreshLocalDataRunnable.this.lambda$executeRefresh$0$AppStoreExportProvider$RefreshLocalDataRunnable(appRemoteStateDao, entities);
                    }
                });
                return;
            }
            Logger.t(TAG).w("executeRefresh error, dao is null. " + entities, new Object[0]);
        }

        public /* synthetic */ void lambda$executeRefresh$0$AppStoreExportProvider$RefreshLocalDataRunnable(AppRemoteStateDao appRemoteStateDao, List list) {
            appRemoteStateDao.clearThenInsert(list);
            notifyChange();
        }

        protected void notifyChange() {
            Utils.getApp().getContentResolver().notifyChange(AppStateContract.URI, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class RefreshWithAppStates extends RefreshLocalDataRunnable<AppStateContainer> {
        RefreshWithAppStates(AppRemoteStateDao dao) {
            super(dao);
        }

        @Override // com.xiaopeng.appstore.appstore_biz.logic.dataexport.AppStoreExportProvider.RefreshLocalDataRunnable
        protected List<AppRemoteStateEntity> parseData(List<AppStateContainer> list) {
            AppRemoteStateEntity appRemoteStateEntity;
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList(list.size());
            for (AppStateContainer appStateContainer : list) {
                if (appStateContainer.getState() == 1) {
                    appRemoteStateEntity = new AppRemoteStateEntity(appStateContainer.getPackageName());
                    appRemoteStateEntity.setState(1001);
                } else if (appStateContainer.getState() == 3) {
                    appRemoteStateEntity = new AppRemoteStateEntity(appStateContainer.getPackageName());
                    appRemoteStateEntity.setState(1002);
                    appRemoteStateEntity.setPromptTitle(appStateContainer.getPromptTitle());
                    appRemoteStateEntity.setPromptText(appStateContainer.getPromptText());
                } else if (appStateContainer.getState() == 2) {
                    appRemoteStateEntity = new AppRemoteStateEntity(appStateContainer.getPackageName());
                    appRemoteStateEntity.setState(1051);
                } else {
                    appRemoteStateEntity = null;
                }
                if (appStateContainer.isHasUpdate()) {
                    if (appRemoteStateEntity == null) {
                        appRemoteStateEntity = new AppRemoteStateEntity(appStateContainer.getPackageName());
                    }
                    appRemoteStateEntity.setHasUpdate(true);
                }
                if (appRemoteStateEntity != null) {
                    arrayList.add(appRemoteStateEntity);
                }
            }
            return arrayList;
        }
    }
}
