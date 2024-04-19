package com.xiaopeng.appstore.appstore_biz.model2;

import java.io.Serializable;
/* loaded from: classes2.dex */
public class DeveloperModel implements Serializable {
    private String mId;
    private String mName;

    public String getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
