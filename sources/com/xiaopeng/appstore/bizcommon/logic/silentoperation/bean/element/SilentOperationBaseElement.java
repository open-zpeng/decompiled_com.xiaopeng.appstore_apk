package com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element;

import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.action.SilentOperationBaseAction;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.result.SilentOperationElementResult;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
/* loaded from: classes2.dex */
public abstract class SilentOperationBaseElement {
    protected LinkedBlockingQueue<SilentOperationBaseAction> mSilentOperationActionsQueue = new LinkedBlockingQueue<>();
    protected SilentOperationElementResult mElementResult = new SilentOperationElementResult();

    public abstract SilentOperationElementResult execute();

    public <T extends SilentOperationBaseAction> boolean offer(T operationAction) {
        return this.mSilentOperationActionsQueue.offer(operationAction);
    }

    public <T extends SilentOperationBaseAction> T poll() {
        return (T) this.mSilentOperationActionsQueue.poll();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Objects.equals(this.mSilentOperationActionsQueue, ((SilentOperationBaseElement) o).mSilentOperationActionsQueue);
    }

    public int hashCode() {
        return Objects.hash(this.mSilentOperationActionsQueue);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SilentOperationBaseElement{");
        sb.append("mSilentOperationActionsQueue=").append(this.mSilentOperationActionsQueue);
        sb.append(", mElementResult=").append(this.mElementResult);
        sb.append('}');
        return sb.toString();
    }
}
