package com.xiaopeng.aar.utils;

import com.xiaopeng.aar.utils.ThreadUtils;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
/* loaded from: classes2.dex */
public class TimeLogs implements Runnable {
    private long mEndTime;
    private boolean mIsMicrosecond;
    private String mName;
    private long mStartTime;
    private final LinkedHashMap<String, Long> mTagTimeMap = new LinkedHashMap<>();
    private long mTempTime;

    private TimeLogs() {
    }

    public static TimeLogs create() {
        return new TimeLogs();
    }

    public void setMicrosecond(boolean z) {
        this.mIsMicrosecond = z;
    }

    public void start(String str) {
        this.mName = str;
        long nanoTime = System.nanoTime();
        this.mStartTime = nanoTime;
        this.mTempTime = nanoTime;
    }

    public void record(String str) {
        this.mTagTimeMap.put(str, Long.valueOf(System.nanoTime() - this.mTempTime));
        this.mTempTime = System.nanoTime();
    }

    public void end() {
        this.mEndTime = System.nanoTime() - this.mStartTime;
        ThreadUtils.SINGLE.post(this);
    }

    @Override // java.lang.Runnable
    public void run() {
        Set<Map.Entry<String, Long>> entrySet = this.mTagTimeMap.entrySet();
        StringBuilder sb = new StringBuilder();
        sb.append("total:");
        if (this.mIsMicrosecond) {
            sb.append(this.mEndTime / 1000);
            sb.append("μs");
        } else {
            sb.append((this.mEndTime / 1000) / 1000);
            sb.append("ms");
        }
        for (Map.Entry<String, Long> entry : entrySet) {
            sb.append(", ");
            sb.append(entry.getKey());
            sb.append(":");
            if (this.mIsMicrosecond) {
                sb.append(entry.getValue().longValue() / 1000);
            } else {
                sb.append((entry.getValue().longValue() / 1000) / 1000);
            }
        }
        this.mTagTimeMap.clear();
        LogUtils.d(TimeLogs.class.getSimpleName(), this.mName + sb.toString());
    }
}
