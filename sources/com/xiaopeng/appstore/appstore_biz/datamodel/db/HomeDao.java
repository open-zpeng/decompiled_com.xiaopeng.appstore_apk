package com.xiaopeng.appstore.appstore_biz.datamodel.db;

import androidx.lifecycle.LiveData;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.DbItemDependentCrossRef;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.ItemDependent;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PackageItem;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PackageWithItems;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PagePackage;
import java.util.List;
/* loaded from: classes2.dex */
public abstract class HomeDao {
    public abstract void clearAppItems();

    public abstract void clearDependents();

    public abstract void clearItemDependentCrossRefs();

    public abstract void clearPackages();

    public abstract LiveData<List<PackageWithItems>> getPackages();

    public abstract void insertItem(List<PackageItem> list);

    public abstract void insertItemDependentCrossRefs(List<DbItemDependentCrossRef> list);

    public abstract void insertItemDependents(List<ItemDependent> list);

    public abstract void insertPackage(List<PagePackage> packageList);

    public void clearThenInsert(List<PagePackage> packageList, List<PackageItem> itemList, List<ItemDependent> dependents, List<DbItemDependentCrossRef> itemDependentCrossRefs) {
        if (packageList != null) {
            clearPackages();
            clearAppItems();
            clearDependents();
            clearItemDependentCrossRefs();
            insertPackage(packageList);
            insertItem(itemList);
            insertItemDependents(dependents);
            insertItemDependentCrossRefs(itemDependentCrossRefs);
        }
    }
}
