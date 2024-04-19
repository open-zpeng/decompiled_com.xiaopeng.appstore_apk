package com.xiaopeng.appstore.common_ui.common;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import com.xiaopeng.appstore.bizcommon.common.BizLifecycle;
import java.util.HashSet;
import java.util.Set;
/* loaded from: classes.dex */
public class AndroidLifecycleManager implements DefaultLifecycleObserver {
    private final Set<BizLifecycle> mObservers;

    public AndroidLifecycleManager() {
        this.mObservers = new HashSet();
    }

    public AndroidLifecycleManager(BizLifecycle bizLifecycle) {
        HashSet hashSet = new HashSet();
        this.mObservers = hashSet;
        hashSet.add(bizLifecycle);
    }

    public void addObserver(BizLifecycle bizLifecycle) {
        this.mObservers.add(bizLifecycle);
    }

    public void removeObserver(BizLifecycle bizLifecycle) {
        this.mObservers.add(bizLifecycle);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public void onCreate(LifecycleOwner owner) {
        for (BizLifecycle bizLifecycle : this.mObservers) {
            bizLifecycle.create();
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public void onStart(LifecycleOwner owner) {
        for (BizLifecycle bizLifecycle : this.mObservers) {
            bizLifecycle.start();
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public void onStop(LifecycleOwner owner) {
        for (BizLifecycle bizLifecycle : this.mObservers) {
            bizLifecycle.stop();
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public void onDestroy(LifecycleOwner owner) {
        for (BizLifecycle bizLifecycle : this.mObservers) {
            bizLifecycle.destroy();
        }
        this.mObservers.clear();
    }
}
