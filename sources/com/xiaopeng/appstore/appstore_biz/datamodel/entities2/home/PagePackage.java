package com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home;

import java.util.List;
/* loaded from: classes2.dex */
public class PagePackage {
    public String contentType;
    public int layoutType;
    public String packageId;
    public List<String> pageImageList;
    public String title;

    public PagePackage(String packageId) {
        this.packageId = packageId;
    }
}
