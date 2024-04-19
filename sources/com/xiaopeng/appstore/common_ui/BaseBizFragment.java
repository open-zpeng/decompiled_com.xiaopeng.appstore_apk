package com.xiaopeng.appstore.common_ui;

import android.os.Bundle;
import com.xiaopeng.appstore.common_ui.BizRootActivity;
import com.xiaopeng.appstore.common_ui.base.BaseFragment;
/* loaded from: classes.dex */
public abstract class BaseBizFragment extends BaseFragment implements BizRootActivity.OnActivityEventListener {
    private static final String TAG = "BaseBizFrag";
    protected BizRootActivity mRootActivity;

    public boolean ignoreSameArguments(Bundle arguments) {
        return false;
    }

    @Override // com.xiaopeng.appstore.common_ui.BizRootActivity.OnActivityEventListener
    public void onPageSelected(int index) {
    }

    public void onTabSelected(int index, Class<?> fragmentClz) {
    }

    public void onWindowClose() {
    }

    public void onWindowReset() {
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof BizRootActivity) {
            BizRootActivity bizRootActivity = (BizRootActivity) getActivity();
            this.mRootActivity = bizRootActivity;
            bizRootActivity.addOnActivityListener(this);
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        BizRootActivity bizRootActivity = this.mRootActivity;
        if (bizRootActivity != null) {
            bizRootActivity.removeActivityListener(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public BizRootActivity getRootActivity() {
        return this.mRootActivity;
    }
}
