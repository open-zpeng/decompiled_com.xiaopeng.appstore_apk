package com.xiaopeng.appstore.appstore_ui.fragment;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.logic.AppStoreLogicUtils;
import com.xiaopeng.appstore.appstore_biz.model2.AppDetailModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.DetailImageListAdapter;
import com.xiaopeng.appstore.appstore_ui.databinding.FragmentItemDetailBinding;
import com.xiaopeng.appstore.appstore_ui.viewmodel.ItemDetailViewModel;
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
import com.xiaopeng.appstore.libcommon.utils.NetworkUtils;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XToast;
/* loaded from: classes2.dex */
public class ItemDetailFragment extends BaseBizFragment implements IAgreementViewListener {
    public static final String APP_PACKAGE_NAME = "detail_page_app_package_name";
    public static final String OPEN_WITH_ACTION = "open_page_with_action";
    private static final String TAG = "ItemDetailFragment";
    private int mAction;
    private FragmentItemDetailBinding mBinding;
    private PanelDialog mCancelConfirmDialog;
    private DetailImageListAdapter mImageListAdapter;
    private String mPackageName;
    private UserProtocolDialog mProtocolDialog;
    private ItemDetailViewModel mViewModel;

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
        ItemDetailViewModel itemDetailViewModel = (ItemDetailViewModel) new ViewModelProvider(this).get(ItemDetailViewModel.class);
        this.mViewModel = itemDetailViewModel;
        itemDetailViewModel.request(this.mPackageName);
        this.mAction = arguments != null ? arguments.getInt("open_page_with_action") : 0;
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentItemDetailBinding fragmentItemDetailBinding = (FragmentItemDetailBinding) DataBindingUtil.inflate(inflater, R.layout.fragment_item_detail, container, false);
        this.mBinding = fragmentItemDetailBinding;
        fragmentItemDetailBinding.setLifecycleOwner(this);
        this.mBinding.setDividerColor(Integer.valueOf(R.color.divider_color));
        this.mBinding.rvImage.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        this.mImageListAdapter = new DetailImageListAdapter();
        this.mBinding.rvImage.setAdapter(this.mImageListAdapter);
        return this.mBinding.getRoot();
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this.mAction != 0) {
            openAndExecuteAction();
        }
        if (this.mBinding.tvPermission != null) {
            this.mBinding.tvPermission.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$ItemDetailFragment$DshJ4X1v3UJtmjeMkYsVWGQJAeg
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    ItemDetailFragment.this.lambda$onViewCreated$0$ItemDetailFragment(view2);
                }
            });
        }
        if (this.mBinding.tvPrivate != null) {
            this.mBinding.tvPrivate.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$ItemDetailFragment$TOKjFSaqnLRUiQdY5YsVbRZZZpw
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    ItemDetailFragment.this.lambda$onViewCreated$1$ItemDetailFragment(view2);
                }
            });
        }
        if (this.mBinding.tvProtocol != null) {
            this.mBinding.tvProtocol.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$ItemDetailFragment$MHJlphdbXf__G1gAyKdfrwTqiZo
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    ItemDetailFragment.this.lambda$onViewCreated$2$ItemDetailFragment(view2);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onViewCreated$0$ItemDetailFragment(View view) {
        launchPermissionFragment();
    }

    public /* synthetic */ void lambda$onViewCreated$1$ItemDetailFragment(View view) {
        launchPrivatePolicyFragment();
    }

    public /* synthetic */ void lambda$onViewCreated$2$ItemDetailFragment(View view) {
        showDynamicProtocoDialog();
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
        EventTrackingHelper.sendMolecast(PagesEnum.STORE_MAIN, EventEnum.USER_AGREEMENT, 1);
        AgreementDialogHelper.getInstance().show(null, true);
    }

    private void launchPermissionFragment() {
        NavController findNavController = NavHostFragment.findNavController(this);
        if (findNavController.getCurrentDestination() != null && findNavController.getCurrentDestination().getId() != R.id.itemDetailFragment) {
            Logger.t(TAG).w("launchPermissionFragment, current is not detailFragment:" + findNavController.getCurrentDestination(), new Object[0]);
            return;
        }
        EventTrackingHelper.sendMolecast(PagesEnum.STORE_MAIN, EventEnum.THIRD_APP_PERMISSION, this.mViewModel.getAppModel().getPackageName(), this.mViewModel.getAppModel().getAppName());
        Bundle bundle = new Bundle();
        bundle.putSerializable(PermissionFragment.PERMISSION_DATA, this.mViewModel.getAppModel().getPermissionModel());
        findNavController.navigate(R.id.action_itemDetailFragment_to_permissionFragment, bundle);
    }

    private void launchPrivatePolicyFragment() {
        NavController findNavController = NavHostFragment.findNavController(this);
        if (findNavController.getCurrentDestination() != null && findNavController.getCurrentDestination().getId() != R.id.itemDetailFragment) {
            Logger.t(TAG).w("launchPrivatePolicyFragment, current is not detailFragment:" + findNavController.getCurrentDestination(), new Object[0]);
            return;
        }
        EventTrackingHelper.sendMolecast(PagesEnum.STORE_MAIN, EventEnum.THIRD_PRIVACY_AGREEMENT, this.mViewModel.getAppModel().getPackageName(), this.mViewModel.getAppModel().getAppName());
        Bundle bundle = new Bundle();
        bundle.putSerializable(PrivatePolicyFragment.PRIVATE_DATA, this.mViewModel.getAppModel().getPolicyModel());
        findNavController.navigate(R.id.action_itemDetailFragment_to_privatePolicyFragment, bundle);
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
            this.mBinding.setDividerColor(Integer.valueOf(R.color.divider_color));
            this.mBinding.executePendingBindings();
        }
    }

    private void subscribeViewModel() {
        ItemDetailViewModel itemDetailViewModel = this.mViewModel;
        if (itemDetailViewModel != null) {
            itemDetailViewModel.getResourceLiveData().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$ItemDetailFragment$SQ8P20fje83OTEnWYk2XAUiTXJQ
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ItemDetailFragment.this.lambda$subscribeViewModel$3$ItemDetailFragment((Resource) obj);
                }
            });
            this.mViewModel.getOnBackEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$ItemDetailFragment$M5memvasB3-1cWr_m8P5nkpuMzY
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ItemDetailFragment.this.lambda$subscribeViewModel$4$ItemDetailFragment((String) obj);
                }
            });
            this.mViewModel.getOnExecuteEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$ItemDetailFragment$QRsD7p0qLqJW0fDUodAjGsmDhqU
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ItemDetailFragment.this.lambda$subscribeViewModel$7$ItemDetailFragment((AppDetailModel) obj);
                }
            });
            this.mViewModel.getOnRetryEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$ItemDetailFragment$cYi-ETgmDP4_i45dELqiDaeI2oI
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ItemDetailFragment.this.lambda$subscribeViewModel$8$ItemDetailFragment((String) obj);
                }
            });
            this.mViewModel.getOnCancelEvent().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$ItemDetailFragment$G9QnDMEisP7L8v6NdBXo56BdRwo
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ItemDetailFragment.this.lambda$subscribeViewModel$10$ItemDetailFragment((AppDetailModel) obj);
                }
            });
            this.mViewModel.getOnDownloadFinish().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$ItemDetailFragment$HUt2hefsWOUYxrnTWdk1G_YwCp4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ItemDetailFragment.this.lambda$subscribeViewModel$11$ItemDetailFragment(obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$subscribeViewModel$3$ItemDetailFragment(Resource resource) {
        this.mBinding.setViewModel(this.mViewModel);
    }

    public /* synthetic */ void lambda$subscribeViewModel$4$ItemDetailFragment(String str) {
        NavHostFragment.findNavController(this).popBackStack();
    }

    public /* synthetic */ void lambda$subscribeViewModel$7$ItemDetailFragment(final AppDetailModel appDetailModel) {
        if (NetworkUtils.isConnected()) {
            final int state = AppStoreAssembleManager.get().getState(appDetailModel.getPackageName(), appDetailModel.getDownloadUrl(), appDetailModel.getConfigUrl());
            AgreementManager.get().tryExecute(requireContext(), state, new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$ItemDetailFragment$iQeGX_4oL_6OuJpkwnkBxN3b40U
                @Override // java.lang.Runnable
                public final void run() {
                    ItemDetailFragment.this.lambda$subscribeViewModel$6$ItemDetailFragment(appDetailModel, state);
                }
            }, true);
            EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_DETAIL, EventEnum.OPEN_APP_FROM_STORE_DETAIL_ENTRANCE, Integer.valueOf(LogicUtils.convertAppOperationStatePause(state)), appDetailModel.getPackageName(), appDetailModel.getAppName());
            return;
        }
        XToast.show(ResUtils.getString(R.string.net_not_connect));
    }

    public /* synthetic */ void lambda$subscribeViewModel$6$ItemDetailFragment(final AppDetailModel appDetailModel, final int i) {
        LogicUIUtils.tryExecuteSuspendApp(appDetailModel.getPackageName(), i, getContext(), appDetailModel.isSuspended(), new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$ItemDetailFragment$gEo9x9sohHjKU4OPAC-fC_b_pEI
            @Override // java.lang.Runnable
            public final void run() {
                AppExecuteHelper.execute(i, r1.getPackageName(), r1.getDownloadUrl(), r1.getMd5(), r1.getConfigUrl(), r1.getConfigMd5(), r1.getAppName(), appDetailModel.getIconUrl());
            }
        });
    }

    public /* synthetic */ void lambda$subscribeViewModel$8$ItemDetailFragment(String str) {
        if (NetworkUtils.isConnected()) {
            this.mViewModel.request(this.mPackageName);
        } else {
            XToast.show(ResUtils.getString(R.string.net_not_connect));
        }
    }

    public /* synthetic */ void lambda$subscribeViewModel$10$ItemDetailFragment(AppDetailModel appDetailModel) {
        tryInitCancelDialog();
        final String packageName = appDetailModel.getPackageName();
        this.mCancelConfirmDialog.setPositiveButton(ResUtils.getString(R.string.dialog_positive_btn), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$ItemDetailFragment$nFdd3lhykfn709MEmgi64UrlV34
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                ItemDetailFragment.this.lambda$subscribeViewModel$9$ItemDetailFragment(packageName, xDialog, i);
            }
        });
        this.mCancelConfirmDialog.show();
        EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_DETAIL, EventEnum.OPEN_APP_FROM_STORE_DETAIL_ENTRANCE, Integer.valueOf(LogicUtils.convertAppOperationStateCancel(1)), appDetailModel.getPackageName(), appDetailModel.getAppName());
    }

    public /* synthetic */ void lambda$subscribeViewModel$9$ItemDetailFragment(String str, XDialog xDialog, int i) {
        cancel(str);
    }

    public /* synthetic */ void lambda$subscribeViewModel$11$ItemDetailFragment(Object obj) {
        PanelDialog panelDialog = this.mCancelConfirmDialog;
        if (panelDialog != null) {
            panelDialog.dismiss();
        }
    }

    private void openAndExecuteAction() {
        ItemDetailViewModel itemDetailViewModel = this.mViewModel;
        if (itemDetailViewModel != null) {
            itemDetailViewModel.getResourceLiveData().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$ItemDetailFragment$yScKjMWzUjat0yalElEpOn-tQm0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ItemDetailFragment.this.lambda$openAndExecuteAction$14$ItemDetailFragment((Resource) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$openAndExecuteAction$14$ItemDetailFragment(Resource resource) {
        final AppDetailModel appDetailModel = (AppDetailModel) resource.data;
        if (appDetailModel != null) {
            if (NetworkUtils.isConnected()) {
                final int state = AppStoreAssembleManager.get().getState(appDetailModel.getPackageName(), appDetailModel.getDownloadUrl(), appDetailModel.getConfigUrl());
                Logger.t(TAG).d("openAndExecuteAction: state = " + state);
                if (this.mAction == 1) {
                    if (state == 0 || state == 6 || state == 2) {
                        AgreementManager.get().tryExecute(requireContext(), state, new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$ItemDetailFragment$FEsotmde8ewyRnCcYeHWBKoFXYc
                            @Override // java.lang.Runnable
                            public final void run() {
                                ItemDetailFragment.this.lambda$openAndExecuteAction$13$ItemDetailFragment(appDetailModel, state);
                            }
                        }, true);
                        EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_DETAIL, EventEnum.OPEN_APP_FROM_STORE_DETAIL_ENTRANCE, Integer.valueOf(LogicUtils.convertAppOperationStatePause(state)), appDetailModel.getPackageName(), appDetailModel.getAppName());
                        return;
                    }
                    return;
                }
                return;
            }
            XToast.show(ResUtils.getString(R.string.net_not_connect));
            return;
        }
        Logger.t(TAG).d("openAndExecuteAction: model is null");
    }

    public /* synthetic */ void lambda$openAndExecuteAction$13$ItemDetailFragment(final AppDetailModel appDetailModel, final int i) {
        LogicUIUtils.tryExecuteSuspendApp(appDetailModel.getPackageName(), i, getContext(), appDetailModel.isSuspended(), new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$ItemDetailFragment$bkzI7ridbqpD145oCuKyw6kdkUQ
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
        AgreementDialogHelper.getInstance().show(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.-$$Lambda$ItemDetailFragment$XzZU5qmxE8Tv3ADovshJBJjOYqQ
            @Override // java.lang.Runnable
            public final void run() {
                AgreementManager.get().agreement(runnable, async);
            }
        });
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        Logger.t(TAG).i("ItemDetailFragment onStop Start", new Object[0]);
        super.onStop();
        destroyAgreement();
        PanelDialog panelDialog = this.mCancelConfirmDialog;
        if (panelDialog != null && panelDialog.isShowing()) {
            this.mCancelConfirmDialog.dismiss();
        }
        Logger.t(TAG).i("ItemDetailFragment onStop Start", new Object[0]);
    }

    private void tryInitCancelDialog() {
        if (this.mCancelConfirmDialog == null) {
            PanelDialog panelDialog = new PanelDialog(requireContext());
            this.mCancelConfirmDialog = panelDialog;
            panelDialog.setTitle(R.string.detail_cancel_confirm_title);
            this.mCancelConfirmDialog.setMessage(R.string.detail_cancel_confirm_desc);
            this.mCancelConfirmDialog.setPositiveButton(ResUtils.getString(R.string.dialog_positive_btn)).setNegativeButton(ResUtils.getString(R.string.dialog_negative_btn));
        }
    }

    private void cancel(String packageName) {
        AppStoreAssembleManager.get().cancelWithPackage(packageName);
    }
}
