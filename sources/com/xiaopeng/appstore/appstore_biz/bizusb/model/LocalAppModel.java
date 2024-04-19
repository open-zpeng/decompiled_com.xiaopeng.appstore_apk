package com.xiaopeng.appstore.appstore_biz.bizusb.model;

import android.graphics.Bitmap;
import java.io.File;
/* loaded from: classes2.dex */
public class LocalAppModel {
    private String apkMd5;
    private String configMd5;
    private String configUrl;
    private File file;
    private String iconUrl;
    private boolean isInstalled;
    private Bitmap mBitmap;
    private String name;
    private String packageName;

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public LocalAppModel(String name, String packageName, String iconUrl, boolean isInstalled, File file, String configUrl, Bitmap bitmap, String configMd5, String apkMd5) {
        this.name = name;
        this.packageName = packageName;
        this.iconUrl = iconUrl;
        this.isInstalled = isInstalled;
        this.file = file;
        this.configUrl = configUrl;
        this.mBitmap = bitmap;
        this.configMd5 = configMd5;
        this.apkMd5 = apkMd5;
    }

    public boolean isInstalled() {
        return this.isInstalled;
    }

    public void setInstalled(boolean installed) {
        this.isInstalled = installed;
    }

    public String getConfigUrl() {
        return this.configUrl;
    }

    public void setConfigUrl(String configUrl) {
        this.configUrl = configUrl;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getConfigMd5() {
        return this.configMd5;
    }

    public String getApkMd5() {
        return this.apkMd5;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("LocalAppModel{");
        sb.append("name='").append(this.name).append('\'');
        sb.append(", packageName='").append(this.packageName).append('\'');
        sb.append(", iconUrl='").append(this.iconUrl).append('\'');
        sb.append(", isInstalled=").append(this.isInstalled);
        sb.append(", configUrl='").append(this.configUrl).append('\'');
        sb.append(", file=").append(this.file);
        sb.append('}');
        return sb.toString();
    }
}
