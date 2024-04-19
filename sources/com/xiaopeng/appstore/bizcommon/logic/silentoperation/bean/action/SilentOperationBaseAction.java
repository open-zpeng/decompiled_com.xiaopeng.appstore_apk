package com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action;

import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.result.SilentOperationActionResult;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.CountDownLatch;
/* loaded from: classes2.dex */
public abstract class SilentOperationBaseAction {
    public static final int ACTION_TYPE_APPLY_CONFIG = 4;
    public static final int ACTION_TYPE_DELETE_FILE = 5;
    public static final int ACTION_TYPE_DOWNLOAD = 0;
    public static final int ACTION_TYPE_INIT_ALIPAY_ENGINE_SERVICE = 6;
    public static final int ACTION_TYPE_INSTALL = 1;
    public static final int ACTION_TYPE_INVALID = -1;
    public static final int ACTION_TYPE_UNINSTALL = 2;
    public static final int ACTION_TYPE_UPGRADE = 3;
    protected CountDownLatch mActionOperationSync;
    protected int mActionType;
    protected SilentOperationActionResult mOperationResult = new SilentOperationActionResult();

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface ActionType {
    }

    public abstract SilentOperationActionResult execute();

    public boolean pause() {
        return false;
    }

    public boolean retry() {
        return false;
    }

    public SilentOperationBaseAction(int actionType) {
        this.mActionType = -1;
        this.mActionType = actionType;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SilentOperationBaseAction{");
        sb.append("mOperationResult=").append(this.mOperationResult);
        sb.append(", mActionType=").append(this.mActionType);
        sb.append('}');
        return sb.toString();
    }
}
