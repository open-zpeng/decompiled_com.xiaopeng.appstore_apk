package com.xiaopeng.appstore.libcommon.utils.actionprocessor;
/* loaded from: classes2.dex */
public interface Policy {
    PolicyExecutor<? extends Policy> toExecutor();
}
