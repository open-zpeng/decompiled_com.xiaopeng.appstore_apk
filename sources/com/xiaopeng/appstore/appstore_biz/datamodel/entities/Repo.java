package com.xiaopeng.appstore.appstore_biz.datamodel.entities;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class Repo {
    public static final int UNKNOWN_ID = -1;
    @SerializedName("description")
    public final String description;
    @SerializedName("full_name")
    public final String fullName;
    public final int id;
    @SerializedName("name")
    public final String name;
    @SerializedName("owner")
    public final Owner owner;
    @SerializedName("stargazers_count")
    public final int stars;

    public Repo(int id, String name, String fullName, String description, Owner owner, int stars) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.owner = owner;
        this.stars = stars;
    }

    /* loaded from: classes2.dex */
    public static class Owner {
        @SerializedName("login")
        public final String login;
        @SerializedName("url")
        public final String url;

        public Owner(String login, String url) {
            this.login = login;
            this.url = url;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Owner owner = (Owner) o;
            String str = this.login;
            if (str == null ? owner.login == null : str.equals(owner.login)) {
                String str2 = this.url;
                String str3 = owner.url;
                return str2 != null ? str2.equals(str3) : str3 == null;
            }
            return false;
        }

        public int hashCode() {
            String str = this.login;
            int hashCode = (str != null ? str.hashCode() : 0) * 31;
            String str2 = this.url;
            return hashCode + (str2 != null ? str2.hashCode() : 0);
        }
    }
}
