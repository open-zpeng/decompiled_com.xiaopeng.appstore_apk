package com.xiaopeng.appstore.appstore_biz.model2.adv;

import java.util.List;
/* loaded from: classes2.dex */
public class AdvBannerModel extends AdvListModel<AdvModel> {
    private List<String> mBgList;

    public AdvBannerModel(String id) {
        super(id);
    }

    public List<String> getBgList() {
        return this.mBgList;
    }

    public void setBgList(List<String> bgList) {
        this.mBgList = bgList;
    }
}
