package com.xiaopeng.appstore.ui_miniprog.miniprog.viewmodel;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.appstore.applet_biz.datamodel.MiniProgRepository;
import com.xiaopeng.appstore.common_ui.common.Resource;
import com.xiaopeng.appstore.common_ui.common.adapter.AdapterData;
import com.xiaopeng.appstore.ui_miniprog.miniprog.parser.MiniProgramDataParser;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class MiniProgramViewModel extends ViewModel {
    private static final int GROUP_FAVOR = 1;
    private static final int GROUP_NORMAL = 2;
    private MiniProgRepository mRepository;
    private MediatorLiveData<Resource<List<AdapterData<?>>>> mListMutableLiveData = new MediatorLiveData<>();
    private List<AdapterData<?>> mAdapterDataList = new ArrayList();

    public MiniProgramViewModel() {
        MiniProgRepository miniProgRepository = new MiniProgRepository();
        this.mRepository = miniProgRepository;
        miniProgRepository.setLoadListener(new MiniProgRepository.AppletLoadListener() { // from class: com.xiaopeng.appstore.ui_miniprog.miniprog.viewmodel.-$$Lambda$MiniProgramViewModel$J3uORrwqGXSRY9eGoJBbTFvfqic
            @Override // com.xiaopeng.appstore.applet_biz.datamodel.MiniProgRepository.AppletLoadListener
            public final void onAppletLoaded(List list) {
                MiniProgramViewModel.this.lambda$new$0$MiniProgramViewModel(list);
            }
        });
    }

    public /* synthetic */ void lambda$new$0$MiniProgramViewModel(List list) {
        if (list != null && !list.isEmpty()) {
            List<AdapterData<?>> parseList = MiniProgramDataParser.parseList(list);
            this.mAdapterDataList = parseList;
            this.mListMutableLiveData.postValue(Resource.success(parseList));
            return;
        }
        this.mListMutableLiveData.postValue(Resource.error("", this.mAdapterDataList));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        this.mRepository.release();
    }

    public MutableLiveData<Resource<List<AdapterData<?>>>> getListLiveData() {
        return this.mListMutableLiveData;
    }

    public void loadData() {
        this.mListMutableLiveData.postValue(Resource.loading(this.mAdapterDataList));
        this.mAdapterDataList.clear();
        this.mRepository.loadData();
    }

    public void launch(String id, String name) {
        MiniProgRepository miniProgRepository = this.mRepository;
        if (miniProgRepository != null) {
            miniProgRepository.launch(id, name);
        }
    }
}
