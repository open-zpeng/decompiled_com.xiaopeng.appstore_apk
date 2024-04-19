package com.xiaopeng.appstore.appstore_ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvAppModel;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvContainerModel;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemGridVH;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemNarrowGridVH;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemWideGridVH;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.FooterViewHolder;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.ItemPackageGrid2ColumnsVH;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.ItemPackageGrid3ColumnsVH;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.ItemPackageHorizontalVH;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.ItemPackageTitleVH;
import com.xiaopeng.appstore.appstore_ui.common.NavUtils;
import com.xiaopeng.appstore.bizcommon.logic.AgreementManager;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.common_ui.AppExecuteHelper;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
import com.xiaopeng.appstore.common_ui.logic.LogicUIUtils;
import com.xiaopeng.appstore.libcommon.utils.NetworkUtils;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
import com.xiaopeng.xui.app.XToast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/* loaded from: classes2.dex */
public class BaseAdvListAdapter extends ListAdapter<AdvContainerModel<?>, BaseBoundViewHolder<? extends AdvModel, ?>> {
    private static final String TAG = "BaseAdvListAdapter";
    private final Map<String, RecyclerView.Adapter<?>> mAdapterCache;
    private BannerViewHolder mBannerViewHolder;
    private final AdvItemViewHolder.OnItemEventCallback mOnItemEventCallback;
    private final RecyclerView.RecycledViewPool mViewPool;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        onBindViewHolder((BaseBoundViewHolder) holder, position, (List<Object>) payloads);
    }

    /* renamed from: com.xiaopeng.appstore.appstore_ui.adapter.BaseAdvListAdapter$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements AdvItemViewHolder.OnItemEventCallback {
        AnonymousClass1() {
        }

        @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder.OnItemEventCallback
        public void onBtnClick(final View btn, int position, final AdvAppModel model) {
            if (NetworkUtils.isConnected()) {
                final int state = AppStoreAssembleManager.get().getState(model.getPackageName(), model.getDownloadUrl(), model.getConfigUrl());
                AgreementManager.get().tryExecute(btn.getContext(), state, new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.-$$Lambda$BaseAdvListAdapter$1$wE2YP1F8Va_fSRSs3WWaD2a0VJo
                    @Override // java.lang.Runnable
                    public final void run() {
                        LogicUIUtils.tryExecuteSuspendApp(r0.getPackageName(), r1, btn.getContext(), r0.getAppBaseInfo().isSuspended(), new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.-$$Lambda$BaseAdvListAdapter$1$x84RdyqkSMB2qb4YuxoxVBTb7mE
                            @Override // java.lang.Runnable
                            public final void run() {
                                AppExecuteHelper.execute(r1, r1.getPackageName(), r1.getDownloadUrl(), r1.getMd5(), r1.getConfigUrl(), r1.getConfigMd5(), r1.getAppBaseInfo().getAppName(), r2.getIconUrl());
                            }
                        });
                    }
                }, true);
                EventTrackingHelper.sendMolecast(PagesEnum.STORE_STORE_MAIN, EventEnum.OPEN_APP_FROM_STORE_MAIN_ENTRANCE, Integer.valueOf(LogicUtils.convertAppOperationStatePause(state)), model.getAppBaseInfo().getPackageName(), model.getTitle());
            } else {
                XToast.show(ResUtils.getString(R.string.net_not_connect), 0, XuiMgrHelper.get().getShareId());
            }
            Logger.t(BaseAdvListAdapter.TAG).d("OnItemClick pos=" + position + " model=" + model.getPackageName());
        }

        @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder.OnItemEventCallback
        public void onItemClick(View btn, int position, AdvAppModel model) {
            NavController findNavController = Navigation.findNavController(btn);
            if (findNavController.getCurrentDestination() == null || findNavController.getCurrentDestination().getId() == R.id.storeHomeFragment) {
                NavUtils.goToDetail(findNavController, model.getPackageName());
                EventTrackingHelper.sendMolecast(PagesEnum.STORE_STORE_MAIN, EventEnum.STORE_APP_DETAIL_ENTRANCE, model.getPackageName(), model.getTitle());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public BaseAdvListAdapter() {
        super(new DiffUtil.ItemCallback<AdvContainerModel<?>>() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.BaseAdvListAdapter.2
            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public /* bridge */ /* synthetic */ boolean areContentsTheSame(AdvContainerModel<?> oldItem, AdvContainerModel<?> newItem) {
                return areContentsTheSame2((AdvContainerModel) oldItem, (AdvContainerModel) newItem);
            }

            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public /* bridge */ /* synthetic */ boolean areItemsTheSame(AdvContainerModel<?> oldItem, AdvContainerModel<?> newItem) {
                return areItemsTheSame2((AdvContainerModel) oldItem, (AdvContainerModel) newItem);
            }

            /* renamed from: areItemsTheSame  reason: avoid collision after fix types in other method */
            public boolean areItemsTheSame2(AdvContainerModel oldItem, AdvContainerModel newItem) {
                return oldItem.getType() == newItem.getType();
            }

            /* renamed from: areContentsTheSame  reason: avoid collision after fix types in other method */
            public boolean areContentsTheSame2(AdvContainerModel oldItem, AdvContainerModel newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.mOnItemEventCallback = new AnonymousClass1();
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        this.mViewPool = recycledViewPool;
        recycledViewPool.setMaxRecycledViews(6, 20);
        recycledViewPool.setMaxRecycledViews(8, 20);
        recycledViewPool.setMaxRecycledViews(11, 5);
        recycledViewPool.setMaxRecycledViews(10, 5);
        this.mAdapterCache = new HashMap();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseBoundViewHolder<? extends AdvModel, ?> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 2) {
            if (this.mBannerViewHolder == null) {
                this.mBannerViewHolder = new BannerViewHolder(parent);
            }
            return this.mBannerViewHolder;
        }
        switch (viewType) {
            case 5:
            case 10:
            case 11:
                return new ItemPackageHorizontalVH(parent, this.mAdapterCache, this.mOnItemEventCallback, this.mViewPool);
            case 6:
                return new ItemPackageGrid3ColumnsVH(parent, this.mAdapterCache, this.mOnItemEventCallback, this.mViewPool);
            case 7:
                return new AdvItemGridVH(parent, this.mOnItemEventCallback);
            case 8:
            case 12:
                return new ItemPackageGrid2ColumnsVH(parent, this.mAdapterCache, this.mOnItemEventCallback, this.mViewPool);
            case 9:
                return new AdvItemWideGridVH(parent, this.mOnItemEventCallback);
            case 13:
                return new AdvItemNarrowGridVH(parent, this.mOnItemEventCallback);
            case 14:
                return new ItemPackageTitleVH(parent);
            case 15:
                return new FooterViewHolder(parent);
            default:
                return new ItemPackageHorizontalVH(parent, this.mAdapterCache, this.mOnItemEventCallback, this.mViewPool);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseBoundViewHolder<? extends AdvModel, ?> holder, int position) {
        onBindViewHolderInternal(holder, position);
    }

    public void onBindViewHolder(BaseBoundViewHolder<? extends AdvModel, ?> holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            onBindViewHolderInternal(holder, position, payloads);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> void onBindViewHolderInternal(BaseBoundViewHolder<T, ?> holder, int position) {
        holder.setData(getItem(position).getData());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> void onBindViewHolderInternal(BaseBoundViewHolder<T, ?> holder, int position, List<Object> payloads) {
        holder.setData(getItem(position).getData(), payloads);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        if (getCurrentList().isEmpty() || getItemCount() <= position) {
            return 0;
        }
        return getItem(position).getType();
    }

    @Override // androidx.recyclerview.widget.ListAdapter
    public void submitList(List<AdvContainerModel<?>> list) {
        super.submitList(list != null ? new ArrayList(list) : null);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(BaseBoundViewHolder<? extends AdvModel, ?> holder) {
        super.onViewAttachedToWindow((BaseAdvListAdapter) holder);
        holder.markAttached();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(BaseBoundViewHolder<? extends AdvModel, ?> holder) {
        super.onViewDetachedFromWindow((BaseAdvListAdapter) holder);
        holder.markDetached();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(BaseBoundViewHolder<? extends AdvModel, ?> holder) {
        super.onViewRecycled((BaseAdvListAdapter) holder);
        holder.onViewRecycled();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public void startBannerTimer() {
        BannerViewHolder bannerViewHolder = this.mBannerViewHolder;
        if (bannerViewHolder == null || !bannerViewHolder.itemView.isAttachedToWindow()) {
            return;
        }
        this.mBannerViewHolder.startBannerTimer();
    }

    public void stopBannerTimer() {
        BannerViewHolder bannerViewHolder = this.mBannerViewHolder;
        if (bannerViewHolder == null || !bannerViewHolder.itemView.isAttachedToWindow()) {
            return;
        }
        this.mBannerViewHolder.stopBannerTimer();
    }
}
