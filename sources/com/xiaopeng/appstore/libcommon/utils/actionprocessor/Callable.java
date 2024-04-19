package com.xiaopeng.appstore.libcommon.utils.actionprocessor;
@FunctionalInterface
/* loaded from: classes2.dex */
public interface Callable<T> {
    T run(PolicyExecutor<? extends Policy> executor) throws Throwable;
}
