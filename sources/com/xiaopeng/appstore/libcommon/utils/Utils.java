package com.xiaopeng.appstore.libcommon.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import java.util.HashSet;
/* loaded from: classes2.dex */
public class Utils {
    private static final String PACKAGENAME_NAPA = "com.xiaopeng.napa";
    private static int mDensityDpi;
    private static DisplayMetrics mDm;
    private static Context sApplication;

    public static void init(Context application) {
        sApplication = application.getApplicationContext();
        Display defaultDisplay = ((WindowManager) application.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(displayMetrics);
        mDm = displayMetrics;
        mDensityDpi = displayMetrics.densityDpi;
    }

    @Deprecated
    public static Context getApp() {
        return sApplication;
    }

    public static DisplayMetrics getDisplayMetrics() {
        return mDm;
    }

    public static int getDensityDpi() {
        return mDensityDpi;
    }

    public static String getPackageName() {
        return sApplication.getPackageName();
    }

    public static <T> HashSet<T> singletonHashSet(T elem) {
        HashSet<T> hashSet = new HashSet<>(1);
        hashSet.add(elem);
        return hashSet;
    }

    public static boolean checkNapaApkExist() {
        try {
            sApplication.getPackageManager().getApplicationInfo("com.xiaopeng.napa", 8192);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }
}
