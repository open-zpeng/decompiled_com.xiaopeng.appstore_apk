package com.xiaopeng.appstore.bizcommon.entities;
/* loaded from: classes2.dex */
public class AgreementStateEntity {
    public static final String TABLE_NAME = "UserAgreementStates";
    private int mAgreed;
    private final String mUserId;

    public AgreementStateEntity(String userId) {
        this.mUserId = userId;
    }

    public String getUserId() {
        return this.mUserId;
    }

    public int getAgreed() {
        return this.mAgreed;
    }

    public void setAgreed(int agreed) {
        this.mAgreed = agreed;
    }

    public String toString() {
        return "AgreementStateEntity{mUserId='" + this.mUserId + "', mAgreed=" + this.mAgreed + '}';
    }
}
