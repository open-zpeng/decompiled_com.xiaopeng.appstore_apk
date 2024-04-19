package com.xiaopeng.appstore.bizcommon.logic;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.AppStateContract;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;
/* loaded from: classes2.dex */
public class AppStateContract {
    public static final String AUTHORITY = "appstore";
    public static final String EXTRA_KEY_PARAMS_SHOW_REJECT_DIALOG = "extra_key_show_dialog";
    public static final String EXTRA_KEY_PARAMS_UPDATE_IN_BACKGROUND = "extra_key_params_update_in_background";
    private static final int INDEX_HAS_UPDATE = 2;
    private static final int INDEX_PACKAGE_NAME = 0;
    private static final int INDEX_PROMPT_TEXT = 4;
    private static final int INDEX_PROMPT_TITLE = 3;
    private static final int INDEX_STATE = 1;
    public static final String LAUNCH_APP_KEY = "intent";
    public static final String LAUNCH_RESULT_KEY = "launch_reslut";
    public static final int LAUNCH_SUCCESS = 1000;
    private static final String PACKAGE_NAME_COLUMN = "packageName";
    public static final int STATE_DEFAULT = 0;
    public static final int STATE_FORCE_UPDATE = 1051;
    public static final int STATE_SUSPENDED = 1001;
    public static final int STATE_TIPS = 1002;
    private static final String TAG = "AppStateContract";
    public static final Uri URI = Uri.parse("content://appstore/remote_states");

    public static String translateState(int state) {
        return state == 1051 ? "STATE_FORCE_UPDATE" : state == 1001 ? "STATE_SUSPENDED" : state == 1002 ? "STATE_TIPS" : "unknown_state(" + state + ")";
    }

    public static List<AppState> queryAppStateList(ContentResolver contentResolver, String pn) {
        Cursor query = contentResolver.query(URI, null, "packageName LIKE ?", new String[]{pn}, null);
        if (query != null) {
            try {
                if (query.getCount() != 0) {
                    ArrayList arrayList = new ArrayList(query.getCount());
                    while (query.moveToNext()) {
                        boolean z = query.getInt(2) > 0;
                        int i = query.getInt(1);
                        AppState create = AppState.create(pn);
                        create.setHasUpdate(z);
                        create.setState(i);
                        arrayList.add(create);
                    }
                    Logger.t(TAG).d("queryAppStateList finish: pn=" + pn + " list=" + arrayList);
                    if (query != null) {
                        query.close();
                    }
                    return arrayList;
                }
            } catch (Throwable th) {
                if (query != null) {
                    try {
                        query.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        Logger.t(TAG).d("queryAppStateList finish: no data relative to " + pn);
        if (query != null) {
            query.close();
        }
        return null;
    }

    public static List<AppState> queryAppStateList(ContentResolver contentResolver) {
        Cursor query = contentResolver.query(URI, null, null, null);
        if (query != null) {
            try {
                if (query.getCount() != 0) {
                    ArrayList arrayList = new ArrayList(query.getCount());
                    while (query.moveToNext()) {
                        boolean z = false;
                        String string = query.getString(0);
                        if (query.getInt(2) > 0) {
                            z = true;
                        }
                        int i = query.getInt(1);
                        AppState create = AppState.create(string);
                        create.setHasUpdate(z);
                        create.setState(i);
                        arrayList.add(create);
                    }
                    Logger.t(TAG).d("queryAppStateList finish: list=" + arrayList);
                    if (query != null) {
                        query.close();
                    }
                    return arrayList;
                }
            } catch (Throwable th) {
                if (query != null) {
                    try {
                        query.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        Logger.t(TAG).d("queryAppStateList finish: no data");
        if (query != null) {
            query.close();
        }
        return null;
    }

    /* loaded from: classes2.dex */
    public static class AppState {
        private boolean mHasUpdate;
        private String mPackageName;
        private int mState;

        private AppState() {
        }

        public static AppState create(String packageName) {
            AppState appState = new AppState();
            appState.mPackageName = packageName;
            return appState;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public boolean hasUpdate() {
            return this.mHasUpdate;
        }

        public void setHasUpdate(boolean hasUpdate) {
            this.mHasUpdate = hasUpdate;
        }

        public int getState() {
            return this.mState;
        }

        public void setState(int state) {
            this.mState = state;
        }

        public String toString() {
            return "AppState{pn=" + this.mPackageName + ", hasUpdate=" + this.mHasUpdate + ", state=" + AppStateContract.translateState(this.mState) + '}';
        }
    }

    /* loaded from: classes2.dex */
    public static class Cache {
        private final List<AppState> mAppStateList;
        private ContentResolver mContentResolver;
        private Handler mHandler;
        private HandlerThread mHandlerThread;
        private AppStateObserver mObserver;
        private final Set<Runnable> mOnChangeList;

        private Cache() {
            this.mAppStateList = new CopyOnWriteArrayList();
            this.mOnChangeList = new CopyOnWriteArraySet();
        }

        /* loaded from: classes2.dex */
        static class SingletonHolder {
            static Cache sInstance = new Cache();

            SingletonHolder() {
            }
        }

        public static Cache get() {
            return SingletonHolder.sInstance;
        }

        public List<AppState> getAppStateList() {
            assertInit();
            return this.mAppStateList;
        }

        public void init(ContentResolver contentResolver) {
            this.mContentResolver = contentResolver;
            if (this.mHandlerThread == null) {
                HandlerThread handlerThread = new HandlerThread("AppStateCache");
                this.mHandlerThread = handlerThread;
                handlerThread.start();
                this.mHandler = new Handler(this.mHandlerThread.getLooper());
            }
            this.mHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AppStateContract$Cache$GehN_tvueOoma80FEMUdqM8EMnY
                @Override // java.lang.Runnable
                public final void run() {
                    AppStateContract.Cache.this.updateData();
                }
            });
            AppStateObserver appStateObserver = new AppStateObserver(this.mHandler);
            this.mObserver = appStateObserver;
            appStateObserver.setOnChangeListener(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AppStateContract$Cache$GehN_tvueOoma80FEMUdqM8EMnY
                @Override // java.lang.Runnable
                public final void run() {
                    AppStateContract.Cache.this.updateData();
                }
            });
            contentResolver.registerContentObserver(AppStateContract.URI, false, this.mObserver);
        }

        public void release() {
            ContentResolver contentResolver = this.mContentResolver;
            if (contentResolver != null) {
                contentResolver.unregisterContentObserver(this.mObserver);
            }
        }

        public void addOnChangeListener(Runnable lis) {
            this.mOnChangeList.add(lis);
        }

        public void removeOnChangeListener(Runnable lis) {
            this.mOnChangeList.remove(lis);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateData() {
            if (this.mContentResolver != null) {
                this.mAppStateList.clear();
                List<AppState> queryAppStateList = AppStateContract.queryAppStateList(this.mContentResolver);
                if (queryAppStateList != null) {
                    this.mAppStateList.addAll(queryAppStateList);
                }
                dispatchOnChange();
                return;
            }
            Logger.t(AppStateContract.TAG).w("updateData, contentResolver is null", new Object[0]);
        }

        private void dispatchOnChange() {
            Set<Runnable> set = this.mOnChangeList;
            if (set != null) {
                for (Runnable runnable : set) {
                    runnable.run();
                }
            }
        }

        private void assertInit() {
            if (this.mHandler == null) {
                throw new IllegalStateException("Please call init method first.");
            }
        }

        public void parseAppStatesAsync(final Consumer<Map<String, Integer>> onFinish) {
            assertInit();
            this.mHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AppStateContract$Cache$m8eqcRB95BLKvU9UU1iSbWAlQgY
                @Override // java.lang.Runnable
                public final void run() {
                    AppStateContract.Cache.this.lambda$parseAppStatesAsync$0$AppStateContract$Cache(onFinish);
                }
            });
        }

        public /* synthetic */ void lambda$parseAppStatesAsync$0$AppStateContract$Cache(Consumer consumer) {
            HashMap hashMap = new HashMap();
            parseAppStates(hashMap);
            if (consumer != null) {
                consumer.accept(hashMap);
            }
        }

        private void parseAppStates(Map<String, Integer> states) {
            if (states == null) {
                return;
            }
            for (AppState appState : this.mAppStateList) {
                states.put(appState.getPackageName(), Integer.valueOf(appState.getState()));
            }
        }
    }

    /* loaded from: classes2.dex */
    private static class AppStateObserver extends ContentObserver {
        private Runnable mOnChangeListener;

        public AppStateObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Logger.t(AppStateContract.TAG).d("AppStateObserver onChange");
            Runnable runnable = this.mOnChangeListener;
            if (runnable != null) {
                runnable.run();
            }
        }

        public void setOnChangeListener(Runnable onChange) {
            this.mOnChangeListener = onChange;
        }
    }
}
