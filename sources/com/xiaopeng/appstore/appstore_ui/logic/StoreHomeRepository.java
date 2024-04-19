package com.xiaopeng.appstore.appstore_ui.logic;

import androidx.lifecycle.LiveData;
import com.xiaopeng.appstore.appstore_biz.datamodel.api.XpApiClient;
import com.xiaopeng.appstore.appstore_biz.datamodel.db.AppStoreDb;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PackageWithItems;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PageBean;
import com.xiaopeng.appstore.appstore_biz.parser.AdDataParser;
import com.xiaopeng.appstore.bizcommon.entities.common.ApiResponse;
import com.xiaopeng.appstore.bizcommon.entities.common.XpApiResponse;
import com.xiaopeng.appstore.bizcommon.utils.DatabaseUtils;
import com.xiaopeng.appstore.common_ui.common.NetworkBoundResource;
import com.xiaopeng.appstore.common_ui.common.Resource;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class StoreHomeRepository {
    private static final String TAG = "StoreRepo";

    /* renamed from: com.xiaopeng.appstore.appstore_ui.logic.StoreHomeRepository$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    class AnonymousClass1 extends NetworkBoundResource<List<PackageWithItems>, XpApiResponse<PageBean>> {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaopeng.appstore.common_ui.common.NetworkBoundResource
        public boolean shouldFetch(List<PackageWithItems> cache) {
            return true;
        }

        AnonymousClass1(AppExecutors appExecutors) {
            super(appExecutors);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaopeng.appstore.common_ui.common.NetworkBoundResource
        public void saveCallResult(XpApiResponse<PageBean> item) {
            if (item.isSuccessful()) {
                final ArrayList arrayList = new ArrayList();
                final ArrayList arrayList2 = new ArrayList();
                final ArrayList arrayList3 = new ArrayList();
                final ArrayList arrayList4 = new ArrayList();
                AdDataParser.parsePageBeanToDb(item.data, arrayList, arrayList2, arrayList3, arrayList4);
                DatabaseUtils.tryExecute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.logic.-$$Lambda$StoreHomeRepository$1$BPnWbq1JXhqks0-wkJxtkj3QxY0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppStoreDb.getInstance(Utils.getApp()).getHomeDao().clearThenInsert(arrayList, arrayList2, arrayList3, arrayList4);
                    }
                });
            }
        }

        @Override // com.xiaopeng.appstore.common_ui.common.NetworkBoundResource
        protected LiveData<List<PackageWithItems>> loadFromCache() {
            return AppStoreDb.getInstance(Utils.getApp()).getHomeDao().getPackages();
        }

        @Override // com.xiaopeng.appstore.common_ui.common.NetworkBoundResource
        protected LiveData<ApiResponse<XpApiResponse<PageBean>>> createCall() {
            return XpApiClient.getAdvService().getPage();
        }
    }

    public LiveData<Resource<List<PackageWithItems>>> getHomeDataWithCache() {
        return new AnonymousClass1(AppExecutors.get()).asLiveData();
    }
}
