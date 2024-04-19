package com.xiaopeng.appstore.appserver_common.module;

import android.content.pm.PackageInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.datamodel.api.XpApiClient;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailData;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppDetailRequest;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppListRequest;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.AppRequestContainer;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.UpdateRespContainer;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.ItemBean;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PackageBean;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PageBean;
import com.xiaopeng.appstore.bizcommon.entities.common.XpApiResponse;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.libcommon.utils.NumberUtils;
import com.xiaopeng.appstore.protobuf.AppDataContentProto;
import com.xiaopeng.appstore.protobuf.AppDataProto;
import com.xiaopeng.appstore.protobuf.AppDetailDataProto;
import com.xiaopeng.appstore.protobuf.AppIconDataProto;
import com.xiaopeng.appstore.protobuf.AppItemProto;
import com.xiaopeng.appstore.protobuf.AppStoreHomeDataProto;
import com.xiaopeng.appstore.protobuf.AppStoreHomeDataWrapperProto;
import com.xiaopeng.appstore.protobuf.AppStoreHomeListProto;
import com.xiaopeng.appstore.protobuf.AppStoreHomeResponseProto;
import com.xiaopeng.appstore.protobuf.AppUpdateDataProto;
import com.xiaopeng.appstore.protobuf.AppUpdateResponseProto;
import com.xiaopeng.appstore.protobuf.DeveloperDataProto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class AppStore {
    private static final String TAG = "AppStore";

    public static AppDetailDataProto parseAppDetail(AppDetailData bean) {
        return AppDetailDataProto.newBuilder().setAppName(bean.getAppName() == null ? "" : bean.getAppName()).setAppId(bean.getAppId() == null ? "" : bean.getAppId()).setPackageName(bean.getPackageName() == null ? "" : bean.getPackageName()).setBriefDesc(bean.getBriefDesc() == null ? "" : bean.getBriefDesc()).setDetailDesc(bean.getDetailDesc() == null ? "" : bean.getDetailDesc()).setVersionCode(NumberUtils.stringToLong(bean.getVersionCode() == null ? "0" : bean.getVersionCode())).setVersionName(bean.getVersionName() == null ? "" : bean.getVersionName()).setVersionDesc(bean.getVersionDesc() == null ? "" : bean.getVersionDesc()).setUpdateTime(NumberUtils.stringToLong(bean.getUpdateTime() == null ? "0" : bean.getUpdateTime())).addAllDetailImg(bean.getDetailImg() == null ? new ArrayList<>() : bean.getDetailImg()).setDownloadUrl(bean.getDownloadUrl() == null ? "" : bean.getDownloadUrl()).setConfigUrl(bean.getConfigUrl() == null ? "" : bean.getConfigUrl()).setConfigMd5(bean.getConfigMd5() == null ? "" : bean.getConfigMd5()).setMd5(bean.getMd5() == null ? "" : bean.getMd5()).setSource(bean.getSource() == null ? "" : bean.getSource()).setIsHidden(bean.isHidden() ? 1 : 0).setIsSilentInstall(bean.isSilentInstall() ? 1 : 0).setStatus(NumberUtils.stringToInt(bean.getStatus() != null ? bean.getStatus() : "0")).setSize(LogicUtils.getAppSizeString(NumberUtils.stringToDouble(bean.getSize(), 0.0d) * 1024.0d)).setIsForceUpdate(bean.isForceUpdate() ? 1 : 0).setAppIcons(AppIconDataProto.newBuilder().setSmallIcon((bean.getIconData() == null || bean.getIconData().getSmallIcon() == null) ? "" : bean.getIconData().getSmallIcon()).setLargeIcon((bean.getIconData() == null || bean.getIconData().getLargeIcon() == null) ? "" : bean.getIconData().getLargeIcon()).build()).setDeveloper(DeveloperDataProto.newBuilder().setDeveloperName((bean.getDeveloper() == null || bean.getDeveloper().getName() == null) ? "" : bean.getDeveloper().getName()).build()).setApkPermissions(bean.getPermissionData() == null ? "" : bean.getPermissionData()).setPrivacyPolicy(bean.getPrivatePolicyUrl() != null ? bean.getPrivatePolicyUrl() : "").build();
    }

    public static AppUpdateResponseProto parseUpdateData(UpdateRespContainer bean) {
        if (bean == null || bean.getAppList() == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (AppDetailData appDetailData : bean.getAppList()) {
            AppDetailDataProto.Builder newBuilder = AppDetailDataProto.newBuilder();
            if (appDetailData.getAppId() != null) {
                newBuilder.setAppId(appDetailData.getAppId());
            }
            if (appDetailData.getAppName() != null) {
                newBuilder.setAppName(appDetailData.getAppName());
            }
            if (appDetailData.getPackageName() != null) {
                newBuilder.setPackageName(appDetailData.getPackageName());
            }
            if (appDetailData.getBriefDesc() != null) {
                newBuilder.setBriefDesc(appDetailData.getBriefDesc());
            }
            if (appDetailData.getDetailDesc() != null) {
                newBuilder.setDetailDesc(appDetailData.getDetailDesc());
            }
            if (appDetailData.getVersionName() != null) {
                newBuilder.setVersionName(appDetailData.getVersionName());
            }
            if (appDetailData.getVersionDesc() != null) {
                newBuilder.setVersionDesc(appDetailData.getVersionDesc());
            }
            if (appDetailData.getDetailImg() != null) {
                newBuilder.addAllDetailImg(appDetailData.getDetailImg());
            }
            if (appDetailData.getDownloadUrl() != null) {
                newBuilder.setDownloadUrl(appDetailData.getDownloadUrl());
            }
            if (appDetailData.getConfigUrl() != null) {
                newBuilder.setConfigUrl(appDetailData.getConfigUrl());
            }
            if (appDetailData.getMd5() != null) {
                newBuilder.setMd5(appDetailData.getMd5());
            }
            if (appDetailData.getSource() != null) {
                newBuilder.setSource(appDetailData.getSource());
            }
            if (appDetailData.getPermissionData() != null) {
                newBuilder.setApkPermissions(appDetailData.getPermissionData());
            }
            if (appDetailData.getPrivatePolicyUrl() != null) {
                newBuilder.setPrivacyPolicy(appDetailData.getPrivatePolicyUrl());
            }
            if (appDetailData.getStatus() != null) {
                newBuilder.setStatus(NumberUtils.stringToInt(appDetailData.getStatus()));
            }
            if (appDetailData.getSize() != null) {
                newBuilder.setSize(LogicUtils.getAppSizeString(NumberUtils.stringToDouble(appDetailData.getSize(), 0.0d) * 1024.0d));
            }
            if (appDetailData.getIconData() != null) {
                newBuilder.setAppIcons(AppIconDataProto.newBuilder().setLargeIcon(appDetailData.getIconData().getLargeIcon() != null ? appDetailData.getIconData().getLargeIcon() : "").setSmallIcon(appDetailData.getIconData().getSmallIcon() != null ? appDetailData.getIconData().getSmallIcon() : "").build());
            }
            newBuilder.setVersionCode(NumberUtils.stringToLong(appDetailData.getVersionCode()));
            newBuilder.setUpdateTime(NumberUtils.stringToLong(appDetailData.getUpdateTime()));
            if (appDetailData.getDeveloper() != null) {
                newBuilder.setDeveloper(DeveloperDataProto.newBuilder().setDeveloperName(appDetailData.getDeveloper().getName() != null ? appDetailData.getDeveloper().getName() : "").build());
            }
            newBuilder.setIsHidden(appDetailData.isHidden() ? 1 : 0);
            newBuilder.setIsSilentInstall(appDetailData.isSilentInstall() ? 1 : 0);
            newBuilder.setIsForceUpdate(appDetailData.isForceUpdate() ? 1 : 0);
            arrayList.add(newBuilder.build());
        }
        return AppUpdateResponseProto.newBuilder().setData(AppUpdateDataProto.newBuilder().addAllApps(arrayList).build()).build();
    }

    public static AppUpdateResponseProto parseAppListToUpdate(List<AppDetailData> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (AppDetailData appDetailData : list) {
            arrayList.add(parseAppDetail(appDetailData));
        }
        return AppUpdateResponseProto.newBuilder().setData(AppUpdateDataProto.newBuilder().addAllApps(arrayList).build()).build();
    }

    public static AppStoreHomeResponseProto parseStoreHome(PageBean bean) {
        AppStoreHomeDataProto build;
        if (bean == null) {
            return null;
        }
        AppStoreHomeResponseProto.Builder newBuilder = AppStoreHomeResponseProto.newBuilder();
        AppStoreHomeDataWrapperProto.Builder contentType = AppStoreHomeDataWrapperProto.newBuilder().setLayout(bean.layout == null ? "" : bean.layout).setContentType(NumberUtils.stringToInt(bean.contentType == null ? "0" : bean.contentType));
        if (bean.data == null) {
            build = AppStoreHomeDataProto.newBuilder().build();
        } else {
            build = AppStoreHomeDataProto.newBuilder().setId(bean.data.id == null ? "" : bean.data.id).setDesc(bean.data.desc != null ? bean.data.desc : "").addAllList(transAppStoreHomeListProto(bean.data.list)).build();
        }
        return newBuilder.setData(contentType.setData(build).build()).build();
    }

    private static List<AppStoreHomeListProto> transAppStoreHomeListProto(List<PackageBean> beans) {
        AppDataProto build;
        ArrayList arrayList = new ArrayList();
        if (beans == null) {
            return arrayList;
        }
        for (PackageBean packageBean : beans) {
            AppStoreHomeListProto.Builder layout = AppStoreHomeListProto.newBuilder().setContentType(NumberUtils.stringToInt(packageBean.contentType == null ? "0" : packageBean.contentType)).setLayout(packageBean.layout == null ? "" : packageBean.layout);
            if (packageBean.data == null) {
                build = AppDataProto.newBuilder().build();
            } else {
                build = AppDataProto.newBuilder().setId(packageBean.data.id == null ? "" : packageBean.data.id).setTitle(packageBean.data.title != null ? packageBean.data.title : "").addAllList(transAppDataContentProto(packageBean.data.list)).build();
            }
            arrayList.add(layout.setData(build).build());
        }
        return arrayList;
    }

    private static List<AppDataContentProto> transAppDataContentProto(List<ItemBean> beans) {
        AppItemProto build;
        ArrayList arrayList = new ArrayList();
        if (beans == null) {
            return arrayList;
        }
        for (ItemBean itemBean : beans) {
            double stringToDouble = itemBean.data != null ? NumberUtils.stringToDouble(itemBean.data.size, 0.0d) : 0.0d;
            AppDataContentProto.Builder contentType = AppDataContentProto.newBuilder().setContentType(NumberUtils.stringToInt(itemBean.contentType == null ? "0" : itemBean.contentType));
            if (itemBean.data == null) {
                build = AppItemProto.newBuilder().build();
            } else {
                String str = "";
                AppItemProto.Builder versionCode = AppItemProto.newBuilder().setId(itemBean.data.id == null ? "" : itemBean.data.id).setDesc(itemBean.data.desc == null ? "" : itemBean.data.desc).setSize(LogicUtils.getAppSizeString(stringToDouble * 1024.0d)).setMd5(itemBean.data.md5 == null ? "" : itemBean.data.md5).setStatus(NumberUtils.stringToInt(itemBean.data.status == null ? "0" : itemBean.data.status)).setTitle(itemBean.data.title == null ? "" : itemBean.data.title).setPackageName(itemBean.data.packageName == null ? "" : itemBean.data.packageName).setDownloadUrl(itemBean.data.downloadUrl == null ? "" : itemBean.data.downloadUrl).setConfigUrl(itemBean.data.configUrl == null ? "" : itemBean.data.configUrl).setVersionCode(NumberUtils.stringToLong(itemBean.data.versionCode != null ? itemBean.data.versionCode : "0"));
                AppIconDataProto.Builder smallIcon = AppIconDataProto.newBuilder().setSmallIcon((itemBean.data.appIcons == null || itemBean.data.appIcons.smallIcon == null) ? "" : itemBean.data.appIcons.smallIcon);
                if (itemBean.data.appIcons != null && itemBean.data.appIcons.largeIcon != null) {
                    str = itemBean.data.appIcons.largeIcon;
                }
                build = versionCode.setAppIcons(smallIcon.setSmallIcon(str).build()).addAllAdImg(itemBean.data.adImage == null ? new ArrayList<>() : itemBean.data.adImage).build();
            }
            arrayList.add(contentType.setData(build).build());
        }
        return arrayList;
    }

    public static AppDetailData requestAppDetail(String packageName) {
        Response<XpApiResponse<AppDetailData>> response;
        if (TextUtils.isEmpty(packageName)) {
            Log.w(TAG, "getAppDetail error: " + packageName);
            return null;
        }
        AppRequestContainer appRequestContainer = new AppRequestContainer();
        appRequestContainer.setPackageName(packageName);
        AppDetailRequest appDetailRequest = new AppDetailRequest();
        appDetailRequest.setParam(appRequestContainer);
        try {
            response = XpApiClient.getAppService().getDetail(appDetailRequest).execute();
        } catch (IOException e) {
            e.printStackTrace();
            response = null;
        }
        if (response == null) {
            Log.w(TAG, "response is null");
            return null;
        }
        if (response.isSuccessful()) {
            XpApiResponse<AppDetailData> body = response.body();
            if (body != null && body.isSuccessful()) {
                return body.data;
            }
            Logger.t(TAG).i("getAppDetail, pn:" + packageName + ", XpApiResponse fail:" + body, new Object[0]);
        } else {
            Logger.t(TAG).i("getAppDetail, pn:" + packageName + ", Response fail:" + response, new Object[0]);
        }
        return null;
    }

    public static UpdateRespContainer requestUpdateSync(Collection<PackageInfo> packageList) {
        Response<XpApiResponse<UpdateRespContainer>> response;
        List<AppRequestContainer> parsePackageInfoList = parsePackageInfoList(packageList);
        if (parsePackageInfoList == null || parsePackageInfoList.isEmpty()) {
            Logger.t(TAG).w("requestUpdateSync: requestParam is empty " + parsePackageInfoList, new Object[0]);
            return null;
        }
        AppListRequest appListRequest = new AppListRequest();
        appListRequest.setParams(parsePackageInfoList);
        Logger.t(TAG).d("requestUpdateSync, request:" + appListRequest);
        try {
            response = XpApiClient.getAppService().getUpdateCall(appListRequest).execute();
        } catch (IOException e) {
            Logger.t(TAG).w("requestUpdateSync ex:" + e, new Object[0]);
            response = null;
        }
        if (response == null || !response.isSuccessful()) {
            Logger.t(TAG).w("requestUpdateSync, response is null or fail:" + response, new Object[0]);
            return null;
        }
        XpApiResponse<UpdateRespContainer> body = response.body();
        if (body != null && body.isSuccessful()) {
            return body.data;
        }
        Logger.t(TAG).w("requestUpdateSync, XpApiResponse fail:" + body, new Object[0]);
        return null;
    }

    public static PageBean requestStoreHome() {
        Response<XpApiResponse<PageBean>> response;
        try {
            response = XpApiClient.getAdvService().getPageDataCall().execute();
        } catch (IOException e) {
            e.printStackTrace();
            response = null;
        }
        if (response == null || !response.isSuccessful()) {
            Logger.t(TAG).w("response is null or fail:" + response, new Object[0]);
            return null;
        }
        XpApiResponse<PageBean> body = response.body();
        if (body != null && body.isSuccessful()) {
            return body.data;
        }
        Logger.t(TAG).w("requestStoreHome XpApiResponse fail: " + body, new Object[0]);
        return null;
    }

    private static List<AppRequestContainer> parsePackageInfoList(Collection<PackageInfo> packageList) {
        long j;
        if (packageList == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (PackageInfo packageInfo : packageList) {
            AppRequestContainer appRequestContainer = new AppRequestContainer();
            String str = packageInfo.packageName;
            if (Build.VERSION.SDK_INT >= 28) {
                j = packageInfo.getLongVersionCode();
            } else {
                j = packageInfo.versionCode;
            }
            appRequestContainer.setPackageName(str);
            appRequestContainer.setVersionCode(j);
            arrayList.add(appRequestContainer);
        }
        return arrayList;
    }
}
