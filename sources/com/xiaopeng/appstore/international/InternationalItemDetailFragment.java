package com.xiaopeng.appstore.international;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.logic.AppStoreLogicUtils;
import com.xiaopeng.appstore.appstore_biz.model2.AppDetailModel;
import com.xiaopeng.appstore.appstore_ui.adapter.DetailImageListAdapter;
import com.xiaopeng.appstore.appstore_ui.fragment.PermissionFragment;
import com.xiaopeng.appstore.appstore_ui.fragment.PrivatePolicyFragment;
import com.xiaopeng.appstore.bizcommon.logic.AgreementDialogHelper;
import com.xiaopeng.appstore.bizcommon.logic.AgreementManager;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.IAgreementViewListener;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.common_ui.AppExecuteHelper;
import com.xiaopeng.appstore.common_ui.BaseBizFragment;
import com.xiaopeng.appstore.common_ui.UserProtocolDialog;
import com.xiaopeng.appstore.common_ui.base.PanelDialog;
import com.xiaopeng.appstore.common_ui.common.Resource;
import com.xiaopeng.appstore.common_ui.logic.LogicUIUtils;
import com.xiaopeng.appstore.international.databinding.InternationalFragmentItemDetailBinding;
import com.xiaopeng.appstore.libcommon.utils.NetworkUtils;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XToast;
/* loaded from: classes.dex */
public class InternationalItemDetailFragment extends BaseBizFragment implements IAgreementViewListener {
    public static final String APP_PACKAGE_NAME = "detail_page_app_package_name";
    public static final String OPEN_WITH_ACTION = "open_page_with_action";
    private static final String TAG = "ItemDetailFragment";
    private int mAction;
    private InternationalFragmentItemDetailBinding mBinding;
    private PanelDialog mCancelConfirmDialog;
    private DetailImageListAdapter mImageListAdapter;
    private String mPackageName;
    private UserProtocolDialog mProtocolDialog;
    private InternationalItemDetailViewModel mViewModel;

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment
    public boolean ignoreSameArguments(Bundle arguments) {
        String string = arguments != null ? arguments.getString("detail_page_app_package_name") : null;
        boolean z = string != null && string.equals(this.mPackageName);
        if (z) {
            this.mAction = arguments.getInt("open_page_with_action");
            openAndExecuteAction();
        }
        return z;
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        this.mPackageName = arguments != null ? arguments.getString("detail_page_app_package_name") : null;
        InternationalItemDetailViewModel internationalItemDetailViewModel = (InternationalItemDetailViewModel) new ViewModelProvider(this).get(InternationalItemDetailViewModel.class);
        this.mViewModel = internationalItemDetailViewModel;
        internationalItemDetailViewModel.request(this.mPackageName);
        this.mAction = arguments != null ? arguments.getInt("open_page_with_action") : 0;
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        InternationalFragmentItemDetailBinding internationalFragmentItemDetailBinding = (InternationalFragmentItemDetailBinding) DataBindingUtil.inflate(inflater, R.layout.international_fragment_item_detail, container, false);
        this.mBinding = internationalFragmentItemDetailBinding;
        internationalFragmentItemDetailBinding.setLifecycleOwner(this);
        this.mBinding.setDividerColor(Integer.valueOf(com.xiaopeng.appstore.appstore_ui.R.color.divider_color));
        return this.mBinding.getRoot();
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this.mAction != 0) {
            openAndExecuteAction();
        }
        if (this.mBinding.tvPrivate != null) {
            this.mBinding.tvPrivate.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.international.-$$Lambda$InternationalItemDetailFragment$-vp90Vxx2XMhhKVzu1jFOG-gknA
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    InternationalItemDetailFragment.this.lambda$onViewCreated$0$InternationalItemDetailFragment(view2);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onViewCreated$0$InternationalItemDetailFragment(View view) {
        launchPrivatePolicyFragment();
    }

    private void showProtocolDialog() {
        if (this.mProtocolDialog == null && getActivity() != null) {
            this.mProtocolDialog = new UserProtocolDialog(getActivity());
        }
        if (this.mProtocolDialog.isShowing()) {
            return;
        }
        this.mProtocolDialog.show();
    }

    private void showDynamicProtocoDialog() {
        AgreementDialogHelper.getInstance().show(null, true);
    }

    private void launchPermissionFragment() {
        NavController findNavController = NavHostFragment.findNavController(this);
        if (findNavController.getCurrentDestination() != null && findNavController.getCurrentDestination().getId() != com.xiaopeng.appstore.appstore_ui.R.id.itemDetailFragment) {
            Logger.t(TAG).w("launchPermissionFragment, current is not detailFragment:" + findNavController.getCurrentDestination(), new Object[0]);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(PermissionFragment.PERMISSION_DATA, this.mViewModel.getAppModel().getPermissionModel());
        findNavController.navigate(com.xiaopeng.appstore.appstore_ui.R.id.action_itemDetailFragment_to_permissionFragment, bundle);
    }

    private void launchPrivatePolicyFragment() {
        NavController findNavController = NavHostFragment.findNavController(this);
        if (findNavController.getCurrentDestination() != null && findNavController.getCurrentDestination().getId() != com.xiaopeng.appstore.appstore_ui.R.id.itemDetailFragment) {
            Logger.t(TAG).w("launchPrivatePolicyFragment, current is not detailFragment:" + findNavController.getCurrentDestination(), new Object[0]);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(PrivatePolicyFragment.PRIVATE_DATA, this.mViewModel.getAppModel().getPolicyModel());
        findNavController.navigate(com.xiaopeng.appstore.appstore_ui.R.id.action_itemDetailFragment_to_privatePolicyFragment, bundle);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subscribeViewModel();
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        Logger.t(TAG).i("ItemDetailFragment onResume Start", new Object[0]);
        super.onResume();
        AgreementManager.get().addAgreementViewListener(this);
        this.mRootActivity.setCanNavBack(true);
        Logger.t(TAG).i("ItemDetailFragment onResume End", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        Logger.t(TAG).i("ItemDetailFragment onStop Start", new Object[0]);
        super.onStop();
        destroyAgreement();
        Logger.t(TAG).i("ItemDetailFragment onStop Start", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        Logger.t(TAG).i("ItemDetailFragment onDestroy Start", new Object[0]);
        super.onDestroy();
        Logger.t(TAG).i("ItemDetailFragment onDestroy End", new Object[0]);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (ThemeManager.isThemeChanged(newConfig)) {
            DetailImageListAdapter detailImageListAdapter = this.mImageListAdapter;
            if (detailImageListAdapter != null) {
                detailImageListAdapter.notifyItemRangeChanged(0, detailImageListAdapter.getItemCount());
            }
            this.mBinding.setDividerColor(Integer.valueOf(com.xiaopeng.appstore.appstore_ui.R.color.divider_color));
            this.mBinding.executePendingBindings();
        }
    }

    private void subscribeViewModel() {
        InternationalItemDetailViewModel internationalItemDetailViewModel = this.mViewModel;
        if (internationalItemDetailViewModel != null) {
            internationalItemDetailViewModel.getResourceLiveData().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.international.-$$Lambda$InternationalItemDetailFragment$tnZ-o_TOLdNa3J5MzWvQdTcVVr0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    InternationalItemDetailFragment.this.lambda$subscribeViewModel$1$InternationalItemDetailFragment((Resource) obj);
                }
            });
            this.mViewModel.getOnBackEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.international.-$$Lambda$InternationalItemDetailFragment$zhpWE7HvxOq4h6-BXZdGKah1moE
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    InternationalItemDetailFragment.this.lambda$subscribeViewModel$2$InternationalItemDetailFragment((String) obj);
                }
            });
            this.mViewModel.getOnExecuteEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.international.-$$Lambda$InternationalItemDetailFragment$w4WV0EY6fesQJ9pYIFSKNETADGg
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    InternationalItemDetailFragment.this.lambda$subscribeViewModel$5$InternationalItemDetailFragment((AppDetailModel) obj);
                }
            });
            this.mViewModel.getOnRetryEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.international.-$$Lambda$InternationalItemDetailFragment$B7OGVRHKNvgXvFpugcygkC22ess
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    InternationalItemDetailFragment.this.lambda$subscribeViewModel$6$InternationalItemDetailFragment((String) obj);
                }
            });
            this.mViewModel.getOnCancelEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.international.-$$Lambda$InternationalItemDetailFragment$fkPuPoGwBiMxUVoDjnyKcnlXI1w
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    InternationalItemDetailFragment.this.lambda$subscribeViewModel$8$InternationalItemDetailFragment((AppDetailModel) obj);
                }
            });
            this.mViewModel.getOnDownloadFinish().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.international.-$$Lambda$InternationalItemDetailFragment$k9EHqiXMofFYxwmjlgksl1TXkAQ
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    InternationalItemDetailFragment.this.lambda$subscribeViewModel$9$InternationalItemDetailFragment(obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$subscribeViewModel$1$InternationalItemDetailFragment(Resource resource) {
        this.mBinding.setViewModel(this.mViewModel);
    }

    public /* synthetic */ void lambda$subscribeViewModel$2$InternationalItemDetailFragment(String str) {
        NavHostFragment.findNavController(this).popBackStack();
    }

    public /* synthetic */ void lambda$subscribeViewModel$5$InternationalItemDetailFragment(final AppDetailModel appDetailModel) {
        if (NetworkUtils.isConnected()) {
            NavHostFragment.findNavController(this).popBackStack();
            final int state = AppStoreAssembleManager.get().getState(appDetailModel.getPackageName(), appDetailModel.getDownloadUrl(), appDetailModel.getConfigUrl());
            AgreementManager.get().tryExecute(requireContext(), state, new Runnable() { // from class: com.xiaopeng.appstore.international.-$$Lambda$InternationalItemDetailFragment$DQmq1OZtZxNdZshOaOD0jfiacbo
                @Override // java.lang.Runnable
                public final void run() {
                    InternationalItemDetailFragment.this.lambda$subscribeViewModel$4$InternationalItemDetailFragment(appDetailModel, state);
                }
            }, true);
            EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_DETAIL, EventEnum.OPEN_APP_FROM_STORE_DETAIL_ENTRANCE, Integer.valueOf(LogicUtils.convertAppOperationStatePause(state)), appDetailModel.getPackageName(), appDetailModel.getAppName());
            return;
        }
        XToast.show(ResUtils.getString(com.xiaopeng.appstore.appstore_ui.R.string.net_not_connect));
    }

    public /* synthetic */ void lambda$subscribeViewModel$4$InternationalItemDetailFragment(final AppDetailModel appDetailModel, final int i) {
        LogicUIUtils.tryExecuteSuspendApp(appDetailModel.getPackageName(), i, getContext(), appDetailModel.isSuspended(), new Runnable() { // from class: com.xiaopeng.appstore.international.-$$Lambda$InternationalItemDetailFragment$FOp2cTjCP660g7sxUVAxYjNvyck
            @Override // java.lang.Runnable
            public final void run() {
                AppExecuteHelper.execute(i, r1.getPackageName(), r1.getDownloadUrl(), r1.getMd5(), r1.getConfigUrl(), r1.getConfigMd5(), r1.getAppName(), appDetailModel.getIconUrl());
            }
        });
    }

    public /* synthetic */ void lambda$subscribeViewModel$6$InternationalItemDetailFragment(String str) {
        if (NetworkUtils.isConnected()) {
            this.mViewModel.request(this.mPackageName);
        } else {
            XToast.show(ResUtils.getString(com.xiaopeng.appstore.appstore_ui.R.string.net_not_connect));
        }
    }

    public /* synthetic */ void lambda$subscribeViewModel$8$InternationalItemDetailFragment(AppDetailModel appDetailModel) {
        tryInitCancelDialog();
        final String packageName = appDetailModel.getPackageName();
        this.mCancelConfirmDialog.setPositiveButton(ResUtils.getString(com.xiaopeng.appstore.appstore_ui.R.string.dialog_positive_btn), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.appstore.international.-$$Lambda$InternationalItemDetailFragment$7KsiSxXzischcyXzgu3h57qAjdc
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                InternationalItemDetailFragment.this.lambda$subscribeViewModel$7$InternationalItemDetailFragment(packageName, xDialog, i);
            }
        });
        this.mCancelConfirmDialog.show();
        EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_DETAIL, EventEnum.OPEN_APP_FROM_STORE_DETAIL_ENTRANCE, Integer.valueOf(LogicUtils.convertAppOperationStateCancel(1)), appDetailModel.getPackageName(), appDetailModel.getAppName());
    }

    public /* synthetic */ void lambda$subscribeViewModel$7$InternationalItemDetailFragment(String str, XDialog xDialog, int i) {
        cancel(str);
    }

    public /* synthetic */ void lambda$subscribeViewModel$9$InternationalItemDetailFragment(Object obj) {
        PanelDialog panelDialog = this.mCancelConfirmDialog;
        if (panelDialog != null) {
            panelDialog.dismiss();
        }
    }

    private void openAndExecuteAction() {
        InternationalItemDetailViewModel internationalItemDetailViewModel = this.mViewModel;
        if (internationalItemDetailViewModel != null) {
            internationalItemDetailViewModel.getResourceLiveData().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.international.-$$Lambda$InternationalItemDetailFragment$5JfM8VHNqcqZUXbgDbQD5ThAk8Q
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    InternationalItemDetailFragment.this.lambda$openAndExecuteAction$12$InternationalItemDetailFragment((Resource) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$openAndExecuteAction$12$InternationalItemDetailFragment(Resource resource) {
        final AppDetailModel appDetailModel = (AppDetailModel) resource.data;
        if (appDetailModel != null) {
            if (NetworkUtils.isConnected()) {
                final int state = AppStoreAssembleManager.get().getState(appDetailModel.getPackageName(), appDetailModel.getDownloadUrl(), appDetailModel.getConfigUrl());
                Logger.t(TAG).d("openAndExecuteAction: state = " + state);
                if (this.mAction == 1) {
                    if (state == 0 || state == 6 || state == 2) {
                        AgreementManager.get().tryExecute(requireContext(), state, new Runnable() { // from class: com.xiaopeng.appstore.international.-$$Lambda$InternationalItemDetailFragment$iLpTjOkV6W3GGrwnRg-zndGDxso
                            @Override // java.lang.Runnable
                            public final void run() {
                                InternationalItemDetailFragment.this.lambda$openAndExecuteAction$11$InternationalItemDetailFragment(appDetailModel, state);
                            }
                        }, true);
                        EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_DETAIL, EventEnum.OPEN_APP_FROM_STORE_DETAIL_ENTRANCE, Integer.valueOf(LogicUtils.convertAppOperationStatePause(state)), appDetailModel.getPackageName(), appDetailModel.getAppName());
                        return;
                    }
                    return;
                }
                return;
            }
            XToast.show(ResUtils.getString(com.xiaopeng.appstore.appstore_ui.R.string.net_not_connect));
            return;
        }
        Logger.t(TAG).d("openAndExecuteAction: model is null");
    }

    public /* synthetic */ void lambda$openAndExecuteAction$11$InternationalItemDetailFragment(final AppDetailModel appDetailModel, final int i) {
        LogicUIUtils.tryExecuteSuspendApp(appDetailModel.getPackageName(), i, getContext(), appDetailModel.isSuspended(), new Runnable() { // from class: com.xiaopeng.appstore.international.-$$Lambda$InternationalItemDetailFragment$ZkrPAaCX8EbKwFemMTjXrZPC6ws
            @Override // java.lang.Runnable
            public final void run() {
                AppExecuteHelper.execute(i, r1.getPackageName(), r1.getDownloadUrl(), r1.getMd5(), r1.getConfigUrl(), r1.getConfigMd5(), r1.getAppName(), appDetailModel.getIconUrl());
            }
        });
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.BizRootActivity.OnActivityEventListener
    public void onWindowClose() {
        super.onWindowClose();
        if (TextUtils.isEmpty(this.mPackageName)) {
            return;
        }
        AppStoreLogicUtils.setLastViewPackage(this.mPackageName);
    }

    private void destroyAgreement() {
        AgreementManager.get().removeAgreementViewListener(this);
        AgreementDialogHelper.getInstance().dismiss();
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.IAgreementViewListener
    public void onShowAgreementDialog(final Runnable runnable, final boolean async) {
        AgreementDialogHelper.getInstance().show(new Runnable() { // from class: com.xiaopeng.appstore.international.-$$Lambda$InternationalItemDetailFragment$cy6kN376wUckM3Ru0_86kpf3S08
            @Override // java.lang.Runnable
            public final void run() {
                AgreementManager.get().agreement(runnable, async);
            }
        });
    }

    private void tryInitCancelDialog() {
        if (this.mCancelConfirmDialog == null) {
            PanelDialog panelDialog = new PanelDialog(requireContext());
            this.mCancelConfirmDialog = panelDialog;
            panelDialog.setTitle(com.xiaopeng.appstore.appstore_ui.R.string.detail_cancel_confirm_title);
            this.mCancelConfirmDialog.setMessage(com.xiaopeng.appstore.appstore_ui.R.string.detail_cancel_confirm_desc);
            this.mCancelConfirmDialog.setPositiveButton(ResUtils.getString(com.xiaopeng.appstore.appstore_ui.R.string.dialog_positive_btn)).setNegativeButton(ResUtils.getString(com.xiaopeng.appstore.appstore_ui.R.string.dialog_negative_btn));
        }
    }

    private void cancel(String packageName) {
        AppStoreAssembleManager.get().cancelWithPackage(packageName);
    }
}
