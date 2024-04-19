package com.xiaopeng.appstore.xpcommon.push;

import java.util.Objects;
/* loaded from: classes2.dex */
public class PushedMessageBean {
    private int bizType;
    private String content;
    private long messageId;
    private int messageType;
    private int priority;
    private int scene;
    private long ts;
    private int type;
    private long uid;
    private long validEndTs;
    private long validStartTs;

    public void setBizType(int bizType) {
        this.bizType = bizType;
    }

    public int getBizType() {
        return this.bizType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getMessageId() {
        return this.messageId;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getMessageType() {
        return this.messageType;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setScene(int scene) {
        this.scene = scene;
    }

    public int getScene() {
        return this.scene;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public long getTs() {
        return this.ts;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getUid() {
        return this.uid;
    }

    public void setValidEndTs(long validEndTs) {
        this.validEndTs = validEndTs;
    }

    public long getValidEndTs() {
        return this.validEndTs;
    }

    public void setValidStartTs(long validStartTs) {
        this.validStartTs = validStartTs;
    }

    public long getValidStartTs() {
        return this.validStartTs;
    }

    public String toString() {
        return "PushedMessageBean{bizType=" + this.bizType + ", content='" + this.content + "', messageId=" + this.messageId + ", messageType=" + this.messageType + ", priority=" + this.priority + ", scene=" + this.scene + ", ts=" + this.ts + ", type=" + this.type + ", uid=" + this.uid + ", validEndTs=" + this.validEndTs + ", validStartTs=" + this.validStartTs + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof PushedMessageBean) {
            PushedMessageBean pushedMessageBean = (PushedMessageBean) o;
            return this.bizType == pushedMessageBean.bizType && this.messageId == pushedMessageBean.messageId && this.messageType == pushedMessageBean.messageType && this.priority == pushedMessageBean.priority && this.scene == pushedMessageBean.scene && this.ts == pushedMessageBean.ts && this.type == pushedMessageBean.type && this.uid == pushedMessageBean.uid && this.validEndTs == pushedMessageBean.validEndTs && this.validStartTs == pushedMessageBean.validStartTs && Objects.equals(this.content, pushedMessageBean.content);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.bizType), this.content, Long.valueOf(this.messageId), Integer.valueOf(this.messageType), Integer.valueOf(this.priority), Integer.valueOf(this.scene), Long.valueOf(this.ts), Integer.valueOf(this.type), Long.valueOf(this.uid), Long.valueOf(this.validEndTs), Long.valueOf(this.validStartTs));
    }
}
