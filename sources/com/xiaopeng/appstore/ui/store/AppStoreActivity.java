package com.xiaopeng.appstore.ui.store;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;
import com.xiaopeng.appstore.AppUtils;
import com.xiaopeng.appstore.R;
import com.xiaopeng.appstore.appstore_biz.model.TabBean;
import com.xiaopeng.appstore.appstore_ui.fragment.StoreRootFragment;
import com.xiaopeng.appstore.bizcommon.logic.Constants;
import com.xiaopeng.appstore.bizcommon.router.RouterConstants;
import com.xiaopeng.appstore.bizcommon.router.RouterManager;
import com.xiaopeng.appstore.bizcommon.router.provider.AppStoreProvider;
import com.xiaopeng.appstore.common_ui.BizRootActivity;
import com.xiaopeng.appstore.common_ui.BizRootActivityImpl;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.libtheme.ThemeManager;
/* loaded from: classes2.dex */
public class AppStoreActivity extends BizRootActivityImpl implements View.OnClickListener {
    private static final String TAG = "AppStoreActivity";
    public AppStoreProvider mAppStoreProvider;
    private Drawable mBtnBackIcon;
    private Drawable mBtnBg;
    private Drawable mBtnCloseIcon;
    private boolean mCanNavBack = false;
    private Fragment mFragment;
    private ImageView mIvBack;

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public Class<?> getCurrentSelected() {
        return null;
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public int getIndex() {
        return 0;
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public int getIndexOfTab(BizRootActivity.TabDefine tabDefine) {
        return 0;
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public void notifyTabInit(BizRootActivity.TabDefine tab) {
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivityImpl
    protected void onWindowReset() {
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public void setTitleVisible(boolean visible) {
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivityImpl
    protected int windowHeight() {
        return -1;
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivityImpl
    protected int windowWidth() {
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.BizRootActivityImpl, com.xiaopeng.appstore.common_ui.base.BaseActivity, com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_store);
        this.mIvBack = (ImageView) findViewById(R.id.iv_back_btn);
        this.mFragment = getSupportFragmentManager().findFragmentById(R.id.appStoreFragment);
        loadBtnDrawable();
        this.mIvBack.setOnClickListener(this);
        refreshBackBtn();
        this.mAppStoreProvider = (AppStoreProvider) RouterManager.get().getModule(RouterConstants.AppStoreProviderPaths.APP_STORE_MAIN_PROVIDER);
        handleStart(getIntent());
        AppStoreProvider appStoreProvider = this.mAppStoreProvider;
        if (appStoreProvider != null) {
            appStoreProvider.requestUpdateData();
        }
        if (this.mOnActivityEventListenerList.isEmpty()) {
            return;
        }
        for (BizRootActivity.OnActivityEventListener onActivityEventListener : this.mOnActivityEventListenerList) {
            onActivityEventListener.onTabSelected(0, StoreRootFragment.class);
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivityImpl, com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (ThemeManager.isThemeChanged(newConfig)) {
            Logger.t(TAG).e("AppStoreActivity onConfigurationChanged theme" + ThemeManager.getThemeMode(this), new Object[0]);
            Drawable drawable = ResUtils.getDrawable(R.drawable.x_btn_icon_bg_selector);
            this.mBtnBg = drawable;
            this.mIvBack.setBackground(drawable);
        }
    }

    private void loadBtnDrawable() {
        Logger.t(TAG).d("loadBtnDrawable start");
        this.mBtnBackIcon = ResUtils.getDrawable(R.drawable.x_ic_small_back);
        this.mBtnCloseIcon = ResUtils.getDrawable(R.drawable.x_ic_small_close);
        this.mBtnBg = ResUtils.getDrawable(R.drawable.x_btn_icon_bg_selector);
        Logger.t(TAG).i("loadBtnDrawable: backIcon=%s, closeIcon=%s", this.mBtnBackIcon, this.mBtnCloseIcon);
    }

    private void refreshBackBtn() {
        Printer t = Logger.t(TAG);
        Object[] objArr = new Object[2];
        objArr[0] = Boolean.valueOf(this.mCanNavBack);
        objArr[1] = this.mCanNavBack ? this.mBtnBackIcon : this.mBtnCloseIcon;
        t.i("refreshBackBtn: canNavBack=%s, icon=%s.", objArr);
        if (this.mCanNavBack) {
            this.mIvBack.setImageDrawable(this.mBtnBackIcon);
        } else {
            this.mIvBack.setImageDrawable(this.mBtnCloseIcon);
        }
        this.mIvBack.setBackground(this.mBtnBg);
    }

    private void canNavBack() {
        if (this.mCanNavBack) {
            Fragment fragment = this.mFragment;
            if (fragment instanceof NavHostFragment) {
                NavHostFragment navHostFragment = (NavHostFragment) fragment;
                NavController navController = null;
                try {
                    navController = navHostFragment.getNavController();
                } catch (IllegalStateException unused) {
                    Logger.t(TAG).e("NavController is not available before onCreate(), navHostFragment:" + navHostFragment.hashCode(), new Object[0]);
                }
                if (navController == null) {
                    Logger.t(TAG).w("NavController is null", new Object[0]);
                    return;
                } else if (navController.popBackStack()) {
                    return;
                } else {
                    navController.navigate(R.id.back_to_home);
                    return;
                }
            }
            Logger.t(TAG).e("Back click error, Not NavHostFragment:" + this.mFragment, new Object[0]);
            return;
        }
        closeWindow();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleStart(intent);
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivityImpl, com.xiaopeng.appstore.common_ui.BizRootActivity
    public void setCanNavBack(boolean canNavBack) {
        Logger.t(TAG).d("setCanNavBack: canNavBack=" + canNavBack);
        this.mCanNavBack = canNavBack;
        refreshBackBtn();
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public void handleStart(Intent intent) {
        Fragment primaryNavigationFragment = getSupportFragmentManager().getPrimaryNavigationFragment();
        if (primaryNavigationFragment instanceof StoreRootFragment) {
            Bundle arguments = primaryNavigationFragment.getArguments();
            if (arguments == null) {
                arguments = new Bundle();
                primaryNavigationFragment.setArguments(arguments);
            }
            if (intent != null) {
                Uri data = intent.getData();
                Logger.t(TAG).i("handleStart: Uri=" + data, new Object[0]);
                if (data != null) {
                    String queryParameter = data.getQueryParameter("id");
                    int intExtra = intent.getIntExtra(Constants.OPEN_PAGE_WITH_ACTION_, 0);
                    Logger.t(TAG).d("action = " + intExtra);
                    if (AppUtils.isGoToDetail(data)) {
                        TabBean tabBean = new TabBean();
                        tabBean.setNavigatorType(200);
                        tabBean.setPackageName(queryParameter);
                        tabBean.setAction(intExtra);
                        arguments.putParcelable(Constants.STORE_FRAGMENT_PARAMS, tabBean);
                    }
                }
            }
            StoreRootFragment storeRootFragment = (StoreRootFragment) primaryNavigationFragment;
            if (storeRootFragment.isResumed()) {
                Logger.t(TAG).d("handleStart, StoreRootFragment resumed, handleNavigate");
                storeRootFragment.handleNavigate();
            }
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back_btn) {
            canNavBack();
        }
    }
}
