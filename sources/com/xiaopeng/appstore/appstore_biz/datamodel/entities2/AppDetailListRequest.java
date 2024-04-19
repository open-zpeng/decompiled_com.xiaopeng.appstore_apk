package com.xiaopeng.appstore.appstore_biz.datamodel.entities2;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import java.util.ArrayList;
/* loaded from: classes2.dex */
public class AppDetailListRequest {
    @SerializedName(IpcConfig.DeviceCommunicationConfig.KEY_APP_MESSAGE_PARAMS)
    private ArrayList<AppRequestContainer> mParams;

    public void setParam(ArrayList<AppRequestContainer> param) {
        this.mParams = param;
    }
}
