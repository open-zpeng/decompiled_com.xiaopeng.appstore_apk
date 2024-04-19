package com.xiaopeng.appstore.appstore_biz.datamodel.entities2;
/* loaded from: classes2.dex */
public class AdvLocalData {
    private AdvContainer mAdvContainer;
    private String mId;
    private long mTimestamp;

    public String getId() {
        return this.mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public AdvContainer getAdvContainer() {
        return this.mAdvContainer;
    }

    public void setAdvContainer(AdvContainer advContainer) {
        this.mAdvContainer = advContainer;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public void setTimestamp(long timestamp) {
        this.mTimestamp = timestamp;
    }
}
