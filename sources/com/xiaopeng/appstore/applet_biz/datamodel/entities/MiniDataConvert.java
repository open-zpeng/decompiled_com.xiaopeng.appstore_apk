package com.xiaopeng.appstore.applet_biz.datamodel.entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/* loaded from: classes2.dex */
public class MiniDataConvert {
    public static List<MiniProgramData> storedStringToMyObjects(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return Collections.emptyList();
        }
        return (List) gson.fromJson(data, new TypeToken<List<MiniProgramData>>() { // from class: com.xiaopeng.appstore.applet_biz.datamodel.entities.MiniDataConvert.1
        }.getType());
    }

    public static String myObjectsToStoredString(List<MiniProgramData> myObjects) {
        return new Gson().toJson(myObjects);
    }

    public static List<MiniProgramGroup> managerObjectToBean(List<com.xiaopeng.xuimanager.xapp.MiniProgramGroup> miniProgramGroups) {
        ArrayList arrayList = new ArrayList();
        if (miniProgramGroups != null && miniProgramGroups.size() > 0) {
            for (com.xiaopeng.xuimanager.xapp.MiniProgramGroup miniProgramGroup : miniProgramGroups) {
                if (miniProgramGroup != null) {
                    MiniProgramGroup miniProgramGroup2 = new MiniProgramGroup();
                    miniProgramGroup2.setGroupName(miniProgramGroup.getGroupName());
                    List<com.xiaopeng.xuimanager.xapp.MiniProgramData> data = miniProgramGroup.getData();
                    ArrayList arrayList2 = new ArrayList();
                    if (data != null && data.size() > 0) {
                        for (com.xiaopeng.xuimanager.xapp.MiniProgramData miniProgramData : data) {
                            if (miniProgramData != null) {
                                MiniProgramData miniProgramData2 = new MiniProgramData();
                                miniProgramData2.setId(miniProgramData.getMiniAppId());
                                miniProgramData2.setIconName(miniProgramData.getIconName());
                                miniProgramData2.setName(miniProgramData.getName());
                                miniProgramData2.setParams(miniProgramData.getParams());
                                arrayList2.add(miniProgramData2);
                            }
                        }
                    }
                    miniProgramGroup2.setData(arrayList2);
                    arrayList.add(miniProgramGroup2);
                }
            }
        }
        return arrayList;
    }
}
