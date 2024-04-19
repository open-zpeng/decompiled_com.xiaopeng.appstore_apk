package com.xiaopeng.appstore.libcommon.utils;

import androidx.lifecycle.LiveData;
/* loaded from: classes2.dex */
public class AbsentLiveData extends LiveData {
    private AbsentLiveData() {
        postValue(null);
    }

    public static <T> LiveData<T> create() {
        return new AbsentLiveData();
    }
}
