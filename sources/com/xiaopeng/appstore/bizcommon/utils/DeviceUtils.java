package com.xiaopeng.appstore.bizcommon.utils;

import android.content.Context;
import com.xiaopeng.appstore.libcommon.utils.Utils;
/* loaded from: classes2.dex */
public class DeviceUtils {
    public static final int D21_SCREEN_HEIGHT = 1920;
    public static final int D21_SCREEN_WIDTH = 1080;
    public static final int E28_SCREEN_HEIGHT = 1200;
    public static final int E28_SCREEN_WIDTH = 2400;
    private static boolean sInter = false;
    private static int sOrientation;

    private DeviceUtils() {
    }

    public static void init(Context context) {
        sInter = !context.getResources().getConfiguration().locale.getLanguage().equals("zh");
        sOrientation = context.getResources().getConfiguration().orientation;
    }

    public static int getScreenWidth() {
        return Utils.getDisplayMetrics().widthPixels;
    }

    public static boolean isE28Screen() {
        return sOrientation == 2;
    }

    public static boolean isD21Screen() {
        return sOrientation == 1;
    }

    public static boolean isInter() {
        return sInter;
    }
}
