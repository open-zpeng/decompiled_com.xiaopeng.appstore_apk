package com.xiaopeng.appstore.bizcommon.entities;

import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import java.util.concurrent.atomic.AtomicInteger;
/* loaded from: classes2.dex */
public class AssembleDataContainer {
    private static final AtomicInteger sCurrentId = new AtomicInteger(0);
    private String mAppLabel;
    private String mConfigMd5;
    private String mConfigUrl;
    private long mDownloadBytes;
    private int mDownloadId;
    private String mDownloadUrl;
    private String mIconUrl;
    private final int mId;
    private String mMd5;
    private String mPackageName;
    private float mProgress;
    private long mRemain;
    private long mSpeedPerSecond;
    private int mState;
    private long mTotalBytes;

    private static int generateId() {
        return sCurrentId.incrementAndGet();
    }

    public AssembleDataContainer() {
        this(generateId());
    }

    public AssembleDataContainer(int id) {
        this.mDownloadId = -1;
        this.mSpeedPerSecond = 0L;
        this.mRemain = 0L;
        this.mProgress = 0.0f;
        this.mState = 5;
        this.mId = id;
        AtomicInteger atomicInteger = sCurrentId;
        if (id >= atomicInteger.get()) {
            atomicInteger.set(id);
        }
    }

    public int getId() {
        return this.mId;
    }

    public int getDownloadId() {
        return this.mDownloadId;
    }

    public void setDownloadId(int downloadId) {
        this.mDownloadId = downloadId;
    }

    public String getDownloadUrl() {
        return this.mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.mDownloadUrl = downloadUrl;
    }

    public String getConfigUrl() {
        return this.mConfigUrl;
    }

    public void setConfigUrl(String configUrl) {
        this.mConfigUrl = configUrl;
    }

    public String getMd5() {
        return this.mMd5;
    }

    public void setMd5(String md5) {
        this.mMd5 = md5;
    }

    public String getConfigMd5() {
        return this.mConfigMd5;
    }

    public void setConfigMd5(String configMd5) {
        this.mConfigMd5 = configMd5;
    }

    public long getTotalBytes() {
        return this.mTotalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.mTotalBytes = totalBytes;
    }

    public String getAppLabel() {
        return this.mAppLabel;
    }

    public void setAppLabel(String appLabel) {
        this.mAppLabel = appLabel;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public String getIconUrl() {
        return this.mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public float getProgress() {
        return this.mProgress;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
    }

    public long getDownloadBytes() {
        return this.mDownloadBytes;
    }

    public void setDownloadBytes(long mDownloadBytes) {
        this.mDownloadBytes = mDownloadBytes;
    }

    public long getSpeedPerSecond() {
        return this.mSpeedPerSecond;
    }

    public void setSpeedPerSecond(long speedPerSecond) {
        this.mSpeedPerSecond = speedPerSecond;
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int state) {
        this.mState = state;
    }

    public long getRemain() {
        return this.mRemain;
    }

    public void setRemain(long remain) {
        this.mRemain = remain;
    }

    public String toString() {
        return "AssembleData{id='" + this.mId + "'url='" + this.mDownloadUrl + "', config='" + this.mConfigUrl + "', pn='" + this.mPackageName + "', downloaded='" + this.mDownloadBytes + "', total='" + this.mTotalBytes + "', state='" + AppStoreAssembleManager.stateToStr(this.mState) + "', progress='" + this.mProgress + "'}";
    }
}
