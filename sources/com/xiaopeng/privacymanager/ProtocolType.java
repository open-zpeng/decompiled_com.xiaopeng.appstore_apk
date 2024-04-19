package com.xiaopeng.privacymanager;
/* loaded from: classes3.dex */
public class ProtocolType {
    public static final int EN_APP_STORE = 207;
    public static final int EN_MAP = 2001;
    public static final int EN_MAP_LOCATION_AUTH = 203;
    public static final int EN_MAP_USER_AGREEMENT = 202;
    public static final int EN_SPEECH_DATA_AUTH = 204;
    public static final int EN_SPEECH_USER_EXPERIENCE = 205;
    public static final int EN_USER_EXPERIENCE = 206;
    public static final int EN_XMART = 201;
    public static final int NONE = 0;
    public static final int ZH_APP_STORE = 105;
    public static final int ZH_SPEECH = 1001;
    public static final int ZH_SPEECH_POLICY = 104;
    public static final int ZH_SPEECH_PRIVACY_DISCLAIMER = 106;
    public static final int ZH_SPEECH_USER_EXPERIENCE = 103;
    public static final int ZH_USER_AGREEMENT = 102;
    public static final int ZH_XMART = 101;

    public static int[] getSubTypes(int i) {
        return i == 1001 ? new int[]{103, 104} : i == 2001 ? new int[]{202, EN_MAP_LOCATION_AUTH} : new int[]{i};
    }
}
