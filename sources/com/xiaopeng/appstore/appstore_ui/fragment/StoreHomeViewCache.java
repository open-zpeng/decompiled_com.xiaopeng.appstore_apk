package com.xiaopeng.appstore.appstore_ui.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.appstore_ui.AdvItemDecoration;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.StoreHomeAdapter2;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
import com.xiaopeng.appstore.libcommon.utils.Utils;
/* loaded from: classes2.dex */
public class StoreHomeViewCache {
    private static final String TAG = "StoreHomeViewCache";
    private static volatile StoreHomeViewCache sInstance;
    private RecyclerView mHomeRecyclerView;
    private StoreHomeAdapter2 mStoreHomeAdapter;

    private StoreHomeViewCache() {
    }

    public static StoreHomeViewCache get() {
        if (sInstance == null) {
            synchronized (StoreHomeViewCache.class) {
                if (sInstance == null) {
                    sInstance = new StoreHomeViewCache();
                }
            }
        }
        return sInstance;
    }

    public boolean hasCache() {
        return this.mHomeRecyclerView != null;
    }

    public RecyclerView getRecyclerView() {
        return this.mHomeRecyclerView;
    }

    public StoreHomeAdapter2 getStoreHomeAdapter() {
        return this.mStoreHomeAdapter;
    }

    public void tryInitViewCache() {
        Context app = Utils.getApp();
        if (this.mHomeRecyclerView == null) {
            this.mHomeRecyclerView = (RecyclerView) LayoutInflater.from(app).inflate(R.layout.store_home_recyclerview, (ViewGroup) null);
            StoreHomeAdapter2 storeHomeAdapter2 = new StoreHomeAdapter2(app);
            this.mStoreHomeAdapter = storeHomeAdapter2;
            this.mHomeRecyclerView.setLayoutManager(storeHomeAdapter2.getLayoutManager());
            this.mHomeRecyclerView.setAdapter(this.mStoreHomeAdapter);
            this.mHomeRecyclerView.addItemDecoration(new AdvItemDecoration());
            this.mHomeRecyclerView.setItemViewCacheSize(10);
        }
    }

    public void restore() {
        if (this.mStoreHomeAdapter == null) {
            return;
        }
        this.mHomeRecyclerView.scrollToPosition(0);
        PayloadData payloadData = new PayloadData();
        payloadData.type = 1004;
        StoreHomeAdapter2 storeHomeAdapter2 = this.mStoreHomeAdapter;
        storeHomeAdapter2.notifyItemRangeChanged(0, storeHomeAdapter2.getItemCount(), payloadData);
    }

    public void release() {
        if (this.mStoreHomeAdapter == null) {
            return;
        }
        PayloadData payloadData = new PayloadData();
        payloadData.type = 1005;
        StoreHomeAdapter2 storeHomeAdapter2 = this.mStoreHomeAdapter;
        storeHomeAdapter2.notifyItemRangeChanged(0, storeHomeAdapter2.getItemCount(), payloadData);
    }
}
