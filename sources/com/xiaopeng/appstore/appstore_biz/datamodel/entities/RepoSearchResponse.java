package com.xiaopeng.appstore.appstore_biz.datamodel.entities;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class RepoSearchResponse {
    @SerializedName("items")
    private List<Repo> items;
    private Integer nextPage;
    @SerializedName("total_count")
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Repo> getItems() {
        return this.items;
    }

    public void setItems(List<Repo> items) {
        this.items = items;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public Integer getNextPage() {
        return this.nextPage;
    }

    public List<Integer> getRepoIds() {
        ArrayList arrayList = new ArrayList();
        for (Repo repo : this.items) {
            arrayList.add(Integer.valueOf(repo.id));
        }
        return arrayList;
    }
}
