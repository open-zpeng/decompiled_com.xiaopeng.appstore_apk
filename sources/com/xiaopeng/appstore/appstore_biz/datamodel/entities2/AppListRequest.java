package com.xiaopeng.appstore.appstore_biz.datamodel.entities2;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import java.io.Serializable;
import java.util.List;
/* loaded from: classes2.dex */
public class AppListRequest implements Serializable {
    @SerializedName(IpcConfig.DeviceCommunicationConfig.KEY_APP_MESSAGE_PARAMS)
    private List<AppRequestContainer> mParams;

    public void setParams(List<AppRequestContainer> params) {
        this.mParams = params;
    }
}
