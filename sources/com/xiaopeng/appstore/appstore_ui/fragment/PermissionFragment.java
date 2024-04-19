package com.xiaopeng.appstore.appstore_ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.appstore_biz.model2.PermissionItemModel;
import com.xiaopeng.appstore.appstore_biz.model2.PermissionModel;
import com.xiaopeng.appstore.appstore_biz.model2.PermissionViewData;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.PermissionDetailAdapter;
import com.xiaopeng.appstore.appstore_ui.viewmodel.PermissionViewModel;
import com.xiaopeng.appstore.common_ui.BaseBizFragment;
import com.xiaopeng.appstore.libcommon.utils.ImageUtils;
import java.util.List;
/* loaded from: classes2.dex */
public class PermissionFragment extends BaseBizFragment {
    public static final String PERMISSION_DATA = "permission_data";
    private View mErrorView;
    private ImageView mIconImageView;
    private PermissionModel mPermissionModel;
    private RecyclerView mRecyclerView;
    private PermissionViewModel mViewModel;

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_permission, container, false);
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mViewModel = (PermissionViewModel) new ViewModelProvider(this).get(PermissionViewModel.class);
        handlerArgs();
    }

    private void handlerArgs() {
        PermissionViewData permissionViewData;
        Bundle arguments = getArguments();
        if (arguments == null || (permissionViewData = (PermissionViewData) arguments.getSerializable(PERMISSION_DATA)) == null) {
            return;
        }
        this.mPermissionModel = this.mViewModel.parseData(permissionViewData);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mIconImageView = (ImageView) view.findViewById(R.id.iv_icon);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        this.mErrorView = view.findViewById(R.id.linear_view);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PermissionModel permissionModel = this.mPermissionModel;
        if (permissionModel != null) {
            ImageUtils.loadAdaptiveIcon(this.mIconImageView, permissionModel.getIconUrl(), null, null);
            List<PermissionItemModel> itemModels = this.mPermissionModel.getItemModels();
            if (itemModels != null && !itemModels.isEmpty()) {
                this.mRecyclerView.setAdapter(new PermissionDetailAdapter(itemModels));
                return;
            }
            showErrorView();
            return;
        }
        showErrorView();
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.mRootActivity.setCanNavBack(true);
    }

    private void showErrorView() {
        this.mErrorView.setVisibility(0);
        this.mRecyclerView.setVisibility(4);
    }
}
