package com.xiaopeng.appstore.appstore_biz.datamodel.entities2;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
/* loaded from: classes2.dex */
public class SubmitRequest {
    public static final int REPORT_TYPE_DOWNLOAD = 1;
    public static final int REPORT_TYPE_INSTALL_FAILED = 2;
    @SerializedName(IpcConfig.DeviceCommunicationConfig.KEY_APP_MESSAGE_PARAMS)
    private List<AppRequestContainer> mParams;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface SubmitRequestType {
    }

    public void setParams(List<AppRequestContainer> params) {
        this.mParams = params;
    }
}
