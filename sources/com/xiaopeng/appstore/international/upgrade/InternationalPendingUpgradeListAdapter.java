package com.xiaopeng.appstore.international.upgrade;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.model2.AppBaseModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.binding.BindableAdapter;
import com.xiaopeng.appstore.bizcommon.logic.AgreementManager;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.common_ui.AppExecuteHelper;
import com.xiaopeng.appstore.common_ui.base.PanelDialog;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundListAdapter;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
import com.xiaopeng.appstore.common_ui.logic.LogicUIUtils;
import com.xiaopeng.appstore.international.upgrade.InternationalAppItemViewHolder;
import com.xiaopeng.appstore.international.upgrade.InternationalPendingUpgradeListAdapter;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.NetworkUtils;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XToast;
import java.util.List;
/* loaded from: classes.dex */
public class InternationalPendingUpgradeListAdapter extends BaseBoundListAdapter<AppBaseModel> implements BindableAdapter<AppBaseModel> {
    private static final String TAG = "UpdateAdapterAbs";
    private PanelDialog mCancelConfirmDialog;
    private final InternationalAppItemViewHolder.OnItemEventCallback mOnItemEventCallback;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.international.upgrade.InternationalPendingUpgradeListAdapter$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements InternationalAppItemViewHolder.OnItemEventCallback {
        AnonymousClass1() {
        }

        @Override // com.xiaopeng.appstore.international.upgrade.InternationalAppItemViewHolder.OnItemEventCallback
        public void onProgressBtnClick(InternationalAppItemViewHolder viewHolder, final View btn, int position, final AppBaseModel model) {
            if (NetworkUtils.isConnected()) {
                final int state = AppStoreAssembleManager.get().getState(model.getPackageName(), model.getDownloadUrl(), model.getConfigUrl());
                AgreementManager.get().tryExecute(btn.getContext(), state, new Runnable() { // from class: com.xiaopeng.appstore.international.upgrade.-$$Lambda$InternationalPendingUpgradeListAdapter$1$DPARiHOWEycLhyM9-yyM0x1enzU
                    @Override // java.lang.Runnable
                    public final void run() {
                        LogicUIUtils.tryExecuteSuspendApp(r0.getPackageName(), r1, btn.getContext(), r0.isSuspended(), new Runnable() { // from class: com.xiaopeng.appstore.international.upgrade.-$$Lambda$InternationalPendingUpgradeListAdapter$1$8aBDP5heceu_QXI4E9PAuRnOB-k
                            @Override // java.lang.Runnable
                            public final void run() {
                                AppExecuteHelper.execute(r1, r1.getPackageName(), r1.getDownloadUrl(), r1.getMd5(), r1.getConfigUrl(), r1.getConfigMd5(), r1.getAppName(), r2.getIconUrl());
                            }
                        });
                    }
                }, true);
            } else {
                XToast.show(R.string.net_not_connect);
            }
            Logger.t(InternationalPendingUpgradeListAdapter.TAG).d("onProgressBtnClick pos=" + position + " model=" + model.getPackageName());
        }

        @Override // com.xiaopeng.appstore.international.upgrade.InternationalAppItemViewHolder.OnItemEventCallback
        public void onBtnClick(InternationalAppItemViewHolder viewHolder, final View btn, int position, final AppBaseModel model) {
            if (NetworkUtils.isConnected()) {
                final int state = AppStoreAssembleManager.get().getState(model.getPackageName(), model.getDownloadUrl(), model.getConfigUrl());
                AgreementManager.get().tryExecute(btn.getContext(), state, new Runnable() { // from class: com.xiaopeng.appstore.international.upgrade.-$$Lambda$InternationalPendingUpgradeListAdapter$1$Q3nGy7KVet7kzbEWKBoBY-g-sas
                    @Override // java.lang.Runnable
                    public final void run() {
                        InternationalPendingUpgradeListAdapter.AnonymousClass1.this.lambda$onBtnClick$5$InternationalPendingUpgradeListAdapter$1(model, state, btn);
                    }
                }, true);
            } else {
                XToast.show(R.string.net_not_connect);
            }
            Logger.t(InternationalPendingUpgradeListAdapter.TAG).d("OnItemClick pos=" + position + " model=" + model.getPackageName());
        }

        public /* synthetic */ void lambda$onBtnClick$5$InternationalPendingUpgradeListAdapter$1(final AppBaseModel appBaseModel, final int i, final View view) {
            LogicUIUtils.tryExecuteSuspendApp(appBaseModel.getPackageName(), i, view.getContext(), appBaseModel.isSuspended(), new Runnable() { // from class: com.xiaopeng.appstore.international.upgrade.-$$Lambda$InternationalPendingUpgradeListAdapter$1$h070MJhQjI9bxNlhHRZTVDhpnR4
                @Override // java.lang.Runnable
                public final void run() {
                    InternationalPendingUpgradeListAdapter.AnonymousClass1.this.lambda$onBtnClick$4$InternationalPendingUpgradeListAdapter$1(i, view, appBaseModel);
                }
            });
        }

        public /* synthetic */ void lambda$onBtnClick$3$InternationalPendingUpgradeListAdapter$1(View view, final AppBaseModel appBaseModel) {
            InternationalPendingUpgradeListAdapter.this.tryInitCancelDialog(view);
            if (InternationalPendingUpgradeListAdapter.this.mCancelConfirmDialog != null) {
                if (!InternationalPendingUpgradeListAdapter.this.mCancelConfirmDialog.isShowing()) {
                    InternationalPendingUpgradeListAdapter.this.mCancelConfirmDialog.setPositiveButton(R.string.dialog_positive_btn, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.appstore.international.upgrade.-$$Lambda$InternationalPendingUpgradeListAdapter$1$K-dW8yAJUfuIgq4-hf5OKF1giPU
                        @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                        public final void onClick(XDialog xDialog, int i) {
                            AppStoreAssembleManager.get().cancelWithPackage(AppBaseModel.this.getPackageName());
                        }
                    });
                    InternationalPendingUpgradeListAdapter.this.mCancelConfirmDialog.show();
                    return;
                }
                Logger.t(InternationalPendingUpgradeListAdapter.TAG).d("Dialog is showing");
                return;
            }
            Logger.t(InternationalPendingUpgradeListAdapter.TAG).d("mCancelConfirmDialog is null");
        }

        public /* synthetic */ void lambda$onBtnClick$4$InternationalPendingUpgradeListAdapter$1(int i, final View view, final AppBaseModel appBaseModel) {
            if (i == 1 || i == 5 || i == 2 || i == 10001) {
                AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.international.upgrade.-$$Lambda$InternationalPendingUpgradeListAdapter$1$hI80S7DXNcWnKXcl5noRS0W8x9c
                    @Override // java.lang.Runnable
                    public final void run() {
                        InternationalPendingUpgradeListAdapter.AnonymousClass1.this.lambda$onBtnClick$3$InternationalPendingUpgradeListAdapter$1(view, appBaseModel);
                    }
                });
            } else {
                AppExecuteHelper.startOrCancel(i, appBaseModel.getPackageName(), appBaseModel.getDownloadUrl(), appBaseModel.getMd5(), appBaseModel.getConfigUrl(), appBaseModel.getConfigMd5(), appBaseModel.getAppName(), appBaseModel.getIconUrl());
            }
        }

        @Override // com.xiaopeng.appstore.international.upgrade.InternationalAppItemViewHolder.OnItemEventCallback
        public void onItemClick(InternationalAppItemViewHolder viewHolder, View btn, int position, AppBaseModel model) {
            NavController findNavController = Navigation.findNavController(btn);
            if (findNavController.getCurrentDestination() == null || findNavController.getCurrentDestination().getId() == R.id.pendingUpdateFragment) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("detail_page_app_package_name", model.getPackageName());
                findNavController.navigate(com.xiaopeng.appstore.international.R.id.action_pendingUpdateFragment_to_itemDetailFragment, bundle);
            }
        }

        @Override // com.xiaopeng.appstore.international.upgrade.InternationalAppItemViewHolder.OnItemEventCallback
        public void onExpandClick(InternationalAppItemViewHolder viewHolder, View view, int position, AppBaseModel model) {
            viewHolder.toggleExpand();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryInitCancelDialog(View v) {
        if (this.mCancelConfirmDialog == null) {
            PanelDialog panelDialog = new PanelDialog(v.getContext());
            this.mCancelConfirmDialog = panelDialog;
            panelDialog.setTitle(R.string.detail_cancel_confirm_title);
            this.mCancelConfirmDialog.setMessage(R.string.detail_cancel_confirm_desc);
            this.mCancelConfirmDialog.setNegativeButton(ResUtils.getString(R.string.dialog_negative_btn)).setPositiveButton(ResUtils.getString(R.string.dialog_positive_btn));
        }
    }

    public InternationalPendingUpgradeListAdapter() {
        super(new DiffUtil.ItemCallback<AppBaseModel>() { // from class: com.xiaopeng.appstore.international.upgrade.InternationalPendingUpgradeListAdapter.2
            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public boolean areItemsTheSame(AppBaseModel oldItem, AppBaseModel newItem) {
                return oldItem.getPackageName().equals(newItem.getPackageName());
            }

            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public boolean areContentsTheSame(AppBaseModel oldItem, AppBaseModel newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.mOnItemEventCallback = new AnonymousClass1();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseBoundViewHolder<AppBaseModel, ?> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InternationalAppItemViewHolder(parent, getBindingItemLayoutId(), this.mOnItemEventCallback);
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.binding.BindableAdapter
    public void setList(List<AppBaseModel> list) {
        submitList(list);
    }

    protected int getBindingItemLayoutId() {
        return com.xiaopeng.appstore.international.R.layout.international_pending_upgrade_item;
    }
}
