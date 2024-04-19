package com.xiaopeng.appstore.applist_biz.logic;

import android.content.ContentUris;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.storeprovider.AssembleInfo;
import com.xiaopeng.appstore.storeprovider.ResourceProviderContract;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/* loaded from: classes2.dex */
public class AppDownloadObserver {
    private static final Uri ASSEMBLE_URI_APP = ContentUris.withAppendedId(ResourceProviderContract.ASSEMBLE_URI, 1000);
    private static final String CONFIG_DOWNLOAD_KEY_PREFIX = "config_";
    private static final String TAG = "AppDownloadObserver";
    private ContentObserver mAssembleDataObserver;
    private Map<String, AppHolder> mCache;
    private Context mContext;
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private final Object mListenerLock;
    private Set<OnChangeListener> mOnChangeListeners;
    private Set<String> mTempPackageSet;

    /* loaded from: classes2.dex */
    public interface OnChangeListener {
        void onDownloadTrackAdd(AppHolder appHolder);

        void onDownloadTrackRemove(AppHolder appHolder);
    }

    /* loaded from: classes2.dex */
    static class SingletonHolder {
        static AppDownloadObserver sInstance = new AppDownloadObserver();

        SingletonHolder() {
        }
    }

    public static AppDownloadObserver get() {
        return SingletonHolder.sInstance;
    }

    private AppDownloadObserver() {
        this.mListenerLock = new Object();
    }

    public void init(Context context) {
        if (isInit()) {
            throw new IllegalStateException("Already init:" + hashCode());
        }
        this.mCache = new HashMap();
        this.mOnChangeListeners = new HashSet();
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        if (this.mHandlerThread == null) {
            HandlerThread handlerThread = new HandlerThread(TAG);
            this.mHandlerThread = handlerThread;
            handlerThread.start();
            this.mHandler = new Handler(this.mHandlerThread.getLooper());
            this.mAssembleDataObserver = new ContentObserver(this.mHandler) { // from class: com.xiaopeng.appstore.applist_biz.logic.AppDownloadObserver.1
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    super.onChange(selfChange, uri);
                    AppDownloadObserver.this.update();
                }
            };
            applicationContext.getContentResolver().registerContentObserver(ASSEMBLE_URI_APP, false, this.mAssembleDataObserver);
        }
    }

    public void release() {
        Map<String, AppHolder> map = this.mCache;
        if (map != null) {
            map.clear();
        }
        synchronized (this.mListenerLock) {
            Set<OnChangeListener> set = this.mOnChangeListeners;
            if (set != null) {
                set.clear();
            }
            this.mOnChangeListeners = null;
        }
        Context context = this.mContext;
        if (context != null) {
            context.getContentResolver().unregisterContentObserver(this.mAssembleDataObserver);
        }
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quit();
        }
        this.mContext = null;
    }

    public void registerListener(OnChangeListener listener) {
        synchronized (this.mListenerLock) {
            Set<OnChangeListener> set = this.mOnChangeListeners;
            if (set != null) {
                set.add(listener);
            }
        }
    }

    public void unregister(OnChangeListener listener) {
        synchronized (this.mListenerLock) {
            Set<OnChangeListener> set = this.mOnChangeListeners;
            if (set != null) {
                set.remove(listener);
            }
        }
    }

    public boolean isInit() {
        HandlerThread handlerThread = this.mHandlerThread;
        return handlerThread != null && handlerThread.isAlive();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:79:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void update() {
        /*
            Method dump skipped, instructions count: 354
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.applist_biz.logic.AppDownloadObserver.update():void");
    }

    private boolean isRunningState(int state) {
        return ((state == 2) || AssembleInfo.isPaused(state) || AssembleInfo.isFinished(state) || (state == 0)) ? false : true;
    }

    private boolean isConfigDownload(String key) {
        return key.startsWith(CONFIG_DOWNLOAD_KEY_PREFIX);
    }

    private void dispatchTrackAdd(AppHolder appHolder) {
        Logger.t(TAG).i("dispatchTrackAdd:" + appHolder, new Object[0]);
        synchronized (this.mListenerLock) {
            Set<OnChangeListener> set = this.mOnChangeListeners;
            if (set != null) {
                for (OnChangeListener onChangeListener : set) {
                    onChangeListener.onDownloadTrackAdd(appHolder);
                }
            }
        }
    }

    private void dispatchTrackRemove(AppHolder appHolder) {
        Logger.t(TAG).i("dispatchTrackRemove:" + appHolder, new Object[0]);
        synchronized (this.mListenerLock) {
            Set<OnChangeListener> set = this.mOnChangeListeners;
            if (set != null) {
                for (OnChangeListener onChangeListener : set) {
                    onChangeListener.onDownloadTrackRemove(appHolder);
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class AppHolder {
        private final String mIconUrl;
        private final String mName;
        private final String mPackageName;
        private float mProgress;
        private int mState;

        public AppHolder(String packageName, String name, String iconUrl) {
            this.mPackageName = packageName;
            this.mName = name;
            this.mIconUrl = iconUrl;
        }

        public String toString() {
            return "AppHolder{pn='" + this.mPackageName + "', name='" + this.mName + "', icon='" + this.mIconUrl + "', state=" + AssembleInfo.stateToStr(this.mState) + ", progress=" + this.mProgress + '}';
        }

        public String getKey() {
            return this.mPackageName;
        }

        public String getName() {
            return this.mName;
        }

        public String getIconUrl() {
            return this.mIconUrl;
        }

        public int getState() {
            return this.mState;
        }

        public void setState(int state) {
            this.mState = state;
        }

        public float getProgress() {
            return this.mProgress;
        }

        public void setProgress(float progress) {
            this.mProgress = progress;
        }
    }
}
