package com.xiaopeng.appstore.libcommon.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import com.orhanobut.logger.Logger;
/* loaded from: classes2.dex */
public class AsyncInflateHelper {
    private static final String TAG = "AsyncInflateHelper";

    private AsyncInflateHelper() {
    }

    public static void inflateAsync(final Context context, int resId, ViewGroup parent, final AsyncLayoutInflater.OnInflateFinishedListener finishedListener) {
        new AsyncLayoutInflater(context).inflate(resId, parent, new AsyncLayoutInflater.OnInflateFinishedListener() { // from class: com.xiaopeng.appstore.libcommon.utils.-$$Lambda$AsyncInflateHelper$toLevAwK3QvJBpGWuaRtPdmEwHU
            @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
            public final void onInflateFinished(View view, int i, ViewGroup viewGroup) {
                AsyncInflateHelper.lambda$inflateAsync$0(context, finishedListener, view, i, viewGroup);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$inflateAsync$0(Context context, AsyncLayoutInflater.OnInflateFinishedListener onInflateFinishedListener, View view, int i, ViewGroup viewGroup) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing() || activity.isDestroyed()) {
                Logger.t(TAG).w("inflateAsync, ignore sinch Activity is invalid.", new Object[0]);
                return;
            }
        }
        onInflateFinishedListener.onInflateFinished(view, i, viewGroup);
    }
}
