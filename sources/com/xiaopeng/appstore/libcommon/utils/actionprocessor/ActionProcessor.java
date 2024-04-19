package com.xiaopeng.appstore.libcommon.utils.actionprocessor;
/* loaded from: classes2.dex */
public class ActionProcessor {
    private PolicyExecutor<? extends Policy> mPolicyExecutor;

    public PolicyExecutor<? extends Policy> with(Policy policy) {
        PolicyExecutor<? extends Policy> executor = policy.toExecutor();
        this.mPolicyExecutor = executor;
        return executor;
    }

    public void abort() {
        PolicyExecutor<? extends Policy> policyExecutor = this.mPolicyExecutor;
        if (policyExecutor != null) {
            policyExecutor.abort();
        }
    }
}
