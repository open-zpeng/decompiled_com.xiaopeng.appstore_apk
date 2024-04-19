package com.xiaopeng.appstore.installer;

import android.content.res.Configuration;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaopeng.appstore.R;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public abstract class DialogActivityAbs extends AppCompatActivity {
    protected XButton mDialogBtn1;
    protected XButton mDialogBtn2;
    protected XTextView mDialogMsg;
    protected XTextView mDialogTitle;
    protected ImageView mIconView;
    protected View mLoadingView;

    /* JADX INFO: Access modifiers changed from: protected */
    public void initView() {
        this.mDialogTitle = (XTextView) findViewById(R.id.dialog_title);
        this.mDialogMsg = (XTextView) findViewById(R.id.dialog_message);
        this.mLoadingView = findViewById(R.id.loading);
        this.mDialogBtn1 = (XButton) findViewById(R.id.dialog_button1);
        this.mDialogBtn2 = (XButton) findViewById(R.id.dialog_button2);
        this.mIconView = (ImageView) findViewById(R.id.iv_icon);
    }

    protected void showLoading() {
        this.mDialogTitle.setVisibility(8);
        this.mDialogMsg.setVisibility(8);
        this.mLoadingView.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void hideLoading() {
        this.mLoadingView.setVisibility(8);
        this.mDialogTitle.setVisibility(0);
        this.mDialogMsg.setVisibility(0);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (ThemeManager.isThemeChanged(newConfig)) {
            ThemeManager.setWindowBackgroundDrawable(newConfig, getWindow(), getDrawable(R.drawable.x_bg_dialog));
        }
    }
}
