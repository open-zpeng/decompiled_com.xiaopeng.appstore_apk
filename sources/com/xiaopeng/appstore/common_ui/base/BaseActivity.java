package com.xiaopeng.appstore.common_ui.base;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import com.orhanobut.logger.Logger;
import com.xiaopeng.xui.app.XActivity;
/* loaded from: classes.dex */
public abstract class BaseActivity extends XActivity {
    private static final String TAG = "BaseAct";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.t(TAG).d("onCreate: " + getClass().getSimpleName() + "@" + hashCode());
        setEnableDrawableCache(true);
    }

    private void setEnableDrawableCache(boolean enableDrawableCache) {
        Logger.t(TAG).d("setEnableDrawableCache start");
        try {
            Resources.class.getMethod("setDrawableCacheEnabled", Boolean.TYPE).invoke(getResources(), Boolean.valueOf(enableDrawableCache));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger.t(TAG).d("setEnableDrawableCache end");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.t(TAG).d("onNewIntent: " + getClass().getSimpleName() + "@" + hashCode());
    }

    @Override // com.xiaopeng.xui.app.XActivity, android.app.Activity
    public void recreate() {
        super.recreate();
        Logger.t(TAG).d("recreate: " + getClass().getSimpleName() + "@" + hashCode());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        Logger.t(TAG).d("onStart: " + getClass().getSimpleName() + "@" + hashCode());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        Logger.t(TAG).d("onResume: " + getClass().getSimpleName() + "@" + hashCode());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        Logger.t(TAG).d("onPause: " + getClass().getSimpleName() + "@" + hashCode());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        Logger.t(TAG).d("onStop: " + getClass().getSimpleName() + "@" + hashCode());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Logger.t(TAG).d("onDestroy: " + getClass().getSimpleName() + "@" + hashCode());
    }
}
