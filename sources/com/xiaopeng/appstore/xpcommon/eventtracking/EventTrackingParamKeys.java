package com.xiaopeng.appstore.xpcommon.eventtracking;

import com.xiaopeng.speech.vui.constants.VuiConstants;
/* loaded from: classes2.dex */
public enum EventTrackingParamKeys {
    KEY_TYPE(0, VuiConstants.ELEMENT_TYPE),
    KEY_RESULT(1, "result"),
    KEY_NAME(2, "name"),
    KEY_COUNT(3, "count"),
    KEY_SOURCE(4, "source"),
    KEY_INSTALLING_APP_NAME(5, "installing_app_name"),
    KEY_INSTALLING_PACKAGE_NAME(6, "installing_package_name"),
    KEY_INITIATE_APP_NAME(7, "Initiate_app_name"),
    KEY_INITIATE_PACKAGE_NAME(8, "Initiate_package_name"),
    KEY_INSTALL_APP_LIST(9, "appList"),
    KEY_END_TIME(11, "ent_time"),
    KEY_START_TIME(10, "start_time");
    
    public static final int INVALID_INDEX = -1;
    private int mIndex;
    private String mParamKey;

    EventTrackingParamKeys(int index, String paramKey) {
        this.mIndex = index;
        this.mParamKey = paramKey;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public String getParamKey() {
        return this.mParamKey;
    }

    public static int getIndex(EventTrackingParamKeys key) {
        for (int i = 0; i < values().length; i++) {
            EventTrackingParamKeys eventTrackingParamKeys = values()[i];
            if (eventTrackingParamKeys.getIndex() == key.getIndex() && eventTrackingParamKeys.getParamKey().equals(key.getParamKey())) {
                return i;
            }
        }
        return -1;
    }
}
