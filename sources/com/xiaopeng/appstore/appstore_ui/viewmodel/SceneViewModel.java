package com.xiaopeng.appstore.appstore_ui.viewmodel;

import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvContainerModel;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel;
import com.xiaopeng.appstore.common_ui.common.Resource;
import com.xiaopeng.appstore.common_ui.common.base.SingleLiveEvent;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class SceneViewModel extends ViewModel {
    private static final String ID_PREFIX = "scene_";
    private final View.OnClickListener mOnBackClick = new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.viewmodel.SceneViewModel.1
        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            SceneViewModel.this.mOnBackEvent.call();
        }
    };
    private final SingleLiveEvent<String> mOnBackEvent = new SingleLiveEvent<>();
    private final List<AdvContainerModel<?>> mAdapterDataList = new ArrayList();
    private final MediatorLiveData<Resource<List<AdvContainerModel<?>>>> mViewDataListLiveData = new MediatorLiveData<>();

    public View.OnClickListener getOnBackClick() {
        return this.mOnBackClick;
    }

    public SingleLiveEvent<String> getOnBackEvent() {
        return this.mOnBackEvent;
    }

    public LiveData<Resource<List<AdvContainerModel<? extends AdvModel>>>> getAdapterDataList() {
        return this.mViewDataListLiveData;
    }
}
