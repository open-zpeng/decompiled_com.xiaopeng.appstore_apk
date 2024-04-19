package com.xiaopeng.appstore.ui_miniprog.miniprog.parser;

import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.applet_biz.datamodel.entities.MiniProgramData;
import com.xiaopeng.appstore.applet_biz.datamodel.entities.MiniProgramGroup;
import com.xiaopeng.appstore.applet_biz.parser.Parser;
import com.xiaopeng.appstore.applet_ui.R;
import com.xiaopeng.appstore.common_ui.common.adapter.AdapterData;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class MiniProgramDataParser {
    private static final String TAG = "MiniProgParser";

    /* JADX WARN: Type inference failed for: r1v11, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v8, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.xiaopeng.appstore.applet_biz.model.MiniProgramModel, T] */
    public static List<AdapterData<?>> parseList(List<MiniProgramGroup> list) {
        ArrayList arrayList = new ArrayList();
        for (MiniProgramGroup miniProgramGroup : list) {
            List<MiniProgramData> data = miniProgramGroup.getData();
            if (data != null) {
                AdapterData adapterData = new AdapterData();
                adapterData.type = 2;
                adapterData.data = miniProgramGroup.getGroupName();
                arrayList.add(adapterData);
                for (MiniProgramData miniProgramData : data) {
                    ?? parseData = Parser.parseData(miniProgramData);
                    AdapterData adapterData2 = new AdapterData();
                    adapterData2.type = 1;
                    adapterData2.data = parseData;
                    arrayList.add(adapterData2);
                }
            } else {
                Logger.t(TAG).d("mini data list is null , group name is " + miniProgramGroup.getGroupName());
            }
        }
        if (!arrayList.isEmpty()) {
            AdapterData adapterData3 = new AdapterData();
            adapterData3.type = 3;
            adapterData3.data = Utils.getApp().getResources().getString(R.string.alipay_mini_program_policy);
            arrayList.add(adapterData3);
        }
        Logger.t(TAG).d("Parse mini list = " + arrayList);
        return arrayList;
    }
}
