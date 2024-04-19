package com.xiaopeng.appstore.libcommon.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import java.lang.reflect.Field;
/* loaded from: classes2.dex */
public class ResUtils {
    public static String getString(int id) {
        return Utils.getApp().getResources().getString(id);
    }

    public static float getDimen(int id) {
        return Utils.getApp().getResources().getDimension(id);
    }

    public static int getDimenPixel(int id) {
        return Utils.getApp().getResources().getDimensionPixelOffset(id);
    }

    public static int getInteger(int id) {
        return Utils.getApp().getResources().getInteger(id);
    }

    public static Drawable getDrawable(int id) {
        return Utils.getApp().getResources().getDrawable(id, null);
    }

    public static int getColor(int id) {
        return Utils.getApp().getResources().getColor(id, null);
    }

    public static ColorStateList getColorStateList(int id) {
        return Utils.getApp().getResources().getColorStateList(id, null);
    }

    public static int getResId(String variableName, Class<?> c) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = c.getDeclaredField(variableName);
        return declaredField.getInt(declaredField);
    }
}
