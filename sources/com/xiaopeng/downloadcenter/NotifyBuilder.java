package com.xiaopeng.downloadcenter;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
/* loaded from: classes2.dex */
public class NotifyBuilder {
    private static final String TAG = "DownloadBuilder";
    private DownLoadCenterImpl mDownLoadCenterService;
    private int mId;
    private int mProgress;
    private int mRemainTime;
    private String mTitle;
    private Icon mIcon = null;
    private int mStatus = 0;
    private int mButtonEnabled = -1;

    /* JADX INFO: Access modifiers changed from: package-private */
    public NotifyBuilder(int i, DownLoadCenterImpl downLoadCenterImpl) {
        Log.d(TAG, "DownloadBuilder: init id:" + i);
        this.mId = i;
        this.mDownLoadCenterService = downLoadCenterImpl;
    }

    Icon getIcon() {
        return this.mIcon;
    }

    public NotifyBuilder setIcon(Icon icon) {
        this.mIcon = icon;
        return this;
    }

    String getTitle() {
        return this.mTitle;
    }

    public NotifyBuilder setTitle(String str) {
        this.mTitle = str;
        return this;
    }

    int getStatus() {
        return this.mStatus;
    }

    public NotifyBuilder setStatus(int i) {
        this.mStatus = i;
        return this;
    }

    int getProgress() {
        return this.mProgress;
    }

    public NotifyBuilder setProgress(int i) {
        this.mProgress = i;
        return this;
    }

    int getRemainTime() {
        return this.mRemainTime;
    }

    public NotifyBuilder setRemainTime(int i) {
        this.mRemainTime = i;
        return this;
    }

    public NotifyBuilder setButtonEnabled(int i) {
        this.mButtonEnabled = i;
        return this;
    }

    public void notifyChanged() {
        notifyChanged(false);
    }

    public void notifyChanged(boolean z) {
        Bundle item = this.mDownLoadCenterService.getItem(this.mId);
        if (item == null) {
            return;
        }
        int i = this.mStatus;
        if (i > 0) {
            item.putInt("xp.download.status", i);
        }
        item.putInt("xp.download.progress", this.mProgress);
        item.putInt("xp.download.remaining.time", this.mRemainTime);
        int i2 = this.mButtonEnabled;
        if (i2 > -1) {
            int min = Math.min(i2, 1);
            this.mButtonEnabled = min;
            item.putInt("xp.download.button.status", min);
        }
        Log.d(TAG, "notifyChanged: {id=" + this.mId + ", icon=" + this.mIcon + ", title=" + this.mTitle + ", status=" + this.mStatus + ", progress=" + this.mProgress + ", remain=" + this.mRemainTime + ", btnEnabled=" + this.mButtonEnabled + ", isProg=" + z + '}');
        this.mDownLoadCenterService.notifyChanged(this.mId, z, this.mTitle, this.mIcon, item);
    }

    public String toString() {
        return "DownloadBuilder{id=" + this.mId + ", icon=" + this.mIcon + ", title='" + this.mTitle + "', status=" + this.mStatus + ", progress=" + this.mProgress + ", remain=" + this.mRemainTime + ", btnEnabled=" + this.mButtonEnabled + '}';
    }
}
