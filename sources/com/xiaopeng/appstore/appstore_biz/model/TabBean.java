package com.xiaopeng.appstore.appstore_biz.model;

import android.os.Parcel;
import android.os.Parcelable;
/* loaded from: classes2.dex */
public class TabBean implements Parcelable {
    public static final Parcelable.Creator<TabBean> CREATOR = new Parcelable.Creator<TabBean>() { // from class: com.xiaopeng.appstore.appstore_biz.model.TabBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TabBean createFromParcel(Parcel in) {
            return new TabBean(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TabBean[] newArray(int size) {
            return new TabBean[size];
        }
    };
    private int mAction;
    private int mNavigatorType;
    private String mPackageName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TabBean() {
    }

    public TabBean(Parcel in) {
        this.mNavigatorType = in.readInt();
        this.mPackageName = in.readString();
        this.mAction = in.readInt();
    }

    public int getNavigatorType() {
        return this.mNavigatorType;
    }

    public void setNavigatorType(int navigatorType) {
        this.mNavigatorType = navigatorType;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public int getAction() {
        return this.mAction;
    }

    public void setAction(int action) {
        this.mAction = action;
    }

    public String toString() {
        return "TabBean{mNavigatorType=" + this.mNavigatorType + ", mPackageName='" + this.mPackageName + "', mAction=" + this.mAction + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mNavigatorType);
        dest.writeString(this.mPackageName);
        dest.writeInt(this.mAction);
    }
}
