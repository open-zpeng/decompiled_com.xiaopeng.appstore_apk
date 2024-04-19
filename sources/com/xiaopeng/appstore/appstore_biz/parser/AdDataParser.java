package com.xiaopeng.appstore.appstore_biz.parser;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AdvLayout;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.DependentAppBean;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.DbItemDependentCrossRef;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.ItemBean;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.ItemDependent;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.ItemWithDependents;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PackageBean;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PackageItem;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PackageWithItems;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PageBean;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PagePackage;
import com.xiaopeng.appstore.appstore_biz.model2.AppBaseModel;
import com.xiaopeng.appstore.appstore_biz.model2.DependentAppModel;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvAppModel;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvBannerModel;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvContainerModel;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvListModel;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.libcommon.utils.NumberUtils;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class AdDataParser {
    private static final int LAYOUT_TYPE_BANNER = 11001;
    private static final int LAYOUT_TYPE_GRID = 12002;
    private static final int LAYOUT_TYPE_GRID_NARROW = 12006;
    private static final int LAYOUT_TYPE_GRID_WIDE = 12003;
    private static final int LAYOUT_TYPE_HORIZONTAL = 12004;
    private static final int LAYOUT_TYPE_HORIZONTAL_CARD = 12001;
    private static final int LAYOUT_TYPE_IMAGE_LIST = 11002;
    private static final int LAYOUT_TYPE_NORMAL_CARD = 12005;
    private static final int LAYOUT_TYPE_PAGE = 10000;
    private static final int LAYOUT_TYPE_SCENE_HEAD = 11003;
    private static final String TAG = "AdDataParser";

    public static int parseGridLayoutToViewType(int gridLayoutType) {
        if (gridLayoutType != LAYOUT_TYPE_GRID_WIDE) {
            return gridLayoutType != LAYOUT_TYPE_GRID_NARROW ? 7 : 13;
        }
        return 9;
    }

    public static int parseLayoutTypeToViewType(int layoutType) {
        if (layoutType != 11001) {
            if (layoutType != LAYOUT_TYPE_HORIZONTAL_CARD) {
                if (layoutType != LAYOUT_TYPE_GRID) {
                    switch (layoutType) {
                        case LAYOUT_TYPE_HORIZONTAL /* 12004 */:
                            return 10;
                        case LAYOUT_TYPE_NORMAL_CARD /* 12005 */:
                            return 11;
                        case LAYOUT_TYPE_GRID_NARROW /* 12006 */:
                            return 12;
                        default:
                            return 8;
                    }
                }
                return 6;
            }
            return 5;
        }
        return 2;
    }

    private static AdvContainerModel<? super AdvModel> getFooterModel() {
        AdvContainerModel<? super AdvModel> advContainerModel = new AdvContainerModel<>();
        advContainerModel.setType(15);
        return advContainerModel;
    }

    public static int parseLayout(String layoutJson) {
        if (TextUtils.isEmpty(layoutJson)) {
            return 0;
        }
        try {
            return NumberUtils.stringToInt(((AdvLayout) new Gson().fromJson(layoutJson, (Class<Object>) AdvLayout.class)).getType());
        } catch (JsonSyntaxException unused) {
            Logger.t(TAG).w("layout JsonSyntaxException ", new Object[0]);
            return 0;
        }
    }

    public static List<DependentAppModel> parseDependentApps(List<DependentAppBean> beans) {
        if (beans == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(beans.size());
        for (DependentAppBean dependentAppBean : beans) {
            DependentAppModel parseDependentApp = parseDependentApp(dependentAppBean);
            if (parseDependentApp != null) {
                arrayList.add(parseDependentApp);
            }
        }
        return arrayList;
    }

    private static DependentAppModel parseDependentApp(DependentAppBean bean) {
        if (bean == null) {
            return null;
        }
        DependentAppModel dependentAppModel = new DependentAppModel();
        dependentAppModel.setAppId(bean.getAppId());
        dependentAppModel.setPackageName(bean.getPackageName());
        dependentAppModel.setType(bean.getType());
        return dependentAppModel;
    }

    public static List<AdvContainerModel<? extends AdvModel>> parsePackageList(List<PackageWithItems> packageWithItems) {
        ArrayList arrayList = new ArrayList(packageWithItems.size());
        for (PackageWithItems packageWithItems2 : packageWithItems) {
            PagePackage pagePackage = packageWithItems2.pagePackage;
            if (pagePackage != null && packageWithItems2.items != null) {
                if (11001 == pagePackage.layoutType) {
                    parseBanner(pagePackage, packageWithItems2.items, arrayList);
                } else {
                    parseNestedList(pagePackage, packageWithItems2.items, arrayList);
                }
            }
        }
        if (!arrayList.isEmpty()) {
            arrayList.add(getFooterModel());
        }
        return arrayList;
    }

    public static List<AdvContainerModel<? extends AdvModel>> parsePackageListV2(List<PackageWithItems> packageWithItems) {
        ArrayList arrayList = new ArrayList(packageWithItems.size());
        for (PackageWithItems packageWithItems2 : packageWithItems) {
            PagePackage pagePackage = packageWithItems2.pagePackage;
            if (pagePackage != null && packageWithItems2.items != null) {
                int i = pagePackage.layoutType;
                if (11001 == i) {
                    parseBanner(pagePackage, packageWithItems2.items, arrayList);
                } else if (LAYOUT_TYPE_GRID == i || LAYOUT_TYPE_GRID_WIDE == i || LAYOUT_TYPE_GRID_NARROW == i) {
                    parseGrid(pagePackage, packageWithItems2.items, arrayList);
                } else {
                    parseNestedList(pagePackage, packageWithItems2.items, arrayList);
                }
            }
        }
        if (!arrayList.isEmpty()) {
            arrayList.add(getFooterModel());
        }
        return arrayList;
    }

    public static void parseItem(ItemWithDependents itemWithDependents, AdvAppModel outAdvModel) {
        PackageItem packageItem;
        if (itemWithDependents == null || (packageItem = itemWithDependents.item) == null) {
            return;
        }
        outAdvModel.setDesc(packageItem.desc);
        outAdvModel.setTitle(packageItem.title);
        if (packageItem.adImage != null) {
            outAdvModel.setImageList(new ArrayList(packageItem.adImage));
        }
        AppBaseModel appBaseModel = new AppBaseModel();
        appBaseModel.setAppId(packageItem.itemId);
        appBaseModel.setAppName(packageItem.title);
        appBaseModel.setPackageName(packageItem.packageName);
        appBaseModel.setBriefDesc(packageItem.desc);
        appBaseModel.setDownloadUrl(packageItem.downloadUrl);
        appBaseModel.setMd5(packageItem.md5);
        appBaseModel.setConfigMd5(packageItem.configMd5);
        appBaseModel.setConfigUrl(packageItem.configUrl);
        if (packageItem.icon != null) {
            appBaseModel.setIconUrl(packageItem.icon);
        }
        appBaseModel.setVersionCode(NumberUtils.stringToLong(packageItem.versionCode));
        double stringToDouble = NumberUtils.stringToDouble(packageItem.size);
        if (stringToDouble > 0.0d) {
            appBaseModel.setSize(LogicUtils.getAppSizeString(stringToDouble * 1024.0d));
        }
        appBaseModel.setDependentApps(parseDependents(itemWithDependents.dependents));
        appBaseModel.setStatus(NumberUtils.stringToInt(packageItem.status, -1));
        outAdvModel.setAppBaseInfo(appBaseModel);
    }

    public static void parseBanner(PagePackage pagePackage, List<ItemWithDependents> items, List<AdvContainerModel<? extends AdvModel>> outResult) {
        AdvBannerModel advBannerModel = new AdvBannerModel(pagePackage.packageId);
        ArrayList arrayList = new ArrayList(items.size());
        for (ItemWithDependents itemWithDependents : items) {
            AdvAppModel advAppModel = new AdvAppModel(itemWithDependents.item.itemId);
            parseItem(itemWithDependents, advAppModel);
            arrayList.add(advAppModel);
        }
        advBannerModel.setTitle(pagePackage.title);
        advBannerModel.setList(arrayList);
        advBannerModel.setBgList(pagePackage.pageImageList);
        AdvContainerModel<? extends AdvModel> advContainerModel = new AdvContainerModel<>();
        advContainerModel.setType(2);
        advContainerModel.setData(advBannerModel);
        outResult.add(advContainerModel);
    }

    public static void parseGrid(PagePackage pagePackage, List<ItemWithDependents> items, List<AdvContainerModel<? extends AdvModel>> outResult) {
        AdvContainerModel<? extends AdvModel> advContainerModel = new AdvContainerModel<>();
        advContainerModel.setType(14);
        AdvModel advModel = new AdvModel(pagePackage.packageId);
        advModel.setTitle(pagePackage.title);
        advContainerModel.setData(advModel);
        outResult.add(advContainerModel);
        int i = pagePackage.layoutType;
        for (ItemWithDependents itemWithDependents : items) {
            AdvAppModel advAppModel = new AdvAppModel(itemWithDependents.item.itemId);
            parseItem(itemWithDependents, advAppModel);
            AdvContainerModel<? extends AdvModel> advContainerModel2 = new AdvContainerModel<>();
            advContainerModel2.setType(parseGridLayoutToViewType(i));
            advContainerModel2.setData(advAppModel);
            outResult.add(advContainerModel2);
        }
    }

    public static void parseNestedList(PagePackage pagePackage, List<ItemWithDependents> items, List<AdvContainerModel<? extends AdvModel>> outResult) {
        AdvListModel advListModel = new AdvListModel(pagePackage.packageId);
        ArrayList arrayList = new ArrayList(items.size());
        for (ItemWithDependents itemWithDependents : items) {
            AdvAppModel advAppModel = new AdvAppModel(itemWithDependents.item.itemId);
            parseItem(itemWithDependents, advAppModel);
            arrayList.add(advAppModel);
        }
        int parseLayoutTypeToViewType = parseLayoutTypeToViewType(pagePackage.layoutType);
        advListModel.setViewType(parseLayoutTypeToViewType);
        advListModel.setTitle(pagePackage.title);
        advListModel.setList(arrayList);
        AdvContainerModel<? extends AdvModel> advContainerModel = new AdvContainerModel<>();
        advContainerModel.setType(parseLayoutTypeToViewType);
        advContainerModel.setData(advListModel);
        outResult.add(advContainerModel);
    }

    public static List<DependentAppModel> parseDependents(List<ItemDependent> dependents) {
        if (dependents == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(dependents.size());
        for (ItemDependent itemDependent : dependents) {
            DependentAppModel parseDependent = parseDependent(itemDependent);
            if (parseDependent != null) {
                arrayList.add(parseDependent);
            }
        }
        return arrayList;
    }

    private static DependentAppModel parseDependent(ItemDependent bean) {
        if (bean == null) {
            return null;
        }
        DependentAppModel dependentAppModel = new DependentAppModel();
        dependentAppModel.setAppId(bean.dependentId);
        dependentAppModel.setPackageName(bean.packageName);
        dependentAppModel.setType(bean.dependentType);
        return dependentAppModel;
    }

    public static void parsePageBeanToDb(PageBean pageBean, List<PagePackage> packageList, List<PackageItem> itemList, List<ItemDependent> dependents, List<DbItemDependentCrossRef> itemDependentCrossRefs) {
        if (pageBean.data == null || pageBean.data.list == null) {
            return;
        }
        for (PackageBean packageBean : pageBean.data.list) {
            if (packageBean != null && packageBean.data != null && !TextUtils.isEmpty(packageBean.data.id)) {
                PagePackage pagePackage = new PagePackage(packageBean.data.id);
                pagePackage.contentType = packageBean.contentType;
                int parseLayout = parseLayout(packageBean.layout);
                if (parseLayout == 11001) {
                    pagePackage.pageImageList = pageBean.data.adImg;
                }
                pagePackage.layoutType = parseLayout;
                pagePackage.title = packageBean.data.title;
                packageList.add(pagePackage);
                if (packageBean.data.list != null) {
                    for (ItemBean itemBean : packageBean.data.list) {
                        if (itemBean != null && itemBean.data != null && !TextUtils.isEmpty(itemBean.data.id)) {
                            PackageItem parseItemToDb = parseItemToDb(packageBean.data.id, itemBean);
                            itemList.add(parseItemToDb);
                            if (itemBean.data.dependentApps != null) {
                                for (DependentAppBean dependentAppBean : itemBean.data.dependentApps) {
                                    if (dependentAppBean != null && !TextUtils.isEmpty(dependentAppBean.getAppId())) {
                                        itemDependentCrossRefs.add(new DbItemDependentCrossRef(parseItemToDb.itemId, dependentAppBean.getAppId()));
                                        dependents.add(parseAppDependentToDb(dependentAppBean));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static PackageItem parseItemToDb(String packageId, ItemBean itemBean) {
        ItemBean.Data data = itemBean.data;
        PackageItem packageItem = new PackageItem(packageId, itemBean.data.id);
        packageItem.adImage = data.adImage;
        packageItem.configUrl = parseDownloadUrl(data.configUrl);
        packageItem.desc = data.desc;
        packageItem.downloadUrl = parseDownloadUrl(data.downloadUrl);
        packageItem.md5 = data.md5;
        packageItem.configMd5 = data.configMd5;
        packageItem.packageName = data.packageName;
        packageItem.size = data.size;
        packageItem.icon = data.appIcons != null ? data.appIcons.smallIcon : null;
        packageItem.status = data.status;
        packageItem.title = data.title;
        packageItem.versionCode = data.versionCode;
        return packageItem;
    }

    private static ItemDependent parseAppDependentToDb(DependentAppBean dependentApp) {
        ItemDependent itemDependent = new ItemDependent(dependentApp.getAppId());
        itemDependent.packageName = dependentApp.getPackageName();
        itemDependent.dependentType = dependentApp.getType();
        return itemDependent;
    }

    private static String parseDownloadUrl(String url) {
        return TextUtils.isEmpty(url) ? url : LogicUtils.replaceHttp(url);
    }
}
