package com.xiaopeng.appstore.applet_biz.datamodel;

import android.database.sqlite.SQLiteFullException;
import android.provider.Settings;
import android.text.TextUtils;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.applet_biz.datamodel.MiniProgRepository;
import com.xiaopeng.appstore.applet_biz.datamodel.db.MiniProgramDb;
import com.xiaopeng.appstore.applet_biz.datamodel.entities.MiniDataConvert;
import com.xiaopeng.appstore.applet_biz.datamodel.entities.MiniProgramGroup;
import com.xiaopeng.appstore.applet_biz.datamodel.entities.MiniProgramsContainer;
import com.xiaopeng.appstore.bizcommon.logic.IMiniProgramListener;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.bizcommon.utils.DatabaseUtils;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.FileUtils;
import com.xiaopeng.appstore.libcommon.utils.GsonUtil;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
import com.xiaopeng.xuimanager.xapp.MiniProgramResponse;
import java.util.List;
/* loaded from: classes2.dex */
public class MiniProgRepository implements IMiniProgramListener {
    private static final String DATA_JSON_FILE = "miniprograms.json";
    public static final String MINI_ALIPAY_VERISON_NAME = "MINI_ALIPAY_VERISON_NAME";
    private static final String TAG = "MiniProgRepository";
    private AppletLoadListener mAppletLoadListener;
    private List<MiniProgramGroup> mDataList = null;
    private List<MiniProgramGroup> mMiniProgramDataListFromDB = null;
    private volatile boolean mIsLoadingApplets = false;

    /* loaded from: classes2.dex */
    public interface AppletLoadListener {
        void onAppletLoaded(List<MiniProgramGroup> list);
    }

    public MiniProgRepository() {
        XuiMgrHelper.get().addMiniProgramListener(this);
    }

    public void setLoadListener(AppletLoadListener appletLoadListener) {
        this.mAppletLoadListener = appletLoadListener;
    }

    public void release() {
        XuiMgrHelper.get().removeMiniProgramListener(this);
        this.mAppletLoadListener = null;
    }

    public void launch(String id, String name) {
        XuiMgrHelper.get().startLaunchMiniAsync(id);
        EventTrackingHelper.sendMolecast(PagesEnum.STORE_MINI_PROGRAM_LIST, EventEnum.OPEN_MINI_PROGRAM, id, name, EventTrackingHelper.EVENT_SOURCE_FOR_MINI_PROGRAM);
    }

    private void loadMiniProgramConfigFromLocalIfNecessary() {
        List<MiniProgramGroup> list = this.mMiniProgramDataListFromDB;
        if (list == null || list.isEmpty()) {
            AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.applet_biz.datamodel.-$$Lambda$MiniProgRepository$gnN5laJajOC9o90LiAaCgilaKsg
                @Override // java.lang.Runnable
                public final void run() {
                    MiniProgRepository.this.lambda$loadMiniProgramConfigFromLocalIfNecessary$0$MiniProgRepository();
                }
            });
            return;
        }
        Logger.t(TAG).i("already showing mini program data list from db while error occurred, do not need load from local assert anymore", new Object[0]);
        this.mIsLoadingApplets = false;
    }

    public /* synthetic */ void lambda$loadMiniProgramConfigFromLocalIfNecessary$0$MiniProgRepository() {
        MiniProgramsContainer miniProgramsContainer;
        String loadFileFromAsset = FileUtils.loadFileFromAsset(DATA_JSON_FILE);
        if (!TextUtils.isEmpty(loadFileFromAsset) && (miniProgramsContainer = (MiniProgramsContainer) GsonUtil.fromJson(loadFileFromAsset, (Class<Object>) MiniProgramsContainer.class)) != null) {
            List<MiniProgramGroup> data = miniProgramsContainer.getData();
            this.mDataList = data;
            AppletLoadListener appletLoadListener = this.mAppletLoadListener;
            if (appletLoadListener != null) {
                appletLoadListener.onAppletLoaded(data);
            }
        }
        this.mIsLoadingApplets = false;
    }

    private void loadMiniProgramDataFromDB() {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.applet_biz.datamodel.-$$Lambda$MiniProgRepository$riXO5Xs9bI3NfOD2thLuYLqvpjI
            @Override // java.lang.Runnable
            public final void run() {
                MiniProgRepository.this.lambda$loadMiniProgramDataFromDB$1$MiniProgRepository();
            }
        });
    }

    public /* synthetic */ void lambda$loadMiniProgramDataFromDB$1$MiniProgRepository() {
        List<MiniProgramGroup> list;
        try {
            list = MiniProgramDb.getInstance(Utils.getApp()).getMiniProgramDao().getMiniProgramDataList();
        } catch (SQLiteFullException e) {
            Logger.t(TAG).e("load mini-program data from db failed, ex: " + e, new Object[0]);
            e.printStackTrace();
            list = null;
        }
        if (list != null) {
            Logger.t(TAG).d("mini program data from db is " + list.size());
            this.mMiniProgramDataListFromDB = list;
            AppletLoadListener appletLoadListener = this.mAppletLoadListener;
            if (appletLoadListener != null) {
                appletLoadListener.onAppletLoaded(list);
                return;
            }
            return;
        }
        Logger.t(TAG).i("mini program data from db is null", new Object[0]);
    }

    public void loadData() {
        if (this.mIsLoadingApplets) {
            Logger.t(TAG).w("loadData ignore, since it's loading now.", new Object[0]);
            return;
        }
        this.mIsLoadingApplets = true;
        loadMiniProgramDataFromDB();
        String string = Settings.System.getString(Utils.getApp().getContentResolver(), MINI_ALIPAY_VERISON_NAME);
        if (TextUtils.isEmpty(string)) {
            loadMiniProgramConfigFromLocalIfNecessary();
        } else {
            XuiMgrHelper.get().requestMiniList(string);
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.IMiniProgramListener
    public void onMiniProgramCallBack(int type, MiniProgramResponse response) {
        if (type != 7) {
            return;
        }
        if (response == null) {
            loadMiniProgramConfigFromLocalIfNecessary();
            return;
        }
        List<MiniProgramGroup> managerObjectToBean = MiniDataConvert.managerObjectToBean(response.getMiniProgramGroups());
        if (managerObjectToBean != null && managerObjectToBean.size() > 0) {
            String str = TAG;
            Logger.t(str).d("data from db. data list  = " + this.mMiniProgramDataListFromDB);
            List<MiniProgramGroup> list = this.mMiniProgramDataListFromDB;
            if (list == null || !list.equals(managerObjectToBean)) {
                this.mDataList = managerObjectToBean;
                AppletLoadListener appletLoadListener = this.mAppletLoadListener;
                if (appletLoadListener != null) {
                    appletLoadListener.onAppletLoaded(managerObjectToBean);
                }
                this.mIsLoadingApplets = false;
                Logger.t(str).d("data from server is different with db, post new data and write it into db . data list  = " + this.mDataList);
                AppExecutors.get().backgroundThread().execute(new AnonymousClass1());
                return;
            }
            Logger.t(str).i("same data from server with from db, skip post value or write db", new Object[0]);
            this.mIsLoadingApplets = false;
            return;
        }
        loadMiniProgramConfigFromLocalIfNecessary();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.applet_biz.datamodel.MiniProgRepository$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            DatabaseUtils.tryExecute(new Runnable() { // from class: com.xiaopeng.appstore.applet_biz.datamodel.-$$Lambda$MiniProgRepository$1$P02C2STnNE7O1TZDIyQiYs1TnWk
                @Override // java.lang.Runnable
                public final void run() {
                    MiniProgRepository.AnonymousClass1.this.lambda$run$0$MiniProgRepository$1();
                }
            });
        }

        public /* synthetic */ void lambda$run$0$MiniProgRepository$1() {
            MiniProgramDb.getInstance(Utils.getApp()).getMiniProgramDao().clearThenInsert(MiniProgRepository.this.mDataList);
        }
    }
}
