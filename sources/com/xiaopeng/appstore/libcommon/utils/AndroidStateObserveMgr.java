package com.xiaopeng.appstore.libcommon.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.orhanobut.logger.Logger;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
/* loaded from: classes2.dex */
public class AndroidStateObserveMgr {
    private static final String TAG = "AndroidStateObserveMgr";
    private Context mContext;
    private final Set<StateListener> mListenerList;
    private final BroadcastReceiver mScreenOffReceiver;

    /* loaded from: classes2.dex */
    public interface StateListener {
        void onScreenOff();
    }

    private AndroidStateObserveMgr() {
        this.mListenerList = Collections.synchronizedSet(new HashSet());
        this.mScreenOffReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.appstore.libcommon.utils.AndroidStateObserveMgr.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Logger.t(AndroidStateObserveMgr.TAG).i("onReceive action=" + action, new Object[0]);
                if ("android.intent.action.SCREEN_OFF".equals(action)) {
                    for (StateListener stateListener : AndroidStateObserveMgr.this.mListenerList) {
                        stateListener.onScreenOff();
                    }
                }
            }
        };
    }

    /* loaded from: classes2.dex */
    static class SingletonHolder {
        static AndroidStateObserveMgr sInstance = new AndroidStateObserveMgr();

        SingletonHolder() {
        }
    }

    public static AndroidStateObserveMgr get() {
        return SingletonHolder.sInstance;
    }

    public void init(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        registerScreenOff(applicationContext, this.mScreenOffReceiver);
    }

    public void release() {
        BroadcastReceiver broadcastReceiver;
        Context context = this.mContext;
        if (context != null && (broadcastReceiver = this.mScreenOffReceiver) != null) {
            unregisterScreenOff(context, broadcastReceiver);
        }
        this.mListenerList.clear();
    }

    private static void registerScreenOff(Context context, BroadcastReceiver receiver) {
        context.registerReceiver(receiver, new IntentFilter("android.intent.action.SCREEN_OFF"));
    }

    public static void unregisterScreenOff(Context context, BroadcastReceiver receiver) {
        context.unregisterReceiver(receiver);
    }

    public void addListener(StateListener listener) {
        this.mListenerList.add(listener);
    }

    public void removeListener(StateListener listener) {
        this.mListenerList.remove(listener);
    }
}
