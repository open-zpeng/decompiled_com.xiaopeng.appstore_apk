package com.xiaopeng.appstore.libcommon.utils;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
/* loaded from: classes2.dex */
public class StartPerformanceUtils {
    private static final String PERFORM_EVENT = "perf_start";
    private static final String TAG = "StartPerformanceUtils";
    private static long appCreateBeginStamp = 0;
    private static long appCreateEndStamp = 0;
    private static long appCreateTime = 0;
    private static long appFirstFrameTime = 0;
    private static long appMainActTime = 0;
    private static long firstFrameEndStamp = 0;
    private static volatile boolean mIsColdStart = true;
    private static long mainActCreateBeginStamp;
    private static long mainActCreateEndStamp;
    private static long mainActCreateTime;
    private static long mainActFirstFrameTime;
    private static long mainActRestartBeginStamp;
    private static long mainActRestartEndStamp;
    private static long mainActRestartTime;
    private static long mainActResumeBeginStamp;
    private static long mainActResumeEndStamp;
    private static long mainActResumeTime;
    private static long mainActStartBeginStamp;
    private static long mainActStartEndStamp;
    private static long mainActStartTime;

    public static void appOnCreateBegin() {
        appCreateBeginStamp = SystemClock.uptimeMillis();
        Log.d(TAG, "appOnCreateBegin: appCreateBeginStamp=" + appCreateBeginStamp);
    }

    public static void appOnCreateEnd() {
        appCreateEndStamp = SystemClock.uptimeMillis();
        Log.d(TAG, "appOnCreateEnd: appCreateEndStamp=" + appCreateEndStamp);
        appCreateTime = appCreateEndStamp - appCreateBeginStamp;
        Log.d(TAG, "appOnCreateEnd: appCreateTime=" + appCreateTime);
    }

    public static void onCreateBegin() {
        mainActCreateBeginStamp = SystemClock.uptimeMillis();
        Log.d(TAG, "onCreateBegin: mainActCreateBeginStamp=" + mainActCreateBeginStamp);
        appMainActTime = mainActCreateBeginStamp - appCreateEndStamp;
        Log.d(TAG, "onCreateBegin: appMainActTime=" + appMainActTime);
    }

    public static void onCreateEnd() {
        mainActCreateEndStamp = SystemClock.uptimeMillis();
        Log.d(TAG, "onCreateEnd: mainActCreateEndStamp=" + mainActCreateEndStamp);
        mainActCreateTime = mainActCreateEndStamp - mainActCreateBeginStamp;
        Log.d(TAG, "onCreateEnd: mainActCreateTime=" + mainActCreateTime);
    }

    public static void firstFrameEnd(Context context) {
        long uptimeMillis = SystemClock.uptimeMillis();
        firstFrameEndStamp = uptimeMillis;
        mainActFirstFrameTime = uptimeMillis - mainActCreateBeginStamp;
        appFirstFrameTime = uptimeMillis - appCreateBeginStamp;
        if (mIsColdStart) {
            mIsColdStart = false;
        } else {
            appCreateTime = -1L;
        }
    }

    public static void onStartBegin() {
        mainActStartBeginStamp = SystemClock.uptimeMillis();
        Log.d(TAG, "onStartBegin: mainActCreateTime=" + mainActStartBeginStamp);
    }

    public static void onStartEnd() {
        mainActStartEndStamp = SystemClock.uptimeMillis();
        Log.d(TAG, "onStartEnd: mainActStartEndStamp=" + mainActStartEndStamp);
        mainActStartTime = mainActStartEndStamp - mainActStartBeginStamp;
        Log.d(TAG, "onStartEnd: mainActStartTime=" + mainActStartTime);
    }

    public static void onReStartBegin() {
        mainActRestartBeginStamp = SystemClock.uptimeMillis();
    }

    public static void onReStartEnd() {
        long uptimeMillis = SystemClock.uptimeMillis();
        mainActRestartEndStamp = uptimeMillis;
        mainActRestartTime = uptimeMillis - mainActRestartBeginStamp;
    }

    public static void onResumeBegin() {
        mainActResumeBeginStamp = SystemClock.uptimeMillis();
        Log.d(TAG, "onResumeBegin: mainActResumeBeginStamp=" + mainActResumeBeginStamp);
    }

    public static void onResumeEnd() {
        mainActResumeEndStamp = SystemClock.uptimeMillis();
        Log.d(TAG, "onResumeEnd: mainActResumeEndStamp=" + mainActResumeEndStamp);
        mainActResumeTime = mainActResumeEndStamp - mainActResumeBeginStamp;
        Log.d(TAG, "onResumeEnd: mainActResumeTime=" + mainActResumeTime);
    }
}
