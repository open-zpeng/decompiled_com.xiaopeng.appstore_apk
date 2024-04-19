package com.xiaopeng.appstore.appstore_biz.model2.adv;

import java.util.List;
import java.util.Objects;
/* loaded from: classes2.dex */
public class AdvListModel<T> extends AdvModel {
    private List<T> mList;

    public AdvListModel(String id) {
        super(id);
    }

    public List<T> getList() {
        return this.mList;
    }

    public void setList(List<T> list) {
        this.mList = list;
    }

    @Override // com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && getClass() == o.getClass() && super.equals(o)) {
            return Objects.equals(this.mList, ((AdvListModel) o).mList);
        }
        return false;
    }

    @Override // com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), this.mList);
    }

    @Override // com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel
    public String toString() {
        StringBuilder sb = new StringBuilder("AdvListModel{");
        sb.append("mList=").append(this.mList);
        sb.append('}');
        return sb.toString();
    }
}
