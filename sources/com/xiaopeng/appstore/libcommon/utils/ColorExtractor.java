package com.xiaopeng.appstore.libcommon.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.SparseArray;
/* loaded from: classes2.dex */
public class ColorExtractor {
    public static int findBorderColor(Bitmap bitmap) {
        return findBorderColor(bitmap, 20);
    }

    public static int findBorderColor(Bitmap bitmap, int samples) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int sqrt = (int) Math.sqrt((height * width) / samples);
        if (sqrt < 1) {
            sqrt = 1;
        }
        for (int i = 0; i < height; i += sqrt) {
            for (int i2 = 0; i2 < width; i2 += sqrt) {
                int pixel = bitmap.getPixel(i2, i);
                if (((pixel >> 24) & 255) >= 128) {
                    return pixel;
                }
            }
        }
        return -16777216;
    }

    public static int findDominantColorByHue(Bitmap bitmap) {
        return findDominantColorByHue(bitmap, 20);
    }

    public static int findDominantColorByHue(Bitmap bitmap, int samples) {
        int i;
        char c;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int sqrt = (int) Math.sqrt((height * width) / samples);
        if (sqrt < 1) {
            sqrt = 1;
        }
        float[] fArr = new float[3];
        char c2 = 360;
        float[] fArr2 = new float[360];
        int i2 = -1;
        int[] iArr = new int[samples];
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        float f = -1.0f;
        while (true) {
            i = -16777216;
            if (i4 >= height) {
                break;
            }
            int i6 = i3;
            while (i6 < width) {
                int pixel = bitmap.getPixel(i6, i4);
                if (((pixel >> 24) & 255) >= 128) {
                    int i7 = pixel | (-16777216);
                    Color.colorToHSV(i7, fArr);
                    int i8 = (int) fArr[i3];
                    if (i8 >= 0) {
                        c = 360;
                        if (i8 < 360) {
                            if (i5 < samples) {
                                iArr[i5] = i7;
                                i5++;
                            }
                            fArr2[i8] = fArr2[i8] + (fArr[1] * fArr[2]);
                            if (fArr2[i8] > f) {
                                f = fArr2[i8];
                                i2 = i8;
                            }
                        }
                        i6 += sqrt;
                        c2 = c;
                        i3 = 0;
                    }
                }
                c = 360;
                i6 += sqrt;
                c2 = c;
                i3 = 0;
            }
            i4 += sqrt;
            i3 = 0;
        }
        SparseArray sparseArray = new SparseArray();
        float f2 = -1.0f;
        for (int i9 = 0; i9 < i5; i9++) {
            int i10 = iArr[i9];
            Color.colorToHSV(i10, fArr);
            if (((int) fArr[0]) == i2) {
                float f3 = fArr[1];
                float f4 = fArr[2];
                int i11 = ((int) (100.0f * f3)) + ((int) (10000.0f * f4));
                float f5 = f3 * f4;
                Float f6 = (Float) sparseArray.get(i11);
                if (f6 != null) {
                    f5 += f6.floatValue();
                }
                sparseArray.put(i11, Float.valueOf(f5));
                if (f5 > f2) {
                    i = i10;
                    f2 = f5;
                }
            }
        }
        return i;
    }
}
