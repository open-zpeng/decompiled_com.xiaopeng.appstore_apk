package com.xiaopeng.appstore.ui.applist;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import com.xiaopeng.appstore.R;
import com.xiaopeng.appstore.common_ui.BizRootActivity;
import com.xiaopeng.appstore.common_ui.BizRootActivityImpl;
import java.util.function.Consumer;
/* loaded from: classes2.dex */
public class AppListActivity extends BizRootActivityImpl {
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
    public void handleStart(Intent intent) {
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
    protected int windowGravity() {
        return 81;
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivityImpl
    protected int windowHeight() {
        return 972;
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivityImpl
    protected int windowWidth() {
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.BizRootActivityImpl, com.xiaopeng.appstore.common_ui.base.BaseActivity, com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        if (Build.VERSION.SDK_INT >= 32) {
            getWindow().addFlags(4);
            getWindow().setBackgroundBlurRadius(70);
            setupWindowBlurListener();
            return;
        }
        updateWindowForBlurs(false);
    }

    private void setupWindowBlurListener() {
        final Consumer consumer = new Consumer() { // from class: com.xiaopeng.appstore.ui.applist.-$$Lambda$AppListActivity$A6YA70JTmfD2felR2R-5kHLTgUo
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppListActivity.this.updateWindowForBlurs(((Boolean) obj).booleanValue());
            }
        };
        getWindow().getDecorView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.xiaopeng.appstore.ui.applist.AppListActivity.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View v) {
                AppListActivity.this.getWindowManager().addCrossWindowBlurEnabledListener(consumer);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View v) {
                AppListActivity.this.getWindowManager().removeCrossWindowBlurEnabledListener(consumer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWindowForBlurs(boolean enableBlur) {
        getWindow().setBackgroundDrawable(getDrawable(enableBlur ? R.drawable.bg_app_panel_blur : R.drawable.x_bg_app_panel));
    }
}
