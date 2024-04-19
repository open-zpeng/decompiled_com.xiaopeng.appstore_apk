package com.xiaopeng.appstore.appserver_common.parser;

import com.xiaopeng.appstore.applet_biz.datamodel.entities.MiniProgramData;
import com.xiaopeng.appstore.applet_biz.datamodel.entities.MiniProgramGroup;
import com.xiaopeng.appstore.appstore_biz.bizusb.datamodel.entities.LocalApkInfo;
import com.xiaopeng.appstore.protobuf.AppIconDataProto;
import com.xiaopeng.appstore.protobuf.AppItemProto;
import com.xiaopeng.appstore.protobuf.AppletGroupListProto;
import com.xiaopeng.appstore.protobuf.AppletGroupProto;
import com.xiaopeng.appstore.protobuf.UsbAppListProto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
/* loaded from: classes2.dex */
public class Parser {
    private static final String TAG = "Parser";

    private Parser() {
    }

    public static AppItemProto parseAppletItem(MiniProgramData itemData) {
        if (itemData == null) {
            return null;
        }
        return AppItemProto.newBuilder().setType(1001).setName(itemData.getName()).setId(itemData.getId()).setAppIcons(AppIconDataProto.newBuilder().setSmallIcon(itemData.getIconName()).setLargeIcon(itemData.getIconName()).build()).build();
    }

    public static AppletGroupProto parseAppletGroup(MiniProgramGroup group) {
        ArrayList arrayList = null;
        if (group == null) {
            return null;
        }
        List<MiniProgramData> data = group.getData();
        if (data != null) {
            arrayList = new ArrayList(data.size());
            for (MiniProgramData miniProgramData : data) {
                if (miniProgramData != null) {
                    arrayList.add(parseAppletItem(miniProgramData));
                }
            }
        }
        AppletGroupProto.Builder group2 = AppletGroupProto.newBuilder().setGroup(group.getGroupName());
        if (arrayList != null) {
            group2.addAllAppletList(arrayList);
        }
        return group2.build();
    }

    public static AppletGroupListProto parseAppletGroupList(List<MiniProgramGroup> groupList) {
        if (groupList == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(groupList.size());
        for (MiniProgramGroup miniProgramGroup : groupList) {
            if (miniProgramGroup != null) {
                arrayList.add(parseAppletGroup(miniProgramGroup));
            }
        }
        return AppletGroupListProto.newBuilder().addAllAppletGroupList(arrayList).build();
    }

    public static UsbAppListProto parseUsbAppList(List<LocalApkInfo> localList, Function<String, Boolean> checkInstalled) {
        if (localList == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(localList.size());
        for (LocalApkInfo localApkInfo : localList) {
            arrayList.add(parseUsbAppItem(localApkInfo, ((Boolean) Optional.ofNullable(checkInstalled.apply(localApkInfo.getPackageName())).orElse(false)).booleanValue() ? 1 : 101));
        }
        return UsbAppListProto.newBuilder().addAllUsbAppList(arrayList).build();
    }

    public static AppItemProto parseUsbAppItem(LocalApkInfo apkInfo) {
        return parseUsbAppItem(apkInfo, 101);
    }

    public static AppItemProto parseUsbAppItem(LocalApkInfo apkInfo, int appType) {
        if (apkInfo == null) {
            return null;
        }
        return AppItemProto.newBuilder().setType(appType).setName(apkInfo.getAppName()).setAppIcons(AppIconDataProto.newBuilder().setSmallIcon(apkInfo.getIconUrl()).setLargeIcon(apkInfo.getIconUrl()).build()).setPackageName(apkInfo.getPackageName()).build();
    }
}
