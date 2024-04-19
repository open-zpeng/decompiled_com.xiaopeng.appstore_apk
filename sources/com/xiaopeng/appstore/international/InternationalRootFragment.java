package com.xiaopeng.appstore.international;

import android.os.Bundle;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.model.TabBean;
import com.xiaopeng.appstore.appstore_ui.navigation.KeepStateNavigator;
import com.xiaopeng.appstore.bizcommon.logic.Constants;
import com.xiaopeng.appstore.common_ui.BizRootActivity;
/* loaded from: classes.dex */
public class InternationalRootFragment extends NavHostFragment implements BizRootActivity.OnActivityEventListener {
    private static final String TAG = "AppListRootFragment";
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

    public InternationalRootFragment() {
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
        getNavController().setGraph(R.navigation.international_app_nav_graph);
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
        handleNavigate();
        this.mShouldReset = false;
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

    public void handleNavigate() {
        if (navigateToOtherFragment(getArguments(), this.mShouldReset)) {
            return;
        }
        if (this.mShouldReset) {
            Logger.t(TAG).i("navigate to home", new Object[0]);
            getNavController().navigate(R.id.back_to_home, new Bundle());
            return;
        }
        Logger.t(TAG).d("do not navigate");
    }

    private boolean navigateToOtherFragment(Bundle arguments, boolean clearBackStack) {
        TabBean tabBean;
        if (arguments != null && (tabBean = (TabBean) arguments.getParcelable(Constants.STORE_FRAGMENT_PARAMS)) != null) {
            Bundle bundle = new Bundle();
            if (tabBean.getNavigatorType() == 200) {
                bundle.putString("detail_page_app_package_name", tabBean.getPackageName());
                bundle.putInt("open_page_with_action", tabBean.getAction());
                NavOptions.Builder builder = new NavOptions.Builder();
                if (clearBackStack) {
                    builder.setPopUpTo(com.xiaopeng.appstore.appstore_ui.R.id.nav_graph, false);
                }
                getNavController().navigate(com.xiaopeng.appstore.appstore_ui.R.id.go_to_itemDetail, bundle, builder.build());
                arguments.remove(Constants.STORE_FRAGMENT_PARAMS);
                return true;
            }
        }
        return false;
    }
}
