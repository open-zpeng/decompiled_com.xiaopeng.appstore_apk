package com.xiaopeng.appstore.common_ui.common.base;

import android.util.Log;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import java.util.concurrent.atomic.AtomicBoolean;
/* loaded from: classes.dex */
public class SingleLiveEvent<T> extends MutableLiveData<T> {
    private static final String TAG = "SingleLiveEvent";
    private final AtomicBoolean mPending = new AtomicBoolean(false);

    @Override // androidx.lifecycle.LiveData
    public void observe(LifecycleOwner owner, final Observer<? super T> observer) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
        }
        super.observe(owner, new Observer<T>() { // from class: com.xiaopeng.appstore.common_ui.common.base.SingleLiveEvent.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(T t) {
                if (SingleLiveEvent.this.mPending.compareAndSet(true, false)) {
                    observer.onChanged(t);
                }
            }
        });
    }

    @Override // androidx.lifecycle.MutableLiveData, androidx.lifecycle.LiveData
    public void setValue(T t) {
        this.mPending.set(true);
        super.setValue(t);
    }

    public void call() {
        setValue(null);
    }
}
