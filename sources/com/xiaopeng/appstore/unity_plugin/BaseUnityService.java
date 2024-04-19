package com.xiaopeng.appstore.unity_plugin;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
/* loaded from: classes2.dex */
public abstract class BaseUnityService {
    private static final String TAG = "BaseUnityService";
    protected Activity mUnityActivity;
    protected Application mUnityApplication;
    protected Handler mUnityMainHandler;

    protected abstract void initInternal(Application unityApplication, Activity unityActivity);

    public void release() {
    }

    public void init() {
        init(Helper.getUnityActivity());
    }

    public void init(Activity activity) {
        this.mUnityActivity = activity;
        if (activity == null) {
            Log.e(TAG, "init ERROR, Unity Activity not found");
            return;
        }
        this.mUnityApplication = activity.getApplication();
        this.mUnityMainHandler = new Handler(Looper.myLooper());
        initInternal(this.mUnityApplication, this.mUnityActivity);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void postToUnityMain(Runnable runnable) {
        Handler handler;
        if (runnable == null || (handler = this.mUnityMainHandler) == null) {
            return;
        }
        handler.post(runnable);
    }

    /* loaded from: classes2.dex */
    public static class Helper {
        private static final String TAG = "UnityServiceHelper";

        private Helper() {
        }

        public static Activity getUnityActivity() {
            try {
                Class<?> cls = Class.forName("com.unity3d.player.UnityPlayer");
                return (Activity) cls.getDeclaredField("currentActivity").get(cls);
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
                Log.e(TAG, "getActivity ex:" + e);
                e.printStackTrace();
                return null;
            }
        }

        public static boolean callUnity(String gameObjectName, String functionName, String args) {
            try {
                Class<?> cls = Class.forName("com.unity3d.player.UnityPlayer");
                cls.getMethod("UnitySendMessage", String.class, String.class, String.class).invoke(cls, gameObjectName, functionName, args);
                return true;
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                Log.e(TAG, "callUnity ex:" + e);
                e.printStackTrace();
                return false;
            }
        }
    }
}
