package com.xiaopeng.appstore.xpcommon;

import android.car.Car;
import android.os.SystemProperties;
import com.orhanobut.logger.Logger;
/* loaded from: classes2.dex */
public class CarUtils {
    public static final String CAR_TYPE_D20 = "D20";
    public static final String CAR_TYPE_D21B = "D21B";
    public static final String CAR_TYPE_D22 = "D22";
    public static final String CAR_TYPE_D55 = "D55";
    public static final String CAR_TYPE_E28 = "E28";
    public static final String CAR_TYPE_E38 = "E38";
    public static final String CDU_TYPE_A1_D20 = "A1";
    public static final String CDU_TYPE_A3_D21 = "A3";
    public static final String CDU_TYPE_Q1_E28 = "Q1";
    public static final String CDU_TYPE_Q2_D21 = "Q2";
    public static final String CDU_TYPE_Q3_D55 = "Q3";
    public static final String CDU_TYPE_Q5_D20 = "Q5";
    public static final String CDU_TYPE_Q6_D22 = "Q6";
    public static final String CDU_TYPE_Q7_E38 = "Q7";
    public static final String CDU_TYPE_Q8_E28A = "Q8";
    public static final int HORIZONTAL_SCREEN = 2;
    public static final int ID_MCU_IG_STATUS = 557847561;
    static final String PROPERTY_NOT_SUPPORT = "0";
    public static final String PROPERTY_SDC = "persist.sys.xiaopeng.scissorsGate";
    static final String PROPERTY_SUPPORT = "1";
    private static final String TAG = "CarUtils";
    public static final int VERTICAL_SCREEN = 1;
    private static boolean mIsAppletType = false;
    private static int mScreenType;

    private CarUtils() {
    }

    public static boolean isE28() {
        return getCduType().contains("Q1");
    }

    public static boolean isD55() {
        return getCduType().contains("Q3");
    }

    public static boolean isD55A() {
        return getCduType().contains("Q3A");
    }

    public static boolean isE38() {
        return getCduType().contains("Q7");
    }

    public static boolean isF30() {
        return getCduType().contains("Q9");
    }

    public static boolean isE28A() {
        return getCduType().contains("Q8");
    }

    public static int getScreenType() {
        Logger.t(TAG).d("CarUtils getScreenType: " + mScreenType);
        return mScreenType;
    }

    public static boolean getHasApplet() {
        return mIsAppletType;
    }

    public static void setScreenType(int isHorizontal) {
        Logger.t(TAG).d("CarUtils setScreenType: " + isHorizontal);
        mScreenType = isHorizontal;
    }

    public static void setHasApplet(boolean isAppletType) {
        mIsAppletType = isAppletType;
    }

    public static boolean isScissorsGate() {
        return "1".equals(SystemProperties.get(PROPERTY_SDC));
    }

    public static String getAppStoreCarType() {
        String cduType = getCduType();
        return "Q5".equals(cduType) ? CAR_TYPE_D20 : "Q2".equals(cduType) ? CAR_TYPE_D21B : "Q6".equals(cduType) ? CAR_TYPE_D22 : "Q3".equals(cduType) ? "D55" : "Q1".equals(cduType) ? CAR_TYPE_E28 : "Q7".equals(cduType) ? CAR_TYPE_E38 : "Q8".equals(cduType) ? "E28A" : getCarType();
    }

    @Deprecated
    private static String getCarType() {
        try {
            return Car.getHardwareCarType();
        } catch (Exception e) {
            Logger.t(TAG).w("getCarType error " + e, new Object[0]);
            return "";
        }
    }

    public static String getCduType() {
        try {
            return Car.getXpCduType();
        } catch (Exception e) {
            Logger.t(TAG).w("getCduType error " + e, new Object[0]);
            return "";
        }
    }

    public static String getRegion() {
        try {
            String str = SystemProperties.get("ro.xiaopeng.software", "");
            return (str == null || str.isEmpty()) ? str : str.substring(7, 9);
        } catch (Exception e) {
            Logger.t(TAG).w("getRegion error " + e, new Object[0]);
            return "";
        }
    }

    public static boolean isEURegion() {
        return "EU".equals(getRegion());
    }
}
