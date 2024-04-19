package com.xiaopeng.appstore.common_ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.utils.DeviceUtils;
import com.xiaopeng.appstore.common_ui.BizRootActivity;
import com.xiaopeng.appstore.common_ui.base.BaseActivity;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.xpcommon.CarUtils;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.app.delegate.XActivityBind;
import java.util.ArrayList;
import java.util.List;
@XActivityBind(1)
/* loaded from: classes.dex */
public abstract class BizRootActivityImpl extends BaseActivity implements BizRootActivity {
    private static final long RESET_WINDOW_DELAY = 2000;
    private static final String TAG = "BizRootAct";
    protected List<BizRootActivity.OnActivityEventListener> mOnActivityEventListenerList = new ArrayList();
    private boolean mIsClosing = false;
    private final Handler mMainHandler = new Handler();
    private final Runnable mResetWindowAction = new Runnable() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$BizRootActivityImpl$y-GkWDvr6xyDvStKiS5wi8kCQp8
        @Override // java.lang.Runnable
        public final void run() {
            BizRootActivityImpl.this.lambda$new$0$BizRootActivityImpl();
        }
    };

    protected abstract void onWindowReset();

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public void setCanNavBack(boolean canNavBack) {
    }

    public /* synthetic */ void lambda$new$0$BizRootActivityImpl() {
        Logger.t(TAG).i("reset window", new Object[0]);
        onWindowReset();
        if (this.mOnActivityEventListenerList.isEmpty()) {
            return;
        }
        Logger.t(TAG).d("dispatch reset window, " + this.mOnActivityEventListenerList);
        for (BizRootActivity.OnActivityEventListener onActivityEventListener : this.mOnActivityEventListenerList) {
            onActivityEventListener.onWindowReset();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.base.BaseActivity, com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int windowWidth = windowWidth();
        int windowHeight = windowHeight();
        getWindowAttributes().setGravity(windowGravity());
        getWindowAttributes().setWidth(windowWidth).setHeight(windowHeight);
        getActivityDismiss().setDismissType(0);
    }

    protected int windowWidth() {
        return ResUtils.getDimenPixel(R.dimen.app_list_window_width_major);
    }

    protected int windowHeight() {
        return ResUtils.getDimenPixel(R.dimen.app_list_window_height_major);
    }

    protected int windowGravity() {
        if (CarUtils.isE38() || CarUtils.isE28A() || CarUtils.isF30() || DeviceUtils.isD21Screen()) {
            return 17;
        }
        return DeviceUtils.isE28Screen() ? 49 : 0;
    }

    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (ThemeManager.isThemeChanged(newConfig)) {
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(16842836, typedValue, true);
            Drawable drawable = ResUtils.getDrawable(typedValue.resourceId);
            Logger.t(TAG).d(getClass().getSimpleName() + " onConfigurationChanged theme:" + typedValue.resourceId + ", bg:" + drawable);
            ThemeManager.setWindowBackgroundDrawable(newConfig, getWindow(), drawable);
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public void addOnActivityListener(BizRootActivity.OnActivityEventListener listener) {
        this.mOnActivityEventListenerList.add(listener);
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public void removeActivityListener(BizRootActivity.OnActivityEventListener listener) {
        this.mOnActivityEventListenerList.remove(listener);
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public boolean isClose() {
        return this.mIsClosing;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.base.BaseActivity, com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        this.mMainHandler.removeCallbacks(this.mResetWindowAction);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.base.BaseActivity, com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        Logger.t(TAG).d("onResume");
        this.mIsClosing = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.base.BaseActivity, com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        Logger.t(TAG).d("onStop");
        this.mIsClosing = false;
        if (!this.mOnActivityEventListenerList.isEmpty()) {
            for (BizRootActivity.OnActivityEventListener onActivityEventListener : this.mOnActivityEventListenerList) {
                onActivityEventListener.onWindowClose();
            }
        }
        this.mMainHandler.removeCallbacks(this.mResetWindowAction);
        this.mMainHandler.postDelayed(this.mResetWindowAction, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.base.BaseActivity, com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Logger.t(TAG).d("onDestroy");
        this.mMainHandler.removeCallbacks(this.mResetWindowAction);
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public void moveTaskToBack() {
        Logger.t(TAG).d("moveTaskToBack, isClosing=" + this.mIsClosing);
        if (this.mIsClosing) {
            return;
        }
        this.mIsClosing = true;
        Logger.t(TAG).d("moveTaskToBack=" + moveTaskToBack(true));
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public void closeWindow() {
        Logger.t(TAG).d("closeWindow");
        finish();
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity
    public void backToHome() {
        Logger.t(TAG).d("backToHome, isClosing=" + this.mIsClosing);
        if (this.mIsClosing) {
            return;
        }
        this.mIsClosing = true;
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        startActivity(intent);
    }
}
