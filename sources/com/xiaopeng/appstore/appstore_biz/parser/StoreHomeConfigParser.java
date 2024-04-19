package com.xiaopeng.appstore.appstore_biz.parser;

import android.util.ArrayMap;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities.HomeBean;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities.ItemBean;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities.ItemDetailBean;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities.PackageBean;
import com.xiaopeng.appstore.appstore_biz.model.AppModel;
import com.xiaopeng.appstore.appstore_biz.model.HomeModel;
import com.xiaopeng.appstore.appstore_biz.model.PackageModel;
import com.xiaopeng.appstore.libcommon.utils.NumberUtils;
import java.util.ArrayList;
import java.util.Map;
/* loaded from: classes2.dex */
public class StoreHomeConfigParser {
    public static AppModel parseAppBean(ItemBean appBean) {
        return AppModel.from(appBean);
    }

    public static HomeModel parseHomeBean(HomeBean homeBean) {
        Map<String, ItemDetailBean> itemDetailList;
        Map<String, ItemDetailBean> itemDetailList2;
        ItemDetailBean itemDetailBean;
        if (homeBean == null) {
            return null;
        }
        HomeModel homeModel = new HomeModel();
        Map<String, ItemDetailBean> itemDetailList3 = homeBean.getItemDetailList();
        if (itemDetailList3 != null && !itemDetailList3.isEmpty()) {
            ArrayMap arrayMap = new ArrayMap(itemDetailList3.size());
            for (Map.Entry<String, ItemDetailBean> entry : itemDetailList3.entrySet()) {
                AppModel parseAppBean = parseAppBean(entry.getValue());
                arrayMap.put(parseAppBean.getKey(), parseAppBean);
            }
            homeModel.setAllAppMap(arrayMap);
        }
        if (homeBean.getBannerList() != null && !homeBean.getBannerList().isEmpty()) {
            ArrayList arrayList = new ArrayList(homeBean.getBannerList().size());
            for (HomeBean.BannerBean bannerBean : homeBean.getBannerList()) {
                HomeModel.BannerModel bannerModel = new HomeModel.BannerModel();
                bannerModel.setType(bannerBean.getType());
                if (bannerBean.getData() != null && !bannerBean.getData().isEmpty()) {
                    ArrayList arrayList2 = new ArrayList(bannerBean.getData().size());
                    for (HomeBean.BannerDataBean bannerDataBean : bannerBean.getData()) {
                        HomeModel.BannerData bannerData = new HomeModel.BannerData();
                        int stringToInt = NumberUtils.stringToInt(bannerDataBean.getType());
                        bannerData.setType(stringToInt);
                        String id = bannerDataBean.getId();
                        bannerData.setId(id);
                        bannerData.setImageUrl(bannerDataBean.getImageUrl());
                        if (stringToInt == 2 && (itemDetailBean = itemDetailList3.get(id)) != null) {
                            bannerData.setAppModel(parseAppBean(itemDetailBean));
                        }
                        arrayList2.add(bannerData);
                    }
                    bannerModel.setData(arrayList2);
                }
                arrayList.add(bannerModel);
            }
            homeModel.setBannerList(arrayList);
        }
        if (homeBean.getTopPackage() != null && homeBean.getTopPackage().getItemList() != null && !homeBean.getTopPackage().getItemList().isEmpty() && (itemDetailList2 = homeBean.getItemDetailList()) != null && !itemDetailList2.isEmpty()) {
            PackageModel packageModel = new PackageModel();
            packageModel.setTitle(homeBean.getTopPackage().getTitle());
            ArrayList arrayList3 = new ArrayList(homeBean.getTopPackage().getItemList().size());
            for (String str : homeBean.getTopPackage().getItemList()) {
                ItemDetailBean itemDetailBean2 = itemDetailList2.get(str);
                if (itemDetailBean2 != null) {
                    arrayList3.add(parseAppBean(itemDetailBean2));
                }
            }
            packageModel.setItemList(arrayList3);
            homeModel.setTopPackage(packageModel);
        }
        if (homeBean.getPackageList() != null && !homeBean.getPackageList().isEmpty() && (itemDetailList = homeBean.getItemDetailList()) != null && !itemDetailList.isEmpty()) {
            ArrayList arrayList4 = new ArrayList(homeBean.getPackageList().size());
            for (PackageBean packageBean : homeBean.getPackageList()) {
                PackageModel packageModel2 = new PackageModel();
                packageModel2.setTitle(packageBean.getTitle());
                ArrayList arrayList5 = new ArrayList(packageBean.getItemList().size());
                for (String str2 : packageBean.getItemList()) {
                    ItemDetailBean itemDetailBean3 = itemDetailList.get(str2);
                    if (itemDetailBean3 != null) {
                        arrayList5.add(parseAppBean(itemDetailBean3));
                    }
                }
                packageModel2.setItemList(arrayList5);
                arrayList4.add(packageModel2);
            }
            homeModel.setPackageList(arrayList4);
        }
        return homeModel;
    }
}
