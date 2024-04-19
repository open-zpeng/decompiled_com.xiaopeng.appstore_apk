package com.xiaopeng.appstore.appstore_ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.logic.CheckUpdateManager;
import com.xiaopeng.appstore.appstore_biz.model2.AppBaseModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.PendingUpgradeListAdapter;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.GridItemDecoration;
import com.xiaopeng.appstore.appstore_ui.databinding.FragmentPendingUpdateBinding;
import com.xiaopeng.appstore.appstore_ui.viewmodel.PendingUpgradeViewModel;
import com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer;
import com.xiaopeng.appstore.bizcommon.logic.AgreementDialogHelper;
import com.xiaopeng.appstore.bizcommon.logic.AgreementManager;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.IAgreementViewListener;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.common_ui.BaseBizFragment;
import com.xiaopeng.appstore.common_ui.base.PanelDialog;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class PendingUpgradeFragment extends BaseBizFragment implements IAgreementViewListener {
    private static final int DIALOG_CANCEL_TIMEOUT = 10;
    private static final String TAG = "UpgradeFrag";
    private PendingUpgradeListAdapter mAdapter;
    private FragmentPendingUpdateBinding mBinding;
    private PanelDialog mUpgradeConfirmDialog;
    private PendingUpgradeViewModel mViewModel;

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        PanelDialog panelDialog;
        super.onConfigurationChanged(newConfig);
        if (!ThemeManager.isThemeChanged(newConfig) || (panelDialog = this.mUpgradeConfirmDialog) == null || panelDialog.getDialog() == null || this.mUpgradeConfirmDialog.getDialog().getWindow() == null) {
            return;
        }
        this.mUpgradeConfirmDialog.getDialog().getWindow().setBackgroundDrawableResource(R.drawable.x_bg_dialog);
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mViewModel = (PendingUpgradeViewModel) new ViewModelProvider(this).get(PendingUpgradeViewModel.class);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentPendingUpdateBinding fragmentPendingUpdateBinding = (FragmentPendingUpdateBinding) DataBindingUtil.inflate(inflater, R.layout.fragment_pending_update, container, false);
        this.mBinding = fragmentPendingUpdateBinding;
        fragmentPendingUpdateBinding.setLifecycleOwner(this);
        this.mAdapter = new PendingUpgradeListAdapter();
        this.mBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        this.mBinding.recyclerView.setAdapter(this.mAdapter);
        this.mBinding.recyclerView.addItemDecoration(new GridItemDecoration(getContext(), ResUtils.getDimen(R.dimen.upgrade_item_divider_width), ResUtils.getDimen(R.dimen.upgrade_item_divider_height)));
        return this.mBinding.getRoot();
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subscribeViewModel();
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.mRootActivity.setCanNavBack(true);
        AgreementManager.get().addAgreementViewListener(this);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        AgreementManager.get().removeAgreementViewListener(this);
        AgreementDialogHelper.getInstance().dismiss();
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        this.mBinding.recyclerView.setAdapter(null);
        super.onDestroy();
    }

    private void subscribeViewModel() {
        this.mViewModel.getOnBackEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$PendingUpgradeFragment$MYQ-z1AKOR8cYp3Mb49hSl6t1dc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PendingUpgradeFragment.this.lambda$subscribeViewModel$0$PendingUpgradeFragment((String) obj);
            }
        });
        this.mViewModel.getPersistentUpgradeList().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$PendingUpgradeFragment$iaLtg-88GmG-UO7xmH0Pe4_PWHY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PendingUpgradeFragment.this.lambda$subscribeViewModel$1$PendingUpgradeFragment((List) obj);
            }
        });
        this.mViewModel.getUpgradeAllEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$PendingUpgradeFragment$I5pdHyqvCEPfOXvA4ef-t2lEsL4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PendingUpgradeFragment.this.lambda$subscribeViewModel$6$PendingUpgradeFragment((String) obj);
            }
        });
    }

    public /* synthetic */ void lambda$subscribeViewModel$0$PendingUpgradeFragment(String str) {
        NavHostFragment.findNavController(this).popBackStack();
    }

    public /* synthetic */ void lambda$subscribeViewModel$1$PendingUpgradeFragment(List list) {
        this.mBinding.setViewModel(this.mViewModel);
    }

    public /* synthetic */ void lambda$subscribeViewModel$6$PendingUpgradeFragment(String str) {
        AgreementManager.get().tryExecute(requireContext(), 6, new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$PendingUpgradeFragment$HIYmmJOpYXvdYlIDKtrfTFq1BJU
            @Override // java.lang.Runnable
            public final void run() {
                PendingUpgradeFragment.this.lambda$subscribeViewModel$5$PendingUpgradeFragment();
            }
        }, true);
    }

    public /* synthetic */ void lambda$subscribeViewModel$5$PendingUpgradeFragment() {
        List<AppBaseModel> value = this.mViewModel.getPersistentUpgradeList().getValue();
        if (value != null && !value.isEmpty()) {
            final ArrayList arrayList = new ArrayList();
            for (AppBaseModel appBaseModel : value) {
                if (appBaseModel.isSuspended()) {
                    Logger.t(TAG).w("UpdateAll, ignore suspended app:" + appBaseModel, new Object[0]);
                } else {
                    int state = AppStoreAssembleManager.get().getState(appBaseModel.getPackageName(), appBaseModel.getDownloadUrl(), appBaseModel.getConfigUrl());
                    String packageName = appBaseModel.getPackageName();
                    if (AppComponentManager.get().isInstalled(packageName) && CheckUpdateManager.get().hasNewVersion(packageName) && (state == 6 || state == 2 || state == 0 || state == 101 || state == 10000)) {
                        AssembleDataContainer assembleDataContainer = new AssembleDataContainer();
                        assembleDataContainer.setPackageName(appBaseModel.getPackageName());
                        assembleDataContainer.setAppLabel(appBaseModel.getAppName());
                        assembleDataContainer.setDownloadUrl(appBaseModel.getDownloadUrl());
                        assembleDataContainer.setMd5(appBaseModel.getMd5());
                        assembleDataContainer.setConfigMd5(appBaseModel.getConfigMd5());
                        assembleDataContainer.setConfigUrl(appBaseModel.getConfigUrl());
                        assembleDataContainer.setIconUrl(appBaseModel.getIconUrl());
                        arrayList.add(assembleDataContainer);
                    }
                }
            }
            AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$PendingUpgradeFragment$7Vi1Mt_RG_VmgpBRD-0GsWxasYo
                @Override // java.lang.Runnable
                public final void run() {
                    PendingUpgradeFragment.this.lambda$subscribeViewModel$4$PendingUpgradeFragment(arrayList);
                }
            });
            return;
        }
        this.mViewModel.getUpdateAllVisible().postValue(false);
    }

    public /* synthetic */ void lambda$subscribeViewModel$4$PendingUpgradeFragment(final List list) {
        String format;
        if (this.mUpgradeConfirmDialog == null) {
            PanelDialog panelDialog = new PanelDialog(requireContext());
            this.mUpgradeConfirmDialog = panelDialog;
            panelDialog.setNegativeButton(ResUtils.getString(R.string.dialog_negative_btn), (XDialogInterface.OnClickListener) null);
        }
        Logger.t(TAG).i("upgrade all confirm dialog init:" + list, new Object[0]);
        this.mUpgradeConfirmDialog.setPositiveButton(ResUtils.getString(R.string.dialog_positive_btn), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$PendingUpgradeFragment$sIzVTpL506sEUbGyJrBp14t2K4w
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                PendingUpgradeFragment.lambda$subscribeViewModel$3(list, xDialog, i);
            }
        });
        int size = list.size();
        if (size == 1) {
            format = String.format(ResUtils.getString(R.string.upgrade_one_app_confirm), ((AssembleDataContainer) list.get(0)).getAppLabel());
        } else {
            format = String.format(ResUtils.getString(R.string.upgrade_confirm), Integer.valueOf(size));
        }
        this.mUpgradeConfirmDialog.setMessage(format);
        this.mUpgradeConfirmDialog.show(0, 10);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$subscribeViewModel$3(final List list, XDialog xDialog, int i) {
        if (i == -1) {
            AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$PendingUpgradeFragment$ls9eFvr7fIFB-W5SUrwJZ3Ru5yI
                @Override // java.lang.Runnable
                public final void run() {
                    PendingUpgradeFragment.lambda$subscribeViewModel$2(list);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$subscribeViewModel$2(List list) {
        Logger.t(TAG).i("upgrade all confirm click:" + list, new Object[0]);
        AppStoreAssembleManager.get().startWithConfig(list);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.IAgreementViewListener
    public void onShowAgreementDialog(final Runnable runnable, final boolean async) {
        AgreementDialogHelper.getInstance().show(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$PendingUpgradeFragment$XYygpI_8HaRE8D_fqIxea7gFUgI
            @Override // java.lang.Runnable
            public final void run() {
                AgreementManager.get().agreement(runnable, async);
            }
        });
    }
}
