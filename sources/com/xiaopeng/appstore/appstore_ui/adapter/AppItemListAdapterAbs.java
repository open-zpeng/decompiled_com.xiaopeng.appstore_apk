package com.xiaopeng.appstore.appstore_ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.model2.AppBaseModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.AppItemListAdapterAbs;
import com.xiaopeng.appstore.appstore_ui.adapter.binding.BindableAdapter;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AppItemViewHolder;
import com.xiaopeng.appstore.appstore_ui.common.NavUtils;
import com.xiaopeng.appstore.bizcommon.logic.AgreementManager;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.common_ui.AppExecuteHelper;
import com.xiaopeng.appstore.common_ui.base.PanelDialog;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundListAdapter;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
import com.xiaopeng.appstore.common_ui.logic.LogicUIUtils;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.NetworkUtils;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XToast;
import java.util.List;
/* loaded from: classes2.dex */
public abstract class AppItemListAdapterAbs extends BaseBoundListAdapter<AppBaseModel> implements BindableAdapter<AppBaseModel> {
    private static final String TAG = "UpdateAdapterAbs";
    private PanelDialog mCancelConfirmDialog;
    private final AppItemViewHolder.OnItemEventCallback mOnItemEventCallback;

    protected abstract int getBindingItemLayoutId();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.appstore_ui.adapter.AppItemListAdapterAbs$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass1 implements AppItemViewHolder.OnItemEventCallback {
        AnonymousClass1() {
        }

        @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AppItemViewHolder.OnItemEventCallback
        public void onProgressBtnClick(final View btn, int position, final AppBaseModel model) {
            if (NetworkUtils.isConnected()) {
                final int state = AppStoreAssembleManager.get().getState(model.getPackageName(), model.getDownloadUrl(), model.getConfigUrl());
                AgreementManager.get().tryExecute(btn.getContext(), state, new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.-$$Lambda$AppItemListAdapterAbs$1$KoC3obm-ycMgajeTMJfXS1u5Kag
                    @Override // java.lang.Runnable
                    public final void run() {
                        LogicUIUtils.tryExecuteSuspendApp(r0.getPackageName(), r1, btn.getContext(), r0.isSuspended(), new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.-$$Lambda$AppItemListAdapterAbs$1$Z4JkeJ3gOQjjwH3NqTsscqdyM4M
                            @Override // java.lang.Runnable
                            public final void run() {
                                AppExecuteHelper.execute(r1, r1.getPackageName(), r1.getDownloadUrl(), r1.getMd5(), r1.getConfigUrl(), r1.getConfigMd5(), r1.getAppName(), r2.getIconUrl());
                            }
                        });
                    }
                }, true);
            } else {
                XToast.show(ResUtils.getString(R.string.net_not_connect), 0, XuiMgrHelper.get().getShareId());
            }
            Logger.t(AppItemListAdapterAbs.TAG).d("onProgressBtnClick pos=" + position + " model=" + model.getPackageName());
        }

        @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AppItemViewHolder.OnItemEventCallback
        public void onBtnClick(final View btn, int position, final AppBaseModel model) {
            if (NetworkUtils.isConnected()) {
                final int state = AppStoreAssembleManager.get().getState(model.getPackageName(), model.getDownloadUrl(), model.getConfigUrl());
                AgreementManager.get().tryExecute(btn.getContext(), state, new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.-$$Lambda$AppItemListAdapterAbs$1$_dHVowteV_rHC0pKgcGDAxXPQLw
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppItemListAdapterAbs.AnonymousClass1.this.lambda$onBtnClick$5$AppItemListAdapterAbs$1(model, state, btn);
                    }
                }, true);
            } else {
                XToast.show(ResUtils.getString(R.string.net_not_connect), 0, XuiMgrHelper.get().getShareId());
            }
            Logger.t(AppItemListAdapterAbs.TAG).d("OnItemClick pos=" + position + " model=" + model.getPackageName());
        }

        public /* synthetic */ void lambda$onBtnClick$5$AppItemListAdapterAbs$1(final AppBaseModel appBaseModel, final int i, final View view) {
            LogicUIUtils.tryExecuteSuspendApp(appBaseModel.getPackageName(), i, view.getContext(), appBaseModel.isSuspended(), new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.-$$Lambda$AppItemListAdapterAbs$1$ZhEUGvGuHQUGcaTjX-vZR3aRaiY
                @Override // java.lang.Runnable
                public final void run() {
                    AppItemListAdapterAbs.AnonymousClass1.this.lambda$onBtnClick$4$AppItemListAdapterAbs$1(i, view, appBaseModel);
                }
            });
        }

        public /* synthetic */ void lambda$onBtnClick$3$AppItemListAdapterAbs$1(View view, final AppBaseModel appBaseModel) {
            AppItemListAdapterAbs.this.tryInitCancelDialog(view);
            if (AppItemListAdapterAbs.this.mCancelConfirmDialog != null) {
                if (!AppItemListAdapterAbs.this.mCancelConfirmDialog.isShowing()) {
                    AppItemListAdapterAbs.this.mCancelConfirmDialog.setPositiveButton(R.string.dialog_positive_btn, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.-$$Lambda$AppItemListAdapterAbs$1$DW3lGEFVaqk6RxuV7iQrK3iX9_w
                        @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                        public final void onClick(XDialog xDialog, int i) {
                            AppStoreAssembleManager.get().cancelWithPackage(AppBaseModel.this.getPackageName());
                        }
                    });
                    AppItemListAdapterAbs.this.mCancelConfirmDialog.show();
                    return;
                }
                Logger.t(AppItemListAdapterAbs.TAG).d("Dialog is showing");
                return;
            }
            Logger.t(AppItemListAdapterAbs.TAG).d("mCancelConfirmDialog is null");
        }

        public /* synthetic */ void lambda$onBtnClick$4$AppItemListAdapterAbs$1(int i, final View view, final AppBaseModel appBaseModel) {
            if (i == 1 || i == 5 || i == 2 || i == 10001) {
                AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.-$$Lambda$AppItemListAdapterAbs$1$DvuMbmGEKnKLpkS3_a9RNTP_qQQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppItemListAdapterAbs.AnonymousClass1.this.lambda$onBtnClick$3$AppItemListAdapterAbs$1(view, appBaseModel);
                    }
                });
            } else {
                AppExecuteHelper.startOrCancel(i, appBaseModel.getPackageName(), appBaseModel.getDownloadUrl(), appBaseModel.getMd5(), appBaseModel.getConfigUrl(), appBaseModel.getConfigMd5(), appBaseModel.getAppName(), appBaseModel.getIconUrl());
            }
        }

        @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.AppItemViewHolder.OnItemEventCallback
        public void onItemClick(View btn, int position, AppBaseModel model) {
            NavController findNavController = Navigation.findNavController(btn);
            if (findNavController.getCurrentDestination() == null || findNavController.getCurrentDestination().getId() == R.id.pendingUpdateFragment) {
                NavUtils.goToDetail(findNavController, model.getPackageName());
            }
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

    public AppItemListAdapterAbs() {
        super(new DiffUtil.ItemCallback<AppBaseModel>() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.AppItemListAdapterAbs.2
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
        return new AppItemViewHolder(parent, getBindingItemLayoutId(), this.mOnItemEventCallback);
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.binding.BindableAdapter
    public void setList(List<AppBaseModel> list) {
        submitList(list);
    }
}
