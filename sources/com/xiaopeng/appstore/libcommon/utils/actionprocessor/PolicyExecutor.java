package com.xiaopeng.appstore.libcommon.utils.actionprocessor;

import com.xiaopeng.appstore.libcommon.utils.actionprocessor.Policy;
/* loaded from: classes2.dex */
public abstract class PolicyExecutor<P extends Policy> {
    protected P mPolicy;

    public abstract void abort();

    public abstract <T> Result<T> run(Callable<T> runnable);

    public PolicyExecutor(P policy) {
        this.mPolicy = policy;
    }
}
