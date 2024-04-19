package com.xiaopeng.downloadcenter;

import android.content.Context;
import android.graphics.drawable.Icon;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
/* loaded from: classes2.dex */
public interface DownLoadCenterService {
    public static final int DOWNLOADING = 1;
    public static final int FAILED = 5;
    public static final int FINISHED = 4;
    public static final int PAUSED = 3;
    public static final int WAITING = 2;

    /* loaded from: classes2.dex */
    public interface CallBack {
        void onCancel(int i);

        default void onDelete(int i) {
        }

        void onOpen(int i);

        void onPause(int i);

        void onResume(int i);

        void onRetry(int i);
    }

    /* loaded from: classes2.dex */
    public interface CallBack2 {
        void onInitItem(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Status {
    }

    void cancel(int i);

    void cancelAll();

    CallBack getCallBack();

    Icon getIcon(int i);

    void init(Context context, int i, CallBack callBack, CallBack2 callBack2);

    void initItem(int i, String str, long j, int i2, boolean z, boolean z2, String str2);

    ArrayList<Integer> loadNotifyIds();

    NotifyBuilder notifyBuilder(int i);

    void notifyButtonChanged(int i, boolean z);

    void notifyProgressChanged(int i, int i2, int i3);

    void notifyStatusChanged(int i, int i2);

    void setIcon(int i, Icon icon);
}
