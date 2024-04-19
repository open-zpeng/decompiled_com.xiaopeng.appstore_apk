package com.xiaopeng.appstore.installer;

import android.content.pm.PackageInfo;
import android.os.Parcel;
import android.os.Parcelable;
/* loaded from: classes.dex */
public class InstallBean implements Parcelable {
    public static final Parcelable.Creator<InstallBean> CREATOR = new Parcelable.Creator<InstallBean>() { // from class: com.xiaopeng.appstore.installer.InstallBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstallBean createFromParcel(Parcel in) {
            return new InstallBean(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstallBean[] newArray(int size) {
            return new InstallBean[size];
        }
    };
    public PackageInfo installingPkgInfo;
    public int retCode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public InstallBean(int retCode) {
        this(retCode, null);
    }

    public InstallBean(int retCode, PackageInfo installingPkgInfo) {
        this.retCode = retCode;
        this.installingPkgInfo = installingPkgInfo;
    }

    protected InstallBean(Parcel in) {
        this.retCode = in.readInt();
        this.installingPkgInfo = (PackageInfo) in.readParcelable(PackageInfo.class.getClassLoader());
    }

    public String toString() {
        return "InstallBean{retCode=" + this.retCode + ", pkg=" + this.installingPkgInfo + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.retCode);
        dest.writeParcelable(this.installingPkgInfo, flags);
    }
}
