package com.xiaopeng.appstore.applet_biz.parser;

import android.text.TextUtils;
import com.xiaopeng.appstore.applet_biz.datamodel.entities.MiniProgramData;
import com.xiaopeng.appstore.applet_biz.model.MiniProgramModel;
/* loaded from: classes2.dex */
public class Parser {
    private static final String URL_FORMAT = "alipays://platformapi/startapp?appId=%s&chinfo=xiaopeng";

    private Parser() {
    }

    public static MiniProgramModel parseData(MiniProgramData data) {
        if (data == null) {
            return null;
        }
        MiniProgramModel miniProgramModel = new MiniProgramModel();
        miniProgramModel.setName(data.getName());
        miniProgramModel.setId(data.getId());
        miniProgramModel.setJumpUrl(String.format(URL_FORMAT, data.getId()) + (TextUtils.isEmpty(data.getParams()) ? "" : data.getParams()));
        miniProgramModel.setIconUrl(data.getIconName());
        return miniProgramModel;
    }
}
