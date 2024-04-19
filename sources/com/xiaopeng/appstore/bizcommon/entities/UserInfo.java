package com.xiaopeng.appstore.bizcommon.entities;

import android.os.Parcel;
import android.os.Parcelable;
/* loaded from: classes2.dex */
public class UserInfo implements Parcelable {
    private static final String DEFAULT_USER_ID = "-1";
    private String mName;
    private String mUserId;
    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() { // from class: com.xiaopeng.appstore.bizcommon.entities.UserInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
    private static int mInfoType = 0;
    private static int mUserType = 0;

    public static String getDefaultUserId() {
        return DEFAULT_USER_ID;
    }

    public static int transToInfoType(int infoType) {
        if (infoType == 1) {
            return 1;
        }
        if (infoType == 0) {
        }
        return 0;
    }

    public static int transToUserType(int userType) {
        if (userType == 0) {
            return 0;
        }
        if (userType == 1) {
            return 1;
        }
        if (userType == 4) {
            return 4;
        }
        if (userType == 3) {
            return 3;
        }
        return userType == 2 ? 2 : 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UserInfo() {
        this.mUserId = DEFAULT_USER_ID;
        this.mName = "";
    }

    protected UserInfo(Parcel in) {
        this.mUserId = DEFAULT_USER_ID;
        this.mName = "";
        this.mUserId = in.readString();
        this.mName = in.readString();
    }

    public boolean isLegal(String userId) {
        return !DEFAULT_USER_ID.equals(userId);
    }

    public String getUserId() {
        return this.mUserId;
    }

    public int getUserType() {
        return mUserType;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mUserId);
        dest.writeString(this.mName);
    }

    public void setData(int update, int type, String uid, String userName) {
        this.mUserId = uid;
        this.mName = userName;
        mInfoType = transToInfoType(update);
        mUserType = transToUserType(type);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("UserInfo{");
        sb.append("mInfoType=").append(mInfoType);
        sb.append(", mUserType=").append(mUserType);
        sb.append(", mUserId='").append(this.mUserId).append('\'');
        sb.append(", mName='").append(this.mName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
