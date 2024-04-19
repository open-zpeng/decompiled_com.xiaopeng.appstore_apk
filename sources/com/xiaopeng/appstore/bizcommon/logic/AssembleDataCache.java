package com.xiaopeng.appstore.bizcommon.logic;

import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer;
import com.xiaopeng.appstore.bizcommon.entities.AssembleLocalInfo;
import com.xiaopeng.appstore.bizcommon.entities.Parser;
import com.xiaopeng.appstore.bizcommon.entities.db.AssembleDao;
import com.xiaopeng.appstore.bizcommon.entities.db.XpAppStoreDatabase;
import com.xiaopeng.appstore.bizcommon.utils.DatabaseUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/* loaded from: classes2.dex */
public class AssembleDataCache {
    private static final String TAG = "AssembleDataCache";
    private final AssembleDao mAssembleDao;
    private final Object mDataLock;
    private Handler mIOHandler;
    private final Map<String, AssembleDataContainer> mUrlAssembleMap;

    private static String generateKey(String downloadUrl) {
        return downloadUrl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingletonHolder {
        static AssembleDataCache sInstance = new AssembleDataCache();

        private SingletonHolder() {
        }
    }

    public static AssembleDataCache get() {
        return SingletonHolder.sInstance;
    }

    private AssembleDataCache() {
        this.mDataLock = new Object();
        this.mAssembleDao = XpAppStoreDatabase.getInstance().getAssembleDao();
        this.mUrlAssembleMap = generateUrlDataMap();
    }

    private Handler getIOHandler() {
        if (this.mIOHandler == null) {
            HandlerThread handlerThread = new HandlerThread("AssembleCacheTh");
            handlerThread.start();
            this.mIOHandler = new Handler(handlerThread.getLooper());
        }
        return this.mIOHandler;
    }

    public void load() {
        synchronized (this.mDataLock) {
            DatabaseUtils.tryExecute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleDataCache$9nGChQ5BCWOgE8GEtRVNfAOqOa8
                @Override // java.lang.Runnable
                public final void run() {
                    AssembleDataCache.this.lambda$load$0$AssembleDataCache();
                }
            });
        }
    }

    public /* synthetic */ void lambda$load$0$AssembleDataCache() {
        parseAssembleLocalList(this.mAssembleDao.queryAssembleDataList(), this.mUrlAssembleMap);
    }

    public Collection<AssembleDataContainer> getUrlAssembleMap() {
        Collection<AssembleDataContainer> values;
        synchronized (this.mDataLock) {
            values = new LinkedHashMap(this.mUrlAssembleMap).values();
        }
        return values;
    }

    public void insertAsync(final AssembleDataContainer data) {
        getIOHandler().post(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleDataCache$6a-c9rYr6J30gmvzXM3VhLa98yc
            @Override // java.lang.Runnable
            public final void run() {
                AssembleDataCache.this.lambda$insertAsync$1$AssembleDataCache(data);
            }
        });
    }

    /* renamed from: insert */
    public void lambda$insertAsync$1$AssembleDataCache(final AssembleDataContainer data) {
        Logger.t(TAG).i("insert " + data + " thread=" + Thread.currentThread(), new Object[0]);
        final AssembleLocalInfo parseAssembleData = Parser.parseAssembleData(data);
        DatabaseUtils.tryExecute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleDataCache$2V5fbIX5SzPBYQ5ln0uiq9a3P9M
            @Override // java.lang.Runnable
            public final void run() {
                AssembleDataCache.this.lambda$insert$2$AssembleDataCache(parseAssembleData, data);
            }
        });
    }

    public /* synthetic */ void lambda$insert$2$AssembleDataCache(AssembleLocalInfo assembleLocalInfo, AssembleDataContainer assembleDataContainer) {
        this.mAssembleDao.insert(assembleLocalInfo);
        insertMemCache(assembleDataContainer);
    }

    public void removeAsync(final String downloadUrl) {
        getIOHandler().post(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleDataCache$gKZovJIveCSoeTpQ9nmgdc4MxTE
            @Override // java.lang.Runnable
            public final void run() {
                AssembleDataCache.this.lambda$removeAsync$3$AssembleDataCache(downloadUrl);
            }
        });
    }

    /* renamed from: remove */
    public void lambda$removeAsync$3$AssembleDataCache(final String downloadUrl) {
        DatabaseUtils.tryExecute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleDataCache$3djbG-fBLHPMB5cHiOTfPmq4Dp0
            @Override // java.lang.Runnable
            public final void run() {
                AssembleDataCache.this.lambda$remove$4$AssembleDataCache(downloadUrl);
            }
        });
    }

    public /* synthetic */ void lambda$remove$4$AssembleDataCache(String str) {
        this.mAssembleDao.remove(str);
        removeMemCache(str);
    }

    public void remove(final List<String> urlList) {
        if (urlList == null || urlList.isEmpty()) {
            return;
        }
        int size = urlList.size();
        final String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            String str = urlList.get(i);
            if (!TextUtils.isEmpty(str)) {
                strArr[i] = str;
            }
        }
        DatabaseUtils.tryExecute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleDataCache$PWFZU9m1E9UvjTWDtOjLqpWPMVc
            @Override // java.lang.Runnable
            public final void run() {
                AssembleDataCache.this.lambda$remove$5$AssembleDataCache(strArr, urlList);
            }
        });
    }

    public /* synthetic */ void lambda$remove$5$AssembleDataCache(String[] strArr, List list) {
        this.mAssembleDao.remove(strArr);
        removeCacheList(list);
    }

    public AssembleDataContainer find(String downloadUrl) {
        if (TextUtils.isEmpty(downloadUrl)) {
            return null;
        }
        AssembleDataContainer findInMem = findInMem(downloadUrl);
        return findInMem == null ? Parser.parseAssembleLocal(this.mAssembleDao.queryAssembleData(downloadUrl)) : findInMem;
    }

    public AssembleDataContainer findInMem(String downloadUrl) {
        AssembleDataContainer assembleDataContainer = null;
        if (TextUtils.isEmpty(downloadUrl)) {
            return null;
        }
        synchronized (this.mDataLock) {
            if (this.mUrlAssembleMap != null) {
                String generateKey = generateKey(downloadUrl);
                if (!TextUtils.isEmpty(generateKey)) {
                    assembleDataContainer = this.mUrlAssembleMap.get(generateKey);
                }
            }
        }
        return assembleDataContainer;
    }

    public AssembleDataContainer findInMemWithPn(String packageName) {
        AssembleDataContainer assembleDataContainer = null;
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        if (this.mUrlAssembleMap != null) {
            ArrayList arrayList = new ArrayList();
            for (AssembleDataContainer assembleDataContainer2 : getUrlAssembleMap()) {
                if (assembleDataContainer2.getPackageName().equals(packageName)) {
                    arrayList.add(assembleDataContainer2);
                }
            }
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                AssembleDataContainer assembleDataContainer3 = (AssembleDataContainer) arrayList.get(i);
                if (assembleDataContainer3 != null) {
                    if (i == size - 1) {
                        Logger.t(TAG).d("findInMemWithPn, return data:" + assembleDataContainer3);
                        assembleDataContainer = assembleDataContainer3;
                    } else {
                        removeAsync(assembleDataContainer3.getDownloadUrl());
                        Logger.t(TAG).w("findInMemWithPn, redundant data found, remove this:" + assembleDataContainer3, new Object[0]);
                    }
                }
            }
        }
        return assembleDataContainer;
    }

    public AssembleDataContainer findInMemOrCreate(String downloadUrl, String configUrl, String packageName, String iconUrl, String appLabel) {
        AssembleDataContainer findInMemWithPn = findInMemWithPn(packageName);
        if (findInMemWithPn == null) {
            AssembleDataContainer assembleDataContainer = new AssembleDataContainer();
            assembleDataContainer.setDownloadUrl(downloadUrl);
            assembleDataContainer.setConfigUrl(configUrl);
            assembleDataContainer.setPackageName(packageName);
            assembleDataContainer.setIconUrl(iconUrl);
            assembleDataContainer.setAppLabel(appLabel);
            Logger.t(TAG).i("findInMemOrCreate, no cache, create " + assembleDataContainer, new Object[0]);
            insertAsync(assembleDataContainer);
            return assembleDataContainer;
        }
        Logger.t(TAG).i("findInMemOrCreate, use cache: " + findInMemWithPn, new Object[0]);
        return findInMemWithPn;
    }

    public void updateAsync(final AssembleDataContainer data) {
        getIOHandler().post(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleDataCache$HrCBE1PGKGrYMz9uUgBVVkSeaoI
            @Override // java.lang.Runnable
            public final void run() {
                AssembleDataCache.this.lambda$updateAsync$6$AssembleDataCache(data);
            }
        });
    }

    /* renamed from: update */
    public void lambda$updateAsync$6$AssembleDataCache(final AssembleDataContainer data) {
        Logger.t(TAG).v("update:" + data, new Object[0]);
        DatabaseUtils.tryExecute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.-$$Lambda$AssembleDataCache$1NQxpBOQdqNDduAZdbQVJixDmNs
            @Override // java.lang.Runnable
            public final void run() {
                AssembleDataCache.this.lambda$update$7$AssembleDataCache(data);
            }
        });
    }

    public /* synthetic */ void lambda$update$7$AssembleDataCache(AssembleDataContainer assembleDataContainer) {
        this.mAssembleDao.update(Parser.parseAssembleData(assembleDataContainer));
        insertMemCache(assembleDataContainer);
    }

    private Map<String, AssembleDataContainer> generateUrlDataMap() {
        return new LinkedHashMap();
    }

    private void insertMemCache(AssembleDataContainer data) {
        synchronized (this.mDataLock) {
            Logger.t(TAG).i("insertMemCache:" + data, new Object[0]);
            String generateKey = generateKey(data.getDownloadUrl());
            if (generateKey != null && !TextUtils.isEmpty(generateKey)) {
                this.mUrlAssembleMap.put(generateKey, data);
            }
        }
    }

    private void removeCacheList(List<String> urlList) {
        for (int i = 0; i < urlList.size(); i++) {
            String str = urlList.get(i);
            if (!TextUtils.isEmpty(str)) {
                removeMemCache(str);
            }
        }
    }

    private void removeMemCache(String downloadUrl) {
        synchronized (this.mDataLock) {
            if (this.mUrlAssembleMap != null && !TextUtils.isEmpty(downloadUrl)) {
                String generateKey = generateKey(downloadUrl);
                if (!TextUtils.isEmpty(generateKey)) {
                    Logger.t(TAG).i("removeCache:" + generateKey + ", removed:" + this.mUrlAssembleMap.remove(generateKey), new Object[0]);
                }
            }
        }
    }

    private static void parseAssembleLocalList(List<AssembleLocalInfo> localList, Map<String, AssembleDataContainer> outMap1) {
        if (localList == null) {
            return;
        }
        for (AssembleLocalInfo assembleLocalInfo : localList) {
            AssembleDataContainer parseAssembleLocal = Parser.parseAssembleLocal(assembleLocalInfo);
            if (outMap1 != null) {
                String generateKey = generateKey(assembleLocalInfo.getDownloadUrl());
                if (!TextUtils.isEmpty(generateKey)) {
                    outMap1.put(generateKey, parseAssembleLocal);
                }
            }
        }
    }
}
