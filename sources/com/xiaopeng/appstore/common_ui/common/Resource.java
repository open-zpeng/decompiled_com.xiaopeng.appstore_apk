package com.xiaopeng.appstore.common_ui.common;
/* loaded from: classes.dex */
public class Resource<T> {
    public final T data;
    public final String message;
    public final Status status;

    /* loaded from: classes.dex */
    public enum Status {
        SUCCESS,
        ERROR,
        LOADING,
        EMPTY
    }

    private Resource(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> loading(T data) {
        return new Resource<>(Status.LOADING, data, null);
    }

    public static <T> Resource<T> empty(String msg, T data) {
        return new Resource<>(Status.EMPTY, data, msg);
    }

    public String toString() {
        return "Resource{status=" + this.status + ", data=" + this.data + ", msg='" + this.message + "'}";
    }
}
