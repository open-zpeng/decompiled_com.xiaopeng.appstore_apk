package com.xiaopeng.appstore.appstore_biz.parser;

import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailData;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.interpreload.InterAppPreloadDetailData;
import com.xiaopeng.appstore.appstore_biz.model2.AppBaseModel;
import com.xiaopeng.appstore.appstore_biz.model2.AppDetailModel;
import com.xiaopeng.appstore.appstore_biz.model2.PermissionData;
import com.xiaopeng.appstore.appstore_biz.model2.PermissionViewData;
import com.xiaopeng.appstore.appstore_biz.model2.PrivatePolicyModel;
import com.xiaopeng.appstore.bizcommon.entities.WebAppInfo;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.libcommon.utils.GsonUtil;
import com.xiaopeng.appstore.libcommon.utils.NumberUtils;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class ItemDetailParser {
    public static final String SIZE_FORMAT = "%.1f MB";

    public static AppDetailModel parseAppDetail(AppDetailData data) {
        if (data != null) {
            AppDetailModel appDetailModel = new AppDetailModel();
            appDetailModel.setAppId(data.getAppId());
            appDetailModel.setAppName(data.getAppName());
            appDetailModel.setPackageName(data.getPackageName());
            if (data.getIconData() != null) {
                appDetailModel.setIconUrl(data.getIconData().getLargeIcon());
            }
            appDetailModel.setBriefDesc(data.getBriefDesc());
            appDetailModel.setDetailDesc(data.getDetailDesc());
            if (data.getDetailImg() != null) {
                appDetailModel.setDetailImg(new ArrayList(data.getDetailImg()));
            }
            appDetailModel.setDownloadUrl(data.getDownloadUrl());
            appDetailModel.setMd5(data.getMd5());
            appDetailModel.setConfigUrl(data.getConfigUrl());
            appDetailModel.setConfigMd5(data.getConfigMd5());
            appDetailModel.setVersionDate(NumberUtils.stringToLong(data.getUpdateTime()));
            appDetailModel.setVersionCode(NumberUtils.stringToLong(data.getVersionCode()));
            appDetailModel.setVersionName(data.getVersionName());
            appDetailModel.setVersionDesc(data.getVersionDesc());
            appDetailModel.setAppSource(data.getSource());
            double stringToDouble = NumberUtils.stringToDouble(data.getSize());
            if (stringToDouble > 0.0d) {
                appDetailModel.setSize(LogicUtils.getAppSizeString(stringToDouble * 1024.0d));
            }
            if (data.getDeveloper() != null) {
                appDetailModel.setDeveloperName(data.getDeveloper().getName());
            }
            appDetailModel.setStatus(NumberUtils.stringToInt(data.getStatus(), -1));
            appDetailModel.setDependentApps(AdDataParser.parseDependentApps(data.getDependentApps()));
            PermissionViewData permissionViewData = new PermissionViewData();
            permissionViewData.setIconUrl(appDetailModel.getIconUrl());
            PermissionData permissionData = (PermissionData) GsonUtil.fromJson(data.getPermissionData(), (Class<Object>) PermissionData.class);
            if (permissionData != null) {
                permissionViewData.setImportantPermission(permissionData.getImportantList());
                permissionViewData.setGeneralPermission(permissionData.getGeneralList());
            }
            appDetailModel.setPermissionModel(permissionViewData);
            PrivatePolicyModel privatePolicyModel = new PrivatePolicyModel();
            privatePolicyModel.setIconUrl(appDetailModel.getIconUrl());
            privatePolicyModel.setWebUrl(data.getPrivatePolicyUrl());
            appDetailModel.setPolicyModel(privatePolicyModel);
            return appDetailModel;
        }
        return null;
    }

    public static AppBaseModel parseAppBase(AppDetailData appData) {
        if (appData == null) {
            return null;
        }
        AppBaseModel appBaseModel = new AppBaseModel();
        appBaseModel.setAppId(appData.getAppId());
        appBaseModel.setAppName(appData.getAppName());
        appBaseModel.setPackageName(appData.getPackageName());
        appBaseModel.setBriefDesc(appData.getBriefDesc());
        appBaseModel.setDownloadUrl(appData.getDownloadUrl());
        appBaseModel.setMd5(appData.getMd5());
        appBaseModel.setConfigUrl(appData.getConfigUrl());
        appBaseModel.setConfigMd5(appData.getConfigMd5());
        if (appData.getIconData() != null) {
            appBaseModel.setIconUrl(appData.getIconData().getLargeIcon());
        }
        appBaseModel.setVersionCode(NumberUtils.stringToLong(appData.getVersionCode()));
        appBaseModel.setVersionName(appData.getVersionName());
        appBaseModel.setVersionDate(NumberUtils.stringToLong(appData.getUpdateTime()));
        double stringToDouble = NumberUtils.stringToDouble(appData.getSize());
        if (stringToDouble > 0.0d) {
            appBaseModel.setSize(LogicUtils.getAppSizeString(stringToDouble * 1024.0d));
        }
        appBaseModel.setStatus(NumberUtils.stringToInt(appData.getStatus(), -1));
        appBaseModel.setVersionDesc(appData.getVersionDesc());
        return appBaseModel;
    }

    public static List<WebAppInfo> parseToWebAppList(List<AppDetailData> remoteList) {
        if (remoteList == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(remoteList.size());
        for (AppDetailData appDetailData : remoteList) {
            WebAppInfo webAppInfo = new WebAppInfo();
            webAppInfo.setAppId(appDetailData.getAppId());
            webAppInfo.setAppName(appDetailData.getAppName());
            webAppInfo.setBriefDesc(appDetailData.getBriefDesc());
            webAppInfo.setDownloadUrl(appDetailData.getDownloadUrl());
            webAppInfo.setConfigUrl(appDetailData.getConfigUrl());
            if (appDetailData.getIconData() != null) {
                webAppInfo.setIconUrl(appDetailData.getIconData().getLargeIcon());
            }
            webAppInfo.setPackageName(appDetailData.getPackageName());
            double stringToDouble = NumberUtils.stringToDouble(appDetailData.getSize());
            if (stringToDouble > 0.0d) {
                webAppInfo.setSize(LogicUtils.getAppSizeString(stringToDouble * 1024.0d));
            }
            webAppInfo.setStatus(NumberUtils.stringToInt(appDetailData.getStatus(), -1));
            webAppInfo.setVersionDate(NumberUtils.stringToLong(appDetailData.getUpdateTime()));
            webAppInfo.setVersionCode(NumberUtils.stringToLong(appDetailData.getVersionCode()));
            webAppInfo.setVersionName(appDetailData.getVersionName());
            webAppInfo.setMd5(appDetailData.getMd5());
            webAppInfo.setConfigMd5(appDetailData.getConfigMd5());
            arrayList.add(webAppInfo);
        }
        return arrayList;
    }

    public static AppDetailData parseInterToNormalDetailData(InterAppPreloadDetailData data) {
        AppDetailData appDetailData = new AppDetailData();
        if (data != null) {
            appDetailData.setAppId(data.getId());
            appDetailData.setAppName(data.getAppName());
            appDetailData.setDownloadUrl(data.getDownloadUrl());
            appDetailData.setConfigUrl(data.getConfigUrl());
            appDetailData.setAppIconData(data.getIconData());
            appDetailData.setPackageName(data.getPackageName());
            appDetailData.setSize(data.getSize());
            appDetailData.setStatus(data.getStatus());
            appDetailData.setVersionCode(data.getVersionCode());
            appDetailData.setBriefDesc(data.getDesc());
        }
        return appDetailData;
    }
}
