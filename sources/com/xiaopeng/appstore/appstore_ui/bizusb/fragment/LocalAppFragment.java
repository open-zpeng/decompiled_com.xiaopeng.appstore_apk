package com.xiaopeng.appstore.appstore_ui.bizusb.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.bizusb.logic.USBStateManager;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.bizusb.adapter.LocalAppAdapter;
import com.xiaopeng.appstore.appstore_ui.bizusb.viewmodel.LocalAppViewModel;
import com.xiaopeng.appstore.common_ui.BaseBizFragment;
import java.util.List;
/* loaded from: classes2.dex */
public class LocalAppFragment extends BaseBizFragment {
    private static final int COUNTS_PER_ROW = 6;
    private static final String TAG = "LocalAppFrag";
    private LocalAppAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private USBStateManager mUSBStateManager;
    private LocalAppViewModel mViewModel;

    public void setUsbStateManager(USBStateManager usbStateManager) {
        this.mUSBStateManager = usbStateManager;
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local_app, container, false);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 6));
        LocalAppAdapter localAppAdapter = new LocalAppAdapter();
        this.mAdapter = localAppAdapter;
        this.mRecyclerView.setAdapter(localAppAdapter);
        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.bizusb.fragment.-$$Lambda$LocalAppFragment$hnLoSm0v3NVDBexBHqT9pL1TMEc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LocalAppFragment.this.lambda$onViewCreated$0$LocalAppFragment(view2);
            }
        });
    }

    public /* synthetic */ void lambda$onViewCreated$0$LocalAppFragment(View view) {
        FragmentTransaction beginTransaction = getParentFragmentManager().beginTransaction();
        beginTransaction.hide(this);
        beginTransaction.commit();
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mViewModel = (LocalAppViewModel) new ViewModelProvider(this).get(LocalAppViewModel.class);
        subscribeViewModel();
        USBStateManager uSBStateManager = this.mUSBStateManager;
        if (uSBStateManager != null) {
            this.mViewModel.loadData(uSBStateManager);
        } else {
            Logger.t(TAG).w("USBStateManger not set", new Object[0]);
        }
    }

    private void subscribeViewModel() {
        this.mViewModel.getListLiveData().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.appstore_ui.bizusb.fragment.-$$Lambda$LocalAppFragment$YiYv23lGGcZO7tUTna78QdE1Sd4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LocalAppFragment.this.lambda$subscribeViewModel$1$LocalAppFragment((List) obj);
            }
        });
    }

    public /* synthetic */ void lambda$subscribeViewModel$1$LocalAppFragment(List list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        this.mAdapter.addAll(list);
        this.mAdapter.notifyDataSetChanged();
    }
}
