package com.xiaopeng.appstore.appstore_biz.bizusb.datamodel.entities;

import android.graphics.Bitmap;
import java.io.File;
/* loaded from: classes2.dex */
public class LocalApkInfo {
    private String appName;
    private String configMd5;
    private String configUrl;
    private String iconUrl;
    private boolean isInstalled;
    private Bitmap mBitmap;
    private File mFile;
    private String md5;
    private String packageName;
    private String versionCode;

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public void setConfigUrl(String configUrl) {
        this.configUrl = configUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getConfigUrl() {
        return this.configUrl;
    }

    public File getFile() {
        return this.mFile;
    }

    public void setFile(File file) {
        this.mFile = file;
    }

    public LocalApkInfo(String packageName, String versionCode, String md5, File file) {
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.md5 = md5;
        this.mFile = file;
    }

    public LocalApkInfo(String appName, String iconUrl, String packageName, String versionCode, String md5) {
        this.appName = appName;
        this.iconUrl = iconUrl;
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.md5 = md5;
    }

    public boolean isInstalled() {
        return this.isInstalled;
    }

    public void setInstalled(boolean installed) {
        this.isInstalled = installed;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getConfigMd5() {
        return this.configMd5;
    }

    public void setConfigMd5(String configMd5) {
        this.configMd5 = configMd5;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
}
