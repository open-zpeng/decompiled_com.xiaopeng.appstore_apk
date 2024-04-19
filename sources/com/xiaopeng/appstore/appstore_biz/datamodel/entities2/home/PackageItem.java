package com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home;

import java.util.List;
/* loaded from: classes2.dex */
public class PackageItem {
    public List<String> adImage;
    public String configMd5;
    public String configUrl;
    public String desc;
    public String downloadUrl;
    public String icon;
    public long id = 0;
    public String itemId;
    public String md5;
    public String packageId;
    public String packageName;
    public String size;
    public String status;
    public String title;
    public String versionCode;

    public PackageItem(String packageId, String itemId) {
        this.packageId = packageId;
        this.itemId = itemId;
    }
}
