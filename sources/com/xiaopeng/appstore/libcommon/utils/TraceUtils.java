package com.xiaopeng.appstore.libcommon.utils;

import java.util.HashMap;
import java.util.Stack;
/* loaded from: classes2.dex */
public class TraceUtils {
    public static final boolean DEBUG_BY_LOG = false;
    private static final boolean NEED_TRACE = false;
    private static final String TAG = "Trace";
    private static HashMap<String, Long> sThreadStartTimeMap = new HashMap<>();
    private static HashMap<String, Stack<String>> sThreadMethodNameMap = new HashMap<>();
    private static HashMap<String, Stack<Long>> sThreadEndTimeMap = new HashMap<>();

    public static void alwaysTraceBegin(String methodName) {
    }

    public static void alwaysTraceEnd() {
    }
}
