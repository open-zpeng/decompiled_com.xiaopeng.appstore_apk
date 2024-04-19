package com.xiaopeng.appstore.applist_biz.logic;
/* loaded from: classes2.dex */
public abstract class Provider<T> {
    public abstract T get();

    public static <T> Provider<T> of(final T value) {
        return new Provider<T>() { // from class: com.xiaopeng.appstore.applist_biz.logic.Provider.1
            @Override // com.xiaopeng.appstore.applist_biz.logic.Provider
            public T get() {
                return (T) value;
            }
        };
    }
}
