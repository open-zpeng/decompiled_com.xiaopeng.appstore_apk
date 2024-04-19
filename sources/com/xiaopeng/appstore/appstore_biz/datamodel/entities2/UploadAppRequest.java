package com.xiaopeng.appstore.appstore_biz.datamodel.entities2;
/* loaded from: classes2.dex */
public class UploadAppRequest {
    public String app_infos;
    public String version;

    public String toString() {
        return "UploadAppRequest{app_infos=" + this.app_infos + ", version='" + this.version + "'}";
    }

    /* loaded from: classes2.dex */
    public static class UploadAppInfo {
        public String versionCode;

        public String toString() {
            return "App{version='" + this.versionCode + "'}";
        }
    }
}
