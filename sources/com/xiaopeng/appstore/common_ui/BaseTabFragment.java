package com.xiaopeng.appstore.common_ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;
/* loaded from: classes.dex */
public abstract class BaseTabFragment extends BaseBizFragment {
    public static final String LAZY_LOADING_DATA = "TAB_FRAGMENT_LAZY_LOAD";
    private static final String TAG = "BaseTabFragment";
    private ViewStub mContentStub;
    private View mViewStubContentView;

    protected abstract int getLayoutViewStub();

    protected abstract ViewStub getViewStub(View view);

    protected abstract void initOtherView(View view);

    protected abstract void initViewStubContent(View inflatedViewStub);

    protected abstract boolean isCurrentTab(int index, Class<?> selectedFragmentClz);

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutViewStub(), container, false);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContentStub = getViewStub(view);
        initOtherView(view);
        Bundle arguments = getArguments();
        if (arguments == null || !arguments.containsKey(LAZY_LOADING_DATA) || arguments.getBoolean(LAZY_LOADING_DATA, true)) {
            return;
        }
        Logger.t(TAG).i("inflateViewStub in onViewCreated:" + hashCode(), new Object[0]);
        inflateViewStub();
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.BizRootActivity.OnActivityEventListener
    public void onTabSelected(int index, Class<?> fragmentClz) {
        super.onTabSelected(index, fragmentClz);
        if (isCurrentTab(index, fragmentClz)) {
            Printer t = Logger.t(TAG);
            Object[] objArr = new Object[3];
            objArr[0] = getClass().getSimpleName();
            objArr[1] = Integer.valueOf(index);
            objArr[2] = fragmentClz != null ? fragmentClz.getSimpleName() : null;
            t.d("onTabSelected, current=%s, index=%s, fragment=%s.", objArr);
            inflateViewStub();
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Logger.t(TAG).d("onResume " + getClass().getSimpleName() + " " + hashCode());
        if (isCurrentTab(this.mRootActivity.getIndex(), this.mRootActivity.getCurrentSelected())) {
            Logger.t(TAG).d("inflate in onResume ");
            inflateViewStub();
            return;
        }
        Logger.t(TAG).d("current tab not selected");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean inflateViewStub() {
        ViewStub viewStub;
        if (this.mViewStubContentView != null || (viewStub = this.mContentStub) == null) {
            return false;
        }
        View inflate = viewStub.inflate();
        this.mViewStubContentView = inflate;
        initViewStubContent(inflate);
        return true;
    }
}
