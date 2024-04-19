package com.xiaopeng.appstore.common_ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.orhanobut.logger.Logger;
/* loaded from: classes.dex */
public class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.t(TAG).d("onCreate " + getClass().getSimpleName() + " " + hashCode());
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.t(TAG).d("onCreateView " + getClass().getSimpleName() + " " + hashCode());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.t(TAG).d("onViewCreated " + getClass().getSimpleName() + " " + hashCode());
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.t(TAG).d("onActivityCreated " + getClass().getSimpleName() + " " + hashCode());
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
    }

    @Override // androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.t(TAG).d("setUserVisibleHint " + getClass().getSimpleName() + " " + hashCode());
    }
}
