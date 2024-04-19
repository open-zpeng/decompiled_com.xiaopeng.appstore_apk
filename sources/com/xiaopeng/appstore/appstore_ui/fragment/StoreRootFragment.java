package com.xiaopeng.appstore.appstore_ui.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.model.TabBean;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.navigation.KeepStateNavigator;
import com.xiaopeng.appstore.bizcommon.logic.AgreementDialogHelper;
import com.xiaopeng.appstore.bizcommon.logic.Constants;
import com.xiaopeng.appstore.common_ui.BaseTabFragment;
import com.xiaopeng.appstore.common_ui.BizRootActivity;
import com.xiaopeng.appstore.xpcommon.privacy.PrivacyUtils;
/* loaded from: classes2.dex */
public class StoreRootFragment extends NavHostFragment implements BizRootActivity.OnActivityEventListener {
    private static final String TAG = "StoreRootFragment";
    protected BizRootActivity mRootActivity;
    private boolean mShouldReset = false;

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity.OnActivityEventListener
    public void onPageSelected(int index) {
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity.OnActivityEventListener
    public void onTabSelected(int index, Class<?> fragmentClz) {
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity.OnActivityEventListener
    public void onWindowClose() {
    }

    public StoreRootFragment() {
        Logger.t(TAG).i("create:" + hashCode() + ", " + this, new Object[0]);
    }

    public boolean canNavBack() {
        return ((KeepStateNavigator) getNavController().getNavigatorProvider().getNavigator(KeepStateNavigator.class)).getBackStackEntryCount() > 0;
    }

    @Override // androidx.navigation.fragment.NavHostFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.t(TAG).d("onCreate " + getClass().getSimpleName() + " " + hashCode());
        if (getActivity() instanceof BizRootActivity) {
            BizRootActivity bizRootActivity = (BizRootActivity) getActivity();
            this.mRootActivity = bizRootActivity;
            bizRootActivity.addOnActivityListener(this);
        }
        getNavController().setGraph(R.navigation.nav_graph);
    }

    @Override // androidx.navigation.fragment.NavHostFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.t(TAG).i("onSaveInstanceState " + outState, new Object[0]);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Logger.t(TAG).i("onViewStateRestored " + savedInstanceState, new Object[0]);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Logger.t(TAG).d("onStart " + getClass().getSimpleName() + " " + hashCode());
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Logger.t(TAG).d("onResume " + getClass().getSimpleName() + " " + hashCode());
        tryHandleNavOnInit(true);
        this.mShouldReset = false;
        this.mRootActivity.setCanNavBack(true);
        tryShowPrivacy();
    }

    private void tryShowPrivacy() {
        if (PrivacyUtils.isNeedReconfirm(requireContext())) {
            AgreementDialogHelper.getInstance().show(null);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        Logger.t(TAG).d("onPause " + getClass().getSimpleName() + " " + hashCode());
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        Logger.t(TAG).d("onStop " + getClass().getSimpleName() + " " + hashCode());
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        Logger.t(TAG).d("onDestroy " + getClass().getSimpleName() + " " + hashCode());
        BizRootActivity bizRootActivity = this.mRootActivity;
        if (bizRootActivity != null) {
            bizRootActivity.removeActivityListener(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.navigation.fragment.NavHostFragment
    public void onCreateNavController(NavController navController) {
        super.onCreateNavController(navController);
        navController.getNavigatorProvider().addNavigator(new KeepStateNavigator(requireContext().getApplicationContext(), getChildFragmentManager(), getId()));
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity.OnActivityEventListener
    public void onWindowReset() {
        this.mShouldReset = true;
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity.OnActivityEventListener
    public void onTabInitRequest() {
        if (getContext() == null) {
            Logger.t(TAG).w("onTabInitRequest, detached. current=%s", Integer.valueOf(hashCode()));
            return;
        }
        Logger.t(TAG).i("onTabInitRequest, current=%s", getClass().getSimpleName());
        tryHandleNavOnInit(false);
    }

    private void tryHandleNavOnInit(boolean homeLazyLoad) {
        Logger.t(TAG).d("tryHandleNavOnInit, homeLazyLoad:" + homeLazyLoad + ", " + hashCode());
        handleNavigate(homeLazyLoad);
    }

    public void handleNavigate() {
        handleNavigate(true);
    }

    public void handleNavigate(boolean homeLazyLoad) {
        if (navigateToOtherFragment(getArguments(), this.mShouldReset)) {
            return;
        }
        if (this.mShouldReset) {
            boolean z = getChildFragmentManager().getPrimaryNavigationFragment() instanceof StoreHomeFragment;
            if (!z && (z = getNavController().popBackStack(R.id.storeHomeFragment, false))) {
                Logger.t(TAG).i("pop to home success, homeLazyLoad:" + homeLazyLoad, new Object[0]);
            }
            Fragment findHomeFragment = findHomeFragment();
            if (z && findHomeFragment != null) {
                Logger.t(TAG).i("use exist home, homeLazyLoad:" + homeLazyLoad + ", homeFrag:" + findHomeFragment, new Object[0]);
                Bundle arguments = findHomeFragment.getArguments();
                if (arguments == null) {
                    arguments = new Bundle();
                }
                arguments.putBoolean(StoreHomeFragment.ARGS_KEY_REFRESH_DATA, true);
                findHomeFragment.setArguments(arguments);
                return;
            }
            Logger.t(TAG).i("pop to home not found, homeLazyLoad:" + homeLazyLoad, new Object[0]);
            Bundle bundle = new Bundle();
            bundle.putBoolean(BaseTabFragment.LAZY_LOADING_DATA, homeLazyLoad);
            getNavController().navigate(R.id.back_to_home, bundle);
            return;
        }
        if (!homeLazyLoad) {
            Fragment primaryNavigationFragment = getChildFragmentManager().getPrimaryNavigationFragment();
            if (primaryNavigationFragment instanceof BizRootActivity.OnActivityEventListener) {
                ((BizRootActivity.OnActivityEventListener) primaryNavigationFragment).onTabInitRequest();
            }
        }
        Logger.t(TAG).d("do not navigate, homeLazyLoad:" + homeLazyLoad);
    }

    private Fragment findHomeFragment() {
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            if (fragment instanceof StoreHomeFragment) {
                NavDestination currentDestination = getNavController().getCurrentDestination();
                if (currentDestination instanceof FragmentNavigator.Destination) {
                    FragmentNavigator.Destination destination = (FragmentNavigator.Destination) currentDestination;
                    if (destination.getClassName().equals(StoreHomeFragment.class.getName())) {
                        return fragment;
                    }
                    Logger.t(TAG).w("findHomeFragment, current destination not home:" + destination, new Object[0]);
                } else {
                    continue;
                }
            }
        }
        return null;
    }

    private boolean navigateToOtherFragment(Bundle arguments, boolean clearBackStack) {
        if (arguments != null) {
            TabBean tabBean = (TabBean) arguments.getParcelable(Constants.STORE_FRAGMENT_PARAMS);
            if (tabBean != null) {
                Bundle bundle = new Bundle();
                if (tabBean.getNavigatorType() == 200) {
                    bundle.putString("detail_page_app_package_name", tabBean.getPackageName());
                    bundle.putInt("open_page_with_action", tabBean.getAction());
                    NavOptions.Builder builder = new NavOptions.Builder();
                    if (clearBackStack) {
                        builder.setPopUpTo(R.id.nav_graph, false);
                    }
                    Logger.t(TAG).i("navigateToOtherFragment, go to detail:" + tabBean.getPackageName(), new Object[0]);
                    getNavController().navigate(R.id.go_to_itemDetail, bundle, builder.build());
                    arguments.remove(Constants.STORE_FRAGMENT_PARAMS);
                    return true;
                }
                Logger.t(TAG).i("navigateToOtherFragment, argument not support:" + tabBean, new Object[0]);
            } else {
                Logger.t(TAG).i("navigateToOtherFragment, argument not satisfy", new Object[0]);
            }
        }
        return false;
    }
}
