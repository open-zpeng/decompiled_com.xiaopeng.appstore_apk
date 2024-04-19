package com.xiaopeng.appstore.appstore_ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvAppModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.binding.BindableAdapter;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder;
import com.xiaopeng.appstore.appstore_ui.common.NavUtils;
import com.xiaopeng.appstore.bizcommon.logic.AgreementManager;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.common_ui.AppExecuteHelper;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundListAdapter;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
import com.xiaopeng.appstore.common_ui.logic.LogicUIUtils;
import com.xiaopeng.appstore.libcommon.utils.NetworkUtils;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
import com.xiaopeng.xui.app.XToast;
import java.util.List;
/* loaded from: classes2.dex */
public abstract class AdvItemListAdapterAbs extends BaseBoundListAdapter<AdvAppModel> implements BindableAdapter<AdvAppModel> {
    private static final boolean LOG_ENABLE = false;
    private static final String TAG = "NestedListAdapter";
    private final AdvItemViewHolder.OnItemEventCallback mOnItemEventCallback;

    protected abstract int getBindingLayoutId();

    protected void log(String msg, Object... args) {
    }

    /* renamed from: com.xiaopeng.appstore.appstore_ui.adapter.AdvItemListAdapterAbs$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements AdvItemViewHolder.OnItemEventCallback {
        AnonymousClass1() {
        }

        @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AdvItemViewHolder.OnItemEventCallback
        public void onBtnClick(final View btn, int position, final AdvAppModel model) {
            if (NetworkUtils.isConnected()) {
                final int state = AppStoreAssembleManager.get().getState(model.getPackageName(), model.getDownloadUrl(), model.getConfigUrl());
                AgreementManager.get().tryExecute(btn.getContext(), state, new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.-$$Lambda$AdvItemListAdapterAbs$1$DXohyxryDpIvKGqMNcUp_oYMY9M
                    @Override // java.lang.Runnable
                    public final void run() {
                        LogicUIUtils.tryExecuteSuspendApp(r0.getPackageName(), r1, btn.getContext(), r0.getAppBaseInfo().isSuspended(), new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.-$$Lambda$AdvItemListAdapterAbs$1$ailWH2VF3cNCWiLQT23rh_b5Z6A
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
            Logger.d("OnItemClick pos=" + position + " model=" + model.getPackageName());
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

    public AdvItemListAdapterAbs() {
        super(new DiffUtil.ItemCallback<AdvAppModel>() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.AdvItemListAdapterAbs.2
            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public boolean areItemsTheSame(AdvAppModel oldItem, AdvAppModel newItem) {
                return oldItem.getPackageName().equals(newItem.getPackageName());
            }

            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public boolean areContentsTheSame(AdvAppModel oldItem, AdvAppModel newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.mOnItemEventCallback = new AnonymousClass1();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseBoundViewHolder<AdvAppModel, ?> onCreateViewHolder(ViewGroup parent, int viewType) {
        log("onCreateViewHolder, this=%s.", getClass().getSimpleName());
        return new AdvItemViewHolder(parent, getBindingLayoutId(), this.mOnItemEventCallback);
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.binding.BindableAdapter
    public void setList(List<AdvAppModel> list) {
        submitList(list);
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseBoundViewHolder<AdvAppModel, ?> holder, int position) {
        super.onBindViewHolder((BaseBoundViewHolder) holder, position);
        log("onBindViewHolder, this=%s, pos=%s.", getClass().getSimpleName(), Integer.valueOf(holder.getAdapterPosition()));
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(BaseBoundViewHolder<AdvAppModel, ?> holder) {
        super.onViewRecycled((BaseBoundViewHolder) holder);
        log("onViewRecycled, this=%s, pos=%s.", getClass().getSimpleName(), Integer.valueOf(holder.getAdapterPosition()));
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(BaseBoundViewHolder<AdvAppModel, ?> holder) {
        super.onViewAttachedToWindow((BaseBoundViewHolder) holder);
        log("onViewAttachedToWindow, this=%s, pos=%s.", getClass().getSimpleName(), Integer.valueOf(holder.getAdapterPosition()));
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(BaseBoundViewHolder<AdvAppModel, ?> holder) {
        super.onViewDetachedFromWindow((BaseBoundViewHolder) holder);
        log("onViewDetachedFromWindow, this=%s, pos=%s.", getClass().getSimpleName(), Integer.valueOf(holder.getAdapterPosition()));
    }
}
