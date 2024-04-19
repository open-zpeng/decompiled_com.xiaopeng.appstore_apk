package com.xiaopeng.appstore.applist_biz.parser;

import com.xiaopeng.appstore.applist_biz.datamodel.entities.LocalData;
import com.xiaopeng.appstore.applist_biz.model.FixedAppItem;
import com.xiaopeng.appstore.applist_biz.model.LoadingAppItem;
import com.xiaopeng.appstore.applist_biz.model.LocalAppData;
import com.xiaopeng.appstore.applist_biz.model.NormalAppItem;
import com.xiaopeng.appstore.bizcommon.entities.AppInfo;
import com.xiaopeng.appstore.bizcommon.entities.WebAppInfo;
/* loaded from: classes2.dex */
public class AppListParser {
    public static LocalAppData parseToLocalAppData(LocalData dbData) {
        LocalAppData localAppData = new LocalAppData();
        localAppData.key = dbData.key;
        localAppData.index = dbData.listIndex;
        localAppData.packageName = dbData.packageName;
        return localAppData;
    }

    public static LocalData parseToDbData(LocalAppData appData) {
        LocalData localData = new LocalData();
        localData.key = appData.key;
        localData.listIndex = appData.index;
        localData.packageName = appData.packageName;
        return localData;
    }

    public static NormalAppItem parseAppInfo(AppInfo appInfo) {
        NormalAppItem normalAppItem = new NormalAppItem(appInfo.componentName, appInfo.user);
        normalAppItem.contentDescription = appInfo.contentDescription;
        if (appInfo.iconBitmap != null) {
            normalAppItem.iconBitmap = appInfo.iconBitmap;
        }
        normalAppItem.intent = appInfo.intent;
        normalAppItem.title = appInfo.title;
        normalAppItem.packageName = appInfo.componentName.getPackageName();
        return normalAppItem;
    }

    public static FixedAppItem parseWebApp(WebAppInfo webAppInfo) {
        FixedAppItem fixedAppItem = new FixedAppItem(webAppInfo.getPackageName(), webAppInfo.getIconUrl(), webAppInfo.getAppName());
        fixedAppItem.setDownloadUrl(webAppInfo.getDownloadUrl());
        fixedAppItem.setMd5(webAppInfo.getMd5());
        fixedAppItem.setConfigUrl(webAppInfo.getConfigUrl());
        fixedAppItem.setConfigMd5(webAppInfo.getConfigMd5());
        fixedAppItem.setSuspended(webAppInfo.isSuspended());
        return fixedAppItem;
    }

    public static LoadingAppItem parseLoadingApp(String packageName, String iconUrl, CharSequence title) {
        return new LoadingAppItem(packageName, iconUrl, title);
    }
}
